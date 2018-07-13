package com.wta.NewCloudApp.mvp.ui.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

public class PJImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if (path instanceof Integer) imageView.setImageResource((Integer) path);
    }

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        //FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(200, 200);
        //lp.gravity = Gravity.CENTER;
        //imageView.setLayoutParams(lp);
        return imageView;
    }
}
