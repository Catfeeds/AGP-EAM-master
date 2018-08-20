package com.hsk.hxqh.agp_eam.webserviceclient;

import android.content.Context;
import android.util.Log;

import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.config.Constants;
import com.hsk.hxqh.agp_eam.model.WebResult;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import javax.security.auth.login.LoginException;

/**
 * Created by think on 2015/8/11.
 */
public class AndroidClientService {
    private static final String TAG = "AndroidClientService";
    public static String NAMESPACE = "http://www.catc.com/maximo";//http://www.ibm.com/maximohttp://www.catc.com/maximo
    public static String url = "";
    public static int timeOut = 60000;

    public AndroidClientService() {
    }

    public AndroidClientService(String url) {
        this.url = url;
    }

    public void setTimeOut(int seconds) {
        this.timeOut = seconds * 1000;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 开始工作流
     * context 上下文
     * processname 过程名
     * keyValue 对应的
     *
     * @return
     */
    public static WebResult startwf(Context context, String processname, String mbo, String keyValue, String key, String loginid) {

        String url = AccountUtils.getIpAddress(context) + Constants.WORK_FLOW_URL+"?WSDL";
        Log.e("发送工作流",url);
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "wfservicestartWF2");
        soapReq.addProperty("processname", processname);//流程名称
        soapReq.addProperty("mbo", mbo);//如工单WORKORDER
        soapReq.addProperty("keyValue", keyValue);//对应的表ID的值，如工单需要传送WONUM的值，采购申请prnum的值
        soapReq.addProperty("key", key);//对应的表ID，如工单：wonum，采购申请，prnum
        soapReq.addProperty("loginid",loginid);//用户id
        Log.e("发送工作流",soapReq.toString());
        soapEnvelope.bodyOut = soapReq;
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(url, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
            Log.e("发送工作流",soapEnvelope.toString());
        } catch (IOException | XmlPullParserException e) {
            return null;
        }
        String obj = null;
        WebResult result = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            result = JsonUtils.parsingStartWF(obj, key);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return result;
    }

