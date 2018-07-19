package com.hsk.hxqh.agp_eam.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.hsk.hxqh.agp_eam.model.COMPANIES;
import com.hsk.hxqh.agp_eam.model.JOBPLAN;
import com.hsk.hxqh.agp_eam.model.LOCATIONS;
import com.hsk.hxqh.agp_eam.model.PERSON;
import com.hsk.hxqh.agp_eam.model.UDYEARPLAN;
import com.hsk.hxqh.agp_eam.model.WOACTIVITY;
import com.hsk.hxqh.agp_eam.model.WORKORDER;
import com.hsk.hxqh.agp_eam.model.WPMATERIAL;
import com.hsk.hxqh.agp_eam.model.WebResult;
import com.hsk.hxqh.agp_eam.ui.activity.option.Companies_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Location_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Person_chooseActivity;
import com.hsk.hxqh.agp_eam.webserviceclient.AndroidClientService;

import java.util.ArrayList;


/**
 * 工单计划物料详情
 */
public class WpmaterialDetailsActivity extends BaseActivity {
    private static String TAG = "WpmaterialDetailsActivity";

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
    private TextView taskid; //任务
    private TextView itemnum; //项目
    private TextView description; //描述
    private TextView itemqty; //数量
    private TextView location; //库房
    private ImageView locationImg;
    private TextView vendor;//供应商
    private ImageView vendorImg;
    private TextView requestby;//请求者
    private ImageView requestbyImg;
    private TextView requiredate;//要求的日期

    /**
     * 任务
     **/
    private WPMATERIAL wpmaterial;
    private WORKORDER workorder;
    private ArrayList<WPMATERIAL> wpmaterials;

    private Button cancel;//取消
    private Button save;//保存

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wpmaterial_details);
        geiIntentData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void geiIntentData() {
        workorder = (WORKORDER) getIntent().getSerializableExtra("workorder");
        wpmaterial = (WPMATERIAL) getIntent().getSerializableExtra("wpmaterial");
        wpmaterials = (ArrayList<WPMATERIAL>) getIntent().getSerializableExtra("wpmaterials");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);

        taskid = (TextView) findViewById(R.id.wpmaterial_taskid);
        itemnum = (TextView) findViewById(R.id.wpmaterial_itemnum);
        description = (TextView) findViewById(R.id.wpmaterial_description);
        itemqty = (TextView) findViewById(R.id.wpmaterial_itemqty);
        location = (TextView) findViewById(R.id.wpmaterial_location);
        locationImg = (ImageView) findViewById(R.id.wpmaterial_location_image_id);
        vendor = (TextView) findViewById(R.id.wpmaterial_vendor);
        vendorImg = (ImageView) findViewById(R.id.wpmaterial_vendor_image_id);
        requestby = (TextView) findViewById(R.id.wpmaterial_requestby);
        requestbyImg = (ImageView) findViewById(R.id.wpmaterial_requestby_image_id);
        requiredate = (TextView) findViewById(R.id.wpmaterial_requiredate);

        cancel = (Button) findViewById(R.id.work_cancel);
        save = (Button) findViewById(R.id.work_save);
    }


    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.work_wpmaterial);

        if (wpmaterial != null) {
            taskid.setText(wpmaterial.TASKID);
            itemnum.setText(wpmaterial.ITEMNUM);
            description.setText(wpmaterial.DESCRIPTION);
            itemqty.setText(wpmaterial.ITEMQTY);
            location.setText(wpmaterial.LOCATION);
            vendor.setText(wpmaterial.VENDOR);
            requestby.setText(wpmaterial.REQUESTBY);
            requiredate.setText(wpmaterial.REQUIREDATE);

        }

        locationImg.setOnClickListener(locationOnClickListener);
        requestbyImg.setOnClickListener(personOnClickListener);
        vendorImg.setOnClickListener(vendorOnClickListener);

        cancel.setOnClickListener(backImageViewOnClickListener);
        save.setOnClickListener(saveOnClickListener);
    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener locationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WpmaterialDetailsActivity.this, Location_chooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener personOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WpmaterialDetailsActivity.this, Person_chooseActivity.class);
            startActivityForResult(intent, 1);
        }
    };

    private View.OnClickListener vendorOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WpmaterialDetailsActivity.this, Companies_chooseActivity.class);
            startActivityForResult(intent, 2);
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
        final NormalDialog dialog = new NormalDialog(WpmaterialDetailsActivity.this);
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
        updataInfo = JsonUtils.WorkToJson(workorder,null,null,getWpmaterials(),null,null);
//            } else if (workOrder.status.equals(Constants.APPROVALED)) {
//                updataInfo = JsonUtils.WorkToJson(getWorkOrder(), null, null, null, null, getLabtransList());
//            }
        final String finalUpdataInfo = updataInfo;
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult reviseresult = AndroidClientService.UpdateWO(WpmaterialDetailsActivity.this, finalUpdataInfo,
                        "WORKORDER", "WONUM", workorder.WONUM, Constants.WORK_URL);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(WebResult workResult) {
                super.onPostExecute(workResult);
                if (workResult == null || workResult.errorMsg == null) {
                    Toast.makeText(WpmaterialDetailsActivity.this, "修改工单失败", Toast.LENGTH_SHORT).show();
                } else if (workResult.errorMsg.equals("成功")) {
                    Toast.makeText(WpmaterialDetailsActivity.this, "修改工单成功", Toast.LENGTH_SHORT).show();
                    setResult(100);
                    finish();
                } else {
                    Toast.makeText(WpmaterialDetailsActivity.this, workResult.errorMsg, Toast.LENGTH_SHORT).show();
                }
                closeProgressDialog();
            }
        }.execute();
        //}else {
        closeProgressDialog();
    }

    private ArrayList<WPMATERIAL> getWpmaterials(){
        WPMATERIAL wpmaterial = this.wpmaterial;
        wpmaterial.setLOCATION(location.getText().toString());
        wpmaterial.setREQUESTBY(requestby.getText().toString());
        wpmaterial.setVENDOR(vendor.getText().toString());
        wpmaterial.setTYPE("update");
        for (int i =0;i<wpmaterials.size();i ++){
            if (wpmaterials.get(i).getTASKID().equals(wpmaterial.getTASKID())){
                wpmaterials.set(i,wpmaterial);
            }
        }
        return wpmaterials;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (requestCode) {
                case 0:
                    LOCATIONS asset = (LOCATIONS) data.getSerializableExtra("location");
                    location.setText(asset.getLOCATION());
                    break;
                case 1:
                    PERSON person = (PERSON) data.getSerializableExtra("person");
                    requestby.setText(person.getPERSONID());
                    break;
                case 2:
                    COMPANIES companies = (COMPANIES) data.getSerializableExtra("companies");
                    vendor.setText(companies.getCOMPANY());
                    break;
            }
        }
    }
}
