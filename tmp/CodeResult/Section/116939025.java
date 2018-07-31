
package org.openbravo.erpWindows.Section;

import org.openbravo.erpCommon.utility.*;
import org.openbravo.erpCommon.reference.*;
import org.openbravo.erpCommon.ad_actionButton.*;
import org.openbravo.data.FieldProvider;
import org.openbravo.utils.FormatUtilities;
import org.openbravo.erpCommon.info.LocationSearchData;
import org.openbravo.base.secureApp.HttpSecureAppServlet;
import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.erpCommon.businessUtility.WindowTabs;
import org.openbravo.erpCommon.businessUtility.WindowTabsData;
import org.openbravo.utils.Replace;
import org.openbravo.xmlEngine.XmlDocument;
import org.openbravo.data.UtilSql;
import java.util.Vector;
import java.util.StringTokenizer;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

// imports para transacciones
import org.openbravo.database.ConnectionProvider;
import java.sql.Connection;

import org.openbravo.erpCommon.utility.DateTimeData;

public class Section extends HttpSecureAppServlet {
  protected static final String windowId = "800029";
  protected static final String tabId = "800084";
  protected static final String tableLevel = "3";
  protected static final String defaultTabView = "RELATION";
  protected static final double SUBTABS_COL_SIZE = 15;
  protected TableSQLData tableSQL = null;

  public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
    VariablesSecureApp vars = new VariablesSecureApp(request);

    if (!Utility.hasAccess(this, vars, tableLevel, Utility.getContext(this, vars, "#AD_Client_ID", windowId), Utility.getContext(this, vars, "#AD_Org_ID", windowId), windowId, tabId)) {
      bdError(response, "AccessTableNoView", vars.getLanguage());
      return;
    }

