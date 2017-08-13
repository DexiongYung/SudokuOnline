package View.Objects;

import java.util.Stack;

/**
 * Created by Dylan on 2017-08-12.
 */

public class UndoRedoStorage {
    private static UndoRedoStorage instance;
    private static Stack<tuple3> Undo;
    private static Stack<tuple3> Redo;

    public UndoRedoStorage(){}

    public static UndoRedoStorage getInstance(){
        if(instance == null){
            instance = new UndoRedoStorage();
            Undo = new Stack<tuple3>();
            Redo = new Stack<tuple3>();
        }

        return instance;
    }

    public void addToUndo(tuple3 v){
        if(!Undo.empty()){
            if(Undo.peek() != v)
                Undo.push(v);}
        else
            Undo.push(v);
    }

    public boolean redoEmpty(){
        return Redo.empty();
    }

    public tuple3 callRedo(){
        tuple3 temp = Redo.pop();
        Undo.push(temp);

        return temp;
    }

    public boolean undoEmpty(){
        return Undo.empty();
    }

    public tuple3 callUndo(){
        tuple3 temp = Undo.pop();
        Redo.push(temp);

        return temp;
    }
}
