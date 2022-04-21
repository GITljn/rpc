package com.ljn;

import java.rmi.Remote;
import java.rmi.RemoteException;

// 用于远程调用的接口必须继承Remote接口
public interface TestInterface extends Remote {
    // 继承了remote接口的接口中的方法，必须抛出RemoteException，包括构造方法
    String testRemoteCall(String name) throws RemoteException;
}
