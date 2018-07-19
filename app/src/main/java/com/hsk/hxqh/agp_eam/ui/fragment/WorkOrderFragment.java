package com.hsk.hxqh.agp_eam.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.ui.activity.WorkOederListActivity;

/**
 * Created by Administrator on 2017/2/27.
 */

public class WorkOrderFragment extends BaseFragment{
    private static final String TAG = "WorkOrderFragment";

    private RelativeLayout cmLayout;
    private RelativeLayout pmLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workorder, container,
                false);

        findByIdView(view);
        initView();
        return view;
    }

    /**
     * 初始化界面组件*
     */
    private void findByIdView(View view) {
        cmLayout = (RelativeLayout) view.findViewById(R.id.work_cm_layout);
        pmLayout = (RelativeLayout) view.findViewById(R.id.work_pm_layout);
    }


    /**
     * 设置事件监听*
     */
    private void initView() {
        cmLayout.setOnClickListener(cmLayoutOnClickListener);
        pmLayout.setOnClickListener(pmLayoutOnClickListener);
    }

    private View.OnClickListener cmLayoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), WorkOederListActivity.class);
            intent.putExtra("type","CM");
            startActivity(intent);
        }
    };

    private View.OnClickListener pmLayoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), WorkOederListActivity.class);
            intent.putExtra("type","PM");
            startActivity(intent);
        }
    };

}
