package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.wta.NewCloudApp.mvp.model.entity.PayInfo;
import com.wta.NewCloudApp.mvp.model.entity.Payback;


public interface PayContract {

    interface View extends IView {

        void pay(PayInfo data);

        void showPayback(Payback payback);
    }

}
