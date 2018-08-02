package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.List;

import io.reactivex.Observable;


public interface HomeContract {
    interface View extends IView {
        void showList(Result<List<Bill>> bills);
        void showBState(Result<Business> businessResult);
    }

    interface Model extends IModel {
        Observable<Result<List<Bill>>> getBillList();
        Observable<Result<Business>> getStoreState();
    }
}
