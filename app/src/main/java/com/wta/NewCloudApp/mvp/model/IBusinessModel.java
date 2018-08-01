package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.BClass;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.model.entity.Business;
        import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.ui.widget.link_with4_class.Street;

import java.util.List;

        import io.reactivex.Observable;

public interface IBusinessModel extends IModel {
    Observable<Result<List<Business>>> getBusiness(boolean isRefresh);
    Observable<Result<Business>> getBusinessState();
    Observable<Result<List<BType>>> getBTypeList();
    Observable<Result<List<BClass>>> getBClassList();
    Observable<Result<List<Street>>> getStreets(int townID);
}
