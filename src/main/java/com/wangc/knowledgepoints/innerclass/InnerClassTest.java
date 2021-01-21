package com.wangc.knowledgepoints.innerclass;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @date 2020/6/8 9:22
 */
public class InnerClassTest {
    public static void main1(String... args) {
        ElectricSignReq electricSignReq = new ElectricSignReq();
        electricSignReq.setCard_no("421099");
        electricSignReq.setCust_name("往下");
        electricSignReq.setId_no("4210982");
        electricSignReq.setId_typ("20");

        ElectricSignReq.CustAuthorize cus = electricSignReq.new CustAuthorize();

        cus.setAuthorize_no("12345");
        cus.setAuthorize_reserve1("reserve1");
        cus.setAuthorize_time("20200608");
        cus.setAuthorize_type("1");
        List<ElectricSignReq.CustAuthorize> custs = new ArrayList<ElectricSignReq.CustAuthorize>();
        custs.add(cus);
        electricSignReq.setList_cust_authorize(custs);
        System.out.println(JSON.toJSONString(electricSignReq));

        String jsonParseStr = "{\"card_no\":\"421099\",\"cust_name\":\"往下\",\"id_no\":\"4210982\",\"id_typ\":\"20\",\"list_cust_authorize\":[{\"authorize_no\":\"12345\",\"authorize_reserve1\":\"reserve1\",\"authorize_time\":\"20200608\",\"authorize_type\":\"1\"}]}";
        ElectricSignReq req2 = JSON.parseObject(jsonParseStr, ElectricSignReq.class);

        System.out.println(JSON.toJSONString(req2.getList_cust_authorize()));

    }

    public static void main(String... args) {
        Person stu = new Stu();
//        InnerClassTest.refs(stu);
        if (stu instanceof Stu) {
            System.out.println("是");
        }
        if (stu instanceof Teac) {
            System.out.println("");
        }
    }

    public static void refs(Person p) {
        System.out.println(p.getClass().getSimpleName());
    }
}
