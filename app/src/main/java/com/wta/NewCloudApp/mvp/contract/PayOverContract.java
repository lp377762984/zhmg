package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.Payback;


public interface PayOverContract {

    interface View extends IView {

        void showPayback(Payback payback);
    }
}
