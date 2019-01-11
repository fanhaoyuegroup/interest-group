package com.fan.design.strategy.example;

import java.util.Random;

/**
 * @program interest-group
 * @description:
 * @author: wangtao.lihao
 * @create: 2018/11/16 13:53
 */

/**
 * WinningStrategy
 *
 * 策略：如果这局赢了则下局继续出这个手势，输了就随机出一个
 */
public class WinningStrategy implements Strategy {

    private Random random;
    private boolean won = false;
    private Hand preHand;

    public WinningStrategy(int seed){
        random = new Random(seed);
    }

    @Override
    public Hand nextHand(){
        //如果没有赢，则下一局随机一个0 1 2
        if(!won){
            preHand = Hand.getHand(random.nextInt(3));
        }
        //返回之前的手势
        return preHand;
    }

    @Override
    public void study(boolean win) {
        won = win;
    }
}
