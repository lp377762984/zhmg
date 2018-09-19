package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.RecAwardListContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.ui.activity.RecAwardListActivity;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class RecAwardListPresenter extends BBasePresenter<IUserModel, RecAwardListContract.View> {

    @Inject
    public RecAwardListPresenter(IUserModel model, RecAwardListContract.View rootView) {
        super(model, rootView);
    }

    public void getAwardBill(boolean isRefresh) {
        doRequest(buildListRequest(mModel.getAwardBill(isRefresh)), 1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            ((BaseDataView) mRootView).getData(what, (Result<List>) result);
        }
    }
}
