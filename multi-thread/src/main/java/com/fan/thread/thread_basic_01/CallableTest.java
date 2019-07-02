package com.fan.thread.thread_basic_01;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Date: 2019/7/2
 * Description:
 *
 * @author lihao
 */
public class CallableTest implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.err.println("计算处理中...");
        Thread.sleep(3000);
        return 1;
    }
    public static void main(String[] args) {
        //构建任务
        CallableTest t2 = new CallableTest();
        FutureTask<Integer> task = new FutureTask<Integer>(t2);
        //启动线程
        new Thread(task).start();
        //获取结果
        try {
            Integer integer = task.get(5000, TimeUnit.MILLISECONDS);
            System.out.println("线程执行结果："+integer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
