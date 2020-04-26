package com.wangc.knowledgepoints.thread.sleep;

/**
 * @Description: 线程阻塞之其他阻塞-sleep
 * @date 2020/4/26 17:16
 */
public class ThreadSleepTest {

    public static void main(String... args) {
        new Thread(new CountThread()).start();
    }
}

class CountThread implements Runnable {
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i%10 == 0) {
                System.out.println("被要求睡眠1s");
                try {
                    System.out.println("当前计数：" + i);
                    Thread.sleep(1000);
                    System.out.println("看看线程睡眠后再次恢复资源情况，当前计数：" + i + "， 这行打印如果在一个线程内一直接在上一行打印，说明线程在由运行状态->就绪状态->运行状态这个过程再开始还是接着之前的代码走的，说明每个线程都有一个程序计数器类似的东西用来记着线程代码走到哪了，下次接着走");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("当前计数：" + i);
        }
        System.out.println("由此可见Thread.sleep()是可以让线程慢点执行");
    }
}
