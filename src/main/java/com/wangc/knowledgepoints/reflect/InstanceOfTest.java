package com.wangc.knowledgepoints.reflect;

/**
 * @author
 * @Description:
 * @date 2020/12/28 11:46
 */
public class InstanceOfTest {

    public static void main(String... args) {
//        System.out.println("你好");
//        Stu stu = new Stu();
//        test(stu);
        String ars = "nihao";
        System.out.println("长度:" + ars.length());
        System.out.println("大小:" + ars.getBytes().length);


        String mm = "你好";
        System.out.println("长度:" + mm.length());
        System.out.println("大小:" + mm.getBytes().length);

        String floatStr = "0.9";
//        System.out.println(Float.parseFloat(floatStr));
        testFloat(Float.parseFloat(floatStr));
    }

    private static void testFloat(float f) {
        System.out.println(f);
    }

    private static void test(Person p) {
        Class<? extends Person> clazz = p.getClass();
        System.out.println(clazz.getSimpleName());
        Class clazzs = p.getClass();
        System.out.println(clazzs.getSimpleName());

        if (p instanceof Stu) {
            System.out.println("是Stu类型");
        }

        if (p instanceof Person) {
            System.out.println("是Person类型");
        }
    }
}
