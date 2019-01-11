package com.fan.design.adapter;

/**
 * @program interest-group
 * @description:
 * @author: wangtao.lihao
 * @create: 2018/11/23 16:12
 */
public class DoublePlugImpl implements DoublePlug {

    public DoublePlugImpl(){
        System.out.println("这是一个双相接头");
    }

    @Override
    public void useDoublePlug() {
        System.out.println("使用双向插头接口充电...");
    }
}
