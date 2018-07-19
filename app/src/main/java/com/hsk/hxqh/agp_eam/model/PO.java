package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by zzw on 2018/6/11.
 */

public class PO implements Serializable {
    public static final String TABLE_NAME= "PO";
    private String POID;
    private String PONUM;//Po
    private String DESCRIPTION;//描述
    private String STATIONDESC;//占场
    private String STATUS;//PO 状态
    private String RECEIPTS;// 接收状态
    private String ORDERDATE;//订购日期
    private String PRETAXTOTAL;//税前总记
    private String RECEIVEDTOTALCOST;//已接收成本
    private String POSTADES;
    private String POPOVENDORNAME;
    private String POSHIDIS;
    private String UDSTATION;

    public String getUDSTATION() {
        return UDSTATION;
    }
    public void setUDSTATION(String UDSTATION) {
        this.UDSTATION = UDSTATION;
    }

    public String getPOID() {
        return POID;
    }

    public void setPOID(String POID) {
        this.POID = POID;
    }

    public String getPOSHIDIS() {
        return POSHIDIS;
    }

    public void setPOSHIDIS(String POSHIDIS) {
        this.POSHIDIS = POSHIDIS;
    }

    public String getPOPOVENDORNAME() {
        return POPOVENDORNAME;
    }

    public void setPOPOVENDORNAME(String POPOVENDORNAME) {
        this.POPOVENDORNAME = POPOVENDORNAME;
    }

    public String getPOSTADES() {
        return POSTADES;
    }

    public void setPOSTADES(String POSTADES) {
        this.POSTADES = POSTADES;
    }

    public String getPONUM() {
        return PONUM;
    }

    public void setPONUM(String PONUM) {
        this.PONUM = PONUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getSTATIONDESC() {
        return STATIONDESC;
    }

    public void setSTATIONDESC(String STATIONDESC) {
        this.STATIONDESC = STATIONDESC;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUSDESC) {
        this.STATUS = STATUSDESC;
    }

    public String getRECEIPTS() {
        return RECEIPTS;
    }

    public void setRECEIPTS(String RECEIPTS) {
        this.RECEIPTS = RECEIPTS;
    }

    public String getORDERDATE() {
        return ORDERDATE;
    }

    public void setORDERDATE(String ORDERDATE) {
        this.ORDERDATE = ORDERDATE;
    }

    public String getPRETAXTOTAL() {
        return PRETAXTOTAL;
    }

    public void setPRETAXTOTAL(String PRETAXTOTAL) {
        this.PRETAXTOTAL = PRETAXTOTAL;
    }

    public String getRECEIVEDTOTALCOST() {
        return RECEIVEDTOTALCOST;
    }

    public void setRECEIVEDTOTALCOST(String RECEIVEDTOTALCOST) {
        this.RECEIVEDTOTALCOST = RECEIVEDTOTALCOST;
    }
}
