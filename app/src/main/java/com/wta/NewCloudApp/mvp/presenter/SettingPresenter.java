package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.mvp.contract.SettingContract;
import com.wta.NewCloudApp.mvp.model.UserModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.uitls.ConfigTag;

import java.util.Map;

import javax.inject.Inject;


@ActivityScope
public class SettingPresenter extends BBasePresenter<UserModel, SettingContract.View> {

    @Inject
    public SettingPresenter(UserModel model, SettingContract.View rootView) {
        super(model, rootView);
    }

    public void bindWX(Map<String, String> map) {
        doRequest(buildRequest(mModel.bindWX(map)), 1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            showToast(result.msg);
            mRootView.showData((User) result.data);
        }
    }
}
