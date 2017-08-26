package com.ubccpsc.android.sudokuonline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by nikrolas on 2017-08-25.
 */

public class achievement_Menu extends AppCompatActivity implements View.OnClickListener {
    private ImageButton extremely_easy_button;
    private ImageButton easy_button;
    private ImageButton medium_button;
    private ImageButton difficult_button;
    private ImageButton evil_button;
    private TextView extremely_easy_text;
    private TextView easy_text;
    private TextView medium_text;
    private TextView difficult_text;
    private TextView evil_text;



    @Override
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.achievement_menu);

        //Instantiating buttons
        extremely_easy_button = (ImageButton) findViewById(R.id.extremelyeasy_achievement);
        easy_button = (ImageButton) findViewById(R.id.easy_achievement);
        medium_button = (ImageButton) findViewById(R.id.medium_achievement);
        difficult_button = (ImageButton) findViewById(R.id.difficult_achievement);
        evil_button = (ImageButton) findViewById(R.id.evil_achievement);

        //Instantiating text
        extremely_easy_text = (TextView) findViewById(R.id.extremelyeasy_achievement_text);
        easy_text = (TextView) findViewById(R.id.easy_achievement_text);
        medium_text = (TextView) findViewById(R.id.medium_achievement_text);
        difficult_text = (TextView) findViewById(R.id.difficult_achievement_text);
        evil_text = (TextView) findViewById(R.id.evil_achievement_text);


        //Setting listeners to buttons
        extremely_easy_button.setOnClickListener(this);
        easy_button.setOnClickListener(this);
        medium_button.setOnClickListener(this);
        difficult_button.setOnClickListener(this);
        evil_button.setOnClickListener(this);
    }

    //Click listener
    @Override
    public void onClick(View v) {
        if(v == extremely_easy_button){
            // TODO: 2017-08-25
        }
        else if(v == easy_button){
            // TODO: 2017-08-25

        }
        else if(v == medium_button){
            // TODO: 2017-08-25

        }
        else if(v == difficult_button){
            // TODO: 2017-08-25

        }
        else if(v == evil_button){
            // TODO: 2017-08-25

        }
    }
}
