package skala.erp.bmp.client.sales.view;

import java.util.Date;
import java.util.Map;

import com.google.gwt.dom.client.Document;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.grid.events.EditorEnterEvent;
import com.smartgwt.client.widgets.grid.events.EditorEnterHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.RibbonBar;
import com.smartgwt.client.widgets.toolbar.RibbonGroup;

import skala.erp.bmp.baseenums.ItemType;
import skala.erp.bmp.client.view.BasicPageView;
import skala.erp.bmp.client.view.extendedcontrols.ActionPaneButton;
import skala.erp.bmp.client.general.dataset.StatisticInventSumDataSource;
import skala.erp.bmp.client.invent.dataset.InventTableDataSource;
import skala.erp.bmp.client.sales.dataset.CustContractDataSource;
import skala.erp.bmp.client.sales.dataset.CustTableDataSource;
import skala.erp.bmp.client.sales.dataset.SalesLineDataSource;
import skala.erp.bmp.client.sales.dataset.SalesTableDataSource;
import skala.erp.bmp.client.sales.model.SalesLineRecord;
import skala.erp.bmp.client.sales.presenter.SalesPagePresenter;
import skala.erp.bmp.shared.MenuItemList;

public class SalesPageView extends BasicPageView implements
		SalesPagePresenter.Display {

	ListGrid salesTableGrid, salesLineGrid;

	SalesLineDataSource salesLineDS;

	SelectItem itemIdEditor;
	SelectItem inventLocationIdEditor;

	ActionPaneButton backButton, newSaleButton, deleteSaleButton,
			refreshSaleTableGrid, postButton, torg12Button, schetFacturaButton,
			ttnButton, undoSaleButton, paymentExportButton,
			reportsInRangeButton, saleReportsButton, reserveButton,
			cloneSaleButton, wayBillButton;

	ActionPaneButton newSaleLineButton, deleteSaleLineButton,
			refreshSaleLineGrid;

	RibbonGroup navigateGroup;

	private SelectItem custIdEditor;

	private SelectItem contractIdEditor;

	DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd.MM.yyyy");

	@SuppressWarnings("rawtypes")
	public SalesPageView(Map parameters) {
		super(parameters);
	}

	@Override
	protected SectionStack createPageBody() {

		SectionStack body = new SectionStack();
		body.setVisibilityMode(VisibilityMode.MULTIPLE);
		body.setResizeBarTarget("next");
		body.setShowResizeBar(false);
		Document.get().setTitle("BMP - Продажи");
		body.addMember(createSalesTableGrid());
		body.addMember(this.createActionPaneForLines());
		body.addMember(createSalesLineGrid());

		return body;
	}

	private ListGrid createSalesTableGrid() {
		salesTableGrid = new ListGrid();
		salesTableGrid.setWidth100();
		salesTableGrid.setHeight100();
		salesTableGrid.setShowAllRecords(true);
		salesTableGrid.setCanResizeFields(true);
		salesTableGrid.setCanEdit(false);
		salesTableGrid.setModalEditing(true);
		salesTableGrid.setEditEvent(ListGridEditEvent.CLICK);
		salesTableGrid.setListEndEditAction(RowEndEditAction.NEXT);
		salesTableGrid.setAutoSaveEdits(true);
		salesTableGrid.setSelectOnEdit(true);

		salesTableGrid.setDataSource(SalesTableDataSource.getInstance());
		salesTableGrid.setAutoFetchData(false);
		setSalesTableGridInitialCriteria();

		ListGridField saleIdField = new ListGridField("saleId", "Код продажи");
		saleIdField.setCanEdit(false);
		saleIdField.setAlign(Alignment.CENTER);
		saleIdField.setCellAlign(Alignment.LEFT);
		saleIdField.setCanDragResize(false);
		saleIdField.setHidden(true);
		saleIdField.setCanReorder(false);
		saleIdField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				String strValue = "";
				if (value != null) {
					strValue = (String) value;
				}
				if (!record.getAttributeAsBoolean("reserved"))
					return strValue;
				else
					return "<font color = #B2AAAA>" + strValue + "</font>";
			}
		});

		ListGridField invoiceNumField = new ListGridField("invoiceNum",
				"Номер счета");
		invoiceNumField.setCanEdit(true);
		invoiceNumField.setAlign(Alignment.CENTER);
		invoiceNumField.setCellAlign(Alignment.LEFT);
		invoiceNumField.setCanDragResize(false);
		invoiceNumField.setCanReorder(false);
		invoiceNumField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				String strValue = "";
				if (value != null) {
					strValue = (String) value;
				}
				if (!record.getAttributeAsBoolean("reserved"))
					return strValue;
				else
					return "<font color = #B2AAAA>" + strValue + "</font>";
			}
		});

		ListGridField factureNumField = new ListGridField("factureNum",
				"Номер счета-фактуры");
		factureNumField.setAlign(Alignment.CENTER);
		factureNumField.setCellAlign(Alignment.LEFT);
		factureNumField.setCanDragResize(false);
		factureNumField.setCanReorder(false);
		factureNumField.setRequired(false);
		factureNumField.setCanEdit(true);
		factureNumField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				String strValue = "";
				if (value != null) {
					strValue = (String) value;
				}
				if (!record.getAttributeAsBoolean("reserved"))
					return strValue;
				else
					return "<font color = #B2AAAA>" + strValue + "</font>";
			}
		});

		ListGridField custIdField = new ListGridField("custId", "Клиент");
		custIdField.setAlign(Alignment.CENTER);
		custIdField.setCellAlign(Alignment.LEFT);
		custIdField.setRequired(true);
		custIdField.setCanDragResize(false);
		custIdField.setCanReorder(false);
		custIdField.setCanEdit(false);
		custIdField.setDisplayValueFromRecord(true);
		custIdField.setOptionDataSource(CustTableDataSource.getInstance());
		custIdField.setDisplayField("shortName");
		custIdField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				String strValue = "";
				if (value != null) {
					strValue = (String) value;
				}
				if (!record.getAttributeAsBoolean("reserved"))
					return strValue;
				else
					return "<font color = #B2AAAA>" + strValue + "</font>";
			}
		});
		custIdField.setShowHover(true);
		custIdField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				return value.toString();
			}
		});

		ListGrid pickListProperties = new ListGrid();
		pickListProperties.setShowFilterEditor(true);

		custIdEditor = new SelectItem();
		custIdEditor.setValidateOnExit(true);
		custIdEditor.setAddUnknownValues(false);
		custIdEditor.setValueField("custId");
		custIdEditor.setDisplayField("shortName");
		custIdEditor.setPickListProperties(pickListProperties);
		custIdEditor.setOptionDataSource(CustTableDataSource.getInstance());
		custIdEditor.setFilterLocally(true);
		custIdEditor.setPickListWidth(600);
		ListGridField IdField = new ListGridField("shortName", "Короткое имя");
		ListGridField custNameField = new ListGridField("name", "Полное имя");
		ListGridField custGroupField = new ListGridField("custGroup", "Группа");
		ListGridField innField = new ListGridField("inn", "ИНН");
		custIdEditor.setPickListFields(IdField, custNameField, custGroupField,
				innField);
		custIdEditor
				.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
					@Override
					public Criteria getCriteria(
							FormItemFunctionContext itemContext) {

						Criterion actualCriterion = new Criterion("notActual",
								OperatorId.EQUALS, false);
						AdvancedCriteria finalyCriteria = new AdvancedCriteria(
								OperatorId.AND,
								new Criterion[] { actualCriterion });
						return finalyCriteria;
					}
				});
		custIdField.setEditorProperties(custIdEditor);

		ListGridField consIdField = new ListGridField("consId",
				"Грузополучатель");
		consIdField.setAlign(Alignment.CENTER);
		consIdField.setCellAlign(Alignment.LEFT);
		consIdField.setRequired(true);
		consIdField.setCanDragResize(false);
		consIdField.setCanReorder(false);
		consIdField.setCanEdit(true);
		consIdField.setEditorProperties(custIdEditor);
		consIdField.setDisplayValueFromRecord(true);
		consIdField.setValueField("custId");
		consIdField.setOptionDataSource(CustTableDataSource.getInstance());
		consIdField.setDisplayField("shortName");
		consIdField.setShowHover(true);
		consIdField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				return value.toString();
			}
		});
		consIdField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				String strValue = "";
				if (value != null) {
					strValue = (String) value;
				}
				if (!record.getAttributeAsBoolean("reserved"))
					return strValue;
				else
					return "<font color = #B2AAAA>" + strValue + "</font>";
			}
		});

		ListGridField contractIdField = new ListGridField("contractId",
				"Номер договора");
		contractIdField.setAlign(Alignment.CENTER);
		contractIdField.setCellAlign(Alignment.LEFT);
		contractIdField.setRequired(false);
		contractIdField.setCanEdit(false);
		contractIdField.setCanDragResize(false);
		contractIdField.setHidden(false);
		contractIdField.setDisplayValueFromRecord(true);
		contractIdField.setOptionDataSource(CustContractDataSource
				.getInstance());
		contractIdField.setDisplayField("contractNumber");
		contractIdField.setCanReorder(false);
		contractIdField.setCanFilter(false);
		contractIdField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				String strValue = "";
				if (value != null) {
					strValue = (String) value;
				}
				if (!record.getAttributeAsBoolean("reserved"))
					return strValue;
				else
					return "<font color = #B2AAAA>" + strValue + "</font>";
			}
		});

		contractIdEditor = new SelectItem();
		contractIdEditor = new SelectItem();
		contractIdEditor.setValidateOnExit(true);
		contractIdEditor.setValueField("contractId");
		contractIdEditor.setDisplayField("contractNumber");
		contractIdEditor.setPickListProperties(pickListProperties);
		contractIdEditor.setOptionDataSource(CustContractDataSource
				.getInstance());
		contractIdEditor.setPickListWidth(690);
		ListGridField number = new ListGridField("contractNumber", 120);
		ListGridField date = new ListGridField("contractDate", 120);
		date.setType(ListGridFieldType.DATE);
		date.setAlign(Alignment.LEFT);
		date.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		ListGridField person = new ListGridField("contractPerson", 250);
		ListGridField limit = new ListGridField("creditLimit", 100);
		ListGridField debt = new ListGridField("debt", 100);
		debt.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				if (value == null)
					return null;
				NumberFormat nf = NumberFormat.getFormat("#,##0.00");
				String strValue;
				try {
					strValue = nf.format(((Number) value).floatValue());
				} catch (Exception e) {
					strValue = value.toString();
				}
				if (record.getAttributeAsDouble("creditLimit") < record
						.getAttributeAsDouble("debt")
						&& record.getAttributeAsLong("recId") != 0)
					return "<div style='background-color:#FF6347'>" + strValue
							+ "</div>";
				return strValue;
			}
		});
		contractIdEditor.setPickListFields(number, date, person, debt, limit);
		contractIdEditor.setFilterLocally(true);
		contractIdEditor.setAddUnknownValues(false);
		contractIdEditor
				.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
					@Override
					public Criteria getCriteria(
							FormItemFunctionContext itemContext) {
						int rowNum;
						if (salesTableGrid.getSelectedRecord() != null)
							rowNum = salesTableGrid
									.getRecordIndex(salesTableGrid
											.getSelectedRecord());
						else {
							rowNum = salesTableGrid.getRecords().length;
						}
						String currentItemId = (String) salesTableGrid
								.getEditedCell(rowNum, "custId");
						Criterion vendCriteria = new Criterion("custId",
								OperatorId.EQUALS, currentItemId);
						AdvancedCriteria finalyCriteria = new AdvancedCriteria(
								OperatorId.AND,
								new Criterion[] { vendCriteria });
						return finalyCriteria;
					}
				});
		contractIdField.setEditorProperties(contractIdEditor);

		ListGridField saleDateField = new ListGridField("saleDate",
				"Дата создания", 110);
		saleDateField.setAlign(Alignment.CENTER);
		saleDateField.setCellAlign(Alignment.LEFT);
		saleDateField.setType(ListGridFieldType.DATE);
		saleDateField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		saleDateField.setCanDragResize(false);
		saleDateField.setCanReorder(false);
		saleDateField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				String strValue = "";
				if (value != null) {
					strValue = dateTimeFormat.format((Date) value);
				}
				if (!record.getAttributeAsBoolean("reserved"))
					return strValue;
				else
					return "<font color = #B2AAAA>" + strValue + "</font>";
			}
		});

		ListGridField postDateField = new ListGridField("postDate",
				"Дата разноски", 100);
		postDateField.setAlign(Alignment.CENTER);
		postDateField.setCellAlign(Alignment.LEFT);
		postDateField.setType(ListGridFieldType.DATE);
		postDateField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		postDateField.setCanDragResize(false);
		postDateField.setCanReorder(false);
		postDateField.setCanEdit(false);
		postDateField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				String strValue = "";
				if (value != null) {
					strValue = dateTimeFormat.format((Date) value);
				}
				if (!record.getAttributeAsBoolean("reserved"))
					return strValue;
				else
					return "<font color = #B2AAAA>" + strValue + "</font>";
			}
		});

		ListGridField postedField = new ListGridField("posted", "Разнесено");
		postedField.setAlign(Alignment.CENTER);
		postedField.setCellAlign(Alignment.LEFT);
		postedField.setType(ListGridFieldType.BOOLEAN);
		postedField.setCanEdit(false);
		postedField.setCanDragResize(false);
		postedField.setCanReorder(false);

		ListGridField reserveField = new ListGridField("reserved",
				"Забронировано");
		reserveField.setAlign(Alignment.CENTER);
		reserveField.setCellAlign(Alignment.LEFT);
		reserveField.setType(ListGridFieldType.BOOLEAN);
		reserveField.setCanEdit(false);
		reserveField.setCanDragResize(false);
		reserveField.setCanReorder(false);

		ListGridField recVersionField = new ListGridField("recVersion",
				"Версия записи");
		recVersionField.setHidden(true);

		ListGridField recIdField = new ListGridField("recId",
				"Уникальный идентификатор записи");
		recIdField.setHidden(true);

		salesTableGrid.setFields(saleIdField, invoiceNumField, saleDateField,
				factureNumField, custIdField, consIdField, contractIdField,
				postedField, postDateField, reserveField);

		return salesTableGrid;
	}

	private void setSalesTableGridInitialCriteria() {
		Criterion initialCriteria = null;

		if (getViewParameters() != null
				&& getViewParameters().containsKey("saleId"))
			initialCriteria = new Criterion("saleId", OperatorId.EQUALS,
					getViewParameters().get("saleId"));
		if (initialCriteria != null)
			salesTableGrid.setInitialCriteria(initialCriteria);

	}

	private Canvas createActionPaneForLines() {
		HLayout actionPane = new HLayout();
		actionPane.setHeight(10);
		actionPane.setBackgroundColor("#F0F5FD");

		newSaleLineButton = getIconButton(MenuItemList.SaleLineNew, false,
				Alignment.CENTER);

		deleteSaleLineButton = getIconButton(MenuItemList.SaleLineDelete,
				false, Alignment.CENTER);

		refreshSaleLineGrid = getIconButton(MenuItemList.Refresh, false,
				Alignment.CENTER);

		actionPane.addMembers(newSaleLineButton, deleteSaleLineButton,
				refreshSaleLineGrid);
		return actionPane;
	}

	private ListGrid createSalesLineGrid() {
		salesLineDS = SalesLineDataSource.getInstance();
		salesLineDS.setAutoCacheAllData(true);

		salesLineGrid = new ListGrid();
		salesLineGrid.setWidth100();
		salesLineGrid.setHeight100();
		salesLineGrid.setShowAllRecords(true);
		salesLineGrid.setCanResizeFields(true);
		salesLineGrid.setCanEdit(false);
		salesLineGrid.setModalEditing(true);
		salesLineGrid.setEditEvent(ListGridEditEvent.CLICK);
		salesLineGrid.setListEndEditAction(RowEndEditAction.NEXT);
		salesLineGrid.setAutoSaveEdits(true);
		salesLineGrid.setSelectOnEdit(true);
		salesLineGrid.setAutoFetchData(false);
		salesLineGrid.setDataSource(salesLineDS);

		ListGridField saleIdField = new ListGridField("saleId", "Код продажи");
		saleIdField.setAlign(Alignment.CENTER);
		saleIdField.setCellAlign(Alignment.LEFT);
		saleIdField.setCanEdit(false);
		saleIdField.setHidden(true);

		ListGridField lineNumField = new ListGridField("lineNum", "№", 30);
		lineNumField.setAlign(Alignment.CENTER);
		lineNumField.setCellAlign(Alignment.LEFT);
		lineNumField.setType(ListGridFieldType.FLOAT);
		lineNumField.setCanEdit(false);
		lineNumField.setHidden(false);
		lineNumField.setCanDragResize(false);
		lineNumField.setCanReorder(false);
		lineNumField.setShowGridSummary(false);

		ListGridField itemIdField = new ListGridField("itemId", "Номенклатура",
				250);
		itemIdField.setAlign(Alignment.CENTER);
		itemIdField.setCellAlign(Alignment.LEFT);
		itemIdField.setRequired(true);
		itemIdField.setCanDragResize(false);
		itemIdField.setCanReorder(false);
		itemIdField.setDisplayValueFromRecord(true);
		itemIdField.setOptionDataSource(InventTableDataSource.getInstance());
		itemIdField.setDisplayField("name");
		itemIdField.setShowHover(true);
		itemIdField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				return value.toString();
			}
		});

		ListGrid itemPickListProperties = new ListGrid();
		itemPickListProperties.setShowFilterEditor(true);
		itemPickListProperties.setGroupByField("itemGroupId");
		itemPickListProperties.setCellHeight(25);

		itemIdEditor = new SelectItem();
		itemIdEditor.setValidateOnExit(true);
		itemIdEditor.setValueField("itemId");
		itemIdEditor.setDisplayField("itemId");
		itemIdEditor.setOptionDataSource(InventTableDataSource.getInstance());
		itemIdEditor.setPickListWidth(430);
		itemIdEditor.setPickListProperties(itemPickListProperties);
		ListGridField idField = new ListGridField("itemId", "Код номенклатуры",
				100);
		ListGridField nameField = new ListGridField("name", "Наименование", 250);
		ListGridField groupField = new ListGridField("itemGroupId", "Группа",
				150);
		groupField.setHidden(true);
		ListGridField UOM = new ListGridField("UOMId", "Ед. изм.", 60);
		itemIdEditor.setPickListFields(idField, nameField, groupField, UOM);
		itemIdEditor.setFilterLocally(true);
		itemIdEditor.setAddUnknownValues(false);
		itemIdEditor
				.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
					@Override
					public Criteria getCriteria(
							FormItemFunctionContext itemContext) {

						Criterion actualCriterion = new Criterion("notActual",
								OperatorId.EQUALS, false);

						Criterion serviceCriterion = new Criterion("itemType",
								OperatorId.NOT_EQUAL, ItemType.Service.type());
						AdvancedCriteria finalyCriteria = new AdvancedCriteria(
								OperatorId.AND, new Criterion[] {
										actualCriterion, serviceCriterion });
						return finalyCriteria;
					}
				});
		itemIdField.setEditorProperties(itemIdEditor);

		ListGrid pickListProperties = new ListGrid();
		pickListProperties.setShowFilterEditor(true);
		inventLocationIdEditor = new SelectItem();
		inventLocationIdEditor.setValidateOnExit(true);
		inventLocationIdEditor.setValueField("inventLocationId");
		inventLocationIdEditor.setDisplayField("inventLocationId");
		inventLocationIdEditor.setOptionDataSource(StatisticInventSumDataSource
				.getInstance());
		inventLocationIdEditor.setFilterLocally(true);
		inventLocationIdEditor.setAddUnknownValues(false);
		inventLocationIdEditor.setPickListWidth(580);
		inventLocationIdEditor.setPickListProperties(pickListProperties);
		ListGridField locationId = new ListGridField("inventLocationId",
				"Склад");
		ListGridField avail = new ListGridField("totalQty",
				"Доступно на складе");
		avail.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				if (value == null)
					return null;
				String displayValue;
				NumberFormat nf = NumberFormat.getFormat("#####0.00");
				try {
					displayValue = nf.format(((Number) value).floatValue());
				} catch (Exception e) {
					displayValue = value.toString();
				}
				displayValue = displayValue.replaceAll(",", ".");
				int gridRowNum;
				if (salesLineGrid.getSelectedRecord() != null)
					gridRowNum = salesLineGrid.getRecordIndex(salesLineGrid
							.getSelectedRecord());
				else {
					gridRowNum = salesLineGrid.getRecords().length;
				}
				String qtyStr = salesLineGrid.getEditedCell(gridRowNum, "qty")
						.toString();
				qtyStr = qtyStr.replaceAll(",", ".");
				if (Double.parseDouble(qtyStr) >= Double
						.parseDouble(displayValue))
					return "<div style='background-color:#FF6347'>"
							+ displayValue + "</div>";
				else
					return "<div style='background-color:#00FF7F'>"
							+ displayValue + "</div>";
			}
		});
		inventLocationIdEditor.setPickListFields(locationId, avail, UOM);
		inventLocationIdEditor
				.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
					@Override
					public Criteria getCriteria(
							FormItemFunctionContext itemContext) {
						int rowNum;
						if (salesLineGrid.getSelectedRecord() != null)
							rowNum = salesLineGrid.getRecordIndex(salesLineGrid
									.getSelectedRecord());
						else {
							rowNum = salesLineGrid.getRecords().length;
						}
						String currentItemId = (String) salesLineGrid
								.getEditedCell(rowNum, "itemId");
						Criterion itemCritetion = new Criterion("itemId",
								OperatorId.EQUALS, currentItemId);
						AdvancedCriteria finalyCriteria = new AdvancedCriteria(
								OperatorId.AND,
								new Criterion[] { itemCritetion });
						return finalyCriteria;
					}
				});

		ListGridField packQtyField = new ListGridField("packQty",
				"Кол-во упаковок");
		packQtyField.setAlign(Alignment.CENTER);
		packQtyField.setCellAlign(Alignment.LEFT);
		packQtyField.setRequired(true);
		packQtyField.setType(ListGridFieldType.FLOAT);
		packQtyField.setCanDragResize(false);
		packQtyField.setCanReorder(false);
		packQtyField.setCanEdit(true);
		packQtyField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				if (value == null)
					return null;
				NumberFormat nf = NumberFormat.getFormat("#,##0.00");
				try {
					return nf.format(((Number) value).floatValue());
				} catch (Exception e) {
					return value.toString();
				}
			}
		});

		packQtyField.addEditorEnterHandler(new EditorEnterHandler() {

			@Override
			public void onEditorEnter(EditorEnterEvent event) {
				if (event.getRecord() != null) {
					SalesLineRecord record = (SalesLineRecord) event
							.getRecord();
					if (record.getPackQty() == 0) {
						event.getRecord().setAttribute("packQty", "");
					}
				}

			}
		});

		ListGridField qtyField = new ListGridField("qty", "Количество");
		qtyField.setAlign(Alignment.CENTER);
		qtyField.setCellAlign(Alignment.LEFT);
		qtyField.setRequired(true);
		qtyField.setType(ListGridFieldType.FLOAT);
		qtyField.setCanDragResize(false);
		qtyField.setCanReorder(false);
		qtyField.setCanEdit(false);
		qtyField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				if (value == null)
					return null;
				NumberFormat nf = NumberFormat.getFormat("#,##0.00");
				try {
					return nf.format(((Number) value).floatValue());
				} catch (Exception e) {
					return value.toString();
				}
			}
		});

		ListGridField salePriceField = new ListGridField("salePrice",
				"Цена продажи (руб.)");
		salePriceField.setAlign(Alignment.CENTER);
		salePriceField.setCellAlign(Alignment.LEFT);
		salePriceField.setRequired(true);
		salePriceField.setType(ListGridFieldType.FLOAT);
		salePriceField.setCanDragResize(false);
		salePriceField.setCanReorder(false);
		salePriceField.setShowGridSummary(false);
		salePriceField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				if (value == null)
					return null;
				NumberFormat nf = NumberFormat.getFormat("#,##0.00");
				try {
					return nf.format(((Number) value).floatValue());
				} catch (Exception e) {
					return value.toString();
				}
			}
		});

		ListGridField amountField = new ListGridField("amount", "Сумма");
		amountField.setAlign(Alignment.CENTER);
		amountField.setCellAlign(Alignment.LEFT);
		amountField.setCanEdit(false);
		amountField.setType(ListGridFieldType.FLOAT);
		amountField.setCanDragResize(false);
		amountField.setCanReorder(false);
		amountField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				if (value == null)
					return null;
				NumberFormat nf = NumberFormat.getFormat("#,##0.00");
				try {
					return nf.format(((Number) value).floatValue());
				} catch (Exception e) {
					return value.toString();
				}
			}
		});

		ListGridField vatValueField = new ListGridField("vatValue", "НДС(%)",
				80);
		vatValueField.setAlign(Alignment.CENTER);
		vatValueField.setCellAlign(Alignment.LEFT);
		vatValueField.setCanEdit(true);
		vatValueField.setType(ListGridFieldType.FLOAT);
		vatValueField.setCanDragResize(false);
		vatValueField.setCanReorder(false);
		vatValueField.setShowGridSummary(false);
		vatValueField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				if (value == null)
					return null;
				NumberFormat nf = NumberFormat.getFormat("#,##0.00");
				try {
					return nf.format(((Number) value).floatValue());
				} catch (Exception e) {
					return value.toString();
				}
			}
		});

		ListGridField vatAmountField = new ListGridField("vatAmount",
				"Сумма НДС");
		vatAmountField.setAlign(Alignment.CENTER);
		vatAmountField.setCellAlign(Alignment.LEFT);
		vatAmountField.setCanEdit(false);
		vatAmountField.setType(ListGridFieldType.FLOAT);
		vatAmountField.setCanDragResize(false);
		vatAmountField.setHidden(true);
		vatAmountField.setCanReorder(false);
		vatAmountField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				if (value == null)
					return null;
				NumberFormat nf = NumberFormat.getFormat("#,##0.00");
				try {
					return nf.format(((Number) value).floatValue());
				} catch (Exception e) {
					return value.toString();
				}
			}
		});

		ListGridField locationIdField = new ListGridField("locationId",
				"Склад продажи", 120);
		locationIdField.setAlign(Alignment.CENTER);
		locationIdField.setCellAlign(Alignment.LEFT);
		locationIdField.setRequired(true);
		locationIdField.setCanDragResize(false);
		locationIdField.setCanReorder(false);
		locationIdField.setSummaryFunction(new SummaryFunction() {

			@Override
			public Object getSummaryValue(Record[] records, ListGridField field) {
				return "Итого:";
			}
		});
		locationIdField.setEditorProperties(inventLocationIdEditor);

		ListGridField recVersionField = new ListGridField("recVersion",
				"Версия записи");
		recVersionField.setHidden(true);

		ListGridField recIdField = new ListGridField("recId",
				"Уникальный идентификатор записи");
		recIdField.setHidden(true);

		salesLineGrid.setFields(lineNumField, saleIdField, itemIdField,
				packQtyField, qtyField, salePriceField, vatValueField,
				locationIdField, amountField, vatAmountField, recVersionField,
				recIdField);
		return salesLineGrid;
	}

	@Override
	protected TabSet createActionPane() {
		createActionPaneTabSet();

		final RibbonBar salesTabRibbonBar = createRibbonBar();

		navigateGroup = createRibbonGroup("Навигация");
		final RibbonGroup newPurchGroup = createRibbonGroup("Создать");
		final RibbonGroup maintainPurchGroup = createRibbonGroup("Обслуживание");
		final RibbonGroup processGroup = createRibbonGroup("Процесс");
		final RibbonGroup listPurchGroup = createRibbonGroup("Список");
		final RibbonGroup reportsGroup = createRibbonGroup("Отчеты");

		backButton = getIconButton(MenuItemList.Back, false, Alignment.CENTER);
		navigateGroup.addControl(backButton);
		// Вкладка Номенклатура
		newSaleButton = getIconButton(MenuItemList.SaleNew, false,
				Alignment.CENTER);
		cloneSaleButton = getIconButton(MenuItemList.SaleClone, false,
				Alignment.CENTER);
		newPurchGroup.addControl(newSaleButton);
		newPurchGroup.addControl(cloneSaleButton);

		deleteSaleButton = getIconButton(MenuItemList.SaleDelete, false,
				Alignment.CENTER);
		maintainPurchGroup.addControl(deleteSaleButton);

		postButton = getIconButton(MenuItemList.SalePost, false,
				Alignment.CENTER);
		undoSaleButton = getIconButton(MenuItemList.SaleUndo, false,
				Alignment.CENTER);
		reserveButton = getIconButton(MenuItemList.SaleReserve, false,
				Alignment.CENTER);
		processGroup.addControl(postButton);
		processGroup.addControl(undoSaleButton);
		processGroup.addControl(reserveButton);

		refreshSaleTableGrid = getIconButton(MenuItemList.Refresh, false,
				Alignment.CENTER);
		listPurchGroup.addControl(refreshSaleTableGrid);
		salesTabRibbonBar.addMember(navigateGroup);
		salesTabRibbonBar.addMember(newPurchGroup);
		salesTabRibbonBar.addMember(maintainPurchGroup);
		salesTabRibbonBar.addMember(processGroup);
		salesTabRibbonBar.addMember(listPurchGroup);
		Tab purchTab = new Tab("Документ");
		purchTab.setPane(salesTabRibbonBar);

		final RibbonBar reportsBar = createRibbonBar();
		torg12Button = getIconButton(MenuItemList.SaleTorg12, false,
				Alignment.CENTER);
		schetFacturaButton = getIconButton(MenuItemList.SaleSchetFactura,
				false, Alignment.CENTER);
		ttnButton = getIconButton(MenuItemList.SaleTTN, false, Alignment.CENTER);
		paymentExportButton = getIconButton(MenuItemList.SalePaymentExport,
				false, Alignment.CENTER);
		wayBillButton = getIconButton(MenuItemList.SaleWayBill, false,
				Alignment.CENTER);
		saleReportsButton = getIconButton(MenuItemList.SaleReports, false,
				Alignment.CENTER);
		reportsInRangeButton = getIconButton(MenuItemList.SaleRepotsInRange,
				false, Alignment.CENTER);
		reportsGroup.addControl(paymentExportButton);
		reportsGroup.addControl(schetFacturaButton);
		reportsGroup.addControl(torg12Button);
		reportsGroup.addControl(wayBillButton);
		reportsGroup.addControl(ttnButton);
		reportsGroup.addControl(saleReportsButton);
		reportsGroup.addControl(reportsInRangeButton);
		reportsBar.addMember(reportsGroup);

		Tab reportsTab = new Tab("Отчеты");
		reportsTab.setPane(reportsBar);

		getActionPaneTabSet().addTab(purchTab);
		getActionPaneTabSet().addTab(reportsTab);
		getActionPaneTabSet().setHeight(90);

		return getActionPaneTabSet();
	}

	public ListGrid getSalesTableGrid() {
		return salesTableGrid;
	}

	public ListGrid getSalesLineGrid() {
		return salesLineGrid;
	}

	public ActionPaneButton getNewSaleButton() {
		return newSaleButton;
	}

	public ActionPaneButton getDeleteSaleButton() {
		return deleteSaleButton;
	}

	public ActionPaneButton getRefreshSaleTableGrid() {
		return refreshSaleTableGrid;
	}

	public ActionPaneButton getPostButton() {
		return postButton;
	}

	public ActionPaneButton getNewSaleLineButton() {
		return newSaleLineButton;
	}

	public ActionPaneButton getDeleteSaleLineButton() {
		return deleteSaleLineButton;
	}

	public ActionPaneButton getRefreshSaleLineGrid() {
		return refreshSaleLineGrid;
	}

	@Override
	public ActionPaneButton getRefreshSaleTableGridButton() {
		return refreshSaleTableGrid;
	}

	@Override
	public ActionPaneButton getPostSaleButton() {
		return postButton;
	}

	@Override
	public ActionPaneButton getRefreshSaleLineGridButton() {
		return refreshSaleLineGrid;
	}

	@Override
	public ActionPaneButton getBackButton() {
		return backButton;
	}

	@Override
	public RibbonGroup getNavigateGroup() {
		return navigateGroup;
	}

	@Override
	public ActionPaneButton getTorg12Button() {
		return torg12Button;
	}

	@Override
	public ActionPaneButton getSchetFacturaButton() {
		return schetFacturaButton;
	}

	@Override
	public ActionPaneButton getTTNFilename() {
		return ttnButton;
	}

	@Override
	public ActionPaneButton getUndoSaleButton() {
		return undoSaleButton;
	}

	@Override
	public ActionPaneButton getPaymentExportButton() {
		return paymentExportButton;
	}

	@Override
	public ActionPaneButton getReportsInRangeButton() {
		return reportsInRangeButton;
	}

	@Override
	public ActionPaneButton getSaleReportsButton() {
		return saleReportsButton;
	}

	@Override
	public ActionPaneButton getReserveButton() {
		return reserveButton;
	}

	@Override
	public ActionPaneButton getCloneSaleButton() {
		return cloneSaleButton;
	}

	@Override
	public ActionPaneButton getWayBillBuuton() {
		return wayBillButton;
	}

}
