package me.next.sharemanager.utils;

import android.content.Context;
import android.content.Intent;

import me.next.sharemanager.beans.ShareActivityInfo;

/**
 * Created by NeXT on 16/11/21.
 */

public class ShareUtils {

    //Mi 4c 1080
    //Nexus 6 1440
    public static int getColumnsNumber(Context context) {
        int screenWidth = ScreenUtils.getScreenWidth(context);
        if (screenWidth > 1080) {
            return 4;
        } else {
            return 3;
        }
    }

    public static void startDefaultShareTextIntent(Context context, ShareActivityInfo shareActivityInfo, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setClassName(
                shareActivityInfo.getPackageName(),
                shareActivityInfo.getActivityName());
        intent.setType("text/plain");
//        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        context.startActivity(intent);
    }

}
