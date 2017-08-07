package View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Dylan on 2017-08-07.
 */

public class SudokuCell extends View {
    private Paint mPaint;
    private int number;

    public SudokuCell(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setNumber(int number){
        this.number = number;

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(60);

        Rect bounds = new Rect();
        mPaint.getTextBounds(String.valueOf(number) , 0 , String.valueOf(number).length() , bounds);

        canvas.drawText(String.valueOf(number), (getWidth() - bounds.width()) / 2 , (getHeight() + bounds.height()) / 2, mPaint);
    }
}
