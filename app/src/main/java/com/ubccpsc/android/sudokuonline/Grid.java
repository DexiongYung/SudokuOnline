package com.ubccpsc.android.sudokuonline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Random;

import SudokuGenerator.GameEngine;

/**
 * Created by Dylan on 2017-08-07.
 */

public class Grid extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.grid);

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
}
