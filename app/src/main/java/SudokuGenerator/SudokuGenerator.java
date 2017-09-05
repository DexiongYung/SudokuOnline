package SudokuGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Dylan on 2017-08-03.
 * Not my code taken from github.com/mfgravesjr to create Sudoku puzzle
 */

public class SudokuGenerator {
    private static SudokuGenerator instance;
    private int[][] grid;
    private int[][] solution;
    private Random rand = new Random();
    private ArrayList<Integer> remains = new ArrayList<>();
    private ArrayList<Integer> removed = new ArrayList<>();

    private SudokuGenerator(){}

    public static SudokuGenerator getInstance(){
        if(instance == null){
            instance = new SudokuGenerator();
        }
        return instance;
    }

    /**
     * Prints a visual representation of a 9x9 Sudoku grid
     *
     * @param grid an array with length 81 to be printed
     */
    public static void printGrid(int[] grid) {
        if (grid.length != 81)
            throw new IllegalArgumentException("The grid must be a single-dimension grid of length 81");
        for (int i = 0; i < 81; i++) {
            System.out.print("[" + grid[i] + "] " + (i % 9 == 8 ? "\n" : ""));
        }
    }

    /**
     * Tests an int array of length 81 to see if it is a valid Sudoku grid. i.e. 1 through 9 appearing once each in every row, column, and box
     *
     * @param grid an array with length 81 to be tested
     * @return a boolean representing if the grid is valid
     */
    private static boolean isPerfect(int[] grid) {
        if (grid.length != 81)
            throw new IllegalArgumentException("The grid must be a single-dimension grid of length 81");

        //tests to see if the grid is perfect

        //for every box
        for (int i = 0; i < 9; i++) {
            boolean[] registered = new boolean[10];
            registered[0] = true;
            int boxOrigin = (i * 3) % 9 + ((i * 3) / 9) * 27;
            for (int j = 0; j < 9; j++) {
                int boxStep = boxOrigin + (j / 3) * 9 + (j % 3);
                int boxNum = grid[boxStep];
                registered[boxNum] = true;
            }
            for (boolean b : registered)
                if (!b) return false;
        }

        //for every row
        for (int i = 0; i < 9; i++) {
            boolean[] registered = new boolean[10];
            registered[0] = true;
            int rowOrigin = i * 9;
            for (int j = 0; j < 9; j++) {
                int rowStep = rowOrigin + j;
                int rowNum = grid[rowStep];
                registered[rowNum] = true;
            }
            for (boolean b : registered)
                if (!b) return false;
        }

        //for every column
        for (int i = 0; i < 9; i++) {
            boolean[] registered = new boolean[10];
            registered[0] = true;
            for (int j = 0; j < 9; j++) {
                int colStep = i + j * 9;
                int colNum = grid[colStep];
                registered[colNum] = true;
            }
            for (boolean b : registered)
                if (!b) return false;
        }

        return true;
    }

    /**
     * Generates a valid 9 by 9 Sudoku grid with 1 through 9 appearing only once in every box, row, and column
     *
     */


