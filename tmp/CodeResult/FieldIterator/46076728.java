package org.nuclos.server.nbo;

import static org.nuclos.common.NuclosEntityValidator.escapeJavaIdentifier;
import static org.nuclos.common.NuclosEntityValidator.escapeJavaIdentifierPart;
import static org.nuclos.common.NuclosEntityValidator.getGetterName;
import static org.nuclos.common.NuclosEntityValidator.getSetterName;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nuclos.api.businessobject.BusinessObject;
import org.nuclos.api.businessobject.GenericBusinessObject;
import org.nuclos.api.businessobject.Query;
import org.nuclos.api.businessobject.facade.Lockable;
import org.nuclos.api.businessobject.facade.LogicalDeletable;
import org.nuclos.api.businessobject.facade.Modifiable;
import org.nuclos.api.common.NuclosFile;
import org.nuclos.api.common.NuclosFileBase;
import org.nuclos.api.common.NuclosUser;
import org.nuclos.api.exception.BusinessException;
import org.nuclos.api.locale.NuclosLocale;
import org.nuclos.api.provider.QueryProvider;
import org.nuclos.businessentity.NucletIntegrationField;
import org.nuclos.businessentity.NucletIntegrationPoint;
import org.nuclos.businessentity.utils.BusinessObjectBuilderForInternalUse;
import org.nuclos.common.CommonMetaDataServerProvider;
import org.nuclos.common.E;
import org.nuclos.common.EntityMeta;
import org.nuclos.common.FieldMeta;
import org.nuclos.common.NucletEntityMeta;
import org.nuclos.common.NuclosBusinessObjectImport;
import org.nuclos.common.NuclosDateTime;
import org.nuclos.common.NuclosEntityValidator;
import org.nuclos.common.NuclosPassword;
import org.nuclos.common.RigidUtils;
import org.nuclos.common.SF;
import org.nuclos.common.StaticMetaDataProvider;
import org.nuclos.common.UID;
import org.nuclos.common.collection.multimap.MultiListHashMap;
import org.nuclos.common.collection.multimap.MultiListMap;
import org.nuclos.common.dal.vo.EntityObjectVO;
import org.nuclos.common.dblayer.IFieldUIDRef;
import org.nuclos.common2.ForeignEntityFieldUIDParser;
import org.nuclos.common2.InternalTimestamp;
import org.nuclos.common2.LangUtils;
import org.nuclos.common2.StringUtils;
import org.nuclos.common2.exception.CommonBusinessException;
import org.nuclos.common2.exception.NuclosCompileException;
import org.nuclos.server.common.INucletCache;
import org.nuclos.server.common.MetaProvider;
import org.nuclos.server.customcode.codegenerator.NuclosCodegeneratorConstants;
import org.nuclos.server.dal.provider.NucletDalProvider;
import org.nuclos.server.eventsupport.valueobject.ProcessVO;
import org.nuclos.server.genericobject.valueobject.GenericObjectDocumentFile;
import org.nuclos.server.masterdata.MasterDataWrapper;
import org.nuclos.server.masterdata.ejb3.MasterDataFacadeLocal;
import org.nuclos.server.nbo.AbstractNuclosObjectCompiler.NuclosBusinessJavaSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("nuclosBusinessObjectBuilder")
public class NuclosBusinessObjectBuilder extends NuclosObjectBuilder {

	public static final String DEPENDENT_PREFIX = "_";

	public static final String DEFFAULT_PACKAGE_NUCLET = "org.nuclet.businessentity";
	public static final String DEFFAULT_PACKAGE_NUCLOS = "org.nuclos.businessentity";

	private static final String DEFAULT_ENTITY_PREFIX = "BO";
	private static final String DEFAULT_FIELD_PREFIX = "_";
	private static final String DEFAULT_PROCESS_PREFIX = "P";

	private static final String COMMENT_COMPARE_METHOD = "/**\n * This method compares the current BusinessObject with an other BusinessObject.\n" +
	                                                     " * If all fields, all references (excluding system fields) and all dependents are equal, the method returns true\n**/";

	private static final String COMMENT_COPY_METHOD  = "/**\n * This method creates a copy of the current BusinessObject\n * and resets it by removing the primary key and setting the state flag to 'new'\n**/";

	@Autowired
	private MasterDataFacadeLocal masterDataFacade;

	@Autowired
	private NuclosBusinessObjectCompiler nuclosBusinessObjectCompiler;

	@Autowired
	private MetaProvider defaultMetaProvider;

	private BuilderMetaProvider metaProvider;

	@Autowired
	private NucletDalProvider nucletDalProvider;

	public static class BuilderMetaProvider extends StaticMetaDataProvider implements CommonMetaDataServerProvider, INucletCache {

		private final WeakReference<MetaProvider> defaultMetaProvider;

		private final Map<UID, Boolean> preventQueryAttributes = new HashMap<>();

		public BuilderMetaProvider(final MetaProvider defaultMetaProvider) {
			super(E.getThis());
			this.defaultMetaProvider = new WeakReference<MetaProvider>(defaultMetaProvider);
			for (EntityMeta<?> eMeta : defaultMetaProvider.getAllEntities()) {
				if (E.isNuclosEntity(eMeta.getUID())) {
					add(eMeta, true);
				} else {
					addClone(eMeta, true);
				}
			}

			final Query<NucletIntegrationPoint> qPoints = QueryProvider.create(NucletIntegrationPoint.class);
			qPoints.where(NucletIntegrationPoint.TargetEntityId.notNull());
			final List<NucletIntegrationPoint> integrationPoints = QueryProvider.execute(qPoints);
			for (NucletIntegrationPoint point : integrationPoints) {
				add(new IntegrationPointEntityMeta(point), true);
			}
		}

		@Override
		public Collection<EntityMeta<?>> getAllLanguageEntities() {
			return defaultMetaProvider.get().getAllLanguageEntities();
		}

		@Override
		public List<EntityObjectVO<UID>> getNuclets() {
			return defaultMetaProvider.get().getNuclets();
		}

		@Override
		public String getFullQualifiedNucletName(final UID nucletUID) {
			return defaultMetaProvider.get().getFullQualifiedNucletName(nucletUID);
		}

		@Override
		public List<String> getPossibleIdFactories() {
			return defaultMetaProvider.get().getPossibleIdFactories();
		}

		@Override
		public List<EntityObjectVO<UID>> getEntityMenus() {
			return defaultMetaProvider.get().getEntityMenus();
		}

		@Override
		public FieldMeta<?> getCalcAttributeCustomization(final UID fieldUID, final String paramValues) {
			return defaultMetaProvider.get().getCalcAttributeCustomization(fieldUID, paramValues);
		}

		@Override
		public Set<UID> getImplementingEntities(final UID genericEntityUID) {
			return defaultMetaProvider.get().getImplementingEntities(genericEntityUID);
		}

		@Override
		public List<EntityObjectVO<UID>> getImplementingEntityDetails(final UID genericEntityUID) {
			return defaultMetaProvider.get().getImplementingEntityDetails(genericEntityUID);
		}

		@Override
		public List<EntityObjectVO<UID>> getImplementingFieldMapping(final UID genericImplementationUID) {
			return defaultMetaProvider.get().getImplementingFieldMapping(genericImplementationUID);
		}
	}

	/**
	 * This method extracts a list of all user and system entities for which
	 * Nuclos Business Objects (NBOs) must be created. Elements in the list are
	 * compiled and stored in the Classpath
	 * 
	 * @throws CommonBusinessException
	 */
	@Override
	public void createObjects() throws CommonBusinessException, InterruptedException {
		metaProvider = new BuilderMetaProvider(defaultMetaProvider);
		// List of relevant user/system entities
		List<EntityMeta<?>> entitiesToCreate = getEntitiesToCreate();
		// create NBO (Nuclos Business Objects) mapping structure
		List<NuclosBusinessObjectMetaData> boMetaDatas = mapNuclosBusinessObject(entitiesToCreate);
		// Save, compile and jar NBOs
		compileNuclosBusinessObject(boMetaDatas);
	}
	
	public static String getProxyInterfaceName(EntityMeta<?> emdVO) {
		return getProxyInterfaceName(emdVO.getEntityName());
	}
	
	public static String getProxyInterfaceName(String entityName) {
		String formatEntity = escapeJavaIdentifier(
				entityName,
				DEFAULT_ENTITY_PREFIX
		);
		String formatEntityProxy = formatEntity + "Proxy";
		return formatEntityProxy;
	}
	
	public static String getMandatorProviderInterfaceName(EntityMeta<?> emdVO) {
		return getMandatorProviderInterfaceName(emdVO.getEntityName());
	}
	
	public static String getMandatorProviderInterfaceName(String entityName) {
		String formatEntity = escapeJavaIdentifier(
				entityName,
				DEFAULT_ENTITY_PREFIX
		);
		String formatEntityMandatorProvider = formatEntity + ".NuclosMandatorProvider";
		return formatEntityMandatorProvider;
	}

	public static String getFormattedEntityName(EntityMeta eMeta, boolean bForSystemRulesOnly) {
		boolean adjustInternalName = false;
		if (eMeta.isUidEntity() && bForSystemRulesOnly) {
			// default is something like this 'nuclosnucletIntegrationPoint'. This sucks.
			String sEntityName = eMeta.getBusinessObjectClassName();
			if (sEntityName.startsWith("nuclos")) {
				sEntityName = sEntityName.substring(6);
			}
			if (sEntityName.startsWith("_")) {
				sEntityName = sEntityName.substring(1);
			}
			sEntityName = StringUtils.capitalized(sEntityName);
			return sEntityName;
		}
		return eMeta.getEntityName();
	}

