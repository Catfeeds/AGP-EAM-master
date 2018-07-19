package com.hsk.hxqh.agp_eam.ui.activity.invuse;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.flyco.dialog.listener.OnBtnEditClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.MaterialDialog2;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalEditTextDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.adpter.BaseQuickAdapter;
import com.hsk.hxqh.agp_eam.adpter.InventoryAdapter;
import com.hsk.hxqh.agp_eam.adpter.InvuseLineAdaper;
import com.hsk.hxqh.agp_eam.adpter.MATUSETRANS_chooseAdapter;
import com.hsk.hxqh.agp_eam.api.HttpManager;
import com.hsk.hxqh.agp_eam.api.HttpRequestHandler;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.config.Constants;

import com.hsk.hxqh.agp_eam.model.INVBALANCES;
import com.hsk.hxqh.agp_eam.model.INVENTORY;
import com.hsk.hxqh.agp_eam.model.INVRESERVE;
import com.hsk.hxqh.agp_eam.model.INVUSEEntity;
import com.hsk.hxqh.agp_eam.model.INVUSELINE;
import com.hsk.hxqh.agp_eam.model.MATUSETRANS;
import com.hsk.hxqh.agp_eam.model.SUBJECT;
import com.hsk.hxqh.agp_eam.model.WebResult;

import com.hsk.hxqh.agp_eam.ui.activity.BaseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.WxDemoActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.INVRESERVE_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.MATUSETRANS_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.widget.SwipeRefreshLayout;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;
import com.hsk.hxqh.agp_eam.unit.ArrayUtil;
import com.hsk.hxqh.agp_eam.webserviceclient.AndroidClientService;
import com.j256.ormlite.stmt.query.In;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxs on 2018/4/8.
 */

public class InvuseDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener{


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

    private TextView invusemi;
    private TextView description;
    private TextView fileno;
    private TextView dep;
    private TextView fromstoreloc;
    private TextView invowner;
    private TextView creatby;
    private TextView creatdate;

    private INVUSEEntity invuseEntity ;
    private Button option;
    private Button quit;
    private String[] optionList = {"Back","upload pictures","startwf","Add Invuse","Add InvuseLine","Save"};
    private String[] addInvusemiLineList = {"Select MI tems","Add New"};
    private String[] addInvusemrLineList = {"Select MR tems"};
    private String[] addInvusemtLineList = {"Add New"};
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private String INVUSENUM;
    private InvuseLineAdaper itemAdaper;
    public RecyclerView recyclerView;
    private LinearLayout nodatalayout;
    ArrayList<INVUSELINE> items = new ArrayList<>();
    LinearLayoutManager layoutManager;
    private SwipeRefreshLayout refresh_layout = null;
    private INVUSELINE invuseline;
    private ProgressDialog mProgressDialog;
    private String type;
    private RelativeLayout relativeLayout;
    private TextView subject;
    private TextView udtowarehouse;
    private TextView udinvowner;
    private LinearLayout mtLinearLayout;
    public  ArrayUtil<INVRESERVE> invreservesList = new ArrayUtil<>();
    private ArrayList<MATUSETRANS> invuselinelist = new ArrayUtil<>();
    private TextView status;
    private ArrayList<INVBALANCES> invbalances;
    private ArrayAdapter<String> adapter;
    private Spinner spinner;
    private boolean isfirst =true;
    private String procsessname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invusemi_details);
        findViewById();
        initView();
        getInvesrverData("");
