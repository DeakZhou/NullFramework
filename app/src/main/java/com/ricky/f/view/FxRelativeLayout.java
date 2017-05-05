package com.ricky.f.view;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.android.utils.DensityUtil;
import com.nineoldandroids.view.ViewHelper;
import com.ricky.f.R;
import com.ricky.f.bean.ToolBarData;
import com.ricky.f.listener.NoDoubleClickListener;
import com.ricky.f.util.ResourceUtils;

/**
 * Created by Deak on 16/10/29.
 * 1、显示
 */

public class FxRelativeLayout extends RelativeLayout {

    public static final int TOOLBAR_ID = 1;

    private Context mContext;
    private View mContainerView;

    private FxToolBar mToolBar;
    private ProgressBar mProgressBar;
    private ErrorView mErrorView;
    private View mRlShadow;

    public FxRelativeLayout(Context context) {
        super(context);
        this.mContext = context;
        setFitsSystemWindows(true);
        setBackgroundColor(ResourceUtils.getColor(R.color.pager_color));
        initChildView();
    }

    private void initChildView() {
        initToolBar();
        initProgressBar();
        initErrorView();
        initShadow();
    }

    private void initToolBar() {
        mToolBar = new FxToolBar(mContext);
        mToolBar.setId(R.id.toolbar);
        //RelativeLayout.LayoutParams toolBarParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.getToolbarHeight(mContext));
        //mToolBar.setLayoutParams(toolBarParams);
    }

    private void initProgressBar() {
        mProgressBar = new ProgressBar(mContext);
        int minWidth = ResourceUtils.getDimensionPixelSize(R.dimen.pb_cicle_min_width);
        int minHeight = ResourceUtils.getDimensionPixelSize(R.dimen.pb_cicle_min_height);
        mProgressBar.setIndeterminateDrawable(ResourceUtils.getDrawable(R.drawable.dlg_loading_bg));
        mProgressBar.setIndeterminate(false);
        LayoutParams params = new LayoutParams(minWidth, minHeight);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mProgressBar.setLayoutParams(params);
    }

    private void initErrorView() {
        mErrorView = new ErrorView(mContext);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        params.addRule(RelativeLayout.BELOW, R.id.toolbar);
        mErrorView.setLayoutParams(params);
    }

    private void initShadow() {
        mRlShadow = new View(mContext);
        mRlShadow.setBackgroundColor(ResourceUtils.getColor(R.color.shadow_bg));
        LayoutParams shadowParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mRlShadow.setLayoutParams(shadowParams);
    }

    public void addToolBar(){
        addView(mToolBar);
    }

    public void addBottomBar(View bottomBar){
        DensityUtil.measureView(bottomBar);

        LayoutParams params = (LayoutParams) mContainerView.getLayoutParams();
        params.setMargins(0, 0, 0, bottomBar.getMeasuredHeight());
        mContainerView.setLayoutParams(params);

        params = new LayoutParams(LayoutParams.MATCH_PARENT, bottomBar.getMeasuredHeight());
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bottomBar.setLayoutParams(params);
        addView(bottomBar);
    }

    public void addContainer(View view, boolean isScroll) {
        mContainerView = view;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, R.id.toolbar);;
        if(!isScroll){
            mContainerView.setLayoutParams(params);
            addView(mContainerView);//添加内容
        } else {
            //将mContainerView添加到ScrollView中
            ScrollView sv = new ScrollView(mContext);
            sv.setVerticalScrollBarEnabled(false);
            sv.setLayoutParams(params);
            RelativeLayout svOnlyChildView = new RelativeLayout(mContext);
            params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            mContainerView.setLayoutParams(params);
            svOnlyChildView.addView(mContainerView);
            sv.addView(svOnlyChildView);
            addView(sv);
        }
    }

    public void addProgressBar(){
        //默认progressBar不显示
        mProgressBar.setVisibility(View.GONE);
        addView(mProgressBar);//添加progress层
    }

    public void addErrView(){
        //默认异常View不显示
        mErrorView.setVisibility(View.GONE);
        addView(mErrorView);
    }

    public void addShadowView(){
        mRlShadow.setVisibility(View.GONE);
        addView(mRlShadow);
    }

    public void showProgressBar(){
        if(mProgressBar.getVisibility() == View.VISIBLE)return;
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void dismissProgressBar(){
        if(mProgressBar.getVisibility() == View.GONE)return;
        mProgressBar.setVisibility(View.GONE);
    }

    public void showErrorView(int errIcon, String errMsg, String btnText, OnClickListener btnOnclickListener){
        if(mErrorView.getVisibility() == View.VISIBLE)return;
        mErrorView.setErrIcon(errIcon);
        mErrorView.setErrMsg(errMsg);
        mErrorView.setErrBtnText(btnText, btnOnclickListener);
        mErrorView.setVisibility(View.VISIBLE);
    }

    public void showErrorView(int errIcon, String errMsg){
        showErrorView(errIcon, errMsg, "", null);
    }

    public void hideErrorView(){
        if(mErrorView.getVisibility() == View.GONE)return;
        mErrorView.setVisibility(View.GONE);
    }

    public void showShadowView(){
        if(mRlShadow.getVisibility() == View.VISIBLE)return;
        mRlShadow.setVisibility(View.VISIBLE);
    }

    public void hideShadowView(){
        if(mRlShadow.getVisibility() == View.GONE)return;
        mRlShadow.setVisibility(View.GONE);
    }

    public void setShadowViewMarginTop(int top){
        LayoutParams params = (LayoutParams) mRlShadow.getLayoutParams();
        params.setMargins(0, top, 0, 0);
        mRlShadow.requestLayout();
    }

    public void setShadowViewAlpha(float alpha){
        ViewHelper.setAlpha(mRlShadow, alpha);
    }

    public void setOnShadownViewClick(NoDoubleClickListener onClickListener){
        mRlShadow.setOnClickListener(onClickListener);
    }

    public void setToolBar(ToolBarData toolBarData){
        if(toolBarData==null)return;
        mToolBar.setToolBarData(toolBarData);
    }
}
