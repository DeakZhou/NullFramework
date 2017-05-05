package com.android.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * Created by Deak on 16/10/15.
 */

public class FastJsonUtil {
    public static <T> T getBean(String str, Class<T> clazz) {
        return (T) JSON.parseObject(str, clazz);
    }
    public static <T> List<T> getBeans(String str, Class<T> clazz) {
        return JSON.parseArray(str, clazz);
    }
    public static String getJsonString(Object bean) {
        return JSON.toJSONString(bean);
    }
}
