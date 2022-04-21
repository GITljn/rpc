package com.ljn.rpc.registry;

import com.ljn.rpc.connection.ZkConnection;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.util.List;

public class RpcRegistry {
    private ZkConnection zkConnection;
    private String ip;
    private Integer port;

    /**
     * 用于将服务发布到注册中心（需提供服务所在的地址及服务的接口的类对象）
     * @param serviceInterface 服务接口类对象
     * @param serviceObject    服务（实现类对象）
     */
    public void registerService(Class<? extends Remote> serviceInterface, Remote serviceObject)
            throws IOException, KeeperException, InterruptedException {
        // zk中节点的名称
        String path = "/ljn/rpc/"+serviceInterface.getName();
        // zk中节点的数据
        String rmi = "rmi://"+ip+":"+port+"/"+serviceInterface.getName();
        ZooKeeper zkServer = zkConnection.getZkServer();
        List<String> children = zkServer.getChildren("/ljn/rpc", null);
        if (children.contains(serviceInterface.getName())) {
            // 删除前先查
            Stat stat = new Stat();
            zkServer.getData(path, null, stat);
            zkServer.delete(path, stat.getCversion());
        }
        zkServer.create(path, rmi.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 将服务发布到注册中心
        Naming.rebind(rmi, serviceObject);
    }

    /**
     * 获取由rmi创建的代理对象（从注册中心拿到需要的服务）
     * @param serviceInterface
     * @param <T>
     * @return
     */
    public <T extends Remote> T getServiceProxy(Class<T> serviceInterface)
            throws IOException, KeeperException, InterruptedException, NotBoundException {
        String path = "/ljn/rpc/"+serviceInterface.getName();
        ZooKeeper zkServer = zkConnection.getZkServer();
        byte[] data = zkServer.getData(path, null, null);
        String rmi = new String(data);
        // 得到代理对象
        Remote serviceProxy = Naming.lookup(rmi);
        return (T) serviceProxy;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public ZkConnection getZkConnection() {
        return zkConnection;
    }

    public void setZkConnection(ZkConnection zkConnection) {
        this.zkConnection = zkConnection;
    }
}
