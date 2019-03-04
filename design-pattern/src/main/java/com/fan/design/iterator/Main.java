/*
 * Copyright (C) 2009-2017 Hangzhou 2Dfire Technology Co., Ltd.All rights reserved
 */
package com.fan.design.iterator;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Main
 *
 * @author lilu
 * @since 2019-03-04
 */
public class Main {
    public static void main(String[] args){
        List lipstick = Lists.newArrayList();
        lipstick.add("DIOR");
        lipstick.add("MAC");
        lipstick.add("YSL");
        lipstick.add("ARMANI");
        lipstick.add("TOM FORD");
        LipstickList lipstickList = new LipstickList(lipstick);

        AbstractIterator iterator = lipstickList.createIterator();
        while (iterator.hasNext()){
            System.out.println(iterator.getNextItem()+"\n");
        }

        FoodList foodList = new FoodList(lipstick);

        AbstractIterator foodIterator = foodList.createIterator();
        while (foodIterator.hasNext()){
            System.out.println(foodIterator.getNextItem()+"\n");
        }
    }
}