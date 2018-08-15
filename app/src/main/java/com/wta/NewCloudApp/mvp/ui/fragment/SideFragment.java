package com.wta.NewCloudApp.mvp.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerSideComponent;
import com.wta.NewCloudApp.di.module.SideModule;
import com.wta.NewCloudApp.mvp.contract.SideContract;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.presenter.SidePresenter;
import com.wta.NewCloudApp.mvp.ui.activity.BServiceActivity;
import com.wta.NewCloudApp.mvp.ui.activity.MerchantAuthActivity;
import com.wta.NewCloudApp.mvp.ui.activity.MerchantInActivity;
import com.wta.NewCloudApp.mvp.ui.activity.MerchantInfoActivity;
import com.wta.NewCloudApp.mvp.ui.activity.SideDetActivity;
import com.wta.NewCloudApp.mvp.ui.activity.SideSearchActivity;
import com.wta.NewCloudApp.mvp.ui.adapter.SideAdapter;
import com.wta.NewCloudApp.mvp.ui.listener.DetDialogCallback;
import com.wta.NewCloudApp.mvp.ui.widget.ClearEditText;
import com.wta.NewCloudApp.uitls.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SideFragment extends BaseListFragment<SidePresenter> implements SideContract.View {
    @BindView(R.id.et_content)
    TextView etContent;
    Unbinder unbinder;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSideComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .sideModule(new SideModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_side, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        etContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArmsUtils.startActivity(SideSearchActivity.class);
            }
        });
    }

    @Override
    protected void getAdapter() {
        adapter = new SideAdapter(R.layout.side_item, data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SideDetActivity.startDet(getActivity(), ((Business) data.get(position)).store_id);
            }
        });
    }

    @Override
    public void loadData(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public Activity getFragmentContext() {
        return getActivity();
    }

    @OnClick(R.id.tv_go_in)
    public void click(){
        mPresenter.getBState();
    }
    @Override
    public void handleBState(Result<Business> businessResult) {
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
                showToast("您已入驻商家");
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
