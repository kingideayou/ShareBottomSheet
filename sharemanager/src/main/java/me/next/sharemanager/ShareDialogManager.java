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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.next.sharemanager.beans.ShareActivityInfo;
import me.next.sharemanager.constants.ShareConstants;
import me.next.sharemanager.interfaces.OnShareClick;
import me.next.sharemanager.utils.MapUtil;
import me.next.sharemanager.utils.ShareUtils;
import me.next.sharemanager.utils.SpUtils;

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
                SpUtils.saveAppShare(context, shareActivityInfoList.get(pos).getActivityName());
                dialog.dismiss();
                if (onShareClick == null) {
                    Log.e("ShareDialogManager", "ItemClickListener can not be null.");
                    return;
                }
                ShareActivityInfo shareActivityInfo = shareActivityInfoList.get(pos);
                String activityName = shareActivityInfo.getActivityName();
                String appName = shareActivityInfo.getAppName();

                if (enableWeChat && activityName.startsWith(ShareConstants.WeChat.WECHAT_PACKAGE)) {
                    if (activityName.equals(ShareConstants.WeChat.WECHAT_FAVORITE)) {
                        onShareClick.onPlatformClick(ShareConstants.PLATFORM_WECHAT_FAVORITE, shareActivityInfo);

                    } else if (activityName.equals(ShareConstants.WeChat.WECHAT_FRIEND)) {
                        onShareClick.onPlatformClick(ShareConstants.PLATFORM_WECHAT_FRIEND, shareActivityInfo);

                    } else if (activityName.equals(ShareConstants.WeChat.WECHAT_TIMELINE)) {
                        onShareClick.onPlatformClick(ShareConstants.PLATFORM_WECHAT_TIMELINE, shareActivityInfo);

                    }
                } else if (enableWeiBo && activityName.startsWith(ShareConstants.WeiBo.WEIBO_PACKAGE)) {
                    if (activityName.equals(ShareConstants.WeiBo.WEIBO_TIMELINE)) {
                        onShareClick.onPlatformClick(ShareConstants.PLATFORM_WEIBO_TIMELINE, shareActivityInfo);

                    }
                } else if (enableQQ && (activityName.startsWith(ShareConstants.QQ.QQ_PACKAGE)
                        || activityName.startsWith(ShareConstants.QQ.QQ_FAVIOTE_PACKAGE)))
                    if (activityName.equals(ShareConstants.QQ.QQ_FRIEND)) {
                        onShareClick.onPlatformClick(ShareConstants.PLATFORM_QQ_FRIEND, shareActivityInfo);
                    } else if (activityName.equals(ShareConstants.QQ.QQ_FAVOITE)) {
                        onShareClick.onPlatformClick(ShareConstants.PLATFORM_QQ_FAVOITE, shareActivityInfo);
                    } else if (activityName.equals(ShareConstants.QQ.QQ_FILE)) {
                        onShareClick.onPlatformClick(ShareConstants.PLATFORM_QQ_FILE, shareActivityInfo);
                    }
                else {
                    onShareClick.onPlatformClick(ShareConstants.PLATFORM_NORMAL, shareActivityInfo);
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

        List<ResolveInfo> resolveInfoList = packageManager
                .queryIntentActivities(sendIntent, 0);

        Map<String, Integer> appShareCountMap = SpUtils.getAppShareCountMap(context);

        Map<String, ShareActivityInfo> shareActivityInfoMap = new LinkedHashMap<>();
        ResolveInfo resolveInfo;
        for (int i = resolveInfoList.size() - 1; i >= 0; i--) {
            resolveInfo = resolveInfoList.get(i);

            String packageName = resolveInfo.activityInfo.packageName;
            String activityName = resolveInfo.activityInfo.name;

            useDefaultShare = !packageName.equals(ShareConstants.WeChat.WECHAT_PACKAGE) &&
                    !packageName.equals(ShareConstants.WeiBo.WEIBO_PACKAGE);

            ShareActivityInfo shareActivityInfo = new ShareActivityInfo(
                    useDefaultShare,
                    resolveInfo.loadLabel(packageManager).toString(),
                    resolveInfo.loadIcon(packageManager),
                    packageName,
                    activityName);

            shareActivityInfoMap.put(activityName, shareActivityInfo);
        }

        if (appShareCountMap.isEmpty()) {
            orderByPlatform(shareActivityInfoList, shareActivityInfoMap);
            shareActivityInfoList.addAll(shareActivityInfoMap.values());
            return shareActivityInfoList;
        } else {
            appShareCountMap = MapUtil.sortByValue(appShareCountMap);
            for (String packageName : appShareCountMap.keySet()) { //按点击数添加点击过的应用
                if (shareActivityInfoMap.containsKey(packageName)) {
                    shareActivityInfoList.add(shareActivityInfoMap.get(packageName));
                    shareActivityInfoMap.remove(packageName);
                }
            }
            orderByPlatform(shareActivityInfoList, shareActivityInfoMap);
            if (!shareActivityInfoMap.isEmpty()) { //添加未点击过的应用
                shareActivityInfoList.addAll(shareActivityInfoMap.values());
            }
            return shareActivityInfoList;
        }

    }

    private void orderByPlatform(List<ShareActivityInfo> shareActivityInfoList, Map<String, ShareActivityInfo> shareActivityInfoMap) {
        List<String> packageNameList = new ArrayList<>();
        for (String packageName : shareActivityInfoMap.keySet()) {
            if (enableQQ) {
                switch (packageName) {
                    case ShareConstants.QQ.QQ_FAVIOTE_PACKAGE:
                        shareActivityInfoList.add(shareActivityInfoMap.get(ShareConstants.QQ.QQ_FAVIOTE_PACKAGE));
                        packageNameList.add(ShareConstants.QQ.QQ_FAVIOTE_PACKAGE);
                        break;
                    case ShareConstants.QQ.QQ_FAVOITE:
                        shareActivityInfoList.add(shareActivityInfoMap.get(ShareConstants.QQ.QQ_FAVOITE));
                        packageNameList.add(ShareConstants.QQ.QQ_FAVOITE);
                        break;
                    case ShareConstants.QQ.QQ_FILE:
                        shareActivityInfoList.add(shareActivityInfoMap.get(ShareConstants.QQ.QQ_FILE));
                        packageNameList.add(ShareConstants.QQ.QQ_FILE);
                        break;
                    case ShareConstants.QQ.QQ_FRIEND:
                        shareActivityInfoList.add(shareActivityInfoMap.get(ShareConstants.QQ.QQ_FRIEND));
                        packageNameList.add(ShareConstants.QQ.QQ_FRIEND);
                        break;
                }
            }
            if (enableWeChat) {
                switch (packageName) {
                    case ShareConstants.WeChat.WECHAT_FAVORITE:
                        shareActivityInfoList.add(shareActivityInfoMap.get(ShareConstants.WeChat.WECHAT_FAVORITE));
                        packageNameList.add(ShareConstants.WeChat.WECHAT_FAVORITE);
                        break;
                    case ShareConstants.WeChat.WECHAT_FRIEND:
                        shareActivityInfoList.add(shareActivityInfoMap.get(ShareConstants.WeChat.WECHAT_FRIEND));
                        packageNameList.add(ShareConstants.WeChat.WECHAT_FRIEND);
                        break;
                    case ShareConstants.WeChat.WECHAT_TIMELINE:
                        shareActivityInfoList.add(shareActivityInfoMap.get(ShareConstants.WeChat.WECHAT_TIMELINE));
                        packageNameList.add(ShareConstants.WeChat.WECHAT_TIMELINE);
                        break;
                }
            }
            if (enableWeiBo) {
                if (packageName.equals(ShareConstants.WeiBo.WEIBO_TIMELINE)) {
                    shareActivityInfoList.add(shareActivityInfoMap.get(ShareConstants.WeiBo.WEIBO_TIMELINE));
                    packageNameList.add(ShareConstants.WeiBo.WEIBO_TIMELINE);
                }
            }
        }
        for (String s : packageNameList) {
            shareActivityInfoMap.remove(s);
        }
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
