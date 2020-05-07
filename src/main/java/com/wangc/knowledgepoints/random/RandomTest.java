package com.wangc.knowledgepoints.random;

import java.util.Random;
import java.util.Scanner;

/**
 * @Description: 随机取数测试类
 * @date 2020/5/6 15:43
 */
public class RandomTest {

    public static void main(String... args) {
//        i.1个月：0.95%~ 1.25%
//        ii.3个月：0.65%~ 0.85%
//        iii.6个月：0.58%~ 0.75%
//        iv.12个月以上：0.55%~ 0.72%
        // 【95,125】
        // 【65,85】
        // 【58,75】
        // 【55,72】
        Random rm = new Random();
        // 第一种
//        int random = rm.nextInt(125-95)+95;
//        for (int i = 0; i < 20; i++) {
//            System.out.println(rm.nextInt(125-95)+95);
//        }

        int random = 0;
        int i = 1; // 3,6,12
        switch (i) {
            case 1:
                random = rm.nextInt(125-95)+95;
                break;
            case 3:
                random = rm.nextInt(85-65)+65;
                break;
            case 6:
                random = rm.nextInt(75-58)+58;
                break;
            case 12:
                random = rm.nextInt(72-55)+55;
                break;
        }
        System.out.println("i = " + i + ", 随机数是: " + random);

//        int i;
//        int random = 0;
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNext()) {
//            i = scanner.nextInt();
//            switch (i) {
//                case 1:
//                    random = rm.nextInt(125-95)+95;
//                    break;
//                case 3:
//                    random = rm.nextInt(85-65)+65;
//                    break;
//                case 6:
//                    random = rm.nextInt(75-58)+58;
//                    break;
//                case 12:
//                    random = rm.nextInt(72-55)+55;
//                    break;
//            }
//            System.out.println("i = " + i + ", 随机数是: " + random);
//        }

    }
}
