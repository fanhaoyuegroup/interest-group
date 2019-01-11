/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.hystrix;

import com.netflix.hystrix.*;

/**
 * HystrixCommandDemo
 *
 * @author luobosi@2dfire.com
 * @since 2018-10-04
 */
public class HystrixCommandDemo extends HystrixCommand<String> {


    public HystrixCommandDemo() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("test"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        // 开启熔断模式
                        .withCircuitBreakerEnabled(true)
                        // 出现错误的比率超过 30% 就开启熔断
                        .withCircuitBreakerErrorThresholdPercentage(30)
                        // 至少有 10 个请求才进行 errorThresholdPercentage 错误百分比计算
                        .withCircuitBreakerRequestVolumeThreshold(10)
                        // 半开试探休眠时间，这里设置为 1 秒, 如果 run 方法超时 1s 则会触发回退方法
                        .withExecutionTimeoutInMilliseconds(1000)
                )

        );
    }

    /**
       fallback（降级）fallback实际流程是当run()/construct()被触发执行时或执行中发生错误时，将转向执行getFallback()/resumeWithFallback()。

        1. 非HystrixBadRequestException异常：当抛出HystrixBadRequestException时，调用程序可以捕获异常，
            没有触发getFallback()，而其他异常则会触发getFallback()，调用程序将获得getFallback()的返回

        2. run()/construct()运行超时：使用无限while循环或sleep模拟超时，触发了getFallback()

        3. 熔断器启动：我们配置10s内请求数大于3个时就启动熔断器，请求错误率大于80%时就熔断，然后for循环发起请求，
            当请求符合熔断条件时将触发getFallback()。更多熔断策略见下文

        4. 线程池/信号量已满：我们配置线程池数目为3，然后先用一个for循环执行queue()，触发的run()sleep 2s，
            然后再用第2个for循环执行execute()，发现所有execute()都触发了fallback，这是因为第1个for的线程还在sleep，
            占用着线程池所有线程，导致第2个for的所有命令都无法获取到线程

       调用程序可以通过isResponseFromFallback()查询结果是由run()/construct()还是getFallback()/resumeWithFallback()返回的
     */
    @Override
    protected String getFallback() {
        //当外部请求超时后，会执行fallback里的业务逻辑
        System.out.println("执行了回退方法");
        return "error";
    }


    @Override
    protected String run() throws InterruptedException {
        //模拟外部请求需要的时间长度
        System.out.println("执行了run方法");
        Thread.sleep(2000);
        return "sucess";

    }
}