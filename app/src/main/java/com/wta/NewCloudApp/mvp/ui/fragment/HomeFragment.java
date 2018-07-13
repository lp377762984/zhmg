package com.wta.NewCloudApp.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.di.component.DaggerHomeComponent;
import com.wta.NewCloudApp.di.module.HomeModule;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.contract.HomeContract;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.presenter.HomePresenter;
import com.wta.NewCloudApp.mvp.ui.activity.SweepActivity;
import com.wta.NewCloudApp.mvp.ui.adapter.HomeListAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeFragment extends BaseLoadingFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.im_sweep)
    ImageView imSweep;
    @BindView(R.id.im_bus_code)
    ImageView imBusCode;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.im_business)
    ImageView imBusiness;
    @BindView(R.id.im_score_shop)
    ImageView imScoreShop;
    HomeListAdapter adapter;
    private List<Bill> billData;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

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
        mPresenter.getMsgList();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showList(Result<List<Bill>> result) {
        if (adapter == null) {
            billData = new ArrayList<>();
            billData.addAll(result.data);
            adapter = new HomeListAdapter(R.layout.home_bill_item, billData);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapter);
        } else {
            billData.clear();
            billData.addAll(result.data);
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.im_sweep, R.id.im_bus_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_sweep:
                ArmsUtils.startActivity(SweepActivity.class);
                break;
            case R.id.im_bus_code:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
