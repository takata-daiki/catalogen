/**
 * Mdl_soapserverBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

/**
 * Modified for KSoap2 library by pp@patrickpollet.net using KSoap2StubWriter
 */

package net.patrickpollet.moodlews.core;

import java.util.List;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import net.patrickpollet.ksoap2.*;

public class Mdl_soapserverBindingStub extends  KSoap2BindingStubBase{
    public Mdl_soapserverBindingStub(String service_url, String nameSpace, boolean debug) {
         super(service_url,nameSpace,debug);
     }

    public net.patrickpollet.moodlews.core.AssignmentRecord[] add_assignment(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.AssignmentDatum datum)  {
    final String METH_NAME = "add_assignment";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("datum",datum);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new AssignmentRecord(this.NAMESPACE));
      return (AssignmentRecord[]) ret.toArray( new AssignmentRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.CategoryRecord[] add_category(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.CategoryDatum datum)  {
    final String METH_NAME = "add_category";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("datum",datum);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new CategoryRecord(this.NAMESPACE));
      return (CategoryRecord[]) ret.toArray( new CategoryRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.CohortRecord[] add_cohort(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.CohortDatum datum)  {
    final String METH_NAME = "add_cohort";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("datum",datum);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new CohortRecord(this.NAMESPACE));
      return (CohortRecord[]) ret.toArray( new CohortRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.CourseRecord[] add_course(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.CourseDatum coursedatum)  {
    final String METH_NAME = "add_course";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("coursedatum",coursedatum);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new CourseRecord(this.NAMESPACE));
      return (CourseRecord[]) ret.toArray( new CourseRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.DatabaseRecord[] add_database(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.DatabaseDatum datum)  {
    final String METH_NAME = "add_database";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("datum",datum);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new DatabaseRecord(this.NAMESPACE));
      return (DatabaseRecord[]) ret.toArray( new DatabaseRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.ForumRecord[] add_forum(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.ForumDatum datum)  {
    final String METH_NAME = "add_forum";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("datum",datum);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new ForumRecord(this.NAMESPACE));
      return (ForumRecord[]) ret.toArray( new ForumRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.GroupRecord[] add_group(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.GroupDatum datum)  {
    final String METH_NAME = "add_group";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("datum",datum);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new GroupRecord(this.NAMESPACE));
      return (GroupRecord[]) ret.toArray( new GroupRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.GroupingRecord[] add_grouping(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.GroupingDatum datum)  {
    final String METH_NAME = "add_grouping";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("datum",datum);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new GroupingRecord(this.NAMESPACE));
      return (GroupingRecord[]) ret.toArray( new GroupingRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.LabelRecord[] add_label(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.LabelDatum datum)  {
    final String METH_NAME = "add_label";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("datum",datum);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new LabelRecord(this.NAMESPACE));
      return (LabelRecord[]) ret.toArray( new LabelRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord add_noneditingteacher(int client, java.lang.String sesskey, java.lang.String courseid, java.lang.String courseidfield, java.lang.String userid, java.lang.String useridfield)  {
    final String METH_NAME = "add_noneditingteacher";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("courseid",courseid);
      envelope.addProperty("courseidfield",courseidfield);
      envelope.addProperty("userid",userid);
      envelope.addProperty("useridfield",useridfield);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.PageWikiRecord[] add_pagewiki(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.PageWikiDatum datum)  {
    final String METH_NAME = "add_pagewiki";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("datum",datum);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new PageWikiRecord(this.NAMESPACE));
      return (PageWikiRecord[]) ret.toArray( new PageWikiRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.SectionRecord[] add_section(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.SectionDatum datum)  {
    final String METH_NAME = "add_section";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("datum",datum);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new SectionRecord(this.NAMESPACE));
      return (SectionRecord[]) ret.toArray( new SectionRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord add_student(int client, java.lang.String sesskey, java.lang.String courseid, java.lang.String courseidfield, java.lang.String userid, java.lang.String useridfield)  {
    final String METH_NAME = "add_student";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("courseid",courseid);
      envelope.addProperty("courseidfield",courseidfield);
      envelope.addProperty("userid",userid);
      envelope.addProperty("useridfield",useridfield);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord add_teacher(int client, java.lang.String sesskey, java.lang.String courseid, java.lang.String courseidfield, java.lang.String userid, java.lang.String useridfield)  {
    final String METH_NAME = "add_teacher";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("courseid",courseid);
      envelope.addProperty("courseidfield",courseidfield);
      envelope.addProperty("userid",userid);
      envelope.addProperty("useridfield",useridfield);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.UserRecord[] add_user(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.UserDatum userdatum)  {
    final String METH_NAME = "add_user";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("userdatum",userdatum);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new UserRecord(this.NAMESPACE));
      return (UserRecord[]) ret.toArray( new UserRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.WikiRecord[] add_wiki(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.WikiDatum datum)  {
    final String METH_NAME = "add_wiki";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("datum",datum);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new WikiRecord(this.NAMESPACE));
      return (WikiRecord[]) ret.toArray( new WikiRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord affect_assignment_to_section(int client, java.lang.String sesskey, int assignmentid, int sectionid, int groupmode)  {
    final String METH_NAME = "affect_assignment_to_section";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("assignmentid",assignmentid);
      envelope.addProperty("sectionid",sectionid);
      envelope.addProperty("groupmode",groupmode);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord affect_course_to_category(int client, java.lang.String sesskey, int courseid, int categoryid)  {
    final String METH_NAME = "affect_course_to_category";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("courseid",courseid);
      envelope.addProperty("categoryid",categoryid);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord affect_database_to_section(int client, java.lang.String sesskey, int databaseid, int sectionid)  {
    final String METH_NAME = "affect_database_to_section";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("databaseid",databaseid);
      envelope.addProperty("sectionid",sectionid);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord affect_forum_to_section(int client, java.lang.String sesskey, int forumid, int sectionid, int groupmode)  {
    final String METH_NAME = "affect_forum_to_section";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("forumid",forumid);
      envelope.addProperty("sectionid",sectionid);
      envelope.addProperty("groupmode",groupmode);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord affect_group_to_course(int client, java.lang.String sesskey, int groupid, int courseid)  {
    final String METH_NAME = "affect_group_to_course";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("groupid",groupid);
      envelope.addProperty("courseid",courseid);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord affect_group_to_grouping(int client, java.lang.String sesskey, int groupid, int groupingid)  {
    final String METH_NAME = "affect_group_to_grouping";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("groupid",groupid);
      envelope.addProperty("groupingid",groupingid);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord affect_grouping_to_course(int client, java.lang.String sesskey, int groupingid, int courseid)  {
    final String METH_NAME = "affect_grouping_to_course";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("groupingid",groupingid);
      envelope.addProperty("courseid",courseid);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord affect_label_to_section(int client, java.lang.String sesskey, int labelid, int sectionid)  {
    final String METH_NAME = "affect_label_to_section";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("labelid",labelid);
      envelope.addProperty("sectionid",sectionid);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord affect_pageWiki_to_wiki(int client, java.lang.String sesskey, int pageid, int wikiid)  {
    final String METH_NAME = "affect_pageWiki_to_wiki";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("pageid",pageid);
      envelope.addProperty("wikiid",wikiid);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord affect_section_to_course(int client, java.lang.String sesskey, int sectionid, int courseid)  {
    final String METH_NAME = "affect_section_to_course";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("sectionid",sectionid);
      envelope.addProperty("courseid",courseid);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord affect_user_to_cohort(int client, java.lang.String sesskey, int userid, int groupid)  {
    final String METH_NAME = "affect_user_to_cohort";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("userid",userid);
      envelope.addProperty("groupid",groupid);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord affect_user_to_course(int client, java.lang.String sesskey, int userid, int courseid, java.lang.String rolename)  {
    final String METH_NAME = "affect_user_to_course";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("userid",userid);
      envelope.addProperty("courseid",courseid);
      envelope.addProperty("rolename",rolename);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord affect_user_to_group(int client, java.lang.String sesskey, int userid, int groupid)  {
    final String METH_NAME = "affect_user_to_group";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("userid",userid);
      envelope.addProperty("groupid",groupid);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.EnrolRecord[] affect_users_to_cohort(int client, java.lang.String sesskey, java.lang.String[] userids, java.lang.String useridfield, java.lang.String cohortid, java.lang.String cohortidfield)  {
    final String METH_NAME = "affect_users_to_cohort";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
     //generate an arraytype SoapObject for input array 
      SoapObject _userids= new SoapObject(this.NAMESPACE,"StringArray");
     if (userids !=null)      
         for ( Object o : userids) 
            _userids.addProperty("item",o);
     envelope.addProperty("userids",_userids);
      envelope.addProperty("useridfield",useridfield);
      envelope.addProperty("cohortid",cohortid);
      envelope.addProperty("cohortidfield",cohortidfield);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new EnrolRecord(this.NAMESPACE));
      return (EnrolRecord[]) ret.toArray( new EnrolRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.EnrolRecord[] affect_users_to_group(int client, java.lang.String sesskey, java.lang.String[] userids, java.lang.String useridfield, java.lang.String groupid, java.lang.String groupidfield)  {
    final String METH_NAME = "affect_users_to_group";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
     //generate an arraytype SoapObject for input array 
      SoapObject _userids= new SoapObject(this.NAMESPACE,"StringArray");
     if (userids !=null)      
         for ( Object o : userids) 
            _userids.addProperty("item",o);
     envelope.addProperty("userids",_userids);
      envelope.addProperty("useridfield",useridfield);
      envelope.addProperty("groupid",groupid);
      envelope.addProperty("groupidfield",groupidfield);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new EnrolRecord(this.NAMESPACE));
      return (EnrolRecord[]) ret.toArray( new EnrolRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AffectRecord affect_wiki_to_section(int client, java.lang.String sesskey, int wikiid, int sectionid, int groupmode, int visible)  {
    final String METH_NAME = "affect_wiki_to_section";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("wikiid",wikiid);
      envelope.addProperty("sectionid",sectionid);
      envelope.addProperty("groupmode",groupmode);
      envelope.addProperty("visible",visible);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      return (AffectRecord)KSoap2Utils.getObject(response,new AffectRecord(this.NAMESPACE));
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public int count_activities(int client, java.lang.String sesskey, java.lang.String userid, java.lang.String useridfield, java.lang.String courseid, java.lang.String courseidfield, int limit)  {
    final String METH_NAME = "count_activities";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("userid",userid);
      envelope.addProperty("useridfield",useridfield);
      envelope.addProperty("courseid",courseid);
      envelope.addProperty("courseidfield",courseidfield);
      envelope.addProperty("limit",limit);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     Integer response = (Integer) envelope.getResponse();
	  this.logInfo(METH_NAME, response);
      return response.intValue();
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return 0;
        }
   }
    public int count_users_bycourse(int client, java.lang.String sesskey, java.lang.String courseid, java.lang.String idfield, int roleid)  {
    final String METH_NAME = "count_users_bycourse";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("courseid",courseid);
      envelope.addProperty("idfield",idfield);
      envelope.addProperty("roleid",roleid);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     Integer response = (Integer) envelope.getResponse();
	  this.logInfo(METH_NAME, response);
      return response.intValue();
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return 0;
        }
   }
    public net.patrickpollet.moodlews.core.CohortRecord[] delete_cohort(int client, java.lang.String sesskey, java.lang.String id, java.lang.String idfield)  {
    final String METH_NAME = "delete_cohort";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("id",id);
      envelope.addProperty("idfield",idfield);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new CohortRecord(this.NAMESPACE));
      return (CohortRecord[]) ret.toArray( new CohortRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.CourseRecord[] delete_course(int client, java.lang.String sesskey, java.lang.String courseid, java.lang.String courseidfield)  {
    final String METH_NAME = "delete_course";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("courseid",courseid);
      envelope.addProperty("courseidfield",courseidfield);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new CourseRecord(this.NAMESPACE));
      return (CourseRecord[]) ret.toArray( new CourseRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.GroupRecord[] delete_group(int client, java.lang.String sesskey, java.lang.String id, java.lang.String idfield)  {
    final String METH_NAME = "delete_group";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("id",id);
      envelope.addProperty("idfield",idfield);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new GroupRecord(this.NAMESPACE));
      return (GroupRecord[]) ret.toArray( new GroupRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.GroupingRecord[] delete_grouping(int client, java.lang.String sesskey, java.lang.String id, java.lang.String idfield)  {
    final String METH_NAME = "delete_grouping";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("id",id);
      envelope.addProperty("idfield",idfield);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new GroupingRecord(this.NAMESPACE));
      return (GroupingRecord[]) ret.toArray( new GroupingRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.UserRecord[] delete_user(int client, java.lang.String sesskey, java.lang.String userid, java.lang.String useridfield)  {
    final String METH_NAME = "delete_user";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("userid",userid);
      envelope.addProperty("useridfield",useridfield);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new UserRecord(this.NAMESPACE));
      return (UserRecord[]) ret.toArray( new UserRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AssignmentRecord[] edit_assignments(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.AssignmentDatum[] assignments)  {
    final String METH_NAME = "edit_assignments";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
     //generate an arraytype SoapObject for input array 
      SoapObject _assignments= new SoapObject(this.NAMESPACE,"AssignmentDatumArray");
     if (assignments !=null)      
         for ( Object o : assignments) 
            _assignments.addProperty("item",o);
     envelope.addProperty("assignments",_assignments);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new AssignmentRecord(this.NAMESPACE));
      return (AssignmentRecord[]) ret.toArray( new AssignmentRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.CategoryRecord[] edit_categories(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.CategoryDatum[] categories)  {
    final String METH_NAME = "edit_categories";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
     //generate an arraytype SoapObject for input array 
      SoapObject _categories= new SoapObject(this.NAMESPACE,"CategoryDatumArray");
     if (categories !=null)      
         for ( Object o : categories) 
            _categories.addProperty("item",o);
     envelope.addProperty("categories",_categories);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new CategoryRecord(this.NAMESPACE));
      return (CategoryRecord[]) ret.toArray( new CategoryRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.CourseRecord[] edit_courses(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.CourseDatum[] courses)  {
    final String METH_NAME = "edit_courses";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
     //generate an arraytype SoapObject for input array 
      SoapObject _courses= new SoapObject(this.NAMESPACE,"CourseDatumArray");
     if (courses !=null)      
         for ( Object o : courses) 
            _courses.addProperty("item",o);
     envelope.addProperty("courses",_courses);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new CourseRecord(this.NAMESPACE));
      return (CourseRecord[]) ret.toArray( new CourseRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.DatabaseRecord[] edit_databases(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.DatabaseDatum[] databases)  {
    final String METH_NAME = "edit_databases";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
     //generate an arraytype SoapObject for input array 
      SoapObject _databases= new SoapObject(this.NAMESPACE,"DatabaseDatumArray");
     if (databases !=null)      
         for ( Object o : databases) 
            _databases.addProperty("item",o);
     envelope.addProperty("databases",_databases);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new DatabaseRecord(this.NAMESPACE));
      return (DatabaseRecord[]) ret.toArray( new DatabaseRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.ForumRecord[] edit_forums(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.ForumDatum[] forums)  {
    final String METH_NAME = "edit_forums";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
     //generate an arraytype SoapObject for input array 
      SoapObject _forums= new SoapObject(this.NAMESPACE,"ForumDatumArray");
     if (forums !=null)      
         for ( Object o : forums) 
            _forums.addProperty("item",o);
     envelope.addProperty("forums",_forums);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new ForumRecord(this.NAMESPACE));
      return (ForumRecord[]) ret.toArray( new ForumRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.GroupingRecord[] edit_groupings(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.GroupingDatum[] groupings)  {
    final String METH_NAME = "edit_groupings";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
     //generate an arraytype SoapObject for input array 
      SoapObject _groupings= new SoapObject(this.NAMESPACE,"GroupingDatumArray");
     if (groupings !=null)      
         for ( Object o : groupings) 
            _groupings.addProperty("item",o);
     envelope.addProperty("groupings",_groupings);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new GroupingRecord(this.NAMESPACE));
      return (GroupingRecord[]) ret.toArray( new GroupingRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.GroupRecord[] edit_groups(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.GroupDatum[] groups)  {
    final String METH_NAME = "edit_groups";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
     //generate an arraytype SoapObject for input array 
      SoapObject _groups= new SoapObject(this.NAMESPACE,"GroupDatumArray");
     if (groups !=null)      
         for ( Object o : groups) 
            _groups.addProperty("item",o);
     envelope.addProperty("groups",_groups);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new GroupRecord(this.NAMESPACE));
      return (GroupRecord[]) ret.toArray( new GroupRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.LabelRecord[] edit_labels(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.LabelDatum[] labels)  {
    final String METH_NAME = "edit_labels";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
     //generate an arraytype SoapObject for input array 
      SoapObject _labels= new SoapObject(this.NAMESPACE,"LabelDatumArray");
     if (labels !=null)      
         for ( Object o : labels) 
            _labels.addProperty("item",o);
     envelope.addProperty("labels",_labels);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new LabelRecord(this.NAMESPACE));
      return (LabelRecord[]) ret.toArray( new LabelRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.PageWikiRecord[] edit_pagesWiki(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.PageWikiDatum[] pageswiki)  {
    final String METH_NAME = "edit_pagesWiki";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
     //generate an arraytype SoapObject for input array 
      SoapObject _pageswiki= new SoapObject(this.NAMESPACE,"PageWikiDatumArray");
     if (pageswiki !=null)      
         for ( Object o : pageswiki) 
            _pageswiki.addProperty("item",o);
     envelope.addProperty("pageswiki",_pageswiki);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new PageWikiRecord(this.NAMESPACE));
      return (PageWikiRecord[]) ret.toArray( new PageWikiRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.SectionRecord[] edit_sections(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.SectionDatum[] sections)  {
    final String METH_NAME = "edit_sections";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
     //generate an arraytype SoapObject for input array 
      SoapObject _sections= new SoapObject(this.NAMESPACE,"SectionDatumArray");
     if (sections !=null)      
         for ( Object o : sections) 
            _sections.addProperty("item",o);
     envelope.addProperty("sections",_sections);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new SectionRecord(this.NAMESPACE));
      return (SectionRecord[]) ret.toArray( new SectionRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.UserRecord[] edit_users(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.UserDatum[] users)  {
    final String METH_NAME = "edit_users";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
     //generate an arraytype SoapObject for input array 
      SoapObject _users= new SoapObject(this.NAMESPACE,"UserDatumArray");
     if (users !=null)      
         for ( Object o : users) 
            _users.addProperty("item",o);
     envelope.addProperty("users",_users);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new UserRecord(this.NAMESPACE));
      return (UserRecord[]) ret.toArray( new UserRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.WikiRecord[] edit_wikis(int client, java.lang.String sesskey, net.patrickpollet.moodlews.core.WikiDatum[] wikis)  {
    final String METH_NAME = "edit_wikis";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
     //generate an arraytype SoapObject for input array 
      SoapObject _wikis= new SoapObject(this.NAMESPACE,"WikiDatumArray");
     if (wikis !=null)      
         for ( Object o : wikis) 
            _wikis.addProperty("item",o);
     envelope.addProperty("wikis",_wikis);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new WikiRecord(this.NAMESPACE));
      return (WikiRecord[]) ret.toArray( new WikiRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.EnrolRecord[] enrol_students(int client, java.lang.String sesskey, java.lang.String courseid, java.lang.String courseidfield, java.lang.String[] userids, java.lang.String idfield)  {
    final String METH_NAME = "enrol_students";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("courseid",courseid);
      envelope.addProperty("courseidfield",courseidfield);
     //generate an arraytype SoapObject for input array 
      SoapObject _userids= new SoapObject(this.NAMESPACE,"StringArray");
     if (userids !=null)      
         for ( Object o : userids) 
            _userids.addProperty("item",o);
     envelope.addProperty("userids",_userids);
      envelope.addProperty("idfield",idfield);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new EnrolRecord(this.NAMESPACE));
      return (EnrolRecord[]) ret.toArray( new EnrolRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.ForumDiscussionRecord[] forum_add_discussion(int client, java.lang.String sesskey, int forumid, net.patrickpollet.moodlews.core.ForumDiscussionDatum discussion)  {
    final String METH_NAME = "forum_add_discussion";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("forumid",forumid);
      envelope.addProperty("discussion",discussion);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new ForumDiscussionRecord(this.NAMESPACE));
      return (ForumDiscussionRecord[]) ret.toArray( new ForumDiscussionRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.ForumPostRecord[] forum_add_reply(int client, java.lang.String sesskey, int parentid, net.patrickpollet.moodlews.core.ForumPostDatum post)  {
    final String METH_NAME = "forum_add_reply";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("parentid",parentid);
      envelope.addProperty("post",post);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new ForumPostRecord(this.NAMESPACE));
      return (ForumPostRecord[]) ret.toArray( new ForumPostRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.ActivityRecord[] get_activities(int client, java.lang.String sesskey, java.lang.String userid, java.lang.String useridfield, java.lang.String courseid, java.lang.String courseidfield, int limit)  {
    final String METH_NAME = "get_activities";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("userid",userid);
      envelope.addProperty("useridfield",useridfield);
      envelope.addProperty("courseid",courseid);
      envelope.addProperty("courseidfield",courseidfield);
      envelope.addProperty("limit",limit);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new ActivityRecord(this.NAMESPACE));
      return (ActivityRecord[]) ret.toArray( new ActivityRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.AssignmentRecord[] get_all_assignments(int client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue)  {
    final String METH_NAME = "get_all_assignments";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("fieldname",fieldname);
      envelope.addProperty("fieldvalue",fieldvalue);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new AssignmentRecord(this.NAMESPACE));
      return (AssignmentRecord[]) ret.toArray( new AssignmentRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.CohortRecord[] get_all_cohorts(int client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue)  {
    final String METH_NAME = "get_all_cohorts";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("fieldname",fieldname);
      envelope.addProperty("fieldvalue",fieldvalue);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new CohortRecord(this.NAMESPACE));
      return (CohortRecord[]) ret.toArray( new CohortRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.DatabaseRecord[] get_all_databases(int client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue)  {
    final String METH_NAME = "get_all_databases";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("fieldname",fieldname);
      envelope.addProperty("fieldvalue",fieldvalue);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new DatabaseRecord(this.NAMESPACE));
      return (DatabaseRecord[]) ret.toArray( new DatabaseRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.ForumRecord[] get_all_forums(int client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue)  {
    final String METH_NAME = "get_all_forums";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("fieldname",fieldname);
      envelope.addProperty("fieldvalue",fieldvalue);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new ForumRecord(this.NAMESPACE));
      return (ForumRecord[]) ret.toArray( new ForumRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.GroupingRecord[] get_all_groupings(int client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue)  {
    final String METH_NAME = "get_all_groupings";
	MySoapSerializationEnvelope envelope = this.makeEnvelope(METH_NAME);
      envelope.addProperty("client",client);
      envelope.addProperty("sesskey",sesskey);
      envelope.addProperty("fieldname",fieldname);
      envelope.addProperty("fieldvalue",fieldvalue);
    HttpTransportSE httpTransport = this.makeHttpTransport();
     try {
       httpTransport.call(METH_NAME, envelope);
     SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
      SoapObject response = (SoapObject) resultsRequestSOAP.getProperty(0);
	  this.logInfo(METH_NAME, response);
      List ret=this.getList(response,new GroupingRecord(this.NAMESPACE));
      return (GroupingRecord[]) ret.toArray( new GroupingRecord[0]);
    } catch (Exception e) {
             this.logError(httpTransport, e);
           return null;
        }
   }
    public net.patrickpollet.moodlews.core.GroupRecord[] get_all_groups(int client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue)  {
    final String METH_NAME = "get_all_groups";
	MySoapSerializationEnvelope e