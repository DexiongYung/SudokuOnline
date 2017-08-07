package View;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ubccpsc.android.sudokuonline.R;

import SudokuGenerator.GameEngine;

/**
 * Created by Dylan on 2017-08-07.
 */

public class SudokuGridViewAdapter extends BaseAdapter{
    private Context context;


    public SudokuGridViewAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 81;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            v = inflater.inflate((R.layout.cell), parent, false);
        }

        SudokuCell cell = (SudokuCell) v;
        int x = position % 9;
        int y = position / 9;
        cell.setNumber(GameEngine.getInstance().getSudoku()[x][y]);

        return v;
    }
}
