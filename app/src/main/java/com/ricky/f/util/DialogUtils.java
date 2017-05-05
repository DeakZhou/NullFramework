package com.ricky.f.util;

import android.content.Context;
import android.content.DialogInterface;

import com.ricky.f.dialog.AlertDialog;
import com.ricky.f.dialog.LoadingDialog;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Deak on 16/10/12.
 */

public class DialogUtils {
    private static LoadingDialog mLoadingDialog;
    private static AlertDialog mAlertDialog;

    public static void showLoadingDialog(Context context, String msg) {
        if(mLoadingDialog == null){
            mLoadingDialog = new LoadingDialog(context);
            mLoadingDialog.setOnCancelListener(onCancelListener);
        }
        mLoadingDialog.show(msg);
    }

    static DialogInterface.OnCancelListener onCancelListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            dismissAllDialogs();
        }
    };

    public static void dismissAllDialogs(){
        dismissLoadingDialog();
        dismissAlertDialog();
    }

    public static void dismissLoadingDialog(){
        if(mLoadingDialog == null)return;
        mLoadingDialog.dismiss();
        mLoadingDialog = null;
    }

    public static void showConfirmDialog(Context context, String title, String contentText, String confirmText, String cancelText, final SweetAlertDialog.OnSweetClickListener onConfirmClickListener) {
        if(mAlertDialog == null){
            mAlertDialog = new AlertDialog(context);
        }
        mAlertDialog.showWarningAlertDialog(title, contentText, confirmText, cancelText, new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dismissAlertDialog();
                if(onConfirmClickListener == null)return;
                onConfirmClickListener.onClick(sweetAlertDialog);
            }
        }, new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dismissAlertDialog();
            }
        });
    }

    public static void dismissAlertDialog(){
        if(mAlertDialog == null)return;
        mAlertDialog.dismiss();
        mAlertDialog = null;
    }
}
