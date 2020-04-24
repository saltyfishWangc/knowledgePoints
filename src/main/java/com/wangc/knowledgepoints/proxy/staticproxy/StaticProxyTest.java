package com.wangc.knowledgepoints.proxy.staticproxy;

/**
 * @Description: 静态代理测试类
 * @date 2020/4/16 15:27
 */
public class StaticProxyTest {

    public static void main(String... args) {
        AdminService target = new AdminServiceImpl();
        AdminService proxy = new AdminServiceProxy(target);
        proxy.update();
        System.out.println("==========================");
        proxy.find();
    }
}
