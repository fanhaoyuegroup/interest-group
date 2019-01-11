/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.curator.master.leader;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * LeaderLatchDemo
 * 这种选举方式是随机从候选中选出一台作为 leader，选中之后除非调用 close() 释放 leadship，否则其他的候选者无法成为 leader
 * LeaderLatch 通过增加一个 ConnectionStateListener 监听连接问题。如果出现 SUSPENDED 或者 LOST，leader 会报告自己不再是 leader
 * (直到重新建立连接，否则不会有 leader)。如果 LOST 的连接被重新建立立即 RECONNECTED，leaderLatch 会删除先前的 zNode 并重新建立 zNode
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-30
 */
public class LeaderLatchDemo {

    private static final String PATH = "/demo/leader";

    public static void main(String[] args) {

        List<LeaderLatch> latchList = new ArrayList<>();
        List<CuratorFramework> clients = new ArrayList<>();
        try {
            for (int i = 0; i < 10; i++) {
                CuratorFramework client = getClient();
                clients.add(client);

                final LeaderLatch leaderLatch = new LeaderLatch(client, PATH, "client#" + i);
                leaderLatch.addListener(new LeaderLatchListener() {
                    @Override
                    public void isLeader() {
                        System.out.println(leaderLatch.getId() + ":I am leader. I am doing jobs!");
                    }

                    @Override
                    public void notLeader() {
                        System.out.println(leaderLatch.getId() + ":I am not leader. I will do nothing!");
                    }
                });
                latchList.add(leaderLatch);
                leaderLatch.start();
            }
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            for (CuratorFramework client : clients) {
                CloseableUtils.closeQuietly(client);
            }

            for (LeaderLatch leaderLatch : latchList) {
                CloseableUtils.closeQuietly(leaderLatch);
            }
        }
    }

    /**
     * 获取客户端连接
     *
     * @return zk client
     */
    private static CuratorFramework getClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(6000)
                .connectionTimeoutMs(3000)
                .namespace("demo")
                .build();
        client.start();
        return client;
    }
}
