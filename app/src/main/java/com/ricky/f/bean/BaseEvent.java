package com.ricky.f.bean;

/**
 * Created by Deak on 16/12/12.
 */

public class BaseEvent {

    public static final int DEFAULT = 0x100;
    public static final int NET_DATA = 0x101;

    protected int action = DEFAULT;

    public BaseEvent() {
    }

    public BaseEvent(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
