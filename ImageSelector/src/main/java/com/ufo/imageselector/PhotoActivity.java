package com.ufo.imageselector;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ufo.imageselector.adapter.AlbumAdapter;
import com.ufo.imageselector.adapter.BasicAdapter;
import com.ufo.imageselector.model.ImageHelper;
import com.ufo.imageselector.model.entity.ImageEntity;
import com.ufo.imageselector.utils.FileUtils;
import com.ufo.imageselector.utils.UriUtils;
import com.ufo.imageselector.widget.TitleBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 日期:2017/11/1
 * <p>
 * 作者:xudiwei
 * <p>
 * 描述:相册选择页面
 */
public class PhotoActivity extends BasicActivity {
    private static final String TAG = "PhotoActivity";
    private RecyclerView mRecyclerView;
    private List<ImageEntity> mList = new ArrayList<>();
    //请求码,跳到选择图片页面的请求码.
    private static final int REQUEST_CODE_IMG = 1111;
    //请求码,跳到拍照页面的请求码.
    private static final int REQUEST_CODE_CAMERA = 1112;
    //数据的key.用于获取从选择图片返回来的数据.
    public static final String KEY_DATA = "data";
    //最多可以选择的图片的数量
    public static final String KEY_SELECT_MAX_LEN = "select_max_len";
    //数据的key.用于获取状态保存的拍照图片文件
    private static final String KEY_CAMERA_FILE = "camera_file";
    private File mCameraFile;
    //最多可选的文件数量
    private int selectMaxLen;
    private AlbumAdapter mAlbumAdapter;
    //列的数量
    private static final int COLUMN = 3;

    @Override
    public int getContentView() {
        return R.layout.activity_photo;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (null != savedInstanceState) {
            mCameraFile = (File) savedInstanceState.getSerializable(KEY_CAMERA_FILE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CAMERA_FILE, mCameraFile);
    }

    @Override
    protected void initView() {
        TitleBar titleBar = setTitleBar(R.id.titleBar, getString(R.string.text_photo), true, false, false, new MyOnTitleBarAllClickListener());
        titleBar.setTvRightText(getString(R.string.text_confrim));
        mRecyclerView = findId(R.id.rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, COLUMN));
        mAlbumAdapter = new AlbumAdapter(this, mList);
        mAlbumAdapter.setItemSize(calculateItemSize(COLUMN));
        mAlbumAdapter.setOnItemClickListener(new MyOnItemClickListener());
        mRecyclerView.setAdapter(mAlbumAdapter);
    }


    @Override
    protected void initData() {
        selectMaxLen = getIntent().getIntExtra(KEY_SELECT_MAX_LEN, 1);
        Log.d(TAG, "initData:--> selectMaxLen: " + selectMaxLen);

        //获取相册集
        new ImageHelper().getAlbum(this, true, new ImageHelper.OnImageFetchCallback() {
            @Override
            public void onSuccess(List<ImageEntity> list) {
                mList.clear();
                mList.add(addTakePhotoFuc());
                mList.addAll(list);
                mAlbumAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String errorMsg) {
                Log.d(TAG, "onFailed:--> errorMsg: " + errorMsg);
            }
        });
    }

    //添加一条拍照功能的Item
    private ImageEntity addTakePhotoFuc() {
        return new ImageEntity("R.mipmap.ic_camera.png", "拍照");
    }


    //跳到图片选择页面
    private void launcherToImageSelectActivity(String album, int needSelectLen) {
        Intent intent = new Intent(this, ImageSelectActivity.class);
        intent.putExtra(ImageSelectActivity.KEY_ALBUM, album);
        intent.putExtra(ImageSelectActivity.KEY_SELECT_MAX_LEN, needSelectLen);
        startActivityForResult(intent, REQUEST_CODE_IMG);
    }

    //跳到拍照页面
    private void launcherToCameraActivity() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mCameraFile = FileUtils.newFileOnExternalCacheDir(this, FileUtils.newFileName(null));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, UriUtils.FileToUri(this, mCameraFile));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMG && null != data) {
            List<String> list = (List<String>) data.getSerializableExtra(KEY_DATA);
            setResultAndFinish((ArrayList<String>) list);
        } else if (requestCode == REQUEST_CODE_CAMERA) {
            Log.d(TAG, "onActivityResult:--> file: " + mCameraFile.getAbsolutePath());
            ArrayList<String> cameraList = new ArrayList<>(1);
            cameraList.add(mCameraFile.getAbsolutePath());
            setResultAndFinish(cameraList);
        }
    }

    //返回获取的图片集
    private void setResultAndFinish(ArrayList<String> list) {
        Intent intent = new Intent();
        intent.putExtra(KEY_DATA, list);
        setResult(0, intent);
        finish();
    }

    private class MyOnTitleBarAllClickListener implements TitleBar.OnTitleBarAllClickListener {
        @Override
        public void onRightTextButtonClick(View view) {

        }

        @Override
        public void onLeftButtonClick(View view) {
            finish();
        }

        @Override
        public void onRightButtonClick(View view) {

        }
    }

    private class MyOnItemClickListener implements BasicAdapter.OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            if (position == 0) {
                launcherToCameraActivity();
            } else {
                String directory = mList.get(position).getDirectory();
                launcherToImageSelectActivity(directory, selectMaxLen);
            }

        }
    }
}
