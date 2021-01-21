package com.wangc.knowledgepoints.io.stream;

/**
 * @Description:
 * @date 2020/5/18 16:55
 */
public class UnicodeTest {
    public static void main(String... args) throws Exception {
        String str = "你好";
        String str2 = new String(str.getBytes("UTF8"), "UTF-8");
        System.out.println(str2);
    }
}
