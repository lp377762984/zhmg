package com.wta.NewCloudApp.mvp.model.entity;

public class ErrorBusiness {

    /**
     * store : {"shop_name":{"info":"那庅","status":"1"},"shop_type":1,"type_name":"办公","shop_level":2,"level_name":"16%级别","location_address":"北京市丰台区","address":"北京市丰台区","shop_doorhead":{"info":"http://zhmg.jjzbest.com/public/uploads/condition/56/20180808115420_288756.png","status":"1"}}
     * photo : {"shop_business":{"info":"http://zhmg.jjzbest.com/public/uploads/condition/56/20180808115254_826556.png","status":"2"},"shop_handheld_idcard":{"info":"http://zhmg.jjzbest.com/public/uploads/condition/56/20180808115254_202256.png","status":"2"},"shop_facade_idcard":{"info":"http://zhmg.jjzbest.com/public/uploads/condition/56/20180808115254_799156.png","status":"1"},"shop_reverse_idcard":{"info":"http://zhmg.jjzbest.com/public/uploads/condition/56/20180808115254_736556.png","status":"2"}}
     */

    public StoreBean store;
    public PhotoBean photo;

    public static class StoreBean {
        /**
         * shop_name : {"info":"那庅","status":"1"}
         * shop_type : 1
         * type_name : 办公
         * shop_level : 2
         * level_name : 16%级别
         * location_address : 北京市丰台区
         * address : 北京市丰台区
         * shop_doorhead : {"info":"http://zhmg.jjzbest.com/public/uploads/condition/56/20180808115420_288756.png","status":"1"}
         */

        public ShopNameBean shop_name;
        public int shop_type;
        public String type_name;
        public int shop_level;
        public String level_name;
        public String location_address;
        public String address_details;
        public String start_time;
        public String end_time;
        public String address;
        public ShopDoorheadBean shop_doorhead;
        public double shop_address_x;
        public double shop_address_y;
        public int province;
        public int city;
        public int district;
        public int twon;

        public static class ShopNameBean {
            /**
             * info : 那庅
             * status : 1
             */

            public String info;
            public int status;
        }

        public static class ShopDoorheadBean {
            /**
             * info : http://zhmg.jjzbest.com/public/uploads/condition/56/20180808115420_288756.png
             * status : 1
             */

            public String info;
            public int status;
        }
    }

    public static class PhotoBean {
        /**
         * shop_business : {"info":"http://zhmg.jjzbest.com/public/uploads/condition/56/20180808115254_826556.png","status":"2"}
         * shop_handheld_idcard : {"info":"http://zhmg.jjzbest.com/public/uploads/condition/56/20180808115254_202256.png","status":"2"}
         * shop_facade_idcard : {"info":"http://zhmg.jjzbest.com/public/uploads/condition/56/20180808115254_799156.png","status":"1"}
         * shop_reverse_idcard : {"info":"http://zhmg.jjzbest.com/public/uploads/condition/56/20180808115254_736556.png","status":"2"}
         */

        public ShopBusinessBean shop_business;
        public ShopHandheldIdcardBean shop_handheld_idcard;
        public ShopFacadeIdcardBean shop_facade_idcard;
        public ShopReverseIdcardBean shop_reverse_idcard;

        public static class ShopBusinessBean {
            /**
             * info : http://zhmg.jjzbest.com/public/uploads/condition/56/20180808115254_826556.png
             * status : 2
             */

            public String info;
            public int status;
        }

        public static class ShopHandheldIdcardBean {
            /**
             * info : http://zhmg.jjzbest.com/public/uploads/condition/56/20180808115254_202256.png
             * status : 2
             */

            public String info;
            public int status;
        }

        public static class ShopFacadeIdcardBean {
            /**
             * info : http://zhmg.jjzbest.com/public/uploads/condition/56/20180808115254_799156.png
             * status : 1
             */

            public String info;
            public int status;
        }

        public static class ShopReverseIdcardBean {
            /**
             * info : http://zhmg.jjzbest.com/public/uploads/condition/56/20180808115254_736556.png
             * status : 2
             */

            public String info;
            public int status;
        }
    }
}
