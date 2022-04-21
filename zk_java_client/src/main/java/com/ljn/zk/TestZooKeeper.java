package com.ljn.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

public class TestZooKeeper {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
//        create();
//        ls();
//        get();
        delete();
    }

    // 创建节点
    public static void create() throws IOException, KeeperException, InterruptedException {
        // 创建客户端对象
        // sessionTimeout设置太短会导致连接失败
        // Watcher对象可以为空
        ZooKeeper zooKeeper = new ZooKeeper("1.14.94.244:2181", 20000, new Watcher() {
            // 连接成功后和关闭客户端前执行
            public void process(WatchedEvent watchedEvent) {
                System.out.println("watcher中的方法正在执行");
            }
        });

        // 发送请求
        // 创建持久节点
        String res1 = zooKeeper.create("/parent", "parent data".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("创建持久节点结果: " + res1);
        // 创建临时节点
        String res2 = zooKeeper.create("/parent/tmp", null,
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("创建临时节点结果: " + res2);
        // 创建带序号节点
        String res3 = zooKeeper.create("/parent/sequence", null,
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println("创建带序号节点结果: " + res3);
        // 关闭连接
        zooKeeper.close();
    }

    // 遍历节点
    public static void ls() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("1.14.94.244:2181",
                20000, null);
        dfs(zooKeeper, "/");
        zooKeeper.close();
    }
    // 递归
    public static void dfs(ZooKeeper zooKeeper, String path) throws KeeperException, InterruptedException {
        System.out.println(path);
        List<String> children = zooKeeper.getChildren(path, false);
        for (String child : children) {
            if (!path.equals("/"))
                dfs(zooKeeper,path+"/"+child);
            else
                dfs(zooKeeper,path+child);
        }
    }

    // 查询节点的数据
    public static void get() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("1.14.94.244:2181",
                20000, null);
        byte[] data = zooKeeper.getData("/parent", false, null);
        System.out.println("节点/parent中的数据是: " + new String(data));
        zooKeeper.close();
    }

    // 删除节点
    public static void delete() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("1.14.94.244:2181",
                20000, null);
        Stat stat = new Stat();
        // 将节点的状态信息保存到stat，主要是处于安全的目的
        zooKeeper.getData("/parent/sequence0000000001", false, stat);
        // 注意是version前面有C
        int cversion = stat.getCversion();
        zooKeeper.delete("/parent/sequence0000000001", cversion);
        zooKeeper.close();
    }
}
