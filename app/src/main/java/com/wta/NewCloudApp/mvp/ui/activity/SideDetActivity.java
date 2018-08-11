package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerSideDetComponent;
import com.wta.NewCloudApp.di.module.SideDetModule;
import com.wta.NewCloudApp.mvp.contract.SideDetContract;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.presenter.SideDetPresenter;

import butterknife.BindView;


public class SideDetActivity extends BaseLoadingActivity<SideDetPresenter> implements SideDetContract.View {


    @BindView(R.id.im_head)
    ImageView imHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.im_class)
    ImageView imClass;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.lat_head)
    RelativeLayout latHead;
    @BindView(R.id.im_store_01)
    RoundedImageView imStore01;
    @BindView(R.id.im_store_02)
    RoundedImageView imStore02;
    @BindView(R.id.im_store_03)
    RoundedImageView imStore03;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.lat_pics)
    RelativeLayout latPics;
    private Business business;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSideDetComponent
                .builder()
                .appComponent(appComponent)
                .sideDetModule(new SideDetModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_side_det;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.getStoreDet(getIntent().getIntExtra("store_id", 0));
    }

    @Override
    public int setUIMode() {
        return UIMODE_TRANSPARENT_NOTALL;
    }

    public static void startDet(Activity activity, int storeID) {
        Intent intent = new Intent(activity, SideDetActivity.class);
        intent.putExtra("store_id", storeID);
        activity.startActivity(intent);
    }

    @Override
    public void showStoreDet(Business business) {
        this.business = business;
        GlideArms.with(this).load(business.shop_doorhead).into(imHead);
        tvName.setText(business.shop_name);
        GlideArms.with(this).load(business.level_img).into(imClass);
        tvLocation.setText(business.location_address);
        Business.PictureBean picture = business.picture;
        if (picture == null) {
            latPics.setVisibility(View.GONE);
        } else {
            latPics.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(picture.image1)) {
                imStore01.setVisibility(View.GONE);
                latPics.setVisibility(View.GONE);
            } else {
                imStore01.setVisibility(View.VISIBLE);
                GlideArms.with(this).load(picture.image1).into(imStore01);
            }
            if (TextUtils.isEmpty(picture.image2)) {
                imStore02.setVisibility(View.GONE);
            } else {
                imStore03.setVisibility(View.VISIBLE);
                GlideArms.with(this).load(picture.image2).into(imStore02);
            }
            if (TextUtils.isEmpty(picture.image3)) {
                imStore03.setVisibility(View.GONE);
            } else {
                imStore03.setVisibility(View.VISIBLE);
                GlideArms.with(this).load(picture.image3).into(imStore03);
            }
        }
        tvTime.setText(String.format("%s-%s", business.start_time, business.end_time));
        tvType.setText(business.type_name);
        tvDesc.setText(business.introduction);
    }

}
