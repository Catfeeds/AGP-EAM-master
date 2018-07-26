package com.hsk.hxqh.agp_eam.ui.activity.invuse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.api.HttpManager;
import com.hsk.hxqh.agp_eam.api.HttpRequestHandler;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.bean.Results;
import com.hsk.hxqh.agp_eam.model.ASSET;
import com.hsk.hxqh.agp_eam.model.INVBALANCES;
import com.hsk.hxqh.agp_eam.model.INVENTORY;
import com.hsk.hxqh.agp_eam.model.INVRESERVE;
import com.hsk.hxqh.agp_eam.model.INVUSEEntity;
import com.hsk.hxqh.agp_eam.model.INVUSELINE;
import com.hsk.hxqh.agp_eam.model.LOCATIONS;
import com.hsk.hxqh.agp_eam.model.PERSON;
import com.hsk.hxqh.agp_eam.ui.activity.BaseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.LabtransDetailsActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Asset_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.INVBALANCES_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Inventory_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Location_chooseActivity;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;
import com.hsk.hxqh.agp_eam.unit.DateSelect;

import java.util.ArrayList;

/**
 * Created by zzw on 2018/6/6.
 */

public class InvuselineAddNewActivity extends BaseActivity{
    private LinearLayout itemnumLinear;
    private LinearLayout descLinear;
    private EditText desc;
    private EditText quantity;
    private EditText remark ;
    private TextView itemnum;
    private TextView frombin ;
    private TextView fromloc;
    private TextView wonum ;
    private TextView assetnum;
    private String locationnum;
    private INVUSELINE invuseline;
    private ArrayList<INVBALANCES> invbalances;
    private INVUSEEntity invuseEntity;
    private String type;
    private String[] optionlist = {"update","delete"};
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private  Button option;
    private Button quit;
    private RelativeLayout btLinearLayout;
    private TextView actualdate;
    private TextView location;
    private TextView usetype;
    private String add;
    private ImageView backImageView;
    private LinearLayout conLayout;
    private EditText conversion;
    private LinearLayout linearLayout;
    private EditText toBin;
    private EditText toLoc;
    private TextView titleTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invuseline_addnew);
        getIntentData();
        findViewById();
        initView();
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    @Override
    protected void findViewById() {
        titleTextView = (TextView) findViewById(R.id.menu_title);
        linearLayout = (LinearLayout) findViewById(R.id.invuseline_tobin_layout);
        toBin = (EditText) findViewById(R.id.invuseline_tobin_id);
        toLoc = (EditText) findViewById(R.id.invuseline_toloc_id);
        conversion =(EditText) findViewById(R.id.invuseline_conversion_id);
        conLayout = (LinearLayout) findViewById(R.id.conversion_layout_id);
        backImageView = (ImageView) findViewById(R.id.menu_id);
    itemnumLinear = (LinearLayout)findViewById(R.id.invuseline_numLayout_id);
    descLinear = (LinearLayout)findViewById(R.id.invuseline_descLayout_id);
    itemnum = (TextView)findViewById(R.id.invuseline_itemnum_id);
    frombin = (TextView)findViewById(R.id.invuseline_frombin_id);
    fromloc = (TextView)findViewById(R.id.invuseline_fromloc_id);
    wonum = (TextView)findViewById(R.id.invuseline_wonum_id);
    assetnum = (TextView)findViewById(R.id.invuseline_assetnum_id);
    desc = (EditText) findViewById(R.id.invuseline_desc_id);
    quantity = (EditText)findViewById(R.id.invuseline_quantity_id);
    remark = (EditText)findViewById(R.id.invuseline_remark_id);
    option = (Button) findViewById(R.id.option);
    quit = (Button) findViewById(R.id.back);
    btLinearLayout = (RelativeLayout) findViewById(R.id.button_layout);
    actualdate = (TextView) findViewById(R.id.invuseline_actualdate_id);
    location = (TextView)findViewById(R.id.invuseline_location_id);
    usetype = (TextView) findViewById(R.id.invuseline_usetype_id);
    }

    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListener);
        btLinearLayout.setVisibility(View.VISIBLE);
        itemnumLinear.setVisibility(View.VISIBLE);
        descLinear.setVisibility(View.VISIBLE);
        itemnum.setOnClickListener(itemnumOnClickListener);
        option.setOnClickListener(optionOnClickListener);
        fromloc.setOnClickListener(fromlocOnClickListener);
        frombin.setOnClickListener(frombinOnClickListener);
        wonum.setOnClickListener(wonumOnClickListener);
        assetnum.setOnClickListener(assetnumOnClickListener);
        location.setOnClickListener(locationOnClickListener);
        actualdate.setOnClickListener(getActualdateOnClickListener);
        quit.setOnClickListener(backImageViewOnClickListener);
        if (type!=null){
            if (type.equalsIgnoreCase("MI")){
                usetype.setText("ISSUE");
                titleTextView.setText(R.string.outbound);
            }else if (type.equalsIgnoreCase("MR")){
                titleTextView.setText(R.string.refund);
                usetype.setText("RETURN");
            }else if (type.equalsIgnoreCase("MT")){
                usetype.setText("TRANSFER");
                conversion.setText("1.00");
                conLayout.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                titleTextView.setText(R.string.invuse_transfer);
            }
        }
        if (invuseline!=null){
            itemnum.setText(invuseline.getITEMNUM());
            desc.setText(invuseline.getDESCRIPTION());
            fromloc.setText(invuseline.getFROMLOT());
            frombin.setText(invuseline.getFROMBIN());
            assetnum.setText(invuseline.getASSETNUM());
            actualdate.setText(invuseline.getACTUALDATE());
            quantity.setText(invuseline.getQUANTITY());
            remark.setText(invuseline.getREMARK());
            wonum.setText(invuseline.getWONUM());
            location.setText(invuseline.getLOCATION());
            usetype.setText(invuseline.getUSETYPE());
            toBin.setText(invuseline.getTOBIN());
            toLoc.setText(invuseline.getTOLOT());
            conversion.setText(invuseline.getCONVERSION());
        }else {
            invuseline = new INVUSELINE();
            quantity.setText("1");
            invuseline.setINVUSENUM(invuseEntity.getINVUSENUM());
            conversion.setText("1.00");
            invuseline.setCONVERSION("1.00");
        }
    }

    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private View.OnClickListener  itemnumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(InvuselineAddNewActivity.this, Inventory_chooseActivity.class);
            intent.putExtra("location",locationnum);
            startActivityForResult(intent,0);
        }
    };
    private  View.OnClickListener getActualdateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new DateSelect(InvuselineAddNewActivity.this, actualdate).showDialog();
        }
    };
    private View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            invuseline.setWONUM(wonum.getText().toString());
            invuseline.setITEMNUM(itemnum.getText().toString());
            invuseline.setQUANTITY(quantity.getText().toString());
            invuseline.setFROMLOT(fromloc.getText().toString());
            invuseline.setFROMBIN(frombin.getText().toString());
            invuseline.setENTERBY(AccountUtils.getpersonId(InvuselineAddNewActivity.this));
            invuseline.setDESCRIPTION(desc.getText().toString());
            invuseline.setREMARK(remark.getText().toString());
            if (type != null){
                if (type.equals("MI")){
                    invuseline.setUSETYPE("ISSUE");
                }else if (type.equals("MR")){
                    invuseline.setUSETYPE("RETURN");
                }else if (type.equals("MT")){
                    invuseline.setUSETYPE("TRANSFER");
                    invuseline.setSHE("");
                    invuseline.setUSE("");
                    invuseline.setTASKID("");
                }
            }
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("INVUSELINE",invuseline);
            bundle.putSerializable("item",invuseEntity);
            intent.putExtras(bundle);
            setResult(130,intent);
            finish();
        }
    };
    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (add!=null){
                optionlist = new String[]{"add"};
            }
            final NormalListDialog normalListDialog = new NormalListDialog(InvuselineAddNewActivity.this, optionlist);
            normalListDialog.title("Option")
                    .showAnim(mBasIn)//
                    .dismissAnim(mBasOut)//
                    .show();
            normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (add!=null){
                        position = 2;
                    }
                    switch (position){
                        case 0:
                            normalListDialog.superDismiss();
                            if (!invuseline.getTYPE().equalsIgnoreCase("add")){
                                invuseline.setTYPE("update");
                                invuseline.setFLAG("U");
                            }
                            Intent intent = getIntent();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("invuseline",invuseline);
                            intent.putExtras(bundle);
                            setResult(100,intent);
                            finish();
                            break;
                        case 1:
                            if (!invuseline.getTYPE().equalsIgnoreCase("add")){
                                invuseline.setTYPE("delete");
                                invuseline.setFLAG("D");
                            }else {
                                invuseline.setTYPE("delete");
                            }
                            Intent intent1 = getIntent();
                            Bundle bundle1 = new Bundle();
                            bundle1.putSerializable("invuseline",invuseline);
                            intent1.putExtras(bundle1);
                            setResult(100,intent1);
                            finish();
                            break;
                        case 2://save
                            normalListDialog.superDismiss();
                            invuseline.setWONUM(wonum.getText().toString());
                            invuseline.setITEMNUM(itemnum.getText().toString());
                            invuseline.setQUANTITY(quantity.getText().toString());
                            invuseline.setFROMLOT(fromloc.getText().toString());
                            invuseline.setFROMBIN(frombin.getText().toString());
                            invuseline.setENTERBY(AccountUtils.getpersonId(InvuselineAddNewActivity.this));
                            invuseline.setDESCRIPTION(desc.getText().toString());
                            invuseline.setREMARK(remark.getText().toString());
                            if (type != null){
                                if (type.equals("MI")){
                                    invuseline.setUSETYPE("ISSUE");
                                }else if (type.equals("MR")){
                                    invuseline.setUSETYPE("RETURN");
                                }else if (type.equals("MT")){
                                    invuseline.setCONVERSION(conversion.getText().toString());
                                    invuseline.setTOBIN(toBin.getText().toString());
                                    invuseline.setTOLOT(toLoc.getText().toString());
                                    invuseline.setUSETYPE("TRANSFER");
                                    invuseline.setSHE("");
                                    invuseline.setUSE("");
                                    invuseline.setTASKID("");
                                }
                            }
                            invuseline.setTYPE("add");
                            invuseline.setFLAG("I");
                            Intent intent2 = getIntent();
                            Bundle bundle2 = new Bundle();
                            bundle2.putSerializable("invuseline",invuseline);
                            intent2.putExtras(bundle2);
                            setResult(100,intent2);
                            finish();
                            break;
                    }
