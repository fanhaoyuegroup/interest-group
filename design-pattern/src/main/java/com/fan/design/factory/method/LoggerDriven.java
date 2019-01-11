/*
 * Copyright (C) 2009-2018 Hangzhou FanDianEr Technology Co., Ltd. All rights reserved
 */
package com.fan.design.factory.method;
/**
 * LoggerDriven
 *
 * @author lanxiang
 * @since 2019-01-02
 */
public class LoggerDriven {
    public static void main(String[] args) {
        Logger logger;
        LoggerFactory loggerFactory;

        /*loggerFactory = new DatabaseLoggerFactory();
        logger = loggerFactory.createLogger();*/

        loggerFactory = new FileLoggerFactory();
        logger = loggerFactory.createLogger();
        logger.writeLog();
    }
}
