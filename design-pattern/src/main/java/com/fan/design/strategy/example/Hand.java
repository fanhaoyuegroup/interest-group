package com.fan.design.strategy.example;

/**
 * @program interest-group
 * @description:
 * @author: wangtao.lihao
 * @create: 2018/11/16 11:30
 */
public class Hand {
    //石头
    public static final int HANDVALUE_GUU = 0;
    //剪刀
    public static final int HANDVALUE_CHO = 1;
    //布
    public static final int HANDVALUE_PAA = 2;

    public static final Hand[] hands = {
            new Hand(HANDVALUE_GUU),
            new Hand(HANDVALUE_CHO),
            new Hand(HANDVALUE_PAA)
    };

    private static final String[] names = {
      "石头","剪刀","布"
    };

    private int handValue;

    private Hand(int handValue){
        this.handValue = handValue;
    }

    public static Hand getHand(int handValue){
        return hands[handValue];
    }

    public boolean isStrongerThan(Hand hand){
        return false;
    }

    public boolean isWeakerThan(Hand hand){
        return false;
    }

    private int fight(Hand hand){
        if(this == hand){
            return 0;
        }else if((this.handValue+1)%3 == hand.handValue){
            return 1;
        }else{
            return -1;
        }
    }
    @Override
    public String toString(){
        return names[handValue];
    }
}
