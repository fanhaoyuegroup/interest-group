package com.fan.thread.thread_basic_01;

/**
 * Date: 2019/7/2
 * Description:
 *
 * @author lihao
 */
public class WaitNotifyTest {
    static private Object lock = new Object();
    static private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                synchronized (lock){
                    System.out.println("线程"+Thread.currentThread().getName()+" wait begin time="+System.currentTimeMillis());
                    lock.wait(5000);
                    System.out.println("线程"+Thread.currentThread().getName()+" wait end time="+System.currentTimeMillis());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    static private Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            synchronized (lock){
                System.out.println("线程"+Thread.currentThread().getName()+" notify begin time = "+System.currentTimeMillis());
                lock.notify();
                System.out.println("线程"+Thread.currentThread().getName()+" notify end time = "+System.currentTimeMillis());
            }
        }
    };

    public static void main(String[] args) throws InterruptedException {
        Thread t= new Thread(runnable,"A");
        t.start();
        Thread.sleep(3000);
        Thread t2= new Thread(runnable2,"B");
        t2.start();
    }
}
