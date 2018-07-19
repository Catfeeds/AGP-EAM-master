package com.hsk.hxqh.agp_eam.adpter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.INVUSELINE;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;

/**
 * Created by zzw on 2018/5/25.
 */

public class InvuseLineAdaper extends BaseQuickAdapter<INVUSELINE> {
    private int position;
    public InvuseLineAdaper(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
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
        CardView cardView = helper.getView(R.id.card_container);
        if (item.getTYPE().equalsIgnoreCase("delete")){
         helper.setBackgroundRes(R.id.drop_down,R.drawable.ic_delete);
            cardView.setClickable(false);
        }
        helper.setText(R.id.invuseline_desc_id,item.getDESCRIPTION());
        helper.setText(R.id.invuseline_text_id,item.getITEMNUM());
        helper.setText(R.id.invuseline_description_id,item.getDESCRIPTION());
        helper.setText(R.id.invuseline_itemnum_id,item.getITEMNUM());
        helper.setText(R.id.invuse_line_FROMBIN_id,item.getFROMBIN());
        helper.setText(R.id.invuseline_FROMLOT_id,item.getFROMLOT());
        helper.setText(R.id.invuseline_frombin_id,item.getFROMBIN());
        helper.setText(R.id.invuseline_fromloc_id,item.getFROMLOT());
        helper.setText(R.id.usage_type_id,item.getUSETYPE());
        helper.setText(R.id.invuseline_quantity_id,item.getQUANTITY());
        helper.setText(R.id.invuseline_iss_id,item.getINVMEASUREMENT());
        helper.setText(R.id.invuseline_unitdes_id,item.getINVMEASUREMENT());
        helper.setText(R.id.invuseline_displayunitcost_id,item.getDISPLAYUNITCOST());
        helper.setText(R.id.invuseline_displaylinecost_id,item.getDISPLAYLINECOST());
        helper.setText(R.id.invuseline_wonum_id,item.getWONUM());
        helper.setText(R.id.invuseline_assetnum_id,item.getASSETNUM());
        helper.setText(R.id.invuseline_location_id,item.getLOCATION());
        helper.setText(R.id.invuseline_enterby_id,item.getENTERBY());
        helper.setText(R.id.invuseline_remark_id,item.getREMARK());
        helper.setText(R.id.invuseline_actualdate_id,item.getACTUALDATE());
        if (!item.getTYPE().equalsIgnoreCase("delete")){
            helper.setOnClickListener(R.id.drop_down,new OnItemChildClickListener(){
                public  boolean flag = false;
                @Override
                public void onClick(View v) {
                    if (!flag){
                        flag = true;
                        helper.setVisible(R.id.invuseline_detail_linearlayout,flag);
                        helper.setBackgroundRes(R.id.drop_down,R.drawable.ic_pack_up);
                    }else{
                        flag = false;
                        helper.setVisible(R.id.invuseline_detail_linearlayout,flag);
                        helper.setBackgroundRes(R.id.drop_down,R.drawable.ic_drop_down);

                    }

                }
            });
        }
    }
}
