package com.hsk.hxqh.agp_eam.model;


import java.util.ArrayList;

/**
 * Created by apple on 15/9/10.
 * 解析结果
 */
public class Result2<T> {

    /**
     * 当前页*
     */
    private int curpage;
    /**
     * 总共条数*
     */
    private int totalresult;
    /**
     * 返回结果*
     */
    private ArrayList<T> resultlist;
    /**
     * 总共页数*
     */
    private int totalpage;
    /**
     * 显示条数*
     */
    private int showcount;


    public int getCurpage() {
        return curpage;
    }

    public void setCurpage(int curpage) {
        this.curpage = curpage;
    }

    public int getTotalresult() {
        return totalresult;
    }

    public void setTotalresult(int totalresult) {
        this.totalresult = totalresult;
    }

    public ArrayList<T> getResultlist() {
        return resultlist;
    }

    public void setResultlist(ArrayList<T> resultlist) {
        this.resultlist = resultlist;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getShowcount() {
        return showcount;
    }

    public void setShowcount(int showcount) {
        this.showcount = showcount;
    }
}
