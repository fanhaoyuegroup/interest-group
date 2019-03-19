package com.fan.design.build;

/**
 * Date: 2019/3/19
 * Description:
 *
 * @author lihao
 */
public class Director {
    private BuilderA builder;
    public Director(BuilderA builder){
        this.builder = builder;
    }

    public Product construct(){
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
        return builder.getProduct();
    }
}
