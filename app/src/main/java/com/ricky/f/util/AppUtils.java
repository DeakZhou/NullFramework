package com.ricky.f.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.android.utils.DeviceUtil;
import com.ricky.f.AppManager;
import com.ricky.f.MyApplication;
import com.ricky.f.R;

import java.io.Serializable;

public class AppUtils {

	private static AppUtils instance = new AppUtils();

	public static AppUtils getInstance() {
		return instance;
	}

	/**
	 * 获取应用上下文
	 * @return
	 */
	public Context getContext(){
		return MyApplication.getInstance();
	}

	public void startActivity(Activity context, String url){
		startActivity(context, url, 0, false, false, false, true, 0, 0, new Object[]{});
	}

	public void startActivity(Activity context, String url, Object ...params){
		startActivity(context, url, 0, false, false, false, true, 0, 0, params);
	}

	public void startActivity(Activity context, String url, int requestCode){
		startActivity(context, url, requestCode, false, false, false, true, 0, 0, new Object[]{});
	}

	public void startActivity(Activity context, String url, int requestCode, Object ...params){
		startActivity(context, url, requestCode, false, false, false, true, 0, 0, params);
	}

	public void startActivity(final Activity activity, String url, int requestCode, boolean isDesoty, boolean isCheckNet, boolean isCheckLogin, boolean isAnim, int animIn, int animOut, Object ...params){
		if(isCheckNet && !DeviceUtil.isNetworkConnected(activity)){
			ToastUtils.showToast(activity, ResourceUtils.getString(R.string.network_error));
			return;
		}
//		if(isCheckLogin && !MyApplication.getInstance().isLogin()){
//			UrlRouterUtils.from(activity).requestCode(RequestCode.REQUEST_LOGIN).jump(RouterSchema.LoginActivity);
//			return;
//		}
		UrlRouterUtils urlRouterUtils = UrlRouterUtils.from(activity).params(buildBundle(params));
		if(requestCode > 0){
			urlRouterUtils.requestCode(requestCode);
		}
		if(isAnim){
			if(animIn>0 && animOut > 0){
				urlRouterUtils.transitionAnim(animIn, animOut);
			} else if(animIn == 0 && animOut == 0) {
				urlRouterUtils.transitionAnim(R.anim.push_left_in, R.anim.nothing);
			} else {
				urlRouterUtils.transitionAnim(0, 0);
			}
		} else {
			urlRouterUtils.transitionAnim(0, 0);
		}

		urlRouterUtils.jump(url);
		if(isDesoty){
			AppManager.getInstance().finishActivity(activity);
		}
	}

	private Bundle buildBundle(Object ...params){
		Bundle bundle = new Bundle();
		if(params != null && params.length > 1 && params.length % 2 == 0){
			for(int i=0; i<params.length; i+=2){
				String key=(String) params[i];
				Object value=params[i+1];
				if(value instanceof String){
					bundle.putString(key, (String)value);
					continue;
				}
				if(value instanceof Integer){
					bundle.putInt(key, (int) value);
					continue;
				}
				if(value instanceof Float){
					bundle.putFloat(key, (float) value);
					continue;
				}
				if(value instanceof Double){
					bundle.putDouble(key, (double) value);
					continue;
				}
				if(value instanceof Serializable){
					bundle.putSerializable(key, (Serializable) value);
					continue;
				}
			}
		}
		return bundle;
	}

	public void finishActivity(Activity activity){
		if(activity == null)return;
		AppManager.getInstance().finishActivity(activity);
	}

	public void finishTopActivities(Class<?> clazz){
		if(clazz == null)return;
		AppManager.getInstance().finishTopActivities(clazz);
	}

	long backTime = -1;
	public void exit(Activity context) {
		if(backTime == -1) {
			ToastUtils.showToast(context, R.string.app_exit);
			backTime = System.currentTimeMillis();
		} else {
			if(System.currentTimeMillis() - backTime < 2000) {
				AppManager.getInstance().finishAllActivity();
			} else {
				ToastUtils.showToast(context, R.string.app_exit);;
				backTime = System.currentTimeMillis();
			}
		}
	}
}