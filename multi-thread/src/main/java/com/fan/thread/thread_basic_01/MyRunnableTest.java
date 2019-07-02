package com.fan.thread.thread_basic_01;

/**
 * Date: 2019/7/2
 * Description:
 *
 * @author lihao
 */
public class MyRunnableTest implements Runnable {
    private int count = 5;
    @Override
    public void run() {
        while (count>0){
            count--;
            System.out.println("count:"+count);
        }
    }
    public static void main(String[] args) {
        Runnable runnable = new MyRunnableTest();
        Thread thread = new Thread(runnable);
        //  runnable.run();
        thread.start();
    }
}
