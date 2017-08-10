package View.SudokuGrid;

import android.content.Context;

/**
 * Created by Adi on 2017-08-07.
 */

public class GameGrid {
    private SudokuCell[][] Sudoku = new SudokuCell[9][9];

    public GameGrid(Context context){
        for(int x = 0; x<9; x++){
            for (int y = 0; y<9; y++){
                Sudoku[x][y] = new SudokuCell(context);
            }
        }
    }

    public void setGrid(int[][] grid){
        for(int x = 0; x<9; x++){
            for (int y = 0; y<9; y++){
                Sudoku[x][y].setValue(grid[x][y]);
            }
        }
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
}
