package org.bm.screenlocker.biz;

import android.os.IBinder;

public interface IScreenLocker extends IBinder {
    void stopService();
}
