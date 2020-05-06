package com.wangc.knowledgepoints.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Description: 日历类
 * @date 2020/4/29 16:54
 */
public class CalendarTest {
    public static void main(String... args) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
    }
}
