package org.bm.screenlocker;

import android.content.Context;
import android.os.PowerManager;

/**
 * 电源锁管理类
 */
public class BatteryUtils {
    private static PowerManager.WakeLock wakeLock;

    public static void acquire(Context context) {
        try {
            if (context != null) {
                PowerManager powerManager = (PowerManager) context.getSystemService(Context
                    .POWER_SERVICE);

                wakeLock = powerManager.newWakeLock(PowerManager
                    .PARTIAL_WAKE_LOCK, "screen-locker");
                wakeLock.setReferenceCounted(false);
                wakeLock.acquire(30000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            wakeLock = null;
        }

    }

    public static void release() {
        try {
            if (wakeLock != null) {
                wakeLock.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            wakeLock = null;
        }

    }
}
