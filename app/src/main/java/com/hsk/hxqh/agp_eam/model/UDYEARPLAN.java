package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 * 年度计划
 */

public class UDYEARPLAN implements Serializable {
    public String PLANNUM;//
    public String DESCRIPTION;//
    public String JOBPLANNUM;//
    public String SERIALNO;//
    public String STARTDATE;//
    public String SUBJECT;//

    public String getPLANNUM() {
        return PLANNUM;
    }

    public void setPLANNUM(String PLANNUM) {
        this.PLANNUM = PLANNUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getJOBPLANNUM() {
        return JOBPLANNUM;
    }

    public void setJOBPLANNUM(String JOBPLANNUM) {
        this.JOBPLANNUM = JOBPLANNUM;
    }

    public String getSERIALNO() {
        return SERIALNO;
    }

    public void setSERIALNO(String SERIALNO) {
        this.SERIALNO = SERIALNO;
    }

    public String getSTARTDATE() {
        return STARTDATE;
    }

    public void setSTARTDATE(String STARTDATE) {
        this.STARTDATE = STARTDATE;
    }

    public String getSUBJECT() {
        return SUBJECT;
    }

    public void setSUBJECT(String SUBJECT) {
        this.SUBJECT = SUBJECT;
    }
}
