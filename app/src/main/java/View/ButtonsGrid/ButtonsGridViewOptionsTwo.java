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

/**
 * Created by Dylan on 2017-09-04.
 */

public class ButtonsGridViewOptionsTwo extends GridView {
    public ButtonsGridViewOptionsTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
        ButtonsGridViewAdapter gridViewAdapter = new ButtonsGridViewAdapter(context);
        setAdapter(gridViewAdapter);
    }

    private class ButtonsGridViewAdapter extends BaseAdapter {

        private Context context;

        private ButtonsGridViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return 2;
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
            if (v == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                v = inflater.inflate(R.layout.button, parent, false);

                //TODO
                //Add timer in between undo, redo and delete, draft
                NumberButton btn;
                btn = (NumberButton) v;
                btn.setTextSize(10);
                btn.setWidth(5);
                btn.setId(position);

                switch (position) {
                    case 0: {
                        btn.setBackgroundResource(R.drawable.ic_grid_off_black_24dp);
                        btn.setNumber(0, 11);
                        break;
                    }
                    case 1: {
                        btn.setBackgroundResource(R.drawable.ic_cancel_black_24dp);
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

