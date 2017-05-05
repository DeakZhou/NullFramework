package com.ricky.f.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.utils.DeviceUtil;
import com.android.utils.StringUtil;
import com.ricky.f.AppManager;
import com.ricky.f.R;
import com.ricky.f.bean.ToolBarData;
import com.ricky.f.bean.BaseEvent;
import com.ricky.f.listener.NoDoubleClickListener;
import com.ricky.f.permission.EasyPermissions;
import com.ricky.f.util.AnnotationsUtils;
import com.ricky.f.util.AppUtils;
import com.ricky.f.util.DialogUtils;
import com.ricky.f.util.PermissionUtils;
import com.ricky.f.util.ResourceUtils;
import com.ricky.f.util.RxBusUtils;
import com.ricky.f.util.ToastUtils;
import com.ricky.f.view.ClearEditText;
import com.ricky.f.view.FxRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Deak on 16/10/11.
 */

public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    protected Context mContext;

    public T mPresenter;
    public AnnotationsUtils mAnnotationsUtils;

    protected FxRelativeLayout mFxRelativeLayout;
    protected LayoutInflater mLayoutInflater;
    private boolean isToolbar=true;//默认需要toolbar
    public ToolBarData mToolBarData = new ToolBarData();
    private boolean isScroll;
    private int mBackAnim= R.anim.slide_out_right;

    protected Intent mIntent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter=getPresenter();
        //关联view
        mPresenter.attachView((V)this);

        mContext = this;

        mLayoutInflater = getLayoutInflater();
        View view = mLayoutInflater.inflate(getLayoutId(), null);
        mFxRelativeLayout = getView(view, isToolbar, isScroll);
        setContentView(mFxRelativeLayout);

        requestFocus();

        AppManager.getInstance().addActivity(this);//将当前的Activity加入到自定义栈里面

        //初始化注解
        mAnnotationsUtils = new AnnotationsUtils();
        mAnnotationsUtils.autoInjectAllField(this, view);

        if (null != getIntent()) {
            handleIntent(getIntent());
        }
    }

    public FxRelativeLayout getView(View view, boolean isToolBar, boolean isScroll){
        FxRelativeLayout fxRelativeLayout = new FxRelativeLayout(this);
        if(isToolBar){
            fxRelativeLayout.addToolBar();
        }
        fxRelativeLayout.addContainer(view, isScroll);
        fxRelativeLayout.addProgressBar();
        fxRelativeLayout.addErrView();
        fxRelativeLayout.addShadowView();
        return fxRelativeLayout;
    }

    public void requestToolBar(){
        mFxRelativeLayout.setToolBar(mToolBarData);
    }

    public Context getContext(){
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * get content view layout id
     *
     * @return
     */
    public abstract int getLayoutId();

    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
            case R.id.iv_exit:
                destoryActivity();
                break;
        }
    }

    //获取Intent
    protected void handleIntent(Intent intent) {
        mIntent = intent;
    }

    public void addBottomBar(View bottomBar){
        mFxRelativeLayout.addBottomBar(bottomBar);
    }

    public void setShowToolBar(boolean isToolBar){
        this.isToolbar = isToolBar;
    }

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }

    public void showToast(String toast) {
        ToastUtils.showToast(this, toast);
    }

    public void showToast(int resId) {
        ToastUtils.showToast(this, ResourceUtils.getString(resId));
    }

    public void showLoadingDialog(String msg) {
        DialogUtils.showLoadingDialog(this, msg);
    }

    public void dismissLoadingDialog() {
        DialogUtils.dismissLoadingDialog();
    }

    public void showLoadingProgress() {
        mFxRelativeLayout.showProgressBar();
    }
    public void dismissLoadingProgress() {
        mFxRelativeLayout.dismissProgressBar();
    }

    public void showErrView(int errIcon, String errMsg, String btnText, View.OnClickListener onClickListener) {
        mFxRelativeLayout.showErrorView(errIcon, errMsg, btnText, onClickListener);
    }

    public void showErrView(int errIcon, String errMsg) {
        showErrView(errIcon, errMsg, "", null);
    }
    public void showErrView(String errMsg) {
        showErrView(0, errMsg, "", null);
    }

    public void hideErrView() {
        mFxRelativeLayout.hideErrorView();
    }

    public void openActivity(String actUrl) {
        openActivity(actUrl, 0, false, 0, 0, new Object[]{});
    }

    public void openActivity(String actUrl, int animIn, int animOut) {
        openActivity(actUrl, 0, false, animIn, animOut, new Object[]{});
    }

    public void openActivity(String actUrl, boolean isDestory) {
        openActivity(actUrl, 0, isDestory, 0, 0, new Object[]{});
    }

    public void openActivity(String actUrl, int requestCode) {
        openActivity(actUrl, requestCode, false, 0, 0, new Object[]{});
    }

    public void openActivity(String actUrl, int requestCode, boolean isDestory) {
        openActivity(actUrl, requestCode, isDestory, 0, 0, new Object[]{});
    }

    public void openActivity(String actUrl, int requestCode, Object... params) {
        openActivity(actUrl, requestCode, false, 0, 0,params);
    }

    public void openActivity(String actUrl, Object ...params) {
        openActivity(actUrl, 0, false, 0, 0, params);
    }

    public void openActivity(String actUrl, int requestCode, boolean isDestory, int inAnim, int outAnim, Object ...params) {
        AppUtils.getInstance().startActivity(this, actUrl, requestCode, isDestory, false, false, true, inAnim, outAnim, params);
    }

    public void destoryActivity() {
        AppUtils.getInstance().finishActivity(this);
        this.overridePendingTransition(0, mBackAnim);
    }
    public void destoryTopActivities(Class<?> clazz) {
        AppUtils.getInstance().finishTopActivities(clazz);
        this.overridePendingTransition(0, mBackAnim);
    }

    public void destoryActivity(int resultCode, Intent data) {
        setResult(resultCode, data);
        AppUtils.getInstance().finishActivity(this);
        this.overridePendingTransition(0, mBackAnim);
    }

    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    public void sendEvent(BaseEvent baseEvent) {
        RxBusUtils.getInstance().post(baseEvent);
    }

    public void showShadowView() {
        mFxRelativeLayout.showShadowView();
    }

    public void hideShadowView() {
        mFxRelativeLayout.hideShadowView();
    }

    public void setShadowViewMarginTop(int top){
        mFxRelativeLayout.setShadowViewMarginTop(top);
    }

    public void setShadowViewAlpha(float alpha){
        mFxRelativeLayout.setShadowViewAlpha(alpha);
    }

    public void setOnShadownViewClick(NoDoubleClickListener onClickListener){
        mFxRelativeLayout.setOnShadownViewClick(onClickListener);
    }

    public void requestPermission(int code) {
        PermissionUtils.getInstance().requestPermission(this, code, onPermissionListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    PermissionUtils.OnPermissionListener onPermissionListener = new PermissionUtils.OnPermissionListener() {
        @Override
        public void onScuessed(int code) {
            handlePermissionResult(code, true);
        }
    };

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        handlePermissionResult(requestCode, false);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        handlePermissionResult(requestCode, true);
    }

    public void handlePermissionResult(int code, boolean flag){
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                requestFocus();
                DeviceUtil.hideSoftInput(this, v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    protected void requestFocus(){
        mFxRelativeLayout.setFocusable(true);
        mFxRelativeLayout.setFocusableInTouchMode(true);
        mFxRelativeLayout.requestFocus();
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    //订阅消息
    Subscription mRxSbscription;
    public void registerEvent(){
        mRxSbscription = RxBusUtils.getInstance().toObserverable(BaseEvent.class)
                .subscribe(new Action1<BaseEvent>() {
                    @Override
                    public void call(BaseEvent baseEvent) {
                        handleSubscribeMsg(baseEvent);
                    }
                });
    }

    //取消订阅
    public void unRegisterEvent(){
        if (mRxSbscription != null && !mRxSbscription.isUnsubscribed()){
            mRxSbscription.unsubscribe();
        }
    }

    protected void handleSubscribeMsg(BaseEvent baseEvent){
        if(baseEvent.getAction() == BaseEvent.NET_DATA){
            //通过网络请求返回的数据，到Presenter处理
            mPresenter.handleNetResult(baseEvent);
            return;
        }
    }

    private List<ClearEditText> mEdtViews = new ArrayList<>();
    private Button mBtnClick;
    private ClearEditText.OnTextWatcherListner onTextWatcherListner = new ClearEditText.OnTextWatcherListner() {
        @Override
        public void onChange() {
            setBtnClickStatus();
        }
    };
    protected void setBtnClickStatus(Button btnClick, ClearEditText... edtViews){
        this.mBtnClick = btnClick;
        this.mEdtViews.clear();
        for(ClearEditText editText : edtViews){
            this.mEdtViews.add(editText);
            editText.setOnTextWatcherListner(onTextWatcherListner);
        }
        setBtnClickStatus();
    }
    protected void setBtnClickStatus(){
        //ViewUtils.setBtnClickStatus(mBtnClick, checkBtnClick(), R.drawable.bg_green_corner_btn_selector, R.drawable.pp_gray_btn);
    }
    protected boolean checkBtnClick(){
        for(EditText editText : mEdtViews){
            if(StringUtil.isEmpty(editText.getText().toString().trim())){
                return false;
            }
        }
        return true;
    }

    public void setBackAnim(int backAnim){
        this.mBackAnim = backAnim;
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        try {
            Configuration config=new Configuration();
            config.setToDefaults();
            res.updateConfiguration(config,res.getDisplayMetrics() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            destoryActivity();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterEvent();
        //解关联view
        mPresenter.detachView();
    }

    //具体的presenter由子类返回
    protected abstract T getPresenter() ;
}
