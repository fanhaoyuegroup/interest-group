package com.fan.design.factory.abs.compment;


import java.util.ArrayList;

public abstract class Tray extends Item{
    protected ArrayList trays = new ArrayList();
    public Tray(String caption){
        this.caption = caption;
    }
}
