package com.hsk.hxqh.agp_eam.adpter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.ASSET_WORKORDER;
import com.hsk.hxqh.agp_eam.model.WOACTIVITY;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 */
public class WoactivityAdapter extends BaseQuickAdapter<WOACTIVITY> {
    private int position;
    public WoactivityAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, WOACTIVITY item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.work_taskid_text, item.getWOSEQUENCE());
        helper.setText(R.id.item_desc_text, item.getDESCRIPTION());
//        helper.setText(R.id.asset_statusdate_text, item.getSTATUSDATE());
//        helper.setText(R.id.asset_actstart_text, item.getACTSTART());
//        helper.setText(R.id.asset_actfinish_text, item.getACTFINISH());
    }


}
