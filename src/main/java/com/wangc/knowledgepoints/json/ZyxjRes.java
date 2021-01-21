package com.wangc.knowledgepoints.json;

import java.io.Serializable;

/**
 * @Description:
 * @author
 * @date 2020/6/11 10:04
 */
public class ZyxjRes implements Serializable {

    private static final long serialVersionUID = -3814085561222420532L;

    /**
     * 响应头
     */
    private ResHead head;

    /**
     * 响应体
     */
    private ResBody body;

    public ResHead getHead() {
        return head;
    }

    public void setHead(ResHead head) {
        this.head = head;
    }

    public ResBody getBody() {
        return body;
    }

    public void setBody(ResBody body) {
        this.body = body;
    }
}
