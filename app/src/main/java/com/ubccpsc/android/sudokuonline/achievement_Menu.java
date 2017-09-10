package com.ubccpsc.android.sudokuonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by nikrolas on 2017-08-25.
 */

public class achievement_Menu extends AppCompatActivity implements View.OnClickListener {
    public static final String PREFS_NAME = "UI_File";
    private ImageButton extremely_easy_button;
    private ImageButton easy_button;
    private ImageButton medium_button;
    private ImageButton difficult_button;
    private ImageButton evil_button;
    private LinearLayout background;
    private TextView extremely_easy_text;
    private TextView easy_text;
    private TextView medium_text;
    private TextView difficult_text;
    private TextView evil_text;



    @Override
    protected void onCreate(Bundle savedStateInstance){
        //Set Theme
        pageCreation();
        super.onCreate(savedStateInstance);
        setContentView(R.layout.achievement_menu);


        //Instantiate background
        background = (LinearLayout) findViewById(R.id.achievement_background);
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
        //Checking and updating text and button if unlocked
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        unlock(settings);

        //Checking and updating buttons if unlocked


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
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("user_ui", "extremely_easy");
            // Commit the edits!
            editor.commit();
        }
        else if(v == easy_button){
            // TODO: 2017-08-25
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("user_ui", "easy");
            // Commit the edits!
            editor.commit();
        }
        else if(v == medium_button){
            // TODO: 2017-08-25
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("user_ui", "medium");
            // Commit the edits!
            editor.commit();
        }
        else if(v == difficult_button){
            // TODO: 2017-08-25
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("user_ui", "difficult");
            // Commit the edits!
            editor.commit();
        }
        else if(v == evil_button){
            // TODO: 2017-08-25
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("user_ui", "evil");
            // Commit the edits!
            editor.commit();
        }
        this.recreate();
    }
    public void pageCreation(){
        // Set UI preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String ui_interface = settings.getString("user_ui", "default");
        if (ui_interface.equals("extremely_easy")) {
            setTheme(R.style.ExtremelyEasy);
        }
        else if (ui_interface.equals("easy")) {
            //TODO
            setTheme(R.style.Holo_Dark);

        }
        else if (ui_interface.equals("medium")) {
            //TODO
            setTheme(R.style.Holo_Light);

        }
        else if (ui_interface.equals("difficult")) {
            //TODO
            setTheme(R.style.Material_dark);

        }
        else if (ui_interface.equals("evil")) {
            //TODO
        }
    }
    private void unlock(SharedPreferences settings){
        if (settings.getBoolean("extremely_easy_unlock", false)){
            extremely_easy_text.setText("Unlocked ___ theme");
            extremely_easy_button.setClickable(true);
            extremely_easy_button.setImageResource(android.R.drawable.ic_partial_secure);
        }
        else if(settings.getBoolean("easy_unlock", false)) {
            easy_text.setText("Unlocked ___ theme");
            easy_button.setClickable(true);
            easy_button.setImageResource(android.R.drawable.ic_partial_secure);
        }
        else if(settings.getBoolean("extremely_easy_unlock", false)) {
            medium_text.setText("Unlocked ___ theme");
            medium_button.setClickable(true);
            medium_button.setImageResource(android.R.drawable.ic_partial_secure);
        }
        else if(settings.getBoolean("difficult_unlock", false)) {
            difficult_text.setText("Unlocked ___ theme");
            difficult_button.setImageResource(android.R.drawable.ic_partial_secure);
        }
        else if(settings.getBoolean("evil_unlock", false)) {
            evil_text.setText("Unlocked ___ theme");
            evil_button.setClickable(true);
            evil_button.setImageResource(android.R.drawable.ic_partial_secure);
        }
    }
}
