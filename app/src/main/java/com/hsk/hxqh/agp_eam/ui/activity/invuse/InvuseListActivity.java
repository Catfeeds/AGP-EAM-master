package com.hsk.hxqh.agp_eam.ui.activity.invuse;

import android.arch.lifecycle.Observer;
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
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.adpter.BaseQuickAdapter;
import com.hsk.hxqh.agp_eam.api.HttpManager;
import com.hsk.hxqh.agp_eam.api.HttpRequestHandler;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.model.INVUSEEntity;
import com.hsk.hxqh.agp_eam.model.INVUSEEntity;
import com.hsk.hxqh.agp_eam.model.ITEM;
import com.hsk.hxqh.agp_eam.ui.activity.BaseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.InventoryListActivity;
import com.hsk.hxqh.agp_eam.ui.activity.ItemListActivity;
import com.hsk.hxqh.agp_eam.ui.activity.MipcaActivityCapture;
import com.hsk.hxqh.agp_eam.ui.activity.WorkOrderAddNewActivity;
import com.hsk.hxqh.agp_eam.ui.activity.invuse.adapter.InvuseAdapter;
import com.hsk.hxqh.agp_eam.ui.widget.SwipeRefreshLayout;
import com.hsk.hxqh.agp_eam.unit.TestUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxs on 2018/4/3.
 */

public class InvuseListActivity  extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener{
    private static final String TAG = "InventoryListActivity";
    public static final int INVUSESCAN_CODE = 1111;
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
    private InvuseAdapter itemAdapter;
    /**
     * 编辑框*
     */
    private EditText search;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<INVUSEEntity> items = new ArrayList<INVUSEEntity>();
    private String type;

    private Button option;
    private Button back;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private RelativeLayout relativeLayout;
    private String serchType = "";
    private ArrayList<DialogMenuItem> dialogMenuItems= new ArrayList();

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

/*    @Override
    public void onStart() {
        super.onStart();
        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<INVUSEEntity>());
        items = new ArrayList<>();
        getData(searchText);
        getIntentData();
    }*/

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
        relativeLayout = (RelativeLayout)findViewById(R.id.button_layout);
        option = (Button) findViewById(R.id.option);
        back = (Button) findViewById(R.id.back);

    }

    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backOnClickListener);
        if (type.equals("MI")) {
            titleTextView.setText(getResources().getString(R.string.outbound));
        }else if (type.equals("MR")){
            titleTextView.setText(R.string.refund);
        }else {
            titleTextView.setText(R.string.invuse_transfer);
        }
        setSearchEdit();
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setBackgroundResource(R.drawable.ic_more);
        menuImageView.setOnClickListener(serchTypeOnClickListener);
        layoutManager = new LinearLayoutManager(InvuseListActivity.this);
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
        option.setOnClickListener(optionOnClickListener);
        back.setOnClickListener(backOnClickListener);
        relativeLayout.setVisibility(View.VISIBLE);
        initDialogItems();
        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<INVUSEEntity>());
        items = new ArrayList<>();
        getData(searchText);

    }
    private View.OnClickListener serchTypeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(InvuseListActivity.this,dialogMenuItems);
            normalListDialog.title("Option")
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
                            serchType = "INVUSENUM";
                            search.setText("");
                            search.setHint(R.string.INVUSENUMI);
                            break;
                        case 1://Add
                            normalListDialog.superDismiss();
                            search.setText("");
                        search.setHint(R.string.description_text);
                            break;
                        case 2:
                            normalListDialog.superDismiss();
                            serchType = "FROMSTORELOC";
                            search.setText("");
                            search.setHint(R.string.FROMSTORELOC);
                            break;
                        case 3:
                            normalListDialog.superDismiss();
                            Intent intent = new Intent(InvuseListActivity.this,  MipcaActivityCapture.class);
                            intent.putExtra("mark", 1); //扫码标识
                            startActivityForResult(intent, INVUSESCAN_CODE);
                            break;
                    }
//                    normalListDialog.dismiss();
                }
            });
        }
    };
    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String [] optionList = {getString(R.string.back),getString(R.string.xinjian)};
            final NormalListDialog normalListDialog = new NormalListDialog(InvuseListActivity.this, optionList);
            normalListDialog.title("Option")
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
                            finish();
                            break;
                        case 1://Add
                            normalListDialog.superDismiss();
                            Intent intent = new Intent(InvuseListActivity.this, InvusemiAddNewActivity.class);
                            intent.putExtra("type",type);
                            startActivity(intent);
                            break;
                    }
