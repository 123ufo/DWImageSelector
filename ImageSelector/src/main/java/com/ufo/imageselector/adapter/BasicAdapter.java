package com.ufo.imageselector.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufo.imageselector.R;
import com.ufo.imageselector.model.entity.ImageEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期:2017/11/2
 * <p>
 * 作者:xudiwei
 * <p>
 * 描述:
 */

public abstract class BasicAdapter extends RecyclerView.Adapter<BasicAdapter.ViewHolder> {

    private static final String TAG = "BasicAdapter";
    List<ImageEntity> mList = new ArrayList<>();
    Context mContext;
    int itemSize;
    AlbumAdapter.OnItemClickListener mListener;
    private OnImageCheckboxCheckListener mOnImageCheckboxCheckListener;

    public BasicAdapter(Context context, List<ImageEntity> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 设置item 的大小
     *
     * @param itemSize
     */
    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        ImageButton ibSelect;
        TextView tvAlbum;

        ViewHolder(final View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            ibSelect = itemView.findViewById(R.id.ib_select);
            tvAlbum = itemView.findViewById(R.id.tv_album);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v, ViewHolder.this.getLayoutPosition());
                }
            });
            if (null != ibSelect) {
                ibSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnImageCheckboxCheckListener.onCheckedChanged(itemView,(ImageButton) v,
                                ViewHolder.this.getLayoutPosition(), ibSelect.isSelected());
                    }
                });
            }


        }
    }

    public void setOnItemClickListener(AlbumAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnImageCheckboxCheckListener(OnImageCheckboxCheckListener onImageCheckboxCheckListener) {
        mOnImageCheckboxCheckListener = onImageCheckboxCheckListener;
    }

    public interface OnImageCheckboxCheckListener {
        void onCheckedChanged(View parent,ImageButton imageButton, int position, boolean check);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
