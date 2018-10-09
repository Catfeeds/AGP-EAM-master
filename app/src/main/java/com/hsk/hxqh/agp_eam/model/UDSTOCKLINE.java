package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 * 库存盘点子表
 */

public class UDSTOCKLINE implements Serializable {
    public String UDSTOCKLINEID;
    public String ISCHECK;

    public String getISCHECK() {
        return ISCHECK;
    }

    public void setISCHECK(String ISCHECK) {
        this.ISCHECK = ISCHECK;
    }

    public String getUDSTOCKLINEID() {
        return UDSTOCKLINEID;
    }

    public void setUDSTOCKLINEID(String UDSTOCKLINE) {
        this.UDSTOCKLINEID = UDSTOCKLINE;
    }

    public String SN;//序号
    public String ITEMNUM;//物资编码
    public String UDSTOCKLINEITEDES;//物资名称
    public String UDSTOCKLINEITEISS;//发放单位
    public String QUANTITY1;//账存数量
    public String QUANTITY2;//实际数量
    public String DIFFERENCE;//差异数量
    public String REASON;//差异原因
    public String LOTNUM;
    public String BINNUM;

    public String getBINNUM() {
        return BINNUM;
    }

    public void setBINNUM(String BINNUM) {
        this.BINNUM = BINNUM;
    }

    public String getUDSTOCKLINEITEMODER() {
        return UDSTOCKLINEITEMODER;
    }

    public void setUDSTOCKLINEITEMODER(String UDSTOCKLINEITEMODER) {
        this.UDSTOCKLINEITEMODER = UDSTOCKLINEITEMODER;
    }

    public String UDSTOCKLINEITEMODER;//订购单位

    public String getLOTNUM() {
        return LOTNUM;
    }

    public void setLOTNUM(String LOTNUM) {
        this.LOTNUM = LOTNUM;
    }

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getUDSTOCKLINEITEDES() {
        return UDSTOCKLINEITEDES;
    }

    public void setUDSTOCKLINEITEDES(String UDSTOCKLINEITEDES) {
        this.UDSTOCKLINEITEDES = UDSTOCKLINEITEDES;
    }

    public String getUDSTOCKLINEITEISS() {
        return UDSTOCKLINEITEISS;
    }

    public void setUDSTOCKLINEITEISS(String UDSTOCKLINEITEISS) {
        this.UDSTOCKLINEITEISS = UDSTOCKLINEITEISS;
    }

    public String getQUANTITY1() {
        return QUANTITY1;
    }

    public void setQUANTITY1(String QUANTITY1) {
        this.QUANTITY1 = QUANTITY1;
    }

    public String getQUANTITY2() {
        return QUANTITY2;
    }

    public void setQUANTITY2(String QUANTITY2) {
        this.QUANTITY2 = QUANTITY2;
    }

    public String getDIFFERENCE() {
        return DIFFERENCE;
    }

    public void setDIFFERENCE(String DIFFERENCE) {
        if (DIFFERENCE!= null){
            this.DIFFERENCE = DIFFERENCE.replace(",","");
        }else {
            this.DIFFERENCE = DIFFERENCE;
        }

    }

    public String getREASON() {
        return REASON;
    }

    public void setREASON(String REASON) {
        this.REASON = REASON;
    }
}
