package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.AuthContract;
import com.wta.NewCloudApp.mvp.model.UserModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;

import javax.inject.Inject;


@ActivityScope
public class AuthPresenter extends BBasePresenter<UserModel, AuthContract.View> {


    @Inject
    public AuthPresenter(UserModel model, AuthContract.View rootView) {
        super(model, rootView);
    }

    public void getAuth(String realname,String cardno){
        doRequest(buildRequest(mModel.auth(realname,cardno)),1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what==1){
            mRootView.authSuccess((Result<User>) result);
        }
    }
}
