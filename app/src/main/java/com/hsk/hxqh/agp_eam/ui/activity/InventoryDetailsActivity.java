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
import com.hsk.hxqh.agp_eam.model.ASSET;
import com.hsk.hxqh.agp_eam.model.INVENTORY;


/**
 * 库存详情
 */
public class InventoryDetailsActivity extends BaseActivity {
    private static String TAG = "InventoryDetailsActivity";

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
    private LinearLayout invbalancesLayout;//库存余量\

    private TextView itemnum;
    private EditText itemnum_dec;
    private EditText location;
    private TextView location_dec;
    private TextView status;
    private TextView issueunit;
    private TextView curbaltotal;
    private TextView avblbalance;
    private TextView isRotatiing;

    private INVENTORY inventory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_details);
        geiIntentData();
        findViewById();
        initView();
//
//        mBasIn = new BounceTopEnter();
//        mBasOut = new SlideBottomExit();
    }

    private void geiIntentData() {
        inventory = (INVENTORY) getIntent().getSerializableExtra("inventory");
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
        itemnum = (TextView) findViewById(R.id.inventory_itemnum);
        itemnum_dec = (EditText) findViewById(R.id.inventory_itemnum_dec);
        location = (EditText) findViewById(R.id.inventory_location);
        location_dec = (TextView) findViewById(R.id.inventory_location_dec);
        //status = (TextView) findViewById(R.id.inventory_status);
        //issueunit = (TextView) findViewById(R.id.inventory_issueunit);
        curbaltotal = (TextView) findViewById(R.id.inventory_curbaltotal);
        //avblbalance = (TextView) findViewById(R.id.inventory_avblbalance);
    }


    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.inventory);
        moreImg.setVisibility(View.VISIBLE);
        moreImg.setOnClickListener(moreImgOnClickListener);
//        submitBtn.setVisibility(View.GONE);

        if (inventory!=null){
            itemnum.setText(inventory.ITEMNUM);
            itemnum_dec.setText(inventory.ITEMNUM_DEC);
            location.setText(inventory.LOCATION);
            location_dec.setText(inventory.LOCATION_DEC);
            curbaltotal.setText(inventory.CURBALTOTAL);
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
        View contentView = LayoutInflater.from(InventoryDetailsActivity.this).inflate(
                R.layout.inventory_popup_window, null);


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

        invbalancesLayout = (LinearLayout) contentView.findViewById(R.id.invbalances_id);
        invbalancesLayout.setOnClickListener(invbalancesOnClickListener);

    }

    private View.OnClickListener invbalancesOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(InventoryDetailsActivity.this, Inventory_InvbalancesActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("inventory", inventory);
//            bundle.putSerializable("woactivityList", woactivityList);
            intent.putExtras(bundle);
            startActivityForResult(intent, 1000);
            popupWindow.dismiss();
        }
    };
}
