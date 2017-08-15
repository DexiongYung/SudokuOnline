package View.SudokuGrid.UndoRedo;

import java.util.ArrayList;

/**
 * Created by Dylan on 2017-08-15.
 */

public class tuple {
    private ArrayList<Integer> draft = new ArrayList<>();
    private int value;

    public tuple(int v, ArrayList<Integer> d) {
        this.value = v;
        this.draft = d;
    }

    public ArrayList<Integer> getDraft() {
        return draft;
    }

    public int getValue() {
        return value;
    }
}
