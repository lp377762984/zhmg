package com.wta.NewCloudApp.mvp.ui.widget;

import android.content.Context;
import android.widget.ImageView;

import com.jess.arms.http.imageloader.glide.GlideArms;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wta.NewCloudApp.uitls.ScreenDpiUtils;
import com.youth.banner.loader.ImageLoader;

public class RoundImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if (path instanceof Integer) imageView.setImageResource((Integer) path);
        else if (path instanceof String) GlideArms.with(context).load(path).into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        RoundedImageView imageView = new RoundedImageView(context);
        imageView.setCornerRadius(ScreenDpiUtils.dp2px(context, 5));
        return imageView;
    }
}
