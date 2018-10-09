package com.hsk.hxqh.agp_eam.ui.activity.invuse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
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
import com.flyco.dialog.widget.MaterialDialog2;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.adpter.BaseQuickAdapter;
import com.hsk.hxqh.agp_eam.adpter.InvuseLineAdaper;
import com.hsk.hxqh.agp_eam.adpter.WpmaterialAdapter;
import com.hsk.hxqh.agp_eam.api.HttpManager;
import com.hsk.hxqh.agp_eam.api.HttpRequestHandler;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.config.Constants;
import com.hsk.hxqh.agp_eam.model.INVENTORY;
import com.hsk.hxqh.agp_eam.model.INVUSEEntity;
import com.hsk.hxqh.agp_eam.model.INVUSELINE;
import com.hsk.hxqh.agp_eam.model.LOCATIONS;
import com.hsk.hxqh.agp_eam.model.MATUSETRANS;
import com.hsk.hxqh.agp_eam.model.SUBJECT;
import com.hsk.hxqh.agp_eam.model.UDDEPT;
import com.hsk.hxqh.agp_eam.model.WORKORDER;
import com.hsk.hxqh.agp_eam.model.WPMATERIAL;
import com.hsk.hxqh.agp_eam.model.WebResult;
import com.hsk.hxqh.agp_eam.ui.activity.BaseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.WorkOederListActivity;
import com.hsk.hxqh.agp_eam.ui.activity.WorkOrderDetailsActivity;
import com.hsk.hxqh.agp_eam.ui.activity.WpmaterialDetailsActivity;
import com.hsk.hxqh.agp_eam.ui.activity.WxDemoActivity;
import com.hsk.hxqh.agp_eam.ui.activity.invuse.adapter.WpmaterialAdapter2;
import com.hsk.hxqh.agp_eam.ui.activity.option.Dept_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.INVRESERVE_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Inventory_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Location_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.MATUSETRANS_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.widget.SwipeRefreshLayout;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;
import com.hsk.hxqh.agp_eam.unit.ArrayUtil;
import com.hsk.hxqh.agp_eam.webserviceclient.AndroidClientService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by zzw on 2018/7/2.
 */

public class MaterialRequisitionDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
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
    private ArrayAdapter<String> adapter;
    private Spinner spinner;
    private boolean isfirst =true;
    private ImageView depImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matreq_details);
        getSubjectList();
        findViewById();
        initView();

