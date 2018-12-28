package org.bm.screenlocker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.eros.framework.BMWXEnvironment;
import com.eros.framework.constant.Constant;
import com.eros.framework.model.RouterModel;

import org.bm.screenlocker.ScreenLockerApi;
import org.bm.screenlocker.ui.ScreenLockerActivity;
import org.bm.screenlocker.utils.TelephonyUtil;

public class ScreenLockerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        /*if (action.equals(Intent.ACTION_SCREEN_ON)) {
            LockManager.createLockView();
            onPhoneRing();
        }*/
        if (action.equals(Intent.ACTION_SCREEN_OFF) && !TelephonyUtil.isTelephonyCalling(context)) {
            String lockPath = ScreenLockerApi.getInstance().getPath();
            if (TextUtils.isEmpty(lockPath)) return;
            RouterModel router = new RouterModel(lockPath, Constant.ACTIVITIES_ANIMATION
                    .ANIMATION_PUSH, null, null, false, "Default");
            Intent mLockIntent = new Intent(context, ScreenLockerActivity.class);
            mLockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            mLockIntent.putExtra(Constant.ROUTERPARAMS, router);
            Uri pathUri = Uri.parse(lockPath);
            if (!TextUtils.equals("http", pathUri.getScheme()) && !TextUtils.equals("https", pathUri
                    .getScheme())) {
                pathUri = Uri.parse(BMWXEnvironment.mPlatformConfig.getUrl().getJsServer() +
                        "/dist/js" + lockPath);
            }
            mLockIntent.setData(pathUri);
            context.startActivity(mLockIntent);
        }
    }
}
