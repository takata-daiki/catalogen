/*-
 * #%L
 * OpenIcar Core Components
 * %%
 * Copyright (C) 2009 - 2017 COMSOFT, JSC
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.comsoft.system;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.comsoft.meta.MetaException;
import org.comsoft.meta.MetaField;
import org.comsoft.meta.MetaObject;
import org.comsoft.meta.seam.persistence.ManyToOne;
import org.comsoft.meta.seam.persistence.OneToMany;
import org.comsoft.meta.seam.service.EntityHomeMode;
import org.comsoft.system.cache.UseCache;
import org.comsoft.system.controllers.BaseEntityHomeController;
import org.comsoft.system.controllers.OperationInvokingException;
import org.comsoft.system.hql.HqlBuilder;
import org.comsoft.system.model.ModelUtils;
import org.comsoft.system.query.QueryUtils;
import org.comsoft.system.util.EntityUtils;
import org.comsoft.system.util.OpenicarLogger;
import org.comsoft.test.SimpleProfiler;
import org.comsoft.velogen.TextUtils;
import org.hibernate.collection.internal.PersistentBag;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.core.Expressions;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.persistence.PersistenceProvider;
import org.jboss.seam.security.AuthorizationException;
import org.jboss.seam.transaction.Transaction;

import org.comsoft.system.i18n.Messages;

public class BaseEntityHome<T> extends EntityHome<T> {

	private static final long serialVersionUID = 1L;

	@In
	ModelUtils modelUtils;

	protected org.comsoft.meta.seam.service.EntityHome entityHomeMetaElement;

	protected org.comsoft.meta.seam.service.EntityHome getEntityHomeMetaElement() {
		if (entityHomeMetaElement == null) {
			try {
				MetaObject entityObject = (MetaObject) modelUtils.getElementByClass(getClass());
				entityHomeMetaElement = modelUtils.wrapMetaElementAnnotation(entityObject, org.comsoft.meta.seam.service.EntityHome.class);
			} catch (MetaException e) {
				e.printStackTrace();
			}
		}
		return entityHomeMetaElement;
	}

	protected Map<String, EntityHomeMode> definedModes;

	public Map<String, EntityHomeMode> getDefinedModes() {
		if (definedModes == null) {
			definedModes = new HashMap<String, EntityHomeMode>();
			entityHomeMetaElement = getEntityHomeMetaElement();
			if (entityHomeMetaElement != null && entityHomeMetaElement.getModes() != null) {
				for (EntityHomeMode entityHomeMode : entityHomeMetaElement.getModes()) {
					definedModes.put(entityHomeMode.getName(), entityHomeMode);
				}
			}
		}
		return definedModes;
	}

	public boolean isControllable() {
		return true;
	}

	private boolean stateless = false;

	private boolean editMode = false;
	private boolean viewMode = false;
	private String mode;

	public boolean isEditMode() {
		if (getMasterHome() != null) return getMasterHome().isEditMode();
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public boolean isViewMode() {
		if (getMasterHome() != null) return getMasterHome().isViewMode();
		return viewMode;
	}

	public void setViewMode(boolean viewMode) {
		this.viewMode = viewMode;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String modeName) {
		this.mode = modeName;
	}

	/**
	 * @return the stateless
	 */
	public boolean isStateless() {
		return stateless;
	}

	public EntityHomeMode getModeObject() {
		return getModeObject(getMode());
	}

	public EntityHomeMode getModeObject(String modeName) {
		if (modeName == null) return null;
		return getDefinedModes().get(modeName);
	}

	private BaseEntityHome<?> masterHome;

	public BaseEntityHome<?> getMasterHome() {
		return masterHome;
	}

	public void setMasterHome(BaseEntityHome<?> masterHome) {
		this.masterHome = masterHome;
	}

	private Object activeTab = null;

	public Object getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(Object activeTab) {
		this.activeTab = activeTab;
	}

	protected EntityUtils getEntityUtils() {
		return (EntityUtils) Component.getInstance(EntityUtils.class);
	}

	//	public void setId(Object id) {
	//		super.setId(id);
	//
	//		refreshDetails();
	//	}

	@Override
	public void setInstance(T instance) {
		super.setInstance(instance);

		refreshDetails();
	}

	@Override
	protected T createInstance() {
		T instance = super.createInstance();
		try {
			if (instance == null) {
				instance = getEntityClass().newInstance();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		try {
			initEntity(instance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		refreshDetails();

		return instance;
	}

	private void initEntity(T instance) throws Exception {
		getEntityUtils().initEntity(instance);
		if (getController() != null) {
			getController().initEntityInstance(instance);
		}
	}

	public T getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	protected org.comsoft.system.EntityHomeController<T, EntityHome<T>> controller;

	@SuppressWarnings({ "unchecked" })
	public org.comsoft.system.EntityHomeController<T, EntityHome<T>> getController() {
		if (controller == null) {
			String modelElementPath = modelUtils.getElementPathByClass(this.getClass());
			if (modelElementPath != null) {
				String controllerName = modelUtils.getEntityHomeControllerName(modelElementPath);
				if (controllerName != null) {
					controller = (org.comsoft.system.EntityHomeController<T, EntityHome<T>>) org.jboss.seam.Component.getInstance(controllerName, ScopeType.STATELESS, true);
					if (controller != null) {
						controller.setHome(this);
					}
				}
			}
			if (controller == null) {
				controller = (org.comsoft.system.EntityHomeController<T, EntityHome<T>>) org.jboss.seam.Component.getInstance(BaseEntityHomeController.class, ScopeType.STATELESS, true);
				controller.setHome(this);
			}
		}
		return controller;
	}

	private String[] supportedActions;

	public String[] getSupportedActions() {
		if (supportedActions == null) {
			supportedActions = new String[] { "EDIT", "VIEW", "SAVE", "REMOVE" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			if (getController() != null) {
				for (Operation operation : getController().getOperations()) {
					supportedActions = (String[]) ArrayUtils.add(supportedActions, operation.getCode());
				}
			}
		}
		return supportedActions;
	}

	protected org.comsoft.system.EntityFieldParams getFieldParamsFromModel(String fieldName) {
		String modelElementPath = modelUtils.getElementPathByClass(this.getClass());
		if (modelElementPath != null) {
			org.comsoft.system.EntityFieldParams result = modelUtils.getEntityFieldParams(modelElementPath, fieldName);
			if (result != null) {
				result = result.clone();
				// result.setEntityInstance(getInstance());
				return result;
			} else {
				return null;
			}
		}
		return null;
	}

	@UseCache
	public org.comsoft.system.EntityFieldParams getFieldParams(String fieldName) {
		SimpleProfiler.st("." + getEntityClass().getName() + "." + fieldName); //$NON-NLS-1$ //$NON-NLS-2$
		org.comsoft.system.EntityFieldParams fieldParams = null;
		if (/*isControllable() && */getController() != null) {
			fieldParams = getController().getFieldParams(fieldName);
		} else {
			fieldParams = getFieldParamsFromModel(fieldName);
		}
		if (fieldParams == null) {
			fieldParams = new org.comsoft.system.EntityFieldParams(fieldName);
		}

		EntityHomeMode entityHomeMode = null;
		if (StringUtils.isNotBlank(mode) && (entityHomeMode = getModeObject()) != null) {
			boolean enabled = false;
			boolean visible = true;

			if (entityHomeMode.getEnabledFields() != null && entityHomeMode.getEnabledFields().length > 0) {
				for (String enabledField : entityHomeMode.getEnabledFields()) {
					if (fieldName.equals(enabledField)) {
						enabled = true;
						break;
					}
				}
			}

			if (entityHomeMode.getVisibleFields() != null && entityHomeMode.getVisibleFields().length > 0) {
				visible = false;
				for (String visibleField : entityHomeMode.getVisibleFields()) {
					if (fieldName.equals(visibleField)) {
						visible = true;
						break;
					}
				}
			}

			if (entityHomeMode.getHiddenFields() != null && entityHomeMode.getHiddenFields().length > 0) {
				visible = true;
				for (String hiddenField : entityHomeMode.getHiddenFields()) {
					if (fieldName.equals(hiddenField)) {
						visible = false;
						break;
					}
				}
			}

			if (!enabled) fieldParams.setReadonlyExpression("true"); //$NON-NLS-1$
			if (!visible) fieldParams.setVisibleExpression("false"); //$NON-NLS-1$
		}

		fieldParams.setEntityInstance(getInstance());
		fieldParams.setEntityHome(this);
		fieldParams.clearCache();

		SimpleProfiler.en("." + getEntityClass().getName() + "." + fieldName); //$NON-NLS-1$ //$NON-NLS-2$
		return fieldParams;
	}

	public String save() throws Exception {
		info("save #0", instance); //$NON-NLS-1$
		return doOperation(BaseEntityHomeController.SAVE_OPERATION);
	}

	@Override
	public String remove() {
		try {
			return doOperation(BaseEntityHomeController.REMOVE_OPERATION);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Метод вызываемый из BaseEntityHomeController, выполняющий стандартную логику Супера
	 * + fixManyToOneFields - удаляет ссылки на удаляемую сущность из родительских коллекций,
	 * иначе при выполнении entityManager.flush происходит сохранение удаленной сущности
	 */
	public void superRemove() {
		try {
			fixManyToOneFields();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		super.remove();
	}

	@SuppressWarnings("rawtypes")
	protected void fixManyToOneFields() throws Exception {

		MetaObject entityObject = getModelObject();
		info("fixManyToOneFields for #0 #1", entityObject.getFullName(), getInstance()); //$NON-NLS-1$
		ModelUtils modelUtils = ModelUtils.instance();
		for (MetaField metaField : entityObject.getFields()) {

			ManyToOne manyToOne = modelUtils.wrapMetaElementAnnotation(metaField, ManyToOne.class);
			if (manyToOne != null) {
				info("found manyToOne  #0", metaField.getName()); //$NON-NLS-1$
				Object parentEntity = PropertyUtils.getProperty(getInstance(), metaField.getName());
				if (parentEntity != null) {
					MetaObject parentObject = (MetaObject) modelUtils.getElementByClass(parentEntity.getClass());
					if (parentObject != null) {
						for (MetaField parentField : parentObject .getFields())
							if (parentField.getTypeFullName().equals(entityObject.getFullName())) {
								info("found parent field #0.#1", parentObject.getFullName(), parentField.getName()); //$NON-NLS-1$
								OneToMany oneToMany = modelUtils.wrapMetaElementAnnotation(parentField, OneToMany.class);
								if ((oneToMany != null) && (oneToMany.getBackReferenceField().equals(metaField.getName()))) {

									Object collection = PropertyUtils.getProperty(parentEntity, parentField.getName());
									if ((collection != null) && (collection instanceof Collection<?>)) {
										info("collection field #0.#1", parentObject.getFullName(), parentField.getName()); //$NON-NLS-1$
										if (
												((collection instanceof PersistentBag) && ((PersistentBag) collection).wasInitialized())
												||
												(!(collection instanceof PersistentBag))

										) {
											info("removing from collection #0.#1", parentObject.getFullName(), parentField.getName()); //$NON-NLS-1$
											boolean remove = ((Collection) collection).remove(getInstance());
											info("removed : #0", remove); //$NON-NLS-1$
										}
									}

								}

							}

					}
				}


			}

		}

	}

	public boolean isWired() {
		return true;
	}

	public String doOperation(org.comsoft.system.Operation operation) throws Exception {
		if (/*isControllable() && */getController() != null) {
			info("doOperation(#0)", operation.getCode()); //$NON-NLS-1$
			try {
				/*
				if (BaseEntityHomeController.SAVE_OPERATION.equals(operation.getCode())) {
					checkPermission(BaseEntityHomeController.EDIT_OPERATION);
				} else {
					checkPermission(operation.getCode());
				}
				 */
				SimpleProfiler.st(operation.getCode());
				String res = getController().doOperation(operation);
				SimpleProfiler.en(operation.getCode());
				return res;
			} catch (Exception e) {
				info("doOperation(#0) - exception #1", operation.getCode(), e); //$NON-NLS-1$
				handleOperationException(e, operation);
			}
		}
		return null;
	}

	public String doOperation(String operationCode) throws Exception {
		if (/*isControllable() && */getController() != null) {
			return doOperation(findOperationByCode(getController().getOperations(), operationCode));
		}
		return null;
	}

	private Operation findOperationByCode(Operation[] operations, String operationCode) {
		for (Operation operation : operations) {
			if (operation.getCode().equals(operationCode)) {
				return operation;
			}
		}
		return null;
	}

	protected void handleOperationException(Exception exception, Operation operation) throws Exception {
		System.err.println("Handling exception for instance " + getId()); //$NON-NLS-1$
		OpenicarLogger.instance().error(this, Messages.getString("org.comsoft.BaseEntityHome.Log"), operation.getCode(), getEntityClass().getName(), getInstanceTitle(), getId(), exception.toString()); //$NON-NLS-1$
		exception.printStackTrace();
		if (isInteractiveMode() && FacesContext.getCurrentInstance() != null) { // устаревшее поведение для JSF
			//System.err.println("isInteractiveMode !");
			if (exception instanceof javax.persistence.PersistenceException) {
			} else if (exception instanceof OperationInvokingException) {
				// setInstance(null); - это приводит к перезачитыванию данных
			} else {
				throw exception;
			}

			/* -тут возникают ошибки типа Transaction is not active 
			try {
				refreshCaller();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			 */

			Transaction.instance().setRollbackOnly();

			StatusMessages.instance().add(StatusMessage.Severity.ERROR, exception.toString());
		} else {
			throw exception;
		}
	}

	public boolean isInteractiveMode() {
		return isEditMode() || isViewMode();
	}

	public boolean isReadonly() {
		boolean result = (getMasterHome() != null ? getMasterHome().isReadonly() : isViewMode());
		result = result || (getController() != null ? getController().isReadonly() : false);
		return result;
	}

	public void fieldValueChanged(String fieldName) {
		if (!(isReadonly() || getFieldParams(fieldName).isReadonly()) &&  /*isControllable() && */getController() != null) {
			Object value = null;
			try {
				value = PropertyUtils.getProperty(getInstance(), fieldName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			info("fieldValueChanged #0 = #1", fieldName, value); //$NON-NLS-1$
			getController().fieldValueChanged(fieldName);
		}
	}

	public String getBrowseViewId() {
		return null;
	}

	public String getEditViewId() {
		return null;
	}

	public String getInstanceTitle() {
		return modelUtils.getEntityTitle(this.getClass()) + "[" + getInstance().toString() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	private org.comsoft.system.NavigationCallback<T> viewCallback;

	@org.jboss.seam.annotations.Begin(nested = true, flushMode = org.jboss.seam.annotations.FlushModeType.MANUAL)
	public String beginView(org.comsoft.system.NavigationCallback<T> viewCallback) throws OperationInvokingException {
		nestedConversationReserved = true;
		return internalBeginView(viewCallback);
	}

	public String beginViewStateless(org.comsoft.system.NavigationCallback<T> viewCallback) throws OperationInvokingException {
		stateless = true;
		return internalBeginView(viewCallback);
	}

	protected String internalBeginView(org.comsoft.system.NavigationCallback<T> viewCallback) throws OperationInvokingException {
		checkPermission("VIEW"); //$NON-NLS-1$

		String browseViewId = getBrowseViewId();
		if (browseViewId == null) throw new OperationInvokingException(Messages.getString("org.comsoft.BaseEntityHome.Exception1")); //$NON-NLS-1$

		this.viewCallback = viewCallback;
		setViewMode(true);

		setInstance(getPersistedInstance(false));

		return browseViewId;
	}

	//@org.jboss.seam.annotations.End(beforeRedirect = true)
	public String endView() {
		return endView(nestedConversationReserved);
	}

	public String endView(boolean endConversation) {
		String callbackViewId = null;
		if (viewCallback != null) {
			callbackViewId = viewCallback.getCallbackViewId();
			viewCallback = null;
		}
		if (callbackViewId == null && !stateless) {
			org.jboss.seam.faces.FacesMessages.instance().add(StatusMessage.Severity.ERROR, Messages.getString("org.comsoft.BaseEntityHome.Error1")); //$NON-NLS-1$
		}

		setViewMode(false);

		if (endConversation) {
			ComponentUtils.instance().release(this, true);
			Conversation.instance().endBeforeRedirect();
		}
		return callbackViewId;
	}

	private org.comsoft.system.NavigationCallback<T> editCallback;

	public org.comsoft.system.NavigationCallback<T> getEditCallback() {
		return editCallback;
	}

	@org.jboss.seam.annotations.Begin(nested = true, flushMode = org.jboss.seam.annotations.FlushModeType.MANUAL)
	public String beginEdit(org.comsoft.system.NavigationCallback<T> editCallback) throws OperationInvokingException {
		nestedConversationReserved = true;
		return internalBeginEdit(editCallback);
	}

	protected String internalBeginEdit(org.comsoft.system.NavigationCallback<T> editCallback) throws OperationInvokingException {
		checkPermission("EDIT"); //$NON-NLS-1$

		String editViewId = getEditViewId();
		if (editViewId == null) throw new OperationInvokingException(Messages.getString("org.comsoft.BaseEntityHome.Exception2")); //$NON-NLS-1$
		this.editCallback = editCallback;

		setEditMode(true);

		if (!isPersisted()) {
			try {
				initEntity(getInstance());
			} catch (Exception e) {
				throw new OperationInvokingException(e);
			}
		} else {
			setInstance(getPersistedInstance(false));
		}

		return editViewId;
	}

	//only to EntityList, EntityForm, EntityField
	protected Boolean internalBeginEdit2(org.comsoft.system.NavigationCallback<T> editCallback) throws OperationInvokingException {
		boolean readonly = false;
		try {
			checkPermission("EDIT");
		} catch (Exception e) {
			checkPermission("VIEW");
			readonly = true;
		}	

		String editViewId = getEditViewId();
		if (editViewId == null) throw new OperationInvokingException(Messages.getString("org.comsoft.BaseEntityHome.Exception2")); //$NON-NLS-1$
		this.editCallback = editCallback;

		setEditMode(true);

		if (!isPersisted()) {
			try {
				initEntity(getInstance());
			} catch (Exception e) {
				throw new OperationInvokingException(e);
			}
		} else {
			setInstance(getPersistedInstance(false));
		}

		//return editViewId;
		return readonly;
	}

	public Boolean beginEditStateless(org.comsoft.system.NavigationCallback<T> editCallback) throws OperationInvokingException {
		stateless = true;
		return internalBeginEdit2(editCallback);
	}

	/**
	 * @deprecated
	 */
	//	@org.jboss.seam.annotations.End(beforeRedirect = true)
	@Deprecated
	public String acceptEdit() throws MetaException {
		try {
			return save();
		} catch (Exception e) {
			StatusMessages.instance().add(StatusMessage.Severity.ERROR, e.getMessage());
		}
		return null;
	}

	public String endEdit() throws Exception {
		return endEdit(nestedConversationReserved);
	}

	public String endEdit(boolean endConversation) throws Exception {
		if (isEditMode()) {
			info("endEdit"); //$NON-NLS-1$

			if (!isManaged() && isPersisted()) {
				setInstance(getPersistedInstance(false));
			}

			String result = doEditCallback(getInstance());

			setEditMode(false);

			if (endConversation) {
				ComponentUtils.instance().release(this, true);
				Conversation.instance().endBeforeRedirect();
			}

			return result;
		}
		return null;
	}

	public String doEditCallback(T instance) {
		refreshCaller();

		String callbackViewId = null;
		if (editCallback != null) {
			callbackViewId = editCallback.getCallbackViewId();
			if (editCallback.getCallbackMethod() != null && editCallback.getCallbackMethodObject() != null) {
				try {
					editCallback.getCallbackMethod().invoke(editCallback.getCallbackMethodObject(), instance);
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			editCallback = null;
		}

		if (callbackViewId == null && !stateless) {
			StatusMessages.instance().add(StatusMessage.Severity.ERROR, Messages.getString("org.comsoft.BaseEntityHome.Error2")); //$NON-NLS-1$
		}

		info("doEditCallback, returning #0", callbackViewId); //$NON-NLS-1$

		return callbackViewId;
	}

	public void refreshCaller() {
		if (viewCallback != null)
			viewCallback.doRefresh();
		if (editCallback != null)
			editCallback.doRefresh();
	}

	public boolean isPersisted() {
		if (super.isManaged()) return true;
		return getPersistedInstance(false) != null;
	}

	public T getPersistedInstance(boolean fresh) {
		if (fresh && super.isManaged()) {
			getEntityManager().refresh(instance);
			return instance;
		}
		T instance = getInstance();
		if (instance == null) return null;
		Object id = PersistenceProvider.instance().getId(instance, getEntityManager());
		if (id != null) {
			T foundInstance = getEntityManager().find(getEntityClass(), id);
			if (foundInstance == null) {
				try {
					String generatedIdField = modelUtils.getEntityGeneratedIdFieldName(getEntityClass());
					if (generatedIdField != null) {
						assignId(null);
						PropertyUtils.setProperty(getInstance(), generatedIdField, null);
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			return foundInstance;
		}
		return null;
	}

	/*
	@Override
	public boolean isManaged() {
		if (!super.isManaged() && getInstance() != null) {
			Object id = PersistenceProvider.instance().getId(getInstance(), getEntityManager());
			if (id != null) {
				T foundInstance = getEntityManager().find(getEntityClass(), id);
				if (foundInstance != null) {
					//instance = getEntityManager().merge(getInstance()); -- в окружении с event-scoped entity manager так делать неправильно
					assignId(id);
					return true;
				} else {
					try {
						String generatedIdField = modelUtils.getEntityGeneratedIdFieldName(getEntityClass());
						if (generatedIdField != null) {
							assignId(null);
							PropertyUtils.setProperty(getInstance(), generatedIdField, null);
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
					return false;
				}
			}
		}

		return super.isManaged();
	}
	 */

	@Override
	@Transactional
	public String update() {
		setInstance(getEntityManager().merge(getInstance()));
		return super.update();
	}

	public MetaObject getModelObject() throws MetaException {
		SimpleProfiler.st("." + getEntityClass().getName()); //$NON-NLS-1$
		MetaObject result = (MetaObject) modelUtils.getElementByClass(getEntityClass());
		SimpleProfiler.en("." + getEntityClass().getName()); //$NON-NLS-1$
		return result;
	}

	public void checkPermission(String action) {
		try {
			MetaObject entityObject = getModelObject();

			org.jboss.seam.security.Identity.instance().checkPermission(entityObject.getFullName(), action);

			if (getId() == null) return;

			boolean isMultiTenant = modelUtils.hasAnnotation(entityObject, MultiTenant.class);
			boolean hasAccessCriteria = modelUtils.hasAccessCriteria(entityObject);

			if (hasAccessCriteria || isMultiTenant) {
				String alias = TextUtils.beanName(entityObject.getName());

				HqlBuilder hqlBuilder = new HqlBuilder("select " + alias + " from " + getEntityClass().getCanonicalName() + " " + alias, alias); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				hqlBuilder.addWhere(alias + " = :instance"); //$NON-NLS-1$
				hqlBuilder.setParam("instance", getInstance()); //$NON-NLS-1$

				Criteria resultCriteria = new Criteria(hqlBuilder.getParams(), hqlBuilder.getWhere());

				if (hasAccessCriteria) {
					QueryUtils.applyAccessCriteria(entityObject, alias, null, resultCriteria);
				}

				if (isMultiTenant) {
					QueryUtils.applyTenantCriteria(entityObject, alias, null, resultCriteria);
				}

				info("check permissions for entity instance by query: #0... with parameters #1", hqlBuilder.getHql(), hqlBuilder.getParams()); //$NON-NLS-1$

				Query query = getEntityManager().createQuery(hqlBuilder.getHql());
				hqlBuilder.setQueryParams(query);
				query.setMaxResults(1);

				if (query.getResultList().size() == 0) throw new AuthorizationException(Messages.getString("org.comsoft.BaseEntityHome.Exception3")); //$NON-NLS-1$
			}

		} catch (MetaException e) {
			throw new RuntimeException(e);
		}
	}

	public void validate() throws Exception {
		SimpleProfiler.st();
		MetaObject entityObject = getModelObject();

		Collection<String> emptyRequiredFields = new HashSet<String>();

		T anInstance = getInstance();

		for (MetaField metaField : entityObject.getFields()) {
			EntityFieldParams fieldParams = getFieldParams(metaField.getName());
			if (!fieldParams.isOneToMany() && fieldParams.isRequired()) {
				Object fieldValue = PropertyUtils.getProperty(anInstance, metaField.getName());
				if (fieldValue == null || fieldValue instanceof String && StringUtils.isBlank((String) fieldValue))
					emptyRequiredFields.add(fieldParams.getCaption());
			}
		}

		if (!emptyRequiredFields.isEmpty()) {
			SimpleProfiler.en();
			throw new OperationInvokingException(String.format(Messages.getString("org.comsoft.BaseEntityHome.Exception4"), emptyRequiredFields)); //$NON-NLS-1$
		}

		try {
			getController().validate(anInstance);
		} finally {
			SimpleProfiler.en();
		}

	}

	protected void refreshDetails() {}

	protected void refreshDetailsQuery(org.comsoft.system.BaseEntityQuery<?> query) {
		if (query != null) {
			query.first();
		}
	}

	protected void prepareDetailsQuery(org.comsoft.system.BaseEntityQuery<?> detailsQuery, String detailsElementPath) {
		try {
			org.comsoft.meta.MetaField metaField = (org.comsoft.meta.MetaField) modelUtils.getElementByPath(detailsElementPath);
			info("Search for criteria IN #0 #1", detailsElementPath, metaField); //$NON-NLS-1$
			if (metaField.hasAnnotationName("@Criteria")) { //$NON-NLS-1$
				info("Found criteria IN #0 #1", detailsElementPath, metaField); //$NON-NLS-1$
				java.util.Map<String, String> replacements = new java.util.HashMap<String, String>();
				replacements.put("instance", getElComponentName() + ".instance"); //$NON-NLS-1$ //$NON-NLS-2$
				org.comsoft.system.query.QueryUtils.applyCriteria(metaField.getAnnotation("@Criteria"), detailsQuery.getQueryBuilder().getAlias(), replacements, detailsQuery.getCriteria()); //$NON-NLS-1$
			}
		} catch (org.comsoft.meta.MetaException e) {
			throw new RuntimeException(e);
		}
	}

	protected String elComponentName;

	private boolean nestedConversationReserved = false;

	public String getElComponentName() {
		if (elComponentName == null)
			return org.jboss.seam.Component.getComponentName(this.getClass());
		return elComponentName;
	}

	public void setElComponentName(String elComponentName) {
		this.elComponentName = elComponentName;
	}

	@Override
	protected void initDefaultMessages()
	{
		Expressions expressions = new Expressions();
		if (getCreatedMessage() == null) {
			setCreatedMessage(expressions.createValueExpression(Messages.getString("org.comsoft.BaseEntityHome.Message1"))); //$NON-NLS-1$
		}
		if (getUpdatedMessage() == null) {
			setUpdatedMessage(expressions.createValueExpression(Messages.getString("org.comsoft.BaseEntityHome.Message2"))); //$NON-NLS-1$
		}
		if (getDeletedMessage() == null) {
			setDeletedMessage(expressions.createValueExpression(Messages.getString("org.comsoft.BaseEntityHome.Message3"))); //$NON-NLS-1$
		}
	}

	@Override
	public EntityManager getPersistenceContext() {
		return (EntityManager) getComponentInstance(getPersistenceContextName());
	}

	@Override
	public void setPersistenceContext(EntityManager persistenceContext) {
		throw new IllegalStateException("operation not supported"); //$NON-NLS-1$
	}

}
