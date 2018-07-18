package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.LoginEntity;
import com.wta.NewCloudApp.mvp.model.entity.Msg;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Share;
import com.wta.NewCloudApp.mvp.model.entity.User;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public interface IUserModel extends IModel {
    Observable<Result<User>> sendCode(String phone);//发送验证码
    Observable<Result<LoginEntity>> login(String phone, String code, String recCode);//登陆
    Observable<Result<User>> wxLogin(Map<String, String> map);//微信登陆
    Observable<Result<User>> setUser(String name, File head);//修改用户信息 上传用户头像
    Observable<Result<Share>> getShare();//分享
    Observable<Result<List<Msg>>> getMsgList(boolean refresh);//获取消息列表
}
