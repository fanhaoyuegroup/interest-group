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
import java.io.InputStream;
import java.util.Properties;

/**
 * ZkConnect
 * 连接 zk 的小 demo
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-21
 */
public class ZkConnect implements Watcher {

    /**
     * logger 对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ZkConnect.class);


    /**
     * 单机模式是一个ip
     */
    private static String zkServerIp;

    // 集群模式则是多个ip
    // private static String zkServerIps;

    /**
     * 连接超时时间
     */
    private static Integer timeout;

    // 加载配置信息
    static {
        Properties properties = new Properties();
        InputStream inputStream = Object.class.getResourceAsStream("/zk-connect.properties");
        try {
            properties.load(inputStream);

             zkServerIp = properties.getProperty("zk.zkServerIp");
            // zkServerIps = properties.getProperty("zk.zkServerIps");
            timeout = Integer.parseInt(properties.getProperty("zk.timeout"));
        } catch (Exception e) {
            logger.error("配置文件读取异常", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("关闭流失败", e);
            }
        }
    }

    /**
     * Watch事件通知
     *
     * @param watchedEvent 事件监听对象
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("接收到watch通知：" + watchedEvent);
    }


    /**
     * 客户端和zk服务端链接是一个异步的过程
     * 当连接成功后后，客户端会收的一个watch通知
     *
     * 参数：
     * connectString：连接服务器的ip字符串，
     *      比如: "192.168.190.128:2181,192.168.190.129:2181,192.168.190.130:2181"
     *      可以是一个ip，也可以是多个ip，一个ip代表单机，多个ip代表集群
     *      也可以在ip后加路径
     * sessionTimeout：超时时间，心跳收不到了，那就超时
     * watcher：通知事件，如果有对应的事件触发，则会收到一个通知；如果不需要，那就设置为null
     * canBeReadOnly：可读，当这个物理机节点断开后，还是可以读到数据的，只是不能写，
     *                         此时数据被读取到的可能是旧数据，此处建议设置为false，不推荐使用
     * sessionId：会话的id
     * sessionPasswd：会话密码   当会话丢失后，可以依据 sessionId 和 sessionPasswd 重新获取会话
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        // 实例化zookeeper客户端
        ZooKeeper zooKeeper = new ZooKeeper(zkServerIp, timeout, new ZkConnect());

        logger.warn("客户端开始连接zookeeper服务器...");
        logger.warn("连接状态：{}", zooKeeper.getState());

        // 避免发出连接请求就断开，不然就无法正常连接也无法获取watch事件的通知
        Thread.sleep(2000);

        logger.warn("连接状态：{}", zooKeeper.getState());
    }
}
