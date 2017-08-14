package View.SudokuGrid;

import android.content.Context;
import android.widget.Toast;

import SudokuGenerator.SudokuChecker;

/**
 * Created by Adi on 2017-08-07.
 */

public class GameGrid {
    private SudokuCell[][] Sudoku = new SudokuCell[9][9];
    private int[][] SudokuArray = new int[9][9];
    private Context context;

    public GameGrid(Context context){
        this.context = context;
        for(int x = 0; x<9; x++){
            for (int y = 0; y<9; y++){
                Sudoku[x][y] = new SudokuCell(context);
            }
        }
    }

    public void setGrid(int[][] grid){
        for(int x = 0; x<9; x++){
            for (int y = 0; y<9; y++){
                Sudoku[x][y].setInitValue(grid[x][y]);

                if(grid[x][y] != 0) {
                    Sudoku[x][y].setNotModifiable();
                }
            }
        }
        SudokuArray = grid;
    }

    public SudokuCell[][] getGrid(){
        return Sudoku;
    }

    public SudokuCell getItem(int x, int y){ return Sudoku[x][y]; }

    public SudokuCell getItem(int position){
        int x = position % 9;
        int y = position / 9;

        return Sudoku[x][y];
    }

    public void setItem(int x, int y, int number){
        Sudoku[x][y].setValue(number);

        if (Sudoku[x][y].getValue() == -1)
            SudokuArray[x][y] = -1;
        else
            SudokuArray[x][y] = number;
    }

    public void checkGame(){
        if(SudokuChecker.getInstance().checkSudoku(SudokuArray))
            Toast.makeText(context, "You solved the Sudoku puzzle!", Toast.LENGTH_LONG).show();
    }
}
