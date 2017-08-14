package View.SudokuGrid;

import android.content.Context;
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
            if((getArrayList().isEmpty()) && (this.value != -1)){
                if(this.value == 0){
                    this.value = value;
                }
                else if((0 < value) && (this.value != value)){
                    possibleValues.add(this.value);
                    possibleValues.add(value);
                    this.value = -1;
                }
                else{
                    possibleValues.clear();
                    this.value = value;
                }
            }
            else {
                if(value == 0){
                    possibleValues.clear();
                    this.value = 0;
                }
                else if(possibleValues.contains(value)){
                    possibleValues.remove(possibleValues.indexOf(value));
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
