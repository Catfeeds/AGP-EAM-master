package com.hsk.hxqh.agp_eam.ui.activity.invuse.adapter;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.adpter.BaseQuickAdapter;
import com.hsk.hxqh.agp_eam.adpter.Inventory_chooseAdapter;
import com.hsk.hxqh.agp_eam.config.Constants;
import com.hsk.hxqh.agp_eam.model.INVBALANCES;
import com.hsk.hxqh.agp_eam.model.INVENTORY;
import com.hsk.hxqh.agp_eam.model.INVRESERVE;
import com.hsk.hxqh.agp_eam.model.INVUSELINE;
import com.hsk.hxqh.agp_eam.ui.activity.BaseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.InventoryListActivity;
import com.hsk.hxqh.agp_eam.ui.activity.WorkOederListActivity;
import com.hsk.hxqh.agp_eam.ui.activity.invuse.InvuselineAddNewActivity;
import com.hsk.hxqh.agp_eam.ui.activity.invuse.InvusemiAddNewActivity;
import com.hsk.hxqh.agp_eam.ui.activity.invuse.MatWpmaterialDetailsActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Asset_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.INVBALANCES_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.INVRESERVE_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Inventory_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Item_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.activity.option.Location_chooseActivity;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;
import com.hsk.hxqh.agp_eam.unit.ArrayUtil;
import com.hsk.hxqh.agp_eam.unit.DateTimeSelect;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzw on 2018/5/25.
 */

public class InvuseLineAddAdaper extends BaseQuickAdapter<INVUSELINE> {
    private String locationnum;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BaseAnimatorSet getmBasIn() {
        return mBasIn;
    }

    public void setmBasIn(BaseAnimatorSet mBasIn) {
        this.mBasIn = mBasIn;
    }

    public BaseAnimatorSet getmBasOut() {
        return mBasOut;
    }

    public void setmBasOut(BaseAnimatorSet mBasOut) {
        this.mBasOut = mBasOut;
    }


    public String getLocationnum() {
        return locationnum;
    }

    public void setLocationnum(String locationnum) {
        this.locationnum = locationnum;
    }