    try {
      tableSQL = new TableSQLData(vars, this, tabId, Utility.getContext(this, vars, "#User_Org", windowId), Utility.getContext(this, vars, "#User_Client", windowId));
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    String strOrderBy = vars.getSessionValue(tabId + "|orderby");
    if (!strOrderBy.equals("")) {
      vars.setSessionValue(tabId + "|newOrder", "1");
    }

    if (vars.commandIn("DEFAULT")) {

      String strMA_Section_ID = vars.getGlobalVariable("inpmaSectionId", windowId + "|MA_Section_ID", "");
      

      String strView = vars.getSessionValue(tabId + "|Section.view");
      if (strView.equals("")) {
        strView = defaultTabView;

        if (strView.equals("EDIT")) {
          if (strMA_Section_ID.equals("")) strMA_Section_ID = firstElement(vars);
          if (strMA_Section_ID.equals("")) strView = "RELATION";
        }
      }
      if (strView.equals("EDIT")) 

        printPageEdit(response, request, vars, false, strMA_Section_ID);

      else printPageDataSheet(response, vars, strMA_Section_ID);
    } else if (vars.commandIn("DIRECT")) {
      String strMA_Section_ID = vars.getStringParameter("inpDirectKey");
      if (strMA_Section_ID.equals("")) strMA_Section_ID = vars.getRequiredGlobalVariable("inpmaSectionId", windowId + "|MA_Section_ID");
      else vars.setSessionValue(windowId + "|MA_Section_ID", strMA_Section_ID);
      
      vars.setSessionValue(tabId + "|Section.view", "EDIT");

      printPageEdit(response, request, vars, false, strMA_Section_ID);

    } else if (vars.commandIn("TAB")) {


      String strView = vars.getSessionValue(tabId + "|Section.view");
      String strMA_Section_ID = "";
      if (strView.equals("")) {
        strView = defaultTabView;
        if (strView.equals("EDIT")) {
          strMA_Section_ID = firstElement(vars);
          if (strMA_Section_ID.equals("")) strView = "RELATION";
        }
      }
      if (strView.equals("EDIT")) {

        if (strMA_Section_ID.equals("")) strMA_Section_ID = firstElement(vars);
        printPageEdit(response, request, vars, false, strMA_Section_ID);

      } else printPageDataSheet(response, vars, "");
    } else if (vars.commandIn("SEARCH")) {
      String strParamName = vars.getRequestGlobalVariable("inpParamName", tabId + "|paramName");

      //vars.setSessionValue(tabId + "|paramSessionDate", Utility.getContext(this, vars, "#Date", windowId));
      //vars.setSessionValue(tabId + "|paramSessionDate", Utility.getTransactionalDate(this, vars, windowId));
      
      
      vars.removeSessionValue(windowId + "|MA_Section_ID");
      String strMA_Section_ID="";

      String strView = vars.getSessionValue(tabId + "|Section.view");
      if (strView.equals("")) strView=defaultTabView;

      if (strView.equals("EDIT")) {
        strMA_Section_ID = firstElement(vars);
        if (strMA_Section_ID.equals("")) strView = "RELATION";
      }

      if (strView.equals("EDIT")) 

        printPageEdit(response, request, vars, false, strMA_Section_ID);

      else printPageDataSheet(response, vars, strMA_Section_ID);
    } else if (vars.commandIn("RELATION")) {
      

      String strMA_Section_ID = vars.getGlobalVariable("inpmaSectionId", windowId + "|MA_Section_ID", "");
      vars.setSessionValue(tabId + "|Section.view", "RELATION");
      printPageDataSheet(response, vars, strMA_Section_ID);
    } else if (vars.commandIn("NEW")) {


      printPageEdit(response, request, vars, true, "");

    } else if (vars.commandIn("EDIT")) {

      String strMA_Section_ID = vars.getGlobalVariable("inpmaSectionId", windowId + "|MA_Section_ID", "");
      vars.setSessionValue(tabId + "|Section.view", "EDIT");

      setHistoryCommand(request, "EDIT");
      printPageEdit(response, request, vars, false, strMA_Section_ID);

    } else if (vars.commandIn("NEXT")) {

      String strMA_Section_ID = vars.getRequiredStringParameter("inpmaSectionId");
      
      String strNext = nextElement(vars, strMA_Section_ID);

      printPageEdit(response, request, vars, false, strNext);
    } else if (vars.commandIn("PREVIOUS")) {

      String strMA_Section_ID = vars.getRequiredStringParameter("inpmaSectionId");
      
      String strPrevious = previousElement(vars, strMA_Section_ID);

      printPageEdit(response, request, vars, false, strPrevious);
    } else if (vars.commandIn("FIRST_RELATION")) {

      vars.setSessionValue(tabId + "|Section.initRecordNumber", "0");
      response.sendRedirect(strDireccion + request.getServletPath() + "?Command=RELATION");
    } else if (vars.commandIn("PREVIOUS_RELATION")) {

      String strInitRecord = vars.getSessionValue(tabId + "|Section.initRecordNumber");
      String strRecordRange = Utility.getContext(this, vars, "#RecordRange", windowId);
      int intRecordRange = strRecordRange.equals("")?0:Integer.parseInt(strRecordRange);
      if (strInitRecord.equals("") || strInitRecord.equals("0")) {
        vars.setSessionValue(tabId + "|Section.initRecordNumber", "0");
      } else {
        int initRecord = (strInitRecord.equals("")?0:Integer.parseInt(strInitRecord));
        initRecord -= intRecordRange;
        strInitRecord = ((initRecord<0)?"0":Integer.toString(initRecord));
        vars.setSessionValue(tabId + "|Section.initRecordNumber", strInitRecord);
      }
      vars.removeSessionValue(windowId + "|MA_Section_ID");

      response.sendRedirect(strDireccion + request.getServletPath() + "?Command=RELATION");
    } else if (vars.commandIn("NEXT_RELATION")) {

      String strInitRecord = vars.getSessionValue(tabId + "|Section.initRecordNumber");
      String strRecordRange = Utility.getContext(this, vars, "#RecordRange", windowId);
      int intRecordRange = strRecordRange.equals("")?0:Integer.parseInt(strRecordRange);
      int initRecord = (strInitRecord.equals("")?0:Integer.parseInt(strInitRecord));
      if (initRecord==0) initRecord=1;
      initRecord += intRecordRange;
      strInitRecord = ((initRecord<0)?"0":Integer.toString(initRecord));
      vars.setSessionValue(tabId + "|Section.initRecordNumber", strInitRecord);
      vars.removeSessionValue(windowId + "|MA_Section_ID");

      response.sendRedirect(strDireccion + request.getServletPath() + "?Command=RELATION");
    } else if (vars.commandIn("FIRST")) {

      String strMA_Section_ID = vars.getRequiredStringParameter("inpmaSectionId");
      
      String strFirst = firstElement(vars);

      printPageEdit(response, request, vars, false, strFirst);
    } else if (vars.commandIn("LAST_RELATION")) {

      String strLast = lastElement(vars);
      printPageDataSheet(response, vars, strLast);
    } else if (vars.commandIn("LAST")) {

      String strMA_Section_ID = vars.getRequiredStringParameter("inpmaSectionId");
      
      String strLast = lastElement(vars);

      printPageEdit(response, request, vars, false, strLast);
    } else if (vars.commandIn("SAVE_NEW_RELATION", "SAVE_NEW_NEW", "SAVE_NEW_EDIT")) {

      OBError myError = null;
      SectionData data = null;
      int total = 0;
      try {
        data = getEditVariables(vars);
        String strSequence = SequenceIdData.getSequence(this, "MA_Section", vars.getClient());
        log4j.info("Sequence: " + strSequence);
        data.maSectionId = strSequence;
        total = data.insert(this);
      } catch(ServletException ex) {
        myError = Utility.translateError(this, vars, vars.getLanguage(), ex.getMessage());
        if (!myError.isConnectionAvailable()) {
          bdErrorConnection(response);
          return;
        } else vars.setMessage(tabId, myError);
      }
      if (myError==null && total==0) {
        myError = Utility.translateError(this, vars, vars.getLanguage(), "@CODE=DBExecuteError");
        vars.setMessage(tabId, myError);
      }
      if (myError!=null) {
        if (data!=null) {
          data.maSectionId = "";
          vars.setEditionData(tabId, data);
        }
        response.sendRedirect(strDireccion + request.getServletPath() + "?Command=NEW");
      } else {
        if (myError==null) {
          myError = Utility.translateError(this, vars, vars.getLanguage(), "@CODE=RowsInserted");
          myError.setMessage(total + " " + myError.getMessage());
          vars.setMessage(tabId, myError);
        }
        vars.setSessionValue(windowId + "|MA_Section_ID", data.maSectionId);
        if (vars.commandIn("SAVE_NEW_NEW")) response.sendRedirect(strDireccion + request.getServletPath() + "?Command=NEW");
        else if (vars.commandIn("SAVE_NEW_EDIT")) response.sendRedirect(strDireccion + request.getServletPath() + "?Command=EDIT");
        else response.sendRedirect(strDireccion + request.getServletPath() + "?Command=RELATION");
      }
    } else if (vars.commandIn("SAVE_EDIT_RELATION", "SAVE_EDIT_NEW", "SAVE_EDIT_EDIT", "SAVE_EDIT_NEXT")) {

      String strMA_Section_ID = vars.getRequiredGlobalVariable("inpmaSectionId", windowId + "|MA_Section_ID");
      int total = 0;
      SectionData data = null;
      OBError myError = null;
      try {
        data = getEditVariables(vars);
        total = data.update(this);
      } catch(ServletException ex) {
        myError = Utility.translateError(this, vars, vars.getLanguage(), ex.getMessage());
        if (!myError.isConnectionAvailable()) {
          bdErrorConnection(response);
          return;
        } else vars.setMessage(tabId, myError);
      }
      if (myError==null && total==0) {
        myError = Utility.translateError(this, vars, vars.getLanguage(), "@CODE=DBExecuteError");
        vars.setMessage(tabId, myError);
      }
      if (myError!=null) {
        if (data!=null) {
          
          vars.setEditionData(tabId, data);
        }
        response.sendRedirect(strDireccion + request.getServletPath() + "?Command=EDIT");
      } else {
        if (myError==null) {
          myError = Utility.translateError(this, vars, vars.getLanguage(), "@CODE=RowsUpdated");
          myError.setMessage(total + " " + myError.getMessage());
          vars.setMessage(tabId, myError);
        }
        if (vars.commandIn("SAVE_EDIT_NEW")) response.sendRedirect(strDireccion + request.getServletPath() + "?Command=NEW");
        else if (vars.commandIn("SAVE_EDIT_EDIT")) response.sendRedirect(strDireccion + request.getServletPath() + "?Command=EDIT");
        else if (vars.commandIn("SAVE_EDIT_NEXT")) {
          String strNext = nextElement(vars, strMA_Section_ID);
          vars.setSessionValue(windowId + "|MA_Section_ID", strNext);
          response.sendRedirect(strDireccion + request.getServletPath() + "?Command=EDIT");
        } else response.sendRedirect(strDireccion + request.getServletPath() + "?Command=RELATION");
      }
/*    } else if (vars.commandIn("DELETE_RELATION")) {

      String strMA_Section_ID = vars.getRequiredInStringParameter("inpmaSectionId");
      String message = deleteRelation(vars, strMA_Section_ID);
      if (!message.equals("")) {
        bdError(response, message, vars.getLanguage());
      } else {
        vars.removeSessionValue(windowId + "|maSectionId");
        vars.setSessionValue(tabId + "|Section.view", "RELATION");
        response.sendRedirect(strDireccion + request.getServletPath());
      }*/
    } else if (vars.commandIn("DELETE")) {

      String strMA_Section_ID = vars.getRequiredStringParameter("inpmaSectionId");
      //SectionData data = getEditVariables(vars);
      int total = 0;
      OBError myError = null;
      try {
        total = SectionData.delete(this, strMA_Section_ID);
      } catch(ServletException ex) {
        myError = Utility.translateError(this, vars, vars.getLanguage(), ex.getMessage());
        if (!myError.isConnectionAvailable()) {
          bdErrorConnection(response);
          return;
        } else vars.setMessage(tabId, myError);
      }
      if (myError==null && total==0) {
        myError = Utility.translateError(this, vars, vars.getLanguage(), "@CODE=DBExecuteError");
        vars.setMessage(tabId, myError);
      }
      vars.removeSessionValue(windowId + "|maSectionId");
      vars.setSessionValue(tabId + "|Section.view", "RELATION");
      if (myError==null) {
        myError = Utility.translateError(this, vars, vars.getLanguage(), "@CODE=RowsDeleted");
        myError.setMessage(total + " " + myError.getMessage());
        vars.setMessage(tabId, myError);
      }
      response.sendRedirect(strDireccion + request.getServletPath());






    } else if (vars.getCommand().toUpperCase().startsWith("BUTTON") || vars.getCommand().toUpperCase().startsWith("SAVE_BUTTON")) {
      pageErrorPopUp(response);
    } else pageError(response);
  }
/*
  String deleteRelation(VariablesSecureApp vars, String strMA_Section_ID) throws IOException, ServletException {
    log4j.debug("Deleting records");
    Connection conn = this.getTransactionConnection();
    try {
      if (strMA_Section_ID.startsWith("(")) strMA_Section_ID = strMA_Section_ID.substring(1, strMA_Section_ID.length()-1);
      if (!strMA_Section_ID.equals("")) {
        strMA_Section_ID = Replace.replace(strMA_Section_ID, "'", "");
        StringTokenizer st = new StringTokenizer(strMA_Section_ID, ",", false);
        while (st.hasMoreTokens()) {
          String strKey = st.nextToken();
          if (SectionData.deleteTransactional(conn, this, strKey)==0) {
            releaseRollbackConnection(conn);
            log4j.warn("deleteRelation - key :" + strKey + " - 0 records deleted");
          }
        }
      }
      releaseCommitConnection(conn);
    } catch (ServletException e) {
      releaseRollbackConnection(conn);
      e.printStackTrace();
      log4j.error("Rollback in transaction");
      return "ProcessRunError";
    }
    return "";
  }
*/
  SectionData getEditVariables(VariablesSecureApp vars) throws IOException,ServletException {
    SectionData data = new SectionData();
    data.adClientId = vars.getRequiredGlobalVariable("inpadClientId", windowId + "|AD_Client_ID");    data.adClientIdr = vars.getStringParameter("inpadClientId_R");    data.adOrgId = vars.getRequiredGlobalVariable("inpadOrgId", windowId + "|AD_Org_ID");    data.adOrgIdr = vars.getStringParameter("inpadOrgId_R");    data.isactive = vars.getStringParameter("inpisactive", "N");    data.name = vars.getRequiredStringParameter("inpname");    data.description = vars.getStringParameter("inpdescription");    data.maSectionId = vars.getRequestGlobalVariable("inpmaSectionId", windowId + "|MA_Section_ID");
      data.createdby = vars.getUser();
      data.updatedby = vars.getUser();


    
    

    

    return data;
  }

