package com.hsk.hxqh.agp_eam.model;

/**
 * Created by think on 2016/7/1.
 * 附件表
 */
public class Doclinks {
    public String DOCINFOID;//
    public String DOCTYPE;//文件类型
    public String OWNERID;//编号Id
    public String OWNERTABLE;//表名
    public String URL;//地址


    public String getOWNERTABLE() {
        return OWNERTABLE;
    }

    public void setOWNERTABLE(String OWNERTABLE) {
        this.OWNERTABLE = OWNERTABLE;
    }

    public String getDOCINFOID() {
        return DOCINFOID;
    }

    public void setDOCINFOID(String DOCINFOID) {
        this.DOCINFOID = DOCINFOID;
    }

    public String getDOCTYPE() {
        return DOCTYPE;
    }

    public void setDOCTYPE(String DOCTYPE) {
        this.DOCTYPE = DOCTYPE;
    }

    public String getOWNERID() {
        return OWNERID;
    }

    public void setOWNERID(String OWNERID) {
        this.OWNERID = OWNERID;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
