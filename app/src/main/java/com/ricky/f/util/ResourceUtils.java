package com.ricky.f.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * 资源helper
 * @author Administrator
 *
 */
public class ResourceUtils {
   //获取颜色
   public static int getColor(int id){
	   return AppUtils.getInstance().getContext().getResources().getColor(id);
   }
   //获取颜色状态list
   public static ColorStateList getColorStateList(int resId){
	   return (ColorStateList) AppUtils.getInstance().getContext().getResources().getColorStateList(resId);
   }
   //获取字符串
   public static String getString(int resId){
	   return AppUtils.getInstance().getContext().getResources().getString(resId);
   }
   //获取drawable
   public static Drawable getDrawable(int resId){
	   return AppUtils.getInstance().getContext().getResources().getDrawable(resId);
   }
   public static LayoutInflater getInflater(){
	   return LayoutInflater.from(AppUtils.getInstance().getContext());
   }
   public static float getDimension(int resId){
	   return AppUtils.getInstance().getContext().getResources().getDimension( resId);
   }
   public static Context getContext(){
	   return AppUtils.getInstance().getContext();
   }
   public static int  getDimensionPixelSize(int id){
	   return getContext().getResources().getDimensionPixelSize(id);
   }
   public static Drawable getDrawableFromDrawablePkg(String drawableName){
	   return getContext().getResources().getDrawable(getContext().getResources().getIdentifier(drawableName,"drawable", getContext().getPackageName()));
   }
   public static int getResIdFromDrawablePkg(String drawableName){
	   return getContext().getResources().getIdentifier(drawableName,"drawable", getContext().getPackageName());
   }

    public static int getLayoutId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "layout",
                paramContext.getPackageName());
    }

    public static int getStringId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "string",
                paramContext.getPackageName());
    }

    public static int getDrawableId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "drawable", paramContext.getPackageName());
    }

    public static int getStyleId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "style", paramContext.getPackageName());
    }

    public static int getId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,"id", paramContext.getPackageName());
    }

    public static int getColorId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "color", paramContext.getPackageName());
    }
    public static int getArrayId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "array", paramContext.getPackageName());
    }

    public static Animation getAnimation(Context paramContext, int animId) {
        if(animId <=0 ) return null;
        return AnimationUtils.loadAnimation(paramContext, animId);
    }
}
