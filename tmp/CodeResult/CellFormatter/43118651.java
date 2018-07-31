package com.swinarta.sunflower.web.gwt.client.widget.listgrid;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.swinarta.sunflower.web.gwt.client.util.StringFormatUtil;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.RecordSummaryFunctionType;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.widgets.form.validator.FloatRangeValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HeaderSpan;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.ListGridSummaryField;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;

public class PurchasingOrderDetailListGrid extends ListGrid implements AddProductToDetail{

	public static Integer EDITABLE_COLUMN = 3;
	public static Integer STOCK_COLUMN = 14;
	
	private Boolean stockIsHidden = false; 
	
	private ListGridField skuField = new ListGridField("sku", "SKU");
	private ListGridField stockCurrentField = new ListGridField("currStock", "Curr.");
	private ListGridField barcodeField = new ListGridField("barcode", "Barcode");
	private ListGridField descriptionField = new ListGridField("desc", "Desc.");
	private ListGridField measurementField = new ListGridField("orderMeasurement", "Measurement");
	private ListGridField buyingPriceField = new ListGridField("buyingPrice", "Buying Price");
	private ListGridField disc1Field = new ListGridField("disc1", "%-1");
	private ListGridField disc2Field = new ListGridField("disc2", "%-2");
	private ListGridField disc3Field = new ListGridField("disc3", "%-3");
	private ListGridField disc4Field = new ListGridField("disc4", "%-4");
	private ListGridField discPriceField = new ListGridField("discPrice", "Disc. Price");
	private ListGridField taxInclField = new ListGridField("taxIncl", "Tax.");
	private ListGridField qtyField = new ListGridField("qty", "Order Qty");
	private ListGridField costPriceOrderField = new ListGridField("costPrice", "Cost Price");		
	private ListGridSummaryField subtotalField = new ListGridSummaryField("subtotal", "Subtotal");
	
	private RestDataSource poDetailsDataSource;
	private Integer poId;
	
	@Inject
	public PurchasingOrderDetailListGrid(
			@Named("PurchasingOrderDetails") RestDataSource poDetailsDataSource,
			@Named("Positive") FloatRangeValidator positiveFloatRangeValidatorProvider
		){
		
		this.poDetailsDataSource = poDetailsDataSource;
		
		final ListGrid list = this;
		
		setDataSource(poDetailsDataSource);
		setHeaderHeight(44);
				
		skuField.setAlign(Alignment.CENTER);
		skuField.setWidth(65);
		skuField.setCellFormatter(new CellFormatter() {			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Record productRecord = record.getAttributeAsRecord("product");
				String sku = productRecord.getAttribute("sku");
				list.getRecord(rowNum).setAttribute("sku", sku);
				return sku;
			}
		});		
			
