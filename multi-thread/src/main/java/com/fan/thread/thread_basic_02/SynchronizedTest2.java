/*
 * Copyright (C) 2009-2018 Hangzhou FanDianEr Technology Co., Ltd. All rights reserved
 */
package com.fan.thread.thread_basic_02;

/**
 * SynchronizedTest1
 *
 * @author yangjiao
 * @since 2019-08-01
 */
public class SynchronizedTest2 implements Runnable{
    private static Integer count = 0;
    /**
     * 类锁
     */
    public static void method2(){
        //代码块
        synchronized (SynchronizedTest2.class){
            for (int i=0;i<5;i++){
                count++;
                System.out.println(Thread.currentThread().getName()+":"+i+":"+count);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
  /*  public void method3(){
        //代码块
        synchronized (this){
            for (int i=0;i<5;i++){
                count++;
                System.out.println(Thread.currentThread().getName()+":"+i+":"+count);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/


    public static  synchronized void method1(){
        //代码块
            for (int i=0;i<5;i++){
                count--;
                System.out.println(Thread.currentThread().getName()+":"+i+":"+count);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        if("add".equals(name)){
            method2();
        }else {
            method1();
        }
    }
    public static void main(String[] args) {
        SynchronizedTest2 test = new SynchronizedTest2();
        SynchronizedTest1 test2 = new SynchronizedTest1();
        Thread thread = new Thread(test,"add");
        Thread thread2 = new Thread(test2,"dec");
        thread.start();
        thread2.start();
    }
}
