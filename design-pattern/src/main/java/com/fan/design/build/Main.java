package com.fan.design.build;

/**
 * Date: 2019/3/19
 * Description:
 *
 * @author lihao
 */
public class Main {
    public static void main(String[] args) {
        Director director = new Director(new ConcreteBuilderA());
        System.out.println("方式1："+director.construct());

        Product product = new ConcreteBuilderB().buildPartA().buildPartB().buildPartC().build();
        System.out.println("方式2："+product);
    }
}
