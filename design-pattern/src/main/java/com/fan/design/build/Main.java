package com.fan.design.build;

public class Main {
    public static void main(String[] args) {
        房间 room = new 房间();
        System.out.println(room);
        Builder builder = new 房间Builder(room);
        //这里我的操作是一次性做了初始化，实际上可以在中间加逻辑然后做下一次初始化操作
        room = builder.build主卧().build主卧卫生间().build卫生间().build客厅().build次卧().build();
        System.out.println(room);
    }
}
