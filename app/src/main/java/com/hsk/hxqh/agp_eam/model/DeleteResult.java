package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by wxs on 2018/3/9.
 */

public class DeleteResult<T> implements Serializable {

    /**
     * errcode : GLOBAL-S-0
     * errmsg : 请求成功
     * result : 成功删除1条记录
     * userid : yangw
     */

    private String errcode;
    private String errmsg;
    private T result;
    private String userid;

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
