package com.ubccpsc.android.sudokuonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartMenu extends AppCompatActivity implements View.OnClickListener{
    private Button single_player_button;
    private Button multiplayer_button;
    private Button achievement_button;
    private TextView appname;
    public static final String PREFS_NAME = "UI_File";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pageCreation();
        super.onCreate(savedInstanceState);
        //Remove External resources on deploy
        setContentView(R.layout.activity_start_menu);
        //Instantiate buttons
        single_player_button = (Button) findViewById(R.id.single_player_button);
        multiplayer_button = (Button) findViewById(R.id.multiplayer_button);
        achievement_button = (Button) findViewById(R.id.achievement_button);

        //Instantiate fonts and apply to title
        appname = (TextView) findViewById(R.id.app_name);
        Typeface font = Typeface.createFromAsset(getAssets(),"Last King Quest.ttf");
        appname.setTypeface(font);

        //Set listeners for buttons
        single_player_button.setOnClickListener(this);
        multiplayer_button.setOnClickListener(this);
        achievement_button.setOnClickListener(this);

    }
    //Refresh page after back button is pressed
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    //On click listeners
    @Override
    public void onClick(View v) {
        if(v == single_player_button){
            Intent myIntent = new Intent(this, difficulty_Menu.class);
            startActivity(myIntent);
        }
        else if(v == multiplayer_button){
            Intent myIntent = new Intent(this, SignInActivity.class);
            startActivity(myIntent);
        } else if (v == achievement_button) {
            Intent myIntent = new Intent(this, achievement_Menu.class);
            startActivity(myIntent);
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
