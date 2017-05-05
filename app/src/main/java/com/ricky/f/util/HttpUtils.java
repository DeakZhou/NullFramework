package com.ricky.f.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.ricky.f.bean.NetBean;
import com.ricky.f.bean.BaseEvent;
import com.ricky.f.config.RequestCode;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Deak on 16/10/11.
 */

public class HttpUtils {

    private static class SingletonHolder {
        private static final HttpUtils INSTANCE = new HttpUtils();
    }

    public static HttpUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void get(Object tag, String url) {
        get(tag, url, null);
    }

    public void get(Object tag, String url, Map<String, Object> parameters) {
        OkGo.get(url)
                .tag(tag)
                .params(formatParamters(parameters))
                .execute(new MyStringCallback());
    }

    public void post(String url) {
        post(url, null);
    }

    public void post(String url, Map<String, Object> parameters) {
        OkGo.post(url)
                .tag(this)
                .params(formatParamters(parameters))
                .execute(new MyStringCallback());
    }

    public void put(String url) {
        put(url, null);
    }

    public void put(String url, Map<String, Object> parameters) {

        OkGo.put(url)
                .tag(this)
                .params(formatParamters(parameters))
                .execute(new MyStringCallback());
    }

    public void delete(String url) {
        delete(url, null);
    }

    public void delete(String url, Map<String, Object> parameters) {

        OkGo.delete(url)
                .tag(this)
                .params(formatParamters(parameters))
                .execute(new MyStringCallback());
    }

    class MyStringCallback extends StringCallback{

        @Override
        public void onSuccess(String s, Call call, Response response) {
            String tag = response.request().tag().toString();
            try {
                JSONObject jsonObject = JSON.parseObject(response.body().string());
                LogUtils.i("http response data:"+ jsonObject.toJSONString());
                int code = jsonObject.getIntValue("code");
                String message = jsonObject.getString("message");
                String data = jsonObject.getString("data");
                RxBusUtils.getInstance().post(new NetBean(BaseEvent.NET_DATA, tag, code, message, data));
            } catch (Exception e) {
                e.printStackTrace();
                RxBusUtils.getInstance().post(new NetBean(BaseEvent.NET_DATA, tag));
            }
        }

        @Override
        public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);
            String tag = response.request().tag().toString();
            handleExcuteResult(tag, response.code(), response.message(), "");
        }
    }

    private void handleExcuteResult(String tag, int code, String message, String data) {
        RxBusUtils.getInstance().post(new NetBean(BaseEvent.NET_DATA, tag, code, message, data));
    }

    private HttpParams formatParamters(Map<String, Object> paramters){
        HttpParams p = new HttpParams();
        if(paramters != null && paramters.size() > 0){
            for(String key : paramters.keySet()){
                Object value = paramters.get(key);
                if(value instanceof String){
                    p.put(key, (String)value);
                } else if(value instanceof Integer){
                    p.put(key, (Integer)value);
                } else if(value instanceof Float){
                    p.put(key, (Float)value);
                } else if(value instanceof Double){
                    p.put(key, (Double)value);
                } else if(value instanceof Boolean){
                    p.put(key, (Boolean)value);
                }
            }
        }
        return p;
    }
}
