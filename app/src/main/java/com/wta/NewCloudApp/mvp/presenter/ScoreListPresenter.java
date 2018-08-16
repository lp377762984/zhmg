package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.ScoreListContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;

import javax.inject.Inject;


@ActivityScope
public class ScoreListPresenter extends BBasePresenter<IUserModel, ScoreListContract.View> {

    @Inject
    public ScoreListPresenter(IUserModel model, ScoreListContract.View rootView) {
        super(model, rootView);
    }
}
