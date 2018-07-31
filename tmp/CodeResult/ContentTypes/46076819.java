//Copyright (C) 2010  Novabit Informationssysteme GmbH
//
//This file is part of Nuclos.
//
//Nuclos is free software: you can redistribute it and/or modify
//it under the terms of the GNU Affero General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//Nuclos is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU Affero General Public License for more details.
//
//You should have received a copy of the GNU Affero General Public License
//along with Nuclos.  If not, see <http://www.gnu.org/licenses/>.
package org.nuclos.server.dbtransfer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

import javax.annotation.security.RolesAllowed;
import javax.json.JsonObject;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;

import org.nuclos.api.businessobject.Query;
import org.nuclos.api.exception.BusinessException;
import org.nuclos.api.provider.BusinessObjectProvider;
import org.nuclos.api.provider.QueryProvider;
import org.nuclos.api.service.MessageContextService;
import org.nuclos.businessentity.NucletIntegrationPoint;
import org.nuclos.common.ApplicationProperties;
import org.nuclos.common.E;
import org.nuclos.common.EntityMeta;
import org.nuclos.common.EntityMetaVO;
import org.nuclos.common.FieldMeta;
import org.nuclos.common.JMSConstants;
import org.nuclos.common.JsonUtils;
import org.nuclos.common.Mutable;
import org.nuclos.common.NucletConstants;
import org.nuclos.common.NucletEntityMeta;
import org.nuclos.common.NucletFieldMeta;
import org.nuclos.common.NuclosBusinessException;
import org.nuclos.common.NuclosFatalException;
import org.nuclos.common.NuclosScript;
import org.nuclos.common.RigidUtils;
import org.nuclos.common.SF;
import org.nuclos.common.SearchConditionUtils;
import org.nuclos.common.SimpleDbField;
import org.nuclos.common.SourceResultHelper;
import org.nuclos.common.SpringApplicationContextHolder;
import org.nuclos.common.UID;
import org.nuclos.common.collect.collectable.searchcondition.CollectableSearchCondition;
import org.nuclos.common.collect.collectable.searchcondition.ComparisonOperator;
import org.nuclos.common.collection.CollectionUtils;
import org.nuclos.common.collection.Pair;
import org.nuclos.common.customcomp.resplan.PlanElement;
import org.nuclos.common.dal.DalCallResult;
import org.nuclos.common.dal.vo.Delete;
import org.nuclos.common.dal.vo.EntityObjectVO;
import org.nuclos.common.dblayer.DbObjectMessage;
import org.nuclos.common.dbtransfer.NucletContentHashMap;
import org.nuclos.common.dbtransfer.NucletContentMap;
import org.nuclos.common.dbtransfer.PreviewPart;
import org.nuclos.common.dbtransfer.Transfer;
import org.nuclos.common.dbtransfer.TransferConstants;
import org.nuclos.common.dbtransfer.TransferEO;
import org.nuclos.common.dbtransfer.TransferNuclet;
import org.nuclos.common.dbtransfer.TransferOption;
import org.nuclos.common.dbtransfer.ZipInput;
import org.nuclos.common.dbtransfer.ZipOutput;
import org.nuclos.common.report.ejb3.IJobKey;
import org.nuclos.common.report.ejb3.JobKeyImpl;
import org.nuclos.common.spring.AnnotationJaxb2Marshaller;
import org.nuclos.common2.BoDataSet;
import org.nuclos.common2.InternalTimestamp;
import org.nuclos.common2.LangUtils;
import org.nuclos.common2.ServiceLocator;
import org.nuclos.common2.StringUtils;
import org.nuclos.common2.exception.CommonBusinessException;
import org.nuclos.common2.exception.CommonPermissionException;
import org.nuclos.common2.exception.CommonValidationException;
import org.nuclos.server.attribute.ejb3.LayoutFacadeBean;
import org.nuclos.server.attribute.ejb3.LayoutFacadeLocal;
import org.nuclos.server.autosync.AutoDbSetup;
import org.nuclos.server.autosync.SchemaHelper;
import org.nuclos.server.common.AttributeCache;
import org.nuclos.server.common.DatasourceCache;
import org.nuclos.server.common.IMyDataBaseConnection;
import org.nuclos.server.common.LocalCachesUtil;
import org.nuclos.server.common.LockedTabProgressNotifier;
import org.nuclos.server.common.MyDataBaseConnection.IStreamingFilterProvider;
import org.nuclos.server.common.NuclosSystemParameters;
import org.nuclos.server.common.SecurityCache;
import org.nuclos.server.common.ServerParameterProvider;
import org.nuclos.server.common.ejb3.EntityObjectFacadeLocal;
import org.nuclos.server.common.ejb3.LocaleFacadeLocal;
import org.nuclos.server.common.ejb3.NuclosFacadeBean;
import org.nuclos.server.customcomp.resplan.ResPlanConfigVO;
import org.nuclos.server.dal.DalUtils;
import org.nuclos.server.dal.processor.jdbc.impl.DataLanguageMetaDataProcessor;
import org.nuclos.server.dal.processor.jdbc.impl.EntityObjectProcessor;
import org.nuclos.server.dal.processor.jdbc.impl.PreferenceProcessor;
import org.nuclos.server.dal.processor.nuclet.IEntityObjectProcessor;
import org.nuclos.server.database.SpringDataBaseHelper;
import org.nuclos.server.dblayer.DbAccess;
import org.nuclos.server.dblayer.DbException;
import org.nuclos.server.dblayer.DbObjectHelper;
import org.nuclos.server.dblayer.DbObjectHelper.DbObject;
import org.nuclos.server.dblayer.DbUtils;
import org.nuclos.server.dblayer.IBatch;
import org.nuclos.server.dblayer.MetaDbEntityWrapper;
import org.nuclos.server.dblayer.MetaDbFieldWrapper;
import org.nuclos.server.dblayer.MetaDbHelper;
import org.nuclos.server.dblayer.MetaDbProvider;
import org.nuclos.server.dblayer.PersistentDbAccess;
import org.nuclos.server.dblayer.SchemaDiff;
import org.nuclos.server.dblayer.impl.SchemaUtils;
import org.nuclos.server.dblayer.query.DbColumnExpression;
import org.nuclos.server.dblayer.query.DbDelete;
import org.nuclos.server.dblayer.query.DbExpression;
import org.nuclos.server.dblayer.query.DbFrom;
import org.nuclos.server.dblayer.query.DbQuery;
import org.nuclos.server.dblayer.query.DbQueryBuilder;
import org.nuclos.server.dblayer.query.DbSelection;
import org.nuclos.server.dblayer.statements.DbMap;
import org.nuclos.server.dblayer.statements.DbPlainStatement;
import org.nuclos.server.dblayer.statements.DbStatement;
import org.nuclos.server.dblayer.statements.DbStructureChange;
import org.nuclos.server.dblayer.statements.DbUpdateStatement;
import org.nuclos.server.dblayer.structure.DbArtifact;
import org.nuclos.server.dblayer.structure.DbConstraint;
import org.nuclos.server.dblayer.structure.DbConstraint.DbForeignKeyConstraint;
import org.nuclos.server.dblayer.structure.DbConstraint.DbPrimaryKeyConstraint;
import org.nuclos.server.dblayer.structure.DbConstraint.DbUniqueConstraint;
import org.nuclos.server.dblayer.structure.DbNamedObject;
import org.nuclos.server.dblayer.structure.DbSimpleView;
import org.nuclos.server.dblayer.structure.DbTable;
import org.nuclos.server.dblayer.structure.DbTableArtifact;
import org.nuclos.server.dblayer.util.DbObjectUtils;
import org.nuclos.server.dblayer.util.DbObjectUtils.LocalIdentifierStore;
import org.nuclos.server.dblayer.util.StatementToStringVisitor;
import org.nuclos.server.dbtransfer.content.AbstractNucletContent;
import org.nuclos.server.dbtransfer.content.IExternalizeBytes;
import org.nuclos.server.dbtransfer.content.INucletContent;
import org.nuclos.server.dbtransfer.content.ValidationType;
import org.nuclos.server.dbtransfer.serializable.DateSerializable;
import org.nuclos.server.dbtransfer.serializable.FileSerializable;
import org.nuclos.server.dbtransfer.serializable.IgnoreMarshalException;
import org.nuclos.server.dbtransfer.serializable.MetaDataRootSerializable;
import org.nuclos.server.dbtransfer.serializable.NucletContentSerializableController;
import org.nuclos.server.dbtransfer.serializable.UIDSerializable;
import org.nuclos.server.eventsupport.ejb3.EventSupportFacadeLocal;
import org.nuclos.server.genericobject.GenericObjectMetaDataCache;
import org.nuclos.server.genericobject.Modules;
import org.nuclos.server.genericobject.ejb3.GenericObjectFacadeLocal;
import org.nuclos.server.genericobject.searchcondition.CollectableSearchExpression;
import org.nuclos.server.i18n.language.data.DataLanguageFacadeBean;
import org.nuclos.server.i18n.language.data.DataLanguageServerUtils;
import org.nuclos.server.jms.NuclosJMSUtils;
import org.nuclos.server.maintenance.MaintenanceConstants;
import org.nuclos.server.maintenance.MaintenanceFacadeBean;
import org.nuclos.server.masterdata.ejb3.MasterDataFacadeLocal;
import org.nuclos.server.navigation.treenode.nuclet.content.AbstractNucletContentEntryTreeNode;
import org.nuclos.server.parameter.NuclosParameterProvider;
import org.nuclos.server.printservice.PrintServiceRepository;
import org.nuclos.server.report.SchemaCache;
import org.nuclos.server.report.ejb3.ReportFacadeBean;
import org.nuclos.server.report.ejb3.SchedulerControlFacadeLocal;
import org.nuclos.server.resource.ResourceCache;
import org.nuclos.server.statemodel.ejb3.StateFacadeLocal;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter.UnknownFieldException;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Transactional(noRollbackFor= {Exception.class})
@RolesAllowed("UseManagementConsole")
public class TransferFacadeBean extends NuclosFacadeBean implements TransferFacadeRemote, TransferConstants {

	private static final Logger LOG = LoggerFactory.getLogger(TransferFacadeBean.class);
	
	// Spring injection

	@Autowired
	private MessageContextService messageService;

	@Autowired
	private SpringDataBaseHelper dataBaseHelper;
	
	@Autowired
	private AnnotationJaxb2Marshaller jaxb2Marshaller;
	
	@Autowired
	private DataLanguageFacadeBean dataLanguageService;
	
	@Autowired
	private MaintenanceFacadeBean maintenanceFacadeBean;

	// end of Spring injection
	
	public TransferFacadeBean() {
	}

	@Override
	@RolesAllowed("Login")
	public List<TransferNuclet> getAvailableNuclets() {
		List<TransferNuclet> result = new ArrayList<TransferNuclet>();
		for (EntityObjectVO<UID> nucletObject : nucletDalProvider.getEntityObjectProcessor(E.NUCLET).getAll()) {
			boolean nuclon = nucletObject.getFieldValue(E.NUCLET.nuclon);
			boolean source = nucletObject.getFieldValue(E.NUCLET.source);
			boolean available = false;
			if (nuclon) {
				Long countDependencies = nucletDalProvider.getEntityObjectProcessor(E.NUCLET).count(new CollectableSearchExpression(SearchConditionUtils.newUidComparison(
						E.NUCLETDEPENDENCE.nuclet, ComparisonOperator.EQUAL, nucletObject.getPrimaryKey())));
				if (countDependencies == 0L) {
					available = true;
				}
			} else {
				if (source) {
					available = true;
				}
			}
			
			if (available) {
				result.add(new TransferNuclet(nucletObject.getPrimaryKey(), nucletObject.getFieldValue(E.NUCLET.name), 
						nucletObject.getFieldValue(E.NUCLET.nucletVersion), nuclon, source));
			}
		}

		return CollectionUtils.sorted(result, new Comparator<TransferNuclet>() {
			@Override
			public int compare(TransferNuclet o1, TransferNuclet o2) {
				return LangUtils.compare(o1.getLabel(), o2.getLabel());
			}});
	}
	
	@Override
	@RolesAllowed("Login")
	public byte[] createTransferFile(TransferEO nuclet, final NucletContentMap nucletContentMap) {
		if (!SecurityCache.getInstance().isSuperUser(getCurrentUserName())) {
			throw new NuclosFatalException("superuser only");
		}
		
		final Map<TransferOption, Serializable> exportOptions = new HashMap<TransferOption, Serializable>();
		List<INucletContent> 	contentTypes = TransferUtils.getNucletContentInstances(exportOptions, TransferUtils.Process.CREATE);
		List<INucletContent> 	rootTypes = TransferUtils.getRootContentTypes(contentTypes);
		ByteArrayOutputStream 	bout = new ByteArrayOutputStream(16348);
		ZipOutput 				zout = new ZipOutput(bout);
		UID						nucletUID = nuclet.getUID();
		
		AbstractNucletContent.fillCaches(contentTypes, LOG);
		
		for (TransferEO nucletTEO : nucletContentMap.getValues(E.NUCLET)) {
			String nucletPackage = nucletTEO.eo.getFieldValue(E.NUCLET.packagefield);
			
			for (INucletContent root : rootTypes) {
				INcObjects ncObjects = new INcObjects() {
					@Override
					public List<EntityObjectVO<UID>> getNcObjects(INucletContent nc, EntityObjectVO<UID> parent) {
						if (parent == null) {
							return TransferEO.transformToEntityObjectVO(nucletContentMap.getValues(nc.getEntity()));
						} else {
							List<EntityObjectVO<UID>> result = new ArrayList<EntityObjectVO<UID>>();
							FieldMeta<UID> fieldToParent = nc.getFieldToParent();
							for (TransferEO teo : nucletContentMap.getValues(nc.getEntity())) {
								if (LangUtils.equal(parent.getPrimaryKey(), teo.eo.getFieldUid(fieldToParent))) {
									result.add(teo.eo);
								}
							}
							return result;
						}
					}
				};
				writeToZip(zout, root, nucletPackage+"/", null, ncObjects, contentTypes, exportOptions);
			}
		}
		
		zout.addEntry(ROOT_ENTRY_NAME, toXML(buildMetaDataRoot(nucletUID, exportOptions)));

		zout.close();
		byte[] bytes = bout.toByteArray();
		AbstractNucletContent.clearCaches(LOG);
		return bytes;
	}

	/**
	 * creates a file for configuration transfer.
	 *
	 * @return the file content as byte array
	 * @throws NuclosBusinessException
	 */
	@Override
	@RolesAllowed("Login")
	public byte[] createTransferFile(UID nucletUID, final Map<TransferOption, Serializable> exportOptions) throws NuclosBusinessException {
		if (!SecurityCache.getInstance().isSuperUser(getCurrentUserName())) {
			throw new NuclosFatalException("superuser only");
		}
		if (nucletUID == null)
			throw new IllegalArgumentException("nucletUID must not be null");
		
		cleanupDeadContent();

		LOG.info("CREATE Transfer (nucletUID={})", nucletUID);
		LockedTabProgressNotifier.notify("read nuclet contents", 0);

		LOG.info("get nuclet content instances");
		List<INucletContent> 	contentTypes = TransferUtils.getNucletContentInstances(exportOptions, TransferUtils.Process.CREATE);
		List<INucletContent> 	rootTypes = TransferUtils.getRootContentTypes(contentTypes);
		Set<UID>				existingNucletUIDs = getExistingNucletUIDs(nucletUID).keySet();
		ByteArrayOutputStream 	bout = new ByteArrayOutputStream(16348);
		ZipOutput 				zout = new ZipOutput(bout);
		
		AbstractNucletContent.fillCaches(contentTypes, LOG);

		final String writeContent = "write nuclet [%s] contents to file";
		double progressPerStep = 80/Math.max(1, existingNucletUIDs.size())/Math.max(1, rootTypes.size());
		double progressCurrent = 10;
		
		for (final UID existingNucletUID : existingNucletUIDs) {
			String nucletPackage = getPackage(existingNucletUID);
			if (StringUtils.looksEmpty(nucletPackage)) {
				nucletPackage = existingNucletUID.getString();
			}
			LockedTabProgressNotifier.notify(String.format(writeContent, nucletPackage), new Double(progressCurrent).intValue());
			INcObjects ncObjects = new INcObjects() {
				@Override
				public List<EntityObjectVO<UID>> getNcObjects(INucletContent nc, EntityObjectVO<UID> parent) {
					List<EntityObjectVO<UID>> ncObjects = parent == null?
							nc.getNcObjects(Collections.singleton(existingNucletUID)):
							nc.getNcObjectsByParent(parent);
					return ncObjects;
				}
			};
			for (INucletContent root : rootTypes) {
				if (!root.isIgnoreReferenceToNuclet() || E.NUCLET == root.getEntity()) {
					writeToZip(zout, root, nucletPackage+"/", null, ncObjects, contentTypes, exportOptions);
					progressCurrent += progressPerStep;
				}
			}
		}

		LockedTabProgressNotifier.notify("save file", 90);

		LOG.info("add root to zip");
		zout.addEntry(ROOT_ENTRY_NAME, toXML(buildMetaDataRoot(nucletUID, exportOptions)));

		zout.close();
		byte[] bytes = bout.toByteArray();
		LockedTabProgressNotifier.notify("finish", 100);
		AbstractNucletContent.clearCaches(LOG);
		return bytes;
	}
	
