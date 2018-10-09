package com.hsk.hxqh.agp_eam.adpter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.ASSET;
import com.hsk.hxqh.agp_eam.model.WORKORDER;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 */
public class WorkOrderAdapter extends BaseQuickAdapter<WORKORDER> {
    private int position;
    public WorkOrderAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, WORKORDER item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.item_num_text, item.getWONUM());
        helper.setText(R.id.item_desc_text, item.getDESCRIPTION());
        helper.setText(R.id.item_location_text, item.getUDTEMPMATERIAL());
        helper.setText(R.id.item_location_desc_text,item.getWORKORDERUDTDES());
        helper.setText(R.id.item_udstatus_text, item.getUDSTATUS());
        helper.setText(R.id.item_createdate_text,item.getCREATEDATE());
    }


}
