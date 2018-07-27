package com.hsk.hxqh.agp_eam.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.adpter.BaseQuickAdapter;
import com.hsk.hxqh.agp_eam.adpter.Item_InventoryAdapter;
import com.hsk.hxqh.agp_eam.adpter.UdstockLineAdapter;
import com.hsk.hxqh.agp_eam.api.HttpManager;
import com.hsk.hxqh.agp_eam.api.HttpRequestHandler;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.model.ITEM;
import com.hsk.hxqh.agp_eam.model.UDSTOCK;
import com.hsk.hxqh.agp_eam.model.UDSTOCKLINE;
import com.hsk.hxqh.agp_eam.ui.widget.SwipeRefreshLayout;
import com.j256.ormlite.stmt.query.In;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by think on 2015/12/3.
 * 库存盘点子表列表页面
 */
public class UdstockLineActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener{

    private ImageView backImageView;
    private TextView titleTextView;
    private ImageView menuImageView;

    LinearLayoutManager layoutManager;
    public RecyclerView recyclerView;
    private LinearLayout nodatalayout;
    private UdstockLineAdapter udstockLineAdapter;
    private SwipeRefreshLayout refresh_layout = null;
    private int page = 1;
    public UDSTOCK udstock;
    public ArrayList<UDSTOCKLINE> assetArrayList = new ArrayList<>();
    private static final int STOCKTAKING_CODE = 1090;
    private static final int STOCKTAKINGDETAIL_CODE = 1111;
    private PopupWindow popupWindow;
    private LinearLayout udstocklinescanLayout;
    private boolean isExistStockline;
    private UDSTOCKLINE udstockline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);

        getIntentDate();
        findViewById();
        initView();
    }

    private void getIntentDate() {
        udstock = (UDSTOCK) getIntent().getSerializableExtra("udstock");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);
        menuImageView = (ImageView) findViewById(R.id.title_more);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);
        refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        nodatalayout = (LinearLayout) findViewById(R.id.have_not_data_id);
//        confirmlayout = (LinearLayout) findViewById(R.id.button_layout);
//        confirmBtn = (Button) findViewById(R.id.confirm);
    }

    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backOnClickListener);
        titleTextView.setText(getResources().getString(R.string.udstockline));
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setOnClickListener(moreImgOnClickListener);
//        menuImageView.setOnClickListener(menuImageViewOnClickListener);
//        confirmlayout.setVisibility(View.GONE);
//        confirmBtn.setOnClickListener(confirmBtnOnClickListener);
        layoutManager = new LinearLayoutManager(UdstockLineActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        woactivityAdapter = new WoactivityAdapter(Work_WoactivityActivity.this);
//        recyclerView.setAdapter(woactivityAdapter);

        refresh_layout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnLoadListener(this);
        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<UDSTOCKLINE>());
        assetArrayList = new ArrayList<>();
        getData();

    }

