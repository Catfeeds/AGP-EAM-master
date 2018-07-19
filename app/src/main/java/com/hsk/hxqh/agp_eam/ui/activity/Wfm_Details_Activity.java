package com.hsk.hxqh.agp_eam.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnEditClickL;
import com.flyco.dialog.widget.NormalEditTextDialog;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.WFASSIGNMENT;
import com.hsk.hxqh.agp_eam.model.WebResult;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;
import com.hsk.hxqh.agp_eam.webserviceclient.AndroidClientService;

import java.util.ArrayList;

/**
 * 流程审批详情
 */
public class Wfm_Details_Activity extends BaseActivity {

    private static final String TAG = "Wfm_Details_Activity";

    /**
     * 标题*
     */
    private TextView titleView;
    /**
     * 返回按钮*
     */
    private ImageView backImageView;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    /**
     * 界面信息显示*
     */
    private TextView appText; //应用程序名
    private TextView descriptionText; //描述
    private TextView assigncodeText; //任务分配人
    //    private TextView duedateText; //到期日期
//    private TextView processnameText; //过程名称
//    private TextView roleidText; //任务角色
    private TextView startdateText; //流程发起日期

    private Button details;//查看详情
    private LinearLayout detailslayout;
    private Button approve;
    private Button nopass;
    private EditText option;

    private ProgressDialog mProgressDialog;
    private String result;
    private WFASSIGNMENT wfm; //流程审批


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wfm_details);
        initData();
        findViewById();
        initView();
    }

    /**
     * 获取初始话数据*
     */
    private void initData() {
        wfm = (WFASSIGNMENT) getIntent().getSerializableExtra("wfassignment");
    }

    @Override
    protected void findViewById() {
        titleView = (TextView) findViewById(R.id.menu_title);
        backImageView = (ImageView) findViewById(R.id.menu_id);

        appText = (TextView) findViewById(R.id.wfm_app_text);
        descriptionText = (TextView) findViewById(R.id.wfm_description_text);
        assigncodeText = (TextView) findViewById(R.id.wfm_assigncode_title);
//        duedateText = (TextView) findViewById(R.id.wfm_duedate_text);
//        processnameText = (TextView) findViewById(R.id.wfm_processname_text);
//        roleidText = (TextView) findViewById(R.id.wfm_roleid_text);
        startdateText = (TextView) findViewById(R.id.wfm_startdate_text);
//        details = (Button) findViewById(R.id.details);
//        detailslayout = (LinearLayout) findViewById(R.id.details_layout);
        approve = (Button) findViewById(R.id.wfm_approve_pass);
        option = (EditText)findViewById(R.id.layout_option_id);
        nopass = (Button)findViewById(R.id.wfm_approve_notpass);

    }

    @Override
    protected void initView() {
        titleView.setText(getString(R.string.title_activity_wfm));
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListenrer);

//        if (wfm.getOWNERTABLE()!=null&&!wfm.getOWNERTABLE().equals("WORKORDER")
//                &&!wfm.getOWNERTABLE().equals("DEBUGWORKORDER")&&!wfm.getOWNERTABLE().equals("UDSTOCK")
//                &&!wfm.getOWNERTABLE().equals("UDFEEDBACK")&&!wfm.getOWNERTABLE().equals("UDREPORT")
//                &&!wfm.getOWNERTABLE().equals("UDINSPO")){
//            detailslayout.setVisibility(View.INVISIBLE);
//        }

        if (wfm != null) {
            appText.setText(wfm.getAPP() == null ? "" : wfm.getAPP());
            descriptionText.setText(wfm.getDESCRIPTION() == null ? "" : wfm.getDESCRIPTION());
            assigncodeText.setText(wfm.getASSIGNCODE() == null ? "" : wfm.getASSIGNCODE());
//            duedateText.setText(wfm.getDUEDATE() == null ? "" : wfm.getDUEDATE());
//            processnameText.setText(wfm.getPROCESSNAME() == null ? "" : wfm.getPROCESSNAME());
//            roleidText.setText(wfm.getROLEID() == null ? "" : wfm.getROLEID());
            startdateText.setText(wfm.getSTARTDATE() == null ? "" : wfm.getSTARTDATE());
//            if (wfm.getUDASSIGN01() != null && !wfm.getUDASSIGN01().equals("")) {
//                details.setText("查看" + wfm.getUDASSIGN01() + "详情");
//            }
        }

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
//        details.setOnClickListener(detailsOnClickListener);
        approve.setOnClickListener(approveOnClickListener);
        nopass.setOnClickListener(nopassOnClickListenrer);
    }

    /*
     *通过按钮
     * */
    private View.OnClickListener approveOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            wfgoon("1",option.getText().toString());
        }
    };
    /*
     *通过按钮
     * */
    private View.OnClickListener nopassOnClickListenrer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            wfgoon("0",option.getText().toString());
        }
    };

    /**
     * 返回按钮*
     */
    private View.OnClickListener backImageViewOnClickListenrer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


