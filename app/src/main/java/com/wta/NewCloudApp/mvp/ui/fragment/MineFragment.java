package com.wta.NewCloudApp.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.di.component.DaggerMineComponent;
import com.wta.NewCloudApp.di.module.MineModule;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.contract.MineContract;
import com.wta.NewCloudApp.mvp.presenter.MinePresenter;
import com.wta.NewCloudApp.mvp.ui.activity.SettingActivity;
import com.wta.NewCloudApp.mvp.ui.activity.UserMsgActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MineFragment extends BaseLoadingFragment<MinePresenter> implements MineContract.View {

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

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

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
    }

    @Override
    public void onResume() {
        super.onResume();
        GlideArms.with(this)
                .load(AppConfig.getInstance().getString("avatar", null))
                .placeholder(R.mipmap.user_default)
                .into(imHead);
        tvNickName.setText(AppConfig.getInstance().getString("nickname", null));
        tvClass.setText(AppConfig.getInstance().getString("group_name", null));
        tvScore.setText(String.valueOf(AppConfig.getInstance().getInt("white_score", 0)));
    }

    @OnClick({R.id.im_head, R.id.im_setting, R.id.lat_record, R.id.lat_card,
            R.id.lat_group, R.id.lat_share, R.id.lat_location, R.id.lat_about_us, R.id.im_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_head:
                ArmsUtils.startActivity(UserMsgActivity.class);
                break;
            case R.id.im_setting:
                ArmsUtils.startActivity(SettingActivity.class);
                break;
            case R.id.im_msg:
                break;
            case R.id.lat_record:
                break;
            case R.id.lat_card:
                break;
            case R.id.lat_group:
                break;
            case R.id.lat_share:
                break;
            case R.id.lat_location:
                break;
            case R.id.lat_about_us:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
