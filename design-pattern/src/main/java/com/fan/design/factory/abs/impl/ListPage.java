package com.fan.design.factory.abs.impl;


import com.fan.design.factory.abs.compment.Item;
import com.fan.design.factory.abs.compment.Page;

import java.util.Iterator;

public class ListPage extends Page {
    public ListPage(String title, String author) {
        super(title, author);
    }

    @Override
    public String makeHTML() {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><head><title>"+title+"</title></head>\n");
        builder.append("<body>\n");
        builder.append("<h1>"+title+"</h1>\n");
        builder.append("<ul>\n");
        Iterator iterator = content.iterator();
        while(iterator.hasNext()){
            Item item = (Item)iterator.next();
            builder.append(item.makeHTML());
        }
        builder.append("</ul>\n");
        builder.append("<hr><address>"+author+"</address>");
        builder.append("</body></html>\n");
        return builder.toString();
    }
}
