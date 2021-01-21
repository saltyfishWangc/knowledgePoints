package com.wangc.knowledgepoints.security;

/**
 * @Description: 加密算法类型
 * @author wangchao
 * @date 2020/06/08
 */
public enum EncryptionModeEnum {
    /**RSA1024加密算法*/
    RSA1024("rsa1024", "RSA1024加密算法"),

    /**RSA2048加密算法*/
    RSA2048("rsa2048", "RSA2048加密算法"),

    /**AES对称加密算法*/
    AES("aes", "AES对称加密算法");

    /**code*/
    private String code;

    /**描述*/
    private String desc;

    /**
     * 构造函数
     * @param code
     * @param desc
     */
    private EncryptionModeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * Getter method for property <tt>code</tt>.
     * 
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     * 
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>desc</tt>.
     * 
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Setter method for property <tt>desc</tt>.
     * 
     * @param desc value to be assigned to property desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public static EncryptionModeEnum getEncryptionModeEnum(String code){
    	
    	for  (EncryptionModeEnum encryptionMode : EncryptionModeEnum.values()) {  
            if  (encryptionMode.getCode().equals(code)) {  
                return  encryptionMode;  
            }  
        }  
        throw new IllegalArgumentException("无效的EncryptionModeEnum");
    }
}