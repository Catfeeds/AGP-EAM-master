package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 * 库存余量
 */

public class INVBALANCES implements Serializable {

    public String BINNUM;//货柜
    public String LOTNUM;//批次
    public String CURBAL;//当前余量
    public String STAGEDCURBAL;//暂存余量
    public String PHYSCNT;//实际库存量
    public String PHYSCNTDATE;//实际盘点日期
    public String RECONCILED;//已调整
    private String ITEMNUM;//
    private String DESCRIPTION;//
    private String ITEMNUM_DEC;
    private String LOCATION;

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getITEMNUM_DEC() {
        return ITEMNUM_DEC;
    }

    public void setITEMNUM_DEC(String ITEMNUM_DEC) {
        this.ITEMNUM_DEC = ITEMNUM_DEC;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }



    public String getBINNUM() {
        return BINNUM;
    }

    public void setBINNUM(String BINNUM) {
        this.BINNUM = BINNUM;
    }

    public String getLOTNUM() {
        return LOTNUM;
    }

    public void setLOTNUM(String LOTNUM) {
        this.LOTNUM = LOTNUM;
    }

    public String getCURBAL() {
        return CURBAL;
    }

    public void setCURBAL(String CURBAL) {
        this.CURBAL = CURBAL;
    }

    public String getSTAGEDCURBAL() {
        return STAGEDCURBAL;
    }

    public void setSTAGEDCURBAL(String STAGEDCURBAL) {
        this.STAGEDCURBAL = STAGEDCURBAL;
    }

    public String getPHYSCNT() {
        return PHYSCNT;
    }

    public void setPHYSCNT(String PHYSCNT) {
        this.PHYSCNT = PHYSCNT;
    }

    public String getPHYSCNTDATE() {
        return PHYSCNTDATE;
    }

    public void setPHYSCNTDATE(String PHYSCNTDATE) {
        this.PHYSCNTDATE = PHYSCNTDATE;
    }

    public String getRECONCILED() {
        return RECONCILED;
    }

    public void setRECONCILED(String RECONCILED) {
        this.RECONCILED = RECONCILED;
    }
}
