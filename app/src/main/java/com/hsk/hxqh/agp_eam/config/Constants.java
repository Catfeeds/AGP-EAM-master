package com.hsk.hxqh.agp_eam.config;

import android.content.SharedPreferences;

/**
 * Created by think on 2015/10/19.
 */
public class Constants {

    /**
     * 基础接口*
     */
    /**
     * 默认*
     */
//    public static final String HTTP_API_IP = "http://182.92.8.94:7011";
//    public static final String HTTP_API_IP = "http://10.3.252.11";
    public static final String HTTP_API_IP = "http://180.76.184.121:7005";
    //public static final String HTTP_API_IP = "http://eam03.agp.com";
    //public static final String HTTP_API_IP = "http://192.168.199.238:7001";
   //public static final String HTTP_API_IP = "http://192.168.1.145:7001";
    //public static final String HTTP_API_IP = "http://10.3.252.1";

    /**
     * 登陆URL*
     */

    public static final String SIGN_IN_URL = "/maximo/mobile/system/login";
//    public static final String SIGN_IN_URL = "eam03.agp.com/maximo";


    /**u
     * 通用接口查询*
     */
    public static final String BASE_URL = "/maximo/mobile/common/api";

    /**
     * 通用修改接口*
     */
//    public static final String WORK_URL = "http://192.168.100.17:7001/meaweb/services/MOBILESERVICE";
    //public static final String WORK_URL = "/meaweb/services/MOBILESERVICE";
    public static final String WORK_URL = "/meaweb/services/MOBILESERVICE";
    /*
    * 物资出库新建接口
    * */
    public static final String INSERT_INVUSE_URl = "/meaweb/services/";
    /**
     * 领料单接口*
     */
    public static final String INVUSE_URL = "/meaweb/services/INVUSESERVICE";
    /**
     * 工作流*
     */
    public static final String WORK_FLOW_URL = "/meaweb/services/WFSERVICE";
    /*
    * INVUESLINE
    * */
    public static final String INVUESLINE_URL = "/meaweb/services/APPSERVICE";

    /**
     * ------------------数据库表名配置－－开始*
     */
    //待办事项的appid
    public static final String WFASSIGNMENT_APPID = "WFDESIGN";

    //待办事项的表名
    public static final String WFASSIGNMENT_NAME = "WFASSIGNMENT";
    /**
     * 工单管理*
     */
    //故障工单查询的appid
    public static final String UDWOTRACK_APPID = "UDWOTRACK";
    //故障工单表名
    public static final String WORKORDER_NAME = "WORKORDER";
    //工单计划任务appid
    public static final String WOACTIVITY_APPID = "WOACTIVITY";
    //工单计划任务表名
    public static final String WOACTIVITY_NAME = "WOACTIVITY";
    //计划任务appid
    public static final String JOBTASK_APPID = "JOBTASK";
    //计划任务表名
    public static final String JOBTASK_NAME = "JOBTASK";
    //调试工单子表appid
    public static final String UDDEBUGWORKORDERLINE_APPID = "UDDEBUGWORKORDERLINE";
    //调试工单子表表名
    public static final String UDDEBUGWORKORDERLINE_NAME = "UDDEBUGWORKORDERLINE";
    //工单计划员工表名
    public static final String WPLABOR_NAME = "WPLABOR";
    //工单物料表名
    public static final String WPMATERIAL_NAME = "WPMATERIAL";
    //工单任务分配表名
    public static final String ASSIGNMENT_NAME = "ASSIGNMENT";
    //工单实际员工表名
    public static final String LABTRANS_NAME = "LABTRANS";
    //工单实际物料表名
    public static final String MATUSETRANS_NAME = "MATUSETRANS";
    public static final String MATUSETRANS_APPID = "MATUSETRANS";


    public static final String WPITEM_NAME = "WPITEM";
    public static final String WPITEM_APPID = "WPITEM";

    //故障汇报表名
    public static final String FAILUREREPORT_NAME = "FAILUREREPORT";
    //设备位置appid
    public static final String LOCATION_APPID = "LOCATIONS";
    //设备位置表名
    public static final String LOCATION_NAME = "LOCATIONS";
    //设备位置appid
    public static final String UDYEARPLAN_APPID = "UDYEARPLAN";
    //设备位置表名
    public static final String UDYEARPLAN_NAME = "UDYEARPLAN";
    //计划表appid
    public static final String UDINVESTP_APPID = "UDZYSPLAN";
    //计划表表名
    public static final String UDINVESTP_NAME = "UDINVESTP";
    //故障代码appid
    public static final String FAILURELIST_APPID = "FAILURELIST";
    //故障代码表名
    public static final String FAILURELIST_NAME = "FAILURELIST";
    //物资编码appid
    public static final String ITEM_APPID = "UDITEM";
    //物资编码表名
    public static final String ITEM_NAME = "ITEM";

    //库存appid
    public static final String INVENTORY_APPID = "INVENTORY";
    //库存表名
    public static final String INVENTORY_NAME = "INVENTORY";
    //库存余量表名
    public static final String INVBALANCES_NAME = "INVBALANCES";

    //物料退库appid
    public static final String INVUSE_APPID = "INVUSE";
    //物料退库表名
    public static final String INVUSE_NAME = "INVUSE";
    //物料退库子表appid
    public static final String INVUSELINE_APPID = "INVUSELINE";
    //物料退库子表表名
    public static final String INVUSELINE_NAME = "INVUSELINE";
    //库存盘点appid
    public static final String UDSTOCK_APPID = "UDSTOCK";
    //库存盘点表名
    public static final String UDSTOCK_NAME = "UDSTOCK";

