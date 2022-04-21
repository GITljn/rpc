package com.ljn;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class TestAppClient {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        // 从注册中心拿到服务
        TestInterface testInterface = (TestInterface) Naming.lookup("rmi://localhost:8888/serviceName");
        // 远程调用
        System.out.println(testInterface.testRemoteCall("ljn"));
    }
}
