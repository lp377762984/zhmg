package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.Bill;


public interface BScoreContract {

    interface View extends IView {

        void showBScore(Bill bill);
    }

}
