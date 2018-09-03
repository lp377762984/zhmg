package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.CashGetContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.ui.activity.CashGetMActivity;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class CashGetPresenter extends BBasePresenter<IUserModel, CashGetContract.View> {

    @Inject
    public CashGetPresenter(IUserModel model, CashGetContract.View rootView) {
        super(model, rootView);
    }

    public void getBReceiveList(boolean isRefresh, int type, String searchType, String date) {
        doRequest(buildListRequest(mModel.getBReceiveList(isRefresh,type,searchType,date)),1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what==1){
            ((BaseDataView) mRootView).getData(what, (Result<List>) result);
        }
    }
}
