package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerBServiceComponent;
import com.wta.NewCloudApp.di.module.BServiceModule;
import com.wta.NewCloudApp.mvp.contract.BServiceContract;
import com.wta.NewCloudApp.mvp.presenter.BServicePresenter;
import com.wta.NewCloudApp.mvp.ui.widget.MoneyBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商家服务
 */
public class BServiceActivity extends BaseLoadingActivity<BServicePresenter> implements BServiceContract.View {

    @BindView(R.id.tv_benefit)
    TextView tvBenefit;
    @BindView(R.id.im_btm)
    ImageView imBtm;
    @BindView(R.id.mb)
    MoneyBar mb;

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
        mb.setCallBack(mb.new CallbackImp() {
            @Override
            public void clickTail() {
                ArmsUtils.startActivity(BQRActivity.class);
            }
        });
        mPresenter.getBMoney();
    }

    @OnClick({R.id.lat_bs_benefit, R.id.lat_bs_details, R.id.lat_bs_gifts, R.id.lat_bs_order, R.id.im_btm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lat_bs_benefit://收款明细
                CashGetMActivity.startCashList(this);
                break;
            case R.id.lat_bs_details:
                ArmsUtils.startActivity(StoreInfoActivity.class);
                break;
            case R.id.im_btm:
                break;
            case R.id.lat_bs_gifts:
                StoreGoodsListActivity.start(this, -1);
                break;
            case R.id.lat_bs_order:
                ArmsUtils.startActivity(BSGOrderActivity.class);
                break;
        }
    }

    @Override
    public int setUIMode() {
        return UIMODE_TRANSPARENT_NOTALL;
    }

    @Override
    public void showBMoney(String money) {
        tvBenefit.setText(money);
    }
}
