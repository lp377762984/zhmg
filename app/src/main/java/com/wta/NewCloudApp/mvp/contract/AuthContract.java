package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;


public interface AuthContract {

    interface View extends IView {
        void authSuccess(Result<User> result);
    }
}
