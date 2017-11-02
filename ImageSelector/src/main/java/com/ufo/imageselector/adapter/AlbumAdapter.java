package com.ufo.imageselector.adapter;

import android.content.Context;
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
 * 描述: 相册适配器
 */

public class AlbumAdapter extends BasicAdapter {
    private static final String TAG = "AlbumAdapter";

    public AlbumAdapter(Context context, List<ImageEntity> list) {
        super(context, list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageEntity entity = mList.get(position);
        holder.tvAlbum.setText(entity.getDirectory());
        holder.ivImage.getLayoutParams().width = itemSize;
        holder.ivImage.getLayoutParams().height = itemSize;
        holder.ivImage.setImageResource(R.color.colorLine);
        ImageLoader.getInstance().loadImage(entity.getPath(), holder.ivImage);
    }


}
