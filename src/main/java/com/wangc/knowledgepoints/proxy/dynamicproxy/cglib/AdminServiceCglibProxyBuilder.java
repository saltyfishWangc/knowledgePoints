package com.wangc.knowledgepoints.proxy.dynamicproxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @Description: cglib代理类生成器
 * @date 2020/4/16 16:34
 */
public class AdminServiceCglibProxyBuilder {

    private Object target;

    private MethodInterceptor invocationHandler;

    public AdminServiceCglibProxyBuilder(Object target, MethodInterceptor invocationHandler) {
        this.target = target;
        this.invocationHandler = invocationHandler;
    }

    // 给目标对象创建一个代理对象
    public Object getProxyInstance() {
        // 工具类
        Enhancer en = new Enhancer();
        // 设置父类(所以说cglib动态代理不要求目标类实现接口，而是以目标类为父类)
        en.setSuperclass(target.getClass());
        // 设置回调函数(也就是指定调用处理器，对于代理类来说，对目标对象方法的增强都是在调用处理器中定义的)
        en.setCallback(this.invocationHandler);
        // 创建子类代理对象
        return en.create();
    }
}
