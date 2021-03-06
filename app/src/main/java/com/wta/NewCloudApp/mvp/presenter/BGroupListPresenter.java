package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.BGroupListContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.ui.activity.BGroupListActivity;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class BGroupListPresenter extends BBasePresenter<IUserModel, BGroupListContract.View> {

    @Inject
    public BGroupListPresenter(IUserModel model, BGroupListContract.View rootView) {
        super(model, rootView);
    }

    public void getGBillsList(boolean isRefresh, int type, String month, String date) {
        doRequest(buildListRequest(mModel.getGBillsList(isRefresh, type, month, date)), 1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            ((BaseDataView) mRootView).getData(what, (Result<List>) result);
        }
    }
}
