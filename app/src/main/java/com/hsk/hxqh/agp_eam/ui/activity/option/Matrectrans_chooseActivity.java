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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.adpter.Asset_chooseAdapter;
import com.hsk.hxqh.agp_eam.adpter.BaseQuickAdapter;
import com.hsk.hxqh.agp_eam.adpter.Matrectrans_chooseAdapter;
import com.hsk.hxqh.agp_eam.api.HttpManager;
import com.hsk.hxqh.agp_eam.api.HttpRequestHandler;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.model.ASSET;
import com.hsk.hxqh.agp_eam.model.INVENTORY;
import com.hsk.hxqh.agp_eam.model.MATRECTRANS;
import com.hsk.hxqh.agp_eam.model.MATUSETRANS;
import com.hsk.hxqh.agp_eam.ui.activity.BaseActivity;
import com.hsk.hxqh.agp_eam.ui.widget.SwipeRefreshLayout;
import com.hsk.hxqh.agp_eam.unit.ArrayUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by think on 2016/5/10.
 */
public class Matrectrans_chooseActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private ImageView backImageView;
    private TextView titleTextView;
    private ImageView menuImageView;

    LinearLayoutManager layoutManager;
    public RecyclerView recyclerView;
    private LinearLayout nodatalayout;
    private Matrectrans_chooseAdapter assetChooseAdapter;
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
//    public ASSET asset;
    public ArrayList<MATRECTRANS> assetArrayList = new ArrayList<>();
//    public ArrayList<Woactivity> deleteList = new ArrayList<>();

//    private BaseAnimatorSet mBasIn;
//    private BaseAnimatorSet mBasOut;
//    private LinearLayout confirmlayout;
//    private Button confirmBtn;
    private RelativeLayout buttonLiner;
    private Button select,ok;
    private boolean flag  = true;
    private View.OnClickListener selectOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0;i< assetChooseAdapter.getItemCount();i++){
                assetChooseAdapter.getItem(i).setCheckBox(flag);
            }
            assetChooseAdapter.notifyDataSetChanged();
            if (flag){
                flag =false;
                select.setText(R.string.quanbuxuan);
            }else {
                flag = true;
                select.setText(R.string.quanxuan);
            }
        }
    };
    private View.OnClickListener okOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<MATRECTRANS> inventoryArrayList = new ArrayList<>();
            for (int i=0;i<assetChooseAdapter.getItemCount();i++){
                if (assetChooseAdapter.getItem(i).isCheckBox()){
                    inventoryArrayList.add(assetChooseAdapter.getItem(i));
                }
            }
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("itemlist",inventoryArrayList);
            intent.putExtras(bundle);
            setResult(100,intent);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getIntentDate();
        findViewById();
        initView();
    }

    private void getIntentDate() {
        assetArrayList = (ArrayList<MATRECTRANS>) getIntent().getExtras().get("matrectrans");
        if (assetArrayList == null){
            assetArrayList = new ArrayList<>();
        }
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
        buttonLiner = (RelativeLayout) findViewById(R.id.button_layout);
        select = (Button) findViewById(R.id.back);
        ok = (Button) findViewById(R.id.option);
//        confirmlayout = (LinearLayout) findViewById(R.id.button_layout);
//        confirmBtn = (Button) findViewById(R.id.confirm);
    }

    @Override
    protected void initView() {
        buttonLiner.setVisibility(View.VISIBLE);
        select.setText(R.string.quanxuan);
        ok.setText(R.string.queren);
        select.setOnClickListener(selectOnClickListener);
        ok.setOnClickListener(okOnClickListener);
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backOnClickListener);
        titleTextView.setText("");
//        menuImageView.setImageResource(R.drawable.ic_add);
//        menuImageView.setOnClickListener(menuImageViewOnClickListener);
//        confirmlayout.setVisibility(View.GONE);
//        confirmBtn.setOnClickListener(confirmBtnOnClickListener);
        layoutManager = new LinearLayoutManager(Matrectrans_chooseActivity.this);
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
        initAdapter(new ArrayUtil<MATRECTRANS>());

        setSearchEdit();

//        mBasIn = new BounceTopEnter();
//        mBasOut = new SlideBottomExit();

    }

    @Override
    public void onStart() {
        super.onStart();
        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<MATRECTRANS>());
        getData(searchText);
    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
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
                                    Matrectrans_chooseActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    assetChooseAdapter.removeAll(assetChooseAdapter.getData());
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
        List<MATRECTRANS> item = assetArrayList;
        refresh_layout.setRefreshing(false);
        refresh_layout.setLoading(false);
        if (item != null || item.size() != 0) {
            if(search != null&&!search.equalsIgnoreCase("")){
                for (int i = 0;i<item.size();i++){
                    double back = Double.parseDouble(item.get(i).getORDERQTYBackup());
                    if (back > 0) {
                        if (item.get(i).getITEMNUM().contains(search)) {
                            assetChooseAdapter.add(item.get(i));
                        } else if (item.get(i).getDESCRIPTION().contains(search)) {
                            assetChooseAdapter.add(item.get(i));
                        }
                    }
                }
            }else {
                nodatalayout.setVisibility(View.GONE);
                for ( MATRECTRANS matrectrans:item) {
                    double orderqty = Double.parseDouble(matrectrans.getORDERQTY());
                    double back = Double.parseDouble(matrectrans.getORDERQTYBackup());
                    if (back > 0){
                        assetChooseAdapter.add(matrectrans);
                    }
                }
            }
        }else {
            nodatalayout.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<MATRECTRANS> list) {
        assetChooseAdapter = new Matrectrans_chooseAdapter(Matrectrans_chooseActivity.this, R.layout.list_itemmatchoose, list);
        assetChooseAdapter.setShowcheckbox(true);
        recyclerView.setAdapter(assetChooseAdapter);
        assetChooseAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
              /*  Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("matrectrans",assetChooseAdapter.getItem(position));
                intent.putExtras(bundle);
                setResult(100,intent);
                finish();*/
//                startAcstivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<MATRECTRANS> list) {
        assetChooseAdapter.addData(list);
    }


    @Override
    public void onRefresh() {
            page = 1;
            assetChooseAdapter.removeAll(assetChooseAdapter.getData());
            getData(searchText);
    }

    @Override
    public void onLoad() {
    }
}
