package com.wangc.knowledgepoints.innerclass;

import com.alibaba.fastjson.JSON;

/**
 * @author
 * @Description:
 * @date 2020/6/10 17:03
 */
public class RequestTest {

    public static void main(String... args) {
        Req req = new Req();

        Head head = new Head();
        head.setSerno("123121");
        head.setTradeDate("2020-06-10");

        Body body = new Body();
        body.setAge(23);
        body.setName("wangc");

        req.setHead(head);
        req.setBoy(body);

        ZyxjReq request = new ZyxjReq();
        request.setRequest(req);

        System.out.println(JSON.toJSONString(request));
    }
}
