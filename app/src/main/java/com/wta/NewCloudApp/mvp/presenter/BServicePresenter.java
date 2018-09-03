package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.wta.NewCloudApp.mvp.contract.BServiceContract;
import com.wta.NewCloudApp.mvp.model.entity.BEntity;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import javax.inject.Inject;


@ActivityScope
public class BServicePresenter extends BBasePresenter<BServiceContract.Model, BServiceContract.View> {
    @Inject
    public BServicePresenter(BServiceContract.Model model, BServiceContract.View rootView) {
        super(model, rootView);
    }

    public void getBMoney() {
        doRequest(buildRequest(mModel.getBMoney()), 1);
    }

    @Override
    public <K> void handleOther(int what, K k) {
        super.handleOther(what, k);
        if (what == 1 && k instanceof BEntity) {
            if (((BEntity) k).code == 200) {
                mRootView.showBMoney(((BEntity) k).data);
            } else {
                showToast("商户数据获取失败");
            }
        }
    }
}
