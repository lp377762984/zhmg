package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 周边商家详情
 */
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
    ArrayList<String> urls=new ArrayList<>();

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

    @OnClick({R.id.im_phone, R.id.lat_imgs})
    public void startPhone(View view) {
        switch (view.getId()) {
            case R.id.im_phone:
                if (TextUtils.isEmpty(business.telephone)){
                    showToast("该店铺未填写电话。");
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + business.telephone));
                startActivity(intent);
                break;
            case R.id.lat_imgs:
                PhotoViewActivity.startViewPhoto(this,urls);
                break;
        }

    }

    @Override
    public void showStoreDet(Business business) {
        this.business = business;
        GlideArms.with(this).load(business.shop_doorhead).into(imHead);
        tvName.setText(business.shop_name);
        GlideArms.with(this).load(business.level_img).into(imClass);
        tvLocation.setText(business.location_address);
        Business.PictureBean picture = business.picture;
        boolean a1 = false;
        boolean a2 = false;
        boolean a3 = false;
        if (picture != null && !TextUtils.isEmpty(picture.image1)) {//第一张有图片
            GlideArms.with(this).load(picture.image1).into(imStore01);
            urls.add(picture.image1);
            a1 = true;
        }
        if (picture != null && !TextUtils.isEmpty(picture.image2)) {//第二张有图片
            GlideArms.with(this).load(picture.image2).into(imStore02);
            urls.add(picture.image2);
            a2 = true;
        }
        if (picture != null && !TextUtils.isEmpty(picture.image3)) {//第三张有图片
            GlideArms.with(this).load(picture.image3).into(imStore03);
            urls.add(picture.image3);
            a3 = true;
        }
        if (!a1) {//没有一张图片
            latPics.setVisibility(View.GONE);
        } else {
            if (!a2) {//只有一张图片
                imStore01.setVisibility(View.VISIBLE);
                imStore02.setVisibility(View.INVISIBLE);
                imStore03.setVisibility(View.INVISIBLE);
            } else {
                if (!a3) {//只有2张图片
                    imStore01.setVisibility(View.VISIBLE);
                    imStore02.setVisibility(View.VISIBLE);
                    imStore03.setVisibility(View.INVISIBLE);
                } else {//有3张图片
                    imStore01.setVisibility(View.VISIBLE);
                    imStore02.setVisibility(View.VISIBLE);
                    imStore03.setVisibility(View.VISIBLE);
                }
            }
        }
        tvTime.setText(String.format("%s-%s", business.start_time, business.end_time));
        tvType.setText(business.type_name);
        if (TextUtils.isEmpty(business.introduction)) {
            tvDesc.setText("暂未填写店铺详情");
        } else {
            tvDesc.setText(business.introduction);
        }
    }

}
