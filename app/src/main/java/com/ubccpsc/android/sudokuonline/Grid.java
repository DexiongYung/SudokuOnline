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

        int[] Sudoku = SudokuGenerator.getInstance().generateGrid();
        int [][] actual_grid = convert1DTo2D(Sudoku);
        GameEngine.getInstance().setSudoku(actual_grid);

    }

    @Override
    public void onClick(View v) {

    }

    public int[][] convert1DTo2D(int[] arr){
        int[][] new2D = new int[9][9];
        int index = 0;
        for(int x = 0 ; x < 9 ; x++){
            for(int y = 0 ; y < 9 ; y++){
                new2D[x][y] = arr[index];
                index++;
            }
        }

        return new2D;
    }
}
