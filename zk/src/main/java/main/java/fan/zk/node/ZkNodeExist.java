/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.node;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * ZkNodeExist
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-21
 */
public class ZkNodeExist implements Watcher {

// 集群模式则是多个ip
    // private static final String zkServerIps = "192.168.190.128:2181,192.168.190.129:2181,192.168.190.130:2181";
    /**
     * 单机模式是一个ip
     */
    private static final String zkServerIp = "127.0.0.1:2181";
    // 超时时间
    private static final Integer timeout = 5000;
    private static ZooKeeper zooKeeper;

    @Override
    public void process(WatchedEvent watchedEvent) {
    }

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper(zkServerIp, timeout, new ZkNodeExist());

        /*
         * 参数：
         * path：节点路径
         * watch：true或者false，注册一个watch事件
         */
        Stat stat = zooKeeper.exists("/testNode", true);
        if (stat != null) {
            System.out.println("testNode 节点存在...");
            System.out.println("该节点的数据版本为：" + stat.getVersion());
        } else {
            System.out.println("该节点不存在...");
        }
    }
}
