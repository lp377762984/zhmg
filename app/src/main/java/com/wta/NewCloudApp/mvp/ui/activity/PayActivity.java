package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.di.component.DaggerPayComponent;
import com.wta.NewCloudApp.di.module.PayModule;
import com.wta.NewCloudApp.mvp.contract.PayContract;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.PayInfo;
import com.wta.NewCloudApp.mvp.presenter.PayPresenter;
import com.wta.NewCloudApp.mvp.ui.widget.EditTextHint;

import butterknife.BindView;
import butterknife.OnClick;


public class PayActivity extends BaseLoadingActivity<PayPresenter> implements PayContract.View {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_money)
    EditTextHint etMoney;
    @BindView(R.id.im_head)
    RoundedImageView imHead;
    private String sellerID;

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
        sellerID = getIntent().getStringExtra("sellerID");
        mPresenter.getBusinessInfo(sellerID);
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

    public static void startPay(Activity activity, String sellerID) {
        Intent intent = new Intent(activity, PayActivity.class);
        intent.putExtra("sellerID", sellerID);
        activity.startActivity(intent);
    }

    @Override
    public int setUIMode() {
        return UIMODE_TRANSPARENT_NOTALL;
    }

    @OnClick({R.id.lat_alipay, R.id.lat_wxpay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lat_alipay:
                break;
            case R.id.lat_wxpay:
                if (!App.getInstance().getWXAPI().isWXAppInstalled()) {
                    showToast("您没有安装微信客户端。无法使用微信支付");
                    return;
                }
                mPresenter.pay(2, sellerID, etMoney.getText().toString());
                break;
        }
    }

    @Override
    public void pay(PayInfo data) {
        PayReq req = new PayReq();
        req.appId = data.appid;
        req.nonceStr = data.noncestr;
        req.packageValue = data.packageX;
        req.partnerId = data.partnerid;
        req.prepayId = data.prepayid;
        req.timeStamp = data.timestamp + "";
        req.sign = data.sign;
        App.getInstance().getWXAPI().sendReq(req);
    }

    @Override
    public void showBusinessMsg(Business data) {
        tvName.setText(data.shop_name);
        GlideArms.with(this).load(data.shop_doorhead).placeholder(R.mipmap.user_default).into(imHead);
    }
}
