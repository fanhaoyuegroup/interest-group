/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;

/**
 * HystrixObservableCommandDemo
 *
 * @author luobosi@2dfire.com
 * @since 2018-10-04
 */
public class HystrixObservableCommandDemo extends HystrixObservableCommand<String> {

    private final String name;

    public HystrixObservableCommandDemo(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
//		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("testCommandGroupKey"))
//                .andCommandKey(HystrixCommandKey.Factory.asKey("testCommandKey"))
//                /* 使用HystrixThreadPoolKey工厂定义线程池名称*/
////                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("testThreadPool"))
//                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(5000)));
        this.name = name;
    }

//	@Override
//    protected String getFallback() {
//		System.out.println("触发了降级!");
//        return "exeucute fallback";
//    }

    //	@Override
    protected Observable<String> construct() {
        System.out.println("in construct! thread:" + Thread.currentThread().getName());
        return Observable.create(new Observable.OnSubscribe<String>() {
            //            @Override
            public void call(Subscriber<? super String> observer) {
                try {
                    System.out.println("in call of construct! thread:" + Thread.currentThread().getName());
                    if (!observer.isUnsubscribed()) {
//                      observer.onError(getExecutionException());	// 直接抛异常退出，不会往下执行
                        observer.onNext("Hello1" + " thread:" + Thread.currentThread().getName());
                        observer.onNext("Hello2" + " thread:" + Thread.currentThread().getName());
                        observer.onNext(name + " thread:" + Thread.currentThread().getName());
                        System.out.println("complete before------" + " thread:" + Thread.currentThread().getName());
                        observer.onCompleted();    // 不会往下执行observer的任何方法
                        System.out.println("complete after------" + " thread:" + Thread.currentThread().getName());
                        observer.onCompleted();    // 不会执行到
                        observer.onNext("abc");    // 不会执行到
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        });
    }
}
