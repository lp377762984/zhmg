package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.Address;
import com.wta.NewCloudApp.mvp.model.entity.AliInfo;
import com.wta.NewCloudApp.mvp.model.entity.BankCard;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.BillType;
import com.wta.NewCloudApp.mvp.model.entity.LoginEntity;
import com.wta.NewCloudApp.mvp.model.entity.Msg;
import com.wta.NewCloudApp.mvp.model.entity.PayInfo;
import com.wta.NewCloudApp.mvp.model.entity.Payback;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Share;
import com.wta.NewCloudApp.mvp.model.entity.Update;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.mvp.model.entity.UserClass;
import com.wta.NewCloudApp.mvp.model.entity.VIPInfo;
import com.wta.NewCloudApp.mvp.model.entity.WXUserInfo;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface IUserModel extends IModel {
    Observable<Result<User>> sendCode(String phone);//发送验证码

    Observable<Result<LoginEntity>> login(String phone, String code, String recCode);//登陆

    Observable<Result<LoginEntity>> wxLogin(String openId);//微信登陆

    Observable<Result<User>> setUser(String name, File head);//修改用户信息 上传用户头像

    Observable<Result<Share>> getShare();//分享

    Observable<Result<List<Msg>>> getMsgList(boolean refresh);//获取消息列表

    Observable<Result<User>> auth(String nickname, String cardno);//实名认证

    Observable<Result<User>> bindSendCode(String phone);//绑定手机号-获取验证码

    Observable<Result<LoginEntity>> bindPhone(String mobile, String verify, WXUserInfo info);//设置手机号

    Observable<Result<User>> bindWX(WXUserInfo info);//微信绑定

    Observable<Result<User>> getTeam();//我的团队

    Observable<Result<User>> setRecCode(String code);//添加推荐人

    Observable<Result<List<BankCard>>> getCardList();//银行卡列表

    Observable<Result<BankCard>> addBankCard(String cardNumber);//添加银行卡

    Observable<Result> delBankCard(int id);//删除银行卡

    Observable<Result<Update>> checkUpdate(String version);//app更新接口

    Observable<ResponseBody> downloadApp(String url);//app下载

    Observable<Result<User>> getUserInfo();//获取用户信息

    Observable<Result<List<Address>>> getAddressList();//获取地址列表

    Observable<Result<Address>> addAddress(String consignee, String mobile, int province, int city, int district, String address, int type);//增加地址

    Observable<Result<Address>> editAddress(int id, String consignee, String mobile, int province, int city, int district, String address, int type);//编辑地址

    Observable<Result<Address>> setDefaultAddress(int id);//设置默认地址

    Observable<Result<Address>> delAddress(int id);//删除地址

    Observable<Result<AliInfo>> bindAlipay(String openID, String type);

    Observable<Result<AliInfo>> getAlipayAuthInfo();

    Observable<Result<Payback>> getPayback(String orderId, String type);

    Observable<Result<List<BillType>>> getBillsType();

    Observable<Result<List<Bill>>> getBillsList(boolean isRefresh, String status,String type,String searchType,String date);

    Observable<Result<Bill>> billDet(long billId);

    Observable<Result<List<Bill>>> getBReceiveList(boolean isRefresh, int type, String searchType, String date);

    Observable<Result<Bill>> getUScore(int billId);

    Observable<Result<Bill>> getBScore(int billId);
    Observable<Result<Bill>> getRScore(int billId);

    Observable<Result<List<Bill>>> getGBillsList(boolean isRefresh, int type, String month, String date);

    Observable<Result<List<UserClass>>> getVIPList();

    Observable<Result<VIPInfo>> getVIPInfo(int gradeId);

    Observable<Result<List<Bill>>> getAwardBill(boolean isRefresh);

    Observable<Result<UserClass>> getVIPayInfo(int gradeId);

    Observable<Result<PayInfo>> payVIP(int gradeId, String payType);

    Observable<Result<UserClass>> checkVIPSuccess(String orderID);
}
