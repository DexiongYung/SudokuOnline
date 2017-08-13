package SudokuGenerator;

import android.content.Context;

import View.SudokuGrid.GameGrid;
import View.Objects.UndoRedoStorage;
import View.Objects.tuple3;
import View.SudokuGrid.SudokuCell;

/**
 * Created by Dylan on 2017-08-07.
 */

public class GameEngine {
    private static GameEngine instance;
    private GameGrid grid = null;
    private SudokuCell[][] temp = null;

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
        UndoRedoStorage.getInstance();
    }

    public GameGrid getGrid(){
        return grid;
    }

    public void setSelectedPosition(int x, int y){
        selectedPosX = x;
        selectedPosY = y;
    }

    public void deleteGrid(){
        for(int x = 0 ; x < 9 ; x++){
            for(int y = 0 ; y < 9 ; y++){
                grid.setItem(x , y , 0);
            }
        }

        this.temp = this.grid.getGrid();

        tuple3 t = new tuple3(-1, -1, -1);
        UndoRedoStorage.getInstance().addToUndo(t);
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

    public void redoSetter(){
        if(!UndoRedoStorage.getInstance().redoEmpty()){
            tuple3 temp = UndoRedoStorage.getInstance().callRedo();

            if(temp.getX_coordinate() == -1)
                grid.replaceGrid(this.temp);
            else
                grid.setItem(temp.getX_coordinate() , temp.getY_coordinate() , temp.getNumber());
        }
    }

    public void undoSetter(){
        if(!UndoRedoStorage.getInstance().undoEmpty()){
            tuple3 temp = UndoRedoStorage.getInstance().callUndo();

            if(temp.getX_coordinate() == -1)
                grid.replaceGrid(this.temp);
            else
                grid.setItem(temp.getX_coordinate(), temp.getY_coordinate(), 0);
        }
    }

    public void setNumber(int number){
        if(selectedPosX != -1 && selectedPosY != -1){
            grid.setItem(selectedPosX,selectedPosY,number);

            tuple3 v = new tuple3(selectedPosX, selectedPosY, number);
            UndoRedoStorage.getInstance().addToUndo(v);
        }
    }
}
