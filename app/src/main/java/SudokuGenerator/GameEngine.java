package SudokuGenerator;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import View.SudokuGrid.GameGrid;

/**
 * Created by Dylan on 2017-08-07.
 */

public class GameEngine {
    private static GameEngine instance;
    private GameGrid grid = null;
    private int selectedPosX = -1, selectedPosY = -1;
    private boolean draftMode;
    private Stack<xyStorage> undoStorage = new Stack<xyStorage>();
    private Stack<xyStorage> redoStorage = new Stack<xyStorage>();

    public GameEngine() {
    }

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
        draftMode = false;
        grid.setGrid(actualGrid);
    }

    private int[][] convert1DTo2D(int[] arr) {
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
            undoStorage.push(new xyStorage(selectedPosX, selectedPosY));
            redoStorage.empty();
        }
        grid.checkGame();
    }

    public void undoFunction() {
        if (!undoStorage.isEmpty()) {
            xyStorage temp = undoStorage.pop();
            grid.getGrid()[temp.getX()][temp.getY()].undo();
        }
    }

    public boolean undoStorageEmpty() {
        return undoStorage.isEmpty();
    }

    public boolean redoStorageEmpty() {
        return redoStorage.isEmpty();
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

    public ArrayList<Integer> getRegionToHighlight(int position) {
        ArrayList<Integer> region1 = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 9, 10, 11, 18, 19, 20));
        ArrayList<Integer> region2 = new ArrayList<Integer>(Arrays.asList(3, 4, 5, 12, 13, 14, 21, 22, 23));
        ArrayList<Integer> region3 = new ArrayList<Integer>(Arrays.asList(6, 7, 8, 15, 16, 17, 24, 25, 26));
        ArrayList<Integer> region4 = new ArrayList<Integer>(Arrays.asList(27, 28, 29, 36, 37, 38, 45, 46, 47));
        ArrayList<Integer> region5 = new ArrayList<Integer>(Arrays.asList(30, 31, 32, 39, 40, 41, 48, 49, 50));
        ArrayList<Integer> region6 = new ArrayList<Integer>(Arrays.asList(33, 34, 35, 42, 43, 44, 51, 52, 53));
        ArrayList<Integer> region7 = new ArrayList<Integer>(Arrays.asList(54, 55, 56, 63, 64, 65, 72, 73, 74));
        ArrayList<Integer> region8 = new ArrayList<Integer>(Arrays.asList(57, 58, 59, 66, 67, 68, 75, 76, 77));
        ArrayList<Integer> region9 = new ArrayList<Integer>(Arrays.asList(60, 61, 62, 69, 70, 71, 78, 79, 80));

        ArrayList<Integer>[] storage = new ArrayList[]{region1, region2, region3, region4, region5, region6, region7, region8, region9};

        for (int i = 0; i < storage.length; i++) {
            if (storage[i].contains(position))
                return storage[i];
        }

        return null;
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

    public ArrayList<Integer> getDuplicatesToHighlight(int position) {
        ArrayList<Integer> duplicatesHolder = new ArrayList<>();

        for (int i = 0; i < 81; i++) {
            if (grid.getItem(i).getValue() == grid.getItem(position).getValue()) {
                duplicatesHolder.add(i);
            }
        }

        return duplicatesHolder;
    }

    public class xyStorage {
        private int x;
        private int y;

        public xyStorage(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getY() {
            return y;
        }

        public int getX() {
            return x;
        }
    }
}
