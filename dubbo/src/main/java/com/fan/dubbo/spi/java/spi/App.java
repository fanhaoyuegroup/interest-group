/*
 * Copyright (C) 2009-2018 Hangzhou FanDianEr Technology Co., Ltd. All rights reserved
 */
package com.presell.dubbo.spi.java.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * App
 *
 * @author huaifeng
 * @since 2018-11-22
 */
public class App {
    public static void main(String[] args) {
        ServiceLoader<TestService> services = ServiceLoader.load(TestService.class);
        //获取迭代器
        Iterator<TestService> driversIterator = services.iterator();
        //遍历所有的驱动实现
        while(driversIterator.hasNext()) {
            driversIterator.next().sayHello();
        }
    }
}
