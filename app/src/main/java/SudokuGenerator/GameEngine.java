package SudokuGenerator;

import android.content.Context;

import View.SudokuGrid.GameGrid;

/**
 * Created by Dylan on 2017-08-07.
 */

public class GameEngine {
    private static GameEngine instance;
    private GameGrid grid = null;
    private int selectedPosX = -1, selectedPosY = -1;
    private boolean draftMode = false;

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

    public GameGrid getGrid() {
        return grid;
    }

    public void setSelectedPosition(int x, int y) {
        selectedPosX = x;
        selectedPosY = y;
    }

    public void setNumber(int number) {
        if (selectedPosX != -1 && selectedPosY != -1) {
            grid.setItem(selectedPosX, selectedPosY, number);

        }
        grid.checkGame();
    }

    public void draftModeSetter() {
        draftMode = !draftMode;
    }

    public boolean getDraftModeSetting() {
        return draftMode;
    }

    public int[] getRowToHighlight(int position){
        int[] rowArray = new int[9];
        int row = position / 9;
        int start = row * 9;

        for(int i = 0; i<rowArray.length; i++){
            rowArray[i] = start;
            start++;
        }

        return rowArray;
    }

    public int[] getColumnToHighlight(int position){
        int[] rowArray = new int[9];
        int col = position % 9;

        for(int i = 0; i<rowArray.length; i++){
            rowArray[i] = col;
            col+= 9;
        }

        return rowArray;
    }

}