	private interface INcObjects {
		public List<EntityObjectVO<UID>> getNcObjects(INucletContent nc, EntityObjectVO<UID> parent);
	}
	
	private void writeToZip (
			final ZipOutput zout, 
			final INucletContent nc, 
			final String parentPath, 
			final EntityObjectVO<UID> parent,
			final INcObjects iNcObjects, 
			final List<INucletContent> contentTypes,
			final Map<TransferOption, Serializable> exportOptions) {
		
		List<INucletContent> children = TransferUtils.getChildrenContentTypes(contentTypes, nc);
		String entity = nc.getEntity().getEntityName().toString().toLowerCase();
		String entityPath = parentPath + entity + "/";
		
		List<EntityObjectVO<UID>> ncObjects = iNcObjects.getNcObjects(nc, parent);
				
		Collections.sort(ncObjects, new Comparator<EntityObjectVO<UID>>() {
			@Override
			public int compare(EntityObjectVO<UID> o1, EntityObjectVO<UID> o2) {
				return LangUtils.compare(o1.getPrimaryKey(), o2.getPrimaryKey());
			}
		});
				
		Set<String> usedNames = new HashSet<String>();
		for (EntityObjectVO<UID> ncObject : ncObjects) {
			
			TransferEO teo = new TransferEO(ncObject);
			
			// name for file / path
			String name = null;
			if (nc.hasNameIdentifier(ncObject)) {
				name = Normalizer.normalize(nc.getIdentifier(ncObject, null), Normalizer.Form.NFD).replaceAll("[^0-9a-zA-Z\\s_-]", "");
				if (name.length() > 60) {
					name = name.substring(0, 60);
				}
				name = name.trim();
				if (usedNames.contains(name)) {
					name = null; // use uid;
				} else {
					usedNames.add(name);
				}
			}
			if (StringUtils.looksEmpty(name)) {
				name = ncObject.getPrimaryKey().getString();
			}
			assert name != null;
			
			// path
			String objectPath = entityPath;
			if (!children.isEmpty()) {
				objectPath = objectPath + name + "/";
			}
			
			// externalize clobs
			Set<FieldMeta.Valueable<String>> clobFields = TransferUtils.getClobFields(nc.getEntity());
			for (FieldMeta.Valueable<String> clobField : clobFields) {
				String value = ncObject.getFieldValue(clobField);
				if (value != null) {
					// write to zip
					String filepath = objectPath + name + "." + clobField.getFieldName().toLowerCase();
					zout.addEntry(filepath, value);
					teo.externalizedFiles.put(clobField, filepath);
				}
			}
			
			// externalize json objects
			Set<FieldMeta.Valueable<JsonObject>> jsonFields = TransferUtils.getJsonFields(nc.getEntity());
			for (FieldMeta.Valueable<JsonObject> jsonField : jsonFields) {
				JsonObject value = ncObject.getFieldValue(jsonField);
				if (value != null) {
					// write to zip
					String prettyValue = JsonUtils.prettyPrint(value);
					String filepath = objectPath + name + "." + jsonField.getFieldName().toLowerCase();
					zout.addEntry(filepath, prettyValue);
					teo.externalizedFiles.put(jsonField, filepath);
				}
			}
			
			// externalize other
			if (nc instanceof IExternalizeBytes) {
				Map<FieldMeta<?>, Pair<String, byte[]>> externalizedBytes = ((IExternalizeBytes) nc).externalize(ncObject);
				if (externalizedBytes != null) {
					for (FieldMeta<?> externalizedField : externalizedBytes.keySet()) {
						Pair<String, byte[]> postNameAndBytes = externalizedBytes.get(externalizedField);
						String filepath = objectPath + name + "." + externalizedField.getFieldName() + "." + postNameAndBytes.x;
						zout.addEntry(filepath, postNameAndBytes.y);
						teo.externalizedFiles.put(externalizedField, filepath);
					}
				}
			}
			
			// write to zip
			try {
				zout.addEntry(objectPath + name + "." + entity + ".eoml", toXML(teo));
				for (INucletContent child : children) {
					writeToZip(zout, child, objectPath, ncObject, iNcObjects, contentTypes, exportOptions);
				}
			} catch (IgnoreMarshalException ime) {
				// ignore ;)
			}
		}
	}
	
	private String getPackage(UID nucletUID) {
		return nucletDalProvider.getEntityObjectProcessor(E.NUCLET).getByPrimaryKey(nucletUID).getFieldValue(E.NUCLET.packagefield);
	}
	
	@Override
	@RolesAllowed("Login")
	public Map<TransferOption, Serializable> getOptions(byte[] bytes) throws NuclosBusinessException {
		MetaDataRoot root = readRoot(bytes, true);
		String error = matchMetaDataRoot(root);
		if (error != null)
			throw new NuclosBusinessException(error);
		return root.exportOptions;
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor= {Exception.class})
	private void prepareTransferTransaction1(byte[] bytes, 
			final Mutable<Boolean> checkOkay,
			final Mutable<Map<UID, String>> existingNuclets,
			final NucletContentMap importContentMap,
			final Mutable<Transfer> t,
			final Mutable<List<INucletContent>> contentTypes,
			final Mutable<MetaDataRoot> root
			) throws NuclosBusinessException {
		if (!SecurityCache.getInstance().isSuperUser(getCurrentUserName())) {
			throw new NuclosFatalException("superuser only");
		}
		
		if (DbObjectHelper.countRunningThreads() > 0) {
			throw new NuclosBusinessException("There are still running DbObject creation threads (" + DbObjectHelper.countRunningThreads() + "). Please try again later.");
		}
		
		cleanupDeadContent();

		root.setValue(readRoot(bytes, true));
		boolean isNuclon = root.getValue().exportOptions.containsKey(TransferOption.IS_NUCLON); 
		
		LOG.info("PREPARE Transfer (isNuclon={})", isNuclon);
		LOG.info("get nuclet content instances");
		contentTypes.setValue(TransferUtils.getNucletContentInstances(root.getValue().exportOptions, TransferUtils.Process.PREPARE));
		
		Collection<EntityObjectVO<UID>> parameter = new ArrayList<EntityObjectVO<UID>>();

		LockedTabProgressNotifier.notify("load file", 0);
		readFromZip(bytes, parameter, false, importContentMap, contentTypes.getValue());
		
		Map<UID, String> nuclets = new HashMap<>();
		for (TransferEO teo : importContentMap.getValues(E.NUCLET)) {
			EntityObjectVO<UID> nucletEO = teo.eo;
			String nucletName = nucletEO.getFieldValue(E.NUCLET.name);
			UID nucletUID = nucletEO.getPrimaryKey();
			String localIdentifier = nucletEO.getFieldValue(E.NUCLET.localidentifier);
			LOG.info("file includes nuclet \"{}\" with UID \"{}\" {}",
			         nucletName,
			         nucletUID.getString(),
			         (LangUtils.equal(root.getValue().nucletUID, nucletUID.getString())?" [root]":""));
			nuclets.put(nucletUID, localIdentifier);
		}
		
		LockedTabProgressNotifier.notify("load all nuclets", 5);
		existingNuclets.setValue(isNuclon?new HashMap<UID, String>():getExistingNucletUIDs(UID.parseUID(root.getValue().nucletUID)));
		Map<UID, String> allExistingNuclets = isNuclon?new HashMap<UID, String>():getExistingNucletUIDsAll();
		LOG.info("existing nuclet: {}", existingNuclets);
		
		List<PreviewPart> previewParts = new ArrayList<>();
		t.setValue(new Transfer(isNuclon, bytes, parameter, root.getValue().exportOptions, previewParts));
		
		checkOkay.setValue(checkNucletVersions(importContentMap, contentTypes.getValue(), t.getValue()));
		
		if (checkOkay.getValue()) {
			LOG.info("update local identifiers");
			LockedTabProgressNotifier.notify("update local identifiers", 10);
			updateLocalIdentifiers(allExistingNuclets, importContentMap);
		}
	}

	/**
	 * @param bytes the content of a transfer file
	 * @return a <code>Transfer</code> object describing how the
	 * current configuration would change if the transfer is executed
	 * @throws NuclosBusinessException
	 */
	@Override
	@RolesAllowed("Login")
	public Transfer prepareTransfer(byte[] bytes) throws NuclosBusinessException
	{
		throwNucletImportOutsideMaintenanceIfNecessary();

		final Mutable<Boolean> checkOkay = new Mutable<Boolean>();
		final Mutable<Map<UID, String>> existingNuclets = new Mutable<Map<UID,String>>();
		final Mutable<Transfer> t = new Mutable<Transfer>();
		final Mutable<List<INucletContent>> contentTypes = new Mutable<List<INucletContent>>();
		final Mutable<MetaDataRoot> root = new Mutable<MetaDataRoot>();
		final NucletContentMap importContentMap = new NucletContentHashMap();
		
		prepareTransferTransaction1(bytes, checkOkay, existingNuclets, importContentMap, t, contentTypes, root);

		boolean hasDataLanguages = this.nucletDalProvider.getEntityObjectProcessor(E.DATA_LANGUAGE).getAllIds().size() > 0;
		
		DbAccess dbAccess = dataBaseHelper.getDbAccess();
		
		if (checkOkay.getValue()) {
			LOG.info("get all constraints and drop");
			AutoDbSetup autoDbSetup = new AutoDbSetup(dbAccess, E.getThis(), ApplicationProperties.getInstance().getNuclosVersion());
			
			Object savepoint = null;
			try {
				autoDbSetup.removeSysConstraints(true, true, "Nuclet import preparation");
				savepoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();

				LOG.info("fill temporary caches");
				LockedTabProgressNotifier.notify("fill temporary caches", 30);
				AbstractNucletContent.fillCaches(contentTypes.getValue(), LOG);				
				LOG.info("delete content");
				LockedTabProgressNotifier.notify("prepare delete obsolete content", 50);
				deleteContent(existingNuclets.getValue().keySet(), importContentMap, contentTypes.getValue(), t.getValue(), true);				
				LOG.info("insert or update content");
				LockedTabProgressNotifier.notify("prepare insert or update content", 70);
				insertOrUpdateContent(existingNuclets.getValue().keySet(), importContentMap, contentTypes.getValue(), t.getValue(), true, hasDataLanguages);
				LOG.info("validation");
				LockedTabProgressNotifier.notify("validation", 80);
				AutoDbSetup.Schema validationSchema = autoDbSetup.getSchema(E.getThis(), autoDbSetup.nuclosStaticsVersion, null);
				SchemaHelper schemaHelper = new SchemaHelper(validationSchema, E.getSchemaVersion(), E.getThis(), dbAccess);
				schemaHelper.setJaxb2Marshaller(jaxb2Marshaller);
				schemaHelper.validateNoLogFile();
				
				try {
					LOG.info("preview changes");
					final String notifyPreviewString = "creating preview of db changes";
					LockedTabProgressNotifier.notify(notifyPreviewString, 90);
					t.getValue().getPreviewParts().addAll(previewChanges(dataBaseHelper.getDbAccess(), existingNuclets.getValue().keySet(), 
							contentTypes.getValue(), importContentMap, root.getValue().exportOptions, new TransferNotifierHelper(notifyPreviewString, 90, 100)));
				} catch (Exception ex) {
					if (t.getValue().result.hasCriticals()) t.getValue().result.sbCritical.append("<br />");
					t.getValue().result.sbCritical.append("Preview of Changes impossible: ");
					t.getValue().result.sbCritical.append(ex.toString());
					LOG.error("Nuclet import preview failed: ", ex);
				}
			} catch (Exception ex) {
				LOG.error("Unable to preview changes: ", ex);
				if (ex instanceof NuclosBusinessException) {
					throw (NuclosBusinessException)ex;					
				}
				throw new NuclosFatalException(ex);
			} finally {
				if (savepoint != null) {
					TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savepoint);
				}
				LOG.info("recreate constraints");
				try {
					autoDbSetup.createSysConstraints(true, true, "Nuclet import preparation");
				} catch (Exception e) {
					LOG.error("recreate constraints failed", e);
				}
				AbstractNucletContent.clearCaches(LOG);
			}
		}

		t.getValue().setImportContentMap(importContentMap);		
		t.getValue().setExistingNucletUIDs(new HashSet<UID>(existingNuclets.getValue().keySet()));
		
		final Set<JobKey> scheduled = getScheduledJobs();
		t.getValue().setScheduledJobs(jobKeySetToI(scheduled));
		
