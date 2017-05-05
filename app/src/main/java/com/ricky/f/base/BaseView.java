package com.ricky.f.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ricky.f.bean.BaseEvent;

/**
 * Created by Deak on 17/5/4.
 */

public interface BaseView {
    Context getContext();

    void showToast(String toast);

    void showLoadingDialog(String msg);
    void dismissLoadingDialog();

    void showLoadingProgress();
    void dismissLoadingProgress();

    void showErrView(int errIcon, String errMsg, String btnText, View.OnClickListener onClickListener);
    void showErrView(int errIcon, String errMsg);
    void showErrView(String errMsg);
    void hideErrView();

    void openActivity(String actUrl);
    void openActivity(String actUrl, boolean isDestory);
    void openActivity(String actUrl, int requestCode);
    void openActivity(String actUrl, int requestCode, boolean isDestory);
    void openActivity(String actUrl, Object... params);
    void openActivity(String actUrl, int requestCode, Object... params);

    void destoryActivity();
    void destoryTopActivities(Class<?> clazz);
    void destoryActivity(int resultCode, Intent data);

    Bundle getBundle();

    void requestPermission(int code);

    void sendEvent(BaseEvent baseEvent);
}
