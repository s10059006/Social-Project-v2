package com.example.minxuan.socialprojectv2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HomeActivity extends AppCompatActivity {

    private Button login;
    private Button create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        create = (Button) findViewById(R.id.home_createNew);
        login = (Button) findViewById(R.id.home_signIN);

        /**
                *      將登入紀錄預先顯示在EditeTextView上
                * */
        String[] userinfo;
        try {
            FileInputStream fis = this.openFileInput("current_user_info");
            ObjectInputStream ois = new ObjectInputStream(fis);
            userinfo = (String[]) ois.readObject();
            ois.close();
            fis.close();

            EditText et_server_address = (EditText)findViewById(R.id.home_serverAddr);
            EditText et_account = (EditText) findViewById(R.id.home_account);
            EditText et_password = (EditText) findViewById(R.id.home_password);

            et_server_address.setText(userinfo[0]);
            et_account.setText(userinfo[1]);
            et_password.setText(userinfo[2]);

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    /**
     *  跳轉到註冊介面
     * */
    public void CreateNew(View view){
        Intent intent = new Intent();
        intent.setClass(HomeActivity.this, CreateAccount.class);
        startActivity(intent);
    }

    /**
     *   按下登入後
     *   1.寫入登入資訊
     *   2.檢查連線模式, normal mode 或是 all -in-one mode
     * */
    public void SignIN(View view){

        /**  將各個editText的內容寫入current user inifo 檔案, 以供下次開啟APP自動預設**/
        EditText et_server_address = (EditText)findViewById(R.id.home_serverAddr);
        EditText et_account = (EditText)findViewById(R.id.home_account);
        EditText et_password = (EditText)findViewById(R.id.home_password);
        final String serverAddr = et_server_address.getText().toString();
        final String account = et_account.getText().toString();
        final String password = et_password.getText().toString();

        /** 判斷是否所有欄位皆填入*/
        if("".compareTo(account)==0 || "".compareTo(password)==0 || "".compareTo(serverAddr)==0){
            Toast.makeText(getApplicationContext(), "請確實輸入所有欄位!", Toast.LENGTH_SHORT).show();
        }else{
            String[] userinfo = new String[3];
            userinfo[0] = serverAddr;
            userinfo[1] = account;
            userinfo[2] = password;

            /** 將登入資訊寫出檔案*/
            try {
                FileOutputStream fos = HomeActivity.this.openFileOutput("current_user_info", Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(userinfo);
                oos.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                /** 宣告WebSocketClient*/
                NetworkClientHandler.setNetworkClient(serverAddr, account, password);
                NetworkClientHandler.networkClient.setActivity(HomeActivity.this);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /** 整理登入訊息*/
                Gson gson = new Gson();
                Message message = new Message();
                message.setTAG("LOGIN");
                message.setAccount(account);
                message.setPassword(password);
                String gsonStr = gson.toJson(message);

                /** 送出登入訊息*/
                NetworkClientHandler.networkClient.webSocketClient.send(gsonStr);
            }
        }).start();

    }


}
