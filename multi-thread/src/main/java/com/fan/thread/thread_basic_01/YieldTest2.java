package com.fan.thread.thread_basic_01;

/**
 * Date: 2019/7/2
 * Description:
 *
 * @author lihao
 */

public class YieldTest2 {
    public static void main(String[] args){
        ThreadC t1 = new ThreadC("t1");
        ThreadC t2 = new ThreadC("t2");
        t1.start();
        t2.start();
    }
}
class ThreadC extends Thread{
    public ThreadC(String name){
        super(name);
    }

    @Override
    public synchronized void run(){
        for(int i=0; i <10; i++){
            System.out.printf("%s [%d]:%d\n", this.getName(), this.getPriority(), i);
            // i整除4时，调用yield
            if (i%4 == 0){
                Thread.yield();
            }
        }
    }
}
/*
 运行结果
 t1 [5]:0
 t2 [5]:0
 t1 [5]:1
 t1 [5]:2
 t1 [5]:3
 t1 [5]:4
 t1 [5]:5
 t1 [5]:6
 t1 [5]:7
 t1 [5]:8
 t1 [5]:9
 t2 [5]:1
 t2 [5]:2
 t2 [5]:3
 t2 [5]:4
 t2 [5]:5
 t2 [5]:6
 t2 [5]:7
 t2 [5]:8
 t2 [5]:9
 */