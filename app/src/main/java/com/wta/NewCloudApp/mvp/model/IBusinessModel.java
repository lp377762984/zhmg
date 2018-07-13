package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.List;

import io.reactivex.Observable;

public interface IBusinessModel extends IModel {
    Observable<Result<List<Business>>> getBusiness(boolean isRefresh);
}
