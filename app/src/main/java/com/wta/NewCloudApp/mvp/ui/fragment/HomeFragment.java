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

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerHomeComponent;
import com.wta.NewCloudApp.di.module.HomeModule;
import com.wta.NewCloudApp.mvp.contract.HomeContract;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.presenter.HomePresenter;
import com.wta.NewCloudApp.mvp.ui.activity.BQRActivity;
import com.wta.NewCloudApp.mvp.ui.activity.BServiceActivity;
import com.wta.NewCloudApp.mvp.ui.activity.MerchantAuthActivity;
import com.wta.NewCloudApp.mvp.ui.activity.MerchantInActivity;
import com.wta.NewCloudApp.mvp.ui.activity.MerchantInfoActivity;
import com.wta.NewCloudApp.mvp.ui.activity.SweepActivity;
import com.wta.NewCloudApp.mvp.ui.adapter.HomeListAdapter;
import com.wta.NewCloudApp.mvp.ui.listener.DetDialogCallback;
import com.wta.NewCloudApp.mvp.ui.widget.PJImageLoader;
import com.wta.NewCloudApp.mvp.ui.widget.RoundImageLoader;
import com.wta.NewCloudApp.uitls.DialogUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

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
    private int position;
    private List<String> imgs = new ArrayList<>();

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
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setImageLoader(new RoundImageLoader());
        imgs.add("https://inews.gtimg.com/newsapp_match/0/4865237967/0");
        imgs.add("https://inews.gtimg.com/newsapp_match/0/4865237967/0");
        imgs.add("https://inews.gtimg.com/newsapp_match/0/4865237967/0");
        banner.setImages(imgs);
        banner.start();
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

    @OnClick({R.id.im_sweep, R.id.im_bus_code, R.id.im_business})
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
