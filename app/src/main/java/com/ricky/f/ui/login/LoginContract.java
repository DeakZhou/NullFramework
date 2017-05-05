package com.ricky.f.ui.login;

import com.ricky.f.base.BaseView;
import com.ricky.f.base.BasePresenter;

/**
 * Created by Deak on 17/5/4.
 */

public interface LoginContract {
    interface View extends BaseView{
        void loginSuccess();
    }

    abstract class Presenter extends BasePresenter<View>{
        public abstract void login(String name, String password);
    }
}