    //库存盘点子表appid
    public static final String UDSTOCKLINE_APPID = "UDSTOCKLINE";
    //库存盘点子表表名
    public static final String UDSTOCKLINE_NAME = "UDSTOCKLINE";

    //物资台帐appid
    public static final String ITEM2_APPID = "ITEM";
    //物资台帐表名
    public static final String ITEM2_NAME = "ITEM";

    /**故障提报单appid**/
    public static final String UDFAULTREP_APPID = "UDFAULTREP";
    /**故障提报单表名**/
    public static final String UDFAULTREPORT_NAME = "UDFAULTREPORT";

    /**工作申请appid**/
    public static final String UDFEDBKCON_APPID = "UDWOAPPLY";
    /**工作申请表名**/
    public static final String UDWORKAPPLY_NAME = "UDWORKAPPLY";

    //设备appid
    public static final String ASSET_APPID = "ASSET";
    //设备表名
    public static final String ASSET_NAME = "ASSET";
    //备件表名
    public static final String SPAREPART_NAME = "SPAREPART";

    //人员appid
    public static final String PERSON_APPID = "PERSON";
    //人员表名
    public static final String PERSON_NAME = "PERSON";

    /**附件appid**/
    public static final String DOCLINKS_APPID = "DOCLINKS";
    /**附件表名**/
    public static final String DOCLINKS_NAME = "DOCLINKS";

    /**
     * 选择工单*
     */
    public static final String WOTRACK_APPID = "WOTRACK";
    /**
     * 选择工单表名*
     */
    public static final String WORKORDER_APPID = "WORKORDER";

    //POappid
    public static final String PO_APPID = "PO";
    //PO表名
    public static final String PO_NAME = "PO";
    public static final String POLINE_APPID = "POLINE";
    public static final String POLINE_NAME = "POLINE";

    //MATRECTRANS
    public static final String MATRECTRANS_NAME = "MATRECTRANS";
    public static final String MATRECTRANS_APPID= "MATRECTRANS";
    //INVRESERVE
    public static final String INVRESERVE_NAME = "INVRESERVE";
    public static final String INVRESERVE_APPID= "INVRESERVE";
    //UDGETREFUNDLINE
    public static final String UDGETREFUNDLINE_NAME = "UDGETREFUNDLINE";
    public static final String UDGETREFUNDLINE_APPID= "UDGETREFUNDLINE";
    public static final String UDDEPT_NAME = "UDDEPT";
    public static final String UDDEPT_APPID= "UDDEPT";
    public static final String USER_INFO = "userinfo";
    public static final String NAME_KEY = "name_key";
    public static final String PASS_KEY = "pass_key";
    public static final String ISREMENBER = "isRemenber";


    /**
     * 用户登录表识--开始*
     */
    public static final String LOGINSUCCESS = "USER-S-101"; //登录成功

    public static final String CHANGEIMEI = "USER-S-104"; //登录成功,检测到用户更换手机登录

    public static final String USERNAMEERROR = "USER-E-100";//用户名密码错误

    public static final String GETDATASUCCESS = "GLOBAL-S-0";//获取数据成功

    /**
     * 工单跳转类型标识
     */
    public static final String FR = "FR";//故障工单
    public static final String AA = "AA";//终验收工单
    public static final String DC = "DC";//调试工单
    public static final String SP = "SP";//排查工单
    public static final String TP = "TP";//技改工单
    public static final String WS = "WS";//定检工单

    /**
     * 工作流名称
     */
    public static final String UDDJWO = "UDDJWO";//定检工单
    public static final String UDGZWO = "UDGZWO";//故障工单
    public static final String UDJGWO = "UDJGWO";//技改工单
    public static final String UDPCWO = "UDPCWO";//排查工单
    public static final String UDZYSWO = "UDZYSWO";//终验收工单
    public static final String UDDEBUGWOR="UDDEBUGWOR";//调试工单
    //工单状态
    public static final String STATUS1 = "新建";
    public static final String STATUS2 = "进行中";





    /**设置数据库参数-开始**/
    /**
     * 数据库路径
     */
    public static final String PATH_DB = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/Android/data/";
    /**
     * 无SD卡的数据库路径
     */
    public static final String NOT_SDCARD_PATH_DB = "/data/data/";
    /**
     * 数据库名称 *
     */
    public static final String TB_NAME = "sqlite-mingyang.db";

    /**
     * 选项跳转请求值
     */
    public static final int PERSONCODE = 100; //人员

    public static final int REGULARINSCODE = 110;//定检计划编号

    public static final int WS_JOBPLANCODE = 120;//定检作业计划

    public static final int UDPROCODE = 130;//项目编号

    public static final int UDLOCNUMCODE = 140;//风机号

    public static final int SP_JOBPLANCODE = 150;//排查作业计划

    public static final int WTCODE = 160;//风机型号

    public static final int TP_JOBPLANCODE = 170;//技改作业计划

    public static final int LOCATIONCODE = 180;//设备位置

    public static final int ZYS_UDPLANNUMCODE = 190;//终验收计划

    public static final int FAILURECODE = 200;//故障类

    public static final int PROBLEMCODE = 210;//问题原因

    public static final int ITEMCODE = 220;//物资编码

    public static final int LOCATIONCODE2 = 230;//库房

    public static final int UDVEHICLE = 240;//车辆

    public static final int WORKORDERCODE = 250;//相关故障工单

    public static final int UDDEPTCODE = 260;//部门

    public static final int WONUMCODE = 270;//业务单号

}
