package com.ufo.imageselector.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

/**
 * 日期:2017/10/27
 * <p>
 * 作者:xudiwei
 * <p>
 * 描述:文件工具类
 */

public class FileUtils {


    /**
     * 创建一个指定 filename的文件在应用的外部Cache目录里
     *
     * @param context  上下文
     * @param filename 文件名
     * @return 返回创建成功的文件或是空
     */
    @Nullable
    public static File newFileOnExternalCacheDir(@NonNull Context context, @NonNull String filename) {
        File externalCacheDir = context.getExternalCacheDir();
        if (null == externalCacheDir) {
            return null;
        }

        File file = new File(externalCacheDir, filename);
        try {
            boolean result = file.createNewFile();
        } catch (IOException e) {
            return null;
        }
        return file;
    }


    /**
     * 创建一个指定 dirName的文件平在应用的外部Cache目录里
     *
     * @param context 上下文
     * @param dirName 文件夹名字
     * @return
     */
    @Nullable
    public static File newDirOnExternalCacheDir(@NonNull Context context, @NonNull String dirName) {
        File externalCacheDir = context.getExternalCacheDir();
        if (null == externalCacheDir) {
            return null;
        }

        File file = new File(externalCacheDir, dirName);
        if (file.isDirectory() && file.exists()) {
            return file;
        }
        boolean mkdirs = file.mkdirs();
        if (mkdirs) {
            return file;
        }
        return null;
    }

    /**
     * 创建一个指定 filename的文件在应用的内部Cache目录里
     *
     * @param context  上下文
     * @param filename 文件名
     * @return 返回创建成功的文件或是空
     */
    @Nullable
    public static File newFileOnCacheDir(@NonNull Context context, @NonNull String filename) {
        File cacheDir = context.getCacheDir();
        if (null == cacheDir) {
            return null;
        }

        File file = new File(cacheDir, filename);
        try {
            boolean result = file.createNewFile();
        } catch (IOException e) {
            return null;
        }
        return file;
    }

    /**
     * 创建一个指定 filename 系统下载目录的文件并返回
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return 返回空或是创建成功的文件
     */
    @Nullable
    public static File newSystemDownloadFile(@NonNull Context context, @NonNull String fileName) {
        File systemDownDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(systemDownDir, fileName);
        if (!file.exists()) {
            try {
                boolean result = file.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return file;
    }

    @Nullable
    public static File newSdcardDirFile(@NonNull Context context, @NonNull String fileName) {
        String sdcardState = Environment.getExternalStorageState();
        if (sdcardState.equals(Environment.MEDIA_MOUNTED)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            File appSdcardDir = new File(sdcardDir, context.getPackageName());
            if (!appSdcardDir.exists()) {
                appSdcardDir.mkdirs();
            }
            File file = new File(appSdcardDir, fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    return null;
                }
            }
            return file;
        }
        return null;
    }

    /**
     * 根据时间磋生成一个文件名
     *
     * @param postfix 文件名的后缀,如果为空则后缀为: .0
     * @return 返回文件名
     */
    @NonNull
    public static String newFileName(@Nullable String postfix) {
        String fileName;
        String timeMillis = String.valueOf(System.currentTimeMillis());

        if (TextUtils.isEmpty(postfix)) {
            fileName = timeMillis + ".0";
        } else {
            fileName = timeMillis + "." + postfix;
        }

        return fileName;
    }

}
