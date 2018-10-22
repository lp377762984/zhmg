package com.wta.NewCloudApp.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerSGDetComponent;
import com.wta.NewCloudApp.di.module.SGDetModule;
import com.wta.NewCloudApp.mvp.contract.SGDetContract;
import com.wta.NewCloudApp.mvp.presenter.SGDetPresenter;

/**
 * 礼品详情
 */
public class SGDetFragment extends BaseLoadingFragment<SGDetPresenter> implements SGDetContract.View {


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSGDetComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .sGDetModule(new SGDetModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sgdet, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

}
