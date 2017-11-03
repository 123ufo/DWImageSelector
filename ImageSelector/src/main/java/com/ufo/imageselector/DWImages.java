package com.ufo.imageselector;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * 日期:2017/11/3
 * <p>
 * 作者:xudiwei
 * <p>
 * 描述:图片入口类
 */

public final class DWImages {
    //相册选择
    public static final int ACTION_ALBUM = 0;
    //拍照
    public static final int ACTION_CAMERA = 1;
    //请求码获取图片
    public static final int REQUEST_CODE_IMAGES = 2020;


    /**
     * 获取图片
     *
     * @param activity     Activity
     * @param action       相册{@linkplain #ACTION_ALBUM} or 拍照{@linkplain #ACTION_CAMERA}
     * @param needImageLen action为 {@linkplain #ACTION_ALBUM}时,needImageLen的值可以为1到9
     *                     action为{@linkplain #ACTION_CAMERA}时,needImageLen的值只能为1
     */
    public static void getImages(@NonNull Activity activity, int action, @Nullable int needImageLen) {
        checkObjectIsNull(activity);
        checkActionRange(action);
        checkNeedImageLen(needImageLen);
        Intent intent = new Intent(activity, PhotoActivity.class);
        intent.putExtra(PhotoActivity.KEY_SELECT_MAX_LEN, needImageLen);
        activity.startActivityForResult(intent, REQUEST_CODE_IMAGES);
    }


    /**
     * 对数据进行解析, 把这个代码添加到onActivityResult里
     *
     * @param requestCode onActivityResult的请求码
     * @param intent      onActivityResult的intent
     * @param callback    回调接口,用于获取选择的图片数据
     */
    public static void parserResult(int requestCode, Intent intent, @NonNull GetImagesCallback callback) {
        if (requestCode == REQUEST_CODE_IMAGES && null != intent) {
            List<String> images = (List<String>) intent.getSerializableExtra(PhotoActivity.KEY_DATA);
            callback.onResult(images);
        }
    }

    private static void checkNeedImageLen(int needImageLen) {
        if (needImageLen < 1 || needImageLen > 9) {
            throw new IllegalArgumentException("needImageLen must 1 to 9 ranged, but you input is: " + needImageLen);
        }
    }

    private static void checkActionRange(int action) {
        if (!(action == ACTION_CAMERA || action == ACTION_ALBUM)) {
            throw new IllegalArgumentException("action need ACTION_ALBUM or ACTION_CAMERA, but you input is:" + action);
        }
    }

    private static void checkObjectIsNull(Activity activity) {
        if (null == activity) {
            throw new NullPointerException("Activity is empty");
        }
    }

    public interface GetImagesCallback {
        void onResult(List<String> images);
    }
}
