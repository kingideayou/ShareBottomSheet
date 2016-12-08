package me.next.sharemanager;

import android.graphics.drawable.Drawable;

/**
 * Created by NeXT on 16/11/18.
 */

public class ShareActivityInfo {

    private Drawable appIcon;
    private String appName;
    private String packageName;
    private String activityName;

    public ShareActivityInfo() {
    }

    public ShareActivityInfo(String appName, Drawable appIcon, String packageName, String activityName) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.packageName = packageName;
        this.activityName = activityName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    @Override
    public String toString() {
        return "ShareActivityInfo{" +
                "appName='" + appName + '\'' +
                ", appIcon='" + appIcon + '\'' +
                ", packageName='" + packageName + '\'' +
                ", activityName='" + activityName + '\'' +
                '}';
    }
}
