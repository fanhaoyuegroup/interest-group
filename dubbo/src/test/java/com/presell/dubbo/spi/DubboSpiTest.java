/*
 * Copyright (C) 2009-2018 Hangzhou FanDianEr Technology Co., Ltd. All rights reserved
 */
package com.presell.dubbo.spi;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

/**
 * DubboSpiTest
 *
 * @author huaifeng
 * @since 2018-11-29
 */
public class DubboSpiTest {
    @Test
    public void test1(){
        ExtensionLoader<com.presell.dubbo.spi.dubbo.spi.Test> loader = ExtensionLoader.getExtensionLoader(com.presell.dubbo.spi.dubbo.spi.Test.class);
        com.presell.dubbo.spi.dubbo.spi.Test adaptiveExtension = loader.getDefaultExtension();
        //URL url = URL.valueOf("test://localhost/world");
        adaptiveExtension.sayHello("test", "111");
    }
}