    /**
     * 审批工作流
     *
     * @return
     */
    public static WebResult approve(Context context, String processname, String mbo, String keyValue, String key, String zx, String desc,String loginid) {
        String url = AccountUtils.getIpAddress(context) + Constants.WORK_FLOW_URL;
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "wfservicewfGoOn");
        soapReq.addProperty("processname", processname);//流程名称
        soapReq.addProperty("mboName", mbo);//表名
        soapReq.addProperty("keyValue",keyValue);//对应的表ID的值，如工单需要传送WONUM的值
        soapReq.addProperty("key", key);//如工单：wonum
        soapReq.addProperty("zx", zx);//审批的结果，1为审批通过，0为审批不通过
        if (!desc.equals("")) {
            soapReq.addProperty("desc", desc);//审批意见
        }
        soapReq.addProperty("loginid",loginid);//用户id
        soapEnvelope.bodyOut = soapReq;
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(url, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOException",e.toString());
            Log.i(TAG, "111111");
            return null;
        } catch (XmlPullParserException e) {
            Log.i(TAG, "22222");
            return null;
        }
        String obj = null;
        WebResult result = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            result = JsonUtils.parsingGoOn(obj, key,keyValue);
        } catch (SoapFault soapFault) {
            Log.i(TAG, "ssssss");
            return null;
        }catch (Exception e){
            return null;
        }
        Log.e(TAG, "approve: " +result.toString());
        return result;
    }

    /**
     * 工单新增方法
     *
     * @param json
     * @return
     */
    public static WebResult InsertWO(Context context, String json, String mboObjectName, String mboKey, String personId, String url) {

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mobileserviceInsertMbo");
        soapReq.addProperty("json", json);//新增信息json
        soapReq.addProperty("flag", "1");//默认为1
        soapReq.addProperty("mboObjectName", mboObjectName);//表名
        soapReq.addProperty("mboKey", mboKey);//表主键 如：WONUM
        soapReq.addProperty("personId", personId);//用户id，登录返回信息得到
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException | XmlPullParserException e) {
//            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingInsertWO(obj, mboKey);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 通用修改
     */
    public static WebResult UpdateWO(Context context, String json, String mboObjectName, String mboKey, String mboKeyValue, String url) {
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mobileserviceUpdateMbo");
        soapReq.addProperty("json", json);//参数
        soapReq.addProperty("mboObjectName", mboObjectName);//表名
        soapReq.addProperty("mboKey", mboKey);//表主键 如：WONUM
        soapReq.addProperty("mboKeyValue", mboKeyValue);//表主键值
        soapReq.addProperty("flag","1");
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(AccountUtils.getIpAddress(context)+ url, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException | XmlPullParserException e) {
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            webResult = JsonUtils.parsingInsertWO(obj, mboKey);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

//    /**
//     * 工单删除
//     */
//    public static WebResult DeleteWO(String wonum, String userid, String url) {
//        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        soapEnvelope.implicitTypes = true;
//        soapEnvelope.dotNet = true;
//        SoapObject soapReq = new SoapObject(NAMESPACE, "workorderserviceWO03ByDelete");
//        soapReq.addProperty("wonum", wonum);
//        soapReq.addProperty("userid",userid);
//        soapEnvelope.setOutputSoapObject(soapReq);
//        HttpTransportSE httpTransport = new HttpTransportSE(url);
//        try {
//            httpTransport.call("urn:action", soapEnvelope);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            return null;
//        }
//        String obj = null;
//        WebResult workResult = null;
//        try {
//            obj = soapEnvelope.getResponse().toString();
//            workResult = JsonUtils.parsinDeleteWo(obj);
//        } catch (SoapFault soapFault) {
//            soapFault.printStackTrace();
//        }
//        return workResult;
//    }


    /**
     * 通过webservice实现图片上传
     *
     * @param imageBuffer
     */
    /**
     * 通用修改
     */
    public static String connectWebService(Context context, String filename, String image, String ownertable, String ownerid, String url) {

        Log.i(TAG, "filename=" + filename + ",ownertable=" + ownertable + ",ownerid=" + ownerid);
        Log.i(TAG, "url=" + url);
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mobileserviceuploadImage");
        soapReq.addProperty("filename", filename);//文件名
        soapReq.addProperty("image", image);//图片Json
        soapReq.addProperty("ownertable", ownertable);//表名
        soapReq.addProperty("ownerid", ownerid);//表主键值
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException | XmlPullParserException e) {
            return null;
        }
        String obj = null;
        String webResult = null;
        try {
            webResult = soapEnvelope.getResponse().toString();
            Log.i(TAG, "webResult=" + webResult);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 通用修改
     */
    public static String permissionWebService(Context context,String name) {

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mobileservicegetLocaPermission");
        soapReq.addProperty("user", AccountUtils.getuserName(context));//新增信息json
        //soapReq.addProperty("user", "A01934");//新增信息json
        soapEnvelope.setOutputSoapObject(soapReq);

        HttpTransportSE httpTransport = new HttpTransportSE(AccountUtils.getIpAddress(context)+"/meaweb/services/MOBILESERVICE", timeOut);

        try {

            httpTransport.call("urn:action", soapEnvelope);

        } catch (IOException | XmlPullParserException e) {

            return null;
        }

        String obj = null;

        try {

            obj = soapEnvelope.getResponse().toString();

            Log.i("库存查询", "obj=" + obj);

        } catch (SoapFault soapFault) {

            soapFault.printStackTrace();

        }
        return obj;
    }
    public static String insertInvuseLine(Context context, String json){
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "appserviceMaterialInv");
        soapReq.addProperty("mrJson", json);//参数
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(AccountUtils.getIpAddress(context)+Constants.INVUESLINE_URL, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = new WebResult();
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }
    public static String insertWPITEM(Context context, String json){
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "appserviceMatReqWpItem");
        soapReq.addProperty("mrWpItemJSON", json);//参数
        soapEnvelope.setOutputSoapObject(soapReq);
            HttpTransportSE httpTransport = new HttpTransportSE(AccountUtils.getIpAddress(context)+Constants.INVUESLINE_URL, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return "";
        }
        String obj = "";
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }
    public static String insertWO(Context context, String json){
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "appserviceMatReqWO");
        soapReq.addProperty("mrWOJSON", json);//参数
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(AccountUtils.getIpAddress(context)+Constants.INVUESLINE_URL, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return "";
        }
        String obj = "";
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }
    public static String goodsRecSel(Context context, String ponum,String wareHouse,String status){
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "appservicegoodsRecSel");
        soapReq.addProperty("PONUM", ponum);//ponum
        soapReq.addProperty("WAREHOUSE",wareHouse );//
        soapReq.addProperty("STATUS", status);//参数
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(AccountUtils.getIpAddress(context)+Constants.INVUESLINE_URL, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return "";
        }
        String obj = "";
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }
    public static String getMatDate(Context context, String invusenum,String fromstoreloc,String status){
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "appservicematRecSel");
        soapReq.addProperty("INVUSENUM", invusenum);//ponum
        soapReq.addProperty("FROMSTORELOC",fromstoreloc );//
        soapReq.addProperty("STATUS", status);//参数
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(AccountUtils.getIpAddress(context)+Constants.INVUESLINE_URL, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return "";
        }
        String obj = "";
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }
    public static String insertMa(Context context, String json){
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "appserviceMatRecIface");
        soapReq.addProperty("mrecJSON", json);//参数
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(AccountUtils.getIpAddress(context)+Constants.INVUESLINE_URL, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = new WebResult();
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }
}
