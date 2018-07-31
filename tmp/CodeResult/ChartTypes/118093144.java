package org.nrg.xnat.restlet.extensions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.store.AbstractPolicy;

import org.apache.commons.math.stat.StatUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.nrg.xdat.XDAT;
import org.nrg.xdat.om.XdatSearchFieldI;
import org.nrg.xdat.om.XnatProjectdata;
import org.nrg.xdat.search.DisplaySearch;
import org.nrg.xdat.om.XdatStoredSearch;
import org.nrg.xft.XFTTableI;
import org.nrg.xnat.restlet.XnatRestlet;
import org.nrg.xnat.restlet.resources.SecureResource;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.StringRepresentation;
import org.restlet.resource.Variant;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Restlet used to filter and group cached search results.
 */
@XnatRestlet({"/projects/{PROJECT_ID}/qcchartdata","/projects/{PROJECT_ID}/qcchartconfig"})
public class QcChartRestlet  extends SecureResource { 

	static org.apache.log4j.Logger logger = Logger.getLogger(QcChartRestlet.class);
	
	private final XnatProjectdata proj;
	private static final String configTool = "qc-chart";
	private static final String configSearchPath = "searches";
	private static final String configInfoFieldPath = "info-fields";
	private static final String configInfoByField = "by-field";
	private static final String configInitialDisplayFieldPath = "initial-display-fields";
	
	private final boolean keepNumericDataOnly;
	private final boolean clearCache;
	
	// Currently only IR Charts supported, but likely to add more
	public enum ChartTypes {  
			IRCHART
	}
	private static final ChartTypes DEFAULT_CHART = ChartTypes.IRCHART;
	private final ChartTypes chartType;
	
	private static final int DEFAULT_INITIAL_BASELINE_NOBS = 20;
	private static final int DEFAULT_BASELINE_NOBS = 100;
	private int baselineNobs;
	private int baselineInitialNobs;
	
	private int displayNobs;
	
	public enum BaselinePeriods {  
		SeriesBeginning,SeriesEnd,Variable,VariableAll
	}
	private static final BaselinePeriods DEFAULT_BASELINE_PERIOD = BaselinePeriods.SeriesEnd;
	private final BaselinePeriods baselinePeriod;
    private final Long projDbID;
    private final List<String> infoFields;
    private final List<String> initialDisplayFields;
    private final String byField;
	private final String noByFieldString = "NOBYVAR";
    private final Map<XdatStoredSearch,List<Object>> searchByValues = Maps.newLinkedHashMap();
	
	private static final CacheManager cm = CacheManager.getInstance();
	private static Cache cache;
	private static final String cacheName = "SEARCH_CACHE";
	
