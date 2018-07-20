package com.wta.NewCloudApp.mvp.model;

import android.util.Base64;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.BankCard;
import com.wta.NewCloudApp.mvp.model.entity.LoginEntity;
import com.wta.NewCloudApp.mvp.model.entity.Msg;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Share;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.uitls.FileUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

@ActivityScope
public class UserModel extends BaseModel implements IUserModel {
    private int index = 1;

    @Inject
    public UserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    private HttpServices getService(){
        return mRepositoryManager.obtainRetrofitService(HttpServices.class);
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
    public Observable<Result<LoginEntity>> wxLogin(Map<String, String> map) {
        String uid = map.get("uid");
        String name = map.get("name");
        //String gender = map.get("gender");
        String iconurl = map.get("iconurl");
        map.put("openid",uid);
        map.put("nickname",name);
        map.put("type","weixin");
        map.put("headimg",iconurl);
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).wxLogin(map);
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

    @Override
    public Observable<Result<List<Msg>>> getMsgList(boolean isRefresh) {
        if (isRefresh) index = 1;
        else index++;
        return  mRepositoryManager.obtainRetrofitService(HttpServices.class).getMsgList(index);
    }

    @Override
    public Observable<Result<User>> auth(String nickname, String cardno) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).auth(nickname,cardno);
    }

    @Override
    public Observable<Result<User>> bindSendCode(String phone) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).bindSendCode(phone);
    }

    @Override
    public Observable<Result<User>> bindPhone(String mobile, String verify) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).bindPhone(mobile,verify);
    }

    @Override
    public Observable<Result<User>> bindWX(Map<String, String> map) {
        String uid = map.get("uid");
        String name = map.get("name");
        //String gender = map.get("gender");
        String iconurl = map.get("iconurl");
        map.put("openid",uid);
        map.put("nickname",name);
        map.put("type","weixin");
        map.put("headimg",iconurl);
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).bindWX(map);
    }

    @Override
    public Observable<Result<User>> getTeam() {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getTeam();
    }

    public Observable<Result<User>> setRecCode(String code) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).setRecCode(code);
    }

    @Override
    public Observable<Result<List<BankCard>>> getCardList() {
        return getService().getCardList();
    }

    @Override
    public Observable<Result<BankCard>> addBankCard(String cardNumber) {
        return getService().addBankCard(cardNumber);
    }

    @Override
    public Observable<Result> delBankCard(int id) {
        return getService().delBankCard(id);
    }

}