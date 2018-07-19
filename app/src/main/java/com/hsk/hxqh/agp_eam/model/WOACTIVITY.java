package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 */

public class WOACTIVITY implements Serializable {
    public String TASKID;//
    public String DESCRIPTION;//描述
    public String ESTDUR;//
    public String STATUS;//
    public String WOSEQUENCE;//
    public String TYPE;

    public String getTASKID() {
        return TASKID;
    }

    public void setTASKID(String TASKID) {
        this.TASKID = TASKID;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getESTDUR() {
        return ESTDUR;
    }

    public void setESTDUR(String ESTDUR) {
        this.ESTDUR = ESTDUR;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getWOSEQUENCE() {
        return WOSEQUENCE;
    }

    public void setWOSEQUENCE(String WOSEQUENCE) {
        this.WOSEQUENCE = WOSEQUENCE;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
