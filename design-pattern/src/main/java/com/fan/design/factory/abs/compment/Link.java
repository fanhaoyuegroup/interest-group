package com.fan.design.factory.abs.compment;

public abstract class Link extends Item {
    protected String url;
    public Link(String caption,String url){
        this.caption = caption;
        this.url = url;
    }
}
