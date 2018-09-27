package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.SideDetContract;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;
import com.wta.NewCloudApp.mvp.model.entity.BusinessNew;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import javax.inject.Inject;


@ActivityScope
public class SideDetPresenter extends BBasePresenter<IBusinessModel, SideDetContract.View> {

    @Inject
    public SideDetPresenter(IBusinessModel model, SideDetContract.View rootView) {
        super(model, rootView);
    }

    public void getStoreDet(int storeID){
        doRequest(buildRequest(mModel.getStoreDet(storeID)),1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what==1){
            mRootView.showStoreDet(((BusinessNew) result.data));
        }
    }
}
