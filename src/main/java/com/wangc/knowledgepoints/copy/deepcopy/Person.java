package com.wangc.knowledgepoints.copy.deepcopy;

/**
 * @Description: 要实现拷贝的类都必须实现Cloneable接口
 * @date 2020/4/24 16:56
 */
public class Person implements Cloneable{

    private String name;
    private int age;
    private Address address;

    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        this.address = new Address();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String provinces, String city) {
        address.setAddress(provinces, city);
    }

    public void display(String name) {
        System.out.println(name + ":" + "name" + name + ", age=" + age + "," + address);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Person p = (Person) super.clone();
        p.address = (Address) address.clone();
//        return super.clone();
        return p;
    }
}
