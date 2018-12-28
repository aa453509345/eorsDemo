package org.bm.screenlocker.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public class TelephonyUtil {
    public static boolean isTelephonyCalling(Context context) {
        boolean calling = false;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        if (TelephonyManager.CALL_STATE_OFFHOOK == telephonyManager.getCallState() ||
                TelephonyManager.CALL_STATE_RINGING == telephonyManager.getCallState()) {
            calling = true;
        }
        return calling;
    }
}
