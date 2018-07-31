package gov.mil.controller;

import gov.mil.domain.DashBoardBean;
import gov.mil.domain.Fields;
import gov.mil.domain.Maintenance;
import gov.mil.domain.Placeslocal;
import gov.mil.domain.ReportBean;
import gov.mil.domain.User;
import gov.mil.extras.DateFilter;
import gov.mil.service.FieldsService;
import gov.mil.service.MaintenanceService;
import gov.mil.service.PlacesLocalService;
import gov.mil.service.ReportsService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;



/**
 * @author angelo
 *
 */
@Controller
@RequestMapping("/dash")
@SessionAttributes({"user","perpVal","graphTypes"})
public class DashboardController {
	protected static Logger logger = Logger.getLogger(DashboardController.class);
	private String defaultStyle = "/resources/js/dashboard_themes/grid.js";		
	@Resource(name="repService")
	private ReportsService repService;
	@Resource(name="placesLocalService")
	private PlacesLocalService placesLocalService;
	@Resource(name="fieldService")
	private FieldsService fieldService;
	@Resource(name="fileService")
	private MaintenanceService fileService;
	
	private List<Integer> list1 = new ArrayList<Integer>();
	private List<Integer> list2 = new ArrayList<Integer>();
	private List<Integer> list3 = new ArrayList<Integer>();
	private List<Integer> list4a = new ArrayList<Integer>();
	private List<Integer> list4b = new ArrayList<Integer>();
	private List<Integer> list4c = new ArrayList<Integer>();
	private List<Integer> list4d = new ArrayList<Integer>();
	private List<String> yAxisCategory0 = new ArrayList<String>();
	private List<String> yAxisCategory1 = new ArrayList<String>();
	private List<String> yAxisCategory2 = new ArrayList<String>();
	private List<String> yAxisCategory3 = new ArrayList<String>();
	
	private Map<String, List<Integer>> bars0 = new HashMap<String, List<Integer>>();
	private Map<String, List<Integer>> bars1 = new HashMap<String, List<Integer>>();
	private Map<String, List<Integer>> bars2 = new HashMap<String, List<Integer>>();
	private Map<String, List<Integer>> bars3 = new HashMap<String, List<Integer>>();
	private Map<String, List<Integer>> bars4 = new HashMap<String, List<Integer>>();
	
	
    @RequestMapping(value = "/dashboardMainOld", method = RequestMethod.GET)
    public String showOldDashboard(@ModelAttribute("user") User user,
    		Model model) {
    	
    	logger.debug("Received request to show Dashboard Initialpage");
    	if (user.getOnDashboard() == null) {
    		logger.debug("ACCESS getOnDashboard: "+user.getOnDashboard());
    		return "/error_page_access";
    	}
    	else if (user.getOnDashboard() == false) {
        	logger.debug("ACCESS getOnDashboard: "+user.getOnDashboard());
        	return "/error_page_access";
        }

        model.addAttribute("javascript", new GraphControllers().generateSample());
        model.addAttribute("graphStyle", defaultStyle);
        return "dashboardMain";
	}
  
