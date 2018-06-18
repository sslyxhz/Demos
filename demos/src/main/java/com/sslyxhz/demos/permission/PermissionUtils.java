package com.sslyxhz.demos.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.targetSdkVersion;

/**
 * author: xh.zeng
 * email: zengxinhong@meituan.com
 * date: 2018/1/8
 */

public class PermissionUtils {

    private static int mRequestCode = -1;

    private static OnPermissionListener mOnPermissionListener;

    public interface OnPermissionListener {

        /**
         * 权限允许
         */
        void onPermissionAllow();

        /**
         * 权限禁止
         * @param deniedPermissions
         */
        void onPermissionBan(String[] deniedPermissions);

        /**
         * 权限永久禁止
         * @param deniedPermissions
         */
        void onPermissionPermanentBan(String[] deniedPermissions);
    }

    public abstract static class RationaleHandler {
        private Context context;
        private int requestCode;
        private String[] permissions;

        /**
         * 权限说明
         */
        protected abstract void showRationale();

        void showRationale(Context context, int requestCode, String[] permissions) {
            this.context = context;
            this.requestCode = requestCode;
            this.permissions = permissions;
            showRationale();
        }

        /**
         * 待权限说明处理可重新调用权限
         */
        @TargetApi(Build.VERSION_CODES.M)
        public void requestPermissionsAgain() {
            ((Activity) context).requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void requestPermissions(Context context, int requestCode
            , String[] permissions, OnPermissionListener listener) {
        requestPermissions(context, requestCode, permissions, listener, null);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void requestPermissions(Context context, int requestCode
            , String[] permissions, OnPermissionListener listener, RationaleHandler handler) {
        if (context instanceof Activity) {
            mRequestCode = requestCode;
            mOnPermissionListener = listener;
            String[] deniedPermissions = getDeniedPermissions(context, permissions);
            if (deniedPermissions.length > 0) {
                boolean rationale = shouldShowRequestPermissionRationale(context, deniedPermissions);
                if (rationale && handler != null) {
                    handler.showRationale(context, requestCode, deniedPermissions);
                } else {
                    ((Activity) context).requestPermissions(deniedPermissions, requestCode);
                }
            } else {
                if (mOnPermissionListener != null)
                    mOnPermissionListener.onPermissionAllow();
            }
        } else {
            throw new RuntimeException("Context must be an Activity");
        }
    }

    /**
     * 请求权限结果，需要在调用Activity调用onRequestPermissionsResult()方法
     */
    public static void onRequestPermissionsResult(Activity context, int requestCode, String[] permissions, int[]
            grantResults) {
        if (mRequestCode != -1 && requestCode == mRequestCode) {
            if (mOnPermissionListener != null) {
                String[] deniedPermissions = getDeniedPermissions(context, permissions);
                if (deniedPermissions.length > 0) {
                    String[] banPermission = getPermanentBanPermission(context,deniedPermissions);
                    if (banPermission.length > 0) {
                        mOnPermissionListener.onPermissionPermanentBan(banPermission);
                    } else {
                        mOnPermissionListener.onPermissionBan(banPermission);
                    }
                } else {
                    mOnPermissionListener.onPermissionAllow();
                }
            }
        }
    }

    /**
     * 获取请求权限中需要授权的权限
     */
    private static String[] getDeniedPermissions(Context context, String[] permissions) {
        Log.d("PermissionUtils","getDeniedPermissions");
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            int checkPermissionResult = ContextCompat.checkSelfPermission(context, permission);
            Log.d("PermissionUtils","getDeniedPermissions:["+permission+":"+checkPermissionResult+"]");
            if (checkPermissionResult == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions.toArray(new String[deniedPermissions.size()]);
    }

    /**
     * 是否彻底拒绝了某项权限
     */
    public static boolean hasPermanentBanPermission(Context context, String... deniedPermissions) {
        for (String deniedPermission : deniedPermissions) {
            if (!shouldShowRequestPermissionRationale(context, deniedPermission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取永久禁止的权限
     * @param context
     * @param deniedPermissions
     * @return
     */
    public static String[] getPermanentBanPermission(Context context, String... deniedPermissions) {
        List<String> banList = new ArrayList<>();
        for (String banPermission : deniedPermissions) {
            if (!shouldShowRequestPermissionRationale(context, banPermission)) {
                banList.add(banPermission);
            }
        }
        return banList.toArray(new String[banList.size()]);
    }

    /**
     * 是否有权限需要说明提示
     */
    private static boolean shouldShowRequestPermissionRationale(Context context, String... deniedPermissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false;
        boolean rationale;
        for (String permission : deniedPermissions) {
            rationale = ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission);
            if (rationale) return true;
        }
        return false;
    }

    public static boolean checkSelfPermissionGranted(Context context, String permission) {
        // For Android < Android M, self permissions are always granted.
        boolean result = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                // targetSdkVersion >= Android M, we can
                // use Context#checkSelfPermission
                result = context.checkSelfPermission(permission)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                // targetSdkVersion < Android M, we have to use PermissionChecker
                result = PermissionChecker.checkSelfPermission(context, permission)
                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }

        return result;
    }
}
