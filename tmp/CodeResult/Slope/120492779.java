package biz.altsoft.wayplot.way;

import biz.altsoft.wayplot.Main;
import biz.altsoft.wayplot.way.chars.BallastThickness;
import biz.altsoft.wayplot.way.chars.BallastType;
import biz.altsoft.wayplot.way.chars.CoercedRailWear;
import biz.altsoft.wayplot.way.chars.Construction;
import biz.altsoft.wayplot.way.chars.RailsGroup;
import biz.altsoft.wayplot.way.chars.RailsType;
import biz.altsoft.wayplot.way.chars.SleepersType;
import biz.altsoft.wayplot.way.chars.CurveDirection;
import biz.altsoft.wayplot.way.chars.FasteningType;
import biz.altsoft.wayplot.way.chars.RailsLength;
import biz.altsoft.wayplot.way.chars.RailsLaying;
import biz.altsoft.wayplot.way.chars.RepairType;
import biz.altsoft.wayplot.way.chars.SideRailWear;
import biz.altsoft.wayplot.way.chars.SleepersDensity;
import biz.altsoft.wayplot.way.chars.SleepersMaterial;
import biz.altsoft.wayplot.way.chars.SlopeDirection;
import biz.altsoft.wayplot.way.chars.SpacerLayer;
import biz.altsoft.wayplot.way.chars.StatusCoordinationType;
import biz.altsoft.wayplot.way.chars.SwitchDirection;
import biz.altsoft.wayplot.way.chars.SwitchMeeting;
import biz.altsoft.wayplot.way.chars.Thermostrengthening;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * 
 * @author pk
 */
public class WayPart implements Cloneable
{
    private static final Logger LOGGER = Logger.getLogger(Main.MODEL_LOGGER_NAME);
    private int start;
    private int startKm;
    private int startM;
    private int endKm;
    private int endM;
    private int length;
    private double passedTonnage;
    private RailsInfo railsInfo;
    private SleepersInfo sleepersInfo;
    private CurveInfo curveInfo;
    private SpeedInfo speedInfo;
    private BallastInfo ballastInfo;
    private double geotextileLength;
    private double polystyreneLength;
    private double slope;
    private SlopeDirection slopeDirection;
    private double axisLoad;
    private double freightDensity;
    private String wayClass;
    private String wayGroup;
    private String wayCategory;
    private Station station;
    private Construction construction;
    private SwitchDirection switchDirection;
    private SwitchMeeting switchMeeting;
    private String switchID;
    private RepairType repairType;
    private Calendar repairDate, lastRepairDate;
    private double repairNeed;
    private String wayDistrict;
    private String railRoad;
    private String direction, directionCaption, directionCode;
    private String span;
    private String wayNumber, wayNumberCaption;
    private ComplexAssessment complexAssessment;
    private RepairNeedFigures repairNeedFigures;
        
    private double repairNeedOldRails;  //потребность на старых рельсах
    private BorderUnit borderUnit;      // границы участков
    private StatusCoordinationType statusCoordination; //Статус согласования 
    private int OcalYear;               //год ремонта
    private double beforeTonnage;       //тоннаж до переукладки
    private double afterTonnage;        //тоннаж после переукладки
            
    //дополнительные поля из плана
    private int InServiceYear;
    private String ZkrLengs, ZkrPr023, ZkrPr311, ZkrPr313, ZkrPr317, ZkrPr319;    
    private Calendar ZtrCdatK, ZtrCdatN;
    private String ZkrPr310;
    private double ZtrfskPr, ZkrKf003, ZkrKf004, ZkrKf010;

    //еще одна порция доп полей 19.10.11
    private double ZkrKf014, ZkrKf015, ZkrKf006, ZkrKf005, ZkrKf109, ZworPr;
    private String /*ZkrPr312,*/ ZtrcrsDr, Ztrcnots;
    private Calendar ZdatUkl;
        
    private int OtherPred; //принадлежность другому ПЧ

    //21.01.12
    private double ZkrKf207,ZkrKf307; //
    private String ZpmPr031;
    
    public String getZpmPr031()
    {
        return ZpmPr031;
    }

    public void setZpmPr031(String ZpmPr031)
    {
        this.ZpmPr031 = ZpmPr031;
    }

    public double getZkrKf207()
    {
        return ZkrKf207;
    }

    public void setZkrKf207(double ZkrKf207)
    {
        this.ZkrKf207 = ZkrKf207;
    }

    public double getZkrKf307()
    {
        return ZkrKf307;
    }

    public void setZkrKf307(double ZkrKf307)
    {
        this.ZkrKf307 = ZkrKf307;
    }

    public int getOtherPred()
    {
        return OtherPred;
    }

    public void setOtherPred(int OtherPred)
    {
        this.OtherPred = OtherPred;
    }

    public double getAfterTonnage()
    {
        return afterTonnage;
    }

    public void setAfterTonnage(double afterTonnage)
    {
        this.afterTonnage = afterTonnage;
    }

    public double getBeforeTonnage()
    {
        return beforeTonnage;
    }

    public void setBeforeTonnage(double beforeTonnage)
    {
        this.beforeTonnage = beforeTonnage;
    }

    
    public StatusCoordinationType getStatusCoordination()
    {
        return statusCoordination;
    }
    
    public int getOcalYear()
    {
        return OcalYear;
    }

    public void setOcalYear(int OcalYear)
    {
        this.OcalYear = OcalYear;
    }
    public void setStatusCoordination(StatusCoordinationType statusCoordination)
    {
        this.statusCoordination = statusCoordination;
    }
    
    public Calendar getZdatUkl()
    {
        return ZdatUkl;
    }

    public void setZdatUkl(Calendar ZdatUkl)
    {
        this.ZdatUkl = ZdatUkl;
    }

    public double getZkrKf005()
    {
        return ZkrKf005;
    }

    public void setZkrKf005(double ZkrKf005)
    {
        this.ZkrKf005 = ZkrKf005;
    }

    public double getZkrKf006()
    {
        return ZkrKf006;
    }

    public void setZkrKf006(double ZkrKf006)
    {
        this.ZkrKf006 = ZkrKf006;
    }

    public double getZkrKf014()
    {
        return ZkrKf014;
    }

    public void setZkrKf014(double ZkrKf014)
    {
        this.ZkrKf014 = ZkrKf014;
    }

    public double getZkrKf015()
    {
        return ZkrKf015;
    }

    public void setZkrKf015(double ZkrKf015)
    {
        this.ZkrKf015 = ZkrKf015;
    }

    public double getZkrKf109()
    {
        return ZkrKf109;
    }

    public void setZkrKf109(double ZkrKf109)
    {
        this.ZkrKf109 = ZkrKf109;
    }

//    public String getZkrPr312()
//    {
//        return ZkrPr312;
//    }
//
//    public void setZkrPr312(String ZkrPr312)
//    {
//        this.ZkrPr312 = ZkrPr312;
//    }

    public String getZtrcnots()
    {
        return Ztrcnots;
    }

    public void setZtrcnots(String Ztrcnots)
    {
        this.Ztrcnots = Ztrcnots;
    }

    public String getZtrcrsDr()
    {
        return ZtrcrsDr;
    }

    public void setZtrcrsDr(String ZtrcrsDr)
    {
        this.ZtrcrsDr = ZtrcrsDr;
    }

    public double getZworPr()
    {
        return ZworPr;
    }

    public void setZworPr(double ZworPr)
    {
        this.ZworPr = ZworPr;
    }



    public double getZkrKf003()
    {
        return ZkrKf003;
    }

    public void setZkrKf003(double ZkrKf003)
    {
        this.ZkrKf003 = ZkrKf003;
    }

    public double getZkrKf004()
    {
        return ZkrKf004;
    }

    public void setZkrKf004(double ZkrKf004)
    {
        this.ZkrKf004 = ZkrKf004;
    }

    public double getZkrKf010()
    {
        return ZkrKf010;
    }

    public void setZkrKf010(double ZkrKf010)
    {
        this.ZkrKf010 = ZkrKf010;
    }

    public String getZkrPr310()
    {
        return ZkrPr310;
    }

    public void setZkrPr310(String ZkrPr310)
    {
        this.ZkrPr310 = ZkrPr310;
    }

    public double getZtrfskPr()
    {
        return ZtrfskPr;
    }

    public void setZtrfskPr(double ZtrfskPr)
    {
        this.ZtrfskPr = ZtrfskPr;
    }

    public String getZkrLengs()
    {
        return ZkrLengs;
    }

    public int getInServiceYear()
    {
        return InServiceYear;
    }

    public String getZkrPr023()
    {
        return ZkrPr023;
    }

    public String getZkrPr311()
    {
        return ZkrPr311;
    }

    public String getZkrPr313()
    {
        return ZkrPr313;
    }

    public String getZkrPr317()
    {
        return ZkrPr317;
    }

    public String getZkrPr319()
    {
        return ZkrPr319;
    }

    public Calendar getZtrCdatK()
    {
        return ZtrCdatK;
    }

    public Calendar getZtrCdatN()
    {
        return ZtrCdatN;
    }
    
    public void setZtrCdatK(Calendar ZtrCdatK)
    {
        this.ZtrCdatK = ZtrCdatK;
    }
    public void setZkrLengs(String ZkrLengs)
    {
        this.ZkrLengs = ZkrLengs;
    }

