/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.hystrix;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 * Main1
 *
 * @author luobosi@2dfire.com
 * @since 2018-10-11
 */
public class Main1 {


    public static void main(String[] args) {
        LocalDate today = LocalDate.now().plusDays(30);
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime.plusDays(12);
        System.out.println(localDateTime);
        System.out.println(today);
        //本月的第一天
        LocalDate firstday = LocalDate.of(today.getYear(),today.getMonth(),1);
        //本月的最后一天
        LocalDate lastDay =today.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("本月的第一天"+firstday);
        System.out.println("本月的最后一天"+lastDay.getDayOfMonth());
    }
}
