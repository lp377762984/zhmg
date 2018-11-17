package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.Red;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.List;

import io.reactivex.Observable;


public interface GetListContract {

    interface View extends BaseDataView {

    }

    interface Model extends IModel {

        Observable<Result<List<Red>>> getRedList(boolean isRefresh);
    }
}
