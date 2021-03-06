package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.AuthInfo;
import com.wta.NewCloudApp.mvp.model.entity.BClass;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.BusinessNew;
import com.wta.NewCloudApp.mvp.model.entity.ErrorBusiness;
import com.wta.NewCloudApp.mvp.model.entity.AliInfo;
import com.wta.NewCloudApp.mvp.model.entity.PayInfo;
import com.wta.NewCloudApp.mvp.model.entity.Payback;
import com.wta.NewCloudApp.mvp.model.entity.Pic;
import com.wta.NewCloudApp.mvp.model.entity.PictureC;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.mvp.ui.widget.link_with4_class.Street;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class BusinessModel extends UserModel implements IBusinessModel {
    private int index = 0;

    @Inject
    public BusinessModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    private HttpServices getService() {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class);
    }

    @Override
    public Observable<Result<List<Business>>> getBusiness(boolean isRefresh, double lat, double lag) {
        if (isRefresh) index = 1;
        else index++;
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getBusinessList(lat, lag, index);
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
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getBClassList();
    }

    @Override
    public Observable<Result<List<Street>>> getStreets(int townID) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getStreetInfo(townID);
    }

    @Override
    public Observable<Result<AuthInfo>> uploadAuth(String a, String b, String c, String d) {
        return getService().uploadBAuth(a, b, c, d);
    }

    @Override
    public Observable<Result<Business>> addStoreInfo(String name, int shop_type, int shop_level, double shop_address_x, double shop_address_y, String start_time, String end_time,
                                                     String shop_door_head, int province, int city, int district, int town, String location_address, String address,String details) {
        return getService().addStoreInfo(name, shop_type, shop_level, shop_address_x, shop_address_y, start_time, end_time, shop_door_head, province, city, district, town, location_address, address,details);
    }

    @Override
    public Observable<Result<ErrorBusiness>> getStoreMsg() {
        return getService().getStoreErrorMsg();
    }

    @Override
    public Observable<Result<Business>> getAllStoreInfo() {
        return getService().getAllStoreMsg();
    }

    @Override
    public Observable<Result<Business>> modifyStore(Business business) {
        return getService().modifyStore(business);
    }

    @Override
    public Observable<Result<BusinessNew>> getStoreDet(int storeID) {
        return getService().getStoreDet(storeID);
    }

    public Observable<Result<AliInfo>> getAlipayAuthInfo() {
        return getService().getAlipayAuthInfo();
    }

    @Override
    public Observable<Result<AliInfo>> uploadAlipayId(String id) {
        return getService().bindAlipay(id, "alipay");
    }

    @Override
    public Observable<Result<User>> checkBindAlipay() {
        return getService().checkBindAlipay();
    }

    @Override
    public Observable<Result<User>> getBPower() {
        return getService().getBPower();
    }

    @Override
    public Observable<Result<List<Business>>> getSearchResult(boolean isRefresh, String keywords) {
        if (isRefresh) index = 1;
        else index++;
        return getService().getSearchResult(keywords, index);
    }

    @Override
    public Observable<Result<Business>> getStoreState() {
        return getService().getBState();
    }

    @Override
    public Observable<Result<Business>> getBQRData() {
        return getService().getBQRData();
    }

    @Override
    public Observable<Result<PayInfo>> pay(int pay_type, String sellerId, String total) {
        if (pay_type == 1) return getService().pay(pay_type, sellerId, total, "乐范儿专属订单", "乐范儿专属订单");
        else return getService().pay(pay_type, sellerId, total, "乐范儿专属订单", null);
    }

    @Override
    public Observable<Result<Business>> getBusinessInfo(String sellerID) {
        return getService().getBusinessInfo(sellerID);
    }

    public Observable<Result<Payback>> getPayback(String orderID, String type) {
        return getService().getPayback(orderID, type);
    }
}
