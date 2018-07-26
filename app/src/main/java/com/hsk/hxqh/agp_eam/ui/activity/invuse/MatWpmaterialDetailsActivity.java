package com.hsk.hxqh.agp_eam.ui.activity.invuse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog2;
import com.flyco.dialog.widget.NormalDialog;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.api.JsonUtils;
import com.hsk.hxqh.agp_eam.config.Constants;
import com.hsk.hxqh.agp_eam.model.COMPANIES;
import com.hsk.hxqh.agp_eam.model.INVENTORY;
import com.hsk.hxqh.agp_eam.model.LOCATIONS;
import com.hsk.hxqh.agp_eam.model.PERSON;
import com.hsk.hxqh.agp_eam.model.WORKORDER;
import com.hsk.hxqh.agp_eam.model.WPITEM;
import com.hsk.hxqh.agp_eam.model.WPMATERIAL;
import com.hsk.hxqh.agp_eam.model.WebResult;
import com.hsk.hxqh.agp_eam.ui.activity.BaseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.WorkOrderDetailsActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Companies_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Inventory_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Item_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Location_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Person_chooseActivity;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;
import com.hsk.hxqh.agp_eam.unit.DateTimeSelect;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * 工单计划物料详情
 */
public class MatWpmaterialDetailsActivity extends BaseActivity {
    private static String TAG = "MatWpmaterialDetailsActivity";

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    /**
     * 字段显示
     **/
    private TextView taskid; //任务
    private TextView itemnum; //项目
    private TextView description; //描述
    private TextView itemqty; //数量
    private TextView location; //库房
    private ImageView locationImg;
    private TextView vendor;//供应商
    private ImageView vendorImg;
    private TextView requestby;//请求者
    private ImageView requestbyImg;
    private TextView requiredate;//要求的日期
    private ImageView itemImage;

    /**
     * 任务
     **/
    private WPMATERIAL wpmaterial;
    private WORKORDER workorder;
    private ArrayList<WPMATERIAL> wpmaterials;