	/**
	 * This method creates a map with meta information of all entities that are
	 * passed as arguments into the method.
	 * 
	 * @param entityMetas
	 * @return List<NuclosBusinessMetaObject>
	 */
	private List<NuclosBusinessObjectMetaData> mapNuclosBusinessObject(
			List<EntityMeta<?>> entityMetas) throws InterruptedException {

		List<NuclosBusinessObjectMetaData> retVal = new ArrayList<>();

		DependentBuilderHelper dependentBuilderHelper = new DependentBuilderHelper();
		
		MultiListMap<UID, EntityObjectVO<UID>> entityProcessMap =
			new MultiListHashMap<>(entityMetas.size());
		List<EntityObjectVO<UID>> allProcesses =
			nucletDalProvider.getEntityObjectProcessor(E.PROCESS).getAll();
		for (EntityObjectVO<UID> process : allProcesses) {
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
			entityProcessMap.addValue(process.getFieldUid(E.PROCESS.module), process);
		}
		
		for (EntityMeta emdVO : entityMetas) {
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
								
			final boolean proxy = emdVO.isProxy();
			final boolean writeProxy = emdVO.isWriteProxy();
			final boolean generic = emdVO.isGeneric();
			
			String sPackage = getNucletPackageStatic(emdVO, metaProvider);
			final boolean bForInternalUseOnly = emdVO.isUidEntity() && BusinessObjectBuilderForInternalUse.getEntityMetas().contains(emdVO);
			final String formatEntity = getNameForFqn(getFormattedEntityName(emdVO, bForInternalUseOnly));

			if (proxy) {
				String formatEntityProxy = getProxyInterfaceName(emdVO);
				NuclosBusinessObjectMetaData newMetaProxy = new NuclosBusinessObjectMetaData(
						emdVO.getEntityName(), getUIDforClass(emdVO), sPackage, formatEntityProxy, true,
						emdVO.isMandator(), getBoHeader(), getBOJavadoc(emdVO));
				
				// Add default package to path
				newMetaProxy.addImportPackageStar(DEFFAULT_PACKAGE_NUCLOS + ".*");
				newMetaProxy.addImport(NuclosBusinessObjectImport.UID);
				
				// Attributes and Methods and References
				mapNuclosBusinessObjectProxy(newMetaProxy, emdVO);

				retVal.add(newMetaProxy);
			}
			if(writeProxy)
			{
				String formatEntityProxy = getProxyInterfaceName(emdVO);
				NuclosBusinessObjectMetaData newMetaProxy = new NuclosBusinessObjectMetaData(
						emdVO.getEntityName(), getUIDforClass(emdVO), sPackage, formatEntityProxy, true,
						emdVO.isMandator(), getBoHeader(), getBOJavadoc(emdVO));
				
				// Add default package to path
				newMetaProxy.addImportPackageStar(DEFFAULT_PACKAGE_NUCLOS + ".*");
				newMetaProxy.addImport(NuclosBusinessObjectImport.UID);
				
				// Attributes and Methods and References
				mapNuclosBusinessObjectWriteProxy(newMetaProxy, emdVO);

				retVal.add(newMetaProxy);
				
			}
			final NuclosBusinessObjectMetaData newMeta = new NuclosBusinessObjectMetaData(
					emdVO.getEntityName(), getUIDforClass(emdVO), sPackage, formatEntity, false,
					emdVO.isMandator(), getBoHeader(),getBOJavadoc(emdVO));
			// Classname and package

			// Add default package to path
			newMeta.addImportPackageStar(DEFFAULT_PACKAGE_NUCLOS + ".*");
			
			// Add BOAttribute for Query issues
			if (!emdVO.isProxy() && !generic) {
				newMeta.addImport(NuclosBusinessObjectImport.ATTRIBUTE);
				newMeta.addImport(NuclosBusinessObjectImport.PRIMARY_KEY_ATTRIBUTE);
				newMeta.addImport(NuclosBusinessObjectImport.NUMERIC_ATTRIBUTE);
				newMeta.addImport(NuclosBusinessObjectImport.STRING_ATTRIBUTE);
				newMeta.addImport(NuclosBusinessObjectImport.DEPENDENT);
				newMeta.addImport(NuclosBusinessObjectImport.FLAG);
			}
			if (generic) {
				newMeta.addImport(NuclosBusinessObjectImport.DEPENDENT);
				newMeta.addImport(NuclosBusinessObjectImport.FLAG);
			} else {
				newMeta.addImport(NuclosBusinessObjectImport.FOREIGN_KEY_ATTRIBUTE);				
			}
			newMeta.addImport(NuclosBusinessObjectImport.UID);

			// Extend
			if (!generic) {
				newMeta.setExtend(AbstractBusinessObject.class.getSimpleName(),
						AbstractBusinessObject.class.getPackage().getName());
			}
			
			// Interface
			if (!emdVO.isUidEntity()) {
				if (emdVO.isStateModel()) {
					if (emdVO.isThin()) {
						newMeta.addInterface(org.nuclos.api.businessobject.facade.thin.Stateful.class.getSimpleName(),
								org.nuclos.api.businessobject.facade.thin.Stateful.class.getPackage().getName());
					} else {
						newMeta.addInterface(org.nuclos.api.businessobject.facade.Stateful.class.getSimpleName(),
								org.nuclos.api.businessobject.facade.Stateful.class.getPackage().getName());
					}
					newMeta.addInterface(LogicalDeletable.class.getSimpleName(),
							LogicalDeletable.class.getPackage().getName());
				}
				if (generic) {
					newMeta.addInterface(GenericBusinessObject.class.getSimpleName(),
							GenericBusinessObject.class.getPackage().getName());
				} else {
					//if (!emdVO.isReadonly()) { // NUCLOS-6196
						newMeta.addInterface(Modifiable.class.getSimpleName(),
								Modifiable.class.getPackage().getName());
					//}
				}
				if (emdVO.isOwner()) {
					newMeta.addInterface(Lockable.class.getSimpleName(),
							Lockable.class.getPackage().getName());
				}
			}
			else {
				if (E.USER.getUID().equals(emdVO.getUID())) {
					newMeta.addInterface(NuclosUser.class.getSimpleName(),
							NuclosUser.class.getPackage().getName());
				}
				if (BusinessObjectBuilderForInternalUse.getEntityMetas().contains(emdVO)) {
					newMeta.addInterface(Modifiable.class.getSimpleName(),
							Modifiable.class.getPackage().getName());
				}
			}

			if (emdVO.isUidEntity()) {
				newMeta.setSerialVersionUID(1L);
			}

			// Variables
			if (generic) {
				newMeta.addAttribute(new NuclosBusinessObjectAttributeMetaData("businessObject", null,
						BusinessObject.class.getCanonicalName() + "<Long>", null));
			}

			// Constructors
			if (generic) {
				new AbstractGenericImplementationBuilder(emdVO) {
					@Override
					public void build(final String implEntityFQN, final String implFieldName, final FieldMeta<?> implFieldMeta) {
						final NuclosBusinessObjectMethodMetaData constructorMethod = new NuclosBusinessObjectMethodMetaData(formatEntity, AbstractNuclosBusinessObjectMetaData.PUBLIC, null, false, null);
						constructorMethod.addParameter(new NuclosBusinessObjectAttributeMetaData("businessObject", null, implEntityFQN, null));
						constructorMethod.setMethodBody("this.businessObject = businessObject;");
						newMeta.addMethod(constructorMethod);
					}
				}.run();
				final List<EntityObjectVO<UID>> implementingEntityDetails = metaProvider.getImplementingEntityDetails(emdVO.getUID());
				for (EntityObjectVO<UID> implDetail : implementingEntityDetails) {
					final UID implementingEntityUID = implDetail.getFieldUid(E.ENTITY_GENERIC_IMPLEMENTATION.implementingEntity);
					final EntityMeta<?> implementingEntityMeta = metaProvider.getEntity(implementingEntityUID);
					final String implementingEntityPackage = getNucletPackageStatic(implementingEntityMeta, metaProvider);
					final String implementingEntityFormattedName = getNameForFqn(implementingEntityMeta.getEntityName());
					final String implDataType = implementingEntityPackage + "." + implementingEntityFormattedName;


				}
			}

			// Attributes and Methods and References
			mapNuclosBusinessObject(newMeta, emdVO, dependentBuilderHelper);

			// Processes for this entity
			if (!emdVO.isUidEntity() && !generic) {
				mapNuclosBusinessObjectProcesses(formatEntity, newMeta, (EntityMeta<Long>) emdVO, entityProcessMap);
			}

			// common utility methods, e.g. compare, copy
			mapNuclosBusinessObjectCommon(newMeta, emdVO);
			
			retVal.add(newMeta);
		}

		return retVal;
	}

	private void mapNuclosBusinessObjectCommon(
			NuclosBusinessObjectMetaData newMeta, EntityMeta emdVO) {
		
		if (!emdVO.isUidEntity() && !emdVO.isGeneric()) {
			
			String sPackage = getNucletPackageStatic(emdVO, metaProvider);
			String formatEntity = getNameForFqn(emdVO.getEntityName());
			
			// compare
			NuclosBusinessObjectMethodMetaData compareMethod = 
					new NuclosBusinessObjectMethodMetaData("compare", NuclosBusinessObjectMethodMetaData.PUBLIC, 
					"boolean", false, COMMENT_COMPARE_METHOD);
			
			NuclosBusinessObjectAttributeMetaData boTOCompareWith =
					new NuclosBusinessObjectAttributeMetaData("boToCompareWith", null, 
							sPackage + "." + formatEntity, null);			
			compareMethod.addParameter(boTOCompareWith);
			compareMethod.setMethodBody("\treturn super.compare(boToCompareWith);\n");
			
			newMeta.addMethod(compareMethod);
			
			// copy
			NuclosBusinessObjectMethodMetaData copyMethod = 
					new NuclosBusinessObjectMethodMetaData("copy", NuclosBusinessObjectMethodMetaData.PUBLIC, 
				    sPackage + "." + formatEntity, false, COMMENT_COPY_METHOD);
			
			copyMethod.setMethodBody("\treturn super.copy(" + sPackage + "." + formatEntity + ".class);\n");
			
			newMeta.addMethod(copyMethod);
		}
	}

	public String getUIDStringforClass(EntityMeta<?> eMeta) {
		return getUIDforClass(eMeta).getString();
	}

	public UID getUIDforClass(EntityMeta<?> eMeta) {
		if (eMeta.isIntegrationPoint()) {
			// shift to target entity
			IntegrationPointEntityMeta iMeta = (IntegrationPointEntityMeta) eMeta;
			return (UID)iMeta.getIntegrationPoint().getTargetEntityId();
		}
		return eMeta.getUID();
	}

	public String getUIDStringforClass(FieldMeta<?> fMeta) {
		return getUIDforClass(fMeta).getString();
	}

	public UID getUIDforClass(FieldMeta<?> fMeta) {
		if (fMeta.isIntegrationField()) {
			// shift to target field
			IntegrationPointEntityMeta.IntegrationFieldMeta iMeta = (IntegrationPointEntityMeta.IntegrationFieldMeta) fMeta;
			UID targetFieldId = (UID)iMeta.getIntegrationField().getTargetFieldId();
			// during integration, target could be empty. return only if is set
			if (targetFieldId != null) {
				return targetFieldId;
			}
		}
		return fMeta.getUID();
	}

	public static String getNameForFqn(String sName) {
		return escapeJavaIdentifier(sName, DEFAULT_ENTITY_PREFIX);
	}
	
	public static String getFieldNameForFqn(FieldMeta<?> field) {
		return escapeJavaIdentifier(adjustName(field), DEFAULT_FIELD_PREFIX);
	}

	/**
	 * Extracts all meta information for fields and fieldIds of the current
	 * entity and converts it into an executable JavaCode
	 * 
	 * @param emdVO The entity
	 * @return
	 */
	private void mapNuclosBusinessObject(
		NuclosBusinessObjectMetaData newMeta,
		EntityMeta<?> emdVO,
		DependentBuilderHelper dependentBuilderHelper) throws InterruptedException {

		// Get field information for given entity
		Collection<FieldMeta<?>> values = metaProvider.getAllEntityFieldsByEntity(emdVO.getUID())
				.values();

		// Methods for fields,fieldIds and referenced elements
		mapNuclosBusinessObjectFields(newMeta, emdVO, values);
		// Methods for dependants
		if (!E.isNuclosEntity(emdVO.getUID()) || BusinessObjectBuilderForInternalUse.getEntityMetas().contains(emdVO)) {
			mapNuclosBusinessObjectDependants(newMeta, emdVO, dependentBuilderHelper);
		}
	}
	
	private void mapNuclosBusinessObjectProcesses(
		String FormattedEntityName,
		NuclosBusinessObjectMetaData newMeta,
		EntityMeta<Long> emdVO,
		MultiListMap<UID, EntityObjectVO<UID>> entityProcessMap) throws InterruptedException {
		
		// get all processes for this entity
		List<EntityObjectVO<UID>> processes = entityProcessMap.getValues(emdVO.getUID());
		
		if (processes != null && processes.size() > 0) {
			
			newMeta.addImport(NuclosBusinessObjectImport.PROCESS);
			
			for (EntityObjectVO<UID> eo : processes) {
				if (Thread.currentThread().isInterrupted()) {
					throw new InterruptedException();
				}
				ProcessVO process = MasterDataWrapper.getProcessVO(eo);
				newMeta.addProcessConstant(new NuclosBusinessObjectProcess(
						getProcessNameForFqn(process.getName()), process.getId(), FormattedEntityName, process.getModule()));
			}
		}
	
	}
	
	public static String getProcessNameForFqn(String sName) {
		return escapeJavaIdentifier(sName, DEFAULT_PROCESS_PREFIX);
	}

	/**
	 * Extracts all meta information for proxy interface methods of the current
	 * entity and converts it into an executable JavaCode
	 * 
	 * @param emdVO The entity
	 * @return
	 */
	private void mapNuclosBusinessObjectProxy(NuclosBusinessObjectMetaData newMetaProxy,
			EntityMeta<?> emdVO) throws InterruptedException {
		// Get field information for given entity
		Collection<FieldMeta<?>> values = metaProvider.getAllEntityFieldsByEntity(emdVO.getUID())
				.values();

		// Methods for referenced elements
		mapNuclosBusinessObjectProxyMethods(newMetaProxy, emdVO, values);
	}
	/**
	 * Extracts all meta information for write-proxy interface methods of the current
	 * entity and converts it into an executable JavaCode
	 * 
	 * @param emdVO The entity
	 * @return
	 */
	private void mapNuclosBusinessObjectWriteProxy(NuclosBusinessObjectMetaData newMetaProxy,
			EntityMeta<?> emdVO) throws InterruptedException {
		// Get field information for given entity
		Collection<FieldMeta<?>> values = metaProvider.getAllEntityFieldsByEntity(emdVO.getUID())
				.values();

		// Methods for referenced elements
		mapNuclosBusinessObjectWriteProxyMethods(newMetaProxy, emdVO, values);
	}

