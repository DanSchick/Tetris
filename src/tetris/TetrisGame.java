/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import javafx.scene.paint.Color;

import java.util.*;

import javafx.geometry.Point2D;

/**
 * This should be implemented to include your game control.
 *
 * @author pipWolfe
 */
public class TetrisGame {

    private final Tetris tetrisApp;
    public ArrayList<TetrisSquare> piece1;
    private TetrisBoard tBoard;
    public Point2D relatives[] = new Point2D[3];

    /**
     * Initialize the game. Remove the example code and replace with code
     * that creates a random piece.
     *
     * @param tetrisApp A reference to the application (use to set
     *                  messages).
     * @param board     A reference to the board on which Squares are drawn
     */
    public TetrisGame(Tetris tetrisApp, TetrisBoard board) {
        // change createI to createZ or createO to change pieces
        piece1 = createT(board);
        tBoard = board;
        relatives = relative('T');
        piece1.get(0).moveToTetrisLocation(5, 10);
        piece1.get(0).moveToTetrisLocation(5, 0);

        this.tetrisApp = tetrisApp;
        // You can use this to show the score, etc.
        tetrisApp.setMessage("Alright let's do this");
    }

    /**
     * Animate the game, by moving the current tetris piece down.
     */
    void update() {
        //System.out.println("updating");
        piece1.get(0).moveToTetrisLocation(piece1.get(0).getX(), piece1.get(0).getY() + 1);
        Point2D[] newpiece = new Point2D[4];
        // move the piece to the new location to read it into the array
        newpiece[0] = new Point2D(piece1.get(0).getX(), piece1.get(0).getY());
        for (int i = 1; i <= 3; i++) {
            int x = (int) (piece1.get(0).getX() + (relatives[i - 1].getX()));
            int y = (int) (piece1.get(0).getY() + (relatives[i - 1].getY()));
            newpiece[i] = new Point2D(x, y);
        }
        piece1.get(0).moveToTetrisLocation(piece1.get(0).getX(), piece1.get(0).getY() - 1);
        if(commitLoc(checkSquare(newpiece), newpiece, 'm') == 0){
            // we need to move the piece to TetrisBoard and create a new piece
            TetrisBoard.addPiece(piece1);
            // Create new random piece
            newPiece();
        }

    }

    /**
     * Move the current tetris piece left.
     */
    void left() {
        Point2D[] newpiece = new Point2D[4];
        // move the piece to the new location to read it into the array
        piece1.get(0).moveToTetrisLocation(piece1.get(0).getX() - 1, piece1.get(0).getY());
        newpiece[0] = new Point2D(piece1.get(0).getX(), piece1.get(0).getY());
        for (int i = 1; i <= 3; i++) {
            int x = (int) (piece1.get(0).getX() + (relatives[i - 1].getX()));
            int y = (int) (piece1.get(0).getY() + (relatives[i - 1].getY()));
            newpiece[i] = new Point2D(x, y);
        }
        // now, move piece back to what it was and calculate if the square is available
        piece1.get(0).moveToTetrisLocation(piece1.get(0).getX() + 1, piece1.get(0).getY());
        commitLoc(checkSquare(newpiece), newpiece, 'm');
    }

    /**
     * Move the current tetris piece right.
     */
    void right() {
        // create a new piece with what the new location would be
        Point2D[] newpiece = new Point2D[4];
        // move the piece to the new location to read it into the array
        piece1.get(0).moveToTetrisLocation(piece1.get(0).getX() + 1, piece1.get(0).getY());
        newpiece[0] = new Point2D(piece1.get(0).getX(), piece1.get(0).getY());
        for (int i = 1; i <= 3; i++) {
            int x = (int) (piece1.get(0).getX() + (relatives[i - 1].getX()));
            int y = (int) (piece1.get(0).getY() + (relatives[i - 1].getY()));
            newpiece[i] = new Point2D(x, y);
        }
        // now, move piece back to what it was and calculate if the square is available
        piece1.get(0).moveToTetrisLocation(piece1.get(0).getX() - 1, piece1.get(0).getY());
        commitLoc(checkSquare(newpiece), newpiece, 'm');

    }

