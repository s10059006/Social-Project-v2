package com.example.minxuan.socialprojectv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.minxuan.socialprojectv2.ListviewAdapter.menulistadapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class Menupage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menupage);

        initview();
    }

    public void initview() {
        //設定Menupage顯示的Listview
        String[] name = {"收件夾","通訊錄","簡訊","多人簡訊","廣播簡訊","語音通話","直播視訊","視訊通話","個人資訊"};
        int[] pic = {R.drawable.inbox,R.drawable.contact,R.drawable.message,R.drawable.messagegroup,
                R.drawable.broadcasting,R.drawable.phone,R.drawable.videocall,R.drawable.singlevideocall,R.drawable.settings};
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<HashMap<String, Object>> Item = new ArrayList<HashMap<String, Object>>();
        for(int i=0; i<9; i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", pic[i]);
            map.put("ItemName", name[i]);
            Item.add(map);
        }
        menulistadapter adapter = new menulistadapter(
                this,
                Item,
                R.layout.menulistview,
                new String[] {"ItemImage","ItemName"},
                new int[] {R.id.ItemImage,R.id.ItemName}
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        MessageBox();   //收件夾
                        break;
                    case 1:
                        //contact();      //通訊錄
                        break;
                    case 2:
                        //messagesend();  //簡訊
                        break;
                    case 3:
                        //messagegroupsend(); //簡訊群播
                        break;
                    case 4:
                        //Broadcasting(); //簡訊廣播
                        break;
                    case 5:
                        //audionultitalk();   //語音通話
                        break;
                    case 6:
                        //videolive();    //視訊直播
                        break;
                    case 7:
                        //videocall();    //視訊通話
                        break;
                    case 8:
                        //MenuFragment2();    //MQTT
                        break;
                }
            }
        });
    }

    /** 收件夾*/
    public void MessageBox(){

        /** 宣告WebSocketClient*/
        NetworkClientHandler.networkClient.setActivity(Menupage.this);

        /** 整理要求簡訊之訊息*/
        Gson gson = new Gson();
        Message message = new Message();
        message.setTAG("MESSAGE_BOX");

    }
}
