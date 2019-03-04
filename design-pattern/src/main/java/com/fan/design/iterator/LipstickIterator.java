/*
 * Copyright (C) 2009-2017 Hangzhou 2Dfire Technology Co., Ltd.All rights reserved
 */
package com.fan.design.iterator;

import java.util.List;

/**
 * LipstickIterator
 *
 * @author lilu
 * @since 2019-03-04
 * 具体迭代器
 */
public class LipstickIterator implements AbstractIterator {
    private LipstickList lipstickList;
    private List lipsticks;
    /**
     * 游标
     */
    private int cursor;

    public LipstickIterator(LipstickList lipstickList) {
        this.lipstickList = lipstickList;
        this.lipsticks = lipstickList.get();
        cursor = 0;

    }

    @Override
    public boolean hasNext() {
        return cursor < lipsticks.size() ? true : false;
    }

    @Override
    public Object getNextItem() {
        return lipsticks.get(cursor++);
    }

}