package com.fan.thread.thread_basic_01;

/**
 * Date: 2019/7/2
 * Description:
 *
 * @author lihao
 */
public class JoinTest {
    static class MyThread extends Thread{
        @Override
        public void run() {
            for(int i = 0; i < 10; i ++){
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName()+"======>子线程在执行任务:"+i);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }

    }
    public static void main(String[] args){
        try {
            System.out.println(Thread.currentThread().getName()+"======>主线程在执行任务前1");
            System.out.println(Thread.currentThread().getName()+"======>主线程在执行任务前2");
            MyThread myThread = new MyThread();
            myThread.start();
            myThread.join();
            System.out.println(Thread.currentThread().getName()+"======>主线程在执行任务后1");
            System.out.println(Thread.currentThread().getName()+"======>主线程在执行任务后2");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
//运行结果：
     /*
        main======>主线程在执行任务前1
        main======>主线程在执行任务前2
        Thread-0======>子线程在执行任务:0
        Thread-0======>子线程在执行任务:1
        Thread-0======>子线程在执行任务:2
        Thread-0======>子线程在执行任务:3
        Thread-0======>子线程在执行任务:4
        Thread-0======>子线程在执行任务:5
        Thread-0======>子线程在执行任务:6
        Thread-0======>子线程在执行任务:7
        Thread-0======>子线程在执行任务:8
        Thread-0======>子线程在执行任务:9
        main======>主线程在执行任务后1
        main======>主线程在执行任务后2
     */