	/**
	 * Checks all entities if there are references on the main entity
	 * 
	 * @param entity
	 * @return
	 */
	private void mapNuclosBusinessObjectDependants(
			NuclosBusinessObjectMetaData newMeta, EntityMeta<?> entity, 
			final DependentBuilderHelper dependentBuilderHelper)
		throws InterruptedException {

		final boolean generic = entity.isGeneric();

		newMeta.addImport(NuclosBusinessObjectImport.LIST);
		newMeta.addImport(NuclosBusinessObjectImport.ARRAY_LIST);
		newMeta.addImport(NuclosBusinessObjectImport.HASH_SET);

	 	final MultiListMap<String, FieldMeta<?>> dependentMethodNameMap = dependentBuilderHelper.getDependentMethodNameMap(entity.getUID());

		for (String dependentMethodName : dependentMethodNameMap.keySet()) {
			final List<FieldMeta<?>> dependentRefFieldMetaList = dependentMethodNameMap.getValues(dependentMethodName);
			final boolean multipleReferences = dependentRefFieldMetaList.size() > 1;
			for (int idx = 0; idx < dependentRefFieldMetaList.size(); idx++) {
				final FieldMeta<?> refFieldMeta = dependentRefFieldMetaList.get(idx);

				final EntityMeta<?> dependentEntityMeta = metaProvider.getEntity(refFieldMeta.getEntity());
				String dependentMethodNameWithPackage = getNucletPackageStatic(dependentEntityMeta, metaProvider) + "." + dependentMethodName;
				String dependenConstantName =  DEPENDENT_PREFIX + formatMethodName(dependentMethodName);
				if (multipleReferences) {
					dependenConstantName = dependenConstantName + (idx+1);
				}

				// Attribute for dependents
				if (!entity.isProxy() && !generic) {
					NuclosBusinessObjectDepConstantMetaData depConst = new NuclosBusinessObjectDepConstantMetaData(
							dependenConstantName,
							dependentEntityMeta.getEntityName(), getUIDforClass(dependentEntityMeta),
							refFieldMeta.getFieldName(), getUIDforClass(refFieldMeta), dependentMethodNameWithPackage);
					newMeta.addQueryConstant(depConst);
				}

				// GetAllMethod for dependents
				final String dependentGetterMethodName = dependentBuilderHelper.createCompleteDependentMethodName("get", dependentMethodName, multipleReferences, idx);
				NuclosBusinessObjectMethodMetaData dataGet = new NuclosBusinessObjectMethodMetaData(
						dependentGetterMethodName,
						NuclosBusinessObjectMethodMetaData.PUBLIC,
						"List<" + dependentMethodNameWithPackage + ">", false,
						getBOMethodJavadoc(refFieldMeta, MethodType.GETTER));
				NuclosBusinessObjectAttributeMetaData depPara = new NuclosBusinessObjectAttributeMetaData(
						"flags",
						NuclosBusinessObjectAttributeMetaData.PUBLIC,
						"Flag...", null);
				dataGet.addParameter(depPara);
				if (generic) {
					dataGet.setMethodBody(getGenericDependentGetterMethodBody(dependentEntityMeta, refFieldMeta, dependentBuilderHelper));
				} else {
					dataGet.setMethodBody("\treturn getDependents(" + dependenConstantName + ", flags); ");
				}
				newMeta.addMethod(dataGet);

				final String paramName = "p" + formatMethodName(dependentMethodName);

				// Add - Referenced Field
				final String dependentInsertMethodName = dependentBuilderHelper.createCompleteDependentMethodName("insert", dependentMethodName, multipleReferences, idx);
				NuclosBusinessObjectMethodMetaData dataAdd = new NuclosBusinessObjectMethodMetaData(
						dependentInsertMethodName,
						NuclosBusinessObjectMethodMetaData.PUBLIC,
						NuclosBusinessObjectMethodMetaData.VOID, false,
						getBOMethodJavadoc(refFieldMeta, MethodType.INSERT));
				dataAdd.addParameter(new NuclosBusinessObjectAttributeMetaData(
						paramName, null, dependentMethodNameWithPackage, null));
				if (generic) {
					dataAdd.setMethodBody(getGenericDependentMethodBodyForType(dependentEntityMeta, refFieldMeta, paramName, MethodType.INSERT, dependentBuilderHelper));
				} else {
					dataAdd.setMethodBody("\tinsertDependent(" + dependenConstantName + ", p" + formatMethodName(dependentMethodName) + ");");
				}
				newMeta.addMethod(dataAdd);

				// Remove - Referenced Field
				final String dependentDeleteMethodName = dependentBuilderHelper.createCompleteDependentMethodName("delete", dependentMethodName, multipleReferences, idx);
				NuclosBusinessObjectMethodMetaData dataRemove = new NuclosBusinessObjectMethodMetaData(
						dependentDeleteMethodName,
						NuclosBusinessObjectMethodMetaData.PUBLIC,
						NuclosBusinessObjectMethodMetaData.VOID, false,
						getBOMethodJavadoc(refFieldMeta, MethodType.DELETE));
				dataRemove
						.addParameter(new NuclosBusinessObjectAttributeMetaData(
								paramName, null, dependentMethodNameWithPackage, null));
				if (generic) {
					dataRemove.setMethodBody(getGenericDependentMethodBodyForType(dependentEntityMeta, refFieldMeta, paramName, MethodType.DELETE, dependentBuilderHelper));
				} else {
					dataRemove.setMethodBody("\tdeleteDependent(" + dependenConstantName + ", p" + formatMethodName(dependentMethodName) + ");");
				}
				newMeta.addMethod(dataRemove);
			}
		}
	}
	
	/**
	 * This Methods creates all getter methods for all referencing fields.
	 * And, if editable, insert/update/delete methods
	 * 
	 * @param newMetaProxy new meta proxy
	 * @param values field meta values
	 */
	private void mapNuclosBusinessObjectProxyMethods(
			NuclosBusinessObjectMetaData newMetaProxy, EntityMeta<?> entity,
			Collection<FieldMeta<?>> values) throws InterruptedException {
		
		String pkClass = entity.isUidEntity()?org.nuclos.api.UID.class.getCanonicalName():Long.class.getCanonicalName();
		newMetaProxy.addImport(NuclosBusinessObjectImport.LIST);
		
		String formatEntity = escapeJavaIdentifier(entity.getEntityName(), DEFAULT_ENTITY_PREFIX);
		
		// main setter "setUser(User)"
		NuclosBusinessObjectMethodMetaData setUser = new NuclosBusinessObjectMethodMetaData(
				"setUser",
				NuclosBusinessObjectMethodMetaData.PUBLIC,
				NuclosBusinessObjectMethodMetaData.VOID, true,
				null);
		NuclosBusinessObjectAttributeMetaData paramSetUser = new NuclosBusinessObjectAttributeMetaData(
				"user", 
				null, org.nuclos.api.User.class.getCanonicalName(), null);
		setUser.addParameter(paramSetUser);
		newMetaProxy.addMethod(setUser);
		
		// main finder "getAll()"
		NuclosBusinessObjectMethodMetaData getAll = new NuclosBusinessObjectMethodMetaData(
				"getAll",
				NuclosBusinessObjectMethodMetaData.PUBLIC,
				"List<" + formatEntity + ">", true, null);
		newMetaProxy.addMethod(getAll);
		
		// main finder "getAllIds()"
		NuclosBusinessObjectMethodMetaData getAllIds = new NuclosBusinessObjectMethodMetaData(
				"getAllIds",
				NuclosBusinessObjectMethodMetaData.PUBLIC,
				"List<" + pkClass + ">", true, null);
		newMetaProxy.addMethod(getAllIds);
		
		// main finder "getById(...)"
		NuclosBusinessObjectMethodMetaData findById = new NuclosBusinessObjectMethodMetaData(
				"getById",
				NuclosBusinessObjectMethodMetaData.PUBLIC,
				formatEntity, true,null);
		NuclosBusinessObjectAttributeMetaData paramFindById = new NuclosBusinessObjectAttributeMetaData(
				"id", 
				null, pkClass, null);
		findById.addParameter(paramFindById);
		newMetaProxy.addMethod(findById);
		
		for (FieldMeta<?> fMeta : values) {
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
			if (fMeta.getForeignEntity() != null) {
				EntityMeta<?> eRefMeta = metaProvider.getEntity(fMeta.getForeignEntity());
				NuclosBusinessObjectMethodMetaData finder = new NuclosBusinessObjectMethodMetaData(
						getProxyGetterForeignMethodName(fMeta),
						NuclosBusinessObjectMethodMetaData.PUBLIC,
						"List<" + formatEntity + ">",
						true,
						getBOMethodJavadoc(fMeta, MethodType.GETTER)
				);
				NuclosBusinessObjectAttributeMetaData paramFindBy = new NuclosBusinessObjectAttributeMetaData(
						"p" + formatMethodName(escapeJavaIdentifier(eRefMeta.getEntityName(), DEFAULT_FIELD_PREFIX) + "Id"),
						null,
						eRefMeta.isUidEntity() ? org.nuclos.api.UID.class.getCanonicalName() : Long.class.getCanonicalName(),
						null
				);
				finder.addParameter(paramFindBy);
				newMetaProxy.addMethod(finder);
			}
		}
		
		// insert + update + delete + commit + rollback
		if (entity.isEditable()) {
			NuclosBusinessObjectMethodMetaData insert = new NuclosBusinessObjectMethodMetaData(
					"insert",
					NuclosBusinessObjectMethodMetaData.PUBLIC,
					NuclosBusinessObjectMethodMetaData.VOID, true, null);
			insert.addParameter(new NuclosBusinessObjectAttributeMetaData(
					"p" + formatMethodName(formatEntity), null,
					formatEntity, null));
			insert.addThrows(BusinessException.class.getCanonicalName());
			newMetaProxy.addMethod(insert);
			
			NuclosBusinessObjectMethodMetaData update = new NuclosBusinessObjectMethodMetaData(
					"update",
					NuclosBusinessObjectMethodMetaData.PUBLIC,
					NuclosBusinessObjectMethodMetaData.VOID, true, null);
			update.addParameter(new NuclosBusinessObjectAttributeMetaData(
					"p" + formatMethodName(formatEntity), null,
					formatEntity, null));
			update.addThrows(BusinessException.class.getCanonicalName());
			newMetaProxy.addMethod(update);
			
			NuclosBusinessObjectMethodMetaData delete = new NuclosBusinessObjectMethodMetaData(
					"delete",
					NuclosBusinessObjectMethodMetaData.PUBLIC,
					NuclosBusinessObjectMethodMetaData.VOID, true, null);
			delete.addParameter(new NuclosBusinessObjectAttributeMetaData(
					"id", 
					null, pkClass, null));
			delete.addThrows(BusinessException.class.getCanonicalName());
			newMetaProxy.addMethod(delete);
			
			NuclosBusinessObjectMethodMetaData commit = new NuclosBusinessObjectMethodMetaData(
					"commit",
					NuclosBusinessObjectMethodMetaData.PUBLIC,
					NuclosBusinessObjectMethodMetaData.VOID, true, null);
			newMetaProxy.addMethod(commit);
			
			NuclosBusinessObjectMethodMetaData rollback = new NuclosBusinessObjectMethodMetaData(
					"rollback",
					NuclosBusinessObjectMethodMetaData.PUBLIC,
					NuclosBusinessObjectMethodMetaData.VOID, true, null);
			newMetaProxy.addMethod(rollback);
		}
	}
	
	
	/**
	 * This Methods creates all getter methods for all referencing fields.
	 * And, if editable, insert/update/delete methods
	 * 
	 * @param newMetaProxy new meta proxy
	 * @param values field meta values
	 */
	private void mapNuclosBusinessObjectWriteProxyMethods(
			NuclosBusinessObjectMetaData newMetaProxy, EntityMeta<?> entity,
			Collection<FieldMeta<?>> values) throws InterruptedException {
		
		String pkClass = entity.isUidEntity()?org.nuclos.api.UID.class.getCanonicalName():Long.class.getCanonicalName();
		newMetaProxy.addImport(NuclosBusinessObjectImport.LIST);
		
		String formatEntity = escapeJavaIdentifier(entity.getEntityName(), DEFAULT_ENTITY_PREFIX);
		
		// main setter "setUser(User)"
		NuclosBusinessObjectMethodMetaData setUser = new NuclosBusinessObjectMethodMetaData(
				"setUser",
				NuclosBusinessObjectMethodMetaData.PUBLIC,
				NuclosBusinessObjectMethodMetaData.VOID, true,
				null);
		NuclosBusinessObjectAttributeMetaData paramSetUser = new NuclosBusinessObjectAttributeMetaData(
				"user", 
				null, org.nuclos.api.User.class.getCanonicalName(), null);
		setUser.addParameter(paramSetUser);
		newMetaProxy.addMethod(setUser);
				
		
		// insert + update + delete + commit + rollback
		if (true) {
			NuclosBusinessObjectMethodMetaData insert = new NuclosBusinessObjectMethodMetaData(
					"insert",
					NuclosBusinessObjectMethodMetaData.PUBLIC,
					NuclosBusinessObjectMethodMetaData.OBJECT, true, null);
			insert.addParameter(new NuclosBusinessObjectAttributeMetaData(
					"p" + formatMethodName(formatEntity), null,
					formatEntity, null));
			insert.addThrows(BusinessException.class.getCanonicalName());
			newMetaProxy.addMethod(insert);
			
			NuclosBusinessObjectMethodMetaData update = new NuclosBusinessObjectMethodMetaData(
					"update",
					NuclosBusinessObjectMethodMetaData.PUBLIC,
					NuclosBusinessObjectMethodMetaData.VOID, true, null);
			update.addParameter(new NuclosBusinessObjectAttributeMetaData(
					"p" + formatMethodName(formatEntity), null,
					formatEntity, null));
			update.addThrows(BusinessException.class.getCanonicalName());
			newMetaProxy.addMethod(update);
			
			NuclosBusinessObjectMethodMetaData delete = new NuclosBusinessObjectMethodMetaData(
					"delete",
					NuclosBusinessObjectMethodMetaData.PUBLIC,
					NuclosBusinessObjectMethodMetaData.VOID, true, null);
			delete.addParameter(new NuclosBusinessObjectAttributeMetaData(
					"id", 
					null, pkClass, null));
			delete.addThrows(BusinessException.class.getCanonicalName());
			newMetaProxy.addMethod(delete);
			
			NuclosBusinessObjectMethodMetaData commit = new NuclosBusinessObjectMethodMetaData(
					"commit",
					NuclosBusinessObjectMethodMetaData.PUBLIC,
					NuclosBusinessObjectMethodMetaData.VOID, true, null);
			newMetaProxy.addMethod(commit);
			
			NuclosBusinessObjectMethodMetaData rollback = new NuclosBusinessObjectMethodMetaData(
					"rollback",
					NuclosBusinessObjectMethodMetaData.PUBLIC,
					NuclosBusinessObjectMethodMetaData.VOID, true, null);
			newMetaProxy.addMethod(rollback);
		}
	}
	public static String getProxyGetterForeignMethodName(FieldMeta<?> fMeta) {
		return "getBy" + formatMethodName(getFieldNameForFqn(fMeta));
	}
	
