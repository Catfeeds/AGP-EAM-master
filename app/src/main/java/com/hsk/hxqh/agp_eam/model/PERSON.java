package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 * 员工
 */

public class PERSON implements Serializable {
    public String PERSONID;//
    public String DISPLAYNAME;//
    public String DEPARTMENT;//
    public String TITLE;//
    public String UDCLASSNAME;//
    public String UDSHIFTSTATUS;//
    public String UDUDTITLECODE;//

    public String getPERSONID() {
        return PERSONID;
    }

    public void setPERSONID(String PERSONID) {
        this.PERSONID = PERSONID;
    }

    public String getDISPLAYNAME() {
        return DISPLAYNAME;
    }

    public void setDISPLAYNAME(String DISPLAYNAME) {
        this.DISPLAYNAME = DISPLAYNAME;
    }

    public String getDEPARTMENT() {
        return DEPARTMENT;
    }

    public void setDEPARTMENT(String DEPARTMENT) {
        this.DEPARTMENT = DEPARTMENT;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getUDCLASSNAME() {
        return UDCLASSNAME;
    }

    public void setUDCLASSNAME(String UDCLASSNAME) {
        this.UDCLASSNAME = UDCLASSNAME;
    }

    public String getUDSHIFTSTATUS() {
        return UDSHIFTSTATUS;
    }

    public void setUDSHIFTSTATUS(String UDSHIFTSTATUS) {
        this.UDSHIFTSTATUS = UDSHIFTSTATUS;
    }

    public String getUDUDTITLECODE() {
        return UDUDTITLECODE;
    }

    public void setUDUDTITLECODE(String UDUDTITLECODE) {
        this.UDUDTITLECODE = UDUDTITLECODE;
    }
}
