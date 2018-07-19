package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 */

public class ASSET_WORKORDER implements Serializable {
    public String WONUM;//资产
    public String DESCRIPTION;//描述
    public String STATUS;//状态
    public String STATUSDATE;//状态日期
    public String ACTFINISH;//实际开始时间
    public String ACTSTART;//实际完成时间

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

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getSTATUSDATE() {
        return STATUSDATE;
    }

    public void setSTATUSDATE(String STATUSDATE) {
        this.STATUSDATE = STATUSDATE;
    }

    public String getACTFINISH() {
        return ACTFINISH;
    }

    public void setACTFINISH(String ACTFINISH) {
        this.ACTFINISH = ACTFINISH;
    }

    public String getACTSTART() {
        return ACTSTART;
    }

    public void setACTSTART(String ACTSTART) {
        this.ACTSTART = ACTSTART;
    }
}
