package com.hsk.hxqh.agp_eam.ui.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.hsk.hxqh.agp_eam.adpter.WfassignmentAdapter;
import com.hsk.hxqh.agp_eam.api.HttpManager;
import com.hsk.hxqh.agp_eam.api.HttpRequestHandler;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.model.ASSET;
import com.hsk.hxqh.agp_eam.model.PROCESSNAME;
import com.hsk.hxqh.agp_eam.model.WFASSIGNMENT;
import com.hsk.hxqh.agp_eam.ui.activity.InventoryListActivity;
import com.hsk.hxqh.agp_eam.ui.activity.MipcaActivityCapture;
import com.hsk.hxqh.agp_eam.ui.activity.Wfm_Details_Activity;
import com.hsk.hxqh.agp_eam.ui.widget.SwipeRefreshLayout;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;
import com.hsk.hxqh.agp_eam.unit.UHFReader;

import java.util.ArrayList;
import java.util.List;

import android_serialport_api.UHFHXAPI;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Administrator on 2017/2/27.
 *
 * 代办任务
 */

public class TaskFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener{
    private static final String TAG = "TaskFragment";
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
    private WfassignmentAdapter wfassignmentAdapter;
    /**
     * 编辑框*
     */
    private EditText search;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;
    private String type = "";
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;


    ArrayList<WFASSIGNMENT> items = new ArrayList<WFASSIGNMENT>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArguments().get("asset");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container,
                false);
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
        findByIdView(view);
        initView();
        return view;
    }

    /**
     * 初始化界面组件*
     */
    private void findByIdView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_id);
        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        nodatalayout = (LinearLayout) view.findViewById(R.id.have_not_data_id);
        search = (EditText) view.findViewById(R.id.search_edit);
        menuImageView = (ImageView) view.findViewById(R.id.title_more);
    }


    /**
     * 设置事件监听*
     */
    private void initView() {
        setSearchEdit();
        menuImageView.setBackgroundResource(R.drawable.ic_more);
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setOnClickListener(optionOnClickListener);
        layoutManager = new LinearLayoutManager(getActivity());
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

    @Override
    public void onStart() {
        super.onStart();
        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<WFASSIGNMENT>());
        items = new ArrayList<>();
        getData(searchText);
    }

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
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        msp.setSpan(new ImageSpan(drawable), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        search.setHint(msp);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    getActivity().getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    wfassignmentAdapter.removeAll(items);
                    items = new ArrayList<WFASSIGNMENT>();
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
    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog;
            normalListDialog = new NormalListDialog(TaskFragment.this.getActivity(),new String[]{getString(R.string.bianhao),getString(R.string.INVUSE_DESCRIPTION)});
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
                            type = "WFASSIGNMENTID";
                            search.setHint(R.string.bianhao);
                            break;
                        case 1:
                            normalListDialog.superDismiss();
                            type = "DESCRIPTION";
                            search.setHint(R.string.asset_description);
                            break;
                    }
//                    normalListDialog.dismiss();
                }
            });
        }
    };

    /**
     * 获取数据*
     */
    private void getData(String search) {
        HttpManager.getDataPagingInfo(getActivity(), HttpManager.getwfassignmentUrl(AccountUtils.getpersonId(getActivity()),search,type, page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<WFASSIGNMENT> item = JsonUtils.parsingWFASSIGNMENT(getActivity(), results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<WFASSIGNMENT>();
                            initAdapter(items);
                        }
                        for (int i = 0; i < item.size(); i++) {
                            if (PROCESSNAME.PROCESSNAME.indexOf(item.get(i).getPROCESSNAME())>= 0){
                                items.add(item.get(i));
                            }
                        }
                        addData(items);
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
    private void initAdapter(final List<WFASSIGNMENT> list) {
        wfassignmentAdapter = new WfassignmentAdapter(getActivity(), R.layout.list_item_wfassignment, list);
        recyclerView.setAdapter(wfassignmentAdapter);
        wfassignmentAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), Wfm_Details_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("wfassignment", items.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<WFASSIGNMENT> list) {
        wfassignmentAdapter.addData(list);
    }

}
