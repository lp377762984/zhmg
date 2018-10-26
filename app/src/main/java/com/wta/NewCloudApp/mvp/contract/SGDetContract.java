package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.SGDet;
import com.wta.NewCloudApp.mvp.model.entity.SGOrder;

import io.reactivex.Observable;


public interface SGDetContract {

    interface View extends IView {

        void showSGDet(SGDet sgDet);

        void showOrderId(int orderId);
    }

    interface Model extends IModel {

        Observable<Result<SGDet>> getSGDet(int type, int goodsId);

        Observable<Result<SGOrder>> exchange(int type, int goodsId, int currentNum, int addressId);
    }
}
