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
import com.flyco.dialog.widget.MaterialDialog2;
import com.flyco.dialog.widget.NormalDialog;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.config.Constants;
import com.hsk.hxqh.agp_eam.model.ITEM;
import com.hsk.hxqh.agp_eam.model.PO;
import com.hsk.hxqh.agp_eam.model.UDSTOCK;
import com.hsk.hxqh.agp_eam.model.WebResult;
import com.hsk.hxqh.agp_eam.ui.activity.invuse.InvuseDetailActivity;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;
import com.hsk.hxqh.agp_eam.webserviceclient.AndroidClientService;

import java.util.ArrayList;


/**
 * 库存盘点详情
 */
public class UdstockDetailsActivity extends BaseActivity {
    private static String TAG = "UdstockDetailsActivity";

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
    private LinearLayout udstocklineLayout;//物资信息
//    private LinearLayout workrealLayout;//实际情况
//    private LinearLayout workflowLayout;//发送工作流
    private LinearLayout commitLayout;//上传附件

    private TextView stocknum;//盘点单号
    private TextView description;//描述
    private TextView storeroom;//库房
    private TextView udstockstodes;//库房名称
    private TextView invowner;//库房管理员
    private TextView udstockinvdis;//管理员姓名
    private TextView remark;//备注
    private TextView status;//状态
    private TextView udstockcredis;//创建人姓名
    private TextView createdate;//创建日期

    private UDSTOCK udstock;

    private Button cancel;//取消
    private Button save;//保存

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private ArrayList<DialogMenuItem> mMenuItems = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private LinearLayout startwf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udstock_details);
        geiIntentData();
        findViewById();
        initView();
//
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void geiIntentData() {
        udstock = (UDSTOCK) getIntent().getSerializableExtra("udstock");
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
        stocknum = (TextView) findViewById(R.id.udstock_stocknum);
        description = (TextView) findViewById(R.id.udstock_description);
        storeroom = (TextView) findViewById(R.id.udstock_storeroom);
        udstockstodes = (TextView) findViewById(R.id.udstock_udstockstodes);
        invowner = (TextView) findViewById(R.id.udstock_invowner);
        udstockinvdis = (TextView) findViewById(R.id.udstock_udstockinvdis);
        remark = (TextView) findViewById(R.id.udstock_remark);
        status = (TextView) findViewById(R.id.udstock_status);
        udstockcredis = (TextView) findViewById(R.id.udstock_udstockcredis);
        createdate = (TextView) findViewById(R.id.udstock_createdate);

        cancel = (Button) findViewById(R.id.work_cancel);
        save = (Button) findViewById(R.id.work_save);
    }


    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.udstock);
        moreImg.setVisibility(View.VISIBLE);
        moreImg.setOnClickListener(moreImgOnClickListener);
//        submitBtn.setVisibility(View.GONE);

        if (udstock != null) {
            stocknum.setText(udstock.STOCKNUM);
            description.setText(udstock.DESCRIPTION);
            storeroom.setText(udstock.STOREROOM);
            udstockstodes.setText(udstock.UDSTOCKSTODES);
            invowner.setText(udstock.INVOWNER);
            udstockinvdis.setText(udstock.UDSTOCKINVDIS);
            remark.setText(udstock.REMARK);
            status.setText(udstock.STATUS);
            udstockcredis.setText(udstock.UDSTOCKCREDIS);
            createdate.setText(udstock.CREATEDATE);

        }
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
        View contentView = LayoutInflater.from(UdstockDetailsActivity.this).inflate(
                R.layout.udstock_popup_window, null);


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

        udstocklineLayout = (LinearLayout) contentView.findViewById(R.id.udstockline_id);
        commitLayout = (LinearLayout) contentView.findViewById(R.id.work_upload_id);
        startwf = (LinearLayout) contentView.findViewById(R.id.start_workflow);
        startwf.setOnClickListener(startWfOnClickListener);
        udstocklineLayout.setOnClickListener(udstocklineOnClickListener);
        commitLayout.setOnClickListener(commitLayoutOnClickListener);

    }

    private View.OnClickListener udstocklineOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(UdstockDetailsActivity.this, UdstockLineActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("udstock", udstock);
//            bundle.putSerializable("woactivityList", woactivityList);
            intent.putExtras(bundle);
            startActivityForResult(intent, 1000);
            popupWindow.dismiss();
        }
    };
    private View.OnClickListener commitLayoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent1 = new Intent(UdstockDetailsActivity.this,  WxDemoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ownertable", UDSTOCK.TABLE_NAME);
            bundle.putString("ownerid",udstock.getSTOCKNUM());
            intent1.putExtras(bundle);
            startActivity(intent1);
            popupWindow.dismiss();
        }
    };
    private View.OnClickListener startWfOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MaterialDialogOneBtn();
            popupWindow.dismiss();
        }
    };

    private void MaterialDialogOneBtn() {//开始工作流
        final MaterialDialog2 dialog = new MaterialDialog2(this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content(getString(R.string.startwf))//
                .btnText(getString(R.string.yes), getString(R.string.no))//
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
    /**
     * 开始工作流
     */
    private void startWF() {
        mProgressDialog = ProgressDialog.show(UdstockDetailsActivity.this, null,
                getString(R.string.start), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult result = AndroidClientService.startwf(UdstockDetailsActivity.this,
                        "UDSTOCK", Constants.UDSTOCK_NAME,udstock.getSTOCKNUM(), "STOCKNUM", AccountUtils.getpersonId(UdstockDetailsActivity.this));
                return result;
            }

            @Override
            protected void onPostExecute(WebResult s) {
                super.onPostExecute(s);
                if (s != null && s.errorMsg != null && s.errorMsg.equals("工作流启动成功")) {
                    Toast.makeText(UdstockDetailsActivity.this, s.errorMsg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UdstockDetailsActivity.this, "工作流启动失败", Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();
            }
        }.execute();
    }


}
