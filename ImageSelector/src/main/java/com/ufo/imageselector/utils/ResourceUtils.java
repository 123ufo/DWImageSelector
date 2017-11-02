package com.ufo.imageselector.utils;


import android.content.Context;

/**
 * Created by: xudiwei
 * <p>
 * on: 2017/5/3.
 * <p>
 * 描述：资源工具类
 */

public class ResourceUtils {

    /**
     * 根据颜色的资源id获取颜色的值
     *
     * @param colorIds 颜色的资源id
     * @return 颜色的Int值
     */
    public static int getColor(Context context,int colorIds) {
        return context.getResources().getColor(colorIds);
    }

}
