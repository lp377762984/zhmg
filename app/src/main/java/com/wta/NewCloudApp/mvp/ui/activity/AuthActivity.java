package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.di.component.DaggerAuthComponent;
import com.wta.NewCloudApp.di.module.AuthModule;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.contract.AuthContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.mvp.presenter.AuthPresenter;
import com.wta.NewCloudApp.mvp.ui.widget.EditTextHint;
import com.wta.NewCloudApp.uitls.ConfigTag;
import com.wta.NewCloudApp.uitls.FinalUtils;

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
        String name = etName.getText().toString();
        String number = etNumber.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ArmsUtils.makeText(App.getInstance(),"请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(number)) {
            ArmsUtils.makeText(App.getInstance(),"请输入身份证号");
            return;
        }
        mPresenter.getAuth(name,number);
    }

    public static void startAuth(Activity context){
        Intent intent=new Intent(context,AuthActivity.class);
        context.startActivityForResult(intent, FinalUtils.REQUEST_AUTH);
    }

    @Override
    public void authSuccess(Result<User> result) {
        AppConfig.getInstance().putInt(ConfigTag.CARD_STATUS,result.data.card_status);
        setResult(RESULT_OK);
        finish();
    }
}