    /**
     * Drop the current tetris piece.
     */
    void drop() {
        while(true){
            piece1.get(0).moveToTetrisLocation(piece1.get(0).getX(), piece1.get(0).getY() + 1);
            Point2D[] newpiece = new Point2D[4];
            // move the piece to the new location to read it into the array
            newpiece[0] = new Point2D(piece1.get(0).getX(), piece1.get(0).getY());
            for (int i = 1; i <= 3; i++) {
                int x = (int) (piece1.get(0).getX() + (relatives[i - 1].getX()));
                int y = (int) (piece1.get(0).getY() + (relatives[i - 1].getY()));
                newpiece[i] = new Point2D(x, y);
            }
            piece1.get(0).moveToTetrisLocation(piece1.get(0).getX(), piece1.get(0).getY() - 1);
            if(commitLoc(checkSquare(newpiece), newpiece, 'm') == 0){
                TetrisBoard.addPiece(piece1);
                newPiece();
                break;
            }

        }
    }

    /**
     * Rotate the current piece clockwise.
     */
    void rotateRight() {
        Point2D[] oldCoords = new Point2D[4];
        // Now, we update the new relative coordinates
        for (int i = 0; i <= 2; i++) { // first we get a new relatives array with same data but different pointers
            oldCoords[i] = new Point2D(relatives[i].getX(), relatives[i].getY());
        }
        for (int i = 0; i <= 2; i++) { // then apply the formula to it
            oldCoords[i] = new Point2D((-1) * oldCoords[i].getY(), oldCoords[i].getX());
        }

        // create a new piece with what the new location would be
        Point2D[] newpiece = new Point2D[4];
        newpiece[0] = new Point2D(piece1.get(0).getX(), piece1.get(0).getY());
        // move the piece to the new location to read it into the array
        for (int i = 1; i <= 3; i++) {
            int x = (int) (piece1.get(0).getX() + (oldCoords[i - 1].getX()));
            int y = (int) (piece1.get(0).getY() + (oldCoords[i - 1].getY()));
            newpiece[i] = new Point2D(x, y);
        }

        // now we pass the info to commit loc flagged as a rotation move
        commitLoc(checkSquare(newpiece), oldCoords, 'r');


    }

    /**
     * Rotates the current piece counter-clockwise
     */
    void rotateLeft() {
        Point2D[] oldCoords2 = new Point2D[4];
        // Now, we update the new relative coordinates
        for (int i = 0; i <= 2; i++) {
            oldCoords2[i] = new Point2D(relatives[i].getX(), relatives[i].getY());
        }
        for (int i = 0; i <= 2; i++) {
            oldCoords2[i] = new Point2D(oldCoords2[i].getY(), (-1) * oldCoords2[i].getX());
            ;
        }
        // create a new piece with what the new location would be
        Point2D[] newpiece2 = new Point2D[4];
        newpiece2[0] = new Point2D(piece1.get(0).getX(), piece1.get(0).getY());
        // move the piece to the new location to read it into the array
        for (int i = 1; i <= 3; i++) {
            int x = (int) (piece1.get(0).getX() + (oldCoords2[i - 1].getX()));
            int y = (int) (piece1.get(0).getY() + (oldCoords2[i - 1].getY()));
            newpiece2[i] = new Point2D(x, y);
        }

        // now we pass the info to commit loc flagged as a rotation move
        commitLoc(checkSquare(newpiece2), oldCoords2, 'r');

    }

    public boolean checkSquare(Point2D[] newSqs) {
        // First, check if move is blocke by a square
        for (Point2D sq : newSqs) {
                if(TetrisBoard.pieces.get((int)sq.getY()).get((int)sq.getX()) != null){
                    return false;
            }
            // then, check it it's blocked by the sides of the game board
            if (sq.getX() < 0 || sq.getX() >= TetrisBoard.X_DIM_SQUARES) {
                return false;
            } else if (sq.getY() < 0 || sq.getY() >= TetrisBoard.Y_DIM_SQUARES) {
                return false;
            }
        }
        return true;
    }

    /**
     * Moves the piece if the new location is available.
     *
     * @param avail    Is the return of checkSquare. If it's false, we don't do anything.
     * @param newSqs   Is an array that we use to move the piece. Different depending on a move or rotate change
     * @param moveType Is the type of movement made. 'm' is left/right, 'r' is rotate left/right
     * @return A number that represents the success of the method. To be used for debugging, and not much else
     */
    int commitLoc(boolean avail, Point2D[] newSqs, char moveType) {
        // if the piece is being moved left/right
        if (moveType == 'm') {
            if (avail) {
                // newSqs represents the coordinates of the new location
                // we simply move the center to wherever the center of the new piece is
                piece1.get(0).moveToTetrisLocation((int) newSqs[0].getX(), (int) newSqs[0].getY());
                return 1;
            } else {
                return 0;
            }
        } else if (moveType == 'r') {
            // if it's a rotates
            if (avail) {
                // newSqs represents what the new relative locations are
                for (int i = 1; i <= 3; i++) {
                    // so we have to update the relatives field
                    relatives[i - 1] = new Point2D(newSqs[i - 1].getX(), newSqs[i - 1].getY());
                    // and then bind the non-central squares to their new location
                    piece1.get(i).xProperty().bind(piece1.get(0).xProperty().add(relatives[i - 1].getX()));
                    piece1.get(i).yProperty().bind(piece1.get(0).yProperty().add(relatives[i - 1].getY()));
                }
                return 1;
            } else {
                return 0;
            }

        }
        return 2;

    }

