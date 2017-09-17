package SudokuGenerator;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Stack;

import View.SudokuGrid.GameGrid;
import View.SudokuGrid.SudokuCell;

/**
 * Created by Dylan on 2017-08-07.
 */

public class GameEngine {
    private static GameEngine instance;
    private GameGrid grid = null;
    private int selectedPosX = -1, selectedPosY = -1, level;
    private boolean draftMode;
    private Stack<Integer> undoStorage = new Stack<>(), redoStorage = new Stack<>();
    private ArrayList<Integer> highlightDuplicatesPosition = new ArrayList<>(), highlightRowsPosition = new ArrayList<>(),
            hightlightColsPosition = new ArrayList<>(), highlightRegionPosition = new ArrayList<>();

    private GameEngine() {
    }

    public static GameEngine getInstance() {
        if (instance == null)
            instance = new GameEngine();
        return instance;
    }

    public void createGrid(Context context, int difficulty) {
        //Create 2D int Array of Sudoku Grid
        level = difficulty;

        SudokuGenerator.getInstance().generateGrid(level);
        int[][] Sudoku = SudokuGenerator.getInstance().getGrid();

        //Set draftMode to false on new grid generation
        draftMode = false;

        //Generate on screen grid
        grid = new GameGrid(context);
        grid.setGrid(Sudoku);
    }

    public void continueGrid(Context context) {
        grid = new GameGrid(context);
        draftMode = false;
    }

    //Grid related functions
    public GameGrid getGameGrid() {
        return grid;
    }

    public void setSelectedPosition(int x, int y) {
        selectedPosX = x;
        selectedPosY = y;
        highlightCells(x, y);
    }

    public void setNumber(int number) {
        if ((selectedPosX != -1) && (selectedPosY != -1) && (getGameGrid().getSudokuCellGrid()[selectedPosX][selectedPosY].isModifiable())) {
            grid.setItem(selectedPosX, selectedPosY, number);
            highlightCells(selectedPosX, selectedPosY);
            undoStorage.push(selectedPosY * 9 + selectedPosX);
            redoStorage.clear();
            grid.checkGame(level);
        }
    }

