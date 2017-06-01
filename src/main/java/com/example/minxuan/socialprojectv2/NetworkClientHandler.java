package com.example.minxuan.socialprojectv2;

/**
 * Created by MinXuan on 2017/5/25.
 */

public class NetworkClientHandler {

    static NetworkClient networkClient = null;

    static void setNetworkClient(String serverAddr, String account, String password){
        networkClient = new NetworkClient(serverAddr, account, password);
    }

}
