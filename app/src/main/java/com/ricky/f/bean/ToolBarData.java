package com.ricky.f.bean;

import com.ricky.f.R;
import com.ricky.f.util.ResourceUtils;

/**
 * Created by Deak on 16/11/14.
 */

public class ToolBarData {
    /**
     *标题
     */
    private String title;

    /**
     * 左导航,默认back
     */
    private int navigationLeftIcon = R.drawable.ic_back_selector;

    /**
     * 图标右导航
     */
    private int navigationRightIcon;

    /**
     * 文字右导航
     */
    public String navigationRightText;

    /**
     * 背景色
     */
    private int backgroundColor;

    private boolean isShowExitIcon;

    private boolean isShowRightNavTip;

    private boolean isShowSearchBox;

    public boolean isShowExitIcon() {
        return isShowExitIcon;
    }

    public void setShowExitIcon(boolean showExitIcon) {
        isShowExitIcon = showExitIcon;
    }

    public boolean isShowRightNavTip() {
        return isShowRightNavTip;
    }

    public void setShowRightNavTip(boolean showRightNavTip) {
        isShowRightNavTip = showRightNavTip;
    }

    public boolean isShowSearchBox() {
        return isShowSearchBox;
    }

    public void setShowSearchBox(boolean showSearchBox) {
        isShowSearchBox = showSearchBox;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNavigationLeftIcon() {
        return navigationLeftIcon;
    }

    public void setNavigationLeftIcon(int navigationLeftIcon) {
        this.navigationLeftIcon = navigationLeftIcon;
    }

    public int getNavigationRightIcon() {
        return navigationRightIcon;
    }

    public void setNavigationRightIcon(int navigationRightIcon) {
        this.navigationRightIcon = navigationRightIcon;
    }

    public int getBackgroundColor() {
        return ResourceUtils.getColor(backgroundColor);
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getNavigationRightText() {
        return navigationRightText;
    }

    public void setNavigationRightText(String navigationRightText) {
        this.navigationRightText = navigationRightText;
    }

    public void reset(){
        title = "";
        navigationLeftIcon = R.drawable.ic_back_selector;
        navigationRightIcon = 0;
        navigationRightText = "";
        isShowExitIcon = false;
        isShowRightNavTip = false;
        isShowSearchBox = false;
    }
}
