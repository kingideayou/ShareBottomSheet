package me.next.sharemanager.interfaces;

import me.next.sharemanager.ShareActivityInfo;

/**
 * Created by NeXT on 16/12/1.
 */

public interface OnShareClick {
    void onPlatformClick(int platform, ShareActivityInfo shareActivityInfo);
}