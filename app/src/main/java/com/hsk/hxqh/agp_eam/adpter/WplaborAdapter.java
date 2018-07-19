package com.hsk.hxqh.agp_eam.adpter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.WOACTIVITY;
import com.hsk.hxqh.agp_eam.model.WPLABOR;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 */
public class WplaborAdapter extends BaseQuickAdapter<WPLABOR> {
    private int position;
    public WplaborAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, WPLABOR item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.wplabor_taskid, item.getTASKID());
        helper.setText(R.id.wplabor_laborcode, item.getLABORCODE());
        helper.setText(R.id.wplabor_quantity, item.getQUANTITY());
    }


}
