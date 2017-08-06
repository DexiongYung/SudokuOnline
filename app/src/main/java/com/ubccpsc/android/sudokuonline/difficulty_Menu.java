package com.ubccpsc.android.sudokuonline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Dylan on 2017-08-05.
 */

public class difficulty_Menu extends AppCompatActivity implements View.OnClickListener {
    private Button back_button;
    private Button extremely_easy_button;
    private Button easy_button;
    private Button medium_button;
    private Button difficult_button;
    private Button evil_button;

    @Override
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);

        back_button = (Button) findViewById(R.id.back_button);
        extremely_easy_button = (Button) findViewById(R.id.extremely_easy_button);
        easy_button = (Button) findViewById(R.id.easy_button);
        medium_button = (Button) findViewById(R.id.medium_button);
        difficult_button = (Button) findViewById(R.id.difficult_button);
        evil_button = (Button) findViewById(R.id.evil_button);
    }

    @Override
    public void onClick(View v) {
        if(v == back_button){
            Intent myIntent = new Intent(this, StartMenu.class);
            startActivity(myIntent);
        }
        else if(v == extremely_easy_button){
            //!!!
        }
        else if(v == easy_button){
            //!!!
        }
        else if(v == medium_button){
            //!!!
        }
        else if(v == difficult_button){
            //!!!
        }
        else if(v == evil_button){
            //!!!
        }
    }
}
