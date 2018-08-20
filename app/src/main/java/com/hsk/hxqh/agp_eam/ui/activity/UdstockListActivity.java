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

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.adpter.BaseQuickAdapter;
import com.hsk.hxqh.agp_eam.adpter.UdstockAdapter;
import com.hsk.hxqh.agp_eam.adpter.WorkOrderAdapter;
import com.hsk.hxqh.agp_eam.api.HttpManager;
import com.hsk.hxqh.agp_eam.api.HttpRequestHandler;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.model.UDSTOCK;
import com.hsk.hxqh.agp_eam.ui.activity.invuse.InvuseListActivity;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;
import com.hsk.hxqh.agp_eam.ui.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */

public class UdstockListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener{
    private static final int UDSTOCK_CODE = 1234;
    private static final String TAG = "UdstockListActivity";
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
    private UdstockAdapter assetAdapter;
    /**
     * 编辑框*
     */
    private EditText search;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<UDSTOCK> items = new ArrayList<UDSTOCK>();
    private String searchType;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private View.OnClickListener searchTypeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(UdstockListActivity.this, new String[]{getString(R.string.item_num_title),getString(R.string.INVUSE_DESCRIPTION),getString(R.string.inventory_location),getString(R.string.scan)});
            normalListDialog.title(getString(R.string.option))
                    .showAnim(mBasIn)//
                    .dismissAnim(mBasOut)//
                    .show();
            normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    linetypeTextView.setText(linetypeList[position]);
                    search.setText("");
                    switch (position){
                        case 0://新建
                            normalListDialog.superDismiss();
                            searchType = "STOCKNUM";
                            search.setText("");
                            search.setHint(R.string.wonum_text);
                            break;
                        case 1://保存
                            normalListDialog.superDismiss();
                            searchType = "DESCRIPTION";
                            search.setHint(R.string.INVUSE_DESCRIPTION);
                            break;
                        case 2://新建
                            normalListDialog.superDismiss();
                            searchType = "STOREROOM";
                            search.setHint(R.string.asset_location);
                            break;
                        case 3://保存
                            normalListDialog.superDismiss();
                            Intent intent = new Intent(UdstockListActivity.this,   MipcaActivityCapture.class);
                            intent.putExtra("mark", 1); //扫码标识
                            startActivityForResult(intent,UDSTOCK_CODE);
                            break;

                    }
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getIntentData();
        findViewById();
        initView();
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<UDSTOCK>());
        items = new ArrayList<>();
        getData(searchText);
    }

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
    }

    @Override
    protected void initView() {
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setOnClickListener(searchTypeOnClickListener);
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backOnClickListener);
        titleTextView.setText(getResources().getString(R.string.udstock));
        setSearchEdit();

        layoutManager = new LinearLayoutManager(UdstockListActivity.this);
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
                                    UdstockListActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    assetAdapter.removeAll(items);
                    items = new ArrayList<UDSTOCK>();
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
        HttpManager.getDataPagingInfo(UdstockListActivity.this, HttpManager.getUdstockUrl(search,searchType, page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<UDSTOCK> item = JsonUtils.parsingUDSTOCK(UdstockListActivity.this, results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<UDSTOCK>();
                            initAdapter(items);
                        }
                        for (int i = 0; i < item.size(); i++) {
                            items.add(item.get(i));
                        }
                        int postion = assetAdapter.getItemCount();
                        addData(item);
                        BaseViewHolder baseViewHolder = new BaseViewHolder(UdstockListActivity.this, recyclerView);
                        assetAdapter.onBindViewHolder(baseViewHolder,postion);
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
    private void initAdapter(final List<UDSTOCK> list) {
        assetAdapter = new UdstockAdapter(UdstockListActivity.this, R.layout.list_item_udstock, list);
        recyclerView.setAdapter(assetAdapter);
        assetAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(UdstockListActivity.this, UdstockDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("udstock", items.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<UDSTOCK> list) {
        assetAdapter.addData(list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UDSTOCK_CODE){
           search.setText(data.getExtras().getString("result"));
            setSearchEdit();
        }
    }
}
