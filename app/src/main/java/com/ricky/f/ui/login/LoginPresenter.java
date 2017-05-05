package com.ricky.f.ui.login;

import com.ricky.f.bean.NetBean;
import com.ricky.f.manager.UserManager;

/**
 * Created by Deak on 17/5/3.
 */

public class LoginPresenter extends LoginContract.Presenter {

    UserManager userManager = new UserManager();

    @Override
    public void login(String name, String password) {
        userManager.login("ricky", "111");
    }

    @Override
    protected void success(NetBean bean) {
        if(UserManager.Login.equals(bean.getTag())){
            mView.loginSuccess();
        }
    }

    @Override
    protected void failure(String tag, int errCode, String message) {
        super.failure(tag, errCode, message);
        mView.showToast(tag + " " + message);
    }
}
