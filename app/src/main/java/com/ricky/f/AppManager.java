package com.ricky.f;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * 
 * CreateTime: 2014-7-30 下午3:34:42 
 * Description: 应用程序管理类
 */
public class AppManager {

	private static Stack<Activity> mActivityStack;
	private static AppManager mAppManager;

	private AppManager() {
	}

	public static AppManager getInstance() {
		if (mAppManager == null) {
			mAppManager = new AppManager();
		}
		return mAppManager;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity pActivity) {
		if (mActivityStack == null) {
			mActivityStack = new Stack<Activity>();
		}
		mActivityStack.add(pActivity);
	}

	/**
	 * 结束当前Activity(结束堆栈中最后一个压入的)
	 */
	public void finishActivity() {
		mActivityStack.lastElement().finish();
	}

	/**
	 * 获取当前Activity(堆栈中最后一个压入的)
	 */
	public Activity getCurrentActivity() {
		return mActivityStack.lastElement();
	}

	/**
	 * 结束指定Activity
	 */
	public void finishActivity(Activity pActivity) {
		if (mActivityStack != null)
			mActivityStack.remove(pActivity);
		pActivity.finish();
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : mActivityStack) {
			if (activity.getClass().equals(cls))
				activity.finish();
		}
	}
	
	/**
	 * 
	 * Date:2014-7-30 
	 * Description: 获取栈大小
	 * @return 
	 * @return int
	 */
	public int getSize() {
		if(mActivityStack == null || mActivityStack.size() <= 0)
			return 0;
		else
			return mActivityStack.size();
	}

	/**
	 * 结束当前栈中所有的Acitivity
	 */
	public void finishAllActivity() {
		if (mActivityStack != null && mActivityStack.size() != 0) {
			for (Activity activity : mActivityStack) {
				if (activity != null)
					activity.finish();
			}
			mActivityStack.clear();
		}
	}
	
	public void finishTopActivities(Class<?> cls){
		Stack<Activity> _Temp = new Stack<>();
		if (mActivityStack != null && mActivityStack.size() > 0) {
			for(int i=mActivityStack.size() - 1; i>=0; i--){
				Activity activity = mActivityStack.get(i);
				if (activity != null && !cls.equals(activity.getClass())){
					activity.finish();
					_Temp.add(activity);
				} else {
					break;
				}
			}
		}
		mActivityStack.removeAll(_Temp);
	}


	/**
	 * 退出应用程序
	 */
	public void appExit(Context pContext) {
//		ShareSDK.stopSDK(pContext); 
		finishAllActivity();
//		ActivityManager am = (ActivityManager)pContext.getSystemService(Context.ACTIVITY_SERVICE);
//		am.killBackgroundProcesses(pContext.getPackageName());  
	}
}