    /**
     * *********************** PIECE CREATION CODE ****************************
     */
    /**
     * Creates a line tetris piece
     *
     * @param board
     * @return the arraylist of squares that makes the piece
     */
    void newPiece(){
        // Create new random piece
        int rand = (int)(Math.random() * 8);
        if(rand == 0){
            piece1 = createI(tBoard);
            relatives = relative('I');
        } else if(rand == 1){
            piece1 = createJ(tBoard);
            relatives = relative('J');
        } else if(rand == 2){
            piece1 = createL(tBoard);
            relatives = relative('L');
        } else if(rand == 3){
            piece1 = createO(tBoard);
            relatives = relative('O');
        } else if(rand == 4){
            piece1 = createS(tBoard);
            relatives = relative('S');
        } else if(rand == 5){
            piece1 = createT(tBoard);
            relatives = relative('T');
        } else if(rand == 6){
            piece1 = createZ(tBoard);
            relatives = relative('Z');
        }
    }
    ArrayList<TetrisSquare> createI(TetrisBoard board) {
        ArrayList<TetrisSquare> piece = new ArrayList<TetrisSquare>();
        for (int i = 0; i < 4; i++) {
            piece.add(new TetrisSquare(board));
        }
        piece.get(0).moveToTetrisLocation(2, 3);
        piece.get(1).xProperty().bind(piece.get(0).xProperty());
        piece.get(1).yProperty().bind(piece.get(0).yProperty().subtract(1));
        piece.get(2).xProperty().bind(piece.get(0).xProperty());
        piece.get(2).yProperty().bind(piece.get(0).yProperty().add(1));
        piece.get(3).xProperty().bind(piece.get(0).xProperty());
        piece.get(3).yProperty().bind(piece.get(0).yProperty().add(2));
        return piece;
    }

    /**
     * At first I tried this method to have a list of all square's relative
     * to the center. I wasn't able to make it work for more than one
     * rotation though, I keep it in the source so you can see what I wanted
     * to do
     *
     * @param type
     * @return A list of the relative spaces of the 3 non-central squares
     */
    Point2D[] relative(char type) {
        if (type == 'I') {
            Point2D[] list = new Point2D[3];
            list[0] = new Point2D(0, -1);
            list[1] = new Point2D(0, 1);
            list[2] = new Point2D(0, 2);
            return list;
        }
        if (type == 'T') {
            Point2D[] list = new Point2D[3];
            list[0] = new Point2D(-1, 0);
            list[1] = new Point2D(1, 0);
            list[2] = new Point2D(0, 1);
            return list;
        }
        if (type == 'S') {
            Point2D[] list = new Point2D[3];
            list[0] = new Point2D(-1, 0);
            list[1] = new Point2D(0, -1);
            list[2] = new Point2D(1, -1);
            return list;
        }
        if (type == 'Z') {
            Point2D[] list = new Point2D[3];
            list[0] = new Point2D(-1, -1);
            list[1] = new Point2D(0, -1);
            list[2] = new Point2D(1, 0);
            return list;
        }
        if (type == 'J') {
            Point2D[] list = new Point2D[3];
            list[0] = new Point2D(1, -1);
            list[1] = new Point2D(0, -1);
            list[2] = new Point2D(0, 1);
            return list;
        }
        if (type == 'L') {
            Point2D[] list = new Point2D[3];
            list[0] = new Point2D(-1, -1);
            list[1] = new Point2D(0, -1);
            list[2] = new Point2D(0, 1);
            return list;
        }
        if (type == 'O') {
            Point2D[] list = new Point2D[3];
            list[0] = new Point2D(-1, 0);
            list[1] = new Point2D(-1, -1);
            list[2] = new Point2D(0, -1);
            return list;
        }
        return null;
    }

