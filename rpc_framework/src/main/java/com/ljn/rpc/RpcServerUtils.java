package com.ljn.rpc;

import com.ljn.rpc.connection.ZkConnection;
import com.ljn.rpc.registry.RpcRegistry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.util.Properties;

// rpc_framework初始化
public class RpcServerUtils {
    private static final Properties config = new Properties();
    private static final ZkConnection zkConnection;
    private static final RpcRegistry rpcRegistry;
    private static final Properties serviceReleaseConfig = new Properties();

    static {
        InputStream inputStream = RpcServerUtils.class.getClassLoader().getResourceAsStream("rpc.properties");
        try {
            config.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String zkServerAddr = config.getProperty("zk.server.addr") == null ? "1.14.94.244" : config.getProperty("zk.server.addr");
        Integer sessionTimeout = config.getProperty("zk.session.timeout") == null ? 200000 : Integer.parseInt(config.getProperty("zk.session.timeout"));
        String serverIp = config.getProperty("server.ip") == null ? "localhost" : config.getProperty("server.ip");
        Integer serverPort = config.getProperty("server.port") == null ? 9090 : Integer.parseInt(config.getProperty("server.port"));
        zkConnection = new ZkConnection(zkServerAddr, sessionTimeout);
        rpcRegistry = new RpcRegistry();
        rpcRegistry.setZkConnection(zkConnection);
        rpcRegistry.setIp(serverIp);
        rpcRegistry.setPort(serverPort);
        try {
            // 创建一个rmi注册中心
            LocateRegistry.createRegistry(serverPort);
            // zookeeper中使用java创建节点的时候必须有父节点
            ZooKeeper zkServer = zkConnection.getZkServer();
            List<String> children = zkServer.getChildren("/", null);
            if (!children.contains("ljn")) {
                zkServer.create("/ljn", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            List<String> ljnChildren = zkServer.getChildren("/ljn", null);
            if (!ljnChildren.contains("rpc")) {
                zkServer.create("/ljn/rpc", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream input = RpcServerUtils.class.getClassLoader().getResourceAsStream("service-release-cofig.properties");
        if (input != null) {
            try {
                serviceReleaseConfig.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Object o : serviceReleaseConfig.keySet()) {
                try {
                    String serviceInterfaceName = (String) o;
                    String serviceClassName = (String) serviceReleaseConfig.get(o);
                    Class<Remote> serviceInterface = (Class<Remote>) Class.forName(serviceInterfaceName);
                    Remote serviceObject = (Remote) Class.forName(serviceClassName).newInstance();
                    rpcRegistry.registerService(serviceInterface, serviceObject);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 服务发布的方法
    public static void registerService(Class<? extends Remote> serviceInterface, Remote serviceObject)
            throws IOException, KeeperException, InterruptedException {
        rpcRegistry.registerService(serviceInterface, serviceObject);
    }
}
