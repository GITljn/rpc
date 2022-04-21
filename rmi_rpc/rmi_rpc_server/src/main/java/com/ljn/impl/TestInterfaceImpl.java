package com.ljn.impl;

import com.ljn.TestInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// 接口实现类除了实现接口还要继承UnicastRemoteObject
// UnicastRemoteObject相当于一个适配器（连接转换），用于创建远程连接的一切资源，包括服务器提供服务，客户端创建代理
public class TestInterfaceImpl extends UnicastRemoteObject implements TestInterface {
    // 因为TestInterface继承字Remote接口，所以所有方法都必须抛出RemoteException，包括构造方法
    public TestInterfaceImpl() throws RemoteException{}
    public String testRemoteCall(String name) throws RemoteException {
        return "你好: " + name;
    }
}
