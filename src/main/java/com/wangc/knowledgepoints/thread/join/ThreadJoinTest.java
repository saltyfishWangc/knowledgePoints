package com.wangc.knowledgepoints.thread.join;

/**
 * @Description: 线程阻塞之其他阻塞-join
 * @date 2020/4/26 17:16
 */
public class ThreadJoinTest {

    public static void main1(String... args) throws Exception{
        Thread t = new Thread(new PrintTest());
        t.start();
//        t.join();
        // 如果将t.join()注释，则Thread-0和main打印的顺序是不确定的，如果打印的数据多，更有可能出现交叉打印的情况
        // 将t.join()注释放开，表示在main线程中调用别的线程join，则会将当前线程从当前位置开始阻塞，直到join进来的线程执行完成后再回到当前线程阻塞处继续执行当前线程接下来的代码
        // 这样可以保证线程顺序的执行
        // join()在这里等同于在当前线程调用wait()将当前线程在此阻塞，在子线程中执行完后调用notify()

        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " : 我是主人，我最后打印 ：" + i);
        }
    }
}

class PrintTest implements Runnable {

    public void run() {
        for (int i = 0; i < 500; i++) {
            System.out.println(Thread.currentThread().getName() + " : 我是客人，我先打印 " + i);
        }
    }
}
