package com.wangc.knowledgepoints.yidai_rateutil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @Description:
 * @date 2020/7/29 14:37
 */
public class RateUtilsTest {

    public static void main(String... args) {


//        YKD2020122918044513111 还款方式SYS002,总费用85.80,总期限6,总利息21.61,本金1000.00 昨天成功的 0.3584
//        YKD2020123011521692513 还款方式SYS002,总费用85.80,总期限6,总利息21.57,本金1000.00 今天失败的 0.3613
//        YKD2020122710155730531 还款方式SYS002,总费用85.80,总期限6,总利息21.64,本金1000.00 27号放款成功 0.3555

//        BigDecimal feeAmt = new BigDecimal(85.80);
//        BigDecimal interestsAmt = new BigDecimal(21.57);
//        BigDecimal loanPeriods = new BigDecimal(6);
//        BigDecimal principal = new BigDecimal(1000.00);

        BigDecimal feeAmt = new BigDecimal(125.84);
        BigDecimal interestsAmt = new BigDecimal(31.70);
        BigDecimal loanPeriods = new BigDecimal(3);
        BigDecimal principal = new BigDecimal(2600.00);

        String calTotalCalDay = "360";
        String calDay = "2021-01-19";

        BigDecimal res = RateUtils.getDEBXRateNew(principal, calDay, loanPeriods.intValue(), feeAmt.add(interestsAmt), calTotalCalDay);
        System.out.println(res.toPlainString());
    }
}