	/**
	 * This Methods creates all mapped attributes and all corresponding
	 * Setter/Getter methods and adds a converting method from storing data
	 * from/in a EntityObjectVO
	 * 
	 * @param newMeta new meta data
	 * @param values field metas.
	 */
	private void mapNuclosBusinessObjectFields(
			NuclosBusinessObjectMetaData newMeta, EntityMeta<?> entity,
			Collection<FieldMeta<?>> values) throws InterruptedException {

		final boolean generic = entity.isGeneric();

		// Getter entity id
		if (generic) {
			NuclosBusinessObjectMethodMetaData entityIdGetter = new NuclosBusinessObjectMethodMetaData(
					"getEntityUid", NuclosBusinessObjectMethodMetaData.PUBLIC,
					org.nuclos.api.UID.class.getCanonicalName(), false, null);
			entityIdGetter
					.setMethodBody("\treturn businessObject.getEntityUid();");
			newMeta.addMethod(entityIdGetter);
		} else {
			NuclosBusinessObjectMethodMetaData entityIdGetter = new NuclosBusinessObjectMethodMetaData(
					"getEntityUid", NuclosBusinessObjectMethodMetaData.PUBLIC,
					(BusinessObjectBuilderForInternalUse.getEntityMetas().contains(entity)) ?
							UID.class.getCanonicalName() : org.nuclos.api.UID.class.getCanonicalName(),
					false, getBOMethodJavadoc(E.ENTITY.entity, MethodType.GETTER));
			entityIdGetter
					.setMethodBody("\treturn new org.nuclos.common.UID(\""
							+ getUIDStringforClass(entity) + "\");");
			newMeta.addMethod(entityIdGetter);
		}

		if (generic) {
			NuclosBusinessObjectMethodMetaData boGetter = new NuclosBusinessObjectMethodMetaData(
					"getBusinessObject", NuclosBusinessObjectMethodMetaData.PUBLIC,
					BusinessObject.class.getCanonicalName() + "<Long>", false, null);
			boGetter
					.setMethodBody("\treturn businessObject;");
			newMeta.addMethod(boGetter);
			NuclosBusinessObjectMethodMetaData entityGetter = new NuclosBusinessObjectMethodMetaData(
					"getEntity", NuclosBusinessObjectMethodMetaData.PUBLIC,
					String.class.getCanonicalName(), false, null);
			entityGetter
					.setMethodBody("\treturn businessObject.getEntity();");
			newMeta.addMethod(entityGetter);
			NuclosBusinessObjectMethodMetaData idGetter = new NuclosBusinessObjectMethodMetaData(
					"getId", NuclosBusinessObjectMethodMetaData.PUBLIC,
					Long.class.getCanonicalName(), false, null);
			idGetter
					.setMethodBody("\treturn businessObject.getId();");
			newMeta.addMethod(idGetter);
			NuclosBusinessObjectMethodMetaData versionGetter = new NuclosBusinessObjectMethodMetaData(
					"getVersion", NuclosBusinessObjectMethodMetaData.PUBLIC,
					Integer.class.getCanonicalName(), false, null);
			versionGetter
					.setMethodBody("\treturn businessObject.getVersion();");
			newMeta.addMethod(versionGetter);
			NuclosBusinessObjectMethodMetaData isInsertGetter = new NuclosBusinessObjectMethodMetaData(
					"isInsert", NuclosBusinessObjectMethodMetaData.PUBLIC,
					boolean.class.getCanonicalName(), false, null);
			isInsertGetter
					.setMethodBody("\treturn businessObject.isInsert();");
			newMeta.addMethod(isInsertGetter);
			NuclosBusinessObjectMethodMetaData isUpdateGetter = new NuclosBusinessObjectMethodMetaData(
					"isUpdate", NuclosBusinessObjectMethodMetaData.PUBLIC,
					boolean.class.getCanonicalName(), false, null);
			isUpdateGetter
					.setMethodBody("\treturn businessObject.isUpdate();");
			newMeta.addMethod(isUpdateGetter);
			NuclosBusinessObjectMethodMetaData isDeleteGetter = new NuclosBusinessObjectMethodMetaData(
					"isDelete", NuclosBusinessObjectMethodMetaData.PUBLIC,
					boolean.class.getCanonicalName(), false, null);
			isDeleteGetter
					.setMethodBody("\treturn businessObject.isDelete();");
			newMeta.addMethod(isDeleteGetter);
		}
		
		if (entity.isProxy()) {
			NuclosBusinessObjectMethodMetaData entityGetter = new NuclosBusinessObjectMethodMetaData(
					"getEntity", NuclosBusinessObjectMethodMetaData.PUBLIC,
					java.lang.String.class.getCanonicalName(), false, getBOMethodJavadoc(E.ENTITY.entity, MethodType.GETTER));
			entityGetter
					.setMethodBody("\treturn \""
							+ entity.getEntityName() + "\";");
			newMeta.addMethod(entityGetter);
			
			//public PK getId() {
			//return this.eo.getPrimaryKey();
			// add setId Method to BO
			NuclosBusinessObjectMethodMetaData idSetter = new NuclosBusinessObjectMethodMetaData(
					"setId", 
					NuclosBusinessObjectMethodMetaData.PUBLIC,
					NuclosBusinessObjectMethodMetaData.VOID, false, getBOMethodJavadoc(
							entity.isUidEntity()? SF.PK_UID.getMetaData(entity) : SF.PK_ID.getMetaData(entity), MethodType.GETTER));
			idSetter.addParameter(new NuclosBusinessObjectAttributeMetaData(
					"id", null,
					entity.isUidEntity()?org.nuclos.api.UID.class.getCanonicalName():Long.class.getCanonicalName(), null));
			idSetter.setMethodBody("\tsuper.setId(id);");
			idSetter.addAnnotation(new NuclosBusinessObjectAttributeMetaData("", "", Override.class.getCanonicalName(), null));
			newMeta.addMethod(idSetter);
		}

		// Id
		if (!entity.isProxy() && !generic) {
			newMeta.addQueryConstant(
				new NuclosBusinessObjectConstantMetaData(
					"Id", newMeta.getPackage(), newMeta.getEntity(), newMeta.getEntityUid(),
					"intId",
					entity.isUidEntity()
						? SF.PK_UID.getUID(newMeta.getEntityUid())
						: SF.PK_ID.getUID(newMeta.getEntityUid()),
					NuclosBusinessObjectMetaData.getPkClassName(entity),
					true, false,
					getBOAttributeJavadoc(
								entity.isUidEntity()
									? SF.PK_UID.getMetaData(entity)
									: SF.PK_ID.getMetaData(entity))));
		}
		for (FieldMeta<?> val : values) {
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
			if (SF.MANDATOR.checkField(entity.getUID(), val.getUID())) {
				continue;
			}
			
			if (entity.isThin() && val.isCalculated()) {
				continue;
			}

			// Foreign Key
			if (val.getForeignEntity() == null && val.getUnreferencedForeignEntity() == null && val.getForeignIntegrationPoint() == null) {
				// map simple field
				if (GenericObjectDocumentFile.class.getCanonicalName().equals(val.getDataType())) {
					mapNuclosBusinessGenericObjectDocumentFile(newMeta, val, entity);
				}
				else {
					mapNuclosBusinessObjectField(newMeta, val, entity);					
				}
			} else {
				if (GenericObjectDocumentFile.class.getCanonicalName().equals(val.getDataType())) {
					// map document file field
					mapNuclosBusinessGenericObjectDocumentFile(newMeta, val, entity);
				}
				else {
					// map foreign key field
					mapNuclosBusinessObjectFieldForeignKey(newMeta, val, entity);
				}
			}
		}
		
		// add 'insertAttachment' methods
		mapNuclosBusinessGenericObjectDocumentFile(newMeta, entity);
		
		// If entity is using a statemodel we add a method to set the process by constant Process-instance
		mapNuclosBusinessObjectProcess(newMeta, entity);
	}

	private void mapNuclosBusinessObjectProcess (NuclosBusinessObjectMetaData newMeta, EntityMeta<?> entity) {
		if (entity.isStateModel()) {
			
			newMeta.addImport(NuclosBusinessObjectImport.PROCESS);
			
			String formatEntity = escapeJavaIdentifier(entity.getEntityName(), DEFAULT_ENTITY_PREFIX);
			String formatEntityWithPath = getNucletPackageStatic(entity, metaProvider) + "." + formatEntity;
			
			// setter process class
			NuclosBusinessObjectMethodMetaData dataSet = new NuclosBusinessObjectMethodMetaData(
					"setNuclosProcess",
					NuclosBusinessObjectMethodMetaData.PUBLIC,
					NuclosBusinessObjectMethodMetaData.VOID, false,
					getBOMethodJavadoc(SF.PROCESS_UID.getMetaData(entity), MethodType.GETTER));
			
			dataSet.addParameter(new NuclosBusinessObjectAttributeMetaData(
					"pProcess", null, "Process<" + formatEntityWithPath + ">", null));
			dataSet.setMethodBody("\tsetFieldId(\"" + SF.PROCESS.getMetaData(entity.getUID()).getUID().getString() + "\", pProcess.getId());");
			
			newMeta.addMethod(dataSet);
		}
	}
	private void mapNuclosBusinessGenericObjectDocumentFile(NuclosBusinessObjectMetaData newMeta, EntityMeta<?> entity) {
		
		if (entity.isStateModel()) {

			// setter (simple attribute)
			NuclosBusinessObjectMethodMetaData dataSet = new NuclosBusinessObjectMethodMetaData(
					"insertAttachment",
					NuclosBusinessObjectMethodMetaData.PUBLIC,
					NuclosBusinessObjectMethodMetaData.VOID, false,
					getBOMethodJavadoc(E.GENERALSEARCHDOCUMENT.documentfile, MethodType.INSERT));
			
			dataSet.addParameter(new NuclosBusinessObjectAttributeMetaData(
					"pNuclosFile", null, NuclosFile.class.getCanonicalName(), null));
			dataSet.addParameter(new NuclosBusinessObjectAttributeMetaData(
					"pComment", null, String.class.getCanonicalName(), null));
			dataSet.setMethodBody("\tinsertGenericObjectDocumentAttachment(pNuclosFile,pComment);");
			
			newMeta.addMethod(dataSet);
			
			// Getter (simple attribute)
			NuclosBusinessObjectMethodMetaData dataGet = new NuclosBusinessObjectMethodMetaData(
					"getAttachments",
					NuclosBusinessObjectMethodMetaData.PUBLIC,
					"List<" + NuclosFile.class.getCanonicalName() + ">", false,
					getBOMethodJavadoc(E.GENERALSEARCHDOCUMENT.documentfile, MethodType.GETTER));
	
			dataGet.setMethodBody("\treturn super.getAttachments();");
			
			newMeta.addMethod(dataGet);
			
			// Remove (simple attribute)
			NuclosBusinessObjectMethodMetaData dataRemove = new NuclosBusinessObjectMethodMetaData(
					"deleteAttachment",
					NuclosBusinessObjectMethodMetaData.PUBLIC,
					NuclosBusinessObjectMethodMetaData.VOID, false,
					getBOMethodJavadoc(E.GENERALSEARCHDOCUMENT.documentfile, MethodType.REMOVE));
	
			dataRemove.addParameter(new NuclosBusinessObjectAttributeMetaData(
					"pNuclosFile", null, NuclosFile.class.getCanonicalName(), null));
			
			dataRemove.setMethodBody("\tsuper.deleteGenericObjectDocumentAttachment(pNuclosFile);");
			
			newMeta.addMethod(dataRemove);
		}
	}

