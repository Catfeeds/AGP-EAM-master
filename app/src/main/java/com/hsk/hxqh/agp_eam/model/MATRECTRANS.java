package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zzw on 2018/6/14.
 */

public class MATRECTRANS implements Serializable{
/*    private String ACTUALCOST;
    private String ACTUALDATE;
    private String ASSETNUM;
    private String CONVERSION;
    private String DESCRIPTION;
    private String ENTERBY;
    private String ISSUETO;
    private String ISSUEUNIT;
    private String ITEMNUM;
    private String LOCATION;
    private String POLINENUM;
    private String QTYOVERRECEIVED;
    private String RECEIPTQUANTITY;
    private String RECEIVEDUNIT;
    private String REJECTQTY;
    private String TOBIN;
    private String TOLOT;
    private String TOSTORELOC;
    private String UNITCOST;
    private String WONUM;*/
private String FLAG ;

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    private int SN;
        private String POLINENUM;
        private String ORDERQTY;
        private String ORDERQTYBackup;
        private String ITEMNUM;
        private String DESCRIPTION;
        private String ORDERUNIT;
        private String UNITCOST;
        private String RECEIVEDQTY;
      private String INSPECTIONREQUIRED;
      private String REMARK;
      private String TOSTORELOC;
      private String TOBIN;
      private String TOLOT;
      private String QUANTITY;
      private String RECEIVEDUNIT;
      private String PONUM;
      private String QTYREQUESTED;
      private String ENTERBY;
      private String ISSUETYPE;
      private String RECEIPTQUANTITY;

    public String getORDERQTYBackup() {
        return ORDERQTYBackup;
    }

    public void setORDERQTYBackup(String ORDERQTYBackup) {
        this.ORDERQTYBackup = ORDERQTYBackup;
    }

    public String getRECEIPTQUANTITY() {
        return RECEIPTQUANTITY;
    }

    public void setRECEIPTQUANTITY(String RECEIPTQUANTITY) {
        this.RECEIPTQUANTITY = RECEIPTQUANTITY;
    }

    public String getISSUETYPE() {
        return ISSUETYPE;
    }

    public void setISSUETYPE(String ISSUETYPE) {
        this.ISSUETYPE = ISSUETYPE;
    }

    public String getENTERBY() {
        return ENTERBY;
    }

    public void setENTERBY(String ENTERBY) {
        this.ENTERBY = ENTERBY;
    }

    public String getQTYREQUESTED() {
        return QTYREQUESTED;
    }

    public void setQTYREQUESTED(String QTYREQUESTED) {
        this.QTYREQUESTED = QTYREQUESTED;
    }

    public String getINSPECTIONREQUIRED() {
        return INSPECTIONREQUIRED;
    }

    public void setINSPECTIONREQUIRED(String INSPECTIONREQUIRED) {
        this.INSPECTIONREQUIRED = INSPECTIONREQUIRED;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getTOSTORELOC() {
        return TOSTORELOC;
    }

    public void setTOSTORELOC(String TOSTORELOC) {
        this.TOSTORELOC = TOSTORELOC;
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

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getRECEIVEDUNIT() {
        return RECEIVEDUNIT;
    }

    public void setRECEIVEDUNIT(String RECEIVEDUNIT) {
        this.RECEIVEDUNIT = RECEIVEDUNIT;
    }

    public String getPONUM() {
        return PONUM;
    }

    public void setPONUM(String PONUM) {
        this.PONUM = PONUM;
    }

    public void setSN(int SN) {
            this.SN = SN;
        }
        public int getSN() {
            return SN;
        }

        public void setPOLINENUM(String POLINENUM) {
            this.POLINENUM = POLINENUM;
        }
        public String getPOLINENUM() {
            return POLINENUM;
        }

        public void setORDERQTY(String ORDERQTY) {
            this.ORDERQTY = ORDERQTY;
            this.ORDERQTYBackup = ORDERQTY;
        }
        public String getORDERQTY() {
            return ORDERQTY;
        }

        public void setITEMNUM(String ITEMNUM) {
            this.ITEMNUM = ITEMNUM;
        }
        public String getITEMNUM() {
            return ITEMNUM;
        }

        public void setDESCRIPTION(String DESCRIPTION) {
            this.DESCRIPTION = DESCRIPTION;
        }
        public String getDESCRIPTION() {
            return DESCRIPTION;
        }

        public void setORDERUNIT(String ORDERUNIT) {
            this.ORDERUNIT = ORDERUNIT;
            this.RECEIVEDUNIT = ORDERUNIT;
        }
        public String getORDERUNIT() {
            return ORDERUNIT;
        }

        public void setUNITCOST(String UNITCOST) {
            this.UNITCOST = UNITCOST.replace(",","");
        }
        public String getUNITCOST() {
            return UNITCOST;
        }

        public void setRECEIVEDQTY(String RECEIVEDQTY) {
            this.RECEIVEDQTY = RECEIVEDQTY;
        }
        public String getRECEIVEDQTY() {
            return RECEIVEDQTY;
        }
    }
