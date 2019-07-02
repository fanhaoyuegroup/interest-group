package com.fan.thread.thread_basic_01;

/**
 * Date: 2019/7/2
 * Description:
 *
 * @author lihao
 */
public class MyThread extends Thread{
    private Integer count = 5;
    @Override
    public void run() {
        synchronized (this) {
            while (count > 0) {
                count--;
                System.out.println(Thread.currentThread().getName() + ":" + count);
            }
        }
    }
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread t2 = new Thread(myThread, "b");
        t2.start();
    }
}
