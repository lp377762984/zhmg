package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.di.component.DaggerAuthComponent;
import com.wta.NewCloudApp.di.module.AuthModule;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.contract.AuthContract;
import com.wta.NewCloudApp.mvp.presenter.AuthPresenter;
import com.wta.NewCloudApp.mvp.ui.widget.EditTextHint;

import butterknife.BindView;
import butterknife.OnClick;


public class AuthActivity extends BaseLoadingActivity<AuthPresenter> implements AuthContract.View {

    @BindView(R.id.et_name)
    EditTextHint etName;
    @BindView(R.id.et_number)
    EditTextHint etNumber;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAuthComponent
                .builder()
                .appComponent(appComponent)
                .authModule(new AuthModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_auth;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_apply)
    public void onViewClicked() {

    }
}