	private void mapNuclosBusinessObjectFieldForeignKey(
			NuclosBusinessObjectMetaData newMeta, FieldMeta<?> val,
			EntityMeta<?> entity) {

		final boolean generic = entity.isGeneric();
		final UID foreignEntityUID = LangUtils.defaultIfNull(val.getForeignIntegrationPoint(),
				LangUtils.defaultIfNull(val.getUnreferencedForeignEntity(), val.getForeignEntity()));
		final EntityMeta<?> foreignEntityMeta = metaProvider.getEntity(foreignEntityUID);
		final String foreignTypeClassName = NuclosBusinessObjectMetaData.getPkClassName(foreignEntityMeta);

		if (!isExcludedCompletly(val)) {
			// Constant value for query issue
			if (!isExcluedQueryConstant(val) && !generic) {
				// NUCLOS-2005
				NuclosBusinessObjectConstantMetaData foreignKeyQryConstant = new NuclosBusinessObjectConstantMetaData(
						formatMethodName(getFieldNameForFqn(val)),
						newMeta.getPackage(), newMeta.getEntity(), newMeta
								.getEntityUid(), val.getFieldName(), getUIDforClass(val), foreignTypeClassName, false, true,
										getBOAttributeJavadoc(val));
				foreignKeyQryConstant.addAnnotation(new NuclosBusinessObjectAttributeMetaData("", "", Deprecated.class.getCanonicalName(), null));
				newMeta.addQueryConstant(foreignKeyQryConstant);

				NuclosBusinessObjectConstantMetaData foreignKeyQryConstantWithId = new NuclosBusinessObjectConstantMetaData(
						formatMethodName(escapeJavaIdentifier(adjustName(val) + "Id", DEFAULT_FIELD_PREFIX)),
						newMeta.getPackage(),
						newMeta.getEntity(),
						newMeta.getEntityUid(),
						val.getFieldName(),
						getUIDforClass(val),
						foreignEntityMeta.isUidEntity() ? UID.class.getName() : Long.class.getName(),
						false,
						true,
						getBOAttributeJavadoc(val)
				);
				newMeta.addQueryConstant(foreignKeyQryConstantWithId);
			}

			// Getter (foreign key attribute)
			if (!entity.isThin()) {
				NuclosBusinessObjectMethodMetaData dataGet = new NuclosBusinessObjectMethodMetaData(
						getGetterName(getFieldNameForFqn(val)),
						NuclosBusinessObjectMethodMetaData.PUBLIC,
						NuclosBusinessObjectSourceBuilder.extractDataType(val
								.getDataType()), false, getBOMethodJavadoc(val, MethodType.GETTER));

				if (generic) {
					dataGet.setMethodBody(getGenericFieldGetterMethodBody(entity, val, null, null, true));
				} else {
					dataGet.setMethodBody("\treturn getField(\""
							+ getUIDStringforClass(val)
							+ "\", "
							+ NuclosBusinessObjectSourceBuilder
							.extractDataType(val.getDataType()) + ".class); ");
				}
				newMeta.addMethod(dataGet);
			}

			// Id-Getter (foreign key attribute)
			if (!isExcluedElementGetterId(val)) {
				NuclosBusinessObjectMethodMetaData dataGetId = new NuclosBusinessObjectMethodMetaData(
						getGetterName(getFieldNameForFqn(val)) + "Id",
						NuclosBusinessObjectMethodMetaData.PUBLIC, foreignTypeClassName, false, getBOMethodJavadoc(val, MethodType.GETTER));
				if (generic) {
					dataGetId.setMethodBody(getGenericFieldGetterMethodBody(entity, val, "Id"));
				} else {
					if (!foreignEntityMeta.isUidEntity())
						dataGetId.setMethodBody("\treturn getFieldId(\"" + getUIDStringforClass(val) + "\");");
					else
						dataGetId.setMethodBody("\treturn getFieldUid(\"" + getUIDStringforClass(val) + "\");");
				}
				newMeta.addMethod(dataGetId);
			}

			if (!isExcluedElementSetterId(val) && (!entity.isUidEntity()
						|| BusinessObjectBuilderForInternalUse.getEntityMetas().contains(entity))
					&& (entity.isEditable() || entity.isProxy()) && isIncludedForeignEntity(val)) {
				final String paramName = "p" + formatMethodName(getFieldNameForFqn(val) + "Id");
				// ID-Setter (foreign key attribute)
				NuclosBusinessObjectMethodMetaData dataSetId = new NuclosBusinessObjectMethodMetaData(
						getSetterName(getFieldNameForFqn(val)) + "Id",
						NuclosBusinessObjectMethodMetaData.PUBLIC,
						NuclosBusinessObjectMethodMetaData.VOID, false, getBOMethodJavadoc(val, MethodType.SETTER));
				dataSetId.addParameter(new NuclosBusinessObjectAttributeMetaData(paramName, null, foreignTypeClassName, null));
				if (generic) {
					dataSetId.setMethodBody(getGenericFieldSetterMethodBody(entity, val, true, paramName));
				} else {
					dataSetId.setMethodBody("\tsetFieldId(\"" + getUIDStringforClass(val)
							+ "\", p" + formatMethodName(getFieldNameForFqn(val) + "Id") + "); ");
				}
				newMeta.addMethod(dataSetId);
				
				// REF-VALUE-Setter (foreign key attribute)
				if (entity.isProxy()) {
					NuclosBusinessObjectMethodMetaData dataSet = new NuclosBusinessObjectMethodMetaData(
							getSetterName(getFieldNameForFqn(val)),
							NuclosBusinessObjectMethodMetaData.PUBLIC,
							NuclosBusinessObjectMethodMetaData.VOID, false, getBOMethodJavadoc(val, MethodType.SETTER));
					dataSet
							.addParameter(new NuclosBusinessObjectAttributeMetaData(
									"p" + formatMethodName(getFieldNameForFqn(val)),
									null, String.class.getCanonicalName(), null));
					dataSet.setMethodBody("\tsetField(\"" + getUIDStringforClass(val)
							+ "\", p" + formatMethodName(getFieldNameForFqn(val)) + "); ");
					newMeta.addMethod(dataSet);
				}
			}
			
			 // Referenced BO -Getter (via foreign key attribute)
			if (!isExcluedElementGetterBO(val) && (!entity.isUidEntity()
					|| BusinessObjectBuilderForInternalUse.getEntityMetas().contains(entity))) {
				final UID boClassEntityUID;
				if (val.getForeignIntegrationPoint() != null) {
					// do not return the foreign entity, return the integration point entity
					boClassEntityUID = val.getForeignIntegrationPoint();
				} else {
					boClassEntityUID = foreignEntityUID;
				}
				final EntityMeta emdVO = metaProvider.getEntity(boClassEntityUID);

				final boolean bForInternalUseOnly = emdVO.isUidEntity() && BusinessObjectBuilderForInternalUse.getEntityMetas().contains(emdVO);
				final String formatEntity = getNameForFqn(getFormattedEntityName(emdVO, bForInternalUseOnly));
				final String formatEntityWithPath = getNucletPackageStatic(emdVO, metaProvider) + "." + formatEntity;
			
				NuclosBusinessObjectMethodMetaData dataGetBO = new NuclosBusinessObjectMethodMetaData(
						getGetterName(getFieldNameForFqn(val)) + "BO",
						NuclosBusinessObjectMethodMetaData.PUBLIC, 
						formatEntityWithPath,
						false, getBOMethodJavadoc(val, MethodType.GETTER));

				if (generic) {
					dataGetBO.setMethodBody(getGenericFieldGetterMethodBody(entity, val, "BO"));
				} else {
					if (!foreignEntityMeta.isUidEntity()) {
						dataGetBO.setMethodBody("\treturn getReferencedBO(" + formatEntityWithPath + ".class, "
								+ "getFieldId(\"" + getUIDStringforClass(val) + "\"), "
								+ "\"" + getUIDStringforClass(val) + "\", "
								+ "\"" + getUIDStringforClass(emdVO) + "\");");
					} else {
						dataGetBO.setMethodBody("\treturn getReferencedBO(" + formatEntityWithPath + ".class, "
								+ "getFieldUid(\"" + getUIDStringforClass(val) + "\"), "
								+ "\"" + getUIDStringforClass(val) + "\", "
								+ "\"" + getUIDStringforClass(emdVO) + "\");");
					}
				}
				
				newMeta.addMethod(dataGetBO);
			}
						
		}
	}

	private boolean isIncludedForeignEntity(FieldMeta<?> val) {
		boolean retVal = false;

		final UID foreignEntityUID = LangUtils.defaultIfNull(val.getForeignIntegrationPoint(),
				LangUtils.defaultIfNull(val.getUnreferencedForeignEntity(), val.getForeignEntity()));

		if (foreignEntityUID != null) {
			if (E.isNuclosEntity(foreignEntityUID)) {
				if (E.PROCESS.checkEntityUID(foreignEntityUID) ||
					E.USER.checkEntityUID(foreignEntityUID) ||
					E.ROLE.checkEntityUID(foreignEntityUID) ||
					E.ROLEUSER.checkEntityUID(foreignEntityUID) ||
					E.DUMMY.checkEntityUID(foreignEntityUID) ||
					E.PRINTSERVICE.checkEntityUID(foreignEntityUID) ||
					E.PRINTSERVICE_TRAY.checkEntityUID(foreignEntityUID)) {
					retVal = true;
				} else if (BusinessObjectBuilderForInternalUse.getEntityMetas().contains(
						metaProvider.getEntity(foreignEntityUID))) {
					return true;
				}
			} else {
				retVal = true;
			}
		}

		return retVal;
	}

	private void mapNuclosBusinessGenericObjectDocumentFile (NuclosBusinessObjectMetaData newMeta, FieldMeta<?> val,
			EntityMeta<?> entity) {

		final boolean generic = entity.isGeneric();
		
		// Getter (simple attribute)
		NuclosBusinessObjectMethodMetaData dataGet = new NuclosBusinessObjectMethodMetaData(
				getGetterName(adjustName(val)),
				NuclosBusinessObjectMethodMetaData.PUBLIC,
				NuclosFile.class.getCanonicalName(),
				false,
				getBOMethodJavadoc(val, MethodType.GETTER)
		);

		if (generic) {
			dataGet.setMethodBody(getGenericFieldGetterMethodBody(entity, val));
		} else {
			dataGet.setMethodBody("\treturn getNuclosFile(\"" + getUIDStringforClass(val) + "\");");
		}
		newMeta.addMethod(dataGet);

		final String paramName = "p" + formatMethodName(escapeJavaIdentifierPart(adjustName(val)));
		
		// Getter with type class
		NuclosBusinessObjectMethodMetaData dataGetType = new NuclosBusinessObjectMethodMetaData(
				"get"+ formatMethodName(escapeJavaIdentifierPart(adjustName(val))),
					NuclosBusinessObjectMethodMetaData.PUBLIC,
					"<T extends " + NuclosFileBase.class.getCanonicalName() + "> T",
					false, getBOMethodJavadoc(val, MethodType.GETTER)
					);
		NuclosBusinessObjectAttributeMetaData getterParam = new NuclosBusinessObjectAttributeMetaData(
				paramName, null,
					"Class<T>",
					null);

		if (generic) {
			dataGetType.setMethodBody(getGenericFieldGetterMethodBody(entity, val, null, paramName));
		} else {
			dataGetType.setMethodBody("\treturn super.getNuclosFile(" + getterParam.getAttributeName() + ",\"" + getUIDStringforClass(val) + "\");");
		}
		
		dataGetType.addParameter(getterParam);	
		newMeta.addMethod(dataGetType);
		
		// Setter with type class
		NuclosBusinessObjectMethodMetaData dataSet = new NuclosBusinessObjectMethodMetaData(
				getSetterName(adjustName(val)),
				NuclosBusinessObjectMethodMetaData.PUBLIC,
				"<T extends " + NuclosFileBase.class.getCanonicalName() +"> " + NuclosBusinessObjectMethodMetaData.VOID,
				false, getBOMethodJavadoc(val, MethodType.SETTER));
		NuclosBusinessObjectAttributeMetaData setterParam = new NuclosBusinessObjectAttributeMetaData(
				paramName,
				null,
				"T",
				null
		);

		if (generic) {
			dataSet.setMethodBody(getGenericFieldSetterMethodBody(entity, val, paramName));
		} else {
			dataSet.setMethodBody("\tsuper.setNuclosFile(" + setterParam.getAttributeName() + ",\"" + getUIDStringforClass(val) + "\");");
		}
		
		dataSet.addParameter(setterParam);
		newMeta.addMethod(dataSet);
	}
	
