package com.ljn.client.controller;

import com.ljn.api.service.UserInterface;
import com.ljn.rpc.RpcClientUtils;
import org.apache.zookeeper.KeeperException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

@RestController
public class UserController {
    private UserInterface userInterface;

    public UserController() {
        try {
            userInterface = RpcClientUtils.getServiceProxy(UserInterface.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/getUserById")
    public String getUserById(String id) throws RemoteException {
        return userInterface.getUserById(id);
    }
}
