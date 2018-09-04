package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.RScoreContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;

import javax.inject.Inject;


@ActivityScope
public class RScorePresenter extends BBasePresenter<IUserModel, RScoreContract.View> {

    @Inject
    public RScorePresenter(IUserModel model, RScoreContract.View rootView) {
        super(model, rootView);
    }
}
