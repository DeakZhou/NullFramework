package com.ricky.f.util;

import android.app.Activity;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.utils.StringUtil;
import com.ricky.f.MyApplication;
import com.ricky.f.R;
import com.ricky.f.util.lib.toast.ToastCompat;

public class ToastUtils {

	private static Toast mToast;

	private static ToastCompat mToastCompat;


	static {
		if (Build.VERSION.SDK_INT >= 19) {
			mToastCompat=new ToastCompat(MyApplication.getInstance());
		} else {
			mToast=new Toast(MyApplication.getInstance());
		}
		//mToast=new Toast(AppApplication.getInstance());
	}
	public static void showToast(Activity context, int resId) {
		if(resId > 0) {
			String _ShowInfo = ResourceUtils.getContext().getResources().getString(resId);
			showToast(context,_ShowInfo);
		}
	}
	public static void showToast(Activity context, String str) {
		showToast(context, str, Gravity.BOTTOM);
	}

	public static void showToast(Activity context, int resId, int gravity) {
		if(resId > 0) {
			String _ShowInfo = ResourceUtils.getContext().getResources().getString(resId);
			showToast(context,_ShowInfo, gravity);
		}
	}

	public static void showToast(Activity context, String str, int gravity){
		if(!StringUtil.isEmpty(str)) {
			View toastRoot = context.getLayoutInflater().inflate(R.layout.toast_unity_bg, null);
			TextView tv=(TextView)toastRoot.findViewById(R.id.tv_toast);
			//tv.setPadding(ResourceHelper.getDimensionPixelSize(R.dimen.padding_large), ResourceHelper.getDimensionPixelSize(R.dimen.padding_mid), ResourceHelper.getDimensionPixelSize(R.dimen.padding_large), ResourceHelper.getDimensionPixelSize(R.dimen.padding_mid));
			if(mToastCompat != null){
				mToastCompat.setView(toastRoot);
				if (mToastCompat == null) {
					tv.setText(str);
				} else {
					tv.setText(str);
					mToastCompat.setDuration(Toast.LENGTH_SHORT);
				}
				if(gravity == Gravity.CENTER || gravity == Gravity.TOP){
					mToastCompat.setGravity(gravity, 0, 0);
				} else {
					mToastCompat.setGravity(Gravity.BOTTOM, 0, context.getResources().getDimensionPixelSize(R.dimen.toast_bottom_y_offset));
				}
				mToastCompat.show();
			} else {
				mToast.setView(toastRoot);
				if (mToast == null) {
					tv.setText(str);
				} else {
					tv.setText(str);
					mToast.setDuration(Toast.LENGTH_SHORT);
				}
				if(gravity == Gravity.CENTER || gravity == Gravity.TOP){
					mToast.setGravity(gravity, 0, 0);
				} else {
					mToast.setGravity(Gravity.BOTTOM, 0, context.getResources().getDimensionPixelSize(R.dimen.toast_bottom_y_offset));
				}
				mToast.show();
			}
		}
	}
}
