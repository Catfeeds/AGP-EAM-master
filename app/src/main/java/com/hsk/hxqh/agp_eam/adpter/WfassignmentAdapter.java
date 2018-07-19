package com.hsk.hxqh.agp_eam.adpter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.ASSET;
import com.hsk.hxqh.agp_eam.model.WFASSIGNMENT;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 */
public class WfassignmentAdapter extends BaseQuickAdapter<WFASSIGNMENT> {
    private int position;
    public WfassignmentAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, WFASSIGNMENT item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.item_num_text, item.getWFASSIGNMENTID());
        helper.setText(R.id.item_desc_text, item.getDESCRIPTION());
        helper.setText(R.id.wfm_assigncode_text, item.getASSIGNCODE());
        helper.setText(R.id.wfm_startdate_text, item.getSTARTDATE());

        switch (helper.getPosition()%5){
            case 0:
                helper.setBackgroundRes(R.id.item_num_text,R.drawable.design_0_point);
                break;
            case 1:
                helper.setBackgroundRes(R.id.item_num_text,R.drawable.design_1_point);
                break;
            case 2:
                helper.setBackgroundRes(R.id.item_num_text,R.drawable.design_2_point);
                break;
            case 3:
                helper.setBackgroundRes(R.id.item_num_text,R.drawable.design_3_point);
                break;
            case 4:
                helper.setBackgroundRes(R.id.item_num_text,R.drawable.design_4_point);
                break;
        }

    }


}
