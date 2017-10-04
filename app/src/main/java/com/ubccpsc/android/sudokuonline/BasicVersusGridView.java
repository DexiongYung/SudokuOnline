package com.ubccpsc.android.sudokuonline;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import SudokuGenerator.Objects.CountUpTimer;

/**
 * Created by Dylan on 2017-08-07.
 */

public class BasicVersusGridView extends Activity implements View.OnClickListener {
    public static final String PREFS_NAME = "UI_File";
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private TextView timer;
    private CountUpTimer oCountUpTimer;
    private DrawerLayout mDrawerLayout;
    private int level;

    @Override
    protected void onCreate(Bundle savedStateInstance) {
        pageCreation();
        super.onCreate(savedStateInstance);
        setContentView(R.layout.basicversusgrid);
        mDrawerList = (ListView) findViewById(R.id.navList);
        timer = (TextView) findViewById(R.id.Timer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Set the adapter for the list view
        addDrawerItems();

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        break;
                    }
                    case 1: {

                        break;
                    }
                }
            }
        });

        Intent myIntent = getIntent();
        if (myIntent.getIntExtra("level", 1) < 1) {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            this.level = settings.getInt("level", 1);
        } else {
            this.level = myIntent.getIntExtra("level", 1);
        }

        oCountUpTimer = new CountUpTimer(1000) {
            public void onTick(long n) {
                String seconds = "" + ((n / 1000) % 60);
                if (((n / 1000) % 60) < 10)
                    seconds = 0 + seconds;
                timer.setText((int) (n / 60000) + ":" + seconds);
                timer.setTextSize(35);
            }
        };
        oCountUpTimer.start();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onPause() {
        oCountUpTimer.pause();
        saveGame();
        super.onPause();
    }

    @Override
    protected void onStop() {
        saveGame();
        super.onStop();
    }

    @Override
    protected void onResume() {
        if (oCountUpTimer.getPaused())
            oCountUpTimer.resume();
        super.onResume();
    }

    private void addDrawerItems() {
        String[] osArray = {"Home", "Surrender"};
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }

    public void pageCreation() {
        // Set UI preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String ui_interface = settings.getString("user_ui", "default");
        if (ui_interface.equals("extremely_easy")) {
            setTheme(R.style.Holo_Dark);
        } else if (ui_interface.equals("easy")) {
            //TODO
            setTheme(R.style.Material_Dark);
        } else if (ui_interface.equals("medium")) {
            //TODO
            setTheme(R.style.Material_Light);

        } else if (ui_interface.equals("difficult")) {
            //TODO
            setTheme(R.style.Holo_Light);

        } else if (ui_interface.equals("evil")) {
            //TODO
            setTheme(R.style.Nicky);
        }
    }


    private void saveGame() {
        //Save to Firebase server
    }

    private void pullPreviousGrid() {
        //pull from firebase if does not exist then create new of level
    }
}

