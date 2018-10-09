package com.hsk.hxqh.agp_eam.ui.activity.invuse;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.MailTo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.adpter.BaseQuickAdapter;
import com.hsk.hxqh.agp_eam.adpter.InvuseLineAdaper;
import com.hsk.hxqh.agp_eam.api.HttpManager;
import com.hsk.hxqh.agp_eam.api.HttpRequestHandler;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.config.Constants;
import com.hsk.hxqh.agp_eam.manager.AppManager;
import com.hsk.hxqh.agp_eam.model.ASSET;
import com.hsk.hxqh.agp_eam.model.INVBALANCES;
import com.hsk.hxqh.agp_eam.model.INVENTORY;
import com.hsk.hxqh.agp_eam.model.INVRESERVE;
import com.hsk.hxqh.agp_eam.model.INVUSEEntity;
import com.hsk.hxqh.agp_eam.model.INVUSELINE;
import com.hsk.hxqh.agp_eam.model.ITEM;
import com.hsk.hxqh.agp_eam.model.LOCATIONS;
import com.hsk.hxqh.agp_eam.model.MATUSETRANS;
import com.hsk.hxqh.agp_eam.model.PERSON;
import com.hsk.hxqh.agp_eam.model.SUBJECT;
import com.hsk.hxqh.agp_eam.model.WebResult;
import com.hsk.hxqh.agp_eam.ui.activity.BaseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.Inventory_InvbalancesActivity;
import com.hsk.hxqh.agp_eam.ui.activity.ItemDetailsActivity;
import com.hsk.hxqh.agp_eam.ui.activity.WorkOrderAddNewActivity;
import com.hsk.hxqh.agp_eam.ui.activity.invuse.adapter.InvuseLineAddAdaper;
import com.hsk.hxqh.agp_eam.ui.activity.option.INVBALANCES_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.INVRESERVE_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Inventory_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Location_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.MATUSETRANS_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Person_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.UdGetrefundline_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.widget.SwipeRefreshLayout;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;
import com.hsk.hxqh.agp_eam.unit.ArrayUtil;
import com.hsk.hxqh.agp_eam.webserviceclient.AndroidClientService;
import com.j256.ormlite.stmt.query.In;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zzw on 2018/5/24.
 */
/*新建出库单*/
public class InvusemiAddNewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,SwipeRefreshLayout.OnLoadListener {


    private static final String TAG = "InvusemiAddNewActivity";

    private ImageView backImageView; //返回按钮

    private TextView titleTextView; //标题

    private EditText descriptionTextView; //description
    private TextView fromstoreroomTextView; //fromstoreroom
    private TextView invownerTextView; //invowner
    private EditText udfilenoTexView;//UDFILENO

    private INVUSEEntity invuseEntity;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    private LinearLayout buttonLayout;
    private Button quit;
    private Button option;
    private List<INVUSELINE> items;
    private InvuseLineAddAdaper itemAdaper;

    public RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private SwipeRefreshLayout refresh_layout = null;
    private String json = "";
    ArrayList<INVBALANCES> invbalances;
    private String type;
    private RelativeLayout relativeLayout;
    public  ArrayUtil<INVRESERVE> invreservesList = new ArrayUtil<>();
    private TextView subject;
    private TextView udtowarehouse;
    private TextView udinvowner;
    private LinearLayout mtLinearLayout;
    private List<MATUSETRANS> invuselinelist ;
    private boolean flag;
    private ImageView fromstoreroomImg;
    private ImageView invownerImg;
    private ImageView udtowarehouseImg;
    private ImageView udinvownerImg;
    private ArrayAdapter<String> adapter;
    private Spinner spinner;
    private boolean isfirst =true;
    private String usetype;

    public  ArrayUtil<INVRESERVE> getInvreservesList() {
        return invreservesList;
    }

