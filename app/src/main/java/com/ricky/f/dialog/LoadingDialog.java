package com.ricky.f.dialog;

/**
 * Created by Deak on 16/10/12.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import com.android.utils.StringUtil;
import com.ricky.f.R;

/**
 * Created by zzz40500 on 15/6/15.
 */
public class LoadingDialog {
    private Context mContext;
    private Dialog mLoadingDialog;
    private TextView mTvLoadingMsg;


    public LoadingDialog(Context context) {
        this.mContext=context;
        init();
    }

    private void init() {
        mLoadingDialog = new Dialog(mContext,R.style.LoadingDialog_UnifyStyle);
        mLoadingDialog.setContentView(R.layout.dlg_loading);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mTvLoadingMsg = (TextView) mLoadingDialog.findViewById(R.id.tv_loadingMsg);

    }

    public void setLoadingText(String msg){
        mTvLoadingMsg.setText(msg);
    }

    public void show(){
        show("");
    }

    public void show(String msg){
        if(!StringUtil.isEmpty(msg)){
            setLoadingText(msg);
        }
        if(isShowing()){
            return;
        }
        mLoadingDialog.show();
    }

    public boolean isShowing(){
        return mLoadingDialog != null && mLoadingDialog.isShowing();
    }

    public void dismiss(){
        if(!isShowing())return;
        mLoadingDialog.dismiss();
        mLoadingDialog = null;
    }

    public Dialog getDialog(){
        return  mLoadingDialog;
    }

    public void setCanceledOnTouchOutside(boolean cancel){
        mLoadingDialog.setCanceledOnTouchOutside(cancel);
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener){
        if(onCancelListener==null)return;
        mLoadingDialog.setOnCancelListener(onCancelListener);
    }
}