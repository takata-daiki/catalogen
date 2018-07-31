package com.swinarta.sunflower.web.gwt.client.widget.listgrid;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.swinarta.sunflower.web.gwt.client.util.StringFormatUtil;
import com.smartgwt.client.data.Record;
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

public class ReceivingOrderDetailListGrid extends ListGrid{
	
	private ListGridField skuField = new ListGridField("sku", "SKU");
	private ListGridField barcodeField = new ListGridField("barcode", "Barcode");
	private ListGridField descriptionField = new ListGridField("desc", "Desc.");
	private ListGridField orderQtyField = new ListGridField("orderQty", "Order");
	private ListGridField qtyField = new ListGridField("qty", "Receive");
	private ListGridField measurementField = new ListGridField("orderMeasurement", "Measurement");
	private ListGridField costPriceOrderField = new ListGridField("costPrice", "Cost Price");
	private ListGridSummaryField subtotalField = new ListGridSummaryField("subtotal", "Subtotal");
	
	@Inject
	public ReceivingOrderDetailListGrid(
			@Named("ReceivingOrderDetails") RestDataSource roDetailsDataSource,
			@Named("Positive") FloatRangeValidator positiveFloatRangeValidatorProvider
		){
				
		setDataSource(roDetailsDataSource);
		setHeaderHeight(44);
		
		skuField.setAlign(Alignment.CENTER);
		skuField.setWidth(65);
		skuField.setCellFormatter(new CellFormatter() {		
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Record productRecord = record.getAttributeAsRecord("poDetail").getAttributeAsRecord("product");
				return productRecord.getAttribute("sku");
			}
		});		

		barcodeField.setAlign(Alignment.CENTER);
		barcodeField.setWidth(90);
		barcodeField.setCellFormatter(new CellFormatter() {
			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Record productRecord = record.getAttributeAsRecord("poDetail").getAttributeAsRecord("product");
				return productRecord.getAttribute("barcode");
			}
		});

		descriptionField.setCellFormatter(new CellFormatter() {
			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Record productRecord = record.getAttributeAsRecord("poDetail").getAttributeAsRecord("product");
				return productRecord.getAttribute("longDescription");				
			}
		});

		orderQtyField.setWidth(60);
		orderQtyField.setAlign(Alignment.RIGHT);
		orderQtyField.setIncludeInRecordSummary(false);
		orderQtyField.setShowGridSummary(false);
		orderQtyField.setType(ListGridFieldType.FLOAT);
		orderQtyField.setCellFormatter(new CellFormatter() {
			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Record poDetailRecord = record.getAttributeAsRecord("poDetail");
				return StringFormatUtil.getFormat(poDetailRecord.getAttributeAsFloat("qty"));
			}
		});

		qtyField.setWidth(60);
		qtyField.setAlign(Alignment.RIGHT);
		qtyField.setIncludeInRecordSummary(true);
		qtyField.setShowGridSummary(false);
		qtyField.setType(ListGridFieldType.FLOAT);

		measurementField.setAlign(Alignment.CENTER);
		measurementField.setWidth(60);
		measurementField.setCellFormatter(new CellFormatter() {
			
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Record poDetailRecord = record.getAttributeAsRecord("poDetail");
				Record productRecord = poDetailRecord.getAttributeAsRecord("product");				
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
				new HeaderSpan("Qty", new String[]{"orderQty", "qty", "orderMeasurement"})
				);
		
		setFields(skuField, barcodeField, descriptionField, orderQtyField, qtyField,
				measurementField, costPriceOrderField, subtotalField);

		
		skuField.setCanEdit(false);
		barcodeField.setCanEdit(false);
		descriptionField.setCanEdit(false);
		subtotalField.setCanEdit(false);
		costPriceOrderField.setCanEdit(false);		
		measurementField.setCanEdit(false);
		orderQtyField.setCanEdit(false);
						
		setShowGridSummary(true);
		setShowAllRecords(true);
		setModalEditing(true);
		
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
	
	public void setStatus(String status){
		if("NEW".equalsIgnoreCase(status)){
			qtyField.setCanEdit(true);
			setSelectionType(SelectionStyle.SINGLE);
			setSelectionAppearance(SelectionAppearance.ROW_STYLE);
		}else{
			setCanEdit(false);
			setSelectionType(SelectionStyle.SIMPLE);
			setSelectionAppearance(SelectionAppearance.CHECKBOX);
		}
	}
	
}