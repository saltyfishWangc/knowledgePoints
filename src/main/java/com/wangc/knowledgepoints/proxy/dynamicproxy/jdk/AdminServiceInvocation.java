package com.wangc.knowledgepoints.proxy.dynamicproxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description: 用户代理类的调用处理器
 * @date 2020/4/16 15:35
 */
public class AdminServiceInvocation implements InvocationHandler{

    private Object target;

    public AdminServiceInvocation(Object target) {
        this.target = target;
    }

    // 每次代理类在调用方法时都会进入到这个方法，代理类真正调用目标对象的方法是在这里，同时在调用前后进行了加强
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("判断用户是否有权限进行操作");
        Object result = method.invoke(target);
        System.out.println("记录用户执行操作的用户信息、更改内容和时间等");
        return result;
    }
}
