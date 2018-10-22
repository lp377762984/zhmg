package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.List;

import io.reactivex.Observable;


public interface ScoreShopContract {

    interface View extends IView {

        void showTypeList(List<BType> types);
    }

    interface Model extends IModel {

        Observable<Result<List<BType>>> getSearchTypeList();
    }
}
