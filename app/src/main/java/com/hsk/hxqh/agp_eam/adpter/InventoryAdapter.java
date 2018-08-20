package com.hsk.hxqh.agp_eam.adpter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.ASSET;
import com.hsk.hxqh.agp_eam.model.INVENTORY;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 */
public class InventoryAdapter extends BaseQuickAdapter<INVENTORY> {
    private int position;
    public InventoryAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, INVENTORY item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.INVUSENUM, item.getITEMNUM());
        helper.setText(R.id.DESCRIPTION, item.getITEMNUM_DEC());
        helper.setText(R.id.FROMSTORELOC, item.getLOCATION());
        helper.setText(R.id.item_location_desc_text,item.getLOCATION_DEC());
        helper.setText(R.id.INVUSELOCATIONSDES, item.getCURBALTOTAL());
    }


}
