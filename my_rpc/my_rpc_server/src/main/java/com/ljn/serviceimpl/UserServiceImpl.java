package com.ljn.serviceimpl;

import com.ljn.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    public UserServiceImpl() throws RemoteException {
    }

    public String getUserInformationByName(String name) throws RemoteException {
        return "{\"name\"=ljn, \"age\"=25}";
    }
}
