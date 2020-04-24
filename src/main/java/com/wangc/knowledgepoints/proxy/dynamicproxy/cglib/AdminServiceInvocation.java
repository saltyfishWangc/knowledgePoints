package com.wangc.knowledgepoints.proxy.dynamicproxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description: cglib动态代理调用处理器
 * @date 2020/4/16 16:40
 */
public class AdminServiceInvocation implements MethodInterceptor{

    private Object target;

    public AdminServiceInvocation(Object target) {
        this.target = target;
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("判断用户是否有权限进行操作");
        Object result = method.invoke(target, objects);
        System.out.println("记录用户执行操作的用户信息、更改内容和时间等");
        return result;
    }
}
