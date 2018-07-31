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
package org.nuclos.server.dbtransfer.content;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nuclos.common.E;
import org.nuclos.common.EntityMeta;
import org.nuclos.common.FieldMeta;
import org.nuclos.common.NuclosFatalException;
import org.nuclos.common.RigidUtils;
import org.nuclos.common.SF;
import org.nuclos.common.UID;
import org.nuclos.common.collection.BinaryPredicate;
import org.nuclos.common.collection.CollectionUtils;
import org.nuclos.common.dal.DalCallResult;
import org.nuclos.common.dal.vo.Delete;
import org.nuclos.common.dal.vo.EntityObjectVO;
import org.nuclos.common.dbtransfer.NucletContentMap;
import org.nuclos.common.dbtransfer.TransferEO;
import org.nuclos.common.dbtransfer.TransferOption;
import org.nuclos.common.nuclet.AbstractNucletContentConstants;
import org.nuclos.common2.InternalTimestamp;
import org.nuclos.common2.LangUtils;
import org.nuclos.common2.LocaleInfo;
import org.nuclos.server.common.MetaProvider;
import org.nuclos.server.common.ServerServiceLocator;
import org.nuclos.server.common.ejb3.LocaleFacadeLocal;
import org.nuclos.server.dal.processor.jdbc.impl.EntityObjectProcessor;
import org.nuclos.server.dal.processor.nuclet.IEntityObjectProcessor;
import org.nuclos.server.dal.provider.NucletDalProvider;
import org.nuclos.server.dblayer.DbException;
import org.nuclos.server.dbtransfer.TransferUtils;
import org.nuclos.server.genericobject.searchcondition.CollectableSearchExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractNucletContent implements INucletContent, AbstractNucletContentConstants {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractNucletContent.class);

	private final EntityMeta<UID> entity;
	private final EntityMeta<UID> parententity;
	private final FieldMeta<UID> fieldToParent;
	
	protected final List<INucletContent> contentTypes;

	private boolean enabled = true;
	private final boolean ignoreReferenceToNuclet;
	
	private static Map<String, Map<String, String>> localeResourceCache;
	private static Collection<LocaleInfo> allLocales;
	
	private static Map<UID, Map<UID, EntityObjectVO<UID>>> eoCaches;
	private static Map<UID, Map<UID, EntityObjectVO<UID>>> eoOriginalCaches;
	private static int countRead, countInsertedOrUpdated, countNotInsertedOrUpdated, countDeleted;

	public AbstractNucletContent(FieldMeta<UID> fieldToParent, List<INucletContent> contentTypes) {
		this(fieldToParent, contentTypes, false);
	}
	
	public AbstractNucletContent(FieldMeta<UID> fieldToParent, List<INucletContent> contentTypes, boolean ignoreReferenceToNuclet) {
		this(E.<UID>getByUID(fieldToParent.getEntity()), E.<UID>getByUID(LangUtils.defaultIfNull(
				fieldToParent.getForeignEntity(), fieldToParent.getUnreferencedForeignEntity())), fieldToParent, contentTypes, ignoreReferenceToNuclet);
	}
	
	public AbstractNucletContent(EntityMeta<UID> entity, List<INucletContent> contentTypes) {
		this(entity, null, null, contentTypes, false);
	}
	
	public AbstractNucletContent(EntityMeta<UID> entity, List<INucletContent> contentTypes, boolean ignoreReferenceToNuclet) {
		this(entity, null, null, contentTypes, ignoreReferenceToNuclet);
	}

	private AbstractNucletContent(EntityMeta<UID> entity, EntityMeta<UID> parententity, FieldMeta<UID> fieldToParent, List<INucletContent> contentTypes, boolean ignoreReferenceToNuclet) {
		super();
		this.entity = entity;
		this.parententity = parententity;
		this.fieldToParent = fieldToParent;
		this.contentTypes = contentTypes;
		this.ignoreReferenceToNuclet = ignoreReferenceToNuclet;

		if (parententity != null) {
			boolean isParentForeignEntity = false;
			for (EntityMeta<?> eMeta : TransferUtils.getForeignEntities(entity)) {
				if (parententity.equals(eMeta)) {
					isParentForeignEntity = true;
				}
			}

			if (!isParentForeignEntity) {
				throw new IllegalArgumentException("Parent entity must be a foreign entity");
			}
		} else {
			if (!ignoreReferenceToNuclet) {
				TransferUtils.getForeignFieldToNuclet(entity); // check validity
			}
		}
	}

	@Override
	public EntityMeta<UID> getEntity() {
		return entity;
	}

	@Override
	public EntityMeta<UID> getParentEntity() {
		return parententity;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public List<EntityObjectVO<UID>> getNcObjects(Set<UID> nucletUIDs) {
		List<EntityObjectVO<UID>> result = new ArrayList<EntityObjectVO<UID>>();
		Set<UID> distinctUIDs = new HashSet<UID>();

		if (!isEnabled())
			return result;
		if (ignoreReferenceToNuclet)
//			return CollectionUtils.transform(NucletDalProvider.getInstance().getEntityObjectProcessor(entity).getAll(), new Transformer<EntityObjectVO<UID>, EntityObjectVO<UID>>() {
//				@Override
//				public EntityObjectVO<UID> transform(EntityObjectVO<UID> ncObject) {
//					return readNc(ncObject);
//				}
//			});
			return getAllEOs(entity.getUID());

		if (nucletUIDs == null || nucletUIDs.isEmpty())
			return result;

		boolean fieldToNucletExists = false;
		try {TransferUtils.getForeignFieldToNuclet(entity); fieldToNucletExists = true;} catch (Exception ex) {}
		if (fieldToNucletExists) {
			// entity has reference to nuclet
			UID fieldToNucletUID = TransferUtils.getForeignFieldToNuclet(entity).getUID();
//			for (UID nucletUID : nucletUIDs) {
//				List<EntityObjectVO<UID>> tmp = NucletDalProvider.getInstance().getEntityObjectProcessor(entity).getBySearchExpression(
//					new CollectableSearchExpression(SearchConditionUtils.newUidComparison(fieldToNucletUID, 
//						ComparisonOperator.EQUAL, nucletUID)));
//				for (EntityObjectVO<UID> eo : tmp) {
			for (EntityObjectVO<UID> eo : getAllEOs(entity.getUID())) {
				UID nucletUID = eo.getFieldUid(fieldToNucletUID);
				if (nucletUIDs.contains(nucletUID)) {
//					
//					result.add(readNc(eo));
					result.add(eo);
				}
			}			
		} else {
			// get nuclet references from parent(s)
			INucletContent ncParent = TransferUtils.getContentType(contentTypes, parententity);
			for (EntityObjectVO<UID> parent : ncParent.getNcObjects(nucletUIDs)) {
//				for (EntityObjectVO<UID> eo : NucletDalProvider.getInstance().getEntityObjectProcessor(entity).getBySearchExpression(
//					new CollectableSearchExpression(SearchConditionUtils.newUidComparison(
//							fieldToParent, ComparisonOperator.EQUAL, parent.getPrimaryKey())))) {
				for (EntityObjectVO<UID> eo : getAllEOs(entity.getUID())) {
					UID parentUID = eo.getFieldUid(fieldToParent);
					if (!RigidUtils.equal(parentUID, parent.getPrimaryKey())) {
						continue;
					}
//					
					if (!distinctUIDs.contains(eo.getPrimaryKey())) {
						distinctUIDs.add(eo.getPrimaryKey());
//						result.add(readNc(eo));
						result.add(eo);
					}
				}
			}
		}
		return new ArrayList<EntityObjectVO<UID>>(CollectionUtils.distinct(result, new BinaryPredicate<EntityObjectVO<UID>, EntityObjectVO<UID>>() {
			@Override
			public boolean evaluate(EntityObjectVO<UID> t1, EntityObjectVO<UID> t2) {
				return LangUtils.equal(t1.getPrimaryKey(), t2.getPrimaryKey());
			}
		}));
	}

	@Override
	public List<EntityObjectVO<UID>> getNcObjectsByParent(EntityObjectVO<UID> parent) {
		if (parententity == null) {
			throw new IllegalArgumentException("Is parent!");
		}
		List<EntityObjectVO<UID>> result = new ArrayList<EntityObjectVO<UID>>();
		FieldMeta<UID> fieldToParent = getFieldToParent();
		UID foreignEntity = LangUtils.defaultIfNull(fieldToParent.getForeignEntity(), fieldToParent.getUnreferencedForeignEntity());
		if (foreignEntity != null) {
//			for (EntityObjectVO<UID> eo : NucletDalProvider.getInstance().getEntityObjectProcessor(entity).getBySearchExpression(
//				new CollectableSearchExpression(SearchConditionUtils.newUidComparison(
//					fieldToParent, 
//					ComparisonOperator.EQUAL, 
//					parent.getPrimaryKey())))) {
			for (EntityObjectVO<UID> eo : getAllEOs(entity.getUID())) {
				UID parentUID = eo.getFieldUid(fieldToParent);
				if (!RigidUtils.equal(parentUID, parent.getPrimaryKey())) {
					continue;
				}
//			
//				result.add(readNc(eo));
				result.add(eo);
			}
		} else {
			throw new IllegalArgumentException();
		}
		
		return result;
	}

	@Override
	public void deleteNcObject(DalCallResult result, EntityObjectVO<UID> ncObject, boolean testOnly) {
		try {
			NucletDalProvider.getInstance().getEntityObjectProcessor(entity).delete(new Delete(ncObject.getPrimaryKey()));
			deleteEOFromCache(entity.getUID(), ncObject.getPrimaryKey());
		}
		catch (DbException e) {
			result.addRuntimeException(e);
		}
	}

	@Override
	public boolean insertOrUpdateNcObject(DalCallResult result, EntityObjectVO<UID> ncObject, boolean isNuclon, boolean testOnly) {
		migrationNewNotNullLikeAutoDbSetup(ncObject);
		try {
			if (ncObject.isFlagUpdated()) {
				EntityObjectVO<UID> eoFromCache = getEOOriginal(entity.getUID(), ncObject.getPrimaryKey());
				
				if (eoFromCache != null) {
					if (eoFromCache.equalFields(ncObject, false)) {
						countNotInsertedOrUpdated++;
						return false;
					}
					
					ncObject.setCreatedAt(eoFromCache.getCreatedAt());
					ncObject.setCreatedBy(eoFromCache.getCreatedBy());
					ncObject.setChangedAt(new InternalTimestamp(System.currentTimeMillis()));					
				}
			}
			ncObject.setFieldValue(SF.IMPORTVERSION, ncObject.getVersion());
			IEntityObjectProcessor<UID> proc = NucletDalProvider.getInstance().getEntityObjectProcessor(entity);
			
			if (proc instanceof EntityObjectProcessor) {
				((EntityObjectProcessor<UID>)proc).insertOrUpdateWithOrWithoutForce(ncObject, true /*force insert or update*/);
			} else {
				proc.insertOrUpdate(ncObject);
			}
			
			insertOrUpdateEOInCache(entity.getUID(), ncObject);
		}
		catch (DbException e) {
			result.addRuntimeException(e);
		}
		return true;
	}

	protected void migrationNewNotNullLikeAutoDbSetup(EntityObjectVO<UID> ncObject) {
		EntityMeta<?> eMeta = MetaProvider.getInstance().getEntity(ncObject.getDalEntity());
		for (FieldMeta<?> fMeta : eMeta.getFields()) {
			if (!fMeta.isNullable() && ncObject.getFieldValue(fMeta.getUID()) == null && fMeta.getDefaultMandatory() != null) {
				String defaultValue = fMeta.getDefaultMandatory();
				Class<?> valueClass = fMeta.getJavaClass();
				try {
					Constructor<?> cons = valueClass.getConstructor(String.class);
					Object o = cons.newInstance(defaultValue);
					ncObject.setFieldValue(fMeta.getUID(), o);
				} catch (Exception e) {
					LOG.error(
						"Set of default value '{}' in not-null-attribute '{}.{}' causes an error: {}",
						defaultValue, eMeta.getEntityName(), fMeta.getFieldName(), e.getMessage());
				}
			}
		}
	}

	protected final void storeLocaleResources(EntityObjectVO<UID> ncObject, @SuppressWarnings("unchecked") FieldMeta.Valueable<String>...fields) {
		Map<LocaleInfo, Map<UID, String>> localeResources = new HashMap<LocaleInfo, Map<UID, String>>();
		for (LocaleInfo localeInfo : allLocales) {
			Map<UID, String> resourceMap = new HashMap<UID, String>();
			for (int i = 0; i < fields.length; i++) {
				resourceMap.put(fields[i].getUID(), getLocaleResourceText(localeInfo, ncObject.getFieldValue(fields[i])));
			}
			localeResources.put(localeInfo, resourceMap);
		}
		
		for (FieldMeta.Valueable<String> lrfield : fields) {
			ncObject.removeFieldValue(lrfield.getUID());
		}
		
		ncObject.setFieldValue(LOCALE_RESOURCE_MAPPING_UID, localeResources);
	}

	protected void restoreLocaleResources(EntityObjectVO<UID> ncObject) {
		LocaleFacadeLocal localeFacade = ServerServiceLocator.getInstance().getFacade(LocaleFacadeLocal.class);

		Map<LocaleInfo, Map<UID, String>> localeResources = (Map<LocaleInfo, Map<UID, String>>) ncObject.getFieldValues().get(LOCALE_RESOURCE_MAPPING_UID);
		if (localeResources != null) {
			Map<UID, String> newResourceIds = new HashMap<UID, String>();
			for (LocaleInfo localeInfo : localeResources.keySet()) {
				for (UID resourceField : localeResources.get(localeInfo).keySet()) {
					String text = localeResources.get(localeInfo).get(resourceField);
					if (text != null) {
						boolean forceNewCreation = false;
						if (ncObject.isFlagUpdated()) {
							EntityObjectVO<UID> existingObject = getEOOriginal(entity.getUID(), ncObject.getPrimaryKey());
							if (existingObject == null) {
								// wegen git merge problemen vermutlich doppelt vorhanden...
								LOG.info("Flag for update, but not found. entity=" + entity.getEntityName() + ", pk=" + ncObject.getPrimaryKey());
								forceNewCreation = true;
							}
						}
						if (ncObject.isFlagNew() || forceNewCreation) {
							// create new resource
							String resourceId = newResourceIds.get(resourceField);
							resourceId = localeFacade.insert(resourceId, localeInfo, text);
							insertOrUpdateResoureTextInCache(localeInfo, resourceId, text);
							newResourceIds.put(resourceField, resourceId);
							ncObject.setFieldValue(resourceField, resourceId);
						} else if (ncObject.isFlagUpdated()) {
							// read resourceid from existing object and update it
//							EntityObjectVO<UID> existingObject = NucletDalProvider.getInstance().getEntityObjectProcessor(entity).getByPrimaryKey(ncObject.getPrimaryKey());
							EntityObjectVO<UID> existingObject = getEOOriginal(entity.getUID(), ncObject.getPrimaryKey());
							String resourceId = existingObject.getFieldValue(resourceField, String.class);
							if (resourceId != null) {
								//if (localeFacade.getResourceById(localeInfo, resourceId) == null) {
								String existingText = getLocaleResourceText(localeInfo, resourceId); 
								if (existingText == null) { 
									localeFacade.insert(resourceId, localeInfo, text);
								} else {
									if (!RigidUtils.equal(text, existingText)) {
										localeFacade.update(resourceId, localeInfo, text);
									}
								}
								insertOrUpdateResoureTextInCache(localeInfo, resourceId, text);
							} else {
								resourceId = newResourceIds.get(resourceField);
								resourceId = localeFacade.insert(resourceId, localeInfo, text);
								insertOrUpdateResoureTextInCache(localeInfo, resourceId, text);
								newResourceIds.put(resourceField, resourceId);
							}
							ncObject.setFieldValue(resourceField, resourceId);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean validate(TransferEO teo, ValidationType type, NucletContentMap importContentMap, 
			Set<UID> existingNucletUIDs, ValidityLogEntry validitylog, Map<TransferOption, Serializable> transferOptions, boolean hasDataLanguages) {
		if (teo.existingVersion == null) {
			//teo.existingVersion = NucletDalProvider.getInstance().getEntityObjectProcessor(getEntity()).getVersion(teo.getUID());
			EntityObjectVO<UID> eoOriginal = getEOOriginal(getEntity().getUID(), teo.getUID());
			if (eoOriginal != null) {
				teo.existingVersion = eoOriginal.getVersion();
			}
		}

		switch (type) {
		case INSERT:
			if (teo.existingVersion != null) {
				return false;
			}
			if (TransferUtils.getEntityObject(getEntity(), teo.getUID()) == null) {
				return true;
			}
			return false;
		case UPDATE:
			// INSERT is checked before UPDATE. An other read is not necessary...
			return true;
		case DELETE:				
			EntityObjectVO<UID> existingEO = teo.eo;
			UID existingUID = existingEO.getPrimaryKey();
			debug("existing eo: " + getIdentifier(existingEO, importContentMap) + " uid=" + existingUID.getString());
			
			boolean result = true;

			// check if in use by user definded entity
			for (FieldMeta<UID> efMeta : TransferUtils.getUserEntityFields(TransferUtils.getFieldDependencies(entity))) {
				if (TransferUtils.existsReference(efMeta, existingUID)) {
					debug("user defined field is referenceing on " + efMeta.getEntity() + " field=" + efMeta);
					validitylog.newReferenceFound(efMeta.getUID(), getIdentifier(existingEO, importContentMap));
					result = false;
				}
			}

			return result;
		default: 
			return true;
		}
	}
	
	private static List<EntityObjectVO<UID>> getAllEOs(UID entityUID) {
		List<EntityObjectVO<UID>> result = new ArrayList<EntityObjectVO<UID>>(getEOCache(entityUID).values());
		countRead += result.size();
		return result;
	}
	
	protected static EntityObjectVO<UID> getEO(UID entityUID, UID pk) {
		countRead++;
		return getEOCache(entityUID).get(pk);
	}
	
	protected static EntityObjectVO<UID> getEOOriginal(UID entityUID, UID pk) {
		countRead++;
		return getEOOriginalCache(entityUID).get(pk);
	}
	
	
	private static EntityObjectVO<UID> clone(EntityObjectVO<UID> eo) {
		EntityObjectVO<UID> result = eo.copy();
		result.setPrimaryKey(eo.getPrimaryKey());
		result.setVersion(eo.getVersion());
		result.setCreatedAt(eo.getCreatedAt());
		result.setCreatedBy(eo.getCreatedBy());
		result.setChangedAt(eo.getChangedAt());
		result.setChangedBy(eo.getChangedBy());
		return result;
	}
	
	private static void deleteEOFromCache(UID entityUID, UID pk) {
		Map<UID, EntityObjectVO<UID>> eoCache = getEOCache(entityUID);
		eoCache.remove(pk);
		countDeleted++;
	}
	
	private static void insertOrUpdateEOInCache(UID entityUID, EntityObjectVO<UID> eo) {
		Map<UID, EntityObjectVO<UID>> eoCache = getEOCache(entityUID);
		eoCache.put(eo.getPrimaryKey(), eo);
		countInsertedOrUpdated++;
	}
	
	private static Map<UID, EntityObjectVO<UID>> getEOCache(UID entityUID) {
		if (eoCaches == null) {
			throw new NuclosFatalException("Caches not filled!");
		}
		Map<UID, EntityObjectVO<UID>> eoCache = eoCaches.get(entityUID);
		if (eoCache == null) {
			throw new NuclosFatalException("Entity not cached!");
		}
		return eoCache;
	}
	
	private static Map<UID, EntityObjectVO<UID>> getEOOriginalCache(UID entityUID) {
		if (eoOriginalCaches == null) {
			throw new NuclosFatalException("Caches not filled!");
		}
		Map<UID, EntityObjectVO<UID>> eoCache = eoOriginalCaches.get(entityUID);
		if (eoCache == null) {
			throw new NuclosFatalException("Entity not cached!");
		}
		return eoCache;
	}
	
	private static void insertOrUpdateResoureTextInCache(LocaleInfo localeInfo, String resid, String text) {
		if (localeResourceCache == null) {
			throw new NuclosFatalException("Caches not filled!");
		}
		final String locale = localeInfo.getLanguage();
		Map<String, String> localeCache = localeResourceCache.get(locale);
		if (localeCache != null) {
			localeCache.put(resid, text);
		}
	}
	
	private static String getLocaleResourceText(LocaleInfo localeInfo, String resid) {
		if (localeResourceCache == null) {
			throw new NuclosFatalException("Caches not filled!");
		}
		final String locale = localeInfo.getLanguage();
		Map<String, String> localeCache = localeResourceCache.get(locale);
		if (localeCache != null) {
			return localeCache.get(resid);
		}
		
		return null;
	}

	public static synchronized void fillCaches(List<INucletContent> contentTypes, Logger log) {
		int count = 0;
		
		// locale resources
		allLocales = ServerServiceLocator.getInstance().getFacade(LocaleFacadeLocal.class).getAllLocales(false);
		localeResourceCache = new HashMap<String, Map<String,String>>();
		List<EntityObjectVO<UID>> all = NucletDalProvider.getInstance().getEntityObjectProcessor(E.LOCALERESOURCE).getAll();		
		for (EntityObjectVO<UID> lres : all) {
			final String locale = lres.getFieldValue(E.LOCALERESOURCE.locale);
			final String resid = lres.getFieldValue(E.LOCALERESOURCE.resourceID);
			final String text = lres.getFieldValue(E.LOCALERESOURCE.text);
			Map<String, String> localeCache = localeResourceCache.get(locale);
			if (localeCache == null) {
				localeCache = new HashMap<String, String>();
				localeResourceCache.put(locale, localeCache);
				count++;
			}
			localeCache.put(resid, text);
		}

		// eo
		eoOriginalCaches = new HashMap<UID, Map<UID, EntityObjectVO<UID>>>();
		eoCaches = new HashMap<UID, Map<UID, EntityObjectVO<UID>>>(); 
		for (INucletContent nc : contentTypes) {
			Map<UID, EntityObjectVO<UID>> eoOriginalCache = new HashMap<UID, EntityObjectVO<UID>>();
			eoOriginalCaches.put(nc.getEntity().getUID(), eoOriginalCache);
			Map<UID, EntityObjectVO<UID>> eoCache = new HashMap<UID, EntityObjectVO<UID>>();
			eoCaches.put(nc.getEntity().getUID(), eoCache);
			
			final Collection<EntityObjectVO<UID>> eos;
			if (nc.cachingCondition() == null) {
				eos = NucletDalProvider.getInstance().getEntityObjectProcessor(nc.getEntity()).getAll();
			} else {
				eos = NucletDalProvider.getInstance().getEntityObjectProcessor(nc.getEntity()).getBySearchExpression(new CollectableSearchExpression(nc.cachingCondition()));
			}			
			for (EntityObjectVO<UID> eo : eos) {
				eoOriginalCache.put(eo.getPrimaryKey(), eo);
				eo = nc.readNc(clone(eo));
				eoCache.put(eo.getPrimaryKey(), eo);
				count++;
			}
		}
				
		log.info(count + " objects temporary cached");
	}
	
	public static synchronized void clearCaches(Logger log) {
		eoCaches = null;
		eoOriginalCaches = null;
		localeResourceCache = null;
		allLocales = null;
		
		log.info("Temporary cache usage: read=" + countRead + 
				", deleted=" + countDeleted +
				", changed=" + countInsertedOrUpdated +  
				", changesSkipped=" + countNotInsertedOrUpdated);
		countRead = 0;
		countInsertedOrUpdated = 0;
		countNotInsertedOrUpdated = 0;
		countDeleted = 0;
	}

	@Override
	public boolean isIgnoreReferenceToNuclet() {
		return ignoreReferenceToNuclet;
	}

	@Override
	public String toString() {
		return entity.getEntityName();
	}
	
	@Override
	public FieldMeta<UID> getFieldToParent() {
		return fieldToParent;
	}

	protected void debug(Object o) {
		LOG.debug("{}", o);
	}

	protected void info(Object o) {
		LOG.info("{}", o);
	}

	protected void warn(Object o) {
		LOG.warn("{}", o);
	}

	protected void error(Object o) {
		LOG.error("{}", o);
	}
	
	protected void error(Object o, Throwable t) {
		LOG.error("{}", o, t);
	}

}
