package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 * 库存
 */

public class INVENTORY implements Serializable {
    public String ITEMNUM;//项目
    public String ITEMNUM_DEC;//项目描述
    public String LOCATION;//库房
    public String LOCATION_DEC;//库房描述
    public String STATUS;//状态
    public String ISSUEUNIT;//发放单位
    public String CURBALTOTAL;//当前余量
    public String AVBLBALANCE;//可用量
    public String BINNUM;//缺省货柜
    public String ROTATING;//固定资产
    public String SITEID;//地点
    public String ITEMSETID;//物资地点itemsetid

    public void setSITEID(String SITEID) {
        this.SITEID = SITEID;
    }

    public String getSITEID() {
        return SITEID;
    }

    public void setITEMSETID(String ITEMSITEID) {
        this.ITEMSETID = ITEMSITEID;
    }

    public String getITEMSETID() {
        return ITEMSETID;
    }

    public String getROTATING() {
        return ROTATING;
    }

    public void setROTATING(String ISROTATING) {
        this.ROTATING = ISROTATING;
    }

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getITEMNUM_DEC() {
        return ITEMNUM_DEC;
    }

    public void setITEMNUM_DEC(String ITEMNUM_DEC) {
        this.ITEMNUM_DEC = ITEMNUM_DEC;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getLOCATION_DEC() {
        return LOCATION_DEC;
    }

    public void setLOCATION_DEC(String LOCATION_DEC) {
        this.LOCATION_DEC = LOCATION_DEC;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getISSUEUNIT() {
        return ISSUEUNIT;
    }

    public void setISSUEUNIT(String ISSUEUNIT) {
        this.ISSUEUNIT = ISSUEUNIT;
    }

    public String getCURBALTOTAL() {
        return CURBALTOTAL;
    }

    public void setCURBALTOTAL(String CURBALTOTAL) {
        this.CURBALTOTAL = CURBALTOTAL;
    }

    public String getAVBLBALANCE() {
        return AVBLBALANCE;
    }

    public void setAVBLBALANCE(String AVBLBALANCE) {
        this.AVBLBALANCE = AVBLBALANCE;
    }

    public String getBINNUM() {
        return BINNUM;
    }

    public void setBINNUM(String BINNUM) {
        this.BINNUM = BINNUM;
    }
}
