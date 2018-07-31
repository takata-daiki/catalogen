/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tct.dashboard.report;

import com.appCinfigpage.bean.fc;
import com.email.bean.SendEmail;
import com.mysql.jdbc.Connection;
import com.sql.bean.Sql;
import com.tct.dashboard.ReportBean;
import com.tct.dashboard.report.Data.DefectTotalReportControlData;
import com.tct.dashboard.report.Data.DefectTotalReportDetialsData;
import com.tct.data.Line;
import com.tct.data.jpa.LineJpaController;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import javax.persistence.Persistence;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import com.tct.data.*;
import com.tct.data.jpa.*;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import com.tct.data.*;
import com.tct.data.jpa.*;
import com.time.timeCurrent;
 
import java.text.ParseException;
 
import java.text.DecimalFormat;
 

/**
 *
 * @author Tawat Saekue
 */
public class DefectTotalReportControl {

    private List<NgPoint> ngPoints;
//    private ChartSeries lineseries;
    private CartesianChartModel ngmodel;
    private String lineiid;
    private Line currentline;
//    private List<Target> targets;
    private double maxnum = 0;
    private String ngvalue;
    private int mount = 0;
    private DefectTotalReportControlData currentSearchDefect;
    private ProcessPoolJpaController processPoolJpaController = null;
    private DetailSpecialJpaController detailSpecialJpaController = null;
    private ModelPoolJpaController modelPoolJpaController = null;
    private NgPoolJpaController ngPoolJpaController = null;
    private SendProblemJpaController sendProblemJpaController = null;

    public SendProblemJpaController getSendProblemJpaController() {
        if (sendProblemJpaController == null) {
            sendProblemJpaController = new SendProblemJpaController(Persistence.createEntityManagerFactory("tct_projectxPU"));
        }
        return sendProblemJpaController;
    }

    public DetailSpecialJpaController getDetailSpecialJpaController() {
        if (detailSpecialJpaController == null) {
            detailSpecialJpaController = new DetailSpecialJpaController(Persistence.createEntityManagerFactory("tct_projectxPU"));
        }
        return detailSpecialJpaController;
    }

    public ModelPoolJpaController getModelPoolJpaController() {
        if (modelPoolJpaController == null) {
            modelPoolJpaController = new ModelPoolJpaController(Persistence.createEntityManagerFactory("tct_projectxPU"));
        }
        return modelPoolJpaController;
    }

    public NgPoolJpaController getNgPoolJpaController() {
        if (ngPoolJpaController == null) {
            ngPoolJpaController = new NgPoolJpaController(Persistence.createEntityManagerFactory("tct_projectxPU"));
        }
        return ngPoolJpaController;
    }

    public ProcessPoolJpaController getProcessPoolJpaController() {
        if (processPoolJpaController == null) {
            processPoolJpaController = new ProcessPoolJpaController(Persistence.createEntityManagerFactory("tct_projectxPU"));
        }
        return processPoolJpaController;
    }

    public int getMount() {
        return mount;
    }

    public DefectTotalReportControl() {
    }

    public void back() {
        mount++;
        getSumNGbyDate(mount);
    }

