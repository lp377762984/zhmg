package com.wta.NewCloudApp.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wta.NewCloudApp.di.component.DaggerMineComponent;
import com.wta.NewCloudApp.di.module.MineModule;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.contract.MineContract;
import com.wta.NewCloudApp.mvp.presenter.MinePresenter;
import com.wta.NewCloudApp.mvp.ui.activity.UserMsgActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MineFragment extends BaseLoadingFragment<MinePresenter> implements MineContract.View {

    @BindView(R.id.im_head)
    RoundedImageView imHead;
    Unbinder unbinder;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mineModule(new MineModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.im_head})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_head:
                ArmsUtils.startActivity(UserMsgActivity.class);
                break;
        }
    }
}
