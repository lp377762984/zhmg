package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.Bill;


public interface CashDetContract {

    interface View extends IView {

        void showBillDet(Bill bill);
    }
}
