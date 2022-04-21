package com.ljn.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {
    String getUserInformationByName(String name) throws RemoteException;
}
