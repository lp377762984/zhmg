package com.wta.NewCloudApp.mvp.model.entity;

public class LoginEntity {

    /**
     * update_token : {"sessionId":"45cd719c688e612742698271d13fb72a","accessToken":"0daf66180fa90bd0697822119970bec8"}
     * user_info : {"avatar":"http://zhmg.com/public/static/base/images/default.jpg","white_score":0,"nickname":"15510253064","card_status":0,"number":"1014953064","mobile":"15510253064","is_weixin":0,"wx_name":"已绑定","group_name":"会员"}
     */

    public UpdateTokenBean update_token;
    public User user_info;

    public static class UpdateTokenBean {
        /**
         * sessionId : 45cd719c688e612742698271d13fb72a
         * accessToken : 0daf66180fa90bd0697822119970bec8
         */

        public String sessionId;
        public String accessToken;
    }

}
