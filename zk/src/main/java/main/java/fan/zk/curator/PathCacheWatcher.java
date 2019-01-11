/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.RetryNTimes;

/**
 * PathCacheWatcher
 * 监视一个路径下孩子节点的建立，删除，以及节点数据更新，使用的监听事件监听类：PathChildrenCacheListener;
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-27
 */
public class PathCacheWatcher {

    /**
     * Zookeeper info
     */
    private static final String ZK_ADDRESS = "127.0.0.1:2181";
    private static final String ZK_PATH = "/zktest";

    public static void main(String[] args) throws Exception {
        // 1. 创建 Client 端
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                ZK_ADDRESS,
                new RetryNTimes(10, 5000)
        );
        client.start();
        System.out.println("zk client start successfully!");

        // 2. 创建 zk 的监听器
        PathChildrenCache watcher = new PathChildrenCache(
                client,
                ZK_PATH,
                true
        );
        watcher.getListenable().addListener((client1, event) -> {
            ChildData data = event.getData();
            if (data == null) {
                System.out.println("No data in event[" + event + "]");
            } else {
                System.out.println(
                        "Receive event: "
                                + "type=[" + event.getType() + "]"
                                + ", path=[" + data.getPath() + "]"
                                + ", data=[" + new String(data.getData()) + "]"
                                + ", stat=[" + data.getStat() + "]");
            }
        });

        watcher.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        System.out.println("Register zk watcher successfully!");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
