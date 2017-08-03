package SudokuGenerator;

import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by Dylan on 2017-08-03.
 */

public class SudokuGenerator {
    private static SudokuGenerator instance;
    private ArrayList<Integer> available = new ArrayList<Integer>();
    public int gridDimensions = 9;

    private SudokuGenerator(){}

    public static SudokuGenerator getInstance(){
        if(instance == null)
            instance = new SudokuGenerator();

        return instance;
    }

    public int[][] generateGrid(){
        int[][] sudokuGrid = new int[gridDimensions][gridDimensions];

        int currentPos = 0;

        while(currentPos < gridDimensions * gridDimensions){

        }

        return sudokuGrid;
    }

    private void clearGrid(int[][] sudokuGrid){
        available.clear();

        for(int i = 0 ; i < gridDimensions ; i++){
            for(int m = 0 ; m < gridDimensions ; m++){
                sudokuGrid[i][m] = -1;
            }
        }
    }
}
