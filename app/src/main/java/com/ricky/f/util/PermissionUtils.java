package com.ricky.f.util;

import com.ricky.f.MyApplication;
import com.ricky.f.permission.AfterPermissionGranted;
import com.ricky.f.permission.EasyPermissions;
import com.ricky.f.permission.PermissionCode;
import com.ricky.f.permission.PermissionConfig;

/**
 * Created by Deak on 16/11/18.
 */

public class PermissionUtils {
    private static PermissionUtils instance = null;
    private PermissionUtils() {
    }
    /**
     *
     * @param
     * @return PermissionUtils
     * @note单例实例化
     */
    public static synchronized PermissionUtils getInstance() {
        if (instance == null) {
            instance = new PermissionUtils();
        }
        return instance;
    }

    public interface OnPermissionListener{
        void onScuessed(int code);
    }

    public void requestPermission(Object object, int permissionCode, OnPermissionListener onPermissionListener){
        switch (permissionCode){
            case PermissionCode.REQUEST_CAMERA:
                requestCallPermission(object, onPermissionListener);
                break;
            case PermissionCode.REQUEST_LOCATION:
                requestLocPermission(object, onPermissionListener);
                break;
            case PermissionCode.REQUEST_READ_PHONE_STATE:
                requestReadPhoneStatePermission(object, onPermissionListener);
                break;
            case PermissionCode.REQUEST_READ_WRITE:
                requestReadWritePermission(object, onPermissionListener);
                break;
            case PermissionCode.REQUEST_CALL:
                requestCallPermission(object, onPermissionListener);
                break;
            case PermissionCode.REQUEST_RECORD:
                requestRecordPermission(object, onPermissionListener);
                break;
        }
    }

    /**
     * 获取相机权限
     * @param object
     * @param onPermissionListener
     */
    @AfterPermissionGranted(PermissionCode.REQUEST_CAMERA)
    private void requestCameraPermission(Object object, OnPermissionListener onPermissionListener){
        //检查是否已经赋予了所有权限
        if (EasyPermissions.hasPermissions(MyApplication.getInstance(), PermissionConfig.CAMERA)) {
            if(onPermissionListener == null)return;
            onPermissionListener.onScuessed(PermissionCode.REQUEST_CAMERA);
        } else {
            //有权限没有被赋予去请求权限
            EasyPermissions.requestPermissions(object, "获取相机权限", "相机权限被禁用", PermissionCode.REQUEST_CAMERA, PermissionConfig.CAMERA);
        }
    }

    /**
     * 获取位置权限
     * @param object
     * @param onPermissionListener
     */
    @AfterPermissionGranted(PermissionCode.REQUEST_LOCATION)
    private void requestLocPermission(Object object, OnPermissionListener onPermissionListener){
        //检查是否已经赋予了所有权限
        if (EasyPermissions.hasPermissions(MyApplication.getInstance(), PermissionConfig.ACCESS_COARSE_LOCATION, PermissionConfig.ACCESS_FINE_LOCATION)) {
            if(onPermissionListener == null)return;
            onPermissionListener.onScuessed(PermissionCode.REQUEST_LOCATION);
        } else {
            //有权限没有被赋予去请求权限
            EasyPermissions.requestPermissions(object, "获取定位权限", "", PermissionCode.REQUEST_LOCATION, PermissionConfig.ACCESS_COARSE_LOCATION, PermissionConfig.ACCESS_FINE_LOCATION);
        }
    }

    /**
     * 获取读取手机状态权限
     * @param object
     * @param onPermissionListener
     */
    @AfterPermissionGranted(PermissionCode.REQUEST_READ_PHONE_STATE)
    private void requestReadPhoneStatePermission(Object object, OnPermissionListener onPermissionListener){
        //检查是否已经赋予了所有权限
        if (EasyPermissions.hasPermissions(MyApplication.getInstance(), PermissionConfig.READ_PHONE_STATE)) {
            if(onPermissionListener == null)return;
            onPermissionListener.onScuessed(PermissionCode.REQUEST_READ_PHONE_STATE);
        } else {
            //有权限没有被赋予去请求权限
            EasyPermissions.requestPermissions(object, "获取手机状态权限", "", PermissionCode.REQUEST_READ_PHONE_STATE, PermissionConfig.READ_PHONE_STATE);
        }
    }

    /**
     * 获取读写权限
     * @param object
     * @param onPermissionListener
     */
    @AfterPermissionGranted(PermissionCode.REQUEST_READ_WRITE)
    private void requestReadWritePermission(Object object, OnPermissionListener onPermissionListener){
        //检查是否已经赋予了所有权限
        if (EasyPermissions.hasPermissions(MyApplication.getInstance(), PermissionConfig.READ_EXTERNAL_STORAGE, PermissionConfig.WRITE_EXTERNAL_STORAGE)) {
            if(onPermissionListener == null)return;
            onPermissionListener.onScuessed(PermissionCode.REQUEST_READ_WRITE);
        } else {
            //有权限没有被赋予去请求权限
            EasyPermissions.requestPermissions(object, "获取读取权限", "", PermissionCode.REQUEST_READ_WRITE, PermissionConfig.READ_EXTERNAL_STORAGE, PermissionConfig.WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * 获取电话权限
     * @param object
     * @param onPermissionListener
     */
    @AfterPermissionGranted(PermissionCode.REQUEST_CALL)
    private void requestCallPermission(Object object, OnPermissionListener onPermissionListener){
        //检查是否已经赋予了所有权限
        if (EasyPermissions.hasPermissions(MyApplication.getInstance(), PermissionConfig.CALL_PHONE, PermissionConfig.PROCESS_OUTGOING_CALLS)) {
            if(onPermissionListener == null)return;
            onPermissionListener.onScuessed(PermissionCode.REQUEST_CALL);
        } else {
            //有权限没有被赋予去请求权限
            EasyPermissions.requestPermissions(object, "获取打电话权限", "电话权限已被禁用", PermissionCode.REQUEST_CALL, PermissionConfig.CALL_PHONE, PermissionConfig.PROCESS_OUTGOING_CALLS);
        }
    }

    /**
     * 获取录音权限
     * @param object
     * @param onPermissionListener
     */
    @AfterPermissionGranted(PermissionCode.REQUEST_RECORD)
    private void requestRecordPermission(Object object, OnPermissionListener onPermissionListener){
        //检查是否已经赋予了所有权限
        if (EasyPermissions.hasPermissions(MyApplication.getInstance(), PermissionConfig.RECORD_AUDIO)) {
            if(onPermissionListener == null)return;
            onPermissionListener.onScuessed(PermissionCode.REQUEST_RECORD);
        } else {
            //有权限没有被赋予去请求权限
            EasyPermissions.requestPermissions(object, "获取打电话权限", "录音权限已被禁用", PermissionCode.REQUEST_RECORD, PermissionConfig.RECORD_AUDIO);
        }
    }
}
