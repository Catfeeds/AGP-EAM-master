package com.hsk.hxqh.agp_eam.adpter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.INVENTORY;
import com.hsk.hxqh.agp_eam.model.SPAREPART;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 */
public class Item_InventoryAdapter extends BaseQuickAdapter<INVENTORY> {
    private int position;
    public Item_InventoryAdapter(Context context, int layoutResId, List data) {
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
        helper.setText(R.id.inventory_location_text, item.getLOCATION());
        helper.setText(R.id.inventory_curbaltotal_text, item.getCURBALTOTAL());
        helper.setText(R.id.inventory_binnum_text, item.getBINNUM());
        helper.setText(R.id.inventory_status_text, item.getSTATUS());
    }


}
