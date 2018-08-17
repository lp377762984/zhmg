package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.mvp.ui.adapter.PhotoViewAdapter;
import com.wta.NewCloudApp.mvp.ui.widget.PJImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class PhotoViewActivity extends BaseLoadingActivity {

    private ArrayList<String> urlStr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        ArrayList<String> urlList = getIntent().getStringArrayListExtra("urlList");
        if (urlList == null || urlList.size() == 0) {
            ArmsUtils.makeText(App.getInstance(), "当前没有图片");
            finish();
        } else {
            urlStr.addAll(urlList);
            ViewPager viewPager = findViewById(R.id.viewPager);
            viewPager.setAdapter(new PhotoViewAdapter(urlStr));
        }
    }

    @Override
    public int setUIMode() {
        return UIMODE_TRANSPARENT_NOTALL;
    }

    public static void startViewPhoto(Activity activity, ArrayList<String> urlStr) {
        if (urlStr == null || urlStr.size() == 0) {
            ArmsUtils.makeText(App.getInstance(), "当前没有图片");
            return;
        }
        Intent intent = new Intent(activity, PhotoViewActivity.class);
        intent.putStringArrayListExtra("urlList", urlStr);
        activity.startActivity(intent);
    }
}
