线程阻塞之其他阻塞-join：
    join可以让线程有序的进行，而不是完全交给cpu任意调度


可以参考这篇博文：https://www.jianshu.com/p/fc51be7e5bc0

总结：Thread.join其实底层是通过wait/notifyAll来实现线程的通信达到线程阻塞的目的；当线程执行结束以后，会触发两个事情，第一个是设置native
线程对象为null、第二个是通过notifyAll方法，让等待在previousThread对象锁上的wait方法被唤醒。
注意：既然join底层是通过wait/notifyall来实现线程的通信的，但是调用wait方法必须要获取锁，所以join方法是被synchronized修饰。