   SectionData[] getRelationData(SectionData[] data) {
    if (data!=null) {
      for (int i=0;i<data.length;i++) {        data[i].adClientId = FormatUtilities.truncate(data[i].adClientId, 14);        data[i].adOrgId = FormatUtilities.truncate(data[i].adOrgId, 14);        data[i].name = FormatUtilities.truncate(data[i].name, 50);        data[i].description = FormatUtilities.truncate(data[i].description, 50);}
    }
    return data;
  }



    void refreshSessionEdit(VariablesSecureApp vars, FieldProvider[] data) {
      if (data==null || data.length==0) return;
          vars.setSessionValue(windowId + "|AD_Client_ID", data[0].getField("adClientId"));    vars.setSessionValue(windowId + "|AD_Org_ID", data[0].getField("adOrgId"));    vars.setSessionValue(windowId + "|MA_Section_ID", data[0].getField("maSectionId"));
    }

    void refreshSessionNew(VariablesSecureApp vars) throws IOException,ServletException {
      SectionData[] data = SectionData.selectEdit(this, vars.getLanguage(), vars.getStringParameter("inpmaSectionId", ""));
      if (data==null || data.length==0) return;
      refreshSessionEdit(vars, data);
    }

  String nextElement(VariablesSecureApp vars, String strSelected) throws IOException, ServletException {
    if (strSelected == null || strSelected.equals("")) return firstElement(vars);
    if (tableSQL!=null) {
      FieldProvider[] data = null;
      try{
        String strSQL = ModelSQLGeneration.generateSQL(this, vars, tableSQL, (tableSQL.getTableName() + "." + tableSQL.getKeyColumn() + " AS ID"), new Vector<String>(), new Vector<String>());
        ExecuteQuery execquery = new ExecuteQuery(this, strSQL, tableSQL.getParameterValues());
        data = execquery.select(0,0);
      } catch (Exception e) { 
        if (log4j.isDebugEnabled()) log4j.debug("Error getting next element");
        e.printStackTrace();
      }
      if (data!=null) {
        boolean found = false;
        for (int i=0;i<data.length;i++) {
          if (!found && data[i].getField("ID").equals(strSelected)) found=true;
          else if (found) return data[i].getField("ID");
        }
      }
    }
    return strSelected;
  }

