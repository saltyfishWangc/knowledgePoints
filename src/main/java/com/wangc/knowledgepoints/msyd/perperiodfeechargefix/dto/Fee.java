package com.wangc.knowledgepoints.msyd.perperiodfeechargefix.dto;

/**
 * @author
 * @Description: 费用实体类
 * @date 2020/12/23 9:55
 */
public class Fee {

    private String loanNo;

    private String txLogSeq;

    private int seqNo;

    private String feeCde;

    private String holdSetlDt;

    /**
     * 变更之前的日期
     */
    private String valBeforeChg;

    /**
     * 对应的还款计划期数
     */
    private int psPerdNo;

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public String getTxLogSeq() {
        return txLogSeq;
    }

    public void setTxLogSeq(String txLogSeq) {
        this.txLogSeq = txLogSeq;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public String getFeeCde() {
        return feeCde;
    }

    public void setFeeCde(String feeCde) {
        this.feeCde = feeCde;
    }

    public String getHoldSetlDt() {
        return holdSetlDt;
    }

    public void setHoldSetlDt(String holdSetlDt) {
        this.holdSetlDt = holdSetlDt;
    }

    public String getValBeforeChg() {
        return valBeforeChg;
    }

    public void setValBeforeChg(String valBeforeChg) {
        this.valBeforeChg = valBeforeChg;
    }

    public int getPsPerdNo() {
        return psPerdNo;
    }

    public void setPsPerdNo(int psPerdNo) {
        this.psPerdNo = psPerdNo;
    }

    @Override
    public String toString() {
        return "Fee{" +
                "loanNo='" + loanNo + '\'' +
                ", txLogSeq='" + txLogSeq + '\'' +
                ", seqNo=" + seqNo +
                ", feeCde='" + feeCde + '\'' +
                ", holdSetlDt='" + holdSetlDt + '\'' +
                ", valBeforeChg='" + valBeforeChg + '\'' +
                ", psPerdNo=" + psPerdNo +
                '}';
    }
}
