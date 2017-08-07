package SudokuGenerator;

/**
 * Created by Dylan on 2017-08-07.
 */

public class GameEngine {
    private static GameEngine instance;
    private int[][] Sudoku;

    private GameEngine(){}

    public static GameEngine getInstance(){
        if(instance == null)
            instance = new GameEngine();

        return instance;
    }

    public int[][] getSudoku(){
        return Sudoku;
    }

    public void setSudoku(int[][] sudoku) {this.Sudoku = sudoku;}
}
