/*
 * Copyright (C) 2009-2017 Hangzhou 2Dfire Technology Co., Ltd.All rights reserved
 */
package com.fan.design.iterator;

import java.util.List;

/**
 * FoodList
 *
 * @author lilu
 * @since 2019-03-04
 */
public class FoodList extends AbstractMyList {
    public FoodList(List objects) {
        super(objects);
    }

    @Override
    public AbstractIterator createIterator() {
        return new FoodIterator();
    }

    private class FoodIterator implements AbstractIterator {
        /**
         * 游标
         */
        private int cursor;

        @Override
        public boolean hasNext() {
            return cursor < objects.size() ? true : false;
        }

        @Override
        public Object getNextItem() {
            return objects.get(cursor++);
        }
    }
}