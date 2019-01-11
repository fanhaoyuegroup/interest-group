/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.node;

import main.java.fan.zk.connect.ZkConnectSessionWatcher;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * ZkNodeCreateOperator
 * 同步或异步去创建zk节点。
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-21
 */
public class ZkNodeCreateOperator implements Watcher {

    private static final Logger logger = LoggerFactory.getLogger(ZkConnectSessionWatcher.class);

    // 集群模式则是多个ip
    // private static final String zkServerIps = "192.168.190.128:2181,192.168.190.129:2181,192.168.190.130:2181";
    /**
     * 单机模式是一个ip
     */
    private static final String zkServerIp = "127.0.0.1:2181";
    // 超时时间
    private static final Integer timeout = 5000;
    private ZooKeeper zooKeeper;

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public ZkNodeCreateOperator() {
    }

    public ZkNodeCreateOperator(String connectStr) {
        try {
            // 在使用该构造器的时候，实例化zk客户端对象
            zooKeeper = new ZooKeeper(connectStr, timeout, new ZkNodeCreateOperator());
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (zooKeeper != null) {
                    zooKeeper.close();
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Watch事件通知方法
     *
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("事件通知：" + watchedEvent);
        logger.warn("接收到watch通知：{}", watchedEvent);
    }


    /**
     * 创建 zk 接待你
     *
     * 同步或者异步创建节点，都不支持子节点的递归创建，异步有一个callback函数
     * 参数：
     * path：节点创建的路径
     * data：节点所存储的数据的byte[]
     * acl：控制权限策略
     *          Ids.OPEN_ACL_UNSAFE --> world:anyone:cdrwa
     *          CREATOR_ALL_ACL --> auth:user:password:cdrwa
     * createMode：节点类型, 是一个枚举
     *          PERSISTENT：持久节点
     *          PERSISTENT_SEQUENTIAL：持久顺序节点
     *          EPHEMERAL：临时节点
     *          EPHEMERAL_SEQUENTIAL：临时顺序节点
     */
    public void createZKNode(String path, byte[] data, List<ACL> acls) {
        String result = "";
        try {

            // todo: 同步创建zk节点，节点类型为临时节点,一旦客户端断开就会被删除
            result = zooKeeper.create(path, data, acls, CreateMode.EPHEMERAL);
            System.out.println("创建节点：\t" + result + "\t成功...");
            Thread.sleep(2000);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * ZKOperatorDemo.java
     * 创建zk节点
     */
    public void asyncCreateZKNode(String path, byte[] data, List<ACL> acls) {
        try {
            // todo: 异步步创建zk节点，节点类型为持久节点
            String ctx = "{'create':'success'}";
            zooKeeper.create(path, data, acls, CreateMode.PERSISTENT, new CreateCallBack(), ctx);

            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ZkNodeCreateOperator zkServer = new ZkNodeCreateOperator(zkServerIp);

        // 同步 创建zk节点
        // zkServer.createZKNode("/testNode", "testNode-data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);
        // 异步
        zkServer.asyncCreateZKNode("/testNode", "testNode-data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);

        Thread.sleep(100000L);
    }
}
