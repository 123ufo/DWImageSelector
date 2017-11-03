package com.ufo.imageselector.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.ufo.imageselector.R;
import com.ufo.imageselector.loader.ImageLoader;

import java.util.List;

/**
 * 日期:2017/11/2
 * <p>
 * 作者:xudiwei
 * <p>
 * 描述:
 */

public class ViewerAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mList;

    public ViewerAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_preview, container, false);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.photoView);
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageLoader.getInstance().loadImage(mList.get(position), photoView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
