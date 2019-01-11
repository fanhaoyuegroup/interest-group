package com.fan.design.strategy.example;

import java.util.Random;

/**
 * @program interest-group
 * @description:
 * @author: wangtao.lihao
 * @create: 2018/11/16 14:07
 */
public class ProbStrategy implements Strategy {
    private Random random;
    private int prevHandValue = 0;
    private int currentHandValue = 0;
    private int[][] history = {
            {1,1,1},
            {1,1,1},
            {1,1,1}
    };

    public ProbStrategy(int seed){
        random = new Random(seed);
    }

    @Override
    public Hand nextHand() {
        //1.获取currentHandValue的总和
        //2.然后随机0-sum中一个值
        //3.根据bet随机值得到handValue
        int bet = random.nextInt(getSum(currentHandValue));
        int handValue = 0;
        if(bet < history[currentHandValue][0]){
            handValue = 0;
        }else if(bet < history[currentHandValue][0] + history[currentHandValue][1]){
            handValue = 1;
        }else{
            handValue = 2;
        }
        prevHandValue = currentHandValue;
        currentHandValue = handValue;
        //获取下一局手势
        return Hand.getHand(handValue);
    }

    /**
     * 获取总和 getSum
     *
     */
    private int getSum(int hv){
        int sum = 0;
        for(int i=0;i<3;i++){
            sum += history[hv][i];
        }
        return sum;
    }

    @Override
    public void study(boolean win) {
        if(win){
            history[prevHandValue][currentHandValue]++;
        }else{
            history[prevHandValue][(currentHandValue+1)%3]++;
            history[prevHandValue][(currentHandValue+2)%3]++;
        }
    }
}
