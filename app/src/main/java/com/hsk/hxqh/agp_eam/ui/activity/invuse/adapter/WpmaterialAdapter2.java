package com.hsk.hxqh.agp_eam.ui.activity.invuse.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.adpter.BaseQuickAdapter;
import com.hsk.hxqh.agp_eam.model.WOACTIVITY;
import com.hsk.hxqh.agp_eam.model.WPMATERIAL;
import com.hsk.hxqh.agp_eam.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 */
public class WpmaterialAdapter2 extends BaseQuickAdapter<WPMATERIAL> {
    private int position;
    public WpmaterialAdapter2(Context context, int layoutResId, List data) {
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
    protected void convert(final BaseViewHolder helper, WPMATERIAL item) {
        if (item.getFLAG()!= null&&item.getFLAG().equalsIgnoreCase("D")){
            helper.setBackgroundRes(R.id.drop_down,R.drawable.ic_delete);
        }
        helper.setVisible(R.id.taskid_layout,false);
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.wpmaterial_itemnum_id, item.getITEMNUM());
        helper.setText(R.id.wpmaterial_description_id, item.getDESCRIPTION());
        helper.setText(R.id.wpmaterial_location_id,item.getLOCATION());
        helper.setText(R.id.wpmaterial_quantity_id,item.getITEMQTY());
        helper.setText(R.id.wpmaterial_itemnum, item.getITEMNUM());
        helper.setText(R.id.wpmaterial_description, item.getDESCRIPTION());
        helper.setText(R.id.wpmaterial_location,item.getLOCATION());
        helper.setText(R.id.wpmaterial_itemqty,item.getITEMQTY());
        helper.setText(R.id.wpmaterial_taskid,item.getTASKID());
        helper.setText(R.id.wpmaterial_vendor,item.getVENDOR());
        helper.setText(R.id.wpmaterial_requestby,item.getREQUESTBY());
        helper.setText(R.id.wpmaterial_requiredate,item.getREQUIREDATE());
        helper.setText(R.id.wpmaterial_issueto,item.getISSUETO());
        helper.setText(R.id.wpmaterial_unitcost,item.getUNITCOST());
        helper.setText(R.id.wpmaterial_linecost,item.getLINECOST());
        helper.setVisible(R.id.wpmaterial_all,false);
        helper.setVisible(R.id.title_id,false);
        helper.setVisible(R.id.button_layout_id,false);
        if (item.getFLAG()!= null&&!item.getFLAG().equalsIgnoreCase("D")){
            helper.setOnClickListener(R.id.drop_down,new OnItemChildClickListener(){
                public  boolean flag = false;
                @Override
                public void onClick(View v) {
                    if (!flag){
                        flag = true;
                        helper.setVisible(R.id.wpmaterial_all,flag);
                        helper.setBackgroundRes(R.id.drop_down,R.drawable.ic_pack_up);
                    }else{
                        flag = false;
                        helper.setVisible(R.id.wpmaterial_all,flag);
                        helper.setBackgroundRes(R.id.drop_down,R.drawable.ic_drop_down);

                    }

                }
            });
        }

    }


}

