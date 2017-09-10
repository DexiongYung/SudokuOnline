package View.SudokuGrid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ubccpsc.android.sudokuonline.StartMenu;

import SudokuGenerator.SudokuChecker;

/**
 * Created by Adi on 2017-08-07.
 */

public class GameGrid extends AppCompatActivity{
    public static final String PREFS_NAME = "UI_File";
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

    public void setSudokuCellGrid(SudokuCell[][] file) {
        this.Sudoku = file;
    }

    public int[][] getGrid() {
        return SudokuArray;
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

    public void checkGame(int level){
        if (SudokuChecker.getInstance().checkSudoku(SudokuArray)) {
            Toast.makeText(context, "CONGRATS! You solved the Sudoku puzzle!", Toast.LENGTH_LONG).show();
            if (level == 1) {
                setAchievement("extremely_easy_unlock");
            } else if (level == 2) {
                setAchievement("easy_unlock");
            } else if (level == 3) {
                setAchievement("medium_unlock");

            } else if (level == 4) {
                setAchievement("difficult_unlock");
            } else if (level == 5) {
                setAchievement("evil_unlock");
            }

            //Maybe a celebration activity?
            //Or stats boards
            //TODO
            context.startActivity(new Intent(context, StartMenu.class));
        }
    }
    //Setting achievement in application
    private void setAchievement(String difficulty) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(difficulty, true);
        // Commit the edits!
        editor.commit();
    }
}
