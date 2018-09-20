package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerUpdateClazzComponent;
import com.wta.NewCloudApp.di.module.UpdateClazzModule;
import com.wta.NewCloudApp.mvp.contract.UpdateClazzContract;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.mvp.model.entity.UserClass;
import com.wta.NewCloudApp.mvp.model.entity.VIPInfo;
import com.wta.NewCloudApp.mvp.presenter.UpdateClazzPresenter;
import com.wta.NewCloudApp.mvp.ui.listener.DetDialogCallback;
import com.wta.NewCloudApp.uitls.DialogUtils;

import butterknife.BindView;
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
    @BindView(R.id.im_bg)
    ImageView imBg;
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
    private UserClass userClass;
    @BindView(R.id.btn_open)
    Button btnOpen;

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
        mPresenter.getVIPInfo(getIntent().getIntExtra("grade_id", -1));
    }

    @Override
    public int setUIMode() {
        return UIMODE_TRANSPARENT_NOTALL;
    }

    @OnClick(R.id.btn_open)
    public void onViewClicked() {
        if (userClass.open_member == 1) {//线上
            PayUpdateActivity.start(this, userClass.grade_id);
        } else {//线下
            DialogUtils.showAlertDialog(this, userClass.closing_prompt, new DetDialogCallback() {
                @Override
                public void handleRight(Dialog dialog) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + userClass.telephone));
                    startActivity(intent);
                }
            });
        }
    }

    public static void startUpdate(Activity activity, int grade_id) {
        Intent intent = new Intent(activity, UpdateClazzActivity.class);
        intent.putExtra("grade_id", grade_id);
        activity.startActivity(intent);
    }

    @Override
    public void showVIPInfo(VIPInfo vipInfo) {
        User user = vipInfo.member_info;
        GlideArms.with(this).load(user.avatar).placeholder(R.mipmap.user_default).into(imHead);
        GlideArms.with(this).load(user.group_avatar).into(imUserClass);
        tvUserName.setText(user.nickname);
        btnOpen.setEnabled(user.is_member == 0);
        tvIsOpen.setText(user.status);
        userClass = vipInfo.grade;
        GlideArms.with(this).load(userClass.classLogo).into(imClassLogo);
        GlideArms.with(this).load(userClass.bg).into(imBg);

        tvClassName.setText(userClass.clazz);
        tvMoney.setText(userClass.money);
        tvDesc.setText(userClass.status);
        tvPower.setText(userClass._abstract);

    }
}
