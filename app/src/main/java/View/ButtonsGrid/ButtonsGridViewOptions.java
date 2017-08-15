package View.ButtonsGrid;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.ubccpsc.android.sudokuonline.R;

import SudokuGenerator.GameEngine;

/**
 * Created by Adi on 2017-08-09.
 */

public class ButtonsGridViewOptions extends GridView {
    public ButtonsGridViewOptions(Context context, AttributeSet attrs){
        super(context, attrs);
        ButtonsGridViewAdapter gridViewAdapter = new ButtonsGridViewAdapter(context);
        setAdapter(gridViewAdapter);
    }

    class ButtonsGridViewAdapter extends BaseAdapter{

        private Context context;

        public ButtonsGridViewAdapter(Context context){
            this.context = context;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v == null){
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                v = inflater.inflate(R.layout.button, parent, false);

                NumberButton btn;
                btn = (NumberButton) v;
                btn.setTextSize(10);
                btn.setId(position);

                switch (position) {
                    case 0: {
                        btn.setText("Del");
                        btn.setNumber(0, 9);
                        break;
                    }
                    case 1: {
                        btn.setText("Undo");
                        btn.setNumber(0, 10);
                        break;
                    }
                    case 2: {
                        btn.setText("Redo");
                        btn.setNumber(0, 11);
                        if (GameEngine.getInstance().redoStorageEmpty())
                            btn.setEnabled(false);
                        else
                            btn.setEnabled(true);
                        break;
                    }
                    case 3: {
                        btn.setText("Draft");
                        btn.setNumber(0, 12);
                        break;
                    }
                }


                return btn;
            }
            return v;
        }
    }
}

