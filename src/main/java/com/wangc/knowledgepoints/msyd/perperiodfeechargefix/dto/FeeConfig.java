package com.wangc.knowledgepoints.msyd.perperiodfeechargefix.dto;

/**
 * @author
 * @Description: 费用配置实体类
 * @date 2020/12/23 9:54
 */
public class FeeConfig {

    private String feeCde;

    private int feeStrPerdNo;

    private int feeSpan;

    public String getFeeCde() {
        return feeCde;
    }

    public void setFeeCde(String feeCde) {
        this.feeCde = feeCde;
    }

    public int getFeeStrPerdNo() {
        return feeStrPerdNo;
    }

    public void setFeeStrPerdNo(int feeStrPerdNo) {
        this.feeStrPerdNo = feeStrPerdNo;
    }

    public int getFeeSpan() {
        return feeSpan;
    }

    public void setFeeSpan(int feeSpan) {
        this.feeSpan = feeSpan;
    }

    @Override
    public String toString() {
        return "FeeConfig{" +
                "feeCde='" + feeCde + '\'' +
                ", feeStrPerdNo='" + feeStrPerdNo + '\'' +
                ", feeSpan='" + feeSpan + '\'' +
                '}';
    }
}
