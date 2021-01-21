package com.wangc.knowledgepoints.security;

/**
 * @author
 * @Description:
 * @date 2020/6/11 18:38
 */
public class RsaTest {

    public static void main(String... args) throws Exception {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBFQuDyJApWoKfC3Ixa1rJgye5wFGXW0fjy+YYyse1Tvbz+iYi5wMGfjshgEGYzY6zJoxpTg2XvnFhCHIJ+/LS9F8Q/fO+mt+WTHPIZiAr1AVKDfYkPMfLepRBOAJb6CB6csDcbVCHEumOvzLSMBrsUjpqxLKEGX+T3pk+PCzAEwIDAQAB";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIEVC4PIkClagp8LcjFrWsmDJ7nAUZdbR+PL5hjKx7VO9vP6JiLnAwZ+OyGAQZjNjrMmjGlODZe+cWEIcgn78tL0XxD9876a35ZMc8hmICvUBUoN9iQ8x8t6lEE4AlvoIHpywNxtUIcS6Y6/MtIwGuxSOmrEsoQZf5PemT48LMATAgMBAAECgYAEMXvTRyOvxDn0Erod8t/bJgCXXzEHWrbGJqyN56B/OlZ5bInqnliR0G35U6JN5XeaSElNbnSwlkY2ocCapG2p8rSto6/UCXzwUC/ibkHT9wMS+N/GQuEFy4v4eNdDfp0mVDEgUt42MBiHh7cm2vzRiXPx55jG9xQ+BrREuK9nqQJBANrO23AekfiE4Q4undkK7tG9LLSUTpiEpVMJ9lN5ROUYhxGqbgf21Va9snB5OS9dBM2G2cA0CX3Sg8r10WPLaX8CQQCXBeMFx719kAAFIWEf/YYEM2894G95yC2pT9oBi9CvBenWwIPM0PhIDKRdUBMnf+pfIJXDHpOi3aD6Oz4TZ6ttAkA976Xv48df+IUYFZv6zvxxBEJ+Tyi8RXfivIQsdrxIuRuqpXqF6JnU9tdmvPmx7Xupjc1feZtiMU+7CMAfpnmTAkBEpwhsuEALtEpOCr58LGOfxUHsfvouAjG/pe0Uhtp2tdl3JkzD7rrvBdAyGxSaACwq0kVCTcQPZXCoUAYIDDiBAkEAxBgMwAxBktmAz0iXLeOLEean3EZZOIxpfXxIwzsj35d5oAloUfvLYDcBmBDlj2+14VwBjXhI/4T4a3lD7Q3cEg==";
        String reqInfo = "{\"request\":{\"body\":{\"card_no\":\"6217211107001880725\",\"check_type\":\"007\",\"cust_name\":\"王小二\",\"id_no\":\"53010219870331315X\",\"id_typ\":\"20\",\"mobile_no\":\"17689556652\"},\"head\":{\"channel_no\":\"53\",\"serno\":\"535312029135\",\"sys_flag\":\"53\",\"trade_code\":\"ZYXF001\",\"trade_date\":\"2020-06-11\",\"trade_time\":\"11:33:59\",\"trade_type\":\"\"}}}";
        //1.产生随机AES私钥
        String aesBase64Key = AESUtil.initKey(128, null);
        System.out.println("key：" + aesBase64Key);
        String info = AESUtil.encrypt(reqInfo.getBytes("UTF-8"), aesBase64Key, "AES/ECB/PKCS5Padding");
        //3.RSA公钥加密
        String key = Base64Utils.encode(RSAUtil.encryptByPublicKey(Base64Utils.decode(aesBase64Key), publicKey, EncryptionModeEnum.RSA1024));

        // Rsa解密key
        String decryptKey = Base64Utils.encode(RSAUtil.decryptByPrivateKey(Base64Utils.decode(key), privateKey, EncryptionModeEnum.RSA1024));
        System.out.println("解密后的key：" + decryptKey);
        // Ase解密
        String decryptInfo = new String(AESUtil.decrypt(info.getBytes("UTF-8"), decryptKey, "AES/ECB/PKCS5Padding"), "UTF-8");
        System.out.println("解密后:" + decryptInfo);

    }
}
