package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 * 库存盘点
 */

public class UDSTOCK implements Serializable {
    public static final String TABLE_NAME = "UDSTOCK";
    public String STOCKNUM;//
    public String DESCRIPTION;//
    public String STOREROOM;//
    public String UDSTOCKSTODES;//
    public String INVOWNER;//
    public String UDSTOCKINVDIS;//
    public String REMARK;//
    public String STATUS;//
    public String UDSTOCKCREDIS;//
    public String CREATEDATE;//

    public String getSTOCKNUM() {
        return STOCKNUM;
    }

    public void setSTOCKNUM(String STOCKNUM) {
        this.STOCKNUM = STOCKNUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getSTOREROOM() {
        return STOREROOM;
    }

    public void setSTOREROOM(String STOREROOM) {
        this.STOREROOM = STOREROOM;
    }

    public String getUDSTOCKSTODES() {
        return UDSTOCKSTODES;
    }

    public void setUDSTOCKSTODES(String UDSTOCKSTODES) {
        this.UDSTOCKSTODES = UDSTOCKSTODES;
    }

    public String getINVOWNER() {
        return INVOWNER;
    }

    public void setINVOWNER(String INVOWNER) {
        this.INVOWNER = INVOWNER;
    }

    public String getUDSTOCKINVDIS() {
        return UDSTOCKINVDIS;
    }

    public void setUDSTOCKINVDIS(String UDSTOCKINVDIS) {
        this.UDSTOCKINVDIS = UDSTOCKINVDIS;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getUDSTOCKCREDIS() {
        return UDSTOCKCREDIS;
    }

    public void setUDSTOCKCREDIS(String UDSTOCKCREDIS) {
        this.UDSTOCKCREDIS = UDSTOCKCREDIS;
    }

    public String getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(String CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }
}
