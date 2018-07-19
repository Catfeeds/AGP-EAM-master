package com.hsk.hxqh.agp_eam.ui.activity;

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
import com.hsk.hxqh.agp_eam.adpter.InventoryAdapter;
import com.hsk.hxqh.agp_eam.adpter.ItemAdapter;
import com.hsk.hxqh.agp_eam.api.HttpManager;
import com.hsk.hxqh.agp_eam.api.HttpRequestHandler;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.model.INVENTORY;
import com.hsk.hxqh.agp_eam.model.ITEM;
import com.hsk.hxqh.agp_eam.model.LOCATIONS;
import com.hsk.hxqh.agp_eam.ui.activity.invuse.InvusemiAddNewActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Location_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.widget.SwipeRefreshLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 * 库存查询列表
 */

public class InventoryListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener{
    private static final int INVUENTORY_CODE = 1000;
    private static final String TAG = "InventoryListActivity";
    private ImageView backImageView;
    private TextView titleTextView;
    private ImageView menuImageView;
    LinearLayoutManager layoutManager;
    /**
     * RecyclerView*
     */
    public RecyclerView recyclerView;
    /**
     * 暂无数据*
     */
    private LinearLayout nodatalayout;
    /**
     * 界面刷新*
     */
    private SwipeRefreshLayout refresh_layout = null;
    /**
     * 适配器*
     */
    private InventoryAdapter itemAdapter;
    /**
     * 编辑框*
     */
    private EditText search;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<INVENTORY> items = new ArrayList<INVENTORY>();
    private String type;
    private String[] optionList;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private boolean isExistItem;
    private INVENTORY inventoryScan;
    private ImageView select;
private     ArrayList<INVENTORY> inventories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        optionList = new String[]{getString(R.string.bianhao),"SCAN"};
       // getIntentData();
        findViewById();
        initView();
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

   /* @Override
    public void onStart() {
        super.onStart();
        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<INVENTORY>());
        items = new ArrayList<>();
        getData(searchText);
    }*/

    private void getIntentData(){
//        type = getIntent().getStringExtra("type");
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
        select = (ImageView) findViewById(R.id.select_dialog_listview);
    }

    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backOnClickListener);
        titleTextView.setText(getResources().getString(R.string.inventory_text));
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setOnClickListener(optionOnClickListener);
        setSearchEdit();

        layoutManager = new LinearLayoutManager(InventoryListActivity.this);
        select.setOnClickListener(fromLocOnClickListener);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refresh_layout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refresh_layout.setRefreshing(true);

        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnLoadListener(this);

        initAdapter(new ArrayList<INVENTORY>());
        getData(searchText);
    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog( InventoryListActivity.this, optionList);
            normalListDialog.title(getString(R.string.chaxuntj))
                    .showAnim(mBasIn)//
                    .dismissAnim(mBasOut)//
                    .show();
            normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    linetypeTextView.setText(linetypeList[position]);
                    search.getText().clear();
                    switch (position){
                        case 99:
                            normalListDialog.superDismiss();
                            type = "DESC";
                            search.setHint(R.string.asset_desc);
                            break;
                        case 0:
                            normalListDialog.superDismiss();
                            type = "INVNUM";
                            select.setVisibility(View.VISIBLE);
                            search.setHint(R.string.item_num_title);
                            break;
                        case 2:
                            normalListDialog.superDismiss();
                            type = "INVDESC";
                            search.setHint(optionList[2]);
                            break;
                        case 1:
                            normalListDialog.superDismiss();
                            Intent intent = new Intent(InventoryListActivity.this, MipcaActivityCapture.class);
                            intent.putExtra("mark", 1); //扫码标识
                            startActivityForResult(intent,INVUENTORY_CODE);
                            break;
                    }
//                    normalListDialog.dismiss();
                }
            });
        }
    };

    @Override
    public void onLoad() {
        page++;
        getData(searchText);
    }

    @Override
    public void onRefresh() {
        page = 1;
        getData(searchText);
    }


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
                                    InventoryListActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    itemAdapter.removeAll(items);
                    items = new ArrayList<INVENTORY>();
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
        HttpManager.getDataPagingInfo(InventoryListActivity.this, HttpManager.getInventoryUrl(search,type, page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                Log.e("库存", "onSuccess: "+results.toString() );
                ArrayList<INVENTORY> item = JsonUtils.parsingINVENTORY(InventoryListActivity.this, results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<INVENTORY>();
                            initAdapter(items);
                        }
                        for (int i = 0; i < item.size(); i++) {
                            items.add(item.get(i));
                        }
                        addData(item);
                    }
                    nodatalayout.setVisibility(View.GONE);

                    initAdapter(items);
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
        itemAdapter = new InventoryAdapter(InventoryListActivity.this, R.layout.list_item_inventory, list);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(InventoryListActivity.this, InventoryDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("inventory", items.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<INVENTORY> list) {
        itemAdapter.addData(list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case INVUENTORY_CODE:
                if (data!=null){
                    Bundle bundle=data.getExtras();
                    String results = bundle.getString("result");
                    if (results==null || results.equals("")){
                        break;
                    }
                    isExistSN(results);
                }
                break;
        }
        if (resultCode==110){
            LOCATIONS locations = (LOCATIONS) data.getExtras().get("location");
            search.setText(locations.getLOCATION());
            searchText = locations.getLOCATION();
        }
    }
    /**
     * 根据SN号查询资产表是否存在
     **/
    private void isExistSN(final String serialnum) {
        HttpManager.getDataPagingInfo(this, HttpManager.getItemUrl(serialnum,"",1,20),new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<INVENTORY> item = JsonUtils.parsingINVENTORY(InventoryListActivity.this, results.getResultlist());
                if (item == null || item.isEmpty()) {
                    isExistItem = false;
                    Toast.makeText(InventoryListActivity.this,"There is no such item",Toast.LENGTH_SHORT).show();
                } else {
                    inventoryScan = item.get(0);
                    boolean flag = false;
                    for (int i =0; i < itemAdapter.getData().size();i++){
                        String itemnum =   itemAdapter.getItem(i).getITEMNUM();
                        if (itemnum.equalsIgnoreCase(inventoryScan.getITEMNUM().trim())){
                            flag = true;
                            startIntent(i,itemAdapter.getItem(i));
                            break;
                        }
                    }
                    if (flag != true){
                        itemAdapter.add(inventoryScan);
                        startIntent(itemAdapter.getItemCount(),inventoryScan);
                    }

                }
            }

            @Override
            public void onFailure(String error) {
            }
        });
    }
    public  void startIntent(int position,INVENTORY itemintent){
        Intent intent = new Intent(this,InventoryDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("inventory",itemintent);
        bundle.putInt("position",position);
        intent.putExtras(bundle);
        startActivityForResult(intent,0);
    }
    private  View.OnClickListener fromLocOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(InventoryListActivity.this, Location_chooseActivity.class);
            intent.putExtra("type","STOREROOM");
            startActivityForResult(intent,0);
        }
    };
    public void getAllDate(){
        HttpManager.getData(this, HttpManager.getInventoryUrl(), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results data) {
               inventories = JsonUtils.parsingINVENTORY(InventoryListActivity.this, data.getResultlist());
            }

            @Override
            public void onSuccess(Results data, int totalPages, int currentPage) {

            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
