package com.ufo.imageselector;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * 日期:2017/11/3
 * <p>
 * 作者:xudiwei
 * <p>
 * 描述:图片选择入口类
 */

public final class DWImages {
    //相册选择
    public static final int ACTION_ALBUM = 0;
    //拍照
    public static final int ACTION_CAMERA = 1;
    //请求码获取图片
    private static final int REQUEST_CODE_IMAGES = 2020;
    //请求码裁剪图片
    private static final int REQUEST_CODE_CROP = 2030;


    /**
     * 获取图片
     *
     * @param activity     Activity
     * @param action       相册{@linkplain #ACTION_ALBUM} or 拍照{@linkplain #ACTION_CAMERA}
     * @param needImageLen action为 {@linkplain #ACTION_ALBUM}时,needImageLen的值可以为1到9
     *                     action为{@linkplain #ACTION_CAMERA}时,needImageLen的值只能为1
     *                     请在onActivityResult里添加
     *                     {@linkplain #parserResult(int, Intent, GetImagesCallback)}方法，获取选择后
     *                     的图片
     */
    public static void getImages(@NonNull Activity activity, int action, int needImageLen) {
        checkObjectIsNull(activity);
        checkActionRange(action);
        checkNeedImageLen(needImageLen);
        Intent intent = new Intent(activity, PhotoActivity.class);
        intent.putExtra(PhotoActivity.KEY_SELECT_MAX_LEN, needImageLen);
        intent.putExtra(PhotoActivity.KEY_ACTION, action);
        activity.startActivityForResult(intent, REQUEST_CODE_IMAGES);
    }


    /**
     * 图片裁剪
     *
     * @param activity     Activity
     * @param srcImagePath 需要裁剪的图片所在的路径
     * @param aspectX      裁剪框的X比例
     * @param aspectY      裁剪框的Y比例
     * @param outputX      裁剪后的图片的宽的大小（像素）
     * @param outputY      裁剪后的图片的高的大小（像素）
     *                     <p>
     *                     请在onActivityResult里添加
     *                     {@linkplain #parserCropResult(int, Intent, CropImageCallback)}方法，获取裁剪后
     *                     的图片
     */
    public static void cropImage(@NonNull Activity activity, @NonNull String srcImagePath
            , int aspectX, int aspectY, int outputX, int outputY) {
        Intent intent = new Intent(activity, CropActivity.class);
        intent.putExtra(CropActivity.KEY_CROP_IMAGE_FILE_PATH, srcImagePath);
        intent.putExtra(CropActivity.KEY_CROP_ASPECTX, aspectX);
        intent.putExtra(CropActivity.KEY_CROP_ASPECTY, aspectY);
        intent.putExtra(CropActivity.KEY_CROP_OUTPUTX, outputX);
        intent.putExtra(CropActivity.KEY_CROP_OUTPUTY, outputY);
        activity.startActivityForResult(intent, REQUEST_CODE_CROP);

    }

    /**
     * 对裁剪后的数据进行解析,把这个代码添加到onActivityResult里
     *
     * @param requestCode onActivityResult的请求码
     * @param intent      onActivityResult的intent
     * @param callback    回调接口,用于获取选择的图片数据
     */
    public static void parserCropResult(int requestCode, Intent intent, @NonNull CropImageCallback callback) {
        if (requestCode == REQUEST_CODE_CROP && null != intent) {
            String images = intent.getStringExtra(CropActivity.KEY_CROP_RESULT_PATH);
            callback.onResult(images);
        }
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

    public interface CropImageCallback {
        void onResult(String images);
    }
}
