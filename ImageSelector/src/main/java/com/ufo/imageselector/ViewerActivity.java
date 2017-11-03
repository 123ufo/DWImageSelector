package com.ufo.imageselector;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.ufo.imageselector.adapter.ViewerAdapter;
import com.ufo.imageselector.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期:2017/11/2
 * <p>
 * 作者:xudiwei
 * <p>
 * 描述:图片查看页面
 */
public class ViewerActivity extends BasicActivity {
    private static final String TAG = "ViewerActivity";

    private TitleBar mTitleBar;
    private List<String> mList = new ArrayList<>();
    private ViewerAdapter mAdapter;
    private static final int DEFAULT_INDEX = 1;

    @Override
    public int getContentView() {
        return R.layout.activity_viewer;
    }

    @Override
    protected void initView() {
        mTitleBar = setTitleBar(R.id.titleBar, getString(R.string.text_preview), true, false, true, new MyOnTitleBarAllClickListener());
        mTitleBar.setTvRightTextColor(R.color.colorRed);
        mTitleBar.setTvRightTextEnable(false);

        ViewPager viewPager = findId(R.id.viewpager);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        mAdapter = new ViewerAdapter(this, mList);
        viewPager.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        mList.addAll((List<String>) getIntent().getSerializableExtra(PhotoActivity.KEY_DATA));
        mAdapter.notifyDataSetChanged();
        Log.d(TAG, "initData:--> mList: " + mList.size());
        viewerPositionSelect(DEFAULT_INDEX);
    }

    private void viewerPositionSelect(int position) {
        mTitleBar.setTvRightText(position + "/" + mList.size());
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

    private class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            viewerPositionSelect(position + 1);
        }

    }
}
