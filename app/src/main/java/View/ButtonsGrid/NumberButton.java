package View.ButtonsGrid;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import SudokuGenerator.GameEngine;

/**
 * Created by Adi on 2017-08-09.
 */

public class NumberButton extends AppCompatButton implements View.OnClickListener {

    private int number;
    private int index;

    public NumberButton(Context context, AttributeSet attrs){
        super(context, attrs);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(index < 9)
            GameEngine.getInstance().setNumber(number);
        else if(index == 9)
            GameEngine.getInstance().setNumber(number);
        else
            GameEngine.getInstance().deleteGrid();
    }

    public void setNumber(int number, int index){
        this.number = number;
        this.index = index;
    }
}
