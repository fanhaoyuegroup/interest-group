/*
 * Copyright (C) 2009-2017 Hangzhou 2Dfire Technology Co., Ltd.All rights reserved
 */
package com.fan.design.facade;

/**
 * ClientService
 *
 * @author lilu
 * @since 2019-05-07
 */
public class ClientService {
    public static void main(String[] args){
        TeaHouseFacade teaHouseFacade = new TeaHouseFacade();
        teaHouseFacade.getTea();
    }
}