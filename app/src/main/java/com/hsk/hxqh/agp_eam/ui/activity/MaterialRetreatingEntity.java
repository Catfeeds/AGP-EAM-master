package com.hsk.hxqh.agp_eam.ui.activity;

import android.content.Intent;
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
import com.flyco.dialog.widget.NormalDialog;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.MATRECTRANS;
import com.hsk.hxqh.agp_eam.model.PO;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 * 库存
 */

public class MaterialRetreatingEntity extends BaseActivity implements Serializable {


    private static String TAG = "MatusetransDetailsActivity";

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
    private TextView itemnum; //项目
    private TextView description; //描述
    private TextView tostoreloc; //目标库房
    private EditText quantity; //数量
    private EditText binnum;//目标货柜
    private EditText lotnum;//目标批次
    private TextView issuetype;//交易类型
    private TextView enterby;//输入人
    private TextView orderunit,orderunitdesc;//订购单位
    private TextView unitcost,poline;//单位成本
    private MATRECTRANS matrectrans;
    private Button back;
    private Button option;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private RelativeLayout relativeLayout;
    private EditText remark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matusetrans_details2);
        geiIntentData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void geiIntentData() {
        matrectrans = (MATRECTRANS)  getIntent().getExtras().get("matrectrans");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);
        itemnum = (TextView) findViewById(R.id.matusetrans_itemnum);
        description = (TextView) findViewById(R.id.matusetrans_description);
        tostoreloc = (TextView) findViewById(R.id.matusetrans_storeloc);
        quantity = (EditText) findViewById(R.id.matusetrans_positivequantity);
        issuetype = (TextView) findViewById(R.id.matusetrans_issuetype);
        enterby = (TextView) findViewById(R.id.matusetrans_enterby);
        orderunit = (TextView) findViewById(R.id.matusetrans_orderunit);
        unitcost = (TextView) findViewById(R.id.matusetrans_unitcost);
        binnum = (EditText) findViewById(R.id.matusetrans_binnum);
        lotnum = (EditText) findViewById(R.id.matusetrans_lotnum);
        back = (Button) findViewById(R.id.back);
        option = (Button) findViewById(R.id.option);
        relativeLayout = (RelativeLayout) findViewById(R.id.button_layout);
        remark = (EditText) findViewById(R.id.matusetrans_remark);
        poline = (TextView) findViewById(R.id.matusetrans_polinenum);
        orderunitdesc= (TextView) findViewById(R.id.matusetrans_orderunitdesc);
    }

    @Override
    protected void initView() {
        relativeLayout.setVisibility(View.VISIBLE);
        itemnum.setText(matrectrans.getITEMNUM());
        description.setText(matrectrans.getDESCRIPTION());
        tostoreloc.setText(matrectrans.getTOSTORELOC());
        quantity .setText(matrectrans.getRECEIPTQUANTITY());
        issuetype.setText(matrectrans.getISSUETYPE());
        enterby .setText(matrectrans.getENTERBY());
        orderunit.setText(matrectrans.getRECEIVEDUNIT());
        unitcost.setText(matrectrans.getUNITCOST());
        binnum .setText(matrectrans.getTOBIN());
        lotnum .setText(matrectrans.getTOLOT());
        remark.setText(matrectrans.getREMARK());
        poline.setText(matrectrans.getPOLINENUM());
        orderunitdesc.setText(matrectrans.getUNITDESC());
        backImageView.setBackgroundResource(R.drawable.ic_back);
        titleTextView.setText(R.string.receiveline);
        backImageView.setOnClickListener(backOnClickListener);
        back.setOnClickListener(backOnClickListener);
        option.setOnClickListener(optionOnClickListener);
    }
    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalDialog normalListDialog = new NormalDialog(MaterialRetreatingEntity.this);
            normalListDialog.title(getString(R.string.tip)).btnText(getString(R.string.cancel),getString(R.string.confirm));
            normalListDialog.content(getString(R.string.suretosave))//
                    .showAnim(mBasIn)//
                    .dismissAnim(mBasOut)//
                    .show();
            normalListDialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            normalListDialog.dismiss();
                        }
                    },
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            getChangeData();
                            if (matrectrans.getTOBIN()==null || "".equalsIgnoreCase(matrectrans.getTOBIN())){
                                Toast.makeText(MaterialRetreatingEntity.this, "The toBin can not be Empty",Toast.LENGTH_LONG).show();
                            }else {
                                Intent intent = getIntent();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("matrectrans",matrectrans);
                                intent.putExtras(bundle);
                                setResult(10,intent);
                                normalListDialog.dismiss();
                                finish();
                            }

                        }
                    });
        }
    };
    private void getChangeData(){
        matrectrans.setTOBIN(binnum.getText().toString());
        matrectrans.setTOLOT(lotnum.getText().toString());
        matrectrans.setREMARK(remark.getText().toString());
        matrectrans.setQUANTITY(quantity.getText().toString());
        matrectrans.setRECEIPTQUANTITY(quantity.getText().toString());
        matrectrans.setENTERBY(enterby.getText().toString());
        matrectrans.setRECEIVEDUNIT(orderunit.getText().toString());
        matrectrans.setQTYREQUESTED(quantity.getText().toString());
    }
}
