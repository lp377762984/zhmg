package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.GroupContract;
import com.wta.NewCloudApp.mvp.model.UserModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;

import javax.inject.Inject;


@ActivityScope
public class GroupPresenter extends BBasePresenter<UserModel, GroupContract.View> {


    @Inject
    public GroupPresenter(UserModel model, GroupContract.View rootView) {
        super(model, rootView);
    }

    public void getTeam() {
        doRequest(buildRequest(mModel.getTeam()), 1);
    }

    public void setRecCode(String code) {
        doRequest(buildRequest(mModel.setRecCode(code)), 2);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.showTeam((Result<User>) result);
        } else if (what == 2) {
            mRootView.showCode((Result<User>) result);
        }
    }
}
