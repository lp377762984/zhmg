package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.BQRContract;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import javax.inject.Inject;


@ActivityScope
public class BQRPresenter extends BBasePresenter<IBusinessModel, BQRContract.View> {

    @Inject
    public BQRPresenter(IBusinessModel model, BQRContract.View rootView) {
        super(model, rootView);
    }

    public void getBQRData() {
        doRequest(buildRequest(mModel.getBQRData()), 1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.showQRCode(((Business) result.data).url);
        }
    }
}
