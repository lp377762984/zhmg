package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.PictureC;
import com.wta.NewCloudApp.mvp.ui.adapter.PhotoViewAdapter;
import com.wta.NewCloudApp.mvp.ui.widget.MoneyBar;

import java.util.ArrayList;

public class PhotoViewActivity extends BaseLoadingActivity implements ViewPager.OnPageChangeListener {

    private MoneyBar mb;
    ArrayList<PictureC> pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        pictures = (ArrayList<PictureC>) getIntent().getSerializableExtra("pictures");
        int position = getIntent().getIntExtra("position", 0);
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new PhotoViewAdapter(pictures));
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(this);
        mb = findViewById(R.id.mb);
        mb.setTextTitle((position + 1) + "/" + pictures.size());
    }

    public static void startViewPhoto(Activity activity, ArrayList<PictureC> pictures, int position) {
        Intent intent = new Intent(activity, PhotoViewActivity.class);
        intent.putExtra("pictures", pictures);
        intent.putExtra("position", position);
        activity.startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mb.setTextTitle((position + 1) + "/" + pictures.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
