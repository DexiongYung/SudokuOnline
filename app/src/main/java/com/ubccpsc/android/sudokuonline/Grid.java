package com.ubccpsc.android.sudokuonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

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
    private DrawerLayout mDrawerLayout;
    private int level;

    @Override
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.grid);
        mDrawerList = (ListView) findViewById(R.id.navList);
        timer = (TextView) findViewById(R.id.Timer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

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
                        GameEngine.getInstance().clearGrid();
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

        if (myIntent.getIntExtra("level", 1) != 0) {
            GameEngine.getInstance().createGrid(this, level);
        } else {
            pullPreviousGrid();
        }

        checkIfFirstTime();
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
        String[] osArray = {"Home", "Clear Grid(Cannot Be Undone)"};
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }

    public void pageCreation(){
        // Set UI preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String ui_interface = settings.getString("user_ui", "default");
    }

    private void saveGame() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        //turn the grid into a string value, then save
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
        editor.putInt("level", this.level);
        editor.putLong("time", oCountUpTimer.getTime());
        editor.putBoolean("saveGame", true);
        // Commit the edits!
        editor.commit();
    }

    private void checkIfFirstTime() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean isFirstStart = settings.getBoolean("key", true);

        if (isFirstStart) {
            mDrawerLayout.openDrawer(Gravity.START);
            SharedPreferences.Editor e = settings.edit();
            e.putBoolean("key", false);
            e.commit();
        }
    }

    private void pullPreviousGrid() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        GameEngine.getInstance().continueGrid(this);
        String sPreviousGrid = settings.getString("savedGrid", "null");
        String sBooleanGrid = settings.getString("savedPositions", "null");
        if (!sPreviousGrid.equals("null") && !sBooleanGrid.equals("null")) {
            try {
                JSONArray oSavedGrid = new JSONArray(sPreviousGrid);
                JSONArray oBooleanGrid = new JSONArray(sBooleanGrid);
                int[][] oValueArray = new int[9][9];
                boolean[][] oBooleanArray = new boolean[9][9];
                for (int j = 0; j < oSavedGrid.length(); j++) {
                    for (int k = 0; k < oSavedGrid.getJSONArray(j).length(); k++) {
                        oBooleanArray[j][k] = oBooleanGrid.getJSONArray(j).getBoolean(k);
                        if (oBooleanArray[j][k])
                            oValueArray[j][k] = oSavedGrid.getJSONArray(j).getInt(k);
                        else
                            oValueArray[j][k] = 0;
                    }
                }
                GameEngine.getInstance().getGameGrid().setGrid(oValueArray);
                for (int x = 0; x < 9; x++) {
                    for (int y = 0; y < 9; y++) {
                        if (!oBooleanArray[x][y])
                            GameEngine.getInstance().getGameGrid().setItem(x, y, oSavedGrid.getJSONArray(x).getInt(y));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Error", "exception", e);
            }
        }
        Long time = settings.getLong("time", 0);
        oCountUpTimer.setTime(time);
    }
}