	private void mapNuclosBusinessObjectField(
			NuclosBusinessObjectMetaData newMeta, FieldMeta<?> val,
			EntityMeta<?> entity) {

		final boolean generic = entity.isGeneric();

		if (!isExcludedCompletly(val)) {
			// Constant value for query issue
			if (!isExcluedQueryConstant(val) && !entity.isProxy() & !generic)
				newMeta.addQueryConstant(new NuclosBusinessObjectConstantMetaData(
					formatMethodName(getFieldNameForFqn(val)), newMeta.getPackage(),
					newMeta.getEntity(), newMeta.getEntityUid(), val.getFieldName(),
					getUIDforClass(val), NuclosBusinessObjectSourceBuilder
							.extractDataType(val.getDataType()), false, false, getBOAttributeJavadoc(val)));

			// Getter (simple attribute)
			if (!(E.USER.getUID().equals(entity.getUID()) && 
				  E.USER.password.getUID().equals(val.getUID()))) {
				NuclosBusinessObjectMethodMetaData dataGet = new NuclosBusinessObjectMethodMetaData(
						getGetterName(adjustName(val)),
						NuclosBusinessObjectMethodMetaData.PUBLIC,
						NuclosBusinessObjectSourceBuilder.extractDataType(val.getDataType()),
						false,
						getBOMethodJavadoc(val, MethodType.GETTER)
				);

				if (generic) {
					dataGet.setMethodBody(getGenericFieldGetterMethodBody(entity, val));
				} else { // !generic
					if (InternalTimestamp.class.getCanonicalName().equals(
							val.getDataType())) {
						newMeta.addImport(NuclosBusinessObjectImport.DATE);
						dataGet.setMethodBody("\treturn getField(\"" + getUIDStringforClass(val)
								+ "\", " + Date.class.getCanonicalName() + ".class); ");
					} else if (Double.class.getCanonicalName()
							.equals(val.getDataType())) {
						int scale = val.getPrecision() != null ? val.getPrecision() : 0;
						dataGet.setMethodBody("java.math.BigDecimal retVal = null;\n\t" +
								"if (getField(\"" + val.getUID().getString()
								+ "\", Double.class) != null) {\n\t\t"
								+ "retVal = new java.math.BigDecimal(getField(\"" + getUIDStringforClass(val)
								+ "\", Double.class).doubleValue());\n\t\tretVal = retVal.setScale(" + scale + ", " + BigDecimal.class.getCanonicalName() + ".ROUND_HALF_UP);\n\t}\n\t"
								+ "return retVal;");

					} else if (NuclosPassword.class.getCanonicalName()
							.equals(val.getDataType())) {

						dataGet.setMethodBody("\treturn getField(\"" + getUIDStringforClass(val)
								+ "\", " + String.class.getCanonicalName() + ".class); ");
					} else {
						dataGet.setMethodBody("\treturn getField(\""
								+ getUIDStringforClass(val)
								+ "\", "
								+ NuclosBusinessObjectSourceBuilder.extractDataType(val
								.getDataType()) + ".class); ");
					}
				}
				newMeta.addMethod(dataGet);
				
				if (val.isLocalized()) {
					NuclosBusinessObjectMethodMetaData dataGetLocalized = new NuclosBusinessObjectMethodMetaData(
							getGetterName(adjustName(val)),
							NuclosBusinessObjectMethodMetaData.PUBLIC,
							NuclosBusinessObjectSourceBuilder.extractDataType(val.getDataType()),
							false,
							getBOMethodJavadoc(val, MethodType.GETTER)
					);
						dataGetLocalized.addParameter(
								new NuclosBusinessObjectAttributeMetaData("pNuclosLocale", 
										null, NuclosLocale.class.getCanonicalName(), null));	
						dataGetLocalized.setMethodBody("\treturn getField(\""
								+ getUIDStringforClass(val)
								+ "\", " 
								+ "pNuclosLocale, "
								+ NuclosBusinessObjectSourceBuilder.extractDataType(val
										.getDataType()) + ".class); ");
					newMeta.addMethod(dataGetLocalized);
					
					if ( (!entity.isUidEntity() &&
							(!isExludedElementSetter(val) ||
							(entity.isProxy())))) {

						NuclosBusinessObjectMethodMetaData dataSet = new NuclosBusinessObjectMethodMetaData(
								getSetterName(adjustName(val)),
								NuclosBusinessObjectMethodMetaData.PUBLIC,
								NuclosBusinessObjectMethodMetaData.VOID,
								false,
								getBOMethodJavadoc(val, MethodType.SETTER)
						);

						NuclosBusinessObjectAttributeMetaData param = new NuclosBusinessObjectAttributeMetaData(
								"p" + formatMethodName(escapeJavaIdentifierPart(adjustName(val))),
								null,
								NuclosBusinessObjectSourceBuilder.extractDataType(val.getDataType()),
								null
						);
						NuclosBusinessObjectAttributeMetaData param2 = new NuclosBusinessObjectAttributeMetaData(
								"pNuclosLocale" , null,
								 NuclosLocale.class.getCanonicalName(), null);
						dataSet.setMethodBody("\tsetField(\""
									+ getUIDStringforClass(val)
									+ "\", pNuclosLocale, p"
									+ formatMethodName(escapeJavaIdentifierPart(adjustName(val))) + ");");
						
						dataSet.addParameter(param2);
						dataSet.addParameter(param);
						newMeta.addMethod(dataSet);
					}

					NuclosBusinessObjectMethodMetaData dataGetAllLocalized = new NuclosBusinessObjectMethodMetaData(
							getGetterName(adjustName(val)),
							NuclosBusinessObjectMethodMetaData.PUBLIC,
							List.class.getCanonicalName() + "<" + NuclosBusinessObjectSourceBuilder.extractDataType(val.getDataType()) + ">",
							false,
							getBOMethodJavadoc(val, MethodType.GETTER)
					);
							dataGetAllLocalized.addParameter(
									new NuclosBusinessObjectAttributeMetaData("pNuclosLocales", 
											null, List.class.getCanonicalName() + "<" + NuclosLocale.class.getCanonicalName() + ">", null));	
							dataGetAllLocalized.setMethodBody("\treturn getFieldLocalizedValues(\""
									+ getUIDStringforClass(val)
									+ "\", " 
									+ "pNuclosLocales); ");
						newMeta.addMethod(dataGetAllLocalized);
						
						if ( (!entity.isUidEntity() && 
								(!isExludedElementSetter(val) ||
								(entity.isProxy())))) {
							
							NuclosBusinessObjectMethodMetaData dataSetAllLocalized = new NuclosBusinessObjectMethodMetaData(
									getSetterName(adjustName(val)),
									NuclosBusinessObjectMethodMetaData.PUBLIC,
									NuclosBusinessObjectMethodMetaData.VOID,
									false,
									getBOMethodJavadoc(val, MethodType.SETTER)
							);

							NuclosBusinessObjectAttributeMetaData paramAllLocalized = new NuclosBusinessObjectAttributeMetaData(
									"p" + formatMethodName(escapeJavaIdentifierPart(adjustName(val))),
									null,
									List.class.getCanonicalName() + "<" + NuclosBusinessObjectSourceBuilder.extractDataType(val.getDataType()) + ">",
									null
							);
							NuclosBusinessObjectAttributeMetaData param2AllLocalized = new NuclosBusinessObjectAttributeMetaData(
									"pNuclosLocales" , null,
									List.class.getCanonicalName() + "<" + NuclosLocale.class.getCanonicalName() + ">", null);
							dataSetAllLocalized.setMethodBody("\tsetFieldLocalizedValues(\""
										+ getUIDStringforClass(val)
										+ "\", pNuclosLocales, p"
										+ formatMethodName(escapeJavaIdentifierPart(adjustName(val))) + ");");
							
							dataSetAllLocalized.addParameter(param2AllLocalized);
							dataSetAllLocalized.addParameter(paramAllLocalized);
							newMeta.addMethod(dataSetAllLocalized);
								
						}
					}
				}

			// Setter (simple attribute)
			// Check against exclude list (e.g. changedAt, changedBy,
			// nuclosDeleted) but only if not proxy
			if (((!entity.isUidEntity() || BusinessObjectBuilderForInternalUse.getEntityMetas().contains(entity)) &&
					(!isExludedElementSetter(val) ||
					(entity.isProxy())))) {
				NuclosBusinessObjectMethodMetaData dataSet = new NuclosBusinessObjectMethodMetaData(
						getSetterName(adjustName(val)),
						NuclosBusinessObjectMethodMetaData.PUBLIC,
						NuclosBusinessObjectMethodMetaData.VOID,
						false,
						getBOMethodJavadoc(val, MethodType.SETTER)
				);
				final String paramName = "p" + formatMethodName(escapeJavaIdentifierPart(adjustName(val)));
				NuclosBusinessObjectAttributeMetaData param = new NuclosBusinessObjectAttributeMetaData(
						paramName,
						null,
						NuclosBusinessObjectSourceBuilder.extractDataType(val.getDataType()),
						null
				);
				if (generic) {
					dataSet.setMethodBody(getGenericFieldSetterMethodBody(entity, val, paramName));
				} else { // !generic
					if (NuclosBusinessObjectSourceBuilder.extractDataType(
							val.getDataType()).equals(
							BigDecimal.class.getCanonicalName())) {
						int scale = val.getPrecision() != null ? val.getPrecision() : 0;
						dataSet.setMethodBody("if(p"
								+ formatMethodName(escapeJavaIdentifierPart(adjustName(val))) + " != null) {\n\t\t"
								+ "p"
								+ formatMethodName(escapeJavaIdentifierPart(adjustName(val))) + " = "
								+ "p"
								+ formatMethodName(escapeJavaIdentifierPart(adjustName(val))) + ".setScale(" + scale + ", " + BigDecimal.class.getCanonicalName() + ".ROUND_HALF_UP);\n\t}\n\tsetField(\""
								+ val.getUID().getString()
								+ "\", p"
								+ formatMethodName(escapeJavaIdentifierPart(adjustName(val)))
								+ " != null ? new Double(p"
								+ formatMethodName(escapeJavaIdentifierPart(adjustName(val)))
								+ ".doubleValue()) : null); ");
					} else {
						dataSet.setMethodBody("\tsetField(\""
								+ getUIDStringforClass(val)
								+ "\", p"
								+ formatMethodName(escapeJavaIdentifierPart(adjustName(val))) + "); ");
					}
				}

				dataSet.addParameter(param);
				newMeta.addMethod(dataSet);
			}

		}
	}

	
	private boolean isExcludedCompletly(FieldMeta<?> val) {
		boolean retVal = false;

		if (val.getFieldName().equals("nuclosOrigin")) {
			retVal = true;
		} else if (val.getFieldName().equals("nuclosStateIcon")) {
			retVal = true;
		} 
		else if (NuclosDateTime.class.getCanonicalName().equals(
				val.getDataType())) {
			retVal = true;
		} else if (val.isCalcOndemand()) {
			retVal = true;
		}

		return retVal;
	}

	private boolean isExcluedQueryConstant(FieldMeta<?> val) {
		boolean retVal = false;

		if (val.getFieldName().equals("nuclosStateNumber")) {
			retVal = true;
		} else if (val.getFieldName().equals("nuclosStateIcon")) {
			retVal = true;
		}

		if (val instanceof IntegrationPointEntityMeta.IntegrationFieldMeta) {
			final IntegrationPointEntityMeta.IntegrationFieldMeta intFieldMeta = (IntegrationPointEntityMeta.IntegrationFieldMeta) val;
			//final NucletIntegrationPoint intPoint = intFieldMeta.getIntegrationPoint();
			final NucletIntegrationField intField = intFieldMeta.getIntegrationField();
			if (//Boolean.TRUE.equals(intPoint.getOptional()) ||
				Boolean.TRUE.equals(intField.getOptional())) {
				retVal = true;
			}
		}

		return retVal;
	}

	private boolean isExcluedElementGetterId(FieldMeta<?> val) {
		boolean retVal = false;

		if (val.getFieldName().equals("nuclosStateNumber")) {
			retVal = true;
		}

		return retVal;
	}
	
	private boolean isExcluedElementSetterId(FieldMeta<?> val) {
		boolean retVal = false;

		if (val.getFieldName().equals(SF.OWNER.getFieldName())) {
			retVal = true;
		}

		return retVal;		
	}
	
	private boolean isExcluedElementGetterBO(FieldMeta<?> val) {
		boolean retVal = isExcluedElementGetterId(val);

		final UID foreignEntityUID = LangUtils.defaultIfNull(val.getUnreferencedForeignEntity(), val.getForeignEntity());

		if (foreignEntityUID != null) {
			final EntityMeta<?> foreignMeta = metaProvider.getEntity(foreignEntityUID);
			if(E.isNuclosEntity(foreignEntityUID)
				&& !BusinessObjectBuilderForInternalUse.getEntityMetas().contains(foreignMeta)){
				retVal = true;
			}
		}

		return retVal;
	}

	private boolean isExludedElementSetter(FieldMeta<?> val) {
		boolean retVal = false;

		if (SF.CHANGEDBY.checkField(val.getFieldName())
						|| SF.CREATEDBY.checkField(val.getFieldName())
						|| SF.CHANGEDAT.checkField(val.getFieldName())
						|| SF.CREATEDAT.checkField(val.getFieldName())
						|| "Autonummer".equals(val.getDefaultComponentType()) // ??? @todo
						|| SF.VERSION.checkField(val.getFieldName())
						|| SF.IMPORTVERSION.checkField(val.getFieldName())
						|| SF.ORIGINUID.checkField(val.getFieldName())) {
			retVal = true;
		}
		return retVal;
	}

	private static String adjustName(FieldMeta<?> val) {
		if (SF.LOGICALDELETED.checkField(val.getFieldName())) {
			return "nuclosLogicalDeleted";
		} else if (SF.SYSTEMIDENTIFIER.checkField(val.getFieldName())) {
			return "nuclosSystemIdentifier";
		}
		return val.getFieldName();
	}

	/**
	 * This method creates, saves, compilesversion and jars all mapped NuclosBusinessMetaObject.
	 * 
	 * @param boMetaDatas BO meta data to create/compile/jar
	 * @throws NuclosCompileException
	 */
	private void compileNuclosBusinessObject(List<NuclosBusinessObjectMetaData> boMetaDatas)
		throws NuclosCompileException, InterruptedException {

		// Create Java sources
		NuclosBusinessObjectSourceBuilder builder = new NuclosBusinessObjectSourceBuilder(metaProvider);
		List<NuclosBusinessJavaSource> fileSources = builder.createJavaSources(boMetaDatas, NuclosCodegeneratorConstants.BO_SRC_FOLDER.toString());

		// Compile and jar classes
		nuclosBusinessObjectCompiler.compileSourcesAndJar(fileSources);

	}

	private static List<EntityMeta<?>> getSystemEntitiesForPublicUsage() {
		List<EntityMeta<?>> sysForPublic = new ArrayList<>();
		sysForPublic.add(E.PROCESS);
		sysForPublic.add(E.STATE);
		sysForPublic.add(E.ROLE);
		sysForPublic.add(E.GROUP);
		sysForPublic.add(E.ROLEUSER);
		sysForPublic.add(E.USER);
		sysForPublic.add(E.PRINTSERVICE);
		sysForPublic.add(E.PRINTSERVICE_TRAY);
		return sysForPublic;
	}

