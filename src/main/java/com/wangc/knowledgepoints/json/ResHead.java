package com.wangc.knowledgepoints.json;

import java.io.Serializable;

/**
 * @author
 * @Description:
 * @date 2020/6/11 10:03
 */
public class ResHead implements Serializable {

    private static final long serialVersionUID = 186421548353778049L;

    /**
     * 返回标志
     *  取值：AAAAAAA-成功，其他表示交易层面错误
     */
    private String ret_flag;

    /**
     * 返回消息
     */
    private String ret_msg;

    /**
     * 报文流水号
     *  备注：系统标识 + 渠道编号 + yyyyMMdd + 8位流水号
     */
    private String serno;

    /**
     * 错误类型
     */
    private String trade_flag;

    /**
     * 错误描述
     */
    private String trade_msg;

    public String getRet_flag() {
        return ret_flag;
    }

    public void setRet_flag(String ret_flag) {
        this.ret_flag = ret_flag;
    }

    public String getRet_msg() {
        return ret_msg;
    }

    public void setRet_msg(String ret_msg) {
        this.ret_msg = ret_msg;
    }

    public String getSerno() {
        return serno;
    }

    public void setSerno(String serno) {
        this.serno = serno;
    }

    public String getTrade_flag() {
        return trade_flag;
    }

    public void setTrade_flag(String trade_flag) {
        this.trade_flag = trade_flag;
    }

    public String getTrade_msg() {
        return trade_msg;
    }

    public void setTrade_msg(String trade_msg) {
        this.trade_msg = trade_msg;
    }
}

