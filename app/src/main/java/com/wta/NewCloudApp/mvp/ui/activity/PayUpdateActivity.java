package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerPayUpdateComponent;
import com.wta.NewCloudApp.di.module.PayUpdateModule;
import com.wta.NewCloudApp.mvp.contract.PayUpdateContract;
import com.wta.NewCloudApp.mvp.presenter.PayUpdatePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会员升级支付界面
 */
public class PayUpdateActivity extends BaseLoadingActivity<PayUpdatePresenter> implements PayUpdateContract.View {

    @BindView(R.id.tv_class_name)
    TextView tvClassName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPayUpdateComponent
                .builder()
                .appComponent(appComponent)
                .payUpdateModule(new PayUpdateModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pay_update;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.lat_alipay, R.id.lat_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lat_alipay:
                break;
            case R.id.lat_wx:
                break;
        }
    }
}
