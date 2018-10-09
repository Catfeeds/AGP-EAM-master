package com.hsk.hxqh.agp_eam.api;


import android.content.Context;
import android.util.Log;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.application.BaseApplication;
import com.hsk.hxqh.agp_eam.bean.LoginResults;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.config.Constants;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;


/**
 * Created by apple on 15/5/27.
 */
public class HttpManager {

    private static BaseApplication mApp = BaseApplication.getInstance();
    private static AsyncHttpClient sClient = null;
    private static final String TAG = "HttpManager";
    private static String appid = null;
    private static String objectname = null;

    /**
     * 设置待办事项接口*
     */
    public static String getwfassignmentUrl(String persionid, String vlaue,String type, int curpage, int showcount) {
        if (vlaue.equals("")) {
            String str =  "{'appid':'" + Constants.WFASSIGNMENT_APPID + "','objectname':'" + Constants.WFASSIGNMENT_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WFASSIGNMENTID DESC','condition':{'ASSIGNCODE':'" + persionid + "','ASSIGNSTATUS':'=ACTIVE'}}";
            Log.e("待办事件", "getwfassignmentUrl: "+str );
            return str;
        } else {
            return "{'appid':'" + Constants.WFASSIGNMENT_APPID + "','objectname':'" + Constants.WFASSIGNMENT_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WFASSIGNMENTID DESC','condition':{'ASSIGNCODE':'" + persionid + "','ASSIGNSTATUS':'=INACTIVE','"+type+"':'%"+vlaue+"%'}}";
        }
    }

