package com.hsk.hxqh.agp_eam.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.config.Constants;
import com.hsk.hxqh.agp_eam.model.UDSTOCKLINE;
import com.hsk.hxqh.agp_eam.model.WebResult;
import com.hsk.hxqh.agp_eam.ui.activity.invuse.InvusemiAddNewActivity;
import com.hsk.hxqh.agp_eam.webserviceclient.AndroidClientService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zzw on 2018/6/12.
 */

public class UdstocklineDetailActivity extends BaseActivity{
    private static final int UDSTOCKLINE_CODE = 1111;
    private ImageView backImageView;
    private TextView titleTextView;
    private ImageView menuImageView;
    private TextView itemnumView;
    private TextView descView;
    private TextView orderunitView;
    private TextView quantity1View;
    private EditText quantity2View;
    private TextView lotnumView,binnumView;
    private TextView differenceView;
    private EditText reasonsView;
    private Button certain;
    private UDSTOCKLINE udstockline;
    private int position;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private  String json;
    private LinearLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udstockline_details);
        findViewById();
        initView();
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);
        menuImageView = (ImageView) findViewById(R.id.title_more);
        itemnumView = (TextView) findViewById(R.id.udstockline_itemnum);
        descView = (TextView) findViewById(R.id.udstockline_description);
        quantity1View = (TextView) findViewById(R.id.udstockline_quantity1);
        quantity2View = (EditText) findViewById(R.id.udstockline_quantity2);
        differenceView = (TextView) findViewById(R.id.udstockline_difference);
        reasonsView = (EditText) findViewById(R.id.udstockline_reasons);
        lotnumView = (TextView) findViewById(R.id.udstockline_lotnum);
        orderunitView = (TextView) findViewById(R.id.udstockline_orderunit);
        certain = (Button)findViewById(R.id.certain_id);
        container = (LinearLayout) findViewById(R.id.container);
        binnumView = (TextView) findViewById(R.id.udstockline_binnum);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView() {
        getIntentData();
        if (udstockline.getISCHECK().equalsIgnoreCase("y")){
            container.setBackgroundColor(R.color.forestgreen);
        }
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backOnClickListener);
        titleTextView.setText(getResources().getString(R.string.udstockline));
        itemnumView.setText(udstockline.getITEMNUM());
        differenceView.setText(udstockline.getDIFFERENCE());
        differenceView.setBackgroundColor(Color.GRAY);
        descView.setText(udstockline.getUDSTOCKLINEITEDES());
        quantity1View.setText(udstockline.getQUANTITY1());
        lotnumView.setText(udstockline.getLOTNUM());
        certain.setOnClickListener(certainOnClickListener);
        orderunitView.setText(udstockline.getUDSTOCKLINEITEISS());
        quantity2View.setText(udstockline.getQUANTITY2());
        reasonsView.setText(udstockline.getREASON());
        binnumView.setText(udstockline.getBINNUM());

    }

    public void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        udstockline =(UDSTOCKLINE)bundle.get("udstockline");
        position = (Integer)bundle.get("position");
    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    private View.OnClickListener certainOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            udstockline.setQUANTITY2(quantity2View.getText().toString());
            udstockline.setREASON(reasonsView.getText().toString());
            submitDataInfo();
        }
    };
    /**
     * 提交数据*
     */
    private void submitDataInfo() {
        final NormalDialog dialog = new NormalDialog(UdstocklineDetailActivity.this);
        dialog.title(getString(R.string.tip)).btnText(getString(R.string.cancel),getString(R.string.confirm));
        dialog.content(getString(R.string.suretosave))//
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
                        startAsyncTask();
                        dialog.dismiss();
                    }
                });
    }
    public void startAsyncTask(){
      String num2 =  quantity2View.getText().toString();
      String num1 = udstockline.getQUANTITY1();
      if (num2 != null && !num2.equals("")){
          udstockline.setDIFFERENCE((Integer.parseInt(num2)-Integer.parseInt(num1)) + "");
      }
        udstockline.setQUANTITY2(quantity2View.getText().toString());

        udstockline.setREASON(reasonsView.getText().toString());
        json = JsonUtils.submitUdstocklineData(udstockline);
        if (num1!=num2 && reasonsView.getText().toString().equals("")){
            Toast.makeText(this, getString(R.string.udstockline_reason),Toast.LENGTH_SHORT).show();
        }else {
            showProgressDialog("Waiting...");
            udstockline.setISCHECK("1");
            new AsyncTask<String,String,String>(){

                @Override
                protected String doInBackground(String... strings) {


                    String s = "";
                    WebResult result= AndroidClientService.UpdateWO(UdstocklineDetailActivity.this,json,Constants.UDSTOCKLINE_NAME,"UDSTOCKLINEID",udstockline.getUDSTOCKLINEID()+ "",Constants.WORK_URL);
                    s= result.errorMsg;
                    return s;

                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if (s == null || s.equalsIgnoreCase("")) {
                        Toast.makeText(UdstocklineDetailActivity.this, "Confim fail", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UdstocklineDetailActivity.this, s, Toast.LENGTH_SHORT).show();
                        if (s.equalsIgnoreCase("成功")){
                            udstockline.setISCHECK("Y");
                            Intent intent = getIntent();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("udstockline",udstockline);
                            bundle.putInt("position",position);
                            intent.putExtras(bundle);
                            setResult(UDSTOCKLINE_CODE,intent);
                            finish();
                        }
                    }
                    closeProgressDialog();
                }
            }.execute();

        }

    }
}
