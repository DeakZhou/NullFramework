package com.ricky.f;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.android.utils.DensityUtil;
import com.android.utils.FileUtil;
import com.android.utils.SystemUtil;
import com.lzy.okgo.OkGo;
import com.ricky.f.bean.DataCache;
import com.ricky.f.config.AppConfig;

import org.litepal.LitePalApplication;

import java.util.logging.Level;

/**
 * Created by Deak on 16/10/12.
 */

public class MyApplication extends LitePalApplication {

    private static MyApplication mApplication;
    public int mScreenWidth, mScreenHeight;

    public DataCache mAppDataCache = new DataCache();

    @Override
    public void onCreate() {
        String processName = SystemUtil.getProcessName(this, android.os.Process.myPid());
        if (processName == null) return;

        boolean defaultProcess = processName.equals(getPackageName());
        if (defaultProcess) {
            mApplication = this;
            initDir();
            initCache();
            initScreenSize();
            initOkGo();
        }
    }

    private void initOkGo() {
        OkGo.init(this);
        OkGo.getInstance().debug("OkGo", Level.INFO, true).setRetryCount(3);
    }

    private void initScreenSize() {
        mScreenWidth = DensityUtil.getDisplayWidth(this);
        mScreenHeight = DensityUtil.getDisplayHeight(this);
    }

    private void initCache() {
        mAppDataCache.reload();
    }

    public static MyApplication getInstance() {
        return mApplication;
    }

    void initDir() {
        FileUtil.createDir(AppConfig.DirConfig.EXT_CACHE);
        FileUtil.createDir(AppConfig.DirConfig.INTERNAL_CACHE);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
