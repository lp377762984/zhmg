package com.wta.NewCloudApp.mvp.model.api;


import com.wta.NewCloudApp.mvp.model.entity.BankCard;
import com.wta.NewCloudApp.mvp.model.entity.LoginEntity;
import com.wta.NewCloudApp.mvp.model.entity.Msg;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Share;
import com.wta.NewCloudApp.mvp.model.entity.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpServices {
    @FormUrlEncoded
    @POST("/getLoginVerify")
    Observable<Result<User>> sendCode(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST("/WechatLogin")
    Observable<Result<LoginEntity>> wxLogin(@FieldMap Map<String, String> user);

    @FormUrlEncoded
    @POST("/login")
    Observable<Result<LoginEntity>> login(@Field("mobile") String phone, @Field("verify") String code, @Field("recCode") String recCode);

    @FormUrlEncoded
    @POST("/user/set")
    Observable<Result<User>> setUser(@Field("nickname") String nickname, @Field("avatar") String avatar);

    @GET("/user/myShare")
    Observable<Result<Share>> getShare();

    @GET("/messageList")
    Observable<Result<List<Msg>>> getMsgList(@Query("page") int index);

    @FormUrlEncoded
    @POST("/user/nameAuth")
    Observable<Result<User>> auth(@Field("realname") String nickname, @Field("cardno") String avatar);

    @FormUrlEncoded
    @POST("/set/setVerify")
    Observable<Result<User>> bindSendCode(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST("/set/setMobile")
    Observable<Result<User>> bindPhone(@Field("mobile") String phone, @Field("verify") String verify);

    @FormUrlEncoded
    @POST("/set/WechatLogin")
    Observable<Result<User>> bindWX(@FieldMap Map<String, String> user);

    @GET("/user/myTeam")
    Observable<Result<User>> getTeam();

    @FormUrlEncoded
    @POST("/set/setReferee")
    Observable<Result<User>> setRecCode(@Field("number") String code);

    @GET("/bank/getBankList")
    Observable<Result<List<BankCard>>> getCardList();

    @FormUrlEncoded
    @POST("/set/verifyBankCard")
    Observable<Result<BankCard>> addBankCard(@Field("bank_card") String number);

    @FormUrlEncoded
    @POST("/bank/delBanks")
    Observable<Result> delBankCard(@Field("bank_id") int id);
}
