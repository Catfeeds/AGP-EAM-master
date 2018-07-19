package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 * 故障提报单
 */

public class UDFAULTREPORT implements Serializable {
    public String UDFAULTREPORTID;
    public String UDFAULTREPORTNUM;//故障编号
    public String DESCRIPTION;//故障描述
    public String UDSTATIONNUM;//站场编号
    public String ASSETNUM;//资产编号
    public String ASSETDES;//资产描述
    public String STATIONDES;//站场
    public String LOCATION;//位置编号
    public String LOCATIONDES;//位置描述
    public String UDINSIDE;//站场内？
    public String UDINLOCATION;//需要派工？
    public String UDSJGY;//技术整改？
    public String FAULTDESC;//故障描述
    public String REASON;//故障原因
    public String MATERIAL;//消耗物料
    public String STATUS;//状态
    public String CREATEDATE;//创建时间
    public String CREATEBYNAME;//创建人
    public String CONCLUSION;//故障总结

    public String getUDFAULTREPORTID() {
        return UDFAULTREPORTID;
    }

    public void setUDFAULTREPORTID(String UDFAULTREPORTID) {
        this.UDFAULTREPORTID = UDFAULTREPORTID;
    }

    public String getUDFAULTREPORTNUM() {
        return UDFAULTREPORTNUM;
    }

    public void setUDFAULTREPORTNUM(String UDFAULTREPORTNUM) {
        this.UDFAULTREPORTNUM = UDFAULTREPORTNUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getUDSTATIONNUM() {
        return UDSTATIONNUM;
    }

    public void setUDSTATIONNUM(String UDSTATIONNUM) {
        this.UDSTATIONNUM = UDSTATIONNUM;
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

    public String getUDINSIDE() {
        return UDINSIDE;
    }

    public void setUDINSIDE(String UDINSIDE) {
        this.UDINSIDE = UDINSIDE;
    }

    public String getUDINLOCATION() {
        return UDINLOCATION;
    }

    public void setUDINLOCATION(String UDINLOCATION) {
        this.UDINLOCATION = UDINLOCATION;
    }

    public String getUDSJGY() {
        return UDSJGY;
    }

    public void setUDSJGY(String UDSJGY) {
        this.UDSJGY = UDSJGY;
    }

    public String getFAULTDESC() {
        return FAULTDESC;
    }

    public void setFAULTDESC(String FAULTDESC) {
        this.FAULTDESC = FAULTDESC;
    }

    public String getREASON() {
        return REASON;
    }

    public void setREASON(String REASON) {
        this.REASON = REASON;
    }

    public String getMATERIAL() {
        return MATERIAL;
    }

    public void setMATERIAL(String MATERIAL) {
        this.MATERIAL = MATERIAL;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(String CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    public String getCREATEBYNAME() {
        return CREATEBYNAME;
    }

    public void setCREATEBYNAME(String CREATEBYNAME) {
        this.CREATEBYNAME = CREATEBYNAME;
    }

    public String getCONCLUSION() {
        return CONCLUSION;
    }

    public void setCONCLUSION(String CONCLUSION) {
        this.CONCLUSION = CONCLUSION;
    }
}