    private Button quit;//取消
    private Button option;//保存

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private View includ;
    private  String flag;
    private RelativeLayout relativeLayout;
    private EditText unitcost;
    private TextView issueto;
    private ImageView dateSelect;
    private ProgressDialog mProgressDialog;
    private LinearLayout linearLayout;
    private LinearLayout vendorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wpmaterial_details);
        geiIntentData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }
    private void geiIntentData() {
        flag = getIntent().getExtras().getString("flag");
        workorder = (WORKORDER) getIntent().getSerializableExtra("workorder");
        wpmaterial = (WPMATERIAL) getIntent().getSerializableExtra("wpmaterial");
        if (wpmaterial == null){
            wpmaterial = new WPMATERIAL();
            wpmaterial.setITEMQTY("1.00");
            wpmaterial.setUNITCOST("0.00");
            wpmaterial.setLINECOST("0.00");
            wpmaterial.setLINETYPE("ITEM");
            wpmaterial.setREQUESTBY(AccountUtils.getpersonId(MatWpmaterialDetailsActivity.this));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:00");
            wpmaterial.setLOCATION(workorder.getLOCATION());
            wpmaterial.setREQUIREDATE(simpleDateFormat.format(new Date()));
        }
        wpmaterials = (ArrayList<WPMATERIAL>) getIntent().getSerializableExtra("wpmaterials");
    }

    @Override
    protected void findViewById() {
        itemImage = (ImageView) findViewById(R.id.wpmaterial_itemnum_image_id);
        relativeLayout = (RelativeLayout) findViewById(R.id.button_layout);
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);
        includ= findViewById(R.id.button_layout_id);
        taskid = (TextView) findViewById(R.id.wpmaterial_taskid);
        itemnum = (TextView) findViewById(R.id.wpmaterial_itemnum);
        description = (TextView) findViewById(R.id.wpmaterial_description);
        itemqty = (TextView) findViewById(R.id.wpmaterial_itemqty);
        location = (TextView) findViewById(R.id.wpmaterial_location);
        locationImg = (ImageView) findViewById(R.id.wpmaterial_location_image_id);
        vendor = (TextView) findViewById(R.id.wpmaterial_vendor);
        vendorImg = (ImageView) findViewById(R.id.wpmaterial_vendor_image_id);
        requestby = (TextView) findViewById(R.id.wpmaterial_requestby);
        requestbyImg = (ImageView) findViewById(R.id.wpmaterial_requestby_image_id);
        requiredate = (TextView) findViewById(R.id.wpmaterial_requiredate);
        unitcost = (EditText)  findViewById(R.id.wpmaterial_unitcost);
        issueto = (TextView) findViewById(R.id.wpmaterial_issueto);
        quit = (Button) findViewById(R.id.back);
        option = (Button) findViewById(R.id.option);
        dateSelect = (ImageView) findViewById(R.id.wpmaterial_requiredate_image_id);
        linearLayout = (LinearLayout) findViewById(R.id.taskid_layout);
        vendorLayout = (LinearLayout) findViewById(R.id.vendor_layout_id);
    }


    @Override
    protected void initView() {
        vendorLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        includ.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.work_wpmaterial);

        if (wpmaterial != null) {
            taskid.setText(wpmaterial.getTASKID());
            itemnum.setText(wpmaterial.getITEMNUM());
            description.setText(wpmaterial.DESCRIPTION);
            itemqty.setText(wpmaterial.ITEMQTY);
            location.setText(wpmaterial.LOCATION);
            vendor.setText(wpmaterial.VENDOR);
            requestby.setText(wpmaterial.REQUESTBY);
            requiredate.setText(wpmaterial.REQUIREDATE);
            issueto.setText(wpmaterial.getISSUETO());
            unitcost.setText(wpmaterial.getUNITCOST());
            vendor.setText(wpmaterial.getVENDOR());

        }

        locationImg.setOnClickListener(locationOnClickListener);
        requestbyImg.setOnClickListener(personOnClickListener);
        vendorImg.setOnClickListener(vendorOnClickListener);
        itemImage.setOnClickListener(itemnumOnClickListener);

        quit.setOnClickListener(backImageViewOnClickListener);
        option.setOnClickListener(saveOnClickListener);
        dateSelect.setOnClickListener(new DateTimeOnClickListener(requiredate));

    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private View.OnClickListener itemnumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MatWpmaterialDetailsActivity.this, Inventory_chooseActivity.class);
            Bundle bundle = new Bundle();
            if (workorder!=null){
                bundle.putSerializable("location",workorder.getLOCATION());
            }else {
                bundle.putSerializable("location","");
            }
            intent.putExtras(bundle);
            startActivityForResult(intent,3);
        }
    };
    private View.OnClickListener locationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MatWpmaterialDetailsActivity.this, Location_chooseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("type","STOREROOM");
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener personOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MatWpmaterialDetailsActivity.this, Person_chooseActivity.class);
            startActivityForResult(intent, 1);
        }
    };

    private View.OnClickListener vendorOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MatWpmaterialDetailsActivity.this, Companies_chooseActivity.class);
            startActivityForResult(intent, 2);
        }
    };

    private View.OnClickListener saveOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            submitDataInfo();
        }
    };

    /**
     * 提交数据*
     */
    private void submitDataInfo() {
        final NormalDialog dialog = new NormalDialog(MatWpmaterialDetailsActivity.this);
        int i = 3;
        boolean isadd = false;
        String[] list = {getString(R.string.cancel),getString(R.string.update),getString(R.string.delete)};
        if (flag!= null && flag .equalsIgnoreCase("I")){
            i = 2;
            isadd = true;
            list = new String[]{getString(R.string.cancel),getString(R.string.xinjian)};
        }
        dialog.content(getString(R.string.option)).btnNum(i).btnText(list)//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        final boolean finalIsadd = isadd;
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
                        getWpmaterial();
                        Intent intent = getIntent();
                        Bundle bundle = new Bundle();
                    if (finalIsadd){
                        wpmaterial.setFLAG("I");
                        if(workorder!=null){
                            wpmaterial.setWONUM(workorder.getWONUM());
                        }
                        wpmaterial.setDIRECTREQ("0");
                        bundle.putSerializable("wpmaterial",wpmaterial);
                        setResult(100,intent);
                        intent.putExtras(bundle);
                        finish();
                    }else {
                        if (!wpmaterial.getFLAG().equalsIgnoreCase("I")){
                            wpmaterial.setFLAG("U");
                        }
                        bundle.putSerializable("wpmaterial",wpmaterial);
                        intent.putExtras(bundle);
                        setResult(100,intent);
                        finish();
                    }

                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        Intent intent = getIntent();
                        Bundle bundle = new Bundle();
                        wpmaterial.setFLAG("D");
                        bundle.putSerializable("wpmaterial",wpmaterial);
                        intent.putExtras(bundle);
                        setResult(100,intent);
                        finish();
                    }
                });
    }

    /**
     * 提交数据*
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (requestCode) {
                case 0:
                    LOCATIONS asset = (LOCATIONS) data.getSerializableExtra("location");
                    location.setText(asset.getLOCATION());
                    break;
                case 1:
                    PERSON person = (PERSON) data.getSerializableExtra("person");
                    requestby.setText(person.getPERSONID());
                    break;
                case 2:
                    COMPANIES companies = (COMPANIES) data.getSerializableExtra("companies");
                    vendor.setText(companies.getCOMPANY());
                    break;
                case 3:
                    INVENTORY inventory = (INVENTORY)data.getSerializableExtra("INVENTORY");
                    itemnum.setText(inventory.getITEMNUM());
                    description.setText(inventory.getITEMNUM_DEC());
                    break;
            }
        }
    }
    public void getWpmaterial(){
     wpmaterial.setITEMQTY(itemqty.getText().toString());
     wpmaterial.setDESCRIPTION(description.getText().toString());
     wpmaterial.setITEMNUM(itemnum.getText().toString());
     wpmaterial.setLOCATION(location.getText().toString());
     wpmaterial.setREQUESTBY(requestby.getText().toString());
     wpmaterial.setREQUIREDATE(requiredate.getText().toString());
     double qut = Double.parseDouble(itemqty.getText().toString());
     double unit = Double.parseDouble(unitcost.getText().toString());
     double totole = qut*unit;
     wpmaterial.setLINECOST(totole+ "");
     wpmaterial.setVENDOR(vendor.getText().toString());
     wpmaterial.setISSUETO(issueto.getText().toString());
    }
    //时间选择监听
    private class DateTimeOnClickListener implements View.OnClickListener {
        TextView textView;

        private DateTimeOnClickListener(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onClick(View view) {
            new DateTimeSelect(MatWpmaterialDetailsActivity.this, textView).showDialog();
        }
    }

}
