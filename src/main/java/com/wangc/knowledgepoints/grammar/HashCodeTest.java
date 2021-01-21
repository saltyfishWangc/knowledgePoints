package com.wangc.knowledgepoints.grammar;

/**
 * @Description:
 * @date 2020/5/22 16:26
 */
public class HashCodeTest {

    public static void main(String... args) {
        String str1 = "通话";
        String str2 = "重地";
        System.out.println(String.format("str1：%d | str2：%d",  str1.hashCode(),str2.hashCode()));
        System.out.println(str1.equals(str2));
    }
}
