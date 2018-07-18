package com.wta.NewCloudApp.mvp.model;

import android.util.Base64;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.LoginEntity;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Share;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.uitls.FileUtils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

@ActivityScope
public class UserModel extends BaseModel implements IUserModel {

    @Inject
    public UserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Result<User>> sendCode(String phone) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).sendCode(phone);
    }

    @Override
    public Observable<Result<LoginEntity>> login(String phone, String code, String recCode) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).login(phone, code, recCode);
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
    public Observable<Result<User>> setUser(String name, File head) {
        String avatar = null;
        if (head != null && head.exists()) {
            byte[] bytes = FileUtils.File2byte(head);
            avatar = "data:image/jpeg;base64," + Base64.encodeToString(bytes, Base64.DEFAULT);
        }
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).setUser(name, avatar);
    }

    @Override
    public Observable<Result<Share>> getShare() {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getShare();
    }

}