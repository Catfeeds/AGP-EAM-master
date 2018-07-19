package com.hsk.hxqh.agp_eam.api;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.hsk.hxqh.agp_eam.bean.LoginResults;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.config.Constants;
import com.hsk.hxqh.agp_eam.model.ASSET;
import com.hsk.hxqh.agp_eam.model.ASSET_WORKORDER;
import com.hsk.hxqh.agp_eam.model.COMPANIES;
import com.hsk.hxqh.agp_eam.model.CRAFTRATE;
import com.hsk.hxqh.agp_eam.model.Doclinks;
import com.hsk.hxqh.agp_eam.model.INVBALANCES;
import com.hsk.hxqh.agp_eam.model.INVENTORY;
import com.hsk.hxqh.agp_eam.model.INVRESERVE;
import com.hsk.hxqh.agp_eam.model.INVUSEEntity;
import com.hsk.hxqh.agp_eam.model.INVUSELINE;
import com.hsk.hxqh.agp_eam.model.ITEM;
import com.hsk.hxqh.agp_eam.model.JOBPLAN;
import com.hsk.hxqh.agp_eam.model.LABTRANS;
import com.hsk.hxqh.agp_eam.model.LOCATIONS;
import com.hsk.hxqh.agp_eam.model.MATRECTRANS;
import com.hsk.hxqh.agp_eam.model.MATUSETRANS;
import com.hsk.hxqh.agp_eam.model.PERSON;
import com.hsk.hxqh.agp_eam.model.PO;
import com.hsk.hxqh.agp_eam.model.SPAREPART;
import com.hsk.hxqh.agp_eam.model.UDSTOCK;
import com.hsk.hxqh.agp_eam.model.UDSTOCKLINE;
import com.hsk.hxqh.agp_eam.model.UDYEARPLAN;
import com.hsk.hxqh.agp_eam.model.WFASSIGNMENT;
import com.hsk.hxqh.agp_eam.model.WOACTIVITY;
import com.hsk.hxqh.agp_eam.model.WORKORDER;
import com.hsk.hxqh.agp_eam.model.UDFAULTREPORT;
import com.hsk.hxqh.agp_eam.model.UDWORKAPPLY;
import com.hsk.hxqh.agp_eam.model.WPITEM;
import com.hsk.hxqh.agp_eam.model.WPLABOR;
import com.hsk.hxqh.agp_eam.model.WPMATERIAL;
import com.hsk.hxqh.agp_eam.model.WebResult;
import com.hsk.hxqh.agp_eam.ui.activity.PodetailActivity;
import com.hsk.hxqh.agp_eam.unit.ArrayUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Json数据解析类
 */
public class JsonUtils<E> {
    private static final String TAG = "JsonUtils";


