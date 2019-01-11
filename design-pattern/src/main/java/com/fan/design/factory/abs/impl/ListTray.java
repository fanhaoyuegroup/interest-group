package com.fan.design.factory.abs.impl;


import com.fan.design.factory.abs.compment.Item;
import com.fan.design.factory.abs.compment.Tray;

import java.util.Iterator;

public class ListTray extends Tray {
    public ListTray(String caption) {
        super(caption);
    }

    @Override
    public String makeHTML() {
        StringBuilder builder = new StringBuilder();
        builder.append("<li>\n");
        builder.append(caption+"\n");
        builder.append("<ul>\n");
        Iterator iterator = trays.iterator();
        while(iterator.hasNext()){
            Item item = (Item) iterator.next();
            builder.append(item.makeHTML());
        }
        builder.append("</ul>\n");
        builder.append("</li>\n");
        return builder.toString();
    }
}
