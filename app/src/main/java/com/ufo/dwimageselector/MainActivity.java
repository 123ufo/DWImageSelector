package com.ufo.dwimageselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ufo.imageselector.DWImages;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView mTvResult;
    private String onePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvResult = (TextView) findViewById(R.id.tv_result);

        Log.d(TAG, "onCreate:--> " + getClass().getSimpleName());
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                album();
            }
        });
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera();
            }
        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crop();
            }
        });


    }


    private void crop() {
        if (TextUtils.isEmpty(onePath)) {
            Toast.makeText(this, "图片路径不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        DWImages.cropImage(this, onePath, 4, 5, 400, 500);
    }


    private void album() {
        DWImages.getImages(this, DWImages.ACTION_ALBUM, 6);
    }

    private void camera() {
        DWImages.getImages(this, DWImages.ACTION_CAMERA, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult:--> data: " + data);
        Log.d(TAG, "onActivityResult:--> requestCode: " + requestCode);
        DWImages.parserResult(requestCode, data, new DWImages.GetImagesCallback() {
            @Override
            public void onResult(List<String> images) {
                Log.d(TAG, "onResult:--> : images: " + images.size());
                mTvResult.setText("");
                for (int i = 0; i < images.size(); i++) {
                    Log.d(TAG, "onResult:--> images: " + images.get(i));
                    Log.d(TAG, "onResult:--> size: " + new File(images.get(i)).length());
                    selectResult(images.get(i));


                    onePath = images.get(i);
                }
            }
        });

        DWImages.parserCropResult(requestCode, data, new DWImages.CropImageCallback() {
            @Override
            public void onResult(String images) {
                Log.d(TAG, "onResult:--> Crop path: " + images);
                Log.d(TAG, "onResult:--> Crop size: " + new File(images).length());
                selectResult(images);
            }
        });
    }

    private void selectResult(String data) {
        mTvResult.append(data);
        mTvResult.append("\n");
    }


}
