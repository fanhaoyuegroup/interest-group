/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.node;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ZkNodeDeleteOperator
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-21
 */
public class ZkNodeDeleteOperator implements Watcher {

    private static final Logger logger = LoggerFactory.getLogger(ZkNodeDeleteOperator.class);

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
        logger.warn("接收到watch通知：{}", watchedEvent);
    }

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper(zkServerIp, timeout, new ZkNodeDeleteOperator());
        // 创建节点
        zooKeeper.create("/testDeleteNode", "test-delete-data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        Thread.sleep(1000);
        /*
         * 删除节点（同步）
         * 参数：
         * path：需要删除的节点路径
         * version：数据版本
         */
        zooKeeper.delete("/testDeleteNode", 0);

        zooKeeper.close();
    }
}