    public void changLine() {
        print("================== chang line ===================================");
        fc.print("currentSearchDefect.getDefectName()"+currentSearchDefect.getDefectName());
        print("line id : " + lineiid);
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Test");
//        ChartSeries lineseries = new ChartSeries();  
        try {
            currentline = getLineJpaController().findLine(lineiid);
            getSumDefect(getfillter());
            String title = "";
            String tmpD = "";
            double numValue = 0;
            Date date = new Date();
            print("NgListSize : " + getNgPoints().size());
            int i = 0;
            for (NgPoint ngPoint : getNgPoints()) {
                print("Test1");
                if (getDateFormat("dd", ngPoint.getDate()).equals(tmpD)) {
                    print("Test2");
                    title += ngPoint.getNgname() + " " + ngPoint.getNumpcs() + "Pcs. " + ngPoint.getNumpercen() + "% ComOn : " + ngPoint.getComeon() + "\n";
                    numValue += ngPoint.getNumpcs();
                    tmpD = getDateFormat("dd", ngPoint.getDate());
                    date = ngPoint.getDate();
                    i++;
                } else {
                    print("Test3");
                    if (!tmpD.isEmpty()) {
                        print("Title : " + title);
//                        lineseries.setLabel(title);
//                        lineseries.set(getDateFormat("dd/MM/yy", date), numValue);
                        boys.set(getDateFormat("dd/MM/yy", date), numValue);
                        title = "";
                        print("======================= Test4");
                        if (maxnum < numValue) {
                            maxnum = numValue + 20;
                        }
                        numValue = 0;
                    }
                    print("Test5");
                    title = ngPoint.getNgname() + " " + ngPoint.getNumpcs() + "Pcs. " + ngPoint.getNumpercen() + "% ComOn : " + ngPoint.getComeon() + "\n";
                    numValue += ngPoint.getNumpcs();
                    tmpD = getDateFormat("dd", ngPoint.getDate());
                    date = ngPoint.getDate();
                    print("Test6");
                    i++;
                }
//                getNgmodel();
            }
            print("Test7");
            if (i >= getNgPoints().size()) {
                print("Test8");
                if (!tmpD.isEmpty()) {
                    print("=============================== Test9");
                    print("Title : \n" + title);
//                    boys.setLabel(title);
//                    lineseries.set(getDateFormat("dd/MM/yy", date), numValue);
                    boys.set(getDateFormat("dd/MM/yy", date), numValue);

                    if (maxnum < numValue) {
                        maxnum = numValue + 20;
                    }
                    numValue = 0;
                }
            }
            print("Test10");
//            LineChartSeries series1 = new LineChartSeries();  
//             series1.setLabel("Series 1");  
//  
//        series1.set(1, 2);  
//        series1.set(2, 1);  
//        series1.set(3, 3);  
//        series1.set(4, 6);  
//        series1.set(5, 8);  
            ngmodel = null;
            getNgmodel().addSeries(boys);
//            getNgmodel().addSeries(lineseries);
            print("NGModel : " + getNgmodel().toString());
            print("Test11");
        } catch (Exception e) {
            print("= Error chang line" + e);
        }
        //resetSearch(); //To default value 
    }

    public void resetSearch() {
        currentline = new Line();
        lineiid = null;
        currentSearchDefect = new DefectTotalReportControlData("null", "null", "null", null, null, "null");
    }

    public List<SelectItem> getLine() {
        print("================== getCombo line ================================");
        try {
            List<SelectItem> line = new ArrayList<SelectItem>();
            List<Line> lines = getLineJpaController().findAllLineNotDeletd();
            for (Line line1 : lines) {
                print("line : " + line1.getLineName());
                line.add(new SelectItem(line1.getLineId(), line1.getLineName()));
            }
            return line;
        } catch (Exception e) {
            print("== getCombo line Error : " + e);
            return new ArrayList<SelectItem>();
        }
    } 
    //*** function to set fillter
    public String getfillter()
    {   
        String fillter  = "";
        if(!currentSearchDefect.getProcess().equals("null"))
        {
            fillter   += " and main_data.id_proc ='"+currentSearchDefect.getProcess().toString()+"' ";
        }
        if(!currentSearchDefect.getProcessType().equals("null"))
        {
            fillter   += " and process_pool.type ='"+currentSearchDefect.getProcessType().toString()+"' ";
        }
        if(!currentSearchDefect.getDefectName().equals("null"))
        { 
            NgPool   objPool  = getNgPoolJpaController().findNgPool(currentSearchDefect.getDefectName());
            fillter   += " and ng_normal.name ='"+objPool.getNgName()+"' ";
        }
        if(!currentSearchDefect.getModel().equals("null"))
        {
            fillter   += " and lot_control.model_pool_id ='"+currentSearchDefect.getModel().toString()+"' ";
        }
        if(!lineiid.equals("null"))
        {
            fillter   += " and current_process.line ='"+lineiid+"' ";
        }  
        if(currentSearchDefect.getStart()!= null)
        {
//            fillter   += " and ";
        }
        if(currentSearchDefect.getEnd()!=null)
        {
//            fillter   += "";
        }
        return  fillter;
    } 
    public String getsql(String fillter, String date) { 
        String sql = " SELECT sum( pcs_qty.qty) AS 'psc',"
                + " ng_normal.name,sum( ng_normal.qty) AS 'ng',(( sum( ng_normal.qty)/ sum( pcs_qty.qty))*100) AS percent"
                + " , main.time_current,current_process.time_current,line.line_name"
                + " FROM current_process main INNER JOIN pcs_qty ON main.id = pcs_qty.current_process_id"
                + " INNER JOIN main_data ON main.main_data_id = main_data.id "
                + " INNER JOIN current_process ON main_data.id = current_process.main_data_id "
                + " INNER JOIN ng_normal ON current_process.id = ng_normal.current_process_id "
                + " INNER JOIN lot_control ON main_data.lot_control_id = lot_control.id "
                + " INNER JOIN process_pool ON main_data.id_proc = process_pool.id_proc "
                + " INNER JOIN model_pool ON model_pool.id = lot_control.model_pool_id "
                + " INNER JOIN line ON current_process.line = line.line_id WHERE pcs_qty.qty_type = 'in' "
                + " and  DATE(current_process.time_current) = DATE('" + date + "') " + fillter
                + " GROUP BY  ng_normal.name ORDER BY ng_normal.name ASC"; 
        print(sql);
        return sql;
    }
    public String array_name_ng = "";