  int getKeyPosition(VariablesSecureApp vars, String strSelected) throws IOException, ServletException {
    if (log4j.isDebugEnabled()) log4j.debug("getKeyPosition: " + strSelected);
    if (tableSQL!=null) {
      FieldProvider[] data = null;
      try{
        String strSQL = ModelSQLGeneration.generateSQL(this, vars, tableSQL, (tableSQL.getTableName() + "." + tableSQL.getKeyColumn() + " AS ID"), new Vector<String>(), new Vector<String>());
        ExecuteQuery execquery = new ExecuteQuery(this, strSQL, tableSQL.getParameterValues());
        data = execquery.select(0,0);
      } catch (Exception e) { 
        if (log4j.isDebugEnabled()) log4j.debug("Error getting key position");
        e.printStackTrace();
      }
      if (data!=null) {
        for (int i=0;i<data.length;i++) {
          if (log4j.isDebugEnabled()) log4j.debug("row: " + i + " - key: " + data[i].getField("ID"));
          if (data[i].getField("ID").equals(strSelected)) return i;
        }
      }
    }
    return 0;
  }

  String previousElement(VariablesSecureApp vars, String strSelected) throws IOException, ServletException {
    if (strSelected == null || strSelected.equals("")) return firstElement(vars);
    if (tableSQL!=null) {
      FieldProvider[] data = null;
      try{
        String strSQL = ModelSQLGeneration.generateSQL(this, vars, tableSQL, (tableSQL.getTableName() + "." + tableSQL.getKeyColumn() + " AS ID"), new Vector<String>(), new Vector<String>());
        ExecuteQuery execquery = new ExecuteQuery(this, strSQL, tableSQL.getParameterValues());
        data = execquery.select(0,0);
      } catch (Exception e) { 
        if (log4j.isDebugEnabled()) log4j.debug("Error getting previous element");
        e.printStackTrace();
      }
      if (data!=null) {
        String previous = strSelected;
        for (int i=0;i<data.length;i++) {
          if (data[i].getField("ID").equals(strSelected)) return previous;
          previous = data[i].getField("ID");
        }
      }
    }
    return strSelected;
  }

