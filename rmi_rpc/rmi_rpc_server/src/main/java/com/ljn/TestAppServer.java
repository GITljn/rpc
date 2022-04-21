package com.ljn;

import com.ljn.impl.TestInterfaceImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class TestAppServer {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        // 服务端创建服务
        TestInterface testInterface = new TestInterfaceImpl();
        // 创建注册中心
        LocateRegistry.createRegistry(8888);
        // 将服务发布到注册中心
        Naming.rebind("rmi://localhost:8888/serviceName", testInterface);
        // 发布成功后程序不会停止
        System.out.println("服已被成功发布到注册中心");
    }
}
