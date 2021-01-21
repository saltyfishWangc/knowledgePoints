package com.wangc.knowledgepoints.grammar;

import java.math.BigDecimal;

/**
 * @Description: 基本数据类型运算转换
 * @date 2020/5/20 20:16
 */
public class GrammarTest {
    public static void main1(String... args) {
        /**
         * 进行四则运算时
         *  a.所有的byte型、short型和char的值将被提升到int型
         *  b.如果一个操作数是long型，计算结果就是long型
         *  c.如果一个操作数是float型，计算结果就是float型
         *  d.如果一个操作数是double型，计算结果就是double型
         *  e.如果一个操作数是String型，计算结果就是String型
         */
        byte b1 = 10;
        byte b2 = 20;
//        byte b3 = b1 + b2; // 报错,b1+b2是int类型,java的整型默认是int,b3是byte类型，b1+b2和b3的类型不同
        byte b3 = (byte) (b1 + b2); // 正确,int强转byte

        short s1 = 10;
//        s1 = s1 + 10; // 报错，s1+10是int类型

        short s2 = 10;
        s2 += 12; // 正确，等同于short s2 = (short)(s2 + (short)12);

        BigDecimal big1 = new BigDecimal(10);
        BigDecimal big2 = new BigDecimal(0);
        big1.divide(big2);
    }

    public static void main2(String... args) {
        int i = 0;
        i++;
        System.out.println(i);
    }
}
