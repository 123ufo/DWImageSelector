package com.ufo.imageselector.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by: xudiwei
 * <p>
 * on: 2017/5/13.
 * <p>
 * 描述：UriUtils
 */

public class UriUtils {


    /**
     * 根据文件生成这个文件的url
     *
     * @param context 上下文
     * @param file    需要生成的uri的文件
     * @return 返回uri
     */
    public static Uri FileToUri(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            fileUri = Uri.fromFile(file);
        } else {
            fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
        }
        return fileUri;
    }

    public static Uri FileToUri(Context context, String file) {
        return FileToUri(context, new File(file));
    }
}
