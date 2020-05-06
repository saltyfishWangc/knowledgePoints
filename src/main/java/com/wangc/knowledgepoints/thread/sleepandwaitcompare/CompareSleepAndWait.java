package com.wangc.knowledgepoints.thread.sleepandwaitcompare;

/**
 * @Description: 比较sleep()和wait()
 * @date 2020/4/27 10:09
 */
public class CompareSleepAndWait {

    public static void main(String... args) throws Exception {
        CalculateThread th = new CalculateThread();
        th.start();
        th.second();
        Thread.sleep(10);
        System.out.println(th.number);
    }
}

class CalculateThread extends Thread {

    int number = 10;

    public synchronized void first() {
        System.out.println("this is first");
        number = number + 1;
    }

    public synchronized void second() throws Exception {
        System.out.println("this is second");
        Thread.sleep(1000);
//        this.wait(1000);
        System.out.println("继续");
        number = number*100;
    }

    @Override
    public void run() {
        first();
    }
}
