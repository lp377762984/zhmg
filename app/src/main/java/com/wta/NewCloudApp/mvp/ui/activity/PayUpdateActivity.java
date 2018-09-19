package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerPayUpdateComponent;
import com.wta.NewCloudApp.di.module.PayUpdateModule;
import com.wta.NewCloudApp.mvp.contract.PayUpdateContract;
import com.wta.NewCloudApp.mvp.model.entity.PayInfo;
import com.wta.NewCloudApp.mvp.model.entity.UserClass;
import com.wta.NewCloudApp.mvp.presenter.PayUpdatePresenter;
import com.wta.NewCloudApp.wxapi.pay.PayListener;
import com.wta.NewCloudApp.wxapi.pay.PayManager;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 会员升级支付界面
 */
public class PayUpdateActivity extends BaseLoadingActivity<PayUpdatePresenter> implements PayUpdateContract.View, PayListener {

    @BindView(R.id.tv_class_name)
    TextView tvClassName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    private int gradeId;
    private String orderID;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPayUpdateComponent
                .builder()
                .appComponent(appComponent)
                .payUpdateModule(new PayUpdateModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pay_update;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        gradeId = getIntent().getIntExtra("grade_id", -1);
        mPresenter.getVIPPayInfo(gradeId);
    }

    public static void start(Activity activity, int grade_id) {
        Intent intent = new Intent(activity, PayUpdateActivity.class);
        intent.putExtra("grade_id", grade_id);
        activity.startActivity(intent);
    }

    @OnClick({R.id.lat_alipay, R.id.lat_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lat_alipay:
                mPresenter.pay(gradeId, "alipay");
                break;
            case R.id.lat_wx:
                mPresenter.pay(gradeId, "weixin");
                break;
        }
    }

    @Override
    public void showVIPInfo(UserClass userClass) {
        tvClassName.setText(userClass.clazz);
        tvPrice.setText("￥" + userClass.money);
    }

    @Override
    public void showPayInfo(PayInfo payInfo) {
        orderID = payInfo.out_trade_no;
        PayManager.getInstance().requestPay(this, payInfo, this);
    }

    @Override
    public void showVIPSuccess(UserClass userClass) {
        UpdatePaySuccessActivity.start(this, userClass);
    }

    @Override
    public void payComplete(int payType, int errorCode) {
        if (errorCode == 0) {
            showToast("支付成功");
            mPresenter.checkVIPSuccess(orderID);
        } else if (errorCode == -1) {
            showToast("支付失败");
        } else if (errorCode == -2) {
            showToast("支付取消");
        } else if (errorCode == -3) {
            showToast("网络错误");
        } else if (errorCode == -4) {
            showToast("支付失败");
        }
    }
}
