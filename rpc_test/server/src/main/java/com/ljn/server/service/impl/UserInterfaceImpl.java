package com.ljn.server.service.impl;

import com.ljn.api.service.UserInterface;
import com.ljn.rpc.RpcServerUtils;
import com.ljn.server.mapper.UserMapper;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@Service
public class UserInterfaceImpl extends UnicastRemoteObject implements UserInterface {
    @Autowired
    private UserMapper userMapper;

    public UserInterfaceImpl() throws RemoteException {
        try {
            RpcServerUtils.registerService(UserInterface.class, this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getUserById(String id) throws RemoteException {
        return userMapper.getUserById(id).toString();
    }
}
