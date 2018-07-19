package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 * 物资台帐
 */

public class ITEM implements Serializable {
    public String ITEMNUM;//物资编码
    public String DESCRIPTION;//物资名称
    public String IN19;//规格型号
    public String IN26;//物资大类
    public String ITEMFIRSTDES;//大类描述
    public String IN27;//物资小类
    public String ITEMSECONDDES;//小类描述
    public String ORDERUNIT;//订购单位
    public String UNITDESC;//订购单位
    public String UDMODULE;//规格型号

    public String getUDMODULE() {
        return UDMODULE;
    }

    public void setUDMODULE(String UDMODULE) {
        this.UDMODULE = UDMODULE;
    }

    public String getITEMFIRSTDES() {
        return ITEMFIRSTDES;
    }

    public void setITEMFIRSTDES(String ITEMFIRSTDES) {
        this.ITEMFIRSTDES = ITEMFIRSTDES;
    }

    public String getITEMSECONDDES() {
        return ITEMSECONDDES;
    }

    public void setITEMSECONDDES(String ITEMSECONDDES) {
        this.ITEMSECONDDES = ITEMSECONDDES;
    }

    public String getUNITDESC() {
        return UNITDESC;
    }

    public void setUNITDESC(String UNITDESC) {
        this.UNITDESC = UNITDESC;
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

    public String getIN19() {
        return IN19;
    }

    public void setIN19(String IN19) {
        this.IN19 = IN19;
    }

    public String getIN26() {
        return IN26;
    }

    public void setIN26(String IN26) {
        this.IN26 = IN26;
    }

    public String getIN27() {
        return IN27;
    }

    public void setIN27(String IN27) {
        this.IN27 = IN27;
    }

    public String getORDERUNIT() {
        return ORDERUNIT;
    }

    public void setORDERUNIT(String ORDERUNIT) {
        this.ORDERUNIT = ORDERUNIT;
    }
}
