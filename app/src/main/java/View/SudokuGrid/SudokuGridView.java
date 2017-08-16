package View.SudokuGrid;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;

import SudokuGenerator.GameEngine;

/**
 * Created by Dylan on 2017-08-07.
 */

public class SudokuGridView extends GridView {
    private Context context;
    private int nPrevSelGridItem = -1;
    private int[] highlightRowPositions = new int[9];
    private int[] highlightColumnPositions = new int[9];
    private ArrayList<Integer> highlightRegionPositions = new ArrayList<>();

    private int[] OLDhighlightRowPositions = new int[9];
    private int[] OLDhighlightColumnPositions = new int[9];
    private ArrayList<Integer> OLDhighlightRegionPositions = new ArrayList<>();

    public SudokuGridView(final Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        this.context = context;

        SudokuGridViewAdapter gridViewAdapter = new SudokuGridViewAdapter(context);

        setAdapter(gridViewAdapter);

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int x = position % 9;
                int y = position / 9;

                GameEngine.getInstance().setSelectedPosition(x,y);
                try {
                    if (nPrevSelGridItem != -1) {
                        for(int g = 0; g<9; g++){
                            View oldRow = getChildAt(OLDhighlightRowPositions[g]);
                            oldRow.setBackgroundColor(Color.WHITE);

                            View oldCol = getChildAt(OLDhighlightColumnPositions[g]);
                            oldCol.setBackgroundColor(Color.WHITE);

                            View oldReg = getChildAt(OLDhighlightRegionPositions.get(g));
                            oldReg.setBackgroundColor(Color.WHITE);
                        }
                    }
                    nPrevSelGridItem = position;
                    if (nPrevSelGridItem == position) {
                        //get all the children at positions in the array
                        //then set the background colour of all those views
                        highlightRowPositions = GameEngine.getInstance().getRowToHighlight(position);
                        highlightColumnPositions = GameEngine.getInstance().getColumnToHighlight(position);
                        highlightRegionPositions = GameEngine.getInstance().getRegionToHighlight(position);

                        for(int z = 0; z<9; z++){
                            View newRow = getChildAt(highlightRowPositions[z]);
                            newRow.setBackgroundColor(Color.parseColor("#e7eecc"));

                            View newCol = getChildAt(highlightColumnPositions[z]);
                            newCol.setBackgroundColor(Color.parseColor("#e7eecc"));

                            View newReg = getChildAt(highlightRegionPositions.get(z));
                            newReg.setBackgroundColor(Color.parseColor("#e7eecc"));
                        }

                        OLDhighlightRowPositions = highlightRowPositions;
                        OLDhighlightColumnPositions = highlightColumnPositions;
                        OLDhighlightRegionPositions = highlightRegionPositions;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    class SudokuGridViewAdapter extends BaseAdapter {
        private Context context;


        public SudokuGridViewAdapter(Context context) {
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
            return GameEngine.getInstance().getGrid().getItem(position);
        }
    }
}
