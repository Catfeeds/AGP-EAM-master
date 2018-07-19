package com.hsk.hxqh.agp_eam.unit;

import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wxs on 2017/4/27.
 */
public class TestUtil {


    /**
     * 空验证
     */
    public static boolean isNotNull(Object object) {
        if(null != object) {
            if (object instanceof String) {
                return !"".equals(((String) object).trim());
            }else if(object instanceof List) {
                return ((List) object).size() > 0;
            }else {
                if(null != object) {
                    return true;
                }
            }
        }else {
            return false;
        }
        return false;
    }

    public static void setWidth(TextView textView) {
        String text = textView.getText().toString();
        int textLength1 = text.length();
        int textLength2 = textLength1 * 40;
        ViewGroup.LayoutParams linearLayout = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.width = textLength2;
        linearLayout.height = (int) (Math.ceil(textLength1/6) * 50);
        textView.setLayoutParams(linearLayout);
    }
}