//
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
        EventBus.getDefault().register(this);
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
        spinner = (Spinner) findViewById(R.id.subject_image_id);
        depImg = (ImageView) findViewById(R.id.dep_img_id);
    }

    @Override
    protected void initView() {
        getIntentData();
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
        refresh_layout.setRefreshing(true);
        refresh_layout.setLoading(true);
        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnLoadListener(this);
        getData();
        initAdapter(new ArrayList<WPMATERIAL>());
        relativeLayout.setVisibility(View.VISIBLE);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        spinner.setAdapter(adapter);
        depImg.setOnClickListener(udstationnumOnClickListener);
        quit.setOnClickListener(backImageViewOnClickListener);
        if (workorder!=null){
            wonum.setText(workorder.getWONUM());
            description.setText(workorder.getDESCRIPTION());
            dep .setText(workorder.getUDSTATIONNUM());
            fromstoreloc.setText(workorder.getUDTEMPMATERIAL());
            //invowner .setText(workorder.get);
            creatby .setText(workorder.getCREATEBY());
            creatdate.setText(workorder.getCREATEDATE());
            String sub = workorder.getSUBJECT();
            subject.setText(SUBJECT.getKeyByValue(sub));
            status.setText(workorder.getSTATUS());
            invowner.setText(workorder.getINVONAME());
        }
    }

    private void initAdapter(final ArrayList<WPMATERIAL> wpmaterials) {
        wpmaterialAdapter = new WpmaterialAdapter2(this, R.layout.list_item_wpmaterial, wpmaterials);
        recyclerView.setAdapter(wpmaterialAdapter);
        wpmaterialAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!wpmaterialAdapter.getItem(position).getFLAG().equalsIgnoreCase("D")){
                    Intent intent = new Intent(MaterialRequisitionDetailActivity.this, MatWpmaterialDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("wpmaterial", wpmaterialAdapter.getItem(position));
                    bundle.putSerializable("workorder", workorder);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, position);
                }
            }
        });
    }

    private void getData() {
        HttpManager.getDataPagingInfo(this, HttpManager.getWpmateriaLUrl(workorder.getWONUM(),1,20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                Log.e("库存", "onSuccess: "+results.toString() );
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                ArrayList<WPMATERIAL> item = JsonUtils.parsingWPMATERIAL(MaterialRequisitionDetailActivity.this, results.getResultlist());
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        items = new ArrayList<WPMATERIAL>();
                        initAdapter(items);
                    }
                    for (int i = 0; i < item.size(); i++) {
                        WPMATERIAL wpmaterial = item.get(i);
                        wpmaterial.setLINE(i);
                        items.add(wpmaterial);
                    }
                    addData(item);
                }
                nodatalayout.setVisibility(View.GONE);
                initAdapter(items);
            }

            @Override
            public void onFailure(String error) {nodatalayout.setVisibility(View.VISIBLE);
            }
        });

    }

    private void addData(ArrayList<WPMATERIAL> item) {
        wpmaterialAdapter.addData(item);
    }

    private void getIntentData() {
        workorder = (WORKORDER) getIntent().getExtras().get("workorder");
        getWorkORder(workorder.getWONUM());
        if (workorder == null){
            workorder = new WORKORDER();
        }
    }

    @Override
    public void onRefresh() {
        wpmaterialAdapter.removeAll(wpmaterialAdapter.getData());
        getData();
    }

    @Override
    public void onLoad() {
        getData();
    }
    private View.OnClickListener udstationnumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MaterialRequisitionDetailActivity.this, Dept_chooseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("type","STOREROOM");
            bundle.putString("from","udstationnum");
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(MaterialRequisitionDetailActivity.this, new String[]{getString(R.string.work_workflow),getString(R.string.work_upload),getString(R.string.xinjian),getString(R.string.newline),getString(R.string.delete),getString(R.string.save)});
            normalListDialog.title(getString(R.string.option))
                    .showAnim(mBasIn)//
                    .dismissAnim(mBasOut)//
                    .show();
            normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    linetypeTextView.setText(linetypeList[position]);
                    switch (position){
                        case 0://返回
                            normalListDialog.superDismiss();
                            Log.e(TAG, "onOperItemClick:返回 " );
                            MaterialDialogOneBtn();
                            break;
                        case 1:
                            normalListDialog.superDismiss();
                            Intent intent2 = new Intent(MaterialRequisitionDetailActivity.this, WxDemoActivity.class);
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("ownertable", WORKORDER.TABLE_NAME);
                            bundle1.putString("ownerid",workorder.getWORKORDERID());
                            intent2.putExtras(bundle1);
                            startActivity(intent2);
                            break;
                        case 2://新建
                            normalListDialog.superDismiss();
                            Intent intent = new Intent(MaterialRequisitionDetailActivity.this, MaterialRequisitionAddNewActivity.class);
                            startActivity(intent);
                            break;
                        case 3:
                            normalListDialog.superDismiss();
                            getMaterialLine();
                            break;
                        case 4:
                            normalListDialog.superDismiss();
                            deleteWO();
                            break;
                        case 5:
                            normalListDialog.superDismiss();
                            submitDataInfo();
                            Log.e(TAG, "onOperItemClick: 保存" );
                            break;
                    }
                }
            });
        }
    };

    private void getMaterialLine() {
        final NormalListDialog normalListDialog = new NormalListDialog(this, new String[]{getString(R.string.xinjian ),getString(R.string.selectitem)});
        normalListDialog.title(getString(R.string.option))
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){

                    case 0://新建
                        normalListDialog.superDismiss();
                        if (workorder.getSTATUS().equalsIgnoreCase("WAPPR")){
                            Intent intent1 = new Intent(MaterialRequisitionDetailActivity.this,MatWpmaterialDetailsActivity.class );
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("workorder",workorder);
                            bundle.putString("flag","I");
                            intent1.putExtras(bundle);
                            startActivityForResult(intent1,wpmaterialAdapter.getItemCount());
                        }else {
                            Toast.makeText(MaterialRequisitionDetailActivity.this, "The Status is not WAPPR",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        normalListDialog.superDismiss();
                        Intent invIntent = new Intent(MaterialRequisitionDetailActivity.this, Inventory_chooseActivity.class);
                        invIntent.putExtra("location",workorder.getLOCATION());
                        invIntent.putExtra("showCheckBox",true);
                        startActivityForResult(invIntent,0);
                        break;
                }
//                    normalListDialog.dismiss();
            }
        });
    }


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
            case 100:
                Bundle bundle = data.getExtras();
                WPMATERIAL wpmaterial = (WPMATERIAL) bundle.get("wpmaterial");
                if (wpmaterial!=null && wpmaterial.getFLAG()!= null) {
                    if (wpmaterial.getFLAG().equalsIgnoreCase("I")) {
                        wpmaterial.setTYPE("add");
                        if (wpmaterialAdapter.getItemCount()==0){
                            wpmaterialAdapter.add(wpmaterial);
                        }else if (requestCode!=0 && requestCode < wpmaterialAdapter.getItemCount() ){
                            wpmaterialAdapter.remove(requestCode);
                            wpmaterialAdapter.add(requestCode,wpmaterial);
                        }else if(wpmaterialAdapter.getItemCount()!=0&&requestCode == 0) {
                            wpmaterialAdapter.remove(0);
                            wpmaterialAdapter.add(0,wpmaterial);
                        }else {
                            wpmaterialAdapter.add(wpmaterial);
                        }
                    } else if (wpmaterial.getFLAG().equalsIgnoreCase("U")){
                        wpmaterial.setTYPE("update");
                        wpmaterial.setSTATUS(workorder.getSTATUS());
                        wpmaterialAdapter.remove(requestCode);
                        wpmaterialAdapter.add(requestCode,wpmaterial);
                    }else if (wpmaterial.getFLAG().equalsIgnoreCase("D")){
                        wpmaterial.setTYPE("delete");
                        wpmaterial.setSTATUS(workorder.getSTATUS());
                        wpmaterialAdapter.remove(requestCode);
                        wpmaterialAdapter.add(requestCode,wpmaterial);
                    }
                }
            break;
            case 110:
                UDDEPT locations = (UDDEPT) data.getExtras().get("dept");
                if (locations!=null){
                    dep.setText(locations.getDEPTNUM());
                    workorder.setUDTEMPMATERIAL(locations.getDEPTNUM());
                    workorder.setUDSTATIONNUM(locations.getDEPTNUM());
                }
                break;
            case 1:
                ArrayList<INVENTORY> inventories = (ArrayList<INVENTORY>) data.getExtras().get("itemlist");
                if (inventories!=null && !inventories.isEmpty()){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String date = simpleDateFormat.format(new Date());
                    for(int i = 0;i<inventories.size();i++) {
                        WPMATERIAL wpmaterial1;
                        wpmaterial1 = new WPMATERIAL();
                        wpmaterial1.setITEMQTY("1.00");
                        wpmaterial1.setLINETYPE("ITEM");
                        wpmaterial1.setREQUESTBY(AccountUtils.getpersonId(this));
                        wpmaterial1.setLOCATION(workorder.getLOCATION());
                        wpmaterial1.setREQUIREDATE(simpleDateFormat.format(new Date()));
                        wpmaterial1.setFLAG("I");
                        wpmaterial1.setWONUM(workorder.getWONUM());
                        wpmaterial1.setITEMNUM(inventories.get(i).getITEMNUM());
                        wpmaterial1.setDESCRIPTION(inventories.get(i).getITEMNUM_DEC());
                        wpmaterialAdapter.add(wpmaterial1);
                    }
                    }
                break;
        }
    }
    private void submitDataInfo() {
        final NormalDialog dialog = new NormalDialog(this);
        dialog.title(getString(R.string.tip)).btnText(getString(R.string.cancel),getString(R.string.queren));
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
                        showProgressDialog("");
                        startAsyncTask();
                        dialog.dismiss();
                    }
                });
    }
    private void startAsyncTask() {
        String updataInfo = null;
        String wpjson = null;
//            if (workOrder.status.equals(Constants.WAIT_APPROVAL)) {
        try {
           updataInfo = JsonUtils.parsingWork(workorder,new ArrayUtil<WPMATERIAL>());
           Gson gson = new GsonBuilder().serializeNulls().create();
           wpjson = gson.toJson(wpmaterialAdapter.getData());

        } catch (JSONException e) {
            e.printStackTrace();
        }
//            } else if (workOrder.status.equals(Constants.APPROVALED)) {
//                updataInfo = JsonUtils.WorkToJson(getWorkOrder(), null, null, null, null, getLabtransList());
//            }
        final String finalUpdataInfo = updataInfo;
        final String finalWpjson = wpjson;
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult reviseresult = AndroidClientService.UpdateWO(MaterialRequisitionDetailActivity.this, finalUpdataInfo,
                        "WORKORDER", "WONUM", workorder.getWONUM(), Constants.WORK_URL);
                String str = AndroidClientService.insertWPITEM(MaterialRequisitionDetailActivity.this, finalWpjson);
                reviseresult.errorMsg = reviseresult.errorMsg + str;
                return reviseresult;
            }

            @Override
            protected void onPostExecute(WebResult workResult) {
                super.onPostExecute(workResult);
                if (workResult == null || workResult.errorMsg == null) {
                    Toast.makeText(MaterialRequisitionDetailActivity.this,R.string.fail, Toast.LENGTH_SHORT).show();
                } else if (workResult.errorMsg.contains("成功")) {
                    Toast.makeText(MaterialRequisitionDetailActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                    setResult(100);
                    for (int i =0;i <wpmaterialAdapter.getItemCount();i++){
                        wpmaterialAdapter.getItem(i).setFLAG("");
                        wpmaterialAdapter.getItem(i).setTYPE("");
                    }
                    refresh_layout.setRefreshing(true);
                    onRefresh();
                } else {
                    Toast.makeText(MaterialRequisitionDetailActivity.this, workResult.errorMsg, Toast.LENGTH_LONG).show();
                }
                closeProgressDialog();
            }
        }.execute();
        //}else {
    }
    /**
     * 开始工作流
     */
    private void startWF() {
        mProgressDialog = ProgressDialog.show(MaterialRequisitionDetailActivity.this, null,
                getString(R.string.start), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult result = AndroidClientService.startwf(MaterialRequisitionDetailActivity.this,
                        "WLCK", Constants.WORKORDER_APPID,workorder.getWONUM(), "WONUM", AccountUtils.getpersonId(MaterialRequisitionDetailActivity.this));
                return result;
            }

            @Override
            protected void onPostExecute(WebResult s) {
                super.onPostExecute(s);
                if (s != null && s.errorMsg != null && s.errorMsg.equals("工作流启动成功")) {
                    Toast.makeText(MaterialRequisitionDetailActivity.this, s.errorMsg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MaterialRequisitionDetailActivity.this, getString(R.string.fail), Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();
            }
        }.execute();
    }
    private void MaterialDialogOneBtn() {//开始工作流
        final MaterialDialog2 dialog = new MaterialDialog2(this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content(getString(R.string.work_workflow))//
                .btnText(getString(R.string.yes),getString(R.string.no))//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {//是
                    @Override
                    public void onBtnClick() {
                        startWF();
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {//否
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                }
        );
    }

    private void deleteWO() {
        final NormalDialog dialog = new NormalDialog(this);
        dialog.title(getString(R.string.tip)).btnText(getString(R.string.cancel),getString(R.string.queren));
        dialog.content(getString(R.string.suretosdelete))//
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
                        showProgressDialog(".....");
                        deleteAsyncTask();
                        dialog.dismiss();
                    }
                });
    }
    private void deleteAsyncTask(){

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String updataInfo = null;
                try {
                Gson gson = new GsonBuilder().serializeNulls().create();
                JSONObject jsonObject = new JSONObject(gson.toJson(workorder));
                jsonObject.put("FLAG","D");
                updataInfo = "["+ jsonObject.toString()+"]";
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String reviseresult = AndroidClientService.insertWO(MaterialRequisitionDetailActivity.this, updataInfo);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String workResult) {
                super.onPostExecute(workResult);
                Toast.makeText(MaterialRequisitionDetailActivity.this, workResult,Toast.LENGTH_SHORT).show();
                finish();
                closeProgressDialog();
            }
        }.execute();
        //}else {
        closeProgressDialog();
    }

    public void getSubjectList(){
        List list =SUBJECT.list;
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,list);
    }
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            if (isfirst){
             spinner.setVisibility(View.VISIBLE);
            }else {
                String m = adapter.getItem(arg2);
                workorder.setSUBJECT(SUBJECT.stringStringMap.get(m));
                subject.setText(m);
            }
            isfirst = false;
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    private void getWorkORder(String search) {
        HttpManager.getDataPagingInfo(this, HttpManager.getWorkOrderUrl(search,"PL","WONUM", 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }
            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<WORKORDER> item = JsonUtils.parsingWORKORDER(MaterialRequisitionDetailActivity.this, results.getResultlist());
                if (item != null && !item.isEmpty()) {
                    workorder = item.get(0);
                }
            }
            @Override
            public void onFailure(String error) {

            }
        });

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void invEventBus(Map<Integer,Object> map){
        ArrayList<INVENTORY> inventories = (ArrayList<INVENTORY>) map.get(1);
        if (inventories!=null && !inventories.isEmpty()){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = simpleDateFormat.format(new Date());
            for(int i = 0;i<inventories.size();i++) {
                WPMATERIAL wpmaterial1;
                wpmaterial1 = new WPMATERIAL();
                wpmaterial1.setITEMQTY("1.00");
                wpmaterial1.setLINETYPE("ITEM");
                wpmaterial1.setUNITCOST("0.00");
                wpmaterial1.setLINECOST("0.00");
                wpmaterial1.setREQUESTBY(AccountUtils.getpersonId(this));
                wpmaterial1.setLOCATION(workorder.getLOCATION());
                wpmaterial1.setREQUIREDATE(simpleDateFormat.format(new Date()));
                wpmaterial1.setFLAG("I");
                wpmaterial1.setWONUM(workorder.getWONUM());
                wpmaterial1.setITEMNUM(inventories.get(i).getITEMNUM());
                wpmaterial1.setDESCRIPTION(inventories.get(i).getITEMNUM_DEC());
                wpmaterialAdapter.add(wpmaterial1);
            }
        }
    }

}
