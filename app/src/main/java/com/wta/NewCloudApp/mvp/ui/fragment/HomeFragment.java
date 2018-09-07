package com.wta.NewCloudApp.mvp.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerHomeComponent;
import com.wta.NewCloudApp.di.module.HomeModule;
import com.wta.NewCloudApp.mvp.contract.HomeContract;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.HomeBanner;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.presenter.HomePresenter;
import com.wta.NewCloudApp.mvp.ui.activity.BQRActivity;
import com.wta.NewCloudApp.mvp.ui.activity.BScoreDetActivity;
import com.wta.NewCloudApp.mvp.ui.activity.BServiceActivity;
import com.wta.NewCloudApp.mvp.ui.activity.CashDetActivity;
import com.wta.NewCloudApp.mvp.ui.activity.CashGetMActivity;
import com.wta.NewCloudApp.mvp.ui.activity.MerchantAuthActivity;
import com.wta.NewCloudApp.mvp.ui.activity.MerchantInActivity;
import com.wta.NewCloudApp.mvp.ui.activity.MerchantInfoActivity;
import com.wta.NewCloudApp.mvp.ui.activity.RScoreDetActivity;
import com.wta.NewCloudApp.mvp.ui.activity.ScoreListActivity;
import com.wta.NewCloudApp.mvp.ui.activity.SweepActivity;
import com.wta.NewCloudApp.mvp.ui.activity.UScoreDetActivity;
import com.wta.NewCloudApp.mvp.ui.activity.WebViewActivity;
import com.wta.NewCloudApp.mvp.ui.adapter.HomeListAdapter;
import com.wta.NewCloudApp.mvp.ui.listener.DetDialogCallback;
import com.wta.NewCloudApp.mvp.ui.widget.PJImageLoader;
import com.wta.NewCloudApp.uitls.DialogUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.util.TypedValue.COMPLEX_UNIT_SP;


public class HomeFragment extends BaseLoadingFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.im_sweep)
    ImageView imSweep;
    @BindView(R.id.im_bus_code)
    ImageView imBusCode;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.im_business)
    ImageView imBusiness;
    @BindView(R.id.im_score_shop)
    ImageView imScoreShop;
    HomeListAdapter adapter;
    private List<Bill> billData = new ArrayList<>();
    private int position;
    private List<HomeBanner> imgs = new ArrayList<>();
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ClassicsHeader ch = new ClassicsHeader(getContext());
        ch.setTextSizeTitle(COMPLEX_UNIT_SP, 14);
        ch.setDrawableArrowSize(15);
        ch.setDrawableProgressSize(15);
        ch.setEnableLastTime(false);
        refreshLayout.setRefreshHeader(ch);
        refreshLayout.setHeaderHeight(48);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getHomeBanner();
                mPresenter.getMsgList();
            }
        });
        adapter = new HomeListAdapter(R.layout.home_bill_item, billData);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Bill bill = billData.get(position);
                String totalType = bill.totalType;
                if (totalType.equals("moneyProfit")) {
                    CashGetMActivity.startCashList(getActivity());
                } else if (totalType.equals("integralProfit")) {
                    ArmsUtils.startActivity(ScoreListActivity.class);
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bill bill = billData.get(position);
                if (bill.totalType.equals("integralProfit")) {
                    String status = bill.status;
                    switch (status) {
                        case "saleStatus":
                            BScoreDetActivity.startDet(getActivity(), bill.bill_id);
                            break;
                        case "consumeStatus":
                            UScoreDetActivity.startDet(getActivity(), bill.bill_id);
                            break;
                        case "recommendStatus":
                            RScoreDetActivity.startDet(getActivity(), bill.bill_id);
                            break;
                    }
                } else if (bill.totalType.equals("moneyProfit")) {
                    CashDetActivity.startCashDet(getActivity(), bill.bill_id);
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setEmptyView(R.layout.home_empty);
        mPresenter.getHomeBanner();
        mPresenter.getMsgList();
    }

    @Override
    public void showList(Result<List<Bill>> result) {
        billData.clear();
        for (int i = 0; i < result.data.size(); i++) {
            if (result.data.get(i) != null) {
                billData.add(result.data.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showHomeBanner(List<HomeBanner> homeBanners) {
        imgs.clear();
        imgs.addAll(homeBanners);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setImageLoader(new PJImageLoader());
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                HomeBanner homeBanner = imgs.get(position);
                if (homeBanner.type == 1) {
                    WebViewActivity.start(getActivity(), "活动详情", homeBanner.jump_url);
                }
            }
        });
//        int margin = (int) ScreenDpiUtils.dp2px(getContext(), 15);
//        ConstraintLayout.LayoutParams lp2 = (ConstraintLayout.LayoutParams) banner.getLayoutParams();
//        lp2.height = (getContext().getResources().getDisplayMetrics().widthPixels - 2 * margin) * 537 / 1038;
//        lp2.width = getContext().getResources().getDisplayMetrics().widthPixels - 2 * margin;
//        banner.setLayoutParams(lp2);
        banner.setImages(imgs);
        banner.start();
    }

    @Override
    public void stopRefresh() {
        if (refreshLayout.isRefreshing())
            refreshLayout.finishRefresh();
    }

    @Override
    public void showListFailed() {
        if (billData != null && billData.size() > 0) {
            billData.clear();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showBState(Result<Business> businessResult) {
        String msg = businessResult.data.msg;
        switch (businessResult.data.code_type) {
            case 0://审核中
                DialogUtils.showAlertDialog(getActivity(), msg, new DetDialogCallback());
                break;
            case 1://店铺详情错误
                DialogUtils.showAlertDialog(getActivity(), msg, new DetDialogCallback() {
                    @Override
                    public void handleRight(Dialog dialog) {
                        MerchantInfoActivity.startInfo(getActivity(), 1);
                    }
                });
                break;
            case 2://资质错误
                DialogUtils.showAlertDialog(getActivity(), msg, new DetDialogCallback() {
                    @Override
                    public void handleRight(Dialog dialog) {
                        MerchantAuthActivity.startAuth(getActivity(), 2);
                    }
                });
                break;
            case 3://都错误
                DialogUtils.showAlertDialog(getActivity(), msg, new DetDialogCallback() {
                    @Override
                    public void handleRight(Dialog dialog) {
                        MerchantAuthActivity.startAuth(getActivity(), 3);
                    }
                });
                break;
            case 4://审核通过
                if (position == 1)
                    ArmsUtils.startActivity(BQRActivity.class);
                else
                    ArmsUtils.startActivity(BServiceActivity.class);
                break;
            case 5://店铺详情未填写
                DialogUtils.showAlertDialog(getActivity(), msg, new DetDialogCallback() {
                    @Override
                    public void handleRight(Dialog dialog) {
                        MerchantInfoActivity.startInfo(getActivity(), 5);
                    }
                });
                break;
            case 6://未入驻店铺
                DialogUtils.showAlertDialog(getActivity(), msg, new DetDialogCallback() {
                    @Override
                    public void handleRight(Dialog dialog) {
                        ArmsUtils.startActivity(MerchantInActivity.class);
                    }
                });
                break;
        }
    }

    @OnClick({R.id.im_sweep, R.id.im_bus_code, R.id.im_business, R.id.im_score_shop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_sweep:
                ArmsUtils.startActivity(SweepActivity.class);
                break;
            case R.id.im_bus_code:
                position = 1;
                mPresenter.getStoreState();
                break;
            case R.id.im_business:
                position = 0;
                mPresenter.getStoreState();
                break;
            case R.id.im_score_shop:
                showToast("暂未开放");
                break;
        }
    }
}
