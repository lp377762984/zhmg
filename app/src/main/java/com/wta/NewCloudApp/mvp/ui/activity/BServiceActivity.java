package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.di.component.DaggerBServiceComponent;
import com.wta.NewCloudApp.di.module.BServiceModule;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.contract.BServiceContract;
import com.wta.NewCloudApp.mvp.presenter.BServicePresenter;
import com.wta.NewCloudApp.uitls.TablayoutUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class BServiceActivity extends BaseLoadingActivity<BServicePresenter> implements BServiceContract.View {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.tv_benefit)
    TextView tvBenefit;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.im_btm)
    ImageView imBtm;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBServiceComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .bServiceModule(new BServiceModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bservice; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tabLayout.addTab(tabLayout.newTab().setText("今日收益"));
        tabLayout.addTab(tabLayout.newTab().setText("推荐收益"));
        tabLayout.post(() -> TablayoutUtils.setIndicator(tabLayout, 45, 45));
    }

    @OnClick({R.id.tv_code, R.id.tv_record, R.id.lat_bs_benefit, R.id.lat_bs_details, R.id.im_btm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                break;
            case R.id.tv_record:
                break;
            case R.id.lat_bs_benefit:
                break;
            case R.id.lat_bs_details:
                break;
            case R.id.im_btm:
                break;
        }
    }
}
