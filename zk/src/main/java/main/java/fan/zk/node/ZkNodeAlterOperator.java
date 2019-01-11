/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.node;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * ZkNodeAlterOperator
 * 同步/异步修改zk节点数据
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-21
 */
public class ZkNodeAlterOperator implements Watcher {

    private static final Logger logger = LoggerFactory.getLogger(ZkNodeAlterOperator.class);


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

    public ZkNodeAlterOperator() {
    }

    public ZkNodeAlterOperator(String connectStr) {
        try {
            // 在使用该构造器的时候，实例化zk客户端对象
            zooKeeper = new ZooKeeper(connectStr, timeout, new ZkNodeAlterOperator());
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
        logger.warn("接收到watch通知：{}", watchedEvent);
    }

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZkNodeAlterOperator zkServer = new ZkNodeAlterOperator(zkServerIp);

        /*
         * 修改zk节点数据（同步）
         * 参数：
         * path：节点路径
         * data：新数据
         * version 数据版本
         */
        Stat status = zkServer.getZooKeeper().setData("/testNode", "this is new data".getBytes(), 0);
        // 通过Stat对象可以获取znode所有的状态属性，这里以version为例
        System.out.println("修改成功，当前数据版本为：" + status.getVersion());



        /*
         * 修改zk节点数据（异步）
         * 参数：
         * path：节点路径
         * data：新数据
         * version： 数据版本
         * sc：实现回调函数的对象
         * ctx：给回调函数的上下文
         */
        // String ctx = "{'alter':'success'}";
        // zkServer.getZooKeeper().setData("/testNode", "asynchronous-data".getBytes(), 0, new AlterCallBack(), ctx);

    }
}
