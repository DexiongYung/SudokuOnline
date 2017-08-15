package View.ButtonsGrid;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

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
        else if (index == 10) {
            GameEngine.getInstance().undoFunction();
        } else if (index == 11) {

        } else {
            GameEngine.getInstance().draftModeSetter();

            if (GameEngine.getInstance().getDraftModeSetting())
                v.getBackground().setColorFilter(Color.rgb(255, 182, 193), PorterDuff.Mode.ADD);
            else
                v.getBackground().clearColorFilter();
        }
    }

    public void setNumber(int number, int index){
        this.number = number;
        this.index = index;
    }
}
