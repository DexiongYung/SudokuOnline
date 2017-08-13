package SudokuGenerator;

import java.util.ArrayList;

/**
 * Created by apple on 2017-08-12.
 */
public class SudokuSolver {

    int[][] sudoku_board=new int[9][9]; //copy of given sudoku
    boolean [][] digit_used_row = new boolean [9][9]; //row constraint
    boolean [][] digit_used_col = new boolean [9][9]; //column constraint
    boolean [][][] digit_used_area = new boolean [3][3][9]; //3*3 grid space constraint
    ArrayList<int[]> toDoArc = new ArrayList<int[]>(); //store next changeable arc
    int depth; //the blank cell remaining



    /**
     * initialize the digit_used_row, digit_used_col and digit_used_area to record domain, and make a copy of
     * given sudoku.
     *
     * @param board the 2d int array representing the Sudoku board. Zeros indicate unfilled cells.
     */

    public void initialize(int [][] board){
        for(int i =0;i<9;i++){
            for(int j = 1;j<10;j++) {
                digit_used_row[i][j] = false;
                digit_used_col[i][j] = false;
                digit_used_area[i/3][i%3][j] = false;
                if (j<9) {
                    sudoku_board[i][j] = board[i][j];
                    sudoku_board[i][j] = board[i][j];
                }
            }
        }
        depth = 81;
        for(int i =0;i<9;i++){
            for(int j = 0;j<9;j++){
                if(board[i][j] != 0){
                    depth --;
                    int temp = board[i][j];
                    digit_used_row[i][temp]= true;
                    digit_used_col[j][temp]= true;
                    digit_used_area[i/3][j/3][temp]= true;
                }
            }
        }
    }



    /**
     * Performs constraint satisfaction on the given Sudoku board using Arc Consistency and Domain Splitting.
     *
     * @param board the 2d int array representing the Sudoku board. Zeros indicate unfilled cells.
     * @return the solved Sudoku board
     */
    public int[][] solve(int[][] board) {
        initialize(board);
        generator();
        boolean next_blank_valid= next_blank(sudoku_board);
        if(next_blank_valid){
            depth--;
            int[] temp = toDoArc.get(toDoArc.size() - 1);
            toDoArc.remove(toDoArc.size() - 1);
            int row = temp[0];
            int col = temp[1];
            int value = temp[2];
            arc_consistency_count(row, col, value);
            sudoku_board[row][col]=value;
            while(toDoArc.size()>0){
                if(depth==0)break;
                next_blank_valid= next_blank(sudoku_board);
                if(next_blank_valid){
                    depth--;
                    temp = toDoArc.get(toDoArc.size() - 1);
                    toDoArc.remove(toDoArc.size() - 1);
                    row = temp[0];
                    col = temp[1];
                    value = temp[2];
                    arc_consistency_count(row, col, value);
                    sudoku_board[row][col]=value;
                }else{
                    temp = toDoArc.get(toDoArc.size() - 1);
                    toDoArc.remove(toDoArc.size() - 1);
                    reset_previous_blank(row, col, board, temp[0], temp[1]);
                    row = temp[0];
                    col = temp[1];
                    value = temp[2];
                    arc_consistency_count(row, col, value);
                    sudoku_board[row][col]=value;

                }
            }
        }
        return sudoku_board;
    }



    //given row index and column index, apply arc consistency
    public void arc_consistency_count(int row, int col, int value){
        digit_used_row[row][value]= true;
        digit_used_col[col][value]= true;
        digit_used_area[row/3][col/3][value]= true;
    }

    //given row index and column index, trace back arc consistency
    public void arc_consistency_trace_back(int row, int col, int value){
        digit_used_row[row][value]= false;
        digit_used_col[col][value]= false;
        digit_used_area[row/3][col/3][value]= false;
    }

    //find the next blank space, return true if there are new arc added into toDoArc
    public boolean next_blank(int [][]board){
        for(int i =0;i<9;i++){
            for(int j = 0;j<9;j++){
                if(board[i][j] == 0){
                    return add_to_toDoArc(i, j);
                }
            }
        }
        return false;
    }

    //test and add possible branch into toDoArc, return tree if there is some arc added
    public boolean add_to_toDoArc (int row, int col){
        boolean add_arc = false;
        for(int i = 1; i< 10;i++){
            if(digit_used_row[row][i])continue;
            if(digit_used_col[col][i])continue;
            if(digit_used_area[row/3][col/3][i])continue;
            int[] newArc = {row,col,i};
            toDoArc.add(newArc);
            add_arc =true;
        }
        return add_arc;
    }

    //reset sudoku cells
    public void reset_previous_blank(int row,int col,int[][]board,int target_row,int target_col){
        boolean finish = false;
        for(int i =row;i>=0;i--){
            for(int j = 8;j>=0;j--){
                if(board[i][j] == 0){
                    arc_consistency_trace_back(i, j, sudoku_board[i][j]);
                    sudoku_board[i][j]=0;
                    depth++;
                    if(i==row && j>=col)
                        depth--;
                }
                if (i==target_row && j==target_col){
                    finish=true;
                    break;
                }
            }
            if (finish){break;}
        }
    }

    public int[][] generator(){
        int [][]soduku = new int [9][9];
        for (int i = 0; i<9;i++) {
            for (int j = 0; j < 9; j++) {
                while   (true)  {
                    int x = 1+(int)Math.random()*8;
                    for(int k = 0;k<8;k++) {
                        if (x!= soduku[i][k] && x!= soduku[k][j]){
                            soduku[i][j] =x;
                            break;
                        }
                    }
                }
            }
        }
        return soduku;
    }
}
