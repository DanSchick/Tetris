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
    public static final int SQUARE_SIZE = 20;
    // The number of squares that fit on the screen in the x and y dimensions
    public static final int X_DIM_SQUARES = 20;
    public static final int Y_DIM_SQUARES = 45;
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
            pieces.add(new ArrayList<TetrisSquare>(X_DIM_SQUARES));
                for(int j=0;j<=X_DIM_SQUARES;j++){
                    pieces.get(i).add(j, null);
                }
            System.out.println(pieces.get(i));
        }
    }
    public static void addPiece(ArrayList<TetrisSquare> squares){
	    System.out.println("sentinel");
	    for(TetrisSquare sq : squares){
            pieces.get(sq.getY()).set(sq.getX(), sq);
	    }
    }

}
