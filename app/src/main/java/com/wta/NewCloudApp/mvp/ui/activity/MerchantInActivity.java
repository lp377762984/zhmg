package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerMerchantInComponent;
import com.wta.NewCloudApp.di.module.MerchantInModule;
import com.wta.NewCloudApp.mvp.contract.MerchantInContract;
import com.wta.NewCloudApp.mvp.model.entity.Benifit;
import com.wta.NewCloudApp.mvp.model.entity.Power;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.mvp.presenter.MerchantInPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.BenifitAdapter;
import com.wta.NewCloudApp.mvp.ui.listener.DetDialogCallback;
import com.wta.NewCloudApp.uitls.DialogUtils;
import com.wta.NewCloudApp.uitls.FinalUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MerchantInActivity extends BaseLoadingActivity<MerchantInPresenter> implements MerchantInContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.im_head)
    RoundedImageView imHead;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMerchantInComponent
                .builder()
                .appComponent(appComponent)
                .merchantInModule(new MerchantInModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_merchant_in;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.getBPower();
    }

    @OnClick(R.id.btn_apply)
    public void onViewClicked() {
        mPresenter.checkBindAlipay();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == FinalUtils.REQUEST_BAUTH) {
            finish();
        }
    }

    @Override
    public int setUIMode() {
        return UIMODE_TRANSPARENT_NOTALL;
    }

    @Override
    public Activity getActivityCet() {
        return this;
    }

    @Override
    public void getIsBindAlipay(int is_alipay) {
        if (is_alipay == 1) {
            MerchantAuthActivity.startAuth(this, 6);
        } else if (is_alipay == 0) {
            DialogUtils.showAlertDialog(this,"需要绑定支付宝才可以继续，是否绑定支付宝？",new DetDialogCallback(){
                @Override
                public void handleRight(Dialog dialog) {
                    mPresenter.getAuthInfo();
                }
            });
        }
    }

    @Override
    public void showPower(User user) {
        tvName.setText(user.nickname);
        tvPhone.setText(user.mobile);
        GlideArms.with(this).load(user.avatar).placeholder(R.mipmap.user_default).into(imHead);
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        List<Power> data = user.condition;
        BenifitAdapter adapter = new BenifitAdapter(R.layout.benifit_item, data);
        recyclerView.setAdapter(adapter);
    }

}
