package com.wangc.knowledgepoints.io.stream;

/**
 * @Description: 字符编码
 * @date 2020/5/19 15:53
 */
public class CharsetTest {
    public static void main(String... args) throws Exception {
        /** GBK和UTF8的区别
         *  GBK不对是英文还是中文字符均使用双字节表示，只不过区分中文，将其最高位都定成1，所以对应英文比较多的为了节省贷款可以使用GBK字符编码
         *  UTF8是变长的，对英文用一个字节（8位）表示，中文使用三个字节（24位）表示
         *
         *  扩展：
         *      ISO8859-1字符编码所占字节长度为1
         *      unicode字符编码所占字节长度为4
         */
        String sourceStr = "你好吗我不好希望会好";
        byte[] gbkBytes = sourceStr.getBytes("GBK");
        byte[] utf8Bytes = sourceStr.getBytes("UTF8");
        System.out.println("【你好吗我不好希望会好】转换成GBK字节数组后总共字节数为：" + gbkBytes.length);
        System.out.println("【你好吗我不好希望会好】转换成UTF8字节数组后总共字节数为：" + utf8Bytes.length);

    }
}
