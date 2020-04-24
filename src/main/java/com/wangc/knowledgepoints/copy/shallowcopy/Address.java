package com.wangc.knowledgepoints.copy.shallowcopy;

/**
 * @Description:
 * @date 2020/4/24 16:59
 */
public class Address {

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
}
