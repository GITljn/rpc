package com.ljn;

import com.ljn.rpc.RpcServerUtils;
import com.ljn.serviceimpl.UserServiceImpl;
import org.apache.zookeeper.KeeperException;
import com.ljn.service.UserService;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args)
            throws IOException, InterruptedException, KeeperException, ClassNotFoundException {
//        UserService userService = new UserServiceImpl();
//        RpcServerUtils.registerService(UserService.class, userService);
        Class.forName("com.ljn.rpc.RpcServerUtils");
    }
}
