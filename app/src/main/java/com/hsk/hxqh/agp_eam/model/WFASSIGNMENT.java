package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 * 待办任务
 */

public class WFASSIGNMENT implements Serializable {
    public String APP;//应用程序的名称
    public String ASSIGNCODE;//任务分配人
    public String DESCRIPTION;//描述
    public String DUEDATE;//到期日期
    public String ORIGPERSON;//进行委派之前的任务分配的原始人员
    public String OWNERID;//受控制记录的唯一标识  //keyValue
    public String OWNERTABLE;//表名
    public String PROCESSNAME;//过程名称  不显示
    public String ROLEID;//任务角色  不显示
    public String STARTDATE;//流程发起日期
    public String WFASSIGNMENTID;//编号

    public String getAPP() {
        return APP;
    }

    public void setAPP(String APP) {
        this.APP = APP;
    }

    public String getASSIGNCODE() {
        return ASSIGNCODE;
    }

    public void setASSIGNCODE(String ASSIGNCODE) {
        this.ASSIGNCODE = ASSIGNCODE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getDUEDATE() {
        return DUEDATE;
    }

    public void setDUEDATE(String DUEDATE) {
        this.DUEDATE = DUEDATE;
    }

    public String getORIGPERSON() {
        return ORIGPERSON;
    }

    public void setORIGPERSON(String ORIGPERSON) {
        this.ORIGPERSON = ORIGPERSON;
    }

    public String getOWNERID() {
        return OWNERID;
    }

    public void setOWNERID(String OWNERID) {
        this.OWNERID = OWNERID;
    }

    public String getOWNERTABLE() {
        return OWNERTABLE;
    }

    public void setOWNERTABLE(String OWNERTABLE) {
        this.OWNERTABLE = OWNERTABLE;
    }

    public String getPROCESSNAME() {
        return PROCESSNAME;
    }

    public void setPROCESSNAME(String PROCESSNAME) {
        this.PROCESSNAME = PROCESSNAME;
    }

    public String getROLEID() {
        return ROLEID;
    }

    public void setROLEID(String ROLEID) {
        this.ROLEID = ROLEID;
    }

    public String getSTARTDATE() {
        return STARTDATE;
    }

    public void setSTARTDATE(String STARTDATE) {
        this.STARTDATE = STARTDATE;
    }

    public String getWFASSIGNMENTID() {
        return WFASSIGNMENTID;
    }

    public void setWFASSIGNMENTID(String WFASSIGNMENTID) {
        this.WFASSIGNMENTID = WFASSIGNMENTID;
    }
}
