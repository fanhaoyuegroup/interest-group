/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.hystrix;

import com.netflix.hystrix.*;

/**
 * HystrixCommandDemo2
 *
 * @author luobosi@2dfire.com
 * @since 2018-10-04
 */
public class HystrixCommandDemo2 extends HystrixCommand<String> {

    private final String name;

    public HystrixCommandDemo2(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("testCommandGroupKey"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("testCommandKey"))
                /* 使用HystrixThreadPoolKey工厂定义线程池名称*/
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("testThreadPool"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
//                		.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)	// 信号量隔离
                        .withExecutionTimeoutInMilliseconds(5000)));
        this.name = name;
    }

//	@Override
//  protected String getFallback() {
//		System.out.println("触发了降级!");
//      return "exeucute fallback";
//  }

    @Override
    protected String run() {
        return "Hello " + name + "! thread:" + Thread.currentThread().getName();
    }
}
