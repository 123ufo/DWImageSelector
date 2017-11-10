package com.ufo.imageselector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.ufo.imageselector.utils.FileUtils;
import com.ufo.imageselector.utils.UriUtils;

import java.io.File;

/**
 * 日期:2017/11/10
 * <p>
 * 作者:xudiwei
 * <p>
 * 描述:图片裁剪Activity
 */
public class CropActivity extends BasicActivity {

    private static final String TAG = "CropActivity";
    //数据的key，需要裁剪的图片的路径
    public static final String KEY_CROP_IMAGE_FILE_PATH = "src_path";
    //数据的key,裁剪后台图片的路径
    public static final String KEY_CROP_RESULT_PATH = "crop_path";
    //数据的key
    public static final String KEY_CROP_FILE = "crop_file";
    //数据的key,裁剪框的宽的比例
    public static final String KEY_CROP_ASPECTX = "aspectX";
    //数据的key,裁剪框的高的比例
    public static final String KEY_CROP_ASPECTY = "aspectY";
    //数据的key,裁剪后图片的宽的大小(像素)
    public static final String KEY_CROP_OUTPUTX = "outputX";
    //数据的key,裁剪后图片的高的大小(像素)
    public static final String KEY_CROP_OUTPUTY = "outputY";
    //请求码,请求图片裁剪
    private static final int REQUEST_CODE_CROP = 1113;

    private String mPath;
    private int mAspectX;
    private int mAspectY;
    private int mOutputX;
    private int mOutputY;

    private File mCropImg = null;
    private Bundle outState = null;

    @Override
    public int getContentView() {
        return R.layout.activity_crop;
    }

    @Override
    protected void initParams() {
        mPath = getIntent().getStringExtra(KEY_CROP_IMAGE_FILE_PATH);
        mAspectX = getIntent().getIntExtra(KEY_CROP_ASPECTX, 5);
        mAspectY = getIntent().getIntExtra(KEY_CROP_ASPECTY, 5);
        mOutputX = getIntent().getIntExtra(KEY_CROP_OUTPUTX, 500);
        mOutputY = getIntent().getIntExtra(KEY_CROP_OUTPUTY, 500);

        Log.d(TAG, "initParams:--> path: " + mPath);
        Log.d(TAG, "initParams:--> mAspectX: " + mAspectX);
        Log.d(TAG, "initParams:--> mAspectY: " + mAspectY);
        Log.d(TAG, "initParams:--> mOutputX: " + mOutputX);
        Log.d(TAG, "initParams:--> mOutputY: " + mOutputY);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        if (outState != null) {
            return;
        }
        Uri outPutUri = UriUtils.FileToUri(this, mPath);
        //裁剪后的图片
        mCropImg = FileUtils.getSdcardSubDirFile(this, "imgs", FileUtils.newFileName(null));
        Log.d(TAG, "mCutImg: " + mCropImg.getAbsolutePath());
        Uri inputUri = Uri.fromFile(mCropImg);
        Log.d(TAG, "imageUri: " + inputUri);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(outPutUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", mAspectX);        //裁剪框的大小比例
        intent.putExtra("aspectY", mAspectY);        //裁剪框的大小比例
        intent.putExtra("outputX", mOutputX);
        intent.putExtra("outputY", mOutputY);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, inputUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        startActivityForResult(intent, REQUEST_CODE_CROP);
    }

    @Override
    protected void initReSaveInstanceState(Bundle savedInstanceState) {
        super.initReSaveInstanceState(savedInstanceState);
        outState = savedInstanceState;
        if (null != savedInstanceState) {
            mCropImg = (File) savedInstanceState.getSerializable(KEY_CROP_RESULT_PATH);
            Log.d(TAG, "initReSaveInstanceState:--> mCropImage: " + mCropImg);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CROP_RESULT_PATH, mCropImg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CROP) {
            Log.d(TAG, "onActivityResult:--> CropImg len: " + mCropImg.length());
            Log.d(TAG, "onActivityResult:--> CropImg size: " + mCropImg.getAbsolutePath());

            setResult();
        }
    }

    private void setResult() {
        Intent intent = new Intent();
        intent.putExtra(KEY_CROP_RESULT_PATH, mCropImg.getAbsolutePath());
        setResult(0, intent);
        finish();
    }

}