  String firstElement(VariablesSecureApp vars) throws IOException, ServletException {
    if (tableSQL!=null) {
      FieldProvider[] data = null;
      try{
        String strSQL = ModelSQLGeneration.generateSQL(this, vars, tableSQL, (tableSQL.getTableName() + "." + tableSQL.getKeyColumn() + " AS ID"), new Vector<String>(), new Vector<String>());
        ExecuteQuery execquery = new ExecuteQuery(this, strSQL, tableSQL.getParameterValues());
        data = execquery.select(0,1);
      } catch (Exception e) { 
        if (log4j.isDebugEnabled()) log4j.debug("Error getting first element");
        e.printStackTrace();
      }
      if (data!=null && data.length!=0) return data[0].getField("ID");
    }
    return "";
  }

  String lastElement(VariablesSecureApp vars) throws IOException, ServletException {
    if (tableSQL!=null) {
      FieldProvider[] data = null;
      try{
        String strSQL = ModelSQLGeneration.generateSQL(this, vars, tableSQL, (tableSQL.getTableName() + "." + tableSQL.getKeyColumn() + " AS ID"), new Vector<String>(), new Vector<String>());
        ExecuteQuery execquery = new ExecuteQuery(this, strSQL, tableSQL.getParameterValues());
        data = execquery.select(0,0);
      } catch (Exception e) { 
        if (log4j.isDebugEnabled()) log4j.debug("Error getting last element");
        e.printStackTrace();
      }
      if (data!=null && data.length!=0) return data[data.length-1].getField("ID");
    }
    return "";
  }

