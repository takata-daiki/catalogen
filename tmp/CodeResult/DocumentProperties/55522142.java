package com.sap.spc.remote.client.object.imp.rfc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.sap.mw.jco.JCO;
import com.sap.spc.remote.client.ClientException;
import com.sap.spc.remote.client.ResourceAccessor;
import com.sap.spc.remote.client.object.DimensionalValue;
import com.sap.spc.remote.client.object.DynamicReturn;
import com.sap.spc.remote.client.object.IPCDocument;
import com.sap.spc.remote.client.object.IPCDocumentProperties;
import com.sap.spc.remote.client.object.IPCException;
import com.sap.spc.remote.client.object.IPCItem;
import com.sap.spc.remote.client.object.IPCItemProperties;
import com.sap.spc.remote.client.object.imp.DefaultDynamicReturn;
import com.sap.spc.remote.client.object.imp.DefaultIPCDocument;
import com.sap.spc.remote.client.object.imp.DefaultIPCDocumentProperties;
import com.sap.spc.remote.client.object.imp.DefaultIPCItem;
import com.sap.spc.remote.client.object.imp.DefaultIPCItemProperties;
import com.sap.spc.remote.client.object.imp.DefaultIPCSession; 
import com.sap.spc.remote.client.object.imp.DefaultTTEDocument;
import com.sap.spc.remote.client.rfc.PricingConverter;
import com.sap.spc.remote.client.rfc.function.ComProductReadViaRfc;
import com.sap.spc.remote.client.rfc.function.IFunctionGroup;
import com.sap.spc.remote.client.rfc.function.SpcChangeDocument;
import com.sap.spc.remote.client.rfc.function.SpcCreateDocument;
import com.sap.spc.remote.client.rfc.function.SpcCreateItems;
import com.sap.spc.remote.client.rfc.function.SpcGetDocumentAttrInfo;
import com.sap.spc.remote.client.rfc.function.SpcGetDocumentInfo;
import com.sap.spc.remote.client.rfc.function.SpcGetPricingDocumentInfo;
import com.sap.spc.remote.client.rfc.function.SpcRemoveItems;
import com.sap.tc.logging.Category;
import com.sap.tc.logging.Location;
import com.sap.tc.logging.Severity;

/**
 * @author I026584
 *
 */
