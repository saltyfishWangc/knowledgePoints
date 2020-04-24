package com.wangc.knowledgepoints.proxy.dynamicproxy.jdk;

/**
 * @Description: 目标类
 * @date 2020/4/16 15:33
 */
public class AdminServiceImpl implements AdminService{

    public void update() {
        System.out.println("修改管理系统数据");
    }

    public Object find() {
        System.out.println("查看管理系统数据");
        return new Object();
    }
}
