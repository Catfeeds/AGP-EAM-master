package com.hsk.hxqh.agp_eam.ui.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.LABTRANS;
import com.hsk.hxqh.agp_eam.model.MATUSETRANS;
import com.hsk.hxqh.agp_eam.model.WOACTIVITY;
import com.hsk.hxqh.agp_eam.model.WORKORDER;
import com.hsk.hxqh.agp_eam.ui.fragment.LabtransFragment;
import com.hsk.hxqh.agp_eam.ui.fragment.MatusetransFragment;
import com.hsk.hxqh.agp_eam.ui.fragment.WoactivityFragment;
import com.hsk.hxqh.agp_eam.ui.fragment.WplaborFragment;
import com.hsk.hxqh.agp_eam.ui.fragment.WpmaterialFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by think on 2015/12/3.
 * 工单计划页面
 */
public class Work_RealActivity extends BaseActivity {

    private TextView titlename;
    private ImageView menuImageView;
    private ImageView backimg;
    private Button woactivity;//任务
    private Button wplabor;//员工
    //    private Button wpservice;//服务
    private Button wpmaterial;//物料
    //    private Button wptool;//工具
    private ViewPager mViewPager;
    private int currentIndex = 0;
    private List<Fragment> fragmentlist = new ArrayList<>();
    private WoactivityFragment woactivityFragment;
    private LabtransFragment labtransFragment;
    private MatusetransFragment matusetransFragment;
//    private WpserviceFragment wpserviceFragment;
//    private WptoolFragment wptoolFragment;

    private LinearLayout confirmBtn;
    private Button revise;//
    private Button wfservice;

    public WORKORDER workOrder;
    public ArrayList<WOACTIVITY> woactivityList = new ArrayList<>();
    public ArrayList<LABTRANS> labtransList = new ArrayList<>();
    public ArrayList<MATUSETRANS> matusetranslList = new ArrayList<>();

    public void setWoactivityList(ArrayList<WOACTIVITY> list){
        this.woactivityList = list;
    }
    public void setWplaborList(ArrayList<LABTRANS> list){
        this.labtransList = list;
    }
    public void setWpmaterialList(ArrayList<MATUSETRANS> list){
        this.matusetranslList = list;
    }

//    private BaseAnimatorSet mBasIn;
//    private BaseAnimatorSet mBasOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workplan);

