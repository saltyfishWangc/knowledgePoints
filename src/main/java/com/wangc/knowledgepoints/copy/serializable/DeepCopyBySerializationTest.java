package com.wangc.knowledgepoints.copy.serializable;

import java.io.*;

/**
 * 序列化实现深拷贝
 */
public class DeepCopyBySerializationTest implements Serializable {

    public static void main(String... args) throws Exception {
        Age a = new Age(20);
        Student stu1 = new Student("耶稣", a, 175);

        // 将stu1序列化
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(stu1);
        oos.flush();

        // 反序列化生成新对象stu2
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        Student stu2 = (Student) ois.readObject();
        System.out.println(stu1.toString());
        System.out.println(stu2.toString());

        // 修改stu1中的name，观察stu2有没有变化
        stu1.setName("老天爷");
        // 修改age，这个时候stu1肯定会变，观察stu2有没有变化
        a.setAge(99);
        stu1.setLength(216);
        System.out.println(stu1.toString());
        System.out.println(stu2.toString());
    }
}

/**
 * 年龄类
 */
class Age implements Serializable {
    // 年龄类的成员变量（属性）
    private int age;

    // 构造方法
    public Age(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return this.age + "";
    }
}

/**
 * 学生类
 */
class Student implements Serializable {

    private String name;

    private Age age;

    private int length;

    public Student(String name, Age a, int length) {
        this.name = name;
        this.age = a;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String toString() {
        return "姓名是: " + this.getName() + ", 年龄为： " + this.getAge().toString() + ", 长度是： " + this.getLength();
    }
}