  void printPageDataSheet(HttpServletResponse response, VariablesSecureApp vars, String strMA_Section_ID)
    throws IOException, ServletException {
    if (log4j.isDebugEnabled()) log4j.debug("Output: dataSheet");

    String strParamName = vars.getSessionValue(tabId + "|paramName");

    boolean hasSearchCondition=false;
    vars.removeEditionData(tabId);
    if (!(strParamName.equals(""))) hasSearchCondition=true;
    String strOffset = vars.getSessionValue(tabId + "|offset");
    String selectedRow = "0";
    if (!strMA_Section_ID.equals("")) {
      selectedRow = Integer.toString(getKeyPosition(vars, strMA_Section_ID));
    }

    String[] discard={"isNotFiltered","isNotTest"};
    if (hasSearchCondition) discard[0] = new String("isFiltered");
    if (vars.getSessionValue("#ShowTest", "N").equals("Y")) discard[1] = new String("isTest");
    XmlDocument xmlDocument = xmlEngine.readXmlTemplate("org/openbravo/erpWindows/Section/Section_Relation", discard).createXmlDocument();

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "Section", false, "document.frmMain.inpmaSectionId", "grid", "..", "".equals("Y"), "Section", strReplaceWith, false);
    toolbar.prepareRelationTemplate("N".equals("Y"), hasSearchCondition, vars.getSessionValue("#ShowTest", "N").equals("Y"), "N".equals("Y"));
    xmlDocument.setParameter("toolbar", toolbar.toString());



    String strOrderBy = vars.getSessionValue(tabId + "|orderby");
    StringBuffer orderByArray = new StringBuffer();
    if (!strOrderBy.equals("")) {
      vars.setSessionValue(tabId + "|newOrder", "1");
      String positions = vars.getSessionValue(tabId + "|orderbyPositions");
      orderByArray.append("var orderByPositions = new Array(\n");
      if (!positions.equals("")) {
        StringTokenizer tokens=new StringTokenizer(positions, ",");
        boolean firstOrder = true;
        while(tokens.hasMoreTokens()){
          if (!firstOrder) orderByArray.append(",\n");
          orderByArray.append("\"").append(tokens.nextToken()).append("\"");
          firstOrder = false;
        }
      }
      orderByArray.append(");\n");
      String directions = vars.getSessionValue(tabId + "|orderbyDirections");
      orderByArray.append("var orderByDirections = new Array(\n");
      if (!positions.equals("")) {
        StringTokenizer tokens=new StringTokenizer(directions, ",");
        boolean firstOrder = true;
        while(tokens.hasMoreTokens()){
          if (!firstOrder) orderByArray.append(",\n");
          orderByArray.append("\"").append(tokens.nextToken()).append("\"");
          firstOrder = false;
        }
      }
      orderByArray.append(");\n");
    }

