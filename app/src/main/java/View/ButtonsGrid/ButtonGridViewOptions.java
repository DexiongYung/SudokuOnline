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
 * Created by Dylan on 2017-08-12.
 */

public class ButtonGridViewOptions extends GridView{
    public ButtonGridViewOptions(Context context, AttributeSet attrs){
        super(context, attrs);
        ButtonsGridViewAdapter gridViewAdapter = new ButtonsGridViewAdapter(context);
        setAdapter(gridViewAdapter);
    }

    class ButtonsGridViewAdapter extends BaseAdapter {

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

                if (position == 0){
                    btn.setText("DEL");
                    btn.setNumber(0 , 9);
                }
                else if(position == 1) {
                    btn.setText("UNDO");
                    btn.setNumber(0, 10);
                }
                else if(position == 2){
                    btn.setText("REDO");
                    btn.setNumber(0, 11);
                }
                else {
                    btn.setText("DEL GRID");
                    btn.setNumber(0, 20);
                }

                return btn;
            }
            return v;
        }
    }
}
