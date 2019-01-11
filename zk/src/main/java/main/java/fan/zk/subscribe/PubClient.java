package main.java.fan.zk.subscribe;


import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * PubClient
 *
 * @author luobosi@2dfire.com
 * @since 2018-10-21
 */
public class PubClient {

    static String marvel = "/marvel";

    public static void main(String[] args) throws Exception {
        String hosts = "127.0.0.1:2181";
        ZooKeeper zk = new ZooKeeper(hosts, 30000, event -> System.out.println("回调方法"));
        // 如果"/marvel"不存在,则创建
        if (zk.exists(marvel, false) == null) {
            String value = zk.create(marvel, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(value);
        }
        // 注册服务
        zk.create(marvel + "/" + "marvel2", "192.168.1.1:8080".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(10000);
        zk.close();

    }
}