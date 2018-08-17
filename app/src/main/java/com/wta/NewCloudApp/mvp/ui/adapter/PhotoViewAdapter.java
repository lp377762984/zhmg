package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.wta.NewCloudApp.R;

import java.util.ArrayList;

public class PhotoViewAdapter extends PagerAdapter {
    private ArrayList<String> urlStr;

    public PhotoViewAdapter(ArrayList<String> urlStr) {
        this.urlStr = urlStr;
    }

    @Override
    public int getCount() {
        return urlStr.size();
    }

    @NonNull
    @Override
    public View instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View view = inflater.inflate(R.layout.photo_item, container, false);
        PhotoView photoView = view.findViewById(R.id.photoView);
        TextView tvPage = view.findViewById(R.id.tv_page);
        GlideArms.with(container.getContext()).load(urlStr.get(position)).into(photoView);
        tvPage.setText((position + 1) + "/" + urlStr.size());
        container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
