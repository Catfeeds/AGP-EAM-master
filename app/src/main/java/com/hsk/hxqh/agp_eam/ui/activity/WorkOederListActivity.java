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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.adpter.BaseQuickAdapter;
import com.hsk.hxqh.agp_eam.adpter.WorkOrderAdapter;
import com.hsk.hxqh.agp_eam.api.HttpManager;
import com.hsk.hxqh.agp_eam.api.HttpRequestHandler;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.model.ITEM;
import com.hsk.hxqh.agp_eam.model.WORKORDER;
import com.hsk.hxqh.agp_eam.model.WPMATERIAL;
import com.hsk.hxqh.agp_eam.ui.activity.invuse.InvuseListActivity;
import com.hsk.hxqh.agp_eam.ui.activity.invuse.MaterialRequisitionAddNewActivity;
import com.hsk.hxqh.agp_eam.ui.activity.invuse.MaterialRequisitionDetailActivity;
import com.hsk.hxqh.agp_eam.ui.widget.SwipeRefreshLayout;
import com.j256.ormlite.stmt.query.In;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */

public class WorkOederListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener{
    private static final int WORKORDER_CODE = 0623;
    private static final String TAG = "WorkOederListActivity";
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
    private WorkOrderAdapter assetAdapter;
    /**
     * 编辑框*
     */
    private EditText search;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;


    ArrayList<WORKORDER> items = new ArrayList<WORKORDER>();
    private String type;
    private Button quit;
    private Button option;
    private RelativeLayout button_layout;
    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(WorkOederListActivity.this, new String[]{"Back","New"});
            normalListDialog.title("OPTION")
                    .showAnim(mBasIn)//
                    .dismissAnim(mBasOut)//
                    .show();
            normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    linetypeTextView.setText(linetypeList[position]);
                    switch (position){
                        case 0://新建
                            normalListDialog.superDismiss();
                            finish();
                            break;
                        case 1://保存
                            normalListDialog.superDismiss();
                            Intent intent = new Intent(WorkOederListActivity.this, MaterialRequisitionAddNewActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            });
        }
    };
    private String searchType = "WONUM";
    private View.OnClickListener searchTypeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(WorkOederListActivity.this, new String[]{getString(R.string.item_num_title),getString(R.string.INVUSE_DESCRIPTION),getString(R.string.asset_location),"SCAN"});
            normalListDialog.title("OPTION")
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
                            searchType = "WONUM";
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
                            searchType = "UDTEMPMATERIAL";
                            search.setHint(R.string.asset_location);
                            break;
                        case 3://保存
                            normalListDialog.superDismiss();
                            Intent intent = new Intent(WorkOederListActivity.this,  MipcaActivityCapture.class);
                            intent.putExtra("mark", 1); //扫码标识
                            startActivityForResult(intent,WORKORDER_CODE);
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

    private void getIntentData(){
        type = getIntent().getStringExtra("type");
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
        button_layout = (RelativeLayout) findViewById(R.id.button_layout);
        quit = (Button) findViewById(R.id.back);
        option = (Button) findViewById(R.id.option);
    }

    @Override
    protected void initView() {
        menuImageView.setBackgroundResource(R.drawable.ic_more);
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setOnClickListener(searchTypeOnClickListener);
        button_layout.setVisibility(View.VISIBLE);
        option.setOnClickListener(optionOnClickListener);
        quit.setOnClickListener(backOnClickListener);
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backOnClickListener);
        if (type.equals("CM")) {
            titleTextView.setText(getResources().getString(R.string.work_cm));
        }else if (type.equalsIgnoreCase("PL")){
            titleTextView.setText("领料单");
        }else {
            titleTextView.setText(getResources().getString(R.string.work_pm));

        }
        setSearchEdit();

        layoutManager = new LinearLayoutManager(WorkOederListActivity.this);
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
        quit.setOnClickListener(backOnClickListener);
        option.setOnClickListener(optionOnClickListener);
        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<WORKORDER>());
        items = new ArrayList<>();
        getData(searchText);
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
    }

    @Override
    public void onRefresh() {
        page = 1;
        searchType = "WONUM";
        searchText = "";
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
                                    WorkOederListActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    assetAdapter.removeAll(items);
                    items = new ArrayList<WORKORDER>();
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
        HttpManager.getDataPagingInfo(WorkOederListActivity.this, HttpManager.getWorkOrderUrl(search,type,searchType, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<WORKORDER> item = JsonUtils.parsingWORKORDER(WorkOederListActivity.this, results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<WORKORDER>();
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
    private void initAdapter(final List<WORKORDER> list) {
        assetAdapter = new WorkOrderAdapter(WorkOederListActivity.this, R.layout.list_item_matreq, list);
        recyclerView.setAdapter(assetAdapter);
        assetAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(WorkOederListActivity.this, MaterialRequisitionDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("workorder", items.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<WORKORDER> list) {
        assetAdapter.addData(list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WORKORDER_CODE){
            switch (requestCode){
                case WORKORDER_CODE:
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
        }
    }
    /*
      * 根据SN号查询资产表是否存在
     **/
    private void isExistSN(final String serialnum) {
        HttpManager.getDataPagingInfo(this, HttpManager.getWorkOrderUrl(serialnum,type,"WONUM",20),new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<WORKORDER> item = JsonUtils.parsingWORKORDER(WorkOederListActivity.this, results.getResultlist());
                if (item == null || item.isEmpty()) {
                    Toast.makeText(WorkOederListActivity.this,"There is no such item",Toast.LENGTH_SHORT).show();
                } else {
                     WORKORDER workorder= item.get(0);
                    boolean flag = false;
                    for (int i =0; i < assetAdapter.getData().size();i++){
                        String itemnum =   assetAdapter.getItem(i).getWONUM();
                        if (itemnum.equalsIgnoreCase(workorder.getWONUM().trim())){
                            flag = true;
                            startIntent(workorder);
                            break;
                        }
                    }
                    if (flag != true){
                        assetAdapter.add(workorder);
                        startIntent(workorder);

                    }

                }
            }

            @Override
            public void onFailure(String error) {
            }
        });
    }
    public  void startIntent(WORKORDER itemintent){
        Intent intent = new Intent(this,ItemDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("workorder",itemintent);
        intent.putExtras(bundle);
        startActivityForResult(intent,0);
    }
}
