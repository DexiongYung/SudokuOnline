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
    private ArrayList<Integer> draft = new ArrayList<>();
    private boolean modifiable = true;
    private Stack<tuple> undoStack = new Stack<>();
    private Stack<tuple> redoStack = new Stack<>();
    private int x;
    private int y;

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
                if (value == 0)
                    draft.clear();
                else if (draft.contains(value)) {
                    draft.remove(draft.indexOf(value));
                } else {
                    draft.add(value);
                }
                this.value = -1;
            }
            else {
                this.value = value;
                draft.clear();
            }
            pushUndoStack(this.value);
            redoStack.clear();
            invalidate();
        }
    }

    public ArrayList<Integer> getDraft() {
        return this.draft;
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            tuple t = redoStack.pop();
            undoStack.push(t);

            this.value = t.getValue();
            this.draft = t.getDraft();
            invalidate();
        }
    }

    public void pushUndoStack(int value) {
        ArrayList<Integer> temp = new ArrayList<>();

        for (int i = 0; i < draft.size(); i++)
            temp.add(draft.get(i));

        undoStack.push(new tuple(value, temp));
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            tuple t = undoStack.pop();
            redoStack.push(t);

            if (!undoStack.isEmpty()) {
                tuple temp = undoStack.peek();
                this.value = temp.getValue();
                this.draft = temp.getDraft();
            }
        }
        invalidate();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getXCoordinate() {
        return this.x;
    }

    public int getYCoordinate() {
        return this.y;
    }
}
