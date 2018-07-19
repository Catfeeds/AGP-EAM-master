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
import com.hsk.hxqh.agp_eam.adpter.Asset_chooseAdapter;
import com.hsk.hxqh.agp_eam.adpter.BaseQuickAdapter;
import com.hsk.hxqh.agp_eam.adpter.Invresvre_chooseAdapter;
import com.hsk.hxqh.agp_eam.api.HttpManager;
import com.hsk.hxqh.agp_eam.api.HttpRequestHandler;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.model.ASSET;
import com.hsk.hxqh.agp_eam.model.INVRESERVE;
import com.hsk.hxqh.agp_eam.model.ITEM;
import com.hsk.hxqh.agp_eam.ui.activity.BaseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.ItemListActivity;
import com.hsk.hxqh.agp_eam.ui.activity.MipcaActivityCapture;
import com.hsk.hxqh.agp_eam.ui.widget.SwipeRefreshLayout;
import com.hsk.hxqh.agp_eam.unit.ArrayUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by think on 2016/5/10.
 */
public class INVRESERVE_chooseActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final  int INVRESERVE_CODE = 1366;
    private ImageView backImageView;
    private TextView titleTextView;
    private ImageView menuImageView;

    LinearLayoutManager layoutManager;
    public RecyclerView recyclerView;
    private LinearLayout nodatalayout;
    private Invresvre_chooseAdapter assetChooseAdapter;
    private SwipeRefreshLayout refresh_layout = null;
    private int page = 1;
    private String[] optionList = new String[]{"SCAN"};
    /**
     * 编辑框*
     */
    private EditText search;
    /**
     * 查询条件*
     */
    private String searchText = "";
//    public ASSET asset;
    public ArrayList<INVRESERVE> assetArrayList;
//    public ArrayList<Woactivity> deleteList = new ArrayList<>();

//    private BaseAnimatorSet mBasIn;
//    private BaseAnimatorSet mBasOut;
//    private LinearLayout confirmlayout;
//    private Button confirmBtn;
        private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getIntentDate();
        findViewById();
        initView();
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void getIntentDate() {
        assetArrayList = (ArrayList<INVRESERVE>) getIntent().getExtras().get("invreservesList");
         if (assetArrayList == null){
             assetArrayList = new ArrayList<INVRESERVE>();
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
//        confirmlayout = (LinearLayout) findViewById(R.id.button_layout);
//        confirmBtn = (Button) findViewById(R.id.confirm);
    }

    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backOnClickListener);
        titleTextView.setText("Reserve");
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setOnClickListener(optionOnClickListener);
//        menuImageView.setImageResource(R.drawable.ic_add);
//        menuImageView.setOnClickListener(menuImageViewOnClickListener);
//        confirmlayout.setVisibility(View.GONE);
//        confirmBtn.setOnClickListener(confirmBtnOnClickListener);
        layoutManager = new LinearLayoutManager(INVRESERVE_chooseActivity.this);
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
    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(INVRESERVE_chooseActivity.this, optionList);
            normalListDialog.title("查询条件")
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
                            Intent intent = new Intent(INVRESERVE_chooseActivity.this, MipcaActivityCapture.class);
                            intent.putExtra("mark", 1); //扫码标识
                            startActivityForResult(intent, INVRESERVE_CODE);
                            break;
                    }
//                    normalListDialog.dismiss();
                }
            });
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<INVRESERVE>());
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
                                    INVRESERVE_chooseActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    assetChooseAdapter.removeAll(assetArrayList);
                    assetArrayList = new ArrayList<INVRESERVE>();
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
                ArrayList<INVRESERVE> item = assetArrayList;
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {
                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            assetArrayList = new ArrayList<INVRESERVE>();
                            initAdapter(assetArrayList);
                        }
                        for (int i = 0; i < item.size(); i++) {
                            assetArrayList.add(item.get(i));
                        }
                        addData(item);
                    }
                    nodatalayout.setVisibility(View.GONE);
                    initAdapter(assetArrayList);
                }
    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<INVRESERVE> list) {
        assetChooseAdapter = new Invresvre_chooseAdapter(INVRESERVE_chooseActivity.this, R.layout.list_item, list);
        recyclerView.setAdapter(assetChooseAdapter);
        assetChooseAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("INVRESERVE", assetChooseAdapter.getItem(position));
                intent.putExtras(bundle);
                setResult(130,intent);
                finish();
//                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<INVRESERVE> list) {
        assetChooseAdapter.addData(list);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case INVRESERVE_CODE:
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
    private void isExistSN(final String serialnum) {
        for (INVRESERVE item:assetArrayList) {
            if (item.getITEMNUM()!=null && item.getITEMNUM().equalsIgnoreCase(serialnum)) {
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("INVRESERVE", item);
                intent.putExtras(bundle);
                setResult(130, intent);
                finish();
            } else {
                Toast.makeText(INVRESERVE_chooseActivity.this, "There is no such item", Toast.LENGTH_SHORT).show();

            }
        }

    }
}
