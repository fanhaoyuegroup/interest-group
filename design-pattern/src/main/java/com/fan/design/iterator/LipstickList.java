/*
 * Copyright (C) 2009-2017 Hangzhou 2Dfire Technology Co., Ltd.All rights reserved
 */
package com.fan.design.iterator;

import java.util.List;

/**
 * LipstickList
 *
 * @author lilu
 * @since 2019-03-04
 * 具体聚合类
 */
public class LipstickList extends AbstractMyList {
    public LipstickList(List objects) {
        super(objects);
    }

    @Override
    public AbstractIterator createIterator() {
        return new LipstickIterator(this);
    }
}