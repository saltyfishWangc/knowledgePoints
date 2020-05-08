package com.wangc.knowledgepoints.io.stream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectStreamTest {

    public static void main(String... args) throws Exception{
        Employee harry = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        Manager carl = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        carl.setSecretary(harry);
        Manager tony = new Manager("Tony Tester", 40000, 1990, 3, 15);
        tony.setSecretary(harry);

        Employee[] staff = new Employee[3];
        staff[0] = carl;
        staff[1] = harry;
        staff[2] = tony;

        System.out.println("序列化之前打印对象的hashCode值分别为：");
        System.out.println(staff.hashCode());
        for (Employee emp : staff) {
            System.out.println(emp.hashCode());
        }

        // save all employee records to the file employee.dat
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("employee.dat"));
            out.writeObject(staff);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }

        // retrieve all records into a new array
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("employee.dat"));
            Employee[] newStaff = (Employee[]) in.readObject();
            System.out.println("反序列化后打印对象的hashCode值分别为：");
            System.out.println(newStaff.hashCode());
            for (Employee emp1 : newStaff) {
                System.out.println(emp1.hashCode());
            }
            System.out.println("序列化之前、反序列化之后打印对象的hashCode值不一致，说明对象序列化后再反序列化生成的对象是一个新的对象");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }

    }
}
