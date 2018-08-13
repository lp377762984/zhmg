package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerMerchantInComponent;
import com.wta.NewCloudApp.di.module.MerchantInModule;
import com.wta.NewCloudApp.mvp.contract.MerchantInContract;
import com.wta.NewCloudApp.mvp.model.entity.Benifit;
import com.wta.NewCloudApp.mvp.presenter.MerchantInPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.BenifitAdapter;
import com.wta.NewCloudApp.uitls.FinalUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MerchantInActivity extends BaseLoadingActivity<MerchantInPresenter> implements MerchantInContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMerchantInComponent
                .builder()
                .appComponent(appComponent)
                .merchantInModule(new MerchantInModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_merchant_in;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        List<Benifit> data = new ArrayList<>();
        data.add(new Benifit("功能权益", "1.入驻成功后，将有商家家二维码。\n2.拥有商家所有服务功能"));
        data.add(new Benifit("积分权益", "1.入驻成功后，将有商家家二维码。\n2.拥有商家所有服务功能"));
        data.add(new Benifit("现金权益", "1.入驻成功后，将有商家家二维码。\n2.拥有商家所有服务功能"));
        BenifitAdapter adapter = new BenifitAdapter(R.layout.benifit_item, data);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.btn_apply)
    public void onViewClicked() {
        mPresenter.checkBindAlipay();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == FinalUtils.REQUEST_BAUTH) {
            finish();
        }
    }

    @Override
    public int setUIMode() {
        return UIMODE_TRANSPARENT_NOTALL;
    }

    @Override
    public Activity getActivityCet() {
        return this;
    }

    @Override
    public void getIsBindAlipay(int is_alipay) {
        if (is_alipay == 1) {
            MerchantAuthActivity.startAuth(this, 6);
        } else if (is_alipay == 0) {
            mPresenter.getAuthInfo();
        }
    }
}
