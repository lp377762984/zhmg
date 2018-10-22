package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.SG2;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import io.reactivex.Observable;


public interface ScoreGoodsListContract {

    interface View extends BaseDataView {

        void getMulti(int what, SG2 sg2);
    }

    interface Model extends IModel {

        Observable<Result<SG2>> getScoreGoods(int position, int type, String keywords, Double lat, Double lon, boolean isRefresh);
    }
}
