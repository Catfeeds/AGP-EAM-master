package com.hsk.hxqh.agp_eam.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.WORKORDER;


/**
 * 工单新增
 */
public class WorkOrderAddNewActivity extends BaseActivity {
    private static String TAG = "WorkOrderAddNewActivity";

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

    private LinearLayout wonumLayout;
    private TextView wonum;
    private EditText description;
    private TextView location;
    private TextView locationdes;
    private TextView assetnum;
    private TextView assetdes;
    private TextView udstatus;
    private TextView jpnum;
    private TextView udfaultreportnum;
    private TextView stationdes;
    private TextView udworeqnum;
    private TextView udyearplannum;
    private TextView createbyname;
    private TextView createdate;
    private TextView udfaultdesc;
    private TextView udreason;
    private TextView schedstart;
    private TextView schedfinish;
    private TextView actstart;
    private TextView actfinish;
    private TextView udyear;
    private TextView workfrequency;
    private TextView udworkscope;
    private TextView hazardlevel;

    private WORKORDER workorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workorder_details);
        geiIntentData();
        findViewById();
        initView();
//
//        mBasIn = new BounceTopEnter();
//        mBasOut = new SlideBottomExit();
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
        wonumLayout = (LinearLayout) findViewById(R.id.wonum_layout);
        wonum = (TextView) findViewById(R.id.wonum_text_id);
        description = (EditText) findViewById(R.id.work_description_id);
        location = (TextView) findViewById(R.id.work_location_id);
        locationdes = (TextView) findViewById(R.id.work_locationdes_id);
        assetnum = (TextView) findViewById(R.id.work_assetnum_id);
        assetdes = (TextView) findViewById(R.id.work_assetdes_id);
        udstatus = (TextView) findViewById(R.id.work_udstatus_id);
        jpnum = (TextView) findViewById(R.id.work_jpnum_id);
        udfaultreportnum = (TextView) findViewById(R.id.work_udfaultreportnum_id);
        stationdes = (EditText) findViewById(R.id.work_stationdes_id);
        udworeqnum = (TextView) findViewById(R.id.work_udworeqnum_id);
        udyearplannum = (TextView) findViewById(R.id.work_udyearplannum_id);
        createbyname = (TextView) findViewById(R.id.work_createbyname_id);
        createdate = (TextView) findViewById(R.id.work_createdate_id);
        udfaultdesc = (TextView) findViewById(R.id.work_udfaultdesc_id);
        udreason = (TextView) findViewById(R.id.work_udreason_id);
        schedstart = (TextView) findViewById(R.id.work_schedstart_id);
        schedfinish = (TextView) findViewById(R.id.work_schedfinish_id);
        actstart = (TextView) findViewById(R.id.work_actstart_id);
        actfinish = (TextView) findViewById(R.id.work_actfinish_id);
        udyear = (TextView) findViewById(R.id.work_udyear_id);
        workfrequency = (TextView) findViewById(R.id.work_workfrequency_id);
        udworkscope = (TextView) findViewById(R.id.work_udworkscope_id);
        hazardlevel = (TextView) findViewById(R.id.work_hazardlevel_id);
    }


    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.asset_title);
        moreImg.setVisibility(View.VISIBLE);
        moreImg.setOnClickListener(moreImgOnClickListener);
//        submitBtn.setVisibility(View.GONE);

        wonumLayout.setVisibility(View.GONE);
        if (workorder!=null){
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
        View contentView = LayoutInflater.from(WorkOrderAddNewActivity.this).inflate(
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
        workplanLayout.setOnClickListener(workplanOnClickListener);
        workrealLayout.setOnClickListener(workrealOnClickListener);
        workflowLayout.setOnClickListener(workflowOnClickListener);

    }

    private View.OnClickListener workplanOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(WorkOrderAddNewActivity.this, Work_PlanActivity.class);
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
            Intent intent = new Intent(WorkOrderAddNewActivity.this, Work_RealActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("workorder", workorder);
//            bundle.putSerializable("woactivityList", woactivityList);
            intent.putExtras(bundle);
            startActivityForResult(intent, 2000);
            popupWindow.dismiss();
        }
    };

    private View.OnClickListener workflowOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(WorkOrderDetailsActivity.this, Asset_workorderActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("workorder", workorder);
////            bundle.putSerializable("woactivityList", woactivityList);
//            intent.putExtras(bundle);
//            startActivityForResult(intent, 3000);
//            popupWindow.dismiss();
        }
    };
}