    public void setInvreservesList(ArrayUtil<INVRESERVE> invreservesList) {
        this.invreservesList = invreservesList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invuse_addnew);
        findViewById();
        initView();
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
        fromstoreroomTextView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            getInvesrverData("");
            }
        });
        getDefLoc();
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);
        descriptionTextView = (EditText) findViewById(R.id.description_text_id);
        udfilenoTexView = (EditText) findViewById(R.id.udfileno_text_id);
        invownerTextView = (TextView) findViewById(R.id.inv_owner_text_id);
        fromstoreroomTextView = (TextView) findViewById(R.id.FROMSTORELOC_text_id);
        option = (Button) findViewById(R.id.option);
        quit = (Button) findViewById(R.id.back);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);
    /*    refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);*/
        relativeLayout = (RelativeLayout)findViewById(R.id.button_layout);
        subject = (TextView)findViewById(R.id.subject_text_id);
        udinvowner = (TextView)findViewById(R.id.UDINVOWNER_text_id);
        udtowarehouse = (TextView) findViewById(R.id.UDTOWAREHOUSE_text_id);
        mtLinearLayout = (LinearLayout) findViewById(R.id.invuse_mt_spid);
        fromstoreroomImg= (ImageView) findViewById(R.id.FROMSTORELOC_textmore_id);
        invownerImg= (ImageView) findViewById(R.id.inv_owner_textmore_id);
        udtowarehouseImg= (ImageView) findViewById(R.id.UDTOWAREHOUSE_textmore_id);
        udinvownerImg= (ImageView) findViewById(R.id.UDINVOWNER_textmore_id);
        refresh_layout= (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        spinner = (Spinner) findViewById(R.id.spnner);
    }

    @Override
    protected void initView() {
        getSubjectList();
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        getIntentData();
        if (type.equals("MT")){
            mtLinearLayout.setVisibility(View.VISIBLE);
        }
        refresh_layout.setRefreshing(false);
        refresh_layout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(InvusemiAddNewActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        option.setOnClickListener(optionOnClickListener);
        quit.setOnClickListener(backImageViewOnClickListener);
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListener);

        invuseEntity= new INVUSEEntity();
        if (type!=null){
            if (type.equals("MR")){
                invuseEntity.setUSETYPE("RETURN");
                usetype= "RETURN";
                titleTextView.setText(R.string.refund);
            }else if (type.equals("MI")){
                invuseEntity.setUSETYPE("ISSUE");
                titleTextView.setText(R.string.outbound);
                usetype = "ISSUE";
            }else if (type.equals("MT")){
                invuseEntity.setUSETYPE("TRANSFER");
                titleTextView.setText(R.string.invuse_transfer);
                usetype= "TRANSFER";
                TextView textView = (TextView) findViewById(R.id.yuankufang);
                textView.setText(R.string.fromstoreroom);
            }
        }
        invuseEntity.setSTATUS("ENTERED");
        invuseEntity.setUDCREATEBY(AccountUtils.getpersonId(this));
        Date date = new Date();
        String str = formatter.format(date);
        invuseEntity.setUDCREATEDATE(str);
        initAdapter(new ArrayList<INVUSELINE>());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        relativeLayout.setVisibility(View.VISIBLE);
        invownerImg.setOnClickListener(invownerTextOnClickListener);
        fromstoreroomImg.setOnClickListener(fromstoreroomTextViewOnClickListener);
        udtowarehouseImg.setOnClickListener(udtowarehouseViewOnClickListener);
        udinvownerImg.setOnClickListener(udinvownerOnClickListener);
/*        refresh_layout.setRefreshing(false);
        refresh_layout.setLoading(false);*/
    }
    public void getIntentData(){
        type = getIntent().getStringExtra("type");
    }
    private  View.OnClickListener udtowarehouseViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(InvusemiAddNewActivity.this,Location_chooseActivity.class);
            intent.putExtra("type","STOREROOM");
            intent.putExtra("from","udtowarehouse");
            startActivityForResult(intent,itemAdaper.getData().size()+1);
        }
    };
    private  View.OnClickListener udinvownerOnClickListener = new View.OnClickListener() {

        public void onClick(View v) {
           Intent intent = new Intent(InvusemiAddNewActivity.this,Person_chooseActivity.class);
           intent.putExtra("type","udinvowner");
           startActivityForResult(intent,0);
        }
    };
    private  View.OnClickListener fromstoreroomTextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(InvusemiAddNewActivity.this,Location_chooseActivity.class);
            intent.putExtra("type","STOREROOM");
            intent.putExtra("from","fromstoreloc");
            startActivityForResult(intent,itemAdaper.getData().size()+1);
        }
    };
    private  View.OnClickListener invownerTextOnClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            Intent intent = new Intent(InvusemiAddNewActivity.this,Person_chooseActivity.class);
            intent.putExtra("type","invowner");
            startActivityForResult(intent,0);
        }
    };
    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String[] optionList = new String[]{getString(R.string.back),getString(R.string.newline) ,getString(R.string.save)};
            final NormalListDialog normalListDialog = new NormalListDialog(InvusemiAddNewActivity.this, optionList);
            normalListDialog.title(getString(R.string.option))
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
                        case 1:
                            normalListDialog.superDismiss();
                            getaddInvuseLine();
                            break;
                        case 2://save
                            normalListDialog.superDismiss();
                            submitDataInfo();
                            break;
                    }
