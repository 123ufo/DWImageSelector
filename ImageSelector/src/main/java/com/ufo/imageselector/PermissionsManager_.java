package com.ufo.imageselector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by: UFO on: 2016/11/25.
 * <p>
 * 描述：Android 6.0权限工具类
 */

public class PermissionsManager_ implements Serializable {

    private static final String TAG = "PermissionsManager_";
    /*权限请求码*/
    public static final int REQUEST_CODE_PERMISSIONS = 10001;
    /*应用设置页面请求码*/
    public static final int REQUEST_CODE_SETTING = 10002;
    private OnPermissionsCallback mCallback;
    private String[] permissions;
    private Activity mActivity;
    private AlertDialog mAlertDialog;

    public PermissionsManager_(Activity activity) {
        mActivity = activity;
    }

    /**
     * 设置回调
     *
     * @param callback
     */
    public void setPermissionCallback(OnPermissionsCallback callback) {
        this.mCallback = callback;
    }

    public String[] getPermissions() {
        return permissions;
    }

    /**
     * 设置需要申请的权限
     *
     * @param permissions
     */
    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    /**
     * 权限请求，结果会在@see {@link #resultPermissionsProcess(Activity, int, String[], int[])}进行处理
     *
     * @param permissionsArr
     * @deprecated @see requestPermission();
     */
    public void requestPermission(String[] permissionsArr) {
        if (!isM()) {
            mCallback.hasPermissions();
            return;
        }
//        this.permissions = permissionsArr;
        /*判断是否有权限*/
        if (!hasSelfPermission(mActivity, this.permissions)) {
            ActivityCompat.requestPermissions(mActivity, this.permissions, REQUEST_CODE_PERMISSIONS);
        } else {
            if (null != mCallback) {
                mCallback.hasPermissions();
            }
        }
    }

    /**
     * 发起权限申请,
     * 设置权限通过@see setPermission(permissions);
     */
    public void requestPermission() {
        Log.d(TAG, "requestPermission:--> Permission: "+this.permissions.length);
        this.requestPermission(null);
    }


    /**
     * 权限请求结果处理，处理@see {@link #requestPermission(String[])}的请求结果
     * 此方法应在{@link android.support.v4.app.FragmentActivity#onRequestPermissionsResult(int, String[], int[])}
     * 里调用
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void resultPermissionsProcess(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            boolean hasAllPermissions = true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    hasAllPermissions = false;
                    break;
                }
            }

            if (null != mCallback) {
                if (hasAllPermissions) {
                    mCallback.hasPermissions();
                } else {
                    mCallback.noPermissions();
                }
            }


        }
    }

   /*if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {
        //第二次申请
        requestPermission(new String[]{permissions[i]});
        return;
    } else {
        //当第二次申请还是不允许的话就走这里了
        if (null != mCallback) {
            mCallback.noPermissions();
        }
        return;
    }*/

    /**
     * 打开设置界面返回后处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SETTING) {
            requestPermission(this.permissions);
        }
    }

    /**
     * 判断当前系统是不是Android 6.0.因为权限申请只有6.0以上（包括6.0才需要）
     *
     * @return 如果是返回true, 否则返回false.
     */
    private boolean isM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 判断是否有指定需要请求的权限，只有有一个没有就返回false.否则返回true
     *
     * @param context
     * @param permissionsArr
     */
    private boolean hasSelfPermission(Context context, String[] permissionsArr) {
        for (int i = 0; i < permissionsArr.length; i++) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, permissionsArr[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示打开应用的设置页面对话框
     *
     * @param context
     */
    public void showOpenSettingDialog(final Activity context) {

        if (null != mAlertDialog && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
        mAlertDialog = new AlertDialog.Builder(context).setTitle("提示")
                .setMessage("当前应用所需要的权限已经被你禁用，你只能找到-权限管理，然后手动打开所需的权限")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        openSettingActivity(context);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        context.finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    /**
     * 显示打开应用的设置页面对话框 只有一个按钮的
     *
     * @param context
     */
    public void showOpenSettingDialogSingle(final Activity context) {
        if (null != mAlertDialog && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
        mAlertDialog = new AlertDialog.Builder(context).setTitle("提示")
                .setMessage("当前应用所需要的权限已经被你禁用，你只能找到-权限管理，然后手动打开所需的权限")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openSettingActivity(context);
                    }
                })
                .show();
    }


    private void openSettingActivity(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + activity.getPackageName()));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        activity.startActivityForResult(intent, REQUEST_CODE_SETTING);
    }

    /**
     * 是否有权限回调，只有在有权限时才会执行hasPermissions()方法。
     * 当没有权限时就直接向系统申请权限
     */
    public interface OnPermissionsCallback {
        /**
         * 当前权限时回调此方法
         */
        void hasPermissions();

        /**
         * 当多次申请后还是没有拿到权限时回调此方法
         */
        void noPermissions();
    }
}
