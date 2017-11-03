package com.ufo.dwimageselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ufo.imageselector.DWImages;
import com.ufo.imageselector.model.ImageHelper;
import com.ufo.imageselector.model.entity.ImageEntity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        testFetchAlbums();
//        testFetchImages();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToImageSelector();
            }
        });

    }


    private void startToImageSelector() {
        DWImages.getImages(this, DWImages.ACTION_ALBUM, 6);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DWImages.parserResult(requestCode, data, new DWImages.GetImagesCallback() {
            @Override
            public void onResult(List<String> images) {
                Log.d(TAG, "onResult:--> : images: " + images.size());
                for (int i = 0; i < images.size(); i++) {
                    Log.d(TAG, "onResult:--> images: " + images.get(i));
                }
            }
        });
    }

    private void testFetchImages() {
        //zuiyou portrait 84cfca86ed58f11691e894e3fc6bfacb weibo Download images weibo image
        //news_article Camera
        new ImageHelper().getImages(this, true, "Camera", new ImageHelper.OnImageFetchCallback() {
            @Override
            public void onSuccess(List<ImageEntity> list) {
                Log.d(TAG, "onSuccess:--> list: " + list.size());
                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "onSuccess:--> album: " + list.get(i).getDirectory());
                    Log.d(TAG, "onSuccess:--> image: " + list.get(i).getPath());
                }
            }

            @Override
            public void onFailed(String errorMsg) {
                Log.d(TAG, "onFailed:--> errorMsg: " + errorMsg);
            }
        });
    }

    private void testFetchAlbums() {
        new ImageHelper().getAlbum(this, false, new ImageHelper.OnImageFetchCallback() {
            @Override
            public void onSuccess(List<ImageEntity> list) {
                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "testFetchAlbums:--> Image: " + list.get(i).getDirectory());
                    Log.d(TAG, "testFetchAlbums:--> path: " + list.get(i).getPath());
                }
            }

            @Override
            public void onFailed(String errorMsg) {
                Log.d(TAG, "onFailed:--> errorMsg: " + errorMsg);
            }
        });


    }
}
