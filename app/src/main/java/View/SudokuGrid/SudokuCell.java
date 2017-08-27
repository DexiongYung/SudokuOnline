package View.SudokuGrid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ubccpsc.android.sudokuonline.R;

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
            //Draft value getter and draft text size
            mPaint.setTextSize(getResources().getDimension(R.dimen.draftTextSize));
            mPaint.getTextBounds(String.valueOf(getValue()) , 0 , String.valueOf(getValue()).length() , bounds);
            ArrayList<Integer> list = getDraft();

            //Setting coordinates of draft numbers
            for(int i = 0 ; i < list.size(); i++){
                switch (list.get(i)) {
                    case 1:
                        canvas.drawText(String.valueOf(1), (getWidth() - bounds.width()) * 100 / 500, (getHeight() + bounds.height()) * 100 / 500, mPaint);
                        break;
                    case 2:
                        canvas.drawText(String.valueOf(2), (getWidth() - bounds.width()) * 100 / 200, (getHeight() + bounds.height()) * 100 / 500, mPaint);
                        break;
                    case 3:
                        canvas.drawText(String.valueOf(4), (getWidth() - bounds.width()) * 100 / 500, (getHeight() + bounds.height()) * 100 / 200, mPaint);
                        break;
                    case 4:
                        canvas.drawText(String.valueOf(4), (getWidth() - bounds.width()) * 100 / 500, (getHeight() + bounds.height()) * 100 / 200, mPaint);
                        break;
                    case 5:
                        canvas.drawText(String.valueOf(5), (getWidth() - bounds.width()) * 100 / 200, (getHeight() + bounds.height()) * 100 / 200, mPaint);
                        break;
                    case 6:
                        canvas.drawText(String.valueOf(6), (getWidth() - bounds.width()) * 100 / 125, (getHeight() + bounds.height()) * 100 / 200, mPaint);
                        break;
                    case 7:
                        canvas.drawText(String.valueOf(7), (getWidth() - bounds.width()) * 100 / 500, (getHeight() + bounds.height()) * 100 / 125, mPaint);
                        break;
                    case 8:
                        canvas.drawText(String.valueOf(8), (getWidth() - bounds.width()) * 100 / 200, (getHeight() + bounds.height()) * 100 / 125, mPaint);
                        break;
                    case 9:
                        canvas.drawText(String.valueOf(9), (getWidth() - bounds.width()) * 100 / 125, (getHeight() + bounds.height()) * 100 / 125, mPaint);
                        break;
                }
            }
        }
        //Set value if it's not draft
        else if(getValue() != 0){
            //Text settings
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(getResources().getDimension(R.dimen.textSize));
            mPaint.getTextBounds(String.valueOf(getValue()) , 0 , String.valueOf(getValue()).length() , bounds);

            //Change text color if it's an initial value
            if (!isModifiable())
                mPaint.setColor(Color.BLUE);

            //Set coordinate of number
            canvas.drawText(String.valueOf(getValue()), (getWidth() - bounds.width()) / 2 , (getHeight() + bounds.height()) / 2, mPaint);
        }
    }

    private void drawLines(Canvas canvas){
        //Set square lines
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth(getResources().getDimension(R.dimen.standardLineSize));
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);

        //Set square region lines
        mPaint.setColor(Color.BLACK);
        if ((getXCoordinate() == 2) || (getXCoordinate() == 5)) {
            mPaint.setStrokeWidth(getResources().getDimension(R.dimen.thickerLineSize));
            canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), mPaint);
        }
        if ((getYCoordinate() == 2) || (getYCoordinate() == 5)) {
            mPaint.setStrokeWidth(getResources().getDimension(R.dimen.thickerLineSize));
            canvas.drawLine(0, getHeight(), getWidth(), getHeight(), mPaint);
        }
    }
}
