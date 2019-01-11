package com.fan.design.adapter;

/**
 * @program interest-group
 * @description:
 * @author: wangtao.lihao
 * @create: 2018/11/23 15:58
 */
public class PlugAdapter extends DoublePlugImpl implements MutipartPlug{



    public PlugAdapter(){
        System.out.println("转接头转接双相接头...");
    }

    @Override
    public void useThridPlug() {
        System.out.println("使用转换头充电...");
    }
}
