package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.wta.NewCloudApp.mvp.contract.ExtraRecordFContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.List;

import javax.inject.Inject;


@FragmentScope
public class ExtraRecordFPresenter extends BBasePresenter<ExtraRecordFContract.Model, ExtraRecordFContract.View> {

    @Inject
    public ExtraRecordFPresenter(ExtraRecordFContract.Model model, ExtraRecordFContract.View rootView) {
        super(model, rootView);
    }

    public void getExtraRecordList(int status, boolean isRefresh) {
        doRequest(buildListRequest(mModel.getExtraRecordList(status, isRefresh)), 1);
    }

    public void sureGetGoods(int orderId) {
        doRequest(buildRequest(mModel.sureGetGoods(orderId)), 2);
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
