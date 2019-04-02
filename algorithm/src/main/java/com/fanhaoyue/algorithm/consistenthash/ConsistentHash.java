/*
 * Copyright (C) 2009-2016 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package com.fanhaoyue.algorithm.consistenthash;

import java.util.*;

/**
 * ConsistentHash
 *
 * @author zhutou
 * @since 2019-04-01
 */
public class ConsistentHash<T> {
    /**
     * 节点的复制因子,实际节点个数 * numberOfReplicas =
     */
    private final int numberOfReplicas;
    /**
     * 虚拟节点个数  存储虚拟节点的hash值到真实节点的映射
     */
    private final SortedMap<Integer, T> circle = new TreeMap();

    private ConsistentHash(int numberOfReplicas,
                           Collection<T> nodes) {
        this.numberOfReplicas = numberOfReplicas;
        for (T node : nodes) {
            add(node);
        }
    }

    private void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            // 对于一个实际机器节点 node, 对应 numberOfReplicas 个虚拟节点
            //不同的虚拟节点(i不同)有不同的hash值,但都对应同一个实际机器node
            //虚拟node一般是均衡分布在环上的,数据存储在顺时针方向的虚拟node上
            String nodestr = node.toString() + i;
            int hashcode = nodestr.hashCode();
            System.out.println("hashcode:" + hashcode);
            circle.put(hashcode, node);

        }
    }

    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove((node.toString() + i).hashCode());
        }
    }


    /**
     * 获得一个最近的顺时针节点,根据给定的key 取Hash 然后再取得顺时针方向上最近的一个虚拟节点对应的实际节点 再从实际节点中取得 数据
     *
     * @param key key
     * @return T
     */
    private T get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
        // node 用String来表示,获得node在哈希环中的hashCode
        int hash = key.hashCode();
        System.out.println("hashcode----->:" + hash);
        if (!circle.containsKey(hash)) {
            //数据映射在两台虚拟机器所在环之间,就需要按顺时针方向寻找机器
            SortedMap<Integer, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    private long getSize() {
        return circle.size();
    }

    /**
     * 查看表示整个哈希环中各个虚拟节点位置
     */
    private void testBalance() {
        //获得TreeMap中所有的Key
        Set<Integer> sets = circle.keySet();
        //将获得的Key集合排序
        SortedSet<Integer> sortedSets = new TreeSet(sets);
        for (Integer hashCode : sortedSets) {
            System.out.println(hashCode);
        }

        System.out.println("----each location 's distance are follows: ----");

        //查看相邻两个hashCode的差值
        Iterator<Integer> it = sortedSets.iterator();
        Iterator<Integer> it2 = sortedSets.iterator();
        if (it2.hasNext()) {
            it2.next();
        }
        long keyPre, keyAfter;
        while (it.hasNext() && it2.hasNext()) {
            keyPre = it.next();
            keyAfter = it2.next();
            System.out.println(keyAfter - keyPre);
        }
    }

    public static void main(String[] args) {
        Set<String> nodes = new HashSet();
        nodes.add("A");
        nodes.add("B");
        nodes.add("C");

        ConsistentHash<String> consistentHash = new ConsistentHash(2, nodes);
        consistentHash.add("D");

        System.out.println("hash circle size: " + consistentHash.getSize());
        System.out.println("location of each node are follows: ");
        consistentHash.testBalance();

        String node = consistentHash.get("apple");
        System.out.println("node----------->:" + node);
    }
}
