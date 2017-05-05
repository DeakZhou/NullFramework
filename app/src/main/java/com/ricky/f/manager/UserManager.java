package com.ricky.f.manager;

import com.ricky.f.base.BaseManager;
import com.ricky.f.util.HttpUtils;

/**
 * Created by Deak on 17/5/4.
 */

public class UserManager implements BaseManager {

    public static final String Login = "Login";

    public void login(String name, String password) {
        HttpUtils.getInstance().get(Login, "http://www.baidu.com");
    }
}
