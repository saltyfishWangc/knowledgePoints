package com.wangc.knowledgepoints.redblacktree;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * @Description: 红黑树
 * @date 2020/9/29 16:26
 */
public class RedBlackTreeTest {

    public static void main(String... args) {
        // java1.8开始，HashMap的链表元素超过8个时就采用红黑树的数据结构，所以java1.8开始，HashMap是由数组、链表、红黑树数据结构构成
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("nihao", "nihao");
//        System.out.println(3/4);
//        System.out.println(3%4);
//
//        BigDecimal decmi = (BigDecimal) null;
//
//        String nihao = (String) null;
//
//        String.valueOf("nihao");
//        System.out.println(nihao);
//
//        BigDecimal decmi1 = new BigDecimal ((String) null);
//        System.out.println(decmi1);

////        String str = "c88b5081-44e9-4f6b-8f8a-47221dae1eef|41930076-d226-41d3-9cc7-2ed3a61d98a4";
////        String[] filePaths = str.split("\\|");
////        for (String tmp : filePaths) {
////            System.out.println(tmp);
////        }
//        long nano = System.nanoTime();
//        System.out.println(nano);
//        do {
//            System.out.println("你好");
//        } while ((System.nanoTime() - nano) < TimeUnit.MILLISECONDS.toNanos(1000));
//
//        System.out.println(System.nanoTime() + "---------" + nano);
        System.out.println(RedBlackTreeTest.strTimeToStrDate("20340824195459", "yyyyMMddHHmmss", "yyyy-MM-dd"));

    }

    public static String strTimeToStrDate(String str, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date date ;
        try {
            date = format.parse(str);
            return format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String strTimeToStrDate(String oriStr, String oriDateFormat,String destDateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(oriDateFormat);
        Date date ;
        try {
            date = format.parse(oriStr);
            format = new SimpleDateFormat(destDateFormat);
            return format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
