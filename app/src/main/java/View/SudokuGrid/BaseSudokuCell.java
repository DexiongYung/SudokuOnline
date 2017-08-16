package View.SudokuGrid;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.Stack;

import SudokuGenerator.GameEngine;
import View.SudokuGrid.UndoRedo.tuple;

/**
 * Created by Adi on 2017-08-07.
 */

public class BaseSudokuCell extends View {
    private int value;
    private ArrayList<Integer> draft = new ArrayList<Integer>();
    private boolean modifiable = true;
    private Stack<tuple> undoStack = new Stack<tuple>();
    private Stack<tuple> redoStack = new Stack<tuple>();

    public BaseSudokuCell(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public boolean isModifiable() {
        return modifiable;
    }

    public void setNotModifiable(){
        modifiable = false;
        draft = null;
        undoStack = null;
        redoStack = null;
    }

    public void setInitialValue(int value) {
        this.value = value;
        undoStack.push(new tuple(getValue(), new ArrayList<Integer>()));
        invalidate();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value){
        if(modifiable) {
            if (GameEngine.getInstance().getDraftModeSetting()) {
                this.value = -1;
                if (draft.contains(value)) {
                    draft.remove(draft.indexOf(value));
                } else if (value == 0) {
                    draft.clear();
                } else {
                    draft.add(value);
                }
            }
            else {
                this.value = value;
                draft.clear();
            }
            redoStack.empty();

            ArrayList<Integer> temp = new ArrayList<Integer>();
            for (int i = 0; i < draft.size(); i++) {
                temp.add(draft.get(i));
            }
            int val = this.value;
            undoStack.push(new tuple(val, temp));
        }
        invalidate();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(undoStack.pop());

            if (!undoStack.isEmpty()) {
                tuple temp = undoStack.pop();
                redoStack.push(temp);
                setValue(temp.getValue());
                setDraft(temp.getDraft());
            }
        }
        invalidate();
    }

    public ArrayList<Integer> getDraft() {
        ArrayList<Integer> temp = this.draft;
        return temp;
    }

    public void setDraft(ArrayList<Integer> a) {
        this.draft = a;
    }
}
