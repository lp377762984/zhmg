package com.wta.NewCloudApp.mvp.model.api;


import com.wta.NewCloudApp.mvp.model.entity.Address;
import com.wta.NewCloudApp.mvp.model.entity.AuthInfo;
import com.wta.NewCloudApp.mvp.model.entity.BClass;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.model.entity.BankCard;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.ErrorBusiness;
import com.wta.NewCloudApp.mvp.model.entity.LoginEntity;
import com.wta.NewCloudApp.mvp.model.entity.Msg;
import com.wta.NewCloudApp.mvp.model.entity.PayInfo;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Share;
import com.wta.NewCloudApp.mvp.model.entity.Update;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.mvp.ui.widget.link_with4_class.Street;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface HttpServices {
    @FormUrlEncoded
    @POST("/getLoginVerify")
    Observable<Result<User>> sendCode(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST("/WechatLogin")
    Observable<Result<LoginEntity>> wxLogin(@Field("openid") String openId);

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
    @POST("/WechatVerify")
    Observable<Result<User>> bindSendCode(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST("/WxBindMobile")
    Observable<Result<LoginEntity>> bindPhone(@Field("mobile") String phone, @Field("verify") String verify, @FieldMap Map<String, String> user);

    @FormUrlEncoded
    @POST("/set/WechatBind")
    Observable<Result<User>> bindWX(@FieldMap Map<String, String> user);

    @GET("/user/myTeam")
    Observable<Result<User>> getTeam();

    @FormUrlEncoded
    @POST("/user/setReferee")
    Observable<Result<User>> setRecCode(@Field("number") String code);

    @GET("/bank/getBankList")
    Observable<Result<List<BankCard>>> getCardList();

    @FormUrlEncoded
    @POST("/bank/verifyBankCard")
    Observable<Result<BankCard>> addBankCard(@Field("bank_card") String number);

    @FormUrlEncoded
    @POST("/bank/delBanks")
    Observable<Result> delBankCard(@Field("bank_id") int id);

    @GET("/app/getUpgrade")
    Observable<Result<Update>> checkUpdate(@Query("version") String version);

    @Streaming
    @GET()
    Observable<ResponseBody> downApp(@Url String fileUrl);

    @GET("/user/myGet")
    Observable<Result<User>> getUserInfo();

    @GET("/memberAddress/getList")
    Observable<Result<List<Address>>> getAddressList();

    @FormUrlEncoded
    @POST("/memberAddress/add")
    Observable<Result<Address>> addAddress(@Field("consignee") String consignee, @Field("mobile") String mobile, @Field("province") int province
            , @Field("city") int city, @Field("district") int district, @Field("address") String address, @Field("type") int type);

    @FormUrlEncoded
    @POST("/memberAddress/edit")
    Observable<Result<Address>> editAddress(@Field("address_id") int address_id, @Field("consignee") String consignee, @Field("mobile") String mobile, @Field("province") int province
            , @Field("city") int city, @Field("district") int district, @Field("address") String address, @Field("type") int type);

    @FormUrlEncoded
    @POST("/memberAddress/setDefault")
    Observable<Result<Address>> defaultAddress(@Field("address_id") int address_id);

    @FormUrlEncoded
    @POST("/memberAddress/del")
    Observable<Result<Address>> delAddress(@Field("address_id") int address_id);

    @GET("/admission")
    Observable<Result<Business>> getBState();

    @GET("/category")
    Observable<Result<List<BType>>> getBTypeList();

    @GET("/shopLevel")
    Observable<Result<List<BClass>>> getBClassList();

    @GET("/getRegion")
    Observable<Result<List<Street>>> getStreetInfo(@Query("town") int townId);

    @FormUrlEncoded
    @POST("/qualification")
    Observable<Result<AuthInfo>> uploadBAuth(@Field("shop_business") String a1, @Field("shop_handheld_idcard") String a2,
                                             @Field("shop_facade_idcard") String a3, @Field("shop_reverse_idcard") String a4);

    @FormUrlEncoded
    @POST("/shopDetails")
    Observable<Result<Business>> addStoreInfo(@Field("shop_name") String shop_name, @Field("shop_type") int shop_type, @Field("shop_level") int shop_level, @Field("shop_address_x") double shop_address_x, @Field("shop_address_y") double shop_address_y, @Field("start_time") String start_time, @Field("end_time") String end_time,
                                              @Field("shop_doorhead") String shop_door_head, @Field("province") int province, @Field("city") int city, @Field("district") int district, @Field("twon") int town, @Field("location_address") String location_address, @Field("address") String address);

    @FormUrlEncoded
    @POST("/nearbyList")
    Observable<Result<List<Business>>> getBusinessList(@Field("lat") double lat, @Field("lng") double lng, @Field("page") int page);

    @GET("/errorStore")
    Observable<Result<ErrorBusiness>> getStoreErrorMsg();

    @GET("/myStore")
    Observable<Result<Business>> getAllStoreMsg();

    @FormUrlEncoded
    @POST("/setMyStore")
    Observable<Result<Business>> modifyStore(@Field("shop_doorhead") String shop_doorhead, @Field("start_time") String start_time, @Field("end_time") String end_time,
                                             @Field("shop_type") int shop_type, @Field("telephone") String telephone
            , @Field("introduction") String introduction, @FieldMap TreeMap<String, Object> map);

    @FormUrlEncoded
    @POST("/nearbyDetails")
    Observable<Result<Business>> getStoreDet(@Field("store_id") int storeID);

    @GET("/set/getSign")
    Observable<Result<PayInfo>> getAlipayAuthInfo();

    @FormUrlEncoded
    @POST("/set/setAlipay")
    Observable<Result<PayInfo>> bindAlipay(@Field("openid") String openID, @Field("type") String type);

    @GET("/set/isBindAlipay")
    Observable<Result<User>> checkBindAlipay();

    @GET("/storeCondition")
    Observable<Result<User>> getBPower();

    @FormUrlEncoded
    @POST("/searchStore")
    Observable<Result<List<Business>>> getSearchResult(@Field("search_name") String keywords, @Field("page")int index);
}
