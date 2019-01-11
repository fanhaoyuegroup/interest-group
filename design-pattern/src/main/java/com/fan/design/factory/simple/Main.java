package com.fan.design.factory.simple;

public class Main {
    public static void main(String[] args) {
        QingZhengOperation qingZhengOperation = new QingZhengOperation();
        Fish fish = new Fish();
        fish.setName("草鱼");
        qingZhengOperation.fish = fish;
        FishTypeOperation fishTypeOperation = SimpleFactory.createOperation(qingZhengOperation);
        fishTypeOperation.typeOperation();
    }
}
