package com.hsk.hxqh.agp_eam.ui.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.manager.AppManager;
import com.hsk.hxqh.agp_eam.ui.fragment.AssetFragment;
import com.hsk.hxqh.agp_eam.ui.fragment.InventoryFragment;
import com.hsk.hxqh.agp_eam.ui.fragment.PoFragment;
import com.hsk.hxqh.agp_eam.ui.fragment.TaskFragment;
import com.hsk.hxqh.agp_eam.ui.fragment.UdworkapplyFragment;
import com.hsk.hxqh.agp_eam.ui.fragment.WorkOrderFragment;
import com.hsk.hxqh.agp_eam.ui.widget.SlidingMenu;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;

public class MainActivity extends BaseActivity {

    private SlidingMenu mMenu;
    private TextView menutitle;

    /**
     * 用户名显示
     **/
    private TextView userNameText;


    private RelativeLayout TaskLayout;//代办任务
    private RelativeLayout AssetLayout;//资产查询
    private RelativeLayout UdworkapplyLayout;//工作申请
    private RelativeLayout WorkOrderLayout;//工单管理
    private RelativeLayout KuCunLayout;//库存管理
//    private RelativeLayout PoLayout;//采购管理

    private TaskFragment taskFragment;
    private AssetFragment assetFragment;
    private UdworkapplyFragment udworkapplyFragment;
    private WorkOrderFragment workorderFragment;
    private InventoryFragment inventoryFragment;
    private PoFragment poFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        findViewById();
        initView();
        isshow(0);
    }

    protected void findViewById() {
        mMenu = (SlidingMenu) findViewById(R.id.id_menu);
        menutitle = (TextView) findViewById(R.id.menu_title);
        userNameText = (TextView) findViewById(R.id.txt_member);
        TaskLayout = (RelativeLayout) findViewById(R.id.task_layout);
        AssetLayout = (RelativeLayout) findViewById(R.id.asset_layout);
        UdworkapplyLayout = (RelativeLayout) findViewById(R.id.udworkapply_layout);
        WorkOrderLayout = (RelativeLayout) findViewById(R.id.workorder_layout);
        KuCunLayout = (RelativeLayout) findViewById(R.id.kucun_layout);
//        PoLayout = (RelativeLayout) findViewById(R.id.po_layout);
    }

    protected void initView() {

        userNameText.setText(AccountUtils.getdisplayName(MainActivity.this));
        TaskLayout.setOnClickListener(new layoutClickListener(0));
        AssetLayout.setOnClickListener(new layoutClickListener(1));
        UdworkapplyLayout.setOnClickListener(new layoutClickListener(2));
        WorkOrderLayout.setOnClickListener(new layoutClickListener(3));
        KuCunLayout.setOnClickListener(new layoutClickListener(4));
//        PoLayout.setOnClickListener(new layoutClickListener(5));

        TaskLayout.performClick();
        mMenu.toggle();
    }


    /**
     * 点击事件
     **/
    class layoutClickListener implements View.OnClickListener {
        int positiong;

        private layoutClickListener(int positiong) {
            this.positiong = positiong;
        }

        @Override
        public void onClick(View v) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            isshow(positiong);
            switch (positiong) {
                case 0: //代办任务
                    if (taskFragment == null) {
                        taskFragment = new TaskFragment();
                        Bundle bundle = new Bundle();
                        taskFragment.setArguments(bundle);
                    }
                    fragmentTransaction.replace(R.id.container, taskFragment).commit();
                    menutitle.setText(R.string.task_text);
                    mMenu.toggle();
                    break;
                case 1: //资产查询
                    if (assetFragment == null) {
                        assetFragment = new AssetFragment();
                        Bundle bundle = new Bundle();
                        assetFragment.setArguments(bundle);
                    }
                    fragmentTransaction.replace(R.id.container, assetFragment).commit();
                    menutitle.setText(R.string.asset_text);
                    mMenu.toggle();
                    break;
                case 2://工作申请
                    if (udworkapplyFragment == null) {
                        udworkapplyFragment = new UdworkapplyFragment();
                        Bundle bundle = new Bundle();
                        udworkapplyFragment.setArguments(bundle);
                    }
                    fragmentTransaction.replace(R.id.container, udworkapplyFragment).commit();
                    menutitle.setText(R.string.job_text);
                    mMenu.toggle();
                    break;
                case 3://工单管理
                    if (workorderFragment == null) {
                        workorderFragment = new WorkOrderFragment();
                        Bundle bundle = new Bundle();
                        workorderFragment.setArguments(bundle);
                    }
                    fragmentTransaction.replace(R.id.container, workorderFragment).commit();
                    menutitle.setText(R.string.workorder_text);
                    mMenu.toggle();
                    break;
                case 4://库存管理
                    if (inventoryFragment == null) {
                        inventoryFragment = new InventoryFragment();
                        Bundle bundle = new Bundle();
                        inventoryFragment.setArguments(bundle);
                    }
                    fragmentTransaction.replace(R.id.container, inventoryFragment).commit();
                    menutitle.setText(R.string.inventory_text);
                    mMenu.toggle();
                    break;
//                case 5://采购管理
//                    if (poFragment == null) {
//                        poFragment = new PoFragment();
//                        Bundle bundle = new Bundle();
//                        poFragment.setArguments(bundle);
//                    }
//                    fragmentTransaction.replace(R.id.container, poFragment).commit();
//                    menutitle.setText(R.string.po_text);
//                    mMenu.toggle();
//                    break;
            }
        }
    }

    public void toggleMenu(View view) {
        mMenu.toggle();
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (mMenu.isActivated()) {
            mMenu.closeMenu();
            return;
        }

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, getResources().getString(R.string.exit_text), Toast.LENGTH_LONG).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(MainActivity.this);
        }
    }


    /**
     * 判断背景显示
     **/
    private void isshow(int index) {
        switch (index) {
            case 0:
                TaskLayout.setBackgroundResource(R.color.light_blue);
                AssetLayout.setBackgroundResource(R.color.white);
                UdworkapplyLayout.setBackgroundResource(R.color.white);
                WorkOrderLayout.setBackgroundResource(R.color.white);
                KuCunLayout.setBackgroundResource(R.color.white);
//                PoLayout.setBackgroundResource(R.color.white);
                break;
            case 1:
                TaskLayout.setBackgroundResource(R.color.white);
                AssetLayout.setBackgroundResource(R.color.light_blue);
                UdworkapplyLayout.setBackgroundResource(R.color.white);
                WorkOrderLayout.setBackgroundResource(R.color.white);
                KuCunLayout.setBackgroundResource(R.color.white);
//                PoLayout.setBackgroundResource(R.color.white);
                break;
            case 2:
                TaskLayout.setBackgroundResource(R.color.white);
                AssetLayout.setBackgroundResource(R.color.white);
                UdworkapplyLayout.setBackgroundResource(R.color.light_blue);
                WorkOrderLayout.setBackgroundResource(R.color.white);
                KuCunLayout.setBackgroundResource(R.color.white);
//                PoLayout.setBackgroundResource(R.color.white);
                break;
            case 3:
                TaskLayout.setBackgroundResource(R.color.white);
                AssetLayout.setBackgroundResource(R.color.white);
                UdworkapplyLayout.setBackgroundResource(R.color.white);
                WorkOrderLayout.setBackgroundResource(R.color.light_blue);
                KuCunLayout.setBackgroundResource(R.color.white);
//                PoLayout.setBackgroundResource(R.color.white);
                break;
            case 4:
                TaskLayout.setBackgroundResource(R.color.white);
                AssetLayout.setBackgroundResource(R.color.white);
                UdworkapplyLayout.setBackgroundResource(R.color.white);
                WorkOrderLayout.setBackgroundResource(R.color.white);
                KuCunLayout.setBackgroundResource(R.color.light_blue);
//                PoLayout.setBackgroundResource(R.color.white);
                break;
//            case 5:
//                TaskLayout.setBackgroundResource(R.color.white);
//                AssetLayout.setBackgroundResource(R.color.white);
//                UdworkapplyLayout.setBackgroundResource(R.color.white);
//                WorkOrderLayout.setBackgroundResource(R.color.white);
//                KuCunLayout.setBackgroundResource(R.color.white);
//                PoLayout.setBackgroundResource(R.color.light_blue);
//                break;
        }
    }
}
