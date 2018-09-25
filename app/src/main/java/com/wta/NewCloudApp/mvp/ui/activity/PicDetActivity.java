package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.PictureC;

import java.util.List;

/**
 * 图片详情
 */
public class PicDetActivity extends BaseActivity {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pic_det;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    public void startPicDet(Activity activity, List<PictureC> pictureCS) {
        Intent intent=new Intent(activity,PicDetActivity.class);
    }
}
