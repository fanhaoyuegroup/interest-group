/*
 * Copyright (C) 2009-2018 Hangzhou FanDianEr Technology Co., Ltd. All rights reserved
 */
package com.fan.design.factory.method;
/**
 * FileLogger
 *
 * @author lanxiang
 * @since 2019-01-02
 */
public class FileLogger implements Logger{
    @Override
    public void writeLog() {
        System.out.println("file log...");
    }
}
