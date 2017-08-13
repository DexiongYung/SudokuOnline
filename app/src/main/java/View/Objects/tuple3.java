package View.Objects;

/**
 * Created by Dylan on 2017-08-12.
 */

public class tuple3 {
    private int x_coordinate;
    private int y_coordinate;
    private int number;

    public tuple3(int x, int y, int n){
        x_coordinate = x;
        y_coordinate = y;
        number = n;
    }

    public int getX_coordinate(){
        return x_coordinate;
    }

    public int getY_coordinate(){
        return y_coordinate;
    }

    public int getNumber(){
        return number;
    }
}
