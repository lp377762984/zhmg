package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.uitls.DialogUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class BaseLoadingActivity<P extends IPresenter> extends BaseActivity<P> implements IView {
    private Dialog progress;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showLoading() {
        if (progress == null) {
            progress = DialogUtils.createWaitDialog(this);
            progress.setCanceledOnTouchOutside(false);
            progress.setCancelable(false);
        }
        progress.show();
    }

    @Override
    public void hideLoading() {
        if (progress != null && progress.isShowing()) progress.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
