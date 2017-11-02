package com.ufo.imageselector.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufo.imageselector.R;
import com.ufo.imageselector.loader.ImageLoader;
import com.ufo.imageselector.model.entity.ImageEntity;

import java.util.List;

/**
 * 日期:2017/11/2
 * <p>
 * 作者:xudiwei
 * <p>
 * 描述:图片选择适配器
 */

public class ImageAdapter extends BasicAdapter {
    private static final String TAG = "ImageAdapter";
    public ImageAdapter(Context context, List<ImageEntity> list) {
        super(context, list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageEntity entity = mList.get(position);
        Log.d(TAG, "onBindViewHolder:--> Position: "+position + "check: "+ entity.isSelect());
        holder.ibSelect.setSelected(entity.isSelect());
        holder.ivImage.getLayoutParams().width = itemSize;
        holder.ivImage.getLayoutParams().height = itemSize;
        holder.ivImage.setImageResource(R.color.colorLine);
        ImageLoader.getInstance().loadImage(entity.getPath(),holder.ivImage);
    }
}
