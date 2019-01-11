package com.fan.design.factory.abs.compment;

public abstract class Item {
    protected String caption;
    public  Item(String caption){
        this.caption = caption;
    }
    public abstract String makeHTML();
    public Item(){

    }
}
