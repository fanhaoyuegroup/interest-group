package com.fan.design.singleton;

public class Singleton {
    private static Singleton singleton = new Singleton();

    //必须设置私有
    private Singleton(){

    }

    public static synchronized Singleton getInstance(){
        return singleton;
    }

    public void sayHello(){
        System.out.println("hello world");
    }
}
