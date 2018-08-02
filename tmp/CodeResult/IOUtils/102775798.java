/*-
 * #%L
 * Detachment Core Logic
 * %%
 * Copyright (C) 2010 - 2017 COMSOFT, JSC
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
package org.comsoft.groovy.jcr;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.IOUtils;
import org.comsoft.groovy.jbpm5.Jbpm5VaadinUtils;
import org.comsoft.groovy.jcr.Filters.JcrFilter;
import org.comsoft.groovy.vaadin.UserTableColumnsManager;
import org.comsoft.help.HelpUtils;
import org.comsoft.jbpm5.Jbpm5Utils;
import org.comsoft.jbpm5.task.Jbpm5TaskUtils;
import org.comsoft.jbpm5.task.TaskInstance;
import org.comsoft.reporting.QueryDesc;
import org.comsoft.reporting.ReportQueryProcessor;
import org.comsoft.reporting.ReportVaadinUtils;
import org.comsoft.ria.BaseApp;
import org.comsoft.ria.ChildWindow;
import org.comsoft.ria.Closeable;
import org.comsoft.ria.ConversationIdContainer;
import org.comsoft.ria.ELContainer;
import org.comsoft.ria.ELProperty;
import org.comsoft.ria.QueryColumn;
import org.comsoft.ria.QueryColumns;
import org.comsoft.ria.SelectOptionDialog;
import org.comsoft.ria.VUtils;
import org.comsoft.ria.fields.FieldInContainer;
import org.comsoft.rm.BaseEntity;
import org.comsoft.rm.BaseFolder;
import org.comsoft.rm.BasePersistedFilter;
import org.comsoft.rm.BaseRmEntityHome;
import org.comsoft.rm.BaseRmEntityList;
import org.comsoft.rm.CloseEvent;
import org.comsoft.rm.EntityDefinitionController;
import org.comsoft.rm.FilterCriteriaType;
import org.comsoft.rm.RmDefinitionsCache;
import org.comsoft.rm.RmEntityListFilterController;
import org.comsoft.rm.RmEntityListFilterControllerImpl;
import org.comsoft.rm.RmException;
import org.comsoft.rm.RmFolderClassifier;
import org.comsoft.rm.RmPermissionManager;
import org.comsoft.rm.RmProvider;
import org.comsoft.rm.RmProvidersList;
import org.comsoft.rm.RmRuntimeException;
import org.comsoft.rm.RmUtils;
import org.comsoft.rtmodel.EntityDefinition;
import org.comsoft.rtmodel.FieldDefinition;
import org.comsoft.rtmodel.logic.EntityOperation;
import org.comsoft.rtmodel.ui.EntityFilter;
import org.comsoft.system.UserSettings;
import org.comsoft.system.downloads.FileDownloadContext;
import org.comsoft.system.downloads.FileDownloadManager;
import org.comsoft.system.excel.ExcelParser;
import org.comsoft.system.i18n.Messages;
import org.comsoft.system.settings.api.SettingsManager;
import org.comsoft.system.settings.impl.DefaultSettingsManager;
import org.comsoft.system.util.ELUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;
import org.jbpm.task.Task;
import org.jbpm.task.User;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.AbstractSplitPanel;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Field;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.CollapseEvent;
import com.vaadin.ui.Tree.ExpandEvent;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ChameleonTheme;
import com.vaadin.ui.themes.Reindeer;

@Name("rmEntityListFrame")
@Scope(ScopeType.STATELESS)
@Install(precedence = Install.FRAMEWORK)
public class JcrEntityListFrame extends VerticalLayout implements ConversationIdContainer, Closeable, RmFoldersNavigationHandler {

	protected static final String PAGE_NO_NAVIGATION_PARAM = "org.comsoft.groovy.jcr.JcrEntityListFrame.PageNoNavigation";

	protected static final String SHOW_PAGES_IN_NAVIGATION_PARAM = "org.comsoft.groovy.jcr.JcrEntityListFrame.showPagesInNavigation";

	protected static final String SHOW_COUNTS_PARAM = "org.comsoft.groovy.jcr.JcrEntityListFrame.showCounts";

	protected static final String FASTFORWARD_ENABLED_PARAM = "org.comsoft.groovy.jcr.JcrEntityListFrame.fastforwardEnabled";

	private static final int DEFAULT_PAGE_RECORDS = 25;

	private static final int MAX_PAGE_RECORDS = 100;

	private static final String TABLE_CODE = "JcrEntityListFrame_table"; //$NON-NLS-1$

	private static final String HIERARCHICAL_MODE_PROPERTY = "hierarchicalMode";	 //$NON-NLS-1$
	private static final String HIERARCHICAL_COLUMN = RmUtils.HIERARCHICAL_COLUMN_PROPERTY_NAME;	 //$NON-NLS-1$
	
	//private static final String ACTIONS_COLUMN_HEADER = "Действия";
	private static final String ACTIONS_COLUMN_NAME = "actions_column";
	
	private static final Resource helpIcon = FontAwesome.QUESTION;//new ThemeResource("icons/16/help.png");

	public static final Resource CLEAR_BTN_ICON = FontAwesome.ERASER;
	//public static final String CLEAR_BTN_DESC = "Очистить фильтр";
	//public static final String CLEAR_BTN_TEXT = "Очистить фильтр";

	private static final long serialVersionUID = 1L;

	public static String getActionsColumnHeader() {
		return Messages.get("org.comsoft.groovy.jcr.JcrEntityListFrame.actionsColumnHeader");
	}

	public static String getClearButtonDescription() {
		return Messages.get("org.comsoft.groovy.jcr.JcrEntityListFrame.clearButtonDescription");
	}

	public static String getClearButtonCaption() {
		return Messages.get("org.comsoft.groovy.jcr.JcrEntityListFrame.clearButtonCaption");
	}

	private Log log = Logging.getLog(this.getClass());

	private String cid;

	protected BaseRmEntityList list;

	protected Table resultsTable = null;

	protected TreeTable resultsTreeTable = null;

	private Label captionLabel;

	private Component oldComponent;

	private BaseFolder folder;

	private HorizontalLayout navigationBar;
	private HorizontalLayout additionalButtonsLayout;

	private Button firstbtn;
	private Button nextbtn;
	private Button prevbtn;

	protected TextField quickFilter;
	private Button clearFiltersBtn;

	private AbstractSplitPanel split = null;

	private boolean selectForm = false;

	//	protected Field filterFieldValue;

	protected String filter;

	protected RmEntityListFilterController filterController = new RmEntityListFilterControllerImpl();

	protected JcrFilterFrame filterFrame;

	private HorizontalLayout filterLayout;

	private Button createBtn;

	private boolean hierarchicalMode = false;

	private FieldDefinition hierarchicalParentField;

	protected String componentName;

	private ReportQueryProcessor reportQueryProcessor;

	private boolean multiSelect = false;

	private ListSelect selectedList;

	private Button lastbtn;

	private RmFoldersFrame foldersFrame;

	protected Component filterBar;

	protected EntityDefinition definition;

	public boolean isSelectForm() {
		return selectForm;
	}

	public void setSelectForm(boolean selectForm) {
		this.selectForm = selectForm;
	}
	
	public ReportQueryProcessor getReportQueryProcessor() {
		return reportQueryProcessor;
	}

	public void setReportQueryProcessor(ReportQueryProcessor reportQueryProcessor) {
		this.reportQueryProcessor = reportQueryProcessor;
	}

	public String getFilter() {
		return filter;
	}

	public BaseFolder getFolder() {
		return folder;
	}

	public void setFolder(BaseFolder folder) {
		setFolder(folder, null, null);
	}

	public void setFolder(BaseFolder folder, BasePersistedFilter persistedFilter, RmFolderClassifier folderClassifier) {
		settingFolder = true;
		try {
			internalSetFolder(folder, persistedFilter, folderClassifier);
		} finally {
			settingFolder = false;
		}
	}

	protected void internalSetFolder(BaseFolder folder, BasePersistedFilter persistedFilter, RmFolderClassifier folderClassifier) {
		boolean applicable = isApplicableFor(folder, persistedFilter); // нужно выполнять до изменения this.folder, создания нового экземпляра list и т.п.
		log.debug("applicable for #0 with filter #1, classifier #3 returns #2", folder, persistedFilter, applicable, folderClassifier); //$NON-NLS-1$
		this.folder = folder;
		EntityDefinition definition = RmDefinitionsCache.instance().findEntityDefinition(folder.getChildrenDataType());
		if (!applicable) {
			instantiateNewListComponent(definition);
		}
		if (createBtn != null) {
			RmPermissionManager permissionManager = RmProvidersList.instance().getProvider(definition).getPermissionManager();
			createBtn.setEnabled(!folder.isVirtual() && permissionManager.hasPermission(folder, RmPermissionManager.PERMISSION_CREATE));
		}
		list.setParentFolder(folder);
		setFolderClassifier(folderClassifier);
		setPersistedFilter(persistedFilter);
		if (!applicable) {
			reinitComponents(definition);
			fixCaption(definition);
			navigationBar.setVisible(useList());
		} else {
			fixCaption(definition);
			loadListChildrenSavedValue();
		}
		if (listChildrenCheckBox != null)
			listChildrenCheckBox.setVisible(!folder.isVirtual() && useList());
	}

	public BaseRmEntityList getList() {
		if (list != null)
			list = (BaseRmEntityList) list.refreshInstance();
		return list;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Override
	public void addListener(Class eventType, Object target, Method method) {
		/*		if (eventType.equals(JcrEntityListFrame.SelectEvent.class)) {
			createSelectButton();
		}
		 */
		super.addListener(eventType, target, method);
	}

	@Override
	public void addListener(Class eventType, Object target, String methodName) {
		/*		if (eventType.equals(JcrEntityListFrame.SelectEvent.class)) {
			createSelectButton();
		}
		 */
		super.addListener(eventType, target, methodName);
	}

	protected Table getActualTable() {
		return resultsTreeTable == null ? resultsTable : resultsTreeTable;
	}

	protected void createRowButtons() {
		final boolean touchDevice = Page.getCurrent().getWebBrowser().isTouchDevice() || ELUtils.yes(DefaultSettingsManager.instance().getParamValue("org.comsoft.groovy.jcr.JcrEntityListFrame.showOpenButton", "0")); //$NON-NLS-1$ //$NON-NLS-2$
		final boolean selectForm = isSelectForm();
		if (!selectForm && !touchDevice) return;		
		Table actualTable = getActualTable();
		final String selectButtonCaption = Messages.getString("org.comsoft.JcrEntityListFrame.Btn1"); //$NON-NLS-1$
		final String openButtonCaption = Messages.getString("org.comsoft.JcrEntityListFrame.Btn2"); //$NON-NLS-1$
		VUtils.addTableButton(actualTable, ACTIONS_COLUMN_NAME, new Table.ColumnGenerator() {
			private static final long serialVersionUID = 1L;
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				HorizontalLayout layout = new HorizontalLayout();
				if (selectForm)
					layout.addComponent(createButton(selectButtonCaption, itemId, "doSelect")); //$NON-NLS-1$
				layout.addComponent(createButton(openButtonCaption, itemId, "doOpen")); //$NON-NLS-1$ //$NON-NLS-2$
				return layout;
			}
		});
		String[] columnHeaders = actualTable.getColumnHeaders();
		actualTable.setColumnHeader(ACTIONS_COLUMN_NAME, getActionsColumnHeader());
		int expandRatio = 1 / (columnHeaders.length - 1);
		//System.out.println("columnHeaders = " + Arrays.deepToString(columnHeaders) + ", visibleColumns = " + actualTable.getVisibleColumns() + ", expandRatio = " + expandRatio);
		for (String columnHeader : columnHeaders) {
			if (!columnHeader.equals(ACTIONS_COLUMN_NAME)) {
				actualTable.setColumnExpandRatio(columnHeader, expandRatio);
			} else {
				actualTable.setColumnWidth(columnHeader, 76);
				actualTable.setColumnAlignment(columnHeader, Align.CENTER);
			}
		}
	}

	protected Button createButton(String caption, Object itemId, String methodName) {
		Button btn = new Button(caption);
		btn.setData(itemId);
		btn.addStyleName(ChameleonTheme.BUTTON_DEFAULT);
		btn.addStyleName(ChameleonTheme.BUTTON_SMALL);
		btn.setDisableOnClick(true);
		btn.addClickListener(new VUtils.EnablingClickListener(this, methodName));
		return btn;
	}

	/*
	private void createOpenButton() {
		VUtils.addTableButton(getActualTable(), "Действия", "Открыть", this, "doOpen");
	}
	 */

	public void doOpen(Button.ClickEvent event) {
		BaseEntity entity = (BaseEntity) event.getButton().getData();
		openEntity(entity);
	}

	/*
	private void createSelectButton() {
		VUtils.addTableButton(getActualTable(), "Действия", "Выбрать", this, "doSelect");
	}
	 */

	public void doSelect(Button.ClickEvent event) {
		BaseEntity entity = (BaseEntity) event.getButton().getData();
		selectItem(entity);
	}

	private void selectItem(BaseEntity object) {
		/* 
		 try {

			object = JcrEntityUtils.instance().deserializeEntity(object.getNode());
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		 */
		// refresh entity
		BaseRmEntityHome entityHome = RmProvidersList.instance().getProvider(object.getDefinition()).getHomeforEntity(object, ScopeType.STATELESS);

		try {
			entityHome.refresh();
		} catch (RmException e) {
			throw new RmRuntimeException(e);
		}
		BaseEntity instance = entityHome.getInstance();

		if (!multiSelect) {
			doClose();
			SelectEvent selectEvent = new SelectEvent(this);
			selectEvent.setSelection(instance);
			fireEvent(selectEvent);
		} else {
			selectedList.getContainerDataSource().addItem(instance);
			selectedList.setValue(instance);
		}
	}

	public void doClose() {
		if (oldComponent != null) {
			ComponentContainer parent = (ComponentContainer) getParent();
			parent.removeComponent(this);
			parent.addComponent(oldComponent);
		} else {
			Window window = VUtils.getWindow(this);
			if (window != null) window.close();
		}
	}

	protected ComponentContainer getCLayout() {
		if (isSelectForm()) {
			if (split == null) {
				split = new HorizontalSplitPanel();

				split.setSizeFull();
				addComponent(split);
				setExpandRatio(split, 1);
				split.setSplitPosition(240, AbstractSplitPanel.UNITS_PIXELS);

				// if multiSelect - add list for selection
				if (isMultiSelect()) {
					HorizontalLayout selBar = new HorizontalLayout();
					selBar.setWidth("100%"); //$NON-NLS-1$
					selectedList = new ListSelect(Messages.getString("org.comsoft.JcrEntityListFrame.ListSelect1")); //$NON-NLS-1$
					selectedList.setNullSelectionAllowed(false);
					selectedList.setWidth("100%"); //$NON-NLS-1$
					selectedList.setHeight(5, UNITS_EM);
					selectedList.setMultiSelect(true);
					selBar.addComponent(selectedList);
					selBar.setExpandRatio(selectedList, 1.0f);
					VerticalLayout selButtonsBar = new VerticalLayout();
					selButtonsBar.setSizeUndefined();
					Button removeSelectedBtn = new Button(Messages.getString("org.comsoft.JcrEntityListFrame.Btn3")); //$NON-NLS-1$
					removeSelectedBtn.setDisableOnClick(true);
					removeSelectedBtn.addClickListener(new VUtils.EnablingClickListener(new Button.ClickListener() {

						@Override
						public void buttonClick(ClickEvent event) {
							removeSelectedFromSelection();
						}
					}));
					removeSelectedBtn.setIcon(FontAwesome.TIMES);
					selButtonsBar.addComponent(removeSelectedBtn);
					
					Button doMultiselectBtn = new Button(Messages.getString("org.comsoft.JcrEntityListFrame.Btn4")); //$NON-NLS-1$
					doMultiselectBtn.setDisableOnClick(true);
					doMultiselectBtn.addClickListener(new VUtils.EnablingClickListener(new Button.ClickListener() {

						@Override
						public void buttonClick(ClickEvent event) {
							doMultiselect();
						}
					}));
					doMultiselectBtn.setIcon(FontAwesome.CHECK);
					selButtonsBar.addComponent(doMultiselectBtn);
					selBar.addComponent(selButtonsBar);
					selBar.setComponentAlignment(selButtonsBar, Alignment.BOTTOM_RIGHT);
					addComponent(selBar);

				}

				foldersFrame = new RmFoldersFrame(list.getDefinition(), this);

/*				
				foldersTree = new Tree();
				foldersTree.setMultiSelect(false);
				foldersTree.setContainerDataSource(getFoldersContainer());
				foldersTree
				.expandItemsRecursively(((HierarchicalContainer) foldersTree
						.getContainerDataSource()).rootItemIds()
						.iterator().next());

				foldersTree.setItemCaptionPropertyId("name");
				foldersTree
				.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
				foldersTree.setNullSelectionAllowed(false);
				foldersTree.setImmediate(true);

				foldersTree.addListener(new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					@Override
					public void valueChange(ValueChangeEvent event) {
						BaseFolder newFolder = (BaseFolder) foldersTree.getValue();

						setFolder(newFolder);

						refreshList();
					}
				});
*/
				split.setFirstComponent(foldersFrame);

				VerticalLayout result = new VerticalLayout();
				result.setSizeFull();
				result.setMargin(true);
				result.setSpacing(true);
				result.addStyleName("main-content"); //$NON-NLS-1$
				//((VerticalLayout) result.getContent()).setSizeFull();
				split.setSecondComponent(result);
			}
			return (ComponentContainer) split.getSecondComponent();
		} else
			return this;
	}

	protected void removeSelectedFromSelection() {
		Object value = selectedList.getValue();
		if (value != null) {
			if (value instanceof Collection) 
				for (Object o : (Collection) value)
					selectedList.getContainerDataSource().removeItem(o);
			else
				selectedList.getContainerDataSource().removeItem(value);
				
		}
		if (!(selectedList.getContainerDataSource().size() == 0))
			selectedList.setValue(selectedList.getContainerDataSource().getItemIds().iterator().next());
	}

	protected void fixCaption(EntityDefinition definition) {
		if (captionLabel == null) return;

		BaseFolder fldr = null;
		try {
			fldr = folder != null ? folder : getDefaultFolder();
		} catch (RmException e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}

		boolean useElProperty = useList();
		String folderName = fldr.getName();

		String captionPrefix = (isSelectForm() && "null".equals(folderName)) ? Messages.getString("org.comsoft.JcrEntityListFrame.CaptionPrefix1") : MessageFormat.format(Messages.getString("org.comsoft.JcrEntityListFrame.DirectoryNameDesc"), folderName); //$NON-NLS-1$ //$NON-NLS-2$
		String captionSuffix = "";
		if (persistedFilter != null) 
			captionSuffix = MessageFormat.format(Messages.getString("org.comsoft.JcrEntityListFrame.CaptionSuffix"), captionSuffix, persistedFilter.getName());

		if (useElProperty) {
			captionLabel.setPropertyDataSource(new ELProperty(captionPrefix + captionSuffix, cid)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		} else {
			captionLabel.setPropertyDataSource(new ObjectProperty<String>(captionPrefix + captionSuffix)); //$NON-NLS-1$
		}
	}

	protected void doMultiselect() {
		doClose();
		SelectEvent selectEvent = new SelectEvent(this);
		selectEvent.setSelection(selectedList.getContainerDataSource().getItemIds());
		fireEvent(selectEvent);

	}


	public void init(EntityDefinition definition, BaseFolder folderToList, String defaultFilter, boolean selectForm, boolean useMultiSelect) {
		cid = BaseApp.needConversation();
		// setDebugId("jcrEntityListFrame");

		initLoadParams();

		if (definition != null && !useMultiSelect) {
			// need multiselect for batch operations
			for (EntityOperation entityOperation : definition.getOperations()) {
				if (Boolean.TRUE.equals(entityOperation.getBatchInvocation())) {
					useMultiSelect = true;
					break;
				}
			}
		}

		this.selectForm = selectForm;
		this.multiSelect = useMultiSelect;
		this.definition = definition;

		componentName = definition.getTypeName() + "_listFrame_" + String.valueOf(System.currentTimeMillis()); //$NON-NLS-1$

		Contexts.getConversationContext().set(componentName, this);

		instantiateNewListComponent(definition);
		log.debug("Init, list = #0", list); //$NON-NLS-1$
		setSizeFull();

		setSpacing(true);

		//setFolder(folderToList);

		/*
		enterKeyShortcutListener = new ShortcutListener(null, null, ShortcutAction.KeyCode.ENTER, (int[])null) {
			private static final long serialVersionUID = 1L;
			@Override
			public void handleAction(Object sender, Object target) {
				System.out.println("process enter... window = " + getWindow());
				Component component = (Component) target;
				Table workTable = getActualTable();
				while (component != null) {
					if (workTable.equals(component)) {
						if (workTable.getValue() != null) {
							if (isSelectForm()) {
								if (isMultiSelect()) {
									@SuppressWarnings("unchecked")
									Set<BaseEntity> values = (Set<BaseEntity>) workTable.getValue();
									for (BaseEntity entity : values) selectItem(entity);
								} else {
									selectItem((BaseEntity) workTable.getValue());
								}
							} else {
								openEntity((BaseEntity) workTable.getValue());
							}
						}
						break;
					}
					component = component.getParent();
				}
			}
		};
		 */


		reinitComponents(definition);
		if (isSelectForm()) {
			try {
				foldersFrame.setFolder(getDefaultFolder());
			} catch (RmException e) {
				throw new RmRuntimeException(e);
			}
		} else {
			setFolder(folderToList);
		}

		fixCaption(definition);

		navigationBar.setVisible(folder != null && folder.getQueryDesc() == null);

		if (quickFilter != null)
			quickFilter.setValue(defaultFilter);

	}

	protected void initLoadParams() {
		SettingsManager settingsManager = DefaultSettingsManager.instance();
		pageNoNavigation = ELUtils.yes(settingsManager.getParamValue(PAGE_NO_NAVIGATION_PARAM, "0")); //$NON-NLS-1$
		showPagesInNavigation = ELUtils.yes(settingsManager.getParamValue(SHOW_PAGES_IN_NAVIGATION_PARAM, "0")); //$NON-NLS-1$
		showCounts = ELUtils.yes(settingsManager.getParamValue(SHOW_COUNTS_PARAM, "1")); //$NON-NLS-1$
		fastforwardEnabled = ELUtils.yes(settingsManager.getParamValue(FASTFORWARD_ENABLED_PARAM, "1")); //$NON-NLS-1$
	}

	protected void instantiateNewListComponent(EntityDefinition definition) {
		list = RmProvidersList.instance().getProvider(definition.getRmProvider()).getListByDefinition(definition, ScopeType.CONVERSATION);
		list.setMaxResults(getConfiguredMaxResults());
		listComponentName = list.getComponentName();
		countsCalculated = false;
	}


	private void reinitComponents(EntityDefinition definition) {
		log.debug("reinitComponents"); //$NON-NLS-1$
		hierarchicalMode = false;
		hierarchicalParentField = null;
		resultsTable = null;
		resultsTreeTable = null;

		ComponentContainer cnt = getCLayout();
		cnt.removeAllComponents();

		if (useList()) {
//			 check hierarchicalMode property and if  == "1",. find parent field
			String hpropertyValue = EntityDefinitionController.instance().getEntityPropertyValue(definition, HIERARCHICAL_COLUMN);
			if (hpropertyValue != null && hpropertyValue.length() > 0) {
				for (FieldDefinition field : definition.getFields()) {
					if (field.getDataType().equals(definition) && (!field.getIsCollection()))  {
						hierarchicalMode = true;
						hierarchicalParentField = field;
						break;
					}
				}
			}
			
			log.debug("Init,hierarchicalMode = #0 , hierarchicalParentField = #1 ", hierarchicalMode, hierarchicalParentField); //$NON-NLS-1$
		}

		captionLabel = new Label(""); //$NON-NLS-1$
		captionLabel.addStyleName(ChameleonTheme.LABEL_H2);
		cnt.addComponent(captionLabel);

		filterBar = createFilterBar();
		cnt.addComponent(filterBar);
		cnt.addComponent(createToolbar());

		Component navbar = createNavbar();
		navbar.setVisible(false);
		cnt.addComponent(navbar);
	}

	private boolean useList() {
		return (folder == null) || (folder.getQueryDesc() == null);
	}

	private Component createFilterBar() {
		log.debug("createFilterBar"); //$NON-NLS-1$
		
		AbstractOrderedLayout result = new VerticalLayout();
		result.setSpacing(true);
		HorizontalLayout horizontal = new HorizontalLayout();
		horizontal.setSpacing(true);
		result.addComponent(horizontal);

		if (useList()) {
			horizontal.addComponent(new Label(Messages.getString("org.comsoft.JcrEntityListFrame.Label1"))); //$NON-NLS-1$
			quickFilter = new TextField();
			quickFilter.setNullRepresentation("");
			//quickFilter.setCaption("Быстрый поиск:");
			quickFilter.setInputPrompt(Messages.getString("org.comsoft.JcrEntityListFrame.Prompt1")); //$NON-NLS-1$
			quickFilter.setColumns(30);
			quickFilter.addStyleName(ChameleonTheme.TEXTFIELD_SEARCH);
			horizontal.addComponent(quickFilter);
			String path;
			if (getFolder() != null)
				 path = getFolder().getPath();
			else {
				try {
					path = getDefaultFolder().getPath();
				} catch (RmException e) {
					throw new RmRuntimeException(e);
				}
			}

			filterFrame = new JcrFilterFrame(getList().getDefinition(), this, path);
			filterFrame.setHeight("100%"); //$NON-NLS-1$
			filterFrame.setListFrame(this);
			Button showFilterFrameBtn = new Button(Messages.getString("org.comsoft.JcrEntityListFrame.Btn5")); //$NON-NLS-1$
			showFilterFrameBtn.setIcon(FontAwesome.FILTER);
			showFilterFrameBtn.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				@Override
				public void buttonClick(ClickEvent event) {
					ChildWindow chlidWindow = new ChildWindow();
					chlidWindow.setCaption(Messages.getString("org.comsoft.JcrEntityListFrame.Caption1")); //$NON-NLS-1$
					chlidWindow.setWidth("960px"); //$NON-NLS-1$
					chlidWindow.setHeight("600px"); //$NON-NLS-1$
					chlidWindow.setContent(filterFrame);
					chlidWindow.addToApplication();
				}
			});
			horizontal.addComponent(showFilterFrameBtn);
			
			clearFiltersBtn = new Button(getClearButtonCaption());
			clearFiltersBtn.setIcon(CLEAR_BTN_ICON);
			clearFiltersBtn.setVisible(false);
			clearFiltersBtn.setDescription(getClearButtonDescription());
			clearFiltersBtn.addClickListener(new Button.ClickListener() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					quickFilter.setValue(""); //$NON-NLS-1$
					filterFrame.clearFilters();
				}
			});
			horizontal.addComponent(clearFiltersBtn);
			
			filterLayout = new HorizontalLayout();
			filterLayout.addStyleName("wrap"); //$NON-NLS-1$
			filterLayout.setSpacing(true);
			filterLayout.setVisible(false);
			
			result.addComponent(filterLayout);
			/*TogglePanel togglePanel = new TogglePanel("Дополнительные фильтры >", filterFrame);
			togglePanel.setBodyVisible(false);
			result.addComponent(togglePanel);*/
			VUtils.setComponentsAlignment(horizontal, Alignment.MIDDLE_LEFT);
			applyPersistedFilter();
		} else {
			return createQueryParamsFrame();
		}
		Resource icon = null;
		result.addShortcutListener(new ShortcutListener(Messages.getString("org.comsoft.JcrEntityListFrame.Listener1"), icon, KeyCode.ENTER, 0) { //$NON-NLS-1$
			
			@Override
			public void handleAction(Object sender, Object target) {
				if ((target != null) && (target instanceof Component) && isParentOf(filterBar, (Component) target)) {
					list.setPageNo(1);
					refreshList();
				}
			}
		});
		return result;
	}


	private void applyPersistedFilter() {
		if ((filterFrame != null) && (persistedFilter != null)) {
			filterFrame.loadPersistedFilter(persistedFilter);
		}


	}
	
	private boolean isParentOf(Component parent, Component toCheck) {
		while (toCheck != null) {
			if (toCheck == parent) 
				return true;
			
			toCheck = toCheck.getParent();
			
		}
		return false;
		
		
	}

	private Component createQueryParamsFrame() {
		VerticalLayout result = new VerticalLayout();
		result.setSpacing(true);
		EntityManager entityManager = (EntityManager) org.jboss.seam.Component.getInstance("entityManager"); //$NON-NLS-1$
		if (!entityManager.contains(folder.getQueryDesc())) {
			folder.setQueryDesc(entityManager.find(QueryDesc.class, folder.getQueryDesc().getId()));
		}
		reportQueryProcessor = ReportQueryProcessor.statelessInstance();
		reportQueryProcessor.loadQueryDesc(folder.getQueryDesc());
		ReportVaadinUtils.instance().createReportParamComponents(this, result, reportQueryProcessor.getParams(), componentName + ".reportQueryProcessor"); //$NON-NLS-1$
		return result;
	}

	class FieldOperator {
		String operation;

		public FieldOperator(String operation, String caption) {
			super();
			this.operation = operation;
			this.caption = caption;
		}

		String caption;

		@Override
		public String toString() {
			return caption;
		}

	}

	class FieldCriteria {
		FieldOperator operator;
		FieldDefinition definition;
		Object filterValue;
	}

	protected Field createFilterField(EntityFilter filter2) {
		Field res = new TextField();
		res.setCaption(Messages.getString("org.comsoft.JcrEntityListFrame.Caption2")); //$NON-NLS-1$

		return res;

	}

	protected Collection getFieldOperators(EntityFilter value) {
		List<JcrEntityListFrame.FieldOperator> result = new LinkedList<JcrEntityListFrame.FieldOperator>();
		result.add(new FieldOperator("like", Messages.getString("org.comsoft.JcrEntityListFrame.FieldOperator1"))); //$NON-NLS-1$ //$NON-NLS-2$
		/*
		 * if (value.getIsCollection()) { result.add(new FieldOperator("empty",
		 * "пусто")); result.add(new FieldOperator("not empty", "не пусто"));
		 * result.add(new FieldOperator("like", "содержит")); } else {
		 * result.add(new FieldOperator("like", "содержит")); result.add(new
		 * FieldOperator("!like", "не содержит")); result.add(new
		 * FieldOperator("=", "равняется")); result.add(new FieldOperator("!=",
		 * "не равняется")); }
		 */
		return result;
	}

	protected boolean isPageNoNavigation() {
		return pageNoNavigation;
	}

	protected boolean isShowPagesInNavigation() {
		return showPagesInNavigation;
	}

	protected boolean isShowCounts() {
		return showCounts;
	}

	protected boolean isFastforwardEnabled() {
		return fastforwardEnabled;
	}

	private void updatePageCountLabel() {
		Integer count = null;
		boolean showCounts = isShowCounts() || countsCalculated;
		if(isShowPagesInNavigation() && isPageNoNavigation() && showCounts)	{
			try {
				count = list.getPageCount();
			} catch (Exception e) {
				e.printStackTrace();
				count = -1;
			}
			pageCountLabel.setValue(Messages.get("org.comsoft.JcrEntityListFrame.From") + count);
		} else {
			if (showCounts)
			try {
				count = list.getCount();
			} catch (Exception e) {
				e.printStackTrace();
				count = -1;
			}
			pageCountLabel.setValue(list.getFirstPosition() + " - " + list.getLastPosition() + " "
					+ Messages.get("org.comsoft.JcrEntityListFrame.From") + " " + (showCounts ? count : "?"));
		}

	}
	private Component createNavbar() {
		navigationBar = new HorizontalLayout();
		//navigationBar.setMargin(true);
		navigationBar.setSpacing(true);
		//bar.setWidth("100%");

		firstbtn = new Button(""); //$NON-NLS-1$
		firstbtn.setIcon(FontAwesome.FAST_BACKWARD);
		firstbtn.setDisableOnClick(true);
		firstbtn.setDescription(Messages.getString("org.comsoft.JcrEntityListFrame.Description2")); //$NON-NLS-1$
		firstbtn.addClickListener(new VUtils.EnablingClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					boolean filtered = isListFiltred();
					list.setPageNo(1);
					refreshTable(filtered);
				} catch (Exception e) {
					e.printStackTrace();
					showException(e, Messages.getString("org.comsoft.JcrEntityListFrame.Exception1")); //$NON-NLS-1$
				}
			}
		}));