//
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
        getMatDate();
    }

    private void getIntentData() {
        Bundle bundle =  getIntent().getExtras();
        INVUSELINE invuseline =(INVUSELINE) bundle.get("INVUSELINE");
        invuseEntity =(INVUSEEntity) bundle.get("item");
        type = bundle.get("type").toString();
        if(type.equals("MT")){
            procsessname = "WLDB";
        }else {
            procsessname = "UDOUTBOUND";
        }

        if (invuseline != null){
            items.add(invuseline);
        }
        INVUSENUM = invuseEntity.getINVUSENUM();
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);
        moreImg = (ImageView) findViewById(R.id.title_more);
        invusemi = (TextView)findViewById(R.id.invusemi_id);
        description = (TextView)findViewById(R.id.description_text_id);
        fileno = (TextView)findViewById(R.id.fileno_text_id);
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
        subject = (TextView)findViewById(R.id.subject_text_id);
        udinvowner = (TextView)findViewById(R.id.UDINVOWNER_text_id);
        udtowarehouse = (TextView) findViewById(R.id.UDTOWAREHOUSE_text_id);
        mtLinearLayout = (LinearLayout) findViewById(R.id.invuse_mt_spid);
        status = (TextView) findViewById(R.id.status_text_id);
        spinner = (Spinner) findViewById(R.id.spnner);
    }


    @Override
    protected void initView() {
        getIntentData();
        if (type.equals("MT")){
            getSubjectList();
            spinner.setAdapter(adapter);
            mtLinearLayout.setVisibility(View.VISIBLE);
            spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        }
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListener);
        if (type.equals("MI")){
            titleTextView.setText(R.string.outbound);
        }else if (type.equals("MR"))
        {
            titleTextView.setText(R.string.refund);
        }else {
            titleTextView.setText(R.string.invuse_transfer);
        }
        if (invuseEntity!=null){
            invusemi.setText(invuseEntity.getINVUSENUM());
            description.setText(invuseEntity.getDESCRIPTION());
            fileno.setText(invuseEntity.getUDFILENO());
            dep .setText(invuseEntity.getFROMSTORELOC());
            fromstoreloc.setText(invuseEntity.getFROMSTORELOC());
            invowner .setText(invuseEntity.getINVOWNER());
            creatby .setText(invuseEntity.getUDCREATEBY());
            creatdate.setText(invuseEntity.getUDCREATEDATE());
            subject.setText(SUBJECT.getKeyByValue(invuseEntity.getSUBJECT()));
            udtowarehouse.setText(invuseEntity.getUDTOWAREHOUSE());
            udinvowner.setText(invuseEntity.getUDINVOWNER());
            status.setText(invuseEntity.getSTATUS());
        }
        layoutManager = new LinearLayoutManager(InvuseDetailActivity.this);
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

        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnLoadListener(this);
        getData();
        initAdapter(new ArrayList<INVUSELINE>());
        relativeLayout.setVisibility(View.VISIBLE);
        quit.setOnClickListener(backImageViewOnClickListener);

    }

    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(InvuseDetailActivity.this, optionList);
            normalListDialog.title("OPTION")
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
                            finish();
                            break;
                        case 1:
                            normalListDialog.superDismiss();
                            Intent intent1 = new Intent(InvuseDetailActivity.this, WxDemoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("ownertable",INVUSEEntity.TABLE_NAME);
                            bundle.putString("ownerid",invuseEntity.getINVUSEID());
                            intent1.putExtras(bundle);
                            startActivity(intent1);
                            break;
                        case 2:
                            normalListDialog.superDismiss();
                            MaterialDialogOneBtn();
                            break;
                        case 3://新建
                            normalListDialog.superDismiss();
                            Log.e(TAG, "onOperItemClick: 新建物资出库单" );
                            Intent intent = new Intent(InvuseDetailActivity.this,InvusemiAddNewActivity.class);
                            intent .putExtra("type",type);
                            startActivity(intent);
                            break;
                        case 4:
                            normalListDialog.superDismiss();
                            Log.e(TAG, "onOperItemClick: 新建物资出库行" );
                            getaddInvuseLine();
                            break;
                        case 5:
                            normalListDialog.superDismiss();
                            submitDataInfo();
                            Log.e(TAG, "onOperItemClick: 保存" );
                            break;
                    }
//                    normalListDialog.dismiss();
                }
            });
        }
    };
    public void getaddInvuseLine(){
        String[] addInvuseLineList;
        if (type.equalsIgnoreCase("MI")){
            addInvuseLineList = addInvusemiLineList;
        }else if(type.equalsIgnoreCase("MR")){
            addInvuseLineList = addInvusemrLineList;
        }else {
            addInvuseLineList = addInvusemtLineList;
        }
        final NormalListDialog normalListDialog = new NormalListDialog(InvuseDetailActivity.this, addInvuseLineList);
        normalListDialog.title("新建物资出库行")
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type.equalsIgnoreCase("MT")){
                    position += 1;
                }