    public String getArray_name_ng() {
        return array_name_ng;
    }

    public void setArray_name_ng(String array_name_ng) {
        this.array_name_ng = array_name_ng;
    }

    public String getArray_ng() {
        return array_ng;
    }

    public void setArray_ng(String array_ng) {
        this.array_ng = array_ng;
    }
    public String array_ng = "";

    public List<SelectItem> dateItem() {
        List<SelectItem> itemDate = new ArrayList<SelectItem>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        // initian Calendar
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Calendar setdate = Calendar.getInstance();
        for (int i = day; i > 0; i--) {
            setdate.set(year, month, i);
            SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
            String date = sd.format(setdate.getTime());
            //  System.out.println("date "+date)
            itemDate.add(new SelectItem(date, date));
        }
        return itemDate;
    }

    public void getSumNGbyDate(int last) {
        // get Max  number in Month
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        // initian Calendar
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) - last;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        Calendar new_cal = Calendar.getInstance();
        new_cal.set(year, month, day);

        int days = new_cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // last full day
        if (last > 0) {
            day = days;
        }

        Connection conn = getConnection("mysql");
        print("Conn : " + conn.getHost());
        Calendar setdate = Calendar.getInstance();
        array_name_ng = "[";
        array_ng = "[";
        for (int i = 1; i < days; i++) {
            if (i <= day) {
                try {
                    // initian date
                    setdate.set(year, month, i);
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date = sd.format(setdate.getTime());
                    print(date);
                    // initian fillter
                    String fillter = "";
                    Statement stm = conn.createStatement();
                    if (stm.execute(getsql(getfillter(), date))) {
                        ResultSet result = stm.executeQuery(getsql(getfillter(), date));
                        double sum_ng = 0.0;
                        double qty_lot = 0.0;

                        array_name_ng += "[";
                        while (result.next()) {
                            print("qty :" + result.getObject(2) + " name :" + result.getObject(2));
                            qty_lot += Double.parseDouble(result.getObject(1).toString());
                            sum_ng += Double.parseDouble(result.getObject(3).toString());
                            array_name_ng += "'" + result.getObject(2).toString() + " " + result.getObject(3) + "pcs. " + convertDoubleFormat(Double.parseDouble(result.getObject(4).toString())) + "%',"; //+ ngPoint.getNumpercen() + "% ComOn : " + ngPoint.getComeon() 
                        }
                        array_name_ng += " ],";
                        if (sum_ng > 0.0) {
                            array_ng += "[" + String.valueOf(i) + "," + convertDoubleFormat((sum_ng / qty_lot) * 100) + "],";
                        }
                    }


                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DefectTotalReportControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        array_name_ng += " []]";
        array_ng += "[]]";
        fc.print("Array name ng :" + array_name_ng);
        fc.print("Array ng :" + array_ng);

    }
    private String convertDoubleFormat(double d) {
        return new DecimalFormat("#,###.####").format(d);
    }

    private void getSumDefect(String fillter) {
        getSumNGbyDate(0); 
    }

    public Connection getConnection(String brand) {
        print(brand);
        if (brand.equals("mysql")) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                return (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/tct_project", "root", "root");
            } catch (Exception ex) {
                Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                return (Connection) DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=tct_project", "test", "1234");
            } catch (Exception ex) {
                Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

    }

    private String getDateFormat(String pattern, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(date);
    } 
    private LineJpaController lineJpaController;

    private LineJpaController getLineJpaController() {
        if (lineJpaController == null) {
            lineJpaController = new LineJpaController(Persistence.createEntityManagerFactory("tct_projectxPU"));
        }
        return lineJpaController;
    }

    public String getLineiid() {
        return lineiid;
    }

    public void setLineiid(String lineiid) {
        this.lineiid = lineiid;
    } 
    public List<NgPoint> getNgPoints() {
        if (ngPoints == null) {
            ngPoints = new ArrayList<NgPoint>();
        }
        return ngPoints;
    }

    public double getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(double maxnum) {
        this.maxnum = maxnum;
    }

    public CartesianChartModel getNgmodel() {
        if (ngmodel == null) {
            ngmodel = new CartesianChartModel();
        }
        return ngmodel;
    } 
    private void print(String str) {
        System.out.println(str);
    }

    public String getNGvalue() {
        return ngvalue;
    }

    //@pang  22/10/2555
//    << ============== fillter for search function ====================>>  
//    loadProcess 
    public List<SelectItem> loadProcess() {
        List<SelectItem> listProcess = new ArrayList<SelectItem>();
        listProcess.add(new SelectItem("null", "--- Select one ---"));
        List<ProcessPool> objProcessPool = getProcessPoolJpaController().findProcessPoolByNameNotDeteltedAsc();
        if (!objProcessPool.isEmpty()) {
            for (ProcessPool objList : objProcessPool) {
                listProcess.add(new SelectItem(objList.getIdProc(), objList.getProcName()));
            }
        }

        return listProcess;
    }
//    load processtype

    public List<SelectItem> loadProcessType() {
        List<SelectItem> listProcessType = new ArrayList<SelectItem>();
        listProcessType.add(new SelectItem("null", "--- Select one ---"));
        List<DetailSpecial> obDetailSpecail = getDetailSpecialJpaController().findAlltNotDelete();
        for (DetailSpecial objList : obDetailSpecail) {
            listProcessType.add(new SelectItem(objList.getId(), objList.getName()));
        }
        return listProcessType;
    }

    public List<SelectItem> loadDefectName() {
        List<SelectItem> listDefectName = new ArrayList<SelectItem>();
        listDefectName.add(new SelectItem("null", "--- Select one ---"));
        List<NgPool> objNgPool = getNgPoolJpaController().findByNgType("ng");
        for (NgPool objList : objNgPool) {
            listDefectName.add(new SelectItem(objList.getIdNg(), objList.getNgName()));
        }
        return listDefectName;
    }

    public List<SelectItem> loadModelName() {
        List<SelectItem> listModelName = new ArrayList<SelectItem>();
        listModelName.add(new SelectItem("null", "--- Select one ---"));
        List<ModelPool> objModelPool = getModelPoolJpaController().findModelPoolByNotDeleted();
        for (ModelPool objList : objModelPool) {
            String model = objList.getModelGroup() + "-" + objList.getModelSeries() + "-" + objList.getModelName();
            listModelName.add(new SelectItem(objList.getId(), model));
        }
        return listModelName;
    }
//     <<================ end=================>>
    //send problem 
    private boolean statusProblem = false;
    private boolean checkovertarget = false;
    private SendProblem currentSendProblem;
    private ReportSettingAttributeJpaController reportSettingAttributeJpaController = null;
    private ReportSettingJpaController reportSettingJpaController = null;
    private String dated;
    private String validateSendProblem;

    private ReportSettingAttributeJpaController getReportSettingAttributeJpaController() {
        if (reportSettingAttributeJpaController == null) {
            reportSettingAttributeJpaController = new ReportSettingAttributeJpaController(Persistence.createEntityManagerFactory("tct_projectxPU"));
        }
        return reportSettingAttributeJpaController;
    }

    public ReportSettingJpaController getReportSettingJpaController() {
        if (reportSettingJpaController == null) {
            reportSettingJpaController = new ReportSettingJpaController(Persistence.createEntityManagerFactory("tct_projectxPU"));
        }
        return reportSettingJpaController;
    }

    public void changetProblem(String status) throws ParseException {
        if (status.equals("open")) {
//            if (checkNotToday()) {
                statusProblem = true;
//            }
        } else if (status.equals("save")) {
            getCurrentSendProblem().setIdSendProblem(gemId());
            getCurrentSendProblem().setResult("0.12");
            getCurrentSendProblem().setDated(Sql.strDate(dated));
            getCurrentSendProblem().setStatus("red");
            getCurrentSendProblem().setSendEmail("false");
            sendProblem(); //save data
//            Email
//            SendEmail.mails(); //send email
            statusProblem = false;
            currentSendProblem = new SendProblem();
        } else {
            statusProblem = false;
        }
    } 
    public void sendProblem() {
        try {
            getSendProblemJpaController().create(getCurrentSendProblem());
        } catch (Exception e) {
            fc.print("Error not to insert send  problem data " + e);
        }
    }
//    load item details

    public List<DefectTotalReportDetialsData> loadDetails() {
        List<DefectTotalReportDetialsData> objDetailsNg = new ArrayList<DefectTotalReportDetialsData>();
        List<SendProblem> objSendProblem = getSendProblemJpaController().findAllNotDeletedOrderByDate();
        if (!objSendProblem.isEmpty()) {
            for (SendProblem objList : objSendProblem) {
                objDetailsNg.add(new DefectTotalReportDetialsData(objList.getIdSendProblem(), Sql.formatDateSqlForNgDetails(objList.getDated()), objList.getCommentGerneral(), objList.getSolution()));
            }
        }

        return objDetailsNg;
    }

    public String gemId() {
        String id = UUID.randomUUID().toString();
        return id;
    }

    public DefectTotalReportControlData getCurrentSearchDefect() {
        if (currentSearchDefect == null) {
            currentSearchDefect = new DefectTotalReportControlData("null", "null", "null", null, null, "null");
        }
        return currentSearchDefect;
    }

    public void changeOverTarget(ValueChangeEvent event) {
        boolean check = (Boolean) event.getNewValue();
        if (check) {
            checkovertarget = true;
        } else {
            checkovertarget = false;
        }
    }

    public void setCurrentSearchDefect(DefectTotalReportControlData currentSearchDefect) {
        this.currentSearchDefect = currentSearchDefect;
    }

    public boolean isStatusProblem() {
        return statusProblem;
    }

    public void setStatusProblem(boolean statusProblem) {
        this.statusProblem = statusProblem;
    }

    public boolean isCheckovertarget() {
        return checkovertarget;
    }

    public void setCheckovertarget(boolean checkovertarget) {
        this.checkovertarget = checkovertarget;
    }

    public SendProblem getCurrentSendProblem() {
        if (currentSendProblem == null) {
            currentSendProblem = new SendProblem();
        }
        return currentSendProblem;
    }

    public void setCurrentSendProblem(SendProblem currentSendProblem) {
        this.currentSendProblem = currentSendProblem;
    }

    public double getTarget() {
        ReportSetting objReport = getReportSettingJpaController().findNameReportSettingNotDeleted("ngdefect");
        List<ReportSettingAttribute> objAttribute = getReportSettingAttributeJpaController().findByIdReportSetting(objReport);
        double target = 0;
        for (ReportSettingAttribute objList : objAttribute) {
            if (objList.getNameReportAtt().equals("target")) {
                target = Double.parseDouble(objList.getValueReportAtt());
                break;
            }
        }
        return target;
    }

    public double getUcl() {
        ReportSetting objReport = getReportSettingJpaController().findNameReportSettingNotDeleted("ngdefect");
        List<ReportSettingAttribute> objAttribute = getReportSettingAttributeJpaController().findByIdReportSetting(objReport);
        double target = 0;
        for (ReportSettingAttribute objList : objAttribute) {
            if (objList.getNameReportAtt().equals("ucl")) {
                target = Double.parseDouble(objList.getValueReportAtt());
                break;
            }
        }
        return target;
    }

    public String getDated() {
        return dated;
    }

    public void setDated(String dated) {
        this.dated = dated;
    }

    public String getValidateSendProblem() {
        return validateSendProblem;
    }

    public void setValidateSendProblem(String validateSendProblem) {
        this.validateSendProblem = validateSendProblem;
    }

    public Line getCurrentline() {
        if(currentline == null)
        {
            currentline  = new Line();
        }
        return currentline;
    }

    public void setCurrentline(Line currentline) {
        this.currentline = currentline;
    }
    
}
