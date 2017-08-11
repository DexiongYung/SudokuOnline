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

    public NumberButton(Context context, AttributeSet attrs){
        super(context, attrs);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        GameEngine.getInstance().setNumber(number);
    }

    public void setNumber(int number){
        this.number = number;
    }



}