    /**
     * 解析登录信息*
     */
    public static LoginResults parsingAuthStr(final Context cxt, String data) {
        Log.i(TAG, "data=" + data);
        LoginResults loginResults = new LoginResults();
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            String errmsg = json.getString("errmsg");
            loginResults.setErrcode(errcode);
            loginResults.setErrmsg(errmsg);
            if (errcode.equals(Constants.LOGINSUCCESS) || errcode.equals(Constants.CHANGEIMEI)) {
                loginResults.setResult(json.getString("result"));
            }


            return loginResults;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析开始工作流返回信息
     *
     * @param data
     * @return
     */
    public static WebResult parsingStartWF(String data, String num) {
        Log.i(TAG, "data=" + data);
        String woNum = null;
        WebResult webResult = new WebResult();
        try {
            JSONObject object = new JSONObject(data);
            if (object.has("msg") && !object.getString("msg").equals("")) {
                webResult.errorMsg = object.getString("msg");
            }
            if (object.has(num) && !object.getString(num).equals("")) {
                webResult.wonum = object.getString(num);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return webResult;
    }

    /**
     * 解析审批工作流返回数据
     *
     * @param data
     * @return
     */
    public static WebResult parsingGoOn(String data, String num,String keyVale) {
        Log.i(TAG, "data=" + data);
        String woNum = null;
        WebResult webResult = new WebResult();
        try {
            JSONObject object = new JSONObject(data);
            if (object.has("status") && !object.getString("status").equals("")) {
                webResult.errorMsg = object.getString("status");
            } else if (object.has("massage") && !object.getString("massage").equals("")) {
                webResult.errorMsg = object.getString("massage");
            } else if (object.has("errorMsg") && !object.getString("errorMsg").equals("")) {
                webResult.errorMsg = object.getString("errorMsg");
            }
            if (object.has(num) && !object.getString(num).equals("")) {
                webResult.wonum = object.getString(num);
            }else {
                webResult.wonum = keyVale ;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return webResult;
    }

    /**
     * 解析新增工单返回信息
     *
     * @param data
     * @return
     */
    public static WebResult parsingInsertWO(String data, String num) {
        Log.i(TAG, "data=" + data);
        String woNum = null;
        WebResult webResult = new WebResult();
        try {
            JSONObject object = new JSONObject(data);
            if (object.has("errorMsg") && !object.getString("errorMsg").equals("")) {
                webResult.errorMsg = object.getString("errorMsg");
            } else if (object.has("success") && !object.getString("success").equals("")) {
                webResult.errorMsg = object.getString("success");
            }
            if (object.has(num) && !object.getString(num).equals("")) {
                webResult.wonum = object.getString(num);
            }
            if (object.has("errorNo")) {
                webResult.errorNo = object.getInt("errorNo");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return webResult;
    }

//    /**
//     * 流程审批*
//     */
//    public static ArrayList<Wfassignment> parsingWfassignment(Context ctx, String data) {
//        ArrayList<Wfassignment> list = null;
//        Wfassignment wfassignment = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Wfassignment>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                wfassignment = new Wfassignment();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = wfassignment.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = wfassignment.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(wfassignment);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = wfassignment.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(wfassignment, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(wfassignment);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//    /**
//     * 解析新增工单返回信息
//     *
//     * @param data
//     * @return
//     */
//    public static WebResult parsingInsertWO(String data, String num) {
//        Log.i(TAG, "data=" + data);
//        String woNum = null;
//        WebResult webResult = new WebResult();
//        try {
//            JSONObject object = new JSONObject(data);
//            if (object.has("errorMsg") && !object.getString("errorMsg").equals("")) {
//                webResult.errorMsg = object.getString("errorMsg");
//            } else if (object.has("success") && !object.getString("success").equals("")) {
//                webResult.errorMsg = object.getString("success");
//            }
//            if (object.has(num) && !object.getString(num).equals("")) {
//                webResult.wonum = object.getString(num);
//            }
//            if (object.has("errorNo")) {
//                webResult.errorNo = object.getInt("errorNo");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return webResult;
//    }
//
//    /**
//     * 解析开始工作流返回信息
//     *
//     * @param data
//     * @return
//     */
//    public static WebResult parsingStartWF(String data, String num) {
//        Log.i(TAG, "data=" + data);
//        String woNum = null;
//        WebResult webResult = new WebResult();
//        try {
//            JSONObject object = new JSONObject(data);
//            if (object.has("msg") && !object.getString("msg").equals("")) {
//                webResult.errorMsg = object.getString("msg");
//            }
//            if (object.has(num) && !object.getString(num).equals("")) {
//                webResult.wonum = object.getString(num);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return webResult;
//    }
//
//    /**
//     * 解析审批工作流返回数据
//     *
//     * @param data
//     * @return
//     */
//    public static WebResult parsingGoOn(String data, String num) {
//        Log.i(TAG, "data=" + data);
//        String woNum = null;
//        WebResult webResult = new WebResult();
//        try {
//            JSONObject object = new JSONObject(data);
//            if (object.has("status") && !object.getString("status").equals("")) {
//                webResult.errorMsg = object.getString("status");
//            }
//            if (object.has(num) && !object.getString(num).equals("")) {
//                webResult.wonum = object.getString(num);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return webResult;
//    }
//

    /**
     * 待办任务*
     */
    public static ArrayList<WFASSIGNMENT> parsingWFASSIGNMENT(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<WFASSIGNMENT> list = null;
        WFASSIGNMENT wfassignment = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<WFASSIGNMENT>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                wfassignment = new WFASSIGNMENT();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = wfassignment.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = wfassignment.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(wfassignment);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = wfassignment.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(wfassignment, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(wfassignment);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 资产*
     */
    public static ArrayList<ASSET> parsingAsset(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<ASSET> list = null;
        ASSET asset = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<ASSET>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                asset = new ASSET();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = asset.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = asset.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(asset);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = asset.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(asset, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(asset);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 人员*
     */
    public static ArrayList<PERSON> parsingPERSON(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<PERSON> list = null;
        PERSON person = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<PERSON>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                person = new PERSON();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = person.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = person.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(person);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = person.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(person, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(person);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 员工*
     */
    public static ArrayList<CRAFTRATE> parsingCRAFTRATE(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<CRAFTRATE> list = null;
        CRAFTRATE craftrate = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<CRAFTRATE>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                craftrate = new CRAFTRATE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = craftrate.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = craftrate.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(craftrate);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = craftrate.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(craftrate, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(craftrate);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 供应商*
     */
    public static ArrayList<COMPANIES> parsingCOMPANIES(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<COMPANIES> list = null;
        COMPANIES companies = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<COMPANIES>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                companies = new COMPANIES();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = companies.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = companies.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(companies);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = companies.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(companies, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(companies);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 备件*
     */
    public static ArrayList<SPAREPART> parsingSPAREPART(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<SPAREPART> list = null;
        SPAREPART sparepart = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<SPAREPART>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                sparepart = new SPAREPART();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = sparepart.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = sparepart.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(sparepart);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = sparepart.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(sparepart, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(sparepart);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 工单历史*
     */
    public static ArrayList<ASSET_WORKORDER> parsingASSET_WORKORDER(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<ASSET_WORKORDER> list = null;
        ASSET_WORKORDER asset_workorder = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<ASSET_WORKORDER>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                asset_workorder = new ASSET_WORKORDER();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = asset_workorder.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = asset_workorder.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(asset_workorder);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = asset_workorder.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(asset_workorder, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(asset_workorder);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 工单*
     */
    public static ArrayList<WORKORDER> parsingWORKORDER(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<WORKORDER> list = null;
        WORKORDER workorder = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<WORKORDER>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                workorder = new WORKORDER();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = workorder.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = workorder.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(workorder);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = workorder.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(workorder, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(workorder);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 工单任务*
     */
    public static ArrayList<WOACTIVITY> parsingWOACTIVITY(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<WOACTIVITY> list = null;
        WOACTIVITY woactivity = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<WOACTIVITY>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                woactivity = new WOACTIVITY();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = woactivity.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = woactivity.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(woactivity);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = woactivity.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(woactivity, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(woactivity);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 工单计划员工*
     */
    public static ArrayList<WPLABOR> parsingWPLABOR(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<WPLABOR> list = null;
        WPLABOR wplabor = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<WPLABOR>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                wplabor = new WPLABOR();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = wplabor.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = wplabor.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(wplabor);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = wplabor.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(wplabor, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(wplabor);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 工单计划物料*
     */
    public static ArrayList<WPMATERIAL> parsingWPMATERIAL(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<WPMATERIAL> list = null;
        WPMATERIAL wpmaterial = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<WPMATERIAL>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                wpmaterial = new WPMATERIAL();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = wpmaterial.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = wpmaterial.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(wpmaterial);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = wpmaterial.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(wpmaterial, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(wpmaterial);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 工单实际员工*
     */
    public static ArrayList<LABTRANS> parsingLABTRANS(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<LABTRANS> list = null;
        LABTRANS labtrans = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<LABTRANS>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                labtrans = new LABTRANS();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = labtrans.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = labtrans.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(labtrans);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = labtrans.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(labtrans, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(labtrans);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 工单实际员工*
     */
    public static ArrayList<MATUSETRANS> parsingMATUSETRANS(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<MATUSETRANS> list = null;
        MATUSETRANS matusetrans = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<MATUSETRANS>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                matusetrans = new MATUSETRANS();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = matusetrans.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = matusetrans.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(matusetrans);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = matusetrans.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(matusetrans, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(matusetrans);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 位置*
     */
    public static ArrayList<LOCATIONS> parsingLOCATIONS(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<LOCATIONS> list = null;
        LOCATIONS locations = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<LOCATIONS>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                locations = new LOCATIONS();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = locations.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = locations.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(locations);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = locations.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(locations, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(locations);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 年度计划*
     */
    public static ArrayList<UDYEARPLAN> parsingUDYEARPLAN(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<UDYEARPLAN> list = null;
        UDYEARPLAN udyearplan = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<UDYEARPLAN>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                udyearplan = new UDYEARPLAN();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = udyearplan.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = udyearplan.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(udyearplan);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = udyearplan.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(udyearplan, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(udyearplan);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 作业计划*
     */
    public static ArrayList<JOBPLAN> parsingJOBPLAN(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<JOBPLAN> list = null;
        JOBPLAN jobplan = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<JOBPLAN>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                jobplan = new JOBPLAN();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = jobplan.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = jobplan.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(jobplan);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = jobplan.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(jobplan, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(jobplan);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 库存*
     */
    public static ArrayList<INVENTORY> parsingINVENTORY(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<INVENTORY> list = null;
        INVENTORY inventory = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<INVENTORY>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                inventory = new INVENTORY();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = inventory.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = inventory.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(inventory);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = inventory.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(inventory, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(inventory);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 库存盘点*
     */
    public static ArrayList<UDSTOCK> parsingUDSTOCK(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<UDSTOCK> list = null;
        UDSTOCK udstock = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<UDSTOCK>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                udstock = new UDSTOCK();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = udstock.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = udstock.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(udstock);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = udstock.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(udstock, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(udstock);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static ArrayList<INVUSELINE> parsingINVUSELINE(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<INVUSELINE> list = null;
        INVUSELINE  invuseline = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<INVUSELINE>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                invuseline = new INVUSELINE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = invuseline.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = invuseline.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(invuseline);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = invuseline.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(invuseline, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                list.add(invuseline);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 库存盘点子表*
     */
    public static ArrayList<UDSTOCKLINE> parsingUDSTOCKLINE(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<UDSTOCKLINE> list = null;
        UDSTOCKLINE udstockline = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<UDSTOCKLINE>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                udstockline = new UDSTOCKLINE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = udstockline.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name)){
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = udstockline.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(udstockline);
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = udstockline.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(udstockline, jsonObject.getInt(name));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = udstockline.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(udstockline);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = udstockline.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(udstockline, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(udstockline);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 库存*
     */
    public static ArrayList<INVBALANCES> parsingINVBALANCES(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<INVBALANCES> list = null;
        INVBALANCES invbalances = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<INVBALANCES>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                invbalances = new INVBALANCES();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = invbalances.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = invbalances.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(invbalances);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = invbalances.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(invbalances, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(invbalances);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 物资台帐*
     */
    public static ArrayList<ITEM> parsingITEM(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<ITEM> list = null;
        ITEM item = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<ITEM>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                item = new ITEM();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = item.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = item.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(item);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = item.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(item, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(item);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 封装工单数据
     *
     * @param workOrder
     * @param woactivities
     * @return
     */
    public static String WorkToJson(WORKORDER workOrder, ArrayList<WOACTIVITY> woactivities,ArrayList<WPLABOR> wplabors,ArrayList<WPMATERIAL> wpmaterials,
                                    ArrayList<LABTRANS> labtranses,ArrayList<MATUSETRANS> matusetranses) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            Field[] field = workOrder.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
            for (int j = 0; j < field.length; j++) {
                field[j].setAccessible(true);
                String name = field[j].getName();//获取属性的名字
                Method getOrSet = null;
                try {
                    if (!name.equals("isnew") && !name.equals("WORKORDERID") && !name.equals("id") && !name.equals("isUpdate")) {
                        getOrSet = workOrder.getClass().getMethod("get" + name);
                        Object value = null;
                        value = getOrSet.invoke(workOrder);
                        if (value != null) {
                            jsonObject.put(name, value + "");
                        }
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            JSONObject object = new JSONObject();
            if (woactivities != null && woactivities.size() != 0) {
                object.put("WOACTIVITY", "");
                JSONArray woactivityArray = new JSONArray();
                JSONObject woactivityObj;
                for (int i = 0; i < woactivities.size(); i++) {
                    woactivityObj = new JSONObject();
                    Field[] field1 = woactivities.get(i).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                    for (int j = 0; j < field1.length; j++) {
                        field1[j].setAccessible(true);
                        String name = field1[j].getName();//获取属性的名字
                        Method getOrSet = null;
                        try {
                            if (!name.equals("belongid") && !name.equals("id") && !name.equals("isUpload")) {
                                getOrSet = woactivities.get(i).getClass().getMethod("get" + name);
                                Object value = null;
                                value = getOrSet.invoke(woactivities.get(i));
                                if (value != null) {
                                    woactivityObj.put(name, value + "");
                                }
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    if (woactivityObj.get("TYPE").equals("add") && woactivityObj.has("WORKORDERID") && woactivityObj.get("WORKORDERID").equals("0")) {
                        woactivityObj.remove("WORKORDERID");
                    }
                    woactivityArray.put(woactivityObj);
                }
                jsonObject.put("WOACTIVITY", woactivityArray);
            }
            if (wplabors != null && wplabors.size() != 0) {
                object.put("WPLABOR", "");
                JSONArray wplaborArray = new JSONArray();
                JSONObject wplaborObj;
                for (int i = 0; i < woactivities.size(); i++) {
                    wplaborObj = new JSONObject();
                    Field[] field1 = woactivities.get(i).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                    for (int j = 0; j < field1.length; j++) {
                        field1[j].setAccessible(true);
                        String name = field1[j].getName();//获取属性的名字
                        Method getOrSet = null;
                        try {
                            if (!name.equals("belongid") && !name.equals("id") && !name.equals("isUpload")) {
                                getOrSet = woactivities.get(i).getClass().getMethod("get" + name);
                                Object value = null;
                                value = getOrSet.invoke(woactivities.get(i));
                                if (value != null) {
                                    wplaborObj.put(name, value + "");
                                }
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
//                    if (wplaborObj.get("TYPE").equals("add") && wplaborObj.has("WORKORDERID") && wplaborObj.get("WORKORDERID").equals("0")) {
//                        wplaborObj.remove("WORKORDERID");
//                    }
                    wplaborArray.put(wplaborObj);
                }
                jsonObject.put("WPLABOR", wplaborArray);
            }
            if (wpmaterials != null && wpmaterials.size() != 0) {
                object.put("WPMATERIAL", "");
                JSONArray wpmaterialArray = new JSONArray();
                JSONObject wpmaterialObj;
                for (int i = 0; i < woactivities.size(); i++) {
                    wpmaterialObj = new JSONObject();
                    Field[] field1 = woactivities.get(i).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                    for (int j = 0; j < field1.length; j++) {
                        field1[j].setAccessible(true);
                        String name = field1[j].getName();//获取属性的名字
                        Method getOrSet = null;
                        try {
                            if (!name.equals("belongid") && !name.equals("id") && !name.equals("isUpload")) {
                                getOrSet = woactivities.get(i).getClass().getMethod("get" + name);
                                Object value = null;
                                value = getOrSet.invoke(woactivities.get(i));
                                if (value != null) {
                                    wpmaterialObj.put(name, value + "");
                                }
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
//                    if (woactivityObj.get("TYPE").equals("add") && woactivityObj.has("WORKORDERID") && woactivityObj.get("WORKORDERID").equals("0")) {
//                        woactivityObj.remove("WORKORDERID");
//                    }
                    wpmaterialArray.put(wpmaterialObj);
                }
                jsonObject.put("WPMATERIAL", wpmaterialArray);
            }
            if (labtranses != null && labtranses.size() != 0) {
                object.put("LABTRANS", "");
                JSONArray labtransArray = new JSONArray();
                JSONObject labtransObj;
                for (int i = 0; i < woactivities.size(); i++) {
                    labtransObj = new JSONObject();
                    Field[] field1 = woactivities.get(i).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                    for (int j = 0; j < field1.length; j++) {
                        field1[j].setAccessible(true);
                        String name = field1[j].getName();//获取属性的名字
                        Method getOrSet = null;
                        try {
                            if (!name.equals("belongid") && !name.equals("id") && !name.equals("isUpload")) {
                                getOrSet = woactivities.get(i).getClass().getMethod("get" + name);
                                Object value = null;
                                value = getOrSet.invoke(woactivities.get(i));
                                if (value != null) {
                                    labtransObj.put(name, value + "");
                                }
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
//                    if (woactivityObj.get("TYPE").equals("add") && woactivityObj.has("WORKORDERID") && woactivityObj.get("WORKORDERID").equals("0")) {
//                        woactivityObj.remove("WORKORDERID");
//                    }
                    labtransArray.put(labtransObj);
                }
                jsonObject.put("LABTRANS", labtransArray);
            }
            if (matusetranses != null && matusetranses.size() != 0) {
                object.put("MATUSETRANS", "");
                JSONArray matusetransArray = new JSONArray();
                JSONObject matusetransObj;
                for (int i = 0; i < woactivities.size(); i++) {
                    matusetransObj = new JSONObject();
                    Field[] field1 = woactivities.get(i).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                    for (int j = 0; j < field1.length; j++) {
                        field1[j].setAccessible(true);
                        String name = field1[j].getName();//获取属性的名字
                        Method getOrSet = null;
                        try {
                            if (!name.equals("belongid") && !name.equals("id") && !name.equals("isUpload")) {
                                getOrSet = woactivities.get(i).getClass().getMethod("get" + name);
                                Object value = null;
                                value = getOrSet.invoke(woactivities.get(i));
                                if (value != null) {
                                    matusetransObj.put(name, value + "");
                                }
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
//                    if (woactivityObj.get("TYPE").equals("add") && woactivityObj.has("WORKORDERID") && woactivityObj.get("WORKORDERID").equals("0")) {
//                        woactivityObj.remove("WORKORDERID");
//                    }
                    matusetransArray.put(matusetransObj);
                }
                jsonObject.put("MATUSETRANS", matusetransArray);
            }
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(object);
            jsonObject.put("relationShip", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("封装工单", jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     * 封装工单数据
     *
     * @param workOrder
     * @return
     */
    public static String WorkToJson(WORKORDER workOrder) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            Field[] field = workOrder.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
            for (int j = 0; j < field.length; j++) {
                field[j].setAccessible(true);
                String name = field[j].getName();//获取属性的名字
                Method getOrSet = null;
                try {
                    if (!name.equals("isnew") && !name.equals("WORKORDERID")) {
                        getOrSet = workOrder.getClass().getMethod("get" + name);
                        Object value = null;
                        value = getOrSet.invoke(workOrder);
                        if (value != null) {
                            jsonObject.put(name, value + "");
                        }
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            JSONObject object = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(object);
            jsonObject.put("relationShip", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("封装工单", jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     * 封装故障提报单
     *
     * @param workOrder
     * @return
     */
    public static String UdfaultreportToJson(UDFAULTREPORT workOrder) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            Field[] field = workOrder.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
            for (int j = 0; j < field.length; j++) {
                field[j].setAccessible(true);
                String name = field[j].getName();//获取属性的名字
                Method getOrSet = null;
                try {
                    if (!name.equals("isnew") && !name.equals("WORKORDERID")) {
                        getOrSet = workOrder.getClass().getMethod("get" + name);
                        Object value = null;
                        value = getOrSet.invoke(workOrder);
                        if (value != null) {
                            jsonObject.put(name, value + "");
                        }
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            JSONObject object = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(object);
            jsonObject.put("relationShip", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("封装工单", jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     * 封装工作申请
     *
     * @param workOrder
     * @return
     */
    public static String UdworkapplyToJson(UDWORKAPPLY workOrder) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            Field[] field = workOrder.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
            for (int j = 0; j < field.length; j++) {
                field[j].setAccessible(true);
                String name = field[j].getName();//获取属性的名字
                Method getOrSet = null;
                try {
                    if (!name.equals("isnew") && !name.equals("WORKORDERID")) {
                        getOrSet = workOrder.getClass().getMethod("get" + name);
                        Object value = null;
                        value = getOrSet.invoke(workOrder);
                        if (value != null) {
                            jsonObject.put(name, value + "");
                        }
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            JSONObject object = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(object);
            jsonObject.put("relationShip", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("封装工作申请", jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     * 附件类*
     */
    public static ArrayList<Doclinks> parsingDoclinks(Context ctx, String data) {
        Log.i(TAG, "ddddata=" + data);
        ArrayList<Doclinks> list = null;
        Doclinks doclinks = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<Doclinks>();
            for (int i = 0; i < jsonArray.length(); i++) {
                doclinks = new Doclinks();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = doclinks.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = doclinks.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(doclinks);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = doclinks.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(doclinks, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(doclinks);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

//
//    /**
//     * 解析风机型号信息*
//     */
//
//    public static ArrayList<Udfandetails> parsingUdfandetails(Context ctx, String data) {
//        ArrayList<Udfandetails> list = null;
//        Udfandetails udfandetails = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Udfandetails>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udfandetails = new Udfandetails();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = udfandetails.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    Log.i(TAG, "name=" + name);
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udfandetails.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udfandetails);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udfandetails.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udfandetails, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(udfandetails);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解析风机型号信息*
//     */
//    public static ArrayList<Location> parsingLocation(String data) {
//        ArrayList<Location> list = null;
//        Location location = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Location>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                location = new Location();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = location.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    Log.i(TAG, "name=" + name);
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = location.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(location);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = location.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(location, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(location);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解析终验收计划表信息*
//     */
//    public static ArrayList<Udinvestp> parsingUdinvestp(String data) {
//        ArrayList<Udinvestp> list = null;
//        Udinvestp location = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Udinvestp>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                location = new Udinvestp();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = location.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    Log.i(TAG, "name=" + name);
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = location.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(location);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = location.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(location, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(location);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解析故障代码信息*
//     */
//    public static ArrayList<Failurelist> parsingFailurelist(String data) {
//        ArrayList<Failurelist> list = null;
//        Failurelist location = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Failurelist>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                location = new Failurelist();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = location.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    Log.i(TAG, "name=" + name);
//                    if (jsonObject.has(name) && jsonObject.get(name) != null && !jsonObject.get(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = location.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(location);
//                            if (value == null || Integer.parseInt(value + "") == 0) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = location.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(location, jsonObject.get(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(location);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解析终验收计划信息*
//     */
//    public static ArrayList<Item> parsingItem(String data) {
//        ArrayList<Item> list = null;
//        Item item = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Item>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                item = new Item();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = item.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    Log.i(TAG, "name=" + name);
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = item.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(item);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = item.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(item, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(item);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解析项目人员信息*
//     */
//
//    public static ArrayList<UdPerson> parsingUdPerson(Context ctx, String data) {
//        ArrayList<UdPerson> list = null;
//        UdPerson udPerson = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<UdPerson>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udPerson = new UdPerson();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = udPerson.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udPerson.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udPerson);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udPerson.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udPerson, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(udPerson);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//    /**
//     * 解析项目车辆信息*
//     */
//
//    public static ArrayList<Udvehicle> parsingUdvehicle(String data) {
//        ArrayList<Udvehicle> list = null;
//        Udvehicle udvehicle = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Udvehicle>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udvehicle = new Udvehicle();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = udvehicle.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udvehicle.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udvehicle);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udvehicle.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udvehicle, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(udvehicle);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解析部门信息*
//     */
//
//    public static ArrayList<Uddept> parsingUddept(String data) {
//        ArrayList<Uddept> list = null;
//        Uddept uddept = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Uddept>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                uddept = new Uddept();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = uddept.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = uddept.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(uddept);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = uddept.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(uddept, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(uddept);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 项目日报*
//     */
//    public static ArrayList<Udprorunlog> parsingUdprorunlog(Context ctx, String data) {
//        Log.i("项目日报", "data=" + data);
//        ArrayList<Udprorunlog> list = null;
//        Udprorunlog udprorunlog = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Udprorunlog>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udprorunlog = new Udprorunlog();
//                jsonObject = jsonArray.getJSONObject(i);
//                Log.e("项目日报","项目日报列表"+jsonObject);
//
//                Field[] field = udprorunlog.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    Log.i(TAG, "name=" + name);
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udprorunlog.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udprorunlog);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udprorunlog.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udprorunlog, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(udprorunlog);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//    /**
//     * 问题联络单*
//     */
//    public static ArrayList<Udfeedback> parsingUdfeedback(Context ctx, String data) {
//        ArrayList<Udfeedback> list = null;
//        Udfeedback udfeedback = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Udfeedback>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udfeedback = new Udfeedback();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = udfeedback.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udfeedback.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udfeedback);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udfeedback.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udfeedback, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(udfeedback);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//    /**
//     * 库存盘点*
//     */
//    public static ArrayList<Udstock> parsingUdstock(Context ctx, String data) {
//        ArrayList<Udstock> list = null;
//        Udstock udstock = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Udstock>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udstock = new Udstock();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = udstock.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udstock.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udstock);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udstock.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udstock, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(udstock);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//    /**
//     * 库存盘点行*
//     */
//    public static ArrayList<Udstockline> parsingUdstockline(Context ctx, String data) {
//        ArrayList<Udstockline> list = null;
//        Udstockline udstockline = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Udstockline>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udstockline = new Udstockline();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = udstockline.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.get(name) != null) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udstockline.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udstockline);
//                            if (value == null || Integer.parseInt(String.valueOf(value)) == 0) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udstockline.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udstockline, jsonObject.get(name));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(udstockline);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//
    /**
     * 分页解析返回的结果*
     */
    public static Results parsingResults(Context ctx, String data) {
        Log.i(TAG, "data=" + data);
        String result = null;
        Results results = null;
        try {
            JSONObject json = new JSONObject(data);
            String jsonString = json.getString("errcode");
            if (jsonString.equals(Constants.GETDATASUCCESS)) {
                result = json.getString("result");
                char first = result.charAt(0);
                JSONObject rJson;
                if (first == '['){
                    rJson  = new JSONArray(result).getJSONObject(0);
                }else {
                    rJson = new JSONObject(result);
                }
                String curpage = rJson.getString("curpage");
                String totalresult = rJson.getString("totalresult");
                String resultlist = rJson.getString("resultlist");
                String totalpage = rJson.getString("totalpage");
                String showcount = rJson.getString("showcount");
                results = new Results();
                results.setCurpage(Integer.valueOf(curpage));
                results.setTotalresult(totalresult);
                results.setResultlist(resultlist);
                results.setTotalpage(totalpage);
                results.setShowcount(Integer.valueOf(showcount));
            }

            return results;


        } catch (JSONException e) {
            e.printStackTrace();
            return results;
        }

    }

    /**
     * 不分页解析返回的结果*
     */
    public static Results parsingResults1(Context ctx, String data) {

        String result = null;
        Results results = null;
        try {
            JSONObject json = new JSONObject(data);
            String jsonString = json.getString("errcode");
            if (jsonString.equals(Constants.GETDATASUCCESS)) {
                result = json.getString("result");
                Log.i(TAG, "result=" + result);
                results = new Results();
                results.setResultlist(result);
            }

            return results;


        } catch (JSONException e) {
            e.printStackTrace();
            return results;
        }

    }
//
//    /**
//     * 解析工单信息
//     */
//    public static ArrayList<WorkOrder> parsingWorkOrder(Context ctx, String data) {
//        Log.i(TAG, "WorkOrder data=" + data);
//        ArrayList<WorkOrder> list = null;
//        WorkOrder workOrder = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<WorkOrder>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                workOrder = new WorkOrder();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = workOrder.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = workOrder.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(workOrder);
//                            if (value == null || Integer.parseInt(String.valueOf(value)) == 0) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = workOrder.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(workOrder, jsonObject.get(name));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                list.add(workOrder);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解析调试工单信息
//     */
//    public static ArrayList<DebugWorkOrder> parsingDebugWorkOrder(Context ctx, String data) {
//        Log.i(TAG, "WorkOrder data=" + data);
//
//
//
//        ArrayList<DebugWorkOrder> list = null;
//
//        DebugWorkOrder workOrder = null;
//
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<DebugWorkOrder>();
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//
//                workOrder = new DebugWorkOrder();
//                jsonObject = jsonArray.getJSONObject(i);
//
//                Log.e("调试工单","数据"+jsonObject);
//
//                Field[] field = workOrder.getClass().getDeclaredFields();//获取实体类的所有属性，返回Field数组
//
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = workOrder.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(workOrder);
//                            if (value == null || Integer.parseInt(String.valueOf(value)) == 0) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = workOrder.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(workOrder, jsonObject.getString(name));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                list.add(workOrder);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解析调试工单信息
//     */
//    public static ArrayList<UdTriprePort> parsingTripReport(Context ctx, String data) {
//
//        ArrayList<UdTriprePort> list = null;
//
//        UdTriprePort udTriprePort = null;
//
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<UdTriprePort>();
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//
//                udTriprePort = new UdTriprePort();
//                jsonObject = jsonArray.getJSONObject(i);
//
//                Log.e("出差","JSON数据"+jsonObject);
//
//                Field[] field = udTriprePort.getClass().getDeclaredFields();//获取实体类的所有属性，返回Field数组
//
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udTriprePort.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udTriprePort);
//                            if (value == null || Integer.parseInt(String.valueOf(value)) == 0) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udTriprePort.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udTriprePort, jsonObject.getString(name));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                list.add(udTriprePort);
//            }
//            return list;
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解析调试工单子表信息
//     */
//    public static ArrayList<UddebugWorkOrderLine> parsingUddebugWorkOrderLine(Context ctx, String data, String wonum) {
//
//
//
//
//
//        ArrayList<UddebugWorkOrderLine> list = null;
//        UddebugWorkOrderLine uddebugWorkOrderLine = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<UddebugWorkOrderLine>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                uddebugWorkOrderLine = new UddebugWorkOrderLine();
//                jsonObject = jsonArray.getJSONObject(i);
//
//                Log.e("调试工单","子表数据"+jsonObject);
//
//                Field[] field = uddebugWorkOrderLine.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = uddebugWorkOrderLine.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(uddebugWorkOrderLine);
//                            if (value == null || Integer.parseInt(String.valueOf(value)) == 0) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = uddebugWorkOrderLine.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(uddebugWorkOrderLine, jsonObject.getString(name));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                uddebugWorkOrderLine.DEBUGWORKORDERNUM = wonum;
//                list.add(uddebugWorkOrderLine);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解析任务信息
//     */
//    public static ArrayList<Woactivity> parsingWoactivity(Context ctx, String data, String wonum) {
//        Log.i(TAG, "WorkOrder data=" + data);
//        ArrayList<Woactivity> list = null;
//        Woactivity woactivity = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Woactivity>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                woactivity = new Woactivity();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = woactivity.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.get(name) != null) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = woactivity.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(woactivity);
//                            if (value == null || Integer.parseInt(String.valueOf(value)) == 0) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = woactivity.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(woactivity, jsonObject.get(name));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                woactivity.WONUM = wonum;
//                woactivity.isUpload = true;
//                list.add(woactivity);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解析任务信息
//     */
//    public static ArrayList<Woactivity> parsingJobtask(String data, String wonum) {
//        Log.i(TAG, "WorkOrder data=" + data);
//        ArrayList<Woactivity> list = null;
//        Woactivity woactivity = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Woactivity>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                woactivity = new Woactivity();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = woactivity.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = woactivity.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(woactivity);
//                            if (value == null || Integer.parseInt(String.valueOf(value)) == 0) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = woactivity.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(woactivity, jsonObject.get(name));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                woactivity.WONUM = wonum == null ? "" : wonum;
//                woactivity.TYPE = "";
//                woactivity.isUpload = true;
//                list.add(woactivity);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解析物料信息
//     */
//    public static ArrayList<Wpmaterial> parsingWpmaterial(Context ctx, String data, String wonum) {
//        Log.i(TAG, "WorkOrder data=" + data);
//        ArrayList<Wpmaterial> list = null;
//        Wpmaterial wpmaterial = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Wpmaterial>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                wpmaterial = new Wpmaterial();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = wpmaterial.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = wpmaterial.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(wpmaterial);
//                            if (value == null || Integer.parseInt(String.valueOf(value)) == 0) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = wpmaterial.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(wpmaterial, jsonObject.get(name));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                wpmaterial.WONUM = wonum;
//                wpmaterial.isUpload = true;
//                list.add(wpmaterial);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//    /**
//     * 解析土建阶段子表信息
//     */
//    public static ArrayList<UdprorunlogLine1> parsingUdprorunlogLine1(Context ctx, String data, String prorunlognum) {
//        Log.i(TAG, "UdprorunlogLine1 data=" + data);
//        ArrayList<UdprorunlogLine1> list = null;
//        UdprorunlogLine1 udprorunlogLine1 = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<UdprorunlogLine1>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udprorunlogLine1 = new UdprorunlogLine1();
//                jsonObject = jsonArray.getJSONObject(i);
//                Log.e("项目日报","土建阶段子表"+jsonObject);
//                Field[] field = udprorunlogLine1.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.get(name) != null) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udprorunlogLine1.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udprorunlogLine1);
//                            if (value == null || Integer.parseInt(String.valueOf(value)) == 0) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udprorunlogLine1.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udprorunlogLine1, jsonObject.get(name));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                udprorunlogLine1.PRORUNLOGNUM = prorunlognum;
//                udprorunlogLine1.isUpload = true;
//                list.add(udprorunlogLine1);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解析吊装调试子表信息
//     */
//    public static ArrayList<UdprorunlogLine2> parsingUdprorunlogLine2(Context ctx, String data, String prorunlognum) {
//
//        ArrayList<UdprorunlogLine2> list = null;
//        UdprorunlogLine2 udprorunlogLine2 = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<UdprorunlogLine2>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udprorunlogLine2 = new UdprorunlogLine2();
//                jsonObject = jsonArray.getJSONObject(i);
//                Log.e("项目日报","吊装调试子表"+jsonObject+"\n");
//                Field[] field = udprorunlogLine2.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.get(name) != null) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udprorunlogLine2.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udprorunlogLine2);
//                            if (value == null || Integer.parseInt(String.valueOf(value)) == 0) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udprorunlogLine2.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udprorunlogLine2, jsonObject.get(name));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                udprorunlogLine2.PRORUNLOGNUM = prorunlognum;
//                udprorunlogLine2.isUpload = true;
//                list.add(udprorunlogLine2);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 工作日报子表信息
//     */
//    public static ArrayList<UdprorunlogLine3> parsingUdprorunlogLine3(Context ctx, String data, String prorunlognum) {
//        Log.i(TAG, "UdprorunlogLine3 data=" + data);
//        ArrayList<UdprorunlogLine3> list = null;
//        UdprorunlogLine3 udprorunlogLine3 = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<UdprorunlogLine3>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udprorunlogLine3 = new UdprorunlogLine3();
//                jsonObject = jsonArray.getJSONObject(i);
//                Log.e("项目日报","工作日报子表"+jsonObject);
//                Field[] field = udprorunlogLine3.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.get(name) != null) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udprorunlogLine3.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udprorunlogLine3);
//                            if (value == null || name.equals("UDPRORUNLOGCID")|| name.equals("TEM")|| name.equals("WINDSPEED")) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udprorunlogLine3.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udprorunlogLine3, jsonObject.get(name));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                udprorunlogLine3.PRORUNLOGNUM = prorunlognum;
//                udprorunlogLine3.isUpload = true;
//                list.add(udprorunlogLine3);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解析工装管理子表信息
//     */
//    public static ArrayList<UdprorunlogLine4> parsingUdprorunlogLine4(Context ctx, String data, String prorunlognum) {
//        Log.i(TAG, "UdprorunlogLine1 data=" + data);
//        ArrayList<UdprorunlogLine4> list = null;
//        UdprorunlogLine4 udprorunlogLine4 = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<UdprorunlogLine4>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udprorunlogLine4 = new UdprorunlogLine4();
//                jsonObject = jsonArray.getJSONObject(i);
//                Log.e("项目日报","工装管理子表"+jsonObject);
//                Field[] field = udprorunlogLine4.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.get(name) != null) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udprorunlogLine4.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udprorunlogLine4);
//                            if (value == null || Integer.parseInt(String.valueOf(value)) == 0) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udprorunlogLine4.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udprorunlogLine4, jsonObject.get(name));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                udprorunlogLine4.PRORUNLOGNUM = prorunlognum;
//                udprorunlogLine4.isUpload = true;
//                list.add(udprorunlogLine4);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 工作日报子表信息
//     */
//    public static ArrayList<Udrunliner> parsingUdrunliner(Context ctx, String data, String prorunlognum) {
//        Log.i(TAG, "Udrunliner data=" + data);
//        ArrayList<Udrunliner> list = null;
//        Udrunliner udrunliner = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Udrunliner>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udrunliner = new Udrunliner();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = udrunliner.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.get(name) != null) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udrunliner.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udrunliner);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udrunliner.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udrunliner, jsonObject.get(name));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
////                udprorunlogLine3.PRORUNLOGNUM = prorunlognum;
//                udrunliner.isUpload = true;
//                list.add(udrunliner);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解析人员信息
//     */
//    public static ArrayList<Person> parsingPerson(String data) {
//        Log.i(TAG, "WorkOrder data=" + data);
//        ArrayList<Person> list;
//        list = null;
//        Person person = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Person>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                person = new Person();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = person.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = person.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(person);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = person.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(person, jsonObject.getString(name));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                list.add(person);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解析人员信息
//     */
//    public static ArrayList<JobPlan> parsingJobPlan(String data) {
//        Log.i(TAG, "WorkOrder data=" + data);
//        ArrayList<JobPlan> list;
//        list = null;
//        JobPlan jobPlan = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<JobPlan>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                jobPlan = new JobPlan();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = jobPlan.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = jobPlan.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(jobPlan);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = jobPlan.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(jobPlan, jsonObject.getString(name));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                list.add(jobPlan);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 封装工单数据
//     *
//     * @param workOrder
//     * @param woactivities
//     * @return
//     */
//    public static String WorkToJson(WorkOrder workOrder, ArrayList<Woactivity> woactivities, ArrayList<Wpmaterial> wpmaterials) {
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//        try {
//            Field[] field = workOrder.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//            for (int j = 0; j < field.length; j++) {
//                field[j].setAccessible(true);
//                String name = field[j].getName();//获取属性的名字
//                Method getOrSet = null;
//                try {
//                    if (!name.equals("isnew")&&!name.equals("WORKORDERID")&&!name.equals("id")&&!name.equals("isUpdate")) {
//                        getOrSet = workOrder.getClass().getMethod("get" + name);
//                        Object value = null;
//                        value = getOrSet.invoke(workOrder);
//                        if (value != null) {
//                            jsonObject.put(name, value + "");
//                        }
//                    }
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//            JSONObject object = new JSONObject();
//            if (woactivities != null && woactivities.size() != 0) {
//                object.put("WOACTIVITY", "");
//                JSONArray woactivityArray = new JSONArray();
//                JSONObject woactivityObj;
//                for (int i = 0; i < woactivities.size(); i++) {
//                    woactivityObj = new JSONObject();
//                    Field[] field1 = woactivities.get(i).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                    for (int j = 0; j < field1.length; j++) {
//                        field1[j].setAccessible(true);
//                        String name = field1[j].getName();//获取属性的名字
//                        Method getOrSet = null;
//                        try {
//                            if (!name.equals("belongid")&&!name.equals("id")&&!name.equals("isUpload")) {
//                                getOrSet = woactivities.get(i).getClass().getMethod("get" + name);
//                                Object value = null;
//                                value = getOrSet.invoke(woactivities.get(i));
//                                if (value != null) {
//                                    woactivityObj.put(name, value + "");
//                                }
//                            }
//                        } catch (NoSuchMethodException e) {
//                            e.printStackTrace();
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (woactivityObj.get("TYPE").equals("add") && woactivityObj.has("WORKORDERID") && woactivityObj.get("WORKORDERID").equals("0")) {
//                        woactivityObj.remove("WORKORDERID");
//                    }
//                    woactivityArray.put(woactivityObj);
//                }
//                jsonObject.put("WOACTIVITY", woactivityArray);
//            }
//            if (wpmaterials != null && wpmaterials.size() != 0) {
//                object.put("WPMATERIAL", "");
//                JSONArray wpmaterialsArray = new JSONArray();
//                JSONObject wpmaterialsObj;
//                for (int i = 0; i < wpmaterials.size(); i++) {
//                    wpmaterialsObj = new JSONObject();
//                    Field[] field1 = wpmaterials.get(i).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                    for (int j = 0; j < field1.length; j++) {
//                        field1[j].setAccessible(true);
//                        String name = field1[j].getName();//获取属性的名字
//                        Method getOrSet = null;
//                        try {
//                            if (!name.equals("isUpload") && name.equals("WPITEMID") || name.equals("ITEMNUM") || name.equals("ITEMQTY") || name.equals("LOCATION") || name.equals("TYPE")) {
//                                getOrSet = wpmaterials.get(i).getClass().getMethod("get" + name);
//                                Object value = null;
//                                value = getOrSet.invoke(wpmaterials.get(i));
//                                if (value != null) {
//                                    wpmaterialsObj.put(name, value + "");
//                                }
//                            }
//                        } catch (NoSuchMethodException e) {
//                            e.printStackTrace();
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (wpmaterialsObj.get("TYPE").equals("add") && wpmaterialsObj.has("WPITEMID") && wpmaterialsObj.get("WPITEMID").equals("0")) {
//                        wpmaterialsObj.remove("WPITEMID");
//                    }
//                    wpmaterialsArray.put(wpmaterialsObj);
//                }
//                jsonObject.put("WPMATERIAL", wpmaterialsArray);
//            }
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(object);
//            jsonObject.put("relationShip", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.e("封装工单", jsonObject.toString());
//        return jsonObject.toString();
//    }
//
//    /**
//     * 封装工单数据
//     *
//     * @param workOrder
//     * @return
//     */
//    public static String WorkToJson(WorkOrder workOrder) {
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//        try {
//            Field[] field = workOrder.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//            for (int j = 0; j < field.length; j++) {
//                field[j].setAccessible(true);
//                String name = field[j].getName();//获取属性的名字
//                Method getOrSet = null;
//                try {
//                    if (!name.equals("isnew")&&!name.equals("WORKORDERID")) {
//                        getOrSet = workOrder.getClass().getMethod("get" + name);
//                        Object value = null;
//                        value = getOrSet.invoke(workOrder);
//                        if (value != null) {
//                            jsonObject.put(name, value + "");
//                        }
//                    }
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//            JSONObject object = new JSONObject();
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(object);
//            jsonObject.put("relationShip", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.e("封装工单", jsonObject.toString());
//        return jsonObject.toString();
//    }
//
//    /**
//     * 封装调试工单数据
//     *
//     * @param workOrder
//     * @param woactivities
//     * @return
//     */
//    public static String DebugWorkToJson(DebugWorkOrder workOrder, ArrayList<UddebugWorkOrderLine> woactivities) {
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//        try {
//            Field[] field = workOrder.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//            for (int j = 0; j < field.length; j++) {
//                field[j].setAccessible(true);
//                String name = field[j].getName();//获取属性的名字
//                Method getOrSet = null;
//                try {
//                    if (!name.equals("isnew")) {
//                        getOrSet = workOrder.getClass().getMethod("get" + name);
//                        Object value = null;
//                        value = getOrSet.invoke(workOrder);
//                        if (value != null) {
//                            jsonObject.put(name, value + "");
//                        }
//                    }
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//            JSONObject object = new JSONObject();
//            if (woactivities != null && woactivities.size() != 0) {
//                object.put("UDDEBUGWORKORDERLINE", "");
//                JSONArray woactivityArray = new JSONArray();
//                JSONObject woactivityObj;
//                for (int i = 0; i < woactivities.size(); i++) {
//                    woactivityObj = new JSONObject();
//                    Field[] field1 = woactivities.get(i).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                    for (int j = 0; j < field1.length; j++) {
//                        field1[j].setAccessible(true);
//                        String name = field1[j].getName();//获取属性的名字
//                        Method getOrSet = null;
//                        try {
//                            getOrSet = woactivities.get(i).getClass().getMethod("get" + name);
//                            Object value = null;
//                            value = getOrSet.invoke(woactivities.get(i));
//                            if (value != null) {
//                                woactivityObj.put(name, value + "");
//                            }
//                        } catch (NoSuchMethodException e) {
//                            e.printStackTrace();
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    woactivityArray.put(woactivityObj);
//                }
//                jsonObject.put("UDDEBUGWORKORDERLINE", woactivityArray);
//            }
////            if (wpmaterials != null && wpmaterials.size() != 0) {
////                object.put("WPMATERIAL", "");
////                JSONArray wpmaterialsArray = new JSONArray();
////                JSONObject wpmaterialsObj;
////                for (int i = 0; i < wpmaterials.size(); i++) {
////                    wpmaterialsObj = new JSONObject();
////                    Field[] field1 = wpmaterials.get(i).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
////                    for (int j = 0; j < field1.length; j++) {
////                        field1[j].setAccessible(true);
////                        String name = field1[j].getName();//获取属性的名字
////                        Method getOrSet = null;
////                        try {
////                            getOrSet = wpmaterials.get(i).getClass().getMethod("get" + name);
////                            Object value = null;
////                            value = getOrSet.invoke(wpmaterials.get(i));
////                            if (value != null) {
////                                wpmaterialsObj.put(name, value + "");
////                            }
////                        } catch (NoSuchMethodException e) {
////                            e.printStackTrace();
////                        } catch (IllegalAccessException e) {
////                            e.printStackTrace();
////                        } catch (InvocationTargetException e) {
////                            e.printStackTrace();
////                        }
////                    }
////                    wpmaterialsArray.put(wpmaterialsObj);
////                }
////                jsonObject.put("WPMATERIAL", wpmaterialsArray);
////            }
//
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(object);
//            jsonObject.put("relationShip", jsonArray);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject.toString();
//    }
//
//    /**
//     * 封装项目日报数据
//     *
//     * @return
//     */
//    public static String UdprorunlogToJson(Udprorunlog udprorunlog, ArrayList<UdprorunlogLine1> udprorunlogLine1s
//            , ArrayList<UdprorunlogLine2> udprorunlogLine2s, ArrayList<UdprorunlogLine3> udprorunlogLine3s,
//                                           ArrayList<UdprorunlogLine4> udprorunlogLine4s) {
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//        try {
//            Field[] field = udprorunlog.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//            for (int j = 0; j < field.length; j++) {
//                field[j].setAccessible(true);
//                String name = field[j].getName();//获取属性的名字
//                Method getOrSet = null;
//                try {
//                    if (!name.equals("isnew")) {
//                        getOrSet = udprorunlog.getClass().getMethod("get" + name);
//                        Object value = null;
//                        value = getOrSet.invoke(udprorunlog);
//                        if (value != null && !name.equals("BRANCH")) {
//                            jsonObject.put(name, value + "");
//                        }
//                    }
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//            JSONObject object = new JSONObject();
//            if (udprorunlogLine1s != null && udprorunlogLine1s.size() != 0) {
//                object.put("UDPRORUNLOGLINE1", "");
//                JSONArray udprorunlogline1Array = new JSONArray();
//                JSONObject udprorunlogline1Obj;
//                for (int i = 0; i < udprorunlogLine1s.size(); i++) {
//                    udprorunlogline1Obj = new JSONObject();
//                    Field[] field1 = udprorunlogLine1s.get(i).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                    for (int j = 0; j < field1.length; j++) {
//                        field1[j].setAccessible(true);
//                        String name = field1[j].getName();//获取属性的名字
//                        Method getOrSet = null;
//                        try {
//                            getOrSet = udprorunlogLine1s.get(i).getClass().getMethod("get" + name);
//                            Object value = null;
//                            value = getOrSet.invoke(udprorunlogLine1s.get(i));
//                            if (value != null) {
//                                udprorunlogline1Obj.put(name, value + "");
//                            }
//                        } catch (NoSuchMethodException e) {
//                            e.printStackTrace();
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (udprorunlogline1Obj.get("TYPE").equals("add") && udprorunlogline1Obj.has("UDPRORUNLOGLINE1ID") && udprorunlogline1Obj.get("UDPRORUNLOGLINE1ID").equals("0")) {
//                        udprorunlogline1Obj.remove("UDPRORUNLOGLINE1ID");
//                    }
//                    udprorunlogline1Array.put(udprorunlogline1Obj);
//                }
//                jsonObject.put("UDPRORUNLOGLINE1", udprorunlogline1Array);
//            }
//            if (udprorunlogLine2s != null && udprorunlogLine2s.size() != 0) {
//                object.put("UDPRORUNLOGLINE2", "");
//                JSONArray udprorunlogline2Array = new JSONArray();
//                JSONObject udprorunlogline2Obj;
//                for (int i = 0; i < udprorunlogLine2s.size(); i++) {
//                    udprorunlogline2Obj = new JSONObject();
//                    Field[] field1 = udprorunlogLine2s.get(i).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                    for (int j = 0; j < field1.length; j++) {
//                        field1[j].setAccessible(true);
//                        String name = field1[j].getName();//获取属性的名字
//                        Method getOrSet = null;
//                        try {
//                            getOrSet = udprorunlogLine2s.get(i).getClass().getMethod("get" + name);
//                            Object value = null;
//                            value = getOrSet.invoke(udprorunlogLine2s.get(i));
//                            if (value != null) {
//                                udprorunlogline2Obj.put(name, value + "");
//                            }
//                        } catch (NoSuchMethodException e) {
//                            e.printStackTrace();
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (udprorunlogline2Obj.get("TYPE").equals("add") && udprorunlogline2Obj.has("UDPRORUNLOGLINE2DZID") && udprorunlogline2Obj.get("UDPRORUNLOGLINE2DZID").equals("0")) {
//                        udprorunlogline2Obj.remove("UDPRORUNLOGLINE2DZID");
//                    }
//                    udprorunlogline2Array.put(udprorunlogline2Obj);
//                }
//                jsonObject.put("UDPRORUNLOGLINE2", udprorunlogline2Array);
//            }
//            if (udprorunlogLine3s != null && udprorunlogLine3s.size() != 0) {
//                object.put("UDPRORUNLOGC", "");
//                JSONArray udprorunlogline3Array = new JSONArray();
//                JSONObject udprorunlogline3Obj;
//                for (int i = 0; i < udprorunlogLine3s.size(); i++) {
//                    udprorunlogline3Obj = new JSONObject();
//                    Field[] field1 = udprorunlogLine3s.get(i).getClass().getDeclaredFields();//获取实体类的所有属性，返回Field数组
//                    for (int j = 0; j < field1.length; j++) {
//                        field1[j].setAccessible(true);
//                        String name = field1[j].getName();//获取属性的名字
//                        Method getOrSet = null;
//                        try {
//                            getOrSet = udprorunlogLine3s.get(i).getClass().getMethod("get" + name);
//                            Object value = null;
//                            value = getOrSet.invoke(udprorunlogLine3s.get(i));
//                            if (value != null) {
//                                udprorunlogline3Obj.put(name, value + "");
//                            }
//                        } catch (NoSuchMethodException e) {
//                            e.printStackTrace();
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (udprorunlogline3Obj.get("TYPE").equals("add") && udprorunlogline3Obj.has("UDPRORUNLOGCID") && udprorunlogline3Obj.get("UDPRORUNLOGCID").equals("0")) {
//                        udprorunlogline3Obj.remove("UDPRORUNLOGCID");
//                    }
//                    udprorunlogline3Array.put(udprorunlogline3Obj);
//                }
//                jsonObject.put("UDPRORUNLOGC", udprorunlogline3Array);
//            }
//            if (udprorunlogLine4s != null && udprorunlogLine4s.size() != 0) {
//                object.put("UDPRORUNLOGLINE4", "");
//                JSONArray udprorunlogLine4Array = new JSONArray();
//                JSONObject udprorunlogLine4Obj;
//                for (int i = 0; i < udprorunlogLine4s.size(); i++) {
//                    udprorunlogLine4Obj = new JSONObject();
//                    Field[] field1 = udprorunlogLine4s.get(i).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                    for (int j = 0; j < field1.length; j++) {
//                        field1[j].setAccessible(true);
//                        String name = field1[j].getName();//获取属性的名字
//                        Method getOrSet = null;
//                        try {
//                            getOrSet = udprorunlogLine4s.get(i).getClass().getMethod("get" + name);
//                            Object value = null;
//                            value = getOrSet.invoke(udprorunlogLine4s.get(i));
//                            if (value != null) {
//                                udprorunlogLine4Obj.put(name, value + "");
//                            }
//                        } catch (NoSuchMethodException e) {
//                            e.printStackTrace();
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (udprorunlogLine4Obj.get("TYPE").equals("add") && udprorunlogLine4Obj.has("UDPRORUNLOGLINE4ID") && udprorunlogLine4Obj.get("UDPRORUNLOGLINE4ID").equals("0")) {
//                        udprorunlogLine4Obj.remove("UDPRORUNLOGLINE4ID");
//                    }
//                    udprorunlogLine4Array.put(udprorunlogLine4Obj);
//                }
//                jsonObject.put("UDPRORUNLOGLINE4", udprorunlogLine4Array);
//            }
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(object);
//            jsonObject.put("relationShip", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject.toString();
//    }
//
//    /**
//     * 封装问题联络单数据
//     *
//     * @return
//     */
//    public static String UdfeedbackToJson(Udfeedback udfeedback) {
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//        try {
//            Field[] field = udfeedback.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//            for (int j = 0; j < field.length; j++) {
//                field[j].setAccessible(true);
//                String name = field[j].getName();//获取属性的名字
//                Method getOrSet = null;
//                try {
//                    getOrSet = udfeedback.getClass().getMethod("get" + name);
//                    Object value = null;
//                    value = getOrSet.invoke(udfeedback);
//                    if (value != null) {
//                        jsonObject.put(name, value + "");
//                    }
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//            JSONObject object = new JSONObject();
//            object.put("", "");
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(object);
//            jsonObject.put("relationShip", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject.toString();
//    }
//
//    /**
//     * 封装出差报告数据
//     *
//     * @return
//     */
//    public static String tripPortToJson(UdTriprePort udTriprePort) {
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//        try {
//            Field[] field = udTriprePort.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//            for (int j = 0; j < field.length; j++) {
//                field[j].setAccessible(true);
//                String name = field[j].getName();//获取属性的名字
//                Method getOrSet = null;
//                try {
//                    getOrSet = udTriprePort.getClass().getMethod("get" + name);
//                    Object value = null;
//                    value = getOrSet.invoke(udTriprePort);
//                    if (value != null) {
//                        jsonObject.put(name, value + "");
//                    }
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//            JSONObject object = new JSONObject();
//            object.put("", "");
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(object);
//            jsonObject.put("relationShip", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject.toString();
//    }
//    /**
//     * 封装故障提报单数据
//     *
//     * @return
//     */
//    public static String UdreportToJson(Udreport udreport) {
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//        try {
//            Field[] field = udreport.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//            for (int j = 0; j < field.length; j++) {
//                field[j].setAccessible(true);
//                String name = field[j].getName();//获取属性的名字
//                Method getOrSet = null;
//                try {
//                    getOrSet = udreport.getClass().getMethod("get" + name);
//                    Object value = null;
//                    value = getOrSet.invoke(udreport);
//                    if (value != null) {
//                        jsonObject.put(name, value + "");
//                    }
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//            JSONObject object = new JSONObject();
//            object.put("", "");
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(object);
//            jsonObject.put("relationShip", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject.toString();
//    }
//
//    /**
//     * 封装质量问题反馈单数据
//     *
//     * @return
//     */
//    public static String UdqtyformToJson(Udqtyform udqtyform) {
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//        try {
//            Field[] field = udqtyform.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//            for (int j = 0; j < field.length; j++) {
//                field[j].setAccessible(true);
//                String name = field[j].getName();//获取属性的名字
//                Method getOrSet = null;
//                try {
//                    if (!name.equals("isnew")) {
//                        getOrSet = udqtyform.getClass().getMethod("get" + name);
//                        Object value = null;
//                        value = getOrSet.invoke(udqtyform);
//                        if (value != null) {
//                            jsonObject.put(name, value + "");
//                        }
//                    }
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//            JSONObject object = new JSONObject();
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(object);
//            jsonObject.put("relationShip", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.e("封装质量问题反馈单", jsonObject.toString());
//        return jsonObject.toString();
//    }
//
//    /**
//     * 封装巡检单数据
//     *
//     * @return
//     */
//    public static String UdinspoToJson(Udinspo udinspo, ArrayList<Udinsproject> udinsprojects) {
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//        try {
//            Field[] field = udinspo.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//            for (int j = 0; j < field.length; j++) {
//                field[j].setAccessible(true);
//                String name = field[j].getName();//获取属性的名字
//                Method getOrSet = null;
//                try {
//                    if (!name.equals("id")&&!name.equals("isUpdate")&&!name.equals("belong")) {
//                        getOrSet = udinspo.getClass().getMethod("get" + name);
//                        Object value = null;
//                        value = getOrSet.invoke(udinspo);
//                        if (value != null) {
//                            jsonObject.put(name, value + "");
//                        }
//                    }
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//            JSONObject object = new JSONObject();
//            if (udinsprojects != null && udinsprojects.size() != 0) {
//                object.put("UDSTOCKLINE", "");
//                JSONArray udinsprojectArray = new JSONArray();
//                JSONObject udinsprojectObj;
//                for (int i = 0; i < udinsprojects.size(); i++) {
//                    udinsprojectObj = new JSONObject();
//                    Field[] field1 = udinsprojects.get(i).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                    for (int j = 0; j < field1.length; j++) {
//                        field1[j].setAccessible(true);
//                        String name = field1[j].getName();//获取属性的名字
//                        Method getOrSet = null;
//                        try {
//                            getOrSet = udinsprojects.get(i).getClass().getMethod("get" + name);
//                            Object value = null;
//                            value = getOrSet.invoke(udinsprojects.get(i));
//                            if (value != null) {
//                                udinsprojectObj.put(name, value + "");
//                            }
//                        } catch (NoSuchMethodException e) {
//                            e.printStackTrace();
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (udinsprojectObj.get("TYPE").equals("add") && udinsprojectObj.has("UDPRORUNLOGLINE4ID") && udinsprojectObj.get("UDPRORUNLOGLINE4ID").equals("0")) {
//                        udinsprojectObj.remove("UDPRORUNLOGLINE4ID");
//                    }
//                    udinsprojectArray.put(udinsprojectObj);
//                }
//                jsonObject.put("UDINSPROJECT", udinsprojectArray);
//            }
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(object);
//            jsonObject.put("relationShip", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject.toString();
//    }
//
//    /**
//     * 封装运行记录数据
//     *
//     * @return
//     */
//    public static String UdrunlogrToJson(Udrunlogr udprorunlog, ArrayList<Udrunliner> udprorunlogLine1s) {
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//        try {
//            Field[] field = udprorunlog.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//            for (int j = 0; j < field.length; j++) {
//                field[j].setAccessible(true);
//                String name = field[j].getName();//获取属性的名字
//                Method getOrSet = null;
//                try {
//                    if (!name.equals("isnew")) {
//                        getOrSet = udprorunlog.getClass().getMethod("get" + name);
//                        Object value = null;
//                        value = getOrSet.invoke(udprorunlog);
//                        if (value != null && !name.equals("BRANCH")) {
//                            jsonObject.put(name, value + "");
//                        }
//                    }
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//            JSONObject object = new JSONObject();
//            if (udprorunlogLine1s != null && udprorunlogLine1s.size() != 0) {
//                object.put("UDRUNLINER", "");
//                JSONArray udprorunlogline1Array = new JSONArray();
//                JSONObject udprorunlogline1Obj;
//                for (int i = 0; i < udprorunlogLine1s.size(); i++) {
//                    udprorunlogline1Obj = new JSONObject();
//                    Field[] field1 = udprorunlogLine1s.get(i).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                    for (int j = 0; j < field1.length; j++) {
//                        field1[j].setAccessible(true);
//                        String name = field1[j].getName();//获取属性的名字
//                        Method getOrSet = null;
//                        try {
//                            if (!name.equals("isUpload")) {
//                                getOrSet = udprorunlogLine1s.get(i).getClass().getMethod("get" + name);
//                                Object value = null;
//                                value = getOrSet.invoke(udprorunlogLine1s.get(i));
//                                if (value != null) {
//                                    udprorunlogline1Obj.put(name, value + "");
//                                }
//                            }
//                        } catch (NoSuchMethodException e) {
//                            e.printStackTrace();
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    udprorunlogline1Obj.put("FORAPP","1");
//                    udprorunlogline1Obj.put("DESCRIPTION",udprorunlog.LOGNUM);
//                    if (udprorunlogline1Obj.get("TYPE").equals("add") && udprorunlogline1Obj.has("UDRUNLINERID") && udprorunlogline1Obj.get("UDRUNLINERID").equals("0")) {
//                        udprorunlogline1Obj.remove("UDRUNLINERID");
//                    }
//                    udprorunlogline1Array.put(udprorunlogline1Obj);
//                }
//                jsonObject.put("UDRUNLINER", udprorunlogline1Array);
//            }
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(object);
//            jsonObject.put("relationShip", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject.toString();
//    }
//
//
//    /**
//     * 封装库存盘点数据
//     *
//     * @return
//     */
//    public static String UdstockToJson(Udstock udstock, ArrayList<Udstockline> udstocklines) {
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//        try {
//            Field[] field = udstock.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//            for (int j = 0; j < field.length; j++) {
//                field[j].setAccessible(true);
//                String name = field[j].getName();//获取属性的名字
//                Method getOrSet = null;
//                try {
//                    getOrSet = udstock.getClass().getMethod("get" + name);
//                    Object value = null;
//                    value = getOrSet.invoke(udstock);
//                    if (value != null) {
//                        jsonObject.put(name, value + "");
//                    }
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//            JSONObject object = new JSONObject();
//            if (udstocklines != null && udstocklines.size() != 0) {
//                object.put("UDSTOCKLINE", "");
//                JSONArray wpmaterialsArray = new JSONArray();
//                JSONObject wpmaterialsObj;
//                for (int i = 0; i < udstocklines.size(); i++) {
//                    wpmaterialsObj = new JSONObject();
//                    Field[] field1 = udstocklines.get(i).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                    for (int j = 0; j < field1.length; j++) {
//                        field1[j].setAccessible(true);
//                        String name = field1[j].getName();//获取属性的名字
//                        Method getOrSet = null;
//                        try {
//                            getOrSet = udstocklines.get(i).getClass().getMethod("get" + name);
//                            Object value = null;
//                            value = getOrSet.invoke(udstocklines.get(i));
//                            if (value != null) {
//                                wpmaterialsObj.put(name, value + "");
//                            }
//                        } catch (NoSuchMethodException e) {
//                            e.printStackTrace();
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (wpmaterialsObj.get("TYPE").equals("add") && wpmaterialsObj.has("UDSTOCKLINEID") && wpmaterialsObj.get("UDSTOCKLINEID").equals("0")) {
//                        wpmaterialsObj.remove("UDSTOCKLINEID");
//                    }
//                    wpmaterialsArray.put(wpmaterialsObj);
//                }
//                jsonObject.put("UDSTOCKLINE", wpmaterialsArray);
//            }
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(object);
//            jsonObject.put("relationShip", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject.toString();
//    }
//
//    /**
//     * 封装车辆维修数据
//     *
//     * @return
//     */
//    public static String udcarmainlogToJson(Udcarmainlog udcarmainlog) {
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//        try {
//            Field[] field = udcarmainlog.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//            for (int j = 0; j < field.length; j++) {
//                field[j].setAccessible(true);
//                String name = field[j].getName();//获取属性的名字
//                Method getOrSet = null;
//                try {
//                    getOrSet = udcarmainlog.getClass().getMethod("get" + name);
//                    Object value = null;
//                    value = getOrSet.invoke(udcarmainlog);
//                    if (value != null && !name.equals("BRANCH")) {
//                        jsonObject.put(name, value + "");
//                    }
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//            JSONObject object = new JSONObject();
//            object.put("", "");
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(object);
//            jsonObject.put("relationShip", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject.toString();
//    }
//
    /**
     * 故障提报单*
     */
    public static ArrayList<UDFAULTREPORT> parsingUDFAULTREPORT(Context ctx, String data) {
        ArrayList<UDFAULTREPORT> list = null;
        UDFAULTREPORT udfaultreport = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<UDFAULTREPORT>();
            for (int i = 0; i < jsonArray.length(); i++) {
                udfaultreport = new UDFAULTREPORT();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = udfaultreport.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = udfaultreport.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(udfaultreport);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = udfaultreport.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(udfaultreport, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(udfaultreport);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 工作申请
     */
    public static ArrayList<UDWORKAPPLY> parsingUdworkapply(Context ctx, String data) {
        ArrayList<UDWORKAPPLY> list = null;
        UDWORKAPPLY udworkapply = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<UDWORKAPPLY>();
            for (int i = 0; i < jsonArray.length(); i++) {
                udworkapply = new UDWORKAPPLY();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = udworkapply.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = udworkapply.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(udworkapply);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = udworkapply.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(udworkapply, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(udworkapply);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 接收*
     */
    public static ArrayList<PO> parsingPO(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<PO> list = null;
        PO item = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<PO>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                item = new PO();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = item.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = item.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(item);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = item.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(item, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(item);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
//
//    /**
//     * 巡检项目*
//     */
//    public static ArrayList<Udinsproject> parsingUdinsproject(Context ctx, String data) {
//        ArrayList<Udinsproject> list = null;
//        Udinsproject udinsproject = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Udinsproject>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udinsproject = new Udinsproject();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = udinsproject.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udinsproject.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udinsproject);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udinsproject.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udinsproject, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(udinsproject);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//    /**
//     * 巡检单*
//     */
//    public static ArrayList<Udrunlogr> parsingUdrunlogr(Context ctx, String data) {
//        ArrayList<Udrunlogr> list = null;
//        Udrunlogr udrunlogr = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Udrunlogr>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udrunlogr = new Udrunlogr();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = udrunlogr.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udrunlogr.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udrunlogr);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udrunlogr.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udrunlogr, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(udrunlogr);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 行驶记录*
//     */
//    public static ArrayList<Udcardrivelog> parsingUdcardrivelog(Context ctx, String data) {
//        ArrayList<Udcardrivelog> list = null;
//        Udcardrivelog udcardrivelog = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Udcardrivelog>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udcardrivelog = new Udcardrivelog();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = udcardrivelog.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udcardrivelog.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udcardrivelog);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udcardrivelog.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udcardrivelog, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(udcardrivelog);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//    /**
//     * 加油记录*
//     */
//    public static ArrayList<Udcarfuelcharge> parsingUdcarfuelcharge(Context ctx, String data) {
//        ArrayList<Udcarfuelcharge> list = null;
//        Udcarfuelcharge udcarfuelcharge = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Udcarfuelcharge>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udcarfuelcharge = new Udcarfuelcharge();
//                jsonObject = jsonArray.getJSONObject(i);
//                Log.e("加油卡台账", "data=" + jsonObject);
//                Field[] field = udcarfuelcharge.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udcarfuelcharge.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udcarfuelcharge);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udcarfuelcharge.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udcarfuelcharge, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(udcarfuelcharge);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//    /**
//     *解析加油卡台账
//     *
//     */
//    public static ArrayList<GreaseCard>parsingGreaseCard(Context ctx,String data){
//        ArrayList<GreaseCard> list = null;
//        GreaseCard greaseCard = null;
//        try {
//            JSONObject jsonObjectData= new JSONObject(data);
//
//            JSONArray jsonArray = new JSONArray(jsonObjectData.getString("resultlist"));
//            JSONObject jsonObject;
//            list = new ArrayList<GreaseCard>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                greaseCard = new GreaseCard();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = greaseCard.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = greaseCard.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(greaseCard);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = greaseCard.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(greaseCard, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(greaseCard);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//    /**
//     * 维修记录*
//     */
//    public static ArrayList<Udcarmainlog> parsingUdcarmainlog(Context ctx, String data) {
//        ArrayList<Udcarmainlog> list = null;
//        Udcarmainlog udcarmainlog = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Udcarmainlog>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                udcarmainlog = new Udcarmainlog();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = udcarmainlog.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = udcarmainlog.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(udcarmainlog);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = udcarmainlog.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(udcarmainlog, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(udcarmainlog);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//
//    /**
//     * 封装车辆加油数据
//     *
//     * @return
//     */
//    public static String udcarfuelchargeToJson(Udcarfuelcharge udcarfuelcharge) {
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//        try {
//            Field[] field = udcarfuelcharge.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//            for (int j = 0; j < field.length; j++) {
//                field[j].setAccessible(true);
//                String name = field[j].getName();//获取属性的名字
//                Method getOrSet = null;
//                try {
//                    getOrSet = udcarfuelcharge.getClass().getMethod("get" + name);
//                    Object value = null;
//                    value = getOrSet.invoke(udcarfuelcharge);
//                    if (value != null && !name.equals("BRANCH")) {
//                        jsonObject.put(name, value + "");
//                    }
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//            JSONObject object = new JSONObject();
//            object.put("", "");
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(object);
//            jsonObject.put("relationShip", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject.toString();
//    }
//
//    /**
//     * 封装车辆行驶数据
//     *
//     * @return
//     */
//    public static String udcardrivelogToJson(Udcardrivelog udcardrivelog) {
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//        try {
//            Field[] field = udcardrivelog.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//            for (int j = 0; j < field.length; j++) {
//                field[j].setAccessible(true);
//                String name = field[j].getName();//获取属性的名字
//                Method getOrSet = null;
//                try {
//                    getOrSet = udcardrivelog.getClass().getMethod("get" + name);
//                    Object value = null;
//                    value = getOrSet.invoke(udcardrivelog);
//                    if (value != null && !name.equals("BRANCH")) {
//                        jsonObject.put(name, value + "");
//                    }
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//            JSONObject object = new JSONObject();
//            object.put("", "");
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(object);
//            jsonObject.put("relationShip", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject.toString();
//    }
//
//
//
//    /**
//     * 附件类*
//     */
//    public static ArrayList<Doclinks> parsingDoclinks(Context ctx, String data) {
//        Log.i(TAG,"ddddata="+data);
//        ArrayList<Doclinks> list = null;
//        Doclinks doclinks = null;
//        try {
//            JSONArray jsonArray = new JSONArray(data);
//            JSONObject jsonObject;
//            list = new ArrayList<Doclinks>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                doclinks = new Doclinks();
//                jsonObject = jsonArray.getJSONObject(i);
//                Field[] field = doclinks.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
//                for (int j = 0; j < field.length; j++) {     //遍历所有属性
//                    field[j].setAccessible(true);
//                    String name = field[j].getName();    //获取属性的名字
//                    Log.i(TAG, "name=" + name);
//                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
//                        try {
//                            // 调用getter方法获取属性值
//                            Method getOrSet = doclinks.getClass().getMethod("get" + name);
//                            Object value = getOrSet.invoke(doclinks);
//                            if (value == null) {
//                                //调用setter方法设属性值
//                                Class[] parameterTypes = new Class[1];
//                                parameterTypes[0] = field[j].getType();
//                                getOrSet = doclinks.getClass().getDeclaredMethod("set" + name, parameterTypes);
//                                getOrSet.invoke(doclinks, jsonObject.getString(name));
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                list.add(doclinks);
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }

    public static int getListSize(String data){
        int length = 0;
        try {
            JSONArray list = new JSONArray(data);
            length = list.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return length;
    }
    public static String parsingINVUSE(INVUSEEntity invuseEntity, List<INVUSELINE> list) throws JSONException {
        Gson g = new GsonBuilder().serializeNulls().create();
        String json = g.toJson(invuseEntity);
        String jsonArray = g.toJson(list);
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray1 = new JSONArray(jsonArray);
        if (!list.isEmpty()){jsonObject.put("INVUSELINE",jsonArray1);}
        JSONObject jsonObject1 = new JSONObject();
        if (!list.isEmpty()){jsonObject1.put("INVUSELINE","");}else {
            jsonObject1.put("","");
        }
        JSONArray jsonArray2 = new JSONArray();
        jsonArray2.put(jsonObject1);
        jsonObject.put("relationShip",jsonArray2);
        JsonObject  jsonObject2 = new JsonObject();
        String ret = jsonObject.toString().replace("null","\"\"");
        return ret;
    }
    public static String parsingWork(WORKORDER workorder, List<WPMATERIAL> list) throws JSONException {
        Gson g = new GsonBuilder().serializeNulls().create();
        String json = g.toJson(workorder);
        String jsonArray = g.toJson(list);
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray1 = new JSONArray(jsonArray);
        if (!list.isEmpty()){jsonObject.put("WPITEM",jsonArray1);}
        JSONObject jsonObject1 = new JSONObject();
        if (!list.isEmpty()){jsonObject1.put("WPITEM","");}else {
            jsonObject1.put("","");
        }
        JSONArray jsonArray2 = new JSONArray();
        jsonArray2.put(jsonObject1);
        jsonObject.put("relationShip",jsonArray2);
        JsonObject  jsonObject2 = new JsonObject();
        String ret = jsonObject.toString().replace("null","\"\"");
        return ret;
    }
    public static String parsingINVUSE(INVUSEEntity workOrder) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            Field[] field = workOrder.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
            for (int j = 0; j < field.length; j++) {
                field[j].setAccessible(true);
                String name = field[j].getName();//获取属性的名字
                Method getOrSet = null;
                try {
                    if (!name.equals("isnew") && !name.equals("INVUSENUM")) {
                        getOrSet = workOrder.getClass().getMethod("get" + name);
                        Object value = null;
                        value = getOrSet.invoke(workOrder);
                        if (value != null) {
                            jsonObject.put(name, value + "");
                        }
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            JSONObject object = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(object);
            jsonObject.put("relationShip", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
    public static String parsingINVUSENUM(String data){
        try {
            List<Integer> list = new ArrayList();
            JSONArray jsonArray = new JSONArray(data);
            int j = 0;
            int y = 0;
            for (int i = 0; i <jsonArray.length();i++){
               JSONObject jsonObject =  jsonArray.getJSONObject(i);
                if (jsonObject.has("INVUSENUM")){
                    String str = jsonObject.get("INVUSENUM").toString().substring(2);
                    j = Integer.parseInt(str);
                    if(y<j){
                        y = j;
                    }
                }
            }
            return y+"";
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static ArrayList<MATRECTRANS> parsingMATRECTRANS(Context context, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<MATRECTRANS> list = null;
        MATRECTRANS matrectrans = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<MATRECTRANS>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                matrectrans = new MATRECTRANS();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = matrectrans.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = matrectrans.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(matrectrans);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = matrectrans.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(matrectrans, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(matrectrans);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public  static  String submitUdstocklineData(UDSTOCKLINE udstockline){
       try {
           Gson g = new GsonBuilder().serializeNulls().create();
           String json = g.toJson(udstockline);
           JSONObject jsonObject = new JSONObject(json);
           JSONArray jsonArray = new JSONArray();
           JSONObject  jsonObject1 = new JSONObject();
           jsonObject1.put("","");
           jsonArray.put(jsonObject1);
           jsonObject.put("relationShip",jsonArray);
           json = jsonObject.toString(). replace("null","\"\"");
           Log.e("盘点", "doInBackground: "+ json );
           return json;
       }catch (JSONException e){
           e.printStackTrace();
           return "";
       }
    }
    public static ArrayUtil<INVRESERVE> parsingINVRESERVE(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayUtil<INVRESERVE> list = null;
        INVRESERVE asset = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayUtil<INVRESERVE>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                asset = new INVRESERVE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = asset.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = asset.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(asset);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = asset.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(asset, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(asset);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
    public static ArrayList<WPITEM> parsingWPITEM(Context ctx, String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<WPITEM> list = null;
        WPITEM wpmaterial = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<WPITEM>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                wpmaterial = new WPITEM();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = wpmaterial.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = wpmaterial.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(wpmaterial);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = wpmaterial.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(wpmaterial, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(wpmaterial);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


}