    public void generateGrid(int level) {
        ArrayList<Integer> arr = new ArrayList<Integer>(9);
        int[] grid = new int[81];
        for (int i = 1; i <= 9; i++) arr.add(i);

        //loads all boxes with numbers 1 through 9
        for (int i = 0; i < 81; i++) {
            if (i % 9 == 0) Collections.shuffle(arr);
            int perBox = ((i / 3) % 3) * 9 + ((i % 27) / 9) * 3 + (i / 27) * 27 + (i % 3);
            grid[perBox] = arr.get(i % 9);
        }

        //tracks rows and columns that have been sorted
        boolean[] sorted = new boolean[81];

        for (int i = 0; i < 9; i++) {
            boolean backtrack = false;
            //0 is row, 1 is column
            for (int a = 0; a < 2; a++) {
                //every number 1-9 that is encountered is registered
                boolean[] registered = new boolean[10]; //index 0 will intentionally be left empty since there are only number 1-9.
                int rowOrigin = i * 9;
                int colOrigin = i;

                ROW_COL:
                for (int j = 0; j < 9; j++) {
                    //row/column stepping - making sure numbers are only registered once and marking which cells have been sorted
                    int step = (a % 2 == 0 ? rowOrigin + j : colOrigin + j * 9);
                    int num = grid[step];

                    if (!registered[num]) registered[num] = true;
                    else //if duplicate in row/column
                    {
                        //box and adjacent-cell swap (BAS method)
                        //checks for either unregistered and unsorted candidates in same box,
                        //or unregistered and sorted candidates in the adjacent cells
                        for (int y = j; y >= 0; y--) {
                            int scan = (a % 2 == 0 ? i * 9 + y : i + 9 * y);
                            if (grid[scan] == num) {
                                //box stepping
                                for (int z = (a % 2 == 0 ? (i % 3 + 1) * 3 : 0); z < 9; z++) {
                                    if (a % 2 == 1 && z % 3 <= i % 3)
                                        continue;
                                    int boxOrigin = ((scan % 9) / 3) * 3 + (scan / 27) * 27;
                                    int boxStep = boxOrigin + (z / 3) * 9 + (z % 3);
                                    int boxNum = grid[boxStep];
                                    if ((!sorted[scan] && !sorted[boxStep] && !registered[boxNum])
                                            || (sorted[scan] && !registered[boxNum] && (a % 2 == 0 ? boxStep % 9 == scan % 9 : boxStep / 9 == scan / 9))) {
                                        grid[scan] = boxNum;
                                        grid[boxStep] = num;
                                        registered[boxNum] = true;
                                        continue ROW_COL;
                                    } else if (z == 8) //if z == 8, then break statement not reached: no candidates available
                                    {
                                        //Preferred adjacent swap (PAS)
                                        //Swaps x for y (preference on unregistered numbers), finds occurence of y
                                        //and swaps with z, etc. until an unregistered number has been found
                                        int searchingNo = num;

                                        //noting the location for the blindSwaps to prevent infinite loops.
                                        boolean[] blindSwapIndex = new boolean[81];

                                        //loop of size 18 to prevent infinite loops as well. Max of 18 swaps are possible.
                                        //at the end of this loop, if continue or break statements are not reached, then
                                        //fail-safe is executed called Advance and Backtrack Sort (ABS) which allows the
                                        //algorithm to continue sorting the next row and column before coming back.
                                        //Somehow, this fail-safe ensures success.
                                        for (int q = 0; q < 18; q++) {
                                            SWAP:
                                            for (int b = 0; b <= j; b++) {
                                                int pacing = (a % 2 == 0 ? rowOrigin + b : colOrigin + b * 9);
                                                if (grid[pacing] == searchingNo) {
                                                    int adjacentCell = -1;
                                                    int adjacentNo = -1;
                                                    int decrement = (a % 2 == 0 ? 9 : 1);

                                                    for (int c = 1; c < 3 - (i % 3); c++) {
                                                        adjacentCell = pacing + (a % 2 == 0 ? (c + 1) * 9 : c + 1);

                                                        //this creates the preference for swapping with unregistered numbers
                                                        if ((a % 2 == 0 && adjacentCell >= 81)
                                                                || (a % 2 == 1 && adjacentCell % 9 == 0))
                                                            adjacentCell -= decrement;
                                                        else {
                                                            adjacentNo = grid[adjacentCell];
                                                            if (i % 3 != 0
                                                                    || c != 1
                                                                    || blindSwapIndex[adjacentCell]
                                                                    || registered[adjacentNo])
                                                                adjacentCell -= decrement;
                                                        }
                                                        adjacentNo = grid[adjacentCell];

                                                        //as long as it hasn't been swapped before, swap it
                                                        if (!blindSwapIndex[adjacentCell]) {
                                                            blindSwapIndex[adjacentCell] = true;
                                                            grid[pacing] = adjacentNo;
                                                            grid[adjacentCell] = searchingNo;
                                                            searchingNo = adjacentNo;

                                                            if (!registered[adjacentNo]) {
                                                                registered[adjacentNo] = true;
                                                                continue ROW_COL;
                                                            }
                                                            break SWAP;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        //begin Advance and Backtrack Sort (ABS)
                                        backtrack = true;
                                        break ROW_COL;
                                    }
                                }
                            }
                        }
                    }
                }

                if (a % 2 == 0)
                    for (int j = 0; j < 9; j++) sorted[i * 9 + j] = true; //setting row as sorted
                else if (!backtrack)
                    for (int j = 0; j < 9; j++) sorted[i + j * 9] = true; //setting column as sorted
                else //reseting sorted cells through to the last iteration
                {
                    backtrack = false;
                    for (int j = 0; j < 9; j++) sorted[i * 9 + j] = false;
                    for (int j = 0; j < 9; j++) sorted[(i - 1) * 9 + j] = false;
                    for (int j = 0; j < 9; j++) sorted[i - 1 + j * 9] = false;
                    i -= 2;
                }
            }
        }

        if (!isPerfect(grid)) throw new RuntimeException("ERROR: Imperfect grid generated.");

        this.solution = convert1DTo2D(grid);
        this.grid = copyArray(this.solution);

        for (int i = 0; i < 81; i++) {
            remains.add(i);
        }

        switch (level) {
            case 1: {
                int n = new Random().nextInt(12) + 25;
                removeElements(n);
                lowestBoundGenerator(5);
                break;
            }
            case 2: {
                int n = new Random().nextInt(14) + 34;
                removeElements(n);
                lowestBoundGenerator(4);
                break;
            }
            case 3: {
                int n = new Random().nextInt(4) + 48;
                removeElements(n);
                lowestBoundGenerator(3);
                break;
            }
            case 4: {
                int n = new Random().nextInt(4) + 52;
                removeElements(n);
                lowestBoundGenerator(2);
                break;
            }
            case 5: {
                int n = new Random().nextInt(5) + 56;
                removeElements(n);
                lowestBoundGenerator(0);
                break;
            }
        }
    }

    private void removeElements(int n) {
        while (n != 0) {
            Collections.shuffle(remains);
            int index = remains.get(0);

            int x = index % 9;
            int y = index / 9;

            if (grid[x][y] != 0) {
                this.grid[x][y] = 0;
                addToRemoved(index);
                n--;
            }
        }
    }

    public void lowestBoundGenerator(int lower) {
        //Look for rows with less than lower bound
        for (int x = 0; x < 9; x++) {
            ArrayList<Integer> indexesOfEmptyRowCells = new ArrayList<>();
            for (int y = 0; y < 9; y++) {
                if (grid[x][y] == 0)
                    indexesOfEmptyRowCells.add(y);
            }

            while (9 - indexesOfEmptyRowCells.size() < lower) {
                Collections.shuffle(indexesOfEmptyRowCells);
                int y = indexesOfEmptyRowCells.get(0);
                indexesOfEmptyRowCells.remove(0);
                this.grid[x][y] = this.solution[x][y];
            }
        }

        //Look for columns with less than lower bound
        for (int y = 0; y < 9; y++) {
            ArrayList<Integer> indexesOfEmptyColumnCells = new ArrayList<>();
            for (int x = 0; x < 9; x++) {
                if (grid[x][y] == 0) {
                    indexesOfEmptyColumnCells.add(x);
                }
            }

            while (9 - indexesOfEmptyColumnCells.size() < lower) {
                Collections.shuffle(indexesOfEmptyColumnCells);
                int x = indexesOfEmptyColumnCells.get(0);
                indexesOfEmptyColumnCells.remove(0);
                this.grid[x][y] = this.solution[x][y];
            }
        }
    }

    //Convert from 1D to 2D
    private int[][] convert1DTo2D(int[] arr) {
        int[][] new2D = new int[9][9];
        int index = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                new2D[x][y] = arr[index];
                index++;
            }
        }
        return new2D;
    }

    public int[][] getGrid() {
        return this.grid;
    }

    private void addToRemains(int i) {
        remains.add(Integer.valueOf(i));
        removed.remove(Integer.valueOf(i));
    }

    private void addToRemoved(int i) {
        removed.add(Integer.valueOf(i));
        remains.remove(Integer.valueOf(i));
    }

    private int[][] copyArray(int[][] a) {
        int[][] v = new int[9][9];

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                v[x][y] = a[x][y];
            }
        }
        return v;
    }
}

