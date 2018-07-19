package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzw on 2018/5/25.
 */

public class INVUSELINE implements Serializable{
    private String INVUSENUM;
    private String TOSTORELOC;
    private String TOBIN;
    private String TOLOT;
    private String FROMSTORELOC;
    private String INVUSELINENUM;
    private String ISSUETO;
    private String REFWO;
    private String CONVERSION = "1.00";
    private String USETYPE;//	使用情况类型
    private String ITEMNUM;//	物料编码
    private String DESCRIPTION;//	物料名称
    private String FROMBIN	;//原货柜
    private String FROMLOT	;//原批次
    private String QUANTITY;//	数量
    private String ISS;//	发放单位
    private String UNITDES;//	发放单位描述
    private String DISPLAYUNITCOST;//	单位成本
    private String DISPLAYLINECOST;//	行成本
    private String WONUM;//	工单
    private String ASSETNUM;//	资产
    private String LOCATION;//	位置
    private String ENTERBY;//	输入人
    private String ACTUALDATE;//	实际日期
    private String REMARK;//	备注
    private int INDEX;
    private String FLAG;
    private String INVMEASUREMENT;//发放单位
    private String PONUM;//工单
    private String REQUESTNUM;

    private String SHE;//货架寿命
    private String TASKID; //任务
    private String USE;//截止日期
    private  String TYPE = "";

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    public String getSHE() {
        return SHE;
    }

    public void setSHE(String SHE) {
        this.SHE = SHE;
    }

    public String getTASKID() {
        return TASKID;
    }

    public void setTASKID(String TASKID) {
        this.TASKID = TASKID;
    }

    public String getUSE() {
        return USE;
    }

    public void setUSE(String USE) {
        this.USE = USE;
    }

    public String getPONUM() {
        return PONUM;
    }

    public void setPONUM(String PONUM) {
        this.PONUM = PONUM;
    }

    public String getINVMEASUREMENT() {
        return INVMEASUREMENT;
    }

    public void setINVMEASUREMENT(String INVMEASUREMENT) {
        this.INVMEASUREMENT = INVMEASUREMENT;
    }

    public String getFlag() {
        return FLAG;
    }

    public void setFlag(String FLAG) {
        this.FLAG = FLAG;
    }

    public String getINVUSENUM() {
        return INVUSENUM;
    }

    public void setINVUSENUM(String INVUSENUM) {
        this.INVUSENUM = INVUSENUM;
    }

    public String getTOSTORELOC() {
        return TOSTORELOC;
    }

    public void setTOSTORELOC(String TOSTORELOC) {
        this.TOSTORELOC = TOSTORELOC;
    }

    public String getREQUESTNUM() {
        return REQUESTNUM;
    }

    public void setREQUESTNUM(String REQUESTNUM) {
        this.REQUESTNUM = REQUESTNUM;
    }

    public String getTOBIN() {
        return TOBIN;
    }

    public void setTOBIN(String TOBIN) {
        this.TOBIN = TOBIN;
    }

    public String getTOLOT() {
        return TOLOT;
    }

    public void setTOLOT(String TOLOT) {
        this.TOLOT = TOLOT;
    }

    public String getFROMSTORELOC() {
        return FROMSTORELOC;
    }

    public void setFROMSTORELOC(String FROMSTORELOC) {
        this.FROMSTORELOC = FROMSTORELOC;
    }

    public String getINVUSELINENUM() {
        return INVUSELINENUM;
    }

    public void setINVUSELINENUM(String INVUSELINENUM) {
        this.INVUSELINENUM = INVUSELINENUM;
    }

    public String getISSUETO() {
        return ISSUETO;
    }

    public void setISSUETO(String ISSUETO) {
        this.ISSUETO = ISSUETO;
    }

    public String getREFWO() {
        return REFWO;
    }

    public void setREFWO(String REFWO) {
        this.REFWO = REFWO;
    }

    public String getCONVERSION() {
        return CONVERSION;
    }

    public void setCONVERSION(String CONVERSION) {
        this.CONVERSION = CONVERSION;
    }

    public int getINDEX() {
        return INDEX;
    }
    public void setINDEX(int INDEX) {
        this.INDEX = INDEX;
        this.INVUSELINENUM = (INDEX + 1)+"";
    }
    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getFROMBIN() {
        return FROMBIN;
    }

    public void setFROMBIN(String FROMBIN) {
        this.FROMBIN = FROMBIN;
    }

    public String getFROMLOT() {
        return FROMLOT;
    }

    public void setFROMLOT(String FROMLOT) {
        this.FROMLOT = FROMLOT;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getISS() {
        return ISS;
    }

    public void setISS(String ISS) {
        this.ISS = ISS;
    }

    public String getUNITDES() {
        return UNITDES;
    }

    public void setUNITDES(String UNITDES) {
        this.UNITDES = UNITDES;
    }

    public String getDISPLAYUNITCOST() {
        return DISPLAYUNITCOST;
    }

    public void setDISPLAYUNITCOST(String DISPLAYUNITCOST) {
        this.DISPLAYUNITCOST = DISPLAYUNITCOST;
    }

    public String getDISPLAYLINECOST() {
        return DISPLAYLINECOST;
    }

    public void setDISPLAYLINECOST(String DISPLAYLINECOST) {
        this.DISPLAYLINECOST = DISPLAYLINECOST;
    }

    public String getWONUM() {
        return WONUM;
    }

    public void setWONUM(String WONUM) {
        this.WONUM = WONUM;
    }

    public String getASSETNUM() {
        return ASSETNUM;
    }

    public void setASSETNUM(String ASSETNUM) {
        this.ASSETNUM = ASSETNUM;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getENTERBY() {
        return ENTERBY;
    }

    public void setENTERBY(String ENTERBY) {
        this.ENTERBY = ENTERBY;
    }

    public String getACTUALDATE() {
        return ACTUALDATE;
    }

    public void setACTUALDATE(String ACTUALDATE) {
        this.ACTUALDATE = ACTUALDATE;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getUSETYPE() {

        return USETYPE;
    }

    public void setUSETYPE(String USETYPE) {
        this.USETYPE = USETYPE;
    }
}
