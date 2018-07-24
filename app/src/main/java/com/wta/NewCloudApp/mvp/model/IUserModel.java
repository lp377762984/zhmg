package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.BankCard;
import com.wta.NewCloudApp.mvp.model.entity.LoginEntity;
import com.wta.NewCloudApp.mvp.model.entity.Msg;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Share;
import com.wta.NewCloudApp.mvp.model.entity.Update;
import com.wta.NewCloudApp.mvp.model.entity.User;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface IUserModel extends IModel {
    Observable<Result<User>> sendCode(String phone);//发送验证码
    Observable<Result<LoginEntity>> login(String phone, String code, String recCode);//登陆
    Observable<Result<LoginEntity>> wxLogin(String openId);//微信登陆
    Observable<Result<User>> setUser(String name, File head);//修改用户信息 上传用户头像
    Observable<Result<Share>> getShare();//分享
    Observable<Result<List<Msg>>> getMsgList(boolean refresh);//获取消息列表
    Observable<Result<User>> auth(String nickname,String cardno);//实名认证
    Observable<Result<User>> bindSendCode(String phone);//绑定手机号-获取验证码
    Observable<Result<LoginEntity>> bindPhone(String mobile,String verify,Map<String,String> map);//设置手机号
    Observable<Result<User>> bindWX(Map<String, String> map);//微信绑定
    Observable<Result<User>> getTeam();//我的团队
    Observable<Result<User>> setRecCode(String code);//添加推荐人
    Observable<Result<List<BankCard>>> getCardList();//银行卡列表
    Observable<Result<BankCard>> addBankCard(String cardNumber);//添加银行卡
    Observable<Result> delBankCard(int id);//删除银行卡
    Observable<Result<Update>> checkUpdate(String version);//app更新接口
    Observable<ResponseBody> downloadApp(String url);//app下载
    Observable<Result<User>> getUserInfo();//获取用户信息
}
