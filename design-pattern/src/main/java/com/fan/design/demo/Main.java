package com.fan.design.demo;

/**
 * Date: 2018/12/19
 * Description:
 *
 * @author lihao
 */
public class Main {
    public static void main(String[] args) {
        AbstractPresellTemplate presellTemplateA = new ActivityA();
        AbstractPresellTemplate presellTemplateB = new ActivityB();
        //presellTemplateA.execute();
        presellTemplateB.execute();
    }
}
