/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk;

import com.google.common.base.Strings;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

/**
 * LockTest
 * 获取分布式锁的总体思路：
 *
 * 1. 在获取分布式锁的时候在locker节点下创建临时顺序节点，释放锁的时候删除该临时节点。
 *
 * 2. 客户端调用createNode方法在locker下创建临时顺序节点
 *
 * 3. 然后调用getChildren(“locker”)来获取locker下面的所有子节点，注意此时不用设置任何Watcher。
 *
 * 4. 客户端获取到所有的子节点path之后，如果发现自己在之前创建的子节点序号最小，那么就认为该客户端获取到了锁。
 *
 * 5. 如果发现自己创建的节点并非locker所有子节点中最小的，说明自己还没有获取到锁，此时客户端需要找到比自己小的那个节点，
 *          然后对其调用exist()方法，同时对其注册事件监听器。
 *
 * 6. 之后，让这个被关注的节点删除，则客户端的Watcher会收到相应通知，此时再次判断自己创建的节点是否是locker子节点中序号最小的，
 *          如果是则获取到了锁，如果不是则重复以上步骤继续获取到比自己小的一个
 *
 *
 * zookeeper 实现分布式锁
 * 优点：
 *  - 实现比较简单，有通知机制，能够提供较快的响应，有点类似 ReentrantLock 的思想，对于节点删除失效的场景由 Session 超时保证节点能够
 *  删除掉。
 * 缺点：
 *  - 重量级，同时在大量锁的情况下会发生 "惊群" 的问题
 *
 * "惊群" 就是在一个节点删除的时候，大量对这个节点的删除动作有订阅 Watcher 的线程会进行回调，这对 ZK 集群是十分不利的。
 *
 * 解决“惊群”：
 *
 * 为了解决“惊群“问题，我们需要放弃订阅一个节点的策略，那么怎么做呢？
 *
 * 1. 我们将锁抽象成目录，多个线程在此目录下创建瞬时的顺序节点，因为Zk会为我们保证节点的顺序性，所以可以利用节点的顺序进行锁的判断。
 * 2. 首先创建顺序节点，然后获取当前目录下最小的节点，判断最小节点是不是当前节点，如果是那么获取锁成功，如果不是那么获取锁失败。
 * 3. 获取锁失败的节点获取当前节点上一个顺序节点，对此节点注册监听，当节点删除的时候通知当前节点。
 * 4. 当unlock的时候删除节点之后会通知下一个节点。

 *
 * @author luobosi@2dfire.com
 * @since 2018-09-27
 */
public class LockTest {
    private String zkQurom = "localhost:2181";

    private String lockNameSpace = "/mylock";

    private String nodeString = lockNameSpace + "/test1";

    private Lock mainLock;

    private ZooKeeper zk;

    public LockTest() {
        try {
            zk = new ZooKeeper(zkQurom, 6000, watchedEvent -> {
                System.out.println("Receive event " + watchedEvent);
                if (Watcher.Event.KeeperState.SyncConnected == watchedEvent.getState())
                    System.out.println("connection is established...");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void ensureRootPath() throws InterruptedException {
        try {
            if (zk.exists(lockNameSpace, true) == null) {
                zk.create(lockNameSpace, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    private void watchNode(String nodeString, final Thread thread) throws InterruptedException {
        try {
            zk.exists(nodeString, watchedEvent -> {
                System.out.println("==" + watchedEvent.toString());
                if (watchedEvent.getType() == Watcher.Event.EventType.NodeDeleted) {
                    System.out.println("Threre is a Thread released DistributedLock==============");
                    thread.interrupt();
                }
                try {
                    zk.exists(nodeString, watchedEvent1 -> {
                        System.out.println("==" + watchedEvent1.toString());
                        if (watchedEvent1.getType() == Watcher.Event.EventType.NodeDeleted) {
                            System.out.println("Threre is a Thread released DistributedLock==============");
                            thread.interrupt();
                        }
                        try {
                            zk.exists(nodeString, true);
                        } catch (KeeperException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取锁
     *
     * @return
     * @throws InterruptedException
     */
    public boolean lock() throws InterruptedException {
        String path = null;
        ensureRootPath();
        watchNode(nodeString, Thread.currentThread());
        while (true) {
            try {
                path = zk.create(nodeString, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            } catch (KeeperException e) {
                System.out.println(Thread.currentThread().getName() + "  getting DistributedLock but can not get");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    System.out.println("thread is notify");
                }
            }
            if (!Strings.nullToEmpty(path).trim().isEmpty()) {
                System.out.println(Thread.currentThread().getName() + "  get DistributedLock...");
                return true;
            }
        }
    }

    /**
     * 释放锁
     */
    public void unlock() {
        try {
            zk.delete(nodeString, -1);
            System.out.println("Thread.currentThread().getName() +  release DistributedLock...");
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 4; i++) {
            service.execute(() -> {
                LockTest test = new LockTest();
                try {
                    test.lock();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.unlock();
            });
        }
        service.shutdown();
    }
}
