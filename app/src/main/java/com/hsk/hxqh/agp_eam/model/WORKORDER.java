package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/27.
 */

public class WORKORDER implements Serializable {
    public static final String TABLE_NAME = "WORKORDER";
    public String WORKORDERID;
    public String WONUM;//工单
    public String DESCRIPTION;//描述
    public String WORKTYPE;//工单类型
    public String ASSETNUM;//资产
    public String ASSETDES;//资产描述
    public String STATIONDES;//站场
    public String LOCATION;//位置
    public String LOCATIONDES;//位置描述
    public String UDWOREQNUM;//工作申请编号
    public String UDFAULTREPORTNUM;//故障提报ACT号
    public String UDYEARPLANNUM;//年度计划编号
    public String UDSTATUS;//状态
    public String CREATEBYNAME;//创建人
    public String CREATEDATE;//创建时间
    public String SCHEDSTART;//调度开始时间
    public String SCHEDFINISH;//计划完成时间
    public String ACTSTART;//实际开始时间
    public String ACTFINISH;//实际完成时间
    public String UDYEAR;//年度计划
    public String WORKFREQUENCY;//频率
    public String JPNUM;//作业计划
    public String UDWORKSCOPE;//工作范围
    public String UDFAULTDESC;//故障描述
    public String UDREASON;//故障原因
    public String HAZARDLEVEL;//工期
    public String UDSTATIONNUM;//
    public String SUBJECT;
    public String FAILURECODE;
    public String HIDDENDANGERDESC;
    public String LEAD;
    public String PROBLEMCODE;
    public String RECTIFYOPINION;
    public Date REPORTDATE;
    public String REPORTEDBY;
    public String RESPONSIBLE;
    public String STATUS;
    public String SUPERVISOR;
    public String WORKCONTENT;
    public String CREATEBY;
    public String UDTEMPMATERIAL;
    public String INVONAME;
    public String WORKORDERUDTDES;

    public String getWORKORDERUDTDES() {
        return WORKORDERUDTDES;
    }

    public void setWORKORDERUDTDES(String WORKORDERUDTDES) {
        this.WORKORDERUDTDES = WORKORDERUDTDES;
    }

    public String getINVONAME() {
        return INVONAME;
    }

    public void setINVONAME(String INVONAME) {
        this.INVONAME = INVONAME;
    }

    public String getUDTEMPMATERIAL() {
        return UDTEMPMATERIAL;
    }

    public void setUDTEMPMATERIAL(String UDTEMPMATERIAL) {
        this.UDTEMPMATERIAL = UDTEMPMATERIAL;
    }

    public String getCREATEBY() {
        return CREATEBY;
    }

    public void setCREATEBY(String CREATEBY) {
        this.CREATEBY = CREATEBY;
    }

    public String getFAILURECODE() {
        return FAILURECODE;
    }

    public void setFAILURECODE(String FAILURECODE) {
        this.FAILURECODE = FAILURECODE;
    }

    public String getHIDDENDANGERDESC() {
        return HIDDENDANGERDESC;
    }

    public void setHIDDENDANGERDESC(String HIDDENDANGERDESC) {
        this.HIDDENDANGERDESC = HIDDENDANGERDESC;
    }

    public String getLEAD() {
        return LEAD;
    }

    public void setLEAD(String LEAD) {
        this.LEAD = LEAD;
    }

    public String getPROBLEMCODE() {
        return PROBLEMCODE;
    }

    public void setPROBLEMCODE(String PROBLEMCODE) {
        this.PROBLEMCODE = PROBLEMCODE;
    }

    public String getRECTIFYOPINION() {
        return RECTIFYOPINION;
    }

    public void setRECTIFYOPINION(String RECTIFYOPINION) {
        this.RECTIFYOPINION = RECTIFYOPINION;
    }

    public Date getREPORTDATE() {
        return REPORTDATE;
    }

    public void setREPORTDATE(Date REPORTDATE) {
        this.REPORTDATE = REPORTDATE;
    }

    public String getREPORTEDBY() {
        return REPORTEDBY;
    }

    public void setREPORTEDBY(String REPORTEDBY) {
        this.REPORTEDBY = REPORTEDBY;
    }

    public String getRESPONSIBLE() {
        return RESPONSIBLE;
    }

