package com.ubccpsc.android.sudokuonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import SudokuGenerator.GameEngine;
import SudokuGenerator.Objects.CountUpTimer;
import View.SudokuGrid.SudokuCell;

/**
 * Created by Dylan on 2017-08-07.
 */

public class Grid extends AppCompatActivity implements View.OnClickListener {
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    public static final String PREFS_NAME = "UI_File";
    private TextView timer;


    @Override
    protected void onCreate(Bundle savedStateInstance){
        pageCreation();
        super.onCreate(savedStateInstance);
        setContentView(R.layout.grid);
        mDrawerList = (ListView)findViewById(R.id.navList);
        timer = (TextView) findViewById(R.id.Timer);

        // Set the adapter for the list view
        addDrawerItems();

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:{
                        Intent myIntent = new Intent(Grid.this, StartMenu.class);
                        startActivity(myIntent);
                        break;}
                    case 1:{

                        break;}
                    case 2:{
                        break;}
                    case 3:{
                        startActivity(new Intent(Grid.this,Grid.class));
                        finish();
                        break;}
                    case 4:{
                        GameEngine.getInstance().clearGrid();
                        break;}
                }
            }
        });

        Intent myIntent = getIntent();
        int level = myIntent.getIntExtra("level", 1);

        if (level != -1)
            GameEngine.getInstance().createGrid(this, level);
        else {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("grid_file"));
                SudokuCell[][] file = (SudokuCell[][]) ois.readObject();
                GameEngine.getInstance().setGrid(this, file);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        new CountUpTimer(1000) {
            public void onTick(long n){
                String seconds = "" + ((n / 1000) % 60);
                if (((n / 1000) % 60) < 10)
                    seconds = 0 + seconds;

                timer.setText((int) (n / 60000) + ":" + seconds);
                timer.setTextSize(35);
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onPause() {
        String time = timer.getText().toString();
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    private void save() throws IOException {
        String fileName = "grid_file";
        SudokuCell[][] file = GameEngine.getInstance().getGameGrid().getSudokuCellGrid();
        FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
        //fos.write();
        //fos.close();
    }

    //TODO
    //Add animation to notify user of existence
    private void addDrawerItems() {
        String[] osArray = { "Home", "Increase Difficulty", "Decrease Difficulty", "New Game", "Clear Grid" };
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
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
}
