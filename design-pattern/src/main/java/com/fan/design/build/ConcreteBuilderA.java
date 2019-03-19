package com.fan.design.build;

/**
 * Date: 2019/3/19
 * Description:
 *
 * @author lihao
 */
public class ConcreteBuilderA extends BuilderA {

    @Override
    public void buildPartA() {
        System.out.println("set a ...");
        product.setPartA("set a");
    }

    @Override
    public void buildPartB() {
        System.out.println("set b ...");
        product.setPartB("set b");
    }

    @Override
    public void buildPartC() {
        System.out.println("set c ...");
        product.setPartC("set c");
    }

}
