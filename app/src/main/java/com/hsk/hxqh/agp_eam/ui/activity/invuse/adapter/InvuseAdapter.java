package com.hsk.hxqh.agp_eam.ui.activity.invuse.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.adpter.BaseQuickAdapter;
import com.hsk.hxqh.agp_eam.model.INVUSEEntity;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 */
public class InvuseAdapter extends BaseQuickAdapter<INVUSEEntity> {
    private int position;
    public InvuseAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, INVUSEEntity item) {
        CardView cardView = helper.getView(R.id.card_container);
        TextView INVUSELOCATIONSDES = helper.getView(R.id.item_status_title);
        INVUSELOCATIONSDES.setText(R.string.inventory_location_dec);
        helper.setText(R.id.INVUSENUM, item.getINVUSENUM());
        TextView desc = helper.getView(R.id.item_desc_title);
        desc.setText(R.string.udstock_description);
        helper.setText(R.id.DESCRIPTION, item.getDESCRIPTION());
        helper.setText(R.id.FROMSTORELOC, item.getFROMSTORELOC());
        helper.setText(R.id.INVUSELOCATIONSDES, item.getINVUSELOCATIONSDES());
        helper.setVisible(R.id.item_location_desc_title,false);
        helper.setVisible(R.id.item_location_desc_text,false);
    }


}
