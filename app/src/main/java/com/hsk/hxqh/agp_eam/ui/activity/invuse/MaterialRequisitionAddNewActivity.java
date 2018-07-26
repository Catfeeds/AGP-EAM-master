package com.hsk.hxqh.agp_eam.ui.activity.invuse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.adpter.BaseQuickAdapter;
import com.hsk.hxqh.agp_eam.api.HttpManager;
import com.hsk.hxqh.agp_eam.api.HttpRequestHandler;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.config.Constants;
import com.hsk.hxqh.agp_eam.model.LOCATIONS;
import com.hsk.hxqh.agp_eam.model.SUBJECT;
import com.hsk.hxqh.agp_eam.model.WORKORDER;
import com.hsk.hxqh.agp_eam.model.WPMATERIAL;
import com.hsk.hxqh.agp_eam.model.WebResult;
import com.hsk.hxqh.agp_eam.ui.activity.BaseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.invuse.adapter.WpmaterialAdapter2;
import com.hsk.hxqh.agp_eam.ui.activity.option.Location_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.widget.SwipeRefreshLayout;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;
import com.hsk.hxqh.agp_eam.unit.ArrayUtil;
import com.hsk.hxqh.agp_eam.webserviceclient.AndroidClientService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zzw on 2018/7/2.
 */

public class MaterialRequisitionAddNewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    private ImageView moreImg;
    private PopupWindow popupWindow;
    private LinearLayout bujianLayout;//部件
    private LinearLayout beijianLayout;//备件
    private LinearLayout workLayout;//工单历史

    private TextView wonum;
    private TextView description;
    private TextView dep;
    private TextView fromstoreloc;
    private TextView invowner;
    private TextView creatby;
    private TextView creatdate;

    private WORKORDER workorder ;
    private Button option;
    private Button quit;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    public RecyclerView recyclerView;
    private LinearLayout nodatalayout;
    private WpmaterialAdapter2 wpmaterialAdapter;
    ArrayList<WPMATERIAL> items = new ArrayList<>();
    LinearLayoutManager layoutManager;
    private SwipeRefreshLayout refresh_layout = null;
    private ProgressDialog mProgressDialog;
    private String type;
    private RelativeLayout relativeLayout;
    private TextView subject;;
    private LinearLayout mtLinearLayout;
    private TextView status;
    private ImageView locationImg;
    private ImageView udstationnumImg;
    private Spinner subjectImg;
    private ArrayAdapter<String> adapter;
    private LinearLayout wonumLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matreq_addnew);
        findViewById();
        initView();
