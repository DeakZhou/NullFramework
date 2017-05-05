package com.ricky.f.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.utils.StringUtil;
import com.ricky.f.R;
import com.ricky.f.bean.ToolBarData;
import com.ricky.f.util.ResourceUtils;

/**
 * Created by Deak on 16/11/29.
 */

public class FxToolBar extends RelativeLayout {

    private TextView mTvTitle;
    private ImageView mIvBack;
    private ImageView mIvExit;
    private ImageView mIvRightNav;
    private RelativeLayout mRlRightNav;
    private TextView mTvRightNav;

    public FxToolBar(Context context) {
        super(context);

        setBackgroundColor(ResourceUtils.getColor(R.color.app_toolbar_color));
        View toolBar =  LayoutInflater.from(context).inflate(R.layout.view_toolbar, this);
        mTvTitle = (TextView) toolBar.findViewById(R.id.tv_title);
        mIvBack = (ImageView) toolBar.findViewById(R.id.iv_back);
        mIvExit = (ImageView) toolBar.findViewById(R.id.iv_exit);
        mRlRightNav = (RelativeLayout) toolBar.findViewById(R.id.rl_rightNav);
        mIvRightNav = (ImageView) toolBar.findViewById(R.id.iv_rightNav);
        mTvRightNav = (TextView) toolBar.findViewById(R.id.tv_rightNav);
    }

    public void setToolBarData(ToolBarData toolBarData){
        mTvTitle.setText(toolBarData.getTitle());
        mIvExit.setVisibility(toolBarData.isShowExitIcon() ? View.VISIBLE : View.GONE);
        if(toolBarData.getNavigationLeftIcon() > 0){
            mIvBack.setImageResource(toolBarData.getNavigationLeftIcon());
            mIvBack.setVisibility(View.VISIBLE);
        } else {
            mIvBack.setVisibility(View.GONE);
        }
        if(toolBarData.getNavigationRightIcon() > 0){
            mIvRightNav.setImageResource(toolBarData.getNavigationRightIcon());
            mIvRightNav.setVisibility(View.VISIBLE);
        } else {
            mIvRightNav.setVisibility(View.GONE);
        }
        if(!StringUtil.isEmpty(toolBarData.getNavigationRightText())){
            mTvRightNav.setText(toolBarData.getNavigationRightText());
            mRlRightNav.setVisibility(View.VISIBLE);
        } else {
            mRlRightNav.setVisibility(View.GONE);
        }
    }
}
