package com.ufo.imageselector.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.ufo.imageselector.model.entity.ImageEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 日期:2017/11/1
 * <p>
 * 作者:xudiwei
 * <p>
 * 描述:图片数据层类
 */

final class ImageModel {

    private static final String TAG = "ImageModel";
    //获取的列表的数据
    private final String[] projection = new String[]{
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA};

    //外部存储器里的相册数据库表的内容提供者uri
    private final Uri externalContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    //图片的添加时间,用于排序用的
    private final String dateAdded = MediaStore.Images.Media.DATE_ADDED;
    //是否过滤掉.gif文件
    private boolean filterGif;

    public ImageModel() {
        filterGif = true;
    }

    /**
     * @param filterGif 是否要过滤Gif文件,默认是过滤的.
     */
    ImageModel(boolean filterGif) {
        this.filterGif = filterGif;
    }

    /**
     * 获取相册
     *
     * @param content 上下文
     * @return
     */
    public final List<ImageEntity> fetchAlbums(@NonNull Context content) {
        Cursor cursor = content.getContentResolver().query(externalContentUri, projection, null, null, dateAdded);
        ArrayList<ImageEntity> albums = new ArrayList<>();
        if (null != cursor && cursor.getCount() > 0) {
            HashSet<Long> set = new HashSet<>();
            File file = null;

            int idIndex = cursor.getColumnIndex(projection[0]);
            int displayNameIndex = cursor.getColumnIndex(projection[1]);
            int dataIndex = cursor.getColumnIndex(projection[2]);

            while (cursor.moveToNext()) {

                long id = cursor.getLong(idIndex);
                String album = cursor.getString(displayNameIndex);
                String image = cursor.getString(dataIndex);

                if (!set.contains(id)) {
                    file = new File(image);
                    //防止添加一些缓存的图片
                    if (file.exists() && file.length() > 0) {
                        //过滤gif文件
                        if (filterGif && image.endsWith("gif")) {
                            continue;
                        }
                        albums.add(new ImageEntity(image, album));
                        set.add(id);
                    }
                }
            }
            closeCursor(cursor);
        }
        Log.d(TAG, "fetchAlbums:--> albums size: " + albums.size());
        return albums;
    }

    /**
     * 跟据相册名称获取图片
     *
     * @param context   上下文
     * @param albumName 相册名称
     */
    public final List<ImageEntity> fetchImages(@NonNull Context context, @NonNull String albumName) {
        if (TextUtils.isEmpty(albumName)) {
            throw new IllegalArgumentException("albumName is Empty");
        }

        String selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " =?";
        String[] selectionArgs = new String[]{albumName};
        ArrayList<ImageEntity> images = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(externalContentUri, projection, selection, selectionArgs, dateAdded);

        if (null != cursor && cursor.getCount() > 0) {
            File file;
            int displayNameIndex = cursor.getColumnIndex(projection[1]);
            int dataIndex = cursor.getColumnIndex(projection[2]);

            while (cursor.moveToNext()) {
                String album = cursor.getString(displayNameIndex);
                String image = cursor.getString(dataIndex);

                //防止添加一些缓存的图片
                file = new File(image);
                if (file.exists() && file.length() > 0) {
                    //过滤gif文件
                    if (filterGif && image.endsWith("gif")) {
                        continue;
                    }
                    images.add(new ImageEntity(image,album));
                }
            }

            closeCursor(cursor);
        }
        Log.d(TAG, "fetchImages:--> images: " + images.size());
        return images;
    }

    private void closeCursor(Cursor cursor) {
        if (null != cursor) {
            cursor.close();
        }
    }

}
