package com.example.minxuan.socialprojectv2;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by MinXuan on 2017/5/24.
 */

public class NetworkClient {

    public WebSocketClient webSocketClient;

    /**Websocket 協定*/
    private Draft draft;

    String serverAddr;
    String account;
    String password;
    String WsAddress;

    Activity activity;
    Gson gson = new Gson();

    /** 收件夾處理*/
    boolean IN_MESSAGE_BOX = false;
    FileInputStream fis;
    FileOutputStream fos;

    public NetworkClient(String serverAddr, String account, String password){
        this.serverAddr = serverAddr;
        this.account = account;
        this.password = password;
        setWsAddress();
        this.draft = new Draft_17();


        try {

            this.webSocketClient = new WebSocketClient(new URI(this.WsAddress), this.draft) {

                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                }

                @Override
                public void onMessage(String message) {

                    /** Gson 訊息解碼*/
                    Type listType = new TypeToken<ArrayList<Message>>() {}.getType();
                    ArrayList<Message> jsonArr = gson.fromJson("[" + message + "]", listType);
                    Message mess = jsonArr.get(0);

                    /** 登入訊息*/
                    switch(mess.getTAG()){
                        case "loginSuccess":
                            Intent i = new Intent();
                            i.setClass(activity, Menupage.class);
                            activity.startActivity(i);
                            activity.finish();
                            break;
                        case "accountNotFound":
                            webSocketClient.close();
                            Log.e("Error", "accountNotFound");
                            break;
                        case "reLoginError":
                            webSocketClient.close();
                            Log.e("Error", "reLoginError");
                            break;
                        case "passwordFailed":
                            webSocketClient.close();
                            Log.e("Error", "passwordFailed");
                            break;
                        case "loginFailed":
                            webSocketClient.close();
                            Log.e("Error", "loginFailed");
                            break;
                    }

                    /** 註冊訊息*/
                    switch (mess.getTAG()){
                        case  "createNewSuccess":
                            activity.finish();
                            webSocketClient.close();
                            Log.e("createNewSuccess","");
                            break;
                    }

                    /** 收件夾*/
                    switch(mess.getTAG()){
                        case "empty":
                            break;
                        case "notEmpty":
                            break;
                        case "message":
                            if(IN_MESSAGE_BOX){ /** 正在對話視窗中，及時顯示的操作與輸出成檔案*/

                            }else{  /** 不在對話視窗，持續監聽，將收到的訊息輸出成檔案*/

                            }
                            break;
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {

                }

                @Override
                public void onError(Exception e) {

                }
            };
            this.webSocketClient.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void setWsAddress(){
        /**要寫技術文件喔*/
        this.WsAddress = "ws://" + this.serverAddr + ":8080/WebSocketServerExample/websocketendpoint/" + this.account + "/" + this.password;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }



}
