package View.SudokuGrid;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

import SudokuGenerator.GameEngine;

/**
 * Created by Adi on 2017-08-07.
 */

public class BaseSudokuCell extends View {
    private int value;
    private ArrayList<Integer> draft = new ArrayList<Integer>();
    private boolean modifiable = true;

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
    }

    public void setInitialValue(int value) {
        this.value = value;
        invalidate();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value){
        if(modifiable) {
            if (GameEngine.getInstance().getDraftModeSetting()) {
                if (draft.contains(value)) {
                    draft.remove(draft.indexOf(value));

                    if (draft.isEmpty())
                        this.value = 0;
                    else
                        this.value = -1;
                } else {
                    draft.add(value);
                    this.value = -1;
                }
            }
            else {
                this.value = value;
                draft.clear();
            }
        }
        invalidate();
    }

    public ArrayList<Integer> getDraft() {
        return draft;
    }

    public boolean draftEmpty() {
        return draft.isEmpty();
    }
}
