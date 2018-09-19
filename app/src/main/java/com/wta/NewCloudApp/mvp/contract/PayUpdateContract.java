package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.PayInfo;
import com.wta.NewCloudApp.mvp.model.entity.UserClass;


public interface PayUpdateContract {

    interface View extends IView {

        void showVIPInfo(UserClass userClass);

        void showPayInfo(PayInfo payInfo);

        void showVIPSuccess(UserClass userClass);
    }

}
