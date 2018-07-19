package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 * 工作申请
 */

public class UDWORKAPPLY implements Serializable {
    public String UDWORKAPPLYID;
    public String WOAPPLYNUM;//工作申请编号
    public String DESCRIPTION;//工作申请描述
    public String CREATEBYNAME;//创建人
    public String ASSETNUM;//资产编号
    public String ASSETDES;//资产描述
    public String STATIONDES;//站场
    public String LOCATION;//位置编号
    public String LOCATIONDES;//位置描述
    public String STATUS;//状态
    public String GTADES;//站场
    public String PURPOSE;//目的
    public String WORKEXE;//工作执行人数
    public String PREPAREDBYNAME;//准备人姓名
    public String PREPAREDBYTITLE;//职位
    public String CREATEDATE;//开始时间
    public String WORKDATES;//工期
    public String PLANNUM;//年度计划编号
    public String JPNUM;//作业计划编号
    public String WONUM;//工单编号

    public String getUDWORKAPPLYID() {
        return UDWORKAPPLYID;
    }

    public void setUDWORKAPPLYID(String UDWORKAPPLYID) {
        this.UDWORKAPPLYID = UDWORKAPPLYID;
    }

    public String getWOAPPLYNUM() {
        return WOAPPLYNUM;
    }

    public void setWOAPPLYNUM(String WOAPPLYNUM) {
        this.WOAPPLYNUM = WOAPPLYNUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getCREATEBYNAME() {
        return CREATEBYNAME;
    }

    public void setCREATEBYNAME(String CREATEBYNAME) {
        this.CREATEBYNAME = CREATEBYNAME;
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

    public String getSTATIONDES() {
        return STATIONDES;
    }

    public void setSTATIONDES(String STATIONDES) {
        this.STATIONDES = STATIONDES;
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

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getGTADES() {
        return GTADES;
    }

    public void setGTADES(String GTADES) {
        this.GTADES = GTADES;
    }

    public String getPURPOSE() {
        return PURPOSE;
    }

    public void setPURPOSE(String PURPOSE) {
        this.PURPOSE = PURPOSE;
    }

    public String getWORKEXE() {
        return WORKEXE;
    }

    public void setWORKEXE(String WORKEXE) {
        this.WORKEXE = WORKEXE;
    }

    public String getPREPAREDBYNAME() {
        return PREPAREDBYNAME;
    }

    public void setPREPAREDBYNAME(String PREPAREDBYNAME) {
        this.PREPAREDBYNAME = PREPAREDBYNAME;
    }

    public String getPREPAREDBYTITLE() {
        return PREPAREDBYTITLE;
    }

    public void setPREPAREDBYTITLE(String PREPAREDBYTITLE) {
        this.PREPAREDBYTITLE = PREPAREDBYTITLE;
    }

    public String getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(String CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    public String getWORKDATES() {
        return WORKDATES;
    }

    public void setWORKDATES(String WORKDATES) {
        this.WORKDATES = WORKDATES;
    }

    public String getPLANNUM() {
        return PLANNUM;
    }

    public void setPLANNUM(String PLANNUM) {
        this.PLANNUM = PLANNUM;
    }

    public String getJPNUM() {
        return JPNUM;
    }

    public void setJPNUM(String JPNUM) {
        this.JPNUM = JPNUM;
    }

    public String getWONUM() {
        return WONUM;
    }

    public void setWONUM(String WONUM) {
        this.WONUM = WONUM;
    }
}
