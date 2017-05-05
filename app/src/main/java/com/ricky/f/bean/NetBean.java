package com.ricky.f.bean;

import com.ricky.f.config.ErrCode;

/**
 * Created by Deak on 16/10/31.
 */

public class NetBean extends BaseEvent{

    private String tag;
    private int code;
    private String message;
    private String data;

    public NetBean(int action, String tag) {
        super(action);
        this.tag = tag;
        code = ErrCode.UNKNOW;
        message = "未知错误";
    }

    public NetBean(int aciton, String tag, int code, String message) {
        super(aciton);
        this.tag = tag;
        this.code = code;
        this.message = message;
    }

    public NetBean(int action, String tag, int code, String message, String data) {
        super(action);
        this.tag = tag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isOk(){
        return code == ErrCode.SUCCESS;
    }
}
