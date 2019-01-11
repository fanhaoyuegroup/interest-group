/*
 * Copyright (C) 2009-2018 Hangzhou FanDianEr Technology Co., Ltd. All rights reserved
 */
package com.fan.design.factory.method;
/**
 * DatabaseLogger
 *
 * @author lanxiang
 * @since 2019-01-02
 */
public class DatabaseLogger implements Logger {
    @Override
    public void writeLog() {
        System.out.println("database log...");
    }
}
