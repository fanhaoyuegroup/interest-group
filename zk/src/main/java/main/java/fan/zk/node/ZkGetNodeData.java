/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.node;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ZkGetNodeData
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-21
 */
public class ZkGetNodeData implements Watcher {

    private static final Logger logger = LoggerFactory.getLogger(ZkGetNodeData.class);

    // 集群模式则是多个ip
    // private static final String zkServerIps = "192.168.190.128:2181,192.168.190.129:2181,192.168.190.130:2181";
    /**
     * 单机模式是一个ip
     */
    private static final String zkServerIp = "127.0.0.1:2181";
    // 超时时间
    private static final Integer timeout = 5000;
    private static ZooKeeper zooKeeper;
    private static Stat stat = new Stat();

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
        zooKeeper = new ZooKeeper(zkServerIp, timeout, new ZkGetNodeData());

        /*
         * 参数：
         * path：节点路径
         * watch：true或者false，注册一个watch事件
         * stat：状态，我们可以通过这个对象获取节点的状态信息
         */
        byte[] resByte = zooKeeper.getData("/zk", true, stat);
        String result = new String(resByte);
        System.out.println("/zk 节点的数据: " + result);

        zooKeeper.close();
    }
}