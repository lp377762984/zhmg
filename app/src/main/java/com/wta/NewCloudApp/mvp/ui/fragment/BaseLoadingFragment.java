package com.wta.NewCloudApp.mvp.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.wta.NewCloudApp.uitls.DialogUtils;

public class BaseLoadingFragment<P extends IPresenter> extends BaseFragment<P> implements IView {
    private Dialog progress;
    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        if (progress == null) {
            progress = DialogUtils.createWaitDialog(getActivity());
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

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }
}
