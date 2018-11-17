package com.wta.NewCloudApp.mvp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.di.component.DaggerMineComponent;
import com.wta.NewCloudApp.di.module.MineModule;
import com.wta.NewCloudApp.mvp.contract.MineContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Share;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.mvp.presenter.MinePresenter;
import com.wta.NewCloudApp.mvp.ui.activity.AboutUsActivity;
import com.wta.NewCloudApp.mvp.ui.activity.AddressListActivity;
import com.wta.NewCloudApp.mvp.ui.activity.CardListActivity;
import com.wta.NewCloudApp.mvp.ui.activity.ExtraRecordActivity;
import com.wta.NewCloudApp.mvp.ui.activity.GetListActivity;
import com.wta.NewCloudApp.mvp.ui.activity.GroupActivity;
import com.wta.NewCloudApp.mvp.ui.activity.LoginActivity;
import com.wta.NewCloudApp.mvp.ui.activity.MsgActivity;
import com.wta.NewCloudApp.mvp.ui.activity.ScoreListActivity;
import com.wta.NewCloudApp.mvp.ui.activity.SettingActivity;
import com.wta.NewCloudApp.mvp.ui.activity.UserMsgActivity;
import com.wta.NewCloudApp.mvp.ui.activity.UserQRActivity;
import com.wta.NewCloudApp.uitls.ConfigTag;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static android.util.TypedValue.COMPLEX_UNIT_SP;


public class MineFragment extends BaseLoadingFragment<MinePresenter> implements MineContract.View, View.OnClickListener {

