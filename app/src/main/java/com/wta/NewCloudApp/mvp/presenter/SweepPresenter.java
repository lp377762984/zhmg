package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.SweepContract;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.PayInfo;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import javax.inject.Inject;

@ActivityScope
public class SweepPresenter extends BBasePresenter<IBusinessModel,SweepContract.View> {

    @Inject
    public SweepPresenter(IBusinessModel model, SweepContract.View rootView) {
        super(model, rootView);
    }

    public void getBusinessInfo(String sellerID) {
        doRequest(buildRequest(mModel.getBusinessInfo(sellerID)), 1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.showBusinessMsg(((Business) result.data));
        }
    }
}
