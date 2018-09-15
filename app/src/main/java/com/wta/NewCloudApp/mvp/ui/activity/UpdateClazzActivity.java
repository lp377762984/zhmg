package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerUpdateClazzComponent;
import com.wta.NewCloudApp.di.module.UpdateClazzModule;
import com.wta.NewCloudApp.mvp.contract.UpdateClazzContract;
import com.wta.NewCloudApp.mvp.presenter.UpdateClazzPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会员升级界面
 */
public class UpdateClazzActivity extends BaseLoadingActivity<UpdateClazzPresenter> implements UpdateClazzContract.View {

    @BindView(R.id.im_head)
    RoundedImageView imHead;
    @BindView(R.id.im_user_class)
    ImageView imUserClass;
    @BindView(R.id.tv_is_open)
    TextView tvIsOpen;
    @BindView(R.id.im_class_logo)
    ImageView imClassLogo;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_power)
    TextView tvPower;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_class_name)
    TextView tvClassName;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUpdateClazzComponent
                .builder()
                .appComponent(appComponent)
                .updateClazzModule(new UpdateClazzModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_update_clazz;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int setUIMode() {
        return UIMODE_TRANSPARENT_NOTALL;
    }

    @OnClick(R.id.btn_open)
    public void onViewClicked() {

    }
}
