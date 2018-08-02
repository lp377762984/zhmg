package com.wta.NewCloudApp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerMerchantInComponent;
import com.wta.NewCloudApp.di.module.MerchantInModule;
import com.wta.NewCloudApp.mvp.contract.MerchantInContract;
import com.wta.NewCloudApp.mvp.presenter.MerchantInPresenter;
import com.wta.NewCloudApp.uitls.FinalUtils;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.model.InvokeParam;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MerchantInActivity extends BaseLoadingActivity<MerchantInPresenter> implements MerchantInContract.View {


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

    }

    @OnClick(R.id.btn_apply)
    public void onViewClicked() {
        MerchantAuthActivity.startAuth(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode== FinalUtils.REQUEST_BAUTH){
            finish();
        }
    }
}
