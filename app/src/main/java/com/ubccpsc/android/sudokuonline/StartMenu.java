package com.ubccpsc.android.sudokuonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartMenu extends AppCompatActivity implements View.OnClickListener{
    private Button single_player_button;
    private Button multiplayer_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        single_player_button = (Button) findViewById(R.id.single_player_button);
        multiplayer_button = (Button) findViewById(R.id.multiplayer_button);
    }

    @Override
    public void onClick(View v) {
        if(v == single_player_button){
            Intent myIntent = new Intent(this, difficulty_Menu.class);
            startActivity(myIntent);
        }
        else if(v == multiplayer_button){
            //!!!
        }
    }
}