//                    normalListDialog.dismiss();
                }
            });
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 100:
                break;
            case 110:
                Bundle locationBundle = data.getExtras();
                LOCATIONS locations = (LOCATIONS) locationBundle.get("location");
                invuseline.setLOCATION(locations.getLOCATION());
                location.setText(locations.getLOCATION());
                break;
            case 120:
                Bundle itemBundle = data.getExtras();
                INVENTORY item = (INVENTORY)itemBundle.get("INVENTORY");
                invuseline.setITEMNUM(item.getITEMNUM());
                itemnum.setText(item.getITEMNUM());
                invuseline.setDESCRIPTION(item.getITEMNUM_DEC());
                desc.setText(item.getITEMNUM_DEC());
                getFromBin(item);
                invuseline.setINDEX(requestCode);
                Log.e(TAG, "onActivityResult: sdasdasdas" + requestCode);
                break;
            case 140:
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    ASSET asset = (ASSET) bundle.get("asset");
                    invuseline.setASSETNUM(asset.getASSETNUM());
                    assetnum.setText(asset.getASSETNUM());
                }
                break;
            case 160:
                Bundle invbalance = data.getExtras();
                INVBALANCES invbalances = (INVBALANCES) invbalance.get("invbalance");
                if (invbalances!=null){
                    invuseline.setFROMBIN(invbalances.getBINNUM());
                    frombin.setText(invbalances.getBINNUM());
                    invuseline.setFROMLOT(invbalances.getLOTNUM());
                    fromloc.setText(invbalances.getLOTNUM());
                    toLoc.setText(invbalances.getLOTNUM());
                    invuseline.setTOLOT(invbalances.getLOTNUM());
                }
                break;
        }
    }



    public void getIntentData() {
        invuseline = (INVUSELINE)getIntent().getExtras().get("invuseline");
        type = getIntent().getExtras().getString("type");
        invuseEntity = (INVUSEEntity)getIntent().getExtras().get("item");
        add = getIntent().getExtras().getString("option");
        locationnum = invuseEntity.getFROMSTORELOC();
    }
    public void getFromBin(INVENTORY inventory){
        HttpManager.getDataPagingInfo(InvuselineAddNewActivity.this, HttpManager.getInvbalancesUrl(inventory.getITEMNUM(),inventory.getLOCATION(),inventory.getSITEID(),inventory.getITEMSETID(),1, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                invbalances = JsonUtils.parsingINVBALANCES(InvuselineAddNewActivity.this, results.getResultlist());
                if (invbalances == null){
                    invbalances = new ArrayList<>();
                }else {
                    String bin = invbalances.get(0).getBINNUM();
                    String loc = invbalances.get(0).getLOTNUM();
                    frombin.setText(bin);
                    fromloc.setText(loc);
                    toLoc.setText(loc);
                    invuseline.setFROMLOT(loc);
                    invuseline.setFROMBIN(bin);
                    invuseline.setTOLOT(loc);
                }

            }
            @Override
            public void onFailure(String error) {

            }
        });

    }
    private void submitDataInfo() {
        final NormalDialog dialog = new NormalDialog(this);
        dialog.btnNum(3).btnText("cancel","update","delete")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        finish();

                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        invuseline.setTYPE("update");
                        Intent intent = getIntent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("invuseline",invuseline);
                        intent.putExtras(bundle);
                        setResult(100,intent);
                        finish();
                        dialog.dismiss();

                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        invuseline.setTYPE("delete");
                        Intent intent = getIntent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("invuseline",invuseline);
                        intent.putExtras(bundle);
                        setResult(100,intent);
                        finish();
                        dialog.dismiss();
                    }
                });
    }
    View.OnClickListener itemnunewOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(InvuselineAddNewActivity.this, Inventory_chooseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("location",locationnum);
            intent.putExtras(bundle);
            startActivityForResult(intent,0);
        }
    };
    View.OnClickListener frombinOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(InvuselineAddNewActivity.this, INVBALANCES_chooseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("invuseline",invuseline);
            intent.putExtras(bundle);
            startActivityForResult(intent,0);
        }
    };
    View.OnClickListener  fromlocOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(InvuselineAddNewActivity.this, INVBALANCES_chooseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("invuseline",invuseline);
            intent.putExtras(bundle);
            startActivityForResult(intent,0);
        }
    };
    View.OnClickListener  assetnumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(InvuselineAddNewActivity.this, Asset_chooseActivity.class);
            startActivityForResult(intent,0);
        }
    };
    View.OnClickListener locationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(InvuselineAddNewActivity.this, Location_chooseActivity.class);
            startActivityForResult(intent,0);
        }
    };
    View.OnClickListener  wonumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Intent intent = new Intent(baseActivity, WorkOederListActivity.class);
            //baseActivity.startActivityForResult(intent,item.getINDEX());
        }
    };
    View.OnClickListener actualdateOnClickListener  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new DateSelect(InvuselineAddNewActivity.this,actualdate).showDialog();
        }
    };

}
