package com.wangc.knowledgepoints.bigdecimal;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @Description:
 * @date 2020/5/12 16:55
 */
public class BigDecimalTest {
    public static void main(String... args) {

//        BigDecimal feeRateRandom = getRandomByKey(12);
//
//        System.out.println("算出来的随机小数：【" + feeRateRandom.toPlainString()+ "】");
//
//        System.out.println(new BigDecimal(6%4).floatValue());
        BigDecimal czIirless24MinRate = new BigDecimal("0.99");
        BigDecimal iirLessThenPercent24Count = new BigDecimal(4);
        BigDecimal total = new BigDecimal(7);
        BigDecimal rs = iirLessThenPercent24Count.divide(total, 6, 6);
        System.out.println("rs:" + rs.toPlainString());
        if (rs.compareTo(czIirless24MinRate) < 0) {
            System.out.println("小于进来了");
        } else {
            System.out.println("没进来");
        }

    }

    private static BigDecimal getRandomByKey(int key) {
        Random rm = new Random();
        BigDecimal dividend = new BigDecimal(10000);
        int random = 0;
        switch (key) {
            case 1:
                random = rm.nextInt(125-95) + 95;
                break;
            case 3:
                random = rm.nextInt(85-65) + 65;
                break;
            case 6:
                random = rm.nextInt(75-58) + 58;
                break;
            case 12:
                random = rm.nextInt(72-55) + 55;
                break;
            default:
                random = rm.nextInt(72-55) + 55;
                break;
        }
        System.out.println("借款期限：【" + key + "】获取的费率为：【" + random + "】");
        return new BigDecimal(random).divide(dividend);
    }
}
