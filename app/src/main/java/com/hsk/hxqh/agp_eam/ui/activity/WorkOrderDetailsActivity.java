package com.hsk.hxqh.agp_eam.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnBtnEditClickL;
import com.flyco.dialog.widget.MaterialDialog2;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalEditTextDialog;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.config.Constants;
import com.hsk.hxqh.agp_eam.model.ASSET;
import com.hsk.hxqh.agp_eam.model.JOBPLAN;
import com.hsk.hxqh.agp_eam.model.LOCATIONS;
import com.hsk.hxqh.agp_eam.model.UDYEARPLAN;
import com.hsk.hxqh.agp_eam.model.WORKORDER;
import com.hsk.hxqh.agp_eam.model.WebResult;
import com.hsk.hxqh.agp_eam.ui.activity.option.Asset_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Jobplan_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Location_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Udyearplan_chooseActivity;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;
import com.hsk.hxqh.agp_eam.unit.DateTimeSelect;
import com.hsk.hxqh.agp_eam.webserviceclient.AndroidClientService;

import java.util.ArrayList;


/**
 * 资产详情
 */
public class WorkOrderDetailsActivity extends BaseActivity {
    private static String TAG = "WorkOrderDetailsActivity";

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    private ImageView moreImg;
    private PopupWindow popupWindow;
    private LinearLayout workplanLayout;//工作计划
    private LinearLayout workrealLayout;//实际情况
    private LinearLayout workflowLayout;//发送工作流
    private LinearLayout commitLayout;//上传附件

    private TextView wonum;//工单
    private EditText description;//描述
    private TextView location;//位置
    private ImageView locationImg;
    private TextView locationdes;//位置描述
    private TextView assetnum;//资产
    private ImageView assetnumImg;
    private TextView assetdes;//资产描述
    private TextView udstatus;//状态
    private TextView jpnum;//作业计划
    private ImageView jpnumImg;//
    private TextView udfaultreportnum;//故障提报ACT号
    private ImageView udfaultreportnumImg;
    private EditText stationdes;//站场
    private TextView udworeqnum;//工作申请编号
    private TextView udyearplannum;//年度计划编号
    private ImageView udyearplannumImg;//
    private TextView createbyname;//创建人
    private TextView createdate;//创建时间
    private TextView udfaultdesc;//故障描述
    private TextView udreason;//故障原因
    private TextView schedstart;//调度开始时间
    private ImageView schedstartImg;
    private TextView schedfinish;//计划完成时间
    private ImageView schedfinishImg;
    private TextView actstart;//实际开始时间
    private ImageView actstartImg;
    private TextView actfinish;//实际完成时间
    private ImageView actfinishImg;
    private TextView udyear;//年度计划
    private TextView workfrequency;//频率
    private TextView udworkscope;//工作范围
    private TextView hazardlevel;//工期

    private WORKORDER workorder;

    private Button cancel;//取消
    private Button save;//保存

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private ArrayList<DialogMenuItem> mMenuItems = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workorder_details);
        geiIntentData();
        findViewById();
        initView();
//
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void geiIntentData() {
        workorder = (WORKORDER) getIntent().getSerializableExtra("workorder");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);
        moreImg = (ImageView) findViewById(R.id.title_more);
//        submitBtn = (Button) findViewById(R.id.sbmit_id);

