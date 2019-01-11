/*
 * Copyright (C) 2009-2018 Hangzhou FanDianEr Technology Co., Ltd. All rights reserved
 */
package com.presell.dubbo.spi.java.spi.impl;

import com.presell.dubbo.spi.java.spi.TestService;

/**
 * PresellService
 *
 * @author huaifeng
 * @since 2018-11-22
 */
public class HelloService implements TestService {
    @Override
    public void sayHello() {
        System.out.println("test hello");
    }
}
