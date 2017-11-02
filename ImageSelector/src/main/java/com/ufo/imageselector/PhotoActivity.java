package com.ufo.imageselector;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ufo.imageselector.adapter.AlbumAdapter;
import com.ufo.imageselector.adapter.BasicAdapter;
import com.ufo.imageselector.model.ImageHelper;
import com.ufo.imageselector.model.entity.ImageEntity;
import com.ufo.imageselector.widget.TitleBar;

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
    private static final String TAG = "ImageSelectorActivity";
    private RecyclerView mRecyclerView;
    private List<ImageEntity> mList = new ArrayList<>();
    private AlbumAdapter mAlbumAdapter;
    //列的数量
    private static final int COLUMN = 3;

    @Override
    public int getContentView() {
        return R.layout.activity_photo;
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
        new ImageHelper().getAlbum(this, true, new ImageHelper.OnImageFetchCallback() {
            @Override
            public void onSuccess(List<ImageEntity> list) {
                mList.clear();
                mList.addAll(list);
                mAlbumAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String errorMsg) {
                Log.d(TAG, "onFailed:--> errorMsg: " + errorMsg);
            }
        });
    }

    private void launcherToImageSelectActivity(String album,int needSelectLen) {
        Intent intent = new Intent(this, ImageSelectActivity.class);
        intent.putExtra(ImageSelectActivity.KEY_ALBUM,album);
        intent.putExtra(ImageSelectActivity.KEY_SELECT_MAX_LEN,needSelectLen);
        startActivity(intent);
    }

    private class MyOnTitleBarAllClickListener implements TitleBar.OnTitleBarAllClickListener {
        @Override
        public void onRightTextButtonClick(View view) {

        }

        @Override
        public void onLeftButtonClick(View view) {

        }

        @Override
        public void onRightButtonClick(View view) {

        }
    }

    private class MyOnItemClickListener implements BasicAdapter.OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            String directory = mList.get(position).getDirectory();
            launcherToImageSelectActivity(directory,9);
        }
    }
}
