package com.hsk.hxqh.agp_eam.model;


import java.util.ArrayList;

/**
 * Created by apple on 15/9/10.
 * 解析结果
 */
public class Result4<T> {

    String errcode;
    String errmsg;
    ArrayList<T> result;
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

    public ArrayList<T> getResult() {
        return result;
    }

    public void setResult(ArrayList<T> result) {
        this.result = result;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