		barcodeField.setAlign(Alignment.CENTER);
		barcodeField.setWidth(90);
		barcodeField.setCellFormatter(new CellFormatter() {
			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Record productRecord = record.getAttributeAsRecord("product");
				String barcode = productRecord.getAttribute("barcode");
				list.getRecord(rowNum).setAttribute("barcode", barcode);
				return barcode;
			}
		});

		descriptionField.setCellFormatter(new CellFormatter() {
			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Record productRecord = record.getAttributeAsRecord("product");
				return productRecord.getAttribute("longDescription");
			}
		});
				
		measurementField.setAlign(Alignment.CENTER);
		measurementField.setWidth(60);
		measurementField.setCellFormatter(new CellFormatter() {
			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				Record productRecord = record.getAttributeAsRecord("product");
				Record buyingRecord = productRecord.getAttributeAsRecord("buying");
				Record buyingMeasurementRecord = buyingRecord.getAttributeAsRecord("measurement");
				Record productMeasurementRecord = productRecord.getAttributeAsRecord("productMeasurement");
				
				Boolean isMutable = buyingMeasurementRecord.getAttributeAsBoolean("mutable");
				
				Integer measurementQty;
				
				if(!isMutable){
					measurementQty = buyingMeasurementRecord.getAttributeAsInt("defaultQty");
				}else{
					measurementQty = getMeasurementQty(productMeasurementRecord, buyingMeasurementRecord);
				}
								
				return buyingMeasurementRecord.getAttribute("code") + " (" + measurementQty + ")";
			}
		});

		buyingPriceField.setAlign(Alignment.RIGHT);
		buyingPriceField.setWidth(70);
		
		buyingPriceField.setCellFormatter(new CellFormatter() {
			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Record productRecord = record.getAttributeAsRecord("product");
				Record buyingRecord = productRecord.getAttributeAsRecord("buying");
				return StringFormatUtil.getFormat(buyingRecord.getAttributeAsDouble("buyingPrice"));
			}
		});

		disc1Field.setWidth(35);
		disc1Field.setAlign(Alignment.RIGHT);
		disc1Field.setCellFormatter(new CellFormatter() {
			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Record productRecord = record.getAttributeAsRecord("product");
				Record buyingRecord = productRecord.getAttributeAsRecord("buying");
				return StringFormatUtil.getFormat(buyingRecord.getAttributeAsDouble("disc1"));
			}
		});

		disc2Field.setWidth(35);
		disc2Field.setAlign(Alignment.RIGHT);
		disc2Field.setCellFormatter(new CellFormatter() {
			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Record productRecord = record.getAttributeAsRecord("product");
				Record buyingRecord = productRecord.getAttributeAsRecord("buying");
				return StringFormatUtil.getFormat(buyingRecord.getAttributeAsDouble("disc2"));
			}
		});

		disc3Field.setWidth(35);		
		disc3Field.setAlign(Alignment.RIGHT);
		disc3Field.setCellFormatter(new CellFormatter() {
			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Record productRecord = record.getAttributeAsRecord("product");
				Record buyingRecord = productRecord.getAttributeAsRecord("buying");
				return StringFormatUtil.getFormat(buyingRecord.getAttributeAsDouble("disc3"));
			}
		});

		disc4Field.setWidth(35);		
		disc4Field.setAlign(Alignment.RIGHT);
		disc4Field.setCellFormatter(new CellFormatter() {
			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Record productRecord = record.getAttributeAsRecord("product");
				Record buyingRecord = productRecord.getAttributeAsRecord("buying");
				return StringFormatUtil.getFormat(buyingRecord.getAttributeAsDouble("disc4"));
			}
		});

		discPriceField.setWidth(70);		
		discPriceField.setAlign(Alignment.RIGHT);
		discPriceField.setCellFormatter(new CellFormatter() {
			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Record productRecord = record.getAttributeAsRecord("product");
				Record buyingRecord = productRecord.getAttributeAsRecord("buying");
				return StringFormatUtil.getFormat(buyingRecord.getAttributeAsDouble("discPrice"));				
			}
		});

		taxInclField.setWidth(30);
		taxInclField.setAlign(Alignment.CENTER);
		taxInclField.setCellFormatter(new CellFormatter() {
			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Record productRecord = record.getAttributeAsRecord("product");
				Record buyingRecord = productRecord.getAttributeAsRecord("buying");
				return StringFormatUtil.getYesNo((buyingRecord.getAttributeAsBoolean("taxIncluded")));
			}
		});

		stockCurrentField.setAlign(Alignment.RIGHT);
		stockCurrentField.setWidth(35);				
		stockCurrentField.setCellFormatter(new CellFormatter() {
			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Record productRecord = record.getAttributeAsRecord("product");
				Record stockRecord = productRecord.getAttributeAsRecord("stock");				
				
				if(stockRecord != null && stockRecord.getAttributeAsFloat("current") != null){
					return StringFormatUtil.getFormat(stockRecord.getAttributeAsFloat("current"));
				}else{
					return StringFormatUtil.getFormat(0f);
				}
		
			}
		});

		qtyField.setWidth(60);
		qtyField.setAlign(Alignment.RIGHT);
		qtyField.setIncludeInRecordSummary(true);
		qtyField.setShowGridSummary(false);
		qtyField.setType(ListGridFieldType.FLOAT);
		qtyField.setValidators(positiveFloatRangeValidatorProvider);
		qtyField.addCellSavedHandler(new CellSavedHandler() {
			
			public void onCellSaved(CellSavedEvent event) {
				Float newQty = ((Number)event.getNewValue()).floatValue();
				if(newQty.intValue() <= 0){
					int rownum = event.getRowNum();
					deleteRecordInList(rownum);
				}
			}
		});

		costPriceOrderField.setWidth(70);
		costPriceOrderField.setAlign(Alignment.RIGHT);
		costPriceOrderField.setIncludeInRecordSummary(true);
		costPriceOrderField.setShowGridSummary(true);
		costPriceOrderField.setCellFormatter(new CellFormatter() {
			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return StringFormatUtil.getFormat(((Number)value).doubleValue());
			}
		});

		subtotalField.setWidth(90);
		subtotalField.setAlign(Alignment.RIGHT);
		subtotalField.setRecordSummaryFunction(RecordSummaryFunctionType.MULTIPLIER); 
		subtotalField.setSummaryFunction(SummaryFunctionType.SUM);
		subtotalField.setShowGridSummary(true);
		subtotalField.setSummaryTitle("Subtotal");		

		subtotalField.setCellFormatter(new CellFormatter() {			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(value != null){
					return StringFormatUtil.getFormat(((Number)value).doubleValue());
				}
				return null;
			}
		});
				
		setHeaderSpans(
				new HeaderSpan("Product", new String[]{"sku", "barcode", "desc"}),
				new HeaderSpan("Buying", new String[]{"buyingPrice", "disc1", "disc2", "disc3", "disc4", "discPrice", "taxIncl", "costPrice"}),
				new HeaderSpan("Stock", new String[]{"currStock"}),
				new HeaderSpan("Order", new String[]{"qty", "orderMeasurement"})
				);
		
		setFields(skuField, barcodeField, descriptionField, qtyField, measurementField, 
				buyingPriceField, disc1Field, disc2Field, disc3Field, disc4Field, 
				discPriceField, taxInclField, costPriceOrderField, subtotalField,  
				stockCurrentField);
		
		skuField.setCanEdit(false);
		barcodeField.setCanEdit(false);
		descriptionField.setCanEdit(false);
		buyingPriceField.setCanEdit(false);
		disc1Field.setCanEdit(false);
		disc2Field.setCanEdit(false);
		disc3Field.setCanEdit(false);
		disc4Field.setCanEdit(false);
		discPriceField.setCanEdit(false);
		taxInclField.setCanEdit(false);
		subtotalField.setCanEdit(false);
		stockCurrentField.setCanEdit(false);
		costPriceOrderField.setCanEdit(false);		
		measurementField.setCanEdit(false);
		
		qtyField.setCanEdit(true);
				
		setShowGridSummary(true);
		setShowAllRecords(true);
		setModalEditing(true);
		setAnimateRemoveRecord(true);
		
	}
	
	@Override
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {

		if(stockIsHidden){
			return super.getCellCSSText(record, rowNum, colNum);
		}
		
		Record productRecord = record.getAttributeAsRecord("product");
		Record stockRecord = productRecord.getAttributeAsRecord("stock");

		float currStock = 0;
		if(stockRecord != null && stockRecord.getAttributeAsFloat("current") != null){
			currStock = stockRecord.getAttributeAsFloat("current");
		}

		if(currStock < 0 && colNum == STOCK_COLUMN){
			return "color:red;";
		}else{
			return super.getCellCSSText(record, rowNum, colNum);
		}

	}
	
	protected void deleteRecordInList(final int rownum) {		
		Record record = getRecord(rownum);
		removeData(record);
	}

	public void setStatus(String status){

		if("PROCESSED".equalsIgnoreCase(status)){
			setSelectionType(SelectionStyle.SIMPLE);
			setSelectionAppearance(SelectionAppearance.CHECKBOX);
		}else{
			setSelectionType(SelectionStyle.SINGLE);
			setSelectionAppearance(SelectionAppearance.ROW_STYLE);
		}
		
		if(!"NEW".equalsIgnoreCase(status)){
			hideField("currStock");
			stockIsHidden = true;
		}
		
		if("CANCELLED".equalsIgnoreCase(status) || "COMPLETED".equalsIgnoreCase(status)){
			hideField("subtotal");
			hideField("buyingPrice");
			hideField("disc1");
			hideField("disc2");
			hideField("disc3");
			hideField("disc4");
			hideField("discPrice");
			hideField("taxIncl");
		}

		if("CANCELLED".equalsIgnoreCase(status)){
			hideField("costPrice");
		}
	}

	public void setPoId(Integer poId) {
		this.poId = poId;
	}

	private Integer getMeasurementQty(Record productMeasurementRecord, Record buyingMeasurementRecord){
		
		Record measurementFromPmRecord = productMeasurementRecord.getAttributeAsRecord("measurement");		
		Integer measurementQty = buyingMeasurementRecord.getAttributeAsInt("defaultQty");
		
		if(productMeasurementRecord != null && measurementFromPmRecord != null){
			if(measurementFromPmRecord.getAttributeAsInt("id").intValue() == buyingMeasurementRecord.getAttributeAsInt("id").intValue()){
				measurementQty = productMeasurementRecord.getAttributeAsInt("overrideQty");
			}			
		}
		
		return measurementQty;
	}

	public void addProduct(Integer productId, Float qty){
		Record record = new Record();
		record.setAttribute("qty", qty);
		record.setAttribute("poId", poId);
		record.setAttribute("productId", productId);
		final ListGrid list = this;
		poDetailsDataSource.addData(record, new DSCallback() {
			
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				DeferredCommand.addCommand(new Command() {
					public void execute() {
						list.scrollToRow(list.getTotalRows()-1);
					}
				});
			}
		});
	}

	public int indexOfProductExistInDetail(String text) {
		String propertyToSearch;
		RecordList recordList = getDataAsRecordList();
		int index;
		
		if(text.length() > 10){
			propertyToSearch = "barcode";
		}else{
			propertyToSearch = "sku";
		}
		
		index = recordList.findIndex(propertyToSearch, text);
		
		return index;
	}

	public void productExist(int index) {
		startEditing(index, PurchasingOrderDetailListGrid.EDITABLE_COLUMN, false);	
	}
	
}
