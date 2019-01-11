/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.hystrix;

import com.netflix.hystrix.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * HystrixCommand4CircuitBreakerTest
 *
 * @author luobosi@2dfire.com
 * @since 2018-10-04
 */
public class HystrixCommand4CircuitBreakerTest extends HystrixCommand<String> {

    private final String name;

    public HystrixCommand4CircuitBreakerTest(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CircuitBreakerTestGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CircuitBreakerTestKey"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("CircuitBreakerTest"))
                .andThreadPoolPropertiesDefaults(
                        // 配置线程池
                        HystrixThreadPoolProperties.Setter()
                                // 配置线程池里的线程数，设置足够多线程，以防未熔断却打满 thread pool
                                .withCoreSize(200)
                )
                .andCommandPropertiesDefaults(
                        // 配置熔断器
                        HystrixCommandProperties.Setter()
                                .withCircuitBreakerEnabled(true)
                                .withCircuitBreakerRequestVolumeThreshold(3)
                                .withCircuitBreakerErrorThresholdPercentage(80)
                                // 设置为 true 时，所有请求都将被拒绝，直接到 fallback
                                .withCircuitBreakerForceOpen(true)
                                // 设置为 true 时，将忽略错误
                                .withCircuitBreakerForceClosed(true)
                                // 信号量隔离
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                                .withExecutionTimeoutInMilliseconds(5000)
                )

        );
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        System.out.println("running run() : " + name);
        int num = Integer.valueOf(name);
        if (num % 2 == 0 && num < 10) {
            return name;
        } else {
            // 无限循环模拟超时
            while (true) {}
        }
    }

    @Override
    protected String getFallback() {
        return "CircuitBreaker fallback: " + name;
    }

    public static class UnitTest {
        @Test
        public void testSynchronous() throws IOException {
            for (int i = 0; i < 50; i++) {
                try {
                    System.out.println("===========" + new HystrixCommand4CircuitBreakerTest(String.valueOf(i)).execute());
//	        		try {
//	            		TimeUnit.MILLISECONDS.sleep(1000);
//	            	}catch(Exception e) {}
//	        		Future<String> future = new HystrixCommand4CircuitBreakerTest("Hlx"+i).queue();
//	        		System.out.println("===========" + future);
                } catch(Exception e) {
                    System.out.println("run()抛出HystrixBadRequestException时，被捕获到这里" + e.getCause());
                }

            }

            System.out.println("------开始打印现有线程---------");
            Map<Thread, StackTraceElement[]> map=Thread.getAllStackTraces();
            for (Thread thread : map.keySet()) {
                System.out.println(thread.getName());
            }
            System.out.println("thread num: " + map.size());

            System.in.read();
        }
    }

}

















