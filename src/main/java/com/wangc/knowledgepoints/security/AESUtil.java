package com.wangc.knowledgepoints.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @Description: AES加解密工具类
 * @author wangchao
 * @date 2020/6/8 11:49
 */
public class AESUtil {

    /**AES算法*/
    private static final String AES_ALGORITHM = "AES";


    /**
     * 利用AES算法对数据进行加密
     * 
     * @param data 要加密的数据
     * @param aesBase64Key base64编码的AES Key
     * @return 加密后的数据(Base64编码)
     */
    public static String encrypt(byte[] data, String aesBase64Key,String algorithm) throws Exception {
        // AES加密  
        Cipher cipher = initCipher(aesBase64Key, algorithm,Cipher.ENCRYPT_MODE);

        byte[] bytOut = cipher.doFinal(data);
        return Base64Utils.encode(bytOut);
    }

    

    /**
     * 利用AES算法对数据进行解密
     * 
     * @param encryptedData AES加密的数据
     * @param aesBase64Key  base64编码的AES Key
     * @return 解密后的数据
     * @throws Exception 解密异常
     */
    public static byte[] decrypt(byte[] encryptedData, String aesBase64Key,String algorithm)
                                                                                           throws Exception {
        // AES加密  
        Cipher cipher = initCipher(aesBase64Key, algorithm,Cipher.DECRYPT_MODE);

        byte[] bytOut = cipher.doFinal(encryptedData);
       
        return bytOut;
    }




  

    /**
     * 
     * 
     * @param aesBase64Key
     * @param mode 指定是加密还是解密模式
     * @return
     * @throws Exception
     */
    public static Cipher initCipher(String aesBase64Key,String algorithm, int mode) throws Exception {
        SecretKeySpec skeySpec = getSecretKeySpec(aesBase64Key);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(mode, skeySpec);
        return cipher;
    }

    /**
     * 根据base64编码的key获取SecretKeySpec对象
     * 
     * @param aesBase64Key base64编码的key
     * @return SecretKeySpec对象
     * @throws Exception 获取SecretKeySpec对象异常
     */
    public static SecretKeySpec getSecretKeySpec(String aesBase64Key) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(Base64Utils.decode(aesBase64Key),
            AES_ALGORITHM);
        return skeySpec;
    }

    /**
     * 初始化AES Key
     * 
     * @return base64编码的AES Key
     * @throws Exception 异常
     */
    public static String initKey(int len,String seek) throws Exception {
        //实例化  
        KeyGenerator kgen = KeyGenerator.getInstance(AES_ALGORITHM);
        //设置密钥长度  
        SecureRandom random =null;
        if(seek !=null){
        	 random = new SecureRandom(seek.getBytes());
        }else{
        	 random = new SecureRandom();
        }
       
        kgen.init(len, random);
        //kgen.init(128);
        //生成密钥
        SecretKey skey = kgen.generateKey();

        //返回密钥的二进制编码  
        return Base64Utils.encode(skey.getEncoded());
    }
}
