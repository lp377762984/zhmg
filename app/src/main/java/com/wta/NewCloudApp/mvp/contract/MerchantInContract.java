package com.wta.NewCloudApp.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.User;


public interface MerchantInContract {

    interface View extends IView {

        Activity getActivityCet();

        void getIsBindAlipay(int is_alipay,int card_status);

        void showPower(User user);
    }
}