        geiIntentData();
        findViewById();
        initView();
    }

    private void geiIntentData() {
        workOrder = (WORKORDER) getIntent().getSerializableExtra("workorder");
//        woactivityList = (ArrayList<WOACTIVITY>) getIntent().getSerializableExtra("woactivityList");
//        wplaborList = (ArrayList<WPLABOR>) getIntent().getSerializableExtra("wplaborList");
//        wpmaterialList = (ArrayList<WPMATERIAL>) getIntent().getSerializableExtra("wpmaterialList");
    }

    @Override
    protected void findViewById() {
        titlename = (TextView) findViewById(R.id.menu_title);
        menuImageView = (ImageView) findViewById(R.id.title_more);
        backimg = (ImageView) findViewById(R.id.menu_id);
        woactivity = (Button) findViewById(R.id.work_woactivity);
        wplabor = (Button) findViewById(R.id.work_wplabor);
//        wpservice = (Button) findViewById(R.id.work_wpservice);
        wpmaterial = (Button) findViewById(R.id.work_wpmaterial);
//        wptool = (Button) findViewById(R.id.work_wptool);
        mViewPager = (ViewPager) findViewById(R.id.pager);

//        confirmBtn = (LinearLayout) findViewById(R.id.buttom_layout);
//        revise = (Button) findViewById(R.id.work_revise);
//        wfservice = (Button) findViewById(R.id.wfservice);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initView() {
        mViewPager.setCurrentItem(currentIndex);
        mViewPager.setOffscreenPageLimit(2);
        titlename.setText(getResources().getString(R.string.work_workplan));
        backimg.setBackgroundResource(R.drawable.ic_back);
        backimg.setOnClickListener(backOnClickListener);
        menuImageView.setImageResource(R.drawable.ic_add);
//        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setOnClickListener(menuImageViewOnClickListener);
//        if (workOrder.wonum!=null&&!workOrder.wonum.equals("")&&!workOrder.status.equals(Constants.WAIT_APPROVAL)){
//            menuImageView.setVisibility(View.GONE);
//        }
        woactivity.setBackground(getResources().getDrawable(R.drawable.button_check_style));
        woactivity.setOnClickListener(new Buttonlistener());
        wplabor.setOnClickListener(new Buttonlistener());
//        wpservice.setOnClickListener(new Buttonlistener());
        wpmaterial.setOnClickListener(new Buttonlistener());
//        wptool.setOnClickListener(new Buttonlistener());
        fragmentlist = new ArrayList<>();
        woactivityFragment = new WoactivityFragment(workOrder,woactivityList,"yes");
        labtransFragment = new LabtransFragment(workOrder,labtransList,"yes");
        matusetransFragment = new MatusetransFragment(workOrder,matusetranslList,"yes");
//        wpserviceFragment = new WpserviceFragment(workOrder);
//        wptoolFragment = new WptoolFragment(workOrder);
        fragmentlist.add(labtransFragment);
        fragmentlist.add(matusetransFragment);
        fragmentlist.add(woactivityFragment);
//        fragmentlist.add(wpserviceFragment);
//        fragmentlist.add(wptoolFragment);
        mViewPager.setAdapter(new MyFrageStatePagerAdapter(getSupportFragmentManager()));//设置ViewPager的适配器
        mViewPager.setOnPageChangeListener(new MyPagerOnPageChangeListener());
        wplabor.performClick();

//        mBasIn = new BounceTopEnter();
//        mBasOut = new SlideBottomExit();

//        revise.setText(getResources().getString(R.string.ok));
//        revise.setOnClickListener(OkOnClickListener);
//        wfservice.setVisibility(View.GONE);

//        setNodataLayout();
    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            if (confirmBtn.getVisibility() == View.VISIBLE) {
//                final NormalDialog dialog = new NormalDialog(com.jf_eam_project.ui.activity.Work_PlanActivity.this);
//                dialog.content("确定放弃修改吗?")//
//                        .showAnim(mBasIn)//
//                        .dismissAnim(mBasOut)//
//                        .show();
//
//                dialog.setOnBtnClickL(
//                        new OnBtnClickL() {
//                            @Override
//                            public void onBtnClick() {
//                                dialog.dismiss();
//                            }
//                        },
//                        new OnBtnClickL() {
//                            @Override
//                            public void onBtnClick() {
//                                com.jf_eam_project.ui.activity.Work_PlanActivity.this.finish();
////                            dialog.dismiss();
//                            }
//                        });
//            }else {
                finish();
//            }
        }
    };

    private View.OnClickListener OkOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Intent intent = getIntent();
//            intent.putExtra("woactivityList", woactivityFragment.woactivityAdapter.getList());
//            intent.putExtra("wplaborList", wplaborFragment.wplaborAdapter.getList());
//            intent.putExtra("wpmaterialList", wpmaterialFragment.wpmaterialAdapter.getList());
//            com.jf_eam_project.ui.activity.Work_PlanActivity.this.setResult(1000, intent);
//            finish();
        }
    };

    private View.OnClickListener menuImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Intent intent;
