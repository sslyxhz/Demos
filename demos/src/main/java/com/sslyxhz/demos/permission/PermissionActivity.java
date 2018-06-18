package com.sslyxhz.demos.permission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.sslyxhz.demos.R;

/**
 * author: xh.zeng
 * email: zengxinhong@meituan.com
 * date: 2018/1/8
 */

public class PermissionActivity extends AppCompatActivity {
    private final static String TAG = PermissionActivity.class.getSimpleName();
    private final static String[] permissionList = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        Log.d(TAG, "onCreate");
        requestPermission();
    }

    private void requestPermission(){
        Log.d(TAG, "requestPermission");
        PermissionUtils.requestPermissions(this, 1088, permissionList, new PermissionUtils.OnPermissionListener() {

            @Override
            public void onPermissionAllow() {
                Log.d(TAG, "onPermissionAllow");
                Toast.makeText(PermissionActivity.this, "onPermissionAllow", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionBan(String[] deniedPermissions) {
                Log.d(TAG, "onPermissionBan, "+(deniedPermissions!=null?deniedPermissions.length:0));
                Toast.makeText(PermissionActivity.this, "onPermissionBan", Toast.LENGTH_LONG).show();
//                finish();
            }

            @Override
            public void onPermissionPermanentBan(String[] deniedPermissions) {
                Toast.makeText(PermissionActivity.this, "onPermissionPermanentBan", Toast.LENGTH_LONG).show();
                Log.d(TAG, "2 onPermissionPermanentBan"+(deniedPermissions!=null?deniedPermissions.length:0));
                DialogUtils.showRequestSetting(PermissionActivity.this);
            }
        });
    }
}
