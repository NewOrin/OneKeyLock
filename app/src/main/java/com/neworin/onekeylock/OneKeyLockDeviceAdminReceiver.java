package com.neworin.onekeylock;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by NewOrin Zhang on 2016/7/2.
 * E-Mail : NewOrinZhang@Gmail.com
 */
public class OneKeyLockDeviceAdminReceiver extends DeviceAdminReceiver {
    private static String TAG = "OneKeyLockDeviceAdminReceiver";
    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        Log.d(TAG,"锁屏成功!");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);

    }
}
