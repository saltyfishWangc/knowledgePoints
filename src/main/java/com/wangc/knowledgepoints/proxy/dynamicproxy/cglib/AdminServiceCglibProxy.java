package com.wangc.knowledgepoints.proxy.dynamicproxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description: cglib代理将调用处理器和生成代理对象逻辑封装在一个类
 * @date 2020/4/16 16:55
 */
public class AdminServiceCglibProxy implements MethodInterceptor{

    private Object target;

    public AdminServiceCglibProxy(Object target) {
        this.target = target;
    }

    // 生成代理对象
    public Object getProxyInstance() {
        // 工具类
        Enhancer en = new Enhancer();
        // 设置父类
        en.setSuperclass(target.getClass());
        // 设置回调函数
        en.setCallback(this);
        // 创建子类代理对象
        return en.create();
    }

    // 调用处理
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("判断用户是否有权限进行操作");
        Object result = method.invoke(target, objects);
        System.out.println("记录用户执行操作的用户信息、更改内容和时间等");
        return result;
    }
}