    //HIGHLIGHTING RELEVANT CELLS
    private void highlightCells(int xPos, int yPos) {
        SudokuCell[][] oSudokuCellGrid = getGameGrid().getSudokuCellGrid();
        //Sets rows, columns, regions and duplicates to white
        for (int i = 0; i < highlightRowsPosition.size(); i++) {
            oSudokuCellGrid[highlightRowsPosition.get(i) % 9][highlightRowsPosition.get(i) / 9].setBackgroundColor(Color.WHITE);
            oSudokuCellGrid[hightlightColsPosition.get(i) % 9][hightlightColsPosition.get(i) / 9].setBackgroundColor(Color.WHITE);
            oSudokuCellGrid[highlightRegionPosition.get(i) % 9][highlightRegionPosition.get(i) / 9].setBackgroundColor(Color.WHITE);
        }

        for (int i = 0; i < highlightDuplicatesPosition.size(); i++)
            oSudokuCellGrid[highlightDuplicatesPosition.get(i) % 9][highlightDuplicatesPosition.get(i) / 9].setBackgroundColor(Color.WHITE);

        //Clears highlight info since they've been set to white
        highlightRowsPosition.clear();
        hightlightColsPosition.clear();
        highlightRegionPosition.clear();
        highlightDuplicatesPosition.clear();

        //sets hightlightRegionPosition
        findRegion(xPos, yPos);

        //Changes color of all row and column shared in (x,y) coordinate and region
        for (int i = 0; i < 9; i++) {
            oSudokuCellGrid[i][yPos].setBackgroundColor(Color.parseColor("#ffffe0"));
            hightlightColsPosition.add(xPos + i * 9);
            oSudokuCellGrid[xPos][i].setBackgroundColor(Color.parseColor("#ffffe0"));
            highlightRowsPosition.add(9 * yPos + i);
            oSudokuCellGrid[highlightRegionPosition.get(i) % 9][highlightRegionPosition.get(i) / 9].setBackgroundColor(Color.parseColor("#ffffe0"));
        }

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if ((getGameGrid().getGrid()[x][y] > 0) && (getGameGrid().getGrid()[x][y] == getGameGrid().getGrid()[xPos][yPos])) {
                    if (x == xPos || y == yPos || ((x / 3) + 3 * (y / 3) + 1) == ((xPos / 3) + 3 * (yPos / 3) + 1))
                        oSudokuCellGrid[x][y].setBackgroundColor(Color.parseColor("#FF6666"));
                    else
                        oSudokuCellGrid[x][y].setBackgroundColor(Color.parseColor("#89cff0"));
                    highlightDuplicatesPosition.add(y * 9 + x);
                }
            }
        }

        //sets (x,y) position to light pink to signify it's the cell currently selected
        oSudokuCellGrid[xPos][yPos].setBackgroundColor(Color.parseColor("#ffb6c1"));
    }

    private void findRegion(int xPos, int yPos) {
        int box_Y = yPos / 3;
        int box_X = xPos / 3;

        switch (box_X + 3 * box_Y + 1) {
            case 1: {
                for (int y = 0; y < 3; y++) {
                    for (int x = 0; x < 3; x++) {
                        highlightRegionPosition.add(y * 9 + x);
                    }
                }
                break;
            }
            case 2: {
                for (int y = 0; y < 6; y++) {
                    for (int x = 3; x < 6; x++) {
                        highlightRegionPosition.add(y * 9 + x);
                    }
                }
                break;
            }
            case 3: {
                for (int x = 6; x < 9; x++) {
                    for (int y = 0; y < 3; y++) {
                        highlightRegionPosition.add(y * 9 + x);
                    }
                }
                break;
            }
            case 4: {
                for (int x = 0; x < 3; x++) {
                    for (int y = 3; y < 6; y++) {
                        highlightRegionPosition.add(y * 9 + x);
                    }
                }
                break;
            }
            case 5: {
                for (int x = 3; x < 6; x++) {
                    for (int y = 3; y < 6; y++) {
                        highlightRegionPosition.add(y * 9 + x);
                    }
                }
                break;
            }
            case 6: {
                for (int x = 6; x < 9; x++) {
                    for (int y = 3; y < 6; y++) {
                        highlightRegionPosition.add(y * 9 + x);
                    }
                }
                break;
            }
            case 7: {
                for (int x = 0; x < 3; x++) {
                    for (int y = 6; y < 9; y++) {
                        highlightRegionPosition.add(y * 9 + x);
                    }
                }
                break;
            }
            case 8: {
                for (int x = 3; x < 6; x++) {
                    for (int y = 6; y < 9; y++) {
                        highlightRegionPosition.add(y * 9 + x);
                    }
                }
                break;
            }
            case 9: {
                for (int x = 6; x < 9; x++) {
                    for (int y = 6; y < 9; y++) {
                        highlightRegionPosition.add(y * 9 + x);
                    }
                }
                break;
            }
        }
    }

    //undo and redo functions
    public void undoFunction() {
        if (!undoStorage.isEmpty()) {
            int t = undoStorage.pop();
            redoStorage.push(t);
            grid.undo(t);
            highlightCells(selectedPosX, selectedPosY);
        }
    }

    public void redoFunction() {
        if (!redoStorage.isEmpty()) {
            int t = redoStorage.pop();
            undoStorage.push(t);
            grid.redo(t);
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

    //Clear Grid
    public void clearGrid() {
        if (!highlightRegionPosition.isEmpty() && !highlightRowsPosition.isEmpty() && !hightlightColsPosition.isEmpty()) {
            undoStorage.clear();
            redoStorage.clear();

            for (int i = 0; i < 9; i++) {
                clearHighlightsHelper(highlightRegionPosition, i);
                clearHighlightsHelper(highlightRowsPosition, i);
                clearHighlightsHelper(hightlightColsPosition, i);
            }

            for (int i = 0; i < highlightDuplicatesPosition.size(); i++) {
                clearHighlightsHelper(highlightDuplicatesPosition, i);
            }

            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    if (grid.getSudokuCellGrid()[x][y].isModifiable()) {
                        grid.getSudokuCellGrid()[x][y].emptyUndoRedoStack();
                        grid.getSudokuCellGrid()[x][y].emptyDraft();
                        grid.setItem(x, y, 0);
                    }
                }
            }
        }
    }

    private void clearHighlightsHelper(ArrayList<Integer> a, int index) {
        int x = a.get(index) % 9;
        int y = a.get(index) / 9;
        getGameGrid().getSudokuCellGrid()[x][y].setBackgroundColor(Color.WHITE);
    }

    public void increaseDifficulty() {
        if (level < 5) {
            SudokuGenerator.getInstance().generateGrid(level + 1);
            replaceBoard(SudokuGenerator.getInstance().getGrid());
            this.level++;
        }
    }

    public void decreaseDifficulty() {
        if (level > 1) {
            SudokuGenerator.getInstance().generateGrid(level - 1);
            replaceBoard(SudokuGenerator.getInstance().getGrid());
            this.level--;
        }
    }

    public void replaceBoard(int[][] array) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                getGameGrid().getSudokuCellGrid()[x][y].setModifiable();
                getGameGrid().setItem(x, y, 0);
                getGameGrid().getSudokuCellGrid()[x][y].emptyUndoRedoStack();
                getGameGrid().getSudokuCellGrid()[x][y].emptyDraft();
                if (array[x][y] != 0) {
                    getGameGrid().setItem(x, y, array[x][y]);
                    getGameGrid().getSudokuCellGrid()[x][y].setNotModifiable();
                }
            }
        }
    }
}
