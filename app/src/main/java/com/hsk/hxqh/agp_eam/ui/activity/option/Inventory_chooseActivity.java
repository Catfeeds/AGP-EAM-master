package com.hsk.hxqh.agp_eam.ui.activity.option;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.adpter.BaseQuickAdapter;
import com.hsk.hxqh.agp_eam.adpter.Inventory_chooseAdapter;
import com.hsk.hxqh.agp_eam.api.HttpManager;
import com.hsk.hxqh.agp_eam.api.HttpRequestHandler;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.model.INVENTORY;
import com.hsk.hxqh.agp_eam.ui.activity.BaseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.MipcaActivityCapture;
import com.hsk.hxqh.agp_eam.ui.activity.UdstockLineActivity;
import com.hsk.hxqh.agp_eam.ui.activity.invuse.InvuseListActivity;
import com.hsk.hxqh.agp_eam.ui.widget.SwipeRefreshLayout;
import com.hsk.hxqh.agp_eam.unit.UHFReader;

import java.util.ArrayList;
import java.util.List;

import android_serialport_api.SerialPortManager;
import android_serialport_api.UHFHXAPI;

import static android.widget.Toast.LENGTH_SHORT;


/**
 * Created by think on 2016/5/10.
 */
public class Inventory_chooseActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    public static final int INVENTORY_SCAN = 8080;
    public static final int INVENTORY_SCANUHF = 8081;
    private ImageView backImageView;
    private TextView titleTextView;
    private ImageView menuImageView;

    LinearLayoutManager layoutManager;
    public RecyclerView recyclerView;
    private LinearLayout nodatalayout;
    private Inventory_chooseAdapter inventory_chooseAdapter;
    private SwipeRefreshLayout refresh_layout = null;
    private int page = 1;
    /**
     * 编辑框*
     */
    private EditText search;
    /**
     * 查询条件*
     */
    private String searchText = "";
//    public INVENTORY INVENTORY;
    public ArrayList<INVENTORY> InventoryArrayList = new ArrayList<>();
//    public ArrayList<Woactivity> deleteList = new ArrayList<>();
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private UHFHXAPI uhfhxapi;
    private String uhfString;
//    private BaseAnimatorSet mBasIn;
//    private BaseAnimatorSet mBasOut;
//    private LinearLayout confirmlayout;
//    private Button confirmBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        findViewById();
        initView();
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private String getIntentDate() {
        searchText = getIntent().getStringExtra("location");
        return searchText;
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);
        menuImageView = (ImageView) findViewById(R.id.title_more);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);
        refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        nodatalayout = (LinearLayout) findViewById(R.id.have_not_data_id);
        search = (EditText) findViewById(R.id.search_edit);
//        confirmlayout = (LinearLayout) findViewById(R.id.button_layout);
//        confirmBtn = (Button) findViewById(R.id.confirm);
    }

    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backOnClickListener);
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setBackgroundResource(R.drawable.ic_more);
        menuImageView.setOnClickListener(menuOnClickListener);
        //titleTextView.setText(getResources().getString(R.string.));
//        menuImageView.setImageResource(R.drawable.ic_add);
//        menuImageView.setOnClickListener(menuImageViewOnClickListener);
//        confirmlayout.setVisibility(View.GONE);
//        confirmBtn.setOnClickListener(confirmBtnOnClickListener);
        layoutManager = new LinearLayoutManager(Inventory_chooseActivity.this);
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

        setSearchEdit();