    public void setInServiceYear(int inServiceYear)
    {
        this.InServiceYear = inServiceYear;
    }

    public void setZkrPr023(String ZkrPr023)
    {
        this.ZkrPr023 = ZkrPr023;
    }

    public void setZkrPr311(String ZkrPr311)
    {
        this.ZkrPr311 = ZkrPr311;
    }

    public void setZkrPr313(String ZkrPr313)
    {
        this.ZkrPr313 = ZkrPr313;
    }

    public void setZkrPr317(String ZkrPr317)
    {
        this.ZkrPr317 = ZkrPr317;
    }

    public void setZkrPr319(String ZkrPr319)
    {
        this.ZkrPr319 = ZkrPr319;
    }

    public void setZtrCdatN(Calendar ZtrCdatN)
    {
        this.ZtrCdatN = ZtrCdatN;
    }
    
    public WayPart()
    {
    }

    public WayPart(int start, int length, int startKm, int startM, int endKm, int endM)
    {
        this.start = start;
        this.length = length;
        this.startKm = startKm;
        this.startM = startM;
        this.endKm = endKm;
        this.endM = endM;
    }

    public WayPart(int start, int length, int startKm, int startM, int endKm, int endM, RailsType railsType,
            SleepersType sleepersType, int curve, double slope)
    {
        this(start, length, startKm, startM, endKm, endM);
        this.railsInfo = new RailsInfo();
        this.railsInfo.setRailsType(railsType);
        this.sleepersInfo = new SleepersInfo();
        this.sleepersInfo.setSleepersType(sleepersType);
        this.curveInfo = new CurveInfo();
        this.curveInfo.setCurveRadius(curve);
        this.slope = slope;
    }

    public void assignPropertiesFrom(WayPart src)
    {
        this.wayDistrict = src.wayDistrict;
        this.railRoad = src.railRoad;
        this.direction = src.direction;
        this.directionCaption = src.directionCaption;
        this.directionCode = src.directionCode;
        this.span = src.span;
        this.wayNumber = src.wayNumber;
        this.wayNumberCaption = src.wayNumberCaption;
        this.wayClass = src.wayClass;
        this.wayGroup = src.wayGroup;
        this.wayCategory = src.wayCategory;
        this.railsInfo = (RailsInfo) (src.railsInfo == null ? null : src.railsInfo.clone());
        this.sleepersInfo = (SleepersInfo) (src.sleepersInfo == null ? null : src.sleepersInfo.clone());
        this.slope = src.slope;
        this.slopeDirection = src.slopeDirection;
        this.axisLoad = src.axisLoad;
        this.passedTonnage = src.passedTonnage;
        this.freightDensity = src.freightDensity;
        this.curveInfo = (CurveInfo) (src.curveInfo == null ? null : src.curveInfo.clone());
        this.speedInfo = (SpeedInfo) (src.speedInfo == null ? null : src.speedInfo.clone());
        this.station = (Station) (src.station == null ? null : src.station.clone());
        this.ballastInfo = (BallastInfo) (src.ballastInfo == null ? null : src.ballastInfo.clone());
        this.geotextileLength = src.geotextileLength;
        this.polystyreneLength = src.polystyreneLength;
        this.construction = src.construction;
        this.switchDirection = src.switchDirection;
        this.switchMeeting = src.switchMeeting;
        this.switchID = src.switchID;
        this.repairType = src.repairType;
        this.repairDate = (Calendar) (src.repairDate == null ? null : src.repairDate.clone());
        this.lastRepairDate = (Calendar) (src.lastRepairDate == null ? null : src.lastRepairDate.clone());
        this.repairNeed = src.repairNeed;
        this.repairNeedOldRails = src.repairNeedOldRails;
        this.complexAssessment = (ComplexAssessment) (src.complexAssessment == null ? null : src.complexAssessment
                .clone());
        this.InServiceYear = src.getInServiceYear(); //лет в эксплуатации
        this.ZdatUkl = (Calendar) (src.ZdatUkl == null ? null : src.ZdatUkl.clone());
    }

