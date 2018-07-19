package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;


public interface GroupContract {

    interface View extends IView {
        void showTeam(Result<User> userResult);
    }
}