//                    normalListDialog.dismiss();
                }
            });
        }
    };

    private View.OnClickListener quitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalDialog dialog = new NormalDialog(InvusemiAddNewActivity.this);
            dialog.content("Sure to exit?")//
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
                            AppManager.AppExit(InvusemiAddNewActivity.this);
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
    /**
     * 提交数据*
     */
    private void submitDataInfo() {
        final NormalDialog dialog = new NormalDialog(InvusemiAddNewActivity.this);
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
                        startAsyncTask();
                        dialog.dismiss();
                    }
                });
    }

    private void startAsyncTask() {
        for (int i= 0;i<itemAdaper.getItemCount();i++){
            double qty = Double.parseDouble(itemAdaper.getItem(i).getQUANTITY());
            if (qty<=0){
                Toast.makeText(this, getString(R.string.genterthan0),Toast.LENGTH_SHORT).show();
                return;
            }
        }
        invuseEntity.setDESCRIPTION(descriptionTextView.getText().toString());
        invuseEntity.setINVOWNER(invownerTextView.getText().toString());
        invuseEntity.setFROMSTORELOC(fromstoreroomTextView.getText().toString());
        invuseEntity.setENTERBY(AccountUtils.getpersonId(InvusemiAddNewActivity.this));
        invuseEntity.setUDFILENO(udfilenoTexView.getText().toString());
        if (type!=null&&type.equalsIgnoreCase("MT"))
        {
            invuseEntity.setUDTOWAREHOUSE(udtowarehouse.getText().toString());
            invuseEntity.setUDINVOWNER(udinvowner.getText().toString());
        }

        try {
             json = JsonUtils.parsingINVUSE(invuseEntity,new ArrayList<INVUSELINE>());
            JSONObject jsonObject = new JSONObject(json);
            jsonObject.remove("INVUSEID");
            json = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (descriptionTextView.getText().toString().equalsIgnoreCase("")){
         Toast.makeText(this, getString(R.string.checknecessary),Toast.LENGTH_SHORT).show();
        }else {
            showProgressDialog("Waiting...");
            new AsyncTask<String, String, WebResult>() {
                @Override
                protected WebResult doInBackground(String... strings) {
                    WebResult reviseresult;
                    if (invuseEntity.getINVUSENUM() == null || invuseEntity.getINVUSENUM().equalsIgnoreCase("")) {
                        reviseresult = AndroidClientService.InsertWO(InvusemiAddNewActivity.this, json, Constants.INVUSE_NAME, "INVUSENUM", AccountUtils.getpersonId(InvusemiAddNewActivity.this), Constants.WORK_URL);
                    } else {
                        reviseresult = AndroidClientService.UpdateWO(InvusemiAddNewActivity.this, json, Constants.INVUSE_NAME, "INVUSENUM", invuseEntity.getINVUSENUM(), Constants.WORK_URL);
                    }
                    String jsonline = "";
                    Gson g = new GsonBuilder().serializeNulls().create();
                    if (reviseresult != null && reviseresult.errorMsg.equals("成功")) {
                        reviseresult.errorMsg = reviseresult.errorMsg + "==";
                        if (invuseEntity.getINVUSENUM()==null || invuseEntity.getINVUSENUM().equalsIgnoreCase("")) {
                            invuseEntity.setINVUSENUM(type + reviseresult.wonum);
                            INVUSELINE invuseline;
                            for (int i = 0; i < itemAdaper.getData().size(); i++) {
                                invuseline = itemAdaper.getItem(i);
                                invuseline.setINVUSENUM(type + reviseresult.wonum);
                            }
                        }else {
                            INVUSELINE invuseline;
                            for (int i = 0; i < itemAdaper.getData().size(); i++) {
                                invuseline = itemAdaper.getItem(i);
                                invuseline.setINVUSENUM(invuseEntity.getINVUSENUM());
                            }
                        }
                        if (!itemAdaper.getData().isEmpty()) {
                            jsonline = g.toJson(itemAdaper.getData());
                            jsonline = jsonline.replace("null", "\"\"");
                            final String finalJsonline = jsonline;
                            String reviseresultline = AndroidClientService.insertInvuseLine(InvusemiAddNewActivity.this, finalJsonline);
                            if (reviseresultline != null) {
                                reviseresult.errorMsg = reviseresult.errorMsg +reviseresultline + "==";
                                for (int i = 0; i < invreservesList.size(); i++) {
                                    boolean flag = false;
                                    double k = 0;
                                    double pqty =Double.parseDouble(invreservesList.get(i).getPENDINGQTY());
                                    for (int j = 0; j < itemAdaper.getItemCount(); j++) {
                                        if (invreservesList.get(i).getREQUESTNUM().equals(itemAdaper.getItem(j).getREQUESTNUM())) {
                                            flag = true;
                                            k += Double.parseDouble(itemAdaper.getItem(j).getQUANTITY());
                                        }
                                    }
                                    if (flag) {
                                        pqty += k;
                                        invreservesList.get(i).setPENDINGQTY(pqty + "");
                                        try {
                                            JSONObject jsonObject = new JSONObject(g.toJson(invreservesList.get(i)));
                                            JSONArray jsonArray = new JSONArray();
                                            JSONObject jsonObject1 = new JSONObject();
                                            jsonObject1.put("", "");
                                            jsonArray.put(jsonObject1);
                                            jsonObject.put("relationShip", jsonArray);
                                            String jsoninvr = jsonObject.toString().replace("null", "\"\"");
                                            WebResult webResult = AndroidClientService.UpdateWO(InvusemiAddNewActivity.this, jsoninvr, "INVRESERVE", "REQUESTNUM", AccountUtils.getpersonId(InvusemiAddNewActivity.this), Constants.WORK_URL);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return reviseresult;
                }

                @Override
                protected void onPostExecute(WebResult workResult) {
                    super.onPostExecute(workResult);
                    String[] results = workResult.errorMsg.split("==");
                    if (workResult == null) {
                        Toast.makeText(InvusemiAddNewActivity.this, getString(R.string.fail), Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0;i < itemAdaper.getItemCount();i++){
                            itemAdaper.getItem(i).setFLAG("U");
                        }
                        getMatDate();
                        Toast.makeText(InvusemiAddNewActivity.this,getString(R.string.success), Toast.LENGTH_SHORT).show();
                        intentToDetail();
                    }
                    List list = itemAdaper.getData();
                    closeProgressDialog();
                }
            }.execute();

        }
    }
    public void getaddInvuseLine(){
        String newline = getString(R.string.newline);
        String[] addInvuseLineMIList = {getString(R.string.reserveditems),newline,getString(R.string.selectitem)};
        String[] addInvuseLineMRList = {getString(R.string.returnitems)};
        String[] optionList2;
        if (type.equalsIgnoreCase("MI")){
            optionList2 = addInvuseLineMIList;
        }else if (type.equalsIgnoreCase("MR")){
            optionList2 = addInvuseLineMRList;
        }else {
            optionList2 = new String[]{getString(R.string.newline),getString(R.string.selectitem)};
        }

        final NormalListDialog normalListDialog = new NormalListDialog(InvusemiAddNewActivity.this, optionList2);
        normalListDialog.title(getString(R.string.option))
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
                    case 0://
                        normalListDialog.superDismiss();
                        if (fromstoreroomTextView.getText().toString().isEmpty()){
                            Toast.makeText(InvusemiAddNewActivity.this,getString(R.string.notnull),Toast.LENGTH_LONG).show();
                        }else {
                            INVUSELINE invuseline = new INVUSELINE();
                            if (type!=null){
                                if (type.equalsIgnoreCase("MI")){
                                    Intent intent1 = new Intent(InvusemiAddNewActivity.this, INVRESERVE_chooseActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("invreservesList",invreservesList);
                                    intent1.putExtras(bundle);
                                    startActivityForResult(intent1,0);
                                }else if (type.equalsIgnoreCase("MR")){
                                    if (invuseEntity.getINVUSENUM() == null || invuseEntity.getINVUSENUM().equalsIgnoreCase("")) {
                                            submitmain();
                                    }else if (invuseEntity.getINVUSENUM() != null && !invuseEntity.getINVUSENUM().equalsIgnoreCase("")){
                                        Intent intent1 = new Intent(InvusemiAddNewActivity.this, MATUSETRANS_chooseActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("invuselinelist", (Serializable) invuselinelist);
                                        intent1.putExtras(bundle);
                                        startActivityForResult(intent1,invuseline.getINDEX());
                                    }
                                }
                            }

                        }
                        break;
                    case 1:
                        normalListDialog.superDismiss();
                        if (fromstoreroomTextView.getText().toString().isEmpty()){
                            Toast.makeText(InvusemiAddNewActivity.this,getString(R.string.notnull),Toast.LENGTH_LONG).show();
                        }else {
                            INVUSELINE invuseline = new INVUSELINE();
                            if (type!=null){
                                if (type.equalsIgnoreCase("MI")){
                                    invuseline.setUSETYPE("ISSUE");
                                }else if (type.equalsIgnoreCase("MR")){
                                    invuseline.setUSETYPE("RETURN");
                                }else if (type.equalsIgnoreCase("MT")){
                                    invuseline.setUSETYPE("TRANSFER");

                                }
                            }

                            invuseline.setQUANTITY("1");
                            invuseline.setFlag("I");
                            invuseline.setFROMSTORELOC(fromstoreroomTextView.getText().toString());
                            invuseline.setENTERBY(AccountUtils.getpersonId(InvusemiAddNewActivity.this));
                            if (itemAdaper.getData().isEmpty()){
                                invuseline.setINDEX(0);
                                addData(invuseline);
                            }else {
                                invuseline.setINDEX(itemAdaper.getData().size());
                                addData(invuseline);
                            }
                        }

                        break;
                    case 2:
                        normalListDialog.superDismiss();
                        Intent invIntent = new Intent(InvusemiAddNewActivity.this, Inventory_chooseActivity.class);
                        invIntent.putExtra("location",fromstoreroomTextView.getText().toString());
                        invIntent.putExtra("showCheckBox",true);
                        startActivityForResult(invIntent,0);
                        break;
                }
//                    normalListDialog.dismiss();
            }
        });
    }
    /**
     * 获取数据*
     */
    private void initAdapter(final List<INVUSELINE> list) {
        itemAdaper = new InvuseLineAddAdaper(InvusemiAddNewActivity.this, R.layout.list_item_invuselineaddnew, list,InvusemiAddNewActivity.this);
        itemAdaper.setType(type);
        recyclerView.setAdapter(itemAdaper);
        itemAdaper.setmBasIn(mBasIn);
        itemAdaper.setmBasOut(mBasOut);
        itemAdaper.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                itemAdaper.getItem(position);
                itemAdaper.notifyItemChanged(position,itemAdaper.getItem(position));
                Log.e(TAG, "onItemClick: " + position );
            }
        });
        itemAdaper.setOnRecyclerViewItemLongClickListener(new BaseQuickAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                if (itemAdaper.getItem(position).getFLAG()!=null && itemAdaper.getItem(position).getFLAG().equals("I")){
                    itemAdaper.remove(position);
                }else {
                    itemAdaper.getItem(position).setFLAG("D");
                    itemAdaper.notifyItemChanged(position);
                }
                return true;
            }
        });
}
    /**
     * 获取数据*
     */
    public void getData(){
        items = itemAdaper.getData();
    }
    /**
     * 添加数据*
     */
    private void addData(final INVUSELINE item) {
        itemAdaper.add(item);
    }
    public void getList(){
        items = new ArrayList<>();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 100:
               Bundle personBundle = data.getExtras();
               PERSON person = (PERSON) personBundle.get("person");
               String owner = personBundle.getString("type");
               if ("invowner".equals(owner)){
                   invownerTextView.setText(person.getPERSONID());
               }else {
                   udinvowner.setText(person.getPERSONID());
               }

               break;
            case 110:
                Bundle locationBundle = data.getExtras();
                String from = locationBundle.getString("from");
                LOCATIONS locations = (LOCATIONS) locationBundle.get("location");
                if (requestCode<=itemAdaper.getData().size()) {
                    INVUSELINE invuseline1 = itemAdaper.getItem(requestCode);
                    invuseline1.setLOCATION(locations.getLOCATION());
                    itemAdaper.remove(requestCode);
                    itemAdaper.add(requestCode,invuseline1);
                }else {
                   if (from!=null && from.equalsIgnoreCase("FROMSTORELOC")){
                       fromstoreroomTextView.setText(locations.getLOCATION());
                       Log.e(TAG, "onActivityResult: " +locations.getINVOWNER());
                       itemAdaper.setLocationnum(fromstoreroomTextView.getText().toString());
                       invownerTextView.setText(locations.getINVOWNER());
                   }else {
                        udtowarehouse.setText(locations.getLOCATION());
                        udinvowner.setText(locations.getINVOWNER());
                   }
                }
                break;
            case 120:
                Bundle itemBundle = data.getExtras();
                INVENTORY item = (INVENTORY)itemBundle.get("INVENTORY");
                INVUSELINE invuseline = (INVUSELINE)itemAdaper.getData().get(requestCode);
                invuseline.setITEMNUM(item.getITEMNUM());
                invuseline.setDESCRIPTION(item.getITEMNUM_DEC());
                invuseline.setISS(item.getISSUEUNIT());
                getFromBin(item,requestCode);
                invuseline.setINDEX(requestCode);
                invuseline.setENTERBY(AccountUtils.getpersonId(InvusemiAddNewActivity.this));
                itemAdaper.remove(requestCode);
                itemAdaper.add(requestCode,invuseline);
                Log.e(TAG, "onActivityResult: sdasdasdas" + requestCode);
                break;
            case 130:
                Bundle invre = data.getExtras();
                ArrayList<INVRESERVE> invreserve = (ArrayList<INVRESERVE>) invre.get("INVRESERVE");
                if (invreserve!=null && !invreserve.isEmpty()){
                    for (int i = 0;i<invreserve.size();i++){
                        INVUSELINE invuseline1 =   new INVUSELINE();
                        invuseline1.setITEMNUM(invreserve.get(i).getITEMNUM());
                        invuseline1 .setASSETNUM(invreserve.get(i).getASSETNUM());
                        invuseline1.setDESCRIPTION(invreserve.get(i).getDESCRIPTION());
                        invuseline1.setWONUM(invreserve.get(i).getWONUM());
                        invuseline1.setISS(invreserve.get(i).getISSUETO());
                        invuseline1.setFROMBIN(invreserve.get(i).getMRLINENUM());
                        invuseline1.setFROMLOT(invreserve.get(i).getTOSTORELOC());
                        invuseline1.setLOCATION(invreserve.get(i).getOPLOCATION());
                        invuseline1.setREQUESTNUM(invreserve.get(i).getREQUESTNUM());
                        double  reservedqty = Double.parseDouble(invreserve.get(i).getRESERVEDQTY());
                        double pendingqty = Double.parseDouble(invreserve.get(i).getPENDINGQTY());
                        double j = 0;
                        for (int m = 0;m < itemAdaper.getItemCount();m++) {
                            INVUSELINE invuseline2 = itemAdaper.getItem(m);
                            if (invuseline2.getREQUESTNUM()!=null&&invuseline2.getREQUESTNUM().equals(invreserve.get(i).getREQUESTNUM())){
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
                        invuseline1.setUSETYPE("ISSUE");
                        invuseline1.setENTERBY(AccountUtils.getpersonId(InvusemiAddNewActivity.this));
                        invuseline1.setFlag("I");
                        invuseline1.setFROMSTORELOC(fromstoreroomTextView.getText().toString());
                        if (itemAdaper.getData().isEmpty()){
                            invuseline1.setINDEX(0);
                        }else {
                            invuseline1.setINDEX(itemAdaper.getData().size());
                        }
                        itemAdaper.add(invuseline1);
                        getFromBin(invuseline1);
                    }
                }

                break;
            case 140:
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    ASSET asset = (ASSET) bundle.get("asset");
                    INVUSELINE invuseline1 = itemAdaper.getItem(requestCode);
                    invuseline1.setASSETNUM(asset.getASSETNUM());
                    itemAdaper.remove(requestCode);
                    itemAdaper.add(requestCode,invuseline1);
                }
                break;
            case 160:
                Bundle invbalance = data.getExtras();
                INVBALANCES invbalances = (INVBALANCES) invbalance.get("invbalance");
                if (invbalances!=null){
                    INVUSELINE invuseline1 =   itemAdaper.getItem(requestCode);
                    invuseline1.setFROMBIN(invbalances.getBINNUM());
                    invuseline1.setFROMLOT(invbalances.getLOTNUM());
                    if(type.equalsIgnoreCase("MT")){
                        invuseline1.setTOLOT(invbalances.getLOTNUM());
                    }
                    itemAdaper.remove(requestCode);
                    itemAdaper.add(requestCode,invuseline1);
                }
                break;
            case 105:
                Bundle invuselineDate = data.getExtras();
                INVUSELINE invuseline1 = new INVUSELINE();
                ArrayList<MATUSETRANS> matusetrans = (ArrayList<MATUSETRANS>) invuselineDate.get("MATUSETRANS");
                if (invuseline1!= null && !matusetrans.isEmpty() ){
                    for (int i=0;i<matusetrans.size();i++){
                        invuseline1.setFROMSTORELOC(invuseEntity.getFROMSTORELOC());
                        invuseline1.setUSETYPE("RETURN");
                        invuseline1.setTYPE("add");
                        invuseline1.setFLAG("I");
                        Double quanty = Double.parseDouble(matusetrans.get(i).getQUANTITY());
                        invuseline1.setQUANTITY(Math.abs(quanty)+ "");
                        invuseline1.setINVUSENUM(invuseEntity.getINVUSENUM());
                        invuseline1.setITEMNUM(matusetrans.get(i).getITEMNUM());
                        invuseline1.setDESCRIPTION(matusetrans.get(i).getDESCRIPTION());
                        invuseline1.setFROMBIN(matusetrans.get(i).getBINNUM());
                        invuseline1.setFROMLOT(matusetrans.get(i).getLOTNUM());
                        invuseline1.setLOCATION(matusetrans.get(i).getLOCATION());
                        invuseline1.setENTERBY(AccountUtils.getpersonId(this));
                        invuseline1.setASSETNUM(matusetrans.get(i).getASSETNUM());
                        invuseline1.setACTUALDATE(matusetrans.get(i).getACTUALDATE());
                        invuseline1.setREFWO(matusetrans.get(i).getREFWO());
                        invuseline1.setISSUETO(matusetrans.get(i).getISSUETO());
                        invuseline1.setINDEX(itemAdaper.getItemCount());
                        invuseline1.setUSETYPE("RETURN");
                        invuseline1.setENTERBY(AccountUtils.getpersonId(InvusemiAddNewActivity.this));
                        invuseline1.setFlag("I");
                        invuseline1.setFROMSTORELOC(fromstoreroomTextView.getText().toString());
                        if (itemAdaper.getData().isEmpty()){
                            invuseline1.setINDEX(0);
                        }else {
                            invuseline1.setINDEX(itemAdaper.getData().size());
                        }
                        itemAdaper.add(invuseline1);
                        getFromBin(invuseline1);
                    }
                }
                break;
            case 1:
                Bundle bundle = data.getExtras();
                ArrayList<INVENTORY> inventories = (ArrayList<INVENTORY>) bundle.get("itemlist");
                if (inventories!=null && !inventories.isEmpty()){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String date = simpleDateFormat.format(new Date());
                    for(int i = 0;i<inventories.size();i++){
                        INVUSELINE invuseline2 = new INVUSELINE();
                        invuseline2.setTYPE("add");
                        invuseline2.setFLAG("I");
                        invuseline2.setITEMNUM(inventories.get(i).getITEMNUM());
                        invuseline2.setDESCRIPTION(inventories.get(i).getITEMNUM_DEC());
                        invuseline2.setINVUSENUM(invuseEntity.getINVUSENUM());
                        invuseline2.setFROMSTORELOC(fromstoreroomTextView.getText().toString());
                        invuseline2.setUSETYPE(usetype);
                        invuseline2.setACTUALDATE(date);
                        invuseline2.setQUANTITY("1");
                        invuseline2.setCONVERSION("1");
                        invuseline2.setENTERBY(AccountUtils.getpersonId(this));
                        invuseline2.setINDEX(itemAdaper.getItemCount());
                        itemAdaper.add(invuseline2);
                        getFromBin(invuseline2);
                    }
                }
                break;
        }
    }
    public void getDefLoc(){
        final String person = AccountUtils.getpersonId(InvusemiAddNewActivity.this);
        HttpManager.getDataPagingInfo(InvusemiAddNewActivity.this, HttpManager.getLocationUrl(person,"STOREROOM", 1, 20,1), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results data) {
                Log.e("默认库房", "成功");
            }
            @Override
            public void onSuccess(Results data, int totalPages, int currentPage) {
                ArrayList<LOCATIONS> locations = JsonUtils.parsingLOCATIONS(InvusemiAddNewActivity.this,data.getResultlist());
                if (!locations.isEmpty()){
                    for (LOCATIONS locations1 : locations){
                       if (person.equalsIgnoreCase(locations1.getINVOWNER())){
                           if (type.equals("MT")){
                               udtowarehouse.setText(locations1.getLOCATION());
                               udinvowner.setText(person);
                           }else {
                               fromstoreroomTextView.setText(locations1.getLOCATION());
                               invownerTextView.setText(person);
                           }
                       }
                    }
                }
            }
            @Override
            public void onFailure(String error) {
                Log.e("默认库房", "失败" );
            }
        });
    }
    public void getFromBin(INVENTORY inventory, final int requestCode){
        HttpManager.getDataPagingInfo(InvusemiAddNewActivity.this, HttpManager.getInvbalancesUrl(inventory.getITEMNUM(),inventory.getLOCATION(),inventory.getSITEID(),inventory.getITEMSETID(),1, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                invbalances = JsonUtils.parsingINVBALANCES(InvusemiAddNewActivity.this, results.getResultlist());
                INVUSELINE invuseline1 = itemAdaper.getItem(requestCode);
                if (invbalances != null && !invbalances.isEmpty()){
                    invuseline1.setFROMBIN(invbalances.get(0).getBINNUM());
                    invuseline1.setFROMLOT(invbalances.get(0).getLOTNUM());
                    if (type.equalsIgnoreCase("MT")){
                        invuseline1.setTOLOT(invbalances.get(0).getLOTNUM());
                    }
                    itemAdaper.remove(requestCode);
                    itemAdaper.add(requestCode,invuseline1);
                }else {
                    itemAdaper.remove(requestCode);
                    itemAdaper.add(requestCode,invuseline1);
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void getInvesrverData(String search) {
        HttpManager.getDataPagingInfo(InvusemiAddNewActivity.this, HttpManager.getINVRESERVEUrl(search,fromstoreroomTextView.getText().toString()), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                invreservesList =  JsonUtils.parsingINVRESERVE(InvusemiAddNewActivity.this, results.getResultlist());
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
                    result  = AndroidClientService.getMatDate(InvusemiAddNewActivity.this, invuseEntity.getINVUSENUM(),invuseEntity.getFROMSTORELOC(),invuseEntity.getSTATUS());
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                    invuselinelist =  JsonUtils.parsingMATUSETRANS(InvusemiAddNewActivity.this, s);

            }
        }.execute();
    }
    public void getFromBin(final INVUSELINE theInvuseline){
        HttpManager.getDataPagingInfo(this, HttpManager.getInvbalancesUrl(theInvuseline.getITEMNUM(),theInvuseline.getFROMSTORELOC(),"","ITEMSET",1, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<INVBALANCES> invbalanceslist = JsonUtils.parsingINVBALANCES(InvusemiAddNewActivity.this, results.getResultlist());
                if (invbalanceslist == null||invbalanceslist.isEmpty()){

                }else {
                    String bin = invbalanceslist.get(0).getBINNUM();
                    String loc = invbalanceslist.get(0).getLOTNUM();
                    theInvuseline.setFROMLOT(loc);
                    theInvuseline.setFROMBIN(bin);
                }
                itemAdaper.notifyDataSetChanged();
            }
            @Override
            public void onFailure(String error) {

            }
        });

    }
    private void submitmain(){
       final NormalDialog normalDialog = new NormalDialog(this);
        normalDialog.content(getString(R.string.suretosave))//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        normalDialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        normalDialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                       normalDialog.dismiss();
                       startAsyncTask();
                    }
                });
    }
    @Override

    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRefresh() {
        refresh_layout.setRefreshing(false);
    }
    public void getItem(String itemnum){
        HttpManager.getDataPagingInfo(this, HttpManager.getItemUrl(itemnum, "", 1, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results data) {

            }

            @Override
            public void onSuccess(Results data, int totalPages, int currentPage) {
                ArrayList<ITEM> items = JsonUtils.parsingITEM(InvusemiAddNewActivity.this, data.getResultlist());
                if (items!=null&&!items.isEmpty()){
                    ITEM item = items.get(0);
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    public void getSubjectList(){
        List list = SUBJECT.list;
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,list);
    }

    @Override
    public void onLoad() {
        refresh_layout.setLoading(false);
    }
    public void intentToDetail(){
        Intent intent = new Intent(InvusemiAddNewActivity.this,InvuseDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("item",invuseEntity);
        bundle.putString("type",type);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
                String m = adapter.getItem(arg2);
                invuseEntity.setSUBJECT(SUBJECT.stringStringMap.get(m));
                subject.setText(m);
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

}


