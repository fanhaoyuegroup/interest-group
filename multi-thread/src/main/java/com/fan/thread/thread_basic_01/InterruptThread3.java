package com.fan.thread.thread_basic_01;

/**
 * Date: 2019/7/2
 * Description:
 *
 * @author lihao
 */
public class InterruptThread3 extends Thread {
    @Override
    public void run() {
        int number = 0;
        // 记录程序开始的时间
        Long start = System.currentTimeMillis();
        while (true) {
            // 每次执行一次结束的时间
            Long end = System.currentTimeMillis();
            // 获取时间差
            Long interval = end - start;
            //时间：如果时间超过了10秒，
            if (interval >= 10000) {
                // 中断线程
                //interrupted();
                System.err.println("中断啦");
                return;
                //数量
            } else if (number >= 500) {
                System.out.println("中断啦");
                // 中断线程
                //interrupted();
                return;
            }
            number++;
            System.out.println("number : "+number);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public static void main(String[] args) throws InterruptedException {
        InterruptThread3 thread = new InterruptThread3();
        // 启动线程
        thread.start();
        // 等待10秒后中断线程
        Thread.sleep(1000);
        //thread.interrupt();
    }
}
