package SudokuGenerator;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Dylan on 2017-08-03.
 */

public class SudokuGenerator {
    private static SudokuGenerator instance;
    private ArrayList<ArrayList<Integer>> available = new ArrayList<>();
    public int gridDimensions = 9;
    private Random randomGenerator = new Random();
    private SudokuGenerator(){}

    public static SudokuGenerator getInstance(){
        if(instance == null)
            instance = new SudokuGenerator();

        return instance;
    }

    public int[][] generateGrid(){
        int[][] sudokuGrid = new int[gridDimensions][gridDimensions];

        int currentPos = 0;

        clearGrid(sudokuGrid);

        while(currentPos < gridDimensions * gridDimensions){
            if(available.get(currentPos).size() != 0){
                int i = randomGenerator.nextInt(available.get(currentPos).size());
                int number = available.get(currentPos).get(i);

                if(!checkConflict(sudokuGrid, currentPos)) {
                    int xPos = currentPos % 9;
                    int yPos = currentPos / 9;

                    sudokuGrid[xPos][yPos] = number;

                    available.get(currentPos).remove(i);

                    currentPos++;
                }else{
                    available.get(currentPos).remove(i);
                }
            }else {
                for(int i = 1 ; i <= gridDimensions ; i++){
                    available.get(currentPos).add(i);
                }
                currentPos--;
            }
        }

        return sudokuGrid;
    }

    /**
     * Sets every index in grid to -1
     * @param sudokuGrid    - The grid representing Sudoku board
     */
    private void clearGrid(int[][] sudokuGrid){
        available.clear();

        for(int x = 0 ; x < gridDimensions ; x++){
            for(int y = 0 ; y < gridDimensions ; y++){
                sudokuGrid[x][y] = -1;
            }
        }

        for(int x = 0 ; x < gridDimensions*gridDimensions ; x++){
            available.add(new ArrayList<Integer>());

            for(int i = 1 ; i <= gridDimensions ; i++){
                available.get(x).add(i);
            }
        }
    }

    /**
     * Checks if there are any duplicates on the horizontal or vertical axis
     * @param sudokuGrid    - whole sudoku grid
     * @param currentPos    - current position being examined
     * @return  True if there is a conflict
     */
    private boolean checkConflict(final int[][] sudokuGrid, int currentPos){
        int xPosition = currentPos % gridDimensions;
        int yPosition = currentPos / gridDimensions;

        return (checkVerticalConflict(sudokuGrid, xPosition, yPosition) || checkHorizontalConflict(sudokuGrid,xPosition, yPosition)
        || checkRegionConflict(sudokuGrid, xPosition, yPosition, sudokuGrid[xPosition][yPosition]));
    }

    /**
     * Checks if has repeating number in the region it belongs
     * @param sudokuGrid
     * @param xpos
     * @param ypos
     * @return
     */
    private boolean checkRegionConflict(final int[][] sudokuGrid, final int xpos, final int ypos, int number){
        int xRegion = xpos / 3;
        int yRegion = ypos / 3;

        for(int x = xRegion ; x < xRegion * 3 + 3 ; x++){
            for(int y = yRegion ; y < yRegion * 3 + 3 ; y++){
                if(number == sudokuGrid[x][y])
                    return true;
            }
        }

        return false;
    }

    /**
     * Checks to see if there are conflicting duplicates on the same horizontal axis
     * @param sudokuGrid    - whole Sudoku grid
     * @param xPos  - x coordinate of the position being examined
     * @param yPos  - y coordinate of the position being examined
     * @return  true if there are duplicates of the same number on the same horizontal axis
     */
    private boolean checkHorizontalConflict(final int[][] sudokuGrid, final int xPos, final int yPos){
        for(int x = 0 ; x < xPos - 1 ; x++){
            if (x != xPos) {
                if(sudokuGrid[x][yPos] == sudokuGrid[xPos][yPos])
                    return true;
            }
        }

        return false;
    }

    /**
     * Checks to see if there are conflicting duplicates on the same vertical axis
     * @param sudokuGrid    - the whole Sudoku grid
     * @param xPos  - x coordinate of the position being examined
     * @param yPos  - y coordinate of the position being examined
     * @return  true if there is duplicates of the same number on the same vertical axis
     */
    private boolean checkVerticalConflict(final int[][] sudokuGrid, final int xPos, final int yPos){
        for(int y = 0 ; y < yPos - 1 ; y++){
            if (y != yPos) {
                if(sudokuGrid[xPos][y] == sudokuGrid[xPos][yPos])
                    return true;
            }
        }

        return false;
    }
}
