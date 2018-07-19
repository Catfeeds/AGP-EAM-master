package com.hsk.hxqh.agp_eam.ui.activity;

import android.animation.LayoutTransition;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.ASSET;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * 资产详情
 */
public class AssetDetailsActivity extends BaseActivity {
    private static String TAG = "AssetDetailsActivity";

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
    private LinearLayout bujianLayout;//部件
    private LinearLayout beijianLayout;//备件
    private LinearLayout workLayout;//工单历史

    private TextView assetnum;
    private EditText assetdesc;
    private EditText udfirstclass;
    private EditText firstdesc;
    private TextView udsecondclass;
    private TextView secondesc;
    private TextView udthirdclass;
    private TextView thirdesc;
    private EditText parent;
    private EditText parentdesc;
    private TextView location;
    private TextView locdesc;
    private TextView udeqmstatus;
    private TextView udmodule;
    private TextView udconnection;
    private TextView udmanufacturer;
    private TextView udsequenceno;
    private TextView udmaintime;
    private TextView udusetime;
    private TextView udremarks;

    private ASSET asset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_details);
        geiIntentData();
        findViewById();
        initView();
//
//        mBasIn = new BounceTopEnter();
//        mBasOut = new SlideBottomExit();
    }

    private void geiIntentData() {
        asset = (ASSET) getIntent().getSerializableExtra("asset");
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
        assetnum = (TextView) findViewById(R.id.assetnum_text_id);
        assetdesc = (EditText) findViewById(R.id.asset_desc_id);
        udfirstclass = (EditText) findViewById(R.id.asset_udfirstclass_id);
        firstdesc = (EditText) findViewById(R.id.asset_firstdesc_id);
        udsecondclass = (TextView) findViewById(R.id.asset_udsecondclass_id);
        secondesc = (TextView) findViewById(R.id.asset_secondesc_id);
        udthirdclass = (TextView) findViewById(R.id.asset_udthirdclass_id);
        thirdesc = (TextView) findViewById(R.id.asset_thirdesc_id);
        parent = (EditText) findViewById(R.id.asset_parent_id);
        parentdesc = (EditText) findViewById(R.id.asset_parentdesc_id);
        location = (TextView) findViewById(R.id.asset_location_id);
        locdesc = (TextView) findViewById(R.id.asset_locdesc_id);
        udeqmstatus = (TextView) findViewById(R.id.asset_udeqmstatus_id);
        udmodule = (TextView) findViewById(R.id.asset_udmodule_id);
        udconnection = (TextView) findViewById(R.id.asset_udconnection_id);
        udmanufacturer = (TextView) findViewById(R.id.asset_udmanufacturer_id);
        udsequenceno = (TextView) findViewById(R.id.asset_udsequenceno_id);
        udmaintime = (TextView) findViewById(R.id.asset_udmaintime_id);
        udusetime = (TextView) findViewById(R.id.asset_udusetime_id);
        udremarks = (TextView) findViewById(R.id.asset_udremarks_id);
    }


    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.asset_title);
        moreImg.setVisibility(View.VISIBLE);
        moreImg.setOnClickListener(moreImgOnClickListener);
//        submitBtn.setVisibility(View.GONE);

        if (asset!=null){
            assetnum.setText(asset.ASSETNUM);
            assetdesc.setText(asset.DESCRIPTION);
            udfirstclass.setText(asset.UDFIRSTCLASS);
            firstdesc.setText(asset.FIRSTDESC);
            udsecondclass.setText(asset.UDSECONDCLASS);
            secondesc.setText(asset.SECONDESC);
            udthirdclass.setText(asset.UDTHIRDCLASS);
            thirdesc.setText(asset.THIRDESC);
            parent.setText(asset.PARENT);
            parentdesc.setText(asset.PARENTDESC);
            location.setText(asset.LOCATION);
            locdesc.setText(asset.LOCDESC);
            udeqmstatus.setText(asset.UDEQMSTATUS);
            udmodule.setText(asset.UDMODULE);
            udconnection.setText(asset.UDCONNECTION);
            udmanufacturer.setText(asset.UDMANUFACTURER);
            udsequenceno.setText(asset.UDSEQUENCENO);
            udmaintime.setText(asset.UDMAINTIME);
            udusetime.setText(asset.UDUSETIME);
            udremarks.setText(asset.UDREMARKS);
        }

//        trainynCheckBox.setOnCheckedChangeListener(trainynCheckBoxOnCheckedChangeListener);
//        imageView.setOnClickListener(imageViewOnClickListener)

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
        View contentView = LayoutInflater.from(AssetDetailsActivity.this).inflate(
                R.layout.asset_popup_window, null);


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

        bujianLayout = (LinearLayout) contentView.findViewById(R.id.asset_bujian_id);
        beijianLayout = (LinearLayout) contentView.findViewById(R.id.asset_beijian_id);
        workLayout = (LinearLayout) contentView.findViewById(R.id.asset_workorder_id);
        bujianLayout.setOnClickListener(bujianOnClickListener);
        beijianLayout.setOnClickListener(beijianOnClickListener);
        workLayout.setOnClickListener(workOnClickListener);

    }

    private View.OnClickListener bujianOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AssetDetailsActivity.this, Asset_bujianActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("asset", asset);
//            bundle.putSerializable("woactivityList", woactivityList);
            intent.putExtras(bundle);
            startActivityForResult(intent, 1000);
            popupWindow.dismiss();
        }
    };

    private View.OnClickListener beijianOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AssetDetailsActivity.this, Asset_beijianActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("asset", asset);
//            bundle.putSerializable("woactivityList", woactivityList);
            intent.putExtras(bundle);
            startActivityForResult(intent, 2000);
            popupWindow.dismiss();
        }
    };

    private View.OnClickListener workOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AssetDetailsActivity.this, Asset_workorderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("asset", asset);
//            bundle.putSerializable("woactivityList", woactivityList);
            intent.putExtras(bundle);
            startActivityForResult(intent, 3000);
            popupWindow.dismiss();
        }
    };
}
