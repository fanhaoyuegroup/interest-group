/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

/**
 * ZKNodeOperator
 * 使用 curator 对 zk 节点进行增删改查
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-27
 */
public class ZKNodeOperator {

    public static void main(String[] args) throws Exception {
        //通过工厂创建连接
        CuratorFramework cachezk = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(10000)
                .retryPolicy(new RetryNTimes(1000, 10))
                .build();
        cachezk.start();
        //创建节点
        cachezk.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/super/gang", "第一个watcher".getBytes());
        //更新节点
        cachezk.setData().forPath("/super", "修改节点".getBytes());
        //删除节点
        cachezk.delete().deletingChildrenIfNeeded().forPath("/super");
        //关闭连接
        cachezk.close();
    }

}
