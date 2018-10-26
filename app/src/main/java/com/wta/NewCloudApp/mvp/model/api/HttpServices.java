package com.wta.NewCloudApp.mvp.model.api;


import com.wta.NewCloudApp.mvp.model.entity.Address;
import com.wta.NewCloudApp.mvp.model.entity.AliInfo;
import com.wta.NewCloudApp.mvp.model.entity.AuthInfo;
import com.wta.NewCloudApp.mvp.model.entity.BClass;
import com.wta.NewCloudApp.mvp.model.entity.BEntity;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.model.entity.BankCard;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.BillType;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.BusinessNew;
import com.wta.NewCloudApp.mvp.model.entity.ErrorBusiness;
import com.wta.NewCloudApp.mvp.model.entity.HomeBanner;
import com.wta.NewCloudApp.mvp.model.entity.LoginEntity;
import com.wta.NewCloudApp.mvp.model.entity.Msg;
import com.wta.NewCloudApp.mvp.model.entity.PayInfo;
import com.wta.NewCloudApp.mvp.model.entity.Payback;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.SG2;
import com.wta.NewCloudApp.mvp.model.entity.SGDet;
import com.wta.NewCloudApp.mvp.model.entity.SGOrder;
import com.wta.NewCloudApp.mvp.model.entity.ScoreGoods;
import com.wta.NewCloudApp.mvp.model.entity.Share;
import com.wta.NewCloudApp.mvp.model.entity.Update;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.mvp.model.entity.UserClass;
import com.wta.NewCloudApp.mvp.model.entity.VIPInfo;
import com.wta.NewCloudApp.mvp.model.entity.WXAccessToken;
import com.wta.NewCloudApp.mvp.model.entity.WXUserInfo;
import com.wta.NewCloudApp.mvp.ui.widget.link_with4_class.Street;
import com.wta.NewCloudApp.uitls.FinalUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
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
    Observable<Result<LoginEntity>> login(@Field("mobile") String phone, @Field("verify") String code, @Field("number") String recCode);

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
    Observable<Result> delAddress(@Field("address_id") int address_id);

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
                                              @Field("shop_doorhead") String shop_door_head, @Field("province") int province, @Field("city") int city, @Field("district") int district, @Field("twon") int town, @Field("location_address") String location_address, @Field("address") String address, @Field("address_details") String address_details);

    @FormUrlEncoded
    @POST("/nearbyList")
    Observable<Result<List<Business>>> getBusinessList(@Field("lat") double lat, @Field("lng") double lng, @Field("page") int page);

    @GET("/errorStore")
    Observable<Result<ErrorBusiness>> getStoreErrorMsg();

    @GET("/newMyStore")
    Observable<Result<Business>> getAllStoreMsg();

    @POST("/newSetMyStore")
    Observable<Result<Business>> modifyStore(@Body() Business picture);

    @FormUrlEncoded
    @POST("/nearbyDetails")
    Observable<Result<BusinessNew>> getStoreDet(@Field("store_id") int storeID);

    @GET("/set/getSign")
    Observable<Result<AliInfo>> getAlipayAuthInfo();

    @FormUrlEncoded
    @POST("/set/setAlipay")
    Observable<Result<AliInfo>> bindAlipay(@Field("openid") String openID, @Field("type") String type);

    @GET("/set/isBindAlipay")
    Observable<Result<User>> checkBindAlipay();

    @GET("/storeCondition")
    Observable<Result<User>> getBPower();

    @FormUrlEncoded
    @POST("/searchStore")
    Observable<Result<List<Business>>> getSearchResult(@Field("search_name") String keywords, @Field("page") int index);

    @GET("/getUrl")
    Observable<Result<Business>> getBQRData();

    @FormUrlEncoded
    @POST("/getPaySign")
    Observable<Result<PayInfo>> pay(@Field("pay_type") int pay_type, @Field("number") String sellerId
            , @Field("total_amount") String total, @Field("body") String body, @Field("subject") String subject);

    @FormUrlEncoded
    @POST("/storeInfo")
    Observable<Result<Business>> getBusinessInfo(@Field("number") String sellerID);

    @FormUrlEncoded
    @POST("/backAppWx")
    Observable<Result<Payback>> getPayback(@Field("out_trade_no") String orderId, @Field("type") String type);

    @GET(FinalUtils.WX_ACCESS_TOKEN)
    Observable<WXAccessToken> getAccessToken(@Query("appid") String wxAppId, @Query("secret") String wxAppSecret
            , @Query("code") String code, @Query("grant_type") String authorization_code);

    @GET(FinalUtils.WX_USER_INFO)
    Observable<WXUserInfo> getWXUserInfo(@Query("access_token") String accessToken, @Query("openid") String openid);

    @GET("/bill/billType")
    Observable<Result<List<BillType>>> getBillsType();

    @GET("/bill/billList")
    Observable<Result<List<Bill>>> getBillsList(@Query("page") int page, @Query("status") String status, @Query("type") String type, @Query("searchType") String searchType, @Query("date") String date);

    @GET("/bill/profitDetail")
    Observable<Result<Bill>> billDet(@Query("billId") long billId);

    @GET("/bill/totalProfitList")
    Observable<Result<List<Bill>>> getBReceiveList(@Query("page") int index, @Query("type") int type, @Query("searchType") String searchType, @Query("date") String date);

    @GET("/bill/dayProfit")
    Observable<BEntity> getBMoney();

    @GET("/bill/billDetail")
    Observable<Result<Bill>> getUScore(@Query("billId") int billId, @Query("status") String status);

    @GET("/bill/billDetail")
    Observable<Result<Bill>> getBScore(@Query("billId") int billId, @Query("status") String status);

    @GET("/bill/billDetail")
    Observable<Result<Bill>> getRScore(@Query("billId") int billId, @Query("status") String status);

    @GET("/banner")
    Observable<Result<List<HomeBanner>>> getHomeBanner();

    @GET("bill/indexBill")
    Observable<Result<List<Bill>>> getHomeBillList();

    @GET("bill/billList")
    Observable<Result<List<Bill>>> getGBillsList(@Query("page") int index, @Query("type") int type, @Query("searchType") String month, @Query("date") String date);

    @GET("/superMember/getRegList")
    Observable<Result<List<UserClass>>> getVIPList();

    @GET("/superMember/getRegInfo")
    Observable<Result<VIPInfo>> getVIPInfo(@Query("grade_id") int gradeId);

    @GET("/superMember/superBill")
    Observable<Result<List<Bill>>> getAwardBill(@Query("page") int index);

    @GET("/superMember/getRegPay")
    Observable<Result<UserClass>> getVIPayInfo(@Query("grade_id") int gradeId);

    @FormUrlEncoded
    @POST("/superMember/GradePurchase")
    Observable<Result<PayInfo>> payVIP(@Field("grade_id") int gradeId, @Field("pay_type") String payType);

    @FormUrlEncoded
    @POST("/superPayBack")
    Observable<Result<UserClass>> checkVIPSuccess(@Field("out_trade_no") String orderID);

    @GET("/user/shareUrl")
    Observable<Result<Share>> shareBigImage();

    @GET("/exchangeList")
    Observable<Result<SG2>> getScoreGoods(@Query("type") int position, @Query("type_id") int type, @Query("search") String keywords,
                                          @Query("lat") Double lat, @Query("lng") Double lon, @Query("page") int index);

    @GET("/moreGifts")
    Observable<Result<List<ScoreGoods>>> getSGList(@Query("shop_id") int shopId, @Query("page") int index);

    @GET("/myGift")
    Observable<Result<List<ScoreGoods>>> getSGListSelf(@Query("page") int index);
    @GET("/exchangeRecord")
    Observable<Result<List<ScoreGoods>>> getExchangeRec(@Query("status")String status, @Query("page")int index);
    @GET("/exchangeType")
    Observable<Result<List<BType>>> getSearchTypeList();
    @GET("/exchangeBanner")
    Observable<Result<List<HomeBanner>>> getSGBanner();
    @GET("/exchangeDetails")
    Observable<Result<SGDet>> getSGDet(@Query("type") int type,@Query("goods_id") int goodsId);
    @GET("/exchange")
    Observable<Result<SGOrder>> exchange(@Query("type")int type, @Query("goods_id")int goodsId, @Query("num")int currentNum, @Query("address_id")int addressId);
    @GET("/determined")
    Observable<Result> sureGetGift(@Query("order_id")int orderId);
    @GET("/recordDetails")
    Observable<Result<SGDet>> getExRecDet(@Query("order_id")int orderId);
    @GET("/giftOrder")
    Observable<Result<List<SGDet>>> getBSGOrder(@Query("status")int status,@Query("page")int index);
    @GET("/determined")
    Observable<Result> confirmGetGoods(@Query("order_id")int orderId);
}
