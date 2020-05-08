package com.wangc.knowledgepoints.io.stream;

import java.io.Serializable;

public class Manager extends Employee implements Serializable {

    public Manager() {
        System.out.println("反序列化你调用我了吗，如果没有说明反序列化不依赖构造函数");
    }

    private Employee secretary;

    public Manager(String name, int salary, int birthYear, int birthMonth, int birthDay) {
        super(name, salary, birthYear, birthMonth, birthDay);
    }

    public Employee getSecretary() {
        return secretary;
    }

    public void setSecretary(Employee secretary) {
        this.secretary = secretary;
    }
}
