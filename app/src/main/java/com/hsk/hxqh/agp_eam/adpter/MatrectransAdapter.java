package com.hsk.hxqh.agp_eam.adpter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.ASSET;
import com.hsk.hxqh.agp_eam.model.MATRECTRANS;
import com.hsk.hxqh.agp_eam.ui.activity.PodetailActivity;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 */
public class MatrectransAdapter extends BaseQuickAdapter<MATRECTRANS> {
    private int position;
    public MatrectransAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, final MATRECTRANS item) {
        TextView location = (TextView) helper.getView(R.id.item_location_title);
        location.setText(R.string.item_quantity1);

        TextView status = (TextView) helper.getView(R.id.item_status_title);
        status.setText(R.string.wpmaterial_unitcost);
        helper.setText(R.id.invuseline_text_id,item.getITEMNUM());
        helper.setText(R.id.invuseline_desc_id,item.getDESCRIPTION());
        helper.setText(R.id.invuse_line_FROMBIN_id,item.getRECEIPTQUANTITY());
        helper.setText(R.id.invuseline_FROMLOT_id,item.getUNITCOST());
        helper.setVisible(R.id.MatrectransAdapter_id,true);
        helper.setText(R.id.invuseline_tobin_id,item.getTOBIN());

        final EditText editText = (EditText) helper.getView(R.id.invuse_line_FROMBIN_id);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    item.setRECEIPTQUANTITY(editText.getText().toString());
                    item.setQUANTITY(editText.getText().toString());
                    item.setQTYREQUESTED(editText.getText().toString());
                }else {
                    item.setRECEIPTQUANTITY(editText.getText().toString());
                    item.setQUANTITY(editText.getText().toString());
                    item.setQTYREQUESTED(editText.getText().toString());
                }
                ((PodetailActivity)mContext).changegoolsDate();
            }
        });
        editText.clearFocus();
        final EditText toBin = (EditText) helper.getView(R.id.invuseline_tobin_id);
        toBin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    item.setTOBIN(toBin.getText().toString());
                }else {
                    item.setTOBIN(toBin.getText().toString());
                }
            }
        });
        TextView textView = new TextView(mContext);
        helper.setBackgroundRes(R.id.drop_down,R.drawable.line);

    }


}
