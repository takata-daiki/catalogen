/*
 * GENERATED FILE
 * Created on Wed Mar 14 11:10:51 CDT 2012
 *
 */
package org.nrg.xdat.om.base.auto;
import org.nrg.xft.*;
import org.nrg.xft.security.UserI;
import org.nrg.xdat.om.*;
import org.nrg.xft.utils.ResourceFile;
import org.nrg.xft.exception.*;

import java.util.*;

/**
 * @author XDAT
 *
 */
@SuppressWarnings({"unchecked","rawtypes"})
public abstract class AutoUdsB5behavasdata extends XnatSubjectassessordata implements org.nrg.xdat.model.UdsB5behavasdataI {
	public static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AutoUdsB5behavasdata.class);
	public static String SCHEMA_ELEMENT_NAME="uds:b5behavasData";

	public AutoUdsB5behavasdata(ItemI item)
	{
		super(item);
	}

	public AutoUdsB5behavasdata(UserI user)
	{
		super(user);
	}

	/*
	 * @deprecated Use AutoUdsB5behavasdata(UserI user)
	 **/
	public AutoUdsB5behavasdata(){}

	public AutoUdsB5behavasdata(Hashtable properties,UserI user)
	{
		super(properties,user);
	}

	public String getSchemaElementName(){
		return "uds:b5behavasData";
	}
	 private org.nrg.xdat.om.XnatSubjectassessordata _Subjectassessordata =null;

	/**
	 * subjectAssessorData
	 * @return org.nrg.xdat.om.XnatSubjectassessordata
	 */
	public org.nrg.xdat.om.XnatSubjectassessordata getSubjectassessordata() {
		try{
			if (_Subjectassessordata==null){
				_Subjectassessordata=((XnatSubjectassessordata)org.nrg.xdat.base.BaseElement.GetGeneratedItem((XFTItem)getProperty("subjectAssessorData")));
				return _Subjectassessordata;
			}else {
				return _Subjectassessordata;
			}
		} catch (Exception e1) {return null;}
	}

	/**
	 * Sets the value for subjectAssessorData.
	 * @param v Value to Set.
	 */
	public void setSubjectassessordata(ItemI v) throws Exception{
		_Subjectassessordata =null;
		try{
			if (v instanceof XFTItem)
			{
				getItem().setChild(SCHEMA_ELEMENT_NAME + "/subjectAssessorData",v,true);
			}else{
				getItem().setChild(SCHEMA_ELEMENT_NAME + "/subjectAssessorData",v.getItem(),true);
			}
		} catch (Exception e1) {logger.error(e1);throw e1;}
	}

	/**
	 * subjectAssessorData
	 * set org.nrg.xdat.model.XnatSubjectassessordataI
	 */
	public <A extends org.nrg.xdat.model.XnatSubjectassessordataI> void setSubjectassessordata(A item) throws Exception{
	setSubjectassessordata((ItemI)item);
	}

	/**
	 * Removes the subjectAssessorData.
	 * */
	public void removeSubjectassessordata() {
		_Subjectassessordata =null;
		try{
			getItem().removeChild(SCHEMA_ELEMENT_NAME + "/subjectAssessorData",0);
		} catch (FieldNotFoundException e1) {logger.error(e1);}
		catch (java.lang.IndexOutOfBoundsException e1) {logger.error(e1);}
	}

	//FIELD

	private String _Initials=null;

	/**
	 * @return Returns the INITIALS.
	 */
	public String getInitials(){
		try{
			if (_Initials==null){
				_Initials=getStringProperty("INITIALS");
				return _Initials;
			}else {
				return _Initials;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for INITIALS.
	 * @param v Value to Set.
	 */
	public void setInitials(String v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/INITIALS",v);
		_Initials=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Npiqinf=null;

	/**
	 * @return Returns the NPIQINF.
	 */
	public Integer getNpiqinf() {
		try{
			if (_Npiqinf==null){
				_Npiqinf=getIntegerProperty("NPIQINF");
				return _Npiqinf;
			}else {
				return _Npiqinf;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for NPIQINF.
	 * @param v Value to Set.
	 */
	public void setNpiqinf(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/NPIQINF",v);
		_Npiqinf=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private String _Npiqinfx=null;

	/**
	 * @return Returns the NPIQINFX.
	 */
	public String getNpiqinfx(){
		try{
			if (_Npiqinfx==null){
				_Npiqinfx=getStringProperty("NPIQINFX");
				return _Npiqinfx;
			}else {
				return _Npiqinfx;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for NPIQINFX.
	 * @param v Value to Set.
	 */
	public void setNpiqinfx(String v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/NPIQINFX",v);
		_Npiqinfx=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Del=null;

	/**
	 * @return Returns the DEL.
	 */
	public Integer getDel() {
		try{
			if (_Del==null){
				_Del=getIntegerProperty("DEL");
				return _Del;
			}else {
				return _Del;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for DEL.
	 * @param v Value to Set.
	 */
	public void setDel(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/DEL",v);
		_Del=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Delsev=null;

	/**
	 * @return Returns the DELSEV.
	 */
	public Integer getDelsev() {
		try{
			if (_Delsev==null){
				_Delsev=getIntegerProperty("DELSEV");
				return _Delsev;
			}else {
				return _Delsev;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for DELSEV.
	 * @param v Value to Set.
	 */
	public void setDelsev(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/DELSEV",v);
		_Delsev=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Hall=null;

	/**
	 * @return Returns the HALL.
	 */
	public Integer getHall() {
		try{
			if (_Hall==null){
				_Hall=getIntegerProperty("HALL");
				return _Hall;
			}else {
				return _Hall;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for HALL.
	 * @param v Value to Set.
	 */
	public void setHall(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/HALL",v);
		_Hall=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Hallsev=null;

	/**
	 * @return Returns the HALLSEV.
	 */
	public Integer getHallsev() {
		try{
			if (_Hallsev==null){
				_Hallsev=getIntegerProperty("HALLSEV");
				return _Hallsev;
			}else {
				return _Hallsev;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for HALLSEV.
	 * @param v Value to Set.
	 */
	public void setHallsev(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/HALLSEV",v);
		_Hallsev=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Agit=null;

	/**
	 * @return Returns the AGIT.
	 */
	public Integer getAgit() {
		try{
			if (_Agit==null){
				_Agit=getIntegerProperty("AGIT");
				return _Agit;
			}else {
				return _Agit;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for AGIT.
	 * @param v Value to Set.
	 */
	public void setAgit(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/AGIT",v);
		_Agit=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Agitsev=null;

	/**
	 * @return Returns the AGITSEV.
	 */
	public Integer getAgitsev() {
		try{
			if (_Agitsev==null){
				_Agitsev=getIntegerProperty("AGITSEV");
				return _Agitsev;
			}else {
				return _Agitsev;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for AGITSEV.
	 * @param v Value to Set.
	 */
	public void setAgitsev(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/AGITSEV",v);
		_Agitsev=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Depd=null;

	/**
	 * @return Returns the DEPD.
	 */
	public Integer getDepd() {
		try{
			if (_Depd==null){
				_Depd=getIntegerProperty("DEPD");
				return _Depd;
			}else {
				return _Depd;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for DEPD.
	 * @param v Value to Set.
	 */
	public void setDepd(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/DEPD",v);
		_Depd=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Depdsev=null;

	/**
	 * @return Returns the DEPDSEV.
	 */
	public Integer getDepdsev() {
		try{
			if (_Depdsev==null){
				_Depdsev=getIntegerProperty("DEPDSEV");
				return _Depdsev;
			}else {
				return _Depdsev;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for DEPDSEV.
	 * @param v Value to Set.
	 */
	public void setDepdsev(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/DEPDSEV",v);
		_Depdsev=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Anx=null;

	/**
	 * @return Returns the ANX.
	 */
	public Integer getAnx() {
		try{
			if (_Anx==null){
				_Anx=getIntegerProperty("ANX");
				return _Anx;
			}else {
				return _Anx;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for ANX.
	 * @param v Value to Set.
	 */
	public void setAnx(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/ANX",v);
		_Anx=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Anxsev=null;

	/**
	 * @return Returns the ANXSEV.
	 */
	public Integer getAnxsev() {
		try{
			if (_Anxsev==null){
				_Anxsev=getIntegerProperty("ANXSEV");
				return _Anxsev;
			}else {
				return _Anxsev;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for ANXSEV.
	 * @param v Value to Set.
	 */
	public void setAnxsev(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/ANXSEV",v);
		_Anxsev=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Elat=null;

	/**
	 * @return Returns the ELAT.
	 */
	public Integer getElat() {
		try{
			if (_Elat==null){
				_Elat=getIntegerProperty("ELAT");
				return _Elat;
			}else {
				return _Elat;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for ELAT.
	 * @param v Value to Set.
	 */
	public void setElat(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/ELAT",v);
		_Elat=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Elatsev=null;

	/**
	 * @return Returns the ELATSEV.
	 */
	public Integer getElatsev() {
		try{
			if (_Elatsev==null){
				_Elatsev=getIntegerProperty("ELATSEV");
				return _Elatsev;
			}else {
				return _Elatsev;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for ELATSEV.
	 * @param v Value to Set.
	 */
	public void setElatsev(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/ELATSEV",v);
		_Elatsev=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Apa=null;

	/**
	 * @return Returns the APA.
	 */
	public Integer getApa() {
		try{
			if (_Apa==null){
				_Apa=getIntegerProperty("APA");
				return _Apa;
			}else {
				return _Apa;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for APA.
	 * @param v Value to Set.
	 */
	public void setApa(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/APA",v);
		_Apa=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Apasev=null;

	/**
	 * @return Returns the APASEV.
	 */
	public Integer getApasev() {
		try{
			if (_Apasev==null){
				_Apasev=getIntegerProperty("APASEV");
				return _Apasev;
			}else {
				return _Apasev;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for APASEV.
	 * @param v Value to Set.
	 */
	public void setApasev(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/APASEV",v);
		_Apasev=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Disn=null;

	/**
	 * @return Returns the DISN.
	 */
	public Integer getDisn() {
		try{
			if (_Disn==null){
				_Disn=getIntegerProperty("DISN");
				return _Disn;
			}else {
				return _Disn;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for DISN.
	 * @param v Value to Set.
	 */
	public void setDisn(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/DISN",v);
		_Disn=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Disnsev=null;

	/**
	 * @return Returns the DISNSEV.
	 */
	public Integer getDisnsev() {
		try{
			if (_Disnsev==null){
				_Disnsev=getIntegerProperty("DISNSEV");
				return _Disnsev;
			}else {
				return _Disnsev;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for DISNSEV.
	 * @param v Value to Set.
	 */
	public void setDisnsev(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/DISNSEV",v);
		_Disnsev=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Irr=null;

	/**
	 * @return Returns the IRR.
	 */
	public Integer getIrr() {
		try{
			if (_Irr==null){
				_Irr=getIntegerProperty("IRR");
				return _Irr;
			}else {
				return _Irr;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for IRR.
	 * @param v Value to Set.
	 */
	public void setIrr(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/IRR",v);
		_Irr=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Irrsev=null;

	/**
	 * @return Returns the IRRSEV.
	 */
	public Integer getIrrsev() {
		try{
			if (_Irrsev==null){
				_Irrsev=getIntegerProperty("IRRSEV");
				return _Irrsev;
			}else {
				return _Irrsev;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for IRRSEV.
	 * @param v Value to Set.
	 */
	public void setIrrsev(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/IRRSEV",v);
		_Irrsev=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Mot=null;

	/**
	 * @return Returns the MOT.
	 */
	public Integer getMot() {
		try{
			if (_Mot==null){
				_Mot=getIntegerProperty("MOT");
				return _Mot;
			}else {
				return _Mot;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for MOT.
	 * @param v Value to Set.
	 */
	public void setMot(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/MOT",v);
		_Mot=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Motsev=null;

	/**
	 * @return Returns the MOTSEV.
	 */
	public Integer getMotsev() {
		try{
			if (_Motsev==null){
				_Motsev=getIntegerProperty("MOTSEV");
				return _Motsev;
			}else {
				return _Motsev;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for MOTSEV.
	 * @param v Value to Set.
	 */
	public void setMotsev(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/MOTSEV",v);
		_Motsev=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Nite=null;

	/**
	 * @return Returns the NITE.
	 */
	public Integer getNite() {
		try{
			if (_Nite==null){
				_Nite=getIntegerProperty("NITE");
				return _Nite;
			}else {
				return _Nite;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for NITE.
	 * @param v Value to Set.
	 */
	public void setNite(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/NITE",v);
		_Nite=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Nitesev=null;

	/**
	 * @return Returns the NITESEV.
	 */
	public Integer getNitesev() {
		try{
			if (_Nitesev==null){
				_Nitesev=getIntegerProperty("NITESEV");
				return _Nitesev;
			}else {
				return _Nitesev;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for NITESEV.
	 * @param v Value to Set.
	 */
	public void setNitesev(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/NITESEV",v);
		_Nitesev=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _App=null;

	/**
	 * @return Returns the APP.
	 */
	public Integer getApp() {
		try{
			if (_App==null){
				_App=getIntegerProperty("APP");
				return _App;
			}else {
				return _App;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for APP.
	 * @param v Value to Set.
	 */
	public void setApp(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/APP",v);
		_App=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	//FIELD

	private Integer _Appsev=null;

	/**
	 * @return Returns the APPSEV.
	 */
	public Integer getAppsev() {
		try{
			if (_Appsev==null){
				_Appsev=getIntegerProperty("APPSEV");
				return _Appsev;
			}else {
				return _Appsev;
			}
		} catch (Exception e1) {logger.error(e1);return null;}
	}

	/**
	 * Sets the value for APPSEV.
	 * @param v Value to Set.
	 */
	public void setAppsev(Integer v){
		try{
		setProperty(SCHEMA_ELEMENT_NAME + "/APPSEV",v);
		_Appsev=null;
		} catch (Exception e1) {logger.error(e1);}
	}

	public static ArrayList<org.nrg.xdat.om.UdsB5behavasdata> getAllUdsB5behavasdatas(org.nrg.xft.security.UserI user,boolean preLoad)
	{
		ArrayList<org.nrg.xdat.om.UdsB5behavasdata> al = new ArrayList<org.nrg.xdat.om.UdsB5behavasdata>();

		try{
			org.nrg.xft.collections.ItemCollection items = org.nrg.xft.search.ItemSearch.GetAllItems(SCHEMA_ELEMENT_NAME,user,preLoad);
			al = org.nrg.xdat.base.BaseElement.WrapItems(items.getItems());
		} catch (Exception e) {
			logger.error("",e);
		}

		al.trimToSize();
		return al;
	}

	public static ArrayList<org.nrg.xdat.om.UdsB5behavasdata> getUdsB5behavasdatasByField(String xmlPath, Object value, org.nrg.xft.security.UserI user,boolean preLoad)
	{
		ArrayList<org.nrg.xdat.om.UdsB5behavasdata> al = new ArrayList<org.nrg.xdat.om.UdsB5behavasdata>();
		try {
			org.nrg.xft.collections.ItemCollection items = org.nrg.xft.search.ItemSearch.GetItems(xmlPath,value,user,preLoad);
			al = org.nrg.xdat.base.BaseElement.WrapItems(items.getItems());
		} catch (Exception e) {
			logger.error("",e);
		}

		al.trimToSize();
		return al;
	}

	public static ArrayList<org.nrg.xdat.om.UdsB5behavasdata> getUdsB5behavasdatasByField(org.nrg.xft.search.CriteriaCollection criteria, org.nrg.xft.security.UserI user,boolean preLoad)
	{
		ArrayList<org.nrg.xdat.om.UdsB5behavasdata> al = new ArrayList<org.nrg.xdat.om.UdsB5behavasdata>();
		try {
			org.nrg.xft.collections.ItemCollection items = org.nrg.xft.search.ItemSearch.GetItems(criteria,user,preLoad);
			al = org.nrg.xdat.base.BaseElement.WrapItems(items.getItems());
		} catch (Exception e) {
			logger.error("",e);
		}

		al.trimToSize();
		return al;
	}

	public static UdsB5behavasdata getUdsB5behavasdatasById(Object value, org.nrg.xft.security.UserI user,boolean preLoad)
	{
		try {
			org.nrg.xft.collections.ItemCollection items = org.nrg.xft.search.ItemSearch.GetItems("uds:b5behavasData/id",value,user,preLoad);
			ItemI match = items.getFirst();
			if (match!=null)
				return (UdsB5behavasdata) org.nrg.xdat.base.BaseElement.GetGeneratedItem(match);
			else
				 return null;
		} catch (Exception e) {
			logger.error("",e);
		}

		return null;
	}

	public static ArrayList wrapItems(ArrayList items)
	{
		ArrayList al = new ArrayList();
		al = org.nrg.xdat.base.BaseElement.WrapItems(items);
		al.trimToSize();
		return al;
	}

	public static ArrayList wrapItems(org.nrg.xft.collections.ItemCollection items)
	{
		return wrapItems(items.getItems());
	}

	public org.w3c.dom.Document toJoinedXML() throws Exception
	{
		ArrayList al = new ArrayList();
		al.add(this.getItem());
		al.add(org.nrg.xft.search.ItemSearch.GetItem("xnat:subjectData.ID",this.getItem().getProperty("xnat:mrSessionData.subject_ID"),getItem().getUser(),false));
		al.trimToSize();
		return org.nrg.xft.schema.Wrappers.XMLWrapper.XMLWriter.ItemListToDOM(al);
	}
	public ArrayList<ResourceFile> getFileResources(String rootPath, boolean preventLoop){
ArrayList<ResourceFile> _return = new ArrayList<ResourceFile>();
	 boolean localLoop = preventLoop;
	        localLoop = preventLoop;
	
	        //subjectAssessorData
	        XnatSubjectassessordata childSubjectassessordata = (XnatSubjectassessordata)this.getSubjectassessordata();
	            if (childSubjectassessordata!=null){
	              for(ResourceFile rf: ((XnatSubjectassessordata)childSubjectassessordata).getFileResources(rootPath, localLoop)) {
	                 rf.setXpath("subjectAssessorData[" + ((XnatSubjectassessordata)childSubjectassessordata).getItem().getPKString() + "]/" + rf.getXpath());
	                 rf.setXdatPath("subjectAssessorData/" + ((XnatSubjectassessordata)childSubjectassessordata).getItem().getPKString() + "/" + rf.getXpath());
	                 _return.add(rf);
	              }
	            }
	
	        localLoop = preventLoop;
	
	return _return;
}
}
