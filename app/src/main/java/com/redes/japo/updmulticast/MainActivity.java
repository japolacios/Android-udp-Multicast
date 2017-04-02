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
        //CommunicationManager.getInstance();
        //Observe Com Instance
        CommunicationManager.getInstance().addObserver(this);
        //--------------------------------------------------------------
        WifiManager wifi = (WifiManager) getApplicationContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi != null){
            WifiManager.MulticastLock lock = wifi.createMulticastLock("HelloAndroid");
            lock.acquire();
        }

        next_btn = (Button) findViewById(R.id.start_btn);
        next_btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){

                Intent myIntent = new Intent(view.getContext(), GameControl.class);
                startActivityForResult(myIntent, 0);


                //--------------------------------------------------------------------------
                ContentMessage tempAutoId = new ContentMessage('c','a',3,0);
                CommunicationManager.getInstance().sendMessage(tempAutoId);
            }
        });


                //Close onCreate
    }


    @Override
    public void update(Observable observable, Object o) {

    }
}
