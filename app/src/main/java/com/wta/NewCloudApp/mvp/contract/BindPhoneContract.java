package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.LoginEntity;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;


public interface BindPhoneContract {

    interface View extends IView {
        void sendCode(Result<User> result);

        void bindPhone(Result<LoginEntity> result);
    }

}
