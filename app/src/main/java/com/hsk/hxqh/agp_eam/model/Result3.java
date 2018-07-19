package com.hsk.hxqh.agp_eam.model;

/**
 * Created by wxs on 2017/11/29.
 */
public class Result3<T> {
    String errcode;
    String errmsg;
    Result2<T> result;
    String userid;


    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Result2<T> getResult() {
        return result;
    }

    public void setResult(Result2<T> result) {
        this.result = result;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
