package com.wangc.knowledgepoints.copy.deepcopy;

/**
 * @Description: 深拷贝测试类
 * @date 2020/4/24 17:30
 */
public class DeepCopyTest {

    public static void main(String... args) throws Exception {
        Person p1 = new Person("zhangsan", 21);
        p1.setAddress("湖北省", "武汉市");

        Person p2 = (Person) p1.clone();

        System.out.println("p1:" + p1);
        System.out.println("p1.getName:" + p1.getName().hashCode());

        System.out.println("p2:" + p2);
        System.out.println("p2.getName:" + p2.getName().hashCode());

        p1.display("p1");
        p2.display("p2");
        p2.setAddress("湖北省", "荆州市");
        System.out.println("将复制之后的对象地址修改：");
        p1.display("p1");
        p2.display("p2");
    }
}
