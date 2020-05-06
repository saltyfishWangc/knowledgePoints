package com.wangc.knowledgepoints.thread.sleepandwaitcompare;

/**
 * @Description: Thread.sleep(long)和Object.wait(long)关于是否释放锁的比较
 * @date 2020/4/27 17:36
 */
public class CompareSleepAndWaitAboutReleaseLock {

    public static void main(String... args) {
        PrintThread t1 = new PrintThread();
        t1.setName("老板");
        t1.start();

        PrintThread t2 = new PrintThread();
        t2.setName("员工");
        t2.start();
    }
}

class PrintThread extends Thread {

    @Override
    public void run() {
        synchronized (PrintThread.class) {
            System.out.println("我是 " + Thread.currentThread().getName() + ", 我先拿到CPU资源了");
            for (int i = 0; i < 10; i++) {
                if (i % 4 == 0) {
                    System.out.println("我是 " + Thread.currentThread().getName() + "，我来睡眠10s");
                    try {
//                        Thread.sleep(10*1000);
//                        System.out.println("我是 " + Thread.currentThread().getName() + ", 上一行打印如果是我，那我刚才就是在sleep，因为我sleep期间不释放锁，别的线程都进不来");
                        this.wait(2*1000);
                        System.out.println("我是 " + Thread.currentThread().getName() + ", 上一行打印如果是我，那我刚才就是在sleep，因为我sleep期间不释放锁，别的线程都进不来");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("我是 " + Thread.currentThread().getName() + ", 打印：" + i);
                }
            }
        }
    }
}
