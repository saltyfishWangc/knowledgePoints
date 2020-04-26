package com.wangc.knowledgepoints.Thread.sleep;

/**
 * @Description:
 * @date 2020/4/26 17:16
 */
public class ThreadSleepTest {
}

class CountThread implements Runnable {
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i%10 == 0) {
                System.out.println("被要求睡眠1s");
                try {
                    System.out.println("当前计数：" + i);
                    Thread.sleep(1000);
                    System.out.println("看看线程睡眠后再次恢复资源情况，当前计数：" + i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("当前计数：" + i);
        }
    }
}
