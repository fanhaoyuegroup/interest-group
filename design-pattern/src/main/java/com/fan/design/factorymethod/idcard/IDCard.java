package com.fan.design.factorymethod.idcard;


import com.fan.design.factorymethod.framework.Product;

public class IDCard extends Product {
    private String owner;

     IDCard(String owner) {
        System.out.println("制作"+owner+"的id card");
        this.owner = owner;
    }

    @Override
    public void user() {
        System.out.println("使用"+owner+"的id card");
    }

    public String getOwner() {
        return owner;
    }
}
