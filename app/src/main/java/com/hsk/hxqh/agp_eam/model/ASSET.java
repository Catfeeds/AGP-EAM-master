package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 */

public class ASSET implements Serializable {
    public String ASSETNUM;//资产
    public String CLASSDESC;//
    public String CLASSFICATION;//
    public String CLASSSTRUCTUREID;//
    public String DESCRIPTION;//描述
    public String FIRSTDESC;//设备大类描述
    public String LOCATION;//位置
    public String LOCDESC;//位置描述
    public String PARENT;//父级
    public String PARENTDESC;//父级描述
    public String SECONDESC;//设备中类描述
    public String THIRDESC;//设备小类描述
    public String UDCONNECTION;//链接方式
    public String UDEQMSTATUS;//设备状态
    public String UDFIRSTCLASS;//设备大类
    public String UDMAINTIME;//出厂时间
    public String UDMANUFACTURER;//生产厂家
    public String UDMODULE;//规格型号
    public String UDREMARKS;//备注
    public String UDSECONDCLASS;//设备中类
    public String UDSEQUENCENO;//出厂序列号
    public String UDTHIRDCLASS;//设备小类
    public String UDUSETIME;//投用日期

    public String getASSETNUM() {
        return ASSETNUM;
    }

    public void setASSETNUM(String ASSETNUM) {
        this.ASSETNUM = ASSETNUM;
    }

    public String getCLASSDESC() {
        return CLASSDESC;
    }

    public void setCLASSDESC(String CLASSDESC) {
        this.CLASSDESC = CLASSDESC;
    }

    public String getCLASSFICATION() {
        return CLASSFICATION;
    }

    public void setCLASSFICATION(String CLASSFICATION) {
        this.CLASSFICATION = CLASSFICATION;
    }

    public String getCLASSSTRUCTUREID() {
        return CLASSSTRUCTUREID;
    }

    public void setCLASSSTRUCTUREID(String CLASSSTRUCTUREID) {
        this.CLASSSTRUCTUREID = CLASSSTRUCTUREID;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getFIRSTDESC() {
        return FIRSTDESC;
    }

    public void setFIRSTDESC(String FIRSTDESC) {
        this.FIRSTDESC = FIRSTDESC;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getLOCDESC() {
        return LOCDESC;
    }

    public void setLOCDESC(String LOCDESC) {
        this.LOCDESC = LOCDESC;
    }

    public String getPARENT() {
        return PARENT;
    }

    public void setPARENT(String PARENT) {
        this.PARENT = PARENT;
    }

    public String getPARENTDESC() {
        return PARENTDESC;
    }

    public void setPARENTDESC(String PARENTDESC) {
        this.PARENTDESC = PARENTDESC;
    }

    public String getSECONDESC() {
        return SECONDESC;
    }

    public void setSECONDESC(String SECONDESC) {
        this.SECONDESC = SECONDESC;
    }

    public String getTHIRDESC() {
        return THIRDESC;
    }

    public void setTHIRDESC(String THIRDESC) {
        this.THIRDESC = THIRDESC;
    }

    public String getUDCONNECTION() {
        return UDCONNECTION;
    }

    public void setUDCONNECTION(String UDCONNECTION) {
        this.UDCONNECTION = UDCONNECTION;
    }

    public String getUDEQMSTATUS() {
        return UDEQMSTATUS;
    }

    public void setUDEQMSTATUS(String UDEQMSTATUS) {
        this.UDEQMSTATUS = UDEQMSTATUS;
    }

    public String getUDFIRSTCLASS() {
        return UDFIRSTCLASS;
    }

    public void setUDFIRSTCLASS(String UDFIRSTCLASS) {
        this.UDFIRSTCLASS = UDFIRSTCLASS;
    }

    public String getUDMAINTIME() {
        return UDMAINTIME;
    }

    public void setUDMAINTIME(String UDMAINTIME) {
        this.UDMAINTIME = UDMAINTIME;
    }

    public String getUDMANUFACTURER() {
        return UDMANUFACTURER;
    }

    public void setUDMANUFACTURER(String UDMANUFACTURER) {
        this.UDMANUFACTURER = UDMANUFACTURER;
    }

    public String getUDMODULE() {
        return UDMODULE;
    }

    public void setUDMODULE(String UDMODULE) {
        this.UDMODULE = UDMODULE;
    }

    public String getUDREMARKS() {
        return UDREMARKS;
    }

    public void setUDREMARKS(String UDREMARKS) {
        this.UDREMARKS = UDREMARKS;
    }

    public String getUDSECONDCLASS() {
        return UDSECONDCLASS;
    }

    public void setUDSECONDCLASS(String UDSECONDCLASS) {
        this.UDSECONDCLASS = UDSECONDCLASS;
    }

    public String getUDSEQUENCENO() {
        return UDSEQUENCENO;
    }

    public void setUDSEQUENCENO(String UDSEQUENCENO) {
        this.UDSEQUENCENO = UDSEQUENCENO;
    }

    public String getUDTHIRDCLASS() {
        return UDTHIRDCLASS;
    }

    public void setUDTHIRDCLASS(String UDTHIRDCLASS) {
        this.UDTHIRDCLASS = UDTHIRDCLASS;
    }

    public String getUDUSETIME() {
        return UDUSETIME;
    }

    public void setUDUSETIME(String UDUSETIME) {
        this.UDUSETIME = UDUSETIME;
    }
}