    /**
     * 设置选择工单接口*
     */
    public static String getChooseWorkOrderUrl(String search, int curpage, int showcount) {
        if (search.equals("")) {
            return "{'appid':'" + Constants.WOTRACK_APPID + "','objectname':'" + Constants.WORKORDER_APPID + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'WORKTYPE':'!=OSPR'}}";
        } else {
            return "{'appid':'" + Constants.WOTRACK_APPID + "','objectname':'" + Constants.WORKORDER_APPID + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'WONUM':'%" + search + "%','WORKTYPE':'!=OSPR'}}";
        }
    }

    /**
     * 设置资产查询*
     */
    public static String getAssetUrl(String search, int curpage, int showcount) {
        if (search.equals("")) {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ASSETNUM ASC'}";
        } else {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ASSETNUM ASC','sinorsearch':{'ASSETNUM':' "+ search + "','DESCRIPTION':'" + search + "','LOCDESC':'" + search + "'}}";
        }
    }
    /*
    * PO查询
    * */
    public static String getPOUrl(String search,String type,int curpage, int showcount){
        if (search.equals("")) {
            return "{'appid':'" + Constants.PO_APPID + "','objectname':'" + Constants.PO_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'PONUM desc','condition':{'PONUM':'PO%'}}";
        }else {
            return "{'appid':'" + Constants.PO_APPID + "','objectname':'" + Constants.PO_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'PONUM desc','condition':{'PONUM':'PO%','"+type+"':'"+search+"'}}";
        }
    }
    public static String getMATRECTRANSUrl(String type,String siteid,int curpage, int showcount){
        return "{'appid':'" + Constants.MATRECTRANS_APPID + "','objectname':'" + Constants.MATRECTRANS_NAME + "'," +
                "'curpage':" + curpage + ",'showcount':" + showcount +",'option':'read','orderby':'POLINENUM asc','condition':{'PONUM':'"+type+"','SITEID':'"+siteid+"'},'sinorserch':{'PONUM':'"+type+"','DESCRIPTION':'"+type+"'}}";
    }
     /**
     * 设置部件查询*
     */
    public static String getBujianUrl(String assetnum,int curpage, int showcount) {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PARENT':'" + assetnum + "'}}";
    }

    /**
     * 设置备件查询*
     */
    public static String getBeijianUrl(String assetnum,int curpage, int showcount) {
        return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.SPAREPART_NAME + "'," +
                "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ASSETNUM':'" + assetnum + "'}}";
    }

    /**
     * 设置工单历史查询*
     */
    public static String getWorkorderUrl(String assetnum,int curpage, int showcount) {
        return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "'," +
                "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ASSETNNUM':'" + assetnum + "'}}";
    }

    /**
     * 设置工单查询*
     */
    public static String getWorkOrderUrl(String search,String type, int curpage, int showcount) {
        if (search.equals("")) {
            return "{'appid':'" + Constants.UDWOTRACK_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM desc','condition':{'WONUM':'" + type + "%'}}";
        } else {
            return "{'appid':'" + Constants.UDWOTRACK_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM desc','condition':{'WONUM':'" + type + "%'},'sinorsearch':{'WONUM':' "+ search + "','DESCRIPTION':'" + search + "'}}";
        }
    }    public static String getWorkOrderUrl(String search, int curpage, int showcount) {
        if (search.equals("")) {
            return "{'appid':'" + Constants.UDWOTRACK_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM desc'}";
        } else {
            return "{'appid':'" + Constants.UDWOTRACK_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM desc','condition':{'WONUM':'" + search + "%'}}";
        }
    }
    public static String getWorkOrderUrl(String search,String type, int showcount) {
        if (search.equals("")) {
            return "{'appid':'" + Constants.WORKORDER_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "'," +
                    "'curpage':" + 1 + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM desc','condition':{'WONUM':'" + type + "%'}}";
        } else {
            return "{'appid':'" + Constants.WORKORDER_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "'," +
                    "'curpage':" + 1 + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM desc','condition':{'WONUM':'" + type + "%'},'sinorsearch':{'WONUM':' "+ search + "','DESCRIPTION':'" + search + "'}}";
        }
    }
    public static String getWorkOrderUrl(String search,String type,String searchType, int showcount) {
        if (search.equals("")) {
            return "{'appid':'" + Constants.WORKORDER_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "'," +
                    "'curpage':" + 1 + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM desc','condition':{'WONUM':'" + type + "'}}";
        } else {
            return "{'appid':'" + Constants.WORKORDER_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "'," +
                    "'curpage':" + 1 + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM desc','condition':{'WONUM':'" + type + "','"+searchType+"':'"+search+"'}}";
        }
    }



    /**
     * 设置工单任务查询*
     */
    public static String getWoactivityUrl(String wonum,String istask,int curpage, int showcount) {
        return "{'appid':'" + Constants.WOACTIVITY_APPID + "','objectname':'" + Constants.WOACTIVITY_NAME + "'," +
                "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'WONUM':'" + wonum + "','istask':'" + istask + "'}}";
//        return "{'appid':'" + Constants.WOACTIVITY_APPID + "','objectname':'" + Constants.WOACTIVITY_NAME + "'," +
//                "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
    }

    /**
     * 设置工单计划员工查询*
     */
    public static String getWplaborLUrl(String wonum,String istask,int curpage, int showcount) {
        return "{'appid':'" + Constants.UDWOTRACK_APPID + "','objectname':'" + Constants.WPLABOR_NAME + "'," +
                "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'WONUM':'" + wonum + "','istask':'" + istask + "'}}";
//        return "{'appid':'" + Constants.UDWOTRACK_APPID + "','objectname':'" + Constants.WPLABOR_NAME + "'," +
//                "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
    }

    /**
     * 设置工单计划物料查询*
     */
    public static String getWpmateriaLUrl(String wonum,String istask,int curpage, int showcount) {
        return "{'appid':'" + Constants.WPMATERIAL_NAME + "','objectname':'" + Constants.WPMATERIAL_NAME + "'," +
                "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'WONUM':'" + wonum + "','istask':'" + istask + "'}}";
//        return "{'appid':'" + Constants.WPMATERIAL_NAME + "','objectname':'" + Constants.WPMATERIAL_NAME + "'," +
//                "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
    }
    public static String getWpmateriaLUrl(String wonum,int curpage, int showcount) {
        return "{'appid':'" + Constants.WPMATERIAL_NAME + "','objectname':'" + Constants.WPMATERIAL_NAME + "'," +
                "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'WONUM':'" + wonum + "'}}";
//        return "{'appid':'" + Constants.WPMATERIAL_NAME + "','objectname':'" + Constants.WPMATERIAL_NAME + "'," +
//                "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
    }

    /**
     * 设置工单实际员工查询*
     */
    public static String getLabtransUrl(String wonum,String istask,int curpage, int showcount) {
//        return "{'appid':'" + Constants.UDWOTRACK_APPID + "','objectname':'" + Constants.LABTRANS_NAME + "'," +
//                "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PARENT':'" + wonum + "','istask':'" + istask + "'}}";
        return "{'appid':'" + Constants.UDWOTRACK_APPID + "','objectname':'" + Constants.LABTRANS_NAME + "'," +
                "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
    }

    /**
     * 设置工单实际物料查询*
     */
    public static String getMatusetransUrl(String wonum,String istask,int curpage, int showcount) {
//        return "{'appid':'" + Constants.MATUSETRANS_NAME + "','objectname':'" + Constants.MATUSETRANS_NAME + "'," +
//                "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PARENT':'" + wonum + "','istask':'" + istask + "'}}";
        return "{'appid':'" + Constants.MATUSETRANS_NAME + "','objectname':'" + Constants.MATUSETRANS_NAME + "'," +
                "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
    }

    public static String getMatusetransUrl(String loct){
        return "{'appid':'" +"MATUSETRANS_VIEW" + "','objectname':'" + "MATUSETRANS_VIEW" + "','option':'read'}";
    }

    /**
     * 设置库存查询*
     */
    public static String getInventoryUrl(String search, int curpage, int showcount) {
        if (search.equals("")) {
            return "{'appid':'" + Constants.INVENTORY_APPID + "','objectname':'" + Constants.INVENTORY_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','ITEMSITEID':'ITEMSET','orderby':'ITEMNUM asc'}";
        } else {
            return "{'appid':'" + Constants.INVENTORY_APPID + "','objectname':'" + Constants.INVENTORY_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','ITEMSITEID':'ITEMSET','orderby':'ITEMNUM desc','condition':{'ITEMNUM';'"+search+"'}";

        }
    }
    public static String getInventoryUrl() {

            return "{'appid':'" + Constants.INVENTORY_APPID + "','objectname':'" + Constants.INVENTORY_NAME + "','option':'read','ITEMSITEID':'ITEMSET','orderby':'ITEMNUM asc'}";

    }
    public static String getInventoryUrl(String search,String type, int curpage, int showcount) {
        if (search.equals("")) {
            return "{'appid':'" + Constants.INVENTORY_APPID + "','objectname':'" + Constants.INVENTORY_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','ITEMSITEID':'ITEMSET','orderby':'ITEMNUM asc'}";
        } else {
            return "{'appid':'" + Constants.INVENTORY_APPID + "','objectname':'" + Constants.INVENTORY_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','ITEMSITEID':'ITEMSET','orderby':'ITEMNUM desc','condition':{'"+type+"':'"+search+"'}}";
        }
    }
    public static String getInventoryUrl(String storeroom,String search, int curpage, int showcount,int s) {
        if (search.equals("")){
            return "{'appid':'" + Constants.INVENTORY_APPID + "','objectname':'" + Constants.INVENTORY_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','ITEMSITEID':'ITEMSET','orderby':'ITEMNUM desc','condition':{'LOCATION':'="+ storeroom + "'}}";
        }else {
            return "{'appid':'" + Constants.INVENTORY_APPID + "','objectname':'" + Constants.INVENTORY_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','ITEMSITEID':'ITEMSET','orderby':'ITEMNUM desc','condition':{'LOCATION':'="+ storeroom + "','ITEMNUM':'"+search+"'}}";

        }
    }

    /**
     * 设置库存盘点查询*
     */
    public static String getUdstockUrl(String search,String serchtype, int curpage, int showcount) {
        if (search.equals("")) {
            return "{'appid':'" + Constants.UDSTOCK_APPID + "','objectname':'" + Constants.UDSTOCK_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'STOCKNUM desc'}";
        } else {
            return "{'appid':'" + Constants.UDSTOCK_APPID + "','objectname':'" + Constants.UDSTOCK_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'STOCKNUM desc','condition':{'"+serchtype+"':' "+ search + "'}}";
        }
    }

    /**
     * 设置库存盘点子表查询*
     */
    public static String getUdstocklineUrl(String stocknum,int curpage, int showcount) {
        return "{'appid':'" + Constants.UDSTOCKLINE_APPID + "','objectname':'" + Constants.UDSTOCKLINE_NAME + "','option':'read','orderby':'SN asc','condition':{'STOCKNUM':'" + stocknum + "'}}";
    }
    public static String getUdstocklineUrl(String stocknum) {
        return "{'appid':'" + Constants.UDSTOCKLINE_APPID + "','objectname':'" + Constants.UDSTOCKLINE_NAME + "','option':'read','orderby':'SN asc','condition':{'STOCKNUM':'" + stocknum + "','ISCHECK':'1'}}";
    }
    public static String getUdstocklineUrl(String stocknum,String itemnum,int curpage,int showcount) {
        return "{'appid':'" + Constants.UDSTOCKLINE_APPID + "','objectname':'" + Constants.UDSTOCKLINE_NAME +"','curpage':" + curpage + ",'showcount':" + showcount +  ",'option':'read','orderby':'SN asc','condition':{'STOCKNUM':'" + stocknum + "','ITEMNUM':'"+itemnum+"'}}";
    }
    public static String getUdstocklineUrl(String stocknum,String binnum) {
        return "{'appid':'" + Constants.UDSTOCKLINE_APPID + "','objectname':'" + Constants.UDSTOCKLINE_NAME +"','option':'read','orderby':'SN asc','condition':{'STOCKNUM':'" + stocknum + "','BINNUM':'"+binnum+"'}}";
    }

    /**
     * 设置物资台帐查询*
     */
    public static String getItemUrl(String search,String type, int curpage, int showcount) {
        if (search.equals("")) {
            return "{'appid':'" + Constants.ITEM2_APPID + "','objectname':'" + Constants.ITEM2_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ITEMNUM asc'}";
        } else {
            if (type == null || type.equals("")) {
                return "{'appid':'" + Constants.ITEM2_APPID + "','objectname':'" + Constants.ITEM2_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ITEMNUM asc','condition':{'ITEMNUM':'=" + search + "'}}";
            }else if (type.equals("IN26")){
                return "{'appid':'" + Constants.ITEM2_APPID + "','objectname':'" + Constants.ITEM2_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ITEMNUM asc','condition':{'IN26':'=" + search + "'}}";
            }else if (type.equals("IN27")){
                return "{'appid':'" + Constants.ITEM2_APPID + "','objectname':'" + Constants.ITEM2_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ITEMNUM asc','condition':{'IN27':'=" + search + "'}}";
            }else if (type.equals("DESC")){
                return "{'appid':'" + Constants.ITEM2_APPID + "','objectname':'" + Constants.ITEM2_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ITEMNUM asc','condition':{'DESCRIPTION':'" + search + "'}}";
            }else {
                return "{'appid':'" + Constants.ITEM2_APPID + "','objectname':'" + Constants.ITEM2_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ITEMNUM':'" + search + "'}}";
            }
        }

    }
    /**
     * 设置物资台帐查询*
     */
    public static String getItemUrl(String search, int curpage, int showcount) {
            return "{'appid':'" + Constants.ITEM2_APPID + "','objectname':'" + Constants.ITEM2_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ITEMNUM desc','sinorsearch':{'ITEMNUM':' "+ search + "','DESCRIPTION':'" + search + "'}}";

    }

    /**
     * 设置备件查询*
     */
    public static String getItemInventoryUrl(String itemnum,int curpage, int showcount) {
        return "{'appid':'" + Constants.INVENTORY_APPID + "','objectname':'" + Constants.INVENTORY_NAME + "'," +
                "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ITEMNUM':'=" + itemnum + "'}}";
    }

    /**
     * 设置库存余量查询*
     */
    public static String getInvbalancesUrl(String itemnum,String location,String siteid,String itemsetid,int curpage, int showcount) {
        return "{'appid':'" + Constants.INVENTORY_APPID + "','objectname':'" + Constants.INVBALANCES_NAME + "'," +
                "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'itemnum':'" + itemnum + "','location':'" + location + "','itemsetid':'"+ itemsetid +"'}}";
    }
    public static String getInvbalancesUrl(String search,String location,String binnum) {
        if (search.equals("")){
            return "{'appid':'" + Constants.INVENTORY_APPID + "','objectname':'" + Constants.INVBALANCES_NAME + "','option':'read','condition':{'LOCATION':'"+location+"','BINNUM':'"+binnum+"'}}";
        }else {
            return "{'appid':'" + Constants.INVENTORY_APPID + "','objectname':'" + Constants.INVBALANCES_NAME + "','option':'read','condition':{'LOCATION':'"+location+"','BINNUM':'"+binnum+"','ITEMNUM':'"+search+"'}}";
        }
    }

    /**
     * 设置故障提报单的接口
     */
    public static String getudfaultreporturl(String value, int curpage, int showcount) {
        if (value.equals("")) {
            return "{'appid':'" + Constants.UDFAULTREP_APPID + "','objectname':'" + Constants.UDFAULTREPORT_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'UDFAULTREPORTNUM DESC'}";
        }
        return "{'appid':'" + Constants.UDFAULTREP_APPID + "','objectname':'" + Constants.UDFAULTREPORT_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'" + ",'orderby':'UDFAULTREPORTNUM DESC','sinorsearch':{'UDFAULTREPORTNUM':'" + value + "','DESCRIPTION':'" + value + "'}}";
    }
    /**
     * 设置工作申请的接口
     */
    public static String getudworkapplyurl(String value, int curpage, int showcount) {
        if (value.equals("")) {
            return "{'appid':'" + Constants.UDFEDBKCON_APPID + "','objectname':'" + Constants.UDWORKAPPLY_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WOAPPLYNUM ASC'}";
        }
        return "{'appid':'" + Constants.UDFEDBKCON_APPID + "','objectname':'" + Constants.UDWORKAPPLY_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'" + ",'orderby':'WOAPPLYNUM ASC','sinorsearch':{'WOAPPLYNUM':'" + value + "','DESCRIPTION':'" + value + "'}}";
    }

    /**
     * 根据id查找故障提报单的接口
     */
    public static String getudreporturlByid(String udreportid) {
        return "{'appid':'" + Constants.UDFAULTREP_APPID + "','objectname':'" + Constants.UDFAULTREPORT_NAME + "','curpage':1,'showcount':20,'option':'read','condition':{'UDREPORTID':'" + udreportid + "'}}";
    }

    /**
     * 设置位置查询*
     */
    public static String getLocationUrl(String search,String type, int curpage, int showcount,int code) {
        if (search.equals("") && type.equals("")) {
            return "{'appid':'" + Constants.LOCATION_APPID + "','objectname':'" + Constants.LOCATION_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'LOCATION desc'}";
        } else if(code != 1 && type.equals("")) {
            return "{'appid':'" + Constants.LOCATION_APPID + "','objectname':'" + Constants.LOCATION_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'LOCATION desc','sinorsearch':{'LOCATION':' "+ search + "','DESCRIPTION':'" + search + "','INVOWNER':'"+search+"'}}";
        } else if (!type.equals("")) {
            return "{'appid':'" + Constants.LOCATION_APPID + "','objectname':'" + Constants.LOCATION_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'LOCATION asc','condition':{'TYPE':'" + type + "'},'sinorsearch':{'LOCATION':' "+ search + "','DESCRIPTION':'" + search + "','INVOWNER':'"+search+"'}}";
        } else {
            return "{'appid':'" + Constants.LOCATION_APPID + "','objectname':'" + Constants.LOCATION_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'LOCATION desc','condition':{'INVOWNER':'" + search + "'}}";
        }
    }

    /**
     * 设置人员查询*
     */
    public static String getPersomUrl(String search, int curpage, int showcount) {
        if (search.equals("")) {
            return "{'appid':'" + Constants.PERSON_APPID + "','objectname':'" + Constants.PERSON_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'LOCATION ASC'}";
        } else {
            return "{'appid':'" + Constants.PERSON_APPID + "','objectname':'" + Constants.PERSON_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'LOCATION ASC','sinorsearch':{'LOCATION':' "+ search + "','DESCRIPTION':'" + search + "'}}";
        }
    }

    /**
     * 设置年度计划查询*
     */
    public static String getUdyearplanUrl(String search, int curpage, int showcount) {
        if (search.equals("")) {
            return "{'appid':'" + Constants.UDYEARPLAN_APPID + "','objectname':'" + Constants.UDYEARPLAN_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'PLANNUM ASC'}";
        } else {
            return "{'appid':'" + Constants.UDYEARPLAN_APPID + "','objectname':'" + Constants.UDYEARPLAN_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'PLANNUM ASC','sinorsearch':{'PLANNUM':' "+ search + "','DESCRIPTION':'" + search + "'}}";
        }
    }

    /**
     * 查询附件的接口
     */
    public static String getDoclinks(String ownertable, String ownerid){
        return "{'appid':'" + Constants.DOCLINKS_APPID + "','objectname':'" + Constants.DOCLINKS_NAME  + "','option':'read','condition':{'OWNERTABLE':'=" + "INVUSE"  + "','OWNERID':'=" + "1446"+"'}}";
    }

    /**
     * 设置物料退库查询*
     */
    public static String getINVUSEUrl(String search,String serchType, int curpage, int showcount,String type) {
        if (search.equals("")) {
            return "{'appid':'" + Constants.INVUSE_APPID + "','objectname':'" + Constants.INVUSE_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'INVUSENUM desc','condition':{'INVUSENUM':'"+type +"'}}";
        }else {
            return  "{'appid':'" + Constants.INVUSE_APPID + "','objectname':'" + Constants.INVUSE_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'INVUSENUM desc','condition':{'INVUSENUM':'"+type +"',\""+serchType+"\":\""+search+"\"}}";

        }
    }
    public static String getINVUSEUrl() {
            return "{'appid':'" + Constants.INVUSE_APPID + "','objectname':'" + Constants.INVUSE_NAME + ",'option':'read','orderby':'INVUSENUM desc'}";
    }

    public static String getINVUSELINEUrl(String type){
        return "{'appid':'" + Constants.INVUSELINE_APPID + "','objectname':'" + Constants.INVUSELINE_NAME + "','option':'read','orderby':'INVUSELINENUM asc','condition':{'INVUSENUM':'" +type +"'}}";
    }
    public static String getINVRESERVEUrl(String serch,String type){
        return "{'appid':'" + Constants.INVRESERVE_APPID + "','objectname':'" + Constants.INVRESERVE_NAME + "'," +
                "'curpage':1,'showcount':" + 20 + ",'option':'read','condition':{'LOCATION':'="+type +"'}}";
    }
    public static String getUDGETREFUNDLINEUrl(String type){
        return "{'appid':'" + Constants.UDGETREFUNDLINE_APPID + "','objectname':'" + Constants.UDGETREFUNDLINE_NAME + "','option':'read','orderby':'ITEMNUM asc'}";
    }
    public static String getRECEIPTINPUTUrl(String type){
        return "{'appid':'" + "RECEIPTINPUT" + "','objectname':'" + "RECEIPTINPUT" + "','option':'read'}";
    }
   public static String getPOlineURl(String type,String udstation){
        return "{'appid':'" + Constants.POLINE_APPID+ "','objectname':'" + Constants.POLINE_NAME + "','option':'read','condition':{'PONUM':'="+type+"','STORELOC':'="+udstation+"'}}";
    }
    public static String getDeptUrl(String search, int curpage, int showcount) {
        if (search.equals("")) {
            return "{'appid':'" + Constants.UDDEPT_APPID + "','objectname':'" + Constants.UDDEPT_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'DEPTNUM ASC'}";
        } else {
            return "{'appid':'" + Constants.PERSON_APPID + "','objectname':'" + Constants.PERSON_NAME + "'," +
                    "'curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'DEPTNUM ASC','sinorsearch':{'LOCATION':' "+ search + "','DESCRIPTION':'" + search + "'}}";
        }
    }





    /**
     * 使用用户名密码登录
     *
     * @param cxt
     * @param username 用户名
     * @param password 密码
     * @param imei     密码
     * @param handler  返回结果处理
     */
    public static void loginWithUsername(final Context cxt, final String username, final String password, String imei,
                                         final HttpRequestHandler<String> handler) {

        //String ip_adress = Constants.HTTP_API_IP + Constants.SIGN_IN_URL;
        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.SIGN_IN_URL;

        Log.i(TAG,"ip_adress="+ip_adress);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("loginid", username);
        params.put("password", password);
        params.put("imei", imei);
        client.setTimeout(20000);
        client.post(ip_adress, params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                SafeHandler.onFailure(handler, IMErrorType.errorMessage(cxt, IMErrorType.ErrorLoginFailure));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "SstatusCode=" + statusCode + "responseString=" + responseString);
                if (statusCode == 200) {
                    LoginResults loginResults = JsonUtils.parsingAuthStr(cxt, responseString);
                    if (loginResults != null) {
                        if (loginResults.getErrcode().equals(Constants.LOGINSUCCESS) || loginResults.getErrcode().equals(Constants.CHANGEIMEI)) {
                            SafeHandler.onSuccess(handler, loginResults.getResult());
                        } else if (loginResults.getErrcode().equals(Constants.USERNAMEERROR)) {
                            SafeHandler.onFailure(handler, loginResults.getErrmsg());
                        }
                    }

                }
            }
        });
    }


    /**
     * 不分页获取信息方法*
     */
    public static void getData(final Context cxt, String data, final HttpRequestHandler<Results> handler) {
        //String url = Constants.HTTP_API_IP + Constants.BASE_URL;
        String url = AccountUtils.getIpAddress(cxt) + Constants.BASE_URL;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("data", data);
        client.setTimeout(20000);
        client.get(url, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Results result = JsonUtils.parsingResults1(cxt, responseString);
                if (result ==null){
                    SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
                }else {
                    assert result != null;
                    int showcount = JsonUtils.getListSize(result.getResultlist());
                    SafeHandler.onSuccess(handler, result,1,showcount);
                }


            }
        });
    }


    /**
     * 解析返回的结果--分页*
     */
    public static void getDataPagingInfo(final Context cxt, String data, final HttpRequestHandler<Results> handler) {
        Log.i(TAG, "data=" + data);
        //String url = Constants.HTTP_API_IP + Constants.BASE_URL;
        String url = AccountUtils.getIpAddress(cxt) + Constants.BASE_URL;
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
        params.put("data", data);
        client.setTimeout(20000);
        client.get(url, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    Results result = JsonUtils.parsingResults(cxt, responseString);
                    if (null == result) {
                        SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
                    }else{
                        assert result != null;
                        Log.e(TAG, "onSuccess:"+result.getResultlist());
                        SafeHandler.onSuccess(handler, result, result.getCurpage(), result.getShowcount());

                    }
                }
        });
    }
    /**
     * 解析返回的结果--分页*
     */
    public static void getDataPagingInfo2(final Context cxt, String data, final HttpRequestHandler<Results> handler) {
        Log.i(TAG, "data=" + data);
        String url = AccountUtils.getIpAddress(cxt) + Constants.BASE_URL;
        //SyncHttpClient client = new SyncHttpClient();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("data", data);
        client.setTimeout(20000);
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Results result = JsonUtils.parsingResults(cxt, responseString);
                if (null == result) {
                    SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
                }else{
                    assert result != null;
                    Log.e(TAG, "onSuccess:"+result.getResultlist());
                    SafeHandler.onSuccess(handler, result, result.getCurpage(), result.getShowcount());

                }
            }
        });
    }


}