    @BindView(R.id.im_head)
    RoundedImageView imHead;
    @BindView(R.id.im_setting)
    ImageView imSetting;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_extra_bind)
    TextView tvExtraBind;
    @BindView(R.id.lat_record)
    RelativeLayout latRecord;
    @BindView(R.id.lat_card)
    RelativeLayout latCard;
    @BindView(R.id.lat_group)
    RelativeLayout latGroup;
    @BindView(R.id.lat_share)
    RelativeLayout latShare;
    @BindView(R.id.lat_location)
    RelativeLayout latLocation;
    @BindView(R.id.lat_about_us)
    RelativeLayout latAboutUs;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.lat_extra_record)
    RelativeLayout latExtraRecord;
    @BindView(R.id.lat_extra_cash)
    RelativeLayout latExtraCash;
    @BindView(R.id.lat_extra_bind)
    RelativeLayout latExtraBind;

    private BottomSheetDialog dialog;
    private Share share;

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
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadmore(false);
        ClassicsHeader ch = new ClassicsHeader(getActivity());
        ch.setTextSizeTitle(COMPLEX_UNIT_SP, 14);
        ch.setDrawableArrowSize(15);
        ch.setDrawableProgressSize(15);
        ch.setEnableLastTime(false);
        refreshLayout.setHeaderHeight(48);
        refreshLayout.setRefreshHeader(ch);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getUserInfo();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        showUserMsg();
    }

    private void showUserMsg() {
        GlideArms.with(this)
                .load(AppConfig.getInstance().getString("avatar", null))
                .placeholder(R.mipmap.user_default)
                .into(imHead);
        if (AppConfig.getInstance().getBoolean(ConfigTag.IS_LOGIN, false))
            tvNickName.setText(AppConfig.getInstance().getString("nickname", null));
        else tvNickName.setText("登陆/注册");
        tvClass.setText(AppConfig.getInstance().getString("group_name", ""));
        try {
            tvScore.setText(String.valueOf(AppConfig.getInstance().getString("white_score", "")));
        } catch (Exception e) {
            tvScore.setText("");
        }
        if (AppConfig.getInstance().getInt(ConfigTag.IS_ALIPAY, 0) == 0) {
            tvExtraBind.setText("未绑定");
            latExtraBind.setEnabled(true);
        } else {
            tvExtraBind.setText("已绑定");
            latExtraBind.setEnabled(false);
        }
    }

    @OnClick({R.id.lat_user, R.id.im_setting, R.id.lat_record, R.id.lat_card, R.id.lat_bill,
            R.id.lat_group, R.id.lat_share, R.id.lat_location, R.id.lat_about_us, R.id.lat_msg,
            R.id.lat_extra_record, R.id.lat_extra_cash, R.id.lat_extra_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lat_user:
                if (!AppConfig.getInstance().getBoolean(ConfigTag.IS_LOGIN, false))
                    ArmsUtils.startActivity(LoginActivity.class);
                else
                    ArmsUtils.startActivity(UserMsgActivity.class);
                break;
            case R.id.im_setting:
                ArmsUtils.startActivity(SettingActivity.class);
                break;
            case R.id.lat_msg:
                if (!AppConfig.getInstance().getBoolean(ConfigTag.IS_LOGIN, false))
                    ArmsUtils.startActivity(LoginActivity.class);
                else
                    ArmsUtils.startActivity(MsgActivity.class);
                break;
            case R.id.lat_record:
                showToast("未开放");
                break;
            case R.id.lat_card:
                if (!AppConfig.getInstance().getBoolean(ConfigTag.IS_LOGIN, false))
                    ArmsUtils.startActivity(LoginActivity.class);
                else
                    ArmsUtils.startActivity(CardListActivity.class);
                break;
            case R.id.lat_group:
                if (!AppConfig.getInstance().getBoolean(ConfigTag.IS_LOGIN, false))
                    ArmsUtils.startActivity(LoginActivity.class);
                else
                    ArmsUtils.startActivity(GroupActivity.class);
                break;
            case R.id.lat_share:
                if (!AppConfig.getInstance().getBoolean(ConfigTag.IS_LOGIN, false))
                    ArmsUtils.startActivity(LoginActivity.class);
                else
                    ArmsUtils.startActivity(UserQRActivity.class);
                break;
            case R.id.lat_location:
                ArmsUtils.startActivity(AddressListActivity.class);
                break;
            case R.id.lat_about_us:
                ArmsUtils.startActivity(AboutUsActivity.class);
                break;
            case R.id.lat_bill:
                if (!AppConfig.getInstance().getBoolean(ConfigTag.IS_LOGIN, false))
                    ArmsUtils.startActivity(LoginActivity.class);
                else
                    ArmsUtils.startActivity(ScoreListActivity.class);
                break;
            case R.id.lat_extra_record:
                ArmsUtils.startActivity(ExtraRecordActivity.class);
                break;
            case R.id.lat_extra_cash:
                ArmsUtils.startActivity(GetListActivity.class);
                break;
            case R.id.lat_extra_bind:
                if (!AppConfig.getInstance().getBoolean(ConfigTag.IS_LOGIN, false))
                    ArmsUtils.startActivity(LoginActivity.class);
                else
                    mPresenter.getAlipayAuthInfo();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        UMWeb web = new UMWeb(share.share_url);
        web.setTitle(share.share_title);//标题
        web.setThumb(new UMImage(getActivity(), share.share_img));  //缩略图
        web.setDescription(share.share_desc);//描述

        switch (v.getId()) {
            case R.id.tv_wx:
                if (!UMShareAPI.get(getActivity()).isInstall(getActivity(), SHARE_MEDIA.WEIXIN)) {
                    ArmsUtils.makeText(App.getInstance(), "您没有安装微信");
                    return;
                }
                new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(new ShareListener())
                        .withMedia(web).share();
                break;
            case R.id.tv_wx_friends:
                if (!UMShareAPI.get(getActivity()).isInstall(getActivity(), SHARE_MEDIA.WEIXIN)) {
                    ArmsUtils.makeText(App.getInstance(), "您没有安装微信");
                    return;
                }
                new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(new ShareListener())
                        .withMedia(web).share();
                break;
            case R.id.tv_qq:
                if (!UMShareAPI.get(getActivity()).isInstall(getActivity(), SHARE_MEDIA.QQ)) {
                    ArmsUtils.makeText(App.getInstance(), "您没有安装QQ");
                    return;
                }
                new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(new ShareListener())
                        .withMedia(web).share();
                break;
            case R.id.tv_qq_zone:
                if (!UMShareAPI.get(getActivity()).isInstall(getActivity(), SHARE_MEDIA.QQ)) {
                    ArmsUtils.makeText(App.getInstance(), "您没有安装QQ");
                    return;
                }
                new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(new ShareListener())
                        .withMedia(web).share();
                break;
            case R.id.tv_cancel:
                break;
        }
    }

    @Override
    public void share(Result<Share> result) {
        share = result.data;
        if (dialog == null) {
            dialog = new BottomSheetDialog(getActivity());
            dialog.setContentView(R.layout.share_dialog);
            dialog.findViewById(R.id.tv_wx).setOnClickListener(this);
            dialog.findViewById(R.id.tv_wx_friends).setOnClickListener(this);
            dialog.findViewById(R.id.tv_qq).setOnClickListener(this);
            dialog.findViewById(R.id.tv_qq_zone).setOnClickListener(this);
            dialog.findViewById(R.id.tv_cancel).setOnClickListener(this);
        }
        dialog.show();
    }

    @Override
    public void showUser(Result<User> result) {
        AppConfig.getInstance().putUser(result.data);
        showUserMsg();
    }

    @Override
    public Activity getActivityCet() {
        return getActivity();
    }

    @Override
    public void bindAliSuccess() {
        AppConfig.getInstance().putInt(ConfigTag.IS_ALIPAY, 1);
        tvExtraBind.setText("已绑定");
        latExtraBind.setEnabled(false);
    }

    @Override
    public void getData(int what, Result<List> result) {

    }

    @Override
    public void showListLoading() {
        refreshLayout.autoRefresh();
    }

    @Override
    public void hideListLoading() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void loadFailed() {

    }

    public static class ShareListener implements UMShareListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ArmsUtils.makeText(App.getInstance(), "分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Timber.d("onError: %s", throwable);
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            ArmsUtils.makeText(App.getInstance(), "分享已取消");
        }
    }

}