//                    normalListDialog.dismiss();
                }
            });
        }
    };

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    public void onLoad() {
/*        page++;
        getData(searchText);*/
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
                                    InvuseListActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    itemAdapter.removeAll(items);
                    items = new ArrayList<INVUSEEntity>();
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

        commonViewModel.getSelectLiveData(InvuseListActivity.this, HttpManager.getINVUSEUrl(search,serchType, page, 20,type), INVUSEEntity.class).observe(InvuseListActivity.this, new Observer<ArrayList<INVUSEEntity>>() {
            @Override
            public void onChanged(ArrayList<INVUSEEntity> item) {
                if (TestUtil.isNotNull(item)) {
                    refresh_layout.setRefreshing(false);
                    refresh_layout.setLoading(false);
                    if (item == null || item.isEmpty()) {
                        nodatalayout.setVisibility(View.VISIBLE);
                    } else {
                        if (item != null || item.size() != 0) {
                            if (page == 1) {
                                items = new ArrayList<INVUSEEntity>();
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
                }else {
                    refresh_layout.setRefreshing(false);
                    nodatalayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<INVUSEEntity> list) {
        itemAdapter = new InvuseAdapter(InvuseListActivity.this, R.layout.list_item_inventory, list);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
               Intent intent = new Intent(InvuseListActivity.this, InvuseDetailActivity.class);
               Bundle bundle = new Bundle();
               bundle.putSerializable("item", items.get(position));
               bundle.putString("type",type);
               intent.putExtras(bundle);
               startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<INVUSEEntity> list) {
        itemAdapter.addData(list);
    }
    private void initDialogItems(){

        dialogMenuItems.add(new DialogMenuItem(getString(R.string.item_num_title),1));
        dialogMenuItems.add(new DialogMenuItem(getString(R.string.inventory_itemnum_dec),2));
        dialogMenuItems.add(new DialogMenuItem(getString(R.string.FROMSTORELOC),3));
        dialogMenuItems.add(new DialogMenuItem("SCAN",4));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INVUSESCAN_CODE){
            switch (requestCode){
                case INVUSESCAN_CODE:
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
    /**
     * 根据SN号查询资产表是否存在
     **/
    private void isExistSN(final String serialnum) {
        itemAdapter.removeAll(itemAdapter.getData());
        commonViewModel.getSelectLiveData(InvuseListActivity.this, HttpManager.getINVUSEUrl(serialnum,"INVUSENUM", page, 20,type), INVUSEEntity.class).observe(InvuseListActivity.this, new Observer<ArrayList<INVUSEEntity>>() {
            @Override
            public void onChanged(ArrayList<INVUSEEntity> item) {
                if (TestUtil.isNotNull(item)) {
                    refresh_layout.setRefreshing(false);
                    refresh_layout.setLoading(false);
                    if (item == null || item.isEmpty()) {
                        nodatalayout.setVisibility(View.VISIBLE);
                        Toast.makeText(InvuseListActivity.this,"There is no such item",Toast.LENGTH_SHORT).show();
                    } else {
                        INVUSEEntity invuseEntity = item.get(0);
                        boolean flag = false;
                        for (int i =0; i < itemAdapter.getData().size();i++){
                            String itemnum =   itemAdapter.getItem(i).getINVUSENUM();
                            if (itemnum.equalsIgnoreCase(invuseEntity.getINVUSENUM().trim())){
                                flag = true;
                                Intent intent = new Intent(InvuseListActivity.this, InvuseDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("item", itemAdapter.getItem(i));
                                bundle.putString("type",type);
                                intent.putExtras(bundle);
                                startActivityForResult(intent, 0);

                                break;
                            }
                        }
                        if (flag != true){
                            itemAdapter.add(invuseEntity);
                            Intent intent = new Intent(InvuseListActivity.this, InvuseDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("item",invuseEntity);
                            bundle.putString("type",type);
                            intent.putExtras(bundle);
                            startActivityForResult(intent, 0);
                        }
                    }
                }else {
                    refresh_layout.setRefreshing(false);
                    nodatalayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
