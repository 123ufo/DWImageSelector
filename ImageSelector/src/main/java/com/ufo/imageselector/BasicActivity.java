package com.ufo.imageselector;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ufo.imageselector.widget.TitleBar;

/**
 * 日期:2017/11/1
 * <p>
 * 作者:xudiwei
 * <p>
 * 描述:
 */

abstract class BasicActivity extends AppCompatActivity {
    private static final String TAG = "BasicActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initView();
        initData();
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取布局id
     *
     * @return layout id
     */
    public abstract int getContentView();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    protected  <T extends View> T findId(@IdRes int id) {
        return (T) findViewById(id);
    }

    /**
     * 计算Item的大小,大小等于屏的宽度减去Item的margin值除以item的数量
     *
     * @param column
     * @return 返回item的size
     */
    public int calculateItemSize(int column) {
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int itemMarginPixel = getResources().getDimensionPixelSize(R.dimen.item_margin);
        Log.d(TAG, "calculateItemSize:--> itemMarginPixel: " + itemMarginPixel);
        int itemSize = (width - column * itemMarginPixel) / column;
        Log.d(TAG, "calculateItemSize:--> itemSize: " + itemSize);
        return itemSize;
    }



    /**
     * 设置标题栏参数
     *
     * @param titleIds              标题栏的id
     * @param title                 标题
     * @param goneLeftBtn           左边按钮是否显示
     * @param goneRightBtn          右边按钮是否显示
     * @param titleBarClickListener 左右边的按钮点击事件回调接口
     * @return 返回设置好的titlebar
     */
    public TitleBar setTitleBar(@IdRes int titleIds, String title
            , boolean goneLeftBtn, boolean goneRightBtn, TitleBar.OnTitleBarClickListener titleBarClickListener) {
        TitleBar titleBar = (TitleBar) findViewById(titleIds);
        titleBar.setTitle(title);
        titleBar.visibleIvLeftBtn(goneLeftBtn);
        titleBar.visibleIvRightBtn(goneRightBtn);
        titleBar.setOnTitleBarClickListener(titleBarClickListener);
        return titleBar;
    }

    /**
     * 设置标题栏参数
     *
     * @param titleIds                 标题栏的id
     * @param title                    标题
     * @param visibleLeftBtn           左边按钮是否显示
     * @param visibleRightBtn          右边按钮是否显示
     * @param visibleRightTextBtn      右边文本按钮是否显示
     * @param titleBarAllClickListener 左右边的按钮 右边文本按钮 点击事件回调接口
     * @return 返回设置好的titlebar
     */
    public TitleBar setTitleBar(@IdRes int titleIds, String title
            , boolean visibleLeftBtn, boolean visibleRightBtn, boolean visibleRightTextBtn,
                                TitleBar.OnTitleBarAllClickListener titleBarAllClickListener) {
        TitleBar titleBar = (TitleBar) findViewById(titleIds);
        titleBar.setTitle(title);
        titleBar.visibleIvLeftBtn(visibleLeftBtn);
        titleBar.visibleIvRightBtn(visibleRightBtn);
        titleBar.visibleTvRightTextBtn(visibleRightTextBtn);
        titleBar.setOnTitleBarAllClickListener(titleBarAllClickListener);
        return titleBar;
    }

}
