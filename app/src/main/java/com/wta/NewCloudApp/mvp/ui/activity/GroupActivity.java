package com.wta.NewCloudApp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerGroupComponent;
import com.wta.NewCloudApp.di.module.GroupModule;
import com.wta.NewCloudApp.mvp.contract.GroupContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.mvp.presenter.GroupPresenter;
import com.wta.NewCloudApp.uitls.FinalUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的伙伴
 */
public class GroupActivity extends BaseLoadingActivity<GroupPresenter> implements GroupContract.View {

    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.im_head)
    ImageView imHead;

    @BindView(R.id.tv_class_name)
    TextView tvClassName;
    @BindView(R.id.im_class)
    ImageView imClass;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGroupComponent
                .builder()
                .appComponent(appComponent)
                .groupModule(new GroupModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_group;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.getTeam();
    }

    @OnClick({R.id.lat_money, R.id.lat_score, R.id.tv_update, R.id.im_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lat_money:

                break;
            case R.id.lat_score:
                ArmsUtils.startActivity(BGroupListActivity.class);
                break;
            case R.id.tv_update:
                ArmsUtils.startActivity(VIPListActivity.class);
                break;
            case R.id.im_code:
                ArmsUtils.startActivity(UserQRActivity.class);
                break;
        }
    }

    @Override
    public void showTeam(Result<User> userResult) {
        User user = userResult.data;
        GlideArms.with(this).load(user.team_img).into(imHead);
        tvCode.setText("我的推广码：" + user.number);
        tvCount.setText(user.people + "");
        tvScore.setText(user.white_score + "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == FinalUtils.REQUEST_REC_CODE) {
            mPresenter.getTeam();
        }
    }
}
