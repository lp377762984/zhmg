package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.contract.HomeContract;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.HomeBanner;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;


@FragmentScope
public class HomeModel extends BaseModel implements HomeContract.Model {

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Result<List<Bill>>> getBillList() {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getHomeBillList();
    }

    @Override
    public Observable<Result<Business>> getStoreState() {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getBState();
    }

    @Override
    public Observable<Result<List<HomeBanner>>> getHomeBanner() {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getHomeBanner();
    }

    @Override
    public Observable<Result<Update>> checkUpdate(String packageVersion) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).checkUpdate(packageVersion);
    }

    @Override
    public Observable<ResponseBody> downloadApp(String url) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).downApp(url);
    }
}