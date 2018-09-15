package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerUpdatePaySuccessComponent;
import com.wta.NewCloudApp.di.module.UpdatePaySuccessModule;
import com.wta.NewCloudApp.mvp.contract.UpdatePaySuccessContract;
import com.wta.NewCloudApp.mvp.presenter.UpdatePaySuccessPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会员升级成功界面
 */
public class UpdatePaySuccessActivity extends BaseLoadingActivity<UpdatePaySuccessPresenter> implements UpdatePaySuccessContract.View {

    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUpdatePaySuccessComponent
                .builder()
                .appComponent(appComponent)
                .updatePaySuccessModule(new UpdatePaySuccessModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_update_pay_success;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_sure)
    public void onViewClicked() {
    }
}
