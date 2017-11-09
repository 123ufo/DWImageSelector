package com.ufo.imageselector.widget;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ufo.imageselector.R;


/**
 * Created by Administrator on 2016/12/23.
 * 作者：xudiwei
 *
 * 描述：标题栏组合控件
 */

public class TitleBar extends LinearLayout implements View.OnClickListener {

    private ImageView mIvLeftBtn;
    private ImageView mIvRightBtn;
    private TextView mTvtitle;
    private OnTitleBarClickListener mListener;
    private OnTitleBarAllClickListener mAllClickListener;
    private TextView mTvRightText;
    private RelativeLayout mRlMain;
    private View mVBottomLine;
    private View mRootView;
    private TextView mTvUnRead;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
//        mRootView = inflate(context, R.layout.widget_title_bar, null);
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.widget_title_bar, this, false);
        this.addView(mRootView);

        //左边按钮
        mIvLeftBtn = (ImageView) mRootView.findViewById(R.id.iv_left_btn);
        //右边按扭
        mIvRightBtn = (ImageView) mRootView.findViewById(R.id.iv_right_btn);
        //标题
        mTvtitle = (TextView) mRootView.findViewById(R.id.tv_title);
        //右边文字按钮
        mTvRightText = (TextView) mRootView.findViewById(R.id.tv_right_text);

        mRlMain = (RelativeLayout) mRootView.findViewById(R.id.rl_main);
        // 底部线
        mVBottomLine = mRootView.findViewById(R.id.v_bottom_line);

        mTvUnRead = (TextView) mRootView.findViewById(R.id.tv_un_read);

        mIvLeftBtn.setOnClickListener(this);
        mIvRightBtn.setOnClickListener(this);
        mTvRightText.setOnClickListener(this);

    }

    /**
     * 设置暗黑模式
     */
    public void setDarkTheme() {
//        mRootView.findViewById(R.id.rl_main).setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorLightBlack));
//        mTvtitle.setTextColor(ContextCompat.getColor(getContext(),R.color.colorWhile));
//        mIvLeftBtn.setImageResource(R.mipmap.nav_white_back);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.iv_left_btn) {
            //左边按钮
            if (null != mListener) {
                mListener.onLeftButtonClick(view);
            }
            if (null != mAllClickListener) {
                mAllClickListener.onLeftButtonClick(view);
            }
        } else if (viewId == R.id.iv_right_btn) {
            //右边按钮
            if (null != mListener) {
                mListener.onRightButtonClick(view);
            }
            if (null != mAllClickListener) {
                mAllClickListener.onRightButtonClick(view);
            }
        } else if (viewId == R.id.tv_right_text) {
            //右边文本按钮
            if (null != mAllClickListener) {
                mAllClickListener.onRightTextButtonClick(view);
            }
        }

//        switch (view.getId()) {
//            //左边按钮
//            case R.id.iv_left_btn:
//                if (null != mListener) {
//                    mListener.onLeftButtonClick(view);
//                }
//                if (null != mAllClickListener) {
//                    mAllClickListener.onLeftButtonClick(view);
//                }
//                break;
//            //右边按钮
//            case R.id.iv_right_btn:
//                if (null != mListener) {
//                    mListener.onRightButtonClick(view);
//                }
//                if (null != mAllClickListener) {
//                    mAllClickListener.onRightButtonClick(view);
//                }
//                break;
//            //右边文本按钮
//            case R.id.tv_right_text:
//                if (null != mAllClickListener) {
//                    mAllClickListener.onRightTextButtonClick(view);
//                }
//                break;
//        }
    }

    /**
     * 是否显示右边未读消息小红点
     * @param showUnReadTip
     */
    public void showUnReadTip(boolean showUnReadTip) {
        mTvUnRead.setVisibility(showUnReadTip ? View.VISIBLE : View.GONE);
    }

    /**
     * 底部线是否显示，默认是显示
     *
     * @param show
     */
    public void showBottomLine(boolean show) {
        mVBottomLine.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示或隐藏左边按钮
     *
     * @param visible
     */
    public void visibleIvLeftBtn(boolean visible) {
        mIvLeftBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示或隐藏右边按钮
     *
     * @param visible
     */
    public void visibleIvRightBtn(boolean visible) {
        mIvRightBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示或隐藏右边文本按钮
     *
     * @param visible
     */
    public void visibleTvRightTextBtn(boolean visible) {
        mTvRightText.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置左边按钮图标
     *
     * @param ids
     */
    public void setIvLeftBtnDrawable(@DrawableRes int ids) {
        mIvLeftBtn.setImageResource(ids);
    }

    /**
     * 设置右边按钮图标
     *
     * @param ids
     */
    public void setIvRightBtnDrawable(@DrawableRes int ids) {
        mIvRightBtn.setImageResource(ids);
    }

    /**
     * 设置标题
     *
     * @param ids
     */
    public void setTitle(@StringRes int ids) {
        setTitle(getContext().getResources().getString(ids));
    }

    /**
     * 设置标题
     *
     * @param titleText
     */
    public void setTitle(String titleText) {
        mTvtitle.setText(titleText);
    }

    /**
     * 设置标题颜色
     *
     * @param colorIds
     */
    public void setTitleColor(@ColorRes int colorIds) {
        mTvtitle.setTextColor(getResources().getColor(colorIds));
    }

    /**
     * 设置标题背景色
     *
     * @param colorIds
     */
    public void setTitleBg(@ColorRes int colorIds) {
        mRlMain.setBackgroundResource(colorIds);
    }

    /**
     * 设置右边文本按钮的文本
     *
     * @param rightButtonText
     */
    public void setTvRightText(String rightButtonText) {
        mTvRightText.setText(rightButtonText);
    }

    /**
     * 设置右边文本按钮的颜色
     *
     * @param color
     */
    public void setTvRightTextColor(@ColorRes int color) {
        mTvRightText.setTextColor(getResources().getColor(color));
    }

    /**
     * 设置右边文本使能
     *
     * @param enable
     */
    public void setTvRightTextEnable(boolean enable) {
        mTvRightText.setEnabled(enable);
    }

    /**
     * 设置标题栏点击事件监听器这个监听器只响应左边按钮与右边按钮。若还想响应右边
     * 文本按的按钮请使用{@link #setOnTitleBarAllClickListener(OnTitleBarAllClickListener)}
     *
     * @param clickListener {@link OnTitleBarClickListener}
     */
    public void setOnTitleBarClickListener(OnTitleBarClickListener clickListener) {
        this.mListener = clickListener;
    }

    /**
     * 设置标题栏的上的按钮全部点击事件监听器能响应左边按钮，右边按钮，右边文本按钮
     *
     * @param clickListener
     */
    public void setOnTitleBarAllClickListener(OnTitleBarAllClickListener clickListener) {
        this.mAllClickListener = clickListener;
    }


    /**
     * 标题栏的点击事件回调接口，响应两个按钮的
     */
    public interface OnTitleBarClickListener {
        /**
         * 左边按钮被点击
         *
         * @param view
         */
        void onLeftButtonClick(View view);

        /**
         * 右边按钮被点击
         *
         * @param view
         */
        void onRightButtonClick(View view);
    }

    /**
     * 标题栏的点击事件回调接口,响应三个按钮的
     */
    public interface OnTitleBarAllClickListener extends OnTitleBarClickListener {
        /**
         * 右边文本按钮点击事件
         *
         * @param view
         */
        void onRightTextButtonClick(View view);

    }


}
