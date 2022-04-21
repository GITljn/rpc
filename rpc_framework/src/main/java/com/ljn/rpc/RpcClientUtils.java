package com.ljn.rpc;

import com.ljn.rpc.connection.ZkConnection;
import com.ljn.rpc.registry.RpcRegistry;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.util.Properties;

public class RpcClientUtils {
    public static final RpcRegistry rpcRegistry;
    static {
        Properties config = new Properties();
        InputStream input = RpcClientUtils.class.getClassLoader().getResourceAsStream("rpc.properties");
        try {
            config.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String zkServerAddr = config.getProperty("zk.server.addr") == null ? "1.14.94.244" : config.getProperty("zk.server.addr");
        Integer sessionTimeout = config.getProperty("zk.session.timeout") == null ? 200000 : Integer.parseInt(config.getProperty("zk.session.timeout"));
        ZkConnection zkConnection = new ZkConnection(zkServerAddr, sessionTimeout);
        rpcRegistry = new RpcRegistry();
        rpcRegistry.setZkConnection(zkConnection);
    }

    public static <T extends Remote> T getServiceProxy(Class<T> serviceInterface)
            throws IOException, KeeperException, InterruptedException, NotBoundException {
        return rpcRegistry.getServiceProxy(serviceInterface);
    }
}
