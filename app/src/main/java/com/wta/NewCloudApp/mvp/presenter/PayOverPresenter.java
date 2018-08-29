package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.PayOverContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.entity.Payback;
import com.wta.NewCloudApp.mvp.model.entity.Result;


@ActivityScope
public class PayOverPresenter extends BBasePresenter<IUserModel, PayOverContract.View> {

    @Inject
    public PayOverPresenter(IUserModel model, PayOverContract.View rootView) {
        super(model, rootView);
    }

    public void checkSuccess(String orderId, String type) {
        doRequest(buildRequest(mModel.getPayback(orderId, type)),1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what==1){
            mRootView.showPayback(((Payback) result.data));
        }
    }
}
