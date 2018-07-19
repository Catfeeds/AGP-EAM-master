package com.hsk.hxqh.agp_eam.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.hsk.hxqh.agp_eam.model.UDWORKAPPLY;
import com.hsk.hxqh.agp_eam.model.UDYEARPLAN;
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
 * 工作申请详情
 */
public class UdworkapplyDetailsActivity extends BaseActivity {
    private static String TAG = "UdworkapplyDetailsActivity";

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
    private LinearLayout workflowLayout;//发送工作流
    private LinearLayout commitLayout;//上传附件

    /**
     * 字段显示
     **/
    private TextView woapplynumTextView; //工作申请编号
    private TextView descriptionTextView; //工作申请描述
    private TextView createbynameTextView; //创建人
    private TextView assetnumTextView; //资产编号
    private ImageView assetnumImg;
    private TextView assetdesTextView; //资产描述
    private TextView locationTextView; //位置编号
    private ImageView locationImg;
    private TextView locdescTextView; //位置编号
    private TextView statusTextView; //状态
    private TextView stationdesTextView; //站场
    private TextView purposeTextView; //目的
    private EditText workexeTextView; //工作执行人数
    private TextView preparedbynameTextView; //准备人姓名
    private TextView preparedbytitleTextView; //职位
    private TextView createdateTextView; //开始时间
    private ImageView createdateImg;
    private EditText workdatesTextView; //工期
    private TextView plannumTextView; //年度计划编号
    private ImageView plannumImg;
    private TextView jpnumTextView; //作业计划编号
    private ImageView jpnumImg;

    /**
     * 故障提报单
     **/
    private UDWORKAPPLY udworkapply;

