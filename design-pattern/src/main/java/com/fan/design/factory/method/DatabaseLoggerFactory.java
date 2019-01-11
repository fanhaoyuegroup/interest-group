/*
 * Copyright (C) 2009-2018 Hangzhou FanDianEr Technology Co., Ltd. All rights reserved
 */
package com.fan.design.factory.method;
/**
 * DatabaseLoggerFactory
 *
 * @author lanxiang
 * @since 2019-01-02
 */
public class DatabaseLoggerFactory implements LoggerFactory {
    @Override
    public Logger createLogger() {
        return new DatabaseLogger();
    }
}
