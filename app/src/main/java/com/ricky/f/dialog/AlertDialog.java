package com.ricky.f.dialog;

import android.content.Context;

import com.android.utils.StringUtil;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Deak on 16/12/12.
 */

public class AlertDialog {
    private Context mContext;
    SweetAlertDialog mDialog;

    public AlertDialog(Context context) {
        this.mContext=context;
    }

    public void showWarningAlertDialog(String title, String contentText, String confirmText, String cancelText, SweetAlertDialog.OnSweetClickListener onConfirmClickListener, SweetAlertDialog.OnSweetClickListener onCancelClickListener){
       show(SweetAlertDialog.WARNING_TYPE, title, contentText, confirmText, cancelText, 0, onConfirmClickListener, onCancelClickListener);
    }

    private void show(int alertType, String title, String contentText, String confirmText, String cancelText, int customImg, SweetAlertDialog.OnSweetClickListener onConfirmClickListener, SweetAlertDialog.OnSweetClickListener onCancelClickListener){
        if(mDialog == null){
            mDialog = new SweetAlertDialog(mContext, alertType);
        } else {
            mDialog.changeAlertType(alertType);
        }
        mDialog.setCancelable(false);

        if(!StringUtil.isEmpty(title)){
            mDialog.setTitleText(title);
        }
        if(!StringUtil.isEmpty(contentText)){
            mDialog.setContentText(contentText);
        }
        if(!StringUtil.isEmpty(confirmText)){
            mDialog.setConfirmText(confirmText);
        }
        if(!StringUtil.isEmpty(confirmText)){
            mDialog.setConfirmText(confirmText);
        }
        if(!StringUtil.isEmpty(cancelText)){
            mDialog.setCancelText(cancelText);
            mDialog.showCancelButton(true);
        } else {
            mDialog.showCancelButton(false);
        }
        if(customImg > 0){
            mDialog.setCustomImage(customImg);
        }
        if(onConfirmClickListener != null){
            mDialog.setConfirmClickListener(onConfirmClickListener);
        }
        if(onCancelClickListener != null){
            mDialog.setCancelClickListener(onCancelClickListener);
        }
        mDialog.show();
    }

    public void dismiss(){
        if(mDialog == null)return;
        mDialog.dismiss();
        mDialog=null;
    }
}
