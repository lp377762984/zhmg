package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerScoreShopComponent;
import com.wta.NewCloudApp.di.module.ScoreShopModule;
import com.wta.NewCloudApp.mvp.contract.ScoreShopContract;
import com.wta.NewCloudApp.mvp.presenter.ScoreShopPresenter;
import com.wta.NewCloudApp.mvp.ui.fragment.ScoreGoodsListFragment;
import com.wta.NewCloudApp.mvp.ui.widget.ClearEditText;
import com.youth.banner.Banner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 兑换积分商城
 */
public class ScoreShopActivity extends BaseLoadingActivity<ScoreShopPresenter> implements ScoreShopContract.View, TextView.OnEditorActionListener, TextWatcher, OnTabSelectListener {

    @BindView(R.id.et_search)
    ClearEditText etSearch;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.barLayout)
    AppBarLayout appBarLayout;
    private String titles[] = new String[]{"线下礼品", "线上礼品"};
    ScoreGoodsListFragment off;
    ScoreGoodsListFragment on;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerScoreShopComponent
                .builder()
                .appComponent(appComponent)
                .scoreShopModule(new ScoreShopModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_score_shop;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        etSearch.setOnEditorActionListener(this);
        etSearch.addTextChangedListener(this);
        tabLayout.setViewPager(viewPager, titles, this, createFragments());
        tabLayout.setOnTabSelectListener(this);
    }

    private ArrayList<Fragment> createFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        off = ScoreGoodsListFragment.getInstance(2, "", 0);
        fragments.add(off);
        on = ScoreGoodsListFragment.getInstance(1, "", 0);
        fragments.add(on);
        return fragments;
    }

    @OnClick(R.id.tv_record)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_record://兑换记录
                ArmsUtils.startActivity(ExchangeRecordActivity.class);
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            String keywords = etSearch.getText().toString().trim();
            if (TextUtils.isEmpty(keywords)) {
                showToast("搜索内容不能为空");
                return false;
            } else {
                // TODO: 2018/10/17
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s.toString().trim())) {
            // TODO: 2018/10/16
            showToast("展示所有");
        }
    }

    /**
     * 设置用户积分
     *
     * @param score 要设置的积分
     */
    public void setScore(String score) {
        tvScore.setText(score);
    }

    @Override
    public boolean useFragment() {
        return true;
    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {
        if (position==0) off.refresh();
        else on.refresh();
        appBarLayout.setExpanded(true,true);
    }
}
