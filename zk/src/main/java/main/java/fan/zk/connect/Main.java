/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.connect;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-27
 */
public class Main {

    public static void main(String[] args) {
        User user1 = new User(1L, "marvel");
        User user2 = new User(2L, "tanxuan");
        User user3 = new User(3L, "luobosi");
        User user4 = new User(4L, "qiezi");
        User user5 = new User(1L, "marvel");

        List<User> list1 = new ArrayList<>();
        List<User> list2 = new ArrayList<>();
        List<User> list3 = new ArrayList<>();

        list1.add(user1);
        list1.add(user2);
        list1.add(user3);

        list2.add(user4);
        list2.add(user5);

        list3.addAll(list1);
        list3.addAll(list2);

        list3 = list3.stream().distinct().collect(Collectors.toList());


        list3.forEach(System.out::println);

    }
}
