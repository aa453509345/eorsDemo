package org.bm.screenlocker.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eros.framework.activity.AbstractWeexActivity;
import com.eros.framework.utils.BMHookGlide;
import com.taobao.weex.RenderContainer;
import com.taobao.weex.WXSDKInstance;

import org.bm.screenlocker.R;
import org.bm.screenlocker.ScreenLockerApi;
import org.bm.screenlocker.utils.AndroidBug5497Workaround;
import org.bm.screenlocker.utils.SoftKeyInputHidWidget;
import org.bm.screenlocker.utils.SoftKeyboardFixerForFullscreen;
import org.bm.screenlocker.widget.SwipeUnLockLayout;

public class ScreenLockerActivity extends AbstractWeexActivity implements SwipeUnLockLayout
        .OnSildingFinishListener {
    private boolean isFullScreen;
    private ImageView mBackGround;
    private boolean hasBackGround = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.activity_screen_locker);
        mContainer = (SwipeUnLockLayout) findViewById(R.id.swipe_layout);
        mBackGround = (ImageView) findViewById(R.id.iv_backGround);
        ((SwipeUnLockLayout) mContainer).setOnSildingFinishListener(this);
        ((SwipeUnLockLayout) mContainer).setTouchView(getWindow().getDecorView());
        renderPage();
        isFullScreen = mRouterParam != null && !mRouterParam.navShow;
        if (isFullScreen) {
            SoftKeyboardFixerForFullscreen.assistActivity(this);
        }
        if (!TextUtils.isEmpty(ScreenLockerApi.getInstance().getBackgroundPath())) {
            BMHookGlide.load(this, ScreenLockerApi.getInstance().getBackgroundPath()).into
                    (mBackGround);
            hasBackGround = true;
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        super.onViewCreated(instance, view);
        if (view instanceof RenderContainer && hasBackGround) {
            RenderContainer container = (RenderContainer) view;
            int childCount = container.getChildCount();
            if (childCount > 0) {
                container.getChildAt(0).setBackgroundColor(Color.parseColor("#00000000"));
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

//    private void setView() {
//        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
//            View v = this.getWindow().getDecorView();
//            v.setSystemUiVisibility(View.GONE);
//        } else if (Build.VERSION.SDK_INT >= 19) {
//            //for new api versions.
//            View decorView = getWindow().getDecorView();
//            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View
// .SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
//            decorView.setSystemUiVisibility(uiOptions);
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//
//    }


    @Override
    public void onSildingFinish() {
       finish();
    }
}
