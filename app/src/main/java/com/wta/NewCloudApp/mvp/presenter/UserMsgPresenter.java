package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.mvp.contract.UserMsgContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.api.Api;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.uitls.ConfigTag;

import java.io.File;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@ActivityScope
public class UserMsgPresenter extends BBasePresenter<IUserModel, UserMsgContract.View> {
    @Inject
    public RxErrorHandler mErrorHandler;


    @Inject
    public UserMsgPresenter(IUserModel model, UserMsgContract.View rootView) {
        super(model, rootView);
    }

    public void setUser(String nickName, File head, int type) {
        doRequest(buildRequest(mModel.setUser(nickName, head)), type);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 0) {
            ArmsUtils.makeText(App.getInstance(), "设置昵称成功");
            AppConfig.getInstance().putString(ConfigTag.NICKNAME, ((User) result.data).nickname);
            mRootView.showUserName();
        } else if (what == 1) {
            ArmsUtils.makeText(App.getInstance(), "设置头像成功");
            AppConfig.getInstance().putString(ConfigTag.AVATAR, Api.APP_DOMAIN+((User) result.data).avatar);
        }
    }
}
