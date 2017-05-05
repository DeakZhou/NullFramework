package com.ricky.f.config;

import com.android.utils.FileUtil;

/**
 * Created by Deak on 16/10/11.
 */

public class AppConfig {

    public static final String BASE_URL = "http://www.xxx.com/";
    public static final int HTTP_TIMEOUT = 15;
    public static final boolean OPEN_HTTP_LOG = true;
    public static final String CONFIG_FILE_NAME = "NullConfig";

    //App启动delay时间
    public static final int LAUNCH_VIEW_DELAY_TIME = 2000;

    public static final int APP_CAPTCHA_WAIT_TIME = 60;

    public static final boolean DEBUG = true;

    public static final String PACKAGE_NAME = "com.ricky.f";

    /**
     *
     * CreateTime: 2014-9-28 下午6:09:25
     * Description: 应用缓存目录
     */
    public static class DirConfig {
        public static final String EXT_CACHE =  FileUtil.getRootDir("/null/cache/");

        public static final String BASE_INTERNAL = "/data/data/"+ PACKAGE_NAME +"/files/";
        public static final String INTERNAL_CACHE = BASE_INTERNAL + "cache/";
    }
}
