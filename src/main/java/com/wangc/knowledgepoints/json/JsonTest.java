package com.wangc.knowledgepoints.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author
 * @Description:
 * @date 2020/6/11 11:21
 */
public class JsonTest {

    public static void main(String... args) {
        String respStr = "{\"response\":{\"head\":{\"trade_code\":\"ZYXF001\",\"serno\":\"535392357458\",\"sys_flag\":\"53\",\"trade_type\":\"\",\"trade_date\":\"2020-06-11\",\"trade_time\":\"11:16:11\",\"channel_no\":\"53\",\"ret_flag\":\"90014\",\"ret_msg\":\"请求报文-证件号码不合法！\"},\"body\":{}}}";
        JSONObject resJob = JSONObject.parseObject(respStr);
        JSONObject resResponse = resJob.getJSONObject("response");
        JSONObject resHeadJob = resResponse.getJSONObject("head");
        ResHead resHead = resHeadJob.toJavaObject(ResHead.class);
        System.out.println("响应头信息：【" + JSON.toJSONString(resHead) + "】");

        JSONObject resBodyJob = resResponse.getJSONObject("body");
//        Class<ResBody> resClass = BindBankCardRes.class;
        ResBody resBody = resBodyJob.toJavaObject(BindBankCardRes.class);
        System.out.println("响应体信息：【" + JSON.toJSONString(resBody) + "】");
    }
}
