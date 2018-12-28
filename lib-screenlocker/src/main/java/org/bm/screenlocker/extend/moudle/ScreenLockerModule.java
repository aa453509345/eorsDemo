package org.bm.screenlocker.extend.moudle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.alibaba.weex.plugin.annotation.WeexModule;
import com.eros.framework.BMWXApplication;
import com.eros.framework.BMWXEnvironment;
import com.eros.framework.utils.BMHookGlide;
import com.eros.framework.utils.JsPoster;
import com.eros.framework.utils.SharePreferenceUtil;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import org.bm.screenlocker.BatteryUtils;
import org.bm.screenlocker.ScreenLockerApi;
import org.bm.screenlocker.constant.Constant;
import org.bm.screenlocker.ui.ScreenLockerActivity;

@WeexModule(name = "screenLocker", lazyLoad = true)
public class ScreenLockerModule extends WXModule {

    /**
     * @param lockPath 锁屏页面的路径
     */
    @JSMethod(uiThread = true)
    public void lock(String lockPath) {
        if (TextUtils.isEmpty(lockPath)) return;
        SharePreferenceUtil.putStringExtra(mWXSDKInstance.getContext(), Constant.SP_LOCK,
                "1");
        ScreenLockerApi.getInstance().activeLock(mWXSDKInstance.getContext());
        ScreenLockerApi.getInstance().setPath(lockPath);
    }


    @JSMethod(uiThread = true)
    public void setLockBackground(String backgroundPath) {
        if (TextUtils.isEmpty(backgroundPath)) return;
        ScreenLockerApi.getInstance().setBackgroundPath(backgroundPath);
        //预加背景
        BMHookGlide.load(BMWXApplication.getWXApplication(), backgroundPath).preload();
    }


    @JSMethod(uiThread = true)
    public void unLock() {
        SharePreferenceUtil.putStringExtra(BMWXEnvironment.mApplicationContext, Constant.SP_LOCK,
                "");
        ScreenLockerApi.getInstance().inactive(mWXSDKInstance.getContext());
    }

    @JSMethod(uiThread = true)
    public void getLockerStatus(JSCallback jsCallback) {
        String status = SharePreferenceUtil.getStringExtra(mWXSDKInstance.getContext(),
                Constant.SP_LOCK, "");
        JsPoster.postSuccess("1".equals(status) ? "ACTIVE" : "INACTIVE", jsCallback);
    }

    @JSMethod(uiThread = true)
    public void acquireWakeLock() {
        BatteryUtils.acquire(mWXSDKInstance.getContext());
    }

    @JSMethod(uiThread = true)
    public void releaseWakeLock() {
        BatteryUtils.release();
    }
}
