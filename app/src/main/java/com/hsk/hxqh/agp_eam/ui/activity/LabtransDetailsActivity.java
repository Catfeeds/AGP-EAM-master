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
import com.hsk.hxqh.agp_eam.model.CRAFTRATE;
import com.hsk.hxqh.agp_eam.model.LABTRANS;
import com.hsk.hxqh.agp_eam.model.WOACTIVITY;
import com.hsk.hxqh.agp_eam.model.WORKORDER;
import com.hsk.hxqh.agp_eam.model.WebResult;
import com.hsk.hxqh.agp_eam.ui.activity.option.Craftrate_chooseActivity;
import com.hsk.hxqh.agp_eam.unit.DateSelect;
import com.hsk.hxqh.agp_eam.unit.TimeSelect;
import com.hsk.hxqh.agp_eam.webserviceclient.AndroidClientService;

import java.util.ArrayList;


/**
 * 工单实际员工详情
 */
public class LabtransDetailsActivity extends BaseActivity {
    private static String TAG = "LabtransDetailsActivity";

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
    private TextView actualstaskid; //任务
    private TextView laborcode; //员工
    private ImageView laborcodeImg;
    private TextView startdate; //开始日期
    private ImageView startdateImg;
    private TextView starttime; //开始时间
    private ImageView starttimeImg;
    private TextView finishdate; //结束日期
    private ImageView finishdateImg;
    private TextView finishtime;//结束时间
    private ImageView finishtimeImg;
    private TextView regularhrs;//常规时数

    /**
     * 任务
     **/
    private LABTRANS labtrans;
    private WORKORDER workorder;
    private ArrayList<LABTRANS> labtranses;

    private Button cancel;//取消
    private Button save;//保存

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labtrans_details);
        geiIntentData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void geiIntentData() {
        workorder = (WORKORDER) getIntent().getSerializableExtra("workorder");
        labtrans = (LABTRANS) getIntent().getSerializableExtra("labtrans");
        labtranses = (ArrayList<LABTRANS>) getIntent().getSerializableExtra("labtranses");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);

        actualstaskid = (TextView) findViewById(R.id.labtrans_actualstaskid);
        laborcode = (TextView) findViewById(R.id.labtrans_laborcode);
        laborcodeImg = (ImageView) findViewById(R.id.labtrans_laborcode_image_id);
        startdate = (TextView) findViewById(R.id.labtrans_startdate);
        startdateImg = (ImageView) findViewById(R.id.labtrans_startdate_image_id);
        starttime = (TextView) findViewById(R.id.labtrans_starttime);
        starttimeImg = (ImageView) findViewById(R.id.labtrans_starttime_image_id);
        finishdate = (TextView) findViewById(R.id.labtrans_finishdate);
        finishdateImg = (ImageView) findViewById(R.id.labtrans_finishdate_image_id);
        finishtime = (TextView) findViewById(R.id.labtrans_finishtime);
        finishtimeImg = (ImageView) findViewById(R.id.labtrans_finishtime_image_id);
        regularhrs = (TextView) findViewById(R.id.labtrans_regularhrs);

        cancel = (Button) findViewById(R.id.work_cancel);
        save = (Button) findViewById(R.id.work_save);
    }


    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.work_labtrans);

        if (labtrans != null) {
            actualstaskid.setText(labtrans.ACTUALSTASKID);
            laborcode.setText(labtrans.LABORCODE);
            startdate.setText(labtrans.STARTDATE);
            starttime.setText(labtrans.STARTTIME);
            finishdate.setText(labtrans.FINISHDATE);
            finishtime.setText(labtrans.FINISHTIME);
            regularhrs.setText(labtrans.REGULARHRS);

        }

        laborcodeImg.setOnClickListener(laborcodeOnClickListener);
        startdateImg.setOnClickListener(new DateOnClickListener(startdate));
        finishdateImg.setOnClickListener(new DateOnClickListener(finishdate));
        starttimeImg.setOnClickListener(new TimeOnClickListener(starttime));
        finishtimeImg.setOnClickListener(new TimeOnClickListener(finishtime));

        cancel.setOnClickListener(backImageViewOnClickListener);
        save.setOnClickListener(saveOnClickListener);
    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    //时间选择监听
    private class DateOnClickListener implements View.OnClickListener {
        TextView textView;

        private DateOnClickListener(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onClick(View view) {
            new DateSelect(LabtransDetailsActivity.this, textView).showDialog();
        }
    }

    //时间选择监听
    private class TimeOnClickListener implements View.OnClickListener {
        TextView textView;

        private TimeOnClickListener(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onClick(View view) {
            new TimeSelect(LabtransDetailsActivity.this, textView).showDialog();
        }
    }

    private View.OnClickListener laborcodeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LabtransDetailsActivity.this, Craftrate_chooseActivity.class);
            startActivityForResult(intent, 0);
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
        final NormalDialog dialog = new NormalDialog(LabtransDetailsActivity.this);
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
        updataInfo = JsonUtils.WorkToJson(workorder,null,null,null,getLabtranses(),null);
//            } else if (workOrder.status.equals(Constants.APPROVALED)) {
//                updataInfo = JsonUtils.WorkToJson(getWorkOrder(), null, null, null, null, getLabtransList());
//            }
        final String finalUpdataInfo = updataInfo;
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult reviseresult = AndroidClientService.UpdateWO(LabtransDetailsActivity.this, finalUpdataInfo,
                        "WORKORDER", "WONUM", workorder.WONUM, Constants.WORK_URL);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(WebResult workResult) {
                super.onPostExecute(workResult);
                if (workResult == null || workResult.errorMsg == null) {
                    Toast.makeText(LabtransDetailsActivity.this, "修改工单失败", Toast.LENGTH_SHORT).show();
                } else if (workResult.errorMsg.equals("成功")) {
                    Toast.makeText(LabtransDetailsActivity.this, "修改工单成功", Toast.LENGTH_SHORT).show();
                    setResult(100);
                    finish();
                } else {
                    Toast.makeText(LabtransDetailsActivity.this, workResult.errorMsg, Toast.LENGTH_SHORT).show();
                }
                closeProgressDialog();
            }
        }.execute();
        //}else {
        closeProgressDialog();
    }

    private ArrayList<LABTRANS> getLabtranses(){
        LABTRANS labtrans = this.labtrans;
        labtrans.setLABORCODE(laborcode.getText().toString());
        labtrans.setTYPE("update");
        for (int i =0;i<labtranses.size();i ++){
            if (labtranses.get(i).getACTUALSTASKID().equals(labtrans.getACTUALSTASKID())){
                labtranses.set(i,labtrans);
            }
        }
        return labtranses;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (requestCode) {
                case 0:
                    CRAFTRATE craftrate = (CRAFTRATE) data.getSerializableExtra("craftrate");
                    laborcode.setText(craftrate.getCRAFT());
                    break;
            }
        }
    }
}
