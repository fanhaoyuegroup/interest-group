/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package main.java.fan.zk.node;

import org.apache.zookeeper.AsyncCallback;

/**
 * CreateCallBack
 * 异步创建zk节点的方式，因为异步创建有一个回调函数，
 * 所以我们得先创建一个类，实现StringCallback接口里面的回调方法：
 *
 * @author luobosi@2dfire.com
 * @since 2018-09-21
 */
public class CreateCallBack implements AsyncCallback.StringCallback {

    /**
     * 回调函数
     *
     * @param rc
     * @param path  节点路径
     * @param ctx
     * @param name
     */
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("创建节点：" + path);
        System.out.println((String) ctx);
    }
}
