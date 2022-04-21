package com.ljn.api.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserInterface extends Remote {
    String getUserById(String id) throws RemoteException;
}
