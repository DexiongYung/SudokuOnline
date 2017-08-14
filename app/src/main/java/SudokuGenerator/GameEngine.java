package SudokuGenerator;

import android.content.Context;

import java.util.Stack;

import View.SudokuGrid.GameGrid;
import View.SudokuGrid.SudokuCell;

/**
 * Created by Dylan on 2017-08-07.
 */

public class GameEngine {
    private static GameEngine instance;
    private GameGrid grid = null;
    private int selectedPosX = -1, selectedPosY = -1;

    private GameEngine(){}

    public static GameEngine getInstance(){
        if(instance == null)
            instance = new GameEngine();

        return instance;
    }

    public void createGrid(Context context, int numberRemoved){
        int[] Sudoku = SudokuGenerator.getInstance().generateGrid();
        grid = new GameGrid(context);
        int[][] actualGrid = convert1DTo2D(Sudoku);
        actualGrid = SudokuGenerator.getInstance().removeElements(actualGrid, numberRemoved);
        grid.setGrid(actualGrid);
    }

    public GameGrid getGrid(){
        return grid;
    }

    public void setSelectedPosition(int x, int y){
        selectedPosX = x;
        selectedPosY = y;
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

    public void setNumber(int number) {
        if (selectedPosX != -1 && selectedPosY != -1) {
            grid.setItem(selectedPosX, selectedPosY, number);

        }
        grid.checkGame();
    }
}
