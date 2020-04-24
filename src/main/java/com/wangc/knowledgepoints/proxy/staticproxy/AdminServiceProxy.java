package com.wangc.knowledgepoints.proxy.staticproxy;

/**
 * @Description: 代理类
 * @date 2020/4/16 15:24
 */
public class AdminServiceProxy implements AdminService{

    private AdminService target;

    public AdminServiceProxy(AdminService target) {
        this.target = target;
    }

    public void update() {
        System.out.println("判断用户是否有权限进行update操作");
        target.update();
        System.out.println("记录用户执行update操作的用户信息、更改内容和事件等");
    }

    public Object find() {
        System.out.println("判断用户是否有权限进行find操作");
        System.out.println("记录用户执行find操作的用户信息、查看内容和时间等");
        return target.find();
    }
}
