package com.wta.NewCloudApp.mvp.model.entity;

public class AuthInfo {

    /**
     * code : 1
     * msg : 图片上传成功
     * data : {"shop_business":"/public/uploads/condition/56/20180727101207_304556.png","shop_handheld_idcard":"/public/uploads/condition/56/20180727101207_809956.png","shop_facade_idcard":"/public/uploads/condition/56/20180727101207_659056.png","shop_reverse_idcard":"/public/uploads/condition/56/20180727101207_348056.png","create_time":1532657527,"member_id":56}
     */

    public int code;
    public String msg;
    public AuthContent data;

    public static class AuthContent {
        /**
         * shop_business : /public/uploads/condition/56/20180727101207_304556.png
         * shop_handheld_idcard : /public/uploads/condition/56/20180727101207_809956.png
         * shop_facade_idcard : /public/uploads/condition/56/20180727101207_659056.png
         * shop_reverse_idcard : /public/uploads/condition/56/20180727101207_348056.png
         * create_time : 1532657527
         * member_id : 56
         */

        public String shop_business;
        public String shop_handheld_idcard;
        public String shop_facade_idcard;
        public String shop_reverse_idcard;
        public int create_time;
        public int member_id;
    }
}
