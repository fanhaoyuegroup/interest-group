package com.fan.design.build;

/**
 * Date: 2019/3/19
 * Description:
 *
 * @author lihao
 */
public abstract class BuilderB {
    protected Product product = new Product();

    public abstract BuilderB buildPartA();
    public abstract BuilderB buildPartB();
    public abstract BuilderB buildPartC();
    public Product build(){
        return product;
    }
}
