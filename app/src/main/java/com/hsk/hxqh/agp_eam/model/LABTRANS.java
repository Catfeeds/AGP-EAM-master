package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 * 实际员工
 */

public class LABTRANS implements Serializable {
    public String ACTUALSTASKID;//任务
    public String LABORNAME;//员工名称
    public String LABORCODE;//员工
    public String STARTDATE;//开始日期
    public String STARTTIME;//开始时间
    public String FINISHDATE;//结束日期
    public String FINISHTIME;//结束时间
    public String REGULARHRS;//常规时数
    public String TYPE;

    public String getLABORNAME() {
        return LABORNAME;
    }

    public void setLABORNAME(String LABORNAME) {
        this.LABORNAME = LABORNAME;
    }

    public String getACTUALSTASKID() {
        return ACTUALSTASKID;
    }

    public void setACTUALSTASKID(String ACTUALSTASKID) {
        this.ACTUALSTASKID = ACTUALSTASKID;
    }

    public String getLABORCODE() {
        return LABORCODE;
    }

    public void setLABORCODE(String LABORCODE) {
        this.LABORCODE = LABORCODE;
    }

    public String getSTARTDATE() {
        return STARTDATE;
    }

    public void setSTARTDATE(String STARTDATE) {
        this.STARTDATE = STARTDATE;
    }

    public String getSTARTTIME() {
        return STARTTIME;
    }

    public void setSTARTTIME(String STARTTIME) {
        this.STARTTIME = STARTTIME;
    }

    public String getREGULARHRS() {
        return REGULARHRS;
    }

    public void setREGULARHRS(String REGULARHRS) {
        this.REGULARHRS = REGULARHRS;
    }

    public String getFINISHDATE() {
        return FINISHDATE;
    }

    public void setFINISHDATE(String FINISHDATE) {
        this.FINISHDATE = FINISHDATE;
    }

    public String getFINISHTIME() {
        return FINISHTIME;
    }

    public void setFINISHTIME(String FINISHTIME) {
        this.FINISHTIME = FINISHTIME;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
