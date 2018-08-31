package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.SideSearchContract;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class SideSearchPresenter extends BBasePresenter<IBusinessModel, SideSearchContract.View> {

    @Inject
    public SideSearchPresenter(IBusinessModel model, SideSearchContract.View rootView) {
        super(model, rootView);
    }

    public void getSearchResult(boolean isRefresh,String keywords) {
        doRequest(buildListRequest(mModel.getSearchResult(isRefresh,keywords)),1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what==1){
            ((BaseDataView) mRootView).getData(what, (Result<List>) result);
        }
    }
}