    private Button cancel;//取消
    private Button save;//保存

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private ArrayList<DialogMenuItem> mMenuItems = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udworkapply_details);
        geiIntentData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void geiIntentData() {
        udworkapply = (UDWORKAPPLY) getIntent().getSerializableExtra("udworkapply");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);
        moreImg = (ImageView) findViewById(R.id.title_more);

        woapplynumTextView = (TextView) findViewById(R.id.woapplynum_text_id);
        descriptionTextView = (TextView) findViewById(R.id.description_text_id);
        createbynameTextView = (TextView) findViewById(R.id.createbyname_text_id);
        assetnumTextView = (TextView) findViewById(R.id.asset_assetnum_id);
        assetnumImg = (ImageView) findViewById(R.id.asset_assetnum_image_id);
        assetdesTextView = (TextView) findViewById(R.id.asset_desc_id);
        locationTextView = (TextView) findViewById(R.id.asset_location_id);
        locationImg = (ImageView) findViewById(R.id.asset_location_image_id);
        locdescTextView = (TextView) findViewById(R.id.asset_locdesc_id);
        statusTextView = (TextView) findViewById(R.id.status_text_id);
        stationdesTextView = (TextView) findViewById(R.id.stationdes_text_id);
        purposeTextView = (TextView) findViewById(R.id.purpose_text_id);
        workexeTextView = (EditText) findViewById(R.id.workexe_text_id);
        preparedbynameTextView = (TextView) findViewById(R.id.preparedbyname_text_id);
        preparedbytitleTextView = (TextView) findViewById(R.id.preparedbytitle_text_id);
        createdateTextView = (TextView) findViewById(R.id.startdate_text_id);
        createdateImg = (ImageView) findViewById(R.id.startdate_text_image_id);
        workdatesTextView = (EditText) findViewById(R.id.workdates_text_id);
        plannumTextView = (TextView) findViewById(R.id.plannum_text_id);
        plannumImg = (ImageView) findViewById(R.id.plannum_text_image_id);
        jpnumTextView = (TextView) findViewById(R.id.jpnum_text_id);
        jpnumImg = (ImageView) findViewById(R.id.jpnum_text_image_id);

        cancel = (Button) findViewById(R.id.work_cancel);
        save = (Button) findViewById(R.id.work_save);
    }


    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.udworkapply_text);
        moreImg.setVisibility(View.VISIBLE);
        moreImg.setOnClickListener(moreImgOnClickListener);

        if (udworkapply != null) {
            woapplynumTextView.setText(udworkapply.getWOAPPLYNUM());
            descriptionTextView.setText(udworkapply.getDESCRIPTION());
            createbynameTextView.setText(udworkapply.getCREATEBYNAME());
            assetnumTextView.setText(udworkapply.getASSETNUM());
            assetdesTextView.setText(udworkapply.getASSETDES());
            locationTextView.setText(udworkapply.getLOCATION());
            locdescTextView.setText(udworkapply.getLOCATIONDES());
            statusTextView.setText(udworkapply.getSTATUS());
            stationdesTextView.setText(udworkapply.getSTATIONDES());
            purposeTextView.setText(udworkapply.getPURPOSE());
            workexeTextView.setText(udworkapply.getWORKEXE());
            preparedbynameTextView.setText(udworkapply.getPREPAREDBYNAME());
            preparedbytitleTextView.setText(udworkapply.getPREPAREDBYTITLE());
            createdateTextView.setText(udworkapply.getCREATEDATE());
            workdatesTextView.setText(udworkapply.getWORKDATES());
            plannumTextView.setText(udworkapply.getPLANNUM());
            jpnumTextView.setText(udworkapply.getJPNUM());

        }

        assetnumImg.setOnClickListener(assetnumOnClickListener);
        locationImg.setOnClickListener(locationOnClickListener);
        plannumImg.setOnClickListener(udyearplannumOnClickListener);
        jpnumImg.setOnClickListener(jpnumOnClickListener);
        createdateImg.setOnClickListener(new DateTimeOnClickListener(createdateTextView));

        cancel.setOnClickListener(backImageViewOnClickListener);
        save.setOnClickListener(saveOnClickListener);
    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener assetnumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UdworkapplyDetailsActivity.this, Asset_chooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener locationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UdworkapplyDetailsActivity.this, Location_chooseActivity.class);
            startActivityForResult(intent, 1);
        }
    };

    private View.OnClickListener udyearplannumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UdworkapplyDetailsActivity.this, Udyearplan_chooseActivity.class);
            startActivityForResult(intent, 2);
        }
    };

    private View.OnClickListener jpnumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UdworkapplyDetailsActivity.this, Jobplan_chooseActivity.class);
            startActivityForResult(intent, 3);
        }
    };

    private View.OnClickListener saveOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            submitDataInfo();
        }
    };

    //时间选择监听
    private class DateTimeOnClickListener implements View.OnClickListener {
        TextView textView;

        private DateTimeOnClickListener(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onClick(View view) {
            new DateTimeSelect(UdworkapplyDetailsActivity.this, textView).showDialog();
        }
    }

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
        View contentView = LayoutInflater.from(UdworkapplyDetailsActivity.this).inflate(
                R.layout.udworkapply_popup_window, null);


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

        workflowLayout = (LinearLayout) contentView.findViewById(R.id.work_workflow_id);
        commitLayout = (LinearLayout) contentView.findViewById(R.id.work_upload_id);
        workflowLayout.setOnClickListener(flowerOnClickListener);
        commitLayout.setOnClickListener(commitOnClickListener);
    }

    private View.OnClickListener commitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popupWindow.dismiss();
            Intent intent = new Intent(UdworkapplyDetailsActivity.this, PhotoActivity.class);
            intent.putExtra("ownertable", "UDWORKAPPLY");
            intent.putExtra("ownerid", udworkapply.getUDWORKAPPLYID() + "");
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener flowerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (udworkapply.STATUS.equals("新建")) {
                MaterialDialogOneBtn();
            } else {
                EditDialog();
            }
            popupWindow.dismiss();
        }
    };

    private void MaterialDialogOneBtn() {//开始工作流
        final MaterialDialog2 dialog = new MaterialDialog2(UdworkapplyDetailsActivity.this);
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
        final NormalEditTextDialog dialog = new NormalEditTextDialog(UdworkapplyDetailsActivity.this);
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
        mProgressDialog = ProgressDialog.show(UdworkapplyDetailsActivity.this, null,
                getString(R.string.start), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult result = AndroidClientService.startwf(UdworkapplyDetailsActivity.this,
                        "UDFAULTREP", "UDWORKAPPLY", udworkapply.WOAPPLYNUM, "WOAPPLYNUM", AccountUtils.getpersonId(UdworkapplyDetailsActivity.this));
                return result;
            }

            @Override
            protected void onPostExecute(WebResult s) {
                super.onPostExecute(s);
                if (s != null && s.errorMsg != null && s.errorMsg.equals("工作流启动成功")) {
                    Toast.makeText(UdworkapplyDetailsActivity.this, s.errorMsg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UdworkapplyDetailsActivity.this, "工作流启动失败", Toast.LENGTH_SHORT).show();
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
        mProgressDialog = ProgressDialog.show(UdworkapplyDetailsActivity.this, null,
                getString(R.string.approve), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult result = AndroidClientService.approve(UdworkapplyDetailsActivity.this,
                        "UDFAULTREP", "UDWORKAPPLY", udworkapply.WOAPPLYNUM, "WOAPPLYNUM", zx, desc,
                        AccountUtils.getpersonId(UdworkapplyDetailsActivity.this));
                return result;
            }

            @Override
            protected void onPostExecute(WebResult s) {
                super.onPostExecute(s);
                if (s == null || s.wonum == null || s.errorMsg == null) {
                    Toast.makeText(UdworkapplyDetailsActivity.this, "审批失败", Toast.LENGTH_SHORT).show();
                } else if (s.wonum.equals(udworkapply.UDWORKAPPLYID+"") && s.errorMsg != null) {
                    statusTextView.setText(s.errorMsg);
                    udworkapply.STATUS = s.errorMsg;
                    Toast.makeText(UdworkapplyDetailsActivity.this, "审批成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UdworkapplyDetailsActivity.this, "审批失败", Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();
            }
        }.execute();
    }

    /**
     * 提交数据*
     */
    private void submitDataInfo() {
        final NormalDialog dialog = new NormalDialog(UdworkapplyDetailsActivity.this);
        dialog.content("确定修改工作申请吗?")//
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
        updataInfo = JsonUtils.UdworkapplyToJson(getUdworkapply());
//            } else if (workOrder.status.equals(Constants.APPROVALED)) {
//                updataInfo = JsonUtils.WorkToJson(getWorkOrder(), null, null, null, null, getLabtransList());
//            }
        final String finalUpdataInfo = updataInfo;
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult reviseresult = AndroidClientService.UpdateWO(UdworkapplyDetailsActivity.this, finalUpdataInfo,
                        "UDWORKAPPLY", "WOAPPLYNUM", udworkapply.WOAPPLYNUM, Constants.WORK_URL);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(WebResult workResult) {
                super.onPostExecute(workResult);
                if (workResult == null || workResult.errorMsg == null) {
                    Toast.makeText(UdworkapplyDetailsActivity.this, "修改工作申请失败", Toast.LENGTH_SHORT).show();
                } else if (workResult.errorMsg.equals("成功")) {
                    Toast.makeText(UdworkapplyDetailsActivity.this, "修改工作申请成功", Toast.LENGTH_SHORT).show();
                    setResult(100);
                    finish();
                } else {
                    Toast.makeText(UdworkapplyDetailsActivity.this, workResult.errorMsg, Toast.LENGTH_SHORT).show();
                }
                closeProgressDialog();
            }
        }.execute();
        //}else {
        closeProgressDialog();
    }

    private UDWORKAPPLY getUdworkapply() {
        UDWORKAPPLY udworkapply = this.udworkapply;
        udworkapply.setASSETNUM(assetnumTextView.getText().toString());
        udworkapply.setASSETDES(assetdesTextView.getText().toString());
        udworkapply.setLOCATION(locationTextView.getText().toString());
        udworkapply.setLOCATIONDES(locdescTextView.getText().toString());
        udworkapply.setPLANNUM(plannumTextView.getText().toString());
        udworkapply.setJPNUM(jpnumTextView.getText().toString());
        udworkapply.setCREATEDATE(createdateTextView.getText().toString());
        return udworkapply;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (requestCode) {
                case 0:
                    ASSET asset = (ASSET) data.getSerializableExtra("asset");
                    assetnumTextView.setText(asset.getASSETNUM());
                    assetdesTextView.setText(asset.getDESCRIPTION());
                    break;
                case 1:
                    LOCATIONS locations = (LOCATIONS) data.getSerializableExtra("location");
                    locationTextView.setText(locations.getLOCATION());
                    locdescTextView.setText(locations.getDESCRIPTION());
                    break;
                case 2:
                    UDYEARPLAN udyearplan = (UDYEARPLAN) data.getSerializableExtra("udyearplan");
                    plannumTextView.setText(udyearplan.getPLANNUM());
                    break;
                case 3:
                    JOBPLAN jobplan = (JOBPLAN) data.getSerializableExtra("jobplan");
                    jpnumTextView.setText(jobplan.getJPNUM());
                    break;
            }
        }
    }
}
