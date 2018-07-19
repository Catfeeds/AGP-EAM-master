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
import com.hsk.hxqh.agp_eam.model.LOCATIONS;
import com.hsk.hxqh.agp_eam.model.MATUSETRANS;
import com.hsk.hxqh.agp_eam.model.PERSON;
import com.hsk.hxqh.agp_eam.model.WOACTIVITY;
import com.hsk.hxqh.agp_eam.model.WORKORDER;
import com.hsk.hxqh.agp_eam.model.WPMATERIAL;
import com.hsk.hxqh.agp_eam.model.WebResult;
import com.hsk.hxqh.agp_eam.ui.activity.option.Location_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Person_chooseActivity;
import com.hsk.hxqh.agp_eam.webserviceclient.AndroidClientService;

import java.util.ArrayList;


/**
 * 工单实际物料详情
 */
public class MatusetransDetailsActivity extends BaseActivity {
    private static String TAG = "MatusetransDetailsActivity";

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
    private TextView itemnum; //项目
    private TextView description; //描述
    private TextView storeloc; //库房
    private ImageView storelocImg;
    private TextView positivequantity; //数量
    private TextView binnum;//货柜
    private TextView lotnum;//批次
    private TextView rotassetnum;//周转资产
    private TextView issuetype;//交易类型
    private TextView enterby;//输入人
    private ImageView enterbyImg;
    private TextView actualdate;//实际日期

    /**
     * 物料
     **/
    private MATUSETRANS matusetrans;
    private WORKORDER workorder;
    private ArrayList<MATUSETRANS> matusetranses;

    private Button cancel;//取消
    private Button save;//保存

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matusetrans_details);
        geiIntentData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void geiIntentData() {
        workorder = (WORKORDER) getIntent().getSerializableExtra("workorder");
        matusetrans = (MATUSETRANS) getIntent().getSerializableExtra("matusetrans");
        matusetranses = (ArrayList<MATUSETRANS>) getIntent().getSerializableExtra("matusetranses");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);

        actualstaskid = (TextView) findViewById(R.id.matusetrans_actualstaskid);
        itemnum = (TextView) findViewById(R.id.matusetrans_itemnum);
        description = (TextView) findViewById(R.id.matusetrans_description);
        storeloc = (TextView) findViewById(R.id.matusetrans_storeloc);
        storelocImg = (ImageView) findViewById(R.id.matusetrans_storeloc_image_id);
        positivequantity = (TextView) findViewById(R.id.matusetrans_positivequantity);
        binnum = (TextView) findViewById(R.id.matusetrans_binnum);
        lotnum = (TextView) findViewById(R.id.matusetrans_lotnum);
        rotassetnum = (TextView) findViewById(R.id.matusetrans_rotassetnum);
        issuetype = (TextView) findViewById(R.id.matusetrans_issuetype);
        enterby = (TextView) findViewById(R.id.matusetrans_enterby);
        enterbyImg = (ImageView) findViewById(R.id.matusetrans_enterby_image_id);
        actualdate = (TextView) findViewById(R.id.matusetrans_actualdate);

        cancel = (Button) findViewById(R.id.work_cancel);
        save = (Button) findViewById(R.id.work_save);
    }


    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.work_matusetrans);

        if (matusetrans != null) {
            actualstaskid.setText(matusetrans.ACTUALSTASKID);
            itemnum.setText(matusetrans.ITEMNUM);
            description.setText(matusetrans.DESCRIPTION);
            storeloc.setText(matusetrans.STORELOC);
            positivequantity.setText(matusetrans.POSITIVEQUANTITY);
            binnum.setText(matusetrans.BINNUM);
            lotnum.setText(matusetrans.LOTNUM);
            rotassetnum.setText(matusetrans.ROTASSETNUM);
            issuetype.setText(matusetrans.ISSUETYPE);
            enterby.setText(matusetrans.ENTERBY);
            actualdate.setText(matusetrans.ACTUALDATE);

        }

        storelocImg.setOnClickListener(locationOnClickListener);
        enterbyImg.setOnClickListener(personOnClickListener);

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
            Intent intent = new Intent(MatusetransDetailsActivity.this, Location_chooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener personOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MatusetransDetailsActivity.this, Person_chooseActivity.class);
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
        final NormalDialog dialog = new NormalDialog(MatusetransDetailsActivity.this);
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
        updataInfo = JsonUtils.WorkToJson(workorder,null,null,null,null,getMatusetranses());
//            } else if (workOrder.status.equals(Constants.APPROVALED)) {
//                updataInfo = JsonUtils.WorkToJson(getWorkOrder(), null, null, null, null, getLabtransList());
//            }
        final String finalUpdataInfo = updataInfo;
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult reviseresult = AndroidClientService.UpdateWO(MatusetransDetailsActivity.this, finalUpdataInfo,
                        "WORKORDER", "WONUM", workorder.WONUM, Constants.WORK_URL);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(WebResult workResult) {
                super.onPostExecute(workResult);
                if (workResult == null || workResult.errorMsg == null) {
                    Toast.makeText(MatusetransDetailsActivity.this, "修改工单失败", Toast.LENGTH_SHORT).show();
                } else if (workResult.errorMsg.equals("成功")) {
                    Toast.makeText(MatusetransDetailsActivity.this, "修改工单成功", Toast.LENGTH_SHORT).show();
                    setResult(100);
                    finish();
                } else {
                    Toast.makeText(MatusetransDetailsActivity.this, workResult.errorMsg, Toast.LENGTH_SHORT).show();
                }
                closeProgressDialog();
            }
        }.execute();
    }

    private ArrayList<MATUSETRANS> getMatusetranses(){
        MATUSETRANS matusetrans = this.matusetrans;
        matusetrans.setSTORELOC(storeloc.getText().toString());
        matusetrans.setENTERBY(enterby.getText().toString());
        matusetrans.setTYPE("update");
        for (int i =0;i<matusetranses.size();i ++){
            if (matusetranses.get(i).getACTUALSTASKID().equals(matusetrans.getACTUALSTASKID())){
                matusetranses.set(i,matusetrans);
            }
        }
        return matusetranses;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (requestCode) {
                case 0:
                    LOCATIONS asset = (LOCATIONS) data.getSerializableExtra("location");
                    storeloc.setText(asset.getLOCATION());
                    break;
                case 1:
                    PERSON person = (PERSON) data.getSerializableExtra("person");
                    enterby.setText(person.getPERSONID());
                    break;
            }
        }
    }
}
