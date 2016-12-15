# ShareHelper

实现系统分享效果（可分享到朋友圈），微信、微博、QQ 分享效果可自定义。

支持 app 按点击数排序。  

## How to Use
      new ShareDialogManager.Builder()
                    .enableWeChat(true)
                    .enableQQ(true)
                    .enableWeiBo(true)
                    .build().showShareTextDialog(MainActivity.this,
                    new OnShareClick() {
                        @Override
                        public void onPlatformClick(int platform, ShareActivityInfo shareActivityInfo) {
                            switch (platform) {
                                case ShareConstants.PLATFORM_WECHAT_FAVORITE:
                                    showToast("微信收藏");
                                    break;
                                case ShareConstants.PLATFORM_WECHAT_FRIEND:
                                    showToast("微信好友");
                                    break;
                                case ShareConstants.PLATFORM_WECHAT_TIMELINE:
                                    showToast("微信朋友圈");
                                    break;
                                case ShareConstants.PLATFORM_WEIBO_TIMELINE:
                                    showToast("微博");
                                    break;
                                case ShareConstants.PLATFORM_QQ_FRIEND:
                                    showToast("QQ 好友");
                                    break;
                                case ShareConstants.PLATFORM_QQ_FILE:
                                    showToast("QQ 文件");
                                    break;
                                case ShareConstants.PLATFORM_QQ_FAVOITE:
                                    showToast("QQ 收藏");
                                    break;
                                case ShareConstants.PLATFORM_NORMAL:
                                    ShareUtils.startDefaultShareTextIntent(MainActivity.this, shareActivityInfo, "要分享的文字");
                                    break;
                            }
                        }


## 三列效果
![](http://ww3.sinaimg.cn/mw690/6db4aff6jw1fariapn5rqj20u01hck0q.jpg)

## 四列效果
![](http://ww3.sinaimg.cn/mw690/6db4aff6jw1farib932gyj21401z44cz.jpg)

## 展开效果
![](http://ww4.sinaimg.cn/mw690/6db4aff6jw1farib2nvv4j21401z47g4.jpg)
