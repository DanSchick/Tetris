/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import javafx.scene.layout.Pane;
import java.util.ArrayList;

/**
 * A Pane in which tetris squares can be displayed.
 * 
 * @author pipWolfe
 */
public class TetrisBoard extends Pane{
    // The size of the side of a tetris square
    public static final int SQUARE_SIZE = 40;
    // The number of squares that fit on the screen in the x and y dimensions
    public static final int X_DIM_SQUARES = 10;
    public static final int Y_DIM_SQUARES = 20;
    // create the list that will hold  all pieces on the board
    public static ArrayList<ArrayList<TetrisSquare>> pieces = new ArrayList<ArrayList<TetrisSquare>>();

    /**
     * Sizes the board to hold the specified number of squares in the x and y
     * dimensions.
     */
    public TetrisBoard() {
        System.out.println(Y_DIM_SQUARES);
        this.setPrefHeight(Y_DIM_SQUARES*SQUARE_SIZE);
        this.setPrefWidth(X_DIM_SQUARES*SQUARE_SIZE);
        for(int i=0;i<=Y_DIM_SQUARES;i++){
            pieces.add(new ArrayList<TetrisSquare>(X_DIM_SQUARES+1));
                for(int j=0;j<=X_DIM_SQUARES-1;j++){
                    pieces.get(i).add(j, null);
                }
        }
    }
    public static void addPiece(ArrayList<TetrisSquare> squares){
	    for(TetrisSquare sq : squares){
            pieces.get(sq.getY()).set(sq.getX(), sq);
	    }
        for(int i=0; i<=Y_DIM_SQUARES;i++){
            if(!pieces.get(i).contains(null)){
                for(int j=0;j<=X_DIM_SQUARES-1;j++){
                    pieces.get(i).get(j).removeFromDrawing();
                    //TODO: Move all pieces down when row is removed
                }
                //TODO: change foreach to an int i loop, the iterator might be interfering with the move method()
                for(int k=0;k<i;k++){
                    for(TetrisSquare sq : pieces.get(k)){
                        if(sq != null){
                            sq.moveToTetrisLocation(sq.getX(), sq.getY() + 1);
                        }
                    }
                }
                System.out.println(pieces.get(i-2));
                pieces.remove(i);
//                for(int p=i+1;i<Y_DIM_SQUARES-2;p++){
//                    pieces.set(p-1, pieces.get(p));
//                }
                pieces.add(0, new ArrayList<TetrisSquare>(X_DIM_SQUARES+1));
                System.out.println(pieces.get(i-1));
                for(int l=0;l<=X_DIM_SQUARES-1;l++){
                    pieces.get(0).add(l, null);
                }
            }

        }
    }

}
