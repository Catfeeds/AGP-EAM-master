package com.hsk.hxqh.agp_eam.adpter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.ASSET;
import com.hsk.hxqh.agp_eam.model.PO;
import com.hsk.hxqh.agp_eam.ui.activity.BaseActivity;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;

/**
 * Created by zzw on 2018/6/11.
 */

public class POAdapter extends BaseQuickAdapter<PO> {
    private int position;
    public POAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, PO item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.po_num_text, item.getPONUM());
        helper.setText(R.id.po_desc_text,item.getDESCRIPTION());
        helper.setText(R.id.po_statusdesc_text,item.getSTATUS());
        helper.setText(R.id.po_recdesc_text,item.getRECEIPTS());



    }
}
