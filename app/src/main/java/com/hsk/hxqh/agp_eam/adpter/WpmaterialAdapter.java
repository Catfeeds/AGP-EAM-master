package com.hsk.hxqh.agp_eam.adpter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.WOACTIVITY;
import com.hsk.hxqh.agp_eam.model.WPMATERIAL;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 */
public class WpmaterialAdapter extends BaseQuickAdapter<WPMATERIAL> {
    private int position;
    public WpmaterialAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, WPMATERIAL item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.wpmaterial_taskid, item.getTASKID());
        helper.setText(R.id.wpmaterial_itemnum, item.getITEMNUM());
        helper.setText(R.id.wpmaterial_description, item.getDESCRIPTION());
//        helper.setText(R.id.asset_statusdate_text, item.getSTATUSDATE());
//        helper.setText(R.id.asset_actstart_text, item.getACTSTART());
//        helper.setText(R.id.asset_actfinish_text, item.getACTFINISH());
    }


}
