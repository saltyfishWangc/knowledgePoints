package com.wangc.knowledgepoints.proxy.dynamicproxy.cglib;

/**
 * @Description: 目标类 注意：这个不同于jdk代理的接口，这个是类
 * @date 2020/4/16 16:28
 */
public class AdminService {

    public void update() {
        System.out.println("修改管理系统数据");
    }

    public Object find() {
        System.out.println("查看管理系统数据");
        return new Object();
    }
}
