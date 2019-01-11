/*
 * Copyright (C) 2009-2018 Hangzhou FanDianEr Technology Co., Ltd. All rights reserved
 */
package com.fan.design.factory.method;
/**
 * LoggerFactory
 *
 * @author lanxiang
 * @since 2019-01-02
 */
public interface LoggerFactory {

    /**
     * 初始化日志对象
     *
     * @return 日志对象
     */
    Logger createLogger();
}
