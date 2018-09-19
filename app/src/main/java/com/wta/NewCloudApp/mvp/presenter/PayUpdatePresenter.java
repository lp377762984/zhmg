package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.PayUpdateContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.entity.PayInfo;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.UserClass;

import javax.inject.Inject;


@ActivityScope
public class PayUpdatePresenter extends BBasePresenter<IUserModel, PayUpdateContract.View> {

    @Inject
    public PayUpdatePresenter(IUserModel model, PayUpdateContract.View rootView) {
        super(model, rootView);
    }

    public void getVIPPayInfo(int gradeId) {
        doRequest(buildRequest(mModel.getVIPayInfo(gradeId)), 1);
    }

    public void pay(int gradeId, String payType) {
        doRequest(buildRequest(mModel.payVIP(gradeId, payType)), 2);
    }

    public void checkVIPSuccess(String orderID) {
        doRequest(buildRequest(false, mModel.checkVIPSuccess(orderID), true, false), 3);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.showVIPInfo(((UserClass) result.data));
        } else if (what == 2) {
            mRootView.showPayInfo(((PayInfo) result.data));
        } else if (what == 3) {
            mRootView.showVIPSuccess(((UserClass) result.data));
        }
    }

}
