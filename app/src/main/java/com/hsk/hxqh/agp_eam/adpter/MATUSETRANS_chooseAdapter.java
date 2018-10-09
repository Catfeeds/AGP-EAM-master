package com.hsk.hxqh.agp_eam.adpter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.INVUSELINE;
import com.hsk.hxqh.agp_eam.model.MATUSETRANS;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 */
public class MATUSETRANS_chooseAdapter extends BaseQuickAdapter<MATUSETRANS> {
    private int position;
    private static boolean showcheckbox;

    public  boolean isShowcheckbox() {
        return showcheckbox;
    }

    public  void setShowcheckbox(boolean showcheckbox) {
        MATUSETRANS_chooseAdapter.showcheckbox = showcheckbox;
    }

    public MATUSETRANS_chooseAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, final MATUSETRANS item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.item_num_text, item.getITEMNUM());
        helper.setText(R.id.item_desc_text, item.getDESCRIPTION());
//        helper.setText(R.id.item_location_text, item.getLOCATION());
//        helper.setText(R.id.item_locdesc_text, item.getLOCDESC());
//        helper.setText(R.id.item_udmodule_text, item.getUDMODULE());
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
