package com.ufo.imageselector.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.ufo.imageselector.model.entity.ImageEntity;

import java.util.List;

/**
 * 日期:2017/11/1
 * <p>
 * 作者:xudiwei
 * <p>
 * 描述: 图片获取帮助类
 */

public class ImageHelper {

    private static final String TAG = "ImageHelper";
    private Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 获取相册
     *
     * @param context   上下文
     * @param filterGif true过滤掉gif文件,false不滤gif文件.
     * @param callback  获取结果通过此回调接口进行回调
     */
    public void getAlbum(@NonNull final Context context, final boolean filterGif, @NonNull final OnImageFetchCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<ImageEntity> imageEntities = new ImageModel(filterGif).fetchAlbums(context);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(imageEntities);
                        }
                    });

                } catch (final Exception e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailed(e.getMessage());
                        }
                    });
                }

            }
        }).start();
    }

    /**
     * 获取图片
     *
     * @param context   上下文
     * @param filterGif true过滤掉gif文件,false不滤gif文件.
     * @param albumName 相册的名称
     * @param callback  获取结果通过此回调接口进行回调
     */
    public void getImages(@NonNull final Context context, final boolean filterGif, @NonNull final String albumName
            , @NonNull final OnImageFetchCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<ImageEntity> imageEntities = new ImageModel(filterGif).fetchImages(context, albumName);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(imageEntities);
                        }
                    });

                } catch (final Exception e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailed(e.getMessage());
                        }
                    });
                }
            }
        }).start();
    }


    public interface OnImageFetchCallback {
        void onSuccess(List<ImageEntity> list);

        void onFailed(String errorMsg);
    }
}
