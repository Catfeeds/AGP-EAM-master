package com.hsk.hxqh.agp_eam.adpter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.ASSET;
import com.hsk.hxqh.agp_eam.model.ASSET_WORKORDER;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 */
public class Asset_workAdapter extends BaseQuickAdapter<ASSET_WORKORDER> {
    private int position;
    public Asset_workAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, ASSET_WORKORDER item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.asset_wonum_text, item.getWONUM());
        helper.setText(R.id.asset_description_text, item.getDESCRIPTION());
        helper.setText(R.id.asset_status_text, item.getSTATUS());
        helper.setText(R.id.asset_statusdate_text, item.getSTATUSDATE());
        helper.setText(R.id.asset_actstart_text, item.getACTSTART());
        helper.setText(R.id.asset_actfinish_text, item.getACTFINISH());
    }


}
