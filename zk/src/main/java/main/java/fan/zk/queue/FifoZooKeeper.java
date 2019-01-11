/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.queue;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

/**
 * FifoZooKeeper
 *        在/queue-fifo的目录下创建 SEQUENTIAL 类型的子目录 /x(i)，这样就能保证所有成员加入队列时都是有编号的，
 *        出队列时通过 getChildren( ) 方法可以返回当前所有的队列中的元素，然后消费其中最小的一个，这样就能保证FIFO。
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-29
 */
public class FifoZooKeeper {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            doOne();
        } else {
            doAction(Integer.parseInt(args[0]));
        }
    }

    public static void doOne() throws Exception {
        String host1 = "127.0.0.1:2181";
        ZooKeeper zk = connection(host1);
        initQueue(zk);
        produce(zk, 1);
        produce(zk, 2);
        cosumer(zk);
        cosumer(zk);
        cosumer(zk);
        zk.close();
    }

    public static void doAction(int client) throws Exception {
        String host1 = "127.0.0.1:2181";
        //String host2 = "127.0.0.1:2182";
        //String host3 = "127.0.0.1:2183";
        ZooKeeper zk = null;
        switch (client) {
            case 1:
                zk = connection(host1);
                initQueue(zk);
                produce(zk, 1);
                break;
            /*case 2:*/
            /*    zk = connection(host2);*/
            /*    initQueue(zk);*/
            /*    produce(zk, 2);*/
            /*    break;*/
            /*case 3:*/
            /*    zk = connection(host3);*/
            /*    initQueue(zk);*/
            /*    cosume(zk);*/
            /*    cosume(zk);*/
            /*    cosume(zk);*/
            //break;
        }
    }


    // 创建一个与服务器的连接
    public static ZooKeeper connection(String host) throws IOException {
        return new ZooKeeper(host, 60000, event -> {
        });
    }

    /**
     * 初始化一个队列
     *
     * @param zk
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void initQueue(ZooKeeper zk) throws KeeperException, InterruptedException {
        if (zk.exists("/queue-fifo", false) == null) {
            System.out.println("create /queue-fifo task-queue-fifo");
            zk.create("/queue-fifo", "task-queue-fifo".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            System.out.println("/queue-fifo is exist!");
        }
    }

    /**
     * 生产消息
     *
     * @param zk
     * @param x
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void produce(ZooKeeper zk, int x) throws KeeperException, InterruptedException {
        System.out.println("create /queue-fifo/x" + x + " x" + x);
        zk.create("/queue-fifo/x" + x, ("x" + x).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    /**
     * 消费消息，从列表中拿出最小的记录消费
     *
     * @param zk
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void cosumer(ZooKeeper zk) throws KeeperException, InterruptedException {
        List<String> list = zk.getChildren("/queue-fifo", true);
        if (list.size() > 0) {
            long min = Long.MAX_VALUE;
            for (String num : list) {
                if (min > Long.parseLong(num.substring(1))) {
                    min = Long.parseLong(num.substring(1));
                }
            }
            System.out.println("delete /queue/x" + min);
            zk.delete("/queue-fifo/x" + min, 0);
        } else {
            System.out.println("No node to cosume");
        }
    }
}