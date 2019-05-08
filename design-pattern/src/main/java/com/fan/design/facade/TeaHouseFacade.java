/*
 * Copyright (C) 2009-2017 Hangzhou 2Dfire Technology Co., Ltd.All rights reserved
 */
package com.fan.design.facade;

/**
 * TeaHouseFacade
 *
 * @author lilu
 * @since 2019-05-07
 */
public class TeaHouseFacade {
    private TeaService teaService;
    private TeaSetService teaSetService;
    private WaterService waterService;

    public TeaHouseFacade(){
        teaService = new TeaService();
        teaSetService = new TeaSetService();
        waterService = new WaterService();
    }

    public void getTea(){
        teaSetService.readyTeaSet();
        waterService.readyWater();
        teaService.readyTea();
    }
}