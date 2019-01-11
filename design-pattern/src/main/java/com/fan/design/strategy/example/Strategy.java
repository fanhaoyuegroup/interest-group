package com.fan.design.strategy.example;

public interface Strategy {
    /**
     * nextHand
     *
     * @return Hand 下一局手势
     */
    Hand nextHand();

    /**
     * 学习策略
     *
     * @param win 这局是否赢了参数
     */
    void study(boolean win);
}
