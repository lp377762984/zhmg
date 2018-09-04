package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.BScoreContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;

import javax.inject.Inject;


@ActivityScope
public class BScorePresenter extends BBasePresenter<IUserModel, BScoreContract.View> {

    @Inject
    public BScorePresenter(IUserModel model, BScoreContract.View rootView) {
        super(model, rootView);
    }
}
