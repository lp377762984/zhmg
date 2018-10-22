package com.wta.NewCloudApp.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class BusinessNew implements Serializable {
    public String distance;
    public int code_type;
    public String msg;
    public String debug;
    public String shop_name;
    public String shop_type;
    public String type_name;
    public String shop_level;
    public String level_name;
    public String shop_address_x;
    public String shop_address_y;
    public String start_time;
    public String end_time;
    public String shop_doorhead;
    public String province;
    public String city;
    public String district;
    public String twon;
    public String location_address;
    public String address;
    public int create_time;
    public String url;
    public int is_gift;

    /**
     * telephone : 400000
     * introduction : two
     * picture : {"image1":"http://zhmg.com/public/uploads/condition/56/20180809123513_667356.png","image2":"http://zhmg.com/public/uploads/condition/56/20180809145832_394456.png","image3":"http://zhmg.com/public/uploads/condition/56/20180809150206_937556.png"}
     */

    public String telephone;
    public String introduction;
    public List<PictureC> new_picture;
    public int store_id;
    public String level_img;
    public int type_id;
    public int img_sum;
    public String address_details;

    /**
     * picture : {"image1":"http://zhmg.com/public/uploads/condition/2/20180925165917_41342.png","image2":"http://zhmg.com/public/uploads/condition/8/20180927153540_22258.png","image3":""}
     */

    public PictureBean picture;

    public static class PictureBean {
        /**
         * image1 : http://zhmg.com/public/uploads/condition/2/20180925165917_41342.png
         * image2 : http://zhmg.com/public/uploads/condition/8/20180927153540_22258.png
         * image3 :
         */

        public String image1;
        public String image2;
        public String image3;
    }
}
