package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.AuthInfo;
import com.wta.NewCloudApp.mvp.model.entity.BClass;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.ErrorBusiness;
import com.wta.NewCloudApp.mvp.model.entity.AliInfo;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.mvp.ui.widget.link_with4_class.Street;

import java.util.List;
import java.util.TreeMap;

import io.reactivex.Observable;

public interface IBusinessModel extends IModel {
    Observable<Result<List<Business>>> getBusiness(boolean isRefresh, double lat, double lag);
    Observable<Result<Business>> getBusinessState();
    Observable<Result<List<BType>>> getBTypeList();
    Observable<Result<List<BClass>>> getBClassList();
    Observable<Result<List<Street>>> getStreets(int townID);
    Observable<Result<AuthInfo>> uploadAuth(String a,String b,String c,String d);
    Observable<Result<Business>> addStoreInfo(String name,int shop_type,int shop_level,double shop_address_x,double shop_address_y,String start_time,String end_time,
                                              String shop_door_head,int province,int city,int district,int town,String location_address,String address);
    Observable<Result<ErrorBusiness>> getStoreMsg();

    Observable<Result<Business>> getAllStoreInfo();

    Observable<Result<Business>> modifyStore(String shop_doorhead, String start_time, String end_time, int shop_type, String telephone, String introduction, TreeMap<String,Object> picture);

    Observable<Result<Business>> getStoreDet(int storeID);

    Observable<Result<AliInfo>> getAlipayAuthInfo();

    Observable<Result<AliInfo>> uploadAlipayId(String id);

    Observable<Result<User>> checkBindAlipay();

    Observable<Result<User>> getBPower();

    Observable<Result<List<Business>>> getSearchResult(boolean isRefresh,String keywords);

    Observable<Result<Business>> getStoreState();
    Observable<Result<Business>> getBQRData();
}
