package View.SudokuGrid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

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
        mPaint.setStyle(Paint.Style.FILL);
        Rect bounds = new Rect();

        if(getValue() == - 1)
        {
            mPaint.setTextSize(20);
            mPaint.getTextBounds(String.valueOf(getValue()) , 0 , String.valueOf(getValue()).length() , bounds);
            ArrayList<Integer> list = getArrayList();

            for(int i = 0 ; i < list.size() -1 ; i++){
                switch(list.get(i)){
                    case 1:{
                        canvas.drawText(String.valueOf(1), (getWidth() - bounds.width())*100 / 500 , (getHeight() + bounds.height())*100 / 500, mPaint);
                        break;
                    }
                    case 2: {
                        canvas.drawText(String.valueOf(2), (getWidth() - bounds.width()) * 100 / 200, (getHeight() + bounds.height()) * 100 / 500, mPaint);
                        break;
                    }
                    case 3: {
                        canvas.drawText(String.valueOf(3), (getWidth() - bounds.width()) * 100 / 125, (getHeight() + bounds.height()) * 100 / 500, mPaint);
                        break;
                    }
                    case 4: {
                        canvas.drawText(String.valueOf(4), (getWidth() - bounds.width())*100 / 500 , (getHeight() + bounds.height())*100 / 200, mPaint);
                        break;
                    }
                    case 5: {
                        canvas.drawText(String.valueOf(5), (getWidth() - bounds.width())*100 / 200 , (getHeight() + bounds.height())*100 / 200, mPaint);
                        break;
                    }
                    case 6:{
                        canvas.drawText(String.valueOf(6), (getWidth() - bounds.width())*100 / 125 , (getHeight() + bounds.height())*100 / 200, mPaint);
                        break;
                    }
                    case 7: {
                        canvas.drawText(String.valueOf(7), (getWidth() - bounds.width())*100 / 500 , (getHeight() + bounds.height())*100 / 125, mPaint);
                        break;
                    }
                    case 8: {
                        canvas.drawText(String.valueOf(8), (getWidth() - bounds.width())*100 / 200 , (getHeight() + bounds.height())*100 / 125, mPaint);
                        break;
                    }
                    case 9: {
                        canvas.drawText(String.valueOf(9), (getWidth() - bounds.width())*100 / 125 , (getHeight() + bounds.height())*100 / 125, mPaint);
                        break;
                    }
                }
            }
        }
        else if(getValue() != 0){
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(60);
            mPaint.getTextBounds(String.valueOf(getValue()) , 0 , String.valueOf(getValue()).length() , bounds);
            canvas.drawText(String.valueOf(getValue()), (getWidth() - bounds.width()) / 2 , (getHeight() + bounds.height()) / 2, mPaint);
        }
    }

    private void drawLines(Canvas canvas){
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }
}
