package com.ljn;

import com.ljn.rpc.RpcClientUtils;
import com.ljn.service.UserService;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.rmi.NotBoundException;

public class ClientApp {
    public static void main(String[] args) {
        try {
            UserService serviceProxy = RpcClientUtils.getServiceProxy(UserService.class);
            String information = serviceProxy.getUserInformationByName("ljn");
            System.out.println(information);
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
}
