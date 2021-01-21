package com.wangc.knowledgepoints.security;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @Description: BASE64编码解码工具包
 * @author wangchao
 * @date 2020/06/08 11:00
 */
public class Base64Utils {
 
 
    /** *//**
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     * 
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder(); 
 
        
            return decoder.decodeBuffer(base64); 
    }
 
    /** *//**
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     * 
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
         return  encoder.encode(bytes);
    }
 
 
}
