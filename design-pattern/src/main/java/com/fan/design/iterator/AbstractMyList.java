/*
 * Copyright (C) 2009-2017 Hangzhou 2Dfire Technology Co., Ltd.All rights reserved
 */
package com.fan.design.iterator;

import java.util.List;

/**
 * AbstractMyList
 *
 * @author lilu
 * @since 2019-03-04
 * 抽象聚合类
 */
public abstract class AbstractMyList {
    protected List<Object> objects;

    public abstract AbstractIterator createIterator();

    public AbstractMyList(List objects) {
        this.objects = objects;
    }

    public void add(Object object) {
        this.objects.add(object);
    }

    public void remove(Object object) {
        this.objects.remove(object);
    }

    public List get() {
        return this.objects;
    }
}