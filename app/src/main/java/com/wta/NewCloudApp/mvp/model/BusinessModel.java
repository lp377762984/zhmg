package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.AuthInfo;
import com.wta.NewCloudApp.mvp.model.entity.BClass;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.ErrorBusiness;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.ui.widget.link_with4_class.Street;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
                                                     String shop_door_head, int province, int city, int district, int town, String location_address, String address) {
        return getService().addStoreInfo(name, shop_type, shop_level, shop_address_x, shop_address_y, start_time, end_time, shop_door_head, province, city, district, town, location_address, address);
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
    public Observable<Result<Business>> modifyStore(String shop_doorhead, String start_time, String end_time, int shop_type, String telephone, String introduction, TreeMap<String,Object> picture) {
        return getService().modifyStore(shop_doorhead, start_time, end_time, shop_type, telephone, introduction, picture);
    }

    @Override
    public Observable<Result<Business>> getStoreDet(int storeID) {
        return getService().getStoreDet(storeID);
    }

}
