package org.bm.screenlocker;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.eros.framework.utils.SharePreferenceUtil;
import com.xdandroid.hellodaemon.DaemonEnv;

import org.bm.screenlocker.biz.ScreenLockerBiz;
import org.bm.screenlocker.constant.Constant;

public class ScreenLockerApi {
    private String mPath;
    private String mBackgroundPath;

    public static ScreenLockerApi getInstance() {
        return Singleton.mInstance;
    }


    public String getPath() {
        return mPath;
    }


    public void setPath(String path) {
        this.mPath = path;
    }

    public String getBackgroundPath() {
        return mBackgroundPath;
    }

    public void setBackgroundPath(String mBackgroundPath) {
        this.mBackgroundPath = mBackgroundPath;
    }

    public void init(Context context) {
        DaemonEnv.initialize(context, ScreenLockerBiz.class, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
    }

    public void activeLock(Context context) {
        if (context == null) return;
        if (TextUtils.isEmpty(SharePreferenceUtil.getStringExtra(context, Constant.SP_LOCK, "")))
            return;
        ScreenLockerBiz.sShouldStopService = false;
        DaemonEnv.startServiceMayBind(ScreenLockerBiz.class);
    }


    public void inactive(Context context) {
        if (context == null) return;
        ScreenLockerBiz.stop();
        BatteryUtils.release();
    }


    private static class Singleton {
        private static ScreenLockerApi mInstance = new ScreenLockerApi();
    }
}