/*    @Override
    public void onStart() {
        super.onStart();

        //getData();
    }*/

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private View.OnClickListener moreImgOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupWindow(menuImageView);
        }
    };
    /**
     * 初始化showPopupWindow*
     */
    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(UdstockLineActivity.this).inflate(
                R.layout.scan_popup_window, null);


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

        udstocklinescanLayout = (LinearLayout) contentView.findViewById(R.id.udstockline_id);
        udstocklinescanLayout.setOnClickListener(scanButtonOnClickListener);
    }
    /**
     * 二维码扫描
     **/
    private View.OnClickListener scanButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                Intent intent = new Intent(UdstockLineActivity.this, MipcaActivityCapture.class);
                intent.putExtra("mark", 1); //扫码标识
                startActivityForResult(intent, STOCKTAKING_CODE);
        }
    };
    /**
     * 获取数据*
     */
    private void getData() {
        HttpManager.getData(UdstockLineActivity.this, HttpManager.getUdstocklineUrl(udstock.getSTOCKNUM(),page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<UDSTOCKLINE> item = JsonUtils.parsingUDSTOCKLINE(UdstockLineActivity.this, results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {
                    List<UDSTOCKLINE> udstocklineList = new ArrayList<>();
                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            assetArrayList = new ArrayList<UDSTOCKLINE>();
                            initAdapter(assetArrayList);
                        }
                        for (int i = 0; i < item.size(); i++) {
                            if ("Y".equalsIgnoreCase(item.get(i).getISCHECK())){
                                udstocklineList.add(item.get(i));
                            }else {
                                assetArrayList.add(item.get(i));
                            }
                        }
                        assetArrayList.addAll(udstocklineList);
                        addData(assetArrayList);
                    }
                    nodatalayout.setVisibility(View.GONE);

                    initAdapter(assetArrayList);
                }
            }

            @Override
            public void onFailure(String error) {
                refresh_layout.setRefreshing(false);
                nodatalayout.setVisibility(View.VISIBLE);
            }
        });

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<UDSTOCKLINE> list) {
        udstockLineAdapter = new UdstockLineAdapter(UdstockLineActivity.this, R.layout.list_item_udstockline, list);
        recyclerView.setAdapter(udstockLineAdapter);
        udstockLineAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(UdstockLineActivity.this, UdstocklineDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("udstockline", udstockLineAdapter.getItem(position));
                bundle.putInt("position",position);
                intent.putExtras(bundle);
               startActivityForResult(intent,STOCKTAKINGDETAIL_CODE);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<UDSTOCKLINE> list) {
            udstockLineAdapter.addData(list);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (resultCode) {
//            case 1://新增
//                if (data != null) {
//                    Woactivity woactivity = (Woactivity) data.getSerializableExtra("woactivity");
//                    woactivityAdapter.add(woactivity);
//                    initAdapter(woactivityAdapter.getData());
//                    nodatalayout.setVisibility(View.GONE);
//                }
//                confirmlayout.setVisibility(View.VISIBLE);
//                setNodataLayout();
//                break;
//            case 2://修改
//                if (data != null) {
//                    Woactivity woactivity = (Woactivity) data.getSerializableExtra("woactivity");
//                    int position = data.getIntExtra("position", 0);
//                    woactivityAdapter.set(position, woactivity);
//                    initAdapter(woactivityAdapter.getData());
//                    woactivityAdapter.notifyDataSetChanged();
//                }
//                confirmlayout.setVisibility(View.VISIBLE);
//                setNodataLayout();
//                break;
//            case 3://本地任务删除
//                if (data != null) {
//                    int position = data.getIntExtra("position", 0);
//                    woactivityAdapter.remove(position);
//                    initAdapter(woactivityAdapter.getData());
//                    woactivityAdapter.notifyDataSetChanged();
//                }
//                confirmlayout.setVisibility(View.VISIBLE);
//                setNodataLayout();
//                break;
//            case 4://服务器任务删除操作
//                if (data != null) {
//                    Woactivity woactivity = (Woactivity) data.getSerializableExtra("woactivity");
//                    int position = data.getIntExtra("position", 0);
//                    deleteList.add(woactivity);
//                    woactivityAdapter.remove(position);
//                    initAdapter(woactivityAdapter.getData());
//                    woactivityAdapter.notifyDataSetChanged();
//                }
//                confirmlayout.setVisibility(View.VISIBLE);
//                setNodataLayout();
//                break;
//        }
//    }

    @Override
    public void onRefresh() {
//        if (!workOrder.isnew&& (woactivityList == null || woactivityList.size() == 0)) {
        page = 1;
        getData();
//        }else {
//            refresh_layout.setRefreshing(false);
//        }
    }

    @Override
    public void onLoad() {
//        if (!workOrder.isnew) {
//            if (woactivityList.size() <= 20) {
//                page = 1;
//            } else {
//                page = woactivityList.size() / 20 + 1;
//            }
//        }else {
//            refresh_layout.setLoading(false);
//        }
        refresh_layout.setLoading(false);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case STOCKTAKING_CODE:
                String results = data.getExtras().getString("result");
                isExistSN(udstock.getSTOCKNUM(),results);
                break;
            case STOCKTAKINGDETAIL_CODE:
                if (data !=null){
                    Bundle bundle = data.getExtras();
                    UDSTOCKLINE result = (UDSTOCKLINE) bundle.get("udstockline");
                    result.setISCHECK("Y");
                    int resInt = bundle.getInt("position");
                    udstockLineAdapter.remove(resInt);
                    udstockLineAdapter.add(result);
                }

        }
    }
    /**
     * 根据SN号查询资产表是否存在
     **/
    private boolean isExistSN(final String serialnum,String item) {
        boolean flag;

        HttpManager.getDataPagingInfo(UdstockLineActivity.this, HttpManager.getUdstocklineUrl(serialnum,item,1,20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<UDSTOCKLINE> item = JsonUtils.parsingUDSTOCKLINE(UdstockLineActivity.this,results.getResultlist());
                if (item == null || item.isEmpty()) {
                    isExistStockline = false;
                    Toast.makeText(UdstockLineActivity.this,"There is no such item",Toast.LENGTH_SHORT).show();
                } else {
                    udstockLineAdapter.removeAll(udstockLineAdapter.getData());
                    addData(item);
                }
            }

            @Override
            public void onFailure(String error) {
            }
        });
            flag = isExistStockline;
            return  flag;
    }
    public  void startIntent(int position,UDSTOCKLINE udstocklinedetail){
        Intent intent = new Intent(UdstockLineActivity.this,UdstocklineDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("udsockline",udstocklinedetail);
        bundle.putInt("position",position);
        intent.putExtras(bundle);
        startActivityForResult(intent,STOCKTAKINGDETAIL_CODE);
    }
}
