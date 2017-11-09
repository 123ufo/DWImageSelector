package com.ufo.imageselector;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ufo.imageselector.adapter.BasicAdapter;
import com.ufo.imageselector.adapter.ImageAdapter;
import com.ufo.imageselector.model.ImageHelper;
import com.ufo.imageselector.model.entity.ImageEntity;
import com.ufo.imageselector.utils.AnimatorUtils;
import com.ufo.imageselector.utils.ResourceUtils;
import com.ufo.imageselector.widget.TitleBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 日期:2017/11/2
 * <p>
 * 作者:xudiwei
 * <p>
 * 描述:图片选择页面
 */
public class ImageSelectActivity extends BasicActivity {
    private static final String TAG = "ImageSelectActivity";
    //相册名称
    public static final String KEY_ALBUM = "alubm";
    //最多可以选择的图片的数量
    public static final String KEY_SELECT_MAX_LEN = "select_max_len";
    //保存当前Activity被意外回收前的数据
    private static final String KEY_SAVE_LIST = "save_list";
    private List<ImageEntity> mList = new ArrayList<>();
    private String album;
    private int selectMaxLen;
    private static final int COLUMN = 3;
    private RecyclerView mRecyclerView;
    private ImageAdapter mImageAdapter;
    private TextView mTvSelected;
    private TextView mTvPreview;
    private List<String> selectImageList;
    private TitleBar mTitleBar;


    @Override
    public int getContentView() {
        return R.layout.activity_image_select;
    }

    @Override
    protected void initView() {
        mTitleBar = setTitleBar(R.id.titleBar, getString(R.string.text_image_select), true, false, true, new MyOnTitleBarAllClickListener());
        mTitleBar.setTvRightText(getString(R.string.text_confirm));
        mTitleBar.setTvRightTextColor(R.color.colorRed);

        mRecyclerView = findId(R.id.rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, COLUMN));
        mImageAdapter = new ImageAdapter(this, mList);
        mImageAdapter.setItemSize(calculateItemSize(COLUMN));
        mImageAdapter.setOnItemClickListener(new MyOnItemClickListener());
        mImageAdapter.setOnImageCheckboxCheckListener(new MyOnImageCheckboxCheckListener());
        mRecyclerView.setAdapter(mImageAdapter);

        mTvSelected = (TextView) findViewById(R.id.tv_selected);
        mTvPreview = (TextView) findViewById(R.id.tv_preview);
        mTvPreview.setOnClickListener(new MyOnClickListener());

    }

    @Override
    protected void initData() {
        album = getIntent().getStringExtra(KEY_ALBUM);
        selectMaxLen = getIntent().getIntExtra(KEY_SELECT_MAX_LEN, 0);
        Log.d(TAG, "initData:--> album: " + album);
        Log.d(TAG, "initData:--> selectMaxLen: " + selectMaxLen);
        selectImageList = new ArrayList<>(selectMaxLen);
        setSelected(0);

        new ImageHelper().getImages(this, true, album, new ImageHelper.OnImageFetchCallback() {
            @Override
            public void onSuccess(List<ImageEntity> list) {
                mList.clear();
                mList.addAll(list);
                mImageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String errorMsg) {
                Log.d(TAG, "onFailed:--> errorMsg: " + errorMsg);
            }
        });
    }


    private void setSelected(int selectedImageLen) {
        mTvSelected.setText(selectedImageLen + "/" + selectMaxLen);
        if (selectedImageLen == 0) {
            mTvPreview.setEnabled(false);
            mTvPreview.setTextColor(ResourceUtils.getColor(this, R.color.colorRedDisable));
            mTitleBar.setTvRightTextEnable(false);
            mTitleBar.setTvRightTextColor(R.color.colorRedDisable);
        } else {
            mTvPreview.setEnabled(true);
            mTvPreview.setTextColor(ResourceUtils.getColor(this, R.color.colorRed));
            mTitleBar.setTvRightTextEnable(true);
            mTitleBar.setTvRightTextColor(R.color.colorRed);
        }
    }

    /**
     * 添加选择的图片或减少选择的图片,添加成功返回true,否则返回false
     *
     * @param position
     * @return
     */
    private void addOrSub(View parent, ImageButton imageButton, int position) {
        ImageEntity entity = mList.get(position);

        if (selectImageList.contains(entity.getPath())) {
            selectImageList.remove(entity.getPath());
            entity.setSelect(false);
        } else if (selectImageList.size() < selectMaxLen) {
            selectImageList.add(entity.getPath());
            entity.setSelect(true);
        } else if (selectImageList.size() == selectMaxLen) {
            AnimatorUtils.animatorShake(mTvSelected);
        }
        imageButton.setSelected(entity.isSelect());
        setSelected(selectImageList.size());

    }

    //带回数据到上个页面
    private void setResultAndFinish() {
        Intent intent = new Intent();
        intent.putExtra(PhotoActivity.KEY_DATA, (Serializable) selectImageList);
        setResult(0, intent);
        finish();
    }

    //跳到预览页面
    private void launcherToViewerActivity(ArrayList<String> list) {
        Intent intent = new Intent(this, ViewerActivity.class);
        intent.putExtra(PhotoActivity.KEY_DATA, list);
        startActivity(intent);
    }

    private class MyOnTitleBarAllClickListener implements TitleBar.OnTitleBarAllClickListener {
        @Override
        public void onRightTextButtonClick(View view) {
            setResultAndFinish();
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
            ArrayList<String> list = new ArrayList<>(1);
            list.add(mList.get(position).getPath());
            launcherToViewerActivity(list);
        }
    }

    private class MyOnImageCheckboxCheckListener implements BasicAdapter.OnImageCheckboxCheckListener {

        @Override
        public void onCheckedChanged(View parent, ImageButton imageButton, int position, boolean check) {
            Log.d(TAG, "onCheckedChanged:--> 之前: " + imageButton.isSelected());
            addOrSub(parent, imageButton, position);
            Log.d(TAG, "onCheckedChanged:--> 之后: " + imageButton.isSelected());
        }
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            launcherToViewerActivity((ArrayList<String>) selectImageList);
        }
    }
}
