package com.presell.dubbo.spi.dubbo.spi;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

@SPI("world")
public interface Test {
    void sayHello(String name, String value);
}
