package com.wangc.knowledgepoints.util;

import sun.misc.BASE64Encoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author XUZHIHONG
 * 2018/04/04
 **/
public class RsaKeyGenerate {


    private static final String KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;


    //编码返回字符串
    private static byte[] encryptBASE64(byte[] key) {
        return Base64.getEncoder().encode(key);
    }

    //map对象中存放公私钥
    private static List<byte[]> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        List<byte[]> keys = new ArrayList<byte[]>(2);
        keys.add(encryptBASE64(publicKey.getEncoded()));
        keys.add(encryptBASE64(privateKey.getEncoded()));

        return keys;
    }

    public static void main(String[] args) {
        try {
            List<byte[]> keys = initKey();
            System.out.println("public key: \n" + new String(keys.get(0)));//公钥
            System.out.println("private key: \n" + new String(keys.get(1)));//私钥
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