	/**
	 * This Method returns a list of all user defined entities and all
	 * systementities that are requiered to create Java Business-Objects
	 * @return List<EntityMeta>
	 */
	private List<EntityMeta<?>> getEntitiesToCreate() throws InterruptedException {
		List<EntityMeta<?>> entitesToCreate = new ArrayList<>();
		entitesToCreate.addAll(getSystemEntitiesForPublicUsage());
		entitesToCreate.addAll(BusinessObjectBuilderForInternalUse.getEntityMetas());
		
		Collection<EntityMeta<?>> allEntities = metaProvider.getAllEntities();
		for (EntityMeta<?> emDVO : allEntities) {
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
			if (!E.isNuclosEntity(emDVO.getUID()) && !NucletEntityMeta.isEntityLanguageUID(emDVO.getUID())) {
				entitesToCreate.add(emDVO);
			}
		}
		return entitesToCreate;
	}
	
	public static String getNucletPackageStatic(EntityMeta<?> emdVO, INucletCache nucletCache) {
		return getNucletPackageStatic(emdVO.getUID(), emdVO.getNuclet(), nucletCache);
	}
	
	public static String getNucletPackageStatic(UID entityUID, UID nucletUID, INucletCache nucletCache) {
		String retVal = (entityUID!=null && E.isNuclosEntity(entityUID)) ? DEFFAULT_PACKAGE_NUCLOS
				: DEFFAULT_PACKAGE_NUCLET;

		if (nucletUID != null) {
			String sFqn = nucletCache.getFullQualifiedNucletName(nucletUID);
			if (!StringUtils.looksEmpty(sFqn)) {
				retVal = sFqn;
			}
		}

		return retVal;
	}

	public String getBOJavadoc(EntityMeta meta) {
		
		StringBuilder sBuilder = new StringBuilder();
		
		sBuilder
			.append("\n\n/**\n")
			.append(meta.isIntegrationPoint() ? " * IntegrationPoint: " : " * BusinessObject: ").append(meta.getEntityName()).append("\n *<br>");
		
		// Comment
		if (meta.getComment() != null) {
			sBuilder.append("\n *<br>").append(validateJavaDocComment(meta.getComment()))
				.append("\n *<br>");
		}
		// BO specific elements
		String nucletPackage = getNucletPackageStatic(meta, metaProvider);
		
		sBuilder.append("\n *<br>Nuclet: ").append(nucletPackage);
		
		if(!meta.isDatasourceBased()) {
			sBuilder.append("\n *<br>DB-Name: ").append(meta.getDbTable());
		}
		
		sBuilder
			.append("\n *<br>Writable: ").append(meta.isWritable())
			.append("\n *<br>Localized: ").append(meta.IsLocalized())
			.append("\n *<br>Statemodel: ").append(meta.isStateModel())
			.append("\n**/");
		return sBuilder.toString();
	}
	
	public static String getBoHeader() {
		StringBuilder sBuilder = new StringBuilder();
		
		sBuilder
			.append("//Copyright (C) 2010  Novabit Informationssysteme GmbH")
			.append("\n//")
			.append("\n//This file is part of Nuclos.")
			.append("\n//")
			.append("\n//Nuclos is free software: you can redistribute it and/or modify")
			.append(
				"\n//it under the terms of the GNU Affero General Public License as published by")
			.append("\n//the Free Software Foundation, either version 3 of the License, or")
			.append("\n//(at your option) any later version.")
			.append("\n//")
			.append("\n//Nuclos is distributed in the hope that it will be useful,")
			.append("\n//but WITHOUT ANY WARRANTY; without even the implied warranty of")
			.append("\n//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the")
			.append("\n//GNU Affero General Public License for more details.")
			.append("\n//")
			.append("\n//You should have received a copy of the GNU Affero General Public License")
			.append("\n//along with Nuclos.  If not, see <http://www.gnu.org/licenses/>.\n\n\n");
		
		return sBuilder.toString();
	}
	public String getBOAttributeJavadoc(FieldMeta meta) {
		StringBuilder sBuilder = new StringBuilder();
		
		sBuilder.append("\n\n/**\n");
		sBuilder.append(" * Attribute: ").append(meta.getFieldName()).append("\n *<br>");
		
		// Comment
		if (meta.getComment() != null) {
			sBuilder.append("\n *<br>").append(validateJavaDocComment(meta.getComment())).append("\n *<br>");
		}
		
		sBuilder
			.append("\n *<br>Entity: ").append(
			metaProvider.getEntity(meta.getEntity()).getEntityName())
			.append("\n *<br>DB-Name: ").append(meta.getDbColumn())
			.append("\n *<br>Data type: ").append(meta.getDataType())
			.append("\n *<br>Localized: ").append(meta.isLocalized())
			.append("\n *<br>Output format: ").append(meta.getFormatOutput())
			.append("\n *<br>Scale: ").append(meta.getScale())
			.append("\n *<br>Precision: ").append(meta.getPrecision())
		
			.append("\n**/");
		
		return sBuilder.toString();
	}
	
	public String getBOMethodJavadoc(FieldMeta meta, MethodType type) {
		StringBuilder sBuilder = new StringBuilder();
		
		sBuilder
			.append("\n\n/**\n")
			.append(" * ").append(type.toString())
			.append(" for attribute: ").append(meta.getFieldName()).append("\n *<br>");
		
		// Comment
		if (meta.getComment() != null) {
			sBuilder.append("\n *<br>Attribute Information:<br>")
				.append(validateJavaDocComment(meta.getComment())).append("\n *<br>");
		}
		
		sBuilder.append("\n *<br>Entity: ")
			.append(metaProvider.getEntity(meta.getEntity()).getEntityName())
			.append("\n *<br>DB-Name: ").append(meta.getDbColumn())
			.append("\n *<br>Data type: ").append(meta.getDataType());
		
		if (meta.getForeignEntity() != null)
			sBuilder.append("\n *<br>Reference entity: ")
				.append(metaProvider.getEntity(meta.getForeignEntity()).getEntityName());
		if (meta.getForeignEntityField() != null) {
			ForeignEntityFieldUIDParser parser = 
					new ForeignEntityFieldUIDParser(meta, metaProvider);	
			
			StringBuilder sValue = new StringBuilder();
			for (IFieldUIDRef ref : parser) {
				if (ref.isConstant()) {
					sValue.append(ref.getConstant());
				}
				if (ref.isUID()) {
					String fieldName = "";
					if (metaProvider.getEntity(meta.getForeignEntity()).hasField(ref.getUID())) {
						fieldName =
							metaProvider.getEntity(meta.getForeignEntity()).getField(ref.getUID())
								.getFieldName();
					} else if (metaProvider.getEntity(meta.getEntity()).hasField(ref.getUID())) {
						fieldName =
							metaProvider.getEntity(meta.getEntity()).getField(ref.getUID())
								.getFieldName();
					}
					sValue.append(fieldName);
				}
			}
			
			sBuilder.append("\n *<br>Reference field: ").append(sValue.toString());
			
		}
		sBuilder
			.append("\n *<br>Localized: ").append(meta.isLocalized())
			.append("\n *<br>Output format: ").append(meta.getFormatOutput())
			.append("\n *<br>Scale: ").append(meta.getScale())
			.append("\n *<br>Precision: ").append(meta.getPrecision())
			.append("\n**/");
		return sBuilder.toString();
	}
	
	private static String validateJavaDocComment(String value) {
		if (value == null)
			return null;
		
		String retVal = NuclosEntityValidator.escapeMutatedVowel(value);
		// No chars that are used in comments can be added
		retVal = retVal.replace("/*", "");
		retVal = retVal.replace("*/", "");
		// Replace new line to html <br> and add comment symbol
		retVal = retVal.replace("\n", "\n *<br>");
		
		return retVal;
	}

	private String getGenericFieldGetterMethodBody(EntityMeta<?> entityMeta, FieldMeta<?> fieldMeta) {
		return this.getGenericFieldGetterMethodBody(entityMeta, fieldMeta, null);
	}

	private String getGenericFieldGetterMethodBody(EntityMeta<?> entityMeta, FieldMeta<?> fieldMeta, final String methodSuffix) {
		return this.getGenericFieldGetterMethodBody(entityMeta, fieldMeta, methodSuffix, null);
	}

	private String getGenericFieldGetterMethodBody(EntityMeta<?> entityMeta, FieldMeta<?> fieldMeta, final String methodSuffix, final String paramName) {
		return this.getGenericFieldGetterMethodBody(entityMeta, fieldMeta, methodSuffix, paramName, false);
	}

	private String getGenericFieldGetterMethodBody(final EntityMeta<?> entityMeta, final FieldMeta<?> fieldMeta, final String methodSuffix, final String paramName, boolean ignoreThinImplementingEntities) {
		final StringBuilder sBody = new StringBuilder();
		sBody.append("// all implemented BOs:\n");
		AbstractGenericImplementationBuilder builder = new AbstractGenericImplementationBuilder(entityMeta, fieldMeta) {
			@Override
			public void build(final String implClassNameWithPackage, final String implFieldName, final FieldMeta<?> implFieldMeta) {
				sBody.append("\tif (this.businessObject instanceof ").append(implClassNameWithPackage).append(") {\n");

				sBody.append("\t\treturn ");
				if ("BO".equals(methodSuffix)) {
					final EntityMeta<?> refEntityMeta = metaProvider.getEntity(fieldMeta.getForeignEntity());
					final String refClassNameWithPackage = getClassNameWithPackage(refEntityMeta);
					sBody.append("new ").append(refClassNameWithPackage).append("(");
				}
				sBody.append("((").append(implClassNameWithPackage).append(") this.businessObject).").append(getGetterName(implFieldName));
				if (methodSuffix != null) {
					sBody.append(methodSuffix);
				}
				sBody.append('(');
				if (paramName != null) {
					sBody.append(paramName);
				}
				sBody.append(')');
				if ("BO".equals(methodSuffix)) {
					sBody.append(')');
				}
				sBody.append(";\n");
				sBody.append("\t}\n");
			}
		};
		builder.setIgnoreThinImplementingEntities(ignoreThinImplementingEntities);
		builder.run();
		sBody.append("\t// not implemented attribute:\n");
		sBody.append("\treturn null;");
		return sBody.toString();
	}

	private String getGenericFieldSetterMethodBody(EntityMeta<?> entityMeta, FieldMeta<?> fieldMeta, final String paramName) {
		return getGenericFieldSetterMethodBody(entityMeta, fieldMeta, false, paramName);
	}

	private String getGenericFieldSetterMethodBody(EntityMeta<?> entityMeta, FieldMeta<?> fieldMeta, final boolean isId, final String paramName) {
		final StringBuilder sBody = new StringBuilder();
		sBody.append("// all implemented BOs:\n");
		new AbstractGenericImplementationBuilder(entityMeta, fieldMeta) {
			@Override
			public void build(final String implClassNameWithPackage, final String implFieldName, final FieldMeta<?> implFieldMeta) {
				sBody.append("\tif (this.businessObject instanceof ").append(implClassNameWithPackage).append(") {\n");
				sBody.append("\t\t((").append(implClassNameWithPackage).append(") this.businessObject).").append(getSetterName(implFieldName));
				if (isId) {
					sBody.append("Id");
				}
				sBody.append('(').append(paramName).append(");\n");
				sBody.append("\t}\n");
			}
		}.run();
		return sBody.toString();
	}

	/*
		if (this.businessObject instanceof example.rest.Order) {
			for (example.rest.OrderPosition sub : ((example.rest.Order) this.businessObject).getOrderPosition(flags)) {
				example.rest.NumberableSubGO subGBO = new example.rest.NumberableSubGO(sub);
				result.add(subGBO);
			}
		}
	 */
	private String getGenericDependentGetterMethodBody(EntityMeta<?> entityMeta, FieldMeta<?> subRefFieldMeta, final DependentBuilderHelper dependentBuilderHelper) {
		final StringBuilder sBody = new StringBuilder();
		final String genSubClassNameWithPackage = getClassNameWithPackage(entityMeta);

		sBody.append("List<").append(genSubClassNameWithPackage).append("> result = new ArrayList<>();\n");
		new AbstractGenericImplementationBuilder(entityMeta, subRefFieldMeta) {
			@Override
			public void build(final String implClassNameWithPackage, final String implFieldName, final FieldMeta<?> implFieldMeta) {
				final EntityMeta<?> parentEntityMeta = metaProvider.getEntity(implFieldMeta.getForeignEntity());
				final MultiListMap<String, FieldMeta<?>> dependentMethodNameMap = dependentBuilderHelper.getDependentMethodNameMap(parentEntityMeta.getUID());
				for (String dependentMethodName : dependentMethodNameMap.keySet()) {
					final List<FieldMeta<?>> dependentRefFieldMetaList = dependentMethodNameMap.getValues(dependentMethodName);
					final boolean multipleReferences = dependentRefFieldMetaList.size() > 1;
					for (int idx = 0; idx < dependentRefFieldMetaList.size(); idx++) {
						final FieldMeta<?> refFieldMeta = dependentRefFieldMetaList.get(idx);
						if (refFieldMeta.getUID().equals(implFieldMeta.getUID())) {
							String implGetterMethod = dependentBuilderHelper.createCompleteDependentMethodName("get", dependentMethodName, multipleReferences, idx);
							final String implSubClassNameWithPackage = getClassNameWithPackage(metaProvider.getEntity(implFieldMeta.getEntity()));
							final String implParentClassNameWithPackage = getClassNameWithPackage(parentEntityMeta);
							sBody.append("\tif (this.businessObject instanceof ").append(implParentClassNameWithPackage).append(") {\n");
							sBody.append("\t\tfor (").append(implSubClassNameWithPackage).append(" sub : ((").append(implParentClassNameWithPackage).append(") this.businessObject).")
									.append(implGetterMethod).append("(flags)) {\n");
							sBody.append("\t\t\t").append(genSubClassNameWithPackage).append(" subGBO = new ").append(genSubClassNameWithPackage).append("(sub);\n");
							sBody.append("\t\t\tresult.add(subGBO);\n");
							sBody.append("\t\t}\n");
							sBody.append("\t}\n");
						}
					}
				}
			}
		}.run();
		sBody.append("\treturn result;\n");
		return sBody.toString();
	}

