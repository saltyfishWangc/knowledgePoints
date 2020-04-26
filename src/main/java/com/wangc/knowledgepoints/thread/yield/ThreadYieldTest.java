package com.wangc.knowledgepoints.thread.yield;

/**
 * 线程让步：当前运行状态中的线程放弃CPU，直接变成就绪状态
 */
public class ThreadYieldTest {

    public static void main(String... args) {
        for (int i = 0; i < 5; i++) {
            new PrintThread().start();
        }
    }
}

class PrintThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if (i%10 == 0) {
                System.out.println(Thread.currentThread().getName() + " 当前是第 " + i + " 次，需要让步");
                Thread.yield();
            } else {
                System.out.println(Thread.currentThread().getName() + " 当前是第 " + i + " 次");
            }
        }
    }
}
