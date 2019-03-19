package com.fan.design.build;

/**
 * Date: 2019/3/19
 * Description:
 *
 * @author lihao
 */
public abstract class BuilderA {
    protected Product product = new Product();

    public abstract void buildPartA();
    public abstract void buildPartB();
    public abstract void buildPartC();
    public Product getProduct(){
        return product;
    }
}