    @Override
    public WayPart clone()
    {
        try
        {
            WayPart clone = (WayPart) super.clone();
            clone.ballastInfo = ballastInfo == null ? null : ballastInfo.clone();
            clone.complexAssessment = complexAssessment == null ? null : complexAssessment.clone();
            clone.curveInfo = curveInfo == null ? null : curveInfo.clone();
            clone.railsInfo = railsInfo == null ? null : railsInfo.clone();
            clone.sleepersInfo = sleepersInfo == null ? null : sleepersInfo.clone();
            clone.speedInfo = speedInfo == null ? null : speedInfo.clone();
            return clone;
        } catch (CloneNotSupportedException ex)
        {
            throw new InternalError();
        }

    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final WayPart other = (WayPart) obj;
        if (this.start != other.start)
            return false;
        if (this.startKm != other.startKm)
            return false;
        if (this.startM != other.startM)
            return false;
        if (this.endKm != other.endKm)
            return false;
        if (this.endM != other.endM)
            return false;
        if (this.length != other.length)
            return false;
        if (Double.doubleToLongBits(this.passedTonnage) != Double.doubleToLongBits(other.passedTonnage))
            return false;
        if (this.railsInfo != other.railsInfo && (this.railsInfo == null || !this.railsInfo.equals(other.railsInfo)))
            return false;
        if (this.sleepersInfo != other.sleepersInfo
                && (this.sleepersInfo == null || !this.sleepersInfo.equals(other.sleepersInfo)))
            return false;
        if (this.curveInfo != other.curveInfo && (this.curveInfo == null || !this.curveInfo.equals(other.curveInfo)))
            return false;
        if (this.speedInfo != other.speedInfo && (this.speedInfo == null || !this.speedInfo.equals(other.speedInfo)))
            return false;
        if (this.ballastInfo != other.ballastInfo
                && (this.ballastInfo == null || !this.ballastInfo.equals(other.ballastInfo)))
            return false;
        if (Double.doubleToLongBits(this.geotextileLength) != Double.doubleToLongBits(other.geotextileLength))
            return false;
        if (Double.doubleToLongBits(this.polystyreneLength) != Double.doubleToLongBits(other.polystyreneLength))
            return false;
        if (Double.doubleToLongBits(this.slope) != Double.doubleToLongBits(other.slope))
            return false;
        if (this.slopeDirection != other.slopeDirection)
            return false;
        if (Double.doubleToLongBits(this.axisLoad) != Double.doubleToLongBits(other.axisLoad))
            return false;
        if (Double.doubleToLongBits(this.freightDensity) != Double.doubleToLongBits(other.freightDensity))
            return false;
        if ((this.wayClass == null) ? (other.wayClass != null) : !this.wayClass.equals(other.wayClass))
            return false;
        if ((this.wayGroup == null) ? (other.wayGroup != null) : !this.wayGroup.equals(other.wayGroup))
            return false;
        if ((this.wayCategory == null) ? (other.wayCategory != null) : !this.wayCategory.equals(other.wayCategory))
            return false;
        if (this.station != other.station && (this.station == null || !this.station.equals(other.station)))
            return false;
        if (this.construction != other.construction)
            return false;
        if (this.switchDirection != other.switchDirection)
            return false;
        if (this.switchMeeting != other.switchMeeting)
            return false;
        if ((this.switchID == null) ? (other.switchID != null) : !this.switchID.equals(other.switchID))
            return false;
        if (this.repairType != other.repairType)
            return false;
        if (this.repairDate != other.repairDate
                && (this.repairDate == null || !this.repairDate.equals(other.repairDate)))
            return false;
        if (this.lastRepairDate != other.lastRepairDate
                && (this.lastRepairDate == null || !this.lastRepairDate.equals(other.lastRepairDate)))
            return false;
        if (Double.doubleToLongBits(this.repairNeed) != Double.doubleToLongBits(other.repairNeed))
            return false;
        if ((this.wayDistrict == null) ? (other.wayDistrict != null) : !this.wayDistrict.equals(other.wayDistrict))
            return false;
        if ((this.railRoad == null) ? (other.railRoad != null) : !this.railRoad.equals(other.railRoad))
            return false;
        if ((this.direction == null) ? (other.direction != null) : !this.direction.equals(other.direction))
            return false;
        if ((this.directionCaption == null) ? (other.directionCaption != null) : !this.directionCaption
                .equals(other.directionCaption))
            return false;
        if ((this.directionCode == null) ? (other.directionCode != null) : !this.directionCode
                .equals(other.directionCode))
            return false;
        if ((this.span == null) ? (other.span != null) : !this.span.equals(other.span))
            return false;
        if ((this.wayNumber == null) ? (other.wayNumber != null) : !this.wayNumber.equals(other.wayNumber))
            return false;
        if ((this.wayNumberCaption == null) ? (other.wayNumberCaption != null) : !this.wayNumberCaption
                .equals(other.wayNumberCaption))
            return false;
        if (this.complexAssessment != other.complexAssessment
                && (this.complexAssessment == null || !this.complexAssessment.equals(other.complexAssessment)))
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 83 * hash + this.start;
        hash = 83 * hash + this.startKm;
        hash = 83 * hash + this.startM;
        hash = 83 * hash + this.endKm;
        hash = 83 * hash + this.endM;
        hash = 83 * hash + this.length;
        hash = 83
                * hash
                + (int) (Double.doubleToLongBits(this.passedTonnage) ^ (Double.doubleToLongBits(this.passedTonnage) >>> 32));
        hash = 83 * hash + (this.railsInfo != null ? this.railsInfo.hashCode() : 0);
        hash = 83 * hash + (this.sleepersInfo != null ? this.sleepersInfo.hashCode() : 0);
        hash = 83 * hash + (this.curveInfo != null ? this.curveInfo.hashCode() : 0);
        hash = 83 * hash + (this.speedInfo != null ? this.speedInfo.hashCode() : 0);
        hash = 83 * hash + (this.ballastInfo != null ? this.ballastInfo.hashCode() : 0);
        hash = 83
                * hash
                + (int) (Double.doubleToLongBits(this.geotextileLength) ^ (Double
                        .doubleToLongBits(this.geotextileLength) >>> 32));
        hash = 83
                * hash
                + (int) (Double.doubleToLongBits(this.polystyreneLength) ^ (Double
                        .doubleToLongBits(this.polystyreneLength) >>> 32));
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.slope) ^ (Double.doubleToLongBits(this.slope) >>> 32));
        hash = 83 * hash + (this.slopeDirection != null ? this.slopeDirection.hashCode() : 0);
        hash = 83 * hash
                + (int) (Double.doubleToLongBits(this.axisLoad) ^ (Double.doubleToLongBits(this.axisLoad) >>> 32));
        hash = 83
                * hash
                + (int) (Double.doubleToLongBits(this.freightDensity) ^ (Double.doubleToLongBits(this.freightDensity) >>> 32));
        hash = 83 * hash + (this.wayClass != null ? this.wayClass.hashCode() : 0);
        hash = 83 * hash + (this.wayGroup != null ? this.wayGroup.hashCode() : 0);
        hash = 83 * hash + (this.wayCategory != null ? this.wayCategory.hashCode() : 0);
        hash = 83 * hash + (this.station != null ? this.station.hashCode() : 0);
        hash = 83 * hash + (this.construction != null ? this.construction.hashCode() : 0);
        hash = 83 * hash + (this.switchDirection != null ? this.switchDirection.hashCode() : 0);
        hash = 83 * hash + (this.switchMeeting != null ? this.switchMeeting.hashCode() : 0);
        hash = 83 * hash + (this.switchID != null ? this.switchID.hashCode() : 0);
        hash = 83 * hash + (this.repairType != null ? this.repairType.hashCode() : 0);
        hash = 83 * hash + (this.repairDate != null ? this.repairDate.hashCode() : 0);
        hash = 83 * hash + (this.lastRepairDate != null ? this.lastRepairDate.hashCode() : 0);
        hash = 83 * hash
                + (int) (Double.doubleToLongBits(this.repairNeed) ^ (Double.doubleToLongBits(this.repairNeed) >>> 32));
        hash = 83 * hash + (this.wayDistrict != null ? this.wayDistrict.hashCode() : 0);
        hash = 83 * hash + (this.railRoad != null ? this.railRoad.hashCode() : 0);
        hash = 83 * hash + (this.direction != null ? this.direction.hashCode() : 0);
        hash = 83 * hash + (this.directionCaption != null ? this.directionCaption.hashCode() : 0);
        hash = 83 * hash + (this.directionCode != null ? this.directionCode.hashCode() : 0);
        hash = 83 * hash + (this.span != null ? this.span.hashCode() : 0);
        hash = 83 * hash + (this.wayNumber != null ? this.wayNumber.hashCode() : 0);
        hash = 83 * hash + (this.wayNumberCaption != null ? this.wayNumberCaption.hashCode() : 0);
        hash = 83 * hash + (this.complexAssessment != null ? this.complexAssessment.hashCode() : 0);
        return hash;
    }

    public int getEnd()
    {
        return start + length;
    }

    public int getStart()
    {
        return start;
    }

    public int getEndKm()
    {
        return endKm;
    }

    public int getEndM()
    {
        return endM;
    }

    public int getStartKm()
    {
        return startKm;
    }

    public int getStartM()
    {
        return startM;
    }

    public int getLength()
    {
        return length;
    }

    public void setStart(int start)
    {
        this.start = start;
    }

    public void setEndKm(int endKm)
    {
        this.endKm = endKm;
    }

    public void setEndM(int endM)
    {
        this.endM = endM;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public void setStartKm(int startKm)
    {
        this.startKm = startKm;
    }

    public void setStartM(int startM)
    {
        this.startM = startM;
    }

    public double getPassedTonnage()
    {
        return passedTonnage;
    }

    public void setPassedTonnage(double passedTonnage)
    {
        this.passedTonnage = passedTonnage;
    }

    public RailsInfo getRailsInfo()
    {
        return railsInfo;
    }

    public void setRailsInfo(RailsInfo railsInfo)
    {
        this.railsInfo = railsInfo;
    }

    public SleepersInfo getSleepersInfo()
    {
        return sleepersInfo;
    }

    public void setSleepersInfo(SleepersInfo sleepersInfo)
    {
        this.sleepersInfo = sleepersInfo;
    }

    public CurveInfo getCurveInfo()
    {
        return curveInfo;
    }

    public void setCurveInfo(CurveInfo curveInfo)
    {
        this.curveInfo = curveInfo;
    }

    public SpeedInfo getSpeedInfo()
    {
        return speedInfo;
    }

    public void setSpeedInfo(SpeedInfo speedInfo)
    {
        this.speedInfo = speedInfo;
    }

    public double getSlope()
    {
        return slope;
    }

    public void setSlope(double slope)
    {
        this.slope = slope;
    }

    /**
     * Направление уклона.
     * 
     * Значения: -1 - спуск, 1 - подъем, 0 - площадка.
     * 
     * @return направление уклона.
     */
    public SlopeDirection getSlopeDirection()
    {
        return slopeDirection;
    }

    public void setSlopeDirection(SlopeDirection slopeDirection)
    {
        this.slopeDirection = slopeDirection;
    }

    public double getAxisLoad()
    {
        return axisLoad;
    }

    public void setAxisLoad(double axisLoad)
    {
        this.axisLoad = axisLoad;
    }

    public double getFreightDensity()
    {
        return freightDensity;
    }

    public void setFreightDensity(double freightDensity)
    {
        this.freightDensity = freightDensity;
    }

    public String getWayClass()
    {
        return wayClass;
    }

    public void setWayClass(String wayClass)
    {
        this.wayClass = wayClass;
    }

    public String getWayGroup()
    {
        return wayGroup;
    }

    public void setWayGroup(String wayGroup)
    {
        this.wayGroup = wayGroup;
    }

    public String getWayCategory()
    {
        return wayCategory;
    }

    public void setWayCategory(String wayCategory)
    {
        this.wayCategory = wayCategory;
    }

    public Station getStation()
    {
        return station;
    }

    public void setStation(Station station)
    {
        this.station = station;
    }

    public BallastInfo getBallastInfo()
    {
        return ballastInfo;
    }

    public void setBallastInfo(BallastInfo ballastInfo)
    {
        this.ballastInfo = ballastInfo;
    }

    public double getGeotextileLength()
    {
        return geotextileLength;
    }

    public void setGeotextileLength(double geotextileLength)
    {
        this.geotextileLength = geotextileLength;
    }

    public double getPolystyreneLength()
    {
        return polystyreneLength;
    }

    public void setPolystyreneLength(double polystyreneLength)
    {
        this.polystyreneLength = polystyreneLength;
    }

    public Construction getConstruction()
    {
        return construction;
    }

    public void setConstruction(Construction construction)
    {
        this.construction = construction;
    }

    public SwitchDirection getSwitchDirection()
    {
        return switchDirection;
    }

    public void setSwitchDirection(SwitchDirection switchDirection)
    {
        this.switchDirection = switchDirection;
    }

    public SwitchMeeting getSwitchMeeting()
    {
        return switchMeeting;
    }

    public void setSwitchMeeting(SwitchMeeting switchMeeting)
    {
        this.switchMeeting = switchMeeting;
    }

    public RepairType getRepairType()
    {
        return repairType;
    }

    public String getSwitchID()
    {
        return switchID;
    }

    public void setSwitchID(String switchID)
    {
        this.switchID = switchID;
    }

    public void setRepairType(RepairType repairType)
    {
        this.repairType = repairType;
    }

    public Calendar getRepairDate()
    {
        return repairDate;
    }

    public void setRepairDate(Calendar repairDate)
    {
        this.repairDate = repairDate;
    }

    public Calendar getLastRepairDate()
    {
        return lastRepairDate;
    }

    public void setLastRepairDate(Calendar lastRepairDate)
    {
        this.lastRepairDate = lastRepairDate;
    }

    public double getRepairNeed()
    {
        return repairNeed;
    }

    public void setRepairNeed(double repairNeed)
    {
        this.repairNeed = repairNeed;
    }

    public String getWayDistrict()
    {
        return wayDistrict;
    }

    public void setWayDistrict(String wayDistrict)
    {
        this.wayDistrict = wayDistrict;
    }

    public String getDirection()
    {
        return direction;
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
    }

    public String getDirectionCaption()
    {
        return directionCaption;
    }

    public void setDirectionCaption(String directionCaption)
    {
        this.directionCaption = directionCaption;
    }

    public String getDirectionCode()
    {
        return directionCode;
    }

    public void setDirectionCode(String directionCode)
    {
        this.directionCode = directionCode;
    }

    public String getRailRoad()
    {
        return railRoad;
    }

    public void setRailRoad(String railRoad)
    {
        this.railRoad = railRoad;
    }

    public String getSpan()
    {
        return span;
    }

    public void setSpan(String span)
    {
        this.span = span;
    }

    public String getWayNumber()
    {
        return wayNumber;
    }

    public void setWayNumber(String wayNumber)
    {
        this.wayNumber = wayNumber;
    }

    public String getWayNumberCaption()
    {
        return wayNumberCaption;
    }

    public void setWayNumberCaption(String wayNumberCaption)
    {
        this.wayNumberCaption = wayNumberCaption;
    }

    public ComplexAssessment getComplexAssessment()
    {
        return complexAssessment;
    }

    public void setComplexAssessment(ComplexAssessment complexAssessment)
    {
        this.complexAssessment = complexAssessment;
    }

    public RepairNeedFigures getRepairNeedFigures()
    {
        return repairNeedFigures;
    }

    public void setRepairNeedFigures(RepairNeedFigures repairNeedFigures)
    {
        this.repairNeedFigures = repairNeedFigures;
    }

    public BorderUnit getBorderUnit()
    {
        return borderUnit;
    }

    public void setBorderUnit(BorderUnit borderUnit)
    {
        this.borderUnit = borderUnit;
    }

    public double getRepairNeedOldRails()
    {
        return repairNeedOldRails;
    }

    public void setRepairNeedOldRails(double repairNeedOldRails)
    {
        this.repairNeedOldRails = repairNeedOldRails;
    }

    public static class Station implements Cloneable
    {
        private String name;
        private int entrySwitchKm;
        private int entrySwitchM;
        private int exitSwitchKm;
        private int exitSwitchM;
        private int linkageKm;
        private int linkageM;

        public int getEntrySwitchKm()
        {
            return entrySwitchKm;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Station other = (Station) obj;
            if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
                return false;
            if (this.entrySwitchKm != other.entrySwitchKm)
                return false;
            if (this.entrySwitchM != other.entrySwitchM)
                return false;
            if (this.exitSwitchKm != other.exitSwitchKm)
                return false;
            if (this.exitSwitchM != other.exitSwitchM)
                return false;
            if (this.linkageKm != other.linkageKm)
                return false;
            if (this.linkageM != other.linkageM)
                return false;
            return true;
        }

        @Override
        public int hashCode()
        {
            int hash = 7;
            hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0);
            hash = 79 * hash + this.entrySwitchKm;
            hash = 79 * hash + this.entrySwitchM;
            hash = 79 * hash + this.exitSwitchKm;
            hash = 79 * hash + this.exitSwitchM;
            hash = 79 * hash + this.linkageKm;
            hash = 79 * hash + this.linkageM;
            return hash;
        }

        @Override
        public Station clone()
        {
            try
            {
                return (Station) super.clone();
            } catch (CloneNotSupportedException ex)
            {
                throw new InternalError(ex.toString());
            }
        }

        public void setEntrySwitchKm(int entrySwitchKm)
        {
            this.entrySwitchKm = entrySwitchKm;
        }

        public int getEntrySwitchM()
        {
            return entrySwitchM;
        }

        public void setEntrySwitchM(int entrySwitchM)
        {
            this.entrySwitchM = entrySwitchM;
        }

        public int getExitSwitchKm()
        {
            return exitSwitchKm;
        }

        public void setExitSwitchKm(int exitSwitchKm)
        {
            this.exitSwitchKm = exitSwitchKm;
        }

        public int getExitSwitchM()
        {
            return exitSwitchM;
        }

        public void setExitSwitchM(int exitSwitchM)
        {
            this.exitSwitchM = exitSwitchM;
        }

        public int getLinkageKm()
        {
            return linkageKm;
        }

        public void setLinkageKm(int linkageKm)
        {
            this.linkageKm = linkageKm;
        }

        public int getLinkageM()
        {
            return linkageM;
        }

        public void setLinkageM(int linkageM)
        {
            this.linkageM = linkageM;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }


    }

    public static class ComplexAssessment implements Cloneable
    {
        private String cocGr, cocGp, cocKr, cocRl, csk, csh, cbl, czp, cis, cust, cocK;

        @Override
        public ComplexAssessment clone()
        {
            try
            {
                return (ComplexAssessment) super.clone();
            } catch (CloneNotSupportedException ex)
            {
                throw new InternalError("clone not supported"); // NOI18N
            }
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final ComplexAssessment other = (ComplexAssessment) obj;
            if ((this.cocGr == null) ? (other.cocGr != null) : !this.cocGr.equals(other.cocGr))
                return false;
            if ((this.cocGp == null) ? (other.cocGp != null) : !this.cocGp.equals(other.cocGp))
                return false;
            if ((this.cocKr == null) ? (other.cocKr != null) : !this.cocKr.equals(other.cocKr))
                return false;
            if ((this.cocRl == null) ? (other.cocRl != null) : !this.cocRl.equals(other.cocRl))
                return false;
            if ((this.csk == null) ? (other.csk != null) : !this.csk.equals(other.csk))
                return false;
            if ((this.csh == null) ? (other.csh != null) : !this.csh.equals(other.csh))
                return false;
            if ((this.cbl == null) ? (other.cbl != null) : !this.cbl.equals(other.cbl))
                return false;
            if ((this.czp == null) ? (other.czp != null) : !this.czp.equals(other.czp))
                return false;
            if ((this.cis == null) ? (other.cis != null) : !this.cis.equals(other.cis))
                return false;
            if ((this.cust == null) ? (other.cust != null) : !this.cust.equals(other.cust))
                return false;
            if ((this.cocK == null) ? (other.cocK != null) : !this.cocK.equals(other.cocK))
                return false;
            return true;
        }

        @Override
        public int hashCode()
        {
            int hash = 7;
            hash = 71 * hash + (this.cocGr != null ? this.cocGr.hashCode() : 0);
            hash = 71 * hash + (this.cocGp != null ? this.cocGp.hashCode() : 0);
            hash = 71 * hash + (this.cocKr != null ? this.cocKr.hashCode() : 0);
            hash = 71 * hash + (this.cocRl != null ? this.cocRl.hashCode() : 0);
            hash = 71 * hash + (this.csk != null ? this.csk.hashCode() : 0);
            hash = 71 * hash + (this.csh != null ? this.csh.hashCode() : 0);
            hash = 71 * hash + (this.cbl != null ? this.cbl.hashCode() : 0);
            hash = 71 * hash + (this.czp != null ? this.czp.hashCode() : 0);
            hash = 71 * hash + (this.cis != null ? this.cis.hashCode() : 0);
            hash = 71 * hash + (this.cust != null ? this.cust.hashCode() : 0);
            hash = 71 * hash + (this.cocK != null ? this.cocK.hashCode() : 0);
            return hash;
        }

        public String getCbl()
        {
            return cbl;
        }

        public void setCbl(String cbl)
        {
            this.cbl = cbl;
        }

        public String getCis()
        {
            return cis;
        }

        public void setCis(String cis)
        {
            this.cis = cis;
        }

        public String getCocGp()
        {
            return cocGp;
        }

        public void setCocGp(String cocGp)
        {
            this.cocGp = cocGp;
        }

        public String getCocGr()
        {
            return cocGr;
        }

        public void setCocGr(String cocGr)
        {
            this.cocGr = cocGr;
        }

        public String getCocK()
        {
            return cocK;
        }

        public void setCocK(String cocK)
        {
            this.cocK = cocK;
        }

        public String getCocKr()
        {
            return cocKr;
        }

        public void setCocKr(String cocKr)
        {
            this.cocKr = cocKr;
        }

        public String getCocRl()
        {
            return cocRl;
        }

        public void setCocRl(String cocRl)
        {
            this.cocRl = cocRl;
        }

        public String getCsh()
        {
            return csh;
        }

        public void setCsh(String csh)
        {
            this.csh = csh;
        }

        public String getCsk()
        {
            return csk;
        }

        public void setCsk(String csk)
        {
            this.csk = csk;
        }

        public String getCust()
        {
            return cust;
        }

        public void setCust(String cust)
        {
            this.cust = cust;
        }

        public String getCzp()
        {
            return czp;
        }

        public void setCzp(String czp)
        {
            this.czp = czp;
        }
    }

    public static class RailsInfo implements Cloneable
    {
        private RailsType railsType, railsTypeRatings;
        private RailsLaying railsQuality;
        private RailsGroup railsGroup;
        private RailsLength railsLength;
        private Thermostrengthening thermostrengthening;
        private CoercedRailWear coercedRailWear;
        private SideRailWear sideRailWear;

        private int outTotal75, outTotal65, outTotal50, outTotalOther;
        private int outYear75, outYear65, outYear50, outYearOther;

        private double brokenRails75, brokenRails65, brokenRails50, brokenRailsOther;

        @Override
        public RailsInfo clone()
        {
            try
            {
                return (RailsInfo) super.clone();
            } catch (CloneNotSupportedException ex)
            {
                throw new InternalError();
            }
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            long temp;
            temp = Double.doubleToLongBits(brokenRails50);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(brokenRails65);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(brokenRails75);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(brokenRailsOther);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            result = prime * result + ((coercedRailWear == null) ? 0 : coercedRailWear.hashCode());
            result = prime * result + outTotal50;
            result = prime * result + outTotal65;
            result = prime * result + outTotal75;
            result = prime * result + outTotalOther;
            result = prime * result + outYear50;
            result = prime * result + outYear65;
            result = prime * result + outYear75;
            result = prime * result + outYearOther;
            result = prime * result + ((railsGroup == null) ? 0 : railsGroup.hashCode());
            result = prime * result + ((railsLength == null) ? 0 : railsLength.hashCode());
            result = prime * result + ((railsQuality == null) ? 0 : railsQuality.hashCode());
            result = prime * result + ((railsType == null) ? 0 : railsType.hashCode());
            result = prime * result + ((railsTypeRatings == null) ? 0 : railsTypeRatings.hashCode());
            result = prime * result + ((sideRailWear == null) ? 0 : sideRailWear.hashCode());
            result = prime * result + ((thermostrengthening == null) ? 0 : thermostrengthening.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            RailsInfo other = (RailsInfo) obj;
            if (Double.doubleToLongBits(brokenRails50) != Double.doubleToLongBits(other.brokenRails50))
                return false;
            if (Double.doubleToLongBits(brokenRails65) != Double.doubleToLongBits(other.brokenRails65))
                return false;
            if (Double.doubleToLongBits(brokenRails75) != Double.doubleToLongBits(other.brokenRails75))
                return false;
            if (Double.doubleToLongBits(brokenRailsOther) != Double.doubleToLongBits(other.brokenRailsOther))
                return false;
            if (coercedRailWear != other.coercedRailWear)
                return false;
            if (outTotal50 != other.outTotal50)
                return false;
            if (outTotal65 != other.outTotal65)
                return false;
            if (outTotal75 != other.outTotal75)
                return false;
            if (outTotalOther != other.outTotalOther)
                return false;
            if (outYear50 != other.outYear50)
                return false;
            if (outYear65 != other.outYear65)
                return false;
            if (outYear75 != other.outYear75)
                return false;
            if (outYearOther != other.outYearOther)
                return false;
            if (railsGroup != other.railsGroup)
                return false;
            if (railsLength != other.railsLength)
                return false;
            if (railsQuality != other.railsQuality)
                return false;
            if (railsType != other.railsType)
                return false;
            if (railsTypeRatings != other.railsTypeRatings)
                return false;
            if (sideRailWear != other.sideRailWear)
                return false;
            if (thermostrengthening != other.thermostrengthening)
                return false;
            return true;
        }

        public RailsType getRailsType()
        {
            return railsType;
        }

        public void setRailsType(RailsType railsType)
        {
            this.railsType = railsType;
        }

        public RailsLaying getRailsQuality()
        {
            return railsQuality;
        }

        public void setRailsQuality(RailsLaying railsQuality)
        {
            this.railsQuality = railsQuality;
        }

        public RailsGroup getRailsGroup()
        {
            return railsGroup;
        }

        public void setRailsGroup(RailsGroup railsGroup)
        {
            this.railsGroup = railsGroup;
        }

        public RailsLength getRailsLength()
        {
            return railsLength;
        }

        public void setRailsLength(RailsLength railsLength)
        {
            this.railsLength = railsLength;
        }

        public Thermostrengthening getThermostrengthening()
        {
            return thermostrengthening;
        }

        public void setThermostrengthening(Thermostrengthening thermostrengthening)
        {
            this.thermostrengthening = thermostrengthening;
        }

        /** Приведенный износ рельса. */
        public CoercedRailWear getCoercedRailWear()
        {
            return coercedRailWear;
        }

        /** Приведенный износ рельса. */
        public void setCoercedRailWear(CoercedRailWear coercedRailWear)
        {
            this.coercedRailWear = coercedRailWear;
        }

        /** Боковой износ рельса. */
        public SideRailWear getSideRailWear()
        {
            return sideRailWear;
        }

        /** Боковой износ рельса. */
        public void setSideRailWear(SideRailWear sideRailWear)
        {
            this.sideRailWear = sideRailWear;
        }

        public RailsType getRailsTypeRatings()
        {
            return railsTypeRatings;
        }

        public void setRailsTypeRatings(RailsType railsTypeRatings)
        {
            this.railsTypeRatings = railsTypeRatings;
        }

        public int getOutTotal75()
        {
            return outTotal75;
        }

        public void setOutTotal75(int outTotal75)
        {
            this.outTotal75 = outTotal75;
        }

        public int getOutTotal65()
        {
            return outTotal65;
        }

        public void setOutTotal65(int outTotal65)
        {
            this.outTotal65 = outTotal65;
        }

        public int getOutTotal50()
        {
            return outTotal50;
        }

        public void setOutTotal50(int outTotal50)
        {
            this.outTotal50 = outTotal50;
        }

        public int getOutTotalOther()
        {
            return outTotalOther;
        }

        public void setOutTotalOther(int outTotalOther)
        {
            this.outTotalOther = outTotalOther;
        }

        public int getOutYear75()
        {
            return outYear75;
        }

        public void setOutYear75(int outYear75)
        {
            this.outYear75 = outYear75;
        }

        public int getOutYear65()
        {
            return outYear65;
        }

        public void setOutYear65(int outYear65)
        {
            this.outYear65 = outYear65;
        }

        public int getOutYear50()
        {
            return outYear50;
        }

        public void setOutYear50(int outYear50)
        {
            this.outYear50 = outYear50;
        }

        public int getOutYearOther()
        {
            return outYearOther;
        }

        public void setOutYearOther(int outYearOther)
        {
            this.outYearOther = outYearOther;
        }

        public double getBrokenRails75()
        {
            return brokenRails75;
        }

        public void setBrokenRails75(double brokenRails75)
        {
            this.brokenRails75 = brokenRails75;
        }

        public double getBrokenRails65()
        {
            return brokenRails65;
        }

        public void setBrokenRails65(double brokenRails65)
        {
            this.brokenRails65 = brokenRails65;
        }

        public double getBrokenRails50()
        {
            return brokenRails50;
        }

        public void setBrokenRails50(double brokenRails50)
        {
            this.brokenRails50 = brokenRails50;
        }

        public double getBrokenRailsOther()
        {
            return brokenRailsOther;
        }

        public void setBrokenRailsOther(double brokenRailsOther)
        {
            this.brokenRailsOther = brokenRailsOther;
        }
    }

    public static class SleepersInfo implements Cloneable
    {
        private SleepersType sleepersType, sleepersTypeRatings;
        private SleepersDensity sleepersDensity;
        private SleepersMaterial sleepersMaterial;
        private FasteningType fasteningType, fasteningTypeRatings;
        private int brokenFasteningsPercent;
        private int brokenWoodenSleepersSpring, brokenWoodenSleepersAutumn;
        private int woodenSleepersSpring, woodenSleepersAutumn;
        private int metalSleepersSpring, metalSleepersAutumn;
        private int concreteSleepersSpring, concreteSleepersAutumn;
        private boolean oldYearSleepers; //старогодние шпалы
        private double brokenSleepersPercent; //процент негодных деревянных шпал
               
        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + brokenFasteningsPercent;
            result = prime * result + brokenWoodenSleepersAutumn;
            result = prime * result + brokenWoodenSleepersSpring;
            result = prime * result + concreteSleepersAutumn;
            result = prime * result + concreteSleepersSpring;
            result = prime * result + ((fasteningType == null) ? 0 : fasteningType.hashCode());
            result = prime * result + ((fasteningTypeRatings == null) ? 0 : fasteningTypeRatings.hashCode());
            result = prime * result + metalSleepersAutumn;
            result = prime * result + metalSleepersSpring;
            result = prime * result + ((sleepersDensity == null) ? 0 : sleepersDensity.hashCode());
            result = prime * result + ((sleepersMaterial == null) ? 0 : sleepersMaterial.hashCode());
            result = prime * result + ((sleepersType == null) ? 0 : sleepersType.hashCode());
            result = prime * result + ((sleepersTypeRatings == null) ? 0 : sleepersTypeRatings.hashCode());
            result = prime * result + woodenSleepersAutumn;
            result = prime * result + woodenSleepersSpring;
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            SleepersInfo other = (SleepersInfo) obj;
            if (brokenFasteningsPercent != other.brokenFasteningsPercent)
                return false;
            if (brokenWoodenSleepersAutumn != other.brokenWoodenSleepersAutumn)
                return false;
            if (brokenWoodenSleepersSpring != other.brokenWoodenSleepersSpring)
                return false;
            if (concreteSleepersAutumn != other.concreteSleepersAutumn)
                return false;
            if (concreteSleepersSpring != other.concreteSleepersSpring)
                return false;
            if (fasteningType != other.fasteningType)
                return false;
            if (fasteningTypeRatings != other.fasteningTypeRatings)
                return false;
            if (metalSleepersAutumn != other.metalSleepersAutumn)
                return false;
            if (metalSleepersSpring != other.metalSleepersSpring)
                return false;
            if (sleepersDensity != other.sleepersDensity)
                return false;
            if (sleepersMaterial != other.sleepersMaterial)
                return false;
            if (sleepersType != other.sleepersType)
                return false;
            if (sleepersTypeRatings != other.sleepersTypeRatings)
                return false;
            if (woodenSleepersAutumn != other.woodenSleepersAutumn)
                return false;
            if (woodenSleepersSpring != other.woodenSleepersSpring)
                return false;
            return true;
        }

        @Override
        public SleepersInfo clone()
        {
            try
            {
                return (SleepersInfo) super.clone();
            } catch (CloneNotSupportedException ex)
            {
                throw new InternalError();
            }
        }

        public SleepersType getSleepersTypeRatings()
        {
            return sleepersTypeRatings;
        }

        public void setSleepersTypeRatings(SleepersType sleepersTypeRatings)
        {
            this.sleepersTypeRatings = sleepersTypeRatings;
        }

        public FasteningType getFasteningTypeRatings()
        {
            return fasteningTypeRatings;
        }

        public void setFasteningTypeRatings(FasteningType fasteningTypeRatings)
        {
            this.fasteningTypeRatings = fasteningTypeRatings;
        }

        public SleepersType getSleepersType()
        {
            return sleepersType;
        }

        public void setSleepersType(SleepersType sleepersType)
        {
            this.sleepersType = sleepersType;
        }

        /** Эпюра шпал. */
        public SleepersDensity getSleepersDensity()
        {
            return sleepersDensity;
        }

        /** Эпюра шпал. */
        public void setSleepersDensity(SleepersDensity sleepersDensity)
        {
            this.sleepersDensity = sleepersDensity;
        }

        /** Дерево/Метал/Железобетон */
        public SleepersMaterial getSleepersMaterial()
        {
            return sleepersMaterial;
        }

        /** Дерево/Метал/Железобетон */
        public void setSleepersMaterial(SleepersMaterial sleepersMaterial)
        {
            this.sleepersMaterial = sleepersMaterial;
        }

        /** Тип скрепления. */
        public FasteningType getFasteningType()
        {
            return fasteningType;
        }

        /** Тип скрепления. */
        public void setFasteningType(FasteningType fasteningType)
        {
            this.fasteningType = fasteningType;
        }

        public int getBrokenFasteningsPercent()
        {
            return brokenFasteningsPercent;
        }

        public void setBrokenFasteningsPercent(int brokenFasteningsPercent)
        {
            this.brokenFasteningsPercent = brokenFasteningsPercent;
        }

        public int getBrokenWoodenSleepersSpring()
        {
            return brokenWoodenSleepersSpring;
        }

        public void setBrokenWoodenSleepersSpring(int brokenWoodenSleepersSpring)
        {
            this.brokenWoodenSleepersSpring = brokenWoodenSleepersSpring;
        }

        public int getBrokenWoodenSleepersAutumn()
        {
            return brokenWoodenSleepersAutumn;
        }

        public void setBrokenWoodenSleepersAutumn(int brokenWoodenSleepersAutumn)
        {
            this.brokenWoodenSleepersAutumn = brokenWoodenSleepersAutumn;
        }

        public int getWoodenSleepersSpring()
        {
            return woodenSleepersSpring;
        }

        public void setWoodenSleepersSpring(int woodenSleepersSpring)
        {
            this.woodenSleepersSpring = woodenSleepersSpring;
        }

        public int getWoodenSleepersAutumn()
        {
            return woodenSleepersAutumn;
        }

        public void setWoodenSleepersAutumn(int woodenSleepersAutumn)
        {
            this.woodenSleepersAutumn = woodenSleepersAutumn;
        }

        public int getMetalSleepersSpring()
        {
            return metalSleepersSpring;
        }

        public void setMetalSleepersSpring(int metalSleepersSpring)
        {
            this.metalSleepersSpring = metalSleepersSpring;
        }

        public int getMetalSleepersAutumn()
        {
            return metalSleepersAutumn;
        }

        public void setMetalSleepersAutumn(int metalSleepersAutumn)
        {
            this.metalSleepersAutumn = metalSleepersAutumn;
        }

        public int getConcreteSleepersSpring()
        {
            return concreteSleepersSpring;
        }

        public void setConcreteSleepersSpring(int concreteSleepersSpring)
        {
            this.concreteSleepersSpring = concreteSleepersSpring;
        }

        public int getConcreteSleepersAutumn()
        {
            return concreteSleepersAutumn;
        }

        public void setConcreteSleepersAutumn(int concreteSleepersAutumn)
        {
            this.concreteSleepersAutumn = concreteSleepersAutumn;
        }
        
        //старогодние шпалы
        public boolean getOldYearSleepers()
        {
            return oldYearSleepers;
        }

        public void setOldYearSleepers(boolean oldYearSleepers)
        {
            this.oldYearSleepers = oldYearSleepers;
        }
        
        //%негодных деревянных шпал
        public double getBrokenSleepersPercent()
        {
            return brokenSleepersPercent;
        }

        public void setBrokenSleepersPercent(double brokenSleepersPercent)
        {
            this.brokenSleepersPercent = brokenSleepersPercent;
        }
    }

    public static class CurveInfo implements Cloneable
    {
        private double curveRadius;
        private double cantOfTheTrack;
        private int gaugeOfRailway;
        private CurveDirection curveDirection;
        private int curveLength;

        @Override
        public CurveInfo clone()
        {
            try
            {
                return (CurveInfo) super.clone();
            } catch (CloneNotSupportedException ex)
            {
                throw new InternalError();
            }
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final CurveInfo other = (CurveInfo) obj;
            if (Double.doubleToLongBits(this.curveRadius) != Double.doubleToLongBits(other.curveRadius))
                return false;
            if (Double.doubleToLongBits(this.cantOfTheTrack) != Double.doubleToLongBits(other.cantOfTheTrack))
                return false;
            if (this.gaugeOfRailway != other.gaugeOfRailway)
                return false;
            if (this.curveDirection != other.curveDirection)
                return false;
            if (this.curveLength != other.curveLength)
                return false;
            return true;
        }

        @Override
        public int hashCode()
        {
            int hash = 3;
            hash = 61
                    * hash
                    + (int) (Double.doubleToLongBits(this.curveRadius) ^ (Double.doubleToLongBits(this.curveRadius) >>> 32));
            hash = 61
                    * hash
                    + (int) (Double.doubleToLongBits(this.cantOfTheTrack) ^ (Double
                            .doubleToLongBits(this.cantOfTheTrack) >>> 32));
            hash = 61 * hash + this.gaugeOfRailway;
            hash = 61 * hash + (this.curveDirection != null ? this.curveDirection.hashCode() : 0);
            hash = 61 * hash + this.curveLength;
            return hash;
        }

        public double getCantOfTheTrack()
        {
            return cantOfTheTrack;
        }

        public void setCantOfTheTrack(double cantOfTheTrack)
        {
            this.cantOfTheTrack = cantOfTheTrack;
        }

        public CurveDirection getCurveDirection()
        {
            return curveDirection;
        }

        public void setCurveDirection(CurveDirection curveDirection)
        {
            this.curveDirection = curveDirection;
        }

        public int getCurveLength()
        {
            return curveLength;
        }

        public void setCurveLength(int curveLength)
        {
            this.curveLength = curveLength;
        }

        public double getCurveRadius()
        {
            return curveRadius;
        }

        public void setCurveRadius(double curveRadius)
        {
            this.curveRadius = curveRadius;
        }

        public int getGaugeOfRailway()
        {
            return gaugeOfRailway;
        }

        public void setGaugeOfRailway(int gaugeOfRailway)
        {
            this.gaugeOfRailway = gaugeOfRailway;
        }
    }

    public static class SpeedInfo implements Cloneable
    {
        private int passengerSpeed, passengerSpeedRatings;
        private int cargoSpeed, cargoSpeedRatings;
        private int emptySpeed, emptySpeedRatings;

        @Override
        public SpeedInfo clone()
        {
            try
            {
                return (SpeedInfo) super.clone();
            } catch (CloneNotSupportedException ex)
            {
                throw new InternalError();
            }
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final SpeedInfo other = (SpeedInfo) obj;
            if (this.passengerSpeed != other.passengerSpeed)
                return false;
            if (this.passengerSpeedRatings != other.passengerSpeedRatings)
                return false;
            if (this.cargoSpeed != other.cargoSpeed)
                return false;
            if (this.cargoSpeedRatings != other.cargoSpeedRatings)
                return false;
            if (this.emptySpeed != other.emptySpeed)
                return false;
            if (this.emptySpeedRatings != other.emptySpeedRatings)
                return false;
            return true;
        }

        @Override
        public int hashCode()
        {
            int hash = 5;
            hash = 73 * hash + this.passengerSpeed;
            hash = 73 * hash + this.passengerSpeedRatings;
            hash = 73 * hash + this.cargoSpeed;
            hash = 73 * hash + this.cargoSpeedRatings;
            hash = 73 * hash + this.emptySpeed;
            hash = 73 * hash + this.emptySpeedRatings;
            return hash;
        }

        public int getCargoSpeedRatings()
        {
            return cargoSpeedRatings;
        }

        public void setCargoSpeedRatings(int cargoSpeedRatings)
        {
            this.cargoSpeedRatings = cargoSpeedRatings;
        }

        public int getEmptySpeedRatings()
        {
            return emptySpeedRatings;
        }

        public void setEmptySpeedRatings(int emptySpeedRatings)
        {
            this.emptySpeedRatings = emptySpeedRatings;
        }

        public int getPassengerSpeedRatings()
        {
            return passengerSpeedRatings;
        }

        public void setPassengerSpeedRatings(int passengerSpeedRatings)
        {
            this.passengerSpeedRatings = passengerSpeedRatings;
        }

        public int getCargoSpeed()
        {
            return cargoSpeed;
        }

        public void setCargoSpeed(int cargoSpeed)
        {
            this.cargoSpeed = cargoSpeed;
        }

        public int getPassengerSpeed()
        {
            return passengerSpeed;
        }

        public void setPassengerSpeed(int passengerSpeed)
        {
            this.passengerSpeed = passengerSpeed;
        }

        public int getEmptySpeed()
        {
            return emptySpeed;
        }

        public void setEmptySpeed(int emptySpeed)
        {
            this.emptySpeed = emptySpeed;
        }
    }

    public static class BallastInfo implements Cloneable
    {
        private BallastType ballastType, ballastTypeRatings;
        private SpacerLayer spacerLayer;
        private boolean ballastDirty;
        private BallastThickness ballastThickness;

        @Override
        public BallastInfo clone()
        {
            try
            {
                return (BallastInfo) super.clone();
            } catch (CloneNotSupportedException ex)
            {
                throw new InternalError();
            }
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final BallastInfo other = (BallastInfo) obj;
            if (this.ballastType != other.ballastType)
                return false;
            if (this.ballastTypeRatings != other.ballastTypeRatings)
                return false;
            if (this.spacerLayer != other.spacerLayer)
                return false;
            if (this.ballastDirty != other.ballastDirty)
                return false;
            if (this.ballastThickness != other.ballastThickness)
                return false;
            return true;
        }

        @Override
        public int hashCode()
        {
            int hash = 5;
            hash = 29 * hash + (this.ballastType != null ? this.ballastType.hashCode() : 0);
            hash = 29 * hash + (this.ballastTypeRatings != null ? this.ballastTypeRatings.hashCode() : 0);
            hash = 29 * hash + (this.spacerLayer != null ? this.spacerLayer.hashCode() : 0);
            hash = 29 * hash + (this.ballastDirty ? 1 : 0);
            hash = 29 * hash + (this.ballastThickness != null ? this.ballastThickness.hashCode() : 0);
            return hash;
        }

        /** Род балласта. */
        public BallastType getBallastType()
        {
            return ballastType;
        }

        /** Род балласта. */
        public void setBallastType(BallastType ballastType)
        {
            this.ballastType = ballastType;
        }

        public BallastType getBallastTypeRatings()
        {
            return ballastTypeRatings;
        }

        public void setBallastTypeRatings(BallastType ballastTypeRatings)
        {
            this.ballastTypeRatings = ballastTypeRatings;
        }

        /** Разделительный слой. */
        public SpacerLayer getSpacerLayer()
        {
            return spacerLayer;
        }

        /** Разделительный слой. */
        public void setSpacerLayer(SpacerLayer spacerLayer)
        {
            this.spacerLayer = spacerLayer;
        }

        public boolean isBallastDirty()
        {
            return ballastDirty;
        }

        public void setBallastDirty(boolean ballastDirty)
        {
            this.ballastDirty = ballastDirty;
        }

        /** Толщина балласта, см. */
        public BallastThickness getBallastThickness()
        {
            return ballastThickness;
        }

        /** Толщина балласта, см. */
        public void setBallastThickness(BallastThickness ballastThickness)
        {
            this.ballastThickness = ballastThickness;
        }
    }

    public static class RepairNeedFigures implements Cloneable
    {
        private double tonnageNormativeCorrection, tncSpacerLayer, tncSpringFastening, tncAxisLoad, tncRailsPolish,
                tncRecuperativeBraking, tncStalkLength, tncSlope, tncCurve;
        private double brokenSleepersPerKmPercent;
        private double splashSleepersPercent;
        private double brokenFasteningsPercent;
        private double singleRailsOutage;
        private double usagePercent;

        @Override
        public RepairNeedFigures clone()
        {
            try
            {
                return (RepairNeedFigures) super.clone();
            } catch (CloneNotSupportedException ex)
            {
                throw new InternalError();
            }
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final RepairNeedFigures other = (RepairNeedFigures) obj;
            if (Double.doubleToLongBits(this.usagePercent) != Double.doubleToLongBits(other.usagePercent))
                return false;
            if (Double.doubleToLongBits(this.tonnageNormativeCorrection) != Double
                    .doubleToLongBits(other.tonnageNormativeCorrection))
                return false;
            if (Double.doubleToLongBits(this.tncSpacerLayer) != Double.doubleToLongBits(other.tncSpacerLayer))
                return false;
            if (Double.doubleToLongBits(this.tncSpringFastening) != Double.doubleToLongBits(other.tncSpringFastening))
                return false;
            if (Double.doubleToLongBits(this.tncAxisLoad) != Double.doubleToLongBits(other.tncAxisLoad))
                return false;
            if (Double.doubleToLongBits(this.tncRailsPolish) != Double.doubleToLongBits(other.tncRailsPolish))
                return false;
            if (Double.doubleToLongBits(this.tncRecuperativeBraking) != Double
                    .doubleToLongBits(other.tncRecuperativeBraking))
                return false;
            if (Double.doubleToLongBits(this.tncStalkLength) != Double.doubleToLongBits(other.tncStalkLength))
                return false;
            if (Double.doubleToLongBits(this.tncSlope) != Double.doubleToLongBits(other.tncSlope))
                return false;
            if (Double.doubleToLongBits(this.tncCurve) != Double.doubleToLongBits(other.tncCurve))
                return false;
            if (Double.doubleToLongBits(this.brokenSleepersPerKmPercent) != Double
                    .doubleToLongBits(other.brokenSleepersPerKmPercent))
                return false;
            if (Double.doubleToLongBits(this.splashSleepersPercent) != Double
                    .doubleToLongBits(other.splashSleepersPercent))
                return false;
            if (Double.doubleToLongBits(this.brokenFasteningsPercent) != Double
                    .doubleToLongBits(other.brokenFasteningsPercent))
                return false;
            if (Double.doubleToLongBits(this.singleRailsOutage) != Double.doubleToLongBits(other.singleRailsOutage))
                return false;
            return true;
        }

        @Override
        public int hashCode()
        {
            int hash = 3;
            hash = 89
                    * hash
                    + (int) (Double.doubleToLongBits(this.tonnageNormativeCorrection) ^ (Double
                            .doubleToLongBits(this.tonnageNormativeCorrection) >>> 32));
            hash = 89
                    * hash
                    + (int) (Double.doubleToLongBits(this.tncSpacerLayer) ^ (Double
                            .doubleToLongBits(this.tncSpacerLayer) >>> 32));
            hash = 89
                    * hash
                    + (int) (Double.doubleToLongBits(this.tncSpringFastening) ^ (Double
                            .doubleToLongBits(this.tncSpringFastening) >>> 32));
            hash = 89
                    * hash
                    + (int) (Double.doubleToLongBits(this.tncAxisLoad) ^ (Double.doubleToLongBits(this.tncAxisLoad) >>> 32));
            hash = 89
                    * hash
                    + (int) (Double.doubleToLongBits(this.tncRailsPolish) ^ (Double
                            .doubleToLongBits(this.tncRailsPolish) >>> 32));
            hash = 89
                    * hash
                    + (int) (Double.doubleToLongBits(this.tncRecuperativeBraking) ^ (Double
                            .doubleToLongBits(this.tncRecuperativeBraking) >>> 32));
            hash = 89
                    * hash
                    + (int) (Double.doubleToLongBits(this.tncStalkLength) ^ (Double
                            .doubleToLongBits(this.tncStalkLength) >>> 32));
            hash = 89 * hash
                    + (int) (Double.doubleToLongBits(this.tncSlope) ^ (Double.doubleToLongBits(this.tncSlope) >>> 32));
            hash = 89 * hash
                    + (int) (Double.doubleToLongBits(this.tncCurve) ^ (Double.doubleToLongBits(this.tncCurve) >>> 32));
            hash = 89
                    * hash
                    + (int) (Double.doubleToLongBits(this.brokenSleepersPerKmPercent) ^ (Double
                            .doubleToLongBits(this.brokenSleepersPerKmPercent) >>> 32));
            hash = 89
                    * hash
                    + (int) (Double.doubleToLongBits(this.splashSleepersPercent) ^ (Double
                            .doubleToLongBits(this.splashSleepersPercent) >>> 32));
            hash = 89
                    * hash
                    + (int) (Double.doubleToLongBits(this.brokenFasteningsPercent) ^ (Double
                            .doubleToLongBits(this.brokenFasteningsPercent) >>> 32));
            hash = 89
                    * hash
                    + (int) (Double.doubleToLongBits(this.singleRailsOutage) ^ (Double
                            .doubleToLongBits(this.singleRailsOutage) >>> 32));
            hash = 89
                    * hash
                    + (int) (Double.doubleToLongBits(this.usagePercent) ^ (Double.doubleToLongBits(this.usagePercent) >>> 32));
            return hash;
        }

        public double getBrokenFasteningsPercent()
        {
            return brokenFasteningsPercent;
        }

        public void setBrokenFasteningsPercent(double brokenFasteningsPercent)
        {
            this.brokenFasteningsPercent = brokenFasteningsPercent;
        }

        public double getBrokenSleepersPerKmPercent()
        {
            return brokenSleepersPerKmPercent;
        }

        public void setBrokenSleepersPerKmPercent(double brokenSleepersPerKmPercent)
        {
            this.brokenSleepersPerKmPercent = brokenSleepersPerKmPercent;
        }

        public double getSingleRailsOutage()
        {
            return singleRailsOutage;
        }

        public void setSingleRailsOutage(double singleRailsOutage)
        {
            this.singleRailsOutage = singleRailsOutage;
        }

        public double getSplashSleepersPercent()
        {
            return splashSleepersPercent;
        }

        public void setSplashSleepersPercent(double splashSleepersPercent)
        {
            this.splashSleepersPercent = splashSleepersPercent;
        }

        public double getTonnageNormativeCorrection()
        {
            return tonnageNormativeCorrection;
        }

        public void setTonnageNormativeCorrection(double tonnageNormativeCorrection)
        {
            this.tonnageNormativeCorrection = tonnageNormativeCorrection;
        }

        public double getTncAxisLoad()
        {
            return tncAxisLoad;
        }

        public void setTncAxisLoad(double tncAxisLoad)
        {
            this.tncAxisLoad = tncAxisLoad;
        }

        public double getTncCurve()
        {
            return tncCurve;
        }

        public void setTncCurve(double tncCurve)
        {
            this.tncCurve = tncCurve;
        }

        public double getTncRailsPolish()
        {
            return tncRailsPolish;
        }

        public void setTncRailsPolish(double tncRailsPolish)
        {
            this.tncRailsPolish = tncRailsPolish;
        }

        public double getTncRecuperativeBraking()
        {
            return tncRecuperativeBraking;
        }

        public void setTncRecuperativeBraking(double tncRecuperativeBraking)
        {
            this.tncRecuperativeBraking = tncRecuperativeBraking;
        }

        public double getTncSlope()
        {
            return tncSlope;
        }

        public void setTncSlope(double tncSlope)
        {
            this.tncSlope = tncSlope;
        }

        public double getTncSpacerLayer()
        {
            return tncSpacerLayer;
        }

        public void setTncSpacerLayer(double tncSpacerLayer)
        {
            this.tncSpacerLayer = tncSpacerLayer;
        }

        public double getTncSpringFastening()
        {
            return tncSpringFastening;
        }

        public void setTncSpringFastening(double tncSpringFastening)
        {
            this.tncSpringFastening = tncSpringFastening;
        }

        public double getTncStalkLength()
        {
            return tncStalkLength;
        }

        public void setTncStalkLength(double tncStalkLength)
        {
            this.tncStalkLength = tncStalkLength;
        }

        public double getUsagePercent()
        {
            return usagePercent;
        }

        public void setUsagePercent(double usagePercent)
        {
            this.usagePercent = usagePercent;
        }
    }

    @Override
    public String toString()
    {
        return String.format("WayPart{%1$d - %2$d %3$d km %4$d m - %5$d km %6$d m }", start, start + length, startKm,
                startM, endKm, endM );
    }

    public boolean restoreCoordinates(Way reference)
    {
        WayPart startRef = reference.get(startKm, startM, Way.RelativePosition.BEFORE), endRef = reference.get(endKm,
                endM, Way.RelativePosition.AFTER);
        boolean referencesFound = true;
        // Исправление координат вида 156 км 1000 м на 157 км 0 м (здесь 156 км
        // имеет длину 1000 м).
        /*
         * if (startRef != null && startRef.getLength() > 0 && startM ==
         * startRef.getLength()) { startKm += 1; startM = 0; startRef =
         * reference.get(startKm, startM, Way.RelativePosition.BEFORE); } if
         * (endRef != null && endRef.getLength() > 0 && endM ==
         * endRef.getLength()) { endKm += 1; endM = 0; endRef =
         * reference.get(endKm, endM, Way.RelativePosition.AFTER); }
         */
        if (startRef == null)
        {
            LOGGER.log(Level.WARNING, "Не удалось восстановить координату начала участка {0}", this);
            referencesFound = false;
        }
        if (endRef == null)
        {
            WayPart lastRef = reference.last();
            if (lastRef != null
                    && (lastRef.getEndKm() == endKm && lastRef.getEndM() == endM || lastRef.getEndKm() < endKm))
                endRef = new WayPart(lastRef.getEnd(), 0, lastRef.getEndKm(), lastRef.getEndM(), lastRef.getEndKm(),
                        lastRef.getEndM());
            else
            {
                LOGGER.log(Level.WARNING, "Не удалось восстановить координату конца участка {0}", this);
                referencesFound = false;
            }
        }
        if (referencesFound)
        {                              
            start = startRef.getStart() + startM - startRef.getStartM();
            int lengthSave = length;
            length = endRef.getStart() + endM - endRef.getStartM() - start;
            if(length<0) length=lengthSave;
           
            if (start < startRef.getStart() || start > startRef.getEnd() || start + length < endRef.getStart()
                    || start + length > endRef.getEnd())
            {
                LOGGER.log(Level.WARNING,
                        "Выход восстановленых координат участка {0} за границы опорных участков {1} и {2}.",
                        new Object[]
                        { this, startRef, endRef });
                
                return true;
            }            
        }
        return referencesFound;
    }
    
    //признак того что координата содержится в участке
    public boolean isContents(int km, int m){
        if ( (getStartKm() < km || getStartKm() == km && getStartM() < m) &&                
            (getEndKm() > km || getEndKm() == km && getEndM() > m) ){
            return true;
        }
        return false;
    }
    
    //признак того что участок совпадает или содержится внутри другого участка
    //проверка по внутренним координатам
    //параметры задают начало и конец участка
    public boolean isContentsInt(int start, int end){
        if( (getStart()<=start && start<=getEnd()) && ( getStart()<=end && end<=getEnd() ))
            return true;
        else
            return false;
    }


    // описывает участки границ
    public static class BorderUnit implements Cloneable
    {
        private String zspwpdhgF1;
        private String zspwpdhgF2;
        private String zspwpdhgF3;
        private String zspwpdhgF4;
        private String zspwpdhgF5;

        public String getZspwpdhgF1()
        {
            return zspwpdhgF1;
        }

        public void setZspwpdhgF1(String zspwpdhgF1)
        {
            this.zspwpdhgF1 = zspwpdhgF1;
        }

        public String getZspwpdhgF2()
        {
            return zspwpdhgF2;
        }

        public void setZspwpdhgF2(String zspwpdhgF2)
        {
            this.zspwpdhgF2 = zspwpdhgF2;
        }

        public String getZspwpdhgF3()
        {
            return zspwpdhgF3;
        }

        public void setZspwpdhgF3(String zspwpdhgF3)
        {
            this.zspwpdhgF3 = zspwpdhgF3;
        }

        public String getZspwpdhgF4()
        {
            return zspwpdhgF4;
        }

        public void setZspwpdhgF4(String zspwpdhgF4)
        {
            this.zspwpdhgF4 = zspwpdhgF4;
        }

        public String getZspwpdhgF5()
        {
            return zspwpdhgF5;
        }

        public void setZspwpdhgF5(String zspwpdhgF5)
        {
            this.zspwpdhgF5 = zspwpdhgF5;
        }

        @Override
        public BorderUnit clone()
        {
            try
            {
                return (BorderUnit) super.clone();
            } catch (CloneNotSupportedException ex)
            {
                throw new InternalError("clone not supported"); // NOI18N
            }
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final BorderUnit other = (BorderUnit) obj;
            if ((this.zspwpdhgF1 == null) ? (other.zspwpdhgF1 != null) : !this.zspwpdhgF1.equals(other.zspwpdhgF1))
                return false;
            if ((this.zspwpdhgF2 == null) ? (other.zspwpdhgF2 != null) : !this.zspwpdhgF2.equals(other.zspwpdhgF2))
                return false;
            if ((this.zspwpdhgF3 == null) ? (other.zspwpdhgF3 != null) : !this.zspwpdhgF3.equals(other.zspwpdhgF3))
                return false;
            if ((this.zspwpdhgF4 == null) ? (other.zspwpdhgF4 != null) : !this.zspwpdhgF4.equals(other.zspwpdhgF4))
                return false;
            if ((this.zspwpdhgF5 == null) ? (other.zspwpdhgF5 != null) : !this.zspwpdhgF5.equals(other.zspwpdhgF5))
                return false;
            return true;
        }

        @Override
        public int hashCode()
        {
            int hash = 7;
            hash = 71 * hash + (this.zspwpdhgF1 != null ? this.zspwpdhgF1.hashCode() : 0);
            hash = 71 * hash + (this.zspwpdhgF2 != null ? this.zspwpdhgF2.hashCode() : 0);
            hash = 71 * hash + (this.zspwpdhgF3 != null ? this.zspwpdhgF3.hashCode() : 0);
            hash = 71 * hash + (this.zspwpdhgF4 != null ? this.zspwpdhgF4.hashCode() : 0);
            hash = 71 * hash + (this.zspwpdhgF5 != null ? this.zspwpdhgF5.hashCode() : 0);

            return hash;
        }
    }
}
