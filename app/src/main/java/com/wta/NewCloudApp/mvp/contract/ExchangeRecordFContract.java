package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.ScoreGoods;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.List;

import io.reactivex.Observable;


public interface ExchangeRecordFContract {

    interface View extends BaseDataView {

        void confirmSuccess(Result result);
    }

    interface Model extends IModel {

        Observable<Result<List<ScoreGoods>>> getExchangeRec(int status, boolean isRefresh);

        Observable<Result> sureGetGift(int orderId);
    }
}
