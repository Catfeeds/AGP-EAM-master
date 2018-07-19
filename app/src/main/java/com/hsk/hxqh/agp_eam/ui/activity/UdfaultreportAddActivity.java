package com.hsk.hxqh.agp_eam.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
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


/**
 * 故障提报单新增
 */
public class UdfaultreportAddActivity extends BaseActivity {
    private static String TAG = "UdfaultreportAddActivity";

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    /**
     * 字段显示
     **/
    private LinearLayout udfaultreportnumLayout;
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
//    private UDFAULTREPORT udfaultreport;

    private Button cancel;//取消
    private Button save;//保存

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

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
//        udfaultreport = (UDFAULTREPORT) getIntent().getSerializableExtra("udfaultreport");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);

        udfaultreportnumLayout = (LinearLayout) findViewById(R.id.udfaultreportnum_layout);
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

        udfaultreportnumLayout.setVisibility(View.GONE);
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
            Intent intent = new Intent(UdfaultreportAddActivity.this, Asset_chooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener locationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UdfaultreportAddActivity.this, Location_chooseActivity.class);
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
        final NormalDialog dialog = new NormalDialog(UdfaultreportAddActivity.this);
        dialog.content("确定新增故障提报单吗?")//
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
                WebResult reviseresult = AndroidClientService.InsertWO(UdfaultreportAddActivity.this, finalUpdataInfo,
                        "UDFAULTREPORT", "UDFAULTREPORTNUM", AccountUtils.getpersonId(UdfaultreportAddActivity.this), Constants.WORK_URL);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(WebResult workResult) {
                super.onPostExecute(workResult);
                if (workResult == null || workResult.errorMsg == null) {
                    Toast.makeText(UdfaultreportAddActivity.this, "新增故障提报单失败", Toast.LENGTH_SHORT).show();
                } else if (workResult.errorMsg.equals("成功")) {
                    Toast.makeText(UdfaultreportAddActivity.this, "新增故障提报单成功", Toast.LENGTH_SHORT).show();
                    setResult(100);
                    finish();
                } else {
                    Toast.makeText(UdfaultreportAddActivity.this, workResult.errorMsg, Toast.LENGTH_SHORT).show();
                }
                closeProgressDialog();
            }
        }.execute();
        //}else {
        closeProgressDialog();
    }

    private UDFAULTREPORT getUdfaultreport() {
        UDFAULTREPORT udfaultreport = new UDFAULTREPORT();
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
