package com.wangc.knowledgepoints.innerclass;

import java.io.Serializable;

/**
 * @author
 * @Description:
 * @date 2020/6/10 17:01
 */
public class Req {

    private Head head;

    private Body boy;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Body getBoy() {
        return boy;
    }

    public void setBoy(Body boy) {
        this.boy = boy;
    }
}
