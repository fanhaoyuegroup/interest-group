/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.curator.distributed.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;

import java.util.concurrent.TimeUnit;

/**
 * 使用 curator 实现 zk 的分布式锁
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-27
 */
public class DistributedLock {

    /**
     * Zookeeper info
     */
    private static final String ZK_ADDRESS = "127.0.0.1:2181";
    private static final String LOCK_PATH = "/locker";

    public static void main(String[] args) throws Exception {
        // 1. 创建 Client 端
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                ZK_ADDRESS,
                new RetryNTimes(10, 5000)
        );
        client.start();


        InterProcessMutex lock = new InterProcessMutex(client, LOCK_PATH);
        if (lock.acquire(1000, TimeUnit.SECONDS)) {
            try {
                // do some work inside of the critical section here
                System.out.println("do something");
            } finally {
                lock.release();
            }
        }
    }
}
