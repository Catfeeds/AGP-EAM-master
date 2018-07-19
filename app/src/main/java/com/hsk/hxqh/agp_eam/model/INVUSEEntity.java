package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by wxs on 2018/4/3.
 */

public class INVUSEEntity implements Serializable {
    public static final String TABLE_NAME = "INVUSE";
    private String INVUSEID;
    private String DESCRIPTION;
    private String FROMSTORELOC;
    private String INVOWNER;
    private String INVUSECREATEBYDIS;
    private String INVUSEINVOWNERDIS;
    private String INVUSELOCATIONSDES;
    private String INVUSELOCATIONSSTA;
    private String STATUS;
    private String UDCREATEDATE;
    private String INVUSENUM;
    private String USETYPE;
    private String ENTERBY;
    private String UDCREATEBY;
    private String UDFILENO;
    private String SUBJECT;
    private String UDTOWAREHOUSE;
    private String UDINVOWNER;

    public String getINVUSEID() {
        return INVUSEID;
    }

    public void setINVUSEID(String INVUSEID) {
        this.INVUSEID = INVUSEID;
    }

    public String getUDTOWAREHOUSE() {
        return UDTOWAREHOUSE;
    }

    public void setUDTOWAREHOUSE(String UDTOWAREHOUSE) {
        this.UDTOWAREHOUSE = UDTOWAREHOUSE;
    }

    public String getUDINVOWNER() {
        return UDINVOWNER;
    }

    public void setUDINVOWNER(String UDINVOWNER) {
        this.UDINVOWNER = UDINVOWNER;
    }

    public String getSUBJECT() {
        return SUBJECT;
    }

    public void setSUBJECT(String SUBJECT) {
        this.SUBJECT = SUBJECT;
    }

    public String getUDFILENO() {
        return UDFILENO;
    }

    public void setUDFILENO(String UDFILENO) {
        this.UDFILENO = UDFILENO;
    }

    public String getUDCREATEBY() {
        return UDCREATEBY;
    }

    public void setUDCREATEBY(String UDCREATEBY) {
        this.UDCREATEBY = UDCREATEBY;
    }

    public String getENTERBY() {
        return ENTERBY;
    }

    public void setENTERBY(String ENTERBY) {
        this.ENTERBY = ENTERBY;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getFROMSTORELOC() {
        return FROMSTORELOC;
    }

    public void setFROMSTORELOC(String FROMSTORELOC) {
        this.FROMSTORELOC = FROMSTORELOC;
    }

    public String getINVOWNER() {
        return INVOWNER;
    }

    public void setINVOWNER(String INVOWNER) {
        this.INVOWNER = INVOWNER;
    }

    public String getINVUSECREATEBYDIS() {
        return INVUSECREATEBYDIS;
    }

    public void setINVUSECREATEBYDIS(String INVUSECREATEBYDIS) {
        this.INVUSECREATEBYDIS = INVUSECREATEBYDIS;
    }

    public String getINVUSEINVOWNERDIS() {
        return INVUSEINVOWNERDIS;
    }

    public void setINVUSEINVOWNERDIS(String INVUSEINVOWNERDIS) {
        this.INVUSEINVOWNERDIS = INVUSEINVOWNERDIS;
    }

    public String getINVUSELOCATIONSDES() {
        return INVUSELOCATIONSDES;
    }

    public void setINVUSELOCATIONSDES(String INVUSELOCATIONSDES) {
        this.INVUSELOCATIONSDES = INVUSELOCATIONSDES;
    }

    public String getINVUSELOCATIONSSTA() {
        return INVUSELOCATIONSSTA;
    }

    public void setINVUSELOCATIONSSTA(String INVUSELOCATIONSSTA) {
        this.INVUSELOCATIONSSTA = INVUSELOCATIONSSTA;
    }

    public String getINVUSENUM() {
        return INVUSENUM;
    }

    public void setINVUSENUM(String INVUSENUM) {
        this.INVUSENUM = INVUSENUM;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getUDCREATEDATE() {
        return UDCREATEDATE;
    }

    public void setUDCREATEDATE(String UDCREATEDATE) {
        this.UDCREATEDATE = UDCREATEDATE;
    }

    public String getUSETYPE() {
        return USETYPE;
    }

    public void setUSETYPE(String USETYPE) {
        this.USETYPE = USETYPE;
    }
}
