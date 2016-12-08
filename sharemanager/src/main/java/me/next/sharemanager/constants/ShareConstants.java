package me.next.sharemanager.constants;

/**
 * Created by NeXT on 16/11/18.
 */

public class ShareConstants {

    /**
     * 微信好友
     */
    public static final int PLATFORM_WECHAT_FRIEND = 1;
    /**
     * 微信朋友圈
     */
    public static final int PLATFORM_WECHAT_TIMELINE = 2;
    /**
     * 添加收藏
     */
    public static final int PLATFORM_WECHAT_FAVORITE = 3;
    /**
     * 微博
     */
    public static final int PLATFORM_WEIBO_TIMELINE = 4;

    /**
     * QQ 好友
     */
    public static final int PLATFORM_QQ_FRIEND = 5;
    /**
     * QQ 收藏
     */
    public static final int PLATFORM_QQ_FAVOITE = 6;
    /**
     * QQ 文件
     */
    public static final int PLATFORM_QQ_FILE = 7;

    /**
     * 不需要特殊处理的应用
     */
    public static final int PLATFORM_NORMAL = 0;


    /**
     * 腾讯 QQ
     * E: com.tencent.mobileqq
     * E: com.tencent.mobileqq.activity.JumpActivity
     * E: -------------------------------
     * E: com.tencent.mobileqq
     * E: com.tencent.mobileqq.activity.qfileJumpActivity
     * E: -------------------------------
     * E: com.tencent.mobileqq
     * E: cooperation.qqfav.widget.QfavJumpActivity
     */

    /**
     * 微信
     * E: com.tencent.mm
     * E: com.tencent.mm.ui.tools.ShareImgUI
     * E: -------------------------------
     * E: com.tencent.mm
     * E: com.tencent.mm.ui.tools.AddFavoriteUI
     * E: -------------------------------
     * E: com.tencent.mm
     * E: com.tencent.mm.ui.tools.ShareToTimeLineUI
     */

    public static final class WeChat {
        public static final String WECHAT_PACKAGE = "com.tencent.mm";
        public static final String WECHAT_FRIEND = "com.tencent.mm.ui.tools.ShareImgUI";
        public static final String WECHAT_FAVORITE = "com.tencent.mm.ui.tools.AddFavoriteUI";
        public static final String WECHAT_TIMELINE = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
    }

    /**
     * 新浪微博
     * E: com.sina.weibo
     * E: com.sina.weibo.composerinde.ComposerDispatchActivity
     */
    public static final class WeiBo {
        public static final String WEIBO_PACKAGE = "com.sina.weibo";
        public static final String WEIBO_TIMELINE = "com.sina.weibo.composerinde.ComposerDispatchActivity";
    }

    public static final class QQ {
        public static final String QQ_PACKAGE = "com.tencent.mobileqq";
        public static final String QQ_FAVIOTE_PACKAGE = "cooperation.qqfav";
        public static final String QQ_FRIEND = "com.tencent.mobileqq.activity.JumpActivity";
        public static final String QQ_FAVOITE = "cooperation.qqfav.widget.QfavJumpActivity";
        public static final String QQ_FILE = "com.tencent.mobileqq.activity.qfileJumpActivity";
    }

}
