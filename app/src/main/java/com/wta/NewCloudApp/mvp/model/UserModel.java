package com.wta.NewCloudApp.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


@ActivityScope
public class UserModel extends BaseModel implements IUserModel {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public UserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<Result<User>> sendCode(String phone) {
        //return mRepositoryManager.obtainRetrofitService(HttpServices.class).sendCode(phone);
        return Observable.timer(2, TimeUnit.SECONDS).map(new Function<Long, Result<User>>() {
            @Override
            public Result<User> apply(Long aLong) throws Exception {
                return new Result<>(200);
            }
        });
    }

    @Override
    public Observable<Result<User>> login(String phone, String code, String recCode) {
        //return mRepositoryManager.obtainRetrofitService(HttpServices.class).login(phone,code,recCode);
        return Observable.timer(2, TimeUnit.SECONDS).map(new Function<Long, Result<User>>() {
            @Override
            public Result<User> apply(Long aLong) throws Exception {
                return new Result<>(200);
            }
        });
    }

    @Override
    public Observable<Result<User>> wxLogin(Map<String, String> map) {
//        String uid = map.get("uid");
//        String name = map.get("name");
//        String gender = map.get("gender");
//        String iconurl = map.get("iconurl");
//        return mRepositoryManager.obtainRetrofitService(HttpServices.class).wxLogin(map);

        return Observable.timer(2, TimeUnit.SECONDS).map(new Function<Long, Result<User>>() {
            @Override
            public Result<User> apply(Long aLong) throws Exception {
                return new Result<>(200);
            }
        });
    }

    @Override
    public Observable<Result<User>> setName(String name) {
        return null;
    }

    @Override
    public Observable<Result<User>> setPortrait(File head) {
        return null;
    }
}