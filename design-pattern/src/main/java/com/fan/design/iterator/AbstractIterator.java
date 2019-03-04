/*
 * Copyright (C) 2009-2017 Hangzhou 2Dfire Technology Co., Ltd.All rights reserved
 */
package com.fan.design.iterator;

/**
 * AbstractIterator
 *
 * @author lilu
 * @since 2019-03-04
 * 抽象迭代器
 */
public interface AbstractIterator {
    /**
     * 移至下一个元素
     */
    boolean hasNext();

    /**
     * 获取下一个元素
     *
     * @return
     */
    Object getNextItem();
}
