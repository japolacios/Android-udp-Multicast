package com.redes.japo.updmulticast;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private Button next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //--------------------------------------------------------------
        //Call Com Instance
        CommunicationManager.getInstance();
        //Observe Com Instance
        CommunicationManager.getInstance().addObserver(this);
        //--------------------------------------------------------------
        /*
        // Acquire multicast lock
        Context context =  getApplicationContext();
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiManager.MulticastLock multicastLock = wifi.createMulticastLock("multicastLock");
        multicastLock.setReferenceCounted(true);
        multicastLock.acquire();
        */
        next_btn = (Button) findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(view.getContext(), gameStart.class); /** Class name here */
                startActivityForResult(myIntent, 0);
            }
        });


                //Close onCreate
    }


    @Override
    public void update(Observable observable, Object o) {

    }
}
