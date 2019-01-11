/*
 * Copyright (C) 2009-2018 Hangzhou FanDianEr Technology Co., Ltd. All rights reserved
 */
package com.presell.dubbo.spi.dubbo.spi;

import com.alibaba.dubbo.common.URL;

/**
 * TestWorld
 *
 * @author huaifeng
 * @since 2018-11-29
 */
public class TestSpring implements Test{
    @Override
    public void sayHello(String name, String value) {
        System.out.println("this is spring:"+name+":"+value);
    }
}
