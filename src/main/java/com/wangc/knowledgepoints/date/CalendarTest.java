package com.wangc.knowledgepoints.date;

import com.alibaba.fastjson.JSON;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description: 日历类
 * @date 2020/4/29 16:54
 */
public class CalendarTest {
    public static void main(String... args) {
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DAY_OF_YEAR, -1);
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));

//        System.out.println(strTimeToStrDate("20201210", "yyyyMMdd", "yyyy-MM-dd HH:mm:ss"));
//        System.out.println(strTimeToStrDate("20201130", "yyyyMMdd",
//                "yyyy-MM-dd 00:00:00", 1));
//        System.out.println(strTimeToStrDate("2020-11-30", "yyyy-MM-dd",
//                "yyyyMMdd", 0));
//        String str = "2020-12-15-";
//        if (str.endsWith("-")) {
//            str = str.substring(0, str.length() - 1);
//        }
//        System.out.println(str);
//        Map<String, Object> map = new HashMap<String, Object>();

        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            if ("4".equals(str) || "5".equals(str) || "6".equals(str)) {
                iterator.remove();
            }
        }

        for (String listSt : list) {
            System.out.println(listSt);
        }


    }

    public static String strTimeToStrDate(String oriStr, String oriDateFormat,String destDateFormat, int days) {
        SimpleDateFormat format = new SimpleDateFormat(oriDateFormat);
        Date date ;
        try {
            date = format.parse(oriStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
//            cal.add(Calendar.DAY_OF_MONTH, days);
            cal.add(Calendar.DAY_OF_YEAR, days);
//            cal.add(Calendar.DATE, days);
            format = new SimpleDateFormat(destDateFormat);
            return format.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
