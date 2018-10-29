package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.ExchangeRecordFContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.List;


@FragmentScope
public class ExchangeRecordFPresenter extends BBasePresenter<ExchangeRecordFContract.Model, ExchangeRecordFContract.View> {

    @Inject
    public ExchangeRecordFPresenter(ExchangeRecordFContract.Model model, ExchangeRecordFContract.View rootView) {
        super(model, rootView);
    }

    public void getExchangeRec(int status, boolean isRefresh) {
        doRequest(buildListRequest(mModel.getExchangeRec(status, isRefresh)), 1);
    }

    public void sureGetGift(int orderId) {
        doRequest(buildListRequest(mModel.sureGetGift(orderId)), 2);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.getData(what, (Result<List>) result);
        } else if (what == 2) {
            mRootView.confirmSuccess(result);
        }
    }

    @Override
    public void handleException(int what, Throwable t) {
        super.handleException(what, t);
        if (what == 1) {
            mRootView.loadFailed();
        }
    }

    @Override
    protected boolean isActivity() {
        return false;
    }
}
