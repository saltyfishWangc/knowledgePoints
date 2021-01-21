package com.wangc.knowledgepoints.bigdecimal;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @Description:
 * @date 2020/7/4 14:57
 */
public class BigDecimalMethodTest {
    public static void main(String... args) {
        BigDecimal number = new BigDecimal(4000.00);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("n1", number.stripTrailingZeros().toPlainString());
        System.out.println(JSON.toJSONString(map));
    }
}
