package com.wta.NewCloudApp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.di.component.DaggerGroupComponent;
import com.wta.NewCloudApp.di.module.GroupModule;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.contract.GroupContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.mvp.presenter.GroupPresenter;
import com.wta.NewCloudApp.uitls.FinalUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class GroupActivity extends BaseLoadingActivity<GroupPresenter> implements GroupContract.View {

    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_rec)
    TextView tvRec;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_add)
    TextView tvAdd;

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

    @OnClick({R.id.tv_add, R.id.tv_details})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                AddRecActivity.start(this);
                break;
            case R.id.tv_details:
                showToast("尽请期待");
                break;
        }
    }

    @Override
    public void showTeam(Result<User> userResult) {
        User user = userResult.data;
        tvCode.setText("我的推广码：" + user.number);
        tvAdd.setVisibility(user.is_referee == 0 ? View.VISIBLE : View.GONE);
        if (user.is_referee == 0) {
            tvRec.setText("我的推荐人：暂无");
        } else {
            tvRec.setText("我的推荐人：" + user.referee);
        }
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
