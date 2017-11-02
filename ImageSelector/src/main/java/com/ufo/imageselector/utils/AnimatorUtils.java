package com.ufo.imageselector.utils;

import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

/**
 * 日期:2017/11/2
 * <p>
 * 作者:xudiwei
 * <p>
 * 描述:
 */

public class AnimatorUtils {

    /**
     * 抖动动画
     *
     * @param view
     */
    public static void animatorShake(final TextView view) {
        TranslateAnimation animation = new TranslateAnimation(0, 15, 0, 0);
        animation.setDuration(500);
        //添加一个循环器。次数是5
        animation.setInterpolator(new CycleInterpolator(5));
        view.startAnimation(animation);
    }
}
