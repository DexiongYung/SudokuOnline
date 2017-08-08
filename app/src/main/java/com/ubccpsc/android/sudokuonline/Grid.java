package com.ubccpsc.android.sudokuonline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import SudokuGenerator.SudokuGenerator;
import SudokuGenerator.GameEngine;

/**
 * Created by Dylan on 2017-08-07.
 */

public class Grid extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.grid);

        GameEngine.getInstance().createGrid(this);

    }

    @Override
    public void onClick(View v) {

    }
}
