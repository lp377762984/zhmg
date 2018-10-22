package com.wta.NewCloudApp.mvp.contract;


import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.ScoreGoods;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.List;

import io.reactivex.Observable;


public interface StoreGoodsListContract {

    interface View extends BaseDataView {

    }

    interface Model extends IModel {

        Observable<Result<List<ScoreGoods>>> getSGList(boolean isRefresh, int shopId);
    }
}
