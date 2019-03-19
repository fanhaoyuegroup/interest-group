package com.fan.design.build;

/**
 * Date: 2019/3/19
 * Description:
 *
 * @author lihao
 */
public class ConcreteBuilderB extends BuilderB {

    @Override
    public BuilderB buildPartA() {
        System.out.println("set a ...");
        product.setPartA("set a");
        return this;
    }

    @Override
    public BuilderB buildPartB() {
        System.out.println("set b ...");
        product.setPartB("set b");
        return this;
    }

    @Override
    public BuilderB buildPartC() {
        System.out.println("set c ...");
        product.setPartC("set c");
        return this;
    }
}
