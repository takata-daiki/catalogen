/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */

package org.teiid.metadata.index;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jboss.vfs.VirtualFile;
import org.jboss.vfs.VirtualFileFilter;
import org.teiid.adminapi.impl.ModelMetaData;
import org.teiid.adminapi.impl.VDBMetaData;
import org.teiid.core.TeiidException;
import org.teiid.core.TeiidRuntimeException;
import org.teiid.core.index.IEntryResult;
import org.teiid.core.util.StringUtil;
import org.teiid.internal.core.index.Index;
import org.teiid.metadata.*;
import org.teiid.metadata.FunctionMethod.Determinism;
import org.teiid.metadata.FunctionMethod.PushDown;
import org.teiid.query.metadata.TransformationMetadata;
import org.teiid.query.metadata.TransformationMetadata.Resource;


/**
 * Loads MetadataRecords from index files.  
 */
public class IndexMetadataStore extends MetadataStore {
	private static final long serialVersionUID = -7606516749442597438L;

	private Index[] indexes;
	private RecordFactory recordFactory = new RecordFactory() {
		
	protected AbstractMetadataRecord getMetadataRecord(char[] record) {
		if (record == null || record.length == 0) {
			return null;
		}
		char c = record[0];
		switch (c) {
		case MetadataConstants.RECORD_TYPE.ANNOTATION: {
	        final List<String> tokens = RecordFactory.getStrings(record, IndexConstants.RECORD_STRING.RECORD_DELIMITER);

	        // Extract the index version information from the record 
	        int indexVersion = recordFactory.getIndexVersion(record);
	        String uuid = tokens.get(2);
	        
	        // The tokens are the standard header values
	        int tokenIndex = 6;

	        if(recordFactory.includeAnnotationProperties(indexVersion)) {
				// The next token are the properties, ignore it not going to be read any way
	            tokenIndex++;
	        }

	        // The next token is the description
	        annotationCache.put(uuid, tokens.get(tokenIndex++));
	        return null;
		}
		case MetadataConstants.RECORD_TYPE.PROPERTY: {
            final List<String> tokens = RecordFactory.getStrings(record, IndexConstants.RECORD_STRING.RECORD_DELIMITER);

            String uuid = tokens.get(1);
	    	LinkedHashMap<String, String> result = extensionCache.get(uuid);
	    	if (result == null) {
	    		result = new LinkedHashMap<String, String>(); 
	    		extensionCache.put(uuid, result);
	    	}
            // The tokens are the standard header values
            int tokenIndex = 2;
            result.put( tokens.get(tokenIndex++), tokens.get(tokenIndex++));
			return null;
		}
		default:
			AbstractMetadataRecord abstractMetadataRecord = super.getMetadataRecord(record);
			if (abstractMetadataRecord == null) {
				return null; //record type no longer used
			}
			String parentName = null;
			if (record[0] == MetadataConstants.RECORD_TYPE.TABLE) {
				parentName = ((Table)abstractMetadataRecord).getParent().getName();
			} else if (record[0] == MetadataConstants.RECORD_TYPE.CALLABLE) {
				parentName = ((Procedure)abstractMetadataRecord).getParent().getName();
			}
			if (parentName != null) {
				Map<Character, List<AbstractMetadataRecord>> map = schemaEntries.get(parentName);
				if (map == null) {
					map = new HashMap<Character, List<AbstractMetadataRecord>>();
					schemaEntries.put(parentName, map);
				}
				List<AbstractMetadataRecord> typeRecords = map.get(record[0]);
				if (typeRecords == null) {
					typeRecords = new ArrayList<AbstractMetadataRecord>();
					map.put(record[0], typeRecords);
				}
				typeRecords.add(abstractMetadataRecord);
			}
			Map<String, AbstractMetadataRecord> uuidMap = getByType(record[0]);
			uuidMap.put(abstractMetadataRecord.getUUID(), abstractMetadataRecord);
			if (parentId != null) {
				List<AbstractMetadataRecord> typeChildren = getByParent(parentId, record[0], AbstractMetadataRecord.class, true);
				typeChildren.add(abstractMetadataRecord);
			}
			return abstractMetadataRecord;
		}
		}
	};
	private Map<String, String> annotationCache = new HashMap<String, String>();
	private Map<String, LinkedHashMap<String, String>> extensionCache = new HashMap<String, LinkedHashMap<String,String>>();
	//map of schema name to record entries
	private Map<String, Map<Character, List<AbstractMetadataRecord>>> schemaEntries = new HashMap<String, Map<Character, List<AbstractMetadataRecord>>>();
	//map of parent uuid to record entries
	private Map<String, Map<Character, List<AbstractMetadataRecord>>> childRecords = new HashMap<String, Map<Character, List<AbstractMetadataRecord>>>();
	//map of type to maps of uuids
	private Map<Character, LinkedHashMap<String, AbstractMetadataRecord>> allRecords = new HashMap<Character, LinkedHashMap<String, AbstractMetadataRecord>>();
	private HashSet<VirtualFile> indexFiles = new HashSet<VirtualFile>();
	private LinkedHashMap<String, Resource> vdbEntries;
	private boolean loaded = false;
	