//    private View.OnClickListener detailsOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            switch (wfm.getOWNERTABLE()){
//                case "WORKORDER"://工单
//                    showProgressDialog("数据查询中...");
//                    String worktype = GetWorkTypeUtil.getWorkType(wfm.getPROCESSNAME());
//                    getWorkOrderData(worktype);
//                    break;
//                case "DEBUGWORKORDER"://调试工单
//                    showProgressDialog("数据查询中...");
//                    getDebugWorkOrderData();
//                    break;
//                case "UDSTOCK"://库存盘点
//                    showProgressDialog("数据查询中...");
//                    getUdstockData();
//                    break;
//                case "UDFEEDBACK"://问题联络单
//                    showProgressDialog("数据查询中...");
//                    getUdfeedbackData();
//                    break;
//                case "UDREPORT"://故障提报单
//                    showProgressDialog("数据查询中...");
//                    getUdreportData();
//                    break;
//                case "UDINSPO"://巡检单
//                    getUdinspoData();
//                    showProgressDialog("数据查询中...");
//                    break;
//                default:
//
//                    String content_url = AccountUtils.getIpAddress(Wfm_Details_Activity.this)
//                            +"/maximo/ui/?event=loadapp&value="+wfm.getAPP()+"&uniqueid="+wfm.getOWNERID()+"";
//
//                    Intent intent = new Intent();
//
//                    intent.putExtra("wfm",wfm);
//
//                    intent.putExtra("url",content_url);
//
//                    intent.setClass(Wfm_Details_Activity.this,Wfm_webview_Activity.class);
//
//                    startActivity(intent);
//
//                    break;
//            }
//        }
//    };
//
 /*   private View.OnClickListener approveOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditDialog();
        }
    };

    private void EditDialog() {//输入审核意见
        final NormalEditTextDialog dialog = new NormalEditTextDialog(Wfm_Details_Activity.this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.isTitleShow(true)//
                .title("审批工作流")
                .btnNum(3)
                .content("通过")//
                .btnText("取消", "通过", "不通过")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)
                .show();

        dialog.setOnBtnClickL(
                new OnBtnEditClickL() {
                    @Override
                    public void onBtnClick(String text) {
                        dialog.dismiss();
                    }
                },
                new OnBtnEditClickL() {
                    @Override
                    public void onBtnClick(String text) {
                        wfgoon("1", text);
                        Log.e("审批", "通过");
                        dialog.dismiss();
                    }
                },
                new OnBtnEditClickL() {
                    @Override
                    public void onBtnClick(String text) {
                        Log.e("审批","不通过");
                        wfgoon("0", text.equals("通过") ? text:"不通过" );
                        dialog.dismiss();
                    }
                }
        );
    }*/

    /**
     * 审批工作流
     *
     * @param zx
     */
    private void wfgoon(final String zx, final String desc) {

/*        mProgressDialog = ProgressDialog.show(Wfm_Details_Activity.this, null,getString(R.string.approve), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);*/

        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult result = AndroidClientService.approve(Wfm_Details_Activity.this,
                        wfm.getPROCESSNAME(), wfm.getOWNERTABLE(), wfm.getOWNERID(), wfm.getOWNERTABLE() + "ID", zx, desc,
                        AccountUtils.getpersonId(Wfm_Details_Activity.this).toUpperCase());

                Log.e("审批","PROCESSNAME:"+wfm.getPROCESSNAME());
                Log.e("审批","OWNERTABLE:"+wfm.getOWNERTABLE());
                Log.e("审批","OWNERID:"+wfm.getOWNERID());
                Log.e("审批","OWNERTABLE:"+wfm.getOWNERTABLE());
                Log.e("审批","是否通过:"+zx);
                Log.e("审批","描述:"+desc);
                Log.e("审批","工号:"+AccountUtils.getpersonId(Wfm_Details_Activity.this));

                Log.i(TAG, "result=" + result);
                return result;
            }

            @Override
            protected void onPostExecute(WebResult s) {
                super.onPostExecute(s);
                if (s == null || s.wonum == null || s.errorMsg == null) {
                    Toast.makeText(Wfm_Details_Activity.this, "审批失败1", Toast.LENGTH_SHORT).show();
                } else if (s.wonum.equals(wfm.getOWNERID()) && s.errorMsg != null) {
                    Toast.makeText(Wfm_Details_Activity.this, s.errorMsg, Toast.LENGTH_LONG).show();
                    //finish();
                } else {
                    Log.e("审批", s.toString());
                    Toast.makeText(Wfm_Details_Activity.this, s.errorMsg, Toast.LENGTH_SHORT).show();
                }
                //mProgressDialog.dismiss();
            }
        }.execute();
    }
