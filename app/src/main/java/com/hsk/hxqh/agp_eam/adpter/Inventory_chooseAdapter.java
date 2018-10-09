package com.hsk.hxqh.agp_eam.adpter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.INVENTORY;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 */
public class Inventory_chooseAdapter extends BaseQuickAdapter<INVENTORY> {
    private int position;
    private static boolean showcheckbox = false;

    public boolean isShowcheckbox() {
        return showcheckbox;
    }

    public void setShowcheckbox(boolean showcheckbox) {
        this.showcheckbox = showcheckbox;
    }

    public Inventory_chooseAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, final INVENTORY item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.item_num_text, item.getITEMNUM());
        helper.setText(R.id.item_desc_text, item.getITEMNUM_DEC());
        final CheckBox checkBox = helper.getView(R.id.check_box_id);
        int thisposition = helper.getLayoutPosition();
        checkBox.setChecked(item.isCheckBox());
        if (showcheckbox){
            checkBox.setVisibility(View.VISIBLE);
        }else {
            checkBox.setVisibility(View.GONE);
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isCheckBox()){
                   item.setCheckBox(false);
                }else {
                    item.setCheckBox(true);
                }
            }
        });

    }
}
