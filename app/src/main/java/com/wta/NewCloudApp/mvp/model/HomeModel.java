package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.contract.HomeContract;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


@FragmentScope
public class HomeModel extends BaseModel implements HomeContract.Model {

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Result<List<Bill>>> getBillList() {
        //return mRepositoryManager.obtainRetrofitService(HttpServices.class).getHomeBillList();
        return Observable.timer(2, TimeUnit.SECONDS)
                .map(new Function<Long, Result<List<Bill>>>() {
                    @Override
                    public Result<List<Bill>> apply(Long aLong) throws Exception {
                        Result<List<Bill>> result = new Result<>(200);
                        List<Bill> bills = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            bills.add(new Bill("￥88088", "aaa", "收款", "06-22 18:00", "现金收益", "bbb", "ccc", "ddd"));
                        }
                        result.data = bills;
                        return result;
                    }
                });
    }

    @Override
    public Observable<Result<Business>> getStoreState() {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getBState();
    }
}