package com.redes.japo.updmulticast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class GameControl extends AppCompatActivity {

    private Button stop_btn;
    private ImageButton duck1_btn,duck2_btn,duck3_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_control);

        stop_btn = (Button) findViewById(R.id.stopGame_btn);
        duck1_btn = (ImageButton) findViewById(R.id.duck1_btn);
        duck2_btn = (ImageButton) findViewById(R.id.duck2_btn);
        duck3_btn = (ImageButton) findViewById(R.id.duck3_btn);

        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create Message Object & Send
                ContentMessage tempMessage = new ContentMessage('c', 'a',2,0);
                CommunicationManager.getInstance().sendMessage(tempMessage);
            }
        });

      duck1_btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              //Create Message Object & Send
              ContentMessage tempMessage = new ContentMessage('c',1,1,0);
              CommunicationManager.getInstance().sendMessage(tempMessage);
          }
      });
        duck2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create Message Object & Send
                ContentMessage tempMessage = new ContentMessage('c',2,1,0);
                CommunicationManager.getInstance().sendMessage(tempMessage);
            }
        });
        duck3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create Message Object & Send
                ContentMessage tempMessage = new ContentMessage('c',3,1,0);
                CommunicationManager.getInstance().sendMessage(tempMessage);
            }
        });
    }
}
