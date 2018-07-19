package com.hsk.hxqh.agp_eam.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.model.INVBALANCES;
import com.hsk.hxqh.agp_eam.model.WPLABOR;


/**
 * 库存余量详情
 */
public class InvbalancesDetailsActivity extends BaseActivity {
    private static String TAG = "InvbalancesDetailsActivity";

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    /**
     * 字段显示
     **/
    private TextView binnum; //货柜
    private EditText lotnum; //批次
    private EditText curbal; //当前余量
    private TextView stagedcurbal;//暂存余量
    private TextView physcnt;//实际库存量
    private TextView physcntdate;//实际盘点日期
    private CheckBox reconciled;//已调整

    /**
     * 任务
     **/
    private INVBALANCES invbalances;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invbalances_details);
        geiIntentData();
        findViewById();
        initView();
    }

    private void geiIntentData() {
        invbalances = (INVBALANCES) getIntent().getSerializableExtra("invbalances");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.menu_id);
        titleTextView = (TextView) findViewById(R.id.menu_title);

        binnum = (TextView) findViewById(R.id.invbalances_binnum);
        lotnum = (EditText) findViewById(R.id.invbalances_lotnum);
        curbal = (EditText) findViewById(R.id.invbalances_curbal);
        stagedcurbal = (TextView) findViewById(R.id.invbalances_stagedcurbal);
        physcnt = (TextView) findViewById(R.id.invbalances_physcnt);
        physcntdate = (TextView) findViewById(R.id.invbalances_physcntdate);
        reconciled = (CheckBox) findViewById(R.id.invbalances_reconciled);
    }


    @Override
    protected void initView() {
        backImageView.setBackgroundResource(R.drawable.ic_back);
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.work_wplabor);

        if (invbalances != null) {
            binnum.setText(invbalances.BINNUM);
            lotnum.setText(invbalances.LOTNUM);
            curbal.setText(invbalances.CURBAL);
            stagedcurbal.setText(invbalances.STAGEDCURBAL);
            physcnt.setText(invbalances.PHYSCNT);
            physcntdate.setText(invbalances.PHYSCNTDATE);
            reconciled.setChecked(invbalances.RECONCILED.equals("Y"));

        }


    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


}
