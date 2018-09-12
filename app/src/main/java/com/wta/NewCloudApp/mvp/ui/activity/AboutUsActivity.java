package com.wta.NewCloudApp.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerAboutUsComponent;
import com.wta.NewCloudApp.di.module.AboutUsModule;
import com.wta.NewCloudApp.mvp.contract.AboutUsContract;
import com.wta.NewCloudApp.mvp.presenter.AboutUsPresenter;
import com.wta.NewCloudApp.uitls.FinalUtils;
import com.wta.NewCloudApp.uitls.PackageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AboutUsActivity extends BaseLoadingActivity<AboutUsPresenter> implements AboutUsContract.View {

    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAboutUsComponent
                .builder()
                .appComponent(appComponent)
                .aboutUsModule(new AboutUsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_about_us;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.tv_phone, R.id.lat_desc, R.id.lat_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_phone:
                String phone = tvPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    showToast("没有客服电话");
                } else {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                    startActivity(intent);
                }
                break;
            case R.id.lat_desc:
                WebViewActivity.start(this, "服务介绍", FinalUtils.ABOUT_US);
                break;
            case R.id.lat_protocol:
                WebViewActivity.start(this, "注册协议", FinalUtils.REGISTER_PROTOCOL);
                break;
        }
    }
}