public class RfcDefaultIPCDocument
    extends DefaultIPCDocument
    implements IPCDocument {
        

	private static final Category category = ResourceAccessor.category;
        private static final Location location = ResourceAccessor.getLocation(RfcDefaultIPCDocument.class);
        
        protected static final String TOTAL_NET_VALUE = "TOTAL_NET_VALUE";
        protected static final String TOTAL_GROSS_VALUE = "TOTAL_GROSS_VALUE";
        protected static final String TOTAL_TAX_VALUE = "TOTAL_TAX_VALUE";
        protected static final String SPC_DUMMY_PRODUCT_GUID  = "FFFFFFFFFFFFFDFCF000000000000000"; // guid returned from SPC_GET_DOCUMENT_INFO for "SPC dummy product"
        protected static final String SPC_DUMMY_PRODUCT_GUID2 = "00000000000000000000000000000000"; // another guid returned from SPC_GET_DOCUMENT_INFO for "SPC dummy product"
        protected static final String SPC_DUMMY_PRODUCT_ID = "SPC dummy product";
        protected static final String INITIAL_DOCUMENT_ID = "00000000000000000000000000000000";
        
        protected HashMap guidsToIds;
        
        protected RfcDefaultIPCDocument(DefaultIPCSession session, DefaultIPCDocumentProperties attr) throws IPCException {
            _session = session;
            _properties = attr;
            _items = new Vector();
            _itemsCache = null;
            _isClosed = false;
            
            String app = _properties.getApplication();
            if (app != null){
                _application = app;
            }            

            _netValueWithoutFreight = null;
            _taxValue = null;
            _grossValue = null;
            _currencyUnit = null;
            _freight = null;
            _netValue = null;
            _tteDocument = RfcDefaultTTEDocument.C_NULL;

            createDocument();
        }


        protected RfcDefaultIPCDocument(DefaultIPCSession session, String documentId) throws IPCException {
            _session = session;
            _documentId = documentId;
            _items = new Vector();
            _itemsCache = null;
            _isClosed = false;
            _properties = factory.newIPCDocumentProperties();

            _netValue = null;
            _netValueWithoutFreight = null;
            _taxValue = null;
            _grossValue = null;
            _currencyUnit = null;
            _freight = null;

            _pricingConditionSet = new RfcDefaultIPCPricingConditionSet(this);
            _tteDocument = RfcDefaultTTEDocument.C_NULL;

            initializeValues(_session, this.getId());
            initializeItems();
        }

		public IPCDocumentProperties getPropertiesFromServer() {

			location.entering("getPropertiesFromServer()");
            
			IPCDocumentProperties documentProperties = factory.newIPCDocumentProperties();

			JCO.Function functionSpcGetDocumentAttrInfo = null;
			try {
				functionSpcGetDocumentAttrInfo =
					((RFCDefaultClient) _session.getClient()).getFunction(
						IFunctionGroup.SPC_GET_DOCUMENT_ATTR_INFO);
			} catch (ClientException e) {
				throw new IPCException(e);
			}
			JCO.ParameterList im =
				functionSpcGetDocumentAttrInfo.getImportParameterList();
			JCO.ParameterList ex =
				functionSpcGetDocumentAttrInfo.getExportParameterList();
			JCO.ParameterList tbl =
				functionSpcGetDocumentAttrInfo.getTableParameterList();

			im.setValue(_documentId, SpcGetDocumentAttrInfo.IV_DOCUMENT_ID);
			// BYTE, Pricing Document GUID

			try {
				((RFCDefaultClient) _session.getClient()).execute(
					functionSpcGetDocumentAttrInfo);
			} catch (ClientException e1) {
				throw new IPCException(e1);
			} 
			//			process structure exSpcgetdocumentattrinfo_esDocument
			JCO.Structure exSpcgetdocumentattrinfo_esDocument =
				ex.getStructure(SpcGetDocumentAttrInfo.ES_DOCUMENT);
			byte[] documentIdExt =
				exSpcgetdocumentattrinfo_esDocument.getByteArray(
					SpcGetDocumentAttrInfo.DOCUMENT_ID_EXT);
			documentProperties.setExternalId(new String(documentIdExt));

			String usag =
				exSpcgetdocumentattrinfo_esDocument.getString(
					SpcGetDocumentAttrInfo.USAG);
			documentProperties.setUsage(usag);
			
			String onlySpecUsage =
				exSpcgetdocumentattrinfo_esDocument.getString(
					SpcGetDocumentAttrInfo.ONLY_SPEC_USAGE);

			String application =
				exSpcgetdocumentattrinfo_esDocument.getString(
					SpcGetDocumentAttrInfo.APPLICATION);
			documentProperties.setApplication(application);
			
			String fieldVarcond =
				exSpcgetdocumentattrinfo_esDocument.getString(
					SpcGetDocumentAttrInfo.FIELD_VARCOND);
			String partialProcessing =
				exSpcgetdocumentattrinfo_esDocument.getString(
					SpcGetDocumentAttrInfo.PARTIAL_PROCESSING);
			String keepZeroPrices =
				exSpcgetdocumentattrinfo_esDocument.getString(
					SpcGetDocumentAttrInfo.KEEP_ZERO_PRICES);
				
			String groupConditionProcessing =
				exSpcgetdocumentattrinfo_esDocument.getString(
					SpcGetDocumentAttrInfo.GROUP_CONDITION_PROCESSING);
			
			byte[] bDocumentId =
				exSpcgetdocumentattrinfo_esDocument.getByteArray(
					SpcGetDocumentAttrInfo.DOCUMENT_ID);

			String procedur =
				exSpcgetdocumentattrinfo_esDocument.getString(
					SpcGetDocumentAttrInfo.PROCEDUR);
			documentProperties.setPricingProcedure(procedur);
			
			BigDecimal authorityDisplay =
				exSpcgetdocumentattrinfo_esDocument.getBigDecimal(
					SpcGetDocumentAttrInfo.AUTHORITY_DISPLAY);
			
			BigDecimal authorityEdit =
				exSpcgetdocumentattrinfo_esDocument.getBigDecimal(
					SpcGetDocumentAttrInfo.AUTHORITY_EDIT);
			
			String documentCurrencyUnit =
				exSpcgetdocumentattrinfo_esDocument.getString(
					SpcGetDocumentAttrInfo.DOCUMENT_CURRENCY_UNIT);
			documentProperties.setDocumentCurrency(documentCurrencyUnit);
			
			String localCurrencyUnit =
				exSpcgetdocumentattrinfo_esDocument.getString(
					SpcGetDocumentAttrInfo.LOCAL_CURRENCY_UNIT);
			documentProperties.setLocalCurrency(localCurrencyUnit);
			
			String businessObjectType =
				exSpcgetdocumentattrinfo_esDocument.getString(
					SpcGetDocumentAttrInfo.BUSINESS_OBJECT_TYPE);
			documentProperties.setBusinessObjectType(businessObjectType);
			
			Date expiringCheckDate =
				exSpcgetdocumentattrinfo_esDocument.getDate(
					SpcGetDocumentAttrInfo.EXPIRING_CHECK_DATE);
			
			String editMode =
				exSpcgetdocumentattrinfo_esDocument.getString(
					SpcGetDocumentAttrInfo.EDIT_MODE);
			
			String performTrace =
				exSpcgetdocumentattrinfo_esDocument.getString(
					SpcGetDocumentAttrInfo.PERFORM_TRACE);
			documentProperties.setPerformPricingAnalysis(performTrace.equals("X") ? true : false);

			//			process table exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributes
			//			TABLE
			JCO
				.Table exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributes =
				exSpcgetdocumentattrinfo_esDocument.getTable(
					SpcGetDocumentAttrInfo.ATTRIBUTES);
			for (int exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributesCounter = 0; exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributesCounter < exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributes.getNumRows(); exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributesCounter++) {
		         exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributes.setRow(exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributesCounter);
				String fieldname =
					exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributes.getString(SpcGetDocumentAttrInfo.FIELDNAME);

				//			process table exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributesSpcgetdocumentattrinfo.values
				// TABLE
				JCO.Table exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributesSpcgetdocumentattrinfo_values =
					exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributes.getTable(
						SpcGetDocumentAttrInfo.VALUES);
				for (int exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributesSpcgetdocumentattrinfo_valuesCounter =	0;
					exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributesSpcgetdocumentattrinfo_valuesCounter
						< exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributesSpcgetdocumentattrinfo_values
							.getNumRows();
					exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributesSpcgetdocumentattrinfo_valuesCounter++) {
					exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributesSpcgetdocumentattrinfo_values
						.setRow(
						exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributesSpcgetdocumentattrinfo_valuesCounter);
					String strEmptyFieldHack =
						exSpcgetdocumentattrinfo_esDocumentSpcgetdocumentattrinfo_attributesSpcgetdocumentattrinfo_values
							.getString(
							SpcGetDocumentAttrInfo.EMPTY_FIELD_HACK);
					documentProperties.setAttribute(fieldname, strEmptyFieldHack);
				}
			}
            if (location.beDebug()){
                location.debugT("return documentProperties " + documentProperties);
            }
            location.exiting();
			return documentProperties;

		}


    /* (non-Javadoc)
     * @see com.sap.spc.remote.client.object.imp.DefaultIPCDocument#initializeItems()
     */
     protected void initializeItems() throws IPCException{ 
         //50 Application Caching
         if (isCacheDirty()){
             syncWithServer();
         }else{
             //Cache is in sync with server -> do nothing...
             category.logT(Severity.INFO, location, "Server is in sync + Saved command calls: " + IFunctionGroup.SPC_GET_PRICING_DOCUMENT_INFO + ", " + IFunctionGroup.SPC_GET_DOCUMENT_INFO);
             return;
         } 
  }

    /* (non-Javadoc)
     * @see com.sap.spc.remote.client.object.imp.DefaultIPCDocument#createDocument()
     */
    protected void createDocument() throws IPCException {
        try {

            JCO.Function functionSpcCreateDocument = ((RFCDefaultClient)_session.getClient()).getFunction(IFunctionGroup.SPC_CREATE_DOCUMENT);
            JCO.ParameterList im = functionSpcCreateDocument.getImportParameterList();
            JCO.ParameterList ex = functionSpcCreateDocument.getExportParameterList();
            JCO.ParameterList tbl = functionSpcCreateDocument.getTableParameterList();
           
            JCO.Structure imSpccreatedocument_isDocument = im.getStructure(SpcCreateDocument.IS_DOCUMENT);
            String usage = _properties.getUsage();
            if (usage != null){
                imSpccreatedocument_isDocument.setValue(usage, SpcCreateDocument.USAG);      // CHAR, Usage for Condition Technique    
            }
            String application = _properties.getApplication();
            if (application != null){
                imSpccreatedocument_isDocument.setValue(application, SpcCreateDocument.APPLICATION);        // CHAR, Application
            }
            imSpccreatedocument_isDocument.setValue(_properties.isGroupConditionProcessingEnabled() ? SpcCreateDocument.XFLAG : "", SpcCreateDocument.GROUP_CONDITION_PROCESSING);      // CHAR, Activate: Group Processing in IPC

            String procedureName = _properties.getPricingProcedure();
            if (procedureName != null) {
                imSpccreatedocument_isDocument.setValue(procedureName, SpcCreateDocument.PROCEDUR);     // CHAR, Pricing Procedure
            }
            String documentCurrency = _properties.getDocumentCurrency();
            if (documentCurrency != null) {
                imSpccreatedocument_isDocument.setValue(documentCurrency, SpcCreateDocument.DOCUMENT_CURRENCY_UNIT);     // CHAR, Document Currency of Price Determination              
            }
            String localCurrency = _properties.getLocalCurrency();
            if (localCurrency != null) {
                imSpccreatedocument_isDocument.setValue(localCurrency, SpcCreateDocument.LOCAL_CURRENCY_UNIT);       // CHAR, Local Currency
            }
            
            String businessObjectType = _properties.getBusinessObjectType();
            if (businessObjectType != null) {
				imSpccreatedocument_isDocument.setValue(businessObjectType, SpcCreateDocument.BUSINESS_OBJECT_TYPE);
            }
            
            // as of 50 this flag is on document-level and not on item-level anymore
            imSpccreatedocument_isDocument.setValue(_properties.getPerformPricingAnalysis() ? SpcCreateDocument.XFLAG : "", SpcCreateDocument.PERFORM_TRACE);       // CHAR, Checkbox

            // process table imSpccreatedocument_isDocumentSpccreatedocument_attributes
            JCO.Table imSpccreatedocument_isDocumentSpccreatedocument_attributes = imSpccreatedocument_isDocument.getTable(SpcCreateDocument.ATTRIBUTES);

            // Header Attributes
            HashMap props = _properties.getHeaderAttributes();
            if (props != null) {
                String attrName = null;
                String attrValue = null;
                for (Iterator iter=props.keySet().iterator(); iter.hasNext();) {
                    attrName = (String)iter.next();
                    attrValue = (String) props.get(attrName);
                    imSpccreatedocument_isDocumentSpccreatedocument_attributes.appendRow();
                    imSpccreatedocument_isDocumentSpccreatedocument_attributes.setValue(attrName, SpcCreateDocument.FIELDNAME);     // CHAR, Field name
                    // process table imSpccreatedocument_isDocumentSpccreatedocument_attributesSpccreatedocument_values
                    JCO.Table imSpccreatedocument_isDocumentSpccreatedocument_attributesSpccreatedocument_values = imSpccreatedocument_isDocumentSpccreatedocument_attributes.getTable(SpcCreateDocument.VALUES);
                    while(true){
                        imSpccreatedocument_isDocumentSpccreatedocument_attributesSpccreatedocument_values.appendRow();
                        imSpccreatedocument_isDocumentSpccreatedocument_attributesSpccreatedocument_values.setValue(attrValue, "");     // CHAR, Value of Field in VAKEY/VADAT
                        // Value is One Row in the Table -> Break the loop
                        break;
                    }
                }
            }
            category.logT(Severity.PATH, location, "executing RFC SPC_CREATE_DOCUMENT");
            ((RFCDefaultClient)_session.getClient()).execute(functionSpcCreateDocument); // does not define ABAP Exceptions
            category.logT(Severity.PATH, location, "done with RFC SPC_CREATE_DOCUMENT");
            
            _documentId = ex.getString(SpcCreateDocument.EV_DOCUMENT_ID);
            
            // If the document id is initial, it doesn't make sense to continue (customer message 555578 2006).
            if (_documentId != null && _documentId.equals(INITIAL_DOCUMENT_ID)){
                category.logT(Severity.ERROR, location, "Pricing engine returned initial document id: " + _documentId);
                throw new IPCException("Error on engine-side: Pricing engine returned initial document id!");
            }
             initializeValues(_session, this.getId());
            if (DefaultTTEDocument.isDocumentCreationEnabled(props)){  
                _tteDocument = factory.newTTEDocument(_session, this, true);
                // if this location is in debug mode we set the TTE trace-mode to 03 (i.e. also warnings and infos are logged)
                if (location.beDebug()){
                    _tteDocument.setTraceMode(DefaultTTEDocument.TRACEMODE_03);
            	}
            }
            else{
                _tteDocument = RfcDefaultTTEDocument.C_NULL;
            }
             setCacheDirty(); //50 Application caching.
        }
        catch(ClientException e) {
            throw new IPCException(e);
        }
    }
    
    /* (non-Javadoc)
     * @see com.sap.spc.remote.client.object.IPCDocument#setHeaderAttributeBindings(java.lang.String[], java.lang.String[])
     */
    public void setHeaderAttributeBindings(String[] keys, String[] values)
        throws IPCException {
            location.entering("setHeaderAttributeBindings(String[] keys, String[] values)");
            if (location.beDebug()){
                location.debugT("Parameters:");
                location.debugT("String[] keys ");
                if (keys != null) {
                    for (int i = 0; i<keys.length; i++){
                        location.debugT("  keys["+i+"] "+keys[i]);
                    }
                }
                location.debugT("String[] values ");
                if (values != null) {
                    for (int i = 0; i<values.length; i++){
                        location.debugT("  values["+i+"] "+values[i]);
                    }
                }
            }
            Map attr = _properties.getHeaderAttributes();
            // update the Map
            for (int i=0; i<keys.length; i++) {
                attr.put(keys[i],values[i]);
            }
            // if TTE is enabled we update also the TTE document with the new data from
            // the header attributes (client- and server-side)
            if (_tteDocument != RfcDefaultTTEDocument.C_NULL){
                if (attr.size() > 0){
                    _tteDocument.changeDocument((HashMap)attr);
                }
            }
            
            JCO.Function function = getChangeDocumentFunction();
            JCO.ParameterList inputParameterList = function.getImportParameterList();
            JCO.Structure importsSpcChangeDocumentSpcchangedocument_isDocument = inputParameterList.getStructure(SpcChangeDocument.IS_DOCUMENT);
            importsSpcChangeDocumentSpcchangedocument_isDocument.setValue(_documentId, SpcChangeDocument.DOCUMENT_ID);        // BYTE, Pricing Document GUID            
            // process table importsSpcChangeDocumentSpcchangedocument_isDocumentSpcchangedocument_attributes
            JCO.Table importsSpcChangeDocumentSpcchangedocument_isDocumentSpcchangedocument_attributes = 
               importsSpcChangeDocumentSpcchangedocument_isDocument.getTable(SpcChangeDocument.ATTRIBUTES);
            for (int i=0; i<keys.length; i++) {
                importsSpcChangeDocumentSpcchangedocument_isDocumentSpcchangedocument_attributes.appendRow();
                importsSpcChangeDocumentSpcchangedocument_isDocumentSpcchangedocument_attributes.setValue(keys[i], SpcChangeDocument.FIELDNAME);       // CHAR, Field name
                // process table imSpcchangedocument_isDocumentSpcchangedocument_attributesSpcchangedocument_values
                JCO.Table imSpcchangedocument_isDocumentSpcchangedocument_attributesSpcchangedocument_values = importsSpcChangeDocumentSpcchangedocument_isDocumentSpcchangedocument_attributes.getTable(SpcChangeDocument.VALUES);
                // there is only one value per key
                imSpcchangedocument_isDocumentSpcchangedocument_attributesSpcchangedocument_values.appendRow();              
                imSpcchangedocument_isDocumentSpcchangedocument_attributesSpcchangedocument_values.setValue(values[i], "");      // CHAR, Value of Field in VAKEY/VADAT   
            }            
            _properties.setHeaderAttributes(attr);
            _changeDocument(inputParameterList, function);
            location.exiting();
    }

    /* (non-Javadoc)
     * @see com.sap.spc.remote.client.object.IPCDocument#changeDocumentCurrency(java.lang.String)
     */
    public void changeDocumentCurrency(String currencyUnit)
        throws IPCException {
            location.entering("changeDocumentCurrency(String currencyUnit)");
            if (location.beDebug()){
                location.debugT("Parameters:");
                location.debugT("String currencyUnit " + currencyUnit);
            }
            // if TTE is enabled we update also the TTE document with the new unit
            // (client- and server-side)
            if (_tteDocument != RfcDefaultTTEDocument.C_NULL){
                _tteDocument.changeDocumentCurrencyUnit(currencyUnit);
            }
            JCO.Function function = getChangeDocumentFunction();
            JCO.ParameterList inputParameterList = function.getImportParameterList();
            JCO.Structure importsSpcChangeDocumentSpcchangedocument_isDocument = inputParameterList.getStructure(SpcChangeDocument.IS_DOCUMENT);
            importsSpcChangeDocumentSpcchangedocument_isDocument.setValue(currencyUnit, SpcChangeDocument.DOCUMENT_CURRENCY_UNIT);       // CHAR, Document Currency of Price Determination
            _changeDocument(inputParameterList, function);
            location.exiting();
    }

    /* (non-Javadoc)
     * @see com.sap.spc.remote.client.object.imp.DefaultIPCDocument#_changeDocument(java.lang.String[])
     */
    protected void _changeDocument(JCO.ParameterList importsSpcChangeDocument, JCO.Function functionSpcChangeDocument) throws IPCException {
        if ((importsSpcChangeDocument != null) && (functionSpcChangeDocument != null)) {
            try {
                JCO.ParameterList exportsSpcChangeDocument = functionSpcChangeDocument.getExportParameterList();
                JCO.ParameterList tablesSpcChangeDocument = functionSpcChangeDocument.getTableParameterList();
                // process structure importsSpcChangeDocumentSpcchangedocument_isDocument
                JCO.Structure importsSpcChangeDocumentSpcchangedocument_isDocument = importsSpcChangeDocument.getStructure(SpcChangeDocument.IS_DOCUMENT);
                importsSpcChangeDocumentSpcchangedocument_isDocument.setValue(_documentId, SpcChangeDocument.DOCUMENT_ID);      // BYTE, Pricing Document GUID
                
                category.logT(Severity.PATH, location, "executing RFC SPC_CHANGE_DOCUMENT");
                ((RFCDefaultClient)_session.getClient()).execute(functionSpcChangeDocument); // does not define ABAP Exceptions
                category.logT(Severity.PATH, location, "done with RFC SPC_CHANGE_DOCUMENT");
                setCacheDirty();
            }
            catch(ClientException e) {
                throw new IPCException(e);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.sap.spc.remote.client.object.IPCDocument#removeItem(com.sap.spc.remote.client.object.IPCItem)
     */
    public synchronized void removeItem(IPCItem item) throws IPCException {
        location.entering("removeItem(IPCItem item)");
        if (location.beDebug()){
            location.debugT("Parameters:");
            location.debugT("IPCItem item " + item.getItemId());
        }
                
        _items.removeElement(item); 
        _itemsCache = null;
        // if TTE is enabled we remove the item also from the TTE document (client- and server-side)
        if (_tteDocument != RfcDefaultTTEDocument.C_NULL){
            ArrayList itemsToRemove = new ArrayList();
            if (item != null){
                itemsToRemove.add(item);
            }
            _tteDocument.removeItemsOnServer(itemsToRemove);
        }
        
        try {
            JCO.Function functionSpcRemoveItems = ((RFCDefaultClient)_session.getClient()).getFunction(IFunctionGroup.SPC_REMOVE_ITEMS);
            JCO.ParameterList importsSpcRemoveItems = functionSpcRemoveItems.getImportParameterList();
            JCO.ParameterList exportsSpcRemoveItems = functionSpcRemoveItems.getExportParameterList();
            JCO.ParameterList tablesSpcRemoveItems = functionSpcRemoveItems.getTableParameterList();
                
            // process table importsSpcRemoveItemsSpcremoveitems_itItem
            // TABLE, List of Item Numbers
            JCO.Table importsSpcRemoveItemsSpcremoveitems_itItem = importsSpcRemoveItems.getTable(SpcRemoveItems.IT_ITEM);
            while (true) {
                importsSpcRemoveItemsSpcremoveitems_itItem.appendRow();
                importsSpcRemoveItemsSpcremoveitems_itItem.setValue(item.getItemId(), "");      // BYTE, Item Number
                break;
            }
            importsSpcRemoveItems.setValue(_documentId, SpcRemoveItems.IV_DOCUMENT_ID);     // BYTE, Pricing Document GUID

            category.logT(Severity.PATH, location, "executing RFC SPC_REMOVE_ITEMS");
            ((RFCDefaultClient)_session.getClient()).execute(functionSpcRemoveItems); // does not define ABAP Exceptions
            category.logT(Severity.PATH, location, "done with RFC SPC_REMOVE_ITEMS");

            setCacheDirty();
            location.exiting();
        }
        catch(Exception e) {
            throw new IPCException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.sap.spc.remote.client.object.imp.DefaultIPCDocument#_removeItemsOnServer(com.sap.spc.remote.client.object.IPCItem[])
     */
    protected void _removeItemsOnServer(IPCItem[] items) throws IPCException {
        // if TTE is enabled we remove the items also from the TTE document (client- and server-side)
        if (_tteDocument != RfcDefaultTTEDocument.C_NULL){
            ArrayList itemsToRemove = new ArrayList();
            for (int i=0; i<items.length; i++){
                itemsToRemove.add(items[i]);
            }
            _tteDocument.removeItemsOnServer(itemsToRemove);
        }
        try {
            JCO.Function functionSpcRemoveItems = ((RFCDefaultClient)_session.getClient()).getFunction(IFunctionGroup.SPC_REMOVE_ITEMS);
            JCO.ParameterList importsSpcRemoveItems = functionSpcRemoveItems.getImportParameterList();
            JCO.ParameterList exportsSpcRemoveItems = functionSpcRemoveItems.getExportParameterList();
            JCO.ParameterList tablesSpcRemoveItems = functionSpcRemoveItems.getTableParameterList();
            
            // process table importsSpcRemoveItemsSpcremoveitems_itItem
            // TABLE, List of Item Numbers
            JCO.Table importsSpcRemoveItemsSpcremoveitems_itItem = importsSpcRemoveItems.getTable(SpcRemoveItems.IT_ITEM);
            int len = items.length;
            String value = null;
            for (int index = 0;index < len; index++) {
                value = items[index].getItemId();
                importsSpcRemoveItemsSpcremoveitems_itItem.appendRow();
                importsSpcRemoveItemsSpcremoveitems_itItem.setValue(value, "");     // BYTE, Item Number
            }
            importsSpcRemoveItems.setValue(_documentId, SpcRemoveItems.IV_DOCUMENT_ID);     // BYTE, Pricing Document GUID

            category.logT(Severity.PATH, location, "executing RFC SPC_REMOVE_ITEMS");
            ((RFCDefaultClient)_session.getClient()).execute(functionSpcRemoveItems); // does not define ABAP Exceptions
            category.logT(Severity.PATH, location, "done with RFC SPC_REMOVE_ITEMS");

            setCacheDirty();
        }
        catch(ClientException e) {
            throw new IPCException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.sap.spc.remote.client.object.IPCDocument#changeLocalCurrency(java.lang.String)
     */
    public void changeLocalCurrency(String localCurrency) throws IPCException {
        location.entering("changeLocalCurrency(String localCurrency)");
        if (location.beDebug()){
            location.debugT("Parameters:");
            location.debugT("String localCurrency " + localCurrency);
        }
        JCO.Function function = getChangeDocumentFunction();
        JCO.ParameterList inputParameterList = function.getImportParameterList();
        JCO.Structure importsSpcChangeDocumentSpcchangedocument_isDocument = inputParameterList.getStructure(SpcChangeDocument.IS_DOCUMENT);
        importsSpcChangeDocumentSpcchangedocument_isDocument.setValue(localCurrency, SpcChangeDocument.LOCAL_CURRENCY_UNIT);      // CHAR, Local Currency
        _changeDocument(inputParameterList, function);
        location.exiting();
    }

    /* (non-Javadoc)
     * @see com.sap.spc.remote.client.object.IPCDocument#changePricingProcedure(java.lang.String)
     */
    public void changePricingProcedure(String pricingProcedure)
        throws IPCException {
            location.entering("changePricingProcedure(String pricingProcedure)");
            if (location.beDebug()){
                location.debugT("Parameters:");
                location.debugT("String pricingProcedure " + pricingProcedure);
            }
            JCO.Function function = getChangeDocumentFunction();
            JCO.ParameterList inputParameterList = function.getImportParameterList();
            JCO.Structure importsSpcChangeDocumentSpcchangedocument_isDocument = inputParameterList.getStructure(SpcChangeDocument.IS_DOCUMENT);
            importsSpcChangeDocumentSpcchangedocument_isDocument.setValue(pricingProcedure, SpcChangeDocument.PROCEDUR);     // CHAR, Pricing Procedure
            _changeDocument(inputParameterList, function);
            location.exiting();
    }

    /* (non-Javadoc)
     * @see com.sap.spc.remote.client.object.IPCDocument#changeSalesOrganisation(java.lang.String)
     */
     //50 new DOCUMENT will not support salesOrganisation
    public void changeSalesOrganisation(String salesOrganisation)
        throws IPCException {
            throw new UnsupportedOperationException("Function Module SPC_CHANGE_DOCUMENT doesn't allow changing of sales organisation.");
    }

    /* (non-Javadoc)
     * @see com.sap.spc.remote.client.object.IPCDocument#changeDistributionChannel(java.lang.String)
     */
    public void changeDistributionChannel(String distributionChannel)
        throws IPCException {
            throw new UnsupportedOperationException("Function Module SPC_CHANGE_DOCUMENT doesn't allow changing of distribution channel.");            
            //50 new DOCUMENT will not support distributionChannel
    }

    /* (non-Javadoc)
     * @see com.sap.spc.remote.client.object.IPCDocument#syncCommonProperties()
     */
    public void syncCommonProperties() throws IPCException {
        location.entering("syncCommonProperties()");
        if (isCacheDirty()){
            syncWithServer();
        }else{
            //Cache is in sync with server -> do nothing...
            category.logT(Severity.INFO, location, "Server is in sync + Saved command calls: " + IFunctionGroup.SPC_GET_PRICING_DOCUMENT_INFO + ", " + IFunctionGroup.SPC_GET_DOCUMENT_INFO);
            location.exiting();
            return;
        }
        location.exiting();
    }
    /**
     * Sets Cache as dirty.
     */
    public void setCacheDirty() {
        location.entering("setCacheDirty()");
        if (!isCacheDirty()){
            _setDocFlagToDirty();
            category.log(Severity.DEBUG,location,"Setting client document cache as Dirty.");
        }
        _setDocItemsFlagToDirty(); //If doc is dirty items should also be dirty.
        location.exiting();
    }
                    
    /* (non-Javadoc)
     * @see com.sap.spc.remote.client.object.imp.DefaultIPCDocument#syncWithServer()
     */
    public synchronized void syncWithServer() throws IPCException{
        location.entering("syncWithServer()");
        if (!isCacheDirty()) {
            location.exiting();
            return;
        } 
        
        category.log(Severity.DEBUG,location,"Client document cache is dirty. Syncing client document with Server....");
        
        RFCDefaultClient client = (RFCDefaultClient)_session.getClient();
        String decSep = _properties.getDecimalSeparator();
        String groupSep = _properties.getGroupingSeparator();
        boolean stripDecimalPlaces = _properties.isStripDecimalPlaces(); 
        try {
            JCO.Function functionSpcGetDocumentInfo = ((RFCDefaultClient) _session.getClient()).getFunction(IFunctionGroup.SPC_GET_DOCUMENT_INFO);
            JCO.ParameterList im2 = functionSpcGetDocumentInfo.getImportParameterList();
            JCO.ParameterList ex2 = functionSpcGetDocumentInfo.getExportParameterList();
            JCO.ParameterList tbl2 = functionSpcGetDocumentInfo.getTableParameterList();
    
            // process table imSpcgetdocumentinfo_itDocumentId
            // optional, TABLE, List of GUID Keys for Pricing Documents (KNUMVs)
            JCO.Table imSpcgetdocumentinfo_itDocumentId = im2.getTable(SpcGetDocumentInfo.IT_DOCUMENT_ID);
            imSpcgetdocumentinfo_itDocumentId.appendRow();
            imSpcgetdocumentinfo_itDocumentId.setValue(_documentId, ""); // BYTE, Pricing Document GUID
    
            category.logT(Severity.PATH, location, "executing RFC SPC_GET_DOCUMENT_INFO");
            ((RFCDefaultClient) _session.getClient()).execute(functionSpcGetDocumentInfo);
            category.logT(Severity.PATH, location, "done with RFC SPC_GET_DOCUMENT_INFO");
           
            JCO.Table exSpcgetdocumentinfo_etDocumentInfo = ex2.getTable(SpcGetDocumentInfo.ET_DOCUMENT_INFO);
            int rowCount = exSpcgetdocumentinfo_etDocumentInfo.getNumRows();
            if (rowCount < 1){
                // no info for the documentId returned -> check messages 
                category.logT(Severity.ERROR, location, "SPC_GET_DOCUMENT_INFO doesn't return any information for documentId " + _documentId);
                JCO.Structure esProfile = ex2.getStructure(SpcGetDocumentInfo.ES_PROFILE);
                JCO.Table esProfile_messages = esProfile.getTable(SpcGetDocumentInfo.MESSAGES);
                int rowCountMessages = esProfile_messages.getNumRows();
                if (rowCountMessages > 0){
                    for (int i=0; i<rowCountMessages; i++){
                        esProfile_messages.setRow(i);
                        String message = esProfile_messages.getString(SpcGetDocumentInfo.MESSAGE);
                        String type = esProfile_messages.getString(SpcGetDocumentInfo.TYPE);
                        String errorMsg = "Message (type: " + type + ") returned by SPC_GET_DOCUMENT_INFO: " + message;
                        category.logT(Severity.ERROR, location, errorMsg);
                    }
                }
                else {
                    String errorMsg = "SPC_GET_DOCUMENT_INFO returned no messages.";
                    category.logT(Severity.ERROR, location, errorMsg);

                }
                _items = new Vector();
                _itemsCache = null;
                location.exiting();
                return;                
            }

            // get number of items 
            // because we are calling GetDocumentInfo with one explicit documentId only one document
            // should be returned -> we set row to 0!                        
            exSpcgetdocumentinfo_etDocumentInfo.setRow(0);
            JCO.Table exSpcgetdocumentinfo_etDocumentInfoSpcgetdocumentinfo_itemInfo = exSpcgetdocumentinfo_etDocumentInfo.getTable(SpcGetDocumentInfo.ITEM_INFO);           
            int numOfItems = exSpcgetdocumentinfo_etDocumentInfoSpcgetdocumentinfo_itemInfo.getNumRows();
            if (numOfItems == 0) {
                _items = new Vector();
                _itemsCache = null;
				//Now cache is not dirty.
				_setDocItemsFlagToSync();
				_setDocFlagToSync();
                location.exiting();                
                return;
            }
            
            // initialize the arrays with the number of items 
            String[] itemIds = new String[numOfItems];
            String[] netValues = new String[numOfItems];
            String[] netValuesWoFreight = new String[numOfItems];
            String[] taxValues = new String[numOfItems];
            String[] grossValues = new String[numOfItems];
            String[] totalGrossValues = new String[numOfItems];           
            String[] totalNetValues = new String[numOfItems];            
            String[] totalTaxValues = new String[numOfItems];            
            String[] productGuids = new String[numOfItems];            
            String[] productIds = new String[numOfItems];            
            String[] productDescriptions = new String[numOfItems];            
            String[] baseQuantityValues = new String[numOfItems];            
            String[] baseQuantityUnits = new String[numOfItems];            
            String[] salesQuantityValues = new String[numOfItems];
            String[] salesQuantityUnits = new String[numOfItems];
            String[] productVariants = new String[numOfItems];            
            String[] completes = new String[numOfItems];            
            String[] consistents = new String[numOfItems];            
            String[] configChangeds = new String[numOfItems];           
            String[] parentIds = new String[numOfItems];            
            String[] configurables = new String[numOfItems];            
            String[] parentItemIds = new String[numOfItems];            
            Map[] dynamicReturns = new HashMap[numOfItems];            
            
            // walk through items of document
            for (int i = 0; i < exSpcgetdocumentinfo_etDocumentInfoSpcgetdocumentinfo_itemInfo.getNumRows(); i++) {
                exSpcgetdocumentinfo_etDocumentInfoSpcgetdocumentinfo_itemInfo.setRow(i);
                itemIds[i] = exSpcgetdocumentinfo_etDocumentInfoSpcgetdocumentinfo_itemInfo.getString(SpcGetDocumentInfo.ITEM_ID);
                parentIds[i] = exSpcgetdocumentinfo_etDocumentInfoSpcgetdocumentinfo_itemInfo.getString(SpcGetDocumentInfo.HIGH_LEVEL_ITEM_ID); 
                configurables[i] = exSpcgetdocumentinfo_etDocumentInfoSpcgetdocumentinfo_itemInfo.getString(SpcGetDocumentInfo.CONFIGURABLE);
                // the fm returns the productGuid as "PRODUCT_ID"!
                productGuids[i] = exSpcgetdocumentinfo_etDocumentInfoSpcgetdocumentinfo_itemInfo.getString(SpcGetDocumentInfo.PRODUCT_ID);
                // filling PRODUCT_ERP into productIds. In the CRM-scenario this won't work. 
                // It will be overwritten later in this method with valid data (calling method convertProductGuidsToProductIds()) 
                productIds[i] = exSpcgetdocumentinfo_etDocumentInfoSpcgetdocumentinfo_itemInfo.getString(SpcGetDocumentInfo.PRODUCT_ERP);
                productDescriptions[i] = exSpcgetdocumentinfo_etDocumentInfoSpcgetdocumentinfo_itemInfo.getString(SpcGetDocumentInfo.PRODUCT_DESCRIPTION);
                // different from other values this qty is not returned as BigDecimal! 
                String itemQtyInternal = exSpcgetdocumentinfo_etDocumentInfoSpcgetdocumentinfo_itemInfo.getString(SpcGetDocumentInfo.ITEM_QUANTITY);
                String itemQtyUnitInternal = exSpcgetdocumentinfo_etDocumentInfoSpcgetdocumentinfo_itemInfo.getString(SpcGetDocumentInfo.ITEM_QUANTITY_UNIT);
                if (itemQtyInternal == null){
                    category.logT(Severity.WARNING, location, "Item quantity for item [" + itemIds[i] + "] is null. Setting to 0.");
                    itemQtyInternal = "0";
                }
                BigDecimal itemQtyInternalBD = new BigDecimal(itemQtyInternal);
                String itemQty = PricingConverter.convertValueInternalToExternal(itemQtyInternalBD, itemQtyUnitInternal, (RfcDefaultIPCSession)_session, getLanguage(), decSep, groupSep, stripDecimalPlaces);
                String itemQtyUnit = PricingConverter.convertUOMInternalToExternal(itemQtyUnitInternal, (RfcDefaultIPCSession)_session, getLanguage());
                baseQuantityValues[i] = itemQtyInternal;
                baseQuantityUnits[i] = itemQtyUnitInternal;
                salesQuantityValues[i] = itemQty;
                salesQuantityUnits[i] = itemQtyUnit;
                productVariants[i] = exSpcgetdocumentinfo_etDocumentInfoSpcgetdocumentinfo_itemInfo.getString(SpcGetDocumentInfo.PRODUCT_VARIANT);
                completes[i] =  exSpcgetdocumentinfo_etDocumentInfoSpcgetdocumentinfo_itemInfo.getString(SpcGetDocumentInfo.CONFIG_COMPLETE);
                consistents[i] = exSpcgetdocumentinfo_etDocumentInfoSpcgetdocumentinfo_itemInfo.getString(SpcGetDocumentInfo.CONFIG_CONSISTENT);
                configChangeds[i] = exSpcgetdocumentinfo_etDocumentInfoSpcgetdocumentinfo_itemInfo.getString(SpcGetDocumentInfo.CONFIG_CHANGED);
            }
            
            // retrieve the missing data from GetPricingDocumentInfo    
            JCO.Function functionSpcGetPricingDocumentInfo = ((RFCDefaultClient) _session.getClient()).getFunction(IFunctionGroup.SPC_GET_PRICING_DOCUMENT_INFO);
            JCO.ParameterList im = functionSpcGetPricingDocumentInfo.getImportParameterList();
            JCO.ParameterList ex = functionSpcGetPricingDocumentInfo.getExportParameterList();
            JCO.ParameterList tbl = functionSpcGetPricingDocumentInfo.getTableParameterList();

            im.setValue(_documentId, SpcGetPricingDocumentInfo.IV_DOCUMENT_ID);

            category.logT(Severity.PATH, location, "executing RFC SPC_GET_PRICING_DOCUMENT_INFO");
            ((RFCDefaultClient) _session.getClient()).execute(functionSpcGetPricingDocumentInfo);
            category.logT(Severity.PATH, location, "done with RFC SPC_GET_PRICING_DOCUMENT_INFO");
            
            // execute the TTE Analysis only if debug mode is set for this location and TTE is enabled 
            if ((location.beDebug()) && (_tteDocument != RfcDefaultTTEDocument.C_NULL)){
                String[] trace = _tteDocument.getTTEAnalysisData(); 
            }
            
            JCO.Structure exSpcgetpricingdocumentinfo_esHeadRet = ex.getStructure(SpcGetPricingDocumentInfo.ES_HEAD_RET);
            _currencyUnit = exSpcgetpricingdocumentinfo_esHeadRet.getString(SpcGetPricingDocumentInfo.DOCUMENT_CURRENCY_UNIT);
            BigDecimal _netValueBD = exSpcgetpricingdocumentinfo_esHeadRet.getBigDecimal(SpcGetPricingDocumentInfo.NET_VALUE);
            BigDecimal _grossValueBD = exSpcgetpricingdocumentinfo_esHeadRet.getBigDecimal(SpcGetPricingDocumentInfo.GROSS_VALUE);
            BigDecimal _taxValueBD = exSpcgetpricingdocumentinfo_esHeadRet.getBigDecimal(SpcGetPricingDocumentInfo.TAX_VALUE);
            BigDecimal _freightBD = exSpcgetpricingdocumentinfo_esHeadRet.getBigDecimal(SpcGetPricingDocumentInfo.FREIGHT);
            if (_isValueFormatting){
                _netValue = PricingConverter.convertCurrencyValueInternalToExternal(_netValueBD, _currencyUnit, (RfcDefaultIPCSession)_session, getLanguage(), decSep, groupSep, stripDecimalPlaces);
                _grossValue = PricingConverter.convertCurrencyValueInternalToExternal(_grossValueBD, _currencyUnit, (RfcDefaultIPCSession)_session, getLanguage(), decSep, groupSep, stripDecimalPlaces);
                _taxValue = PricingConverter.convertCurrencyValueInternalToExternal(_taxValueBD, _currencyUnit, (RfcDefaultIPCSession)_session, getLanguage(), decSep, groupSep, stripDecimalPlaces);
                _freight = PricingConverter.convertCurrencyValueInternalToExternal(_freightBD, _currencyUnit, (RfcDefaultIPCSession)_session, getLanguage(), decSep, groupSep, stripDecimalPlaces);
            }
            else {
                _netValue = _netValueBD.toString();
                _grossValue = _grossValueBD.toString(); 
                _taxValue = _taxValueBD.toString();
                _freight = _freightBD.toString();
            }
            
            // process table exSpcgetpricingdocumentinfo_etItemRet
            // TABLE, Return Values for Pricing at Item Level
            HashMap pricingDocumentInfoData = new HashMap();
            JCO.Table exSpcgetpricingdocumentinfo_etItemRet = ex.getTable(SpcGetPricingDocumentInfo.ET_ITEM_RET);
            for (int i = 0; i < exSpcgetpricingdocumentinfo_etItemRet.getNumRows(); i++) {
                exSpcgetpricingdocumentinfo_etItemRet.setRow(i);
                HashMap data = new HashMap();
                String itemId = exSpcgetpricingdocumentinfo_etItemRet.getString(SpcGetPricingDocumentInfo.ITEM_ID);
                data.put(SpcGetPricingDocumentInfo.ITEM_ID, itemId);
                BigDecimal netValueBD = exSpcgetpricingdocumentinfo_etItemRet.getBigDecimal(SpcGetPricingDocumentInfo.NET_VALUE);
                BigDecimal netValueWoFreightBD = exSpcgetpricingdocumentinfo_etItemRet.getBigDecimal(SpcGetPricingDocumentInfo.NET_VALUE_FREIGHTLESS);
                BigDecimal taxValueBD = exSpcgetpricingdocumentinfo_etItemRet.getBigDecimal(SpcGetPricingDocumentInfo.TAX_VALUE);
                BigDecimal grossValueBD = exSpcgetpricingdocumentinfo_etItemRet.getBigDecimal(SpcGetPricingDocumentInfo.GROSS_VALUE);
                Map dynamicReturn = readDynamicReturn(exSpcgetpricingdocumentinfo_etItemRet.getTable(SpcGetPricingDocumentInfo.DYNAMIC_RETURN));
                data.put(SpcGetPricingDocumentInfo.DYNAMIC_RETURN, dynamicReturn);

                if (_isValueFormatting){
                    // according to M. Goronzy we set netValues for totalNetValues, etc. (if they are needed, APD has to enhance function module)
                    data.put(SpcGetPricingDocumentInfo.NET_VALUE, PricingConverter.convertCurrencyValueInternalToExternal(netValueBD, _currencyUnit, (RfcDefaultIPCSession)_session, getLanguage(), decSep, groupSep, stripDecimalPlaces)); 
                    data.put(SpcGetPricingDocumentInfo.NET_VALUE_FREIGHTLESS, PricingConverter.convertCurrencyValueInternalToExternal(netValueWoFreightBD, _currencyUnit, (RfcDefaultIPCSession)_session, getLanguage(), decSep, groupSep, stripDecimalPlaces));
                    data.put(SpcGetPricingDocumentInfo.GROSS_VALUE, PricingConverter.convertCurrencyValueInternalToExternal(grossValueBD, _currencyUnit, (RfcDefaultIPCSession)_session, getLanguage(), decSep, groupSep, stripDecimalPlaces));
                    data.put(SpcGetPricingDocumentInfo.TAX_VALUE, PricingConverter.convertCurrencyValueInternalToExternal(taxValueBD, _currencyUnit, (RfcDefaultIPCSession)_session, getLanguage(), decSep, groupSep, stripDecimalPlaces));
                    data.put(TOTAL_NET_VALUE, PricingConverter.convertCurrencyValueInternalToExternal(netValueBD, _currencyUnit, (RfcDefaultIPCSession)_session, getLanguage(), decSep, groupSep, stripDecimalPlaces));
                    data.put(TOTAL_GROSS_VALUE, PricingConverter.convertCurrencyValueInternalToExternal(grossValueBD, _currencyUnit, (RfcDefaultIPCSession)_session, getLanguage(), decSep, groupSep, stripDecimalPlaces));
                    data.put(TOTAL_TAX_VALUE, PricingConverter.convertCurrencyValueInternalToExternal(taxValueBD, _currencyUnit, (RfcDefaultIPCSession)_session, getLanguage(), decSep, groupSep, stripDecimalPlaces));
                    
                }
                else {
                    // according to M. Goronzy we set netValues for totalNetValues, etc. (if they are needed, APD has to enhance function module)
                    data.put(SpcGetPricingDocumentInfo.NET_VALUE, netValueBD.toString()); 
                    data.put(SpcGetPricingDocumentInfo.NET_VALUE_FREIGHTLESS, netValueWoFreightBD.toString());
                    data.put(SpcGetPricingDocumentInfo.GROSS_VALUE, grossValueBD.toString());
                    data.put(SpcGetPricingDocumentInfo.TAX_VALUE, taxValueBD.toString());
                    data.put(TOTAL_NET_VALUE, netValueBD.toString());
                    data.put(TOTAL_GROSS_VALUE, grossValueBD.toString());
                    data.put(TOTAL_TAX_VALUE, taxValueBD.toString());
                }
                pricingDocumentInfoData.put(itemId, data);
            }
            
            // merge the data: walkthrough itemIds array try to get data out of pricingDocumentInfo only fill if data != null
            // complete the arrays with data from GetPricingDocumentInfo
            for (int i = 0; i < itemIds.length; i++) {
                HashMap data = (HashMap) pricingDocumentInfoData.get(itemIds[i]);
                if (data == null) {
                    continue;
                }
                netValues[i] = (String) data.get(SpcGetPricingDocumentInfo.NET_VALUE);
                netValuesWoFreight[i] = (String) data.get(SpcGetPricingDocumentInfo.NET_VALUE_FREIGHTLESS);
                grossValues[i] = (String) data.get(SpcGetPricingDocumentInfo.GROSS_VALUE);
                taxValues[i] = (String) data.get(SpcGetPricingDocumentInfo.TAX_VALUE);
                totalNetValues[i] = (String) data.get(TOTAL_NET_VALUE);
                totalGrossValues[i] = (String) data.get(TOTAL_GROSS_VALUE);
                totalTaxValues[i] = (String) data.get(TOTAL_TAX_VALUE);
                dynamicReturns[i] = (Map)data.get(SpcGetPricingDocumentInfo.DYNAMIC_RETURN);
            }            

            // in the CRM case we have to convert the productGuids to the productIds because in this scenario the AP-FMs don't
            // return valid information for productIds.
            if (_application.equals(DefaultIPCDocument.CRM)){
                productIds = convertProductGuidsToProductIds(productGuids);
            }                

            // determine Map: itemId -> List of itemId, where the key is an item id, and the value the ids of all subitems
            Map subitemTable = DefaultIPCDocument.getChildrenMap(itemIds, parentIds);
            Map idToItem = new HashMap();
            // step 0: keep a list of items known on client side before the update (needed to delete items that were deleted on the server side)
            Vector oldItems = (Vector)_items.clone();
            // step 1: create all items
            for (int i=0; i<itemIds.length; i++) {
                DefaultIPCItem item = _getItemRecursive(itemIds[i]); // what happens if item is deleted
                // if item was found already on the client side remove it from the list of oldItems
                if (item != null){
                    oldItems.removeElement(item);
                }
                if (item == null) {
                    item = factory.newIPCItem(client, this, itemIds[i], parentIds[i]);
                    if (item.getParent() == null) {
                        addItem(item);
                    }
                }
                // in the ERP case set productGuid to productId (productId contains already the productIdERP see above)
                if (_application.equals(DefaultIPCDocument.ERP)){
                    productGuids[i] = productIds[i];
                }
                
                Object cacheKey = null;
                item.initCommonProperties(cacheKey, netValues[i], taxValues[i], grossValues[i], totalNetValues[i],
                                          totalTaxValues[i], totalGrossValues[i], _currencyUnit/*currencyUnit*/,
                                          productGuids[i], productIds[i], productDescriptions[i],
                                          baseQuantityValues[i], baseQuantityUnits[i],
                                          salesQuantityValues[i], salesQuantityUnits[i],
                                          configurables[i], productVariants[i],
                                          completes[i], consistents[i], configChangeds[i], (List)subitemTable.get(itemIds[i]));
                item.initOtherProperties(netValuesWoFreight[i], _currencyUnit);
                item.setDynamicReturn(dynamicReturns[i]);
                item.initConfiguration();
                idToItem.put(itemIds[i], item);
            }

            // step 2: remove the remaining items of oldItems from _items -> these have been obviously deleted on the server
            Iterator oldItemsIterator = oldItems.iterator(); 
            while (oldItemsIterator.hasNext()){
                DefaultIPCItem oldItem = (DefaultIPCItem)oldItemsIterator.next();
                _items.removeElement(oldItem);
            }
            // step 2.5: if TTE is enabled we remove the items also from the TTE document (client- and server-side)
            if (_tteDocument != RfcDefaultTTEDocument.C_NULL){
                _tteDocument.removeItemsOnServer(oldItems);
            }
            // step 3: connect every item to its children
            for (int i=0; i<itemIds.length; i++) {
                DefaultIPCItem item = (DefaultIPCItem)idToItem.get(itemIds[i]);
                item.connectToChildren(idToItem);
            }

        }
        catch(ClientException e) {
            throw new IPCException(e);
        }
        
        customerExitSyncWithServer();
        
        //Now cache is not dirty.
        _setDocItemsFlagToSync();
        _setDocFlagToSync();
        location.exiting();
    }

    private Map readDynamicReturn(JCO.Table dynamicReturnTable) {
		HashMap data = new HashMap();
		for (int i = 0; i < dynamicReturnTable.getNumRows(); i++) {
			dynamicReturnTable.setRow(i);
			String key = dynamicReturnTable.getString(SpcGetPricingDocumentInfo.ATTR_NAME);
			String sequence = dynamicReturnTable.getString(SpcGetPricingDocumentInfo.SEQ_NUMBER);
			String attrValue = dynamicReturnTable.getString(SpcGetPricingDocumentInfo.ATTR_VALUE);
			DynamicReturn dynamicReturn = new DefaultDynamicReturn(key, sequence, attrValue);
			data.put(key, dynamicReturn);
		}
		return data;
    }
    
    protected void initializeValues(DefaultIPCSession session, String documentId) {
        RFCDefaultClient client = (RFCDefaultClient)session.getClient();
        try {
            JCO.Function functionSpcGetPricingDocumentInfo = ((RFCDefaultClient) _session.getClient()).getFunction(IFunctionGroup.SPC_GET_PRICING_DOCUMENT_INFO);
            JCO.ParameterList im = functionSpcGetPricingDocumentInfo.getImportParameterList();
            JCO.ParameterList ex = functionSpcGetPricingDocumentInfo.getExportParameterList();
            JCO.ParameterList tbl = functionSpcGetPricingDocumentInfo.getTableParameterList();

            im.setValue(documentId, SpcGetPricingDocumentInfo.IV_DOCUMENT_ID);

            category.logT(Severity.PATH, location, "executing RFC SPC_GET_PRICING_DOCUMENT_INFO");
            ((RFCDefaultClient) _session.getClient()).execute(functionSpcGetPricingDocumentInfo);
            category.logT(Severity.PATH, location, "done with RFC SPC_GET_PRICING_DOCUMENT_INFO");
            
            JCO.Structure exSpcgetpricingdocumentinfo_esHeadRet = ex.getStructure(SpcGetPricingDocumentInfo.ES_HEAD_RET);
            String documentCurrency = exSpcgetpricingdocumentinfo_esHeadRet.getString(SpcGetPricingDocumentInfo.DOCUMENT_CURRENCY_UNIT);
            if (_properties.getDocumentCurrency() == null && documentCurrency != null) {
                 _properties.setDocumentCurrency(documentCurrency);
            }
        }
        catch(ClientException e) {
            throw new IPCException(e);
        }
//        The following data is not returned by the fm anymore:
//            SALES_ORGANISATION
//            DISTRIBUTION_CHANNEL
//            DIVISION
//            DEPARTURE_COUNTRY
//            COUNTRY
//            PRICING_PROCEDURE_NAME
//            DEPARTURE_REGION
//            HEADER_ATTRIBUTES
//            EXTERNAL_ID
//            EDIT_MODE
    }
    
    public String[] getTTEAnalysisData() throws IPCException {
        String[] xmldocuments=new String[2];        
        if (_tteDocument != RfcDefaultTTEDocument.C_NULL){
            xmldocuments = _tteDocument.getTTEAnalysisDataXml();
    	}
        return xmldocuments;
    }
            
    /**
     * Consolidated location were the function object of SPC_CHANGE_DOCUMENT is created. 
     * This is needed to get always the same importParameters object in different methods. 
     * @return JCO.Function object of SPC_CHANGE_DOCUMENT 
     */            
    protected JCO.Function getChangeDocumentFunction(){
        JCO.Function functionSpcChangeDocument = null;
        try {
            functionSpcChangeDocument = ((RFCDefaultClient)_session.getClient()).getFunction(IFunctionGroup.SPC_CHANGE_DOCUMENT);
        }
        catch (ClientException c){
            throw new IPCException(c);
        }
        return functionSpcChangeDocument;
    }
	/* (non-Javadoc)
	 * @see com.sap.spc.remote.client.object.imp.DefaultIPCDocument#customerExitSyncWithServer()
	 */
	public void customerExitSyncWithServer() throws IPCException {

	}

    public String[] convertProductGuidsToProductIds(String[] productGuids){
        location.entering("convertProductGuidsToProductIds(String[] productGuids)");
        if (location.beDebug()){
            location.debugT("Parameters:");
            location.debugT("String[] productGuids ");
            if (productGuids != null) {
                for (int i = 0; i<productGuids.length; i++){
                    location.debugT("  productGuids["+i+"] "+productGuids[i]);
                }
            }
        }
        if (productGuids == null) {  
            category.logT(Severity.ERROR, location, "convertProductGuidsToProductIds: array of productGuids is null!");
            if (location.beDebug()){
                location.debugT("return null");
            }
            location.exiting();
            return null;
        } 
        if (productGuids.length == 0){
            category.logT(Severity.INFO, location, "convertProductGuidsToProductIds: array of productGuids has no entries.");
            if (location.beDebug()){
                location.debugT("return new String[0]");
            }
            location.exiting();
            return new String[0]; 
        }
      
        // generate the cache if not existing
        if (guidsToIds == null){
            guidsToIds = new HashMap();
            // add the GUIDs and the productId for "SPC dummy product" to avoid that the FM tries to convert it (it would fail because it is a dummy guid)
            guidsToIds.put(SPC_DUMMY_PRODUCT_GUID, SPC_DUMMY_PRODUCT_ID);
            guidsToIds.put(SPC_DUMMY_PRODUCT_GUID2, SPC_DUMMY_PRODUCT_ID);    
        }
        
        String[] productIds = new String[productGuids.length];
        ArrayList guidsToBeConverted = new ArrayList();
        
        // check in the cache whether the guids have already been converted
        for (int i=0; i<productGuids.length; i++){
            String id = (String)guidsToIds.get(productGuids[i]);
            if (id == null){
                // guid not found -> has to be converted
                guidsToBeConverted.add(productGuids[i]);
            } 
            else {
                productIds[i] = id;
            }
        }
        
        // call FM only if there are guids that have to be converted
        if (guidsToBeConverted.size() > 0){
            category.logT(Severity.INFO, location, "convertProductGuidsToProductIds: " +  guidsToBeConverted.size() + " guids have to be converted.");
            JCO.Function functionComProductReadViaRfc;
            try {
                functionComProductReadViaRfc = ((RFCDefaultClient) _session.getClient()).getFunction(IFunctionGroup.COM_PRODUCT_READ_VIA_RFC); 
                JCO.ParameterList tbl = functionComProductReadViaRfc.getTableParameterList();
    
                JCO.Table itProductGuid = tbl.getTable(ComProductReadViaRfc.IT_PRODUCT_GUID);
                Iterator guidsIt = guidsToBeConverted.iterator();
                while (guidsIt.hasNext()){
                    itProductGuid.appendRow();
                    String guid = (String) guidsIt.next();
                    itProductGuid.setValue(guid, ComProductReadViaRfc.PRODUCT_GUID);
                }

                category.logT(Severity.PATH, location, "executing " + IFunctionGroup.COM_PRODUCT_READ_VIA_RFC);
                ((RFCDefaultClient) _session.getClient()).execute(functionComProductReadViaRfc); 
                category.logT(Severity.PATH, location, "done with " + IFunctionGroup.COM_PRODUCT_READ_VIA_RFC);
                
                JCO.Table etProduct = tbl.getTable(ComProductReadViaRfc.ET_PRODUCT);
                for (int i=0; i<etProduct.getNumRows(); i++){
                    etProduct.setRow(i);
                    String currentId = etProduct.getString(ComProductReadViaRfc.PRODUCT_ID);
                    String currentGuid = etProduct.getString(ComProductReadViaRfc.PRODUCT_GUID);
                    category.logT(Severity.DEBUG, location, "convertProductGuidsToProductIds: The GUID " +  currentGuid + " has been converted to productId " + currentId +".");
                    // fill the cache with the missing ids
                    guidsToIds.put(currentGuid, currentId);
                }
                
                // loop over the array of guids and fill resulting array of ids
                for (int i=0; i<productGuids.length; i++){
                    // retrieve id from cache
                    String id = (String)guidsToIds.get(productGuids[i]);
                    if (id == null){
                        category.logT(Severity.ERROR, location, "Error during productGuid conversion: "+ IFunctionGroup.COM_PRODUCT_READ_VIA_RFC + " does not return a productId for productGuid \"" + productGuids[i] + "\".");
                        throw new IPCException("Error during productGuid conversion: "+ IFunctionGroup.COM_PRODUCT_READ_VIA_RFC + " does not return a productId for productGuid \"" + productGuids[i] + "\".");
                    } 
                    else {
                        productIds[i] = id;
                    }        
                }
            } catch (ClientException e) {
                throw new IPCException(e);
            }            
        }
        else {
            category.logT(Severity.INFO, location, "convertProductGuidsToProductIds: No call to " + IFunctionGroup.COM_PRODUCT_READ_VIA_RFC + " necessary. All guids/ids are known on client-side.");
        }
        if (location.beDebug()) {
            if (productIds != null) {
                location.debugT("return productIds");
                for (int i = 0; i<productIds.length; i++){
                    location.debugT("  productIds["+i+"] "+productIds[i]);
                }
            }
        }
        return productIds;
    }
    public HashMap getProductInfoForProductGuid(String productGuid){
        location.entering("getProductInfoForProductGuid(String productGuid)");
        if (location.beDebug()){
            location.debugT("Parameters:");
            location.debugT("String productGuid " + productGuid);
        }
        HashMap productInfo = new HashMap();
        if (productGuid == null) {  
            category.logT(Severity.ERROR, location, "getProductInfoForProductGuid: productGuid is null!");
            if (location.beDebug()){
                location.debugT("return null");
            }
            location.exiting();
            return null;
        } 
        if (productGuid.equals("")){
            category.logT(Severity.INFO, location, "getProductInfoForProductGuid: productGuid is empty.");
            if (location.beDebug()){
                if (productInfo!=null){
                    location.debugT("return productInfo " + productInfo.toString());
                }
                else location.debugT("return productInfo null");
            }
            location.exiting();
            return productInfo; 
        }
        JCO.Function functionComProductReadViaRfc;
        try {
            functionComProductReadViaRfc = ((RFCDefaultClient) _session.getClient()).getFunction(IFunctionGroup.COM_PRODUCT_READ_VIA_RFC); 
            JCO.ParameterList tbl = functionComProductReadViaRfc.getTableParameterList();

            JCO.Table itProductGuid = tbl.getTable(ComProductReadViaRfc.IT_PRODUCT_GUID);
            itProductGuid.appendRow();
            itProductGuid.setValue(productGuid, ComProductReadViaRfc.PRODUCT_GUID);

            category.logT(Severity.PATH, location, "executing " + IFunctionGroup.COM_PRODUCT_READ_VIA_RFC);
            ((RFCDefaultClient) _session.getClient()).execute(functionComProductReadViaRfc); 
            category.logT(Severity.PATH, location, "done with " + IFunctionGroup.COM_PRODUCT_READ_VIA_RFC);
            
            JCO.Table etProduct = tbl.getTable(ComProductReadViaRfc.ET_PRODUCT);
            for (int i=0; i<etProduct.getNumRows(); i++){
                etProduct.setRow(i);
                productInfo.put(ComProductReadViaRfc.PRODUCT_ID, etProduct.getString(ComProductReadViaRfc.PRODUCT_ID));
                productInfo.put(ComProductReadViaRfc.PRODUCT_GUID, etProduct.getString(ComProductReadViaRfc.PRODUCT_GUID));
                productInfo.put(ComProductReadViaRfc.LOGSYS, etProduct.getString(ComProductReadViaRfc.LOGSYS));
                productInfo.put(ComProductReadViaRfc.PRODUCT_TYPE, etProduct.getString(ComProductReadViaRfc.PRODUCT_TYPE));
                break; // only one guid has been passed so only one would come back
            }
        } catch (ClientException e) {
            throw new IPCException(e);
        }  
        if (location.beDebug()){
            if (productInfo!=null){
                location.debugT("return productInfo " + productInfo.toString());
            }
            else location.debugT("return productInfo null");
        }
        location.exiting();          
        return productInfo;
    }

    public HashMap getDocAndItemPropertiesFromServer() {
        location.entering("getDocAndItemPropertiesFromServer()");
        HashMap docAndItemProps = new HashMap();
        IPCDocumentProperties documentProperties = factory.newIPCDocumentProperties();

        JCO.Function functionSpcGetDocumentAttrInfo = null;
        try {
            functionSpcGetDocumentAttrInfo = ((RFCDefaultClient) _session.getClient()).getFunction(IFunctionGroup.SPC_GET_DOCUMENT_ATTR_INFO);
        } catch (ClientException e) {
            throw new IPCException(e);
        }
        JCO.ParameterList im = functionSpcGetDocumentAttrInfo.getImportParameterList();
        JCO.ParameterList ex = functionSpcGetDocumentAttrInfo.getExportParameterList();
        JCO.ParameterList tbl = functionSpcGetDocumentAttrInfo.getTableParameterList();

        im.setValue(_documentId, SpcGetDocumentAttrInfo.IV_DOCUMENT_ID);

        // don't sync - use only the current items available
        IPCItem[] ipcItems =  _getItems(false);
        JCO.Table imSpcgetdocumentattrinfo_itItemId = im.getTable(SpcGetDocumentAttrInfo.IT_ITEM_ID);
        int i = 0;
        while (i < ipcItems.length) {
            String ivItemId = ipcItems[i].getItemId();
            imSpcgetdocumentattrinfo_itItemId.appendRow();
            imSpcgetdocumentattrinfo_itItemId.setValue(ivItemId, "");
            i++;
        }
            
        try {
            category.logT(Severity.PATH, location, "executing RFC SPC_GET_DOCUMENT_ATTR_INFO");
            ((RFCDefaultClient) _session.getClient()).execute(functionSpcGetDocumentAttrInfo);
            category.logT(Severity.PATH, location, "done with RFC SPC_GET_DOCUMENT_ATTR_INFO");            
        } catch (ClientException e1) {
            throw new IPCException(e1);
        } 

        // start of doc-props handling
        JCO.Structure exSpcgetdocumentattrinfo_esDocument = ex.getStructure(SpcGetDocumentAttrInfo.ES_DOCUMENT);
        byte[] documentIdExt = exSpcgetdocumentattrinfo_esDocument.getByteArray(SpcGetDocumentAttrInfo.DOCUMENT_ID_EXT);
        documentProperties.setExternalId(new String(documentIdExt));

        String usag = exSpcgetdocumentattrinfo_esDocument.getString(SpcGetDocumentAttrInfo.USAG);
        documentProperties.setUsage(usag);
            
        String onlySpecUsage = exSpcgetdocumentattrinfo_esDocument.getString(SpcGetDocumentAttrInfo.ONLY_SPEC_USAGE);

        String application = exSpcgetdocumentattrinfo_esDocument.getString(SpcGetDocumentAttrInfo.APPLICATION);
        documentProperties.setApplication(application);
            
        String fieldVarcond = exSpcgetdocumentattrinfo_esDocument.getString(SpcGetDocumentAttrInfo.FIELD_VARCOND);
        String partialProcessing = exSpcgetdocumentattrinfo_esDocument.getString(SpcGetDocumentAttrInfo.PARTIAL_PROCESSING);
        String keepZeroPrices = exSpcgetdocumentattrinfo_esDocument.getString(SpcGetDocumentAttrInfo.KEEP_ZERO_PRICES);
                
        String groupConditionProcessing = exSpcgetdocumentattrinfo_esDocument.getString(SpcGetDocumentAttrInfo.GROUP_CONDITION_PROCESSING);
            
        byte[] bDocumentId = exSpcgetdocumentattrinfo_esDocument.getByteArray(SpcGetDocumentAttrInfo.DOCUMENT_ID);

        String procedur = exSpcgetdocumentattrinfo_esDocument.getString(SpcGetDocumentAttrInfo.PROCEDUR);
        documentProperties.setPricingProcedure(procedur);
            
        BigDecimal authorityDisplay = exSpcgetdocumentattrinfo_esDocument.getBigDecimal(SpcGetDocumentAttrInfo.AUTHORITY_DISPLAY);
            
        BigDecimal authorityEdit = exSpcgetdocumentattrinfo_esDocument.getBigDecimal(SpcGetDocumentAttrInfo.AUTHORITY_EDIT);
            
        String documentCurrencyUnit = exSpcgetdocumentattrinfo_esDocument.getString(SpcGetDocumentAttrInfo.DOCUMENT_CURRENCY_UNIT);
        documentProperties.setDocumentCurrency(documentCurrencyUnit);
            
        String localCurrencyUnit = exSpcgetdocumentattrinfo_esDocument.getString(SpcGetDocumentAttrInfo.LOCAL_CURRENCY_UNIT);
        documentProperties.setLocalCurrency(localCurrencyUnit);
            
        String businessObjectType = exSpcgetdocumentattrinfo_esDocument.getString(SpcGetDocumentAttrInfo.BUSINESS_OBJECT_TYPE);
        documentProperties.setBusinessObjectType(businessObjectType);
            
        Date expiringCheckDate = exSpcgetdocumentattrinfo_esDocument.getDate(SpcGetDocumentAttrInfo.EXPIRING_CHECK_DATE);
            
        String editMode = exSpcgetdocumentattrinfo_esDocument.getString(SpcGetDocumentAttrInfo.EDIT_MODE);
            
        String performTrace = exSpcgetdocumentattrinfo_esDocument.getString(SpcGetDocumentAttrInfo.PERFORM_TRACE);
        documentProperties.setPerformPricingAnalysis(performTrace.equals("X") ? true : false);

        JCO.Table spcgetdocumentattrinfo_attributes = exSpcgetdocumentattrinfo_esDocument.getTable(SpcGetDocumentAttrInfo.ATTRIBUTES);
        for (int attributesCounter = 0; attributesCounter < spcgetdocumentattrinfo_attributes.getNumRows(); attributesCounter++) {
            spcgetdocumentattrinfo_attributes.setRow(attributesCounter);
            String fieldname = spcgetdocumentattrinfo_attributes.getString(SpcGetDocumentAttrInfo.FIELDNAME);

            JCO.Table spcgetdocumentattrinfo_values = spcgetdocumentattrinfo_attributes.getTable(SpcGetDocumentAttrInfo.VALUES);
            for (int valuesCounter = 0; valuesCounter < spcgetdocumentattrinfo_values.getNumRows(); valuesCounter++) {
                spcgetdocumentattrinfo_values.setRow(valuesCounter);
                String strEmptyFieldHack = spcgetdocumentattrinfo_values.getString(SpcGetDocumentAttrInfo.EMPTY_FIELD_HACK);
                documentProperties.setAttribute(fieldname, strEmptyFieldHack);
            }
        }
        // put it into the HashMap
        docAndItemProps.put(DefaultIPCDocumentProperties.PROPERTIES, documentProperties);
        // end of doc props-handling
            
        // start of item props-handling
        IPCItemProperties[] itemProperties = new IPCItemProperties[ipcItems.length];

        JCO.Table exSpcgetdocumentattrinfo_etItem = ex.getTable(SpcGetDocumentAttrInfo.ET_ITEM);
        for (int etItemCounter=0; etItemCounter<exSpcgetdocumentattrinfo_etItem.getNumRows(); etItemCounter++) {
            IPCItemProperties currentItemProperties = factory.newIPCItemProperties();

            itemProperties[etItemCounter] = currentItemProperties;
            exSpcgetdocumentattrinfo_etItem.setRow(etItemCounter);
            String itemId = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.ITEM_ID);
            
            BigDecimal itemIdExt = exSpcgetdocumentattrinfo_etItem.getBigDecimal(SpcGetDocumentAttrInfo.ITEM_ID_EXT);
            
            String highLevelItemId = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.HIGH_LEVEL_ITEM_ID);
            
            String productId = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.PRODUCT_ID);
            currentItemProperties.setProductId(productId);
            
            String productErp = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.PRODUCT_ERP);
            
            String decoupleSubitems = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.DECOUPLE_SUBITEMS);
            currentItemProperties.setDecoupleSubitems(decoupleSubitems.equals("X")? true : false);
            
            Integer multiplicity = (Integer)exSpcgetdocumentattrinfo_etItem.getValue(SpcGetDocumentAttrInfo.MULTIPLICITY);
            
            String pricingRelevant = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.PRICING_RELEVANT);
            currentItemProperties.setPricingRelevant(new Boolean(pricingRelevant.equals("X")? true : false));
            
            String returnFlag = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.RETURN_FLAG);

            String statistical = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.STATISTICAL);
            currentItemProperties.setStatistical(statistical.equals("X")? true : false);

            String unchangeable = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.UNCHANGEABLE);

            String fixGroup = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.FIX_GROUP);

            String exchRateDate = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.EXCH_RATE_DATE);
            currentItemProperties.setExchangeRateDate(exchRateDate);

            String exchRateType = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.EXCH_RATE_TYPE);
            currentItemProperties.setExchangeRateType(exchRateType);

            String exchRate = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.EXCH_RATE);

            String itemQuantity = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.ITEM_QUANTITY);
            String itemQuantityUnit = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.ITEM_QUANTITY_UNIT);
            DimensionalValue baseQuantity = new com.sap.spc.remote.client.object.imp.DimensionalValue();
            baseQuantity.setUnitAndValue(itemQuantity, itemQuantityUnit);
            currentItemProperties.setBaseQuantity(baseQuantity);

            Integer numerator = (Integer)exSpcgetdocumentattrinfo_etItem.getValue(SpcGetDocumentAttrInfo.NUMERATOR);
            Integer denominator = (Integer)exSpcgetdocumentattrinfo_etItem.getValue(SpcGetDocumentAttrInfo.DENOMINATOR);
            Integer exponent = (Integer)exSpcgetdocumentattrinfo_etItem.getValue(SpcGetDocumentAttrInfo.EXPONENT);
            String grossWeight = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.GROSS_WEIGHT);
            String netWeight = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.NET_WEIGHT);
            String weightUnit = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.WEIGHT_UNIT);
            String volum = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.VOLUM);
            String volumeUnit = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.VOLUME_UNIT);
            String points = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.POINTS);
            String pointsUnit = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.POINTS_UNIT);
            Integer daysPerMonth = (Integer)exSpcgetdocumentattrinfo_etItem.getValue(SpcGetDocumentAttrInfo.DAYS_PER_MONTH);
            Integer daysPerYear = (Integer)exSpcgetdocumentattrinfo_etItem.getValue(SpcGetDocumentAttrInfo.DAYS_PER_YEAR);
    
            JCO.Table holidays = exSpcgetdocumentattrinfo_etItem.getTable(SpcGetDocumentAttrInfo.HOLIDAYS);
            for (int holidaysCounter=0; holidaysCounter<holidays.getNumRows(); holidaysCounter++) {
                holidays.setRow(holidaysCounter);
                Date dateEmptyFieldHack = holidays.getDate(SpcGetDocumentAttrInfo.EMPTY_FIELD_HACK);
            }
            Date dateFromB = exSpcgetdocumentattrinfo_etItem.getDate(SpcGetDocumentAttrInfo.DATE_FROM_B);
            Date dateToB = exSpcgetdocumentattrinfo_etItem.getDate(SpcGetDocumentAttrInfo.DATE_TO_B);
            Double daysB = (Double)exSpcgetdocumentattrinfo_etItem.getValue(SpcGetDocumentAttrInfo.DAYS_B);
            Double weeksB = (Double)exSpcgetdocumentattrinfo_etItem.getValue(SpcGetDocumentAttrInfo.WEEKS_B);
            Double monthsB = (Double)exSpcgetdocumentattrinfo_etItem.getValue(SpcGetDocumentAttrInfo.MONTHS_B);
            Double yearsB = (Double)exSpcgetdocumentattrinfo_etItem.getValue(SpcGetDocumentAttrInfo.YEARS_B);
            Date dateFromS = exSpcgetdocumentattrinfo_etItem.getDate(SpcGetDocumentAttrInfo.DATE_FROM_S);
            Date dateToS = exSpcgetdocumentattrinfo_etItem.getDate(SpcGetDocumentAttrInfo.DATE_TO_S);
            Double daysS = (Double)exSpcgetdocumentattrinfo_etItem.getValue(SpcGetDocumentAttrInfo.DAYS_S);
            Double weeksS = (Double)exSpcgetdocumentattrinfo_etItem.getValue(SpcGetDocumentAttrInfo.WEEKS_S);
            Double monthsS = (Double)exSpcgetdocumentattrinfo_etItem.getValue(SpcGetDocumentAttrInfo.MONTHS_S);
            Double yearsS = (Double)exSpcgetdocumentattrinfo_etItem.getValue(SpcGetDocumentAttrInfo.YEARS_S);
            String timestampS = exSpcgetdocumentattrinfo_etItem.getString(SpcGetDocumentAttrInfo.TIMESTAMP_S);
    
            JCO.Structure exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configData = exSpcgetdocumentattrinfo_etItem.getStructure(SpcGetDocumentAttrInfo.CONFIG_DATA);
            String kbName = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configData.getString(SpcGetDocumentAttrInfo.KB_NAME);
            String kbDate = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configData.getString(SpcGetDocumentAttrInfo.KB_DATE);
            String kbLogsys = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configData.getString(SpcGetDocumentAttrInfo.KB_LOGSYS);
            String kbVersion = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configData.getString(SpcGetDocumentAttrInfo.KB_VERSION);
            String kbProfile = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configData.getString(SpcGetDocumentAttrInfo.KB_PROFILE);
            Integer kbId = (Integer)exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configData.getValue(SpcGetDocumentAttrInfo.KB_ID);
    
            JCO.Structure exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfig = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configData.getStructure(SpcGetDocumentAttrInfo.EXT_CONFIG);
    
            JCO.Structure exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfigSpcgetdocumentattrinfo_header = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfig.getStructure(SpcGetDocumentAttrInfo.HEADER);
            String rootId = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfigSpcgetdocumentattrinfo_header.getString(SpcGetDocumentAttrInfo.ROOT_ID);
            String sce = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfigSpcgetdocumentattrinfo_header.getString(SpcGetDocumentAttrInfo.SCE);
            String name = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfigSpcgetdocumentattrinfo_header.getString(SpcGetDocumentAttrInfo.NAME);
            String version = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfigSpcgetdocumentattrinfo_header.getString(SpcGetDocumentAttrInfo.VERSION);
            String logsys = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfigSpcgetdocumentattrinfo_header.getString(SpcGetDocumentAttrInfo.LOGSYS);
            String profile = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfigSpcgetdocumentattrinfo_header.getString(SpcGetDocumentAttrInfo.PROFILE);
            String language = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfigSpcgetdocumentattrinfo_header.getString(SpcGetDocumentAttrInfo.LANGUAGE);
            String complete = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfigSpcgetdocumentattrinfo_header.getString(SpcGetDocumentAttrInfo.COMPLETE);
            String consistent = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfigSpcgetdocumentattrinfo_header.getString(SpcGetDocumentAttrInfo.CONSISTENT);
            String info = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfigSpcgetdocumentattrinfo_header.getString(SpcGetDocumentAttrInfo.INFO);
            Integer build = (Integer)exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfigSpcgetdocumentattrinfo_header.getValue(SpcGetDocumentAttrInfo.BUILD);
    
            JCO.Table extConfigSpcgetdocumentattrinfo_partOf = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfig.getTable(SpcGetDocumentAttrInfo.PART_OF);
            for (int partOfCounter=0; partOfCounter<extConfigSpcgetdocumentattrinfo_partOf.getNumRows(); partOfCounter++) {
                extConfigSpcgetdocumentattrinfo_partOf.setRow(partOfCounter);
                String instId = extConfigSpcgetdocumentattrinfo_partOf.getString(SpcGetDocumentAttrInfo.INST_ID);
                String parentId = extConfigSpcgetdocumentattrinfo_partOf.getString(SpcGetDocumentAttrInfo.PARENT_ID);
                String partOfNo = extConfigSpcgetdocumentattrinfo_partOf.getString(SpcGetDocumentAttrInfo.PART_OF_NO);
                String objType = extConfigSpcgetdocumentattrinfo_partOf.getString(SpcGetDocumentAttrInfo.OBJ_TYPE);
                String classType = extConfigSpcgetdocumentattrinfo_partOf.getString(SpcGetDocumentAttrInfo.CLASS_TYPE);
                String objKey = extConfigSpcgetdocumentattrinfo_partOf.getString(SpcGetDocumentAttrInfo.OBJ_KEY);
                String author = extConfigSpcgetdocumentattrinfo_partOf.getString(SpcGetDocumentAttrInfo.AUTHOR);
                String salesRelevant = extConfigSpcgetdocumentattrinfo_partOf.getString(SpcGetDocumentAttrInfo.SALES_RELEVANT);
            }

            JCO.Table spcgetdocumentattrinfo_instances = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfig.getTable(SpcGetDocumentAttrInfo.INSTANCES);
            for (int instancesCounter=0; instancesCounter<spcgetdocumentattrinfo_instances.getNumRows(); instancesCounter++) {
                spcgetdocumentattrinfo_instances.setRow(instancesCounter);
                String instId = spcgetdocumentattrinfo_instances.getString(SpcGetDocumentAttrInfo.INST_ID);
                String objType = spcgetdocumentattrinfo_instances.getString(SpcGetDocumentAttrInfo.OBJ_TYPE);
                String classType = spcgetdocumentattrinfo_instances.getString(SpcGetDocumentAttrInfo.CLASS_TYPE);
                String objKey = spcgetdocumentattrinfo_instances.getString(SpcGetDocumentAttrInfo.OBJ_KEY);
                String objTxt = spcgetdocumentattrinfo_instances.getString(SpcGetDocumentAttrInfo.OBJ_TXT);
                String quantity = spcgetdocumentattrinfo_instances.getString(SpcGetDocumentAttrInfo.QUANTITY);
                String quantityUnit = spcgetdocumentattrinfo_instances.getString(SpcGetDocumentAttrInfo.QUANTITY_UNIT);
                String author = spcgetdocumentattrinfo_instances.getString(SpcGetDocumentAttrInfo.AUTHOR);
                String instComplete = spcgetdocumentattrinfo_instances.getString(SpcGetDocumentAttrInfo.COMPLETE);
                String instConsistent = spcgetdocumentattrinfo_instances.getString(SpcGetDocumentAttrInfo.CONSISTENT);
            }

            JCO.Table spcgetdocumentattrinfo_values = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfig.getTable(SpcGetDocumentAttrInfo.VALUES);
            for (int valuesCounter=0; valuesCounter<spcgetdocumentattrinfo_values.getNumRows(); valuesCounter++) {
                 spcgetdocumentattrinfo_values.setRow(valuesCounter);
                 String valuesInstId = spcgetdocumentattrinfo_values.getString(SpcGetDocumentAttrInfo.INST_ID);
                 String charc = spcgetdocumentattrinfo_values.getString(SpcGetDocumentAttrInfo.CHARC);
                 String charcTxt = spcgetdocumentattrinfo_values.getString(SpcGetDocumentAttrInfo.CHARC_TXT);
                 String value = spcgetdocumentattrinfo_values.getString(SpcGetDocumentAttrInfo.VALUE);
                 String valueUnit = spcgetdocumentattrinfo_values.getString(SpcGetDocumentAttrInfo.VALUE_UNIT);
                 String valueTxt = spcgetdocumentattrinfo_values.getString(SpcGetDocumentAttrInfo.VALUE_TXT);
                 String author = spcgetdocumentattrinfo_values.getString(SpcGetDocumentAttrInfo.AUTHOR);
             }
    
            JCO.Table variantConditions = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configDataSpcgetdocumentattrinfo_extConfig.getTable(SpcGetDocumentAttrInfo.VARIANT_CONDITIONS);
            for (int variantConditionsCounter=0; variantConditionsCounter<variantConditions.getNumRows(); variantConditionsCounter++) {
                variantConditions.setRow(variantConditionsCounter);
                String varcondInstId = variantConditions.getString(SpcGetDocumentAttrInfo.INST_ID);
                String vkey = variantConditions.getString(SpcGetDocumentAttrInfo.VKEY);
                String factor = variantConditions.getString(SpcGetDocumentAttrInfo.FACTOR);
            }
            String configProductId = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configData.getString(SpcGetDocumentAttrInfo.PRODUCT_ID);
            String configProductType = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configData.getString(SpcGetDocumentAttrInfo.PRODUCT_TYPE);
            String configProductLogsys = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configData.getString(SpcGetDocumentAttrInfo.PRODUCT_LOGSYS);

            JCO.Table spcgetdocumentattrinfo_context = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_configData.getTable(SpcGetDocumentAttrInfo.CONTEXT);
            HashMap itemContext = new HashMap(spcgetdocumentattrinfo_context.getNumRows());
            for (int contextCounter=0; contextCounter<spcgetdocumentattrinfo_context.getNumRows(); contextCounter++) {
                spcgetdocumentattrinfo_context.setRow(contextCounter);
                String contextName = spcgetdocumentattrinfo_context.getString(SpcGetDocumentAttrInfo.NAME);
                String contextValue = spcgetdocumentattrinfo_context.getString(SpcGetDocumentAttrInfo.VALUE);
                itemContext.put(contextName, contextValue);
            }
            currentItemProperties.setContext(itemContext);
    
            JCO.Table etItemSpcgetdocumentattrinfo_attributes = exSpcgetdocumentattrinfo_etItem.getTable(SpcGetDocumentAttrInfo.ATTRIBUTES);
            HashMap itemAttributes = new HashMap(etItemSpcgetdocumentattrinfo_attributes.getNumRows());
            for (int etItemSpcgetdocumentattrinfo_attributesCounter=0; etItemSpcgetdocumentattrinfo_attributesCounter<etItemSpcgetdocumentattrinfo_attributes.getNumRows(); etItemSpcgetdocumentattrinfo_attributesCounter++) {
                etItemSpcgetdocumentattrinfo_attributes.setRow(etItemSpcgetdocumentattrinfo_attributesCounter);
                String attrName = etItemSpcgetdocumentattrinfo_attributes.getString(SpcGetDocumentAttrInfo.FIELDNAME);

                JCO.Table exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_attributesSpcgetdocumentattrinfo_values = etItemSpcgetdocumentattrinfo_attributes.getTable(SpcGetDocumentAttrInfo.VALUES);
                for (int etItemSpcgetdocumentattrinfo_valuesCounter=0; etItemSpcgetdocumentattrinfo_valuesCounter<exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_attributesSpcgetdocumentattrinfo_values.getNumRows(); etItemSpcgetdocumentattrinfo_valuesCounter++) {
                    exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_attributesSpcgetdocumentattrinfo_values.setRow(etItemSpcgetdocumentattrinfo_valuesCounter);
                    String attributeValue = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_attributesSpcgetdocumentattrinfo_values.getString(SpcGetDocumentAttrInfo.EMPTY_FIELD_HACK);
                    itemAttributes.put(attrName, attributeValue);
                }
            }
            currentItemProperties.setItemAttributes(itemAttributes);
    
            JCO.Table etItemSpcgetdocumentattrinfo_timestamps = exSpcgetdocumentattrinfo_etItem.getTable(SpcGetDocumentAttrInfo.TIMESTAMPS);
            HashMap timestamps = new HashMap(etItemSpcgetdocumentattrinfo_timestamps.getNumRows());
            for (int etItemSpcgetdocumentattrinfo_timestampsCounter=0; etItemSpcgetdocumentattrinfo_timestampsCounter<etItemSpcgetdocumentattrinfo_timestamps.getNumRows(); etItemSpcgetdocumentattrinfo_timestampsCounter++) {
                etItemSpcgetdocumentattrinfo_timestamps.setRow(etItemSpcgetdocumentattrinfo_timestampsCounter);
                String timestampName = etItemSpcgetdocumentattrinfo_timestamps.getString(SpcGetDocumentAttrInfo.FIELDNAME);
                String timestampValue = etItemSpcgetdocumentattrinfo_timestamps.getString(SpcGetDocumentAttrInfo.TIMESTAMP);
                timestamps.put(timestampName, timestampValue);
                if (timestampName != null && timestampName.equals(SpcCreateItems.PRICE_DATE)) {
                   //the pricing date is now a timestamp. however the properties still have a member date
                   currentItemProperties.setDate(timestampValue);
                }
            }
            currentItemProperties.setConditionTimestamps(timestamps);
    
            JCO.Table exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasource = exSpcgetdocumentattrinfo_etItem.getTable(SpcGetDocumentAttrInfo.EXT_DATASOURCE);
            for (int exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasourceCounter=0; exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasourceCounter<exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasource.getNumRows(); exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasourceCounter++) {
                exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasource.setRow(exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasourceCounter);
                String condType = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasource.getString(SpcGetDocumentAttrInfo.COND_TYPE);
                String calcType = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasource.getString(SpcGetDocumentAttrInfo.CALC_TYPE);
                String condRate = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasource.getString(SpcGetDocumentAttrInfo.COND_RATE);
                String condCurrency = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasource.getString(SpcGetDocumentAttrInfo.COND_CURRENCY);
                String condUnitValue = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasource.getString(SpcGetDocumentAttrInfo.COND_UNIT_VALUE);
                String condUnit = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasource.getString(SpcGetDocumentAttrInfo.COND_UNIT);
                String condBase = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasource.getString(SpcGetDocumentAttrInfo.COND_BASE);
                String condBaseUnit = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasource.getString(SpcGetDocumentAttrInfo.COND_BASE_UNIT);
                String xcondBase = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasource.getString(SpcGetDocumentAttrInfo.XCOND_BASE);
                String removeExtCond = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_extDatasource.getString(SpcGetDocumentAttrInfo.REMOVE_EXT_COND);
            }
    
            JCO.Table exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_variantCondition = exSpcgetdocumentattrinfo_etItem.getTable(SpcGetDocumentAttrInfo.VARIANT_CONDITION);
            for (int exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_variantConditionCounter=0; exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_variantConditionCounter<exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_variantCondition.getNumRows(); exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_variantConditionCounter++) {
                exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_variantCondition.setRow(exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_variantConditionCounter);
                String key = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_variantCondition.getString(SpcGetDocumentAttrInfo.KEY);
                Double doubleFactor = (Double)exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_variantCondition.getValue(SpcGetDocumentAttrInfo.FACTOR);
                String description = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_variantCondition.getString(SpcGetDocumentAttrInfo.DESCRIPTION);
            }
    
            JCO.Table exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopy = exSpcgetdocumentattrinfo_etItem.getTable(SpcGetDocumentAttrInfo.ITEM_COPY);
            for (int exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopyCounter=0; exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopyCounter<exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopy.getNumRows(); exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopyCounter++) {
                exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopy.setRow(exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopyCounter);
                String importConditions = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopy.getString(SpcGetDocumentAttrInfo.IMPORT_CONDITIONS);
                String sourceDocumentId = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopy.getString(SpcGetDocumentAttrInfo.SOURCE_DOCUMENT_ID);
                String sourceItemId = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopy.getString(SpcGetDocumentAttrInfo.SOURCE_ITEM_ID);
                String pricingType = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopy.getString(SpcGetDocumentAttrInfo.PRICING_TYPE);
                String copyType = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopy.getString(SpcGetDocumentAttrInfo.COPY_TYPE);
        
                JCO.Table exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopy.getTable(SpcGetDocumentAttrInfo.CONDITIONS);
                for (int exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditionsCounter=0; exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditionsCounter<exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getNumRows(); exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditionsCounter++) {
                    exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.setRow(exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditionsCounter);
                    String copyDocumentId = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.DOCUMENT_ID);
                    itemId = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.ITEM_ID);
                    BigDecimal stepNumber = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getBigDecimal(SpcGetDocumentAttrInfo.STEP_NUMBER);
                    BigDecimal condCounter = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getBigDecimal(SpcGetDocumentAttrInfo.COND_COUNTER);
                    String application2 = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.APPLICATION);
                    String usage = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.USAGE);
                    String condType = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.COND_TYPE);
                    String condTypeDescr = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.COND_TYPE_DESCR);
                    String purpose = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.PURPOSE);
                    String datasource = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.DATASOURCE);
                    String condRate = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.COND_RATE);
                    String condCurrency = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.COND_CURRENCY);
                    String condUnitValue = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.COND_UNIT_VALUE);
                    String condUnit = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.COND_UNIT);
                    String condValue = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.COND_VALUE);
                    exchRate = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.EXCH_RATE);
                    numerator = (Integer)exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getValue(SpcGetDocumentAttrInfo.NUMERATOR);
                    denominator = (Integer)exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getValue(SpcGetDocumentAttrInfo.DENOMINATOR);
                    exponent = (Integer)exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getValue(SpcGetDocumentAttrInfo.EXPONENT);
                    String manuallyChanged = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.MANUALLY_CHANGED);
                    statistical = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.STATISTICAL);
                    String calcType = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.CALC_TYPE);
                    String pricingDate = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.PRICING_DATE);
                    String condBase = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.COND_BASE);
                    String condCategory = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.COND_CATEGORY);
                    String scaleType = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.SCALE_TYPE);
                    String accrualFlag = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.ACCRUAL_FLAG);
                    String invoiceList = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.INVOICE_LIST);
                    String condOrigin = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.COND_ORIGIN);
                    String groupCondition = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.GROUP_CONDITION);
                    String condUpdate = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.COND_UPDATE);
                    BigDecimal accessCounter = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getBigDecimal(SpcGetDocumentAttrInfo.ACCESS_COUNTER);
                    String condRecordId = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.COND_RECORD_ID);
                    String accountKey1 = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.ACCOUNT_KEY_1);
                    String accountKey2 = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.ACCOUNT_KEY_2);
                    String roundingDiff = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.ROUNDING_DIFF);
                    String condControl = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.COND_CONTROL);
                    String condInactive = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.COND_INACTIVE);
                    String condClass = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.COND_CLASS);
                    BigDecimal headerCounter = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getBigDecimal(SpcGetDocumentAttrInfo.HEADER_COUNTER);
                    Double varcondFactor = (Double)exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getValue(SpcGetDocumentAttrInfo.VARCOND_FACTOR);
                    String scaleBaseType = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.SCALE_BASE_TYPE);
                    String scaleBaseValue = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.SCALE_BASE_VALUE);
                    String scaleUnit = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.SCALE_UNIT);
                    String scaleCurrency = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.SCALE_CURRENCY);
                    String interCompany = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.INTER_COMPANY);
                    String varcondFlag = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.VARCOND_FLAG);
                    String varcondKey = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.VARCOND_KEY);
                    String varcondDescr = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.VARCOND_DESCR);
                    String salesTaxCode = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.SALES_TAX_CODE);
                    String withholdingTaxCode = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.WITHHOLDING_TAX_CODE);
                    String structureCondition = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.STRUCTURE_CONDITION);
                    Double periodFactor = (Double)exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getValue(SpcGetDocumentAttrInfo.PERIOD_FACTOR);
                    String condTableName = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.COND_TABLE_NAME);
                    String manualEntryFlag = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.MANUAL_ENTRY_FLAG);
                    String changeOfRate = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.CHANGE_OF_RATE);
                    String changeOfUnit = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.CHANGE_OF_UNIT);
                    String changeOfValue = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.CHANGE_OF_VALUE);
                    String changeOfCalcType = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.CHANGE_OF_CALC_TYPE);
                    String changeOfConvFactors = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.CHANGE_OF_CONV_FACTORS);
                    String altCondCurrency = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.ALT_COND_CURRENCY);
                    String altCondBase = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.ALT_COND_BASE);
                    String altCondValue = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopySpcgetdocumentattrinfo_conditions.getString(SpcGetDocumentAttrInfo.ALT_COND_VALUE);
                }
                String sourceQuantity = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopy.getString(SpcGetDocumentAttrInfo.SOURCE_QUANTITY);
                String sourceQuantityUnit = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopy.getString(SpcGetDocumentAttrInfo.SOURCE_QUANTITY_UNIT);
                String netValueOrig = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopy.getString(SpcGetDocumentAttrInfo.NET_VALUE_ORIG);
                String netValueNew = exSpcgetdocumentattrinfo_etItemSpcgetdocumentattrinfo_itemCopy.getString(SpcGetDocumentAttrInfo.NET_VALUE_NEW);
            }
        }
        // put it into the HashMap            
        docAndItemProps.put(DefaultIPCItemProperties.PROPERTIES, itemProperties);
        // end of item props-handling
        location.exiting();
        return docAndItemProps;
    }
}