    private int position;
    private InvusemiAddNewActivity baseActivity;
    public InvuseLineAddAdaper(Context context, int layoutResId, List data,InvusemiAddNewActivity baseActivity) {
        super(context, layoutResId, data);
        this.baseActivity = baseActivity;
    }
    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        position = index;
        if (index < 5)
            anim.setStartDelay(index * 150);
    }
    @Override
    protected void convert(final BaseViewHolder helper, final INVUSELINE item) {
        if (item.getFLAG()!=null && item.getFLAG().equalsIgnoreCase("D")){
            helper.setVisible(R.id.invuseline_detail_linearlayout,false);
            helper.setBackgroundRes(R.id.drop_down,R.drawable.ic_delete);
        }
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.invuseline_desc_id,item.getDESCRIPTION());
        helper.setText(R.id.invuseline_itemnum_id,item.getITEMNUM());
        helper.setText(R.id.invuseline_frombin_id,item.getFROMBIN());
        helper.setText(R.id.invuseline_fromloc_id,item.getFROMLOT());
        helper.setText(R.id.invuseline_quantity_id,item.getQUANTITY());
        helper.setText(R.id.invuseline_wonum_id,item.getWONUM());
        helper.setText(R.id.invuseline_assetnum_id,item.getASSETNUM());
        helper.setText(R.id.invuseline_location_id,item.getLOCATION());
        helper.setText(R.id.invuseline_actualdate_id,item.getACTUALDATE());
        helper.setText(R.id.invuseline_conversion_id,item.getCONVERSION());
        helper.setText(R.id.invuseline_tobin_id,item.getTOBIN());
        helper.setText(R.id.invuseline_toloc_id,item.getTOLOT());
        EditText desc = (EditText)helper.getView(R.id.invuseline_desc_id);
        final EditText quantity = (EditText)helper.getView(R.id.invuseline_quantity_id);
        final EditText remark = (EditText) helper.getView(R.id.invuseline_remark_id);
        TextView itemnum = (TextView)helper.getView(R.id.invuseline_itemnum_id);
        final TextView frombin = (TextView)helper.getView(R.id.invuseline_frombin_id);
        TextView fromloc = (TextView)helper.getView(R.id.invuseline_fromloc_id);
        TextView wonum = (TextView)helper.getView(R.id.invuseline_wonum_id);
        TextView assetnum = (TextView)helper.getView(R.id.invuseline_assetnum_id);
        final TextView location = (TextView)helper.getView(R.id.invuseline_location_id);
        TextView actualdate = (TextView) helper.getView(R.id.invuseline_actualdate_id);
        final EditText conversion = (EditText) helper.getView(R.id.invuseline_conversion_id);
        conversion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    item.setCONVERSION(conversion.getText().toString());
                }
            }
        });
        final EditText toBin = (EditText) helper.getView(R.id.invuseline_tobin_id);
        final EditText toLot = (EditText) helper.getView(R.id.invuseline_toloc_id);
        toBin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    item.setTOBIN(toBin.getText().toString());
                }

            }
        });
        toLot.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    item.setTOLOT(toLot.getText().toString());

                }
            }
        });
        helper.setVisible(R.id.the_hidend_id,false);
        helper.setVisible(R.id.title_id,false);
        if (type.equalsIgnoreCase("MT")){
            helper.setVisible(R.id.invuseline_tobin_layout,true);
            helper.setVisible(R.id.conversion_layout_id,true);
        }

        itemnum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(baseActivity, Inventory_chooseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("location",locationnum);
                intent.putExtras(bundle);
                baseActivity.startActivityForResult(intent,item.getINDEX());
            }
        });
        frombin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(baseActivity, INVBALANCES_chooseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("invuseline",item);
                intent.putExtras(bundle);
                baseActivity.startActivityForResult(intent,item.getINDEX());
            }
        });
        fromloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(baseActivity, INVBALANCES_chooseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("invuseline",item);
                intent.putExtras(bundle);
                baseActivity.startActivityForResult(intent,item.getINDEX());
            }
        });
        assetnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(baseActivity, Asset_chooseActivity.class);
                baseActivity.startActivityForResult(intent,item.getINDEX());
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(baseActivity, Location_chooseActivity.class);
                baseActivity.startActivityForResult(intent,item.getINDEX());
            }
        });
        wonum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(baseActivity, WorkOederListActivity.class);
                //baseActivity.startActivityForResult(intent,item.getINDEX());
            }
        });
       quantity.setFocusable(true);
       quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               if (!hasFocus){
                   item.setQUANTITY(quantity.getText().toString());
               }
           }
       });
        if (item.getFLAG()!=null && !item.getFLAG().equalsIgnoreCase("D")) {
            helper.setOnClickListener(R.id.drop_down, new OnItemChildClickListener() {
                public boolean flag = true;

                @Override
                public void onClick(View v) {
                    if (flag) {
                        flag = false;
                        helper.setVisible(R.id.invuseline_detail_linearlayout, flag);
                        helper.setBackgroundRes(R.id.drop_down, R.drawable.ic_drop_down);
                    } else {
                        flag = true;
                        helper.setVisible(R.id.invuseline_detail_linearlayout, flag);
                        helper.setBackgroundRes(R.id.drop_down, R.drawable.ic_pack_up);

                    }
                }
            });
        }
        actualdate.setOnClickListener(new DateTimeOnClickListener(actualdate));
    }

    private class DateTimeOnClickListener implements View.OnClickListener {
        TextView textView;

        private DateTimeOnClickListener(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onClick(View view) {
            new DateTimeSelect(baseActivity, textView).showDialog();
        }
    }
}
