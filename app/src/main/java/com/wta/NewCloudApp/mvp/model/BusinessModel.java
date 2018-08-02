package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.BClass;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.ui.widget.link_with4_class.Street;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class BusinessModel extends BaseModel implements IBusinessModel {
    private int index = 0;

    @Inject
    public BusinessModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Result<List<Business>>> getBusiness(boolean isRefresh) {
        return Observable.timer(1, TimeUnit.SECONDS).map(new Function<Long, Result<List<Business>>>() {
            @Override
            public Result<List<Business>> apply(Long aLong) throws Exception {
                if (isRefresh) index = 0;
                Result result = new Result(200);
                List<Business> businessList = new ArrayList<>();
                if (index < 5) {
                    for (int i = 0; i < 3; i++) {
                        businessList.add(new Business
                                ("http://c.hiphotos.baidu.com/image/pic/item/d439b6003af33a87436092e0ca5c10385343b53f.jpg",
                                        "华华", "服装", "获得的8%", "朝九晚六", "三里屯", "1.5KM"));
                    }
                }
                result.msg="开心哈哈哈";
                result.data = businessList;
                index++;
                return result;
            }
        });
    }

    @Override
    public Observable<Result<Business>> getBusinessState() {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getBState();
    }

    @Override
    public Observable<Result<List<BType>>> getBTypeList() {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getBTypeList();
    }

    @Override
    public Observable<Result<List<BClass>>> getBClassList() {
        return mRepositoryManager.obtainCacheService(HttpServices.class).getBClassList();
    }

    @Override
    public Observable<Result<List<Street>>> getStreets(int townID) {
        HttpServices httpServices = mRepositoryManager.obtainRetrofitService(HttpServices.class);
        Observable<Result<List<Street>>> streetInfo = httpServices.getStreetInfo(townID);
        return streetInfo;
    }
}
