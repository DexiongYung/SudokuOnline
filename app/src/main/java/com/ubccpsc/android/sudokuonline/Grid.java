package com.ubccpsc.android.sudokuonline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Random;

import SudokuGenerator.GameEngine;

/**
 * Created by Dylan on 2017-08-07.
 */

public class Grid extends AppCompatActivity implements View.OnClickListener {
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.grid);
        mDrawerList = (ListView)findViewById(R.id.navList);

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
        int level = myIntent.getIntExtra("level", 0);

        Random randomGenerator = new Random();
        int numRemoved = 0;

        switch(level) {
            case 0:{
                numRemoved = 81 - (randomGenerator.nextInt(60 - 50) + 50);
                break;}
            case 1:{
                numRemoved = 81 - (randomGenerator.nextInt(49 - 36) + 36);
                break;}
            case 2:{
                numRemoved = 81 - (randomGenerator.nextInt(35 - 32) + 32);
                break;}
            case 3:{
                numRemoved = 81 - (randomGenerator.nextInt(31 - 28) + 28);
                break;}
            case 4:{
                numRemoved = 81 - (randomGenerator.nextInt(27 - 22) + 22);
                break;}
        }

        GameEngine.getInstance().createGrid(this, numRemoved);
    }

    @Override
    public void onClick(View v) {
    }

    private void addDrawerItems() {
        String[] osArray = { "Home", "Increase Difficulty", "Decrease Difficulty", "New Game", "Clear Grid" };
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }
}
