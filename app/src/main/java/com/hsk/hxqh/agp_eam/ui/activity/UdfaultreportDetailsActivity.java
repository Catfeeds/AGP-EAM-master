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
import android.widget.CheckBox;
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
import com.hsk.hxqh.agp_eam.model.LOCATIONS;
import com.hsk.hxqh.agp_eam.model.UDFAULTREPORT;
import com.hsk.hxqh.agp_eam.model.WebResult;
import com.hsk.hxqh.agp_eam.ui.activity.option.Asset_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Location_chooseActivity;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;
import com.hsk.hxqh.agp_eam.webserviceclient.AndroidClientService;

import java.util.ArrayList;


/**
 * 故障提报单详情
 */
public class UdfaultreportDetailsActivity extends BaseActivity {
    private static String TAG = "UdfaultreportDetailsActivity";

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
    private TextView udfaultreportnumTextView; //故障编号
    private TextView descriptionTextView; //描述
    private TextView assetnumTextView; //资产编号
    private ImageView assetnumImg;
    private TextView assetdesTextView; //资产描述
    private TextView locationTextView; //位置编号
    private ImageView locationImg;
    private TextView locdescTextView; //位置编号
    private TextView stationdesTextView; //站场
    private TextView statusTextView; //状态
    private TextView createbynameTextView; //创建人
    private TextView createdateTextView; //创建时间
    private CheckBox udinsideCheckBox; //站场内?
    private CheckBox udinlocationCheckBox; //需要派工？
    private CheckBox udsjgyCheckBox; //技术整改？
    private EditText faultdescTextView; //故障描述
    private EditText reasonTextView; //故障原因
    private EditText conclusionTextView; //故障总结
    private TextView materialTextView; //消耗物料

    /**
     * 故障提报单
     **/
    private UDFAULTREPORT udfaultreport;

