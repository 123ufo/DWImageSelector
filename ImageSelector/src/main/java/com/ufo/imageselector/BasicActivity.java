package com.ufo.imageselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
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
    protected PermissionsManagerCompat mPermissionsManagerCompat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate:--> " + getClass().getSimpleName());
        mPermissionsManagerCompat = new PermissionsManagerCompat(this);
        mPermissionsManagerCompat.setPermissions(returnPermissionArr());
        mPermissionsManagerCompat.setPermissionCallback(new MyOnPermissionsCallback());
        initReSaveInstanceState(savedInstanceState);
        setContentView(getContentView());
        initParams();
        initView();
        initData();
    }

    /**
     * 初始化上个页面传过来的参数
     */
    protected void initParams() {

    }


    /**
     * 子类复写此方法返回需要申请的权限
     *
     * @return
     */
    protected String[] returnPermissionArr() {
        return new String[]{};
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (null != mPermissionsManagerCompat) {
            mPermissionsManagerCompat.resultPermissionsProcess(this, requestCode, permissions, grantResults);
        }
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != mPermissionsManagerCompat) {
            mPermissionsManagerCompat.onActivityResult(requestCode, resultCode, data);
        }
    }


    /**
     * 权限申请结果回调
     */
    private class MyOnPermissionsCallback implements PermissionsManagerCompat.OnPermissionsCallback {
        @Override
        public void hasPermissions() {
            hasPermission();
        }

        @Override
        public void noPermissions() {
            noPermission();
        }
    }

    /**
     * 有权限
     */
    protected void hasPermission() {

    }

    /**
     * 无权限
     */
    protected void noPermission() {

    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 初始化状态保存数据
     *
     * @param savedInstanceState
     */
    protected void initReSaveInstanceState(Bundle savedInstanceState) {

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

    protected <T extends View> T findId(@IdRes int id) {
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
//        Log.d(TAG, "calculateItemSize:--> itemMarginPixel: " + itemMarginPixel);
        int itemSize = (width - column * itemMarginPixel) / column;
//        Log.d(TAG, "calculateItemSize:--> itemSize: " + itemSize);
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
