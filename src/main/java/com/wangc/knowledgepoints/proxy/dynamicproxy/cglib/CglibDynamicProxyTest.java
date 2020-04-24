package com.wangc.knowledgepoints.proxy.dynamicproxy.cglib;

/**
 * @Description: cglib动态代理测试类
 * @date 2020/4/16 16:44
 */
public class CglibDynamicProxyTest {

    public static void main(String... args) {
        AdminService target = new AdminService();
        AdminServiceInvocation invocationHandler = new AdminServiceInvocation(target);
        AdminService proxy = (AdminService) (new AdminServiceCglibProxyBuilder(target, invocationHandler).getProxyInstance());
        System.out.println("代理对象：" + proxy.getClass());

        Object result = proxy.find();
        System.out.println("find返回对象：" + result.getClass());
        System.out.println("-----------------------");
        proxy.update();

        // 第二种封装：将生成cglib代理类的方法和调用处理器封装在一个类，这样可以公用一个target，少定义类文件
        AdminService target2 = new AdminService();
        AdminServiceCglibProxy proxyFactory = new AdminServiceCglibProxy(target2);
        AdminService proxy2 = (AdminService) proxyFactory.getProxyInstance();
        System.out.println("代理对象：" + proxy2.getClass());

        Object result2 = proxy2.find();
        System.out.println("find返回对象：" + result2.getClass());
        System.out.println("-------------------------");
        proxy2.update();
    }
}
