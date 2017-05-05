package com.ricky.f.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.ricky.f.util.lib.urlrouter.Route;
import com.ricky.f.util.lib.urlrouter.UrlRouterApi;

/**
 * Created by zhengxiaoyong on 16/4/22.
 */
public class UrlRouterUtils {
    public static final String URL_ROUTER_REFERRER = "UrlRouterUtils.REFERRER";
    private Context mContext;
    private Intent mIntent;
    private boolean isAllowEscape;
    private int mRequestCode;
    private String mCategory;
    private int[] mTransitionAnim;

    private UrlRouterUtils(Context context) {
        if (context == null)
            throw new IllegalArgumentException("context can not be null!");
        this.mContext = context;
        this.isAllowEscape = false;
        this.mRequestCode = -1;
        this.mIntent = new Intent(Intent.ACTION_VIEW);
        this.mIntent.addCategory(Intent.CATEGORY_DEFAULT);
        UrlRouterApi.setupReferrer(mContext, mIntent);
    }

    public static UrlRouterUtils from(Context context) {
        return new UrlRouterUtils(context);
    }

    public final UrlRouterUtils params(Bundle bundle) {
        if (bundle == null)
            return this;
        mIntent.putExtras(bundle);
        return this;
    }

    public final UrlRouterUtils category(String category) {
        mCategory = category;
        return this;
    }

    public final UrlRouterUtils transitionAnim(int enterAnim, int exitAnim) {
        if (enterAnim < 0 || exitAnim < 0) {
            mTransitionAnim = null;
            return this;
        }
        mTransitionAnim = new int[2];
        mTransitionAnim[0] = enterAnim;
        mTransitionAnim[1] = exitAnim;
        return this;
    }

    public final UrlRouterUtils requestCode(int reqCode) {
        mRequestCode = reqCode;
        return this;
    }

    public final boolean jump(String url) {
        return !TextUtils.isEmpty(url) && jump(Uri.parse(url));
    }

    public final UrlRouterUtils allowEscape() {
        isAllowEscape = true;
        return this;
    }

    public final UrlRouterUtils forbidEscape() {
        isAllowEscape = false;
        return this;
    }

    public final boolean jump(Uri uri) {
        if (uri == null)
            return false;
        if (!isAllowEscape) {
            mIntent.setPackage(mContext.getApplicationContext().getPackageName());
        }
        if (!TextUtils.isEmpty(mCategory)) {
            mIntent.addCategory(mCategory);
        }
        mIntent.setData(uri);
        ResolveInfo targetActivity = UrlRouterApi.queryActivity(mContext, mIntent);
        if (targetActivity == null)
            return false;
        String packageName = targetActivity.activityInfo.packageName;
        String className = targetActivity.activityInfo.name;
        mIntent.setClassName(packageName, className);
        ComponentName targetComponentName = mIntent.getComponent();
        if (!(mContext instanceof Activity)) {
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ContextCompat.startActivities(mContext, new Intent[]{mIntent});
            return true;
        }
        if (mContext instanceof Activity) {
            ComponentName thisComponentName = ((Activity) mContext).getComponentName();
//            if (thisComponentName.equals(targetComponentName))
//                return true;
            Bundle options=null;
            if (mTransitionAnim != null && (mTransitionAnim[0]>0 || mTransitionAnim[1]>0)) {
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(mContext, mTransitionAnim[0], mTransitionAnim[1]);
                if(compat != null){
                    options = compat.toBundle();
                }
            }
            if (mRequestCode >= 0) {
                ActivityCompat.startActivityForResult((Activity) mContext, mIntent, mRequestCode, options);
                return true;
            }
            ActivityCompat.startActivity(mContext, mIntent, options);
            return true;
        }
        if (mTransitionAnim != null) {
            //((Activity) mContext).overridePendingTransition(mTransitionAnim[0], mTransitionAnim[1]);
        }
        return false;
    }

    public static Route getStartedRoute(Context context) {
        return UrlRouterApi.parseStartedRoute(context);
    }

    public static Route getCurrentRoute(Context context) {
        return UrlRouterApi.parseCurrentRoute(context);
    }

}

