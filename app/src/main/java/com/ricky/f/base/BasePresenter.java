package com.ricky.f.base;

import com.ricky.f.bean.NetBean;
import com.ricky.f.config.ErrCode;
import com.ricky.f.config.RequestCode;
import com.ricky.f.config.RouterSchema;
import com.ricky.f.bean.BaseEvent;

import java.lang.ref.WeakReference;

/**
 * Created by Deak on 16/10/12.
 */

public abstract class BasePresenter<V> {

    /**
     * 内存不足时释放内存
     */
    protected WeakReference<V> mViewRef;
    protected V mView;

    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
        mView = mViewRef.get();
    }

    protected BaseView getBaseView() {
        return (BaseView) mView;
    }

    //用于在activity销毁时释放资源
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public void handleNetResult(BaseEvent baseEvent){
        NetBean netBean = (NetBean) baseEvent;
        if(netBean.isOk()){
            success(netBean);
        } else {
            unifyErrHandle(netBean.getTag(), netBean.getCode(), netBean.getMessage());
        }
    }

    /**
     * 统一处理异常
     */
    private void unifyErrHandle(String tag, int errCode, String message) {
        /**
         * 未登录跳转到登录
         */
        switch (errCode) {
            case ErrCode.UNAUTHORIZED:
                getBaseView().openActivity(RouterSchema.LoginActivity, RequestCode.REQUEST_LOGIN);
                break;
            default:
                failure(tag, errCode, message);
                break;
        }
    }

    protected abstract void success(NetBean bean);

    protected void failure(String tag, int errCode, String message) {
        getBaseView().dismissLoadingProgress();
        getBaseView().dismissLoadingDialog();
    }
}