    @RequestMapping(value = "/dashboardGraphReport", method = {RequestMethod.GET, RequestMethod.POST})
    public String postDashboardGraphReport(@ModelAttribute("user") User user,
    		@RequestParam(value="dateFrom", required=true) String dateFrom,
    		@RequestParam(value="dateTo", required=true) String dateTo,    	
    		@RequestParam(value="actListValue", required=true) Integer actListValue,
    		@RequestParam(value="actVal", required=true) String actVal,
    		@RequestParam(value="catVal", required=true) String catVal,
    		@RequestParam(value="perpVal", required=true) String perpVal,
    		@RequestParam(value="typRepVal", required=true) String typRepVal,    		
    		@RequestParam(value="locListVal", required=true) String locListVal,
			@RequestParam(value="locListSubVal1", required=true) String locListSubVal1,
			@RequestParam(value="locListSubVal2", required=true) String locListSubVal2,
			@RequestParam(value="locListSubVal3", required=true) String locListSubVal3,			
    		@RequestParam(value="graphTypes", required=true) String graphTypes,
    		@RequestParam(value="reportTitle", required=true) String reportTitle,
    		@RequestParam(value="dateType", required=true) String dateType,
    		Model model) {

    	GraphControllers grph = new GraphControllers();
    	List<Integer> list15 = new ArrayList<Integer>();
    	
    	String chartTypes = "";
    	String containersLoc = "container1";		
		String subtitle = "";
		String rLabel = "", lLabel = "";
		
		ReportBean rB = new ReportBean();
    	//Used for scenario 1 only
	    if (graphTypes.equalsIgnoreCase("pieCharts")) {
	    	chartTypes = "pieCharts";
	    	Integer totalCount = 0;
	    	List<Fields> fields = new Vector<Fields>();
	    	if(!actListValue.equals(0)){
				fields = fieldService.getPerActbyNames(actVal);
			} else {
				fields = fieldService.getPerActbyTableName(catVal);		
			}
	    	
	    	if(catVal.equals("Maritime")) {
	    		fields = fieldService.getPerActCode("MA");
			}
	    	
	    	for (int i = 0; i < fields.size(); i++) {
        		Fields column = fields.get(i);
        		Integer domCount = repService.getCountOfCol(catVal, column.getColumnName(), dateFrom, dateTo, typRepVal, perpVal, locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
        		
        		if(domCount > 0) {
        			List<Integer> n = new ArrayList<Integer>();
    				n.add(domCount);
    				bars0.put(column.getColumnDesc(), n);
        		}
    		}
		    rB.setyAxisCategory(yAxisCategory0);
	    	rB.setJscript1(grph.createGenGraph(chartTypes, containersLoc, reportTitle, subtitle, rB.getyAxisCategory(), rLabel , lLabel, bars0));
	    	
	    }
    	//Used for scenario 2 only	    
	    
		if (graphTypes.equalsIgnoreCase("BarRegions")) {
			chartTypes = "columnCharts2";			
	    	List<Placeslocal> localRegfields = new Vector<Placeslocal>();
			List<Fields> fieldsAA = new Vector<Fields>();
			List<Fields> fieldsUA = new Vector<Fields>();	 
			List<Fields> fieldsCO = new Vector<Fields>();
			List<Fields> fieldsMA = new Vector<Fields>();
	    	yAxisCategory1.clear();
	    	list4a.clear();
	    	list4b.clear();
	    	list4c.clear();
	    	bars1.clear();
	    	Integer totalCount = 0; 	

			
			if(catVal.equals("Maritime")) {
	    		fieldsMA = fieldService.getPerActCode("MA");
	    		localRegfields = placesLocalService.getPlacesByGroup("Isl");
				Placeslocal others = new Placeslocal();
				others.setPlace("unknown");		
				localRegfields.add(others);
		    	
		    	for (int i = 0; i < localRegfields.size(); i++) {
		    		Placeslocal regions = localRegfields.get(i);
		    		if (regions.getPlace().equals("unknown")) {
		    			yAxisCategory1.add("'Unknown'");
		    		} else {
		    			yAxisCategory1.add("'"+regions.getPlace()+"'");
		    		}
		    		
		    		if (actListValue == 1 || actListValue == 0) {
						for (int ix = 0; ix < fieldsMA.size(); ix++) {
			        		Fields column = fieldsMA.get(ix);
			        		Integer domCount = repService.getCountOfColGroupByRegions(catVal, column.getColumnName(), dateFrom, dateTo, "", perpVal, regions.getPlace(), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
			        		if(domCount > 0) {
			        			totalCount = totalCount + domCount;
			        		}
						}
						
						list4a.add(totalCount + 0);
			    		totalCount = 0;
		    		}
		    	}
			}
			
			else if(catVal.equals("Domestic")) {		
				fieldsAA = fieldService.getPerActCode("AA");
				fieldsUA = fieldService.getPerActCode("UA");
				fieldsCO = fieldService.getPerActCode("CO");
				
				localRegfields = placesLocalService.getPlacesByGroup("Isl");
				Placeslocal others = new Placeslocal();
				others.setPlace("unknown");		
				localRegfields.add(others);
		    	
		    	for (int i = 0; i < localRegfields.size(); i++) {
		    		Placeslocal regions = localRegfields.get(i);
		    		if (regions.getPlace().equals("unknown")) {
		    			yAxisCategory1.add("'Unknown'");
		    		} else {
		    			yAxisCategory1.add("'"+regions.getPlace()+"'");
		    		}
		    		
		    		if (actListValue == 1 || actListValue == 0) {
						for (int ix = 0; ix < fieldsAA.size(); ix++) {
			        		Fields column = fieldsAA.get(ix);
			        		Integer domCount = repService.getCountOfColGroupByRegions(catVal, column.getColumnName(), dateFrom, dateTo, "", perpVal, regions.getPlace(), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
			        		if(domCount > 0) {
			        			totalCount = totalCount + domCount;
			        		}
						}
						
						list4a.add(totalCount + 0);
			    		totalCount = 0;
		    		}
		    		
		    		if (actListValue == 2 || actListValue == 0) {
			    		for (int ix = 0; ix < fieldsUA.size(); ix++) {
			        		Fields column = fieldsUA.get(ix);
			        		Integer domCount = repService.getCountOfColGroupByRegions(catVal, column.getColumnName(), dateFrom, dateTo, "", perpVal, regions.getPlace(), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
			        		if(domCount > 0) {
			        			totalCount = totalCount + domCount;
			        		}
			    		}
			    		
			    		list4b.add(totalCount + 0);
			    		totalCount = 0;		
		    		}
		    		
		    		if (actListValue == 4 || actListValue == 0) {
		    			for (int ix = 0; ix < fieldsCO.size(); ix++) {
			        		Fields column = fieldsCO.get(ix);
			        		Integer domCount = repService.getCountOfColGroupByRegions(catVal, column.getColumnName(), dateFrom, dateTo, "", perpVal, regions.getPlace(), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
			        		if(domCount > 0) {
			        			totalCount = totalCount + domCount;
			        		}
		    			}
		    			
			    		list4c.add(totalCount + 0);
			    		totalCount = 0;
		    		}
				}
			}
			
		    	rB.setyAxisCategory(yAxisCategory1);
			
		    	if (actListValue == 1 || actListValue == 0) {
		    		bars1.put("Armed", list4a);
		    	}
		    	
		    	if (actListValue == 2 || actListValue == 0) {
		    		bars1.put("Unarmed", list4b);
		    	}
		    	
		    	if (actListValue == 4 || actListValue == 0) {
		    		bars1.put("Government Counteraction Operations", list4c);
		    	}
			rB.setJscript1(grph.createGenGraph(chartTypes, containersLoc, reportTitle, subtitle, rB.getyAxisCategory(), rLabel , lLabel, bars1));
	    	
		}
		//Scenario 3
		if (graphTypes.equalsIgnoreCase("BarMonthly")) {
			chartTypes = "columnCharts1";
			List<String> yAxisCategory4 = new ArrayList<String>();
			Map<String, List<Integer>> bars4 = new HashMap<String, List<Integer>>();
			List<Fields> fieldsAA = new Vector<Fields>();
			List<Fields> fieldsUA = new Vector<Fields>();	 
			List<Fields> fieldsCO = new Vector<Fields>();
			List<Fields> fieldsMA = new Vector<Fields>();
			
	    	Integer totalCount = 0;
	    	list4a.clear();
	    	list4b.clear();
	    	list4c.clear();
	    	bars4.clear();
	    	
	    	yAxisCategory4.clear();
	    	String months[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	    	
	    if (catVal.equalsIgnoreCase("Domestic")) {
	    	fieldsAA = fieldService.getPerActCode("AA");
	    	fieldsUA = fieldService.getPerActCode("UA");
	    	fieldsCO = fieldService.getPerActCode("CO");
	    	int startMonthLoop = Integer.parseInt(dateFrom.substring(5, 7));
	    	int endMonthLoop = Integer.parseInt(dateTo.substring(5, 7));
			for (int i = 1; i < 13; i++) {
				yAxisCategory4.add(months[i-1]);
				
					if (i >= startMonthLoop && i <= endMonthLoop) { // Apply date filter
						for (int ix = 0; ix < fieldsAA.size(); ix++) {
			        		Fields column = fieldsAA.get(ix);
			        		Integer domCount = repService.getCountOfColGroupByMonth(catVal, column.getColumnName(), dateFrom, dateTo, "", perpVal, String.valueOf(i), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
			        		if(domCount > 0) {
			        			totalCount = totalCount + domCount;
			        		}
						}
					}
					
					list4a.add(totalCount + 0);
		    		totalCount = 0;

		    		if (i >= startMonthLoop && i <= endMonthLoop) { // Apply date filter
			    		for (int ix = 0; ix < fieldsUA.size(); ix++) {
			        		Fields column = fieldsUA.get(ix);
			        		Integer domCount = repService.getCountOfColGroupByMonth(catVal, column.getColumnName(), dateFrom, dateTo, "", perpVal, String.valueOf(i), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
			        		if(domCount > 0) {
			        			totalCount = totalCount + domCount;
			        		}
			    		}
					}    
		    		
		    		list4b.add(totalCount + 0);
		    		totalCount = 0;

		    		if (i >= startMonthLoop && i <= endMonthLoop) { // Apply date filter
		    			for (int ix = 0; ix < fieldsCO.size(); ix++) {
			        		Fields column = fieldsCO.get(ix);
			        		Integer domCount = repService.getCountOfColGroupByMonth(catVal, column.getColumnName(), dateFrom, dateTo, "", perpVal, String.valueOf(i), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
			        		if(domCount > 0) {
			        			totalCount = totalCount + domCount;
			        		}
		    			}
		    		}
		    		
		    		list4c.add(totalCount + 0);
		    		totalCount = 0;
			}
			rB.setyAxisCategory(yAxisCategory4);
	    }
	    else if (catVal.equalsIgnoreCase("Maritime")) {
	    	if(catVal.equals("Maritime")) {
	    		fieldsMA = fieldService.getPerActCode("MA");
			}
	    	int startMonthLoop = Integer.parseInt(dateFrom.substring(5, 7));
	    	int endMonthLoop = Integer.parseInt(dateTo.substring(5, 7));
			for (int i = 1; i < 13; i++) {
				yAxisCategory4.add(months[i-1]);
				
					if (i >= startMonthLoop && i <= endMonthLoop) { // Apply date filter
						for (int ix = 0; ix < fieldsMA.size(); ix++) {
			        		Fields column = fieldsMA.get(ix);
			        		Integer domCount = repService.getCountOfColGroupByMonth(catVal, column.getColumnName(), dateFrom, dateTo, "", perpVal, String.valueOf(i), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
			        		if(domCount > 0) {
			        			totalCount = totalCount + domCount;
			        		}
						}
					}
					
					list4a.add(totalCount + 0);
		    		totalCount = 0;

			}
			rB.setyAxisCategory(yAxisCategory4);
	    }
    	if (actListValue == 1 || actListValue == 0) {
    		bars4.put("Armed", list4a);
    	}
    	if (actListValue == 2 || actListValue == 0) {
    		bars4.put("Unarmed", list4b);
    	}
    	if (actListValue == 4 || actListValue == 0) {
    		bars4.put("Government Counteraction Operations", list4c);
    	}
		rB.setJscript1(grph.createGenGraph(chartTypes, containersLoc, reportTitle, subtitle, rB.getyAxisCategory(), rLabel , lLabel, bars4));
    	
		}
		//Scenario 4
		if (graphTypes.equalsIgnoreCase("BarPerpetratorsPlain")) {
		chartTypes = "columnCharts2";
		yAxisCategory3 = new ArrayList<String>();
		Map<String, List<Integer>> bars3 = new HashMap<String, List<Integer>>();
		list3.clear();	
		    	List<Maintenance> perpetrators = new Vector<Maintenance>();
		    	List<Fields> fields = new Vector<Fields>();
		    	
		    	Integer totalCount = 0; 
		    	if(!actVal.equals("---all---")) {
					fields = fieldService.getPerActbyNames(actVal);
				} else {
					fields = fieldService.getPerActCodeList("AA","UA","CO","MV");
				}
		    	
		    	if(catVal.equals("Maritime")) {
		    		fields = fieldService.getPerActCode("MA");
				}
		    	
		    	perpetrators = fileService.getMenu("SM", "PR");
			    	for (int i = 0; i < perpetrators.size(); i++) {
			    		Maintenance perps = perpetrators.get(i);
			    		if (!perps.getLabel().equals("unknown")) {
			    			yAxisCategory3.add("'"+perps.getLabel()+"'");
				    	} else {
				    		yAxisCategory3.add("'Others'");		
				    	}
			    		
			    		for (int ix = 0; ix < fields.size(); ix++) {
			        		Fields column = fields.get(ix);
			        		Integer domCount = 0;
			        		
			        		if (perpVal.equals("---all---")) {
				        		domCount = repService.getCountOfColGroupByPerp(catVal, column.getColumnName(), dateFrom, dateTo, "", perps.getLabel(), user.getAccessLevel(), user.getReportingUnit(), dateType);
			        		} else {
			        			if(perps.getLabel().equals(perpVal)) {
			        				domCount = repService.getCountOfColGroupByPerp(catVal, column.getColumnName(), dateFrom, dateTo, "", perpVal, user.getAccessLevel(), user.getReportingUnit(), dateType);
			        			}
			        		}
			        		
			        		if(domCount > 0) {
			        			totalCount = totalCount + domCount;
			        		}
			    		}
		    			
			    		list3.add(totalCount);
			    		totalCount = 0; 
					}
			    	rB.setyAxisCategory(yAxisCategory3);
			
			model.addAttribute(list3);
			bars3.put("Activities", list3);
			rB.setJscript1(grph.createGenGraph(chartTypes, containersLoc, reportTitle.replaceFirst("Activities", "Perpetrators"), subtitle, rB.getyAxisCategory(), rLabel , lLabel, bars3));
	    	
		}
		//Scenario 5
		if (graphTypes.equalsIgnoreCase("simpleLineCharts")) {
			chartTypes = "simpleLineCharts";
			List<String> yAxisCategory4 = new ArrayList<String>();
			Map<String, List<Integer>> bars4 = new HashMap<String, List<Integer>>();
			Map<String, List<Integer>> bars5 = new HashMap<String, List<Integer>>();
			List<Fields> fieldsAA = new Vector<Fields>();
			List<Fields> fieldsUA = new Vector<Fields>();	 
			List<Fields> fieldsCO = new Vector<Fields>();
			List<Fields> fieldsMV = new Vector<Fields>();
			List<Fields> fieldsMA = new Vector<Fields>();
	    	Integer totalCount = 0;
	    	list4a.clear();
	    	list4b.clear();
	    	list4c.clear();
	    	list4d.clear();
	    	
	    	yAxisCategory4.clear();
	    	yAxisCategory4.add("'Jan'");
	    	yAxisCategory4.add("'Feb'");
	    	yAxisCategory4.add("'Mar'");
	    	yAxisCategory4.add("'Apr'");
	    	yAxisCategory4.add("'May'");
	    	yAxisCategory4.add("'Jun'");
	    	yAxisCategory4.add("'Jul'");
	    	yAxisCategory4.add("'Aug'");
	    	yAxisCategory4.add("'Sep'");	    	
	    	yAxisCategory4.add("'Oct'");
	    	yAxisCategory4.add("'Nov'");
	    	yAxisCategory4.add("'Dec'");
	    if (catVal.equalsIgnoreCase("Domestic")) {
	    	fieldsAA = fieldService.getPerActCode("AA");
	    	fieldsUA = fieldService.getPerActCode("UA");
	    	fieldsMV = fieldService.getPerActCode("MV");
	    	fieldsCO = fieldService.getPerActCode("CO");
	    	int startMonthLoop = Integer.parseInt(dateFrom.substring(5, 7));
	    	int endMonthLoop = Integer.parseInt(dateTo.substring(5, 7));
			for (int i = 1; i < 13; i++) {
					if (i >= startMonthLoop && i <= endMonthLoop) { // Apply date filter
						for (int ix = 0; ix < fieldsAA.size(); ix++) {
			        		Fields column = fieldsAA.get(ix);
			        		Integer domCount = repService.getCountOfColGroupByMonth(catVal, column.getColumnName(), dateFrom, dateTo, "", perpVal, String.valueOf(i), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
			        		if(domCount > 0) {
			        			totalCount = totalCount + domCount;
			        		}
						}
					}
					list4a.add(totalCount + 0);
		    		totalCount = 0;
		    		
		    		if (i >= startMonthLoop && i <= endMonthLoop) { // Apply date filter
			    		for (int ix = 0; ix < fieldsUA.size(); ix++) {
			        		Fields column = fieldsUA.get(ix);
			        		Integer domCount = repService.getCountOfColGroupByMonth(catVal, column.getColumnName(), dateFrom, dateTo, "", perpVal, String.valueOf(i), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
			        		if(domCount > 0) {
			        			totalCount = totalCount + domCount;
			        		}
			    		}
					}    
		    		list4b.add(totalCount + 0);
		    		totalCount = 0;
				
		    		if (i >= startMonthLoop && i <= endMonthLoop) { // Apply date filter
		    			for (int ix = 0; ix < fieldsCO.size(); ix++) {
			        		Fields column = fieldsCO.get(ix);
			        		Integer domCount = repService.getCountOfColGroupByMonth(catVal, column.getColumnName(), dateFrom, dateTo, "", perpVal, String.valueOf(i), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
			        		if(domCount > 0) {
			        			totalCount = totalCount + domCount;
			        		}
		    			}
		    		}
		    		list4c.add(totalCount + 0);
		    		totalCount = 0;
			}
			rB.setyAxisCategory(yAxisCategory4);
		
	    	if (actListValue == 1 || actListValue == 0) {
	    		bars4.put("Armed", list4a);
	    	}
	    	if (actListValue == 2 || actListValue == 0) {
	    		bars4.put("Unarmed", list4b);
	    	}
	    	if (actListValue == 4 || actListValue == 0) {
	    		bars4.put("Government Counteraction Operations", list4c);
	    	}
	    	
			rB.setJscript1(grph.createGenGraph(chartTypes, containersLoc, "Monthly Trending of Armed and Unarmed Activities and the Governments Counter Operations", subtitle, rB.getyAxisCategory(), rLabel , lLabel, bars4));
			
	    }
	    if (catVal.equalsIgnoreCase("Maritime")) {
	    	fieldsMA = fieldService.getPerActbyTableName("Maritime");
	    	int startMonthLoop = Integer.parseInt(dateFrom.substring(5, 7));
	    	int endMonthLoop = Integer.parseInt(dateTo.substring(5, 7));
	    	for (int ix = 0; ix < fieldsMA.size(); ix++) {
	    		List<Integer> list5 = new ArrayList<Integer>();
	    		Fields column = fieldsMA.get(ix);
	    		for (int i = 1; i < 13; i++) {
	    			if (i >= startMonthLoop && i <= endMonthLoop) { // Apply date filter
	    				Integer domCount = repService.getCountOfColGroupByMonth(catVal, column.getColumnName(), dateFrom, dateTo, "", "---all---", String.valueOf(i), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
	        			if(domCount > 0) {
	        				totalCount = totalCount + domCount;
	        			}
	    			}
	        		list5.add(totalCount + 0);
	        		totalCount = 0;	        		
	    		}
	    		bars5.put(column.getActivityName(), list5);    		
			}
			rB.setyAxisCategory(yAxisCategory4);
	    	rB.setJscript1(grph.createGenGraph(chartTypes, containersLoc, "Monthly Trending of Maritime Activities", subtitle, rB.getyAxisCategory(), rLabel , lLabel, bars5));
			
	    }
	}
		String jscript = rB.getJscript1();
    	model.addAttribute("javascript", jscript);
    	model.addAttribute("graphStyle", defaultStyle);
    	model.addAttribute("reportTitle",reportTitle);
    	model.addAttribute("actListValue",actListValue);
    	model.addAttribute("actVal",actVal);
    	model.addAttribute("catVal", catVal);
    	model.addAttribute("perpVal", perpVal);
    	model.addAttribute("typRepVal", typRepVal);
    	model.addAttribute("dateType", dateType);
    	model.addAttribute("locListVal", locListVal);
    	model.addAttribute("locListSubVal1", locListSubVal1);
    	model.addAttribute("locListSubVal2", locListSubVal2);
    	model.addAttribute("locListSubVal3", locListSubVal3);    	
    	model.addAttribute("fromDate", dateFrom);
    	model.addAttribute("toDate", dateTo);
    	model.addAttribute("dateFrom", dateFrom);
    	model.addAttribute("dateTo", dateTo);
        return "dashboardGraphReport";
	}
    
    /**
     * Initial Development stage. 
     * @author Angelo Dizon
     * @param rptBean
     * @param model
     * @return javascripts and values for the Graph plugin
     */
    @RequestMapping(value = "/dashboardMainOld", method = RequestMethod.POST)
    public String manipulateOldDashBoard(@RequestParam(value="fromDate", required=true) String dateFrom,
			@RequestParam(value="toDate", required=true) String dateTo,
			@ModelAttribute("dashAttribute") ReportBean rptBean, Model model) {
    	List<Integer> list1a = new ArrayList<Integer>();
    	Map<String, List<Integer>> bars = new HashMap<String, List<Integer>>();
    	list1a.add(35);list1a.add(29);list1a.add(83);list1a.add(57);
    	//bars.put("Armed Activities", list1);
    	//bars.put("Unarmed Activities", list1a);
    	ReportBean rB = new ReportBean();
    	logger.debug("Received request to Manipulate Dashboard");
		String addSql = "";
		String title = "" , subtitle = "";
		String rLabel = "", lLabel = "";
		GraphControllers grph = new GraphControllers();
		
		String groupDate = rptBean.getDateReportsGrouping();
		List<String> yAxisCategory1 = new ArrayList<String>();  
		if (groupDate.equalsIgnoreCase("date")) {
			addSql = "GROUP BY DATE()";
			yAxisCategory1.add("'M'");
			yAxisCategory1.add("'T'");
			yAxisCategory1.add("'W'");
			yAxisCategory1.add("'Th'");
			yAxisCategory1.add("'F'");
			yAxisCategory1.add("'Sa'");
			yAxisCategory1.add("'Su'");
		}
		else if (groupDate.equalsIgnoreCase("month")) {
			addSql = "GROUP BY MONTH()";
			yAxisCategory1.add("'J'");
			yAxisCategory1.add("'F'");
			yAxisCategory1.add("'M'");
			yAxisCategory1.add("'A'");
			yAxisCategory1.add("'M'");
			yAxisCategory1.add("'J'");
			yAxisCategory1.add("'J'");
			yAxisCategory1.add("'A'");
			yAxisCategory1.add("'S'");
			yAxisCategory1.add("'O'");
			yAxisCategory1.add("'N'");
			yAxisCategory1.add("'D'");
		}
		else if (groupDate.equalsIgnoreCase("year")) {
			addSql = "GROUP BY YEAR()";
			yAxisCategory1.add("'2010'");
			yAxisCategory1.add("'2011'");
			yAxisCategory1.add("'2012'");
			yAxisCategory1.add("'2013'");
			yAxisCategory1.add("'2014'");
		}
		
		String type = rptBean.getReportsGrouping();
		
		if (type.equalsIgnoreCase("armedActivites")) {
			title = "Armed Activities";
			subtitle = addSql + "2012";
			bars.put("Abduction", repService.getCountOfN("armAbduction"));
			bars.put("Ambush", repService.getCountOfN("armAmbush"));
			bars.put("Arson", repService.getCountOfN("armArson"));
			bars.put("Attack", repService.getCountOfN("armAttack"));
			bars.put("Bombing", repService.getCountOfN("armBombing"));
			bars.put("Encounter", repService.getCountOfN("armEncounter"));
			bars.put("Harassment", repService.getCountOfN("armHarassment"));
			bars.put("Liquidation", repService.getCountOfN("armLiquidation"));
			bars.put("Raid", repService.getCountOfN("armRaid"));
			bars.put("Robbery", repService.getCountOfN("armRobbery"));			
		}
		else if (type.equalsIgnoreCase("unarmedActivities")) {
			title = "UnArmed Activities";
			subtitle = addSql + "2012";
			bars.put("AgitationProp", repService.getCountOfN("unaAgitationProp"));
			bars.put("CasingSurv", repService.getCountOfN("unaCasingSurv"));
			bars.put("Conference", repService.getCountOfN("unaConference"));
			bars.put("Consolidation", repService.getCountOfN("unaConsolidation"));
			bars.put("Demands", repService.getCountOfN("unaDemands"));
			bars.put("Forums", repService.getCountOfN("unaForums"));
			bars.put("Issuance", repService.getCountOfN("unaIssuance"));
			bars.put("Mass", repService.getCountOfN("unaMass"));
			bars.put("Meetings", repService.getCountOfN("unaMeetings"));
			bars.put("Movement", repService.getCountOfN("unaMovement"));
			bars.put("PagRelated", repService.getCountOfN("unaPagRelated"));
			bars.put("Peacetalks", repService.getCountOfN("unaPeacetalks"));
			bars.put("Plans", repService.getCountOfN("unaPlans"));
			bars.put("Recruitment", repService.getCountOfN("unaRecruitment"));
			bars.put("Release", repService.getCountOfN("unaRelease"));
			bars.put("Seaborne", repService.getCountOfN("unaSeaborne"));
			bars.put("Shipments", repService.getCountOfN("unaShipments"));
			bars.put("Sightings", repService.getCountOfN("unaSightings"));
			bars.put("Training", repService.getCountOfN("unaTraining"));
		}
		else if (type.equalsIgnoreCase("maritimeViolations")) {
			title = "Maritime Violations Activities";
			subtitle = addSql + "2012";
			bars.put("ArmedRobbery", repService.getCountOfN("mvaArmedRobbery"));
			bars.put("Extraction", repService.getCountOfN("mvaExtraction"));
			bars.put("Foreign", repService.getCountOfN("mvaForeign"));
			bars.put("IllegalFishing", repService.getCountOfN("mvaIllegalFishing"));
			bars.put("IllegalLog", repService.getCountOfN("mvaIllegalLog"));
			bars.put("IllegalTransport", repService.getCountOfN("mvaIllegalTransport"));
			bars.put("Piracy", repService.getCountOfN("mvaPiracy"));
			bars.put("Smuggling", repService.getCountOfN("mvaSmuggling"));
			bars.put("Trafficking", repService.getCountOfN("mvaTrafficking"));
		}
		else if (type.equalsIgnoreCase("resourceGeneration")) {
			title = "Resource Generation";
			subtitle = addSql + "2012";
			bars.put("Business", repService.getCountOfN("resBusiness"));
			bars.put("Extortion", repService.getCountOfN("resExtortion"));
			bars.put("Foraging", repService.getCountOfN("resForaging"));
			bars.put("Kidnap", repService.getCountOfN("resKidnap"));
		}
		else if (type.equalsIgnoreCase("counteraction")) {
			title = "Counter Action";
			subtitle = addSql + "2012";
			bars.put("Arrest", repService.getCountOfN("couArrest"));
			bars.put("Encounters", repService.getCountOfN("couEncounters"));
			bars.put("Identified", repService.getCountOfN("couIdentified"));
			bars.put("Manhunt", repService.getCountOfN("couManhunt"));
			bars.put("Encounters", repService.getCountOfN("couEncounters"));
			bars.put("Neutralized", repService.getCountOfN("couNeutralized"));
			bars.put("Overrun", repService.getCountOfN("couOverrun"));
			bars.put("Pursuit", repService.getCountOfN("couPursuit"));
			bars.put("Raid", repService.getCountOfN("couRaid"));
			bars.put("Recovered", repService.getCountOfN("couRecovered"));
			bars.put("Rescued", repService.getCountOfN("couRescued"));
			bars.put("Surrenders", repService.getCountOfN("couSurrenders"));
			bars.put("counterOperation", repService.getCountOfN("counterOperation"));
		}
		else if (type.equalsIgnoreCase("sectoral")) {
			title = "Sectoral Groups";
			subtitle = addSql + "2012";
			bars.put("Doctors", repService.getCountOfN("secDoctors"));
			bars.put("Environmental", repService.getCountOfN("secEnvironmental"));
			bars.put("GovernEmp", repService.getCountOfN("secGovernEmp"));
			bars.put("HumanRights", repService.getCountOfN("secHumanRights"));
			bars.put("IndPeople", repService.getCountOfN("secIndPeople"));
			bars.put("LaborUnion", repService.getCountOfN("secLaborUnion"));
			bars.put("Lawyers", repService.getCountOfN("secLawyers"));
			bars.put("Media", repService.getCountOfN("secMedia"));
			bars.put("MigrantWork", repService.getCountOfN("secMigrantWork"));
			bars.put("Others", repService.getCountOfN("secOthers"));
			bars.put("OutSchool", repService.getCountOfN("secOutSchool"));
			bars.put("Peasants", repService.getCountOfN("secPeasants"));
			bars.put("Professional", repService.getCountOfN("secProfessional"));
			bars.put("Students", repService.getCountOfN("secStudents"));			
			bars.put("Teachers", repService.getCountOfN("secTeachers"));			
			bars.put("UrbanPoor", repService.getCountOfN("secUrbanPoor"));			
			bars.put("WomenChild", repService.getCountOfN("secWomenChild"));			
			bars.put("Youth", repService.getCountOfN("secYouth"));			
		}
		else if (type.equalsIgnoreCase("afo")) {
			title = "Affiliations / Factions / Organization";
			subtitle = addSql + "2012";
			bars.put("Cpp", repService.getCountOfN("afoCpp"));
			bars.put("Ndf", repService.getCountOfN("afoNdf"));
			bars.put("Npa", repService.getCountOfN("afoNpa"));
		}		
		
//		perpetrators
		
		String graphTypes = rptBean.getGraphTypes();
		String containersLoc = rptBean.getContainersLoc();
		if (containersLoc.equalsIgnoreCase("container1")) {			
			rB.setJscript1(grph.createGenGraph(graphTypes, containersLoc, title, subtitle, yAxisCategory1, rLabel , lLabel, bars));
		}
		else if (containersLoc.equalsIgnoreCase("container2")) {
			rB.setJscript2(grph.createGenGraph(graphTypes, containersLoc, title, subtitle, yAxisCategory1, rLabel , lLabel, bars));
    	}	
		else if (containersLoc.equalsIgnoreCase("container3")) {			
			rB.setJscript3(grph.createGenGraph(graphTypes, containersLoc, title, subtitle, yAxisCategory1, rLabel , lLabel, bars));
    	}	
		else if (containersLoc.equalsIgnoreCase("container4")) {
			rB.setJscript4(grph.createGenGraph(graphTypes, containersLoc, title, subtitle, yAxisCategory1, rLabel , lLabel, bars));
		}
		
		
		//jscript1 = grph.createGenGraph(graphTypes, containersLoc, title, subtitle, yAxisCategory1, "", "Activities by CPP", bars);
//		  jscript1 = grph.createAreaGraph(containersLoc, title, subtitle, yAxisCategory1, "", "Activities by CPP", bars);
//        jscript2 = grph.createColumnGraph(containersLoc, title, subtitle, yAxisCategory1, "", "Violation/Incidents",  bars);
//        jscript3 = grph.createSimpleBarGraph(containersLoc, title, subtitle, yAxisCategory1, "Incidents", "", list1);
//        jscript4 = grph.createPieGraph(containersLoc, title, subtitle, yAxisCategory1, "", "", bars);

		
		/**
		 * This method makes the graph more persistent
		 */
//		  rB.setJscript1(jscript1);
//		  rB.setJscript2(jscript2);
//		  rB.setJscript3(jscript3);
//		  rB.setJscript4(jscript4);
//		System.out.println("LOOK AT THIS >>"+rB.getJscript1());
        String jscript = rB.getJscript1() + rB.getJscript2() + rB.getJscript3() + rB.getJscript4();
//        System.out.println("LOOK AT THIS >>"+rptBean.getResetRequest());
		if (rptBean.getResetRequest())
		{
			rB.setJscript1("");rB.setJscript2("");rB.setJscript3("");rB.setJscript4("");
			model.addAttribute("javascript", "");
		}
		else
		{
			model.addAttribute("javascript", jscript);  
		}
		if (rptBean.getGraphStyles().equals(null) || rptBean.getGraphStyles().equals(""))
		{
			rB.setGraphStyles(defaultStyle);
		}
		else 
			rB.setGraphStyles(rptBean.getGraphStyles());
		
		model.addAttribute("graphStyle", rB.getGraphStyles());
		return "dashboardMain";
	}
    
    @RequestMapping(value = "/dashboardMain", method = RequestMethod.GET)
    public String showNewDashboard(@ModelAttribute("user") User user,
    		@RequestParam(value="perpVal", required=false) String perpVal,
    		Model model) {
    	ReportBean rB = new ReportBean();
    	logger.debug("DASHBOARD:***==Received request to show Dashboard Initialpage");
    	if (user.getOnDashboard() == null) {
    		logger.debug("ACCESS getOnDashboard: "+user.getOnDashboard());
    		return "/error_page_access";
    	}
    	else if (user.getOnDashboard() == false) {
        	logger.debug("ACCESS getOnDashboard: "+user.getOnDashboard());
        	return "/error_page_access";
        }
    	
    	DateFilter df = new DateFilter();
    	Date date = new Date();
    	
    	String dateFrom = df.getNoOfPreviousMonth(date, 3);
    	String dateTo = df.getCurrentDateToString(date);
    	String weekFrom = df.getNoOfPreviousDays(date, 7);
    	String weekTo = df.getCurrentDateToString(date);
    	String monthFrom = df.getNoOfPreviousMonth(date, 1);
    	
    	String actVal = "ARMED";
    	String catVal = "";
    	
    	if (user.getDefaultDashboard() == null || user.getDefaultDashboard() == "") {
    		catVal = "Domestic";
    		user.setDefaultDashboard("Domestic");
    	}
    	else {
    		catVal = user.getDefaultDashboard();
    		model.addAttribute("user",user);
    	}
    	model.addAttribute("user",user);
    		
    	perpVal = (perpVal == null) ? "---all---" : perpVal;
    	String typRepVal = "---all---";
    	String locListVal = "---all---";
    	String locListSubVal1 = "---all---";
    	String locListSubVal2 = "---all---";
    	String locListSubVal3 = "---all---";
    	String reportTitle = "";
    	
    	GraphControllers grph = new GraphControllers();
    	List<Integer> list15 = new ArrayList<Integer>();
    	List<Integer> list16 = new ArrayList<Integer>();
    	List<String> yAxisCategory0 = new ArrayList<String>();
    	List<String> yAxisCategory1 = new ArrayList<String>();
    	List<String> yAxisCategory2 = new ArrayList<String>();
    	List<String> yAxisCategory3 = new ArrayList<String>();
    	String chartTypes = "";
    			
		String subtitle = "";
		String rLabel = "", lLabel = "";
		String dateType = "G.dateOfIncident";//G.dateOfIncident Or R.dateReport
    	//Used for scenario 1 only
	    	chartTypes = "pieCharts";
	    	bars0.clear();
	    	Integer totalCount = 0;
	    	List<Fields> fields = new Vector<Fields>();
	    	
	    	if(catVal.equals("Maritime")) {
	    		fields = fieldService.getPerActCode("MA");
			} else {
				fields = fieldService.getPerActCode("UA");
			}
	    	
	    	for (int i = 0; i < fields.size(); i++) {
        		Fields column = fields.get(i);
        		
        		Integer domCount = repService.getCountOfCol(catVal, column.getColumnName(), monthFrom, dateTo, typRepVal, perpVal, locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
        		
        		if(domCount > 0) {
        			List<Integer> n = new ArrayList<Integer>();
    				//n.add(0);
    				n.add(domCount);
    				bars0.put(column.getColumnDesc(), n);
//    				n.clear();
        		}
    		}
		    rB.setyAxisCategory(yAxisCategory0);		    
	    	rB.setJscript4(grph.createGenGraph(chartTypes, "container4", reportTitle, subtitle, rB.getyAxisCategory(), rLabel , lLabel, bars0));
	    	
	    	chartTypes = "pieCharts";
	    	bars0.clear();
	    	totalCount = 0;
	    	fields = new Vector<Fields>();
	    	
	    	if(catVal.equals("Maritime")) {
	    		fields = fieldService.getPerActCode("MA");
			} else {
				fields = fieldService.getPerActCode("AA");
			}
	    	
	    	for (int i = 0; i < fields.size(); i++) {
        		Fields column = fields.get(i);
        		
        		Integer domCount = repService.getCountOfCol(catVal, column.getColumnName(), monthFrom, dateTo, typRepVal, perpVal, locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
        		
        		if(domCount > 0) {
        			List<Integer> n = new ArrayList<Integer>();
    				//n.add(0);
    				n.add(domCount);
    				bars0.put(column.getColumnDesc(), n);
//    				n.clear();
        		}
    		}
		    rB.setyAxisCategory(yAxisCategory0);		    
	    	rB.setJscript3(grph.createGenGraph(chartTypes, "container3", reportTitle, subtitle, rB.getyAxisCategory(), rLabel , lLabel, bars0));
	    	
			chartTypes = "columnCharts2";	
			bars1.clear();
	    	List<Placeslocal> localRegfields = new Vector<Placeslocal>();
	    	fields = new Vector<Fields>();
	    	yAxisCategory1.clear();
	    	//list1.clear();
	    	totalCount = 0; 
			
	    	if(catVal.equals("Maritime")){
	    		fields = fieldService.getPerActCode("MA");
			} else {
				fields = fieldService.getPerActCode("AA");
			}
	    	localRegfields.clear();
			localRegfields = placesLocalService.getPlacesByGroup("Isl");
				Placeslocal others = new Placeslocal();
				others.setPlace("unknown");		
				localRegfields.add(others);
				
		    	for (int i = 0; i < localRegfields.size(); i++) {
		    		Placeslocal regions = localRegfields.get(i);
		    		if (regions.getPlace().equals("unknown")) {
		    			yAxisCategory1.add("'Others'");
		    		}
		    		else
		    			yAxisCategory1.add("'"+regions.getPlace()+"'");
		    			fields = fieldService.getPerActCode("AA");
			    		for (int ix = 0; ix < fields.size(); ix++) {
			        		Fields column = fields.get(ix);
			        		Integer domCount = repService.getCountOfColGroupByRegions(catVal, column.getColumnName(), monthFrom, dateTo, "", perpVal, regions.getPlace(), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
			        		if(domCount > 0) {
			        			totalCount = totalCount + domCount;
			        		}			    		
			    		
						}
			    		list15.add(totalCount + 0);
			    		totalCount = 0; 
			    		
			    		fields = fieldService.getPerActCode("UA");
			    		for (int ix = 0; ix < fields.size(); ix++) {
			        		Fields column = fields.get(ix);
			        		Integer domCount = repService.getCountOfColGroupByRegions(catVal, column.getColumnName(), monthFrom, dateTo, "", perpVal, regions.getPlace(), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
			        		if(domCount > 0) {
			        			totalCount = totalCount + domCount;
			        		}			    		
			    		
						}
			    		list16.add(totalCount + 0);
			    		totalCount = 0; 
				}
		    	rB.setyAxisCategory(yAxisCategory1);
			
			bars1.put("Armed", list15);
			bars1.put("Unarmed", list16);
			rB.setJscript2(grph.createGenGraph(chartTypes, "container2", reportTitle, subtitle, rB.getyAxisCategory(), rLabel , lLabel, bars1));
			
			chartTypes = "columnCharts1";
			List<String> yAxisCategory4 = new ArrayList<String>();
			Map<String, List<Integer>> bars4 = new HashMap<String, List<Integer>>();
			List<Fields> fieldsAA = new Vector<Fields>();
			List<Fields> fieldsUA = new Vector<Fields>();	 
			List<Fields> fieldsCO = new Vector<Fields>();
	    	totalCount = 0;
	    	list4a.clear();
	    	list4b.clear();
	    	list4c.clear();
	    	list4d.clear();
	    	bars4.clear();
	    	
	    	yAxisCategory4.clear();
	    	String months[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	    	
	    if (catVal.equalsIgnoreCase("Domestic")) {
	    	fieldsAA = fieldService.getPerActCode("AA");
	    	fieldsUA = fieldService.getPerActCode("UA");
	    	fieldsCO = fieldService.getPerActCode("CO");
	    	int startMonthLoop = Integer.parseInt(dateFrom.substring(5, 7));
	    	int endMonthLoop = Integer.parseInt(dateTo.substring(5, 7));
			for (int i = 1; i < 13; i++) {
				if (i >= startMonthLoop && i <= endMonthLoop) {
					yAxisCategory4.add(months[i-1]);
				}
				
				if (i >= startMonthLoop && i <= endMonthLoop) { // Apply date filter
					for (int ix = 0; ix < fieldsAA.size(); ix++) {
		        		Fields column = fieldsAA.get(ix);
		        		Integer domCount = repService.getCountOfColGroupByMonth(catVal, column.getColumnName(), dateFrom, dateTo, "", perpVal, String.valueOf(i), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
		        		if(domCount > 0) {
		        			totalCount = totalCount + domCount;
		        		}
					}
					
					list4a.add(totalCount + 0);
		    		totalCount = 0;
				}
	    		if (i >= startMonthLoop && i <= endMonthLoop) { // Apply date filter
		    		for (int ix = 0; ix < fieldsUA.size(); ix++) {
		        		Fields column = fieldsUA.get(ix);
		        		Integer domCount = repService.getCountOfColGroupByMonth(catVal, column.getColumnName(), dateFrom, dateTo, "", perpVal, String.valueOf(i), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
		        		if(domCount > 0) {
		        			totalCount = totalCount + domCount;
		        		}
		    		}
		    		
		    		list4b.add(totalCount + 0);
		    		totalCount = 0;
				}    		

	    		if (i >= startMonthLoop && i <= endMonthLoop) { // Apply date filter
	    			for (int ix = 0; ix < fieldsCO.size(); ix++) {
		        		Fields column = fieldsCO.get(ix);
		        		Integer domCount = repService.getCountOfColGroupByMonth(catVal, column.getColumnName(), dateFrom, dateTo, "", perpVal, String.valueOf(i), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
		        		if(domCount > 0) {
		        			totalCount = totalCount + domCount;
		        		}
	    			}
	    			
		    		list4c.add(totalCount + 0);
		    		totalCount = 0;
	    		}
			}
			rB.setyAxisCategory(yAxisCategory4);
	    }
//			model.addAttribute(list4a);
	    	bars4.put("Armed", list4a);
	    	bars4.put("Unarmed", list4b);
	    	bars4.put("Government Counteraction Operations", list4c);
			rB.setJscript1(grph.createGenGraph(chartTypes, "container1", "", subtitle, rB.getyAxisCategory(), rLabel , lLabel, bars4));
			
		String jscript = rB.getJscript1() + rB.getJscript2() + rB.getJscript3() + rB.getJscript4();
    	model.addAttribute("javascript", jscript);
    	model.addAttribute("graphStyle", defaultStyle);
    	model.addAttribute("reportTitle",reportTitle);
    	model.addAttribute("dateType", dateType);
    	model.addAttribute("dateFrom",dateFrom);
		model.addAttribute("dateTo",dateTo);
		model.addAttribute("weekFrom",weekFrom);
		model.addAttribute("weekTo",weekTo);
		model.addAttribute("monthFrom",monthFrom);
		model.addAttribute("perpVal",perpVal);
		model.addAttribute("perpList", fileService.getMenu("SM", "PR"));
		
//    	//Get General Reports Box 
    	String genrepBox = repService.getNextGenRep();
    	model.addAttribute("genrepBox", genrepBox);
        return "dashboardMain";
	}
    
    @RequestMapping(value = "/dashboardMain", method = RequestMethod.POST)
    public String setNewDashboard(@ModelAttribute("user") User user,
    		@ModelAttribute("dashAttribute") DashBoardBean dbb,    
    		@RequestParam(value="perpVal", required=true) String perpVal,
    		@RequestParam(value="settingsDash", required=false) String settingsDash,
    		Model model) {

    	ReportBean rB = new ReportBean();
    	logger.debug("DASHBOARD:***==Received request to show Dashboard Initialpage");
    	if (user.getOnDashboard() == null) {
    		logger.debug("ACCESS getOnDashboard: "+user.getOnDashboard());
    		return "/error_page_access";
    	}
    	else if (user.getOnDashboard() == false) {
        	logger.debug("ACCESS getOnDashboard: "+user.getOnDashboard());
        	return "/error_page_access";
        }
    	
    	DateFilter df = new DateFilter();
    	Date date = new Date();
    	
    	String dateFrom = df.getNoOfPreviousMonth(date, 3);
    	String dateTo = df.getCurrentDateToString(date);
    	String weekFrom = df.getNoOfPreviousDays(date, 7);
    	String weekTo = df.getCurrentDateToString(date);
    	String monthFrom = df.getNoOfPreviousMonth(date, 1);
    	
    	String actVal = "ARMED";
    	String catVal = "";
    	
    	if (user.getDefaultDashboard() == null || user.getDefaultDashboard() == "") {
    		catVal = "Domestic";
    		user.setDefaultDashboard("Domestic");
    	}
    	else {
    		catVal = user.getDefaultDashboard();
    		model.addAttribute("user",user);
    	}
    	model.addAttribute("user",user);
    		
    	if (settingsDash.equals("Domestic")) {
    		perpVal = (perpVal == null) ? "---all---" : perpVal;
    	} else {
    		perpVal = "---all---";
    	}
    	
    	String typRepVal = "---all---";
    	String locListVal = "---all---";
    	String locListSubVal1 = "---all---";
    	String locListSubVal2 = "---all---";
    	String locListSubVal3 = "---all---";
    	String reportTitle = "";
    	
    	GraphControllers grph = new GraphControllers();
    	List<Integer> list15 = new ArrayList<Integer>();
    	List<String> yAxisCategory0 = new ArrayList<String>();
    	List<String> yAxisCategory1 = new ArrayList<String>();
    	List<String> yAxisCategory2 = new ArrayList<String>();
    	List<String> yAxisCategory3 = new ArrayList<String>();
    	String chartTypes = "";
    			
		String subtitle = "";
		String rLabel = "", lLabel = "";
		String dateType = "G.dateOfIncident";//G.dateOfIncident Or R.dateReport
    	//Used for scenario 1 only
	    	chartTypes = "pieCharts";
	    	bars0.clear();
	    	Integer totalCount = 0;
	    	List<Fields> fields = new Vector<Fields>();
	    	String months[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	    	
	    	if(settingsDash.equals("Maritime")) {
	    		fields = fieldService.getPerActCode("MA");
			} else {
				fields = fieldService.getPerActCode("UA");
			}

	    	if (settingsDash.equalsIgnoreCase("Domestic")) {
		    	for (int i = 0; i < fields.size(); i++) {
	        		Fields column = fields.get(i);
	        		Integer domCount = 0;
	        		
	        		domCount = repService.getCountOfCol(settingsDash, column.getColumnName(), monthFrom, dateTo, typRepVal, perpVal, locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
	
	        		if(domCount > 0) {
	        			List<Integer> n = new ArrayList<Integer>();
	    				n.add(domCount);
	    				bars0.put(column.getColumnDesc(), n);
	        		}
	    		}
	    	} else {
		    	int startMonthLoop = Integer.parseInt(dateFrom.substring(5, 7));
		    	int endMonthLoop = Integer.parseInt(dateTo.substring(5, 7));
    			for (int e = 1; e < 13; e++) {
    				if (e >= startMonthLoop && e <= endMonthLoop) {
    					Integer domCount = 0;
	    	    		for (int i = 0; i < fields.size(); i++) {
	    	    			Fields column = fields.get(i);

	    	    			domCount = repService.getCountOfColGroupByMonth(settingsDash, column.getColumnName(), dateFrom, dateTo, "", perpVal, String.valueOf(e), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);

			        		if(domCount > 0) {
			        			totalCount = totalCount + domCount;
			        		}
	    	    		}
	    	    		
	    	    	if (totalCount > 0) {
	        			List<Integer> n = new ArrayList<Integer>();
	    				n.add(totalCount);
	    				bars0.put(months[e-1], n);
	    	    	}
    				}
	    		}
	    	}
		    rB.setyAxisCategory(yAxisCategory0);		    
	    	rB.setJscript4(grph.createGenGraph(chartTypes, "container4", reportTitle, subtitle, rB.getyAxisCategory(), rLabel , lLabel, bars0));
	    	
	    	chartTypes = "pieCharts";
	    	bars0.clear();
	    	totalCount = 0;
	    	fields = new Vector<Fields>();
	    	
	    	if(settingsDash.equals("Maritime")) {
	    		fields = fieldService.getPerActCode("MA");
			} else {
				fields = fieldService.getPerActCode("AA");
			}
	    	
	    	for (int i = 0; i < fields.size(); i++) {
        		Fields column = fields.get(i);
        		Integer domCount = 0;
        		
        		if (settingsDash.equalsIgnoreCase("Domestic")) {
        			domCount = repService.getCountOfCol(settingsDash, column.getColumnName(), monthFrom, dateTo, typRepVal, perpVal, locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
        		} else {
        			domCount = repService.getCountOfCol(settingsDash, column.getColumnName(), weekFrom, dateTo, typRepVal, perpVal, locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
        		}
        		
        		if(domCount > 0) {
        			List<Integer> n = new ArrayList<Integer>();
    				//n.add(0);
    				n.add(domCount);
    				bars0.put(column.getColumnDesc(), n);
//    				n.clear();
        		}
    		}
		    rB.setyAxisCategory(yAxisCategory0);		    
	    	rB.setJscript3(grph.createGenGraph(chartTypes, "container3", reportTitle, subtitle, rB.getyAxisCategory(), rLabel , lLabel, bars0));
	    	
			chartTypes = "columnCharts2";	
			bars1.clear();
	    	List<Placeslocal> localRegfields = new Vector<Placeslocal>();
	    	fields = new Vector<Fields>();
	    	yAxisCategory1.clear();
	    	//list1.clear();
	    	totalCount = 0; 
			
	    	if(settingsDash.equals("Maritime")){
	    		fields = fieldService.getPerActCode("MA");
			} else {
				fields = fieldService.getPerActCode("AA");
			}
	    	localRegfields.clear();
			localRegfields = placesLocalService.getPlacesByGroup("Isl");
				Placeslocal others = new Placeslocal();
				others.setPlace("unknown");		
				localRegfields.add(others);
				
		    	for (int i = 0; i < localRegfields.size(); i++) {
		    		Placeslocal regions = localRegfields.get(i);
		    		if (regions.getPlace().equals("unknown")) {
		    			yAxisCategory1.add("'Others'");
		    		}
		    		else
		    			yAxisCategory1.add("'"+regions.getPlace()+"'");
			    		
			    		for (int ix = 0; ix < fields.size(); ix++) {
			        		Fields column = fields.get(ix);
			        		Integer domCount = repService.getCountOfColGroupByRegions(settingsDash, column.getColumnName(), monthFrom, dateTo, "", perpVal, regions.getPlace(), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
			        		if(domCount > 0) {
			        			totalCount = totalCount + domCount;
			        		}			    		
			    		
						}
			    		list15.add(totalCount + 0);
			    		totalCount = 0; 
				}
		    	rB.setyAxisCategory(yAxisCategory1);
			
			bars1.put("activities", list15);
			rB.setJscript2(grph.createGenGraph(chartTypes, "container2", reportTitle, subtitle, rB.getyAxisCategory(), rLabel , lLabel, bars1));
			
			chartTypes = "columnCharts1";
			List<String> yAxisCategory4 = new ArrayList<String>();
			Map<String, List<Integer>> bars4 = new HashMap<String, List<Integer>>();
			List<Fields> fieldsAA = new Vector<Fields>();
			List<Fields> fieldsUA = new Vector<Fields>();	 
			List<Fields> fieldsCO = new Vector<Fields>();
			List<Fields> fieldsMV = new Vector<Fields>();
			
	    	totalCount = 0;
	    	list4a.clear();
	    	list4b.clear();
	    	list4c.clear();
	    	list4d.clear();
	    	bars4.clear();
	    	
	    	yAxisCategory4.clear();
	    	
	    if (settingsDash.equalsIgnoreCase("Domestic")) {
	    	fieldsAA = fieldService.getPerActCode("AA");
	    	fieldsUA = fieldService.getPerActCode("UA");
	    	fieldsCO = fieldService.getPerActCode("CO");
	    	int startMonthLoop = Integer.parseInt(dateFrom.substring(5, 7));
	    	int endMonthLoop = Integer.parseInt(dateTo.substring(5, 7));
			for (int i = 1; i < 13; i++) {
				if (i >= startMonthLoop && i <= endMonthLoop) {
					yAxisCategory4.add(months[i-1]);
				}
				
				if (i >= startMonthLoop && i <= endMonthLoop) { // Apply date filter
					for (int ix = 0; ix < fieldsAA.size(); ix++) {
		        		Fields column = fieldsAA.get(ix);
		        		Integer domCount = repService.getCountOfColGroupByMonth(settingsDash, column.getColumnName(), dateFrom, dateTo, "", perpVal, String.valueOf(i), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
		        		if(domCount > 0) {
		        			totalCount = totalCount + domCount;
		        		}
					}
					
					list4a.add(totalCount + 0);
		    		totalCount = 0;
				}
	    		if (i >= startMonthLoop && i <= endMonthLoop) { // Apply date filter
		    		for (int ix = 0; ix < fieldsUA.size(); ix++) {
		        		Fields column = fieldsUA.get(ix);
		        		Integer domCount = repService.getCountOfColGroupByMonth(settingsDash, column.getColumnName(), dateFrom, dateTo, "", perpVal, String.valueOf(i), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
		        		if(domCount > 0) {
		        			totalCount = totalCount + domCount;
		        		}
		    		}
		    		
		    		list4b.add(totalCount + 0);
		    		totalCount = 0;
				}    		

	    		if (i >= startMonthLoop && i <= endMonthLoop) { // Apply date filter
	    			for (int ix = 0; ix < fieldsCO.size(); ix++) {
		        		Fields column = fieldsCO.get(ix);
		        		Integer domCount = repService.getCountOfColGroupByMonth(settingsDash, column.getColumnName(), dateFrom, dateTo, "", perpVal, String.valueOf(i), locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
		        		if(domCount > 0) {
		        			totalCount = totalCount + domCount;
		        		}
	    			}
	    			
		    		list4c.add(totalCount + 0);
		    		totalCount = 0;
	    		}
			}
			
	    	bars4.put("Armed", list4a);
	    	bars4.put("Unarmed", list4b);
	    	bars4.put("Government Counteraction Operations", list4c);
	    	
		    rB.setyAxisCategory(yAxisCategory4);
		    rB.setJscript1(grph.createGenGraph(chartTypes, "container1", "", subtitle, rB.getyAxisCategory(), rLabel , lLabel, bars4));
	    } else {
	    	yAxisCategory4.clear();
	    	list4d.clear();
	    	fieldsMV = fieldService.getPerActCode("MA");
	    	
	    	for (int i = 0; i < fieldsMV.size(); i++) {
	    		Fields field = fieldsMV.get(i);
	    		yAxisCategory4.add("");
	    		
	    		List<Integer> list4d = new ArrayList<Integer>();

        		Integer domCount = repService.getCountOfCol(settingsDash, field.getColumnName(), monthFrom, dateTo, typRepVal, perpVal, locListVal, locListSubVal1, locListSubVal2, locListSubVal3, user.getAccessLevel(), user.getReportingUnit(), dateType);
        		
	    		list4d.add(domCount);
	    		bars4.put(field.getColumnDesc(), list4d); //bar colors
	    	}	
    		
		    rB.setyAxisCategory(yAxisCategory4);
		    rB.setJscript1(grph.createGenGraph(chartTypes, "container1", "", subtitle, rB.getyAxisCategory(), rLabel , lLabel, bars4));
	    }
	    
		String jscript = rB.getJscript1() + rB.getJscript2() + rB.getJscript3() + rB.getJscript4();
    	model.addAttribute("javascript", jscript);
    	model.addAttribute("graphStyle", defaultStyle);
    	model.addAttribute("reportTitle",reportTitle);
    	model.addAttribute("dateType", dateType);
    	model.addAttribute("dateFrom",dateFrom);
		model.addAttribute("dateTo",dateTo);
		model.addAttribute("weekFrom",weekFrom);
		model.addAttribute("weekTo",weekTo);
		model.addAttribute("monthFrom",monthFrom);
		model.addAttribute("perpVal",perpVal);
		model.addAttribute("settingsDash",settingsDash);
		model.addAttribute("perpList", fileService.getMenu("SM", "PR"));
		
//    	//Get General Reports Box 
    	String genrepBox = repService.getNextGenRep();
    	model.addAttribute("genrepBox", genrepBox);
        return "dashboardMain";
	}
    
    @RequestMapping(value = "/dashboardSearch", method = RequestMethod.GET)
    public String showDashboardSearch(Model model) {
    	logger.debug("Received request to show Dashboard Searching page");    	
        return "dashboardSearch";

	}
    	
    
}
