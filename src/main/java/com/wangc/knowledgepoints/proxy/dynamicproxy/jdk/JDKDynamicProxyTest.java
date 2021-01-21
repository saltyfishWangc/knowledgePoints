package com.wangc.knowledgepoints.proxy.dynamicproxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description: JDK动态代理测试类
 * @date 2020/4/16 15:46
 */
public class JDKDynamicProxyTest {

    public static void main(String... args) {
        // 开启保存生成的代理类class文件系统配置，默认是关闭
        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // situation 1
        System.out.println("==============situation 1==============");
        AdminService target = new AdminServiceImpl();
        System.out.println("代理的目标对象：" + target.getClass());
        AdminServiceInvocation invocationHandler = new AdminServiceInvocation(target);
        AdminServiceJDKProxyBuilder proxyBuilder = new AdminServiceJDKProxyBuilder(target, invocationHandler);
        AdminService proxy = (AdminService) proxyBuilder.buildProxy();
        System.out.println("代理对象：" + proxy.getClass());

        Object result = proxy.find();
        System.out.println("find返回对象：" + result.getClass());
        System.out.println("------------------------------");
        proxy.update();

        // situation 2
        System.out.println("==================situation 2===================");
        AdminService target2 = new AdminServiceImpl();
        AdminServiceInvocation invocationHandler2 = new AdminServiceInvocation(target2);
        AdminService proxy2 = (AdminService) Proxy.newProxyInstance(target2.getClass().getClassLoader(), target2.getClass().getInterfaces(), invocationHandler2);
        System.out.println("代理对象：" + proxy2.getClass());

        Object result2 = proxy2.find();
        System.out.println("find返回对象：" + result2.getClass());
        System.out.println("------------------------");
        proxy2.update();

        // situation 3
        System.out.println("====================situation 3=====================");
        System.out.println("采用匿名内部类创建代理对象");
        final AdminService target3 = new AdminServiceImpl();
        AdminService proxy3 = (AdminService) Proxy.newProxyInstance(target3.getClass().getClassLoader(), target3.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("判断用户是否有权限进行操作");
                Object result = method.invoke(target3, args); // 内部类可以直接使用外部类的所有成员变量
                System.out.println("记录用户执行操作的用户信息、更改内容和时间等");
                return result;
            }
        });
        System.out.println("代理对象：" + proxy3.getClass());

        Object result3 = proxy3.find();
        System.out.println("find返回对象：" + result3.getClass());
        System.out.println("---------------------------");
        proxy3.update();
    }
}
