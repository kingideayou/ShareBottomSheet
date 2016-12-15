package me.next.sharehelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import me.next.sharemanager.beans.ShareActivityInfo;
import me.next.sharemanager.ShareDialogManager;
import me.next.sharemanager.utils.ShareUtils;
import me.next.sharemanager.constants.ShareConstants;
import me.next.sharemanager.interfaces.OnShareClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                });
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