		LockedTabProgressNotifier.notify("finished", 100);
		return t.getValue();
	}
	
	private boolean checkNucletVersions(
			NucletContentMap importContentMap, 
			List<INucletContent> contentTypes, 
			Transfer t) {
		
		boolean result = true;
		
		for (TransferEO teo : importContentMap.getValues(E.NUCLET)) {
			EntityObjectVO<UID> importEO = teo.eo;
			String importName = importEO.getFieldValue(E.NUCLET.name);
			UID importUID = importEO.getPrimaryKey();
			EntityObjectVO<UID> existingEO = TransferUtils.getEntityObject(E.NUCLET, importUID);
			if (existingEO != null) {
				String existingName = importEO.getFieldValue(E.NUCLET.name);
				Integer importVersion = importEO.getFieldValue(E.NUCLET.nucletVersion);
				Integer existingVersion = existingEO.getFieldValue(E.NUCLET.nucletVersion);
				LOG.info("check nuclet version: [existing={}, version={}] --> [importing={}, version={}]",
						existingName, existingVersion, importName, importVersion);
				
				if (existingVersion != null && importVersion == null) {
					// do not update versioned with unversioned nuclet
					result = false;
					String s = String.format("Version conflict: Existing Nuclet \"%s\" v%s --> Importing Nuclet \"%s\" unversioned", existingName, existingVersion, importName);
					LOG.warn(s);
					t.result.addCritical(new StringBuffer(s));
				} else if (existingVersion != null && importVersion != null && existingVersion > importVersion) {
					// do not downgrade
					result = false;
					String s = String.format("Version conflict: Existing Nuclet \"%s\" v%s --> Importing Nuclet \"%s\" v%s", existingName, existingVersion, importName, importVersion);
					LOG.warn(s);
					t.result.addCritical(new StringBuffer(s));
				}
			}
		}
		
		return result;
	}

	private MetaDbProvider createMetaDbProvider() {
		Collection<MetaDbEntityWrapper> e = new ArrayList<MetaDbEntityWrapper>(); 
		Collection<MetaDbFieldWrapper> f = new ArrayList<MetaDbFieldWrapper>();
		List<NucletFieldMeta<?>> allFieldMetas = nucletDalProvider.getEntityFieldMetaDataProcessor().getAll();
		
		for (NucletEntityMeta entityMeta : nucletDalProvider.getEntityMetaDataProcessor().getAll()) {
			
			Collection<FieldMeta<?>> fieldMetas = new ArrayList<FieldMeta<?>>();
			for (NucletFieldMeta<?> fieldMeta : allFieldMetas) {
				if (LangUtils.equal(fieldMeta.getEntity(), entityMeta.getUID())) {
					fieldMetas.add(fieldMeta);
					f.add(new MetaDbFieldWrapper(fieldMeta));
				}
			}
			for (FieldMeta<?> fieldMeta : DalUtils.getStaticFields(entityMeta.getUID(), entityMeta.isStateModel(), entityMeta.isMandator(), entityMeta.isOwner(), entityMeta.getOwnerForeignEntityField())) {
				fieldMetas.add(fieldMeta);
				f.add(new MetaDbFieldWrapper(fieldMeta));
			}
			
			entityMeta.setFields(fieldMetas);
			e.add(new MetaDbEntityWrapper(entityMeta));
		}
		
		return new MetaDbProvider(e, f, E.getThis());
	}

	private List<PreviewPart> previewChanges(
		DbAccess dbAccess,
		Set<UID> existingNucletUIDs,
		List<INucletContent> contentTypes,
		NucletContentMap importContentMap,
		Map<TransferOption, Serializable> transferOptions,
		TransferNotifierHelper notifierHelper) throws SQLException {

		Map<String, PreviewPart> preview = new HashMap<String, PreviewPart>();
		
		Map<String, DbTable> currentSchema = (new MetaDbHelper(E.getSchemaHelperVersion(), dbAccess, metaProvider)).getSchema();
		
		MetaDbProvider transferredProvider = createMetaDbProvider();
		
		Map<String, DbTable> transferredSchema = (new MetaDbHelper(E.getSchemaHelperVersion(), dbAccess, transferredProvider)).getSchema();
		
		List<DbStructureChange> dbChangeStmts = SchemaUtils.modify(currentSchema.values(), transferredSchema.values(), false);
		StatementToStringVisitor toStringVisitor = new StatementToStringVisitor();
		for (DbStatement stmt : dbChangeStmts) {
			LOG.info("Statements to execute:");
			LOG.info("    {}", stmt.accept(toStringVisitor));
		}
		
		notifierHelper.setSteps(dbChangeStmts.size());
		for (DbStructureChange dbChangeStmt : dbChangeStmts) {
			notifierHelper.notifyNextStep();
			PreviewPart pp = new PreviewPart();

			DbTable table = null;
			DbTableArtifact artifact = null;
			switch (dbChangeStmt.getType()) {
			case CREATE:
				pp.setTypeOnlyOneTime(PreviewPart.NEW);
				if(dbChangeStmt.getArtifact2() instanceof DbUniqueConstraint) {
					DbUniqueConstraint dbu = (DbUniqueConstraint)dbChangeStmt.getArtifact2();
					checkIfNewUniqueConstraintIsAllowed(dbChangeStmt, pp);
				}
			case MODIFY:
				pp.setTypeOnlyOneTime(PreviewPart.CHANGE);
				if (dbChangeStmt.getArtifact2() instanceof DbTableArtifact)
					artifact = (DbTableArtifact) dbChangeStmt.getArtifact2();
				if (dbChangeStmt.getArtifact1() instanceof DbTable) {
					pp.setTable(dbChangeStmt.getArtifact1().getSimpleName());
					table = (DbTable) dbChangeStmt.getArtifact1();
				} else
				if (dbChangeStmt.getArtifact2() instanceof DbTable) {
					pp.setTable(dbChangeStmt.getArtifact2().getSimpleName());
					table = (DbTable) dbChangeStmt.getArtifact2();
				}	
				break;
			case DROP:
				pp.setTypeOnlyOneTime(PreviewPart.DELETE);
				pp.setTable(dbChangeStmt.getArtifact1().getSimpleName());
				if (dbChangeStmt.getArtifact1() instanceof DbTableArtifact)
					artifact = (DbTableArtifact) dbChangeStmt.getArtifact1();
				if (dbChangeStmt.getArtifact1() instanceof DbTable)
					table = (DbTable) dbChangeStmt.getArtifact1();
				break;
			default:
				continue;
			}

			if (table != null) {
				if (pp.getType() == PreviewPart.CHANGE || pp.getType() == PreviewPart.DELETE) {
					try {
						EntityMeta<?> entity = metaProvider.getByTablename(pp.getTable());
						if (entity != null) {
							pp.setDataRecords(nucletDalProvider.getEntityObjectProcessor(entity.getUID()).count(new CollectableSearchExpression()));
						}
					} catch (Exception ex) {
						pp.setDataRecords(-1);
					}
				}
				preview.put(pp.getTable().toUpperCase(), pp);
			} else if (artifact != null) {
				if (preview.get(artifact.getTable().getName().toUpperCase()) == null) {
					// table modified...
					pp.setType(PreviewPart.CHANGE);
					pp.setTable(artifact.getTable().getName());
					preview.put(artifact.getTable().getName().toUpperCase(), pp);
				} else {
					pp = preview.get(artifact.getTable().getName().toUpperCase());
				}
			} else {
				continue;
			}
			
			if (pp.getType() == PreviewPart.CHANGE || pp.getType() == PreviewPart.DELETE) {
				try {
					EntityMeta<?> entity = metaProvider.getByTablename(pp.getTable());
					if (entity != null) {
						pp.setEntity(entity.getEntityName());
					}
				} catch (Exception ex) {
					pp.setDataRecords(-1);
				}
			} else {
				pp.setEntity(getEntityWrapperForTable(pp.getTable(), transferredProvider).getEntityName());
			}

			final IBatch batch = dbAccess.getBatchFor(dbChangeStmt);
	    	// Sometimes (e.g. for creating a virtual entity), there is no SQL to execute. (tp)
			if (batch != null) {
				for (String s : dbAccess.getStatementsForLogging(batch)) {
					pp.addStatement(s);
				}
			}
		}

		List<PreviewPart> result = new ArrayList<PreviewPart>();
		result.addAll(preview.values());
		return CollectionUtils.sorted(result, new Comparator<PreviewPart>() {
			@Override
            public int compare(PreviewPart o1, PreviewPart o2) {
	            return o1.toString().compareToIgnoreCase(o2.toString());
            }});
	}

	@SuppressWarnings("unchecked")
	private void checkIfNewUniqueConstraintIsAllowed(DbStructureChange dbChangeStmt, PreviewPart pp) {
		DbUniqueConstraint artifactConstraint = (DbUniqueConstraint)dbChangeStmt.getArtifact2();
		EntityMeta<?> entityMeta;
		try {
			entityMeta = metaProvider.getByTablename(artifactConstraint.getTable().getName());
		} catch (Exception e) {
			// table does not exist. check okay...
			return;
		}
		try {
			DbQueryBuilder builder = dataBaseHelper.getDbAccess().getQueryBuilder();
			DbQuery<Long> query = builder.createQuery(Long.class);
			DbFrom<?> t = query.from(entityMeta);
			List<DbExpression<?>> lstDBSelection = new ArrayList<DbExpression<?>>();
			for(String sColumn : artifactConstraint.getColumnNames()) {
				DbColumnExpression<?> c = t.baseColumn(SimpleDbField.create(sColumn, DalUtils.getDbType(artifactConstraint.getClass())));
				lstDBSelection.add(c);
			}

			query.select(builder.countRows());
			query.groupBy(lstDBSelection);
			query.having(builder.greaterThan(builder.countRows(), builder.literal(1L)));
			query.limit(2L);

			List<Long> result = dataBaseHelper.getDbAccess().executeQuery(query);
			if(!result.isEmpty()) {
				pp.setWarning(pp.WARNING);
				pp.addStatement("Unique Constraint is not possible!");
			}
		}
		catch (Exception e) {
			LOG.warn("checkIfNewUniqueConstraintIsAllowed: ", e);
		}
	}

	private MetaDbEntityWrapper getEntityWrapperForTable(String table, MetaDbProvider prov) {
		for (MetaDbEntityWrapper eMeta : prov.getAllEntities()) {
			if (MetaDbHelper.getTableName(eMeta).equalsIgnoreCase(table)) {
				return eMeta;
			}
		}
		return null;
	}
	
	@Override
	public UID getNucletUIDFromMetaDataRoot(byte[] bytes, boolean bCheckTransferVersion) throws NuclosBusinessException {
		return new UID(readRoot(bytes, bCheckTransferVersion).nucletUID);
	}
	
	private MetaDataRoot readRoot(byte[] bytes, boolean bCheckTransferVersion) throws NuclosBusinessException {
		MetaDataRoot root = null;
		ZipInput zin = new ZipInput(new ByteArrayInputStream(bytes));
		try {
			ZipEntry ze;
			while ((ze = zin.getNextEntry()) != null) {
				String name = ze.getName();
				if (ze.getSize() > Integer.MAX_VALUE)
					throw new IllegalArgumentException();
				if (ROOT_ENTRY_NAME.equals(name)) {
					String xml = zin.readStringEntry();
					root = (MetaDataRoot) fromXML(xml);
					break;
				}
			}
		} catch (UnknownFieldException ex) {
			root = null;
		} finally {
			zin.close();
		}
		// sanity checks:
		if (root == null) {
			throw new NuclosBusinessException("Import of nuclet failed: File format is not supported.");			
		}
		
		if (bCheckTransferVersion && !TRANSFER_VERSION.equals(root.transferVersion)) {
			throw new NuclosBusinessException("Import of nuclet failed: Found file format version-" + root.transferVersion + ". This Nuclos supports only version-" + TRANSFER_VERSION);
		}
		
		if (ApplicationProperties.getInstance().getNuclosVersion().compareTo(root.version) < 0) {
			String nuclosVersion = root.version.getVersionNumber();
			// We added bugfix to SNAPSHOTS e.g. v4.3.6-SNAPSHOT.
			// No need to compare with version date any more...
			/*
			if (root.version.getVersionDate() != null) {
				nuclosVersion = nuclosVersion + " (" + new SimpleDateFormat("yyyy-MM-dd").format(root.version.getVersionDate()) + ")";
			}
			*/
			throw new NuclosBusinessException("Import of nuclet failed: Nuclos Version " + nuclosVersion + " or greater required");
		}
		
		return root;
	}

	private void readFromZip(byte[] bytes,
			Collection<EntityObjectVO<UID>> parameter,
			boolean protectParameter,
			NucletContentMap importContentMap,
			List<INucletContent> contentTypes) throws NuclosBusinessException {
		List<String> eomls = new ArrayList<String>();
		Map<String, byte[]> externalizedFiles = new HashMap<String, byte[]>();
		
		ZipInput zin = new ZipInput(new ByteArrayInputStream(bytes));
		try {
			ZipEntry ze;
			
			while ((ze = zin.getNextEntry()) != null) {
				String name = ze.getName();
				if (name.endsWith(".eoml")) {
					String eoml = zin.readStringEntry();
					eomls.add(eoml);
				} else {
					// externalized file content
					externalizedFiles.put(name, zin.readEntry());
				}
			}
		} finally {
			zin.close();
		}
		
		Collection<Runnable> afterDeserialize = new ArrayList<Runnable>();
		
		for (String eoml : eomls) {
			TransferEO teo = fromXML(eoml, externalizedFiles, contentTypes, importContentMap, afterDeserialize);
			if (teo != null) {
				InternalTimestamp sysdate = new InternalTimestamp(System.currentTimeMillis());
				teo.eo.setCreatedBy("Nuclet Import");
				teo.eo.setChangedBy("Nuclet Import");
				teo.eo.setCreatedAt(sysdate);
				teo.eo.setChangedAt(sysdate);
				importContentMap.add(teo);
			}
		}
		
		for (Runnable r : afterDeserialize) {
			r.run();
		}

		if (!protectParameter) {
			if (parameter != null){
				List<TransferEO> importParameter = importContentMap.getValues(E.NUCLETPARAMETER);
				List<TransferEO> importNuclets = importContentMap.getValues(E.NUCLET);
				if (importParameter != null) {
					for (TransferEO parameterTEO : importParameter) {
						TransferEO nucletEO = TransferUtils.getEntityObjectVO(importNuclets, parameterTEO.eo.getFieldUid(E.NUCLETPARAMETER.nuclet));
						if (nucletEO != null) {
							parameterTEO.eo.setFieldValue(E.NUCLETPARAMETER.nuclet.getUID(), nucletEO.eo.getFieldValue(E.NUCLET.packagefield));
						}
						parameter.add(parameterTEO.eo);
					}
				}
			}
		}
	}

	private String matchMetaDataRoot(MetaDataRoot root) {
		String error = null;
		ApplicationProperties p = ApplicationProperties.getInstance();
		if (p.getNuclosVersion().compareTo(root.version) < 0) {
			String nuclosVersion = root.version.getVersionNumber();
			// We added bugfix to SNAPSHOTS e.g. v4.3.6-SNAPSHOT.
			// No need to compare with version date any more...
			/*
			if (root.version.getVersionDate() != null) {
				nuclosVersion = nuclosVersion + " (" + new SimpleDateFormat("yyyy-MM-dd").format(root.version.getVersionDate()) + ")";
			}
			*/
			error = StringUtils.getParameterizedExceptionMessage("dbtransfer.problem.version.mismatch", nuclosVersion);
		}
		return error;
	}

	private static String toXML(Object o) {
		XStream xstream = new XStream(new DomDriver("UTF-8"));
		
		MetaDataRootSerializable.register(xstream);
		UIDSerializable.register(xstream);
		FileSerializable.register(xstream);
		
		return xstream.toXML(o);
	}
	
	private static String toXML(TransferEO teo) {
		XStream xstream = new XStream(new DomDriver("UTF-8"));
		
		NucletContentSerializableController.register(xstream);
		UIDSerializable.register(xstream);
		FileSerializable.register(xstream);
				
		return xstream.toXML(teo);
	}

	private static Object fromXML(String xml) {
		XStream xstream = new XStream(new DomDriver("UTF-8"));
		
		MetaDataRootSerializable.register(xstream);
		UIDSerializable.register(xstream);
		FileSerializable.register(xstream);
		
		return xstream.fromXML(xml);
	}
	
	private static TransferEO fromXML(String eoml, 
			Map<String, byte[]> externalizedFiles, 
			List<INucletContent> contentTypes, 
			NucletContentMap importContentMap,
			Collection<Runnable> afterDeserialize) {
		XStream xstream = new XStream(new DomDriver("UTF-8"));
		
		NucletContentSerializableController.register(xstream, externalizedFiles, contentTypes, importContentMap, afterDeserialize);
		UIDSerializable.register(xstream);
		DateSerializable.register(xstream);
		FileSerializable.register(xstream);
		
		return (TransferEO) xstream.fromXML(eoml);
	}

	private MetaDataRoot buildMetaDataRoot(UID nucletUID, Map<TransferOption, Serializable> exportOptions) {
		return new MetaDataRoot(
			TRANSFER_VERSION,
			nucletUID.getString(),
			ApplicationProperties.getInstance().getNuclosVersion(),
			getDatabaseType(),
			new Date(),
			exportOptions);
	}
	
	private Map<DbObject, Pair<DbPlainStatement, DbStatement>> getAllDbOjects() {
		final DbAccess dbAccess = dataBaseHelper.getDbAccess();
		DbObjectHelper dboHelper = new DbObjectHelper(dbAccess);
		return dboHelper.getAllDbObjects(null);
	}

	private Map<String, DbTable> getSchema() {
		final DbAccess dbAccess = dataBaseHelper.getDbAccess();
		MetaDbHelper currentHelper = new MetaDbHelper(E.getSchemaHelperVersion(), dbAccess, metaProvider);
		return currentHelper.getSchema();
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor= {Exception.class})
	private void runTransferTransaction1(final Transfer t,
			final Mutable<List<INucletContent>> contentTypes,
			final Mutable<Map<DbObject, Pair<DbPlainStatement, DbStatement>>> currentUserDefinedDbObjects,
			final Mutable<Map<String, DbTable>> currentSchema
			) throws NuclosBusinessException {
		if (!SecurityCache.getInstance().isSuperUser(getCurrentUserName())) {
			throw new NuclosFatalException("superuser only");
		}
		
		t.result = new Transfer.Result();
		unscheduleJobs(jobKeySetFromI(t.getScheduledJobs()));

		LOG.info("RUN Transfer (isNuclon={})", t.isNuclon());
		LOG.info("get nuclet content instances");
		contentTypes.setValue(TransferUtils.getNucletContentInstances(t.getTransferOptions(), TransferUtils.Process.RUN));

		LockedTabProgressNotifier.notify("load schema", 0);
		LOG.info("read current schema");

		//** save current db objects
		currentUserDefinedDbObjects.setValue(getAllDbOjects());
		
		//** save current configuration
		currentSchema.setValue(getSchema());
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor= {Exception.class})
	private void runTransferTransaction2(final Transfer t,
			final Mutable<List<INucletContent>> contentTypes,
			final Mutable<Map<String, DbTable>> transferredSchema,
			final Mutable<Map<DbObject, Pair<DbPlainStatement, DbStatement>>> transferredUserDefinedDbObjects
			) throws NuclosBusinessException {
		
		NucletContentMap importContentMap = t.getImportContentMap();
		final Set<UID> existingNucletUids;
		if (t.getExistingNucletUIDs() != null) {
			existingNucletUids = t.getExistingNucletUIDs(); 
		} else {
			existingNucletUids = new HashSet<UID>();
		}
		
		final DbAccess dbAccess = dataBaseHelper.getDbAccess();
		AutoDbSetup autoDbSetup = new AutoDbSetup(dbAccess, E.getThis(), ApplicationProperties.getInstance().getNuclosVersion());
		
		boolean hasDataLanguages = this.nucletDalProvider.getEntityObjectProcessor(E.DATA_LANGUAGE).getAllIds().size() > 0;
		
		
		DbObjectHelper dboHelper = new DbObjectHelper(dbAccess);
		
		Object savepoint = null;
		try {
			autoDbSetup.removeSysConstraints(true, true, "Nuclet import");
			savepoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
			
			LOG.info("fill temporary caches");
			LockedTabProgressNotifier.notify("fill temporary caches", 30);
			AbstractNucletContent.fillCaches(contentTypes.getValue(), LOG);
			LOG.info("delete content");
			LockedTabProgressNotifier.notify("delete obsolete content", 35);
			deleteContent(existingNucletUids, importContentMap, contentTypes.getValue(), t, false);				
			LOG.info("insert or update content");
			LockedTabProgressNotifier.notify("delete obsolete content",45);
			insertOrUpdateContent(existingNucletUids, importContentMap, contentTypes.getValue(), t, false, hasDataLanguages);
			LOG.info("update parameter");
			LockedTabProgressNotifier.notify("update parameter", 55);
			updateParameter(existingNucletUids, t.getParameter(), importContentMap);
			LOG.info("validation");
			LockedTabProgressNotifier.notify("validation", 60);
			AutoDbSetup.Schema validationSchema = autoDbSetup.getSchema(E.getThis(), autoDbSetup.nuclosStaticsVersion, null);
			SchemaHelper schemaHelper = new SchemaHelper(validationSchema, E.getSchemaVersion(), E.getThis(), dbAccess);
			schemaHelper.setJaxb2Marshaller(jaxb2Marshaller);
			schemaHelper.validateNoLogFile();
			
			TransactionAspectSupport.currentTransactionStatus().releaseSavepoint(savepoint);
		} catch (Exception ex) {
			LOG.error("Unable to run transfer: ", ex);
			if (savepoint != null) {
				TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savepoint);
			}
			if (ex instanceof NuclosBusinessException) {
				throw (NuclosBusinessException)ex;				
			}
			throw new NuclosFatalException(ex);
		} finally {
			AbstractNucletContent.clearCaches(LOG);
		}
	
		LOG.info("update db schema");
		LockedTabProgressNotifier.notify("update db schema", 70);		
		
		MetaDbProvider transferredProvider = createMetaDbProvider();
		
		transferredSchema.setValue((new MetaDbHelper(E.getSchemaHelperVersion(), dbAccess, transferredProvider)).getSchema());

		LOG.info("get all db objects");
		transferredUserDefinedDbObjects.setValue(dboHelper.getAllDbObjects(null));
	}

	private void manageIntegrationPointChanges(final StringBuffer sbWarning) {
		final Query<NucletIntegrationPoint> q = QueryProvider.create(NucletIntegrationPoint.class);

		boolean withProblem = false;
		for (NucletIntegrationPoint ip : QueryProvider.execute(q)) {
			ip.setProblem(true);
			try {
				BusinessObjectProvider.update(ip);
			} catch (BusinessException e) {
				sbWarning.append("\nException during update of integration point \"" + ip.getName() + "\": ").append(e.getMessage());
			}
			ip = QueryProvider.getById(NucletIntegrationPoint.class, ip.getId());
			if (Boolean.TRUE.equals(ip.getProblem())) {
				withProblem = true;
			}
		}

		if (withProblem) {
			sbWarning.append("There are integration points with problems, fix them before using the system.");
		}
	}

	private void manageDataLanguageTables(List<EntityMeta> existingEntities, MetaDbProvider transferredProvider) {
		
		DataLanguageMetaDataProcessor dlProcessor = 
				this.nucletDalProvider.getDataLanguageMetaDataProcessor();
		
		for (final EntityMeta metaExisting : existingEntities) {
			if (transferredProvider.hasEntity(metaExisting.getUID())) {
				if (metaExisting.IsLocalized()) {
					if (transferredProvider.getEntity(metaExisting.getUID()).isLocalized()) {
						Map<UID, FieldMeta<?>> allEntityFieldsExisting = metaProvider.getAllEntityFieldsByEntity(metaExisting.getUID());
						Collection<MetaDbFieldWrapper> allEntityFieldsByTransfer = transferredProvider.getAllEntityFieldsByEntity(metaExisting.getUID());
						List<UID> existLocalizedFields = new ArrayList<UID>();
						List<UID> newLocalizedFields = new ArrayList<UID>();
						
						for (UID fieldUID : allEntityFieldsExisting.keySet()) {
							FieldMeta<?> fieldMeta = allEntityFieldsExisting.get(fieldUID);
							if (fieldMeta.isLocalized()) {
								existLocalizedFields.add(fieldUID);
							}
						}
						
						for (MetaDbFieldWrapper field : allEntityFieldsByTransfer) {
							if (field.isLocalized()) {
								newLocalizedFields.add(field.getUID());
							}
						}
						
						if (! (newLocalizedFields.containsAll(existLocalizedFields) && existLocalizedFields.containsAll(newLocalizedFields)) ){
							final NucletEntityMeta dataLangMeta =
									DataLanguageServerUtils.createEntityLanguageMeta(
											new EntityMetaVO(transferredProvider.getEntity(metaExisting.getUID()).getEntityMeta(), true), 
											SF.PK_ID.getMetaData(transferredProvider.getEntity(metaExisting.getUID()).getEntityMeta()));
							dataLangMeta.flagUpdate();
							dlProcessor.modify(dataLangMeta, allEntityFieldsExisting);	
							
							Thread t = new Thread(new Runnable() {						
								@Override
								public void run() {
									dataLanguageService.fillLocalizedDataIntoLanguageTable(false, new NucletEntityMeta(metaExisting, true), dataLangMeta);					
								}
							});
							t.start();
						}
						
					} else {
						// updated entity is not localized anymore
						NucletEntityMeta dataLangMeta =
								DataLanguageServerUtils.createEntityLanguageMeta(new EntityMetaVO(metaExisting, true), SF.PK_ID.getMetaData(metaExisting));
						dataLangMeta.flagRemove();
						dlProcessor.remove(dataLangMeta);
					}
				} else {
					if (transferredProvider.getEntity(metaExisting.getUID()).getEntityMeta().IsLocalized()) {
						final EntityMeta<?> transferedEntityMeta = transferredProvider.getEntity(metaExisting.getUID()).getEntityMeta();
						// updated entity is now localized			
						final NucletEntityMeta dataLangMeta =
								DataLanguageServerUtils.createEntityLanguageMeta(
										new EntityMetaVO(transferedEntityMeta, true), 
										SF.PK_ID.getMetaData(transferredProvider.getEntity(metaExisting.getUID()).getEntityMeta()));
						dataLangMeta.flagNew();
						dlProcessor.create(dataLangMeta);
						metaProvider.revalidate(false, false);
						
						Thread t = new Thread(new Runnable() {						
							@Override
							public void run() {
								dataLanguageService.fillLocalizedDataIntoLanguageTable(true, new NucletEntityMeta(transferedEntityMeta, true), dataLangMeta);
							}
						});
						t.start();
						
					}
				} 
			}
		}
		
		for (MetaDbEntityWrapper dbMeta : transferredProvider.getAllEntities()) {
			if (existingEntities != null && !existingEntities.contains(dbMeta.getEntityMeta()) && dbMeta.isLocalized()) {
				// new entity is localized			
				NucletEntityMeta dataLangMeta =
						DataLanguageServerUtils.createEntityLanguageMeta(
								new EntityMetaVO(transferredProvider.getEntity(dbMeta.getUID()).getEntityMeta(), true), 
								SF.PK_ID.getMetaData(transferredProvider.getEntity(dbMeta.getUID()).getEntityMeta()));
				dataLangMeta.flagNew();
				dlProcessor.create(dataLangMeta);
			}
		}
	}

	public void throwNucletImportOutsideMaintenanceIfNecessary() throws NuclosBusinessException {
		final boolean isProductionEnvironment = NuclosSystemParameters.is(NuclosSystemParameters.ENVIRONMENT_PRODUCTION);
		if (isProductionEnvironment) {
			if (!MaintenanceConstants.MAINTENANCE_MODE_ON.equals(maintenanceFacadeBean.getMaintenanceMode())) {
				throw new NuclosBusinessException("nuclet.import.outside.maintenance");
			}
		}
	}

	/**
	 * execute a transfer
	 *
	 * @param t
	 * @return a message object informing the client about success or failure
	 * @throws NuclosBusinessException (only for pre database changes)
	 */
	@Override
	@RolesAllowed("Login")
	public synchronized Transfer.Result runTransfer(final Transfer t) throws NuclosBusinessException {
		
		if (!SecurityCache.getInstance().isSuperUser(getCurrentUserName())) {
			throw new NuclosFatalException("superuser only");
		}

		throwNucletImportOutsideMaintenanceIfNecessary();
		
		if (DbObjectHelper.countRunningThreads() > 0) {
			throw new NuclosBusinessException("There are still running DbObject creation threads (" + DbObjectHelper.countRunningThreads() + "). Please try again later.");
		}
		
		final Mutable<List<INucletContent>> contentTypes = new Mutable<List<INucletContent>>();
		final Mutable<Map<DbObject, Pair<DbPlainStatement, DbStatement>>> currentUserDefinedDbObjects = new Mutable<Map<DbObject,Pair<DbPlainStatement,DbStatement>>>();
		final Mutable<Map<String, DbTable>> currentSchema = new Mutable<Map<String,DbTable>>();
		final Mutable<Map<String, DbTable>> transferredSchema = new Mutable<Map<String,DbTable>>();
		final Mutable<Map<DbObject, Pair<DbPlainStatement, DbStatement>>> transferredUserDefinedDbObjects = new Mutable<Map<DbObject,Pair<DbPlainStatement,DbStatement>>>();
		
		runTransferTransaction1(t, contentTypes, currentUserDefinedDbObjects, currentSchema);
		
		final DbAccess dbAccess = dataBaseHelper.getDbAccess();
		AutoDbSetup autoDbSetup = new AutoDbSetup(dbAccess, E.getThis(), ApplicationProperties.getInstance().getNuclosVersion());
		
		try {
			runTransferTransaction2(t, contentTypes, transferredSchema, transferredUserDefinedDbObjects);
			
		} catch (Exception ex) {
			if (ex instanceof NuclosBusinessException) {
				throw (NuclosBusinessException)ex;				
			}
			throw new NuclosFatalException(ex);
		} finally {
			LOG.info("recreate constraints");
			try {
				autoDbSetup.createSysConstraints(true, true, "Nuclet import");
			} catch (Exception e) {
				LOG.error("recreate constraints failed", e);
			}
		}
		
		updateSchema(currentUserDefinedDbObjects.getValue(), transferredUserDefinedDbObjects.getValue(), 
				currentSchema.getValue(), transferredSchema.getValue(), t.result.script, t.result.sbWarning);

		List<TransferEO> transferredEntities = t.getImportContentMap().getValues(E.ENTITY);// remember if transfered entity has statemodel or not.
		Set<UID> transferredEntityUIDs = new HashSet<UID>();
		for (TransferEO transferEO : transferredEntities) {
			transferredEntityUIDs.add(transferEO.getUID());
		}
			
		List<EntityMeta> locEntites = getEntityMeta();
		
		revalidateCacheAndStateModels(transferredEntityUIDs, true, t.result.sbWarning, t.result.sbCritical);

		manageIntegrationPointChanges(t.result.sbWarning);
		
		manageDataLanguageTables(locEntites, createMetaDbProvider());
		
		final Set<IJobKey> iScheduled = t.getScheduledJobs();
		scheduleJobs(jobKeySetFromI(iScheduled), t.result);

		LockedTabProgressNotifier.notify("wait for recompile", 100);
		return t.result;
	}
	
	private List<EntityMeta> getEntityMeta() {
		List<EntityMeta> retVal = new ArrayList<EntityMeta>();
		
		for (EntityMeta meta : metaProvider.getAllEntities()) {
			retVal.add(meta);
		}
		return retVal;
	}

	private void updateSchema(Map<DbObject, Pair<DbPlainStatement, DbStatement>> oldDbObjects, Map<DbObject, Pair<DbPlainStatement, DbStatement>> newDbObjects,
			Map<String, DbTable> oldSchema, Map<String, DbTable> newSchema, List<String> script, StringBuffer sbWarning) throws NuclosBusinessException {
		LOG.info("update schema");
		final DbAccess dbAccess = dataBaseHelper.getDbAccess();
		DbObjectHelper.updateDbObjects(dbAccess, oldDbObjects, newDbObjects, DbStructureChange.Type.DROP, true, script, sbWarning);
		updateDB(dbAccess, true, oldSchema.values(), newSchema.values(), true, script, sbWarning);
		DbObjectHelper.updateDbObjects(dbAccess, newDbObjects, oldDbObjects, DbStructureChange.Type.CREATE, true, script, sbWarning);

	}
	
	private void revalidateCacheAndStateModels(Set<UID> entities, boolean jmsNotify, StringBuffer sbWarning, StringBuffer sbCritical) {
		Map<UID, Boolean> mpEntityMetaStateModel = new HashMap<UID, Boolean>(); 
		for (UID entityUID : entities) {
			try {
				mpEntityMetaStateModel.put(entityUID, metaProvider.getEntity(entityUID).isStateModel());
			} catch (Exception e) {
				// ignore. entity is unknown.
			}
		}
		
		/** create StatemodelObjects
		 */
		metaProvider.revalidate(false, false);
			
		StateFacadeLocal stateFacadeLocal = SpringApplicationContextHolder.getBean(StateFacadeLocal.class);
		GenericObjectFacadeLocal genericObjectFacadeLocal = SpringApplicationContextHolder.getBean(GenericObjectFacadeLocal.class);
		
		try {
			stateFacadeLocal.createStatemodelObjects();
		} catch(CommonBusinessException | InterruptedException e) {
			LOG.info("runTransfer: {}", e);
			sbWarning.append("\nError creating StatemodelObjects: ").append(e.getMessage());
		}

		LOG.debug("reload caches");
		if (jmsNotify) {
			LockedTabProgressNotifier.notify("reload caches", 85);
		}
		this.revalidateCaches();

		if (jmsNotify) {
			LockedTabProgressNotifier.notify("update generic objects", 95);		
		}
		// @see NUCLOS-581 / NUCLOS-1271. if statemodel is removed or added. it is important to do this after reloading the caches.
		
		Collection<UID> allEntities = metaProvider.getAllEntityUids();
		for (UID entityUID : allEntities) {
			try {
				// if isStatemodel has changed...
				if (mpEntityMetaStateModel.containsKey(entityUID)
						&& !mpEntityMetaStateModel.get(entityUID).equals(metaProvider.getEntity(entityUID).isStateModel())) {
					genericObjectFacadeLocal.updateGenericObjectEntries(entityUID);	
					stateFacadeLocal.updateInitialStatesByEntity(entityUID);
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				sbCritical.append("\nError update GenericObject Entries: " + e.getMessage());					
			}
		}
	}
	
	private static Set<JobKey> jobKeySetFromI(Set<IJobKey> set) {
		final Set<JobKey> result = new HashSet<JobKey>(set.size());
		for (IJobKey ijk: set) {
			result.add(JobKey.jobKey(ijk.getName(), ijk.getGroup()));
		}
		return result;
	}

	private static Set<IJobKey> jobKeySetToI(Set<JobKey> set) {
		final Set<IJobKey> result = new HashSet<IJobKey>(set.size());
		for (JobKey jk: set) {
			result.add(new JobKeyImpl(new UID(jk.getName()), jk.getGroup()));
		}
		return result;
	}

	@Override
	@RolesAllowed("UseManagementConsole")
	public void revalidateCaches() {
		ResourceCache.getInstance().invalidateCache(true, false);
		ServerParameterProvider.getInstance().invalidateCache(true, true);
		SpringApplicationContextHolder.getBean(LocaleFacadeLocal.class).flushInternalCaches(true);
		metaProvider.revalidate(false, true);
		DatasourceCache.getInstance().invalidate(false);
		SchemaCache.getInstance().invalidateCache(true, true);
		DatasourceCache.getInstance().invalidateCache(true, true); // we need do invalidate DatasourceCache twice.
		SecurityCache.getInstance().invalidate(false);
		
		
		LocalCachesUtil.getInstance().updateLocalCacheRevalidation(JMSConstants.TOPICNAME_MASTERDATACACHE);
		NuclosJMSUtils.sendOnceAfterCommitDelayed(null, JMSConstants.TOPICNAME_MASTERDATACACHE);
		
		AttributeCache.getInstance().invalidateCache(true, true);
		Modules.getInstance().invalidateCache(true, true);
		GenericObjectMetaDataCache.getInstance().revalidate();

		LOG.debug("JMS send: notify clients that custom components changed: {}", this);
		LocalCachesUtil.getInstance().updateLocalCacheRevalidation(JMSConstants.TOPICNAME_CUSTOMCOMPONENTCACHE);
		NuclosJMSUtils.sendOnceAfterCommitDelayed(null, JMSConstants.TOPICNAME_CUSTOMCOMPONENTCACHE);
		nucletDalProvider.getWorkspaceProcessor().invalidateWorkspaceCaches();
		SpringApplicationContextHolder.getBean(StateFacadeLocal.class).invalidateCache();
		SpringApplicationContextHolder.getBean(LayoutFacadeBean.class).evictCaches();
		SpringApplicationContextHolder.getBean(NuclosParameterProvider.class).evictNucletParameterCaches();
		SpringApplicationContextHolder.getBean(PreferenceProcessor.class).evictPreferences();
		SpringApplicationContextHolder.getBean(LayoutFacadeLocal.class).evictCaches();
		SpringApplicationContextHolder.getBean(EntityObjectFacadeLocal.class).evictGroupNamesCache();
		SpringApplicationContextHolder.getBean(PrintServiceRepository.class).evictCaches();
		SpringApplicationContextHolder.getBean(MasterDataFacadeLocal.class).evictAllProcessesFromCache();
		SpringApplicationContextHolder.getBean(ReportFacadeBean.class).evictAllReportUsagesAndFormMasterData();
		SpringApplicationContextHolder.getBean(EventSupportFacadeLocal.class).invalidateCaches();
		LOG.info("revalidateCaches: Many caches were invalidated/revalidated.");
	}

	private void logDalCallResult(List<DalCallResult> dcrs, StringBuffer sbErrorMessage) {
		for (DalCallResult dcr : dcrs)
			logDalCallResult(dcr, sbErrorMessage);
	}

	private void logDalCallResult(DalCallResult dcr, StringBuffer sbErrorMessage) {
		if (dcr.hasException()) {
			for (Exception dbe : dcr.getExceptions()) {
				final List<String> statements;
				if (dbe instanceof DbException) {
					statements = ((DbException) dbe).getStatements();
				}
				else {
					statements = Collections.emptyList();
				}
				logDMLError(sbErrorMessage, dbe.getMessage(), statements);
			}
		}
	}

	/**
	 *
	 * @param dbAccess
	 * @param currentSchema
	 * @param transferredSchema
	 * @param script
	 * @param bExecuteDDL
	 * @param sbResultMessage
	 */
	private void updateDB(DbAccess dbAccess, boolean persistImmediately, Collection<DbTable> currentSchema, Collection<DbTable> transferredSchema,
			boolean bExecuteDDL, List<String> script, StringBuffer sbResultMessage) {
		PersistentDbAccess pdbAccess = null;
		if (persistImmediately) {
			pdbAccess = new PersistentDbAccess(dbAccess);
		}
		for (DbStructureChange dbChangeStmt : SchemaUtils.modify(currentSchema, transferredSchema, false)) {
			if (dbChangeStmt.getArtifact1() instanceof DbSimpleView || dbChangeStmt.getArtifact2() instanceof DbSimpleView) {
				continue;
			}
			if (dbChangeStmt.getArtifact1() instanceof DbTable) {
				((DbTable)dbChangeStmt.getArtifact1()).getTableArtifacts().removeAll(((DbTable)dbChangeStmt.getArtifact1()).getTableArtifacts(DbSimpleView.class));
			}
			if (dbChangeStmt.getArtifact2() instanceof DbTable) {
				((DbTable)dbChangeStmt.getArtifact2()).getTableArtifacts().removeAll(((DbTable)dbChangeStmt.getArtifact2()).getTableArtifacts(DbSimpleView.class));
			}
			List<String> statements = null;
			try {
				final IBatch batch = dbAccess.getBatchFor(dbChangeStmt);
				// logScript(script, batch);
				statements = dbAccess.getStatementsForLogging(batch);
				script.addAll(statements);
				if (bExecuteDDL) {
					if (persistImmediately) {
						pdbAccess.execute(dbChangeStmt);
					} else {
						dbAccess.execute(dbChangeStmt);
					}
				}
			} catch (Exception e) {
				logDDLError(sbResultMessage, e.getMessage(), statements);
			}
		}
	}
	
	

	public static void logDMLError(StringBuffer sbErrorMessage, String error, List<?> statements) {
		logError(sbErrorMessage, error, statements, "DML");
	}

	public static void logDDLError(StringBuffer sbErrorMessage, String error, List<?> statements) {
		logError(sbErrorMessage, error, statements, "DDL");
	}

	public static void logError(StringBuffer sbErrorMessage, String error, List<?> statements, String errorType) {
		sbErrorMessage.append("<br />-------------------- "+ errorType + " Error --------------------");
		sbErrorMessage.append("<br />" + error);
		if (statements == null)
			return;
		sbErrorMessage.append("<br />"+ errorType + " Statement(s) --------------------");
		for (Object sql : statements)
			sbErrorMessage.append("<br />" + sql);
	}

	private void insertOrUpdateContent(
		Set<UID> existingNucletUIDs,
		NucletContentMap importContentMap,
		List<INucletContent> contentTypes,
		Transfer t,
		boolean testMode,
		boolean hasDataLanguages) {

		if (importContentMap == null)
			return;
		
		for (INucletContent nc : contentTypes) {
			LOG.info("insert or update content for nuclos entity: {}", nc.getEntity());
			
			if (nc.isEnabled()) {
				final DalCallResult result = new DalCallResult();
				for (TransferEO teo : importContentMap.getValues(nc.getEntity())) {
					EntityObjectVO<UID> importEO = teo.eo;
					UID uid = importEO.getPrimaryKey();
					LOG.debug("import eo: {} [{}]",
					          nc.getIdentifier(importEO, importContentMap),
					          uid.getString());
					if (TransferUtils.validate(nc, teo, ValidationType.INSERT, importContentMap, existingNucletUIDs, t.getTransferOptions(), t.result, hasDataLanguages)) {
						try {
							importEO.flagNew();
							nc.insertOrUpdateNcObject(result, importEO, t.isNuclon(), testMode);
							if (testMode && nc.getParentEntity()==null && nc.hasNameIdentifier(importEO)) {
								t.appendWarning(String.format("Insert %s %s", nc.getEntity(), nc.getIdentifier(importEO, importContentMap)));
							}
						}
						catch (DbException e) {
							result.addRuntimeException(e);
						}
					} else if (nc.canUpdate() &&
							TransferUtils.validate(nc, teo, ValidationType.UPDATE, importContentMap, existingNucletUIDs, t.getTransferOptions(), t.result, hasDataLanguages)) {
						try {
							importEO.flagUpdate();
							nc.insertOrUpdateNcObject(result, importEO, t.isNuclon(), testMode);
							if (testMode && nc.getParentEntity()==null && nc.hasNameIdentifier(importEO)) {
								int existingEOversion = LangUtils.defaultIfNull(teo.existingVersion, 0);
								if (existingEOversion != LangUtils.defaultIfNull(importEO.getVersion(), 0)) {
									t.appendWarning(String.format("Update %s %s", nc.getEntity(), nc.getIdentifier(importEO, importContentMap)));
								}
							}
						}
						catch (DbException e) {
							result.addRuntimeException(e);
						}
					}
				}
				logDalCallResult(result, t.result.sbWarning);
			}
		}
	}

	private void deleteContent(
			Set<UID> existingNucletUIDs,
			NucletContentMap importContentMap,
			List<INucletContent> contentTypes,
			Transfer t,
			boolean testMode) {
		deleteContent(existingNucletUIDs, importContentMap, contentTypes, t, testMode, true);
		deleteContent(existingNucletUIDs, importContentMap, contentTypes, t, testMode, false);
	}
	
	private void deleteContent(
		Set<UID> existingNucletUIDs,
		NucletContentMap importContentMap,
		List<INucletContent> contentTypes,
		Transfer t,
		boolean testMode,
		boolean cleanupMode) {

		if (t.isNuclon()) {
			LOG.info("is nuclon import. do not delete anything");
			return;
		}

		List<INucletContent> contentTypesReversedOrder = new ArrayList<INucletContent>(contentTypes);
		Collections.reverse(contentTypesReversedOrder);
		for (INucletContent nc : contentTypesReversedOrder) {
			LOG.info((cleanupMode ? "cleanup" : "delete") + " content for nuclos entity: {}", nc.getEntity());
			
			List<TransferEO> existingContent = TransferUtils.getTransferNcObjects(nc, existingNucletUIDs);
			if (nc.isEnabled() && nc.canDelete()) {
				Map<UID, TransferEO> mapIncoming = new HashMap<UID, TransferEO>();
				for (TransferEO teo : importContentMap.getValues(nc.getEntity())) {
					mapIncoming.put(teo.getUID(), teo);
				}
				final DalCallResult result = new DalCallResult();
				for (TransferEO existingTEO : existingContent) {
					EntityObjectVO<UID> existingEO = existingTEO.eo;
					UID existingUID = existingEO.getPrimaryKey();
					LOG.debug("existing eo: {} [{}]",
					          nc.getIdentifier(existingEO, importContentMap),
					          existingUID.getString());

//					TransferEO incomingTEO = TransferUtils.getEntityObjectVO(importContentMap.getValues(nc.getEntity()), existingUID);
					TransferEO incomingTEO = mapIncoming.get(existingUID);
					
					if (cleanupMode) {
						if (nc.getEntity().checkEntityUID(E.ENTITY.getUID())) {
							if (incomingTEO == null) {
								// removing an entity with statemodel
								// (see NUCLOS-4660)
								// set state and process to null...
								LOG.info("--> cleanup system attributes and sub entities");
								cleanupSystemAttributesAndSubEntities(existingTEO);
							}
						}
						
					} else {
						if (incomingTEO != null) {
							LOG.debug("imcoming found --> do NOT delete existing eo");
							incomingTEO.existingVersion = existingEO.getVersion();
						}
						
						if (incomingTEO == null && TransferUtils.validate(nc, existingTEO, ValidationType.DELETE, importContentMap, existingNucletUIDs, t.getTransferOptions(), t.result, false)) {
							LOG.debug("--> delete existing eo");
							try {
								nc.deleteNcObject(result, existingEO, testMode);
								if (testMode && nc.getParentEntity()==null && nc.hasNameIdentifier(existingEO)) {
									t.appendWarning(String.format("Remove %s %s", nc.getEntity(), nc.getIdentifier(existingEO, importContentMap)));
								}
							}
							catch (DbException e) {
								result.addRuntimeException(e);
							}
						} 
					}
				}
				logDalCallResult(result, t.result.sbWarning);
			}
		}
	}
	
	private void cleanupSystemAttributesAndSubEntities(TransferEO existingTEO) {
		final DbAccess dbAccess = dataBaseHelper.getDbAccess();
		final EntityMeta<?> eMeta = metaProvider.getEntity(existingTEO.getUID());
		
		if (!RigidUtils.looksEmpty(eMeta.getVirtualEntity()) ||
			!eMeta.isTableMaster() ||
			eMeta.isProxy() ||
			eMeta.isGeneric() ||
			eMeta.isDynamic() ||
			eMeta.isChart()) {
			return;
		}

		final DbQueryBuilder dbQuery = dbAccess.getQueryBuilder();
		DbDelete dbDelete = null;
		
		final DbQuery<Long> existingEOQuery = dbQuery.createQuery(Long.class);
		DbFrom<?> dbFrom = existingEOQuery.from(eMeta);
		existingEOQuery.select((DbSelection<Long>) dbFrom.basePk());
		
		if (eMeta.isStateModel()) {			
			DbMap updateMap = new DbMap();
			DbMap condition = new DbMap();
			updateMap.putNull(SF.PROCESS_UID);
			updateMap.putNull(SF.STATE_UID);
			DbUpdateStatement<?> updateStmt = new DbUpdateStatement(eMeta, updateMap, condition);
			dbAccess.execute(updateStmt);
			
			dbDelete = dbQuery.createDelete(E.STATEHISTORY);
			dbDelete.where(dbQuery.in(dbDelete.baseColumn(E.STATEHISTORY.genericObject), existingEOQuery));
			dbAccess.executeDelete(dbDelete);
			
			dbDelete = dbQuery.createDelete(E.GENERICOBJECTLOGBOOK);
			dbDelete.where(dbQuery.in(dbDelete.baseColumn(E.GENERICOBJECTLOGBOOK.genericObject), existingEOQuery));
			dbAccess.executeDelete(dbDelete);
			
			dbDelete = dbQuery.createDelete(E.GENERICOBJECTRELATION);
			dbDelete.where(dbQuery.in(dbDelete.baseColumn(E.GENERICOBJECTRELATION.destination), existingEOQuery));
			dbAccess.executeDelete(dbDelete);
			
			dbDelete = dbQuery.createDelete(E.GENERICOBJECTRELATION);
			dbDelete.where(dbQuery.in(dbDelete.baseColumn(E.GENERICOBJECTRELATION.source), existingEOQuery));
			dbAccess.executeDelete(dbDelete);
			
			dbDelete = dbQuery.createDelete(E.GENERALSEARCHCOMMENT);
			dbDelete.where(dbQuery.in(dbDelete.baseColumn(E.GENERALSEARCHCOMMENT.genericObject), existingEOQuery));
			dbAccess.executeDelete(dbDelete);
			
			// TODO remove attachments from data folder (and other attachment attributes) - Validation Job?
			dbDelete = dbQuery.createDelete(E.GENERALSEARCHDOCUMENT);
			dbDelete.where(dbQuery.in(dbDelete.baseColumn(E.GENERALSEARCHDOCUMENT.genericObject), existingEOQuery));
			dbAccess.executeDelete(dbDelete);
		}
		
		dbDelete = dbQuery.createDelete(E.HISTORY);
		dbDelete.where(dbQuery.equalValue(dbDelete.baseColumn(E.HISTORY.entity), eMeta.getUID()));
		dbAccess.executeDelete(dbDelete);
	}

	/*
	 * Noetig, weil einige Konfigurationsinhalte Rueckstaende hinterlassen!
	 *  -> Statusmodelleditor
	 */
	private void cleanupDeadContent() {
		LOG.info("cleanup dead content -> schema validation...");
		DbAccess dbAccess = dataBaseHelper.getDbAccess();
		AutoDbSetup autoDbSetup = new AutoDbSetup(dbAccess, E.getThis(), ApplicationProperties.getInstance().getNuclosVersion());
		AutoDbSetup.Schema validationSchema = autoDbSetup.getSchema(E.getThis(), autoDbSetup.nuclosStaticsVersion, null);
		SchemaHelper schemaHelper = new SchemaHelper(validationSchema, E.getSchemaVersion(), E.getThis(), new PersistentDbAccess(dbAccess));
		schemaHelper.setJaxb2Marshaller(jaxb2Marshaller);
		schemaHelper.validateNoLogFile();
	}

	@Override
	@RolesAllowed("Login")
	public String getDatabaseType(){
		return dataBaseHelper.getDbAccess().getDbType().toString();
	}


	/**
	 * 
	 * @param nucletUID
	 * @return UID - LocalIdentifier Map
	 */
	private Map<UID, String> getExistingNucletUIDs(UID nucletUID) {
		Map<UID, String> result = new HashMap<>();
		if (nucletUID != null) {
			for (UID dependenceNucletUID : CollectionUtils.transformIntoSet(nucletDalProvider.getEntityObjectProcessor(E.NUCLETDEPENDENCE).getBySearchExpression(
				new CollectableSearchExpression(SearchConditionUtils.newUidComparison(
					E.NUCLETDEPENDENCE.nuclet, ComparisonOperator.EQUAL, nucletUID))),
				new TransferUtils.NucletDependenceTransformer())) {
				result.putAll(getExistingNucletUIDs(dependenceNucletUID));
			}
			EntityObjectVO<UID> nuclet = nucletDalProvider.getEntityObjectProcessor(E.NUCLET).getByPrimaryKey(nucletUID);
			if (nuclet != null) {
				result.put(nucletUID, nuclet.getFieldValue(E.NUCLET.localidentifier));
			}
		}
		return result;
	}
	
	private Map<UID, String> getExistingNucletUIDsAll() {
		Map<UID, String> result = new HashMap<>();
		for (EntityObjectVO<UID> nuclet : nucletDalProvider.getEntityObjectProcessor(E.NUCLET).getAll()) {
			result.put(nuclet.getPrimaryKey(), nuclet.getFieldValue(E.NUCLET.localidentifier));
		}
		return result;
	}

	private void updateParameter(
			Set<UID> existingNucletUIDs,
			Collection<EntityObjectVO<UID>> incomingParameterVOs, 
			NucletContentMap importContentMap) throws NuclosBusinessException {
		
		final IEntityObjectProcessor<UID> parameterProc = nucletDalProvider.getEntityObjectProcessor(E.NUCLETPARAMETER);
		final Collection<EntityObjectVO<UID>> existingParameterVOs = TransferUtils.getEntityObjectVOs(E.NUCLETPARAMETER.nuclet, existingNucletUIDs);
		
		// aktualisiere oder fuege hinzu
		for (EntityObjectVO<UID> incomingParameterVO : incomingParameterVOs) {
			boolean exists = false;
			// entferne aus existing
			Iterator<EntityObjectVO<UID>> itExisting = existingParameterVOs.iterator();
			while (itExisting.hasNext()) {
				if (itExisting.next().getPrimaryKey().equals(incomingParameterVO.getPrimaryKey())) {
					itExisting.remove();
					exists = true;
					break;
				}
			}
			if (exists) {
				incomingParameterVO.flagUpdate();
			} else {
				incomingParameterVO.flagNew();
			}
			DalUtils.updateVersionInformation(incomingParameterVO, getCurrentUserName());
			
			if (parameterProc instanceof EntityObjectProcessor) {
				((EntityObjectProcessor<UID>)parameterProc).insertOrUpdateWithOrWithoutForce(incomingParameterVO, true /*force insert or update*/);
			} else {
				parameterProc.insertOrUpdate(incomingParameterVO);
			}
			
		}
		
		// loesche obsolete
		for (EntityObjectVO<UID> existingParameterVO : existingParameterVOs) {
			parameterProc.delete(new Delete<UID>(existingParameterVO.getPrimaryKey()));
		}
	}

	public void checkCircularReference(UID nucletUID) throws CommonValidationException {
		CollectableSearchCondition clctcondUP = SearchConditionUtils.newUidComparison(
			E.NUCLETDEPENDENCE.nucletDependence,
			ComparisonOperator.EQUAL,
			nucletUID);
		CollectableSearchCondition clctcondDOWN = SearchConditionUtils.newUidComparison(
			E.NUCLETDEPENDENCE.nuclet,
			ComparisonOperator.EQUAL,
			nucletUID);
		if (checkCircularReferenceUp(nucletUID, nucletDalProvider.getEntityObjectProcessor(E.NUCLETDEPENDENCE).getBySearchExpression(new CollectableSearchExpression(clctcondUP))) ||
			checkCircularReferenceDown(nucletUID, nucletDalProvider.getEntityObjectProcessor(E.NUCLETDEPENDENCE).getBySearchExpression(new CollectableSearchExpression(clctcondDOWN)))) {
			throw new CommonValidationException("nuclet.circular.reference.found");
		}

	}

	private boolean checkCircularReferenceUp(UID nucletUID, Collection<EntityObjectVO<UID>> nucletDependences) {
		for (EntityObjectVO<UID> nucletDependence : nucletDependences) {
			if (LangUtils.equal(nucletUID, nucletDependence.getFieldUid(E.NUCLETDEPENDENCE.nuclet))) {
				return true;
			}

			CollectableSearchCondition clctcond = SearchConditionUtils.newUidComparison(
				E.NUCLETDEPENDENCE.nucletDependence,
				ComparisonOperator.EQUAL,
				nucletDependence.getFieldUid(E.NUCLETDEPENDENCE.nuclet));
			return checkCircularReferenceUp(nucletUID, nucletDalProvider.getEntityObjectProcessor(E.NUCLETDEPENDENCE).getBySearchExpression(
					new CollectableSearchExpression(clctcond)));
		}

		return false;
	}

	private boolean checkCircularReferenceDown(UID nucletUID, Collection<EntityObjectVO<UID>> nucletDependences) {
		for (EntityObjectVO<UID> nucletDependence : nucletDependences) {
			if (LangUtils.equal(nucletUID, nucletDependence.getFieldUid(E.NUCLETDEPENDENCE.nucletDependence))) {
				return true;
			}

			CollectableSearchCondition clctcond = SearchConditionUtils.newUidComparison(
				E.NUCLETDEPENDENCE.nuclet,
				ComparisonOperator.EQUAL,
				nucletDependence.getFieldUid(E.NUCLETDEPENDENCE.nucletDependence));
			return checkCircularReferenceDown(nucletUID, nucletDalProvider.getEntityObjectProcessor(E.NUCLETDEPENDENCE).getBySearchExpression(
					new CollectableSearchExpression(clctcond)));
		}
		return false;
	}
	
	/**
	 * TODO (multinuclet): Names are not "multinucletable"!
	 */
	private Set<JobKey> getScheduledJobs() {
		Set<JobKey> result = new HashSet<JobKey>();
		SchedulerControlFacadeLocal scheduler = ServiceLocator.getInstance().getFacade(SchedulerControlFacadeLocal.class);
		for (JobKey job : scheduler._getJobKeys()) {
			if (scheduler.isScheduled(job)) {
				result.add(job);
			}
		}
		return result;
	}
	
	/**
	 * TODO (multinuclet): Unschedule jobs is NOT the same than halting the scheduler temporary. (tp)
	 */
	public void unscheduleJobs(Set<JobKey> jobs) {
		Scheduler scheduler = (Scheduler) SpringApplicationContextHolder.getBean("nuclosScheduler");
		try {
			scheduler.standby();
		} catch (Exception ex) {
			LOG.error("Unschedule jobs failed", ex);
			throw new NuclosFatalException("Unschedule jobs failed", ex);
		}
	}
	
	/**
	 * TODO (multi-nuclet): Schedule jobs is NOT the same than starting the scheduler. (tp)
	 */
	public void scheduleJobs(Set<JobKey> jobs, Transfer.Result result) {
		if (!maintenanceFacadeBean.isMaintenanceOff()) {
			LOG.info("Job scheduler not started because of maintenance mode");
			return;
		}

		Scheduler scheduler = (Scheduler) SpringApplicationContextHolder.getBean("nuclosScheduler");
		try {
			scheduler.start();
		} catch (Exception ex) {
			LOG.error("Schedule jobs failed", ex);
			result.sbCritical.append("Schedule jobs failed: " + ex.getMessage());
		}
	}
	
	@Override
	@RolesAllowed("Login")
	public void updateNucletContents(UID nuclet, Set<AbstractNucletContentEntryTreeNode> contentsToAdd, Set<AbstractNucletContentEntryTreeNode> contentsToRemove) throws NuclosBusinessException, CommonPermissionException {
		if (!SecurityCache.getInstance().isSuperUser(getCurrentUserName())) {
			throw new CommonPermissionException("superuser only");
		}
		final CacheInvalidator ci = new CacheInvalidator();
		if (contentsToAdd != null && !contentsToAdd.isEmpty()) {
			addNucletContents(nuclet, contentsToAdd, ci);
		}
		if (contentsToRemove != null && !contentsToRemove.isEmpty()) {
			removeNucletContents(contentsToRemove, ci);
		}
		ci.run();
	}
	
	@Override
	@RolesAllowed("Login")
	public void addNucletContents(UID nuclet, Set<AbstractNucletContentEntryTreeNode> contents) throws CommonPermissionException, NuclosBusinessException {
		if (!SecurityCache.getInstance().isSuperUser(getCurrentUserName())) {
			throw new CommonPermissionException("superuser only");
		}
		
		final CacheInvalidator ci = new CacheInvalidator();
		addNucletContents(nuclet, contents, ci);
		ci.run();
	}
	
	private void addNucletContents(UID nuclet, Set<AbstractNucletContentEntryTreeNode> contents, CacheInvalidator ci) throws CommonPermissionException {
		for (AbstractNucletContentEntryTreeNode node : contents) {
			addNucletContent(node.getEntity(), node.getId(), nuclet, ci);
		}
	}
	
	@Override
	@RolesAllowed("Login")
	public void removeNucletContents(Set<AbstractNucletContentEntryTreeNode> contents) throws CommonPermissionException, NuclosBusinessException {
		if (!SecurityCache.getInstance().isSuperUser(getCurrentUserName())) {
			throw new CommonPermissionException("superuser only");
		}
		
		final CacheInvalidator ci = new CacheInvalidator();
		removeNucletContents(contents, ci);
		ci.run();
	}
	
	private void removeNucletContents(Set<AbstractNucletContentEntryTreeNode> contents, CacheInvalidator ci) throws CommonPermissionException {
		for (AbstractNucletContentEntryTreeNode node : contents) {			
			removeNucletContent(node.getEntity(), node.getId(), ci);
		}
	}

	private class CacheInvalidator {
		
		private final DbObjectHelper dboHelper = new DbObjectHelper(dataBaseHelper.getDbAccess());
		private final Map<DbObject, Pair<DbPlainStatement, DbStatement>> dbObjectsBefore;
		
		private boolean dboUpdate = false;
		private List<String> script = new ArrayList<String>();
		private StringBuffer warnings = new StringBuffer();
		
		private boolean invalidateMetaDataCache = false;
		private boolean invalidateDatasourceCache = false;
		private boolean invalidateLayoutCache = false;
		private Set<UID> invalidateMasterDataCache = new HashSet<UID>();
		
		public CacheInvalidator() {
			dbObjectsBefore = dboHelper.getAllDbObjects(null);
		}
		
		public void objectChanged(EntityMeta<?> entity, EntityObjectVO<UID> eo) {
			invalidateMasterDataCache.add(entity.getUID());
			if (E.ENTITY.equals(entity) ||
				E.ENTITYFIELD.equals(entity) || 
				E.CHART.equals(entity) ||
				E.DYNAMICENTITY.equals(entity)) {
				invalidateMetaDataCache = true;
				invalidateDatasourceCache = true;
			}
			if (E.DATASOURCE.equals(entity) ||
				E.VALUELISTPROVIDER.equals(entity) ||
				E.RECORDGRANT.equals(entity) ||
				E.CALCATTRIBUTE.equals(entity)) {
				invalidateDatasourceCache = true;
			}
			if (E.DBSOURCE.equals(entity)) {
				dboUpdate = true;
			}
			if (E.LAYOUT.equals(entity)) {
				invalidateLayoutCache = true;
			}
		}
		
		public void run() throws NuclosBusinessException {
			if (dboUpdate) {
				Map<DbObject, Pair<DbPlainStatement, DbStatement>>  dbObjectsNew = dboHelper.getAllDbObjects(null);
				// drop DBOs
				DbObjectHelper.updateDbObjects(dataBaseHelper.getDbAccess(), dbObjectsBefore, dbObjectsNew, 
						DbStructureChange.Type.DROP, true, script, warnings);
				// recreate DBOs
				DbObjectHelper.updateDbObjects(dataBaseHelper.getDbAccess(), dbObjectsNew, dbObjectsBefore,  
						DbStructureChange.Type.CREATE, true, script, warnings);
				
				if (warnings.length() > 0) {
					messageService.sendMessage(new DbObjectMessage("Error during update of database object", 
						"Database Update", false, script, warnings));
				}
			}
			if (invalidateMetaDataCache) {
				metaProvider.revalidate(false, true);
			}
			if (invalidateDatasourceCache) {
				DatasourceCache.getInstance().invalidate(false);
				SchemaCache.getInstance().invalidate();
				DatasourceCache.getInstance().invalidate(); // we need do invalidate DatasourceCache twice.
			}
			if (invalidateLayoutCache) {
				SpringApplicationContextHolder.getBean(LayoutFacadeLocal.class).evictCaches();
			}
			LocalCachesUtil.getInstance().updateLocalCacheRevalidation(JMSConstants.TOPICNAME_MASTERDATACACHE);
			for (UID uid : invalidateMasterDataCache) {
				NuclosJMSUtils.sendOnceAfterCommitDelayed(uid, JMSConstants.TOPICNAME_MASTERDATACACHE);
			}
		}
	}
	
	private void addNucletContent(EntityMeta<UID> entity, UID pk, UID nuclet, CacheInvalidator ci) throws CommonPermissionException {
		final IEntityObjectProcessor<UID> processor = nucletDalProvider.getEntityObjectProcessor(entity);
		final EntityObjectVO<UID> eo = processor.getByPrimaryKey(pk); //reload the content, no version check here!
		final FieldMeta<?> fMeta = TransferUtils.getRefToNuclet(entity);
		final UID nucletRef = eo.getFieldUid(fMeta.getUID());
		
		if (nucletRef != null) {
			if (LangUtils.equal(nuclet, nucletRef)) {
				return;
			}
		}

		updateNucletAssignment(entity, eo, fMeta, nucletRef, nuclet, ci);		
	}
	
	private void removeNucletContent(EntityMeta<UID> entity, UID pk, CacheInvalidator ci) throws CommonPermissionException {
		final IEntityObjectProcessor<UID> processor = nucletDalProvider.getEntityObjectProcessor(entity);
		final EntityObjectVO<UID> eo = processor.getByPrimaryKey(pk); //reload the content, no version check here!
		final FieldMeta<?> fMeta = TransferUtils.getRefToNuclet(entity);
		final UID nucletRef = eo.getFieldUid(fMeta.getUID());

		if (nucletRef != null) {
			updateNucletAssignment(entity, eo, fMeta, nucletRef, null, ci);
		}
	}
	
	private void updateNucletAssignment(EntityMeta<UID> entity, EntityObjectVO<UID> eo, FieldMeta<?> nucletRefField, UID sourceNuclet, UID targetNuclet, CacheInvalidator ci) throws CommonPermissionException {
		if (RigidUtils.equal(sourceNuclet, targetNuclet)) {
			return;
		}

		maintenanceFacadeBean.throwRecompileOutsideMaintenanceIfNecessary();
		
		final String sourceLI, targetLI;
		
		if (sourceNuclet == null) {
			sourceLI = NucletConstants.DEFAULT_LOCALIDENTIFIER;
		} else {
			EntityObjectVO<UID> nuclet = metaProvider.getNuclet(sourceNuclet);
			sourceLI = nuclet.getFieldValue(E.NUCLET.localidentifier);
		}
		
		if (targetNuclet == null) {
			targetLI = NucletConstants.DEFAULT_LOCALIDENTIFIER;
		} else {
			EntityObjectVO<UID> nuclet = metaProvider.getNuclet(targetNuclet);
			targetLI = nuclet.getFieldValue(E.NUCLET.localidentifier);
		}
		
		if (E.ENTITY.equals(entity)) {
			String virtual = eo.getFieldValue(E.ENTITY.virtualentity);

			String oldTable = eo.getFieldValue(E.ENTITY.dbtable);
			if (StringUtils.looksEmpty(virtual) && oldTable.startsWith(sourceLI)) {
				String newTable = oldTable.replaceFirst("^"+sourceLI, targetLI);
				eo.setFieldValue(E.ENTITY.dbtable, newTable);
				
				DbUtils.renameTable(new PersistentDbAccess(dataBaseHelper.getDbAccess()), oldTable, newTable);
				
				// replace in dbobject source
				for (EntityObjectVO<UID> sourceEO : nucletDalProvider.getEntityObjectProcessor(E.DBSOURCE).getAll()) {
					boolean changed = replaceInDBOSource(sourceEO, oldTable, newTable);
					if (changed) {
						updateNucletContent(E.DBSOURCE, sourceEO, ci);
					}
				}
				// replace in datasources
				for (EntityObjectVO<UID> datasourceEO : nucletDalProvider.getEntityObjectProcessor(E.DATASOURCE).getAll()) {
					boolean changed = replaceInDataSource(datasourceEO, E.DATASOURCE.source, oldTable, newTable);
					if (changed) {
						updateNucletContent(E.DATASOURCE, datasourceEO, ci);
					}
				}
				for (EntityObjectVO<UID> datasourceEO : nucletDalProvider.getEntityObjectProcessor(E.DYNAMICENTITY).getAll()) {
					boolean changed = replaceInDataSource(datasourceEO, E.DYNAMICENTITY.source, oldTable, newTable);
					changed = changed || replaceInDataSource(datasourceEO, E.DYNAMICENTITY.query, oldTable, newTable);
					if (changed) {
						updateNucletContent(E.DYNAMICENTITY, datasourceEO, ci);
					}
				}
				for (EntityObjectVO<UID> datasourceEO : nucletDalProvider.getEntityObjectProcessor(E.VALUELISTPROVIDER).getAll()) {
					boolean changed = replaceInDataSource(datasourceEO, E.VALUELISTPROVIDER.source, oldTable, newTable);
					if (changed) {
						updateNucletContent(E.VALUELISTPROVIDER, datasourceEO, ci);
					}
				}
				for (EntityObjectVO<UID> datasourceEO : nucletDalProvider.getEntityObjectProcessor(E.RECORDGRANT).getAll()) {
					boolean changed = replaceInDataSource(datasourceEO, E.RECORDGRANT.source, oldTable, newTable);
					if (changed) {
						updateNucletContent(E.RECORDGRANT, datasourceEO, ci);
					}
				}
				for (EntityObjectVO<UID> datasourceEO : nucletDalProvider.getEntityObjectProcessor(E.DYNAMICTASKLIST).getAll()) {
					boolean changed = replaceInDataSource(datasourceEO, E.DYNAMICTASKLIST.source, oldTable, newTable);
					if (changed) {
						updateNucletContent(E.DYNAMICTASKLIST, datasourceEO, ci);
					}
				}
				for (EntityObjectVO<UID> datasourceEO : nucletDalProvider.getEntityObjectProcessor(E.CHART).getAll()) {
					boolean changed = replaceInDataSource(datasourceEO, E.CHART.source, oldTable, newTable);
					changed = changed || replaceInDataSource(datasourceEO, E.CHART.query, oldTable, newTable);
					if (changed) {
						updateNucletContent(E.CHART, datasourceEO, ci);
					}
				}
				for (EntityObjectVO<UID> datasourceEO : nucletDalProvider.getEntityObjectProcessor(E.CALCATTRIBUTE).getAll()) {
					boolean changed = replaceInDataSource(datasourceEO, E.CALCATTRIBUTE.source, oldTable, newTable);
					if (changed) {
						updateNucletContent(E.CALCATTRIBUTE, datasourceEO, ci);
					}
				}
				
				// rename lang table and reset reference to new dbtable name of main entity
				if (eo.getFieldValue(E.ENTITY.dataLanguageDBTable) != null) {
					String oldDLTableName = eo.getFieldValue(E.ENTITY.dataLanguageDBTable).toString();
					String newDLTableName = oldDLTableName.replace(sourceLI, targetLI);
					// first drop all constraints and indices
					NucletEntityMeta localizedBo = new NucletEntityMeta(metaProvider.getEntity(eo.getPrimaryKey()), true);
					NucletEntityMeta langTable = DataLanguageServerUtils.createEntityLanguageMeta(localizedBo, localizedBo.isUidEntity() ?  SF.PK_UID.getMetaData(localizedBo) : SF.PK_ID.getMetaData(localizedBo)); 
					final PersistentDbAccess dbAccess = new PersistentDbAccess(dataBaseHelper.getDbAccess());
					final MetaDbHelper helper = new MetaDbHelper(E.getSchemaHelperVersion(), dbAccess, metaProvider);
					DbTable dbTableLang = helper.getDbTable(langTable, langTable.getFields());
					List<DbConstraint> listConstraints = new ArrayList<DbConstraint>();
					listConstraints.addAll(dbTableLang.getTableArtifacts(DbForeignKeyConstraint.class));
					listConstraints.addAll(dbTableLang.getTableArtifacts(DbUniqueConstraint.class));
					createOrDropConstraints(listConstraints, false, dbAccess, null);
					createOrDropIndexes(helper.getIndexes(langTable), false, dbAccess, null);
					// rename lang table
					DbUtils.renameTable(dbAccess, oldDLTableName, newDLTableName);
					eo.setFieldValue(E.ENTITY.dataLanguageDBTable, newDLTableName);
					// now set the new table name to update the reference field in lang table
					UID foreignEntityReference = DataLanguageServerUtils.extractForeignEntityReference(eo.getPrimaryKey());
					localizedBo = new NucletEntityMeta(metaProvider.getEntity(eo.getPrimaryKey()), true);
					NucletEntityMeta updatedLangTable = 
							DataLanguageServerUtils.createEntityLanguageMeta(localizedBo, localizedBo.isUidEntity() ?  SF.PK_UID.getMetaData(localizedBo) : SF.PK_ID.getMetaData(localizedBo));
					String oldReferenceName = updatedLangTable.getField(foreignEntityReference).getDbColumn();
					localizedBo.setDbTable(eo.getFieldValue(E.ENTITY.dbtable));
					updatedLangTable = 
							DataLanguageServerUtils.createEntityLanguageMeta(localizedBo, localizedBo.isUidEntity() ?  SF.PK_UID.getMetaData(localizedBo) : SF.PK_ID.getMetaData(localizedBo));
					DataLanguageMetaDataProcessor pro = new DataLanguageMetaDataProcessor();
					pro.modifyReferenceColumn(updatedLangTable, updatedLangTable.getField(foreignEntityReference), oldReferenceName, dbAccess);
					// recreate indices
					createOrDropIndexes(helper.getIndexes(updatedLangTable), true, dbAccess, null);
					dbTableLang = helper.getDbTable(updatedLangTable, updatedLangTable.getFields());
					// recreate constrains
					listConstraints = new ArrayList<DbConstraint>();
					for (DbForeignKeyConstraint constraint : dbTableLang.getTableArtifacts(DbForeignKeyConstraint.class)) {
						if (constraint.getReferencedTable().getName().endsWith(oldTable)) {
							// the constraint to main table still has the old table name
							// so create an new constraint with new table name
							DbForeignKeyConstraint newConstaint = new DbForeignKeyConstraint(null, constraint.getTable(),
									constraint.getConstraintName(), constraint.getColumnNames(), new DbNamedObject(null, newTable),
									constraint.getReferencedConstraintName(), constraint.getReferencedColumnNames(), constraint.isOnDeleteCascade());
							constraint = newConstaint;
						} 
						listConstraints.add(constraint);
					}
					listConstraints.addAll(dbTableLang.getTableArtifacts(DbUniqueConstraint.class));
					createOrDropConstraints(listConstraints, true, dbAccess, null);
				}
			}
		} else if (E.DBOBJECT.equals(entity)) {
			String oldName = eo.getFieldValue(E.DBOBJECT.name);
			if (oldName.startsWith(sourceLI)) {
				String newName = oldName.replaceFirst("^"+sourceLI, targetLI);
				eo.setFieldValue(E.DBOBJECT.name, newName);
				
				// replace in entity
				for (EntityObjectVO<UID> entityEO : nucletDalProvider.getEntityObjectProcessor(E.ENTITY).getAll()) {
					boolean changed = replaceDBONameInEntity(entityEO, oldName, newName);
					if (changed) {
						updateNucletContent(E.ENTITY, entityEO, ci);
					}
				}
				// replace in calc attributes
				for (EntityObjectVO<UID> fieldEO : nucletDalProvider.getEntityObjectProcessor(E.ENTITYFIELD).getAll()) {
					boolean changed = replaceDBONameInField(fieldEO, oldName, newName);
					if (changed) {
						updateNucletContent(E.ENTITYFIELD, fieldEO, ci);
					}
				}
				// replace in job dbobject
				for (EntityObjectVO<UID> jobDbObjectEO : nucletDalProvider.getEntityObjectProcessor(E.JOBDBOBJECT).getAll()) {
					boolean changed = replaceDBONameInJobDbObject(jobDbObjectEO, oldName, newName);
					if (changed) {
						updateNucletContent(E.JOBDBOBJECT, jobDbObjectEO, ci);
					}
				}
				// replace in dbobject source
				for (EntityObjectVO<UID> sourceEO : nucletDalProvider.getEntityObjectProcessor(E.DBSOURCE).getAll()) {
					boolean changed = replaceInDBOSource(sourceEO, oldName, newName);
					if (changed) {
						updateNucletContent(E.DBSOURCE, sourceEO, ci);
					}
				}
				// replace in datasources
				for (EntityObjectVO<UID> datasourceEO : nucletDalProvider.getEntityObjectProcessor(E.DATASOURCE).getAll()) {
					boolean changed = replaceInDataSource(datasourceEO, E.DATASOURCE.source, oldName, newName);
					if (changed) {
						updateNucletContent(E.DATASOURCE, datasourceEO, ci);
					}
				}
				for (EntityObjectVO<UID> datasourceEO : nucletDalProvider.getEntityObjectProcessor(E.DYNAMICENTITY).getAll()) {
					boolean changed = replaceInDataSource(datasourceEO, E.DYNAMICENTITY.source, oldName, newName);
					changed = changed || replaceInDataSource(datasourceEO, E.DYNAMICENTITY.query, oldName, newName);
					if (changed) {
						updateNucletContent(E.DYNAMICENTITY, datasourceEO, ci);
					}
				}
				for (EntityObjectVO<UID> datasourceEO : nucletDalProvider.getEntityObjectProcessor(E.VALUELISTPROVIDER).getAll()) {
					boolean changed = replaceInDataSource(datasourceEO, E.VALUELISTPROVIDER.source, oldName, newName);
					if (changed) {
						updateNucletContent(E.VALUELISTPROVIDER, datasourceEO, ci);
					}
				}
				for (EntityObjectVO<UID> datasourceEO : nucletDalProvider.getEntityObjectProcessor(E.RECORDGRANT).getAll()) {
					boolean changed = replaceInDataSource(datasourceEO, E.RECORDGRANT.source, oldName, newName);
					if (changed) {
						updateNucletContent(E.RECORDGRANT, datasourceEO, ci);
					}
				}
				for (EntityObjectVO<UID> datasourceEO : nucletDalProvider.getEntityObjectProcessor(E.DYNAMICTASKLIST).getAll()) {
					boolean changed = replaceInDataSource(datasourceEO, E.DYNAMICTASKLIST.source, oldName, newName);
					if (changed) {
						updateNucletContent(E.DYNAMICTASKLIST, datasourceEO, ci);
					}
				}
				for (EntityObjectVO<UID> datasourceEO : nucletDalProvider.getEntityObjectProcessor(E.CHART).getAll()) {
					boolean changed = replaceInDataSource(datasourceEO, E.CHART.source, oldName, newName);
					changed = changed || replaceInDataSource(datasourceEO, E.CHART.query, oldName, newName);
					if (changed) {
						updateNucletContent(E.CHART, datasourceEO, ci);
					}
				}
			}
		}
		
		eo.setFieldUid(nucletRefField.getUID(), targetNuclet);
		eo.flagUpdate();
		DalUtils.updateVersionInformation(eo, getCurrentUserName());
		nucletDalProvider.getEntityObjectProcessor(entity).insertOrUpdate(eo);
		ci.objectChanged(entity, eo);
	}
	
	private void updateNucletContent(EntityMeta<UID> entity, EntityObjectVO<UID> eo, CacheInvalidator ci) {
		eo.flagUpdate();
		DalUtils.updateVersionInformation(eo, getCurrentUserName());
		nucletDalProvider.getEntityObjectProcessor(entity).insertOrUpdate(eo);
		ci.objectChanged(entity, eo);
	}
	
	private void updateLocalIdentifiers(final Map<UID, String> existingNuclets, final NucletContentMap importContentMap) {
		
		// local identifiers
		final Map<String, String> replaceLI = new HashMap<String, String>();
		// db tables
		final Map<String, String> replaceDBT = new HashMap<String, String>();
		// db objects
		final Map<String, String> replaceDBO = new HashMap<String, String>();
		
		final Set<String> allLiInUse = new HashSet<String>();
		for (EntityObjectVO<UID> eo : nucletDalProvider.getEntityObjectProcessor(E.NUCLET).getAll()) {
			allLiInUse.add(eo.getFieldValue(E.NUCLET.localidentifier));
		}
		
		for (TransferEO teo : importContentMap.getValues(E.NUCLET)) {
			final EntityObjectVO<UID> nucletEO = teo.eo;
			final UID nucletUID = nucletEO.getPrimaryKey();
			final String incomingLI = nucletEO.getFieldValue(E.NUCLET.localidentifier);
			
			if (existingNuclets.containsKey(nucletUID)) {
				// nuclet exist already
				String existingLI = existingNuclets.get(nucletUID);
				if (!StringUtils.equals(incomingLI, existingLI)) {
					replaceLI.put(incomingLI, existingLI);
					nucletEO.setFieldValue(E.NUCLET.localidentifier, existingLI);
				}
			} else {
				// new nuclet -> test if li is in use
				boolean liInUse = allLiInUse.contains(incomingLI);
				if (liInUse) {
					// new nuclet -> create new li
					DbObjectUtils.createUniqueLocalIdentifier(nucletUID, new LocalIdentifierStore() {
						@Override
						public void set(UID nucletUID, String li) {
							replaceLI.put(incomingLI, li);
							nucletEO.setFieldValue(E.NUCLET.localidentifier, li);
						}
						@Override
						public boolean exist(String li) {
							// search in import
							for (TransferEO teo : importContentMap.getValues(E.NUCLET)) {
								if (li.equals(teo.eo.getFieldValue(E.NUCLET.localidentifier))) {
									return true;
								}
							}
							// search in existing
							return allLiInUse.contains(li);
						}
					});
				}
			}
		}
		
		/*
		 *  replace local identifier in
		 *  - db objects
		 *  - db tables
		 *  
		 */
		for (Entry<String, String> repLI : replaceLI.entrySet()) {
			for (TransferEO dboEO : importContentMap.getValues(E.DBOBJECT)) {
				String incomingDBO = dboEO.eo.getFieldValue(E.DBOBJECT.name);
				if (incomingDBO.startsWith(repLI.getKey())) {
					String replacment = incomingDBO.replaceFirst("^"+repLI.getKey(), repLI.getValue());
					replaceDBO.put(incomingDBO, replacment);
					dboEO.eo.setFieldValue(E.DBOBJECT.name, replacment);
				}
			}
			for (TransferEO entityEO : importContentMap.getValues(E.ENTITY)) {
				String incomingDBT = entityEO.eo.getFieldValue(E.ENTITY.dbtable);
				String incomingVE = entityEO.eo.getFieldValue(E.ENTITY.virtualentity);
				String incomingRead = entityEO.eo.getFieldValue(E.ENTITY.readDelegate);
				
				if (incomingVE == null) {
					if (incomingDBT.startsWith(repLI.getKey())) {
						String replacment = incomingDBT.replaceFirst("^"+repLI.getKey(), repLI.getValue());
						replaceDBT.put(incomingDBT, replacment);
						entityEO.eo.setFieldValue(E.ENTITY.dbtable, replacment);
					}
				} else {
					// replace virtual entity
					if (replaceDBO.containsKey(incomingVE)) {
						entityEO.eo.setFieldValue(E.ENTITY.virtualentity, replaceDBO.get(incomingVE));
						entityEO.eo.setFieldValue(E.ENTITY.dbtable, replaceDBO.get(incomingVE));
					}
				}
				if (incomingRead != null) {
					if (replaceDBO.containsKey(incomingRead)) {
						entityEO.eo.setFieldValue(E.ENTITY.readDelegate, replaceDBO.get(incomingRead));
					}
				}
			}
		}
		
		/*
		 * replace color script
		 */
		for (TransferEO entityEO : importContentMap.getValues(E.ENTITY)) {
			NuclosScript rowcolorscript = entityEO.eo.getFieldValue(E.ENTITY.rowcolorscript);
			if (rowcolorscript != null) {
				if (!RigidUtils.looksEmpty(rowcolorscript.getSource())) {
					String goovycode = rowcolorscript.getSource();
					rowcolorscript.setSource(updateGroovycode(goovycode, replaceLI));
				}
			}
		}
		
		/*
		 *  replace calc function and color script
		 */
		for (TransferEO fieldEO : importContentMap.getValues(E.ENTITYFIELD)) {
			String field = fieldEO.eo.getFieldValue(E.ENTITYFIELD.field);
			String function = fieldEO.eo.getFieldValue(E.ENTITYFIELD.calcfunction);
			
			if (!RigidUtils.looksEmpty(function)) {
				function = function.toUpperCase();
				if (replaceDBO.containsKey(function)) {
					String replacment = replaceDBO.get(function);
					fieldEO.eo.setFieldValue(E.ENTITYFIELD.calcfunction, replacment);
				}
			}
			NuclosScript calcscript = fieldEO.eo.getFieldValue(E.ENTITYFIELD.calculationscript);
			if (calcscript != null) {
				if (!RigidUtils.looksEmpty(calcscript.getSource())) {
					String groovycode = calcscript.getSource();
					calcscript.setSource(updateGroovycode(groovycode, replaceLI));
				}
			}
			NuclosScript backgroundscript = fieldEO.eo.getFieldValue(E.ENTITYFIELD.backgroundcolorscript);
			if (backgroundscript != null) {
				if (!RigidUtils.looksEmpty(backgroundscript.getSource())) {
					String groovycode = backgroundscript.getSource();
					backgroundscript.setSource(updateGroovycode(groovycode, replaceLI));
				}
			}
		}
		
		/*
		 *  replace in dbobject source
		 */
		for (TransferEO sourceEO : importContentMap.getValues(E.DBSOURCE)) {
			String source = sourceEO.eo.getFieldValue(E.DBSOURCE.source);
			String drop = sourceEO.eo.getFieldValue(E.DBSOURCE.dropstatement);
			for (String search : replaceDBO.keySet()) {
				source = DbObjectUtils.replaceName(source, search, replaceDBO.get(search));
			}
			for (String search : replaceDBT.keySet()) {
				source = DbObjectUtils.replaceName(source, search, replaceDBT.get(search));
			}
			if (!RigidUtils.looksEmpty(drop)) {
				for (String search : replaceDBO.keySet()) {
					drop = DbObjectUtils.replaceName(drop, search, replaceDBO.get(search));
				}
				for (String search : replaceDBT.keySet()) {
					drop = DbObjectUtils.replaceName(drop, search, replaceDBT.get(search));
				}
			}
			
			sourceEO.eo.setFieldValue(E.DBSOURCE.source, source);
			sourceEO.eo.setFieldValue(E.DBSOURCE.dropstatement, drop);
		}
		
		/*
		 *  replace in datasources
		 */
		for (TransferEO datasourceEO : importContentMap.getValues(E.DATASOURCE)) {
			String source = datasourceEO.eo.getFieldValue(E.DATASOURCE.source);
			for (String search : replaceDBO.keySet()) {
				source = DbObjectUtils.replaceName(source, search, replaceDBO.get(search));
			}
			for (String search : replaceDBT.keySet()) {
				source = DbObjectUtils.replaceName(source, search, replaceDBT.get(search));
			}
			datasourceEO.eo.setFieldValue(E.DATASOURCE.source, source);
		}
		for (TransferEO datasourceEO : importContentMap.getValues(E.DYNAMICENTITY)) {
			String source = datasourceEO.eo.getFieldValue(E.DYNAMICENTITY.source);
			String query = datasourceEO.eo.getFieldValue(E.DYNAMICENTITY.query);
			for (String search : replaceDBO.keySet()) {
				source = DbObjectUtils.replaceName(source, search, replaceDBO.get(search));
				query = DbObjectUtils.replaceName(query, search, replaceDBO.get(search));
			}
			for (String search : replaceDBT.keySet()) {
				source = DbObjectUtils.replaceName(source, search, replaceDBT.get(search));
				query = DbObjectUtils.replaceName(query, search, replaceDBT.get(search));
			}
			datasourceEO.eo.setFieldValue(E.DYNAMICENTITY.source, source);
			datasourceEO.eo.setFieldValue(E.DYNAMICENTITY.query, query);
		}
		for (TransferEO datasourceEO : importContentMap.getValues(E.VALUELISTPROVIDER)) {
			String source = datasourceEO.eo.getFieldValue(E.VALUELISTPROVIDER.source);
			for (String search : replaceDBO.keySet()) {
				source = DbObjectUtils.replaceName(source, search, replaceDBO.get(search));
			}
			for (String search : replaceDBT.keySet()) {
				source = DbObjectUtils.replaceName(source, search, replaceDBT.get(search));
			}
			datasourceEO.eo.setFieldValue(E.VALUELISTPROVIDER.source, source);
		}
		for (TransferEO datasourceEO : importContentMap.getValues(E.RECORDGRANT)) {
			String source = datasourceEO.eo.getFieldValue(E.RECORDGRANT.source);
			for (String search : replaceDBO.keySet()) {
				source = DbObjectUtils.replaceName(source, search, replaceDBO.get(search));
			}
			for (String search : replaceDBT.keySet()) {
				source = DbObjectUtils.replaceName(source, search, replaceDBT.get(search));
			}
			datasourceEO.eo.setFieldValue(E.RECORDGRANT.source, source);
		}
		for (TransferEO datasourceEO : importContentMap.getValues(E.DYNAMICTASKLIST)) {
			String source = datasourceEO.eo.getFieldValue(E.DYNAMICTASKLIST.source);
			for (String search : replaceDBO.keySet()) {
				source = DbObjectUtils.replaceName(source, search, replaceDBO.get(search));
			}
			for (String search : replaceDBT.keySet()) {
				source = DbObjectUtils.replaceName(source, search, replaceDBT.get(search));
			}
			datasourceEO.eo.setFieldValue(E.DYNAMICTASKLIST.source, source);
		}
		for (TransferEO datasourceEO : importContentMap.getValues(E.CHART)) {
			String source = datasourceEO.eo.getFieldValue(E.CHART.source);
			String query = datasourceEO.eo.getFieldValue(E.CHART.query);
			for (String search : replaceDBO.keySet()) {
				source = DbObjectUtils.replaceName(source, search, replaceDBO.get(search));
				query = DbObjectUtils.replaceName(query, search, replaceDBO.get(search));
			}
			for (String search : replaceDBT.keySet()) {
				source = DbObjectUtils.replaceName(source, search, replaceDBT.get(search));
				query = DbObjectUtils.replaceName(query, search, replaceDBT.get(search));
			}
			datasourceEO.eo.setFieldValue(E.CHART.source, source);
			datasourceEO.eo.setFieldValue(E.CHART.query, query);
		}
		for (TransferEO datasourceEO : importContentMap.getValues(E.CALCATTRIBUTE)) {
			String source = datasourceEO.eo.getFieldValue(E.CALCATTRIBUTE.source);
			for (String search : replaceDBO.keySet()) {
				source = DbObjectUtils.replaceName(source, search, replaceDBO.get(search));
			}
			for (String search : replaceDBT.keySet()) {
				source = DbObjectUtils.replaceName(source, search, replaceDBT.get(search));
			}
			datasourceEO.eo.setFieldValue(E.CALCATTRIBUTE.source, source);
		}
		
		/*
		 * replace in jobs
		 */
		for (TransferEO jobDbObjectEO : importContentMap.getValues(E.JOBDBOBJECT)) {
			String dbObject = jobDbObjectEO.eo.getFieldValue(E.JOBDBOBJECT.object);
			for (String search : replaceDBO.keySet()) {
				dbObject = DbObjectUtils.replaceName(dbObject, search, replaceDBO.get(search));
			}
			jobDbObjectEO.eo.setFieldValue(E.JOBDBOBJECT.object, dbObject);
		}
		
		/*
		 *  replace in layouts
		 */
		for (TransferEO layoutEO : importContentMap.getValues(E.LAYOUT)) {
			String layoutML = layoutEO.eo.getFieldValue(E.LAYOUT.layoutML);
			layoutEO.eo.setFieldValue(E.LAYOUT.layoutML, updateGroovycode(layoutML, replaceLI));
		}
		
		/*
		 * replace in resplan
		 */
		for (TransferEO resplanEO : importContentMap.getValues(E.CUSTOMCOMPONENT)) {
			final Source xmlSource = SourceResultHelper.newSource((byte[]) resplanEO.eo.getFieldValue(E.CUSTOMCOMPONENT.data));
			if (xmlSource != null) {
				xmlSource.setSystemId("E.CUSTOMCOMPONENT.data");
			}
			final ResPlanConfigVO resplan;
			try {
				resplan = (ResPlanConfigVO) jaxb2Marshaller.unmarshal(xmlSource);
			} catch (OutOfMemoryError e) {
				LOG.error("JAXB unmarshal failed: resplan={} eo={} xml is:\n{}",
				          resplanEO, resplanEO.eo, xmlSource, e);
				throw e;
			}
			
			resplan.setScriptingCode(updateGroovycode(resplan.getScriptingCode(), replaceLI));
			resplan.setScriptingBackgroundPaintMethod(updateGroovycode(resplan.getScriptingBackgroundPaintMethod(), replaceLI));
			resplan.setScriptingResourceCellMethod(updateGroovycode(resplan.getScriptingResourceCellMethod(), replaceLI));
			resplan.setScriptingEntryCellMethod(updateGroovycode(resplan.getScriptingEntryCellMethod(), replaceLI));
			if (resplan.getPlanElements() != null) {
				for (Object o: resplan.getPlanElements()) {
					final PlanElement planelement = (PlanElement) o;
					planelement.setScriptingEntryCellMethod(updateGroovycode(planelement.getScriptingEntryCellMethod(), replaceLI));
				}
			}
			final StreamResult bytes = SourceResultHelper.newResultForByteArray();
			jaxb2Marshaller.marshal(resplan, bytes);
			resplanEO.eo.setFieldValue(E.CUSTOMCOMPONENT.data, 
					((ByteArrayOutputStream) bytes.getOutputStream()).toByteArray());
		}
	}
	
	private boolean replaceInDBOSource(EntityObjectVO<UID> sourceEO, String oldName, String newName) {
		boolean result = false;
		
		String oldSource = sourceEO.getFieldValue(E.DBSOURCE.source);
		String oldDrop = sourceEO.getFieldValue(E.DBSOURCE.dropstatement);
		String newSource = DbObjectUtils.replaceName(oldSource, oldName, newName);
		String newDrop = null;
		if (!RigidUtils.looksEmpty(oldDrop)) {
			newDrop = DbObjectUtils.replaceName(oldDrop, oldName, newName);
			if (!StringUtils.equals(oldDrop, newDrop)) {
				result = true;
				sourceEO.setFieldValue(E.DBSOURCE.dropstatement, newDrop);
			}
		}
		if (!StringUtils.equals(oldSource, newSource)) {
			result = true;
			sourceEO.setFieldValue(E.DBSOURCE.source, newSource);
		}
		
		return result;
	}
		
	private Collection<String> createOrDropTables(Collection<String> tablesToProcess, Map<String, DbTable> schema, boolean bCreate, boolean bWithConstraints) {
		final DbAccess dbAccess = dataBaseHelper.getDbAccess();
		
		Set<String> processed = new HashSet<String>();
		for (String table : tablesToProcess) {
			DbTable dbTable = schema.get(table);
			if (dbTable != null) {
				try {
					List<DbStructureChange> lstChanges = bCreate ? SchemaUtils.create(dbTable) : SchemaUtils.drop(dbTable);
					for (DbStructureChange db : lstChanges) {
						DbArtifact artifact = bCreate ? db.getArtifact2() : db.getArtifact1();
						//Do not process constraints but primary keys
						if (!bWithConstraints && artifact instanceof DbConstraint 
								&& !(artifact instanceof DbPrimaryKeyConstraint)) {
							continue;
						}
						
						dbAccess.execute(db);										
					}
					processed.add(table);
				} catch (DbException e) {
					String msg = bCreate ? "FAILED TO CREATE TABLE:" + table : "FAILED TO DROP TABLE:" + table;
					LOG.error(msg, e);
				}
			}
		}
		return processed;
	}
	
	private Collection<String> updateTableMetaData(Map<String, DbTable> oldSchema) {
		final DbAccess dbAccess = dataBaseHelper.getDbAccess();
		
		metaProvider.rebuildMapsUnsafe();
		
		Map<String, DbTable> newSchema = getSchema();
		SchemaDiff schemaDiff2 = dbAccess.getSchemaDiff(newSchema);
		
		Collection<String> droppedTables = createOrDropTables(schemaDiff2.getRemovedTables(SchemaDiff.DATATABLE), oldSchema, false, false);
		Collection<String> createdTables = createOrDropTables(schemaDiff2.getAddedTables(SchemaDiff.DATATABLE), newSchema, true, false);

		Collection<String> dataTables = schemaDiff2.getKeptTables(SchemaDiff.DATATABLE);
		dataTables.addAll(createdTables);

		LOG.info("Dropped Tables in updateTableMetaData: {}", droppedTables);
		LOG.info("Created Tables in updateTableMetaData: {}", createdTables);
		return dataTables;
	}
	
	private class FilterProviderImpl implements IStreamingFilterProvider {

		private final Collection<String> tables;
		private final Map<String, DbTable> startSchema;
		private boolean updated;
		private IMyDataBaseConnection myDBConnection;
		
		private FilterProviderImpl(Collection<String> tables, Map<String, DbTable> startSchema) {
			this.tables = tables;
			this.startSchema = startSchema;
			this.updated = false;
		}
		
		@Override
		public boolean prepare(String table) {
			try {
				String tableUpper = table.toUpperCase();
				
				if (!updated && !SchemaHelper.isSystemTable(tableUpper)) {
					myDBConnection.getConnection().commit();
					tables.addAll(updateTableMetaData(startSchema));
					myDBConnection.getConnection().commit();
					updated = true;
					myDBConnection.resetMeta();
				}
				
				if (tables.contains(tableUpper)) {
					myDBConnection.getConnection().createStatement().executeUpdate("DELETE FROM " + table);
					LOG.info("DB-Import Table: {}", table);
					return true;
				} 
				
			} catch (Exception ie) {
				LOG.error(ie.getMessage(), ie);
			}
			
			return false;
		}
		
		@Override
		public void commit() {
			try {
				myDBConnection.getConnection().commit();				
			} catch (SQLException e) {
				LOG.error(e.getMessage(), e);
			}
		}

		@Override
		public void setMyDataBaseConnection(IMyDataBaseConnection conn) {
			this.myDBConnection = conn;
		}
		
	}
	
	public BoDataSet importOrExportDatabase(InputStream is) throws CommonPermissionException {
		
		if (!securityCache.isSuperUser(getCurrentUserName())) {
			throw new CommonPermissionException("Only Super-User is allowed to export or import the Database!");
			
		} else {
			
			try {
				final DbAccess dbAccess = dataBaseHelper.getDbAccess();
				
				Map<String, DbTable> oldSchema = getSchema();
				SchemaDiff schemaDiff = dbAccess.getSchemaDiff(oldSchema);
				
				if (is == null) {
					List<String> systemtables = new ArrayList<String>(schemaDiff.getDbTables(SchemaDiff.SYSTEMTABLE));
					List<String> datatables = new ArrayList<String>(schemaDiff.getDbTables(SchemaDiff.DATATABLE));
					
					Collections.sort(systemtables);
					Collections.sort(datatables);
					
					List<String> allTables = new ArrayList<String>(systemtables);
					allTables.addAll(datatables);
									
					return dbAccess.exportDatabase(allTables);
				}
				
				Collection<String> createdSystemTables = createOrDropTables(schemaDiff.getAddedTables(SchemaDiff.SYSTEMTABLE), oldSchema, true, true);
				
				Set<UID> oldEntities = metaProvider.getAllEntityUids();
				createOrDropConstraints(getConstraints(dbAccess), false, dbAccess, null);
				
				try {
					Collection<String> systemTables = schemaDiff.getKeptTables(SchemaDiff.SYSTEMTABLE);
					systemTables.addAll(createdSystemTables);
					systemTables.remove(E.RELEASEHISTORY.getDbTable());
					
					FilterProviderImpl filterProvider = new FilterProviderImpl(systemTables, oldSchema);

					dbAccess.importDatabase(is, filterProvider);
					
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
					
				} finally {
					
					StringBuffer sbWarning = new StringBuffer();
					StringBuffer sbCritical = new StringBuffer();
					
					revalidateCacheAndStateModels(oldEntities, false, sbWarning, sbCritical);

					createOrDropConstraints(getConstraints(dbAccess), true, dbAccess, null);
					
					Long maxId = getMaxIdFromLongEntities();
					if (maxId >= 0L) {
						dbAccess.resetSequenceToValue(SpringDataBaseHelper.DEFAULT_SEQUENCE, maxId + 1);
					}

				}
				
				return null;
				
			} catch (NuclosFatalException nfe) {
				throw nfe;
			} catch (Exception e) {
				throw new NuclosFatalException(e.getMessage(), e);
			}
		}
	}
	
	private Long getMaxIdFromLongEntities() {
		final DbAccess dbAccess = dataBaseHelper.getDbAccess();
		
		Long maxId = -1L;
		Map<String, DbTable> schemas = getSchema();
		for (String dbTable : schemas.keySet()) {
			if ("INTID".equalsIgnoreCase(schemas.get(dbTable).getPkColumnName())) {
				Long entMaxId = dbAccess.getMaxId(dbTable);
				if (entMaxId != null && entMaxId > maxId) {
					maxId = entMaxId;
				}
			}
		}
		
		return maxId;
	}
	
	private boolean replaceInDataSource(EntityObjectVO<UID> sourceEO, FieldMeta<?> field, String oldName, String newName) {
		boolean result = false;
		
		String oldSource = sourceEO.getFieldValue(field.getUID(), String.class);
		if (oldSource == null) {
			return result;
		}
		String newSource = DbObjectUtils.replaceName(oldSource, oldName, newName);
		if (!StringUtils.equals(oldSource, newSource)) {
			sourceEO.setFieldValue(field.getUID(), newSource);
			result = true;
		}
		
		return result;
	}
	
	private boolean replaceDBONameInEntity(EntityObjectVO<UID> entityEO, String oldName, String newName) {
		boolean result = false;
		
		String dbt = entityEO.getFieldValue(E.ENTITY.dbtable);
		String ve = entityEO.getFieldValue(E.ENTITY.virtualentity);
		String read = entityEO.getFieldValue(E.ENTITY.readDelegate);
		if (ve == null) {
			if (dbt.equals(oldName)) {
				entityEO.setFieldValue(E.ENTITY.dbtable, newName);
				result = true;
			}
		} else {
			// replace virtual entity
			if (ve.equals(oldName)) {
				entityEO.setFieldValue(E.ENTITY.virtualentity, newName);
				entityEO.setFieldValue(E.ENTITY.dbtable, newName);
				result = true;
			}
		}
		if (read != null) {
			if (read.equals(oldName)) {
				entityEO.setFieldValue(E.ENTITY.readDelegate, newName);
				result = true;
			}
		}
		
		return result;
	}
	
	private boolean replaceDBONameInField(EntityObjectVO<UID> fieldEO, String oldName, String newName) {
		boolean result = false;
		
		String function = fieldEO.getFieldValue(E.ENTITYFIELD.calcfunction);
		if (!RigidUtils.looksEmpty(function)) {
			function = function.toUpperCase();
			if (function.equals(oldName)) {
				fieldEO.setFieldValue(E.ENTITYFIELD.calcfunction, newName);
				result = true;				
			}
		}
		
		return result;
	}
	
	private boolean replaceDBONameInJobDbObject(EntityObjectVO<UID> jobdbObjectEO, String oldName, String newName) {
		boolean result = false;
		
		String object = jobdbObjectEO.getFieldValue(E.JOBDBOBJECT.object);
		if (!RigidUtils.looksEmpty(object)) {
			object = object.toUpperCase();
			if (object.equals(oldName)) {
				jobdbObjectEO.setFieldValue(E.JOBDBOBJECT.object, newName);
				result = true;				
			}
		}
		
		return result;
	}
	
	private String replaceLocalIdentifierInGroovycode(String searchIn, String searchFor, String replacement) {
		Matcher m = Pattern.compile(Pattern.quote("\"#{"+ searchFor +".")).matcher(searchIn);
		
		StringBuffer sb = new StringBuffer(searchIn.length());
		int index = 0;
		while (m.find()) {
			sb.append(searchIn.substring(index, m.start()+3));
			sb.append(replacement);
			index = m.start()+3+searchFor.length();
		}
		if (index < searchIn.length()-1) {
			sb.append(searchIn.substring(index));
		}
		
		return sb.toString();
	}
	
	private String updateGroovycode(String searchIn, Map<String, String> replaceLI) {
		if (searchIn == null) {
			return null;
		}
		for (String searchFor : replaceLI.keySet()) {
			String replacement = replaceLI.get(searchFor);
			searchIn = replaceLocalIdentifierInGroovycode(searchIn, searchFor, replacement);
		}
		return searchIn;
	}

	/**
	 * Synchronizes the given old and new parameters:
	 * If a new param already exists, the old value is used.
	 * If a new param does not already exist, the new value is used.
	 * If an old param does not exist in the new params anymore, it is omitted (i.e. it will be deleted).
	 * 
	 * @param oldParams
	 * @param newParams
	 * @return
	 */
	public Collection<EntityObjectVO<UID>> synchronizeParameters(Collection<EntityObjectVO<UID>> oldParams,
			Collection<EntityObjectVO<UID>> newParams) {
		Collection<EntityObjectVO<UID>> result = new ArrayList<EntityObjectVO<UID>>();
		Map<UID, EntityObjectVO<UID>> mapOld = mapByUID(oldParams);
		Map<UID, EntityObjectVO<UID>> mapNew = mapByUID(newParams);

		for (UID uid : mapNew.keySet()) {
			if (mapOld.containsKey(uid)) {
				result.add(mapOld.get(uid));
			} else {
				result.add(mapNew.get(uid));
			}
		}

		return result;
	}

	private Map<UID, EntityObjectVO<UID>> mapByUID(Collection<EntityObjectVO<UID>> entityObjects) {
		final Map<UID, EntityObjectVO<UID>> map = new HashMap<UID, EntityObjectVO<UID>>();
		for (EntityObjectVO<UID> eo : entityObjects) {
			map.put(eo.getPrimaryKey(), eo);
		}
		return map;
	}
}
