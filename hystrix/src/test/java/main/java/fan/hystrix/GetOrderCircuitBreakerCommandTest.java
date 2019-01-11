/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.hystrix;

import com.netflix.hystrix.*;
import org.junit.Test;

import java.util.Random;

/**
 * GetOrderCircuitBreakerCommandTest
 * 模拟 10 次调用，错误百分比为 5% 的情况下，打开熔断器开关
 *
 * @author luobosi@2dfire.com
 * @since 2018-10-07
 */
public class GetOrderCircuitBreakerCommandTest extends HystrixCommand<String> {

    public GetOrderCircuitBreakerCommandTest(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ThreadPoolTestGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("testCommandKey"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(name))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                // 是否启用熔断器，默认是TURE。
                                .withCircuitBreakerEnabled(true)
                                // 熔断器强制打开，始终保持打开状态。默认值FLASE。
                                .withCircuitBreakerForceOpen(false)
                                // 熔断器强制关闭，始终保持关闭状态。默认值FLASE。
                                .withCircuitBreakerForceClosed(false)
                                /*
                                    设定错误百分比，默认值50%，
                                    例如一段时间（10s）内有100个请求，其中有55个超时或者异常返回了，那么这段时间内的错误百分比是55%，
                                    大于了默认值50%，这种情况下触发熔断器-打开。这里设置成 5%
                                 */
                                .withCircuitBreakerErrorThresholdPercentage(5)
                                /*
                                    默认值20.意思是至少有20个请求才进行errorThresholdPercentage错误百分比计算。
                                    比如一段时间（10s）内有19个请求全部失败了。错误百分比是100%，但熔断器不会打开，
                                    因为requestVolumeThreshold的值是20. 这个参数非常重要，熔断器是否打开首先要满足这个条件
                                 */
                                .withCircuitBreakerRequestVolumeThreshold(10)
                                // 隔5s之后，熔断器会尝试半开(关闭)，重新放进来请求
                                .withCircuitBreakerSleepWindowInMilliseconds(5000)
                        // .withExecutionTimeoutInMilliseconds(1000) run 方法的超时时间

                )
        );
    }


    @Override
    protected String run() throws Exception {
        Random rand = new Random();
        //模拟错误百分比(方式比较粗鲁但可以证明问题)
        if (1 == rand.nextInt(2)) {
            throw new Exception("make exception");
        }
        return "running:  ";
    }

    @Override
    protected String getFallback() {
        return "fallback: ";
    }


    public static class UnitTest {

        @Test
        public void testCircuitBreaker() throws Exception {
            for (int i = 0; i < 25; i++) {
                Thread.sleep(500);
                HystrixCommand<String> command = new GetOrderCircuitBreakerCommandTest("testCircuitBreaker");
                String result = command.execute();
                //本例子中从第11次，熔断器开始打开
                System.out.println("call times:" + (i + 1) + "   result:" + result + " isCircuitBreakerOpen: " + command.isCircuitBreakerOpen());
                //本例子中5s以后，熔断器尝试关闭，放开新的请求进来
            }
        }
    }

}
