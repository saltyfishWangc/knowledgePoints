package com.wangc.knowledgepoints.proxy.dynamicproxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Description: AdminService代理类生成器
 * @date 2020/4/16 15:41
 */
public class AdminServiceJDKProxyBuilder {

    // 目标对象
    private Object target;

    // 调用处理器（用户对目标对象方法加强）
    private InvocationHandler invocationHandler;

    public AdminServiceJDKProxyBuilder(Object target, InvocationHandler invocationHandler) {
        this.target = target;
        this.invocationHandler = invocationHandler;
    }

    public Object buildProxy() {
        // JDK动态代理的核心就是用的java.lang.Proxy的newProxyInstance()
        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), invocationHandler);
        return proxy;
    }
}
