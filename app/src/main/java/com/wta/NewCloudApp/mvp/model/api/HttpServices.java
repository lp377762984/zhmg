package com.wta.NewCloudApp.mvp.model.api;


import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpServices {
    @FormUrlEncoded
    @POST("/getLoginVerify")
    Observable<Result<User>> sendCode(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST("/Wechatlogin")
    Observable<Result<User>> wxLogin(@FieldMap Map<String,String> user);

    @FormUrlEncoded
    @POST("/login")
    Observable<Result<User>> login(@Field("phone") String phone,@Field("code") String code,@Field("recCode") String recCode);

    @FormUrlEncoded
    @POST("/getHomeList")
    Observable<Result<List<Bill>>> getHomeBillList();
}
