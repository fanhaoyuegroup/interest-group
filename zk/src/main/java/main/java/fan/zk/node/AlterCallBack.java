/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.node;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;

/**
 * AlterCallBack
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-21
 */
public class AlterCallBack implements AsyncCallback.StatCallback {
    /**
     * 回调函数
     *
     * @param rc
     * @param path
     * @param ctx
     * @param stat
     */
    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        System.out.println("修改节点：" + path + "成功...");
        // 通过Stat对象可以获取znode所有的状态属性，这里以version为例
        System.out.println("当前数据版本为：" + stat.getVersion());
        System.out.println((String) ctx);
    }
}
