package View.ButtonsGrid;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

import com.ubccpsc.android.sudokuonline.R;

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
            GameEngine.getInstance().undoFunction();
        else if (index == 10)
            GameEngine.getInstance().redoFunction();
        else if (index == 11) {
            GameEngine.getInstance().draftModeSetter();

            if (GameEngine.getInstance().getDraftModeSetting())
                v.setBackgroundResource(R.drawable.ic_grid_on_black_24dp);
            else
                v.setBackgroundResource(R.drawable.ic_grid_off_black_24dp);
        } else
            GameEngine.getInstance().setNumber(number);
    }

    public void setNumber(int number, int index){
        this.number = number;
        this.index = index;
    }
}
