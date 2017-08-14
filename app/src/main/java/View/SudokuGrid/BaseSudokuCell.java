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
    private ArrayList<Integer> possibleValues = new ArrayList<Integer>();
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

    public void setInitValue(int value){
        this.value = value;
        invalidate();
    }

    public void setValue(int value){
        if(modifiable) {
            if (GameEngine.getInstance().getDraftModeSetting()) {
                if (possibleValues.contains(value))
                    possibleValues.remove(value);
                else
                    possibleValues.add(value);

                this.value = -1;
            }
            else {
                this.value = value;
            }
        }
        invalidate();
    }

    public int getValue(){
        return value;
    }

    public ArrayList<Integer> getArrayList(){
        return possibleValues;
    }
}
