package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.ExRecDetContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.SGDet;

import javax.inject.Inject;


@ActivityScope
public class ExRecDetPresenter extends BBasePresenter<ExRecDetContract.Model, ExRecDetContract.View> {

    @Inject
    public ExRecDetPresenter(ExRecDetContract.Model model, ExRecDetContract.View rootView) {
        super(model, rootView);
    }

    public void getExRecDet(int orderId) {
        doRequest(buildRequest(mModel.getExRecDet(orderId)), 1);
    }

    public void confirmGetGoods(int orderId) {
        doRequest(buildRequest(mModel.confirmGetGoods(orderId)), 2);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.ExRecDet(((SGDet) result.data));
        }else if (what==2){
            mRootView.confirmSuccess(result);
        }
    }

    @Override
    public void handleException(int what, Throwable t) {
        super.handleException(what, t);
        if (what == 1) {
            mRootView.killMyself();
        }
    }
}
