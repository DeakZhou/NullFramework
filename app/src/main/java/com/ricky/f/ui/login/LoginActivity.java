package com.ricky.f.ui.login;

import android.os.Bundle;
import android.view.View;

import com.ricky.f.R;
import com.ricky.f.base.BaseActivity;

public class LoginActivity extends BaseActivity<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {

    private LoginPresenter mLoginPresenter = new LoginPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEvent();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    public void login(View v){
        mLoginPresenter.login("xx", "xxx");
    }

    @Override
    protected LoginContract.Presenter getPresenter() {
        return mLoginPresenter;
    }

    @Override
    public void loginSuccess() {
        showToast("登录成功");
    }
}