    ArrayList<TetrisSquare> createO(TetrisBoard board) {
        ArrayList<TetrisSquare> piece = new ArrayList<TetrisSquare>();
        for (int i = 0; i < 4; i++) {
            piece.add(new TetrisSquare(board));
        }
        piece.get(0).moveToTetrisLocation(5, 0);
        piece.get(1).xProperty().bind(piece.get(0).xProperty().subtract(1));
        piece.get(1).yProperty().bind(piece.get(0).yProperty());
        piece.get(2).xProperty().bind(piece.get(0).xProperty().subtract(1));
        piece.get(2).yProperty().bind(piece.get(0).yProperty().subtract(1));
        piece.get(3).xProperty().bind(piece.get(0).xProperty());
        piece.get(3).yProperty().bind(piece.get(0).yProperty().subtract(1));

        return piece;
    }

    ArrayList<TetrisSquare> createL(TetrisBoard board) {
        ArrayList<TetrisSquare> piece = new ArrayList<TetrisSquare>();
        for (int i = 0; i < 4; i++) {
            piece.add(new TetrisSquare(board));
        }
        piece.get(0).moveToTetrisLocation(5, 0);
        piece.get(1).xProperty().bind(piece.get(0).xProperty().subtract(1));
        piece.get(1).yProperty().bind(piece.get(0).yProperty().subtract(1));
        piece.get(2).xProperty().bind(piece.get(0).xProperty());
        piece.get(2).yProperty().bind(piece.get(0).yProperty().subtract(1));
        piece.get(3).xProperty().bind(piece.get(0).xProperty());
        piece.get(3).yProperty().bind(piece.get(0).yProperty().add(1));

        return piece;
    }

    ArrayList<TetrisSquare> createJ(TetrisBoard board) {
        ArrayList<TetrisSquare> piece = new ArrayList<TetrisSquare>();
        for (int i = 0; i < 4; i++) {
            piece.add(new TetrisSquare(board));
        }
        piece.get(0).moveToTetrisLocation(5, 0);
        piece.get(1).xProperty().bind(piece.get(0).xProperty().add(1));
        piece.get(1).yProperty().bind(piece.get(0).yProperty().subtract(1));
        piece.get(2).xProperty().bind(piece.get(0).xProperty());
        piece.get(2).yProperty().bind(piece.get(0).yProperty().subtract(1));
        piece.get(3).xProperty().bind(piece.get(0).xProperty());
        piece.get(3).yProperty().bind(piece.get(0).yProperty().add(1));

        return piece;
    }

    ArrayList<TetrisSquare> createZ(TetrisBoard board) {
        ArrayList<TetrisSquare> piece = new ArrayList<TetrisSquare>();

        for (int i = 0; i < 4; i++) {
            piece.add(new TetrisSquare(board));
        }
        piece.get(0).moveToTetrisLocation(5, 0);
        piece.get(1).xProperty().bind(piece.get(0).xProperty().subtract(1));
        piece.get(1).yProperty().bind(piece.get(0).yProperty().subtract(1));
        piece.get(2).xProperty().bind(piece.get(0).xProperty());
        piece.get(2).yProperty().bind(piece.get(0).yProperty().subtract(1));
        piece.get(3).xProperty().bind(piece.get(0).xProperty().add(1));
        piece.get(3).yProperty().bind(piece.get(0).yProperty());

        return piece;
    }

    ArrayList<TetrisSquare> createS(TetrisBoard board) {
        ArrayList<TetrisSquare> piece = new ArrayList<TetrisSquare>();
        for (int i = 0; i < 4; i++) {
            piece.add(new TetrisSquare(board));
        }
        piece.get(0).moveToTetrisLocation(5, 0);
        piece.get(1).xProperty().bind(piece.get(0).xProperty().subtract(1));
        piece.get(1).yProperty().bind(piece.get(0).yProperty());
        piece.get(2).xProperty().bind(piece.get(0).xProperty());
        piece.get(2).yProperty().bind(piece.get(0).yProperty().subtract(1));
        piece.get(3).xProperty().bind(piece.get(0).xProperty().add(1));
        piece.get(3).yProperty().bind(piece.get(0).yProperty().subtract(1));

        return piece;
    }

    ArrayList<TetrisSquare> createT(TetrisBoard board) {
        ArrayList<TetrisSquare> piece = new ArrayList<TetrisSquare>();
        for (int i = 0; i < 4; i++) {
            piece.add(new TetrisSquare(board));
        }
        piece.get(0).moveToTetrisLocation(5, 0);
        piece.get(1).xProperty().bind(piece.get(0).xProperty().subtract(1));
        piece.get(1).yProperty().bind(piece.get(0).yProperty());
        piece.get(2).xProperty().bind(piece.get(0).xProperty().add(1));
        piece.get(2).yProperty().bind(piece.get(0).yProperty());
        piece.get(3).xProperty().bind(piece.get(0).xProperty());
        piece.get(3).yProperty().bind(piece.get(0).yProperty().add(1));

        return piece;
    }

}