    private Button cancel;//取消
    private Button save;//保存

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private ArrayList<DialogMenuItem> mMenuItems = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udfaultreportnum_details);
        geiIntentData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void geiIntentData() {
        udfaultreport = (UDFAULTREPORT) getIntent().getSerializableExtra("udfaultreport");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);
        moreImg = (ImageView) findViewById(R.id.title_more);

        udfaultreportnumTextView = (TextView) findViewById(R.id.udfaultreportnum_text_id);
        descriptionTextView = (TextView) findViewById(R.id.description_text_id);
        assetnumTextView = (TextView) findViewById(R.id.asset_assetnum_id);
        assetnumImg = (ImageView) findViewById(R.id.asset_assetnum_image_id);
        assetdesTextView = (TextView) findViewById(R.id.asset_desc_id);
        locationTextView = (TextView) findViewById(R.id.asset_location_id);
        locationImg = (ImageView) findViewById(R.id.asset_location_image_id);
        locdescTextView = (TextView) findViewById(R.id.asset_locdesc_id);
        stationdesTextView = (TextView) findViewById(R.id.stationdes_text_id);
        statusTextView = (TextView) findViewById(R.id.status_text_id);
        createbynameTextView = (TextView) findViewById(R.id.createbyname_text_id);
        createdateTextView = (TextView) findViewById(R.id.createdate_text_id);
        udinsideCheckBox = (CheckBox) findViewById(R.id.udinside＿text＿id);
        udinlocationCheckBox = (CheckBox) findViewById(R.id.udinlocation_text_id);
        udsjgyCheckBox = (CheckBox) findViewById(R.id.udsjgy_text_id);
        faultdescTextView = (EditText) findViewById(R.id.faultdesc_text_id);
        reasonTextView = (EditText) findViewById(R.id.reason_text_id);
        conclusionTextView = (EditText) findViewById(R.id.conclusion_text_id);
        materialTextView = (TextView) findViewById(R.id.material_text_id);

        cancel = (Button) findViewById(R.id.work_cancel);
        save = (Button) findViewById(R.id.work_save);
    }


    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.udfaultreport_text);
        moreImg.setVisibility(View.VISIBLE);
        moreImg.setOnClickListener(moreImgOnClickListener);

        if (udfaultreport != null) {
            udfaultreportnumTextView.setText(udfaultreport.getUDFAULTREPORTNUM());
            descriptionTextView.setText(udfaultreport.getDESCRIPTION());
            assetnumTextView.setText(udfaultreport.getASSETNUM());
            assetdesTextView.setText(udfaultreport.getASSETDES());
            locationTextView.setText(udfaultreport.getLOCATION());
            locdescTextView.setText(udfaultreport.getLOCATIONDES());
            stationdesTextView.setText(udfaultreport.getSTATIONDES());
            statusTextView.setText(udfaultreport.getSTATUS());
            createbynameTextView.setText(udfaultreport.getCREATEBYNAME());
            createdateTextView.setText(udfaultreport.getCREATEDATE());
            if (udfaultreport.getUDINSIDE().equals("Y")) {
                udinsideCheckBox.setChecked(true);
            } else {
                udinsideCheckBox.setChecked(false);
            }
            if (udfaultreport.getUDINLOCATION().equals("Y")) {
                udinlocationCheckBox.setChecked(true);
            } else {
                udinlocationCheckBox.setChecked(false);
            }
            if (udfaultreport.getUDSJGY().equals("Y")) {
                udsjgyCheckBox.setChecked(true);
            } else {
                udsjgyCheckBox.setChecked(false);
            }
            faultdescTextView.setText(udfaultreport.getFAULTDESC());
            reasonTextView.setText(udfaultreport.getREASON());
            conclusionTextView.setText(udfaultreport.getCONCLUSION());
            materialTextView.setText(udfaultreport.getMATERIAL());
        }

        assetnumImg.setOnClickListener(assetnumOnClickListener);
        locationImg.setOnClickListener(locationOnClickListener);

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
            Intent intent = new Intent(UdfaultreportDetailsActivity.this, Asset_chooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener locationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UdfaultreportDetailsActivity.this, Location_chooseActivity.class);
            startActivityForResult(intent, 1);
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
        final NormalDialog dialog = new NormalDialog(UdfaultreportDetailsActivity.this);
        dialog.content("确定修改故障提报单吗?")//
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
        updataInfo = JsonUtils.UdfaultreportToJson(getUdfaultreport());
