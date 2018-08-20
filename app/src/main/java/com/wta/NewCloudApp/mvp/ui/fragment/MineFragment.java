package com.wta.NewCloudApp.mvp.ui.fragment;

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
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.di.component.DaggerMineComponent;
import com.wta.NewCloudApp.di.module.MineModule;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.contract.MineContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Share;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.mvp.presenter.MinePresenter;
import com.wta.NewCloudApp.mvp.ui.activity.CardListActivity;
import com.wta.NewCloudApp.mvp.ui.activity.GroupActivity;
import com.wta.NewCloudApp.mvp.ui.activity.MsgActivity;
import com.wta.NewCloudApp.mvp.ui.activity.SettingActivity;
import com.wta.NewCloudApp.mvp.ui.activity.UserMsgActivity;
import com.wta.NewCloudApp.mvp.ui.activity.WebViewActivity;
import com.wta.NewCloudApp.uitls.FinalUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

import static android.util.TypedValue.COMPLEX_UNIT_SP;


public class MineFragment extends BaseLoadingFragment<MinePresenter> implements MineContract.View, View.OnClickListener {

    @BindView(R.id.im_head)
    RoundedImageView imHead;
    Unbinder unbinder;
    @BindView(R.id.im_setting)
    ImageView imSetting;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.tv_score)
    TextView tvScore;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadmore(false);
        ClassicsHeader ch = new ClassicsHeader(getActivity());
        ch.setTextSizeTitle(COMPLEX_UNIT_SP,14);
        ch.setDrawableArrowSize(15);
        ch.setDrawableProgressSize(15);
        ch.setEnableLastTime(false);
        ch.setAccentColorId(R.color.white);
        ch.setPrimaryColorId(R.color.style_color);
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
    private void showUserMsg(){
        GlideArms.with(this)
                .load(AppConfig.getInstance().getString("avatar", null))
                .placeholder(R.mipmap.user_default)
                .into(imHead);
        tvNickName.setText(AppConfig.getInstance().getString("nickname", null));
        tvClass.setText(AppConfig.getInstance().getString("group_name", null));
        tvScore.setText(String.valueOf(AppConfig.getInstance().getInt("white_score", 0)));
    }

    @OnClick({R.id.im_head, R.id.im_setting, R.id.lat_record, R.id.lat_card,R.id.lat_bill,
            R.id.lat_group, R.id.lat_share, R.id.lat_location, R.id.lat_about_us, R.id.lat_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_head:
                ArmsUtils.startActivity(UserMsgActivity.class);
                break;
            case R.id.im_setting:
                ArmsUtils.startActivity(SettingActivity.class);
                break;
            case R.id.lat_msg:
                ArmsUtils.startActivity(MsgActivity.class);
                break;
            case R.id.lat_record:
                showToast("未开放");
                break;
            case R.id.lat_card:
                ArmsUtils.startActivity(CardListActivity.class);
                break;
            case R.id.lat_group:
                ArmsUtils.startActivity(GroupActivity.class);
                break;
            case R.id.lat_share:
                mPresenter.startShare();
                break;
            case R.id.lat_location:
                showToast("未开放");
                break;
            case R.id.lat_about_us:
                WebViewActivity.start(getActivity(), "关于我们", FinalUtils.ABOUT_US);
                break;
            case R.id.lat_bill:
                showToast("未开放");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (dialog!=null && dialog.isShowing()) dialog.dismiss();
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
            case R.id.imageView:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
            dialog.findViewById(R.id.imageView).setOnClickListener(this);
        }
        dialog.show();
    }

    @Override
    public void showUser(Result<User> result) {
        AppConfig.getInstance().putUser(result.data);
        showUserMsg();
    }

    @Override
    public void getData(int what, List data) {

    }

    @Override
    public void showListLoading() {
        refreshLayout.autoRefresh();
    }

    @Override
    public void hideListLoading() {
        refreshLayout.finishRefresh();
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