//            if (currentIndex == 0) {
//                intent = new Intent(com.jf_eam_project.ui.activity.Work_PlanActivity.this, WoactivityAddNewActivity.class);
//                intent.putExtra("taskid", (woactivityFragment.woactivityAdapter.woactivityList.size() + 1) * 10);
//                startActivityForResult(intent, 0);
//            } else if (currentIndex == 1) {
//                intent = new Intent(com.jf_eam_project.ui.activity.Work_PlanActivity.this, WplaborAddNewActivity.class);
////                intent.putExtra("woactivityList", woactivityFragment.woactivityAdapter.woactivityList);
//                startActivityForResult(intent, 1);
//            } else if (currentIndex == 2) {
//                intent = new Intent(com.jf_eam_project.ui.activity.Work_PlanActivity.this, WpmaterialAddNewActivity.class);
////                intent.putExtra("woactivityList", woactivityFragment.woactivityAdapter.woactivityList);
//                startActivityForResult(intent, 2);
//            }
        }
    };

    public class Buttonlistener implements View.OnClickListener {
        public Buttonlistener() {

        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {
            resetTextView();
            if (view.getId() == woactivity.getId()) {
                view.setBackground(getResources().getDrawable(R.drawable.button_check_style2));
                currentIndex = 2;
            } else if (view.getId() == wplabor.getId()) {
                view.setBackground(getResources().getDrawable(R.drawable.button_check_style2));
                currentIndex = 0;
            } else if (view.getId() == wpmaterial.getId()) {
                view.setBackground(getResources().getDrawable(R.drawable.button_check_style2));
                currentIndex = 1;
            }
            mViewPager.setCurrentItem(currentIndex);
        }
    }

    /**
     * ViewPager的适配器
     */
    class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {

        public MyFrageStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentlist.get(position);
        }

        @Override
        public int getCount() {
            return fragmentlist.size();
        }
    }

    /**
     * ViewPager的PageChangeListener(页面改变的监听器)
     */
    private class MyPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int position, float offset, int offsetPixels) {

        }

        /**
         * 滑动ViewPager的时候
         */
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onPageSelected(int position) {
            resetTextView();
            switch (position) {
                case 2:
                    woactivity.performClick();
                    break;
                case 0:
                    wplabor.performClick();
                    break;
                case 1:
                    wpmaterial.performClick();
                    break;
            }
            currentIndex = position;
        }
    }

    /**
     * 重置颜色
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void resetTextView() {
        woactivity.setBackground(getResources().getDrawable(R.drawable.button_check_style));
        wplabor.setBackground(getResources().getDrawable(R.drawable.button_check_style));
        wpmaterial.setBackground(getResources().getDrawable(R.drawable.button_check_style));
    }

    private void setNodataLayout(){
        if(currentIndex==2){
            if (woactivityFragment.woactivityAdapter.getItemCount()==0) {
                woactivityFragment.nodatalayout.setVisibility(View.VISIBLE);
            }else {
                woactivityFragment.nodatalayout.setVisibility(View.GONE);
            }
        }else if (currentIndex==0){
            if (labtransFragment.labtransAdapter.getItemCount()==0) {
                labtransFragment.nodatalayout.setVisibility(View.VISIBLE);
            }else {
                labtransFragment.nodatalayout.setVisibility(View.GONE);
            }
        }else if (currentIndex==1){
            if (matusetransFragment.matusetransAdapter.getItemCount()==0) {
                matusetransFragment.nodatalayout.setVisibility(View.VISIBLE);
            }else {
                matusetransFragment.nodatalayout.setVisibility(View.GONE);
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (resultCode) {
//            case -1:
//                if (data != null) {
//                    Woactivity woactivity = (Woactivity) data.getSerializableExtra("woactivity");
////                    woactivityList.add(woactivity);
//                    woactivityFragment.woactivityAdapter.adddate(woactivity);
//                    woactivityFragment.nodatalayout.setVisibility(View.GONE);
//                }
//                confirmBtn.setVisibility(View.VISIBLE);
//                setNodataLayout();
//                break;
//            case 1:
//                if (data != null) {
//                    Wplabor wplabor = (Wplabor) data.getSerializableExtra("wplabor");
////                    wplaborList.add(wplabor);
//                    wplaborFragment.wplaborAdapter.adddate(wplabor);
//                    wplaborFragment.nodatalayout.setVisibility(View.GONE);
//                }
//                confirmBtn.setVisibility(View.VISIBLE);
//                setNodataLayout();
//                break;
//            case 2:
//                if (data != null) {
//                    Wpmaterial wpmaterial = (Wpmaterial) data.getSerializableExtra("wpmaterial");
////                    wpmaterialList.add(wpmaterial);
//                    wpmaterialFragment.wpmaterialAdapter.adddate(wpmaterial);
//                    wpmaterialFragment.nodatalayout.setVisibility(View.GONE);
//                }
//                confirmBtn.setVisibility(View.VISIBLE);
//                setNodataLayout();
//                break;
//            case 4:
//                if (data != null){
//                    Woactivity woactivity = (Woactivity) data.getSerializableExtra("woactivity");
//                    int position = data.getIntExtra("position",0);
////                    woactivityList.set(position,woactivity);
//                    woactivityFragment.woactivityAdapter.woactivityList.set(position, woactivity);
//                    woactivityFragment.woactivityAdapter.notifyDataSetChanged();
//                }
//                confirmBtn.setVisibility(View.VISIBLE);
//                setNodataLayout();
//                break;
//            case 5:
//                if(data != null){
//                    Wplabor wplabor = (Wplabor) data.getSerializableExtra("wplabor");
//                    int position = data.getIntExtra("position",0);
////                    wplaborList.set(position,wplabor);
//                    wplaborFragment.wplaborAdapter.wplaborList.set(position,wplabor);
//                    wplaborFragment.wplaborAdapter.notifyDataSetChanged();
//                }
//                confirmBtn.setVisibility(View.VISIBLE);
//                setNodataLayout();
//                break;
//            case 6:
//                if(data != null){
//                    Wpmaterial wpmaterial = (Wpmaterial) data.getSerializableExtra("wpmaterial");
//                    int position = data.getIntExtra("position",0);
////                    wpmaterialList.set(position,wpmaterial);
//                    wpmaterialFragment.wpmaterialAdapter.wpmaterialList.set(position, wpmaterial);
//                    wpmaterialFragment.wpmaterialAdapter.notifyDataSetChanged();
//                }
//                confirmBtn.setVisibility(View.VISIBLE);
//                setNodataLayout();
//                break;
//            case 7://本地任务删除
//                if (data != null){
//                    int position = data.getIntExtra("position",0);
//                    woactivityFragment.woactivityAdapter.woactivityList.remove(position);
//                    woactivityFragment.woactivityAdapter.notifyDataSetChanged();
//                }
//                confirmBtn.setVisibility(View.VISIBLE);
//                setNodataLayout();
//                break;
//            case 8://服务器任务删除操作
//                if (data != null){
//                    Woactivity woactivity = (Woactivity) data.getSerializableExtra("woactivity");
//                    int position = data.getIntExtra("position",0);
//                    woactivityFragment.woactivityAdapter.deleteList.add(woactivity);
//                    woactivityFragment.woactivityAdapter.woactivityList.remove(position);
//                    woactivityFragment.woactivityAdapter.notifyDataSetChanged();
//                }
//                confirmBtn.setVisibility(View.VISIBLE);
//                setNodataLayout();
//                break;
//            case 9://本地员工删除
//                if (data != null) {
//                    int position = data.getIntExtra("position", 0);
//                    wplaborFragment.wplaborAdapter.wplaborList.remove(position);
//                    wplaborFragment.wplaborAdapter.notifyDataSetChanged();
//                }
//                confirmBtn.setVisibility(View.VISIBLE);
//                setNodataLayout();
//                break;
//            case 10://服务器员工删除
//                if(data != null){
//                    Wplabor wplabor = (Wplabor) data.getSerializableExtra("wplabor");
//                    int position = data.getIntExtra("position",0);
//                    wplaborFragment.wplaborAdapter.deleteList.add(wplabor);
//                    wplaborFragment.wplaborAdapter.wplaborList.remove(position);
//                    wplaborFragment.wplaborAdapter.notifyDataSetChanged();
//                }
//                confirmBtn.setVisibility(View.VISIBLE);
//                setNodataLayout();
//                break;
//            case 11://本地物料删除
//                if(data != null){
//                    int position = data.getIntExtra("position", 0);
//                    wpmaterialFragment.wpmaterialAdapter.wpmaterialList.remove(position);
//                    wpmaterialFragment.wpmaterialAdapter.notifyDataSetChanged();
//                }
//                confirmBtn.setVisibility(View.VISIBLE);
//                setNodataLayout();
//                break;
//            case 12://服务器物料删除
//                if(data != null){
//                    Wpmaterial wpmaterial = (Wpmaterial) data.getSerializableExtra("wpmaterial");
//                    int position = data.getIntExtra("position",0);
//                    wpmaterialFragment.wpmaterialAdapter.deleteList.add(wpmaterial);
//                    wpmaterialFragment.wpmaterialAdapter.wpmaterialList.remove(position);
//                    wpmaterialFragment.wpmaterialAdapter.notifyDataSetChanged();
//                }
//                confirmBtn.setVisibility(View.VISIBLE);
//                setNodataLayout();
//                break;
//            default:
//                break;
//        }
//    }
}
