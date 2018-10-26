package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;

import butterknife.OnClick;

public class ExSucActivity extends BaseActivity {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_ex_suc;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    public static void start(Activity activity, int orderId) {
        Intent intent = new Intent(activity, ExSucActivity.class);
        intent.putExtra("order_id", orderId);
        activity.startActivity(intent);
    }

    @OnClick({R.id.btn_re, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_re:
                break;
            case R.id.btn_sure:
                break;
        }
    }
}
