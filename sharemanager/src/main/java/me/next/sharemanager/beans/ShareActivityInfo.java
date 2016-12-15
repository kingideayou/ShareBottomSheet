package me.next.sharemanager.beans;

import android.graphics.drawable.Drawable;

/**
 * Created by NeXT on 16/11/18.
 */

public class ShareActivityInfo {

    private boolean useDefaultShare;
    private Drawable appIcon;
    private String appName;
    private String packageName;
    private String activityName;

    public ShareActivityInfo() {
    }

    public ShareActivityInfo(boolean useDefaultShare, String appName, Drawable appIcon, String packageName, String activityName) {
        this.useDefaultShare = useDefaultShare;
        this.appName = appName;
        this.appIcon = appIcon;
        this.packageName = packageName;
        this.activityName = activityName;
    }

    public boolean useDefaultShare() {
        return useDefaultShare;
    }

    public void setUseDefaultShare(boolean useDefaultShare) {
        this.useDefaultShare = useDefaultShare;
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
                "useDefaultShare=" + useDefaultShare +
                ", appName='" + appName + '\'' +
                ", appIcon='" + appIcon + '\'' +
                ", packageName='" + packageName + '\'' +
                ", activityName='" + activityName + '\'' +
                '}';
    }
}
