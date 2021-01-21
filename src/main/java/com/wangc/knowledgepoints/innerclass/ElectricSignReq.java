package com.wangc.knowledgepoints.innerclass;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 电子签约请求实体类
 * @date 2020/6/6 11:35
 */
public class ElectricSignReq implements Serializable {

    private static final long serialVersionUID = 7063863452806024843L;

    /**
     * 证件类型
     */
    private String id_typ;

    /**
     * 证件号码
     */
    private String id_no;

    /**
     * 客户姓名
     */
    private String cust_name;

    /**
     * 银行卡号
     */
    private String card_no;

    /**
     * 银行预留手机号
     */
    private String mobile_no;

    /**
     * 协议授权
     */
    private List<CustAuthorize> list_cust_authorize;

    class CustAuthorize implements Serializable {

        private static final long serialVersionUID = 5737337121427208126L;

        /**
         * 授权协议编号
         */
        private String authorize_no;

        /**
         * 授权协议类型
         */
        private String authorize_type;

        /**
         * 授权协议时间
         */
        private String authorize_time;

        /**
         * 授权签约方式
         */
        private String authorize_reserve1;

        /**
         * 授权预留字段2
         */
        private String authorize_reserve2;

        /**
         * 协议字段取值集合
         */
        private String authorize_reserve3;

        public String getAuthorize_no() {
            return authorize_no;
        }

        public void setAuthorize_no(String authorize_no) {
            this.authorize_no = authorize_no;
        }

        public String getAuthorize_type() {
            return authorize_type;
        }

        public void setAuthorize_type(String authorize_type) {
            this.authorize_type = authorize_type;
        }

        public String getAuthorize_time() {
            return authorize_time;
        }

        public void setAuthorize_time(String authorize_time) {
            this.authorize_time = authorize_time;
        }

        public String getAuthorize_reserve1() {
            return authorize_reserve1;
        }

        public void setAuthorize_reserve1(String authorize_reserve1) {
            this.authorize_reserve1 = authorize_reserve1;
        }

        public String getAuthorize_reserve2() {
            return authorize_reserve2;
        }

        public void setAuthorize_reserve2(String authorize_reserve2) {
            this.authorize_reserve2 = authorize_reserve2;
        }

        public String getAuthorize_reserve3() {
            return authorize_reserve3;
        }

        public void setAuthorize_reserve3(String authorize_reserve3) {
            this.authorize_reserve3 = authorize_reserve3;
        }
    }

    public String getId_typ() {
        return id_typ;
    }

    public void setId_typ(String id_typ) {
        this.id_typ = id_typ;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public List<CustAuthorize> getList_cust_authorize() {
        return list_cust_authorize;
    }

    public void setList_cust_authorize(List<CustAuthorize> list_cust_authorize) {
        this.list_cust_authorize = list_cust_authorize;
    }
}