//        ViewGroup container = (ViewGroup) findViewById(R.id.container);
//        LayoutTransition transition = new LayoutTransition();
//        container.setLayoutTransition(transition);
        wonum = (TextView) findViewById(R.id.wonum_text_id);
        description = (EditText) findViewById(R.id.work_description_id);
        location = (TextView) findViewById(R.id.work_location_id);
        locationImg = (ImageView) findViewById(R.id.work_location_image_id);
        locationdes = (TextView) findViewById(R.id.work_locationdes_id);
        assetnum = (TextView) findViewById(R.id.work_assetnum_id);
        assetnumImg = (ImageView) findViewById(R.id.work_assetnum_image_id);
        assetdes = (TextView) findViewById(R.id.work_assetdes_id);
        udstatus = (TextView) findViewById(R.id.work_udstatus_id);
        jpnum = (TextView) findViewById(R.id.work_jpnum_id);
        jpnumImg = (ImageView) findViewById(R.id.work_jpnum_image_id);
        udfaultreportnum = (TextView) findViewById(R.id.work_udfaultreportnum_id);
        udfaultreportnumImg = (ImageView) findViewById(R.id.work_udfaultreportnum_image_id);
        stationdes = (EditText) findViewById(R.id.work_stationdes_id);
        udworeqnum = (TextView) findViewById(R.id.work_udworeqnum_id);
        udyearplannum = (TextView) findViewById(R.id.work_udyearplannum_id);
        udyearplannumImg = (ImageView) findViewById(R.id.work_udyearplannum_image_id);
        createbyname = (TextView) findViewById(R.id.work_createbyname_id);
        createdate = (TextView) findViewById(R.id.work_createdate_id);
        udfaultdesc = (TextView) findViewById(R.id.work_udfaultdesc_id);
        udreason = (TextView) findViewById(R.id.work_udreason_id);
        schedstart = (TextView) findViewById(R.id.work_schedstart_id);
        schedstartImg = (ImageView) findViewById(R.id.work_schedstart_image_id);
        schedfinish = (TextView) findViewById(R.id.work_schedfinish_id);
        schedfinishImg = (ImageView) findViewById(R.id.work_schedfinish_image_id);
        actstart = (TextView) findViewById(R.id.work_actstart_id);
        actstartImg = (ImageView) findViewById(R.id.work_actstart_image_id);
        actfinish = (TextView) findViewById(R.id.work_actfinish_id);
        actfinishImg = (ImageView) findViewById(R.id.work_actfinish_image_id);
        udyear = (TextView) findViewById(R.id.work_udyear_id);
        workfrequency = (TextView) findViewById(R.id.work_workfrequency_id);
        udworkscope = (TextView) findViewById(R.id.work_udworkscope_id);
        hazardlevel = (TextView) findViewById(R.id.work_hazardlevel_id);

        cancel = (Button) findViewById(R.id.work_cancel);
        save = (Button) findViewById(R.id.work_save);
    }


    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.work_title);
        moreImg.setVisibility(View.VISIBLE);
        moreImg.setOnClickListener(moreImgOnClickListener);
