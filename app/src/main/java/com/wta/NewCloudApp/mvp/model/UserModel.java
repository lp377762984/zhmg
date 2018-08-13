package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.Address;
import com.wta.NewCloudApp.mvp.model.entity.AliInfo;
import com.wta.NewCloudApp.mvp.model.entity.BankCard;
import com.wta.NewCloudApp.mvp.model.entity.LoginEntity;
import com.wta.NewCloudApp.mvp.model.entity.Msg;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Share;
import com.wta.NewCloudApp.mvp.model.entity.Update;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.uitls.EncodeUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

@ActivityScope
public class UserModel extends BaseModel implements IUserModel {
    private int index = 1;

    @Inject
    public UserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    private HttpServices getService() {
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
    public Observable<Result<LoginEntity>> wxLogin(String openID) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).wxLogin(openID);
    }

    @Override
    public Observable<Result<User>> setUser(String name, File head) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).setUser(name, EncodeUtils.fileToBase64(head));
    }

    @Override
    public Observable<Result<Share>> getShare() {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getShare();
    }

    @Override
    public Observable<Result<List<Msg>>> getMsgList(boolean isRefresh) {
        if (isRefresh) index = 1;
        else index++;
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getMsgList(index);
    }

    @Override
    public Observable<Result<User>> auth(String nickname, String cardno) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).auth(nickname, cardno);
    }

    @Override
    public Observable<Result<User>> bindSendCode(String phone) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).bindSendCode(phone);
    }

    @Override
    public Observable<Result<LoginEntity>> bindPhone(String mobile, String verify, Map<String, String> map) {
        String name = map.get("name");
        String uid = map.get("uid");
        //String gender = map.get("gender");
        String iconurl = map.get("iconurl");
        map.put("nickname", name);
        map.put("type", "weixin");
        map.put("openid", uid);
        map.put("headimg", iconurl);
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).bindPhone(mobile, verify, map);
    }

    @Override
    public Observable<Result<User>> bindWX(Map<String, String> map) {
        String uid = map.get("uid");
        String name = map.get("name");
        //String gender = map.get("gender");
        String iconurl = map.get("iconurl");
        map.put("openid", uid);
        map.put("nickname", name);
        map.put("type", "weixin");
        map.put("headimg", iconurl);
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

    @Override
    public Observable<Result<Update>> checkUpdate(String version) {
        return getService().checkUpdate(version);
    }

    @Override
    public Observable<ResponseBody> downloadApp(String url) {
        return getService().downApp(url);
    }

    @Override
    public Observable<Result<User>> getUserInfo() {
        return getService().getUserInfo();
    }

    @Override
    public Observable<Result<List<Address>>> getAddressList() {
        return getService().getAddressList();
    }

    @Override
    public Observable<Result<Address>> addAddress(String consignee, String mobile, int province, int city, int district, String address, int type) {
        return getService().addAddress(consignee, mobile, province, city, district, address, type);
    }

    @Override
    public Observable<Result<Address>> editAddress(int id, String consignee, String mobile, int province, int city, int district, String address, int type) {
        return getService().editAddress(id,consignee, mobile, province, city, district, address, type);
    }

    @Override
    public Observable<Result<Address>> setDefaultAddress(int id) {
        return getService().defaultAddress(id);
    }

    @Override
    public Observable<Result<Address>> delAddress(int id) {
        return getService().delAddress(id);
    }

    @Override
    public Observable<Result<AliInfo>> bindAlipay(String openID,String type) {
        return getService().bindAlipay(openID,type);
    }
    public Observable<Result<AliInfo>> getAlipayAuthInfo() {
        return  getService().getAlipayAuthInfo();
    }

}