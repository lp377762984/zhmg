package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.Goods2;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.List;

import io.reactivex.Observable;


public interface ExtraRecordFContract {

    interface View extends BaseDataView {

        void confirmSuccess(Result result);
    }

    interface Model extends IModel {

        Observable<Result<List<Goods2>>> getExtraRecordList(int status, boolean isRefresh);

        Observable<Result> sureGetGoods(int orderId);
    }
}
