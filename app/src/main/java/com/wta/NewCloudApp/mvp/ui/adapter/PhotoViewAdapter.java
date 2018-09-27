package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.PictureC;

import java.util.ArrayList;

public class PhotoViewAdapter extends PagerAdapter {
    private ArrayList<PictureC> pictures;

    public PhotoViewAdapter(ArrayList<PictureC> pictures) {
        this.pictures = pictures;
    }

    @Override
    public int getCount() {
        return pictures.size();
    }

    @NonNull
    @Override
    public View instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View view = inflater.inflate(R.layout.photo_item, container, false);
        ImageView imageView = view.findViewById(R.id.image);
        GlideArms.with(container.getContext()).load(pictures.get(position).url)
                .placeholder(R.mipmap.side_b_placeholder).into(imageView);
        TextView desc = view.findViewById(R.id.tv_desc);
        String descStr = pictures.get(position).desc;
        if (TextUtils.isEmpty(descStr)){
            descStr ="没有图片说明喔~";
        }
        desc.setText(descStr);
        container.addView(view);
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