//
//    //获取工单信息并跳转
//    private void getWorkOrderData(String worktype){
//        HttpManager.getDataPagingInfo(this, HttpManager.getworkorderByid(worktype, wfm.getOWNERID()), new HttpRequestHandler<Results>() {
//            @Override
//            public void onSuccess(Results results) {
//                Log.i(TAG, "data=" + results);
//            }
//
//            @Override
//            public void onSuccess(Results results, int totalPages, int currentPage) {
//                closeProgressDialog();
//                if (results.getResultlist() != null) {
//                    ArrayList<WorkOrder> items = JsonUtils.parsingWorkOrder(Wfm_Details_Activity.this, results.getResultlist());
//                    if (items != null && items.get(0) != null) {
//                        Intent intent = new Intent(Wfm_Details_Activity.this, Work_DetailsActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("workOrder", items.get(0));
////                        bundle.putSerializable("justread", items.get(0));
//                        intent.putExtras(bundle);
//                        startActivityForResult(intent, 0);
////                        finish();
//                    } else {
//                        closeProgressDialog();
//                        Toast.makeText(Wfm_Details_Activity.this, "获取工单数据失败", Toast.LENGTH_SHORT).show();
////                        setResult(100);
////                        finish();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(String error) {
//                closeProgressDialog();
//                Toast.makeText(Wfm_Details_Activity.this, "获取工单数据失败", Toast.LENGTH_SHORT).show();
////                setResult(100);
////                finish();
//            }
//        });
//    }
//
//    //获取调试工单信息并跳转
//    private void getDebugWorkOrderData(){
//        HttpManager.getDataPagingInfo(this, HttpManager.getdebugworkorderByid(wfm.getOWNERID()), new HttpRequestHandler<Results>() {
//            @Override
//            public void onSuccess(Results results) {
//                Log.i(TAG, "data=" + results);
//            }
//
//            @Override
//            public void onSuccess(Results results, int totalPages, int currentPage) {
//                closeProgressDialog();
//                if (results.getResultlist() != null) {
//                    ArrayList<DebugWorkOrder> items = JsonUtils.parsingDebugWorkOrder(Wfm_Details_Activity.this, results.getResultlist());
//                    if (items != null && items.get(0) != null) {
//                        Intent intent = new Intent(Wfm_Details_Activity.this, DebugWork_DetailsActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("debugworkOrder", items.get(0));
////                        bundle.putSerializable("justread", items.get(0));
//                        intent.putExtras(bundle);
//                        startActivityForResult(intent, 0);
////                        finish();
//                    }else {
//                        closeProgressDialog();
//                        Toast.makeText(Wfm_Details_Activity.this, "获取工单数据失败", Toast.LENGTH_SHORT).show();
////                        setResult(100);
////                        finish();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(String error) {
//                closeProgressDialog();
//                Toast.makeText(Wfm_Details_Activity.this, "获取工单数据失败", Toast.LENGTH_SHORT).show();
////                setResult(100);
////                finish();
//            }
//        });
//    }
//
//    //获取问题联络单信息并跳转
//    private void getUdstockData(){
//        HttpManager.getDataPagingInfo(this, HttpManager.getudstockurlByid(wfm.getOWNERID()), new HttpRequestHandler<Results>() {
//            @Override
//            public void onSuccess(Results results) {
//                Log.i(TAG, "data=" + results);
//            }
//
//            @Override
//            public void onSuccess(Results results, int totalPages, int currentPage) {
//                closeProgressDialog();
//                if (results.getResultlist() != null) {
//                    ArrayList<Udstock> items = JsonUtils.parsingUdstock(Wfm_Details_Activity.this, results.getResultlist());
//                    if (items != null && items.get(0) != null) {
//                        Intent intent = new Intent(Wfm_Details_Activity.this, Udstock_DetailActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("udstock", items.get(0));
////                        bundle.putSerializable("justread", items.get(0));
//                        intent.putExtras(bundle);
//                        startActivityForResult(intent, 0);
////                        finish();
//                    }else {
//                        closeProgressDialog();
//                        Toast.makeText(Wfm_Details_Activity.this, "获取库存盘点数据失败", Toast.LENGTH_SHORT).show();
////                        setResult(100);
////                        finish();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(String error) {
//                closeProgressDialog();
//                Toast.makeText(Wfm_Details_Activity.this, "获取库存盘点数据失败", Toast.LENGTH_SHORT).show();
////                setResult(100);
////                finish();
//            }
//        });
//    }
//
//    //获取问题联络单信息并跳转
//    private void getUdfeedbackData(){
//        HttpManager.getDataPagingInfo(this, HttpManager.getUdfeedbacksurlByid(wfm.getOWNERID()), new HttpRequestHandler<Results>() {
//            @Override
//            public void onSuccess(Results results) {
//                Log.i(TAG, "data=" + results);
//            }
//
//            @Override
//            public void onSuccess(Results results, int totalPages, int currentPage) {
//                closeProgressDialog();
//                if (results.getResultlist() != null) {
//                    ArrayList<Udfeedback> items = JsonUtils.parsingUdfeedback(Wfm_Details_Activity.this, results.getResultlist());
//                    if (items != null && items.get(0) != null) {
//                        Intent intent = new Intent(Wfm_Details_Activity.this, Udfeedback_DetailActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("udfeedback", items.get(0));
////                        bundle.putSerializable("justread", items.get(0));
//                        intent.putExtras(bundle);
//                        startActivityForResult(intent, 0);
////                        finish();
//                    }else {
//                        closeProgressDialog();
//                        Toast.makeText(Wfm_Details_Activity.this, "获取问题联络单数据失败", Toast.LENGTH_SHORT).show();
////                        setResult(100);
////                        finish();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(String error) {
//                closeProgressDialog();
//                Toast.makeText(Wfm_Details_Activity.this, "获取问题联络单数据失败", Toast.LENGTH_SHORT).show();
////                setResult(100);
////                finish();
//            }
//        });
//    }
//
//    //获取故障提报单信息并跳转
//    private void getUdreportData(){
//        HttpManager.getDataPagingInfo(this, HttpManager.getudreporturlByid(wfm.getOWNERID()), new HttpRequestHandler<Results>() {
//            @Override
//            public void onSuccess(Results results) {
//                Log.i(TAG, "data=" + results);
//            }
//
//            @Override
//            public void onSuccess(Results results, int totalPages, int currentPage) {
//                closeProgressDialog();
//                if (results.getResultlist() != null) {
//                    ArrayList<Udreport> items = JsonUtils.parsingUdreport(Wfm_Details_Activity.this, results.getResultlist());
//                    if (items != null && items.get(0) != null) {
//                        Intent intent = new Intent(Wfm_Details_Activity.this, Udreport_DetailActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("udreport", items.get(0));
////                        bundle.putSerializable("justread", items.get(0));
//                        intent.putExtras(bundle);
//                        startActivityForResult(intent, 0);
////                        finish();
//                    }else {
//                        closeProgressDialog();
//                        Toast.makeText(Wfm_Details_Activity.this, "获取故障提报单数据失败", Toast.LENGTH_SHORT).show();
////                        setResult(100);
////                        finish();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(String error) {
//                closeProgressDialog();
//                Toast.makeText(Wfm_Details_Activity.this, "获取故障提报单数据失败", Toast.LENGTH_SHORT).show();
////                setResult(100);
////                finish();
//            }
//        });
//    }
//
//    //获取巡检单信息并跳转
//    private void getUdinspoData(){
//        HttpManager.getDataPagingInfo(this, HttpManager.getudinspourlByid(wfm.getOWNERID()), new HttpRequestHandler<Results>() {
//            @Override
//            public void onSuccess(Results results) {
//                Log.i(TAG, "data=" + results);
//            }
//
//            @Override
//            public void onSuccess(Results results, int totalPages, int currentPage) {
//                closeProgressDialog();
//                if (results.getResultlist() != null) {
//                    ArrayList<Udinspo> items = JsonUtils.parsingUdinspo(Wfm_Details_Activity.this, results.getResultlist());
//                    if (items != null && items.get(0) != null) {
//                        Intent intent = new Intent(Wfm_Details_Activity.this, Udinspo_DetailActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("udinspo", items.get(0));
////                        bundle.putSerializable("justread", items.get(0));
//                        intent.putExtras(bundle);
//                        startActivityForResult(intent, 0);
////                        finish();
//                    }else {
//                        closeProgressDialog();
//                        Toast.makeText(Wfm_Details_Activity.this, "获取巡检单数据失败", Toast.LENGTH_SHORT).show();
////                        setResult(100);
////                        finish();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(String error) {
//                closeProgressDialog();
//                Toast.makeText(Wfm_Details_Activity.this, "获取巡检单数据失败", Toast.LENGTH_SHORT).show();
////                setResult(100);
////                finish();
//            }
//        });
//    }

}
