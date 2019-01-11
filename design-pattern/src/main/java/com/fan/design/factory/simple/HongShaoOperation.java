package com.fan.design.factory.simple;

public class HongShaoOperation extends FishTypeOperation {
    @Override
    public void typeOperation() {
        System.out.println(fish.getName()+"红烧做法");
    }
}
