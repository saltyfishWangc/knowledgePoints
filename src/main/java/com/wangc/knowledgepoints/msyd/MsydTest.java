package com.wangc.knowledgepoints.msyd;

import java.math.BigDecimal;

/**
 * @Description:
 * @date 2020/5/26 15:53
 */
public class MsydTest {

    public static void main(String... args) {
        BigDecimal res = RateUtils.getIntergratedLoanRatePerYear("SYS002", BigDecimal.ZERO, new BigDecimal(12), BigDecimal.ZERO, new BigDecimal(2000), "2020-05-26");
        System.out.println("结果：" + res.toPlainString());
    }
}
