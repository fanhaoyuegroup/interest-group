package com.fan.design.singleton;

public class ThreadSingleton {
    private static volatile  ThreadSingleton threadSingleton;
    private ThreadSingleton(){}
    public static ThreadSingleton getInstance(){
        if(threadSingleton == null){
            synchronized (ThreadSingleton.class){
                if(threadSingleton == null){
                    threadSingleton = new ThreadSingleton();
                }
            }
        }
        return threadSingleton;
    }
}
