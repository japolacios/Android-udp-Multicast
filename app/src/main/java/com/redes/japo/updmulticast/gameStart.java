package com.redes.japo.updmulticast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class gameStart extends AppCompatActivity {

    private Button startGame_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

        startGame_btn = (Button) findViewById(R.id.startGame_btn);


        startGame_btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view) {
                Intent gameControl = new Intent (view.getContext(), GameControl.class); /** Class name here */
                startActivityForResult(gameControl, 0);
            }
        });


        //End onCreate Class
    }
}
