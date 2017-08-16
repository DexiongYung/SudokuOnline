package SudokuGenerator;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
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
    private ArrayList<xyStorage> highlightDuplicatesPosition = new ArrayList<>();
    private ArrayList<xyStorage> highlightRowsPosition = new ArrayList<>();
    private ArrayList<xyStorage> hightlightColsPosition = new ArrayList<>();
    private ArrayList<xyStorage> highlightRegionPosition = new ArrayList<>();

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
        highlightCells(x, y);
    }

    public void setNumber(int number) {
        if (selectedPosX != -1 && selectedPosY != -1) {
            grid.setItem(selectedPosX, selectedPosY, number);
            highlightCells(selectedPosX, selectedPosY);
            undoStorage.push(new xyStorage(selectedPosX, selectedPosY));
            redoStorage.empty();
        }
        grid.checkGame();
    }

    public void highlightCells(int xPos, int yPos) {
        for (int i = 0; i < highlightRowsPosition.size(); i++) {
            getGrid().getGrid()[highlightRowsPosition.get(i).getX()][highlightRowsPosition.get(i).getY()].setBackgroundColor(Color.WHITE);
            getGrid().getGrid()[hightlightColsPosition.get(i).getX()][hightlightColsPosition.get(i).getY()].setBackgroundColor(Color.WHITE);
            getGrid().getGrid()[highlightRegionPosition.get(i).getX()][highlightRegionPosition.get(i).getY()].setBackgroundColor(Color.WHITE);
        }

        for (int i = 0; i < highlightDuplicatesPosition.size(); i++)
            getGrid().getGrid()[highlightDuplicatesPosition.get(i).getX()][highlightDuplicatesPosition.get(i).getY()].setBackgroundColor(Color.WHITE);

        highlightRowsPosition.clear();
        hightlightColsPosition.clear();
        highlightRegionPosition.clear();
        highlightDuplicatesPosition.clear();

        findRegion(xPos, yPos);

        for (int i = 0; i < 9; i++) {
            getGrid().getGrid()[i][yPos].setBackgroundColor(Color.parseColor("#ffffe0"));
            hightlightColsPosition.add(new xyStorage(i, yPos));
            getGrid().getGrid()[xPos][i].setBackgroundColor(Color.parseColor("#ffffe0"));
            highlightRowsPosition.add(new xyStorage(xPos, i));
            getGrid().getGrid()[highlightRegionPosition.get(i).getX()][highlightRegionPosition.get(i).getY()].setBackgroundColor(Color.parseColor("#ffffe0"));
        }

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if ((getGrid().getGrid()[x][y].getValue() != 0) && (getGrid().getGrid()[x][y].getValue() != -1)) {
                    if (getGrid().getGrid()[x][y].getValue() == getGrid().getGrid()[xPos][yPos].getValue()) {
                        getGrid().getGrid()[x][y].setBackgroundColor(Color.parseColor("#89cff0"));
                        highlightDuplicatesPosition.add(new xyStorage(x, y));
                    }
                }
            }
        }
    }

    public void findRegion(int xPos, int yPos) {
        int box_Y = yPos / 3;
        int box_X = xPos / 3;

        switch (box_X + 3 * box_Y + 1) {
            case 1: {
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        highlightRegionPosition.add(new xyStorage(x, y));
                    }
                }
                break;
            }
            case 2: {
                for (int x = 3; x < 6; x++) {
                    for (int y = 0; y < 3; y++) {
                        highlightRegionPosition.add(new xyStorage(x, y));
                    }
                }
                break;
            }
            case 3: {
                for (int x = 6; x < 9; x++) {
                    for (int y = 0; y < 3; y++) {
                        highlightRegionPosition.add(new xyStorage(x, y));
                    }
                }
                break;
            }
            case 4: {
                for (int x = 0; x < 3; x++) {
                    for (int y = 3; y < 6; y++) {
                        highlightRegionPosition.add(new xyStorage(x, y));
                    }
                }
                break;
            }
            case 5: {
                for (int x = 3; x < 6; x++) {
                    for (int y = 3; y < 6; y++) {
                        highlightRegionPosition.add(new xyStorage(x, y));
                    }
                }
                break;
            }
            case 6: {
                for (int x = 6; x < 9; x++) {
                    for (int y = 3; y < 6; y++) {
                        highlightRegionPosition.add(new xyStorage(x, y));
                    }
                }
                break;
            }
            case 7: {
                for (int x = 0; x < 3; x++) {
                    for (int y = 6; y < 9; y++) {
                        highlightRegionPosition.add(new xyStorage(x, y));
                    }
                }
                break;
            }
            case 8: {
                for (int x = 3; x < 6; x++) {
                    for (int y = 6; y < 9; y++) {
                        highlightRegionPosition.add(new xyStorage(x, y));
                    }
                }
                break;
            }
            case 9: {
                for (int x = 6; x < 9; x++) {
                    for (int y = 6; y < 9; y++) {
                        highlightRegionPosition.add(new xyStorage(x, y));
                    }
                }
                break;
            }
        }
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
