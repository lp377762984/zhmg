package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.PayContract;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.PayInfo;
import com.wta.NewCloudApp.mvp.model.entity.Payback;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import javax.inject.Inject;


@ActivityScope
public class PayPresenter extends BBasePresenter<IBusinessModel, PayContract.View> {

    @Inject
    public PayPresenter(IBusinessModel model, PayContract.View rootView) {
        super(model, rootView);
    }

    public void pay(int pay_type, String sellerId, String total) {
        doRequest(buildRequest(mModel.pay(pay_type, sellerId, total)), 1);
    }

    public void checkSuccess(String type, String orderID) {
        doRequest(buildRequest(mModel.getPayback(orderID, type)), 2);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.pay(((PayInfo) result.data));
        } else if (what == 2) {
            mRootView.showPayback(((Payback) result.data));
        }
    }
}
