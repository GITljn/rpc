package com.ljn.rpc.connection;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

// 用于提供zookeeper连接的类
public class ZkConnection {
    // 1.14.94.244:2181
    private String zkServerAddr;
    private Integer sessionTimeOut;

    public ZkConnection() {
        this.zkServerAddr = "1.14.94.244:2181";
        this.sessionTimeOut = 20000;
    }

    public ZkConnection(String zkServerAddr, Integer sessionTimeOut) {
        this.zkServerAddr = zkServerAddr;
        this.sessionTimeOut = sessionTimeOut;
    }

    public ZooKeeper getZkServer() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(zkServerAddr, sessionTimeOut, null);
        return zooKeeper;
    }
}
