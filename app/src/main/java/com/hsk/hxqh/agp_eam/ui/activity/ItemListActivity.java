package com.hsk.hxqh.agp_eam.ui.activity;

import android.content.ClipData;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.authentication.activity.HDUHFActivity;
import com.authentication.activity.HXUHFActivity;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.adpter.BaseQuickAdapter;
import com.hsk.hxqh.agp_eam.adpter.ItemAdapter;
import com.hsk.hxqh.agp_eam.api.HttpManager;
import com.hsk.hxqh.agp_eam.api.HttpRequestHandler;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.model.INVBALANCES;
import com.hsk.hxqh.agp_eam.model.ITEM;
import com.hsk.hxqh.agp_eam.model.UDSTOCKLINE;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;
import com.hsk.hxqh.agp_eam.ui.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 * 物资台帐列表
 */

public class ItemListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener{
    public static final  int ITEMSCAN_CODE = 8080;
    private static final int ITEMUHF_CODE = 8081;
    private static final String TAG = "ItemListActivity";
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
    private ItemAdapter itemAdapter;
    /**
     * 编辑框*
     */
    private EditText search;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<ITEM> items = new ArrayList<ITEM>();
    private String type;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private Button typeLayout;
    private String[] optionList ;
    private boolean isExistItem;
    private ITEM itemscan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        optionList = new String[]{getString(R.string.miaoshu),getString(R.string.bianhao),getString(R.string.scan),getString(R.string.uhfscan)};
        //getIntentData();
        findViewById();
        initView();
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

 /*   @Override
    public void onStart() {
        super.onStart();
        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<ITEM>());
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
        typeLayout = (Button)findViewById(R.id.stack_type);
    }

    @Override
    protected void initView() {
        setSearchEdit();
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backOnClickListener);
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setOnClickListener(optionOnClickListener);
        titleTextView.setText(getResources().getString(R.string.invuse_item));
        layoutManager = new LinearLayoutManager(ItemListActivity.this);
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

        initAdapter(new ArrayList<ITEM>());
        getData(searchText);

        typeLayout.setOnClickListener(optionOnClickListener);
    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    public void onLoad() {
        page++;
        getData(searchText);
    refresh_layout.setLoading(false);
    }

    @Override
    public void onRefresh() {
        page = 1;
        getData(searchText);
    }

    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(ItemListActivity.this, optionList);
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
                        case 0:
                            normalListDialog.superDismiss();
                            Log.e(TAG, "onOperItemClick: 我是二维码" );
                            type = "DESC";
                            search.setHint(getString(R.string.item_description));
                            break;
                        case 1:
                            normalListDialog.superDismiss();
                            type = "ITEMNUM";
                            search.setHint(getString(R.string.item_itemnum));
                            Log.e(TAG, "onOperItemClick: 我是磁条码");
                            break;
                        case 2:
                            normalListDialog.superDismiss();
                            Intent intent = new Intent(ItemListActivity.this, MipcaActivityCapture.class);
                            intent.putExtra("mark", 1); //扫码标识
                            startActivityForResult(intent, ITEMSCAN_CODE);
                            break;
                        case 3:
                            normalListDialog.superDismiss();
                            Intent intent1 = new Intent(ItemListActivity.this, UHFActivity.class);
                            intent1.putExtra("markUHF",2);
                            startActivityForResult(intent1,ITEMUHF_CODE);
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
                                    ItemListActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    itemAdapter.removeAll(items);
                    items = new ArrayList<ITEM>();
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
        HttpManager.getDataPagingInfo(ItemListActivity.this, HttpManager.getItemUrl(search,type,page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<ITEM> item = JsonUtils.parsingITEM(ItemListActivity.this, results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<ITEM>();
                            initAdapter(items);
                        }
                        for (int i = 0; i < item.size(); i++) {
                            items.add(item.get(i));
                        }
                        int postion = itemAdapter.getItemCount();
                        addData(item);
                        BaseViewHolder baseViewHolder = new BaseViewHolder(ItemListActivity.this, recyclerView);
                        itemAdapter.onBindViewHolder(baseViewHolder,1);
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
    private void initAdapter(final List<ITEM> list) {
        itemAdapter = new ItemAdapter(ItemListActivity.this, R.layout.list_item_item, list);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ItemListActivity.this, ItemDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", items.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<ITEM> list) {
        itemAdapter.addData(list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ITEMSCAN_CODE:
                if (data!=null){
                Bundle bundle=data.getExtras();
                String results = bundle.getString("result");
                if (results==null || results.equals("")){
                    break;
                }
                isExistSN(results);
                }
            case ITEMUHF_CODE:
                if (data!=null){
                    Bundle bundle=data.getExtras();
                    INVBALANCES invbalances = (INVBALANCES) bundle.get("invbalance");
                    if (invbalances==null){
                        break;
                    }
                    isExistSN(invbalances.getITEMNUM());
                }
                break;
        }
    }
    /**
     * 根据SN号查询资产表是否存在
     **/
    private void isExistSN(final String serialnum) {
        HttpManager.getDataPagingInfo(ItemListActivity.this, HttpManager.getItemUrl(serialnum,"",1,20),new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<ITEM> item = JsonUtils.parsingITEM(ItemListActivity.this,results.getResultlist());
                if (item == null || item.isEmpty()) {
                    isExistItem = false;
                    Toast.makeText(ItemListActivity.this,"There is no such item",Toast.LENGTH_SHORT).show();
                } else {
                    itemscan = item.get(0);
                    boolean flag = false;
                    for (int i =0; i < itemAdapter.getData().size();i++){
                        String itemnum =   itemAdapter.getItem(i).getITEMNUM();
                        if (itemnum.equalsIgnoreCase(itemscan.getITEMNUM().trim())){
                            flag = true;
                            startIntent(i,itemAdapter.getItem(i));
                            break;
                        }
                    }
                    if (flag != true){
                        itemAdapter.add(itemscan);
                        startIntent(itemAdapter.getItemCount(),itemscan);
                    }

                }
            }

            @Override
            public void onFailure(String error) {
            }
        });
    }
    public  void startIntent(int position,ITEM itemintent){
        Intent intent = new Intent(ItemListActivity.this,ItemDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("item",itemintent);
        bundle.putInt("position",position);
        intent.putExtras(bundle);
        startActivityForResult(intent,0);
    }
}
