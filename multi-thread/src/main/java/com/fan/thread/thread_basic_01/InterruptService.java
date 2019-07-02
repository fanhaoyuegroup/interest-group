package com.fan.thread.thread_basic_01;

/**
 * Date: 2019/7/2
 * Description:
 *
 * @author lihao
 */
public class InterruptService implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("begin run");
            Thread.currentThread().interrupt();
            //方式一，线程进入sleep
            //Thread.sleep(10);
            //方式二、join
            //Thread.currentThread().join();
            //方式三、wait
            //Thread.currentThread().wait();
            System.out.println("begin end");
        } catch (Exception e) {
            System.out.println("先interrupt再阻塞后终止了");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        try {
            InterruptService interruptService = new InterruptService();
            Thread thread = new Thread(interruptService);
            thread.start();
            //thread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//运行结果：
/*
begin run
java.lang.IllegalMonitorStateException
先interrupt再阻塞后终止了
	at java.lang.Object.wait(Native Method)
	at java.lang.Object.wait(Object.java:502)
	at thread.InterruptService.run(InterruptService.java:25)
	at java.lang.Thread.run(Thread.java:748)
*/
