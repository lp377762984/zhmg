package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.UpdateClazzContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.VIPInfo;


@ActivityScope
public class UpdateClazzPresenter extends BBasePresenter<IUserModel, UpdateClazzContract.View> {

    @Inject
    public UpdateClazzPresenter(IUserModel model, UpdateClazzContract.View rootView) {
        super(model, rootView);
    }

    public void getVIPInfo(int gradeId) {
        doRequest(buildRequest(mModel.getVIPInfo(gradeId)), 1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.showVIPInfo(((VIPInfo) result.data));
        }
    }
}
