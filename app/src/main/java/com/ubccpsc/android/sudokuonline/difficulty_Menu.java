package com.ubccpsc.android.sudokuonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Dylan on 2017-08-05.
 */

public class difficulty_Menu extends AppCompatActivity implements View.OnClickListener {
    public static final String PREFS_NAME = "UI_File";
    private Button extremely_easy_button;
    private Button easy_button;
    private Button medium_button;
    private Button difficult_button;
    private Button evil_button;
    private Button continue_button;

    @Override
    protected void onCreate(Bundle savedStateInstance) {
        //Theme creation
        pageCreation();
        super.onCreate(savedStateInstance);
        setContentView(R.layout.difficulty_selection);

        //Instantiating buttons
        extremely_easy_button = (Button) findViewById(R.id.extremely_easy_button);
        easy_button = (Button) findViewById(R.id.easy_button);
        medium_button = (Button) findViewById(R.id.medium_button);
        difficult_button = (Button) findViewById(R.id.difficult_button);
        evil_button = (Button) findViewById(R.id.evil_button);
        continue_button = (Button) findViewById(R.id.continue_button);

        //Setting listeners to buttons
        extremely_easy_button.setOnClickListener(this);
        easy_button.setOnClickListener(this);
        medium_button.setOnClickListener(this);
        difficult_button.setOnClickListener(this);
        evil_button.setOnClickListener(this);
        continue_button.setOnClickListener(this);
    }

    //Click listener
    @Override
    public void onClick(View v) {
        if(v == extremely_easy_button){
            Intent myIntent = new Intent(this, Grid.class);
            myIntent.putExtra("level", 1);
            startActivity(myIntent);
        } else if (v == easy_button) {
            Intent myIntent = new Intent(this, Grid.class);
            myIntent.putExtra("level", 2);
            startActivity(myIntent);
        } else if (v == medium_button) {
            Intent myIntent = new Intent(this, Grid.class);
            myIntent.putExtra("level", 3);
            startActivity(myIntent);
        } else if (v == difficult_button) {
            Intent myIntent = new Intent(this, Grid.class);
            myIntent.putExtra("level", 4);
            startActivity(myIntent);
        } else if (v == evil_button) {
            Intent myIntent = new Intent(this, Grid.class);
            myIntent.putExtra("level", 5);
            startActivity(myIntent);
        } else if (v == continue_button) {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            boolean bSaveGameExist = settings.getBoolean("saveGame", false);

            if (bSaveGameExist) {
                Intent myIntent = new Intent(this, Grid.class);
                myIntent.putExtra("level", 0);
                startActivity(myIntent);
            }
        }
    }

    public void pageCreation(){
        // Set UI preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String ui_interface = settings.getString("user_ui", "default");
        if (ui_interface.equals("extremely_easy")) {
            setTheme(R.style.Holo_Dark);
        }
        else if (ui_interface.equals("easy")) {
            //TODO
            setTheme(R.style.Material_Dark);
        }
        else if (ui_interface.equals("medium")) {
            //TODO
            setTheme(R.style.Material_Light);

        }
        else if (ui_interface.equals("difficult")) {
            //TODO
            setTheme(R.style.Holo_Light);

        }
        else if (ui_interface.equals("evil")) {
            //TODO
            setTheme(R.style.Nicky);
        }
    }

}
