package com.wta.NewCloudApp.mvp.ui.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.youth.banner.loader.ImageLoader;

public class PJImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if (path instanceof Integer) imageView.setImageResource((Integer) path);
        else if (path instanceof String) GlideArms.with(context).load(path).into(imageView);
    }
}
