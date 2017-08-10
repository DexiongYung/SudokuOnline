package View.SudokuGrid;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Adi on 2017-08-07.
 */

public class BaseSudokuCell extends View {
    private int value;

    public BaseSudokuCell(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public void setValue(int value){
        this.value = value;

        invalidate();
    }

    public int getValue(){
        return value;
    }
}
