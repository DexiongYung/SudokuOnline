package View.SudokuGrid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;

import com.ubccpsc.android.sudokuonline.StartMenu;

import SudokuGenerator.SudokuChecker;

/**
 * Created by Adi on 2017-08-07.
 */

public class GameGrid {
    private SudokuCell[][] Sudoku = new SudokuCell[9][9];
    private int[][] SudokuArray = new int[9][9];
    private Context context;
    private boolean solved = false;

    public GameGrid(Context context){
        this.context = context;
        for(int x = 0; x<9; x++){
            for (int y = 0; y<9; y++){
                Sudoku[x][y] = new SudokuCell(context);
            }
        }
    }

    public SudokuCell[][] getSudokuCellGrid() {
        return Sudoku;
    }

    public void setGrid(int[][] grid){
        SudokuArray = grid;

        for(int x = 0; x<9; x++){
            for (int y = 0; y<9; y++){
                Sudoku[x][y].setInitialValue(grid[x][y]);
                Sudoku[x][y].setBackgroundColor(Color.WHITE);
                Sudoku[x][y].setX(x);
                Sudoku[x][y].setY(y);

                if(grid[x][y] != 0) {
                    Sudoku[x][y].setNotModifiable();
                }
            }
        }
    }

    public SudokuCell getItem(int position) {
        int x = position % 9;
        int y = position / 9;

        return Sudoku[x][y];
    }

    public void setItem(int x, int y, int number){
        Sudoku[x][y].setValue(number);
        SudokuArray[x][y] = Sudoku[x][y].getValue();
    }

    public void checkGame(){
        if (SudokuChecker.getInstance().checkSudoku(SudokuArray)) {
            Toast.makeText(context, "CONGRATS! You solved the Sudoku puzzle!", Toast.LENGTH_LONG).show();

            //TODO
            //Maybe a celebration activity?
            context.startActivity(new Intent(context, StartMenu.class));
        }
    }
}
