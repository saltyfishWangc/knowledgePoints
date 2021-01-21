package com.wangc.knowledgepoints.numberic.integer;

/**
 * @author
 * @Description:
 * @date 2020/9/29 17:35
 */
public class IntegerCacheTest {

    public static void main(String... args) {
        if (1000 == 1000) {
            System.out.println("1000等于1000");
        } else {
            System.out.println("1000不等于1000?");
        }

        if (100 == 100) {
            System.out.println("100等于100");
        } else {
            System.out.println("100不等于100?");
        }

        Integer a = 1000, b = 1000;
        if (a == b) {
            System.out.println("1000等于1000");
        } else {
            System.out.println("1000不等于1000?");
        }

        Integer c = 100, d = 100;
        if (c == d) {
            System.out.println("1000等于1000");
        } else {
            System.out.println("1000不等于1000?");
        }
        System.out.println("Integer有一个小数缓冲区，范围是-128~127，在这之间的都是同一个引用");
        // 详细说明引自 https://blog.csdn.net/a_little_e/article/details/49991971
    }
}