    public void setRESPONSIBLE(String RESPONSIBLE) {
        this.RESPONSIBLE = RESPONSIBLE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getSUPERVISOR() {
        return SUPERVISOR;
    }

    public void setSUPERVISOR(String SUPERVISOR) {
        this.SUPERVISOR = SUPERVISOR;
    }

    public String getWORKCONTENT() {
        return WORKCONTENT;
    }

    public void setWORKCONTENT(String WORKCONTENT) {
        this.WORKCONTENT = WORKCONTENT;
    }

    public String getSUBJECT() {
        return SUBJECT;
    }

    public void setSUBJECT(String SUBJECT) {
        this.SUBJECT = SUBJECT;
    }

    public String getWORKORDERID() {
        return WORKORDERID;
    }

    public void setWORKORDERID(String WORKORDERID) {
        this.WORKORDERID = WORKORDERID;
    }

    public String getWONUM() {
        return WONUM;
    }

    public void setWONUM(String WONUM) {
        this.WONUM = WONUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getWORKTYPE() {
        return WORKTYPE;
    }

    public void setWORKTYPE(String WORKTYPE) {
        this.WORKTYPE = WORKTYPE;
    }

    public String getWORKFREQUENCY() {
        return WORKFREQUENCY;
    }

    public void setWORKFREQUENCY(String WORKFREQUENCY) {
        this.WORKFREQUENCY = WORKFREQUENCY;
    }

    public String getACTSTART() {
        return ACTSTART;
    }

    public void setACTSTART(String ACTSTART) {
        this.ACTSTART = ACTSTART;
    }

    public String getACTFINISH() {
        return ACTFINISH;
    }

    public void setACTFINISH(String ACTFINISH) {
        this.ACTFINISH = ACTFINISH;
    }

    public String getASSETNUM() {
        return ASSETNUM;
    }

    public void setASSETNUM(String ASSETNUM) {
        this.ASSETNUM = ASSETNUM;
    }

    public String getASSETDES() {
        return ASSETDES;
    }

    public void setASSETDES(String ASSETDES) {
        this.ASSETDES = ASSETDES;
    }

    public String getCREATEBYNAME() {
        return CREATEBYNAME;
    }

    public void setCREATEBYNAME(String CREATEBYNAME) {
        this.CREATEBYNAME = CREATEBYNAME;
    }

    public String getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(String CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    public String getHAZARDLEVEL() {
        return HAZARDLEVEL;
    }

    public void setHAZARDLEVEL(String HAZARDLEVEL) {
        this.HAZARDLEVEL = HAZARDLEVEL;
    }

    public String getJPNUM() {
        return JPNUM;
    }

    public void setJPNUM(String JPNUM) {
        this.JPNUM = JPNUM;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getLOCATIONDES() {
        return LOCATIONDES;
    }

    public void setLOCATIONDES(String LOCATIONDES) {
        this.LOCATIONDES = LOCATIONDES;
    }

    public String getSCHEDSTART() {
        return SCHEDSTART;
    }

    public void setSCHEDSTART(String SCHEDSTART) {
        this.SCHEDSTART = SCHEDSTART;
    }

    public String getSCHEDFINISH() {
        return SCHEDFINISH;
    }

    public void setSCHEDFINISH(String SCHEDFINISH) {
        this.SCHEDFINISH = SCHEDFINISH;
    }

    public String getSTATIONDES() {
        return STATIONDES;
    }

    public void setSTATIONDES(String STATIONDES) {
        this.STATIONDES = STATIONDES;
    }

    public String getUDFAULTDESC() {
        return UDFAULTDESC;
    }

    public void setUDFAULTDESC(String UDFAULTDESC) {
        this.UDFAULTDESC = UDFAULTDESC;
    }

    public String getUDFAULTREPORTNUM() {
        return UDFAULTREPORTNUM;
    }

    public void setUDFAULTREPORTNUM(String UDFAULTREPORTNUM) {
        this.UDFAULTREPORTNUM = UDFAULTREPORTNUM;
    }

    public String getUDREASON() {
        return UDREASON;
    }

    public void setUDREASON(String UDREASON) {
        this.UDREASON = UDREASON;
    }

    public String getUDSTATIONNUM() {
        return UDSTATIONNUM;
    }

    public void setUDSTATIONNUM(String UDSTATIONNUM) {
        this.UDSTATIONNUM = UDSTATIONNUM;
    }

    public String getUDSTATUS() {
        return UDSTATUS;
    }

    public void setUDSTATUS(String UDSTATUS) {
        this.UDSTATUS = UDSTATUS;
    }

    public String getUDWOREQNUM() {
        return UDWOREQNUM;
    }

    public void setUDWOREQNUM(String UDWOREQNUM) {
        this.UDWOREQNUM = UDWOREQNUM;
    }

    public String getUDWORKSCOPE() {
        return UDWORKSCOPE;
    }

    public void setUDWORKSCOPE(String UDWORKSCOPE) {
        this.UDWORKSCOPE = UDWORKSCOPE;
    }

    public String getUDYEAR() {
        return UDYEAR;
    }

    public void setUDYEAR(String UDYEAR) {
        this.UDYEAR = UDYEAR;
    }

    public String getUDYEARPLANNUM() {
        return UDYEARPLANNUM;
    }

    public void setUDYEARPLANNUM(String UDYEARPLANNUM) {
        this.UDYEARPLANNUM = UDYEARPLANNUM;
    }
}
