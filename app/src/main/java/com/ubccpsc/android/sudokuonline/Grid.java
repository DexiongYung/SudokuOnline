package com.ubccpsc.android.sudokuonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.Arrays;

import SudokuGenerator.GameEngine;
import SudokuGenerator.Objects.CountUpTimer;

/**
 * Created by Dylan on 2017-08-07.
 */

public class Grid extends AppCompatActivity implements View.OnClickListener {
    public static final String PREFS_NAME = "UI_File";
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private TextView timer;
    private CountUpTimer oCountUpTimer;


    @Override
    protected void onCreate(Bundle savedStateInstance){
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
                    //save game capability
                    case 1:{
                        saveGame();
                        break;}
                    case 3:{
                        break;}
                    case 4:{
                        startActivity(new Intent(Grid.this,Grid.class));
                        finish();
                        break;}
                    case 5:{
                        GameEngine.getInstance().clearGrid();
                        break;}
                }
            }
        });

        Intent myIntent = getIntent();
        int level = myIntent.getIntExtra("level", 0);

        if (level > 0) {
            GameEngine.getInstance().createGrid(this, level);
        } else {
            Bundle extras = getIntent().getExtras();
            String openedGrid;
            String openedPositions;
            if (extras != null) {
                openedGrid = extras.getString("showSavedGrid");
                openedPositions = extras.getString("showSavedPositions");
                if (openedGrid != null) {
                    try {
                        JSONArray newJArray = new JSONArray(openedGrid);
                        JSONArray newPositionsArray = new JSONArray(openedPositions);
                        int[][] transformedArr = new int[9][9];
                        boolean[][] positionsArr = new boolean[9][9];
                        for (int j = 0; j < newJArray.length(); j++) {
                            for (int k = 0; k < newJArray.getJSONArray(j).length(); k++) {
                                positionsArr[j][k] = newPositionsArray.getJSONArray(j).getBoolean(k);
                                if (newPositionsArray.getJSONArray(j).getBoolean(k))
                                    transformedArr[j][k] = newJArray.getJSONArray(j).getInt(k);
                                else
                                    transformedArr[j][k] = 0;
                            }
                        }

                        GameEngine.getInstance().getGameGrid().setGrid(transformedArr);

                        for (int x = 0; x < 9; x++) {
                            for (int y = 0; y < 9; y++) {
                                if (!newPositionsArray.getJSONArray(x).getBoolean(y))
                                    GameEngine.getInstance().getGameGrid().setItem(x, y, newJArray.getJSONArray(x).getInt(y));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        oCountUpTimer = new CountUpTimer(1000) {
            public void onTick(long n){
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
    protected void onResume() {
        if (oCountUpTimer.getPaused())
            oCountUpTimer.resume();
        super.onResume();
    }

    //TODO
    //Add animation to notify user of existence
    private void addDrawerItems() {
        String[] osArray = { "Home", "Save Game", "Increase Difficulty", "Decrease Difficulty", "New Game", "Clear Grid" };
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }

    public void pageCreation(){
        // Set UI preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String ui_interface = settings.getString("user_ui", "default");
    }

    private void saveGame() {
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        //turn the grid into a string value, then save
        String stringGrid = Arrays.toString(GameEngine.getInstance().getGameGrid().getGrid());
        int[][] firstArr = GameEngine.getInstance().getGameGrid().getGrid();
        boolean[][] oInitialValues = new boolean[9][9];

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                oInitialValues[x][y] = !GameEngine.getInstance().getGameGrid().getSudokuCellGrid()[x][y].isModifiable();
            }
        }

        JSONArray mainArr = new JSONArray();
        JSONArray positionArr = new JSONArray();

        for (int r = 0; r < firstArr.length; r++) {
            JSONArray innerArr = new JSONArray();
            for (int s = 0; s < firstArr[r].length; s++) {
                innerArr.put(firstArr[r][s]);
            }
            mainArr.put(innerArr);
        }

        for (int r = 0; r < oInitialValues.length; r++) {
            JSONArray innerArr = new JSONArray();
            for (int s = 0; s < oInitialValues[r].length; s++) {
                innerArr.put(oInitialValues[r][s]);
            }
            positionArr.put(innerArr);
        }

        String convertedArr = mainArr.toString();
        String convertedPositionArr = positionArr.toString();
        editor.putString("savedGrid", convertedArr);
        editor.putString("savedPositions", convertedPositionArr);
        // Commit the edits!
        editor.commit();
    }
}