	public IndexMetadataStore() {
		
	}
	
	/**
	 * Load index metadata from a URL.  For the system and test vdbs
	 * @param url
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public IndexMetadataStore(VirtualFile vdb) throws IOException, URISyntaxException {
		List<VirtualFile> children = vdb.getChildrenRecursively(new VirtualFileFilter() {
			@Override
			public boolean accepts(VirtualFile file) {
				return file.getName().endsWith(IndexConstants.NAME_DELIM_CHAR+IndexConstants.INDEX_EXT);
			}
		});
		
		for (VirtualFile f: children) {
			addIndexFile(f);
		}
		//just use the defaults for model visibility
		addEntriesPlusVisibilities(vdb, new VDBMetaData());
	}
	
    Map<String, AbstractMetadataRecord> getByType(char type) {
		LinkedHashMap<String, AbstractMetadataRecord> uuidMap = allRecords.get(type);
		if (uuidMap == null) {
			uuidMap = new LinkedHashMap<String, AbstractMetadataRecord>();
			allRecords.put(type, uuidMap);
		}
		return uuidMap;
	}
	
	<T extends AbstractMetadataRecord> List<T> getByParent(String parentId, char type, @SuppressWarnings("unused") Class<T> clazz, boolean create) {
		Map<Character, List<AbstractMetadataRecord>> children = childRecords.get(parentId);
		if (children == null) {
			children = new HashMap<Character, List<AbstractMetadataRecord>>();
			childRecords.put(parentId, children);
		}
		List<AbstractMetadataRecord> typeChildren = children.get(type);
		if (typeChildren == null) {
			if (!create) {
				return Collections.emptyList();
			} 
			typeChildren = new ArrayList<AbstractMetadataRecord>(2);
			children.put(type, typeChildren);
		}
		return (List<T>) typeChildren;
	}

    private void loadAll() {
    	for (Index index : this.indexes) {
    		try {
				IEntryResult[] results = SimpleIndexUtil.queryIndex(new Index[] {index}, new char[0], true, true, false);
				recordFactory.getMetadataRecord(results);
			} catch (TeiidException e) {
				 throw new TeiidRuntimeException(RuntimeMetadataPlugin.Event.TEIID80000, e);
			}
    	}
    	//associate the annotation/extension metadata
    	for (Map<String, AbstractMetadataRecord> map : allRecords.values()) {
    		for (AbstractMetadataRecord metadataRecord : map.values()) {
				String uuid = metadataRecord.getUUID();
				
				metadataRecord.setAnnotation(this.annotationCache.get(uuid));
				metadataRecord.setProperties(this.extensionCache.get(uuid));
    		}
    	}
    }
    
	public void load(String modelName, Collection<Datatype> systemDatatypes) throws IOException {
		// there are multiple threads trying to load this, since the initial index lading is not 
		// optimized for multi-thread loading this locking to sync will work
		synchronized (this) {
			if (!this.loaded) {
				
		    	ArrayList<Index> tmp = new ArrayList<Index>();
				for (VirtualFile f : indexFiles) {
					Index index = new Index(f, true);
					index.setDoCache(true);
		            tmp.add(index);
				}
				this.indexes = tmp.toArray(new Index[tmp.size()]);
				loadAll();
				//force close, since we cached the index files
				for (Index index : tmp) {
					index.close(); 
				}
				Map<String, AbstractMetadataRecord> uuidToRecord = getByType(MetadataConstants.RECORD_TYPE.DATATYPE);
				if (systemDatatypes != null) {
					for (Datatype datatype : systemDatatypes) {
						uuidToRecord.put(datatype.getUUID(), datatype);
					}
				}
				for (AbstractMetadataRecord datatypeRecordImpl : uuidToRecord.values()) {
					addDatatype((Datatype) datatypeRecordImpl);
				}
				this.loaded = true;
			}
		}
		
    	// the index map below is keyed by uuid not modelname, so map lookup is not possible
    	Collection<AbstractMetadataRecord> modelRecords = getByType(MetadataConstants.RECORD_TYPE.MODEL).values();
    	for (AbstractMetadataRecord modelRecord:modelRecords) {
    		Schema s = (Schema) modelRecord;
    		if (modelName == null || s.getName().equalsIgnoreCase(modelName)) {
    			addSchema(s);
    			getTables(s);
    			getProcedures(s);
    			if (modelName != null) {
    				break;
    			}
    		}
    	}
    }

    public void addIndexFile(VirtualFile f) {
    	this.indexFiles.add(f);
    }
    
	public void addEntriesPlusVisibilities(VirtualFile root, VDBMetaData vdb) throws IOException {
		LinkedHashMap<String, Resource> visibilityMap = new LinkedHashMap<String, Resource>();
		for(VirtualFile f: root.getChildrenRecursively()) {
			if (f.isFile()) {
				// remove the leading vdb name from the entry
				String path = f.getPathName().substring(root.getPathName().length());
				if (!path.startsWith("/")) { //$NON-NLS-1$
					path = "/" + path; //$NON-NLS-1$
				}
				visibilityMap.put(path, new Resource(f, isFileVisible(f.getPathName(), vdb))); 
			}
		}
		this.vdbEntries = visibilityMap;
	}
	
	private boolean isFileVisible(String pathInVDB, VDBMetaData vdb) {

		if (pathInVDB.endsWith(".xmi")) { //$NON-NLS-1$
			String modelName = StringUtil.getFirstToken(StringUtil.getLastToken(pathInVDB, "/"), "."); //$NON-NLS-1$ //$NON-NLS-2$
	
			ModelMetaData model = vdb.getModel(modelName);
			if (model != null) {
				return model.isVisible();
			}
		}
		
		if (pathInVDB.startsWith("META-INF/")) {//$NON-NLS-1$
			return false;
		}
		
        String entry = StringUtil.getLastToken(pathInVDB, "/"); //$NON-NLS-1$
        
        // index files should not be visible
		if( entry.endsWith(VdbConstants.INDEX_EXT) || entry.endsWith(VdbConstants.SEARCH_INDEX_EXT)) {
			return false;
		}

		// deployment file should not be visible
        if(entry.equalsIgnoreCase(VdbConstants.DEPLOYMENT_FILE)) {
            return false;
        }
        
        // any other file should be visible
        return true;		
	}
	
	public LinkedHashMap<String, Resource> getEntriesPlusVisibilities(){
		return this.vdbEntries;
	}
	
	/**
	 * override for thread safety
	 */
    @Override
    public synchronized void addSchema(Schema schema) {
    	super.addSchema(schema);
    }

