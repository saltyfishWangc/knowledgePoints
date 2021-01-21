/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.wangc.knowledgepoints.security;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * RSA加密工具类
 * @author wuming
 *
 */
public class RSAUtil {

    /**
     * 加密算法
     */
    public static final String KEY_ALGORTHM           = "RSA";

    /**具体加密算法，包括padding的方式*/
    public static final String SPECIFIC_KEY_ALGORITHM = "RSA/ECB/PKCS1Padding";
    
    
    private static final String ENCODE = "encoding";
    
    
    /**
     * 用RSA算法进行加密
     * 
     * @param paramsString 要加密的字符串
     * @param charset 字符集
     * @param publicKey 加密使用的公钥
     * @return  加密结果
     * @throws Exception 异常
     */
    public static String encrypt(String paramsString, String charset, String publicKey)
                                                                                       throws Exception {
        byte[] encryptedResult = RSAUtil.encryptByPublicKey(paramsString.getBytes(charset),
            publicKey, null);

        return Base64Utils.encode(encryptedResult);
    }

    /**
     * 用RSA算法进行加密
     * 
     * @param paramsString 要加密的字符串
     * @param charset 字符集
     * @param publicKey 加密使用的公钥
     * @param encryptionType 加密算法类型
     * @return  加密结果
     * @throws Exception 异常
     */
    public static String encrypt(String paramsString, String charset, String publicKey,
                                 EncryptionModeEnum encryptionType) throws Exception {
        byte[] encryptedResult = RSAUtil.encryptByPublicKey(paramsString.getBytes(charset),
            publicKey, encryptionType);

        return Base64Utils.encode(encryptedResult);
    }

   

   

   

  
    /**
     * 用私钥解密 
     * @param data   加密数据
     * @param key    密钥
     * @param encryptionType 加密类型
     * @return 解密后的byte数组
     * @throws Exception 异常
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key,
                                             EncryptionModeEnum encryptionType) throws Exception {
        byte[] decryptedData = null;

        //对私钥解密
        byte[] keyBytes = Base64Utils.decode(key);

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //对数据解密
        Cipher cipher = Cipher.getInstance(SPECIFIC_KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // 解密时超过maxDecryptBlockSize字节就报错。为此采用分段解密的办法来解密
        int maxDecryptBlockSize;
        if (encryptionType != null) {
            maxDecryptBlockSize = getMaxDecryptBlockSizeByEncryptionType(encryptionType);
        } else {
            maxDecryptBlockSize = getMaxDecryptBlockSize(keyFactory, privateKey);
        }

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            int dataLength = data.length;
            for (int i = 0; i < dataLength; i += maxDecryptBlockSize) {
                int decryptLength = dataLength - i < maxDecryptBlockSize ? dataLength - i
                    : maxDecryptBlockSize;
                byte[] doFinal = cipher.doFinal(data, i, decryptLength);
                bout.write(doFinal);
            }
            decryptedData = bout.toByteArray();
        } finally {
            if (bout != null) {
                bout.close();
            }
        }

        return decryptedData;
    }

    
    /** *//**
     * <p>
     * 公钥解密
     * </p>
     * 
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String publicKey,EncryptionModeEnum encryptionType)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(SPECIFIC_KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        
        
        // 解密时超过maxDecryptBlockSize字节就报错。为此采用分段解密的办法来解密
        int maxDecryptBlockSize;
        if (encryptionType != null) {
            maxDecryptBlockSize = getMaxDecryptBlockSizeByEncryptionType(encryptionType);
        } else {
            maxDecryptBlockSize = getMaxDecryptBlockSize(keyFactory, publicK);
        }
        
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] decryptedData = null;
        try {
            int dataLength = data.length;
            for (int i = 0; i < dataLength; i += maxDecryptBlockSize) {
                int decryptLength = dataLength - i < maxDecryptBlockSize ? dataLength - i
                    : maxDecryptBlockSize;
                byte[] doFinal = cipher.doFinal(data, i, decryptLength);
                out.write(doFinal);
            }
            decryptedData = out.toByteArray();
        } finally {
                if (out != null) {
                	out.close();
                }
          }
        return decryptedData;
    }
    
    /**
     * 用公钥加密
     * @param data   加密数据
     * @param key    公钥
     * @param encryptionType 加密类型
     * @return 加密后的字节数组
     * @throws Exception 异常
     */
    public static byte[] encryptByPublicKey(byte[] data, String key,
                                            EncryptionModeEnum encryptionType) throws Exception {
        byte[] encryptedData = null;
        "aag".replaceAll("\r\n", "");
        //对公钥解密
        byte[] keyBytes = Base64Utils.decode(key);
        //取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //对数据解密
        Cipher cipher = Cipher.getInstance(SPECIFIC_KEY_ALGORITHM);
        System.err.println(cipher.getClass().getName());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // 加密时超过maxEncryptBlockSize字节就报错。为此采用分段加密的办法来加密
        int maxEncryptBlockSize;
        if (encryptionType != null) {
            maxEncryptBlockSize = getMaxEncryptBlockSizeByEncryptionType(encryptionType);
        } else {
            maxEncryptBlockSize = getMaxEncryptBlockSize(keyFactory, publicKey);
        }

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            int dataLength = data.length;
            for (int i = 0; i < data.length; i += maxEncryptBlockSize) {
                int encryptLength = dataLength - i < maxEncryptBlockSize ? dataLength - i
                    : maxEncryptBlockSize;
                byte[] doFinal = cipher.doFinal(data, i, encryptLength);
                bout.write(doFinal);
            }
            encryptedData = bout.toByteArray();
        } finally {
            if (bout != null) {
                bout.close();
            }
        }
        return encryptedData;
    }


    /**
     * <p>
     * 私钥加密
     * </p>
     * 
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(byte[] data, String privateKey,EncryptionModeEnum encryptionType)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);

        
        int maxEncryptBlockSize;
        if (encryptionType != null) {
            maxEncryptBlockSize = getMaxEncryptBlockSizeByEncryptionType(encryptionType);
        } else {
            maxEncryptBlockSize = getMaxEncryptBlockSize(keyFactory, privateK);
        }
        
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] encryptedData = null;
        try {
            int dataLength = data.length;
            for (int i = 0; i < data.length; i += maxEncryptBlockSize) {
                int encryptLength = dataLength - i < maxEncryptBlockSize ? dataLength - i
                    : maxEncryptBlockSize;
                byte[] doFinal = cipher.doFinal(data, i, encryptLength);
                out.write(doFinal);
            }
            encryptedData = out.toByteArray();
        } finally {
            if (out != null) {
            	out.close();
            }
        }
        return Base64Utils.encode(encryptedData);
    }

   

  




    /**
     * 获取每次加密的最大长度
     * 
     * @param keyFactory KeyFactory
     * @param key 公钥
     * @return 单词加密最大长度
     * @throws Exception 异常
     */
    private static int getMaxEncryptBlockSize(KeyFactory keyFactory, Key key) throws Exception {
        //默认先设置成RSA1024的最大加密长度
        int maxLength = 117;
        try {
            RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(key, RSAPublicKeySpec.class);
            int keyLength = publicKeySpec.getModulus().bitLength();
            maxLength = keyLength / 8 - 11;
        } catch (Exception e) {

        }
        return maxLength;
    }

    /**
     * 根据加密算法类型获取单次最大加密长度
     * 
     * @param encryptionType
     * @return
     */
    private static int getMaxEncryptBlockSizeByEncryptionType(EncryptionModeEnum encryptionType) {
        if (encryptionType == EncryptionModeEnum.RSA1024) {
            return 1024 / 8 - 11;
        } else if (encryptionType == EncryptionModeEnum.RSA2048) {
            return 2048 / 8 - 11;
        }

        return 1024 / 8 - 11;
    }

    /***
     * 获取每次解密最大长度
     * 
     * @param keyFactory KeyFactory
     * @param key 私钥
     * @return 单次解密最大长度
     * @throws Exception 异常
     */
    private static int getMaxDecryptBlockSize(KeyFactory keyFactory, Key key) throws Exception {
        //默认先设置成RSA1024的最大解密长度
        int maxLength = 128;
        try {
            RSAPrivateKeySpec publicKeySpec = keyFactory.getKeySpec(key, RSAPrivateKeySpec.class);
            int keyLength = publicKeySpec.getModulus().bitLength();
            maxLength = keyLength / 8;
        } catch (Exception e) {

        }
        return maxLength;
    }

    /**
     * 
     * 
     * @param encryptionType
     * @return
     */
    private static int getMaxDecryptBlockSizeByEncryptionType(EncryptionModeEnum encryptionType) {
    	
    	
        if (encryptionType == EncryptionModeEnum.RSA1024) {
            return 1024 / 8;
        } else if (encryptionType == EncryptionModeEnum.RSA2048) {
            return 2048 / 8;
        }

        return 1024 / 8;
    }
    
    
   
   
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
    
    /** 
     * 使用模和指数生成RSA私钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *  
     * @param modulus 
     *            模 
     * @param exponent 
     *            指数 
     * @return 
     */  
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
 
}
