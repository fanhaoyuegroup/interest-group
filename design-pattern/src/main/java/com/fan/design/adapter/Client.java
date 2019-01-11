package com.fan.design.adapter;

/**
 * @program interest-group
 * @description:
 * @author: wangtao.lihao
 * @create: 2018/11/23 16:17
 */
public class Client {

    public static void main(String[] args) {
        MutipartPlug mutipartPlug = new PlugAdapter();
        mutipartPlug.useThridPlug();
    }
}
