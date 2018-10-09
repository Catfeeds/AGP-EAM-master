package com.hsk.hxqh.agp_eam.adpter;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.Log;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.INVENTORY;
import com.hsk.hxqh.agp_eam.model.UDSTOCKLINE;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 */
public class UdstockLineAdapter extends BaseQuickAdapter<UDSTOCKLINE> {
    private int position;
    private Object o = new Object();
    public UdstockLineAdapter(Context context, int layoutResId, List data) {
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
    protected  void convert(BaseViewHolder helper, UDSTOCKLINE item) {
        CardView cardView = helper.getView(R.id.card_container);
/*            if (item.getISCHECK().equalsIgnoreCase("Y")) {
                if(Double.parseDouble(item.getDIFFERENCE())!=0){
                    cardView.setCardBackgroundColor(Color.YELLOW);
                }else{
                    cardView.setCardBackgroundColor(Color.GREEN);
                }
            }else {
                cardView.setCardBackgroundColor(Color.WHITE);
            }*/
        if (item.getDIFFERENCE() == null){
            cardView.setCardBackgroundColor(Color.WHITE);
        }else {
            if(Double.parseDouble(item.getDIFFERENCE())!=0){
                cardView.setCardBackgroundColor(Color.YELLOW);
            }else{
                cardView.setCardBackgroundColor(Color.GREEN);
            }
        }

       //cardView.setCardBackgroundColor(R.color.blue);
        helper.setText(R.id.udstockline_sn_text, item.getSN()+"");
        helper.setText(R.id.udstockline_udstocklineitedes_text, item.getUDSTOCKLINEITEDES());
        helper.setText(R.id.udstockline_quantity1_text, item.getQUANTITY1()+"");
        helper.setText(R.id.udstockline_quantity2_text, item.getQUANTITY2()+"");
        helper.setText(R.id.udstockline_itemnum_text,item.getITEMNUM());
        helper.setText(R.id.udstockline_toLot_text,item.getLOTNUM());
    }


}
