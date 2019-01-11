package com.fan.design.demo;

/**
 * Date: 2018/12/19
 * Description:
 *
 * @author lihao
 */
public class ActivityA extends AbstractPresellTemplate {

    @Override
    public void abstractMethod() {
        System.out.println("活动A业务");
        System.out.println("--------------");
    }

    @Override
    public void hookMethod() {
        System.out.println("活动A重写钩子方法");
        System.out.println("--------------");
    }
}
