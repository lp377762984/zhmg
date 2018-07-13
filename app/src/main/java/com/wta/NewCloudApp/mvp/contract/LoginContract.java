package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.mvp.ui.activity.LoginActivity;


public interface LoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        LoginActivity getActivity();
        void timeCutDown(Result<User> results);
        void loginSuccess(Result<User> results);
    }
}