//        submitBtn.setVisibility(View.GONE);

        if (workorder != null) {
            wonum.setText(workorder.WONUM);
            description.setText(workorder.DESCRIPTION);
            location.setText(workorder.LOCATION);
            locationdes.setText(workorder.LOCATIONDES);
            assetnum.setText(workorder.ASSETNUM);
            assetdes.setText(workorder.ASSETDES);
            udstatus.setText(workorder.UDSTATUS);
            jpnum.setText(workorder.JPNUM);
            udfaultreportnum.setText(workorder.UDFAULTREPORTNUM);
            stationdes.setText(workorder.STATIONDES);
            udworeqnum.setText(workorder.UDWOREQNUM);
            udyearplannum.setText(workorder.UDYEARPLANNUM);
            createbyname.setText(workorder.CREATEBYNAME);
            createdate.setText(workorder.CREATEDATE);
            udfaultdesc.setText(workorder.UDFAULTDESC);
            udreason.setText(workorder.UDREASON);
            schedstart.setText(workorder.SCHEDSTART);
            schedfinish.setText(workorder.SCHEDFINISH);
            actstart.setText(workorder.ACTSTART);
            actfinish.setText(workorder.ACTFINISH);
            udyear.setText(workorder.UDYEAR);
            workfrequency.setText(workorder.WORKFREQUENCY);
            udworkscope.setText(workorder.UDWORKSCOPE);
            hazardlevel.setText(workorder.HAZARDLEVEL);

        }

        locationImg.setOnClickListener(locationOnClickListener);
        assetnumImg.setOnClickListener(assetnumOnClickListener);
        udyearplannumImg.setOnClickListener(udyearplannumOnClickListener);
        jpnumImg.setOnClickListener(jpnumOnClickListener);
        schedstartImg.setOnClickListener(new DateTimeOnClickListener(schedstart));
        schedfinishImg.setOnClickListener(new DateTimeOnClickListener(schedfinish));
        actstartImg.setOnClickListener(new DateTimeOnClickListener(actstart));
        actfinishImg.setOnClickListener(new DateTimeOnClickListener(actfinish));

        cancel.setOnClickListener(backImageViewOnClickListener);
        save.setOnClickListener(saveOnClickListener);
    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener moreImgOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupWindow(moreImg);
        }
    };

    /**
     * 初始化showPopupWindow*
     */
    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(WorkOrderDetailsActivity.this).inflate(
                R.layout.workorder_popup_window, null);


        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.popup_background_mtrl_mult));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

        workplanLayout = (LinearLayout) contentView.findViewById(R.id.work_workplan_id);
        workrealLayout = (LinearLayout) contentView.findViewById(R.id.work_workreal_id);
        workflowLayout = (LinearLayout) contentView.findViewById(R.id.work_workflow_id);
        commitLayout = (LinearLayout) contentView.findViewById(R.id.work_upload_id);
        workplanLayout.setOnClickListener(workplanOnClickListener);
        workrealLayout.setOnClickListener(workrealOnClickListener);
        workflowLayout.setOnClickListener(flowerOnClickListener);
        commitLayout.setOnClickListener(commitOnClickListener);
    }

    private View.OnClickListener workplanOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(WorkOrderDetailsActivity.this, Work_PlanActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("workorder", workorder);
//            bundle.putSerializable("woactivityList", woactivityList);
            intent.putExtras(bundle);
            startActivityForResult(intent, 1000);
            popupWindow.dismiss();
        }
    };

    private View.OnClickListener workrealOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(WorkOrderDetailsActivity.this, Work_RealActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("workorder", workorder);
//            bundle.putSerializable("woactivityList", woactivityList);
            intent.putExtras(bundle);
            startActivityForResult(intent, 2000);
            popupWindow.dismiss();
        }
    };

    private View.OnClickListener commitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popupWindow.dismiss();
            Intent intent = new Intent(WorkOrderDetailsActivity.this, PhotoActivity.class);
            intent.putExtra("ownertable", "WORKORDER");
            intent.putExtra("ownerid", workorder.getWORKORDERID() + "");
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener assetnumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WorkOrderDetailsActivity.this, Asset_chooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener locationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WorkOrderDetailsActivity.this, Location_chooseActivity.class);
            startActivityForResult(intent, 1);
        }
    };

    private View.OnClickListener udyearplannumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WorkOrderDetailsActivity.this, Udyearplan_chooseActivity.class);
            startActivityForResult(intent, 2);
        }
    };

    private View.OnClickListener jpnumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WorkOrderDetailsActivity.this, Jobplan_chooseActivity.class);
            startActivityForResult(intent, 3);
        }
    };

    private View.OnClickListener saveOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            submitDataInfo();
        }
    };

    /**
     * 提交数据*
     */
    private void submitDataInfo() {
        final NormalDialog dialog = new NormalDialog(WorkOrderDetailsActivity.this);
        dialog.content("确定修改工单吗?")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        showProgressDialog("数据提交中...");
                        startAsyncTask();
                        dialog.dismiss();
                    }
                });
    }

    /**
     * 提交数据*
     */
    private void startAsyncTask() {
                String updataInfo = null;
//            if (workOrder.status.equals(Constants.WAIT_APPROVAL)) {
                updataInfo = JsonUtils.WorkToJson(getWorkOrder());
//            } else if (workOrder.status.equals(Constants.APPROVALED)) {
//                updataInfo = JsonUtils.WorkToJson(getWorkOrder(), null, null, null, null, getLabtransList());
//            }
                final String finalUpdataInfo = updataInfo;
                new AsyncTask<String, String, WebResult>() {
                    @Override
                    protected WebResult doInBackground(String... strings) {
                        WebResult reviseresult = AndroidClientService.UpdateWO(WorkOrderDetailsActivity.this, finalUpdataInfo,
                                "WORKORDER", "WONUM", workorder.WONUM, Constants.WORK_URL);
                        return reviseresult;
                    }

                    @Override
                    protected void onPostExecute(WebResult workResult) {
                        super.onPostExecute(workResult);
                        if (workResult == null || workResult.errorMsg == null) {
                            Toast.makeText(WorkOrderDetailsActivity.this, "修改工单失败", Toast.LENGTH_SHORT).show();
                        } else if (workResult.errorMsg.equals("成功")) {
                            Toast.makeText(WorkOrderDetailsActivity.this, "修改工单成功", Toast.LENGTH_SHORT).show();
                            setResult(100);
                            finish();
                        } else {
                            Toast.makeText(WorkOrderDetailsActivity.this, workResult.errorMsg, Toast.LENGTH_SHORT).show();
                        }
                        closeProgressDialog();
                    }
                }.execute();
                //}else {
                closeProgressDialog();
    }

    private WORKORDER getWorkOrder() {
        WORKORDER workOrder = this.workorder;
        workOrder.setASSETNUM(assetnum.getText().toString());
        workOrder.setASSETDES(assetdes.getText().toString());
        workOrder.setLOCATION(location.getText().toString());
        workOrder.setLOCATIONDES(locationdes.getText().toString());
        workOrder.setUDYEARPLANNUM(udyearplannum.getText().toString());
        workOrder.setJPNUM(jpnum.getText().toString());
        workOrder.setSCHEDSTART(schedstart.getText().toString());
        workOrder.setSCHEDFINISH(schedfinish.getText().toString());
        workOrder.setACTSTART(actstart.getText().toString());
        workOrder.setACTFINISH(actfinish.getText().toString());
        return workOrder;
    }

    //时间选择监听
    private class DateTimeOnClickListener implements View.OnClickListener {
        TextView textView;

        private DateTimeOnClickListener(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onClick(View view) {
            new DateTimeSelect(WorkOrderDetailsActivity.this, textView).showDialog();
        }
    }

    private View.OnClickListener flowerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (workorder.UDSTATUS.equals("新建")) {
                MaterialDialogOneBtn();
            } else {
                EditDialog();
            }
            popupWindow.dismiss();
        }
    };

    private void MaterialDialogOneBtn() {//开始工作流
        final MaterialDialog2 dialog = new MaterialDialog2(WorkOrderDetailsActivity.this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content("是否启动工作流")//
                .btnText("是", "否")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {//是
                    @Override
                    public void onBtnClick() {
                        startWF();
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {//否
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                }
        );
    }

    private void EditDialog() {//输入审核意见
        final NormalEditTextDialog dialog = new NormalEditTextDialog(WorkOrderDetailsActivity.this);
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
                        dialog.dismiss();
                    }
                },
                new OnBtnEditClickL() {
                    @Override
                    public void onBtnClick(String text) {
                        wfgoon("0", text.equals("通过") ? "不通过" : text);
                        dialog.dismiss();
                    }
                }
        );
    }

    /**
     * 开始工作流
     */
    private void startWF() {
        mProgressDialog = ProgressDialog.show(WorkOrderDetailsActivity.this, null,
                getString(R.string.start), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult result = AndroidClientService.startwf(WorkOrderDetailsActivity.this,
                        "UDWOTRACK", "WORKORDER", workorder.WONUM, "WONUM", AccountUtils.getpersonId(WorkOrderDetailsActivity.this));
                return result;
            }

            @Override
            protected void onPostExecute(WebResult s) {
                super.onPostExecute(s);
                if (s != null && s.errorMsg != null && s.errorMsg.equals("工作流启动成功")) {
                    Toast.makeText(WorkOrderDetailsActivity.this, s.errorMsg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WorkOrderDetailsActivity.this, "工作流启动失败", Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();
            }
        }.execute();
    }

    /**
     * 审批工作流
     *
     * @param zx
     */
    private void wfgoon(final String zx, final String desc) {
        mProgressDialog = ProgressDialog.show(WorkOrderDetailsActivity.this, null,
                getString(R.string.approve), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult result = AndroidClientService.approve(WorkOrderDetailsActivity.this,
                        "UDWOTRACK", "WORKORDER", workorder.WORKORDERID+"", "WORKORDERID", zx, desc,
                        AccountUtils.getpersonId(WorkOrderDetailsActivity.this));
                return result;
            }

            @Override
            protected void onPostExecute(WebResult s) {
                super.onPostExecute(s);
                if (s == null || s.wonum == null || s.errorMsg == null) {
                    Toast.makeText(WorkOrderDetailsActivity.this, "审批失败", Toast.LENGTH_SHORT).show();
                } else if (s.wonum.equals(workorder.WORKORDERID+"") && s.errorMsg != null) {
                    udstatus.setText(s.errorMsg);
                    workorder.UDSTATUS = s.errorMsg;
                    Toast.makeText(WorkOrderDetailsActivity.this, "审批成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WorkOrderDetailsActivity.this, "审批失败", Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();
            }
        }.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (requestCode) {
                case 0:
                    ASSET asset = (ASSET) data.getSerializableExtra("asset");
                    assetnum.setText(asset.getASSETNUM());
                    assetdes.setText(asset.getDESCRIPTION());
                    break;
                case 1:
                    LOCATIONS locations = (LOCATIONS) data.getSerializableExtra("location");
                    location.setText(locations.getLOCATION());
                    locationdes.setText(locations.getDESCRIPTION());
                    break;
                case 2:
                    UDYEARPLAN udyearplan = (UDYEARPLAN) data.getSerializableExtra("udyearplan");
                    udyearplannum.setText(udyearplan.getPLANNUM());
                    break;
                case 3:
                    JOBPLAN jobplan = (JOBPLAN) data.getSerializableExtra("jobplan");
                    jpnum.setText(jobplan.getJPNUM());
                    break;
            }
        }
    }
}
