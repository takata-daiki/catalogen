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
package org.nuclos.client.nuclet.generator.content;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.nuclos.client.common.MetaProvider;
import org.nuclos.client.nuclet.generator.NucletGenerator;
import org.nuclos.common.E;
import org.nuclos.common.NuclosBusinessException;
import org.nuclos.common.UID;
import org.nuclos.common.dal.vo.EntityObjectVO;
import org.nuclos.common2.LangUtils;
import org.nuclos.common2.StringUtils;

public class EntityNucletContentGenerator extends AbstractNucletContentGenerator {
	
	public static final String SHEET = "Entity";

	public static final int COL_NAME = 0;						// STRING
	public static final int COL_TABLE_NAME = 1;					// STRING
	public static final int COL_LABEL = 2;						// STRING
	public static final int COL_MENU = 3;						// STRING
	public static final int COL_MENUSHORTCUT = 4;				// STRING
	public static final int COL_ACCELERATOR = 5;				// STRING
	public static final int COL_ACCELERATOR_MODIFIER = 6;		// INTEGER
	public static final int COL_SEARCHABLE = 7;					// BOOLEAN
	public static final int COL_CACHEABLE = 8;					// BOOLEAN
	public static final int COL_LOGBOOK = 9;					// BOOLEAN
	public static final int COL_EDITABLE = 10;					// BOOLEAN
	public static final int COL_STATEMODEL = 11;				// BOOLEAN
	public static final int COL_SYSTEM_ID_PREFIX = 12;			// STRING
	public static final int COLUMN_COUNT = 13;
	
	public static final String FIELD_ENTITY = "entity";

	public EntityNucletContentGenerator(NucletGenerator generator) {
		super(generator, E.ENTITY);
	}
	
	@Override
	public String getSheetName() {
		return SHEET;
	}

	@Override
	public void generateEntityObjects() {
		final XSSFSheet sheet = generator.getWorkbook().getSheet(SHEET);
		for (Row row : sheet) {
			if (row.getRowNum() <= 1)
				continue; // header row
			
			newEntityObject();
			
			boolean emptyRow = true;
			for (int i = 0; i < COLUMN_COUNT; i++) {
				final Cell cell = row.getCell(i); // could be null!
				try {
					switch (i) {
					case COL_NAME:
						storeField(E.ENTITY.entity.getUID(), getStringValue(cell));
						if (!StringUtils.looksEmpty(getStringValue(cell))) {
							emptyRow = false;
						}
						break;
					case COL_TABLE_NAME:
						storeField(E.ENTITY.dbtable.getUID(), getStringValue(cell));
						break;
					case COL_LABEL:
						storeLocaleResource(E.ENTITY.localeresourcel.getUID(), getStringValue(cell));
						storeLocaleResource(E.ENTITY.localeresourced.getUID(), getStringValue(cell));
						break;
					case COL_MENU:
						storeLocaleResource(E.ENTITY.localeresourcem.getUID(), getStringValue(cell));
						break;
					case COL_MENUSHORTCUT:
						storeField(E.ENTITY.menushortcut.getUID(), getStringValue(cell));
						break;
					case COL_ACCELERATOR:
						storeField(E.ENTITY.accelerator.getUID(), getStringValue(cell));
						break;
					case COL_ACCELERATOR_MODIFIER:
						storeField(E.ENTITY.acceleratormodifier.getUID(), getIntegerValue(cell));
						break;
					case COL_SEARCHABLE:
						storeField(E.ENTITY.searchable.getUID(), getBooleanValue(cell));
						break;
					case COL_CACHEABLE:
						storeField(E.ENTITY.cacheable.getUID(), getBooleanValue(cell));
						break;
					case COL_LOGBOOK:
						storeField(E.ENTITY.logbooktracking.getUID(), getBooleanValue(cell));
						break;
					case COL_EDITABLE:
						storeField(E.ENTITY.editable.getUID(), getBooleanValue(cell));
						break;
					case COL_STATEMODEL:
						storeField(E.ENTITY.usessatemodel.getUID(), getBooleanValue(cell));
						break;
					case COL_SYSTEM_ID_PREFIX:
						storeField(E.ENTITY.systemidprefix.getUID(), getStringValue(cell));
						break;
					}
				} catch (Exception ex) {
					error(cell, ex);
				}
			}
			
			if (!emptyRow)
				finishEntityObject();
		}
	}
	
	@Override
	protected boolean finishEntityObject() {
		boolean result = super.finishEntityObject();
		if (result) {
			// add static not nullable fields here
			eo.setFieldValue(E.ENTITY.fieldvalueentity.getUID(), false);
			eo.setFieldValue(E.ENTITY.treerelation.getUID(), false);
			eo.setFieldValue(E.ENTITY.treegroup.getUID(), false);		
			eo.setFieldValue(E.ENTITY.importexport.getUID(), false);		
		}
		return result;
	}
	
	public UID getIdByName(String entity) throws NuclosBusinessException {
		
		for (EntityObjectVO<UID> eo : getResult()) {
			UID uidEntity = eo.getPrimaryKey();
			String sName = MetaProvider.getInstance().getEntity(uidEntity).getEntityName();
			
			if (LangUtils.equals(sName, entity)) {
				return eo.getPrimaryKey();
			}
		}
		
		throw new NuclosBusinessException(String.format("Entity with name \"%s\" not found!", entity));
	}

}
