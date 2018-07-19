package com.hsk.hxqh.agp_eam.adpter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.ITEM;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 */
public class ItemAdapter extends BaseQuickAdapter<ITEM> {
    private int position;
    public ItemAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, ITEM item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.item_num_text, item.getITEMNUM());
        helper.setText(R.id.item_desc_text, item.getDESCRIPTION());
        helper.setText(R.id.item_lottype_text, item.getIN26());
        helper.setText(R.id.item_in19_text, item.getIN27());
//        helper.setText(R.id.item_udmodule_text, item.getUDMODULE());

        /*switch (helper.getPosition()%5){
            case 0:
                helper.setBackgroundRes(R.id.item_num_text,R.color.background_blue);
                break;
            case 1:
                helper.setBackgroundRes(R.id.item_num_text,R.color.green);
                break;
            case 2:
                helper.setBackgroundRes(R.id.item_num_text,R.color.darkred);
                break;
            case 3:
                helper.setBackgroundRes(R.id.item_num_text,R.color.yellow);
                break;
            case 4:
                helper.setBackgroundRes(R.id.item_num_text,R.color.pink);
                break;
        }*/

    }


}
