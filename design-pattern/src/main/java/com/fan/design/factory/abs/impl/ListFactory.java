package com.fan.design.factory.abs.impl;


import com.fan.design.factory.abs.compment.Factory;
import com.fan.design.factory.abs.compment.Link;
import com.fan.design.factory.abs.compment.Page;
import com.fan.design.factory.abs.compment.Tray;

public class ListFactory extends Factory {
    @Override
    public Link createLink(String caption, String url) {
        return new ListLink(caption,url);
    }

    @Override
    public Tray createTray(String caption) {
        return new ListTray(caption);
    }

    @Override
    public Page createPage(String caption, String author) {
        return new ListPage(caption,author);
    }
}
