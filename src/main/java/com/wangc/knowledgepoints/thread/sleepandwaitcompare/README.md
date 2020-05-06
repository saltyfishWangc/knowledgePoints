Thread.sleep()、Object.wait()区别：
    共同点：都是在多线程环境下，都可以在程序的调用处阻塞指定的毫秒数，并返回
    不同点：Thread.sleep(long)可以不在synchronized块下调用，使用Thread.sleep(long)不会丢失当前线程对任何对象的同步锁(monitor);
            Object.wait(long)必须在synchronized块下调用，调用之后失去对object的monitor，这样做的好处是它不影响其他的线程对object进行操作。

理解：我一直在纠结阻塞的时候释放锁和不释放锁有什么影响？
    Thread.sleep(long)不释放锁，假设我们在synchronized或者lock代码块中调用Thread.sleep(long)，那么当前线程是阻塞了，但是由于Thread.sleep(long)阻塞线程
时不释放锁，那么就算CPU调度其他的线程来执行这个synchronized，其他线程是进不来的，也就是遇到synchronized就因为拿不到锁阻塞了。而Object.wait(long)则不同，它
阻塞线程时是释放掉锁了的，那么其他被CPU调度的线程进来是可以拿到锁执行synchronized代码块的。