package com.wangc.knowledgepoints.msyd.perperiodfeechargefix.dto;

/**
 * @author
 * @Description: 还款计划实体类
 * @date 2020/12/23 9:56
 */
public class Shd {

    private String loanNo;

    private int psPerdNo;

    private String psDueDt;

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public int getPsPerdNo() {
        return psPerdNo;
    }

    public void setPsPerdNo(int psPerdNo) {
        this.psPerdNo = psPerdNo;
    }

    public String getPsDueDt() {
        return psDueDt;
    }

    public void setPsDueDt(String psDueDt) {
        this.psDueDt = psDueDt;
    }

    @Override
    public String toString() {
        return "Shd{" +
                "loanNo='" + loanNo + '\'' +
                ", psPerdNo='" + psPerdNo + '\'' +
                ", psDueDt='" + psDueDt + '\'' +
                '}';
    }
}
