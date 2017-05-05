package com.ricky.f.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.utils.StringUtil;
import com.ricky.f.R;
import com.ricky.f.util.ResourceUtils;

/**
 * Created by Deak on 16/10/29.
 */

public class ErrorView extends LinearLayout {

    private Context mContext;
    private ImageView mIvErrIcon;
    private TextView mTvErrMsg;
    private Button mBtnDo;

    public ErrorView(Context context) {
        super(context);
        this.mContext = context;
        setGravity(Gravity.CENTER);
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(ResourceUtils.getColor(R.color.pager_color));
        initView();
    }

    private void initView() {
        mIvErrIcon = new ImageView(mContext);
        mIvErrIcon.setImageResource(R.drawable.ic_error);
        mIvErrIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        mTvErrMsg = new TextView(mContext);
        mTvErrMsg.setTextSize(16);
        mTvErrMsg.setTextColor(ResourceUtils.getColor(R.color.prompt_color));
        mTvErrMsg.setGravity(Gravity.CENTER);

        mBtnDo = new Button(mContext);
        mBtnDo.setTextSize(16);
        mBtnDo.setTextColor(ResourceUtils.getColor(R.color.white));
        mBtnDo.setVisibility(View.GONE);

        LayoutParams params = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mIvErrIcon.setLayoutParams(params);
        params.setMargins(0, ResourceUtils.getDimensionPixelSize(R.dimen.view_margin_large), 0, 0);
        mTvErrMsg.setLayoutParams(params);
        mBtnDo.setLayoutParams(params);

        addView(mIvErrIcon);
        addView(mTvErrMsg);
        addView(mBtnDo);
    }

    public void setErrIcon(int errIcon){
        if(errIcon<=0) {
            mIvErrIcon.setVisibility(View.GONE);
            return;
        }
        mIvErrIcon.setImageResource(errIcon);
        mIvErrIcon.setVisibility(View.VISIBLE);
    }

    public void setErrMsg(String errMsg){
        if(StringUtil.isEmpty(errMsg))return;
        mTvErrMsg.setText(errMsg);
    }

    public void setErrBtnText(String btnText, OnClickListener onClickListener){
        if(StringUtil.isEmpty(btnText))return;
        mBtnDo.setText(btnText);
        mBtnDo.setOnClickListener(onClickListener);
        mBtnDo.setVisibility(View.VISIBLE);
    }
}
