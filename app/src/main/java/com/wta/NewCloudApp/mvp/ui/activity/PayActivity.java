package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerPayComponent;
import com.wta.NewCloudApp.di.module.PayModule;
import com.wta.NewCloudApp.mvp.contract.PayContract;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.PayInfo;
import com.wta.NewCloudApp.mvp.model.entity.Payback;
import com.wta.NewCloudApp.mvp.presenter.PayPresenter;
import com.wta.NewCloudApp.mvp.ui.widget.EditTextHint;
import com.wta.NewCloudApp.wxapi.pay.PayListener;
import com.wta.NewCloudApp.wxapi.pay.PayManager;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;


public class PayActivity extends BaseLoadingActivity<PayPresenter> implements PayContract.View, PayListener {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_money)
    EditTextHint etMoney;
    @BindView(R.id.im_head)
    RoundedImageView imHead;
    private Business business;
    private String sellerID;
    private String orderID;
    private String type;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPayComponent
                .builder()
                .appComponent(appComponent)
                .payModule(new PayModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pay;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        business = (Business) getIntent().getSerializableExtra("business");
        sellerID = getIntent().getStringExtra("sellerID");
        tvName.setText(business.shop_name);
        GlideArms.with(this).load(business.shop_doorhead).placeholder(R.mipmap.user_default).into(imHead);
        etMoney.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(".") && dest.toString().length() == 0) {
                    return "0.";
                }
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int length = dest.toString().substring(index).length();
                    if (length == 3) {
                        return "";
                    }
                }
                return null;
            }
        }});
    }

    public static void startPay(Activity activity, Business business, String sellerID) {
        Intent intent = new Intent(activity, PayActivity.class);
        intent.putExtra("business", business);
        intent.putExtra("sellerID", sellerID);
        activity.startActivity(intent);
    }

    @Override
    public int setUIMode() {
        return UIMODE_TRANSPARENT_NOTALL;
    }

    @OnClick({R.id.lat_alipay, R.id.lat_wxpay})
    public void onViewClicked(View view) {
        String money = etMoney.getText().toString();
        if (TextUtils.isEmpty(money) || 0 == Double.parseDouble(money)) {
            showToast("请输入有效金额");
            return;
        }
        switch (view.getId()) {
            case R.id.lat_alipay:
                type = "alipay";
                mPresenter.pay(1, sellerID, etMoney.getText().toString());
                break;
            case R.id.lat_wxpay:
                type = "weixin";
                mPresenter.pay(2, sellerID, etMoney.getText().toString());
                break;
        }
    }

    @Override
    public void pay(PayInfo data) {
        orderID = data.out_trade_no;
        PayManager.getInstance().requestPay(this, data, this);
    }

    //错误码（以微信为准）0成功 -1错误 -2取消 -3网络错误 -4其他错误
    @Override
    public void payComplete(int payType, int errorCode) {
        if (errorCode == 0) {
            showToast("支付成功");
            mPresenter.checkSuccess(type, orderID);
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

    @Override
    public void showPayback(Payback payback) {
        PayOverActivity.startPayStatus(this, payback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PayManager.getInstance().destoryListener();
    }
}
