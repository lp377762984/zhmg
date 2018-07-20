package com.wta.NewCloudApp.mvp.model.entity;

import java.io.Serializable;

public class BankCard implements Serializable {

    /**
     * id : 568
     * bank : 中国邮政储蓄银行
     * type : IC绿卡通
     * nature : 借记卡
     * bank_card : 9303
     * is_default : 1
     * bank_logo : http://apiserver.qiniudn.com/youzheng.png
     */

    public int id;
    public String bank;
    public String type;
    public String nature;
    public String bank_card;
    public int is_default;
    public String bank_logo;
}