//		firstbtn.addStyleName(ChameleonTheme.BUTTON_SMALL);
		navigationBar.addComponent(firstbtn);
		prevbtn = new Button(""); //$NON-NLS-1$
		prevbtn.setIcon(FontAwesome.BACKWARD);
		prevbtn.setDescription(Messages.getString("org.comsoft.JcrEntityListFrame.Description3")); //$NON-NLS-1$
		prevbtn.setDisableOnClick(true);
		prevbtn.addClickListener(new VUtils.EnablingClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					boolean filtered = isListFiltred();
					list.prevPage();
					if (list.getResultList().isEmpty()) {
						list.resetCountCache();
						list.setPageNo(calcLastPageNo(false));
					}
					refreshTable(filtered);
				} catch (Exception e) {
					e.printStackTrace();
					showException(e, Messages.getString("org.comsoft.JcrEntityListFrame.Exception2")); //$NON-NLS-1$
				}
			}
		}));
//		prevbtn.addStyleName(ChameleonTheme.BUTTON_SMALL);
		navigationBar.addComponent(prevbtn);
		String  c =  list.getComponentName();
		if (isShowPagesInNavigation()) {
			pageNumberField = new TextField(new ELProperty("#{" + c + ".pageNo}", cid, Integer.class)); //$NON-NLS-1$ //$NON-NLS-2$
			pageNumberField.setImmediate(true);
			pageNumberField.setWidth(5, Sizeable.Unit.EM);
			navigationBar.addComponent(new Label(Messages.getString("org.comsoft.JcrEntityListFrame.Label2"))); //$NON-NLS-1$
			navigationBar.addComponent(pageNumberField);
			if (isPageNoNavigation()) {
				pageNumberField.addValueChangeListener(new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					@Override
					public void valueChange(ValueChangeEvent event) {
						try {
							boolean filtered = isListFiltred();
							if (list.getResultList().isEmpty()) {
								list.resetCountCache();
								list.setPageNo(calcLastPageNo(false));
							}
							refreshTable(filtered);
						} catch (Exception e) {
							e.printStackTrace();
							showException(e, Messages.getString("org.comsoft.JcrEntityListFrame.Exception3")); //$NON-NLS-1$
						}
					}
				});
				pageCountLabel = new Label(Messages.get("org.comsoft.JcrEntityListFrame.From") + " 0"); //$NON-NLS-1$ //$NON-NLS-2$
				navigationBar.addComponent(pageCountLabel);
			} else
				pageNumberField.setReadOnly(true);
		} else {
			pageCountLabel = new Label("0 - 0 " + Messages.get("org.comsoft.JcrEntityListFrame.From") + " 0"); //$NON-NLS-1$ //$NON-NLS-2$
			navigationBar.addComponent(pageCountLabel);
		}
		//navigationBar.setComponentAlignment(lbl, Alignment.MIDDLE_CENTER);
		nextbtn = new Button(""); //$NON-NLS-1$
		nextbtn.setIcon(FontAwesome.FORWARD);
		nextbtn.setDisableOnClick(true);
		nextbtn.setDescription(Messages.getString("org.comsoft.JcrEntityListFrame.Description4")); //$NON-NLS-1$
		nextbtn.addClickListener(new VUtils.EnablingClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					boolean filtered = isListFiltred();
					list.setPageNo(list.getPageNo() + 1); // nextPage не всегда правильно отрабатывает
					if (list.getResultList().isEmpty()) {
						list.resetCountCache();
						list.setPageNo(calcLastPageNo(false));
					}
					refreshTable(filtered);
				} catch (Exception e) {
					e.printStackTrace();
					showException(e, Messages.getString("org.comsoft.JcrEntityListFrame.Exception4")); //$NON-NLS-1$
				}
			}
		}));
