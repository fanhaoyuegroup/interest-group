package com.fan.thread.thread_basic_01;

/**
 * Date: 2019/7/2
 * Description:
 *
 * @author lihao
 */
public class YieldTest {
    public static void main(String[] args) {
        Thread threadA = new ThreadA();
        Thread threadB = new ThreadB();
        threadA.start();
        threadB.start();
    }
}
class ThreadA extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("ThreadA--" + i);
            Thread.yield();
        }
    }
}
class ThreadB extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("ThreadB--" + i);
            Thread.yield();
        }
    }
}
//运行结果：
/*
ThreadB--0
ThreadA--0
ThreadB--1
ThreadB--2
ThreadB--3
ThreadB--4
ThreadB--5
ThreadB--6
ThreadB--7
ThreadB--8
ThreadB--9
ThreadA--1
ThreadA--2
ThreadA--3
ThreadA--4
ThreadA--5
ThreadA--6
ThreadA--7
ThreadA--8
ThreadA--9
*/
