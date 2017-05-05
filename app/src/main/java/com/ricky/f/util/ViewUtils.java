package com.ricky.f.util;

import android.widget.Button;
import android.widget.EditText;
import com.ricky.f.R;

/**
 * Created by Deak on 16/12/12.
 */

public class ViewUtils {
    public static void setEditViewText(EditText edtView, String text){
        if(edtView == null)return;
        edtView.setText(text);
        edtView.setSelection(text.length());
    }

    /**
     *
     * Date:2014-11-21
     * Description: 处理点击背景
     * @param pClickBg
     * @param pDoNotClickBg
     * @return void
     */
    public static void setBtnClickStatus(Button pBtn, boolean isClick, int pClickBg, int pDoNotClickBg) {
        if(pBtn==null)return;
        if(!isClick) {
            pBtn.setEnabled(false);
            pBtn.setBackgroundResource(pDoNotClickBg);
            pBtn.setTextColor(ResourceUtils.getColor(R.color.color_60FFFFFF));
        } else {
            pBtn.setEnabled(true);
            pBtn.setTextColor(ResourceUtils.getColor(R.color.white));
            pBtn.setBackgroundResource(pClickBg);
        }
    }
}
