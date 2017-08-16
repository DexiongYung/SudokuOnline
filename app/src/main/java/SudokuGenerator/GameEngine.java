package SudokuGenerator;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Stack;

import SudokuGenerator.Objects.xyCoordinates;
import View.SudokuGrid.GameGrid;

/**
 * Created by Dylan on 2017-08-07.
 */

public class GameEngine {
    private static GameEngine instance;
    private GameGrid grid = null;
    private int selectedPosX = -1, selectedPosY = -1;
    private boolean draftMode;
    private Stack<xyCoordinates> undoStorage = new Stack<>();
    private Stack<xyCoordinates> redoStorage = new Stack<>();
    private ArrayList<xyCoordinates> highlightDuplicatesPosition = new ArrayList<>();
    private ArrayList<xyCoordinates> highlightRowsPosition = new ArrayList<>();
    private ArrayList<xyCoordinates> hightlightColsPosition = new ArrayList<>();
    private ArrayList<xyCoordinates> highlightRegionPosition = new ArrayList<>();

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
            undoStorage.push(new xyCoordinates(selectedPosX, selectedPosY));
            redoStorage.empty();
        }
        grid.checkGame();
    }

    //HIGHLIGHTING RELEVANT CELLS
    private void highlightCells(int xPos, int yPos) {
        //Sets rows, columns, regions and duplicates to white
        for (int i = 0; i < highlightRowsPosition.size(); i++) {
            getGrid().getGrid()[highlightRowsPosition.get(i).getX()][highlightRowsPosition.get(i).getY()].setBackgroundColor(Color.WHITE);
            getGrid().getGrid()[hightlightColsPosition.get(i).getX()][hightlightColsPosition.get(i).getY()].setBackgroundColor(Color.WHITE);
            getGrid().getGrid()[highlightRegionPosition.get(i).getX()][highlightRegionPosition.get(i).getY()].setBackgroundColor(Color.WHITE);
        }

        for (int i = 0; i < highlightDuplicatesPosition.size(); i++)
            getGrid().getGrid()[highlightDuplicatesPosition.get(i).getX()][highlightDuplicatesPosition.get(i).getY()].setBackgroundColor(Color.WHITE);

        //Clears highlight info since they've been set to white
        highlightRowsPosition.clear();
        hightlightColsPosition.clear();
        highlightRegionPosition.clear();
        highlightDuplicatesPosition.clear();

        //sets hightlightRegionPosition
        findRegion(xPos, yPos);

        //Changes color of all row and column shared in (x,y) coordinate and region
        for (int i = 0; i < 9; i++) {
            getGrid().getGrid()[i][yPos].setBackgroundColor(Color.parseColor("#ffffe0"));
            hightlightColsPosition.add(new xyCoordinates(i, yPos));
            getGrid().getGrid()[xPos][i].setBackgroundColor(Color.parseColor("#ffffe0"));
            highlightRowsPosition.add(new xyCoordinates(xPos, i));
            getGrid().getGrid()[highlightRegionPosition.get(i).getX()][highlightRegionPosition.get(i).getY()].setBackgroundColor(Color.parseColor("#ffffe0"));
        }

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if ((getGrid().getGrid()[x][y].getValue() != 0) && (getGrid().getGrid()[x][y].getValue() != -1)) {
                    if (getGrid().getGrid()[x][y].getValue() == getGrid().getGrid()[xPos][yPos].getValue()) {
                        getGrid().getGrid()[x][y].setBackgroundColor(Color.parseColor("#89cff0"));
                        highlightDuplicatesPosition.add(new xyCoordinates(x, y));
                    }
                }
            }
        }

        //sets (x,y) position to light pink to signify it's the cell currently selected
        getGrid().getGrid()[xPos][yPos].setBackgroundColor(Color.parseColor("#ffb6c1"));
    }

    private void findRegion(int xPos, int yPos) {
        int box_Y = yPos / 3;
        int box_X = xPos / 3;

        switch (box_X + 3 * box_Y + 1) {
            case 1: {
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        highlightRegionPosition.add(new xyCoordinates(x, y));
                    }
                }
                break;
            }
            case 2: {
                for (int x = 3; x < 6; x++) {
                    for (int y = 0; y < 3; y++) {
                        highlightRegionPosition.add(new xyCoordinates(x, y));
                    }
                }
                break;
            }
            case 3: {
                for (int x = 6; x < 9; x++) {
                    for (int y = 0; y < 3; y++) {
                        highlightRegionPosition.add(new xyCoordinates(x, y));
                    }
                }
                break;
            }
            case 4: {
                for (int x = 0; x < 3; x++) {
                    for (int y = 3; y < 6; y++) {
                        highlightRegionPosition.add(new xyCoordinates(x, y));
                    }
                }
                break;
            }
            case 5: {
                for (int x = 3; x < 6; x++) {
                    for (int y = 3; y < 6; y++) {
                        highlightRegionPosition.add(new xyCoordinates(x, y));
                    }
                }
                break;
            }
            case 6: {
                for (int x = 6; x < 9; x++) {
                    for (int y = 3; y < 6; y++) {
                        highlightRegionPosition.add(new xyCoordinates(x, y));
                    }
                }
                break;
            }
            case 7: {
                for (int x = 0; x < 3; x++) {
                    for (int y = 6; y < 9; y++) {
                        highlightRegionPosition.add(new xyCoordinates(x, y));
                    }
                }
                break;
            }
            case 8: {
                for (int x = 3; x < 6; x++) {
                    for (int y = 6; y < 9; y++) {
                        highlightRegionPosition.add(new xyCoordinates(x, y));
                    }
                }
                break;
            }
            case 9: {
                for (int x = 6; x < 9; x++) {
                    for (int y = 6; y < 9; y++) {
                        highlightRegionPosition.add(new xyCoordinates(x, y));
                    }
                }
                break;
            }
        }
    }

    //undo and redo functions
    public void undoFunction() {
        if (!undoStorage.isEmpty()) {
            xyCoordinates temp = undoStorage.pop();
            redoStorage.push(temp);
            grid.getGrid()[temp.getX()][temp.getY()].undo();
            highlightCells(selectedPosX, selectedPosY);
        }
    }

    public void redoFunction() {
        if (!redoStorage.isEmpty()) {
            xyCoordinates t = redoStorage.pop();
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
