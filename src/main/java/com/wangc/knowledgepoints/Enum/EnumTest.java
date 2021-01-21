package com.wangc.knowledgepoints.Enum;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author
 * @Description:
 * @date 2020/11/18 17:38
 */
public class EnumTest {

    public static void main(String... args) {
//        int[] arr = {WeekendEnum.MONDAY.getCode(), WeekendEnum.WEDNESDAY.getCode(),WeekendEnum.TurseDay.getCode()};
//
//        for (int i : arr) {
//            System.out.println(i);
//        }
//        Arrays.sort(arr);
//        for (int i : arr) {
//            System.out.println(i);
//        }
//        WeekendEnum.values();
//        for (WeekendEnum en : WeekendEnum.values()) {
//            System.out.println(en.getCode() + "-" + en.getDesc());
//        }
//        System.out.println(WeekendEnum.class.getSimpleName());
//        Integer[] arrs = (Integer[]) WeekendEnum.MONDAY.parseEnum2Arr();
//        System.out.println(WeekendEnum.MONDAY.parseEnum2Arr().length);
//        System.out.println(WeekendEnum.TurseDay.parseEnum2Arr().length);
//        System.out.println(WeekendEnum.WEDNESDAY.parseEnum2Arr().length);
//        System.out.println(WeekendEnum.ThursDay.parseEnum2Arr().length);
//        System.out.println(arrs.length);
//        for (Integer in : arrs) {
//            System.out.println(in);
//        }
//        List<String> list = WeekendEnum.MONDAY.parseEnum2Arr();
//        for (String str : list) {
//            System.out.println(str);
//        }
//        BigDecimal nn = new BigDecimal(0);
//        if(nn.compareTo(BigDecimal.ZERO)<1){
////            System.out
//        }
        JsonTestDto dto = new JsonTestDto();
        dto.setName("wangc");
        dto.setAge("21");
        Map<String, String> tableMap = new HashMap<String, String>();
        tableMap.put("addr", "深圳");
        tableMap.put("score", "56");
        dto.setTable(tableMap);

        String jsonStr = JSON.toJSONString(dto);

        System.out.println(jsonStr);

//        JsonTestDto2 jsonTestDto2 = JSONObject.parseObject(jsonStr, JsonTestDto2.class);
//        System.out.println(jsonTestDto2);
        JsonTestDto2 dto2 = new JsonTestDto2();
        dto2.setName("xiaoming");
        dto2.setAge("22");
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setAddr("北京");
        deviceInfo.setScore("95");
        dto2.setTable(deviceInfo);
        System.out.println(JSON.toJSONString(dto2));

        Map<String, String> map = new HashMap<String, String>();
        new BigDecimal(map.get("nihao"));

    }
}

class JsonTestDto {
    private String name;

    private String age;

    private Map<String, String> table;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Map<String, String> getTable() {
        return table;
    }

    public void setTable(Map<String, String> table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "JsonTestDto{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", table=" + table +
                '}';
    }
}

class JsonTestDto2 {
    private String name;

    private String age;

    private DeviceInfo table;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public DeviceInfo getTable() {
        return table;
    }

    public void setTable(DeviceInfo table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "JsonTestDto2{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", table=" + table +
                '}';
    }
}

class DeviceInfo {
    private String score;

    private String addr;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "deviceInfo{" +
                "score='" + score + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }
}
