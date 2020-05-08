package com.wangc.knowledgepoints.io.stream;

import java.io.Serializable;

public class Employee implements Serializable {

    private String name;

    private int salary;

    private int birthYear;

    private int birthMonth;

    private int birthDay;

    public Employee() {
        System.out.println("反序列化你调用我了吗，如果没有说明反序列化不依赖构造函数");
    }

    public Employee(String name, int salary, int birthYear, int birthMonth, int birthDay) {
        this.name = name;
        this.salary = salary;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(int birthDay) {
        this.birthDay = birthDay;
    }
}
