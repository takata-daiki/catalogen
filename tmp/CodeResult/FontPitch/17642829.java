/*
 *  Copyright 2007-2008, Plutext Pty Ltd.
 *   
 *  This file is part of docx4j.

    docx4j is licensed under the Apache License, Version 2.0 (the "License"); 
    you may not use this file except in compliance with the License. 

    You may obtain a copy of the License at 

        http://www.apache.org/licenses/LICENSE-2.0 

    Unless required by applicable law or agreed to in writing, software 
    distributed under the License is distributed on an "AS IS" BASIS, 
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
    See the License for the specific language governing permissions and 
    limitations under the License.

 */


package org.docx4j.wml;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.docx4j.math.CTAcc;
import org.docx4j.math.CTBar;
import org.docx4j.math.CTBorderBox;
import org.docx4j.math.CTBox;
import org.docx4j.math.CTD;
import org.docx4j.math.CTEqArr;
import org.docx4j.math.CTF;
import org.docx4j.math.CTFunc;
import org.docx4j.math.CTGroupChr;
import org.docx4j.math.CTLimLow;
import org.docx4j.math.CTLimUpp;
import org.docx4j.math.CTM;
import org.docx4j.math.CTNary;
import org.docx4j.math.CTPhant;
import org.docx4j.math.CTR;
import org.docx4j.math.CTRad;
import org.docx4j.math.CTSPre;
import org.docx4j.math.CTSSub;
import org.docx4j.math.CTSSubSup;
import org.docx4j.math.CTSSup;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.docx4j.wml package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Settings_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "settings");
    private final static QName _Recipients_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "recipients");
    private final static QName _WebSettings_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "webSettings");
    private final static QName _Footnotes_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "footnotes");
    private final static QName _TxbxContent_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "txbxContent");
    private final static QName _Endnotes_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "endnotes");
    private final static QName _CTCustomXmlCellMoveFromRangeStart_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "moveFromRangeStart");
    private final static QName _CTCustomXmlCellMoveFrom_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "moveFrom");
    private final static QName _CTCustomXmlCellMoveFromRangeEnd_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "moveFromRangeEnd");
    private final static QName _CTCustomXmlCellCustomXmlMoveFromRangeStart_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "customXmlMoveFromRangeStart");
    private final static QName _CTCustomXmlCellSdt_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "sdt");
    private final static QName _CTCustomXmlCellMoveToRangeEnd_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "moveToRangeEnd");
    private final static QName _CTCustomXmlCellCustomXmlDelRangeEnd_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "customXmlDelRangeEnd");
    private final static QName _CTCustomXmlCellCustomXmlDelRangeStart_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "customXmlDelRangeStart");
    private final static QName _CTCustomXmlCellCustomXmlMoveToRangeEnd_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "customXmlMoveToRangeEnd");
    private final static QName _CTCustomXmlCellCustomXmlInsRangeStart_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "customXmlInsRangeStart");
    private final static QName _CTCustomXmlCellCustomXmlMoveToRangeStart_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "customXmlMoveToRangeStart");
    private final static QName _CTCustomXmlCellCustomXmlInsRangeEnd_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "customXmlInsRangeEnd");
    private final static QName _CTCustomXmlCellPermEnd_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "permEnd");
    private final static QName _CTCustomXmlCellPermStart_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "permStart");
    private final static QName _CTCustomXmlCellMoveToRangeStart_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "moveToRangeStart");
    private final static QName _CTCustomXmlCellTc_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "tc");
    private final static QName _CTCustomXmlCellBookmarkEnd_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "bookmarkEnd");
    private final static QName _CTCustomXmlCellCustomXml_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "customXml");
    private final static QName _CTCustomXmlCellBookmarkStart_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "bookmarkStart");
    private final static QName _CTCustomXmlCellMoveTo_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "moveTo");
    private final static QName _CTCustomXmlCellCustomXmlMoveFromRangeEnd_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "customXmlMoveFromRangeEnd");
    private final static QName _CTTrPrBaseTblHeader_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "tblHeader");
    private final static QName _CTTrPrBaseJc_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "jc");
    private final static QName _CTTrPrBaseGridBefore_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "gridBefore");
    private final static QName _CTTrPrBaseCantSplit_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "cantSplit");
    private final static QName _CTTrPrBaseHidden_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "hidden");
    private final static QName _CTTrPrBaseTrHeight_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "trHeight");
    private final static QName _CTTrPrBaseTblCellSpacing_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "tblCellSpacing");
    private final static QName _CTTrPrBaseGridAfter_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "gridAfter");
    private final static QName _CTTrPrBaseWAfter_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "wAfter");
    private final static QName _CTTrPrBaseDivId_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "divId");
    private final static QName _CTTrPrBaseWBefore_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "wBefore");
    private final static QName _CTTrPrBaseCnfStyle_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "cnfStyle");
    private final static QName _CTParaRPrOriginalRtl_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "rtl");
    private final static QName _CTParaRPrOriginalB_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "b");
    private final static QName _CTParaRPrOriginalSnapToGrid_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "snapToGrid");
    private final static QName _CTParaRPrOriginalI_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "i");
    private final static QName _CTParaRPrOriginalCs_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "cs");
    private final static QName _CTParaRPrOriginalDstrike_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "dstrike");
    private final static QName _CTParaRPrOriginalICs_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "iCs");
    private final static QName _CTParaRPrOriginalOMath_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "oMath");
    private final static QName _CTParaRPrOriginalNoProof_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "noProof");
    private final static QName _CTParaRPrOriginalVertAlign_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "vertAlign");
    private final static QName _CTParaRPrOriginalW_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "w");
    private final static QName _CTParaRPrOriginalEffect_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "effect");
    private final static QName _CTParaRPrOriginalCaps_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "caps");
    private final static QName _CTParaRPrOriginalEm_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "em");
    private final static QName _CTParaRPrOriginalPosition_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "position");
    private final static QName _CTParaRPrOriginalWebHidden_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "webHidden");
    private final static QName _CTParaRPrOriginalShd_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "shd");
    private final static QName _CTParaRPrOriginalSmallCaps_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "smallCaps");
    private final static QName _CTParaRPrOriginalSzCs_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "szCs");
    private final static QName _CTParaRPrOriginalSpecVanish_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "specVanish");
    private final static QName _CTParaRPrOriginalSpacing_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "spacing");
    private final static QName _CTParaRPrOriginalEastAsianLayout_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "eastAsianLayout");
    private final static QName _CTParaRPrOriginalFitText_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "fitText");
    private final static QName _CTParaRPrOriginalKern_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "kern");
    private final static QName _CTParaRPrOriginalVanish_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "vanish");
    private final static QName _CTParaRPrOriginalLang_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "lang");
    private final static QName _CTParaRPrOriginalOutline_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "outline");
    private final static QName _CTParaRPrOriginalBCs_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "bCs");
    private final static QName _CTParaRPrOriginalStrike_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "strike");
    private final static QName _CTParaRPrOriginalShadow_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "shadow");
    private final static QName _CTParaRPrOriginalImprint_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "imprint");
    private final static QName _CTParaRPrOriginalBdr_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "bdr");
    private final static QName _CTParaRPrOriginalSz_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "sz");
    private final static QName _CTParaRPrOriginalEmboss_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "emboss");
    private final static QName _CTFFDataEntryMacro_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "entryMacro");
    private final static QName _CTFFDataDdList_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "ddList");
    private final static QName _CTFFDataEnabled_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "enabled");
    private final static QName _CTFFDataCalcOnExit_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "calcOnExit");
    private final static QName _CTFFDataName_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "name");
    private final static QName _CTFFDataCheckBox_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "checkBox");
    private final static QName _CTFFDataHelpText_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "helpText");
    private final static QName _CTFFDataStatusText_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "statusText");
    private final static QName _CTFFDataTextInput_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "textInput");
    private final static QName _CTFFDataExitMacro_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "exitMacro");
    private final static QName _RunInsSPre_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "sPre");
    private final static QName _RunInsBorderBox_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "borderBox");
    private final static QName _RunInsGroupChr_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "groupChr");
    private final static QName _RunInsFunc_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "func");
    private final static QName _RunInsSSubSup_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "sSubSup");
    private final static QName _RunInsLimUpp_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "limUpp");
    private final static QName _RunInsAcc_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "acc");
    private final static QName _RunInsM_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "m");
    private final static QName _RunInsLimLow_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "limLow");
    private final static QName _RunInsSSup_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "sSup");
    private final static QName _RunInsD_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "d");
    private final static QName _RunInsF_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "f");
    private final static QName _RunInsEqArr_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "eqArr");
    private final static QName _RunInsRad_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "rad");
    private final static QName _RunInsNary_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "nary");
    private final static QName _RunInsBar_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "bar");
    private final static QName _RunInsBox_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "box");
    private final static QName _RunInsR_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "r");
    private final static QName _RunInsSmartTag_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "smartTag");
    private final static QName _RunInsPhant_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "phant");
    private final static QName _RunInsSSub_QNAME = new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "sSub");
    private final static QName _PFldSimple_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "fldSimple");
    private final static QName _PHyperlink_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "hyperlink");
    private final static QName _PSubDoc_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "subDoc");
    private final static QName _BodyTbl_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "tbl");
    private final static QName _BodyAltChunk_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "altChunk");
    private final static QName _RMonthShort_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "monthShort");
    private final static QName _RYearLong_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "yearLong");
    private final static QName _RPgNum_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "pgNum");
    private final static QName _RFootnoteReference_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "footnoteReference");
    private final static QName _REndnoteRef_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "endnoteRef");
    private final static QName _RSoftHyphen_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "softHyphen");
    private final static QName _REndnoteReference_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "endnoteReference");
    private final static QName _RT_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "t");
    private final static QName _RCr_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "cr");
    private final static QName _RFldChar_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "fldChar");
    private final static QName _RSeparator_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "separator");
    private final static QName _RCommentReference_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "commentReference");
    private final static QName _RDayLong_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "dayLong");
    private final static QName _RAnnotationRef_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "annotationRef");
    private final static QName _RRuby_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "ruby");
    private final static QName _RObject_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "object");
    private final static QName _RTab_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "tab");
    private final static QName _RLastRenderedPageBreak_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "lastRenderedPageBreak");
    private final static QName _RDrawing_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "drawing");
    private final static QName _RInstrText_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "instrText");
    private final static QName _RDelInstrText_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "delInstrText");
    private final static QName _RSym_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "sym");
    private final static QName _RPict_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "pict");
    private final static QName _RContinuationSeparator_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "continuationSeparator");
    private final static QName _RYearShort_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "yearShort");
    private final static QName _RFootnoteRef_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "footnoteRef");
    private final static QName _RDayShort_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "dayShort");
    private final static QName _RMonthLong_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "monthLong");
    private final static QName _RPtab_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "ptab");
    private final static QName _RNoBreakHyphen_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "noBreakHyphen");
    private final static QName _SdtPrRPr_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "rPr");
    private final static QName _SdtPrText_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "text");
    private final static QName _SdtPrCitation_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "citation");
    private final static QName _SdtPrDocPartObj_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "docPartObj");
    private final static QName _SdtPrShowingPlcHdr_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "showingPlcHdr");
    private final static QName _SdtPrPlaceholder_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "placeholder");
    private final static QName _SdtPrAlias_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "alias");
    private final static QName _SdtPrLock_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "lock");
    private final static QName _SdtPrTemporary_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "temporary");
    private final static QName _SdtPrComboBox_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "comboBox");
    private final static QName _SdtPrBibliography_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "bibliography");
    private final static QName _SdtPrDate_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "date");
    private final static QName _SdtPrRichText_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "richText");
    private final static QName _SdtPrDropDownList_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "dropDownList");
    private final static QName _SdtPrPicture_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "picture");
    private final static QName _SdtPrEquation_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "equation");
    private final static QName _SdtPrDataBinding_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "dataBinding");
    private final static QName _SdtPrDocPartList_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "docPartList");
    private final static QName _SdtPrGroup_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "group");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.docx4j.wml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Pict }
     * 
     */
    public Pict createPict() {
        return new Pict();
    }

    /**
     * Create an instance of {@link CTShortHexNumber }
     * 
     */
    public CTShortHexNumber createCTShortHexNumber() {
        return new CTShortHexNumber();
    }

    /**
     * Create an instance of {@link CTDocPartTypes }
     * 
     */
    public CTDocPartTypes createCTDocPartTypes() {
        return new CTDocPartTypes();
    }


    /**
     * Create an instance of {@link SdtPr.Picture }
     * 
     */
    public SdtPr.Picture createSdtPrPicture() {
        return new SdtPr.Picture();
    }

    /**
     * Create an instance of {@link Id }
     * 
     */
    public Id createId() {
        return new Id();
    }

    /**
     * Create an instance of {@link CTSdtDate.DateFormat }
     * 
     */
    public CTSdtDate.DateFormat createCTSdtDateDateFormat() {
        return new CTSdtDate.DateFormat();
    }

    /**
     * Create an instance of {@link TblWidth }
     * 
     */
    public TblWidth createTblWidth() {
        return new TblWidth();
    }

    /**
     * Create an instance of {@link CTSdtDate }
     * 
     */
    public CTSdtDate createCTSdtDate() {
        return new CTSdtDate();
    }

    /**
     * Create an instance of {@link SdtBlock }
     * 
     */
    public SdtBlock createSdtBlock() {
        return new SdtBlock();
    }

    /**
     * Create an instance of {@link CTRPrChange.RPr }
     * 
     */
    public CTRPrChange.RPr createCTRPrChangeRPr() {
        return new CTRPrChange.RPr();
    }

    /**
     * Create an instance of {@link CTMailMerge.AddressFieldName }
     * 
     */
    public CTMailMerge.AddressFieldName createCTMailMergeAddressFieldName() {
        return new CTMailMerge.AddressFieldName();
    }

    /**
     * Create an instance of {@link CTFtnEdnSepRef }
     * 
     */
    public CTFtnEdnSepRef createCTFtnEdnSepRef() {
        return new CTFtnEdnSepRef();
    }

    /**
     * Create an instance of {@link CTFFTextInput }
     * 
     */
    public CTFFTextInput createCTFFTextInput() {
        return new CTFFTextInput();
    }

    /**
     * Create an instance of {@link CTCompat }
     * 
     */
    public CTCompat createCTCompat() {
        return new CTCompat();
    }
    
    /**
     * Create an instance of {@link CTCompatSetting }
     * 
     */
    public CTCompatSetting createCTCompatSetting() {
        return new CTCompatSetting();
    }    

    /**
     * Create an instance of {@link PPrBase }
     * 
     */
    public PPrBase createPPrBase() {
        return new PPrBase();
    }

    /**
     * Create an instance of {@link CTSdtContentCell }
     * 
     */
    public CTSdtContentCell createCTSdtContentCell() {
        return new CTSdtContentCell();
    }

    /**
     * Create an instance of {@link SectPr.Type }
     * 
     */
    public SectPr.Type createSectPrType() {
        return new SectPr.Type();
    }

    /**
     * Create an instance of {@link CTTextEffect }
     * 
     */
    public CTTextEffect createCTTextEffect() {
        return new CTTextEffect();
    }

    /**
     * Create an instance of {@link CTBookmarkRange }
     * 
     */
    public CTBookmarkRange createCTBookmarkRange() {
        return new CTBookmarkRange();
    }

    /**
     * Create an instance of {@link TblGridCol }
     * 
     */
    public TblGridCol createTblGridCol() {
        return new TblGridCol();
    }

    /**
     * Create an instance of {@link CTGuid }
     * 
     */
    public CTGuid createCTGuid() {
        return new CTGuid();
    }

    /**
     * Create an instance of {@link SdtPr.Alias }
     * 
     */
    public SdtPr.Alias createSdtPrAlias() {
        return new SdtPr.Alias();
    }

    /**
     * Create an instance of {@link CTTblLayoutType }
     * 
     */
    public CTTblLayoutType createCTTblLayoutType() {
        return new CTTblLayoutType();
    }

    /**
     * Create an instance of {@link CTSettings.ConsecutiveHyphenLimit }
     * 
     */
    public CTSettings.ConsecutiveHyphenLimit createCTSettingsConsecutiveHyphenLimit() {
        return new CTSettings.ConsecutiveHyphenLimit();
    }

    /**
     * Create an instance of {@link CTTrPrChange }
     * 
     */
    public CTTrPrChange createCTTrPrChange() {
        return new CTTrPrChange();
    }

    /**
     * Create an instance of {@link CTTargetScreenSz }
     * 
     */
    public CTTargetScreenSz createCTTargetScreenSz() {
        return new CTTargetScreenSz();
    }

    /**
     * Create an instance of {@link CTSettings.SummaryLength }
     * 
     */
    public CTSettings.SummaryLength createCTSettingsSummaryLength() {
        return new CTSettings.SummaryLength();
    }

    /**
     * Create an instance of {@link CTTrackChangeNumbering }
     * 
     */
    public CTTrackChangeNumbering createCTTrackChangeNumbering() {
        return new CTTrackChangeNumbering();
    }

    /**
     * Create an instance of {@link CTFFTextInput.Format }
     * 
     */
    public CTFFTextInput.Format createCTFFTextInputFormat() {
        return new CTFFTextInput.Format();
    }

    /**
     * Create an instance of {@link CTCnf }
     * 
     */
    public CTCnf createCTCnf() {
        return new CTCnf();
    }

    /**
     * Create an instance of {@link CTFFHelpText }
     * 
     */
    public CTFFHelpText createCTFFHelpText() {
        return new CTFFHelpText();
    }

    /**
     * Create an instance of {@link CTEm }
     * 
     */
    public CTEm createCTEm() {
        return new CTEm();
    }

    /**
     * Create an instance of {@link CTMarkupRange }
     * 
     */
    public CTMarkupRange createCTMarkupRange() {
        return new CTMarkupRange();
    }

    /**
     * Create an instance of {@link CTRubyPr }
     * 
     */
    public CTRubyPr createCTRubyPr() {
        return new CTRubyPr();
    }

    /**
     * Create an instance of {@link Lvl.LvlRestart }
     * 
     */
    public Lvl.LvlRestart createLvlLvlRestart() {
        return new Lvl.LvlRestart();
    }

    /**
     * Create an instance of {@link Numbering.NumPicBullet }
     * 
     */
    public Numbering.NumPicBullet createNumberingNumPicBullet() {
        return new Numbering.NumPicBullet();
    }

    /**
     * Create an instance of {@link RFonts }
     * 
     */
    public RFonts createRFonts() {
        return new RFonts();
    }

    /**
     * Create an instance of {@link CTRecipients }
     * 
     */
    public CTRecipients createCTRecipients() {
        return new CTRecipients();
    }

    /**
     * Create an instance of {@link CTTrackChangeRange }
     * 
     */
    public CTTrackChangeRange createCTTrackChangeRange() {
        return new CTTrackChangeRange();
    }

    /**
     * Create an instance of {@link org.docx4j.wml.Style.Name }
     * 
     */
    public org.docx4j.wml.Style.Name createStyleName() {
        return new org.docx4j.wml.Style.Name();
    }

    /**
     * Create an instance of {@link FontFamily }
     * 
     */
    public FontFamily createFontFamily() {
        return new FontFamily();
    }

    /**
     * Create an instance of {@link Fonts }
     * 
     */
    public Fonts createFonts() {
        return new Fonts();
    }

    /**
     * Create an instance of {@link CTFFDDList.Result }
     * 
     */
    public CTFFDDList.Result createCTFFDDListResult() {
        return new CTFFDDList.Result();
    }

    /**
     * Create an instance of {@link CTDocGrid }
     * 
     */
    public CTDocGrid createCTDocGrid() {
        return new CTDocGrid();
    }

    /**
     * Create an instance of {@link CTTblGridChange }
     * 
     */
    public CTTblGridChange createCTTblGridChange() {
        return new CTTblGridChange();
    }

    /**
     * Create an instance of {@link HpsMeasure }
     * 
     */
    public HpsMeasure createHpsMeasure() {
        return new HpsMeasure();
    }

    /**
     * Create an instance of {@link CTPageNumber }
     * 
     */
    public CTPageNumber createCTPageNumber() {
        return new CTPageNumber();
    }

    /**
     * Create an instance of {@link CTSdtRow }
     * 
     */
    public CTSdtRow createCTSdtRow() {
        return new CTSdtRow();
    }

    /**
     * Create an instance of {@link CTSdtComboBox }
     * 
     */
    public CTSdtComboBox createCTSdtComboBox() {
        return new CTSdtComboBox();
    }

    /**
     * Create an instance of {@link CTTrPrBase.GridBefore }
     * 
     */
    public CTTrPrBase.GridBefore createCTTrPrBaseGridBefore() {
        return new CTTrPrBase.GridBefore();
    }

    /**
     * Create an instance of {@link org.docx4j.wml.RPr }
     * 
     */
    public org.docx4j.wml.RPr createRPr() {
        return new org.docx4j.wml.RPr();
    }

    /**
     * Create an instance of {@link CTBorder }
     * 
     */
    public CTBorder createCTBorder() {
        return new CTBorder();
    }

    /**
     * Create an instance of {@link CTMacroName }
     * 
     */
    public CTMacroName createCTMacroName() {
        return new CTMacroName();
    }

    /**
     * Create an instance of {@link RunDel }
     * 
     */
    public RunDel createRunDel() {
        return new RunDel();
    }

    /**
     * Create an instance of {@link CTFtnPos }
     * 
     */
    public CTFtnPos createCTFtnPos() {
        return new CTFtnPos();
    }

    /**
     * Create an instance of {@link GlossaryDocument }
     * 
     */
    public GlossaryDocument createGlossaryDocument() {
        return new GlossaryDocument();
    }

    /**
     * Create an instance of {@link Fonts.Font }
     * 
     */
    public Fonts.Font createFontsFont() {
        return new Fonts.Font();
    }

    /**
     * Create an instance of {@link CTShd }
     * 
     */
    public CTShd createCTShd() {
        return new CTShd();
    }

    /**
     * Create an instance of {@link PPrBase.PBdr }
     * 
     */
    public PPrBase.PBdr createPPrBasePBdr() {
        return new PPrBase.PBdr();
    }

    /**
     * Create an instance of {@link CTNumRestart }
     * 
     */
    public CTNumRestart createCTNumRestart() {
        return new CTNumRestart();
    }

    /**
     * Create an instance of {@link Numbering.AbstractNum.NumStyleLink }
     * 
     */
    public Numbering.AbstractNum.NumStyleLink createNumberingAbstractNumNumStyleLink() {
        return new Numbering.AbstractNum.NumStyleLink();
    }

    /**
     * Create an instance of {@link DocDefaults }
     * 
     */
    public DocDefaults createDocDefaults() {
        return new DocDefaults();
    }

    /**
     * Create an instance of {@link CTMailMerge.ConnectString }
     * 
     */
    public CTMailMerge.ConnectString createCTMailMergeConnectString() {
        return new CTMailMerge.ConnectString();
    }

    /**
     * Create an instance of {@link CTSmartTagType }
     * 
     */
    public CTSmartTagType createCTSmartTagType() {
        return new CTSmartTagType();
    }

    /**
     * Create an instance of {@link CTSdtDocPart.DocPartGallery }
     * 
     */
    public CTSdtDocPart.DocPartGallery createCTSdtDocPartDocPartGallery() {
        return new CTSdtDocPart.DocPartGallery();
    }

    /**
     * Create an instance of {@link CTDocPartPr }
     * 
     */
    public CTDocPartPr createCTDocPartPr() {
        return new CTDocPartPr();
    }

    /**
     * Create an instance of {@link CTSettings.ForceUpgrade }
     * 
     */
    public CTSettings.ForceUpgrade createCTSettingsForceUpgrade() {
        return new CTSettings.ForceUpgrade();
    }

    /**
     * Create an instance of {@link CTTrPrBase }
     * 
     */
    public CTTrPrBase createCTTrPrBase() {
        return new CTTrPrBase();
    }

    /**
     * Create an instance of {@link CTSettings }
     * 
     */
    public CTSettings createCTSettings() {
        return new CTSettings();
    }

    /**
     * Create an instance of {@link R.CommentReference }
     * 
     */
    public R.CommentReference createRCommentReference() {
        return new R.CommentReference();
    }

    /**
     * Create an instance of {@link CTColumns }
     * 
     */
    public CTColumns createCTColumns() {
        return new CTColumns();
    }

    /**
     * Create an instance of {@link CTSimpleField }
     * 
     */
    public CTSimpleField createCTSimpleField() {
        return new CTSimpleField();
    }

    /**
     * Create an instance of {@link CTRubyContent }
     * 
     */
    public CTRubyContent createCTRubyContent() {
        return new CTRubyContent();
    }

    /**
     * Create an instance of {@link org.docx4j.wml.Style.UiPriority }
     * 
     */
    public org.docx4j.wml.Style.UiPriority createStyleUiPriority() {
        return new org.docx4j.wml.Style.UiPriority();
    }

    /**
     * Create an instance of {@link R.EndnoteRef }
     * 
     */
    public R.EndnoteRef createREndnoteRef() {
        return new R.EndnoteRef();
    }

    /**
     * Create an instance of {@link CTTblPrEx }
     * 
     */
    public CTTblPrEx createCTTblPrEx() {
        return new CTTblPrEx();
    }

    /**
     * Create an instance of {@link Highlight }
     * 
     */
    public Highlight createHighlight() {
        return new Highlight();
    }

    /**
     * Create an instance of {@link CTFFCheckBox }
     * 
     */
    public CTFFCheckBox createCTFFCheckBox() {
        return new CTFFCheckBox();
    }

    /**
     * Create an instance of {@link CommentRangeEnd }
     * 
     */
    public CommentRangeEnd createCommentRangeEnd() {
        return new CommentRangeEnd();
    }

    /**
     * Create an instance of {@link CTSectPrChange }
     * 
     */
    public CTSectPrChange createCTSectPrChange() {
        return new CTSectPrChange();
    }

    /**
     * Create an instance of {@link CTSdtContentRun }
     * 
     */
    public CTSdtContentRun createCTSdtContentRun() {
        return new CTSdtContentRun();
    }

    /**
     * Create an instance of {@link CTDocPart }
     * 
     */
    public CTDocPart createCTDocPart() {
        return new CTDocPart();
    }

    /**
     * Create an instance of {@link CTTblOverlap }
     * 
     */
    public CTTblOverlap createCTTblOverlap() {
        return new CTTblOverlap();
    }

    /**
     * Create an instance of {@link Tc }
     * 
     */
    public Tc createTc() {
        return new Tc();
    }

    /**
     * Create an instance of {@link CTPictureBase }
     * 
     */
    public CTPictureBase createCTPictureBase() {
        return new CTPictureBase();
    }

    /**
     * Create an instance of {@link org.docx4j.wml.Style.Aliases }
     * 
     */
    public org.docx4j.wml.Style.Aliases createStyleAliases() {
        return new org.docx4j.wml.Style.Aliases();
    }

    /**
     * Create an instance of {@link TblGrid }
     * 
     */
    public TblGrid createTblGrid() {
        return new TblGrid();
    }

    /**
     * Create an instance of {@link TcPrInner }
     * 
     */
    public TcPrInner createTcPrInner() {
        return new TcPrInner();
    }

    /**
     * Create an instance of {@link Document }
     * 
     */
    public Document createDocument() {
        return new Document();
    }

    /**
     * Create an instance of {@link SectPrBase }
     * 
     */
    public SectPrBase createSectPrBase() {
        return new SectPrBase();
    }

    /**
     * Create an instance of {@link Lvl.LvlText }
     * 
     */
    public Lvl.LvlText createLvlLvlText() {
        return new Lvl.LvlText();
    }

    /**
     * Create an instance of {@link CTDocType }
     * 
     */
    public CTDocType createCTDocType() {
        return new CTDocType();
    }

    /**
     * Create an instance of {@link R.MonthShort }
     * 
     */
    public R.MonthShort createRMonthShort() {
        return new R.MonthShort();
    }

    /**
     * Create an instance of {@link CTCharacterSpacing }
     * 
     */
    public CTCharacterSpacing createCTCharacterSpacing() {
        return new CTCharacterSpacing();
    }

    /**
     * Create an instance of {@link CTSdtDropDownList }
     * 
     */
    public CTSdtDropDownList createCTSdtDropDownList() {
        return new CTSdtDropDownList();
    }

    /**
     * Create an instance of {@link R.NoBreakHyphen }
     * 
     */
    public R.NoBreakHyphen createRNoBreakHyphen() {
        return new R.NoBreakHyphen();
    }

    /**
     * Create an instance of {@link CTFFData }
     * 
     */
    public CTFFData createCTFFData() {
        return new CTFFData();
    }

    /**
     * Create an instance of {@link Body }
     * 
     */
    public Body createBody() {
        return new Body();
    }

    /**
     * Create an instance of {@link CTSignedHpsMeasure }
     * 
     */
    public CTSignedHpsMeasure createCTSignedHpsMeasure() {
        return new CTSignedHpsMeasure();
    }

    /**
     * Create an instance of {@link CTShapeDefaults }
     * 
     */
    public CTShapeDefaults createCTShapeDefaults() {
        return new CTShapeDefaults();
    }

    /**
     * Create an instance of {@link CTCustomXmlRun }
     * 
     */
    public CTCustomXmlRun createCTCustomXmlRun() {
        return new CTCustomXmlRun();
    }

    /**
     * Create an instance of {@link CTOdso.Udl }
     * 
     */
    public CTOdso.Udl createCTOdsoUdl() {
        return new CTOdso.Udl();
    }

    /**
     * Create an instance of {@link Numbering.Num }
     * 
     */
    public Numbering.Num createNumberingNum() {
        return new Numbering.Num();
    }

    /**
     * Create an instance of {@link TcPrInner.HMerge }
     * 
     */
    public TcPrInner.HMerge createTcPrInnerHMerge() {
        return new TcPrInner.HMerge();
    }

    /**
     * Create an instance of {@link PPr }
     * 
     */
    public PPr createPPr() {
        return new PPr();
    }

    /**
     * Create an instance of {@link CTFFTextType }
     * 
     */
    public CTFFTextType createCTFFTextType() {
        return new CTFFTextType();
    }

    /**
     * Create an instance of {@link CTDivBdr }
     * 
     */
    public CTDivBdr createCTDivBdr() {
        return new CTDivBdr();
    }

    /**
     * Create an instance of {@link CTSdtDateMappingType }
     * 
     */
    public CTSdtDateMappingType createCTSdtDateMappingType() {
        return new CTSdtDateMappingType();
    }

    /**
     * Create an instance of {@link CTObject }
     * 
     */
    public CTObject createCTObject() {
        return new CTObject();
    }

    /**
     * Create an instance of {@link CTLongHexNumber }
     * 
     */
    public CTLongHexNumber createCTLongHexNumber() {
        return new CTLongHexNumber();
    }

    /**
     * Create an instance of {@link R.PgNum }
     * 
     */
    public R.PgNum createRPgNum() {
        return new R.PgNum();
    }

    /**
     * Create an instance of {@link CTTrPrBase.DivId }
     * 
     */
    public CTTrPrBase.DivId createCTTrPrBaseDivId() {
        return new CTTrPrBase.DivId();
    }

    /**
     * Create an instance of {@link CTFootnotes }
     * 
     */
    public CTFootnotes createCTFootnotes() {
        return new CTFootnotes();
    }

    /**
     * Create an instance of {@link CTDocPartCategory.Name }
     * 
     */
    public CTDocPartCategory.Name createCTDocPartCategoryName() {
        return new CTDocPartCategory.Name();
    }

    /**
     * Create an instance of {@link CTWebSettings.PixelsPerInch }
     * 
     */
    public CTWebSettings.PixelsPerInch createCTWebSettingsPixelsPerInch() {
        return new CTWebSettings.PixelsPerInch();
    }

    /**
     * Create an instance of {@link Numbering.AbstractNum.Name }
     * 
     */
    public Numbering.AbstractNum.Name createNumberingAbstractNumName() {
        return new Numbering.AbstractNum.Name();
    }

    /**
     * Create an instance of {@link CTTwipsMeasure }
     * 
     */
    public CTTwipsMeasure createCTTwipsMeasure() {
        return new CTTwipsMeasure();
    }

    /**
     * Create an instance of {@link org.docx4j.wml.Style.Link }
     * 
     */
    public org.docx4j.wml.Style.Link createStyleLink() {
        return new org.docx4j.wml.Style.Link();
    }

    /**
     * Create an instance of {@link BooleanDefaultFalse }
     * 
     */
    public BooleanDefaultFalse createBooleanDefaultFalse() {
        return new BooleanDefaultFalse();
    }

    /**
     * Create an instance of {@link Fonts.Font.AltName }
     * 
     */
    public Fonts.Font.AltName createFontsFontAltName() {
        return new Fonts.Font.AltName();
    }

    /**
     * Create an instance of {@link CTDocPartName }
     * 
     */
    public CTDocPartName createCTDocPartName() {
        return new CTDocPartName();
    }

    /**
     * Create an instance of {@link CTBookmark }
     * 
     */
    public CTBookmark createCTBookmark() {
        return new CTBookmark();
    }

    /**
     * Create an instance of {@link CTOdsoFieldMapData }
     * 
     */
    public CTOdsoFieldMapData createCTOdsoFieldMapData() {
        return new CTOdsoFieldMapData();
    }

    /**
     * Create an instance of {@link CTParaRPrOriginal }
     * 
     */
    public CTParaRPrOriginal createCTParaRPrOriginal() {
        return new CTParaRPrOriginal();
    }

    /**
     * Create an instance of {@link CTFitText }
     * 
     */
    public CTFitText createCTFitText() {
        return new CTFitText();
    }

    /**
     * Create an instance of {@link CTCalendarType }
     * 
     */
    public CTCalendarType createCTCalendarType() {
        return new CTCalendarType();
    }

    /**
     * Create an instance of {@link R }
     * 
     */
    public R createR() {
        return new R();
    }

    /**
     * Create an instance of {@link Lvl.Suff }
     * 
     */
    public Lvl.Suff createLvlSuff() {
        return new Lvl.Suff();
    }

    /**
     * Create an instance of {@link CTCustomXmlRow }
     * 
     */
    public CTCustomXmlRow createCTCustomXmlRow() {
        return new CTCustomXmlRow();
    }

    /**
     * Create an instance of {@link CTPPrChange }
     * 
     */
    public CTPPrChange createCTPPrChange() {
        return new CTPPrChange();
    }

    /**
     * Create an instance of {@link CTUcharHexNumber }
     * 
     */
    public CTUcharHexNumber createCTUcharHexNumber() {
        return new CTUcharHexNumber();
    }

    /**
     * Create an instance of {@link CTFFTextInput.MaxLength }
     * 
     */
    public CTFFTextInput.MaxLength createCTFFTextInputMaxLength() {
        return new CTFFTextInput.MaxLength();
    }

    /**
     * Create an instance of {@link R.YearShort }
     * 
     */
    public R.YearShort createRYearShort() {
        return new R.YearShort();
    }

    /**
     * Create an instance of {@link Tr }
     * 
     */
    public Tr createTr() {
        return new Tr();
    }

    /**
     * Create an instance of {@link CTFtnEdnRef }
     * 
     */
    public CTFtnEdnRef createCTFtnEdnRef() {
        return new CTFtnEdnRef();
    }

    /**
     * Create an instance of {@link CTCaption }
     * 
     */
    public CTCaption createCTCaption() {
        return new CTCaption();
    }

    /**
     * Create an instance of {@link CTFrame.Name }
     * 
     */
    public CTFrame.Name createCTFrameName() {
        return new CTFrame.Name();
    }

    /**
     * Create an instance of {@link DocDefaults.RPrDefault }
     * 
     */
    public DocDefaults.RPrDefault createDocDefaultsRPrDefault() {
        return new DocDefaults.RPrDefault();
    }

    /**
     * Create an instance of {@link CTSettings.DisplayHorizontalDrawingGridEvery }
     * 
     */
    public CTSettings.DisplayHorizontalDrawingGridEvery createCTSettingsDisplayHorizontalDrawingGridEvery() {
        return new CTSettings.DisplayHorizontalDrawingGridEvery();
    }

    /**
     * Create an instance of {@link CTDocPartPr.Style }
     * 
     */
    public CTDocPartPr.Style createCTDocPartPrStyle() {
        return new CTDocPartPr.Style();
    }

    /**
     * Create an instance of {@link CTSdtCell }
     * 
     */
    public CTSdtCell createCTSdtCell() {
        return new CTSdtCell();
    }

    /**
     * Create an instance of {@link CTPPrDefault }
     * 
     */
    public CTPPrDefault createCTPPrDefault() {
        return new CTPPrDefault();
    }

    /**
     * Create an instance of {@link SdtPr }
     * 
     */
    public SdtPr createSdtPr() {
        return new SdtPr();
    }


    /**
     * Create an instance of {@link CTWebSettings }
     * 
     */
    public CTWebSettings createCTWebSettings() {
        return new CTWebSettings();
    }

    /**
     * Create an instance of {@link CTEmpty }
     * 
     */
    public CTEmpty createCTEmpty() {
        return new CTEmpty();
    }

    /**
     * Create an instance of {@link CTPaperSource }
     * 
     */
    public CTPaperSource createCTPaperSource() {
        return new CTPaperSource();
    }

    /**
     * Create an instance of {@link Numbering.Num.LvlOverride }
     * 
     */
    public Numbering.Num.LvlOverride createNumberingNumLvlOverride() {
        return new Numbering.Num.LvlOverride();
    }

    /**
     * Create an instance of {@link CTLang }
     * 
     */
    public CTLang createCTLang() {
        return new CTLang();
    }

    /**
     * Create an instance of {@link CTDocPartCategory }
     * 
     */
    public CTDocPartCategory createCTDocPartCategory() {
        return new CTDocPartCategory();
    }

    /**
     * Create an instance of {@link Styles }
     * 
     */
    public Styles createStyles() {
        return new Styles();
    }

    /**
     * Create an instance of {@link CTTxbxContent }
     * 
     */
    public CTTxbxContent createCTTxbxContent() {
        return new CTTxbxContent();
    }

    /**
     * Create an instance of {@link SectPr.PgMar }
     * 
     */
    public SectPr.PgMar createSectPrPgMar() {
        return new SectPr.PgMar();
    }

    /**
     * Create an instance of {@link CTTblPrBase.TblStyleRowBandSize }
     * 
     */
    public CTTblPrBase.TblStyleRowBandSize createCTTblPrBaseTblStyleRowBandSize() {
        return new CTTblPrBase.TblStyleRowBandSize();
    }

    /**
     * Create an instance of {@link Ftr }
     * 
     */
    public Ftr createFtr() {
        return new Ftr();
    }

    /**
     * Create an instance of {@link CTMailMerge.MailSubject }
     * 
     */
    public CTMailMerge.MailSubject createCTMailMergeMailSubject() {
        return new CTMailMerge.MailSubject();
    }

    /**
     * Create an instance of {@link CTSdtContentRow }
     * 
     */
    public CTSdtContentRow createCTSdtContentRow() {
        return new CTSdtContentRow();
    }

    /**
     * Create an instance of {@link ProofErr }
     * 
     */
    public ProofErr createProofErr() {
        return new ProofErr();
    }

    /**
     * Create an instance of {@link TextDirection }
     * 
     */
    public TextDirection createTextDirection() {
        return new TextDirection();
    }

    /**
     * Create an instance of {@link PPrBase.NumPr }
     * 
     */
    public PPrBase.NumPr createPPrBaseNumPr() {
        return new PPrBase.NumPr();
    }

    /**
     * Create an instance of {@link CTAutoCaptions }
     * 
     */
    public CTAutoCaptions createCTAutoCaptions() {
        return new CTAutoCaptions();
    }

    /**
     * Create an instance of {@link CTDocPartType }
     * 
     */
    public CTDocPartType createCTDocPartType() {
        return new CTDocPartType();
    }

    /**
     * Create an instance of {@link PPrBase.Spacing }
     * 
     */
    public PPrBase.Spacing createPPrBaseSpacing() {
        return new PPrBase.Spacing();
    }

    /**
     * Create an instance of {@link CTSmartTagPr }
     * 
     */
    public CTSmartTagPr createCTSmartTagPr() {
        return new CTSmartTagPr();
    }

    /**
     * Create an instance of {@link CTSettings.ListSeparator }
     * 
     */
    public CTSettings.ListSeparator createCTSettingsListSeparator() {
        return new CTSettings.ListSeparator();
    }

    /**
     * Create an instance of {@link TblBorders }
     * 
     */
    public TblBorders createTblBorders() {
        return new TblBorders();
    }

    /**
     * Create an instance of {@link ParaRPr }
     * 
     */
    public ParaRPr createParaRPr() {
        return new ParaRPr();
    }

    /**
     * Create an instance of {@link Tabs }
     * 
     */
    public Tabs createTabs() {
        return new Tabs();
    }

    /**
     * Create an instance of {@link CTColumn }
     * 
     */
    public CTColumn createCTColumn() {
        return new CTColumn();
    }

    /**
     * Create an instance of {@link CTCustomXmlPr }
     * 
     */
    public CTCustomXmlPr createCTCustomXmlPr() {
        return new CTCustomXmlPr();
    }

    /**
     * Create an instance of {@link PPrBase.PStyle }
     * 
     */
    public PPrBase.PStyle createPPrBasePStyle() {
        return new PPrBase.PStyle();
    }

    /**
     * Create an instance of {@link CTSettings.DefaultTableStyle }
     * 
     */
    public CTSettings.DefaultTableStyle createCTSettingsDefaultTableStyle() {
        return new CTSettings.DefaultTableStyle();
    }

    /**
     * Create an instance of {@link Styles.LatentStyles.LsdException }
     * 
     */
    public Styles.LatentStyles.LsdException createStylesLatentStylesLsdException() {
        return new Styles.LatentStyles.LsdException();
    }

    /**
     * Create an instance of {@link TblGridBase }
     * 
     */
    public TblGridBase createTblGridBase() {
        return new TblGridBase();
    }

    /**
     * Create an instance of {@link org.docx4j.wml.Color }
     * 
     */
    public org.docx4j.wml.Color createColor() {
        return new org.docx4j.wml.Color();
    }

    /**
     * Create an instance of {@link CTFramePr }
     * 
     */
    public CTFramePr createCTFramePr() {
        return new CTFramePr();
    }

    /**
     * Create an instance of {@link CTTabStop }
     * 
     */
    public CTTabStop createCTTabStop() {
        return new CTTabStop();
    }

    /**
     * Create an instance of {@link TcPrInner.GridSpan }
     * 
     */
    public TcPrInner.GridSpan createTcPrInnerGridSpan() {
        return new TcPrInner.GridSpan();
    }

    /**
     * Create an instance of {@link org.docx4j.wml.Style.Next }
     * 
     */
    public org.docx4j.wml.Style.Next createStyleNext() {
        return new org.docx4j.wml.Style.Next();
    }

    /**
     * Create an instance of {@link FontPitch }
     * 
     */
    public FontPitch createFontPitch() {
        return new FontPitch();
    }

    /**
     * Create an instance of {@link CTTblPPr }
     * 
     */
    public CTTblPPr createCTTblPPr() {
        return new CTTblPPr();
    }

    /**
     * Create an instance of {@link Numbering.AbstractNum }
     * 
     */
    public Numbering.AbstractNum createNumberingAbstractNum() {
        return new Numbering.AbstractNum();
    }

    /**
     * Create an instance of {@link CTSaveThroughXslt }
     * 
     */
    public CTSaveThroughXslt createCTSaveThroughXslt() {
        return new CTSaveThroughXslt();
    }

    /**
     * Create an instance of {@link CTMarkup }
     * 
     */
    public CTMarkup createCTMarkup() {
        return new CTMarkup();
    }

    /**
     * Create an instance of {@link CTAltChunkPr }
     * 
     */
    public CTAltChunkPr createCTAltChunkPr() {
        return new CTAltChunkPr();
    }

    /**
     * Create an instance of {@link R.DayLong }
     * 
     */
    public R.DayLong createRDayLong() {
        return new R.DayLong();
    }

    /**
     * Create an instance of {@link CTFrame.Sz }
     * 
     */
    public CTFrame.Sz createCTFrameSz() {
        return new CTFrame.Sz();
    }

    /**
     * Create an instance of {@link CTTblPrBase.TblStyle }
     * 
     */
    public CTTblPrBase.TblStyle createCTTblPrBaseTblStyle() {
        return new CTTblPrBase.TblStyle();
    }

    /**
     * Create an instance of {@link CTVerticalJc }
     * 
     */
    public CTVerticalJc createCTVerticalJc() {
        return new CTVerticalJc();
    }

    /**
     * Create an instance of {@link CTLock }
     * 
     */
    public CTLock createCTLock() {
        return new CTLock();
    }

    /**
     * Create an instance of {@link FldChar }
     * 
     */
    public FldChar createFldChar() {
        return new FldChar();
    }

    /**
     * Create an instance of {@link CTCellMergeTrackChange }
     * 
     */
    public CTCellMergeTrackChange createCTCellMergeTrackChange() {
        return new CTCellMergeTrackChange();
    }

    /**
     * Create an instance of {@link CTReadingModeInkLockDown }
     * 
     */
    public CTReadingModeInkLockDown createCTReadingModeInkLockDown() {
        return new CTReadingModeInkLockDown();
    }

    /**
     * Create an instance of {@link RunIns }
     * 
     */
    public RunIns createRunIns() {
        return new RunIns();
    }

    /**
     * Create an instance of {@link CTDocParts }
     * 
     */
    public CTDocParts createCTDocParts() {
        return new CTDocParts();
    }

    /**
     * Create an instance of {@link CTFFDDList.ListEntry }
     * 
     */
    public CTFFDDList.ListEntry createCTFFDDListListEntry() {
        return new CTFFDDList.ListEntry();
    }

    /**
     * Create an instance of {@link CTMailMergeSourceType }
     * 
     */
    public CTMailMergeSourceType createCTMailMergeSourceType() {
        return new CTMailMergeSourceType();
    }

    /**
     * Create an instance of {@link Jc }
     * 
     */
    public Jc createJc() {
        return new Jc();
    }

    /**
     * Create an instance of {@link SdtPr.Equation }
     * 
     */
    public SdtPr.Equation createSdtPrEquation() {
        return new SdtPr.Equation();
    }

    /**
     * Create an instance of {@link CTTextScale }
     * 
     */
    public CTTextScale createCTTextScale() {
        return new CTTextScale();
    }

    /**
     * Create an instance of {@link CTColorSchemeMapping }
     * 
     */
    public CTColorSchemeMapping createCTColorSchemeMapping() {
        return new CTColorSchemeMapping();
    }

    /**
     * Create an instance of {@link CTFrameset }
     * 
     */
    public CTFrameset createCTFrameset() {
        return new CTFrameset();
    }

    /**
     * Create an instance of {@link CTOdso }
     * 
     */
    public CTOdso createCTOdso() {
        return new CTOdso();
    }

    /**
     * Create an instance of {@link CTSettings.DisplayVerticalDrawingGridEvery }
     * 
     */
    public CTSettings.DisplayVerticalDrawingGridEvery createCTSettingsDisplayVerticalDrawingGridEvery() {
        return new CTSettings.DisplayVerticalDrawingGridEvery();
    }

    /**
     * Create an instance of {@link CTFrameset.Sz }
     * 
     */
    public CTFrameset.Sz createCTFramesetSz() {
        return new CTFrameset.Sz();
    }

    /**
     * Create an instance of {@link DocDefaults.PPrDefault }
     * 
     */
    public DocDefaults.PPrDefault createDocDefaultsPPrDefault() {
        return new DocDefaults.PPrDefault();
    }

    /**
     * Create an instance of {@link CTMailMergeDocType }
     * 
     */
    public CTMailMergeDocType createCTMailMergeDocType() {
        return new CTMailMergeDocType();
    }

    /**
     * Create an instance of {@link CTWritingStyle }
     * 
     */
    public CTWritingStyle createCTWritingStyle() {
        return new CTWritingStyle();
    }

    /**
     * Create an instance of {@link Numbering }
     * 
     */
    public Numbering createNumbering() {
        return new Numbering();
    }

    /**
     * Create an instance of {@link CTEastAsianLayout }
     * 
     */
    public CTEastAsianLayout createCTEastAsianLayout() {
        return new CTEastAsianLayout();
    }

    /**
     * Create an instance of {@link CTSettings.ClickAndTypeStyle }
     * 
     */
    public CTSettings.ClickAndTypeStyle createCTSettingsClickAndTypeStyle() {
        return new CTSettings.ClickAndTypeStyle();
    }

    /**
     * Create an instance of {@link CTFFDDList }
     * 
     */
    public CTFFDDList createCTFFDDList() {
        return new CTFFDDList();
    }

    /**
     * Create an instance of {@link R.Separator }
     * 
     */
    public R.Separator createRSeparator() {
        return new R.Separator();
    }

    /**
     * Create an instance of {@link Lvl.Start }
     * 
     */
    public Lvl.Start createLvlStart() {
        return new Lvl.Start();
    }

    /**
     * Create an instance of {@link CTFramesetSplitbar }
     * 
     */
    public CTFramesetSplitbar createCTFramesetSplitbar() {
        return new CTFramesetSplitbar();
    }

    /**
     * Create an instance of {@link SectPr.PgBorders }
     * 
     */
    public SectPr.PgBorders createSectPrPgBorders() {
        return new SectPr.PgBorders();
    }

    /**
     * Create an instance of {@link CTTextboxTightWrap }
     * 
     */
    public CTTextboxTightWrap createCTTextboxTightWrap() {
        return new CTTextboxTightWrap();
    }

    /**
     * Create an instance of {@link PPrBase.OutlineLvl }
     * 
     */
    public PPrBase.OutlineLvl createPPrBaseOutlineLvl() {
        return new PPrBase.OutlineLvl();
    }

    /**
     * Create an instance of {@link CTSettings.BookFoldPrintingSheets }
     * 
     */
    public CTSettings.BookFoldPrintingSheets createCTSettingsBookFoldPrintingSheets() {
        return new CTSettings.BookFoldPrintingSheets();
    }

    /**
     * Create an instance of {@link NumFmt }
     * 
     */
    public NumFmt createNumFmt() {
        return new NumFmt();
    }

    /**
     * Create an instance of {@link TcPr }
     * 
     */
    public TcPr createTcPr() {
        return new TcPr();
    }

    /**
     * Create an instance of {@link R.LastRenderedPageBreak }
     * 
     */
    public R.LastRenderedPageBreak createRLastRenderedPageBreak() {
        return new R.LastRenderedPageBreak();
    }

    /**
     * Create an instance of {@link CTFtnProps }
     * 
     */
    public CTFtnProps createCTFtnProps() {
        return new CTFtnProps();
    }

    /**
     * Create an instance of {@link PPrBase.TextAlignment }
     * 
     */
    public PPrBase.TextAlignment createPPrBaseTextAlignment() {
        return new PPrBase.TextAlignment();
    }

    /**
     * Create an instance of {@link CTRubyAlign }
     * 
     */
    public CTRubyAlign createCTRubyAlign() {
        return new CTRubyAlign();
    }

    /**
     * Create an instance of {@link CTSdtListItem }
     * 
     */
    public CTSdtListItem createCTSdtListItem() {
        return new CTSdtListItem();
    }

    /**
     * Create an instance of {@link ParaRPrChange }
     * 
     */
    public ParaRPrChange createParaRPrChange() {
        return new ParaRPrChange();
    }

    /**
     * Create an instance of {@link CTPlaceholder }
     * 
     */
    public CTPlaceholder createCTPlaceholder() {
        return new CTPlaceholder();
    }

    /**
     * Create an instance of {@link CTPlaceholder.DocPart }
     * 
     */
    public CTPlaceholder.DocPart createCTPlaceholderDocPart() {
        return new CTPlaceholder.DocPart();
    }

    /**
     * Create an instance of {@link CTHeight }
     * 
     */
    public CTHeight createCTHeight() {
        return new CTHeight();
    }

    /**
     * Create an instance of {@link FooterReference }
     * 
     */
    public FooterReference createFooterReference() {
        return new FooterReference();
    }

    /**
     * Create an instance of {@link CTFFDDList.Default }
     * 
     */
    public CTFFDDList.Default createCTFFDDListDefault() {
        return new CTFFDDList.Default();
    }

    /**
     * Create an instance of {@link CTPerm }
     * 
     */
    public CTPerm createCTPerm() {
        return new CTPerm();
    }

    /**
     * Create an instance of {@link BooleanDefaultTrue }
     * 
     */
    public BooleanDefaultTrue createBooleanDefaultTrue() {
        return new BooleanDefaultTrue();
    }

    /**
     * Create an instance of {@link CTDocPartBehaviors }
     * 
     */
    public CTDocPartBehaviors createCTDocPartBehaviors() {
        return new CTDocPartBehaviors();
    }

    /**
     * Create an instance of {@link CTTblPrChange }
     * 
     */
    public CTTblPrChange createCTTblPrChange() {
        return new CTTblPrChange();
    }

    /**
     * Create an instance of {@link CTRel }