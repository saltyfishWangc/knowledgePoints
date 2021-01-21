package com.wangc.knowledgepoints.Enum;

import java.util.*;

public enum WeekendEnum implements EnumInnerTest {

    MONDAY(1, "xq1", "星期一"),
    TurseDay(2, "xq2", "星期二"),
    ThursDay(4, "xq4", "星期四"),
    WEDNESDAY(3, "xq3", "星期三");

    private int code;
    private String node;
    private String desc;
    WeekendEnum(int code, String node, String desc) {
        this.code = code;
        this.node = node;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getNode() {
        return this.node;
    }

    public List<String> parseEnum2Arr() {
//        Integer[] arrs = new Integer[WeekendEnum.values().length];
//        List<Integer> list = new ArrayList<Integer>();
//        for (WeekendEnum weekendEnum : WeekendEnum.values()) {
//            list.add(weekendEnum.getCode());
//        }
////        Integer[] arrs = (Integer[]) list.toArray();
//        list.toArray(arrs);
//        Arrays.sort(arrs);
//        return arrs;
        List<String> list = new ArrayList<String>();
        Map<Integer, String> sortedMap = new TreeMap<Integer, String>();
        for (WeekendEnum creditProcessNodeEnum : WeekendEnum.values()) {
            sortedMap.put(creditProcessNodeEnum.getCode(), creditProcessNodeEnum.getNode());
        }
        for (Integer idx : sortedMap.keySet()) {
            list.add(sortedMap.get(idx));
        }
        return list;
    }
}

interface EnumInnerTest {
    List<String> parseEnum2Arr();
}
