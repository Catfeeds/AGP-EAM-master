package com.hsk.hxqh.agp_eam.adpter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.MATUSETRANS;
import com.hsk.hxqh.agp_eam.model.WPMATERIAL;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 */
public class MatusetransAdapter extends BaseQuickAdapter<MATUSETRANS> {
    private int position;
    public MatusetransAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, MATUSETRANS item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.matusetrans_actualstaskid, item.getACTUALSTASKID());
        helper.setText(R.id.matusetrans_itemnum, item.getITEMNUM());
        helper.setText(R.id.matusetrans_description, item.getDESCRIPTION());
    }


}
