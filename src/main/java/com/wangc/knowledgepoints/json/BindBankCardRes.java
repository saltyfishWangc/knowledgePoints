package com.wangc.knowledgepoints.json;

/**
 * @Description: 绑定银行卡响应实体类
 * @date 2020/6/5 11:32
 */
public class BindBankCardRes extends ResBody {

    private static final long serialVersionUID = 2245974126085891226L;

    /**
     * 鉴权结果
     *  取值：Y-通过，N-未通过
     */
    private String check_flag;

    /**
     * 交易结果描述
     */
    private String check_msg;

    /**
     * 失败原因码
     */
    private String refuse_no;

    /**
     * 拒绝原因描述
     */
    private String refuse_cause;

    /**
     * 是否支持放款
     */
    private String pay_card_flag;

    /**
     * 是否支持还款
     */
    private String reimburse_card_flag;

    /**
     * 发送方标志
     *  取值：O-我方，F-其他方
     */
    private String sender_flag;

    /**
     * 预签约协议号
     */
    private String unique_code;

    public String getCheck_flag() {
        return check_flag;
    }

    public void setCheck_flag(String check_flag) {
        this.check_flag = check_flag;
    }

    public String getCheck_msg() {
        return check_msg;
    }

    public void setCheck_msg(String check_msg) {
        this.check_msg = check_msg;
    }

    public String getRefuse_no() {
        return refuse_no;
    }

    public void setRefuse_no(String refuse_no) {
        this.refuse_no = refuse_no;
    }

    public String getRefuse_cause() {
        return refuse_cause;
    }

    public void setRefuse_cause(String refuse_cause) {
        this.refuse_cause = refuse_cause;
    }

    public String getPay_card_flag() {
        return pay_card_flag;
    }

    public void setPay_card_flag(String pay_card_flag) {
        this.pay_card_flag = pay_card_flag;
    }

    public String getReimburse_card_flag() {
        return reimburse_card_flag;
    }

    public void setReimburse_card_flag(String reimburse_card_flag) {
        this.reimburse_card_flag = reimburse_card_flag;
    }

    public String getSender_flag() {
        return sender_flag;
    }

    public void setSender_flag(String sender_flag) {
        this.sender_flag = sender_flag;
    }

    public String getUnique_code() {
        return unique_code;
    }

    public void setUnique_code(String unique_code) {
        this.unique_code = unique_code;
    }
}
