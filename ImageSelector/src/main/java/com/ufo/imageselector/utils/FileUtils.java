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
     * 如:/storage/emulated/0/Android/data/com.irongxiaobai.rxbl/cache/bb.txt
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
     * 如:/storage/emulated/0/Android/data/com.irongxiaobai.rxbl/cache/Demo
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
     * 如:/data/user/0/com.irongxiaobai.rxbl/cache/bb.txt
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
     * 如/storage/emulated/0/Download/Demo.txt
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

    /**
     * 创建一个指定 filename 的文件在SD卡下以包名为目录里并返回
     * 如: com.irongxiaobai.rxb/demo.txt
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return 返回创建成功的文件或null
     */
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
     * 在SD卡里的包名目录中创建一个指定子目录的文件
     * 如: /com.irongxiaobai.rxb/Demo/text.txt
     *
     * @param context    上下文
     * @param subDirname 子目录名字
     * @param fileName   文件名
     * @return 返回创建成功的文件
     */
    @Nullable
    public static File newSdcardSubDirFile(@NonNull Context context, @NonNull String subDirname, @NonNull String fileName) {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            File appSubDir = new File(sdcardDir, context.getPackageName() + "/" + subDirname);
            if (!appSubDir.exists()) {
                appSubDir.mkdirs();
            }
            File file = new File(appSubDir, fileName);
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
     * 获取一个指定 filename 的文件在SD卡下以包名为目录里并返回路径,(注意.这里不创建文件)
     * 如: com.irongxiaobai.rxb/demo.txt
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return 返回文件路径或null
     */
    @Nullable
    public static File getSdcardDirFile(@NonNull Context context, @NonNull String fileName) {
        String sdcardState = Environment.getExternalStorageState();
        if (sdcardState.equals(Environment.MEDIA_MOUNTED)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            File appSdcardDir = new File(sdcardDir, context.getPackageName());
            if (!appSdcardDir.exists()) {
                appSdcardDir.mkdirs();
            }
            File file = new File(appSdcardDir, fileName);
            return file;
        }
        return null;
    }


    /**
     * 在SD卡里的包名目录中创建一个指定子目录的但是不创建文件然后返回这个文件
     * 如: /com.irongxiaobai.rxb/Demo/text.txt
     *
     * @param context    上下文
     * @param subDirname 子目录名字
     * @param fileName   文件名
     * @return 返回文件的路径
     */
    @Nullable
    public static File getSdcardSubDirFile(@NonNull Context context, @NonNull String subDirname, @NonNull String fileName) {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            File appSubDir = new File(sdcardDir, context.getPackageName() + "/" + subDirname);
            if (!appSubDir.exists()) {
                appSubDir.mkdirs();
            }
            File file = new File(appSubDir, fileName);
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
