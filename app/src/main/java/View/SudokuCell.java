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

public class SudokuCell extends BaseSudokuCell {
    private Paint mPaint;

    public SudokuCell(Context context){
        super(context);

        mPaint = new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawNumber(canvas);
        drawLines(canvas);

    }

    private void drawNumber(Canvas canvas){
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(60);
        mPaint.setStyle(Paint.Style.FILL);

        Rect bounds = new Rect();
        mPaint.getTextBounds(String.valueOf(getVaue()) , 0 , String.valueOf(getVaue()).length() , bounds);

        canvas.drawText(String.valueOf(getVaue()), (getWidth() - bounds.width()) / 2 , (getHeight() + bounds.height()) / 2, mPaint);
    }

    private void drawLines(Canvas canvas){
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }
}
