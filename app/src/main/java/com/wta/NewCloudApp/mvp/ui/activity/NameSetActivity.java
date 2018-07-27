package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.di.component.DaggerNameSetComponent;
import com.wta.NewCloudApp.di.component.NameSetComponent;
import com.wta.NewCloudApp.di.module.NameSetModule;
import com.wta.NewCloudApp.di.module.UserMsgModule;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.contract.NameSetContract;
import com.wta.NewCloudApp.mvp.contract.UserMsgContract;
import com.wta.NewCloudApp.mvp.presenter.NameSetPresenter;
import com.wta.NewCloudApp.mvp.ui.widget.EditTextHint;
import com.wta.NewCloudApp.uitls.ConfigTag;
import com.wta.NewCloudApp.uitls.FinalUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class NameSetActivity extends BaseLoadingActivity<NameSetPresenter> implements NameSetContract.View {

    @BindView(R.id.et_name)
    EditTextHint etName;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNameSetComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .nameSetModule(new NameSetModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_name_set;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String name = getIntent().getStringExtra("name");
        if (!TextUtils.isEmpty(name)) {
            etName.setText(name);
            etName.setSelection(name.length());
        }
    }

    public static void start(Activity context, String name) {
        Intent intent = new Intent(context, NameSetActivity.class);
        intent.putExtra("name", name);
        context.startActivityForResult(intent, FinalUtils.REQUEST_SET_NAME);
    }

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        String name = etName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            showToast("请输入昵称");
            return;
        }
        mPresenter.setNickName(name, null, 1);
    }

    @Override
    public void showName(String nickname) {
        AppConfig.getInstance().putString(ConfigTag.NICKNAME, nickname);
        Intent intent = getIntent();
        intent.putExtra("name", nickname);
        setResult(RESULT_OK, intent);
        finish();
    }
}
