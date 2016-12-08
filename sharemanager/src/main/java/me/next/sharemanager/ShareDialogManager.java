package me.next.sharemanager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.next.sharemanager.constants.ShareConstants;
import me.next.sharemanager.interfaces.OnShareClick;

/**
 * Created by NeXT on 16/11/18.
 */

public class ShareDialogManager {

    private boolean enableWeChat = false;
    private boolean enableWeiBo = false;
    private boolean enableQQ = false;

    public ShareDialogManager(Builder builder) {
        this.enableQQ = builder.enableQQ;
        this.enableWeiBo = builder.enableWeiBo;
        this.enableWeChat = builder.enableWeChat;
    }

    public void showShareTextDialog(final Context context, final OnShareClick onShareClick) {
        int columnNumber = ShareUtils.getColumnsNumber(context);
        final BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_apps);
        GridLayoutManager layoutManager = new GridLayoutManager(context, columnNumber);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ShareItemDecorationColumns(
                context.getResources().getDimensionPixelSize(R.dimen.app_list_spacing),
                columnNumber));
        final List<ShareActivityInfo> shareActivityInfoList = getShareableAppList(context);
        final ShareActivityInfo shareActivityInfo = getWechatTimeLineShareActivityInfo(context);
        if (shareActivityInfo != null) {
            shareActivityInfoList.add(2, shareActivityInfo);
        }
        ShareAdapter adapter = new ShareAdapter(context, shareActivityInfoList);
        adapter.setItemClickListener(new ShareAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                dialog.dismiss();
                if (onShareClick == null) {
                    Log.e("ShareDialogManager", "ItemClickListener can not be null.");
                    return;
                }
                ShareActivityInfo shareActivityInfo = shareActivityInfoList.get(pos);
                String activityName = shareActivityInfo.getActivityName();
                if (enableWeChat && activityName.startsWith(ShareConstants.WeChat.WECHAT_PACKAGE)) {
                    switch (activityName) {
                        case ShareConstants.WeChat.WECHAT_FAVORITE:
                            onShareClick.onPlatformClick(ShareConstants.PLATFORM_WECHAT_FAVORITE, shareActivityInfo);
                            break;
                        case ShareConstants.WeChat.WECHAT_FRIEND:
                            onShareClick.onPlatformClick(ShareConstants.PLATFORM_WECHAT_FRIEND, shareActivityInfo);
                            break;
                        case ShareConstants.WeChat.WECHAT_TIMELINE:
                            onShareClick.onPlatformClick(ShareConstants.PLATFORM_WECHAT_TIMELINE, shareActivityInfo);
                            break;
                    }
                } else if (enableWeiBo && activityName.startsWith(ShareConstants.WeChat.WECHAT_PACKAGE)) {
                    switch (activityName) {
                        case ShareConstants.WeiBo.WEIBO_TIMELINE:
                            onShareClick.onPlatformClick(ShareConstants.PLATFORM_WEIBO_TIMELINE, shareActivityInfo);
                            break;
                    }
                } else {
                    onShareClick.onPlatformClick(ShareConstants.PLATFROM_NORMAL, shareActivityInfo);
                }

            }
        });
        recyclerView.setAdapter(adapter);
        dialog.setContentView(view);
        dialog.show();
    }

    public List<ShareActivityInfo> getShareableAppList(Context context) {

        boolean useDefaultShare;
        List<ShareActivityInfo> shareActivityInfoList = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
//        sendIntent.setType("image/*");

        List<ResolveInfo> resolveInfoList = packageManager
                .queryIntentActivities(sendIntent, 0);

        ResolveInfo resolveInfo;
        for (int i = resolveInfoList.size() - 1; i >= 0; i--) {
            resolveInfo = resolveInfoList.get(i);

            String packageName = resolveInfo.activityInfo.packageName;

            useDefaultShare = !packageName.equals(ShareConstants.WeChat.WECHAT_PACKAGE) &&
                    !packageName.equals(ShareConstants.WeiBo.WEIBO_PACKAGE);

            shareActivityInfoList.add(useDefaultShare ? shareActivityInfoList.size() : 0,
                    new ShareActivityInfo(
                            useDefaultShare,
                            resolveInfo.loadLabel(packageManager).toString(),
                            resolveInfo.loadIcon(packageManager),
                            resolveInfo.activityInfo.packageName,
                            resolveInfo.activityInfo.name));
        }

        return shareActivityInfoList;

    }

    public static ShareActivityInfo getWechatTimeLineShareActivityInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("image/*");

        List<ResolveInfo> resolveInfoList = packageManager
                .queryIntentActivities(sendIntent, 0);

        for (ResolveInfo info : resolveInfoList) {
            if (info.activityInfo.name.equals(ShareConstants.WeChat.WECHAT_TIMELINE)) {
                return new ShareActivityInfo(
                        false,
                        info.loadLabel(packageManager).toString(),
                        info.loadIcon(packageManager),
                        info.activityInfo.packageName,
                        info.activityInfo.name);
            }
        }

        return null;

    }

    public static final class Builder {

        private boolean enableWeChat = false;
        private boolean enableWeiBo = false;
        private boolean enableQQ = false;

        public Builder() {
        }

        public Builder enableWeChat(boolean enableWeChat) {
            this.enableWeChat = enableWeChat;
            return this;
        }

        public Builder enableWeiBo(boolean enableWeiBo) {
            this.enableWeiBo = enableWeiBo;
            return this;
        }

        public Builder enableQQ(boolean enableQQ) {
            this.enableQQ = enableQQ;
            return this;
        }

        public ShareDialogManager build() {
            return new ShareDialogManager(this);
        }

    }

}
