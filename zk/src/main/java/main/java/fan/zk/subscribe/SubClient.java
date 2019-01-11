package main.java.fan.zk.subscribe;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;

/**
 * SubClient
 *
 * @author luobosi@2dfire.com
 * @since 2018-10-21
 */
public class SubClient {
    static ZooKeeper zk;
    static String marvel = "/marvel";

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1:2181";
        zk = new ZooKeeper(host, 30000, event -> {
            // 监听"/marvel"节点下的子节点变化事件, 更新 marvel 列表, 并重新注册监听
            if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged
                    && (marvel).equals(event.getPath())) {
                try {
                    updateServerList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        updateServerList();
        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void updateServerList() throws Exception {
        // 如果"/marvel"不存在,则创建
        if (zk.exists(marvel, false) == null) {
            zk.create(marvel, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        }

        List<String> newServerList = new ArrayList<>();
        // 获取并监听"/marvel"的子节点变化
        // watch参数为true, 表示监听子节点变化事件.
        // 每次都需要重新注册监听, 因为一次注册, 只能监听一次事件, 如果还想继续保持监听, 必须重新注册
        List<String> subList = zk.getChildren(marvel, true);
        for (String subNode : subList) {
            // 获取每个子节点下关联的server地址
            byte[] data = zk.getData(marvel + "/" + subNode, false, null);
            newServerList.add(new String(data, "utf-8"));
        }
        System.out.println(newServerList);
    }
}
