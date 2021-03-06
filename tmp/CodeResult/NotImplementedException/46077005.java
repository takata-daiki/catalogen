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
package org.nuclos.server.autosync;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.nuclos.server.dblayer.DbAccess;
import org.nuclos.server.dblayer.statements.DbStructureChange;
import org.nuclos.server.dblayer.structure.DbArtifact;
import org.nuclos.server.dblayer.structure.DbTable;
import org.nuclos.server.dblayer.structure.DbTableArtifact;
import org.nuclos.server.dblayer.util.StatementToStringVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaValidator {

	private static final Logger LOG = LoggerFactory.getLogger(SchemaValidator.class);

	private final DbAccess dbAccess;
	private boolean preview = false;
	private boolean idfactory = false;

	public SchemaValidator(DbAccess dbAccess, String[] parameters) {
	    super();
	    this.dbAccess = dbAccess;
	    for (String parameter : parameters) {
	    	if ("-p".equals(parameter)) {
	    		this.preview = true;
	    	}
	    	if ("-idfactory".equals(parameter)) {
	    		this.idfactory = true;
	    	}
	    }
    }

	public void validate() {
		// drop views in db
		throw new NotImplementedException("validate schema");
//		for (String viewname : dbAccess.getTableNames(DbTableType.VIEW)) {
//			List<DbSimpleViewColumn> cs = new ArrayList<DbSimpleViewColumn>();
//			executeStructureChange(new DbStructureChange(DbStructureChange.Type.DROP, new DbSimpleView(viewname, viewname, cs)));
//		}
//
//		// drop all artifacts except tables;
//		Collection<DbArtifact> artifacts = dbAccess.getAllMetaData();
//
//		// drop callables and sequences (except IDFACTORY as this would reset the sequence)
//		for (DbArtifact artifact : artifacts) {
//			if (artifact instanceof DbCallable) {
//				if (((DbCallable) artifact).getCallableName().equals(SpringDataBaseHelper.DEFAULT_SEQUENCE) && !idfactory) {
//					continue;
//				}
//				executeStructureChange(new DbStructureChange(DbStructureChange.Type.DROP, artifact));
//			}
//		}
//
//		// drop foreign keys
//		for (DbForeignKeyConstraint artifact : getTableArtifacts(artifacts, DbForeignKeyConstraint.class)) {
//			executeStructureChange(new DbStructureChange(DbStructureChange.Type.DROP, artifact));
//		}
//
//		// drop unique constraints
//		for (DbUniqueConstraint artifact : getTableArtifacts(artifacts, DbUniqueConstraint.class)) {
//			executeStructureChange(new DbStructureChange(DbStructureChange.Type.DROP, artifact));
//		}
//
//		// drop primary key constraints
//		for (DbPrimaryKeyConstraint artifact : getTableArtifacts(artifacts, DbPrimaryKeyConstraint.class)) {
//			executeStructureChange(new DbStructureChange(DbStructureChange.Type.DROP, artifact));
//		}
//
//		// drop indexes
//		for (DbIndex artifact : getTableArtifacts(artifacts, DbIndex.class)) {
//			executeStructureChange(new DbStructureChange(DbStructureChange.Type.DROP, artifact));
//		}
//
//		Transformer<DbTable, String> tabletransformer = new Transformer<DbTable, String>() {
//			@Override
//			public String transform(DbTable i) {
//				return i.getTableName().toUpperCase();
//			}
//		};
//
//		Map<String, DbTable> actualTables = CollectionUtils.transformIntoMap(CollectionUtils.selectInstancesOf(artifacts, DbTable.class), tabletransformer);
//
//		AutoDbSetup setup = new AutoDbSetup(dbAccess);
//
//		// validate tables and columns
//		Map<String, DbTable> setTables = CollectionUtils.transformIntoMap(CollectionUtils.selectInstancesOf(setup.getCurrentSchema().getX(), DbTable.class), tabletransformer);
//
//		MetaDbHelper dbHelper = new MetaDbHelper(dbAccess,  MetaProvider.getInstance());
//		setTables.putAll(CollectionUtils.transformIntoMap(dbHelper.getSchema().values(), tabletransformer));
//
//		artifacts = new ArrayList<DbArtifact>();
//		artifacts.addAll(setTables.values());
//
//		Set<String> commontables = CollectionUtils.intersection(actualTables.keySet(), setTables.keySet());
//
//		for (String tablename : commontables) {
//			DbTable actualTable = actualTables.get(tablename);
//			DbTable setTable = setTables.get(tablename);
//
//			Transformer<DbColumn, String> columntransformer = new Transformer<DbColumn, String>() {
//				@Override
//				public String transform(DbColumn i) {
//					return i.getColumnName().toUpperCase();
//				}
//			};
//
//			Map<String, DbColumn> actualColumns = CollectionUtils.transformIntoMap(actualTable.getTableColumns(), columntransformer);
//			Map<String, DbColumn> setColumns = CollectionUtils.transformIntoMap(setTable.getTableColumns(), columntransformer);
//
//			Set<String> commoncolumns = CollectionUtils.intersection(actualColumns.keySet(), setColumns.keySet());
//
//
//			for (String columnname : commoncolumns) {
//				DbColumn actualColumn = actualColumns.get(columnname);
//				DbColumn setColumn = setColumns.get(columnname);
//				if (actualColumn.getColumnName().equals(setColumn.getColumnName())) {
//					if (actualColumn.isAltered(setColumn)) {
//						executeStructureChange(new DbStructureChange(Type.MODIFY, actualColumn, setColumn));
//					}
//				}
//				actualColumns.remove(columnname);
//				setColumns.remove(columnname);
//			}
//
//			for (DbColumn column : actualColumns.values()) {
//				executeStructureChange(new DbStructureChange(Type.DROP, column));
//			}
//
//			for (DbColumn column : setColumns.values()) {
//				executeStructureChange(new DbStructureChange(Type.CREATE, column));
//			}
//
//			actualTables.remove(tablename);
//			setTables.remove(tablename);
//		}
//
//		for (DbTable table : actualTables.values()) {
//			log.info("Table " + table.getTableName() + " is no more present in new schema and should be dropped.");
//			//executeStructureChange(new DbStructureChange(Type.DROP, table));
//		}
//
//		for (DbTable table : setTables.values()) {
//			// create tables without keys and constraints first
//			DbTable tmpTable = new DbTable(table.getTableName(), table.getTableColumns());
//			executeStructureChange(new DbStructureChange(Type.CREATE, tmpTable));
//		}
//
//		// create primary keys
//		for (DbPrimaryKeyConstraint artifact : getTableArtifacts(artifacts, DbPrimaryKeyConstraint.class)) {
//			executeStructureChange(new DbStructureChange(DbStructureChange.Type.CREATE, artifact));
//		}
//
//		// create callables
//		for (DbStatement stmt : setup.getSetupStatements()) {
//			if (stmt instanceof DbStructureChange) {
//				DbStructureChange sc = (DbStructureChange)stmt;
//				if (sc.getArtifact2() instanceof DbCallable || (sc.getArtifact2() instanceof DbSequence && idfactory)) {
//					executeStructureChange(sc);
//				}
//			}
//		}
//
//		// create foreign key constraints
//		for (DbForeignKeyConstraint artifact : getTableArtifacts(artifacts, DbForeignKeyConstraint.class)) {
//			executeStructureChange(new DbStructureChange(DbStructureChange.Type.CREATE, artifact));
//		}
//
//		// create unique constraints
//		for (DbUniqueConstraint artifact : getTableArtifacts(artifacts, DbUniqueConstraint.class)) {
//			executeStructureChange(new DbStructureChange(DbStructureChange.Type.CREATE, artifact));
//		}
//
//		// create views
//		for (DbSimpleView artifact : getTableArtifacts(artifacts, DbSimpleView.class)) {
//			executeStructureChange(new DbStructureChange(DbStructureChange.Type.CREATE, artifact));
//		}
//
//		// create indexes
//		for (DbIndex artifact : getTableArtifacts(artifacts, DbIndex.class)) {
//			executeStructureChange(new DbStructureChange(DbStructureChange.Type.CREATE, artifact));
//		}
//
//		// create release entry if necessary
//		if (setup.getInstalledVersions().isEmpty()) {
//			SpringDataBaseHelper.getInstance().execute(setup.getReleaseStatement(setup.getCurrentRelease()));
//		}
    }

	private <T extends DbTableArtifact> Collection<T> getTableArtifacts(Collection<DbArtifact> artifacts, Class<T> clazz) {
		Collection<T> result = new ArrayList<T>();
		for (DbArtifact artifact : artifacts) {
			if (artifact instanceof DbTable) {
				DbTable table = (DbTable) artifact;
				result.addAll(table.getTableArtifacts(clazz));
			}
		}
		return result;
	}

	private void executeStructureChange(DbStructureChange sc) {
		try {
			LOG.info(sc.accept(new StatementToStringVisitor()));
			if (!preview) {
				dbAccess.execute(sc);
			}
		} catch (Exception ex) {
			LOG.error("Unable to execute structure change:", ex);
		};
	}
}