//        mBasIn = new BounceTopEnter();
//        mBasOut = new SlideBottomExit();

    }

    @Override
    public void onStart() {
        super.onStart();
        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<INVENTORY>());
        InventoryArrayList = new ArrayList<>();
        getData(searchText);
    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private View.OnClickListener menuOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(Inventory_chooseActivity.this, new String[]{getString(R.string.scan),});
            normalListDialog.title(getString(R.string.option))
                    .showAnim(mBasIn)//
                    .dismissAnim(mBasOut)//
                    .show();
            normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    linetypeTextView.setText(linetypeList[position]);
                    switch (position){
                        case 0://Back
                            normalListDialog.superDismiss();
                            Intent intent = new Intent(Inventory_chooseActivity.this,  MipcaActivityCapture.class);
                            intent.putExtra("mark", 1); //扫码标识
                            startActivityForResult(intent, INVENTORY_SCAN);
                            break;
                        case 1://Add
                            normalListDialog.superDismiss();
                            SerialPortManager.getInstance().openSerialPort();
                            uhfhxapi = new UHFHXAPI();
                            UHFReader uhfReader = new UHFReader();
                            uhfString = uhfReader.reader(uhfhxapi);
                            if (!uhfString.contains("fail")){
                                String[] results = uhfString.split("a1a");
                                if (getIntentDate().equals(results[0])){
                                    inventory_chooseAdapter.removeAll(inventory_chooseAdapter.getData());
                                    getData("",getIntentDate(),results[1]);
                                }else {
                                    Toast.makeText(Inventory_chooseActivity.this, "It's not this storeroom",LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(Inventory_chooseActivity.this, uhfString + "\nPlease try again!",LENGTH_SHORT).show();
                            }
                            break;
                    }
//                    normalListDialog.dismiss();
                }
            });
        }
    };

    private void setSearchEdit() {
        SpannableString msp = new SpannableString(getString(R.string.search_text));
        Drawable drawable = getResources().getDrawable(R.drawable.ic_search);
        msp.setSpan(new ImageSpan(drawable), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        search.setHint(msp);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    Inventory_chooseActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    inventory_chooseAdapter.removeAll(InventoryArrayList);
                    InventoryArrayList = new ArrayList<INVENTORY>();
                    nodatalayout.setVisibility(View.GONE);
                    refresh_layout.setRefreshing(true);
                    page = 1;
                    getData(searchText);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 获取数据*
     */
    private void getData(String search) {
        HttpManager.getDataPagingInfo(Inventory_chooseActivity.this, HttpManager.getInventoryUrl(getIntentDate(),search,page, 20,1), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<INVENTORY> item = JsonUtils.parsingINVENTORY(Inventory_chooseActivity.this, results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            InventoryArrayList = new ArrayList<INVENTORY>();
                            initAdapter(InventoryArrayList);
                        }
                        for (int i = 0; i < item.size(); i++) {
                            InventoryArrayList.add(item.get(i));
                        }
                        addData(item);
                    }
                    nodatalayout.setVisibility(View.GONE);

                    initAdapter(InventoryArrayList);
                }
            }

            @Override
            public void onFailure(String error) {
                refresh_layout.setRefreshing(false);
                nodatalayout.setVisibility(View.VISIBLE);
            }
        });

    }
    private void getData(String search,String location,String binnum) {
        HttpManager.getData(Inventory_chooseActivity.this, HttpManager.getInvbalancesUrl(search,getIntentDate(),binnum), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<INVENTORY> item = JsonUtils.parsingINVENTORY(Inventory_chooseActivity.this, results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            InventoryArrayList = new ArrayList<INVENTORY>();
                            initAdapter(InventoryArrayList);
                        }
                        for (int i = 0; i < item.size(); i++) {
                            InventoryArrayList.add(item.get(i));
                        }
                        addData(item);
                    }
                    nodatalayout.setVisibility(View.GONE);

                    initAdapter(InventoryArrayList);
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
    private void initAdapter(final List<INVENTORY> list) {
        inventory_chooseAdapter = new Inventory_chooseAdapter(Inventory_chooseActivity.this, R.layout.list_item, list);
        recyclerView.setAdapter(inventory_chooseAdapter);
        inventory_chooseAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("INVENTORY", InventoryArrayList.get(position));
                intent.putExtras(bundle);
                setResult(120,intent);
                finish();
//                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case INVENTORY_SCAN:
                if (data!=null){
                    String result = data.getExtras().getString("result");
                    inventory_chooseAdapter.removeAll(inventory_chooseAdapter.getData());
                    if(result!=null){
                        getData(result);
                    }
                }
                break;
        }
    }

    /**
     * 添加数据*
     */
    private void addData(final List<INVENTORY> list) {
        inventory_chooseAdapter.addData(list);
    }


    @Override
    public void onRefresh() {
            page = 1;
            getData(searchText);
    }

    @Override
    public void onLoad() {
            page++;
        getData(searchText);
    }
    @Override
    protected void onPause() {
        SerialPortManager.getInstance().closeSerialPort();
        if (uhfhxapi!=null){
            uhfhxapi.close();
        }
        super.onPause();
    }
}
