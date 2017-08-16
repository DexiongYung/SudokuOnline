package SudokuGenerator;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Stack;

import SudokuGenerator.Objects.xyStorage;
import View.SudokuGrid.GameGrid;

/**
 * Created by Dylan on 2017-08-07.
 */

public class GameEngine {
    private static GameEngine instance;
    private GameGrid grid = null;
    private int selectedPosX = -1, selectedPosY = -1;
    private boolean draftMode;
    private Stack<xyStorage> undoStorage = new Stack<>();
    private Stack<xyStorage> redoStorage = new Stack<>();
    private ArrayList<xyStorage> highlightDuplicatesPosition = new ArrayList<>();
    private ArrayList<xyStorage> highlightRowsPosition = new ArrayList<>();
    private ArrayList<xyStorage> hightlightColsPosition = new ArrayList<>();
    private ArrayList<xyStorage> highlightRegionPosition = new ArrayList<>();

    private GameEngine() {
    }

    public static GameEngine getInstance() {
        if (instance == null)
            instance = new GameEngine();

        return instance;
    }

    public void createGrid(Context context, int numberRemoved) {
        //Create 2D int Array of Sudoku Grid
        int[][] Sudoku = SudokuGenerator.getInstance().generateGrid();
        Sudoku = SudokuGenerator.getInstance().removeElements(Sudoku, numberRemoved);

        //Set draftMode to false on new grid generation
        draftMode = false;

        //Generate on screen grid
        grid = new GameGrid(context);
        grid.setGrid(Sudoku);
    }

    //Grid related functions
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

    //HIGHLIGHTING RELEVANT CELLS
    private void highlightCells(int xPos, int yPos) {
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

        getGrid().getGrid()[xPos][yPos].setBackgroundColor(Color.parseColor("#ffb6c1"));
    }

    private void findRegion(int xPos, int yPos) {
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

    //undo and redo functions
    public void undoFunction() {
        if (!undoStorage.isEmpty()) {
            xyStorage temp = undoStorage.pop();
            redoStorage.push(temp);
            grid.getGrid()[temp.getX()][temp.getY()].undo();
            highlightCells(selectedPosX, selectedPosY);
        }
    }

    public void redoFunction() {
        if (!redoStorage.isEmpty()) {
            xyStorage t = redoStorage.pop();
            undoStorage.push(t);
            grid.getGrid()[t.getX()][t.getY()].redo();
            highlightCells(selectedPosX, selectedPosY);
        }
    }

    //DRAFT MODE
    public void draftModeSetter() {
        draftMode = !draftMode;
    }

    public boolean getDraftModeSetting() {
        return draftMode;
    }
}