	/*
		if (this.businessObject instanceof example.rest.Order) {
			((example.rest.Order) this.businessObject).insertOrderPosition((OrderPosition)pNumberableSubGO.getBusinessObject());
		}
	 */
	private String getGenericDependentMethodBodyForType(EntityMeta<?> entityMeta, FieldMeta<?> subRefFieldMeta, final String paramName,
														final MethodType methodType, final DependentBuilderHelper dependentBuilderHelper) {
		final StringBuilder sBody = new StringBuilder();
		final String genSubClassNameWithPackage = getClassNameWithPackage(entityMeta);
		final String type;
		switch (methodType) {
			case INSERT: type = "insert"; break;
			case DELETE: type = "delete"; break;
			default: throw new IllegalArgumentException();
		}

		new AbstractGenericImplementationBuilder(entityMeta, subRefFieldMeta) {
			@Override
			public void build(final String implClassNameWithPackage, final String implFieldName, final FieldMeta<?> implFieldMeta) {
				final EntityMeta<?> parentEntityMeta = metaProvider.getEntity(implFieldMeta.getForeignEntity());
				final MultiListMap<String, FieldMeta<?>> dependentMethodNameMap = dependentBuilderHelper.getDependentMethodNameMap(parentEntityMeta.getUID());
				for (String dependentMethodName : dependentMethodNameMap.keySet()) {
					final List<FieldMeta<?>> dependentRefFieldMetaList = dependentMethodNameMap.getValues(dependentMethodName);
					final boolean multipleReferences = dependentRefFieldMetaList.size() > 1;
					for (int idx = 0; idx < dependentRefFieldMetaList.size(); idx++) {
						final FieldMeta<?> refFieldMeta = dependentRefFieldMetaList.get(idx);
						if (refFieldMeta.getUID().equals(implFieldMeta.getUID())) {
							String implMethod = dependentBuilderHelper.createCompleteDependentMethodName(type, dependentMethodName, multipleReferences, idx);
							final String implSubClassNameWithPackage = getClassNameWithPackage(metaProvider.getEntity(implFieldMeta.getEntity()));
							final String implParentClassNameWithPackage = getClassNameWithPackage(parentEntityMeta);

							sBody.append("if (this.businessObject instanceof ").append(implParentClassNameWithPackage).append(") {\n");
							sBody.append("\t\t((").append(implParentClassNameWithPackage).append(") this.businessObject).")
									.append(implMethod).append("((").append(implSubClassNameWithPackage).append(")").append(paramName).append(".getBusinessObject());\n");
							sBody.append("\t}\n");
						}
					}
				}
			}
		}.run();
		return sBody.toString();
	}

	abstract class AbstractGenericImplementationBuilder {

		private final EntityMeta<?> genericEntityMeta;

		private final FieldMeta<?> genericFieldMeta;

		private boolean ignoreThinImplementingEntities;

		public void setIgnoreThinImplementingEntities(final boolean ignoreThinImplementingEntities) {
			this.ignoreThinImplementingEntities = ignoreThinImplementingEntities;
		}

		protected AbstractGenericImplementationBuilder(final EntityMeta<?> genericEntityMeta) {
			this(genericEntityMeta, null);
		}

		protected AbstractGenericImplementationBuilder(final EntityMeta<?> genericEntityMeta, final FieldMeta<?> genericFieldMeta) {
			this.genericEntityMeta = genericEntityMeta;
			this.genericFieldMeta = genericFieldMeta;
		}

		public void run() {
			final List<EntityObjectVO<UID>> implementingEntityDetails = metaProvider.getImplementingEntityDetails(genericEntityMeta.getUID());
			for (EntityObjectVO<UID> implDetail : implementingEntityDetails) {
				final UID implementingEntityUID = implDetail.getFieldUid(E.ENTITY_GENERIC_IMPLEMENTATION.implementingEntity);
				final EntityMeta<?> implementingEntityMeta = metaProvider.getEntity(implementingEntityUID);
				if (ignoreThinImplementingEntities && implementingEntityMeta.isThin()) {
					// ignore thin
					continue;
				}
				final String implementingEntityPackage = getNucletPackageStatic(implementingEntityMeta, metaProvider);
				final String implementingEntityFormattedName = getNameForFqn(implementingEntityMeta.getEntityName());
				final String implClassNameWithPackage = implementingEntityPackage + "." + implementingEntityFormattedName;

				if (genericFieldMeta != null) {
					FieldMeta<?> implFieldMeta = null;
					if (SF.CREATEDBY.checkField(genericFieldMeta.getFieldName())) {
						implFieldMeta = SF.CREATEDBY.getMetaData(implementingEntityUID);
					} else if (SF.CREATEDAT.checkField(genericFieldMeta.getFieldName())) {
						implFieldMeta = SF.CREATEDAT.getMetaData(implementingEntityUID);
					} else if (SF.CHANGEDBY.checkField(genericFieldMeta.getFieldName())) {
						implFieldMeta = SF.CHANGEDBY.getMetaData(implementingEntityUID);
					} else if (SF.CHANGEDAT.checkField(genericFieldMeta.getFieldName())) {
						implFieldMeta = SF.CHANGEDAT.getMetaData(implementingEntityUID);
					} else {
						EntityObjectVO<UID> implField = null;
						final List<EntityObjectVO<UID>> implementingFieldMappings = metaProvider.getImplementingFieldMapping(implDetail.getPrimaryKey());
						for (EntityObjectVO<UID> implFieldMapping : implementingFieldMappings) {
							if (implFieldMapping.getFieldUid(E.ENTITY_GENERIC_FIELDMAPPING.genericField).equals(genericFieldMeta.getUID())) {
								implField = implFieldMapping;
							}
						}
						if (implField != null) {
							implFieldMeta = metaProvider.getEntityField(implField.getFieldUid(E.ENTITY_GENERIC_FIELDMAPPING.implementingField));
						}
					}

					if (implFieldMeta != null) {
						final String implFieldName = adjustName(implFieldMeta);
						build(implClassNameWithPackage, implFieldName, implFieldMeta);
					} // else: not implemented (optional)... ignore it
				} else {
					build(implClassNameWithPackage, null, null);
				}
			}
		}

		public abstract void build(String implClassNameWithPackage, String implFieldName, final FieldMeta<?> implFieldMeta);

	}

	private String getClassNameWithPackage(EntityMeta<?> entityMeta) {
		String formatEntity = escapeJavaIdentifier(entityMeta.getEntityName(), DEFAULT_ENTITY_PREFIX);
		return getNucletPackageStatic(entityMeta, metaProvider) + "." + formatEntity;
	}

	/**
	  There two ways to have duplicates
	  1. Entity has two fields with a reference on the same foreign entity
	  2. Two different entities with save name (via different nuclets) having one or more references
	     to the same foreign entity
	     In this case we must numerate method-names as well to avoid compile errors
	 */
	private class DependentBuilderHelper {

		private final List<EntityMeta<?>> allEntitiesSorted;

		//            <"parentEntityUID", MultiListMap<"dependentMethodName", "refFieldMeta">>
		private final Map<UID, MultiListMap<String, FieldMeta<?>>> dependentMethodNameMap = new HashMap<>();

		public DependentBuilderHelper() {
			allEntitiesSorted = new ArrayList<>(metaProvider.getAllEntities());
			final Iterator<EntityMeta<?>> entityIterator = allEntitiesSorted.iterator();
			while (entityIterator.hasNext()) {
				final EntityMeta<?> entityMeta = entityIterator.next();
				// only non-system-entities
				if ((E.isNuclosEntity(entityMeta.getUID())
							&& !BusinessObjectBuilderForInternalUse.getEntityMetas().contains(entityMeta))
						|| NucletEntityMeta.isEntityLanguageUID(entityMeta.getUID())) {
					entityIterator.remove();
				}
			}
			Collections.sort(allEntitiesSorted, new Comparator<EntityMeta<?>>() {
				@Override
				public int compare(final EntityMeta<?> o1, final EntityMeta<?> o2) {
					final String classNameWithPackage1 = getClassNameWithPackage(o1);
					final String classNameWithPackage2 = getClassNameWithPackage(o2);
					return classNameWithPackage1.compareTo(classNameWithPackage2);
				}
			});

			// a temporary map for better performance
			//    <"dependentMetaUID", "refFieldMeta">
			final MultiListMap<UID, FieldMeta<?>> allDependentReferencesSorted = new MultiListHashMap<>();
			for (EntityMeta<?> dependentEntityMeta : allEntitiesSorted) {
				final List<FieldMeta<?>> refFieldsSorted = new ArrayList<>(metaProvider.getAllEntityFieldsByEntity(dependentEntityMeta.getUID()).values());
				final Iterator<FieldMeta<?>> fieldIterator = refFieldsSorted.iterator();
				while (fieldIterator.hasNext()) {
					final FieldMeta<?> fieldMeta = fieldIterator.next();
					// We only need references on our entity
					if (fieldMeta.getForeignEntity() == null && fieldMeta.getForeignIntegrationPoint() == null) {
						fieldIterator.remove();
					}
				}
				// Sort the attributes for multiple references !!! (NUCLOS-3692)
				Collections.sort(refFieldsSorted, new Comparator<FieldMeta<?>>() {
					@Override
					public int compare(FieldMeta<?> fm1, FieldMeta<?> fm2) {
						return RigidUtils.compare(fm1.getOrder(), fm2.getOrder());
					}
				});
				allDependentReferencesSorted.addAllValues(dependentEntityMeta.getUID(), refFieldsSorted);
			}

			// build DependentMethodNameMap
			for (EntityMeta<?> parentEntityMeta : allEntitiesSorted) {
				final MultiListMap<String, FieldMeta<?>> methodNameMap = new MultiListHashMap<>();
				for (EntityMeta<?> dependentEntityMeta : allEntitiesSorted) {
					final String dependentMethodNameWithoutIndex = getDependentMethodNameWithoutIndex(dependentEntityMeta);

					for (FieldMeta<?> refFieldMeta : allDependentReferencesSorted.getValues(dependentEntityMeta.getUID())) {
						// We only need references on the parent entity
						if (parentEntityMeta.getUID().equals(RigidUtils.defaultIfNull(refFieldMeta.getForeignIntegrationPoint(), refFieldMeta.getForeignEntity()))) {
							methodNameMap.addValue(dependentMethodNameWithoutIndex, refFieldMeta);
						}
					}
				}
				// for integration points remove origin foreignkey references. Otherwise we generate two getters for both types of BO
				for (String dependentMethodNameWithoutIndex : methodNameMap.keySet()) {
					final List<FieldMeta<?>> refFieldMetas = methodNameMap.getValues(dependentMethodNameWithoutIndex);
					final Collection<IntegrationPointEntityMeta.IntegrationFieldMeta> integrationFields = new ArrayList<>();
					final Collection<FieldMeta<?>> otherFields = new ArrayList<>();
					for (FieldMeta<?> refFieldMeta : refFieldMetas) {
						if (refFieldMeta.isIntegrationField()) {
							integrationFields.add((IntegrationPointEntityMeta.IntegrationFieldMeta) refFieldMeta);
						} else {
							otherFields.add(refFieldMeta);
						}
					}
					for (IntegrationPointEntityMeta.IntegrationFieldMeta integrationFieldMeta : integrationFields) {
						final UID targetFieldId = (UID) integrationFieldMeta.getIntegrationField().getTargetFieldId();
						for (FieldMeta<?> refFieldMeta : otherFields) {
							if (refFieldMeta.getUID().equals(targetFieldId)) {
								methodNameMap.removeValue(dependentMethodNameWithoutIndex, refFieldMeta);
							}
						}
					}
				}
				dependentMethodNameMap.put(parentEntityMeta.getUID(), methodNameMap);
			}
		}

		public MultiListMap<String, FieldMeta<?>> getDependentMethodNameMap(UID parentEntityUID) {
			return dependentMethodNameMap.get(parentEntityUID);
		}

		public String getDependentMethodNameWithoutIndex(EntityMeta<?> dependentEntityMeta) {
			final boolean bForInternalUseOnly = dependentEntityMeta.isUidEntity() && BusinessObjectBuilderForInternalUse.getEntityMetas().contains(dependentEntityMeta);
			final String dependentClassName = getNameForFqn(getFormattedEntityName(dependentEntityMeta, bForInternalUseOnly));
			return dependentClassName;
		}

		public String createCompleteDependentMethodName(String prefix, String dependentMethodName, boolean multipleReferences, int idx) {
			if (prefix == null) {
				throw new IllegalArgumentException();
			}
			String result = prefix +  org.apache.commons.lang.StringUtils.capitalize(dependentMethodName);
			if (multipleReferences) {
				result = result + (idx+1);
			}
			return result;
		}

		public List<EntityMeta<?>> getAllEntitiesSorted() {
			return allEntitiesSorted;
		}
	}
}
