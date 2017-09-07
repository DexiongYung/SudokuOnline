package SudokuGenerator;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by Dylan on 2017-08-03.
 */

public class SudokuGenerator {
    private static SudokuGenerator instance;
    private int[][] grid;
    private int[][] solution;
    private ArrayList<Integer> remains = new ArrayList<>();
    private HashSet<Integer> doNotTouch = new HashSet<>();

    private SudokuGenerator(){}

    public static SudokuGenerator getInstance(){
        if(instance == null){
            instance = new SudokuGenerator();
        }
        return instance;
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
     * Returns a grid of the desired difficulty level
     * @param level between [1,5] representing the level the user wants
     */
    public void generateGrid(int level) {
        if (level < 1 || level > 5)
            throw new InvalidParameterException("not a level");

        this.solution = generateFullGrid();
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
                int n = new Random().nextInt(14) + 35;
                removeElements(n);
                for (int x = 0; x < 9; x++) {
                    for (int y = 0; y < 9; y++) {
                        if (getGrid()[x][y] == 0) {
                            if (singleCandidate(x, y)) {
                                singleCandidateDestroyer(x, y);
                            }
                        }
                    }
                }
                lowestBoundGenerator(4);
                break;
            }
            case 3: {
                int n = new Random().nextInt(4) + 45;
                removeElements(n);

                for (int x = 0; x < 9; x++) {
                    for (int y = 0; y < 9; y++) {
                        if (getGrid()[x][y] == 0) {
                            if (singleCandidate(x, y)) {
                                singleCandidateDestroyer(x, y);
                            }
                        }
                    }
                }
                lowestBoundGenerator(3);
                break;
            }
            case 4: {
                int n = new Random().nextInt(4) + 50;
                removeElements(n);
                lowestBoundGenerator(2);

                break;
            }
            case 5: {
                int n = new Random().nextInt(5) + 54;
                removeElements(n);
                lowestBoundGenerator(0);
                break;
            }
        }
    }

    /**
     * Create a filled 9 x 9 grid
     *
     * @return a filled valid 9 x 9 Sudoku
     */
    private int[][] generateFullGrid() {
        ArrayList<Integer> arr = new ArrayList<>(9);
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

        return convert1DTo2D(grid);
    }

    /**
     * Removes n random elements
     * @param n number of elements to remove
     */
    private void removeElements(int n) {
        while (n != 0) {
            Collections.shuffle(remains);
            int index = remains.get(0);

            int x = index % 9;
            int y = index / 9;

            if (grid[x][y] != 0) {
                this.grid[x][y] = 0;
                n--;
            }
        }
    }

    /**
     * Makes sure lower bound of the level chosen are not violated
     * @param lower
     */
    private void lowestBoundGenerator(int lower) {
        for (int x = 0; x < 9; x++) {
            ArrayList<Integer> indexesOfEmptyRowCells = new ArrayList<>();
            ArrayList<Integer> indexesOfEmptyColumnCells = new ArrayList<>();

            for (int y = 0; y < 9; y++) {
                if (grid[x][y] == 0)
                    indexesOfEmptyRowCells.add(y);
                if (grid[y][x] == 0)
                    indexesOfEmptyColumnCells.add(y);
            }

            while (9 - indexesOfEmptyRowCells.size() < lower) {
                Collections.shuffle(indexesOfEmptyRowCells);
                int i = indexesOfEmptyRowCells.get(0);
                indexesOfEmptyRowCells.remove(0);
                if (!doNotTouch.contains(i))
                    this.grid[x][i] = this.solution[x][i];
            }

            while (9 - indexesOfEmptyColumnCells.size() < lower) {
                Collections.shuffle(indexesOfEmptyColumnCells);
                int i = indexesOfEmptyColumnCells.get(0);
                indexesOfEmptyColumnCells.remove(0);
                if (!doNotTouch.contains(i))
                    this.grid[i][x] = this.solution[i][x];
            }
        }

        if (lower == 0) {
            boolean needed = true;
            for (int x = 0; x < 9; x++) {
                HashSet<Integer> rowsFilled = new HashSet<>();
                HashSet<Integer> columnsFilled = new HashSet<>();
                for (int y = 0; y < 9; y++) {
                    if (grid[x][y] != 0)
                        rowsFilled.add(grid[x][y]);
                    if (grid[y][x] != 0)
                        columnsFilled.add(grid[x][y]);
                }

                if (rowsFilled.size() == 0 || columnsFilled.size() == 0)
                    needed = false;
            }

            if (needed) {
                int random = new Random().nextInt(9);
                int rowOrColumn = new Random().nextInt(2);

                if (rowOrColumn == 0) {
                    for (int i = 0; i < 9; i++)
                        grid[i][random] = 0;
                } else {
                    for (int i = 0; i < 9; i++)
                        grid[random][i] = 0;
                }
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

    /**
     * @param x
     * @param y
     * @return true if single candidate strategy works on cell
     */
    private boolean singleCandidate(int x, int y) {
        ArrayList<Integer> pos = possibleEntries(x, y);

        if (pos.size() == 1) {
            grid[x][y] = pos.get(0);
            return true;
        }

        return false;
    }

    /**
     * @param x            is the x-coordinate
     * @param y            is the y-coordinate
     * @param rowColRegion and integer  [0,2]. 0 means row, 1 means col and 2 means region
     * @return true if single candidate can be used on any empty cell in row, column or region
     */
    private boolean singlePosition(int x, int y, int rowColRegion) {
        if (rowColRegion == 0) {
            for (int index = 0; index < 9; index++) {
                if (grid[x][index] == 0) {
                    if (singleCandidate(x, index))
                        return true;
                }
            }
        } else if (rowColRegion == 1) {
            for (int index = 0; index < 9; index++) {
                if (grid[index][y] == 0) {
                    if (singleCandidate(index, y))
                        return true;
                }
            }
        } else {
            int row = x / 3;
            int col = y / 3;

            for (int i = row * 3; i < row * 3 + 3; i++) {
                for (int j = col * 3; j < col * 3 + 3; j++) {
                    if (singleCandidate(i, j)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param i is the x coordinate
     * @param j is the y coordinate
     * @return a array of numbers 1 - 9 not accounted for
     */
    private ArrayList<Integer> possibleEntries(int i, int j) {
        ArrayList<Integer> possible = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        //check horizontally
        for (int a = 0; a < 9; a++) {
            if (grid[i][a] != 0) {
                possible.remove(Integer.valueOf(grid[i][a]));
            }
        }

        //check vertically
        for (int a = 0; a < 9; a++) {
            if (grid[a][j] != 0) {
                possible.remove(Integer.valueOf(grid[a][j]));
            }
        }

        //check region
        int row = i / 3;
        int col = j / 3;

        for (int x = row * 3; x < row * 3 + 3; x++) {
            for (int y = col * 3; y < col * 3 + 3; y++) {
                if (grid[x][y] != 0) {
                    possible.remove(Integer.valueOf(grid[x][y]));
                }
            }
        }

        return possible;
    }

    /**
     * @param i is the x coordinate
     * @param j is the y coordinate
     * @return the indexes of filled rows on i
     */
    private ArrayList<Integer> filledRows(int i, int j) {
        ArrayList<Integer> filledEntries = new ArrayList<Integer>();

        //check horizontally
        for (int a = 0; a < 9; a++) {
            if (grid[i][a] != 0 && !filledEntries.contains(j * 9 + a)) {
                filledEntries.add(j * 9 + a);
            }
        }

        return filledEntries;
    }

    /**
     * @param x is the x coordinate
     * @param y is the y coordinate
     * @return the indexes of the filled columns
     */
    private ArrayList<Integer> filledColums(int x, int y) {
        ArrayList<Integer> filledEntries = new ArrayList<Integer>();

        //check vertically
        for (int a = 0; a < 9; a++) {
            if (grid[a][y] != 0 && !filledEntries.contains(y * 9 + a)) {
                filledEntries.add(y * 9 + a);
            }
        }

        return filledEntries;
    }

    /**
     * @param i is the x coordinates
     * @param j is the y coordinates
     * @return the indexes of the filled regions
     */
    private ArrayList<Integer> filledRegions(int i, int j) {
        ArrayList<Integer> filledEntries = new ArrayList<Integer>();

        //check region
        int row = i / 3;
        int col = j / 3;

        for (int x = row * 3; x < row * 3 + 3; x++) {
            for (int y = col * 3; y < col * 3 + 3; y++) {
                if (grid[x][y] != 0 && !filledEntries.contains(y * 9 + x)) {
                    filledEntries.add(y * 9 + x);
                }
            }
        }

        return filledEntries;
    }

    /**
     * @param a Array to be copied
     * @return a dynamic copy of array a
     */
    private int[][] copyArray(int[][] a) {
        int[][] v = new int[9][9];

        for (int x = 0; x < 9; x++) {
            v[x] = a[x].clone();
        }
        return v;
    }

    private void singleCandidateDestroyer(int x, int y) {
        ArrayList<Integer> filledEntries = filledRegions(x, y);
        filledEntries.addAll(filledColums(x, y));
        filledEntries.addAll(filledRows(x, y));

        Collections.shuffle(filledEntries);
        int xPos = filledEntries.get(0) % 9;
        int yPos = filledEntries.get(0) / 9;
        filledEntries.remove(0);
        while (!singleCandidate(xPos, yPos) && !filledEntries.isEmpty()) {
            Collections.shuffle(filledEntries);
            xPos = filledEntries.get(0) % 9;
            yPos = filledEntries.get(0) / 9;
            filledEntries.remove(0);
        }
        if (!filledEntries.isEmpty()) {
            this.grid[xPos][yPos] = 0;
            doNotTouch.add(yPos * 9 + xPos);
        }
    }

    private void singlePositionDestroyer(int x, int y) {
        boolean regionSP = singlePosition(x, y, 2);
        if (regionSP) {
            ArrayList<Integer> regions = filledRegions(x, y);
            Collections.shuffle(regions);
            int index = regions.get(0);
            this.grid[index % 9][index / 9] = 0;
            doNotTouch.add(index);
        } else {
            boolean rowSP = singlePosition(x, y, 0);
            if (rowSP) {
                ArrayList<Integer> rows = filledRows(x, y);
                Collections.shuffle(rows);
                int index = rows.get(0);
                this.grid[index % 9][index / 9] = 0;
                doNotTouch.add(index);
            } else {
                boolean columnSP = singlePosition(x, y, 1);
                if (columnSP) {
                    ArrayList<Integer> columns = filledColums(x, y);
                    Collections.shuffle(columns);
                    int index = columns.get(0);
                    this.grid[index % 9][index / 9] = 0;
                    doNotTouch.add(index);
                }
            }
        }
    }

    public int[][] getGrid() {
        return this.grid;
    }
}

