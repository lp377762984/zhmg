package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.VIPListContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.ui.activity.VIPListActivity;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.List;


@ActivityScope
public class VIPListPresenter extends BBasePresenter<IUserModel, VIPListContract.View> {

    @Inject
    public VIPListPresenter(IUserModel model, VIPListContract.View rootView) {
        super(model, rootView);
    }

    public void getVIPList() {
        doRequest(buildListRequest(mModel.getVIPList()), 1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            ((BaseDataView) mRootView).getData(what, (Result<List>) result);
        }
    }

    @Override
    public void handleExcept200(int what, Result rt) {
        if (what == 1) {
            ((BaseDataView) mRootView).loadFailed();
        }
    }
}
