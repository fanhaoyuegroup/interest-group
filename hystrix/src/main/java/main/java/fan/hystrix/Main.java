/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.hystrix;

/**
 * Main
 *
 * @author luobosi@2dfire.com
 * @since 2018-10-04
 */
public class Main {


    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 30; i++) {
            // todo：每个 Command 对象只能调用一次，不可以重复调用，
            // 重复调用对应异常信息：This instance can only be executed once. Please instantiate a new instance.
            HystrixCommandDemo command = new HystrixCommandDemo();
            String result = command.execute();
            System.out.println(result);
            System.out.println("circuit Breaker is open : " + command.isCircuitBreakerOpen());
            if (command.isCircuitBreakerOpen()) {
                Thread.sleep(50);
            }
        }


    }
}