    xmlDocument.setParameter("selectedColumn", "\nvar selectedRow = " + selectedRow + ";\n" + orderByArray.toString());
    xmlDocument.setParameter("direction", "var baseDirection = \"" + strReplaceWith + "/\";\n");
    xmlDocument.setParameter("windowId", windowId);
    xmlDocument.setParameter("KeyName", "maSectionId");
    xmlDocument.setParameter("language", "LNG_POR_DEFECTO=\"" + vars.getLanguage() + "\";");
    xmlDocument.setParameter("theme", vars.getTheme());
    //xmlDocument.setParameter("buttonReference", Utility.messageBD(this, "Reference", vars.getLanguage()));
    try {
      WindowTabs tabs = new WindowTabs(this, vars, tabId, windowId);
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "Section_Relation.html", "Section", "W", strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "Section_Relation.html", strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.relationTemplate());
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    try {
      KeyMap key = new KeyMap(this, vars, tabId, windowId);
      xmlDocument.setParameter("keyMap", key.getRelationKeyMaps());
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    {
      OBError myMessage = vars.getMessage(tabId);
      vars.removeMessage(tabId);
      if (myMessage!=null) {
        xmlDocument.setParameter("messageType", myMessage.getType());
        xmlDocument.setParameter("messageTitle", myMessage.getTitle());
        xmlDocument.setParameter("messageMessage", myMessage.getMessage());
      }
    }


    xmlDocument.setParameter("grid", Utility.getContext(this, vars, "#RecordRange", windowId));

    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println(xmlDocument.print());
    out.close();
  }