//                    linetypeTextView.setText(linetypeList[position]);
                switch (position){
/*                    case 0://
                        normalListDialog.superDismiss();
                        Log.e(TAG, "onOperItemClick:返回 " );
                        break;*/
                    case 0://新建
                        normalListDialog.superDismiss();
                    if (type.equalsIgnoreCase("MI")) {
                        Intent intent1 = new Intent(InvuseDetailActivity.this, INVRESERVE_chooseActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("invreservesList", invreservesList);
                        intent1.putExtras(bundle1);
                        startActivityForResult(intent1, 0);
                    }else {
                        Intent intent1 = new Intent(InvuseDetailActivity.this,MATUSETRANS_chooseActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("invuselinelist", invuselinelist);
                        intent1.putExtras(bundle1);
                        startActivityForResult(intent1, 0);
                    }
                        break;
                    case 1:
                        normalListDialog.superDismiss();
                        Intent intent = new Intent(InvuseDetailActivity.this,InvuselineAddNewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("item" ,invuseEntity);
                        bundle.putString("type",type);
                        bundle.putString("option","add");
                        intent.putExtras(bundle);
                        startActivityForResult(intent,130);
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
    public void onLoad() {
        getData();
    }

    @Override
    public void onRefresh() {
        getData();
    }
   public void getData(){
       HttpManager.getData(InvuseDetailActivity.this, HttpManager.getINVUSELINEUrl(INVUSENUM), new HttpRequestHandler<Results>() {
           @Override
           public void onSuccess(Results results) {
               Log.i(TAG, "data=" + results);
           }

           @Override
           public void onSuccess(Results results, int totalPages, int currentPage) {
               Log.e("库存", "onSuccess: "+results.toString() );
               refresh_layout.setRefreshing(false);
               refresh_layout.setLoading(false);
               ArrayList<INVUSELINE> item = JsonUtils.parsingINVUSELINE(InvuseDetailActivity.this, results.getResultlist());
               if (item == null || item.isEmpty()) {
                   nodatalayout.setVisibility(View.VISIBLE);
               } else {

                   if (item != null || item.size() != 0) {
                           items = new ArrayList<INVUSELINE>();
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

           @Override
           public void onFailure(String error) {nodatalayout.setVisibility(View.VISIBLE);
           }
       });
    }
    /**
     * 获取数据*
     */
    private void initAdapter(final List<INVUSELINE> list) {
        itemAdaper = new InvuseLineAdaper(InvuseDetailActivity.this, R.layout.list_item_invuseline, list);
        recyclerView.setAdapter(itemAdaper);
        itemAdaper.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!itemAdaper.getItem(position).getTYPE().equalsIgnoreCase("delete")){
                    Intent intent = new Intent(InvuseDetailActivity.this, InvuselineAddNewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("invuseline",itemAdaper.getItem(position));
                    bundle.putSerializable("item",invuseEntity);
                    bundle.putString("type",type);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,position);
                }

            }
        });
        itemAdaper.setOnRecyclerViewItemLongClickListener(new BaseQuickAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
             INVUSELINE invuseline = itemAdaper.getItem(position);
             invuseline.setTYPE("delete");
             invuseline.setFLAG("D");
             itemAdaper.remove(position);
             itemAdaper.add(position,invuseline);
             return true;
            }
        });

    }


    /**
     * 添加数据*
     */
    private void addData(final List<INVUSELINE> list) {
        itemAdaper.addData(list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 100:
                INVUSELINE invuseline = (INVUSELINE) data.getExtras().get("invuseline");
                invuseline.setFROMSTORELOC(invuseEntity.getFROMSTORELOC());
                if (invuseline.getTYPE().equalsIgnoreCase("delete")){
                    itemAdaper.remove(requestCode);
                    itemAdaper.add(requestCode,invuseline);
                }else if (invuseline.getTYPE().equalsIgnoreCase("update")){
                    itemAdaper.remove(requestCode);
                    itemAdaper.add(requestCode,invuseline);
                }else if (invuseline.getTYPE().equalsIgnoreCase("add")){
                    if (requestCode==0){
                        itemAdaper.remove(0);
                        itemAdaper.add(0,invuseline);
                    }else if (requestCode!=0 && requestCode < itemAdaper.getItemCount() ){
                        itemAdaper.remove(requestCode);
                        itemAdaper.add(requestCode,invuseline);
                    }else {
                        invuseline.setINDEX(itemAdaper.getItemCount());
                        itemAdaper.add(invuseline);
                    }
                }

                break;
            case 130:
                Bundle invre = data.getExtras();
                INVRESERVE invreserve = (INVRESERVE) invre.get("INVRESERVE");
                if (invreserve!=null){
                    INVUSELINE invuseline1 = new INVUSELINE();
                    invuseline1.setFLAG("I");
                    invuseline1.setTYPE("add");
                    invuseline1.setUSETYPE("ISSUE");
                    invuseline1.setENTERBY(AccountUtils.getpersonId(this));
                    invuseline1.setFROMSTORELOC(invuseEntity.getFROMSTORELOC());
                    invuseline1.setINVUSENUM(invuseEntity.getINVUSENUM());
                    invuseline1.setITEMNUM(invreserve.getITEMNUM());
                    invuseline1 .setASSETNUM(invreserve.getASSETNUM());
                    invuseline1.setDESCRIPTION(invreserve.getDESCRIPTION());
                    invuseline1.setWONUM(invreserve.getWONUM());
                    invuseline1.setISS(invreserve.getISSUETO());
                    invuseline1.setFROMBIN(invreserve.getMRLINENUM());
                    invuseline1.setFROMLOT(invreserve.getTOSTORELOC());
                    invuseline1.setLOCATION(invreserve.getOPLOCATION());
                    invuseline1.setREQUESTNUM(invreserve.getREQUESTNUM());
                    double  reservedqty = Double.parseDouble(invreserve.getRESERVEDQTY());
                    double pendingqty = Double.parseDouble(invreserve.getPENDINGQTY());
                    double j = 0;
                    for (int i = 0;i < itemAdaper.getItemCount();i++) {
                        INVUSELINE invuseline2 = itemAdaper.getItem(i);
                        if (invuseline2.getREQUESTNUM()!=null&&invuseline2.getREQUESTNUM().equals(invreserve.getREQUESTNUM())&&!invuseline2.getTYPE().equalsIgnoreCase("delete")){
                            double k =0;
                            String a = invuseline2.getQUANTITY();
                            if (invuseline2.getQUANTITY()!=null&&!invuseline2.getQUANTITY().equals("")){
                                k = Double.parseDouble(invuseline2.getQUANTITY());
                            }
                            j+=k;
                        }
                    }
                    if ((reservedqty-pendingqty-j)<0){
                        invuseline1.setQUANTITY("0");
                    }else {
                        invuseline1.setQUANTITY((reservedqty-pendingqty-j) + "");
                    }
                    invuseline1.setINDEX(itemAdaper.getItemCount());
                    getFromBin(invuseline1);
                }
                break;
            case 105:
                Bundle invuselineDate = data.getExtras();
                INVUSELINE invuseline1 = new INVUSELINE();
                MATUSETRANS matusetrans = (MATUSETRANS) invuselineDate.get("MATUSETRANS");
                if (invuseline1!= null){
                    invuseline1.setFROMSTORELOC(invuseEntity.getFROMSTORELOC());
                    invuseline1.setUSETYPE("RETURN");
                    invuseline1.setTYPE("add");
                    invuseline1.setFLAG("I");
                    Double quanty = Double.parseDouble(matusetrans.getQUANTITY());
                    invuseline1.setQUANTITY(Math.abs(quanty)+ "");
                    invuseline1.setINVUSENUM(invuseEntity.getINVUSENUM());
                    invuseline1.setFROMBIN(matusetrans.getBINNUM());
                    invuseline1.setFROMLOT(matusetrans.getLOTNUM());
                    invuseline1.setLOCATION(matusetrans.getLOCATION());
                    invuseline1.setENTERBY(matusetrans.getENTERBY());
                    invuseline1.setASSETNUM(matusetrans.getASSETNUM());
                    invuseline1.setACTUALDATE(matusetrans.getACTUALDATE());
                    invuseline1.setREFWO(matusetrans.getREFWO());
                    invuseline1.setISSUETO(matusetrans.getISSUETO());
                    invuseline1.setINDEX(itemAdaper.getItemCount());
                    invuseline1.setITEMNUM(matusetrans.getITEMNUM());
                    invuseline1.setDESCRIPTION(matusetrans.getDESCRIPTION());
                    getFromBin(invuseline1);
                }
                break;
        }
    }
    private String json = "";
    /**
     * 提交数据*
     */
    private void startAsyncTask() {
        for (int i= 0;i<itemAdaper.getItemCount();i++){
            double qty = Double.parseDouble(itemAdaper.getItem(i).getQUANTITY());
            if (qty<=0){
                Toast.makeText(this, "请指定大于零的数量",Toast.LENGTH_SHORT).show();
                return;
            }
        }

        try {
            invuseEntity.setDESCRIPTION(description.getText().toString());
            invuseEntity.setUDFILENO(fileno.getText().toString());
            json = JsonUtils.parsingINVUSE(invuseEntity,new ArrayUtil<INVUSELINE>());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {

                Gson g = new GsonBuilder().serializeNulls().create();
                String string = g.toJson(itemAdaper.getData());
                string = string.replace("null","\"\"");

                WebResult reviseresult = AndroidClientService.UpdateWO(InvuseDetailActivity.this,json, Constants.INVUSE_NAME,"INVUSENUM", invuseEntity.getINVUSENUM(),Constants.WORK_URL);
                String stringrel = AndroidClientService.insertInvuseLine(InvuseDetailActivity.this, string);
                //WebResult result = AndroidClientService.UpdateWO(InvuseDetailActivity.this, "",Constants.MATUSETRANS_NAME,"MATUSETRANSID","",Constants.WORK_URL);
                reviseresult.errorMsg = reviseresult.errorMsg + stringrel;
                return reviseresult;
            }

            @Override
            protected void onPostExecute(WebResult workResult) {
                super.onPostExecute(workResult);
                if (workResult == null) {
                    Toast.makeText(InvuseDetailActivity.this, "false", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InvuseDetailActivity.this, workResult.errorMsg, Toast.LENGTH_SHORT).show();
                    finish();
                }
                closeProgressDialog();
            }
        }.execute();
        closeProgressDialog();
    }
    /**
     * 提交数据*
     */
    private void submitDataInfo() {
        final NormalDialog dialog = new NormalDialog(InvuseDetailActivity.this);
        dialog.content("Sure to save?")//
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
                        showProgressDialog("Waiting...");
                        startAsyncTask();
                        dialog.dismiss();
                    }
                });
    }
    private void MaterialDialogOneBtn() {//开始工作流
        final MaterialDialog2 dialog = new MaterialDialog2(InvuseDetailActivity.this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content(getString(R.string.startwf))//
                .btnText(getString(R.string.yes), getString(R.string.no))//
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

    private void EditDialog() {//输入审核意见
        final NormalEditTextDialog dialog = new NormalEditTextDialog(InvuseDetailActivity.this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.isTitleShow(true)//
                .title(getString(R.string.wfgon))
                .btnNum(3)
                .content(getString(R.string.wfm_pass_text))//
                .btnText(getString(R.string.cancel),getString(R.string.wfm_pass_text), getString(R.string.wfm_notpass_text))//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)
                .show();

        dialog.setOnBtnClickL(
                new OnBtnEditClickL() {
                    @Override
                    public void onBtnClick(String text) {
                        dialog.dismiss();
                    }
                },
                new OnBtnEditClickL() {
                    @Override
                    public void onBtnClick(String text) {
                        wfgoon("1", text);
                        dialog.dismiss();
                    }
                },
                new OnBtnEditClickL() {
                    @Override
                    public void onBtnClick(String text) {
                        wfgoon("0", text.equals("通过") ? "不通过" : text);
                        dialog.dismiss();
                    }
                }
        );
    }

    /**
     * 开始工作流
     */
    private void startWF() {
        mProgressDialog = ProgressDialog.show(InvuseDetailActivity.this, null,
                getString(R.string.start), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult result = AndroidClientService.startwf(InvuseDetailActivity.this,
                        procsessname, Constants.INVUSE_NAME,invuseEntity.getINVUSENUM(), "INVUSENUM", AccountUtils.getpersonId(InvuseDetailActivity.this));
                return result;
            }

            @Override
            protected void onPostExecute(WebResult s) {
                super.onPostExecute(s);
                if (s != null && s.errorMsg != null && s.errorMsg.equals("工作流启动成功")) {
                    Toast.makeText(InvuseDetailActivity.this, s.errorMsg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InvuseDetailActivity.this, "工作流启动失败", Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();
            }
        }.execute();
    }

    /**
     * 审批工作流
     *
     * @param zx
     */
    private void wfgoon(final String zx, final String desc) {
        mProgressDialog = ProgressDialog.show(InvuseDetailActivity.this, null,
                getString(R.string.approve), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult result = AndroidClientService.approve(InvuseDetailActivity.this,
                        "INVUSE", Constants.INVUSE_NAME, invuseEntity.getINVUSENUM()+"", "WORKORDERID", zx, desc,
                        AccountUtils.getpersonId(InvuseDetailActivity.this));
                return result;
            }

            @Override
            protected void onPostExecute(WebResult s) {
                super.onPostExecute(s);
                if (s == null || s.wonum == null || s.errorMsg == null) {
                    Toast.makeText(InvuseDetailActivity.this, "审批失败", Toast.LENGTH_SHORT).show();
                } else if (s.wonum.equals(invuseEntity.getINVUSENUM()+"") && s.errorMsg != null) {
                    invuseEntity.setUSETYPE(s.errorMsg);
                    Toast.makeText(InvuseDetailActivity.this, "审批成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InvuseDetailActivity.this, "审批失败", Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();
            }
        }.execute();
    }
    private void getInvesrverData(String search) {
        HttpManager.getDataPagingInfo(this, HttpManager.getINVRESERVEUrl(search,fromstoreloc.getText().toString()), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                invreservesList =  JsonUtils.parsingINVRESERVE(InvuseDetailActivity.this, results.getResultlist());
                int i = 0;
                while (i < invreservesList.size())
                {
                    Double RESERVEDQTY = Double.parseDouble(invreservesList.get(i).getRESERVEDQTY());
                    Double  PENDINGQTY = Double.parseDouble(invreservesList.get(i).getPENDINGQTY());
                    if ((RESERVEDQTY - PENDINGQTY) <= 0){
                        invreservesList.remove(i);
                    }else {
                        i++;
                    }

                }

                Log.e("invreservesList","onSuccess: "+invreservesList.size() );
            }

            @Override
            public void onFailure(String error) {
            }
        });

    }
    public void getMatDate(){
        new AsyncTask<String,String,String>(){

            @Override
            protected String doInBackground(String... strings) {
                String result = "";
                if (invuseEntity.getSTATUS().equalsIgnoreCase("ENTERED")){
                    result  = AndroidClientService.getMatDate(InvuseDetailActivity.this, invuseEntity.getINVUSENUM(),invuseEntity.getFROMSTORELOC(),invuseEntity.getSTATUS());
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                    invuselinelist =  JsonUtils.parsingMATUSETRANS(InvuseDetailActivity.this, s);
                    List list = invreservesList;

            }
        }.execute();
    }
    public void getFromBin(final INVUSELINE invuseline1){
        HttpManager.getDataPagingInfo(this, HttpManager.getInvbalancesUrl(invuseline1.getITEMNUM(),invuseline1.getFROMSTORELOC(),"","ITEMSET",1, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                invbalances = JsonUtils.parsingINVBALANCES(InvuseDetailActivity.this,  results.getResultlist());
                if (invbalances == null||invbalances.isEmpty()){
                    invbalances = new ArrayList<>();
                }else {
                    String bin = invbalances.get(0).getBINNUM();
                    String loc = invbalances.get(0).getLOTNUM();
                    invuseline1.setFROMLOT(loc);
                    invuseline1.setFROMBIN(bin);
                }
                itemAdaper.add(invuseline1);

            }
            @Override
            public void onFailure(String error) {
                itemAdaper.add(invuseline1);
            }
        });

    }
    public void getSubjectList(){
        List list = SUBJECT.list;
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,list);
    }
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            if (isfirst){
                spinner.setVisibility(View.VISIBLE);
            }else {
                String m = adapter.getItem(arg2);
                invuseEntity.setSUBJECT(SUBJECT.stringStringMap.get(m));
                subject.setText(m);
            }
            isfirst = false;
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

}