package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zzw on 2018/6/5.
 */


public class INVRESERVE implements Serializable {

    private String ACTUALQTY;
    private String ASSETNUM;
    private String CONDITIONCODE;
    private String DESCRIPTION;
    private String DIRECTREQ;
    private String FINCNTRLID;
    private String INITFLAG;
    private String ISSUETO;
    private String ITEMNUM;
    private String ITEMSETID;
    private String LOCATION;
    private String MRLINENUM;
    private String MRNUM;
    private String OPLOCATION;
    private String ORGID;
    private String PENDINGQTY;
    private String POLINEID;
    private String POLINENUM;
    private String PONUM;
    private String REQUESTEDBY;
    private Date REQUESTEDDATE;
    private Date REQUIREDDATE;
    private String RESERVEDQTY;
    private String RESTYPE;
    private String SHIPPEDQTY;
    private String SITEID;
    private String STAGEDQTY;
    private String TOSTORELOC;
    private String WONUM;
    private String REQUESTNUM;

    public String getREQUESTNUM() {
        return REQUESTNUM;
    }

    public void setREQUESTNUM(String REQUESTNUM) {
        this.REQUESTNUM = REQUESTNUM;
    }

    public void setACTUALQTY(String ACTUALQTY) {
        this.ACTUALQTY = ACTUALQTY;
    }
    public String getACTUALQTY() {
        return ACTUALQTY;
    }

    public void setASSETNUM(String ASSETNUM) {
        this.ASSETNUM = ASSETNUM;
    }
    public String getASSETNUM() {
        return ASSETNUM;
    }

    public void setCONDITIONCODE(String CONDITIONCODE) {
        this.CONDITIONCODE = CONDITIONCODE;
    }
    public String getCONDITIONCODE() {
        return CONDITIONCODE;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }
    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDIRECTREQ(String DIRECTREQ) {
        this.DIRECTREQ = DIRECTREQ;
    }
    public String getDIRECTREQ() {
        return DIRECTREQ;
    }

    public void setFINCNTRLID(String FINCNTRLID) {
        this.FINCNTRLID = FINCNTRLID;
    }
    public String getFINCNTRLID() {
        return FINCNTRLID;
    }

    public void setINITFLAG(String INITFLAG) {
        this.INITFLAG = INITFLAG;
    }
    public String getINITFLAG() {
        return INITFLAG;
    }

    public void setISSUETO(String ISSUETO) {
        this.ISSUETO = ISSUETO;
    }
    public String getISSUETO() {
        return ISSUETO;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }
    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMSETID(String ITEMSETID) {
        this.ITEMSETID = ITEMSETID;
    }
    public String getITEMSETID() {
        return ITEMSETID;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }
    public String getLOCATION() {
        return LOCATION;
    }

    public void setMRLINENUM(String MRLINENUM) {
        this.MRLINENUM = MRLINENUM;
    }
    public String getMRLINENUM() {
        return MRLINENUM;
    }

    public void setMRNUM(String MRNUM) {
        this.MRNUM = MRNUM;
    }
    public String getMRNUM() {
        return MRNUM;
    }

    public void setOPLOCATION(String OPLOCATION) {
        this.OPLOCATION = OPLOCATION;
    }
    public String getOPLOCATION() {
        return OPLOCATION;
    }

    public void setORGID(String ORGID) {
        this.ORGID = ORGID;
    }
    public String getORGID() {
        return ORGID;
    }

    public void setPENDINGQTY(String PENDINGQTY) {
        this.PENDINGQTY = PENDINGQTY;
    }
    public String getPENDINGQTY() {
        return PENDINGQTY;
    }

    public void setPOLINEID(String POLINEID) {
        this.POLINEID = POLINEID;
    }
    public String getPOLINEID() {
        return POLINEID;
    }

    public void setPOLINENUM(String POLINENUM) {
        this.POLINENUM = POLINENUM;
    }
    public String getPOLINENUM() {
        return POLINENUM;
    }

    public void setPONUM(String PONUM) {
        this.PONUM = PONUM;
    }
    public String getPONUM() {
        return PONUM;
    }

    public void setREQUESTEDBY(String REQUESTEDBY) {
        this.REQUESTEDBY = REQUESTEDBY;
    }
    public String getREQUESTEDBY() {
        return REQUESTEDBY;
    }

    public void setREQUESTEDDATE(Date REQUESTEDDATE) {
        this.REQUESTEDDATE = REQUESTEDDATE;
    }
    public Date getREQUESTEDDATE() {
        return REQUESTEDDATE;
    }

    public void setREQUIREDDATE(Date REQUIREDDATE) {
        this.REQUIREDDATE = REQUIREDDATE;
    }
    public Date getREQUIREDDATE() {
        return REQUIREDDATE;
    }

    public void setRESERVEDQTY(String RESERVEDQTY) {
        this.RESERVEDQTY = RESERVEDQTY;
    }
    public String getRESERVEDQTY() {
        return RESERVEDQTY;
    }

    public void setRESTYPE(String RESTYPE) {
        this.RESTYPE = RESTYPE;
    }
    public String getRESTYPE() {
        return RESTYPE;
    }

    public void setSHIPPEDQTY(String SHIPPEDQTY) {
        this.SHIPPEDQTY = SHIPPEDQTY;
    }
    public String getSHIPPEDQTY() {
        return SHIPPEDQTY;
    }

    public void setSITEID(String SITEID) {
        this.SITEID = SITEID;
    }
    public String getSITEID() {
        return SITEID;
    }

    public void setSTAGEDQTY(String STAGEDQTY) {
        this.STAGEDQTY = STAGEDQTY;
    }
    public String getSTAGEDQTY() {
        return STAGEDQTY;
    }

    public void setTOSTORELOC(String TOSTORELOC) {
        this.TOSTORELOC = TOSTORELOC;
    }
    public String getTOSTORELOC() {
        return TOSTORELOC;
    }

    public void setWONUM(String WONUM) {
        this.WONUM = WONUM;
    }
    public String getWONUM() {
        return WONUM;
    }

}
