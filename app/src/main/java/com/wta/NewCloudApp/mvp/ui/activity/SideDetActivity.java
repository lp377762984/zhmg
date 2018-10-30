package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerSideDetComponent;
import com.wta.NewCloudApp.di.module.SideDetModule;
import com.wta.NewCloudApp.mvp.contract.SideDetContract;
import com.wta.NewCloudApp.mvp.model.entity.BusinessNew;
import com.wta.NewCloudApp.mvp.model.entity.PictureC;
import com.wta.NewCloudApp.mvp.presenter.SideDetPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.PictureAdapter;
import com.wta.NewCloudApp.uitls.IntentUtils;
import com.wta.NewCloudApp.uitls.PackageUtils;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.lat_pics)
    RelativeLayout latPics;
    @BindView(R.id.lat_gifts)
    View latGifts;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private BusinessNew business;
    PictureAdapter adapter;

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
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        List<PictureC> pictures = new ArrayList<>();
        adapter = new PictureAdapter(R.layout.picture_item, pictures);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PhotoViewActivity.startViewPhoto(SideDetActivity.this, (ArrayList<PictureC>) business.new_picture, position);
            }
        });
        recyclerView.setAdapter(adapter);
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

    @OnClick({R.id.im_phone, R.id.tv_location, R.id.lat_gifts})
    public void startPhone(View view) {
        switch (view.getId()) {
            case R.id.im_phone:
                if (TextUtils.isEmpty(business.telephone)) {
                    showToast("该店铺未填写电话。");
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + business.telephone));
                startActivity(intent);
                break;
            case R.id.tv_location:
                if (PackageUtils.appIsInstalled(this, IntentUtils.GAODE_MAP_APP)) {
                    IntentUtils.goNaviByGaoDeMap(this, business.shop_address_x, business.shop_address_y, business.shop_name);
                } else {
                    if (PackageUtils.appIsInstalled(this, IntentUtils.BAIDU_MAP_APP)) {
                        IntentUtils.baiduMarker(this, business.shop_address_x, business.shop_address_y, business.shop_name);
                    } else {
                        if (PackageUtils.appIsInstalled(this, IntentUtils.TX_MAP_APP)) {
                            IntentUtils.txMarker(this, business.shop_address_x, business.shop_address_y, business.shop_name);
                        } else {
                            showToast("请安装地图应用！");
                        }
                    }
                }
                break;
            case R.id.lat_gifts:
                StoreGoodsListActivity.start(this, business.store_id);
                break;
        }

    }

    @Override
    public void showStoreDet(BusinessNew business) {
        this.business = business;
        latGifts.setVisibility(business.is_gift == 0 ? View.GONE : View.VISIBLE);
        GlideArms.with(this).load(business.shop_doorhead).placeholder(R.mipmap.side_b_placeholder).into(imHead);
        tvName.setText(business.shop_name);
        GlideArms.with(this).load(business.level_img).into(imClass);
        if (TextUtils.isEmpty(business.address_details)) {
            tvLocation.setText(business.location_address);
        } else {
            tvLocation.setText(business.location_address + business.address_details);
        }
        tvTime.setText(String.format("%s-%s", business.start_time, business.end_time));
        tvType.setText(business.type_name);
        if (TextUtils.isEmpty(business.introduction)) {
            tvDesc.setText("暂未填写店铺详情");
        } else {
            tvDesc.setText(business.introduction);
        }
        BusinessNew.PictureBean pictureOld = business.picture;
        if (pictureOld != null) {
            String image1 = pictureOld.image1;
            String image2 = pictureOld.image2;
            String image3 = pictureOld.image3;
            if (business.new_picture == null) {
                business.new_picture = new ArrayList<>();
            }
            if (!TextUtils.isEmpty(image1)) business.new_picture.add(new PictureC(image1));
            if (!TextUtils.isEmpty(image2)) business.new_picture.add(new PictureC(image2));
            if (!TextUtils.isEmpty(image3)) business.new_picture.add(new PictureC(image3));
        }
        if (business.new_picture != null && business.new_picture.size() > 0) {
            latPics.setVisibility(View.VISIBLE);
            adapter.setNewData(business.new_picture);
        } else {
            latPics.setVisibility(View.GONE);
        }

    }

}