	/**
	 * override for thread safety
	 */
    @Override
    public synchronized Schema getSchema(String name) {
    	return super.getSchema(name);
    }
    
    private void getTables(Schema model) {
    	Map<Character, List<AbstractMetadataRecord>> entries = schemaEntries.get(model.getName());
		if (entries == null) {
			return;
		}
		
		List recs = entries.get(MetadataConstants.RECORD_TYPE.TABLE);
		if (recs == null) {
			return;
		}
		
		List<Table> records = recs;
		
		for (Table tableRecord : records) {
	    	List<Column> columns = new ArrayList<Column>(getByParent(tableRecord.getUUID(), MetadataConstants.RECORD_TYPE.COLUMN, Column.class, false));
	        for (Column columnRecordImpl : columns) {
	    		columnRecordImpl.setDatatype((Datatype) getByType(MetadataConstants.RECORD_TYPE.DATATYPE).get(columnRecordImpl.getDatatypeUUID()));
	    		columnRecordImpl.setParent(tableRecord);
	    		String fullName = columnRecordImpl.getName();
	    		if (fullName.startsWith(tableRecord.getName() + '.')) {
	    			columnRecordImpl.setName(new String(fullName.substring(tableRecord.getName().length() + 1)));
	    		}
			}
	        Collections.sort(columns);
	        tableRecord.setColumns(columns);
	        tableRecord.setAccessPatterns(getByParent(tableRecord.getUUID(), MetadataConstants.RECORD_TYPE.ACCESS_PATTERN, KeyRecord.class, false));
	        Map<String, Column> uuidColumnMap = new HashMap<String, Column>();
	        for (Column columnRecordImpl : columns) {
				uuidColumnMap.put(columnRecordImpl.getUUID(), columnRecordImpl);
			}
	        for (KeyRecord columnSetRecordImpl : tableRecord.getAccessPatterns()) {
				loadColumnSetRecords(columnSetRecordImpl, uuidColumnMap);
				columnSetRecordImpl.setParent(tableRecord);
			}
	        tableRecord.setForiegnKeys(getByParent(tableRecord.getUUID(), MetadataConstants.RECORD_TYPE.FOREIGN_KEY, ForeignKey.class, false));
	        for (ForeignKey foreignKeyRecord : tableRecord.getForeignKeys()) {
	        	KeyRecord pk = (KeyRecord) getRecordByType(foreignKeyRecord.getUniqueKeyID(), MetadataConstants.RECORD_TYPE.PRIMARY_KEY, false);
	        	if (pk == null) {
	        		pk = (KeyRecord) getRecordByType(foreignKeyRecord.getUniqueKeyID(), MetadataConstants.RECORD_TYPE.UNIQUE_KEY);
	        	}
	        	foreignKeyRecord.setPrimaryKey(pk);
	        	loadColumnSetRecords(foreignKeyRecord, uuidColumnMap);
	        	foreignKeyRecord.setParent(tableRecord);
			}
	        tableRecord.setUniqueKeys(getByParent(tableRecord.getUUID(), MetadataConstants.RECORD_TYPE.UNIQUE_KEY, KeyRecord.class, false));
	        for (KeyRecord columnSetRecordImpl : tableRecord.getUniqueKeys()) {
				loadColumnSetRecords(columnSetRecordImpl, uuidColumnMap);
				columnSetRecordImpl.setParent(tableRecord);
			}
	        List<KeyRecord> indexRecords = tableRecord.getIndexes();
			for (int i = 0; i < indexRecords.size(); i++) {
	        	indexRecords.set(i, (KeyRecord) getRecordByType(indexRecords.get(i).getUUID(), MetadataConstants.RECORD_TYPE.INDEX));
	        }
	        for (KeyRecord columnSetRecordImpl : indexRecords) {
				loadColumnSetRecords(columnSetRecordImpl, uuidColumnMap);
				columnSetRecordImpl.setParent(tableRecord);
			}
	        if (tableRecord.getPrimaryKey() != null) {
	        	KeyRecord primaryKey = (KeyRecord) getRecordByType(tableRecord.getPrimaryKey().getUUID(), MetadataConstants.RECORD_TYPE.PRIMARY_KEY);
	        	loadColumnSetRecords(primaryKey, uuidColumnMap);
	        	primaryKey.setParent(tableRecord);
	        	tableRecord.setPrimaryKey(primaryKey);
	        }
	        String groupUUID = tableRecord.getUUID();
	        if (tableRecord.isVirtual()) {
	        	TransformationRecordImpl update = (TransformationRecordImpl)getRecordByType(groupUUID, MetadataConstants.RECORD_TYPE.UPDATE_TRANSFORM,false);
		        if (update != null) {
		        	tableRecord.setUpdatePlan(update.getTransformation());
		        }
		        TransformationRecordImpl insert = (TransformationRecordImpl)getRecordByType(groupUUID, MetadataConstants.RECORD_TYPE.INSERT_TRANSFORM,false);
		        if (insert != null) {
		        	tableRecord.setInsertPlan(insert.getTransformation());
		        }
		        TransformationRecordImpl delete = (TransformationRecordImpl)getRecordByType(groupUUID, MetadataConstants.RECORD_TYPE.DELETE_TRANSFORM,false);
		        if (delete != null) {
		        	tableRecord.setDeletePlan(delete.getTransformation());
		        }
		        TransformationRecordImpl select = (TransformationRecordImpl)getRecordByType(groupUUID, MetadataConstants.RECORD_TYPE.SELECT_TRANSFORM,false);
		        // this group may be an xml document            
		        if(select == null) {
			        select = (TransformationRecordImpl)getRecordByType(groupUUID, MetadataConstants.RECORD_TYPE.MAPPING_TRANSFORM,false);
		        }
		        if (select != null) {
			        tableRecord.setSelectTransformation(select.getTransformation());
			        tableRecord.setBindings(select.getBindings());
			        tableRecord.setSchemaPaths(select.getSchemaPaths());
			        tableRecord.setResourcePath(select.getResourcePath());
		        }
	        }
	        if (tableRecord.isMaterialized()) {
	        	tableRecord.setMaterializedStageTable((Table)getByType(MetadataConstants.RECORD_TYPE.TABLE).get(tableRecord.getMaterializedStageTable().getUUID()));
	        	tableRecord.setMaterializedTable((Table)getByType(MetadataConstants.RECORD_TYPE.TABLE).get(tableRecord.getMaterializedTable().getUUID()));
	        }
			model.addTable(tableRecord);
		}
    }