//            } else if (workOrder.status.equals(Constants.APPROVALED)) {
//                updataInfo = JsonUtils.WorkToJson(getWorkOrder(), null, null, null, null, getLabtransList());
//            }
        final String finalUpdataInfo = updataInfo;
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult reviseresult = AndroidClientService.UpdateWO(UdfaultreportDetailsActivity.this, finalUpdataInfo,
                        "UDFAULTREPORT", "UDFAULTREPORTNUM", udfaultreport.UDFAULTREPORTNUM, Constants.WORK_URL);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(WebResult workResult) {
                super.onPostExecute(workResult);
                if (workResult == null || workResult.errorMsg == null) {
                    Toast.makeText(UdfaultreportDetailsActivity.this, "修改故障提报单失败", Toast.LENGTH_SHORT).show();
                } else if (workResult.errorMsg.equals("成功")) {
                    Toast.makeText(UdfaultreportDetailsActivity.this, "修改故障提报单成功", Toast.LENGTH_SHORT).show();
                    setResult(100);
                    finish();
                } else {
                    Toast.makeText(UdfaultreportDetailsActivity.this, workResult.errorMsg, Toast.LENGTH_SHORT).show();
                }
                closeProgressDialog();
            }
        }.execute();
        //}else {
        closeProgressDialog();
    }

    private UDFAULTREPORT getUdfaultreport() {
        UDFAULTREPORT udfaultreport = this.udfaultreport;
        udfaultreport.setASSETNUM(assetnumTextView.getText().toString());
        udfaultreport.setASSETDES(assetdesTextView.getText().toString());
        udfaultreport.setLOCATION(locationTextView.getText().toString());
        udfaultreport.setLOCATIONDES(locdescTextView.getText().toString());
        udfaultreport.setUDINSIDE(udinsideCheckBox.isChecked()?"Y":"N");
        udfaultreport.setUDINLOCATION(udinlocationCheckBox.isChecked()?"Y":"N");
        udfaultreport.setUDSJGY(udsjgyCheckBox.isChecked()?"Y":"N");
        udfaultreport.setFAULTDESC(faultdescTextView.getText().toString());
        udfaultreport.setREASON(reasonTextView.getText().toString());
        udfaultreport.setCONCLUSION(conclusionTextView.getText().toString());
        return udfaultreport;
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
        View contentView = LayoutInflater.from(UdfaultreportDetailsActivity.this).inflate(
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
            Intent intent = new Intent(UdfaultreportDetailsActivity.this, PhotoActivity.class);
            intent.putExtra("ownertable", "UDFAULTREPORT");
            intent.putExtra("ownerid", udfaultreport.getUDFAULTREPORTID() + "");
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener flowerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (udfaultreport.STATUS.equals("新建")) {
                MaterialDialogOneBtn();
            } else {
                EditDialog();
            }
            popupWindow.dismiss();
        }
    };

    private void MaterialDialogOneBtn() {//开始工作流
        final MaterialDialog2 dialog = new MaterialDialog2(UdfaultreportDetailsActivity.this);
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
        final NormalEditTextDialog dialog = new NormalEditTextDialog(UdfaultreportDetailsActivity.this);
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
        mProgressDialog = ProgressDialog.show(UdfaultreportDetailsActivity.this, null,
                getString(R.string.start), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult result = AndroidClientService.startwf(UdfaultreportDetailsActivity.this,
                        "UDFAULTREP", "UDFAULTREPORT", udfaultreport.UDFAULTREPORTNUM, "UDFAULTREPORTNUM", AccountUtils.getpersonId(UdfaultreportDetailsActivity.this));
                return result;
            }

            @Override
            protected void onPostExecute(WebResult s) {
                super.onPostExecute(s);
                if (s != null && s.errorMsg != null && s.errorMsg.equals("工作流启动成功")) {
                    Toast.makeText(UdfaultreportDetailsActivity.this, s.errorMsg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UdfaultreportDetailsActivity.this, "工作流启动失败", Toast.LENGTH_SHORT).show();
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
        mProgressDialog = ProgressDialog.show(UdfaultreportDetailsActivity.this, null,
                getString(R.string.approve), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult result = AndroidClientService.approve(UdfaultreportDetailsActivity.this,
                        "UDFAULTREP", "UDFAULTREPORT", udfaultreport.UDFAULTREPORTNUM, "UDFAULTREPORTNUM", zx, desc,
                        AccountUtils.getpersonId(UdfaultreportDetailsActivity.this));
                return result;
            }

            @Override
            protected void onPostExecute(WebResult s) {
                super.onPostExecute(s);
                if (s == null || s.wonum == null || s.errorMsg == null) {
                    Toast.makeText(UdfaultreportDetailsActivity.this, "审批失败", Toast.LENGTH_SHORT).show();
                } else if (s.wonum.equals(udfaultreport.UDFAULTREPORTID+"") && s.errorMsg != null) {
                    statusTextView.setText(s.errorMsg);
                    udfaultreport.STATUS = s.errorMsg;
                    Toast.makeText(UdfaultreportDetailsActivity.this, "审批成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UdfaultreportDetailsActivity.this, "审批失败", Toast.LENGTH_SHORT).show();
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
                    assetnumTextView.setText(asset.getASSETNUM());
                    assetdesTextView.setText(asset.getDESCRIPTION());
                    break;
                case 1:
                    LOCATIONS locations = (LOCATIONS) data.getSerializableExtra("location");
                    locationTextView.setText(locations.getLOCATION());
                    locdescTextView.setText(locations.getDESCRIPTION());
                    break;
            }
        }
    }
}