	static {
		try {
		final CacheConfiguration cacheConfig = new CacheConfiguration(cacheName,0).copyOnRead(false).copyOnWrite(false);
		cacheConfig.setTimeToLiveSeconds(Long.valueOf(60*60*24));
		cacheConfig.setTimeToIdleSeconds(Long.valueOf(60*60*24));
		cacheConfig.setMaxEntriesLocalHeap(0);
		cacheConfig.setMaxBytesLocalHeap("192M");
		// NOTE:  Disk fail over didn't seem to be working well.  Not allowing as much into cache.  
		//cacheConfig.setMaxBytesLocalDisk(Long.valueOf(512*1024*1024));
		cacheConfig.persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.NONE));
		cache = new Cache(cacheConfig);
		cm.addCache(cache);
		cache.setMemoryStoreEvictionPolicy(new QcEvictionPolicy());
		} catch (Exception e) {
			cache = null;
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private class OrganizedSearchResults {
		public Map<String,Map<Object,ChartElements>> charts;
		public Map<String,Map<Object, List<String>>> SessionInfoFields;
		public List<String> initialDisplayFields;
	}
	
	private class CachedSearchResults {
		public Date date;
		public XdatStoredSearch ssx;
		public XFTTableI t;
		public Map<String, Map<Object,ChartElements>> columnMap;
		public CachedSearchResults(Date date,XdatStoredSearch ssx,XFTTableI t,Map<String, Map<Object,ChartElements>> columnMap) {
			this.date = date;
			this.ssx = ssx;
			this.t = t;
			this.columnMap = columnMap;
		}
	}
	
	private class CachedChartJSON implements Serializable {
		public Date date;
		public String chartJSON;
		public CachedChartJSON(Date date,String chartJSON) {
			this.date = date;
			this.chartJSON = chartJSON;
		}
	}
	
	@SuppressWarnings("unused")
	private class ChartElements {
		public List<Double> RawData;
		public List<Double> CL;
		public List<Double> USD1;
		public List<Double> USD2;
		public List<Double> UCL;
		public List<Double> LSD1;
		public List<Double> LSD2;
		public List<Double> LCL;
		public List<Double> MR;
		public List<Double> MR_CL;
		public List<Double> MR_USD1;
		public List<Double> MR_USD2;
		public List<Double> MR_UCL;
		public List<Double> MR_LCL;
		public List<String> RunsRules;
		public String ByValue;
		public String ChartTitle;
	}
	
	public static class QcEvictionPolicy extends AbstractPolicy {
		
		public static final String POLICY_NAME = "QCEP";

		@Override
		public String getName() {
			return POLICY_NAME;
		}

		@Override
		public boolean compare(Element element1, Element element2) {
			// Give priority to cached JSON results because they are smaller.
			final long element1Count = element1.getHitCount() + ((element1.getObjectValue() instanceof CachedChartJSON) ? 30 : 0);
			final long element2Count = element2.getHitCount() + ((element2.getObjectValue() instanceof CachedChartJSON) ? 30 : 0);
			return element2Count < element1Count;
		}
		
	}

	public QcChartRestlet(Context context, Request request, Response response) {
		super(context, request, response);
		
		//cache.setMemoryStoreEvictionPolicy(new QcEvictionPolicy());
		
		final String projID = (String)getParameter(request,"PROJECT_ID");
		proj = (projID!=null) ? XnatProjectdata.getProjectByIDorAlias(projID, user, false) : null;
		if (proj == null) {
			response.setStatus(Status.CLIENT_ERROR_NOT_FOUND,"Specified project either does not exist or user is not permitted access to subject data");
		}
		this.getVariants().add(new Variant(MediaType.APPLICATION_JSON));
		this.getVariants().add(new Variant(MediaType.TEXT_PLAIN));
		
		// CHART PARAMETERS
		final String keepNumericStr = getQueryVariable("keepNumericDataOnly");
		keepNumericDataOnly = (keepNumericStr != null) ? !keepNumericStr.equalsIgnoreCase("false") : true; 
		
		final String clearCacheStr = getQueryVariable("clearCache");
		clearCache = (clearCacheStr != null) ? clearCacheStr.equalsIgnoreCase("true") : false; 
		
		final String chartTypeStr = getQueryVariable("chartType");
		// Currently only supporting IR Charts, but will likely add more
		if (chartTypeStr == null) {
			chartType=DEFAULT_CHART;
		} else if (chartTypeStr.equalsIgnoreCase("IRCHART")) {
			chartType=ChartTypes.IRCHART;
		} else {
			chartType=DEFAULT_CHART;
		}
		
		final String baselineNobsStr = getQueryVariable("baselineNobs");
		try {
			baselineNobs=(baselineNobsStr!=null) ? Integer.valueOf(baselineNobsStr) : DEFAULT_BASELINE_NOBS;
		} catch (NumberFormatException e) {
			baselineNobs=DEFAULT_BASELINE_NOBS;
		}
		
		final String baselineInitialNobsStr = getQueryVariable("baselineInitialNobs");
		try {
			baselineInitialNobs=(baselineInitialNobsStr!=null) ? Integer.valueOf(baselineInitialNobsStr) : DEFAULT_INITIAL_BASELINE_NOBS;
		} catch (NumberFormatException e) {
			baselineInitialNobs=DEFAULT_INITIAL_BASELINE_NOBS;
		}
		
		final String baselinePeriodStr = getQueryVariable("baselinePeriod");
		if (baselinePeriodStr == null) {
			baselinePeriod = DEFAULT_BASELINE_PERIOD;
		} else if (baselinePeriodStr.toLowerCase().contains("begin")) {
			baselinePeriod = BaselinePeriods.SeriesBeginning;
		} else if (baselinePeriodStr.toLowerCase().contains("variableall")) {
			baselinePeriod = BaselinePeriods.VariableAll;
		} else if (baselinePeriodStr.toLowerCase().contains("variable")) {
			baselinePeriod = BaselinePeriods.Variable;
		} else {
			baselinePeriod = BaselinePeriods.SeriesEnd;
		}
		
		final String displayNobsStr = getQueryVariable("displayNobs");
		displayNobs = (displayNobsStr == null || !isNumeric(displayNobsStr)) ? Integer.MAX_VALUE : Integer.parseInt(displayNobsStr);
		
        projDbID = (long)(Integer)proj.getItem().getProps().get("projectdata_info");
        
		final String chartInfoFieldsStr = XDAT.getConfigService().getConfigContents(configTool,configInfoFieldPath,projDbID);
		final String[] chartInfoFieldsA = (chartInfoFieldsStr != null && chartInfoFieldsStr.length()>0) ?
				chartInfoFieldsStr.replaceAll("[\n\\\\].*$","").split(",") : null;
		infoFields = (chartInfoFieldsA != null && chartInfoFieldsA.length>0) ? Arrays.asList(chartInfoFieldsA) : new ArrayList<String>();
        
		final String byFieldStr = XDAT.getConfigService().getConfigContents(configTool,configInfoByField,projDbID);
		byField = (byFieldStr!=null) ? byFieldStr.replaceAll("[\n\\\\].*$","") : null;
		
		final String initialDisplayFieldsStr = XDAT.getConfigService().getConfigContents(configTool,configInitialDisplayFieldPath,projDbID);
		final String[] initialDisplayFieldsA = (initialDisplayFieldsStr != null && initialDisplayFieldsStr.length()>0) ?
				initialDisplayFieldsStr.replaceAll("[\n\\\\].*$","").split(",") : null;
		initialDisplayFields = (initialDisplayFieldsA != null && initialDisplayFieldsA.length>0) ?
				Arrays.asList(initialDisplayFieldsA) : new ArrayList<String>();
		
	}
	
	@Override
	public void handleGet() {
		try {
			if (!proj.canRead(user)) {
				getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
				return;
			}
		} catch (Exception e) {
			getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
			logger.error("",e);
			return;
		}
		if (getRequest().getResourceRef().getSegments().contains("qcchartdata")) {
			returnChartData();
		} else if (getRequest().getResourceRef().getSegments().contains("qcchartconfig")) {
			returnChartConfig();
		}
	}

	private void returnChartData() {
		final String resourceRef = getRequest().getResourceRef().getPath() + "?" + getRequest().getResourceRef().getQuery().replaceAll("&_=.*$","");
		try {
			if (!clearCache && cache.isKeyInCache(resourceRef)) {
				final CachedChartJSON cachedChartJSON = (CachedChartJSON)cache.get(resourceRef).getObjectValue();
				if (cachedChartJSON.date !=null && ((new Date().getTime()-cachedChartJSON.date.getTime())<(Long.valueOf(24*60*60*1000))) &&
						cachedChartJSON.chartJSON != null) {
					getResponse().setEntity(new StringRepresentation(cachedChartJSON.chartJSON));
					getResponse().setStatus(Status.SUCCESS_OK);
					return;
				}
			} else if (clearCache && cache.isKeyInCache(resourceRef)) {
				cache.remove(resourceRef);
			}
		} catch (Exception e) {
			// Do nothing
		}
		final String chartConfig = XDAT.getConfigService().getConfigContents(configTool,configSearchPath,projDbID);
		final String[] chartSearchA = (chartConfig != null && chartConfig.length()>0) ? chartConfig.split(",") : null;
		final Map<String,OrganizedSearchResults> outMap = Maps.newLinkedHashMap();
		if (chartSearchA != null) {
			for (String ss : Arrays.asList(chartSearchA)) {
				ss = ss.replaceAll("[\n\\\\].*$","");
				final OrganizedSearchResults osr = new OrganizedSearchResults();
				osr.initialDisplayFields = initialDisplayFields;
				if (!clearCache && cache.isKeyInCache(ss)) {
					try {
						final CachedSearchResults cachedSearchResults = (CachedSearchResults)cache.get(ss).getObjectValue();
						if (cachedSearchResults.date !=null && ((new Date().getTime()-cachedSearchResults.date.getTime())<(Long.valueOf(24*60*60*1000))) &&
								cachedSearchResults.t != null) {
							osr.charts = cachedSearchResults.columnMap;
							populateChartInfo(osr.charts,cachedSearchResults.ssx);
							computeChartData(osr.charts);
							// Note:  This call should come after the computeChartData method call.  Needs NOBS information
							osr.SessionInfoFields = getSessionInfoFields(cachedSearchResults.t,cachedSearchResults.ssx,osr.charts);
							outMap.put(ss,osr);
							continue;
						}
					} catch (Exception e) {
						// Do nothing
					}
				} else if (clearCache && cache.isKeyInCache(ss)) {
					cache.remove(ss);
				} 
				final XdatStoredSearch ssx = (ss.startsWith("@")) ? proj.getDefaultSearch(ss.substring(1)) : XdatStoredSearch.getXdatStoredSearchsById(ss,  user,  true); 
				if (ssx != null) {
					if (!user.canQuery(ssx.getRootElementName())) {
						getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
						return;
					}
					try {
						final DisplaySearch ds = ssx.getDisplaySearch(user);
						final XFTTableI t = ds.execute(user.getLogin());
						if (t != null) {
							osr.charts = getColumnMapFromTable(t,ssx);
							populateChartInfo(osr.charts,ssx);
							// Only cache reasonably sized results.
							if (t.getNumRows()<1000) {
								cacheSearchResults(ss, ssx, t, osr.charts);
							}
							computeChartData(osr.charts);
							// Note:  This call should come after the computeChartData method call.  Needs NOBS information
							osr.SessionInfoFields = getSessionInfoFields(t,ssx,osr.charts);
							outMap.put(ss,osr);
						} else {
							getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
							logger.error("Unexpectedly returned a null table (CLASS=" + this.getClass().getName() + ")");
							return;
						}
					} catch (Exception e) {
						getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
						logger.error("",e);
						return;
					}
				}
			}
		}
		final Gson gson = new GsonBuilder().create();
		final String gsonResults = (outMap.keySet().size()>=1) ? gson.toJson(outMap) : "";
		getResponse().setEntity(new StringRepresentation(gsonResults));
		getResponse().setStatus(Status.SUCCESS_OK);
		if (!cache.isKeyInCache(resourceRef)) {
			//putToCache(cache,new Element(resourceRef,new CachedChartJSON(new Date(),gsonResults)));
			cache.put(new Element(resourceRef,new CachedChartJSON(new Date(),gsonResults)));
		}
	}
	
	private synchronized void cacheSearchResults(String ss, XdatStoredSearch ssx, XFTTableI t, Map<String, Map<Object,ChartElements>> columnMap) {
		final CachedSearchResults newCache = new CachedSearchResults(new Date(), ssx, t,columnMap);
		//putToCache(cache,new Element(ss,newCache));
		cache.put(new Element(ss,newCache));
	}

	private Map<String, Map<Object,List<String>>> getSessionInfoFields(XFTTableI t, XdatStoredSearch ssx, Map<String, Map<Object,ChartElements>> charts) {
		t.resetRowCursor();
		final List<String> columnList = Lists.newArrayList(Arrays.asList(t.getColumns()));
		final Map<String, Map<Object,List<String>>> columnMap = Maps.newLinkedHashMap();
		final Iterator<String> it = columnList.iterator();
		while (it.hasNext()) {
			if (!infoFields.contains(it.next())) {
				it.remove();
			}
		}
		final Map<String,String> headerMap = Maps.newLinkedHashMap();
		for (final XdatSearchFieldI sf : ssx.getSearchField()) {
			headerMap.put(getTableColumnFromSearchField(ssx,sf),sf.getHeader());
		}
		for (final String column : columnList) {
			final Map<Object,List<String>> newMap = Maps.newLinkedHashMap();
			for (final Object byValue : searchByValues.get(ssx)) {
				final List<String> newList = Lists.newArrayList();
				newMap.put(byValue, newList);
			}
			columnMap.put((headerMap.containsKey(column)) ? headerMap.get(column) : column, newMap);
		}
		while (t.hasMoreRows()) {
			t.nextRow();
			for (final String column : columnList) {
				if (!columnMap.containsKey((headerMap.containsKey(column) ? headerMap.get(column) : column))) {
					continue;
				}
				final Object cellValue = t.getCellValue(column);
				final Object byValue = (byField == null || byField.length()<1 || !Arrays.asList(t.getColumns()).contains(byField)) ?
						noByFieldString : t.getCellValue(byField);
				if ( byValue == null) {
					continue;
				}
				if (cellValue == null) {
					columnMap.get((headerMap.containsKey(column)) ? headerMap.get(column) : column).get(byValue).add(null);
				} else {
					columnMap.get((headerMap.containsKey(column)) ? headerMap.get(column) : column).get(byValue).add(t.getCellValue(column).toString());
				}
			}
		}
		// trim lists back to display size
		//int displayNobs = getDisplayNobsFromChartData(charts);
		//if (displayNobs>=0) {
			// NOTE:  For now, we're assuming that all fields should have the same number of records (that should hold)
			for (final String column : columnMap.keySet()) {
				for (final Object byValue : searchByValues.get(ssx)) {
					final List<String> columnValues = columnMap.get(column).get(byValue);
					final int currentSize = columnValues.size();
					if (charts.keySet().size()<1) continue;
					final Map<Object, ChartElements> chartValues = charts.get((charts.keySet().toArray())[0]);
					if (chartValues == null)  continue;
					final ChartElements chartByValue = chartValues.get(byValue);
					if (chartByValue == null) continue;
					for (int i=0; i<(currentSize-chartByValue.RawData.size()); i++) {
						columnValues.remove(0);
					}
				}
			}
			return columnMap;
		//}
		//return null;
	}

	private String getTableColumnFromSearchField(XdatStoredSearch ssx, XdatSearchFieldI sf) {
		return (sf.getElementName().equals(ssx.getRootElementName())) ? sf.getFieldId().replaceAll("[:=]","_").toLowerCase() :
			sf.getElementName().replaceAll("[:=]","_").toLowerCase() + "_" + sf.getFieldId().replaceAll("[:=]","_").toLowerCase();
	}

	/*
	private int getDisplayNobsFromChartData(Map<String, Map<Object,ChartElements>> charts) {
		// Just verify that all charts have the same number of observations.
		int returnv = -9;
		for (final String chart : charts.keySet()) {
			for (final Object chartby : charts.get(chart).keySet()) {
				int thisSize = charts.get(chart).get(chartby).RawData.size();
				if (returnv>=0 && thisSize!=returnv) {
					return -9;
				} else {
					returnv = thisSize;
				}
			}
		}
		return returnv;
	}
	*/

	private void populateChartInfo(Map<String, Map<Object,ChartElements>> map, XdatStoredSearch ssx) {
		// Note:  need to do case conversion on the header map right now.  The ChartElements object currently seems to have 
		// all field id's converted  to lower case, but we'll convert there too, in case that changes.
		final Map<String,String> headerMap = Maps.newLinkedHashMap();
		for (final XdatSearchFieldI sf : ssx.getSearchField()) {
			headerMap.put(getTableColumnFromSearchField(ssx,sf),sf.getHeader());
		}
		for (final String chartIt : map.keySet()) {
			for (final Object chartBy : map.get(chartIt).keySet()) {
				final ChartElements cEles = map.get(chartIt).get(chartBy);
				if (cEles != null) {
					cEles.ChartTitle = ((headerMap.containsKey(chartIt.toLowerCase())) ? headerMap.get(chartIt.toLowerCase()) : chartIt) +
							((! chartBy.equals(noByFieldString)) ? " (" + chartBy + ")" : "");
				}
			}
		}
	}

	private void computeChartData(Map<String, Map<Object,ChartElements>> searchMap) {
		for (final String ssit : searchMap.keySet()) {
			computeChartDataForItem(searchMap.get(ssit));
		}
	}

	private void computeChartDataForItem(Map<Object,ChartElements> cElesMap) {
		if (chartType == ChartTypes.IRCHART) {
			computeIrChartData(cElesMap);
		}
	}

	
	private void computeIrChartData(Map<Object,ChartElements> cElesMap) {
		for (final Object byValue : cElesMap.keySet()) {
			final ChartElements cEles = cElesMap.get(byValue);
			final int calcBaselineNobs = getBaselineNobs(cEles);
			final List<Double> dataList = Lists.newArrayList(cEles.RawData);
			// Step 1:  Compute Moving Range
			cEles.MR =  Lists.newArrayListWithCapacity(dataList.size());
			if (dataList.size()<2) {
				if (dataList.size()==1) { 
					cEles.MR.add(0,null);
				} 
				return;
			}
			// Initial MR entry will be null
			cEles.MR.add(0,null);
			// Intentionally starting at i=1
			for (int i=1; i<dataList.size(); i++) {
				if (dataList.get(i)==null || dataList.get(i-1)==null) {
					cEles.MR.add(i,null);
				} else if (isNumeric(dataList.get(i)) && isNumeric(dataList.get(i-1))) {
					cEles.MR.add(i,getAbsoluteDifference(dataList.get(i),dataList.get(i-1)));
				} else {
					cEles.MR.add(i,null);
				}
			}
			// Step 2:  Compute Data Mean
			final List<Double> baselineDataList = Lists.newArrayListWithCapacity(calcBaselineNobs);
			int startpoint=Integer.MAX_VALUE;
			if (baselinePeriod == BaselinePeriods.SeriesBeginning || baselinePeriod == BaselinePeriods.Variable ||
					baselinePeriod == BaselinePeriods.VariableAll) {
				for (int i=0; i<dataList.size(); i++) {
					if (dataList.get(i) != null) {
						startpoint = i;
						break;
					}
				}
			} else if (baselinePeriod == BaselinePeriods.SeriesEnd) {
				for (int i=dataList.size()-1; i>0; i--) {
					if (dataList.get(i) != null) {
						startpoint = i-calcBaselineNobs;
						break;
					}
				}
			}
			if (startpoint==Integer.MAX_VALUE) {
				return;
			}
			for (int i=startpoint; i<(startpoint+calcBaselineNobs); i++) {
				if (dataList.get(i)!=null) {
					try {
						baselineDataList.add(Double.valueOf(dataList.get(i).toString()));
					} catch (NumberFormatException e) {
						// Do nothing
					}
				}
			}
			final double[] baselineDataArr = new double[baselineDataList.size()];
			for (int i=0; i<baselineDataList.size(); i++) {
				baselineDataArr[i] = baselineDataList.get(i);
			}
			final double dataMean = StatUtils.mean(baselineDataArr);
			// Step 3:  Compute MR Mean
			final List<Double> baselineMrList = Lists.newArrayListWithCapacity(calcBaselineNobs);
			for (int i=startpoint; i<(startpoint+calcBaselineNobs); i++) {
				if (cEles.MR.get(i)!=null) {
					try {
						baselineMrList.add(Double.valueOf(cEles.MR.get(i).toString()));
					} catch (NumberFormatException e) {
						// Do nothing
					}
				}
			}
			final double[] baselineMrArr = new double[baselineMrList.size()];
			for (int i=0; i<baselineMrArr.length; i++) {
				baselineMrArr[i] = baselineMrList.get(i);
			}
			final double mrMean = StatUtils.mean(baselineMrArr);
			
			cEles.CL = Lists.newArrayListWithCapacity(dataList.size());
			cEles.CL.addAll(Collections.nCopies(dataList.size(),(Double)null));
			cEles.USD1 = Lists.newArrayListWithCapacity(dataList.size());
			cEles.USD1.addAll(Collections.nCopies(dataList.size(),(Double)null));
			cEles.USD2 = Lists.newArrayListWithCapacity(dataList.size());
			cEles.USD2.addAll(Collections.nCopies(dataList.size(),(Double)null));
			cEles.UCL = Lists.newArrayListWithCapacity(dataList.size());
			cEles.UCL.addAll(Collections.nCopies(dataList.size(),(Double)null));
			cEles.LSD1 = Lists.newArrayListWithCapacity(dataList.size());
			cEles.LSD1.addAll(Collections.nCopies(dataList.size(),(Double)null));
			cEles.LSD2 = Lists.newArrayListWithCapacity(dataList.size());
			cEles.LSD2.addAll(Collections.nCopies(dataList.size(),(Double)null));
			cEles.LCL = Lists.newArrayListWithCapacity(dataList.size());
			cEles.LCL.addAll(Collections.nCopies(dataList.size(),(Double)null));
			cEles.MR_CL = Lists.newArrayListWithCapacity(dataList.size());
			cEles.MR_CL.addAll(Collections.nCopies(dataList.size(),(Double)null));
			cEles.MR_USD1 = Lists.newArrayListWithCapacity(dataList.size());
			cEles.MR_USD1.addAll(Collections.nCopies(dataList.size(),(Double)null));
			cEles.MR_USD2 = Lists.newArrayListWithCapacity(dataList.size());
			cEles.MR_USD2.addAll(Collections.nCopies(dataList.size(),(Double)null));
			cEles.MR_UCL = Lists.newArrayListWithCapacity(dataList.size());
			cEles.MR_UCL.addAll(Collections.nCopies(dataList.size(),(Double)null));
			cEles.MR_LCL = Lists.newArrayListWithCapacity(dataList.size());
			cEles.MR_LCL.addAll(Collections.nCopies(dataList.size(),(Double)null));
			cEles.RunsRules = Lists.newArrayListWithCapacity(dataList.size());
			cEles.RunsRules.addAll(Collections.nCopies(dataList.size(),""));
			
			for (int i=0; i<cEles.RawData.size(); i++) {
				cEles.CL.set(i,dataMean);
				cEles.MR_CL.set(i,mrMean);
			}
			
			calculateLimits(cEles,0);
			calculateRunsRules(cEles);
			
			// In case anything goes wrong and there's a violation at the end that can't be eliminated (shouldn't happen - would be a bug)
			int justInCase = 0;
			while ((baselinePeriod == BaselinePeriods.Variable || baselinePeriod == BaselinePeriods.VariableAll) &&
					justInCase < 9999 && shiftLimits(cEles)) {
				justInCase++;
				calculateRunsRules(cEles);
			}
			
			trimListsBackToDisplaySize(cEles);
		}
	}

	private boolean shiftLimits(ChartElements cEles) {
		Integer rangeStart = null;
		Integer rangeStop = null; 
		int obsCount=0;
		char ruleMatch = ' ';
		for (int i=0;i<cEles.RunsRules.size(); i++) {
			if (rangeStart==null && cEles.RunsRules.get(i).contains("D")) {
				rangeStart=i;
				obsCount=1;
				ruleMatch = 'D';
			} else if (baselinePeriod == BaselinePeriods.VariableAll && rangeStart==null && cEles.RunsRules.get(i).contains("C")) {
				rangeStart=i;
				obsCount=1;
				ruleMatch = 'C';
			} else if (baselinePeriod == BaselinePeriods.VariableAll && rangeStart==null && cEles.RunsRules.get(i).contains("B")) {
				rangeStart=i;
				obsCount=1;
				ruleMatch = 'B';
			} else if (rangeStart!=null && cEles.RawData.get(i)!=null) {
				obsCount++;
			}
			if (ruleMatch == 'D' && obsCount==9) {
				rangeStop=i;
				break;
			} else if (ruleMatch == 'C' && obsCount==5) {
				rangeStop=i;
				break;
			} else if (ruleMatch == 'B' && obsCount==3) {
				rangeStop=i;
				break;
			}
		}
		if (rangeStart==null || rangeStop==null) {
			return false;
		}
		final double[] dataArr = new double[rangeStop-rangeStart+1];
		final double[] mrArr = new double[rangeStop-rangeStart+1];
		for (int i=rangeStart;i<=rangeStop;i++) {
			if (cEles.RawData != null && cEles.RawData.get(i)!=null) {
				dataArr[i-rangeStart] = cEles.RawData.get(i);
			}
			if (cEles.MR != null && cEles.MR.get(i)!=null) {
				mrArr[i-rangeStart] = cEles.MR.get(i);
			}
		}
		final double dataMean = StatUtils.mean(dataArr);
		final double mrMean = StatUtils.mean(mrArr);
		for (int i=rangeStart;i<cEles.RawData.size();i++) {
			cEles.CL.set(i,dataMean);
			cEles.MR_CL.set(i,mrMean);
		}
		calculateLimits(cEles,rangeStart);
		return true;
	}

	/*
	private boolean needsShift(ChartElements cEles) {
		if (baselinePeriod == BaselinePeriods.Variable) {
			for (final String viol : cEles.RunsRules) {
				if (viol.contains("D")) {
					return true;
				}
			}
		} else if (baselinePeriod == BaselinePeriods.VariableAll) {
			for (final String viol : cEles.RunsRules) {
				if (viol != null && viol.length()>0) {
					return true;
				}
			}
		}
		return false;
	}
	*/

	private void calculateRunsRules(ChartElements cEles) {
			// Clear out any current runs rules
			for (int i=(cEles.RunsRules.size()-1); i>0; i--) {
				if (cEles.RunsRules.get(i)==null || !cEles.RunsRules.get(i).equals("")) {
					cEles.RunsRules.set(i,"");
				}
			}
			boolean returnvar = false;
			// Step ##:  Flag rules violations (Currently Western Electric Rules)
			//for (int i=(cEles.RawData.size()-1); i>0; i--) {
			for (int i=1;i<cEles.RawData.size(); i++) {
				if (cEles.RawData.get(i)==null) {
					continue;
				}
				boolean returnVar = false;
				int returnCount = 0;
				if (returnCount>0) {
					returnCount++;
				}
				// RULE A - One point outside confidence intervals
				if (cEles.RawData.get(i) > cEles.UCL.get(i) || cEles.RawData.get(i) < cEles.LCL.get(i)) {
					runsRuleAppend(cEles.RunsRules,i,1,'A');
				}
				// RULE B - 2 out of 3 in zone A or beyond (beyond 2-sigma)
				int aboveZoneA = 0;
				int belowZoneA = 0;
				int obsBackA = 0;
				int endVar = 3;
				for (int j=i; j>(i-endVar); j--) {
					if (j<1) {
						continue;
					} else if (cEles.RawData.get(j) == null || cEles.USD2.get(j) == null) {
						endVar++;
						continue;
					} else if (j<i && !cEles.CL.get(j).equals(cEles.CL.get(j+1))) {
						// Quit processing if there's been a shift in control limits during this check;
						break;
					}
					if (cEles.RawData.get(j) > cEles.USD2.get(j)) {
						aboveZoneA++;
						obsBackA++;
					} else if (cEles.RawData.get(j) < cEles.LSD2.get(j)) {
						belowZoneA++;
						obsBackA++;
					} else if (j > (i-2)) {
						// Always count all observations but the last one
						obsBackA++;
					}
				}
				if (belowZoneA>=2 || aboveZoneA>=2) {
					runsRuleAppend(cEles.RunsRules,i,obsBackA,'B');
					if (baselinePeriod==BaselinePeriods.VariableAll) {
						// Basically done (this violation will cause shift), but we'll go out a few more points to make 
						// sure the longest rule violation gets used for limits shifts
						returnVar = true;
						returnCount = 1;
					}
				}
				// RULE C - 4 out of 5 in zone B or beyond (beyond 1-sigma)
				int aboveZoneB = 0;
				int belowZoneB = 0;
				int obsBackB =	 0;
				endVar = 5;
				for (int j=i; j>(i-endVar); j--) {
					if (j<1) {
						continue;
					} else if (cEles.RawData.get(j) == null || cEles.USD1.get(j) == null) {
						endVar++;
						continue;
					} else if (j<i && !cEles.CL.get(j).equals(cEles.CL.get(j+1))) {
						// Quit processing if there's been a shift in control limits during this check;
						break;
					}
					if (cEles.RawData.get(j) > cEles.USD1.get(j)) {
						aboveZoneB++;
						obsBackB++;
					} else if (cEles.RawData.get(j) < cEles.LSD1.get(j)) {
						belowZoneB++;
						obsBackB++;
					} else if (j > (i-4)) {
						// Always count all observations but the last one
						obsBackB++;
					}
				}
				if (belowZoneB >= 4 || aboveZoneB >= 4) {
					runsRuleAppend(cEles.RunsRules,i,obsBackB,'C');
					if (baselinePeriod==BaselinePeriods.VariableAll) {
						// Basically done (this violation will cause shift), but we'll go out a few more points to make 
						// sure the longest rule violation gets used for limits shifts
						returnVar = true;
						returnCount = 1;
					}
				}
				// RULE D - 9 in a row on one side of center line
				int aboveCL = 0;
				int belowCL = 0;
				endVar = 9;
				for (int j=i; j > (i-endVar); j--) {
					if (j<1) {
						continue;
					} else if  (cEles.RawData.get(j) == null || cEles.CL.get(j) == null) {
						endVar++;
						continue;
					} else if (j<i && !cEles.CL.get(j).equals(cEles.CL.get(j+1))) {
						// Quit processing if there's been a shift in control limits during this check;
						break;
					}
					if (cEles.RawData.get(j) > cEles.CL.get(j)) {
						aboveCL++;
					} else if (cEles.RawData.get(j) < cEles.CL.get(j)) {
						belowCL++;
					}
				}
				if (belowCL == 9 || aboveCL == 9) {
					runsRuleAppend(cEles.RunsRules,i,9,'D');
					if (baselinePeriod==BaselinePeriods.Variable || baselinePeriod==BaselinePeriods.VariableAll) {
						// Basically done (this violation will cause shift), but we'll go out a few more points to make 
						// sure the longest rule violation gets used for limits shifts
						returnVar = true;
						returnCount = 1;
					}
				}
				if (returnVar & returnCount>9) {
					return;
				}
			}
		
	}
	
	private void trimListsBackToDisplaySize(ChartElements cEles) {
		
			if (cEles.RawData.size() > displayNobs) {
				final int currentSize = cEles.RawData.size();
				for (int i=0; i<(currentSize-displayNobs); i++) {
					cEles.RawData.remove(0);
					cEles.CL.remove(0);
					cEles.UCL.remove(0);
					cEles.USD1.remove(0);
					cEles.USD2.remove(0);
					cEles.LCL.remove(0);
					cEles.LSD1.remove(0);
					cEles.LSD2.remove(0);
					cEles.MR.remove(0);
					cEles.MR_CL.remove(0);
					cEles.MR_UCL.remove(0);
					cEles.MR_USD1.remove(0);
					cEles.MR_USD2.remove(0);
					cEles.MR_LCL.remove(0);
					cEles.RunsRules.remove(0);
				}
			}
		
	}

	private void calculateLimits(ChartElements cEles,Integer startPoint) {
		for (int i=((startPoint==null) ? 0 : startPoint); i<cEles.RawData.size(); i++) {
			final double dataMean = cEles.CL.get(i);
			final double mrMean = cEles.MR_CL.get(i);
			final double symClValue = 2.66*mrMean;
			final double assymClValue = 3.267*mrMean;
			cEles.UCL.set(i,dataMean+symClValue);
			cEles.USD1.set(i,dataMean+(symClValue/3));
			cEles.USD2.set(i,dataMean+(symClValue/3*2));
			cEles.LCL.set(i,dataMean-symClValue);
			cEles.LSD1.set(i,dataMean-(symClValue/3));
			cEles.LSD2.set(i,dataMean-(symClValue/3*2));
			cEles.MR_UCL.set(i,mrMean+(assymClValue));
			cEles.MR_USD1.set(i,mrMean+(assymClValue/3));
			cEles.MR_USD2.set(i,mrMean+(assymClValue/3*2));
			cEles.MR_LCL.set(i,Double.valueOf(0));
		}
	}

	private void runsRuleAppend(List<String> rrList, int pointer, int nObs, char rule) {
		for (int i=pointer; i>(pointer-nObs); i--) {
			if (i<1) {
				continue;
			}
			final String rrStr = (String)rrList.get(i);
			final StringBuilder sb = (rrStr==null) ? new StringBuilder() : new StringBuilder(rrStr);
			if (!(rrStr.indexOf(rule)>=0)) {
				if (sb.length()>0) {
					sb.append(',');
				} 
				sb.append(rule);
			}
			rrList.set(i,sb.toString());
		}
	}

	private int getBaselineNobs(ChartElements cEles) {
		final List<Double> rawDataList = (List<Double>)cEles.RawData;
		final int calcBaselineNobs = (rawDataList.size()>=baselineNobs) ? baselineNobs :
								(rawDataList.size()>=baselineInitialNobs) ? baselineInitialNobs : rawDataList.size();  
		return calcBaselineNobs;
	}

	private Double getAbsoluteDifference(Double inobj1, Double inobj2) {
		if (inobj1==null || inobj2==null) {
			return null;
		} else {
			return  Math.abs(inobj1-inobj2);
		}
	}
	
	/*
	private Number getAbsoluteDifference(Object inobj1, Object inobj2) {
		try {
			final Object num1;
			final Object num2;
			// Don't expect this to happen in this data, but check for it just the same
			if (!inobj1.getClass().equals(inobj2.getClass())) {
				num1 = inobj1.toString();
				num2 = inobj2.toString();
			} else {
				num1 = inobj1;
				num2 = inobj2;
			}
			if (num1 instanceof Integer) {
				return Math.abs((Integer)num1-(Integer)num2);
			} else if (num1 instanceof Float) {
				return Math.abs((Float)num1-(Float)num2);
			} else if (num1 instanceof Double) {
				return Math.abs((Double)num1-(Double)num2);
			} else if (num1 instanceof Long) {
				return Math.abs((Long)num1-(Long)num2);
			} else if (num1 instanceof String) {
				if (((String)num1).contains(".") || ((String)num2).contains(".")) {
					return Math.abs(Double.parseDouble((String)num1)-Double.parseDouble((String)num2));
				} else {
					return Math.abs(Long.parseLong((String)num1)-Long.parseLong((String)num2));
				}
			} else {
				// Shouldn't ever hit this block, but if we do, try converting to string and calculating.
				if (num1.toString().contains(".") || num2.toString().contains(".")) {
					return Math.abs(Double.parseDouble(num1.toString())-Double.parseDouble(num2.toString()));
				} else {
					return Math.abs(Long.parseLong(num1.toString())-Long.parseLong(num2.toString()));
				}
			}
		} catch (NumberFormatException e) {
			// Do nothing
		}
		return null;
	}
	*/

	private void returnChartConfig() {
        final Long projID = (long)(Integer)proj.getItem().getProps().get("projectdata_info");
		getResponse().setEntity(new StringRepresentation(XDAT.getConfigService().getConfigContents(configTool,configSearchPath,projID)));
		getResponse().setStatus(Status.SUCCESS_OK);
	}
	
	private Map<String, Map<Object,ChartElements>> getColumnMapFromTable(XFTTableI t, XdatStoredSearch ssx) {
		t.resetRowCursor();
		final Map<String, Map<Object,ChartElements>> columnMap = Maps.newLinkedHashMap();
		final List<String> columnList = Lists.newArrayList(Arrays.asList(t.getColumns()));
		final List<Object> byValues = Lists.newArrayList();
		searchByValues.put(ssx,byValues);
		final Iterator<String> it = columnList.iterator();
		while (it.hasNext()) {
			final String column = it.next();
			if (infoFields.contains(column.toLowerCase())) {
				it.remove();
			}
		}	
		if (byField == null || byField.length()<1 || !columnList.contains(byField)) {
			byValues.add(noByFieldString);
		} else {
			while (t.hasMoreRows()) {
				t.nextRow();
				final Object cellValue = t.getCellValue(byField);
				if (!byValues.contains(cellValue)) {
					byValues.add(cellValue);
				}
			}
			t.resetRowCursor();
		}
		Collections.sort(byValues,new Comparator<Object>(){
			@Override
			public int compare(Object arg0, Object arg1) {
				return arg0.toString().compareTo(arg1.toString());
			}
		});
		for (final String column : columnList) {
			final Map<Object,ChartElements> cElesMap = Maps.newLinkedHashMap();
			for (final Object byValue : byValues) {
				final ChartElements cEles = new ChartElements();
				final List<Double> newList = Lists.newArrayList(); 
				cEles.RawData = newList;
				cElesMap.put(byValue, cEles);
			}
			columnMap.put(column, cElesMap);
		}
		while (t.hasMoreRows()) {
			t.nextRow();
			for (final String column : columnList) {
				final Object cellValue = t.getCellValue(column);
				final Object byValue = (!byValues.get(0).equals(noByFieldString)) ? t.getCellValue(byField) : noByFieldString;
				if (byValue == null) {
					continue;
				}
				if (cellValue == null) {
					columnMap.get(column).get(byValue).RawData.add(null);
				} else if (cellValue instanceof Double) {
					columnMap.get(column).get(byValue).RawData.add((Double)t.getCellValue(column));
				} else if (isNumeric(cellValue)) {
					columnMap.get(column).get(byValue).RawData.add(Double.valueOf((t.getCellValue(column)).toString()));
				} else {
					columnMap.get(column).get(byValue).RawData.add(null);
				}
			}
		}
		// Remove columns from map with non-numeric values (reverse list, so we're looking at latest values)
		for (final String column : Lists.reverse(Lists.newArrayList(columnList))) {
			for (final Object byValue : byValues) {
				final List<Double> colValues = (List<Double>)columnMap.get(column).get(byValue).RawData;
				double nullCount=0;
				final double rowsToCount = Math.ceil(colValues.size()/10);
				for (int i=0;i<=rowsToCount;i++) {
					if (colValues.get(i) == null) {
						nullCount++;
					} else if (keepNumericDataOnly && !isNumeric(colValues.get(i))) {
						columnMap.get(column).remove(byValue);
						break;
					}
					// Remove columns that are effectively all null
					if (keepNumericDataOnly && (nullCount>=Math.floor(rowsToCount*.99))) {
						columnMap.get(column).remove(byValue);
						break;
					}
				}
			}
			if (columnMap.get(column).keySet().size()<1) {
				columnMap.remove(column);
			}
		}
		return columnMap;
	}
	
	private boolean isNumeric(Object o) {
		// NOTE:  Returning true for null values because we don't want to drop based on null (Currently won't check nulls anyway, but just in case....)
		return (o==null) ? true : o.toString().matches("-?\\d+(\\.\\d+)?");
	}
		
}
