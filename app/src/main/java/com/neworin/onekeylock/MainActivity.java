package com.neworin.onekeylock;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

    private DevicePolicyManager devicePolicyManager;
    private ComponentName componentName;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1,获取设备管理接收者
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        //2,申请权限
        //OneKeyLockDeviceAdminReceiver继承了DeviceAdminReceiver
        componentName = new ComponentName(this, OneKeyLockDeviceAdminReceiver.class);
        //3,实现锁屏
        lockScreen();
    }

    /**
     * 锁屏
     */
    private void lockScreen() {
        //判断该组件是否有系统管理员的权限
        if (devicePolicyManager.isAdminActive(componentName)) {
            Log.d(TAG, "权限已获取");
            //已获得管理员的权限，则直接锁屏
            devicePolicyManager.lockNow();
            //锁屏之后杀掉我们自己的Activity，避免资源的浪费
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            //没有管理员的权限，则获取管理员的权限
            Log.d(TAG, "没有权限,需要获取");
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            //会在激活界面中显示的额外内容
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.active_string));
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            devicePolicyManager.lockNow();
        } else {
            Toast.makeText(this, R.string.failActivation, Toast.LENGTH_LONG).show();
        }
        //锁屏之后杀掉我们自己的Activity，避免资源的浪费
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}