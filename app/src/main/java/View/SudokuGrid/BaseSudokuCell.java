package View.SudokuGrid;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

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

    public void setNotModifiable(){
        modifiable = false;
    }

    public void setInitValue(int value){
        this.value = value;
        invalidate();
    }

    public void setValue(int value){
        if(modifiable) {
            if(getArrayList().isEmpty() && this.value != -1){
                if(this.value == 0)
                    this.value = value;
                else if(this.value != value){
                    possibleValues.add(this.value);
                    possibleValues.add(value);
                    this.value = -1;
                }
            }
            else {
                if(possibleValues.contains(value)){
                    this.value = value;
                    possibleValues.clear();
                }
                else
                    possibleValues.add(value);
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
