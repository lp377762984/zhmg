package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import timber.log.Timber;

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
                Result result = new Result(404);
                List<Business> businessList = new ArrayList<>();
                if (index < 5) {
                    for (int i = 0; i < 3; i++) {
                        businessList.add(new Business
                                ("http://c.hiphotos.baidu.com/image/pic/item/d439b6003af33a87436092e0ca5c10385343b53f.jpg",
                                        "华华", "服装", "获得的8%", "朝九晚六", "三里屯", "1.5KM"));
                    }
                }
                result.msg="开心哈哈哈";
                result.data ="hahhahah";
                //result.data = businessList;
                Timber.d("apply: " + businessList.size());
                index++;
                return result;
            }
        });
    }
}
