/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.curator.master.leader;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * LeaderElectionDemo
 * 通过 LeaderSelectorListener 可以对领导权进行控制，在适当的时候释放领导权，这样每个节点都有可能获得领导权。
 * 而 LeaderLatch 则一直持有 leadership，除非调用 close 方法，否则它不会释放领导权。
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-30
 */
public class LeaderElectionDemo {


    private static final String PATH = "/marvel/leader";

    public static void main(String[] args) {

        List<LeaderSelector> selectors = new ArrayList<>();
        List<CuratorFramework> clients = new ArrayList<>();


        try {
            for (int i = 0; i < 10; i++) {
                CuratorFramework client = getClient();
                clients.add(client);

                final String name = "client#" + i;
                /*
                    为每个客户端添加一个监听器，当被选为 leader 之后，调用 takeLeadership 方法进行业务逻辑处理，处理完成即释放领导权。
                    LeaderSelectorListener类继承了ConnectionStateListener。一旦LeaderSelector启动，它会向curator客户端添加监听器。
                    使用LeaderSelector必须时刻注意连接的变化。一旦出现连接问题如SUSPENDED，curator实例必须确保它可能不再是leader，
                    直至它重新收到RECONNECTED。如果LOST出现，curator实例不再是leader并且其takeLeadership()应该直接退出。

                    推荐的做法是，如果发生SUSPENDED或者LOST连接问题，最好直接抛CancelLeadershipException，此时，
                    leaderSelector实例会尝试中断并且取消正在执行takeLeadership（）方法的线程。
                    建议扩展LeaderSelectorListenerAdapter, LeaderSelectorListenerAdapter中已经提供了推荐的处理方式 。
                 */
                LeaderSelector leaderSelector = new LeaderSelector(client, PATH, new LeaderSelectorListener() {
                    @Override
                    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                        System.out.println(name + ":I am leader.");
                        Thread.sleep(2000);
                    }

                    @Override
                    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {

                    }
                });
                // 该方法调用确保此实例在释放领导权后还能获得领导权
                leaderSelector.autoRequeue();
                leaderSelector.start();
                selectors.add(leaderSelector);
            }

            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            for (CuratorFramework client : clients) {
                CloseableUtils.closeQuietly(client);
            }

            for (LeaderSelector selector : selectors) {
                CloseableUtils.closeQuietly(selector);
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