  void printPageEdit(HttpServletResponse response, HttpServletRequest request, VariablesSecureApp vars,boolean boolNew, String strMA_Section_ID)
    throws IOException, ServletException {
    if (log4j.isDebugEnabled()) log4j.debug("Output: edit");
    String strOrderByFilter = vars.getSessionValue(tabId + "|orderby");
    String orderClause = " 1";
    if (strOrderByFilter==null || strOrderByFilter.equals("")) strOrderByFilter = orderClause;
    /*{
      if (!strOrderByFilter.equals("") && !orderClause.equals("")) strOrderByFilter += ", ";
      strOrderByFilter += orderClause;
    }*/
    String strCommand = null;
    SectionData[] data=null;
    XmlDocument xmlDocument=null;
    FieldProvider dataField = vars.getEditionData(tabId);
    vars.removeEditionData(tabId);
    String strParamName = vars.getSessionValue(tabId + "|paramName");

    
    
    boolean hasSearchCondition=false;
    if (!(strParamName.equals(""))) hasSearchCondition=true;

       String strParamSessionDate = vars.getGlobalVariable("inpParamSessionDate", Utility.getTransactionalDate(this, vars, windowId), "");
      String strParamUser = vars.getUser();
      String buscador = "";
      String[] discard = {"", "isNotTest"};
      
      if (vars.getSessionValue("#ShowTest", "N").equals("Y")) discard[1] = new String("isTest");
    if (dataField==null) {
      if (!boolNew) {
        discard[0] = new String("newDiscard");
        data = SectionData.selectEdit(this, vars.getLanguage(), strMA_Section_ID);
  
        refreshSessionEdit(vars, data);
        strCommand = "EDIT";
      }

      if (boolNew || data==null || data.length==0) {
        discard[0] = new String ("editDiscard");
        strCommand = "NEW";
        data = new SectionData[0];
      } else {
        discard[0] = new String ("newDiscard");
      }
    } else {
      if (dataField.getField("maSectionId").equals("")) {
        discard[0] = new String ("editDiscard");
        strCommand = "NEW";
        boolNew = true;
      } else {
        discard[0] = new String ("newDiscard");
        strCommand = "EDIT";
      }
    }
    xmlDocument = xmlEngine.readXmlTemplate("org/openbravo/erpWindows/Section/Section_Edition",discard).createXmlDocument();

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "Section", (boolNew || (dataField==null && (data==null || data.length==0))), "document.frmMain.inpmaSectionId", "", "..", "".equals("Y"), "Section", strReplaceWith, true);
    toolbar.prepareEditionTemplate("N".equals("Y"), hasSearchCondition, vars.getSessionValue("#ShowTest", "N").equals("Y"), "N".equals("Y"));
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars, tabId, windowId);
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "Section_Relation.html", "Section", "W", strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "Section_Relation.html", strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.editionTemplate());
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    try {
      KeyMap key = new KeyMap(this, vars, tabId, windowId);
      xmlDocument.setParameter("keyMap", key.getEditionKeyMaps(boolNew));
    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    

    if (dataField==null) {
      if (boolNew || data==null || data.length==0) {
        refreshSessionNew(vars);
        data = SectionData.set(Utility.getDefault(this, vars, "AD_Client_ID", "@AD_CLIENT_ID@", "800029", ""), Utility.getDefault(this, vars, "AD_Org_ID", "@AD_ORG_ID@", "800029", ""), "Y", Utility.getDefault(this, vars, "Name", "", "800029", ""), Utility.getDefault(this, vars, "Description", "", "800029", ""), Utility.getDefault(this, vars, "MA_Section_ID", "", "800029", "0"));
        
      }
    }

    

    xmlDocument.setParameter("commandType", strCommand);
    xmlDocument.setParameter("buscador",buscador);
    xmlDocument.setParameter("windowId", windowId);
    xmlDocument.setParameter("changed", (strCommand.equals("NEW")?"1":""));
    xmlDocument.setParameter("language", "LNG_POR_DEFECTO=\"" + vars.getLanguage() + "\";");
    xmlDocument.setParameter("theme", vars.getTheme());
    //xmlDocument.setParameter("buttonReference", Utility.messageBD(this, "Reference", vars.getLanguage()));

    xmlDocument.setParameter("paramSessionDate", strParamSessionDate);

    xmlDocument.setParameter("direction", "var baseDirection = \"" + strReplaceWith + "/\";\n");
    {
      OBError myMessage = vars.getMessage(tabId);
      vars.removeMessage(tabId);
      if (myMessage!=null) {
        xmlDocument.setParameter("messageType", myMessage.getType());
        xmlDocument.setParameter("messageTitle", myMessage.getTitle());
        xmlDocument.setParameter("messageMessage", myMessage.getMessage());
      }
    }
    
    
    if (dataField==null) xmlDocument.setData("structure1",data);
    else {
      FieldProvider[] dataAux = new FieldProvider[1];
      dataAux[0] = dataField;
      xmlDocument.setData("structure1",dataAux);
    }
    try {
      ComboTableData comboTableData = null;
comboTableData = new ComboTableData(vars, this, "19", "AD_Client_ID", "", "129", Utility.getContext(this, vars, "#User_Org", windowId), Utility.getContext(this, vars, "#User_Client", windowId), 0);
Utility.fillSQLParameters(this, vars, (dataField==null?data[0]:dataField), comboTableData, windowId, (dataField==null?data[0].getField("adClientId"):dataField.getField("adClientId")));
xmlDocument.setData("reportAD_Client_ID","liststructure", comboTableData.select(!strCommand.equals("NEW")));
comboTableData = null;
comboTableData = new ComboTableData(vars, this, "19", "AD_Org_ID", "", "104", Utility.getContext(this, vars, "#User_Org", windowId), Utility.getContext(this, vars, "#User_Client", windowId), 0);
Utility.fillSQLParameters(this, vars, (dataField==null?data[0]:dataField), comboTableData, windowId, (dataField==null?data[0].getField("adOrgId"):dataField.getField("adOrgId")));
xmlDocument.setData("reportAD_Org_ID","liststructure", comboTableData.select(!strCommand.equals("NEW")));
comboTableData = null;

    } catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex);
    }
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println(xmlDocument.print());
    out.close();
  }







  public String getServletInfo() {
    return "Servlet Section. This Servlet was made by Wad constructor";
  } // fin del mÃ©todo getServletInfo()
}
