package com.hsk.hxqh.agp_eam.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
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


/**
 * 工作申请新增
 */
public class UdworkapplyAddActivity extends BaseActivity {
    private static String TAG = "UdworkapplyAddActivity";

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
    private LinearLayout woapplynumLayout;
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
//    private UDWORKAPPLY udworkapply;

    private Button cancel;//取消
    private Button save;//保存

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;


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
//        udworkapply = (UDWORKAPPLY) getIntent().getSerializableExtra("udworkapply");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);

        woapplynumLayout = (LinearLayout) findViewById(R.id.woapplynum_layout);
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

        woapplynumLayout.setVisibility(View.GONE);
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
            Intent intent = new Intent(UdworkapplyAddActivity.this, Asset_chooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener locationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UdworkapplyAddActivity.this, Location_chooseActivity.class);
            startActivityForResult(intent, 1);
        }
    };

    private View.OnClickListener udyearplannumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UdworkapplyAddActivity.this, Udyearplan_chooseActivity.class);
            startActivityForResult(intent, 2);
        }
    };

    private View.OnClickListener jpnumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UdworkapplyAddActivity.this, Jobplan_chooseActivity.class);
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
            new DateTimeSelect(UdworkapplyAddActivity.this, textView).showDialog();
        }
    }

    /**
     * 提交数据*
     */
    private void submitDataInfo() {
        final NormalDialog dialog = new NormalDialog(UdworkapplyAddActivity.this);
        dialog.content("确定新增工作申请吗?")//
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
                WebResult reviseresult = AndroidClientService.InsertWO(UdworkapplyAddActivity.this, finalUpdataInfo,
                        "UDWORKAPPLY", "WOAPPLYNUM", AccountUtils.getpersonId(UdworkapplyAddActivity.this), Constants.WORK_URL);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(WebResult workResult) {
                super.onPostExecute(workResult);
                if (workResult == null || workResult.errorMsg == null) {
                    Toast.makeText(UdworkapplyAddActivity.this, "新增工作申请失败", Toast.LENGTH_SHORT).show();
                } else if (workResult.errorMsg.equals("成功")) {
                    Toast.makeText(UdworkapplyAddActivity.this, "新增工作申请成功", Toast.LENGTH_SHORT).show();
                    setResult(100);
                    finish();
                } else {
                    Toast.makeText(UdworkapplyAddActivity.this, workResult.errorMsg, Toast.LENGTH_SHORT).show();
                }
                closeProgressDialog();
            }
        }.execute();
        //}else {
        closeProgressDialog();
    }

    private UDWORKAPPLY getUdworkapply() {
        UDWORKAPPLY udworkapply = new UDWORKAPPLY();
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