	private Column findElement(String fullName) {
		Column columnRecord = (Column)getRecordByType(fullName, MetadataConstants.RECORD_TYPE.COLUMN);
    	columnRecord.setDatatype((Datatype) getByType(MetadataConstants.RECORD_TYPE.DATATYPE).get(columnRecord.getDatatypeUUID()));
        return columnRecord;
    }
	    
    private AbstractMetadataRecord getRecordByType(final String entityName, final char recordType) {
    	return getRecordByType(entityName, recordType, true);
    }
    
    private AbstractMetadataRecord getRecordByType(final String entityName, final char recordType, boolean mustExist) {
    	// Query the index files
		AbstractMetadataRecord record = getByType(recordType).get(entityName);
    	
        if(record == null) {
        	if (mustExist) {
			// there should be only one for the UUID
	             throw new TeiidRuntimeException(RuntimeMetadataPlugin.Event.TEIID80002, entityName+TransformationMetadata.NOT_EXISTS_MESSAGE);
        	} 
        	return null;
		} 
        return record;
    }
    
    private void getProcedures(Schema model) {
		Map<Character, List<AbstractMetadataRecord>> entries = schemaEntries.get(model.getName());
		if (entries == null) {
			return;
		}
		
		List recs = entries.get(MetadataConstants.RECORD_TYPE.CALLABLE);
		if (recs == null) {
			return;
		}
		
		List<Procedure> records = recs;
		for (Procedure procedureRecord : records) {
	        // get the parameter metadata info
	        for (int i = 0; i < procedureRecord.getParameters().size(); i++) {
	            ProcedureParameter paramRecord = (ProcedureParameter) this.getRecordByType(procedureRecord.getParameters().get(i).getUUID(), MetadataConstants.RECORD_TYPE.CALLABLE_PARAMETER);
	            paramRecord.setDatatype((Datatype) getByType(MetadataConstants.RECORD_TYPE.DATATYPE).get(paramRecord.getDatatypeUUID()));
	            procedureRecord.getParameters().set(i, paramRecord);
	            paramRecord.setProcedure(procedureRecord);
	        }
	    	
	        ColumnSet<Procedure> result = procedureRecord.getResultSet();
	        if(result != null) {
	            ColumnSet<Procedure> resultRecord = (ColumnSet<Procedure>) getRecordByType(result.getUUID(), MetadataConstants.RECORD_TYPE.RESULT_SET, false);
	            if (resultRecord != null) {
	            	resultRecord.setParent(procedureRecord);
	            	resultRecord.setName(RecordFactory.getShortName(resultRecord.getName()));
		            loadColumnSetRecords(resultRecord, null);
		            procedureRecord.setResultSet(resultRecord);
	            }
	            //it is ok to be null here.  it will happen when a 
	            //virtual stored procedure is created from a
	            //physical stored procedure without a result set
	            //TODO: find a better fix for this
	        }

	        // if this is a virtual procedure get the procedure plan
	        if(procedureRecord.isVirtual()) {
	    		TransformationRecordImpl transformRecord = (TransformationRecordImpl)getRecordByType(procedureRecord.getUUID(), MetadataConstants.RECORD_TYPE.PROC_TRANSFORM, false);
	    		if(transformRecord != null) {
	    			procedureRecord.setQueryPlan(transformRecord.getTransformation());
	    		}
	        } else if (procedureRecord.isFunction()) {
	        	FunctionParameter outputParam = null;
	        	List<FunctionParameter> args = new ArrayList<FunctionParameter>(procedureRecord.getParameters().size() - 1);
	        	boolean valid = true;
	        	for (ProcedureParameter param : procedureRecord.getParameters()) {
					FunctionParameter fp = new FunctionParameter();
					fp.setName(param.getName());
					fp.setDescription(param.getAnnotation());
					fp.setType(param.getRuntimeType());
					switch (param.getType()) {
					case ReturnValue:
						if (outputParam != null) {
							valid = false;
						}
						outputParam = fp;
						break;
					case In:
						args.add(fp);
						break;
					default:
						valid = false;
					}
				}
	        	if (valid && outputParam != null) {
	        	    FunctionMethod function = new FunctionMethod(procedureRecord.getName(), procedureRecord.getAnnotation(), model.getName(), PushDown.MUST_PUSHDOWN, 
		        			null, null, args, outputParam, false, Determinism.DETERMINISTIC);
		        	FunctionMethod.convertExtensionMetadata(procedureRecord, function);
					model.addFunction(function);
					continue;
	        	}
	        }
			model.addProcedure(procedureRecord);
		}
    }
    
	private void loadColumnSetRecords(ColumnSet<?> indexRecord, Map<String, Column> columns) {
		for (int i = 0; i < indexRecord.getColumns().size(); i++) {
			String uuid = indexRecord.getColumns().get(i).getUUID();
			Column c = null;
			if (columns != null) {
				c = columns.get(uuid);
			} else {
				c = findElement(uuid);
				c.setName(RecordFactory.getShortName(c.getName()));
			}
			indexRecord.getColumns().set(i, c);
			if (columns == null) {
				c.setParent(indexRecord);
			}
		}
	}

}