//
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }
    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);
        moreImg = (ImageView) findViewById(R.id.title_more);
        wonum = (TextView)findViewById(R.id.matreq_wonum_id);
        description = (TextView)findViewById(R.id.description_text_id);
        dep = (TextView)findViewById(R.id.dep_text_id);
        fromstoreloc = (TextView)findViewById(R.id.fromstoreloc_text_id);
        invowner = (TextView)findViewById(R.id.invowner_text_id);
        creatby  = (TextView)findViewById(R.id.creatby_text_id);
        creatdate = (TextView)findViewById(R.id.createdate_text_id);
        option = (Button) findViewById(R.id.option);
        quit = (Button) findViewById(R.id.back);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);
        nodatalayout = (LinearLayout) findViewById(R.id.have_not_data_id);
        refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        relativeLayout = (RelativeLayout) findViewById(R.id.button_layout);
        subject = (TextView)findViewById(R.id.matreq_subject_id);
        status = (TextView) findViewById(R.id.matreq_status_id);
        locationImg = (ImageView)findViewById(R.id.location_image_id);
        subjectImg = (Spinner) findViewById(R.id.subject_image_id);
        udstationnumImg = (ImageView) findViewById(R.id.UDSTATIONNUM_image_id);
        wonumLayout  = (LinearLayout) findViewById(R.id.wonum_layout);

    }

    @Override
    protected void initView() {
        wonumLayout.setVisibility(View.GONE);
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,SUBJECT.list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        subjectImg.setOnItemSelectedListener(new SpinnerSelectedListener());
        subjectImg.setAdapter(adapter);
        workorder = new WORKORDER();
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.lingliaodan);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        option.setOnClickListener(optionOnClickListener);
        refresh_layout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refresh_layout.setRefreshing(false);

        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnLoadListener(this);
        initAdapter(new ArrayList<WPMATERIAL>());
        relativeLayout.setVisibility(View.VISIBLE);
        locationImg.setOnClickListener(locationOnClickListener);
        udstationnumImg.setOnClickListener(udstationnumOnClickListener);
        status.setText("WAPPR");
        workorder.setSTATUS("WAPPR");
        creatby.setText(AccountUtils.getpersonId(this));
        workorder.setCREATEBY(AccountUtils.getpersonId(this));
        workorder.setCREATEBYNAME(AccountUtils.getpersonId(this));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        creatdate.setText(simpleDateFormat.format(new Date()));
        workorder.setCREATEDATE(simpleDateFormat.format(new Date()));
        quit.setOnClickListener(backImageViewOnClickListener);

    }
    private View.OnClickListener locationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i =  wpmaterialAdapter.getData().size();
            if ( i == 0){
               Intent intent = new Intent(MaterialRequisitionAddNewActivity.this,  Location_chooseActivity.class);
               Bundle bundle = new Bundle();
               bundle.putString("type","STOREROOM");
               bundle.putString("from","location");
               intent.putExtras(bundle);
               startActivityForResult(intent, 0);
           }else {
               Toast.makeText(MaterialRequisitionAddNewActivity.this,"No modification allowed",Toast.LENGTH_SHORT).show();
           }
        }
    };
    private View.OnClickListener udstationnumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MaterialRequisitionAddNewActivity.this,  Location_chooseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("type","STOREROOM");
            bundle.putString("from","udstationnum");
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
        }
    };

    private void initAdapter(ArrayList<WPMATERIAL> wpmaterials) {
        wpmaterialAdapter = new WpmaterialAdapter2(this, R.layout.list_item_wpmaterial, wpmaterials);
        recyclerView.setAdapter(wpmaterialAdapter);
        wpmaterialAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MaterialRequisitionAddNewActivity.this, MatWpmaterialDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("workorder",workorder);
                bundle.putSerializable("wpmaterial",wpmaterialAdapter.getItem(position));
                intent.putExtras(bundle);
                startActivityForResult(intent,position);
            }
        }
        );
        wpmaterialAdapter.setOnRecyclerViewItemLongClickListener(new BaseQuickAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                wpmaterialAdapter.remove(position);
                return true;
            }
        });
    }

    private void addData(ArrayList<WPMATERIAL> item) {
        wpmaterialAdapter.addData(item);
    }

    @Override
    public void onRefresh() {
            refresh_layout.setRefreshing(false);
    }

    @Override
    public void onLoad() {
        refresh_layout.setLoading(false);
    }
    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(MaterialRequisitionAddNewActivity.this, new String[]{getString(R.string.newline),getString(R.string.save)});
            normalListDialog.title(getString(R.string.option))
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
                            Intent intent = new Intent(MaterialRequisitionAddNewActivity.this, MatWpmaterialDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("flag","I");
                            bundle.putSerializable("workorder",workorder);
                            intent.putExtras(bundle);
                            startActivityForResult(intent,wpmaterialAdapter.getItemCount());
                            break;
                        case 1://保存
                            normalListDialog.superDismiss();
                            submitDataInfo();
                            break;
                    }
                }
            });
        }
    };
    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 110:
                Bundle bundle =data.getExtras();
                LOCATIONS locations = (LOCATIONS) bundle.get("location");
               String from = bundle.getString("from");
                if (from!=null && from.equalsIgnoreCase("location")){
                    fromstoreloc.setText(locations.getLOCATION());
                    invowner.setText(locations.getINVOWNER());
                    workorder.setINVONAME(locations.getINVOWNER());
                    workorder.setLOCATION(locations.getLOCATION());
                }else {
                    dep.setText(locations.getLOCATION());
                    workorder.setUDTEMPMATERIAL(locations.getLOCATION());
                    workorder.setUDSTATIONNUM(locations.getLOCATION());
                }
                break;
            case 100:
                WPMATERIAL wpmaterial = (WPMATERIAL) data.getExtras().get("wpmaterial");
                if (wpmaterial!=null){
                    if (wpmaterial.getFLAG().equalsIgnoreCase("I")){
                        if (requestCode<wpmaterialAdapter.getItemCount()){
                            wpmaterialAdapter.remove(requestCode);
                            wpmaterialAdapter.add(requestCode,wpmaterial);
                        }else {
                            wpmaterialAdapter.add(wpmaterial);
                        }
                    }else if (wpmaterial.getFLAG().equalsIgnoreCase("D")){
                        wpmaterialAdapter.remove(requestCode);
                    }
                }
                break;
        }
    }
    private void submitDataInfo() {
        final NormalDialog dialog = new NormalDialog(this);
        dialog.content(getString(R.string.suretosave))//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        showProgressDialog("......");
                        startAsyncTask();
                        dialog.dismiss();
                    }
                });
    }
    private void startAsyncTask() {
        String updataInfo = null;
        String wpjson = null;
       try {
           workorder.setDESCRIPTION(description.getText().toString());
           Gson gson = new GsonBuilder().serializeNulls().create();
           JSONObject jsonObject = new JSONObject(gson.toJson(workorder));
           if (workorder.getWONUM()==null || workorder.getWONUM().isEmpty()){
               jsonObject.put("FLAG","I");
           }else {
               jsonObject.put("FLAG","U");
           }

           updataInfo = "["+ jsonObject.toString()+"]";

       }catch (Exception e){
           e.printStackTrace();
       }

        final String finalUpdataInfo = updataInfo;
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                  String woString = AndroidClientService.insertWO(MaterialRequisitionAddNewActivity.this, finalUpdataInfo);
                List<WPMATERIAL> list = new ArrayUtil<>();
                if (woString.contains("succeed")){
                    String  wonum1 = woString.split(":")[1];
                    if (workorder.getWONUM()==null|| workorder.getWONUM().isEmpty()){
                        workorder.setWONUM(wonum1);
                    }
                    for (int i = 0;i < wpmaterialAdapter.getItemCount();i++){
                        WPMATERIAL wpmaterial = wpmaterialAdapter.getItem(i);
                        wpmaterial.setWONUM(workorder.getWONUM());
                        list.add(wpmaterial);
                    }
                }
                Gson gson = new GsonBuilder().serializeNulls().create();
                String wpjson = gson.toJson(list);
                String str = AndroidClientService.insertWPITEM(MaterialRequisitionAddNewActivity.this, wpjson);

                return woString +"==" + str;
            }

            @Override
            protected void onPostExecute(String workResult) {
                boolean flag = false;
                super.onPostExecute(workResult);
                String woStr = workResult.split("==")[0];
                String wpStr = workResult.split("==")[1];
                if (wpmaterialAdapter.getData().isEmpty()) {
                    if (woStr.contains("Insert succeed")) {
                        wonum.setText(workorder.getWONUM());
                        flag = true;
                        Toast.makeText(MaterialRequisitionAddNewActivity.this, R.string.success, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MaterialRequisitionAddNewActivity.this, R.string.fail, Toast.LENGTH_LONG).show();
                    }
                    closeProgressDialog();
                } else {
                    if (woStr.contains("succeed") && wpStr.contains("succeed")) {
                        wonum.setText(workorder.getWONUM());
                        flag= true;
                        Toast.makeText(MaterialRequisitionAddNewActivity.this, R.string.success, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MaterialRequisitionAddNewActivity.this,  R.string.fail, Toast.LENGTH_LONG).show();
                    }
                    closeProgressDialog();
                }
                if (flag){
                    intentToDetail();
                }
            }
        }.execute();
    }
    private void intentToDetail(){
        Intent intent = new Intent(this, MaterialRequisitionDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("workorder", workorder);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
        finish();
    }
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
                String m = adapter.getItem(arg2);
                workorder.setSUBJECT(SUBJECT.stringStringMap.get(m));
                subject.setText(m);

        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}
