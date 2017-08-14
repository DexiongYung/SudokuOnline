package SudokuGenerator;

import java.util.HashSet;

/**
 * Created by Dylan on 2017-08-12.
 */

public class SudokuChecker {
    private static SudokuChecker instance;

    public SudokuChecker(){}

    public static SudokuChecker getInstance(){
        if(instance == null){
            instance = new SudokuChecker();
        }

        return instance;
    }

    public boolean checkSudoku(int[][] Sudoku){
        return (checkHorizontal(Sudoku) || checkVertical(Sudoku) || checkRegions(Sudoku));
    }

    private boolean checkRegions(int[][] sudoku) {
        for(int x = 0 ; x < 3 ; x++){
            for(int y = 0 ; y < 3 ; y++){
                if(!checkRegion(sudoku , x, y)){
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkRegion(int[][] sudoku, int xRegion, int yRegion){
        for(int x = xRegion * 3 ;  x < xRegion * 3 + 3 ; x++){
            for(int y = yRegion * 3 ; y < yRegion * 3 + 3 ; y++){
                for(int x2 = x ; x2 < xRegion * 3 + 3 ; x2++){
                    for(int y2 = y ; y2 < yRegion * 3 + 3 ; y2++){
                        if(((x2 != x || y2 != y) && (sudoku[x][y] == sudoku[x2][y2])) || (sudoku[x][y] == 0) || (sudoku[x][y] == -1)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean checkVertical(int[][] sudoku) {
        for(int x = 0 ; x < 9 ; x++){
            HashSet<Integer> holder = new HashSet<Integer>();
            for(int y = 0 ; y < 9 ; y++){
                if(sudoku[x][y] == 0 || sudoku[x][y] == -1)
                    return false;

                if(holder.contains(sudoku[x][y]))
                    return false;

                holder.add(sudoku[x][y]);
            }
        }
        return true;
    }

    private boolean checkHorizontal(int[][] sudoku) {
        for(int y = 0 ; y < 9 ; y++){
            HashSet<Integer> holder = new HashSet<Integer>();
            for(int x = 0 ; x < 9 ; x++){
                if(sudoku[x][y] == 0 || sudoku[x][y] == -1)
                    return false;

                if(holder.contains(sudoku[x][y]))
                    return false;

                holder.add(sudoku[x][y]);
            }
        }
        return true;
    }
}
