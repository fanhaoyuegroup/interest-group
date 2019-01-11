/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.node;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * ZkGetChildrenList
 * zookeeper 获取子节点数据
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-21
 */
public class ZkGetChildrenList implements Watcher {

    // 集群模式则是多个ip
    // private static final String zkServerIps = "192.168.190.128:2181,192.168.190.129:2181,192.168.190.130:2181";
    /**
     * 单机模式是一个ip
     */
    private static final String zkServerIp = "127.0.0.1:2181";
    // 超时时间
    private static final Integer timeout = 5000;
    private static ZooKeeper zooKeeper;

    /**
     * Watch事件通知方法
     *
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
    }

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper(zkServerIp, timeout, new ZkGetChildrenList());

        /*
         * 参数：
         * path：父节点路径
         * watch：true或者false，注册一个watch事件
         */
        List<String> strChildList = zooKeeper.getChildren("/testNode", false);
        for (String s : strChildList) {
            System.out.println(s);
        }
    }
}