package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 * 计划员工
 */

public class WPLABOR implements Serializable {
    public String TASKID;//
    public String LABORCODE;//
    public String QUANTITY;//
    public String TYPE;

    public String getTASKID() {
        return TASKID;
    }

    public void setTASKID(String TASKID) {
        this.TASKID = TASKID;
    }

    public String getLABORCODE() {
        return LABORCODE;
    }

    public void setLABORCODE(String LABORCODE) {
        this.LABORCODE = LABORCODE;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