//		nextbtn.addStyleName(ChameleonTheme.BUTTON_SMALL);
		navigationBar.addComponent(nextbtn);


		if (isFastforwardEnabled()) {
		lastbtn = new Button(""); //$NON-NLS-1$
		lastbtn.setIcon(FontAwesome.FAST_FORWARD);
		lastbtn.setDescription(Messages.getString("org.comsoft.JcrEntityListFrame.Description5")); //$NON-NLS-1$
		lastbtn.setDisableOnClick(true);
		lastbtn.addClickListener(new VUtils.EnablingClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					boolean filtered = isListFiltred();
					list.setPageNo(calcLastPageNo(true));
					if (list.getResultList().isEmpty()) {
						list.resetCountCache();
						list.setPageNo(calcLastPageNo(true));
					}
					refreshTable(filtered);
				} catch (Exception e) {
					e.printStackTrace();
					showException(e, Messages.getString("org.comsoft.JcrEntityListFrame.Exception5")); //$NON-NLS-1$
				}
			}
		}));
//		lastbtn.addStyleName(ChameleonTheme.BUTTON_SMALL);
		navigationBar.addComponent(lastbtn);
		}

		List<Integer> lst = ELUtils.instance().list(10, 25, 50, 100);
		ComboBox s = new ComboBox(null, lst);
		s.setWidth(6, Sizeable.Unit.EM);
		s.setNullSelectionAllowed(false);
		navigationBar.addComponent(s);
		s.setPropertyDataSource(new ELProperty("#{" + list.getComponentName() + ".maxResults}", cid, Integer.class)); //$NON-NLS-1$ //$NON-NLS-2$
		s.setImmediate(true);
		s.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (settingParent || attaching) return;
				setConfiguredMaxResults((Integer)event.getProperty().getValue());
				list.setPageNo(1);
				refreshTable(isListFiltred());
			}
		});

		if (PropertyUtils.isWriteable(list, "listChildren")) {
			listChildrenCheckBox = new CheckBox(Messages.getString("org.comsoft.JcrEntityListFrame.CheckBox1"), new ELProperty("#{" + list.getComponentName() + ".listChildren}", cid, Boolean.class)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			loadListChildrenSavedValue();
			listChildrenCheckBox.setImmediate(true);
			listChildrenCheckBox.addValueChangeListener(new Property.ValueChangeListener() {
				@Override
				public void valueChange(ValueChangeEvent event) {
					if (settingParent || attaching) return;
					listChildrenCheckBoxChanged();
				}
			});
			navigationBar.addComponent(listChildrenCheckBox);
		}
		//navigationBar.setComponentAlignment(listChildren, Alignment.MIDDLE_CENTER);

		Alignment alignment = Alignment.MIDDLE_CENTER; 
		Iterator<Component> componentIterator = navigationBar.getComponentIterator();
		while (componentIterator.hasNext()) {
			Component component = (Component) componentIterator.next();
			navigationBar.setComponentAlignment(component, alignment);
		}
		
		createAdditionalButtonsLayout();
		navigationBar.addComponent(additionalButtonsLayout);
		
		return navigationBar;
	}
	
	protected HorizontalLayout createAdditionalButtonsLayout() {
		additionalButtonsLayout = new HorizontalLayout();
		return additionalButtonsLayout;
	}

	protected Integer calcLastPageNo(boolean force) throws RmException {
		if (!force && !isShowCounts())
			return 1;
		Integer cnt = list.getCount();
		countsCalculated = true;
		Integer lastPageNo = Math.max(1, (int)(cnt / list.getMaxResults() + (cnt % list.getMaxResults() > 0 ? 1 : 0)));
		return lastPageNo;
	}

	private boolean loadListChildrenSavedValueInProgress = false;

	private void loadListChildrenSavedValue() {
		if ((listChildrenCheckBox != null) &&  (getFolder() != null)) {
			loadListChildrenSavedValueInProgress = true;
			try {
				listChildrenCheckBox.setValue(ELUtils.yes(UserSettings.instance().getSettings("JcrEntityListFrame.listChildren").getProperty(getFolder().getPath(), "1"))); //$NON-NLS-1$ //$NON-NLS-2$
			} finally {
				loadListChildrenSavedValueInProgress = false;
			}
		}
	}

	protected void listChildrenCheckBoxChanged() {
		if (loadListChildrenSavedValueInProgress) return;

		if (getFolder() != null)
			UserSettings.instance().setSettingValue("JcrEntityListFrame.listChildren", getFolder().getPath(), listChildrenCheckBox.getValue() ? "1" : "0"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		
		refreshList();
		
	}

	public void open(Button.ClickEvent event) {
		BaseEntity entity = (BaseEntity) event.getButton().getData();

		openEntity(entity);
		// parent.addComponent(entityFrame);

	}

	protected void openEntity(BaseEntity entity) {
		List<Task> activeTasks = Jbpm5TaskUtils.instance().getActiveTasksForControlledEntity(entity);
		List<Task> userTasks = new ArrayList<Task>();
		if (activeTasks.size() > 0) {

			User jbpmCurrentUser = new User(Jbpm5Utils.instance().getCurrentUserId());
			for (Task task : activeTasks) {
				User actualOwner = task.getTaskData().getActualOwner();
				if (jbpmCurrentUser.equals(actualOwner) || (actualOwner == null && task.getPeopleAssignments().getPotentialOwners().contains(jbpmCurrentUser))) {
					//	Jbpm5VaadinUtils.instance().openTaskWindow(new TaskInstance(task), getParent());
					//	return;
					userTasks.add(task);
				}
			}
		}

		if (userTasks.size() > 0) {
			SelectOptionDialog.Option[] opts = new SelectOptionDialog.Option[userTasks.size()+1];
			opts[0] = new SelectOptionDialog.Option(entity, MessageFormat.format(Messages.getString("org.comsoft.JcrEntityListFrame.DialogOption"), entity.getDefinition().getName(), entity.toString())); //$NON-NLS-2$ //$NON-NLS-3$

			for (int i=0; i<userTasks.size(); i++) {
				Task task = userTasks.get(i);
				TaskInstance taskInstance = new TaskInstance(task);
				opts[i+1] = new SelectOptionDialog.Option(taskInstance, MessageFormat.format(Messages.getString("org.comsoft.JcrEntityListFrame.OpenTaskDialog"), taskInstance.getTitle())); //$NON-NLS-2$
			}

			SelectOptionDialog.select(Messages.getString("org.comsoft.JcrEntityListFrame.Dialog1"), Messages.getString("org.comsoft.JcrEntityListFrame.Dialog2"), opts, getUI(),  //$NON-NLS-1$ //$NON-NLS-2$
					new SelectOptionDialog.OptionSelectedListener() {

				@Override
				public void selected(Object option) {
					// System.out.println("Selected " + option);
					if (option instanceof BaseEntity) {
						openEntityFrame((BaseEntity) option);
					} else if (option instanceof TaskInstance) {
						Jbpm5VaadinUtils.instance().openTaskWindow((TaskInstance) option, getParent());
					}
				}

				@Override
				public void canceled() {
				}
			});

		} else
			openEntityFrame(entity);
	}

	private void openEntityFrame(BaseEntity entity) {
		try {
			// real parent
			BaseEntity entityparent = entity.getOwner();
			BaseFolder folder = entity.getFolder();
			try {
				log.debug("entity parent : #0 #1", (entityparent != null ? entityparent.getClass() : null), entityparent); //$NON-NLS-1$
			} catch (Exception e) {
				e.printStackTrace();
			}

			// создадим новую ЭФ сущности и установим для нее обработчик события закрытия формы
			JcrEntityFrame entityFrame = JcrEntityFrame.editInstance(entity, null, this, "formClosed"); //$NON-NLS-1$
			// создадим новое модальное окно для ЭФ 
			JcrEntityFrame.newWindowFor(entityFrame).setCaption(entity.toString());

			// получим компонент для проверки прав доступа
			RmPermissionManager permissionManager = RmProvidersList.instance().getProvider(getList().getDefinition()).getPermissionManager();

			// проверим факт наличия прав на запись у текущего пользователя в данной папке 
			boolean hasPermission = permissionManager.hasPermission(folder == null ? getFolder() : folder, RmPermissionManager.PERMISSION_WRITE);
			log.debug("has permission ? #0", hasPermission); //$NON-NLS-1$
			entityFrame.setFrameReadonly(!hasPermission);
			log.debug("adding entityframe #0 to parent #1, readonly : #2", entityFrame, null, entityFrame.isFrameReadonly()); //$NON-NLS-1$
		} catch (Exception e) {
			e.printStackTrace();
			showException(e, Messages.getString("org.comsoft.JcrEntityListFrame.Exception6")); //$NON-NLS-1$
		}
	}

	private Component createToolbar() {
		HorizontalLayout bar = new HorizontalLayout();
		bar.setSpacing(true);
		// bar.setWidth("100%");
		
		Button helpBtn = new Button("", new Button.ClickListener() { //$NON-NLS-1$
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(Button.ClickEvent event) {
				HelpUtils.instance().showHelpWindow("RM_REFINITION.LIST", getList().getDefinition().getTypeName(), getList().getDefinition().getName(), event.getButton()); //$NON-NLS-1$
			}
		});
		helpBtn.setIcon(helpIcon);
		bar.addComponent(helpBtn);

		
		Button btn = null;
		btn = new Button(Messages.getString("org.comsoft.JcrEntityListFrame.Btn6")); //$NON-NLS-1$
		btn.setDisableOnClick(true);
		btn.setIcon(FontAwesome.REFRESH); //new ThemeResource("../chameleon/img/magnifier.png"));
		btn.addClickListener(new VUtils.EnablingClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				list.setPageNo(1);
				refreshList();
			}
		}));
		btn.addStyleName(Reindeer.BUTTON_DEFAULT);
		bar.addComponent(btn);

		Button exportButton = new Button(Messages.getString("org.comsoft.JcrEntityListFrame.Btn7"), new VUtils.EnablingClickListener(new Button.ClickListener() { //$NON-NLS-1$
			@Override
			public void buttonClick(ClickEvent event) {
				exportToExcel();
			}
		}));
		exportButton.setIcon(FontAwesome.FILE_EXCEL_O);
		exportButton.setDisableOnClick(true);
		bar.addComponent(exportButton);

		createBtn = new Button(Messages.getString("org.comsoft.JcrEntityListFrame.Btn8")); //$NON-NLS-1$
		createBtn.setIcon(FontAwesome.FILE_O);
		createBtn.setDisableOnClick(true);
		createBtn.addClickListener(new VUtils.EnablingClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				createNew();
			}
		}));
		bar.addComponent(createBtn);
		createBtn.setEnabled(false);
		//log.debug("org.comsoft.groovy.jcr.JcrEntityListFrame.createToolbar() - impelment PERMISSION_CREATE check");
		//		btn.setEnabled(JcrPermissionUtils.instance().hasPermission(getFolder(),
		//				JcrPermissionUtils.PERMISSION_CREATE));

		
		if (isSelectForm()) {
			if (isMultiSelect()) {
				btn = new Button(Messages.getString("org.comsoft.JcrEntityListFrame.Btn9")); //$NON-NLS-1$
				btn.setDisableOnClick(true);
				btn.addClickListener(new VUtils.EnablingClickListener(new Button.ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						doSelectSelected();
					}
				}));
				btn.setDescription(Messages.getString("org.comsoft.JcrEntityListFrame.Description6")); //$NON-NLS-1$
				bar.addComponent(btn);
			}

			btn = new Button(Messages.getString("org.comsoft.JcrEntityListFrame.Btn10")); //$NON-NLS-1$
			btn.setDisableOnClick(true);
			btn.addClickListener(new VUtils.EnablingClickListener(new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					doClose();
				}
			}));
			bar.addComponent(btn);
		}
		if (folder != null) {
			RmPermissionManager permissionManager = RmProvidersList.instance().getProvider(getList().getDefinition()).getPermissionManager();
			createBtn.setEnabled(!folder.isVirtual() && permissionManager.hasPermission(folder, RmPermissionManager.PERMISSION_CREATE));
		}
		return bar;
	}

	protected void doSelectSelected() {
		Table table = getActualTable();
		Object value = table.getValue();
		if (value == null)
			return;
		if (value instanceof Collection) {
			for (Object item : ((Collection) value))
				selectItem((BaseEntity) item);
		} else
			selectItem((BaseEntity) value);
		
	}

	public void refreshList() {		
		if (settingFolder) return;
		log.debug("refreshList"); //$NON-NLS-1$
		// list.setMaxResults(100);

		try {
			boolean filtered = refreshListAndApplyFilters();
			if (list.getResultList().isEmpty())
				list.setPageNo(calcLastPageNo(false));
			refreshTable(filtered);
			clearFiltersBtn.setVisible(filtered);
			filterFrame.setClearFilterBtnVisible(filtered);
		} catch (Exception e) {
			e.printStackTrace();
			showException(e, Messages.getString("org.comsoft.JcrEntityListFrame.Exception7")); //$NON-NLS-1$
		}
	}

	public boolean refreshListAndApplyFilters() {
		boolean filtered = false;
		if (useList())
			filtered = refreshEntityListAndApplyFilters();
		else
			refreshQueryAndApplyFilter();
		countsCalculated = false;
		return filtered;
	}

	public boolean isListFiltred() {
		return !list.getFilters().isEmpty();
	}

	public void refreshTable(boolean filtered) {
		try {
			if (useList()) {
				log.debug("Filtered : #0, HierarchicalMode : #1", filtered, isHierarchicalMode()); //$NON-NLS-1$
				if (filtered || !isHierarchicalMode())  {
					refreshResultsTable();
				} else 
					refreshResultsTreeTable();
			} else {
				refreshResultsTable();	
			}
		} catch (Exception e) {
			e.printStackTrace();
			showException(e, Messages.getString("org.comsoft.JcrEntityListFrame.Exception8")); //$NON-NLS-1$
		}
		if (navigationBar != null && navigationBar.isVisible()) {
			prevbtn.setEnabled(list.getPageNo() > 1);
			nextbtn.setEnabled(list.isNextPageExists());
			navigationBar.markAsDirtyRecursive();
			if (pageCountLabel != null)
				updatePageCountLabel();
		}
	}

	/**
	 * @return true if filtered
	 */
	protected boolean refreshEntityListAndApplyFilters() {
		list.refresh();
		list.setParentFolder(folder);
		list.clearFilters();
		list.clearParams();
		list.clearJoins();
		list.setMaxResults(getConfiguredMaxResults());
		boolean filtered = false;
		if (StringUtils.isNotBlank((String) quickFilter.getValue())) {
			String str = (String) quickFilter.getValue();
			String fltExpr = null;

			/*					
			Object node = null;
			try {
				if ("51e0c653-5f91-450a-b560-94074cd1ee1b".length() == str
						.length()) // maybe its uuid :)
					node = ManagedContentRepositorySession.instance().getNodeByIdentifier(str);
			} catch (RepositoryException e) {

			}
			if (node != null) {
				fltExpr = "[alias].[jcr:uuid] = " + RmUtils.DOLLAR_SIGN + "filter";
			} 
			 */

			if (str.contains(":")) { //$NON-NLS-1$
				String[] arr = str.split(":"); //$NON-NLS-1$
				if (arr[0].equals(list.getDefinition().getTypeName()) && StringUtils.isNumeric(arr[1])) {
					fltExpr = "[alias].id = :filter_by_id"; //$NON-NLS-1$
					list.setParamValue("filter_by_id", Long.valueOf(arr[1])); //$NON-NLS-1$
				}
			}
			if (StringUtils.isBlank(fltExpr))
				fltExpr = list.getQuickFilterExpression();
			// (StringUtils.isBlank(list.getDefinition().getQuickFilterExpression())) ? "CONTAINS([alias].*, *" + JcrMiscUtils.DOLLAR_SIGN + "filter*)" : list.getDefinition().getQuickFilterExpression();

			// String afilter = "*" + fixParamString((String)
			// quickFilter.getValue()) + "*";
			// String fltExpr =
			// (StringUtils.isBlank(list.getDefinition().getQuickFilterExpression()))
			// ? "[alias].aname like " + JcrMiscUtils.DOLLAR_SIGN + "filter" :
			// list.getDefinition().getQuickFilterExpression();
			String afilter = fixParamString((String) quickFilter.getValue());
			/*
			if (JcrRmProvider.PROVIDER_CODE.equals(list.getDefinition().getRmProvider())) {
				if (fltExpr.contains("%" + RmUtils.DOLLAR_SIGN + "filter%")) {
					String regex = "%\\" + RmUtils.DOLLAR_SIGN + "filter%";
					String replacement = "\\" + RmUtils.DOLLAR_SIGN + "likeFilter";
					log.debug("try replace %filter%, source = #0, regex = #1, replacement = #2", ELUtils.instance().array(fltExpr, regex, replacement));				
					fltExpr = fltExpr.replaceAll(regex, replacement);
					list.setParamValue("likeFilter", "%" + afilter.toLowerCase() + "%");
					log.debug("replaced = #0", ELUtils.instance().array(fltExpr));
				}
				if (fltExpr.contains("*" + RmUtils.DOLLAR_SIGN + "filter*")) {
					String regex = "\\*\\" + RmUtils.DOLLAR_SIGN + "filter\\*";
					String replacement = "\\" + RmUtils.DOLLAR_SIGN + "containsFilter";
					log.debug("try replace *filter*, source = #0, regex = #1, replacement = #2", ELUtils.instance().array(fltExpr, regex, replacement));
					fltExpr = fltExpr.replaceAll(regex, replacement); 
					list.setParamValue("containsFilter", "*" + afilter + "*");				
					log.debug("replaced = #0", ELUtils.instance().array(fltExpr));
				}
			}
			 */
			list.addFilter(fltExpr);
			if (fltExpr.contains(RmUtils.DOLLAR_SIGN + "filter") || fltExpr.contains(":filter")) { //$NON-NLS-1$ //$NON-NLS-2$
				list.setParamValue("filter", afilter); //$NON-NLS-1$
			}
			filtered = true;
		}
		if ((filterFrame != null) && (filterFrame.apply(list)))
			filtered = true;

		if (filterFrame.hasAppliedFilters()) {
			
			filterLayout.removeAllComponents();
			
			for(JcrFilter f: filterFrame.getAppliedFilter()) {
				filterLayout.addComponent(filterFrame.createFilterComponent(f));
			}			
			
			filterLayout.setVisible(true);
		} else {
			filterLayout.removeAllComponents();
			filterLayout.setVisible(false);
		}

		if (StringUtils.isNotBlank(filter)) {
			list.addFilter(filter);
			filtered = true;
		}

		if (!filterController.getFilters().isEmpty() || !filterController.getJoins().isEmpty()) {
			list.addFilters(filterController.getFilters());
			list.addJoins(filterController.getJoins());
			list.setParamValues(filterController.getParams());
			filtered = true;
		}

		if (folderClassifier != null) {
			list.addCriteria(folderClassifier.getClassifierField().getFieldName(), folderClassifier.getClassifier(), FilterCriteriaType.EQUALS);
			//filtered = true;
		}

		if (!filtered && isHierarchicalMode()) {
			list.addCriteria(hierarchicalParentField.getFieldName(), null, FilterCriteriaType.ISNULL);
			list.setMaxResults(100000);
		}

		
		
		return filtered;
	}
	
	/*private void refreshQueryResultsTable() {

		refreshQueryAndApplyFilter();

		// refresh query results
		refreshResultsTable();
	}*/

	protected void refreshQueryAndApplyFilter() {
		getReportQueryProcessor().doProcessQuery();
		List<?> resultList = getReportQueryProcessor().getResultList();
		if (resultList.size() == 1 && resultList.get(0) instanceof BaseRmEntityList) {
			Integer pageNo = list.getPageNo();
			Integer maxResults = list.getMaxResults();
			String order = list.getOrder();
			list = (BaseRmEntityList<BaseEntity>) resultList.get(0);
			Contexts.getConversationContext().set(listComponentName, list);
			list.setComponentName(listComponentName);
			list.setMaxResults(maxResults);
			list.setPageNo(pageNo);
			list.setOrder(order);
		}
	}

	private void refreshResultsTreeTable() {
		log.debug("refreshResultsTreeTable"); //$NON-NLS-1$

		navigationBar.setVisible(false);

		if (resultsTable !=null)
			removeTableComponent(resultsTable);
		if (resultsTreeTable !=null)
			removeTableComponent(resultsTreeTable);

		initResultsTreeTable();

		refreshTreeTableData();

	}

	private void refreshTreeTableData() {
		Properties treeState = new Properties();
		saveTreeTableState(resultsTreeTable, treeState);
		try {
			resultsTreeTable.removeAllItems();
			refreshEntityListAndApplyFilters();
			/*BaseRmEntityList alist = getList();
			alist.clearFilters();
			alist.clearParams();
			alist.addCriteria(hierarchicalParentField.getFieldName(), null, FilterCriteriaType.ISNULL);
			alist.setMaxResults(100000);*/
			addTreeTableNodes(null);
		} finally {
			loadTreeTableState(resultsTreeTable, treeState);
		}
	}

	protected void saveTreeTableState(TreeTable treeTable, Properties treeState) {
		HierarchicalContainer dataSource = (HierarchicalContainer) treeTable.getContainerDataSource();
		List<?> ids = dataSource.getItemIds();
		for (Object item : ids)
		if (item instanceof BaseEntity) {
			BaseEntity entity = (BaseEntity) item;
			if (!treeTable.isCollapsed(item)) 
				treeState.put(entity.getIdentifier().toString(), "expanded"); //$NON-NLS-1$
		}
		if (treeTable.getValue() != null && treeTable.getValue() instanceof BaseEntity)
			treeState.put("selectedEntityId", ((BaseEntity) treeTable.getValue()).getIdentifier().toString()); //$NON-NLS-1$
	}

	private void loadTreeTableState(TreeTable treeTable, Properties treeStateProperties) {
		String selectedEntityId = treeStateProperties.getProperty("selectedEntityId"); //$NON-NLS-1$
		HierarchicalContainer dataSource = (HierarchicalContainer) treeTable.getContainerDataSource();
		Collection<?> rootItemIds = dataSource.rootItemIds();
		if (rootItemIds != null) {
			Collection<?> itemIds = new ArrayList<Object>(rootItemIds);
			expandSavedNodes(treeTable, itemIds, treeStateProperties, selectedEntityId);
		}
	}

	private void expandSavedNodes(TreeTable treeTable, Collection<?> itemIds, Properties treeStateProperties, String selectedEntityIdStr) {
		HierarchicalContainer dataSource = (HierarchicalContainer) treeTable.getContainerDataSource();
		for (Object itemId : itemIds) {
			if (itemId instanceof BaseEntity) {
				BaseEntity entity = (BaseEntity) itemId;
				String entityIdStr = entity.getIdentifier().toString();
				if (entityIdStr.equals(selectedEntityIdStr)) {
				//if (itemId.equals(selectedEntityId))
					treeTable.setValue(entity);
					treeTable.setCurrentPageFirstItemId(entity);
					treeTable.focus();
				}
				if ("expanded".equals(treeStateProperties.getProperty(entityIdStr))) { //$NON-NLS-1$
					treeTable.setCollapsed(entity, false);
					Collection<?> children = dataSource.getChildren(entity);
					if (children != null)
						expandSavedNodes(treeTable, new ArrayList<Object>(children), treeStateProperties, selectedEntityIdStr);
				}
			}
		}
	}

	private void addTreeTableNodes(Object parentEntity) {
		QueryColumns columns = RmUtils.columnsForDefinition(list
				.getDefinition());
		try {
			List<BaseEntity> resultList = list.getResultList();
			log.debug("Refresh children for #0", parentEntity == null ? "null" : parentEntity.toString()); //$NON-NLS-1$ //$NON-NLS-2$
			log.debug("result List : #0", resultList.size()); //$NON-NLS-1$
			for (BaseEntity e  : resultList) {
				log.debug("adding #0", e.toString()); //$NON-NLS-1$

				Item item = resultsTreeTable.addItem(e);
				if (parentEntity != null)
					resultsTreeTable.setParent(e, parentEntity);

				resultsTreeTable.setCollapsed(e, true);

				setHierarchicalItemPropertyValues(e, item, columns);
			}
			if (resultList.size() == 0)
				resultsTreeTable.setChildrenAllowed(parentEntity, false);
		} catch (RmException e) {
			e.printStackTrace();
		}
	}

	protected void setHierarchicalItemPropertyValues(BaseEntity entity, Item item, QueryColumns columns) {
		for (QueryColumn c : columns.getColumns()) {
			Object value  = c.getCellValue(entity);
			Property property = item.getItemProperty(c.getName());
			if (String.class.equals(property.getType()))
				value = (value == null ? "" : value.toString()); //$NON-NLS-1$
			property.setValue(value);
		}
	}

	private void initResultsTreeTable() {
		log.debug("initResultsTreeTable"); //$NON-NLS-1$
		resultsTreeTable = new TreeTable();

		QueryColumns columns = RmUtils.columnsForDefinition(list.getDefinition());
		
		String hierColumns = getHierarhicalColumn();
		
		boolean isHierarhet = false;
		
		List<Object> visible = new LinkedList<Object>();
		for (QueryColumn c : columns.getColumns()) {
			resultsTreeTable.addContainerProperty(c.getName(), String.class, ""); //$NON-NLS-1$
			if (c.isVisible())
				visible.add(c.getName());
			resultsTreeTable.setColumnHeader(c.getName(), c.getCaption());
			if(hierColumns.equals(c.getName())) {
				resultsTreeTable.setHierarchyColumn(c.getName());
				isHierarhet = true;
				visible.remove(c.getName());
				visible.add(0, c.getName());
			}			
		}		
		resultsTreeTable.setVisibleColumns(visible.toArray());		
		resultsTreeTable.setSizeFull();
		resultsTreeTable.setSortEnabled(true);

		// VUtils.addTableButton(resultsTable, "Открыть", this, "open");
		resultsTreeTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {			
			private static final long serialVersionUID = 1L;

			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick() && (event.getItemId() != null)) {
					if (isSelectForm())
						selectItem((BaseEntity) event.getItemId());
					else
						openEntity((BaseEntity) event.getItemId());
				}
			}
		});

		setupTableActions(resultsTreeTable);

		resultsTreeTable.addCollapseListener(new Tree.CollapseListener() {

			@Override
			public void nodeCollapse(CollapseEvent event) {
				log.debug("Collapsed #0", event.getItemId()); //$NON-NLS-1$

			}
		});
		resultsTreeTable.addExpandListener(new Tree.ExpandListener() {

			@Override
			public void nodeExpand(ExpandEvent event) {
				if (event.getItemId() != null)
					refreshChildren(event.getItemId());
				else
					log.warn("Null itemid passed to nodeExpand()"); //$NON-NLS-1$
			}
		});
		resultsTreeTable.setSelectable(true);
		ComponentContainer contentLayout = getCLayout();		

		contentLayout.addComponent(resultsTreeTable);
		((VerticalLayout) resultsTreeTable.getParent()).setExpandRatio(
				resultsTreeTable, 1.0f);
		
		createRowButtons();
		
		if (isMultiSelect())
			resultsTreeTable.setMultiSelect(true);

		String tableCode = ("JcrEntityListFrame." + (folder != null ? folder.getPath() : getList().getDefinition().getTypeName()));
		String oldTableCode = "JcrEntityListFrame." + getList().getDefinition().getTypeName();		 //$NON-NLS-1$
		UserTableColumnsManager.manageColumns(resultsTreeTable, tableCode, oldTableCode);
		
		if(!isHierarhet) {
			resultsTreeTable.setHierarchyColumn(resultsTreeTable.getVisibleColumns()[0]);
		}				

	}
	
	private String getHierarhicalColumn(){
		
		RmDefinitionsCache definitionsCache = RmDefinitionsCache.instance();
		EntityDefinition ed = list.getDefinition();				
		
		String hc = definitionsCache.getEntityPropertyValue(ed, HIERARCHICAL_COLUMN);
		if(hc != null) {
			FieldDefinition fd = definitionsCache.getField(ed, hc);
			if(fd != null) {
				return hc;
			}			
		}
		
		return null;
	}

	protected void refreshChildren(Object itemId) {
		Collection<?> children = resultsTreeTable.getChildren(itemId);
		if (children != null) {
			Object[] charray = children.toArray();
			HierarchicalContainer containerDataSource = (HierarchicalContainer) resultsTreeTable.getContainerDataSource();
			for (Object o : charray) {
				containerDataSource.removeItemRecursively(o);
			}

		}
		getList();
		list.clearFilters(); list.clearParams();
		list.addCriteria(hierarchicalParentField.getFieldName(), ((BaseEntity) itemId)/*.getIdentifier()*/ , FilterCriteriaType.EQUALS);
		list.refresh();
		addTreeTableNodes(itemId);

	}

	private boolean isHierarchicalMode() {

		return hierarchicalMode ;
	}

	private void refreshResultsTable() {
		log.debug("refreshResultsTable"); //$NON-NLS-1$
		navigationBar.setVisible(usinglistFromQuery || folder != null && folder.getQueryDesc() == null);

// 		закомментированный код ломает сортировку в папках на основе QueryDesc!!!
//		if (resultsTable !=null)
//			removeTableComponent(resultsTable);
		if (resultsTreeTable != null)
			removeTableComponent(resultsTreeTable);
// 		закомментированный код ломает сортировку в папках на основе QueryDesc!!!
//		initResultsTable();

		if (resultsTable == null) {
			initResultsTable();
		} else
			VUtils.refresh(resultsTable, true);

	}

	public List<BaseEntity> getResultList() throws RmException {
		log.debug("getResultList"); //$NON-NLS-1$
		if (useList() || usinglistFromQuery)
			return list.getResultList();

		log.info("Before execute query"); //$NON-NLS-1$
		getReportQueryProcessor().doProcessQuery();
		log.info("after execute query"); //$NON-NLS-1$

		List<Object[]> resultList = getReportQueryProcessor().getResultList();
		List<BaseEntity> result = new ArrayList<BaseEntity>(resultList.size());
		for (Object[] row : resultList) 
			if ((row.length > 0)  && (row[0] != null))
				result.add((BaseEntity) row[0]);
		log.info("result list : #0", result.size()); //$NON-NLS-1$

		return result;
	}
	
	private void alignmentColumnIconOnCenter() {
		Map<String, String> params = new HashMap<String, String>();
		boolean columnWithIcons = RmUtils.isFieldDefinitionWithIcon(list.getDefinition(), params);
		String iconFinding = null;

		if (columnWithIcons && params.get("icon") != null) {
			for (Object fs : resultsTable.getContainerPropertyIds()) {
				if (fs instanceof String) {
					if (((String) fs).equals(params.get("icon"))) {
						iconFinding = (String) fs;
						break;
					}
				}
			}
		}
		if (iconFinding != null)
			resultsTable.setColumnAlignment(iconFinding, Align.CENTER);
	}

	protected void initResultsTable() {
		log.debug("initResultsTable"); //$NON-NLS-1$
		QueryColumns columns = RmUtils.columnsForDefinition(list.getDefinition());

		boolean useList = useList();

		boolean useQueryList = false;

		if (!useList) {
			getReportQueryProcessor().doProcessQuery();
			List<?> resultList = getReportQueryProcessor().getResultList();
			if (resultList.size() == 1 && resultList.get(0) instanceof BaseRmEntityList) {
				useQueryList = true;
				Integer pageNo = list.getPageNo();
				Integer maxResults = list.getMaxResults();
				list = (BaseRmEntityList<BaseEntity>) resultList.get(0);
				Contexts.getConversationContext().set(listComponentName, list);
				list.setMaxResults(maxResults);
				list.setPageNo(pageNo);
				usinglistFromQuery = true;
				navigationBar.setVisible(true);
				if (listChildrenCheckBox != null)
					listChildrenCheckBox.setVisible(false);
			}
		} else {
			getList().setMaxResults(getConfiguredMaxResults());
		}
		log.debug(columns);
		log.debug(componentName);
		ELContainer container = new ELContainer(columns, cid, "#{" + componentName + ".resultList}"); //$NON-NLS-1$ //$NON-NLS-2$
		resultsTable = VUtils.createTable(container);
		// resultsTable.setDebugId("resultsTable");

		if (useList || useQueryList)
			resultsTable.addHeaderClickListener(new Table.HeaderClickListener() {
				public void headerClick(Table.HeaderClickEvent event) {
					// VUtils.showMessage(resultsTable,event.getPropertyId().toString(),
					// "", 0);
					ELContainer qcontainer = (ELContainer) resultsTable
							.getContainerDataSource();
					String propName = event.getPropertyId().toString();
					if (qcontainer.getColumns().find(propName) != null) {

						Boolean desc = propName.equals(getList().getOrder());
						getList().setOrder(propName + (desc ? " desc" : "")); //$NON-NLS-1$ //$NON-NLS-2$
						refreshList();
						setTableOrderColumn(resultsTable, propName, desc);

					}
				}
			});

		resultsTable.setSizeUndefined();
		resultsTable.setWidth("100%"); // setSizeFull(); //$NON-NLS-1$
		resultsTable.setHeight("100%");  //$NON-NLS-1$

		// VUtils.addTableButton(resultsTable, "Открыть", this, "open");
		resultsTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick() && (event.getItemId() != null)) {
					if (isSelectForm())
						selectItem((BaseEntity) event.getItemId());
					else
						openEntity((BaseEntity) event.getItemId());
				}
			}
		});

		setupTableActions(resultsTable);

		ComponentContainer contentLayout = getCLayout();
		contentLayout.addComponent(resultsTable);
		((VerticalLayout) resultsTable.getParent()).setExpandRatio(resultsTable, 1.0f);

		createRowButtons();

		String tableCode = ("JcrEntityListFrame." + (folder != null ? folder.getPath() : getList().getDefinition().getTypeName()));
		String oldTableCode = "JcrEntityListFrame." + getList().getDefinition().getTypeName();		 //$NON-NLS-1$
		UserTableColumnsManager.manageColumns(resultsTable, tableCode, oldTableCode);
		
		if (isMultiSelect())
			resultsTable.setMultiSelect(true);
		
		alignmentColumnIconOnCenter();

		/*
		if (isSelectForm()) {
			//createSelectButton();
			createOpenButton();
		}
		 */

		//resultsTable.addShortcutListener(enterKeyShortcutListener);
	}

	@SuppressWarnings("unchecked")
	protected void setupTableActions(Action.Container actionContainer) {
		new RmEntityListOperations(actionContainer, getList().getOperations()) {
			@Override
			protected void internalHandleOperation(DoOperationContext context) throws Exception {
				getList().doOperation(context.getOperation(), context.getEntityInstances());
			}
			@Override
			protected void internalDoRefresh() throws Exception {
				refreshList();
			}
		};
	}

	private static final Resource ascIcon = FontAwesome.SORT_ASC; //new ThemeResource("icons/up.png");
	private static final Resource descIcon = FontAwesome.SORT_DESC;//new ThemeResource("icons/down.png");

	private boolean usinglistFromQuery;

	private CheckBox listChildrenCheckBox;

	private String listComponentName;

	private BasePersistedFilter persistedFilter;

	private RmFolderClassifier folderClassifier;

	private TextField pageNumberField;

	private Label pageCountLabel;

	protected boolean settingParent = false;

	protected boolean settingFolder = false;

	protected boolean attaching = false;

	protected boolean countsCalculated = false;

	protected boolean pageNoNavigation = false;

	protected boolean showPagesInNavigation = false;

	protected boolean showCounts = true;

	protected boolean fastforwardEnabled = true;

	//private ShortcutListener enterKeyShortcutListener;

	public void setTableOrderColumn(Table table, String property, Boolean desc) {
		for (Object col : table.getVisibleColumns())
			table.setColumnIcon(col, null);
		table.setColumnIcon(property, desc ? descIcon : ascIcon);

	}

	private String fixParamString(String value) {
		return value.replace("'", "''"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected void createNew() {

		JcrEntityFrame frame = JcrEntityFrame.edit(getList().getDefinition(),
				null, this, "formClosed"); //$NON-NLS-1$
		if (folder != null) {
			//System.out.println("set folder to " + folder);
			frame.getHome().getInstance().setFolder(folder);
		}
		JcrEntityFrame.newWindowFor(frame).setCaption(MessageFormat.format(Messages.getString("org.comsoft.JcrEntityListFrame.NewCaption"), getList().getDefinition().getName()));

	}

	public void formClosed(CloseEvent event) {
		if(event.getSource() instanceof JcrEntityFrame && useList()) {
			boolean needRefresh = true;
			JcrEntityFrame frame = (JcrEntityFrame) event.getSource();
			BaseEntity instance = frame.getHome().getInstance();
			Container container = (resultsTable != null ? resultsTable.getContainerDataSource() : resultsTreeTable.getContainerDataSource());
			if (container instanceof IndexedContainer) {
				IndexedContainer indexedContainer = (IndexedContainer) container;
				int itemIndex = indexedContainer.indexOfId(instance);
				if (itemIndex >= 0) { // была открыта экранная форма существующего экземпляра сущности
					Object oldParent = null;
					if (indexedContainer instanceof HierarchicalContainer)
						oldParent = ((HierarchicalContainer) indexedContainer).getParent(instance);
					if (!instance.isRemoved()) { // экз. сущности не удален
						if (indexedContainer instanceof ELContainer) { // "обновляем" строку
							indexedContainer.removeItem(instance);
							Item item = indexedContainer.addItemAt(itemIndex, instance);
							((ELContainer) indexedContainer).setItemPropertyValues(instance, item);
						}
						if (indexedContainer instanceof HierarchicalContainer) { // обновляем дерево
							if (oldParent != null) { // обновляем ветку дерева
								Properties treeState = new Properties();
								saveTreeTableState(resultsTreeTable, treeState);
								try {									
									refreshChildren(oldParent);
								} finally {
									loadTreeTableState(resultsTreeTable, treeState);
								}
							} else { // обновляем все дерево
								refreshResultsTreeTable();
							}
						}
						needRefresh = false;
					}
				} else if (!instance.isPersisted()) { // было создание экз. сущности, но его не сохранили 
					needRefresh = false;
				}
			}
			if (needRefresh) refreshList();
		}
		// VUtils.refresh(resultsTable, true);
	}

	public class SelectEvent extends com.vaadin.ui.Component.Event {

		public SelectEvent(com.vaadin.ui.Component source) {
			super(source);
		}

		public JcrEntityListFrame getEntityList() {
			return JcrEntityListFrame.this;
		}

		private Object selection;

		public Object getSelection() {
			return selection;
		}

		public void setSelection(Object selection) {
			this.selection = selection;
		}

	}

	public static JcrEntityListFrame newFrameInstance(EntityDefinition definition, BaseFolder folder, String defaultFilter, boolean selectForm, boolean useMultiSelect) {
		if (definition == null) throw new IllegalStateException("Entity definition not defined!"); //$NON-NLS-1$
		String customListFrameExpression = definition.getCustomListFrame();
		JcrEntityListFrame frameInstance = null;
		if (StringUtils.isNotBlank(customListFrameExpression)) {
			customListFrameExpression = customListFrameExpression.trim();
			if (customListFrameExpression.startsWith("#{")) { //$NON-NLS-1$
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("definition", definition); //$NON-NLS-1$
				params.put("folder", folder); //$NON-NLS-1$
				params.put("defaultFilter", defaultFilter); //$NON-NLS-1$
				params.put("selectForm", selectForm); //$NON-NLS-1$
				params.put("multiSelect", useMultiSelect); //$NON-NLS-1$
				frameInstance = (JcrEntityListFrame) ELUtils.instance().calcEl(customListFrameExpression, params);
			} else {
				frameInstance = (JcrEntityListFrame) org.jboss.seam.Component.getInstance(customListFrameExpression, ScopeType.STATELESS);
			}
		} else {
			frameInstance = (JcrEntityListFrame) org.jboss.seam.Component.getInstance(JcrEntityListFrame.class.getAnnotation(Name.class).value(), ScopeType.STATELESS);
		}
		if (frameInstance == null) throw new IllegalStateException("Entity list frame instantiation error: frameInstance == null"); //$NON-NLS-1$
		frameInstance.init(definition, folder, defaultFilter, selectForm, useMultiSelect);
		return frameInstance;
	}

	public static JcrEntityListFrame list(EntityDefinition definition, ComponentContainer parent, boolean selectForm, String filter) {
		return list(definition, parent, selectForm, filter, false, null);
	}

	public static JcrEntityListFrame list(EntityDefinition definition, ComponentContainer parent, boolean selectForm, String filter, boolean useMultiSelect, BasePersistedFilter persistedFilter) {
		JcrEntityListFrame result = newFrameInstance(definition, null, "", selectForm, useMultiSelect); //$NON-NLS-1$
		result.setFilter(filter);
		result.setPersistedFilter(persistedFilter);
		processParent(result, parent);
		//result.refreshList();
		return result;
	}

	protected static void processParent(JcrEntityListFrame listFrame, ComponentContainer parent) {
		if (parent != null) {
			Iterator<Component> it = parent.iterator();
			if (it.hasNext())
				listFrame.setOldComponent(it.next());
			parent.removeAllComponents();
			parent.addComponent(listFrame);
		}
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public void addFilter(String filter) {
		filterController.addFilter(filter);
	}

	public void addJoin(String join) {
		filterController.addJoin(join);
	}

	public void clearJoins() {
		filterController.clearJoins();
	}

	public void clearFilters() {
		filterController.clearFilters();
	}

	public void clearParams() {
		filterController.clearParams();
	}

	public void setParamValue(String name, Object value) {
		filterController.setParamValue(name, value);
	}

	public void setOrder(String orderBy) {
		filterController.setOrder(orderBy);
	}

	public static JcrEntityListFrame listFolder(BaseFolder folder, ComponentContainer parent, String defaultFilter, BasePersistedFilter persistedFilter, RmFolderClassifier folderClassifier) {
		EntityDefinition definition;
		definition = RmDefinitionsCache.instance().findEntityDefinition(folder.getChildrenDataType());

		JcrEntityListFrame result = newFrameInstance(definition, folder, defaultFilter, false, false);
		// result.setFilter(defaultFilter);
		result.setPersistedFilter(persistedFilter);
		result.setFolderClassifier(folderClassifier);

		processParent(result, parent);
		result.refreshList();
		return result;

	}

	public static JcrEntityListFrame listFolder(BaseFolder folder, ComponentContainer parent, String defaultFilter) {
		return listFolder(folder, parent, defaultFilter, null, null);
	}

	public void setOldComponent(Component next) {
		oldComponent = next;

	}

	public static JcrEntityListFrame select(EntityDefinition definition,
			com.vaadin.ui.Component parent, String callback, String filter) {
		return select(definition, parent, callback, filter, false);
	}
	public static JcrEntityListFrame select(EntityDefinition definition,
			com.vaadin.ui.Component parent, String callback, String filter, boolean useMultiSelect) {
		return select(definition, parent, callback, filter, useMultiSelect, null);
	}

	public static JcrEntityListFrame select(EntityDefinition definition,
			com.vaadin.ui.Component parent, String callback, String filter, boolean useMultiSelect, BasePersistedFilter persistedFilter) {
		JcrEntityListFrame result = null;

		result = list(definition, null, true, filter, useMultiSelect, persistedFilter);

		Window details = new Window();
		details.setCaption(MessageFormat.format(Messages.getString("org.comsoft.JcrEntityListFrame.ChangeFromListCaption"), definition.getName()));
		details.setModal(true);
		details.setWidth("90%"); //$NON-NLS-1$
		details.setHeight("90%"); //$NON-NLS-1$

		UI ui = (parent instanceof FieldInContainer ? ((FieldInContainer<?>)parent).getContainer().getUI() : parent.getUI());
		ui.addWindow(details);

		details.setContent(result);
		details.getContent().setSizeFull();
		//details.setBorder(1);
		//details.addComponent(result);
		//((VerticalLayout) details.getContent()).setComponentAlignment(result, Alignment.MIDDLE_CENTER);

		if (callback != null) {
			result.addListener(JcrEntityListFrame.SelectEvent.class, parent,
					callback);
		}

		result.refreshList();
		result.getActualTable().focus();
		return result;
	}

	@Override
	public void attach() {
		attaching = true;
		try {
			super.attach();
		} finally {
			attaching = false;
		}
	}

	@Override
	public void setParent(HasComponents parent) {
		settingParent = true;
		try {
			super.setParent(parent);
		} finally {
			settingParent = false;
		}
	}

	public void setPageNo(int i) {
		list.setPageNo(i);
	}

	/**
	 * Exports entity list data to excel file 
	 */
	protected void exportToExcel() {
		Integer pageNo = list.getPageNo();		
		try {
			List<BaseEntity> dataToExport = new ArrayList<BaseEntity>(); 
			if (useList() || usinglistFromQuery) {
				list.setPageNo(1);
				list.refresh();
				boolean done = false;
				//int i=0;
				while (!done) {
					@SuppressWarnings("unchecked")
					Collection<? extends BaseEntity> resultList = list.getResultList();
					dataToExport.addAll(resultList);
					//i++;
					done = !list.isNextPageExists();
					if (!done) list.nextPage();
					log.debug("next page"); //$NON-NLS-1$
				}
			} else {
				dataToExport.addAll(getResultList());
			}
			if (dataToExport.size() == 0) {
				Notification.show(Messages.getString("org.comsoft.JcrEntityListFrame.Notification1")); //$NON-NLS-1$
				return;
			}

			List<String> fields = new ArrayList<String>();
			List<String> headers = new ArrayList<String>();

			EntityDefinition definition = list.getDefinition();

			List<Object> visibleColumns = Arrays.asList(
					resultsTable != null ? 
					resultsTable.getVisibleColumns() :
					resultsTreeTable.getVisibleColumns()
			);

			List<QueryColumn> columns = RmUtils.columnsForDefinition(definition).getColumns();
			for (QueryColumn queryColumn : columns) {
				if (!visibleColumns.contains(queryColumn.getName()))
					continue;
				fields.add(queryColumn.getName());
				headers.add(queryColumn.getCaption());
			}

			final InputStream inputStream = new ByteArrayInputStream(ExcelParser.instance().createXls(dataToExport, fields, headers, null));

			FileDownloadManager downloadManager = FileDownloadManager.instance();

			FileDownloadContext downloadContext = downloadManager.prepareDownloadContext();
			try {
				downloadContext.setFilename(definition.getTypeName() + ".xls"); //$NON-NLS-1$
				downloadContext.setMimeType("application/vnd.ms-excel"); //$NON-NLS-1$
				IOUtils.copy(inputStream, downloadContext.getOutputStream());
			} finally {
				downloadContext.closeOutputStream();
			}

			//HttpServletRequest req = ((EmbApp) getWindow().getApplication()).getRequest();
			String href = VaadinService.getCurrentRequest().getContextPath() + downloadManager.getDownloadURLSuffix(downloadContext.getIdentifier());
			log.debug("open #0", href); //$NON-NLS-1$
			JavaScript.getCurrent().execute("window.open('" + href + "');"); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (Exception e) {
			e.printStackTrace();
			showException(e, Messages.getString("org.comsoft.JcrEntityListFrame.Exception9")); //$NON-NLS-1$
		} finally {
			list.setPageNo(pageNo);
			refreshList();
		}
	}

	protected void showException(Throwable exception, String caption) {
		Throwable e = exception;
		while (exception.getCause() != null) exception = exception.getCause();
		VUtils.showException(exception, caption);
		e.printStackTrace();
	}

	/**
	 * Метод должен вызываться до того как this.folder стал равным anotherFolder
	 * @param anotherFolder
	 * @param newPersistedFilter 
	 * @return
	 */
	public boolean isApplicableFor(BaseFolder anotherFolder, BasePersistedFilter newPersistedFilter) {
		if (!ObjectUtils.equals(persistedFilter, newPersistedFilter)) return false; 
		if (anotherFolder == null) return false;
		if (!getList().getDefinition().getTypeName().equals(anotherFolder.getChildrenDataType()))
			return false;
		if ((getFolder() != null) && (getFolder().getQueryDesc() != null)) 
			return false;
		if (anotherFolder.getQueryDesc() != null) 
			return false;
		return true;
	}

	@Override
	public void close() {
		Contexts.getConversationContext().remove(componentName);
	}

	public boolean isMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		this.multiSelect = multiSelect;
	}

	public void setCaptionVisible(boolean visible) {
		captionLabel.setVisible(visible);
	}

	public void setCreateButtonVisible(boolean visible) {
		createBtn.setVisible(visible);
	}

	public void setNavigationBarVisible(boolean visible) {
		navigationBar.setVisible(visible);
	}

	public BasePersistedFilter getPersistedFilter() {
		return persistedFilter;
	}

	public void setPersistedFilter(BasePersistedFilter persistedFilter) {
		this.persistedFilter = persistedFilter;
		fixCaption(list.getDefinition());
		applyPersistedFilter();
	}

	public void setFolderClassifier(RmFolderClassifier folderClassifier) {
		this.folderClassifier = folderClassifier;
	}

	@Override
	public void openItem(Item item) {
			if (item == null) return;
			Object value = item.getItemProperty("id").getValue(); //$NON-NLS-1$
			BaseFolder newFolder = null;
			BasePersistedFilter newFilter = null;
			RmFolderClassifier newClassifier = null;
			if (value instanceof BasePersistedFilter) {
				newFilter = ((BasePersistedFilter) value);
				newFolder = newFilter.getFolder();
			} else if (value instanceof RmFolderClassifier) {
				newClassifier = (RmFolderClassifier) value;
				newFolder = newClassifier.getFolder();
			} else
				newFolder = (BaseFolder) value;

			if (newFolder == null || StringUtils.isBlank(newFolder.getChildrenDataType())) return;
			if (newFolder.equals(folder) && ObjectUtils.equals(persistedFilter, newFilter) && ObjectUtils.equals(folderClassifier, newClassifier)) return;

			setFolder(newFolder, newFilter, newClassifier);

			setPageNo(1);
			
			refreshList();
	}

	@Override
	public void expandNode(ExpandEvent event, Item item) {
		
		
	}

	@Override
	public void fillRootItems() {
		
	}

	private BaseFolder getDefaultFolder() throws RmException {
		BaseFolder result = null;

		RmProvider provider = RmProvidersList.instance().getProvider(list.getDefinition());
		String entityFolderPath = provider.getEntityFolderPath(list.getDefinition());
		result = provider.getFolderByPath(entityFolderPath);

		if (result == null) {
			result = provider.getRoot(); //jcrEntityUtils.root();
		}
		return result;
	}

	public void navigateToFolder(BaseFolder folder,
			BasePersistedFilter apersistedFilter) throws Exception {
		if (selectForm)
			foldersFrame.navigateToFolder(folder, apersistedFilter);
		else
			JcrEntityNavigator.findNavigatorFor(this).navigateToFolder(folder, apersistedFilter);
		
	}

	private void removeTableComponent(Component component) {
		
		ComponentContainer tabParent = ((ComponentContainer) component.getParent());
		boolean toRemove = tabParent.getComponentCount() > 1;
		tabParent.removeComponent(component);
		if(component instanceof TreeTable) {
			resultsTreeTable = null;
		} else {
			resultsTable = null;
		}
		
		if(toRemove && !(tabParent instanceof JcrEntityListFrame)) {
			((ComponentContainer) tabParent.getParent()).removeComponent(tabParent);
		}
		
	}

	protected void setConfiguredMaxResults(Integer maxResults) {
		if (maxResults > MAX_PAGE_RECORDS)
			maxResults = MAX_PAGE_RECORDS;
		String tc = TABLE_CODE + "_" + definition.getTypeName();  //$NON-NLS-1$
		Properties prop = new Properties();
		prop.setProperty("maxResults", maxResults.toString()); //$NON-NLS-1$
		UserSettings.instance().setSettings(tc, prop);
	}

	protected Integer getConfiguredMaxResults() {
		String tc = TABLE_CODE + "_" + definition.getTypeName(); //$NON-NLS-1$
		Properties prop = UserSettings.instance().getSettings(tc);
		if(prop != null) {
			String propValue = prop.getProperty("maxResults"); //$NON-NLS-1$
			if(StringUtils.isNumeric(propValue)) {
				Integer result = Integer.parseInt(propValue);
				if (result > MAX_PAGE_RECORDS)
					result = MAX_PAGE_RECORDS;
				return result;
			}
		}
		return DEFAULT_PAGE_RECORDS;
	}

}
