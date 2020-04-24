package com.wangc.knowledgepoints.copy.deepcopy;

/**
 * @Description: 实现Cloneable接口，表示可以进行clone
 * @date 2020/4/24 16:59
 */
public class Address implements Cloneable{

    private String provinces;
    private String city;

    public void setAddress(String provinces, String city) {
        this.provinces = provinces;
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address [province=" + provinces + ", city=" + city + "]";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
