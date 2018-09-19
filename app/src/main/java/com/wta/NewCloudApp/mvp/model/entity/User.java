package com.wta.NewCloudApp.mvp.model.entity;

import java.util.List;

public class User {

    public int expiring_in;
    public int is_member;
    /**
     * avatar : http://zhmg.com/public/static/base/images/default.jpg
     * white_score : 0
     * nickname : 15510253064
     * card_status : 0
     * number : 1014953064
     * mobile : 15510253064
     * is_weixin : 0
     * wx_name : 已绑定
     * group_name : 会员
     */

    public String avatar;
    public String white_score;
    public String nickname;
    public int card_status;
    public String number;
    public String mobile;
    public int is_weixin;
    public int is_mobile;
    public int is_alipay;
    public String wx_name;
    public String group_name;
    public String group_avatar;
    public String group_money;

    /**
     * team_img : http://zhmg.jjzbest.com/public/static/base/images/join_picbak.png
     * is_referee : 0
     * referee : 上级推荐人电话号
     * people : 2
     */

    public String team_img;
    public int is_referee;
    public String referee;
    public int people;
    public String share_url;
    public String status;
    public List<Power> condition;
}
