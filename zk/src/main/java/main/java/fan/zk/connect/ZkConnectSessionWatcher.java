/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.connect;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;



/**
 * ZkConnectSessionWatcher
 * 如何通过sessionid和session密码去恢复上一次的会话，也就是zk的会话重连机制。
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-21
 */
public class ZkConnectSessionWatcher implements Watcher {

    private static final Logger logger = LoggerFactory.getLogger(ZkConnectSessionWatcher.class);

    // 集群模式则是多个ip
    // private static final String zkServerIps = "192.168.190.128:2181,192.168.190.129:2181,192.168.190.130:2181";
    /**
     * 单机模式是一个ip
     */
    private static final String zkServerIp = "127.0.0.1:2181";
    // 超时时间
    private static final Integer timeout = 5000;

    // Watch事件通知
    public void process(WatchedEvent watchedEvent) {
        logger.warn("接收到watch通知：{}", watchedEvent);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // 实例化zookeeper客户端
        ZooKeeper zooKeeper = new ZooKeeper(zkServerIp, timeout, new ZkConnectSessionWatcher());

        logger.warn("客户端开始连接zookeeper服务器...");
        logger.warn("连接状态：{}", zooKeeper.getState());
        Thread.sleep(2000);
        logger.warn("连接状态：{}", zooKeeper.getState());

        // 记录本次会话的sessionId
        long sessionId = zooKeeper.getSessionId();
        // 转换成16进制进行打印
        logger.warn("sid：{}", "0x" + Long.toHexString(sessionId));
        // 记录本次会话的session密码
        byte[] sessionPassword = zooKeeper.getSessionPasswd();

        Thread.sleep(200);

        // 开始会话重连
        logger.warn("开始会话重连...");
        // 加上sessionId和password参数去实例化zookeeper客户端
        ZooKeeper zkSession = new ZooKeeper(zkServerIp, timeout, new ZkConnectSessionWatcher(), sessionId, sessionPassword);
        logger.warn("重新连接状态zkSession：{}", zkSession.getState());
        Thread.sleep(2000);
        logger.warn("重新连接状态zkSession：{}", zkSession.getState());
    }
}
