package com.wta.NewCloudApp.mvp.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerScoreShopComponent;
import com.wta.NewCloudApp.di.module.ScoreShopModule;
import com.wta.NewCloudApp.mvp.contract.ScoreShopContract;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.model.entity.HomeBanner;
import com.wta.NewCloudApp.mvp.presenter.ScoreShopPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.SGSearchTypeAdapter;
import com.wta.NewCloudApp.mvp.ui.fragment.ScoreGoodsListFragment;
import com.wta.NewCloudApp.mvp.ui.widget.ClearEditText;
import com.wta.NewCloudApp.mvp.ui.widget.RoundImageLoader;
import com.wta.NewCloudApp.uitls.ScreenDpiUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

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
    @BindView(R.id.tv_filter)
    TextView tvFilter;
    private String titles[] = new String[]{"线下礼品", "线上礼品"};
    ScoreGoodsListFragment off;
    ScoreGoodsListFragment on;
    PopupWindow popupWindow;
    private List<BType> types;
    private int itemPosition;
    private boolean isBannerStared;

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
        mPresenter.getSGBanner();
    }

    private ArrayList<Fragment> createFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        off = ScoreGoodsListFragment.getInstance(2, "", 0);
        fragments.add(off);
        on = ScoreGoodsListFragment.getInstance(1, "", 0);
        fragments.add(on);
        return fragments;
    }

    @OnClick({R.id.tv_record, R.id.tv_filter, R.id.im_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_record://兑换记录
                ArmsUtils.startActivity(ExchangeRecordActivity.class);
                break;
            case R.id.tv_filter:
                if (types != null && types.size() > 0) {
                    if (popupWindow != null && !popupWindow.isShowing()) {
                        showPopList();
                    }
                } else mPresenter.getSearchTypeList();
                break;
            case R.id.im_back:
                finish();
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
                off.refresh(keywords, -1);
                on.refresh(keywords, -1);
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
        String scoTrs = "积分";
        SpannableStringBuilder ss = new SpannableStringBuilder(score + scoTrs);
        ss.setSpan(new RelativeSizeSpan(0.8f), score.length(), ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvScore.setText(ss);
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
        if (position == 0) off.refresh("", -1);
        else on.refresh("", -1);
        appBarLayout.setExpanded(true, true);
    }

    @Override
    public void showTypeList(List<BType> types) {
        this.types = types;
        this.types.add(0, new BType(0, "全部"));
        showPopList();
    }

    @Override
    public void showBanner(List<HomeBanner> banners) {
        if (banners != null && banners.size() > 0) {
            banner.setVisibility(View.VISIBLE);
            if (isBannerStared) {
                banner.update(banners);
            } else {
                banner.setIndicatorGravity(BannerConfig.RIGHT);
                banner.setImageLoader(new RoundImageLoader());
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        HomeBanner homeBanner = banners.get(position);
                        if (homeBanner.type == 1) {
                            WebViewActivity.start(ScoreShopActivity.this, "活动详情", homeBanner.jump_url);
                        }
                    }
                });
                banner.setImages(banners);
                banner.start();
                isBannerStared = true;
            }
        } else {
            banner.setVisibility(View.GONE);
        }
    }

    private void showPopList() {
        if (types != null && types.size() > 0) {
            if (popupWindow == null) {
                View view = LayoutInflater.from(this).inflate(R.layout.sg_search_type, null);
                RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                SGSearchTypeAdapter typeAdapter = new SGSearchTypeAdapter(R.layout.sg_search_type_item, types, types.get(itemPosition).type_id);
                recyclerView.setAdapter(typeAdapter);
                recyclerView.addOnItemTouchListener(new OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                        BType bType = types.get(position);
                        typeAdapter.setSelectTypeId(bType.type_id);
                        typeAdapter.notifyDataSetChanged();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                popupWindow.dismiss();
                                tvFilter.setText(bType.type_name);
                                on.refresh("", bType.type_id);
                                off.refresh("", bType.type_id);
                            }
                        }, 200);
                    }
                });
                popupWindow = new PopupWindow(view, WRAP_CONTENT, WRAP_CONTENT);
                popupWindow.setOutsideTouchable(true);
            }
            popupWindow.showAsDropDown(tvFilter, 0, (int) ScreenDpiUtils.dp2px(this, 7));
            //set filter drawable
            Drawable drawable = getResources().getDrawable(R.mipmap.tri_up);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            tvFilter.setCompoundDrawables(null, null, drawable, null);
            //set window alpha
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = 0.6f;
            getWindow().setAttributes(lp);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    //set window alpha
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.alpha = 1f;
                    getWindow().setAttributes(lp);
                    //set filter drawable
                    Drawable drawable = getResources().getDrawable(R.mipmap.tri_down);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    tvFilter.setCompoundDrawables(null, null, drawable, null);
                }
            });
        }
    }
}
